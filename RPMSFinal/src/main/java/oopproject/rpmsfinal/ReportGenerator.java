package oopproject.rpmsfinal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static ReportGenerator instance;

    public ReportGenerator() {}

    public static synchronized ReportGenerator getInstance() {
        if (instance == null) {
            instance = new ReportGenerator();
        }
        return instance;
    }


    public void exportVitalsReportPdf(String patientId, List<VitalRecord> records, File outPdf) throws Exception {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("No records to export");
        }

        // recompute summary statistics as before
        DoubleSummaryStatistics tempStats = records.stream()
                .mapToDouble(VitalRecord::getTemperature).summaryStatistics();
        DoubleSummaryStatistics hrStats = records.stream()
                .mapToDouble(VitalRecord::getHeartRate).summaryStatistics();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.COURIER_BOLD, 16);
                cs.newLineAtOffset(50, 750);
                cs.showText("Vitals Report for Patient " + patientId);

                // move down and set up for lines
                cs.newLineAtOffset(0, -20);
                cs.setFont(PDType1Font.COURIER, 10);
                cs.setLeading(12f);

                // Header row
                cs.showText("Timestamp           | Temp(°C) | HR(bpm) | BP(sys/dia) | Resp(br/min) | O2(%) | Notes            | Status");
                cs.newLine();
                cs.showText("-----------------------------------------------------------------------------------------------");
                cs.newLine();

                // Write each record with all attributes
                for (VitalRecord rec : records) {
                    String ts   = rec.getTimestamp().format(DATE_FORMAT);
                    String temp = String.format("%.1f", rec.getTemperature());
                    String hr   = Integer.toString(rec.getHeartRate());
                    String bp   = rec.getSystolicBP() + "/" + rec.getDiastolicBP();
                    String resp = Integer.toString(rec.getRespirationRate());
                    String o2   = String.format("%.1f", rec.getOxygenSaturation());
                    String notes = rec.getNotes().isBlank() ? "-" : rec.getNotes();
                    String status = rec.isCritical() ? "CRITICAL" : "Normal";

                    String line = String.format(
                            "%-19s | %7s | %7s | %11s | %12s | %5s | %-15s | %s",
                            ts, temp, hr, bp, resp, o2, notes, status
                    );
                    cs.showText(line);
                    cs.newLine();
                }

                // blank line then summary
                cs.newLine();
                cs.setFont(PDType1Font.COURIER_BOLD, 12);
                cs.showText("Summary Statistics:");
                cs.newLine();
                cs.setFont(PDType1Font.COURIER, 10);
                cs.showText(String.format("Temperature -> Avg: %.1f  Min: %.1f  Max: %.1f",
                        tempStats.getAverage(), tempStats.getMin(), tempStats.getMax()));
                cs.newLine();
                cs.showText(String.format("Heart Rate -> Avg: %.0f  Min: %.0f  Max: %.0f",
                        hrStats.getAverage(), hrStats.getMin(), hrStats.getMax()));

                cs.endText();
            }

            doc.save(outPdf);
        }
    }

}
