package com.acme.example.test;

import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter

import com.acme.example.data.MemberRepository
import com.acme.example.model.Member
import com.acme.example.rest.MemberService
import com.acme.example.service.MemberRegistration
import com.acme.example.util.Resources

public class Deployments {

    static def createDeployment() {
        // if use ShrinkWrap importer
        //createDeploymentByImporter()
        // otherwise, construct deployment by hand
        createDeploymentStepByStep()
    }

    static def WebArchive createDeploymentByImporter() {
        ShrinkWrap.create(MavenImporter.class, "arquillian-rest-scenario.war").loadPomFromFile("pom.xml")
                .importBuildOutput().as(WebArchive.class)
    }

    def static createDeploymentStepByStep() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "arquillian-rest-scenario.war")
                // add classes
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage(),
                MemberRepository.class.getPackage(), MemberRegistration.class.getPackage())
                // add configuration - resources from src/main/resources
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsWebInfResource("arquillian-ds.xml")
                // add initial data
                .addAsResource("import.sql")

        // add web pages and other resources
        war = ArchiveUtils.addWebResourcesRecursively(war, new File("src/main/webapp"))

        // add required libraries
        def requiredLibraries = Maven.resolver().loadPomFromFile("pom.xml").resolve("org.apache.commons:commons-lang3")
                .withTransitivity().as(File.class)

        war.addAsLibraries(requiredLibraries)
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
                    archive.addAsWebResource(file, getArchivePath(webAppDirectory, file))
                }
            }
            archive
        }

        private static String getArchivePath(File parent, File file) {
            if (file == null) {
                throw new IllegalArgumentException("File to be archived must not be null.")
            }

            try {
                String parentCanonicalPath = parent.getCanonicalPath()
                String fileCanonicalPath = file.getCanonicalPath()
                return fileCanonicalPath.replace(parentCanonicalPath, "").replace(File.separatorChar, '/' as char)
            } catch (IOException e) {
                return file.getPath().replace(File.separatorChar, '/')
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

                def allFiles = []
                Queue<File> dirs = new LinkedList<File>()
                dirs.add(root)
                while (!dirs.isEmpty()) {
                    for (File f : dirs.poll().listFiles()) {
                        if (f.isDirectory()) {
                            allFiles.add(f)
                            dirs.add(f)
                        } else if (f.isFile()) {
                            allFiles.add(f)
                        }
                    }
                }
                allFiles
            }
        }
    }
}
