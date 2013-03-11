package com.acme.example.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.jboss.shrinkwrap.resolver.api.maven.filter.MavenResolutionFilter;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.DefaultTransitiveExclusionPolicy;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.MavenResolutionStrategy;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.TransitiveExclusionPolicy;

import com.acme.example.data.MemberRepository;
import com.acme.example.model.Member;
import com.acme.example.rest.MemberService;
import com.acme.example.service.MemberRegistration;
import com.acme.example.util.Resources;

public class Deployments {

    public static WebArchive createDeployment() {
        WebArchive archive = null;
        // if experimental versions are enabled
        // archive = createDeploymentByImporter();
        // otherwise, construct deployment by hand
        archive = createDeploymentStepByStep();

        return archive;
    }

    public static WebArchive createDeploymentByImporter() {
        return ShrinkWrap.create(MavenImporter.class).loadPomFromFile("pom.xml")
                .importBuildOutput(new ResolutionScopesStrategy(ScopeType.COMPILE)).as(WebArchive.class);
    }

    public static WebArchive createDeploymentStepByStep() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class)
                // add classes
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage(),
                        MemberRepository.class.getPackage(), MemberRegistration.class.getPackage())
                // add configuration - resources from src/main/resources
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsWebInfResource("arquillian-ds.xml");

        // add web pages and other resources
        war = ArchiveUtils.addWebResourcesRecursively(war, new File("src/main/webapp"));

        // add required libraries
        File[] requiredLibraries = Maven.resolver().loadPomFromFile("pom.xml").resolve("org.apache.commons:commons-lang3")
                .withTransitivity().as(File.class);

        return war.addAsLibraries(requiredLibraries);
    }

    /**
     * An utility to add resources to the archive using file system independent abstraction
     *
     * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
     *
     */
    private static class ArchiveUtils {

        /**
         * Recursively adds files present in {@code webAppDirectory} to the {@code archive}.
         *
         * @param archive The archive to be enriched
         * @param webAppDirectory Directory to be added to the archive
         * @return Modified archive
         */
        public static WebArchive addWebResourcesRecursively(WebArchive archive, File webAppDirectory) {
            for (File file : FileUtils.listFiles(webAppDirectory)) {
                if (!file.isDirectory()) {
                    archive.addAsWebResource(file, getArchivePath(webAppDirectory, file));
                }
            }
            return archive;
        }

        private static String getArchivePath(File parent, File file) {
            if (file == null) {
                throw new IllegalArgumentException("File to be archived must not be null.");
            }

            try {
                String parentCanonicalPath = parent.getCanonicalPath();
                String fileCanonicalPath = file.getCanonicalPath();
                return fileCanonicalPath.replace(parentCanonicalPath, "").replace(File.separatorChar, '/');
            } catch (IOException e) {
                return file.getPath().replace(File.separatorChar, '/');
            }
        }

        /**
         * Simple directory listing utility
         *
         * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
         *
         */
        private static final class FileUtils {
            public static Collection<File> listFiles(File root) {

                List<File> allFiles = new ArrayList<File>();
                Queue<File> dirs = new LinkedList<File>();
                dirs.add(root);
                while (!dirs.isEmpty()) {
                    for (File f : dirs.poll().listFiles()) {
                        if (f.isDirectory()) {
                            allFiles.add(f);
                            dirs.add(f);
                        } else if (f.isFile()) {
                            allFiles.add(f);
                        }
                    }
                }
                return allFiles;
            }
        }
    }

    private static final class ResolutionScopesStrategy implements MavenResolutionStrategy {

        private final ScopeType[] scopes;

        public ResolutionScopesStrategy(ScopeType... scopes) {
            this.scopes = scopes;
        }

        @Override
        public TransitiveExclusionPolicy getTransitiveExclusionPolicy() {
            return DefaultTransitiveExclusionPolicy.INSTANCE;
        }

        @Override
        public MavenResolutionFilter[] getPreResolutionFilters() {
            return new MavenResolutionFilter[] { new ScopeFilter(scopes) };
        }

        @Override
        public MavenResolutionFilter[] getResolutionFilters() {
            return new MavenResolutionFilter[] { new ScopeFilter(scopes) };
        }

    }

}
