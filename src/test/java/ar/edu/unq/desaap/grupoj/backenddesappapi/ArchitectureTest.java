package ar.edu.unq.desaap.grupoj.backenddesappapi;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    @AnalyzeClasses(packages = "ar.edu.unq.desaap.grupoj.backenddesappapi")
    public static class MyArchitectureTest {

        @ArchTest
        public static final ArchRule servicesOnlyAccessByControllersRule = classes()
                .that().resideInAPackage("..services..")
                .should().onlyBeAccessed().byAnyPackage("..webServices..", "..services..", "..util..");

    }
}