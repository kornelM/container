package com.document.generator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {

    public void generate(List<Transaction> transactions, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {


            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDRectangle mediaBox = page.getMediaBox();
            float marginLeft = 50;
            float marginRight = 30;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Logo
                InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logo.jpg");
                if (logoStream != null) {
                    PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
                    contentStream.drawImage(logo, mediaBox.getWidth() - marginRight - 100, mediaBox.getHeight() - 70, 100, 50);
                }

                InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Regular.ttf");
                if (fontStream == null) {
                    throw new IOException("Nie znaleziono pliku czcionki.");
                }
                PDType0Font fontRegular = PDType0Font.load(document, fontStream);
                fontStream.close();

                InputStream fontBoldStream = getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Bold.ttf");
                if (fontBoldStream == null) {
                    throw new IOException("Nie znaleziono pliku czcionki bold.");
                }
                PDType0Font fontBold = PDType0Font.load(document, fontBoldStream);
                fontBoldStream.close();


                // Nagłówek
                contentStream.beginText();
                contentStream.setFont(fontBold, 16);
                contentStream.newLineAtOffset(marginLeft, mediaBox.getHeight() - 50);
                contentStream.showText("Historia transakcji");
                contentStream.endText();

                // Opis z datami
                contentStream.beginText();
                contentStream.setFont(fontRegular, 11);
                contentStream.newLineAtOffset(marginLeft, mediaBox.getHeight() - 80);
                contentStream.showText("Elektroniczne zestawienie operacji zaksięgowanych za okres od ");
                contentStream.setFont(fontBold, 11);
                contentStream.showText("01-01-2025");
                contentStream.setFont(fontRegular, 11);
                contentStream.showText(" do ");
                contentStream.setFont(fontBold, 11);
                contentStream.showText("31-01-2025.");
                contentStream.endText();

                float currentY = mediaBox.getHeight() - 110;
                contentStream.setFont(fontRegular, 11);

                // Kryteria
                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Kryteria transakcji:");
                // Wstawianie tekstu "Okres:" normalnie, daty pogrubione
                contentStream.beginText();
// Ustawienie początkowej pozycji tekstu
                contentStream.newLineAtOffset(marginLeft, currentY);
// Czcionka regular dla "Okres:"
                contentStream.setFont(fontRegular, 11);
                contentStream.showText("Okres: ");
// Przesunięcie kursora w prawo po tekście "Okres: "
                float okresWidth = fontRegular.getStringWidth("Okres: ") / 1000 * 11;
                contentStream.newLineAtOffset(okresWidth, 0);
// Czcionka bold dla dat
                contentStream.setFont(fontBold, 11);
                contentStream.showText("01-01-2025 - 31-01-2025");
                contentStream.endText();
                currentY -= 15;

//                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Okres: 01-01-2025 - 31-01-2025", true);
                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Typ transakcji: Uznania/Obciążenia");
                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Waluty: PLN, EUR");
                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Produkty: 123, 123, 123, 123");

                currentY -= 15;
                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "Dokument wygenerowany automatycznie w dniu 28-03-2025 o godz. 13:45:25.");
                currentY -= 15;

                currentY = addLine(fontRegular, fontBold, contentStream, marginLeft, currentY, "DANE TRANSAKCJI", true);
                contentStream.moveTo(marginLeft, currentY - 3);
                contentStream.lineTo(mediaBox.getWidth() - marginRight, currentY - 3);
                contentStream.stroke();
                currentY -= 20;

                // Tabela z transakcjami
                float[] colWidths = {70, 70, 220, 70, 80};
                String[] headers = {"Data transakcji", "Data księgowania", "Szczegóły transakcji", "Kwota", "Kwota w walucie rachunku"};

//                currentY = addTableRow(fontRegular, fontBold, contentStream, headers, colWidths, marginLeft, currentY, true);
                currentY = addTableRow(contentStream, headers, colWidths, marginLeft, currentY, true, fontRegular, fontBold);

                for (Transaction tx : transactions) {
                    String[] txData = {
                            tx.getTransactionDate(),
                            tx.getPostingDate(),
                            tx.getDetails(),
                            tx.getAmount(),
                            tx.getCurrency()
                    };
                    currentY = addTableRow(contentStream, txData, colWidths, marginLeft, currentY, false, fontRegular, fontBold);
//                    currentY = addTableRow(fontRegular, fontBold,contentStream, txData, colWidths, marginLeft, currentY, false);
                }
                contentStream.close();
            }

            document.save(outputPath);
        }
    }

    private float addLine(PDType0Font fontRegular, PDType0Font fontBold, PDPageContentStream contentStream, float x, float y, String text) throws IOException {
        return addLine(fontRegular, fontBold, contentStream, x, y, text, false);
    }

    private float addLine(PDType0Font fontRegular, PDType0Font fontBold, PDPageContentStream contentStream, float x, float y, String text, boolean bold) throws IOException {
        contentStream.beginText();
        contentStream.setFont(bold ? fontBold : fontRegular, 11);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
        return y - 15;
    }

    private float addTableRow(PDPageContentStream cs, String[] data, float[] widths, float startX, float y, boolean header, PDType0Font fontRegular, PDType0Font fontBold) throws IOException {
        float lineHeight = 14;
        float cellMargin = 2;
        float maxY = y;
        float x = startX;
        float fontSize = header ? 10 : 9;
        PDType0Font font = header ? fontBold : fontRegular;

        int maxLinesInRow = 1;
        List<List<String>> wrappedData = new ArrayList<>();

        // najpierw zawijamy tekst każdej komórki
        for (int i = 0; i < data.length; i++) {
            List<String> wrapped = wrapText(data[i], font, fontSize, widths[i] - 2 * cellMargin);
            wrappedData.add(wrapped);
            if (wrapped.size() > maxLinesInRow) {
                maxLinesInRow = wrapped.size();
            }
        }

        // teraz rysujemy tekst, odpowiednio przesunięty
        for (int i = 0; i < data.length; i++) {
            float textY = y;
            for (String line : wrappedData.get(i)) {
                cs.beginText();
                cs.setFont(font, fontSize);
                cs.newLineAtOffset(x + cellMargin, textY);
                cs.showText(line);
                cs.endText();
                textY -= lineHeight;
            }
            x += widths[i];
            if (textY < maxY) maxY = textY;
        }

        // Obliczamy wysokość całego wiersza na podstawie największej liczby linii
        return y - (lineHeight * maxLinesInRow) - 5;
    }

    private float addTableRow(PDType0Font fontRegular, PDType0Font fontBold, PDPageContentStream cs, String[] data, float[] widths, float startX, float y, boolean header) throws IOException {
        float lineHeight = 14;
        float maxY = y;
        float x = startX;
        for (int i = 0; i < data.length; i++) {
            List<String> lines = wrapText(data[i], widths[i], header);
            float textY = y;
            for (String line : lines) {
                cs.beginText();
                cs.setFont(header ? fontBold : fontRegular, 11);
                cs.newLineAtOffset(x, textY);
                cs.showText(line);
                cs.endText();
                textY -= lineHeight;
            }
            x += widths[i];
            if (textY < maxY) maxY = textY;
        }
        return maxY - 5;
    }

    private List<String> wrapText(String text, PDType0Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String originalLine : text.split("\\n")) {
            String[] words = originalLine.split(" ");
            StringBuilder lineBuilder = new StringBuilder();
            for (String word : words) {
                String tempLine = lineBuilder + (lineBuilder.length() == 0 ? "" : " ") + word;
                float size = font.getStringWidth(tempLine) / 1000 * fontSize;
                if (size > maxWidth) {
                    if (lineBuilder.length() == 0) {
                        lines.add(word); // pojedyncze słowo większe niż szerokość – dodajemy jako osobną linię
                    } else {
                        lines.add(lineBuilder.toString());
                        lineBuilder = new StringBuilder(word);
                    }
                } else {
                    lineBuilder.append(lineBuilder.length() == 0 ? "" : " ").append(word);
                }
            }
            if (lineBuilder.length() > 0) {
                lines.add(lineBuilder.toString());
            }
        }
        return lines;
    }

    private List<String> wrapText(String text, float width, boolean header) throws IOException {
        // Implementacja zawijania tekstu (prosty podział, można rozszerzyć)
        List<String> lines = new ArrayList<>();
        for (String line : text.split("\n")) {
            lines.add(line); // uproszczone, można podzielić dalej
        }
        return lines;
    }
}
