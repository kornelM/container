package com.document.generator;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfGeneratorTest {

    @Test
    void testGeneratePdf() throws IOException {
        List<Transaction> transactions = List.of(
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Testowa transakcja", "1000,00", "PLN")
        );

        PdfGenerator generator = new PdfGenerator();
        String fileName = "test_transactions.pdf";
        generator.generate(transactions, fileName);

        File file = new File(fileName);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }
}
