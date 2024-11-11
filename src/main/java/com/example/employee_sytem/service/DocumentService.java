package com.example.employee_sytem.service;
import com.example.employee_sytem.dto.EmployeeDto;
import java.io.IOException;
import java.util.List;
/**
 * Service interface for handling document generation tasks, such as generating PDF reports.
 * <p>
 * This interface defines the methods to generate reports for employees, which can be in the form of PDF documents.
 * </p>
 */
public interface DocumentService {

    /**
     * Generates a PDF report for a list of employees.
     * <p>
     * This method will create a PDF document that includes details for all the employees
     * in the provided list. The generated document can be downloaded or saved.
     * </p>
     *
     * @param employees the list of employees to include in the report
     * @return a byte array representing the PDF document content
     * @throws IOException if an error occurs during PDF generation or file handling
     */
    byte[] generateEmployeeReportPdf(List<EmployeeDto> employees) throws IOException;
}
