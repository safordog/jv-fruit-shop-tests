package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.writer.WriterService;
import core.basesyntax.service.writer.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String ILLEGAL_FILE_PATH = "###/###.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/testReport.csv";
    private static Path PATH_TO_EXPECTED_REPORT_FILE;
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,20" + System.lineSeparator();
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
        PATH_TO_EXPECTED_REPORT_FILE =
                Paths.get("src/test/resources/expectedReportTest.csv");
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_illegalPathFile_RuntimeException() {
        writerService.writeReportToFile(REPORT, ILLEGAL_FILE_PATH);
    }

    @Test
    public void writeReportToFile_validInputData_returnsTrue() {
        writerService.writeReportToFile(REPORT, VALID_FILE_PATH);
        List<String> expected;
        try {
            expected = Files.readAllLines(PATH_TO_EXPECTED_REPORT_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + PATH_TO_EXPECTED_REPORT_FILE, e);
        }
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(VALID_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + VALID_FILE_PATH, e);
        }
        assertEquals(expected, actual);
    }
}