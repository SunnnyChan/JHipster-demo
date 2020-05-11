package me.sunny.jhpster.demo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("me.sunny.jhpster.demo");

        noClasses()
            .that()
                .resideInAnyPackage("me.sunny.jhpster.demo.service..")
            .or()
                .resideInAnyPackage("me.sunny.jhpster.demo.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..me.sunny.jhpster.demo.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
