package core.basesyntax;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest {
    private static final String OUTPUT_PATH = "src/main/resources/output/reportFile.csv";
    private static final ProductDao<Product, Integer> DAO = new ProductDaoImpl();

    @AfterEach
    void tearDown() {
        File outputFile = new File(OUTPUT_PATH);
        outputFile.delete();
        DAO.clear();
    }

    @DisplayName("Test full application")
    @Test
    void run_testApplication() {
        Application.run();
        List<String> expected = List.of("fruit,quantity",
                "banana,152",
                "apple,90");
        List<String> actual;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_PATH))) {
            actual = bufferedReader.lines().collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file: " + OUTPUT_PATH, e);
        }
        assertEquals(expected, actual);
    }
}
