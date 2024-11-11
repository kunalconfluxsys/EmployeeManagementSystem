package com.example.employee_sytem.service.Impl;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import com.example.employee_sytem.service.DocumentService;
import com.example.employee_sytem.service.ReportService;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Service implementation for generating PDF reports for employee-related data.
 * This service is responsible for generating employee reports in PDF format,
 * including details such as employee ID, name, department, and leave summary.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private ReportService reportService;  // ReportService that handles fetching the data

    /**
     * Generates a PDF report for the given list of employees.
     * The PDF includes a table of employee details such as ID, name, department, and leave summary.
     *
     * @param employees a list of EmployeeDto objects containing employee details to be included in the report.
     * @return a byte array representing the generated PDF report.
     * @throws RuntimeException if there is an error during PDF generation.
     */
    @Override
    public byte[] generateEmployeeReportPdf(List<EmployeeDto> employees) {

        // Create ByteArrayOutputStream for PDF content
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Initialize PdfWriter and PdfDocument
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Create Document instance for adding content
            Document document = new Document(pdfDocument);

            // Add a title with styling
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD); // Correct font loading
            document.add(new Paragraph("Employee Report")
                    .setFont(font)  // Set the bold font
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            // Add a line separator with 1-point width
            document.add(new LineSeparator(new SolidLine(1f)));
            document.add(new Paragraph(" ")); // Add some spacing after the line separator

            // Create a table with 6 columns
            // Define column widths in relative proportions (use float[] to set specific column widths)
            Table table = new Table(new float[]{2, 3, 3, 2, 3, 4});
            table.setWidth(100);  // Set the width of the table to 100% of the available space

            // Add table headers with styling
            table.addCell(new Cell().add(new Paragraph("Employee ID"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph("Employee Name"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph("Email"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph("Department ID"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph("Department Name"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));
            table.addCell(new Cell().add(new Paragraph("Leave Summary"))
                    .setBold().setTextAlignment(TextAlignment.CENTER).setPadding(5));

            // Populate the table with employee data
            for (EmployeeDto employee : employees) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getId())))
                        .setTextAlignment(TextAlignment.CENTER).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(employee.getFirstName() + " " + employee.getLastName()))
                        .setTextAlignment(TextAlignment.LEFT).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(employee.getEmail()))
                        .setTextAlignment(TextAlignment.LEFT).setPadding(5));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getDepartmentId())))
                        .setTextAlignment(TextAlignment.CENTER).setPadding(5));

                // Get department details
                DepartmentDto department = reportService.getDepartmentDetails(employee.getDepartmentId());
                table.addCell(department != null ? new Cell().add(new Paragraph(department.getName()))
                        .setTextAlignment(TextAlignment.LEFT).setPadding(5) :
                        new Cell().add(new Paragraph("No Department"))
                                .setTextAlignment(TextAlignment.LEFT).setPadding(5));

                // Get leave summary
                LeaveSummaryDTO leaveSummary = reportService.getLeaveSummary(employee.getId());
                if (leaveSummary != null) {
                    String leaveSummaryText = "Total Leave: " + leaveSummary.getTotalLeaveDays() +
                            ", Taken: " + leaveSummary.getTakenLeaves() +
                            ", Remaining: " + leaveSummary.getRemainingLeaves();
                    table.addCell(new Cell().add(new Paragraph(leaveSummaryText))
                            .setTextAlignment(TextAlignment.LEFT).setPadding(5));
                } else {
                    table.addCell(new Cell().add(new Paragraph("No Leave Summary"))
                            .setTextAlignment(TextAlignment.LEFT).setPadding(5));
                }
            }

            // Add the table to the document
            document.add(table);

            // Add some space after the table
            document.add(new Paragraph(" "));

            // Optional: Add a footer or any other information

            // Close the document to finish the PDF
            document.close();

        } catch (IOException | java.io.IOException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
            // Optionally, rethrow as a runtime exception if you don't want to propagate IOException
            throw new RuntimeException("Error generating the PDF report", e);
        }

        return baos.toByteArray();

    }
}
