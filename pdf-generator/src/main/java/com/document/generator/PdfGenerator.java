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

    private final float marginLeft = 50;
    private final float marginRight = 30;
    private final float bottomMargin = 50;

    public void generate(List<Transaction> transactions, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDRectangle mediaBox = PDRectangle.A4;

            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Regular.ttf");
            InputStream fontBoldStream = getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Bold.ttf");

            PDType0Font fontRegular = PDType0Font.load(document, fontStream);
            PDType0Font fontBold = PDType0Font.load(document, fontBoldStream);

            fontStream.close();
            fontBoldStream.close();

            List<PDPage> pages = new ArrayList<>();
            PDPage page = new PDPage(mediaBox);
            document.addPage(page);
            pages.add(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float currentY = mediaBox.getHeight() - 50;

            // Logo
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("logo.jpg");
            if (logoStream != null) {
                PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
                contentStream.drawImage(logo, mediaBox.getWidth() - marginRight - 100, mediaBox.getHeight() - 70, 100, 50);
                logoStream.close();
            }

            // Nagłówek
            contentStream.beginText();
            contentStream.setFont(fontBold, 16);
            contentStream.newLineAtOffset(marginLeft, currentY);
            contentStream.showText("Historia transakcji");
            contentStream.endText();

            currentY -= 30;

            // Opis z datami
            contentStream.beginText();
            contentStream.setFont(fontRegular, 11);
            contentStream.newLineAtOffset(marginLeft, currentY);
            contentStream.showText("Elektroniczne zestawienie operacji zaksięgowanych za okres od ");
            contentStream.setFont(fontBold, 11);
            contentStream.showText("01-01-2025");
            contentStream.setFont(fontRegular, 11);
            contentStream.showText(" do ");
            contentStream.setFont(fontBold, 11);
            contentStream.showText("31-01-2025.");
            contentStream.endText();

            currentY -= 30;

            currentY = addLine(fontRegular, contentStream, marginLeft, currentY, "Kryteria transakcji:");

            contentStream.beginText();
            contentStream.newLineAtOffset(marginLeft, currentY);
            contentStream.setFont(fontRegular, 11);
            contentStream.showText("Okres: ");
            float okresWidth = fontRegular.getStringWidth("Okres: ") / 1000 * 11;
            contentStream.newLineAtOffset(okresWidth, 0);
            contentStream.setFont(fontBold, 11);
            contentStream.showText("01-01-2025 - 31-01-2025");
            contentStream.endText();
            currentY -= 15;

            currentY = addLine(fontRegular, contentStream, marginLeft, currentY, "Typ transakcji: Uznania/Obciążenia");
            currentY = addLine(fontRegular, contentStream, marginLeft, currentY, "Waluty: PLN, EUR");
            currentY = addLine(fontRegular, contentStream, marginLeft, currentY, "Produkty: 123, 123, 123, 123");

            currentY -= 15;
            currentY = addLine(fontRegular, contentStream, marginLeft, currentY, "Dokument wygenerowany automatycznie w dniu 28-03-2025 o godz. 13:45:25.");

            currentY -= 15;
            currentY = addLine(fontBold, contentStream, marginLeft, currentY, "DANE TRANSAKCJI");
            contentStream.moveTo(marginLeft, currentY - 3);
            contentStream.lineTo(mediaBox.getWidth() - marginRight, currentY - 3);
            contentStream.stroke();
            currentY -= 20;

            float[] colWidths = {70, 70, 220, 70, 80};
            String[] headers = {"Data transakcji", "Data księgowania", "Szczegóły transakcji", "Kwota", "Kwota w walucie rachunku"};

            currentY = addTableRow(contentStream, headers, colWidths, marginLeft, currentY, true, fontRegular, fontBold);

            for (Transaction tx : transactions) {
                String[] txData = {
                        tx.getTransactionDate(),
                        tx.getPostingDate(),
                        tx.getDetails(),
                        tx.getAmount(),
                        tx.getCurrency()
                };
                float rowHeight = calculateRowHeight(txData, colWidths, fontRegular, 9, 2);
                if (currentY - rowHeight < bottomMargin) {
                    addFooter(contentStream, fontRegular, mediaBox, pages.size());
                    contentStream.close();
                    page = new PDPage(mediaBox);
                    document.addPage(page);
                    pages.add(page);
                    contentStream = new PDPageContentStream(document, page);
                    currentY = mediaBox.getHeight() - 50;
                    currentY = addLine(fontBold, contentStream, marginLeft, currentY, "DANE TRANSAKCJI");
                    contentStream.moveTo(marginLeft, currentY - 3);
                    contentStream.lineTo(mediaBox.getWidth() - marginRight, currentY - 3);
                    contentStream.stroke();
                    currentY -= 20;
                    currentY = addTableRow(contentStream, headers, colWidths, marginLeft, currentY, true, fontRegular, fontBold);
                }
                currentY = addTableRow(contentStream, txData, colWidths, marginLeft, currentY, false, fontRegular, fontBold);
            }

            addFooter(contentStream, fontRegular, mediaBox, pages.size());
            contentStream.close();
            document.save(outputPath);
        }
    }

    private void addFooter(PDPageContentStream cs, PDType0Font font, PDRectangle box, int pageNum) throws IOException {
        cs.beginText();
        cs.setFont(font, 7);
        cs.setNonStrokingColor(0.9f);
        cs.newLineAtOffset(marginLeft, bottomMargin - 20);
        String text = "Niniejszy dokument został wygenerowany elektronicznie. Strona " + pageNum;

        List<String> strings = wrapText(text, font, 9, 220);
        strings.forEach(s -> {
            try {
                cs.showText(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        cs.endText();
    }

    private float addLine(PDType0Font font, PDPageContentStream contentStream, float x, float y, String text) throws IOException {
        return addLine(font, contentStream, x, y, text, false);
    }

    private float addLine(PDType0Font font, PDPageContentStream contentStream, float x, float y, String text, boolean bold) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, 11);
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

        for (int i = 0; i < data.length; i++) {
            List<String> wrapped = wrapText(data[i], font, fontSize, widths[i] - 2 * cellMargin);
            wrappedData.add(wrapped);
            if (wrapped.size() > maxLinesInRow) {
                maxLinesInRow = wrapped.size();
            }
        }

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

        return y - (lineHeight * maxLinesInRow) - 5;
    }

    private float calculateRowHeight(String[] txData, float[] colWidths, PDType0Font font, float fontSize, float cellMargin) throws IOException {
        float lineHeight = 14;
        int maxLinesInRow = 1;

        for (int i = 0; i < txData.length; i++) {
            List<String> wrapped = wrapText(txData[i], font, fontSize, colWidths[i] - 2 * cellMargin);
            if (wrapped.size() > maxLinesInRow) {
                maxLinesInRow = wrapped.size();
            }
        }

        return (lineHeight * maxLinesInRow) + 5;
    }

    private List<String> wrapText(String text, PDType0Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String originalLine : text.split("\n")) {
            String[] words = originalLine.split(" ");
            StringBuilder lineBuilder = new StringBuilder();
            for (String word : words) {
                String tempLine = lineBuilder + (lineBuilder.length() == 0 ? "" : " ") + word;
                float size = font.getStringWidth(tempLine) / 1000 * fontSize;
                if (size > maxWidth) {
                    if (lineBuilder.length() == 0) {
                        lines.add(word);
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


//    private void addFooter(PDPageContentStream cs, PDType0Font font, PDRectangle box, int pageNum) throws IOException {
//        cs.beginText();
//        cs.setFont(font, 7);
//        cs.setNonStrokingColor(100);
//        cs.newLineAtOffset(marginLeft, bottomMargin - 20);
//        cs.showText("Niniejszy dokument został wygenerowany elektronicznie i nie wymaga podpisu ani stempla. Dokument sporządzony na podstawie art.7 ustawy Prawo Bankowe (Dz. U. Nr 140 z 1997 roku poz. 939 z późniejszymi zmianami). Strona " + pageNum);
//        cs.endText();
//    }
//
//    // Implementacja addLine, addTableRow, calculateRowHeight oraz wrapText pozostaje bez zmian
//
//
//    private float addLine(PDType0Font fontRegular, PDType0Font fontBold, PDPageContentStream contentStream, float x, float y, String text) throws IOException {
//        return addLine(fontRegular, fontBold, contentStream, x, y, text, false);
//    }
//
//    private float addLine(PDType0Font fontRegular, PDType0Font fontBold, PDPageContentStream contentStream, float x, float y, String text, boolean bold) throws IOException {
//        contentStream.beginText();
//        contentStream.setFont(bold ? fontBold : fontRegular, 11);
//        contentStream.newLineAtOffset(x, y);
//        contentStream.showText(text);
//        contentStream.endText();
//        return y - 15;
//    }
//
//    private float addTableRow(PDPageContentStream cs, String[] data, float[] widths, float startX, float y, boolean header, PDType0Font fontRegular, PDType0Font fontBold) throws IOException {
//        float lineHeight = 14;
//        float cellMargin = 2;
//        float maxY = y;
//        float x = startX;
//        float fontSize = header ? 10 : 9;
//        PDType0Font font = header ? fontBold : fontRegular;
//
//        int maxLinesInRow = 1;
//        List<List<String>> wrappedData = new ArrayList<>();
//
//        // najpierw zawijamy tekst każdej komórki
//        for (int i = 0; i < data.length; i++) {
//            List<String> wrapped = wrapText(data[i], font, fontSize, widths[i] - 2 * cellMargin);
//            wrappedData.add(wrapped);
//            if (wrapped.size() > maxLinesInRow) {
//                maxLinesInRow = wrapped.size();
//            }
//        }
//
//        // teraz rysujemy tekst, odpowiednio przesunięty
//        for (int i = 0; i < data.length; i++) {
//            float textY = y;
//            for (String line : wrappedData.get(i)) {
//                cs.beginText();
//                cs.setFont(font, fontSize);
//                cs.newLineAtOffset(x + cellMargin, textY);
//                cs.showText(line);
//                cs.endText();
//                textY -= lineHeight;
//            }
//            x += widths[i];
//            if (textY < maxY) maxY = textY;
//        }
//
//        // Obliczamy wysokość całego wiersza na podstawie największej liczby linii
//        return y - (lineHeight * maxLinesInRow) - 5;
//    }
//
//    private List<String> wrapText(String text, PDType0Font font, float fontSize, float maxWidth) throws IOException {
//        List<String> lines = new ArrayList<>();
//        for (String originalLine : text.split("\\n")) {
//            String[] words = originalLine.split(" ");
//            StringBuilder lineBuilder = new StringBuilder();
//            for (String word : words) {
//                String tempLine = lineBuilder + (lineBuilder.length() == 0 ? "" : " ") + word;
//                float size = font.getStringWidth(tempLine) / 1000 * fontSize;
//                if (size > maxWidth) {
//                    if (lineBuilder.length() == 0) {
//                        lines.add(word); // pojedyncze słowo większe niż szerokość – dodajemy jako osobną linię
//                    } else {
//                        lines.add(lineBuilder.toString());
//                        lineBuilder = new StringBuilder(word);
//                    }
//                } else {
//                    lineBuilder.append(lineBuilder.length() == 0 ? "" : " ").append(word);
//                }
//            }
//            if (lineBuilder.length() > 0) {
//                lines.add(lineBuilder.toString());
//            }
//        }
//        return lines;
//    }
}
