package unq.cryptoexchange.ArchTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {



    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(location -> !location.contains("configuration"))// Exclude tests classes
            .importPackages("unq.cryptoexchange");

    @Test
    void layeredArchitectureShouldBeRespected() {
        Architectures.LayeredArchitecture architecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controllers..")
                .layer("Service").definedBy("..services..")
                .layer("Repository").definedBy("..repository..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

        architecture.check(classes);
    }
}
