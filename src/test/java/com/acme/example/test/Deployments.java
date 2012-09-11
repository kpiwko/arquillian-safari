package com.acme.example.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;

import com.acme.example.data.MemberRepository;
import com.acme.example.model.Member;
import com.acme.example.rest.MemberService;
import com.acme.example.service.MemberRegistration;
import com.acme.example.util.Resources;

public class Deployments {

    public static WebArchive createDeployment() {
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
        File[] requiredLibraries = DependencyResolvers.use(MavenDependencyResolver.class).loadEffectivePom("pom.xml").importAnyDependencies(new ScopeFilter("", "compile")).resolveAsFiles();

        return war.addAsLibraries(requiredLibraries);
    }
}
