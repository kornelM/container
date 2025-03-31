package com.document.generator;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Transaction> transactions = List.of(
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("01-01-2025", "02-01-2025", "Jan Kowalski", "PL12345", "Anna Nowak", "PL67890", "Opłata za fakturę 2025/01", "1000,00", "PLN"),
                new Transaction("05-01-2025", "06-01-2025", "Firma ABC", "PL112233", "Firma XYZ", "PL445566", "Wynagrodzenie za usługi", "2500,00", "EUR")
        );

        PdfGenerator generator = new PdfGenerator();
        generator.generate(transactions, "transactions.pdf");
        System.out.println("PDF został wygenerowany.");
    }
}