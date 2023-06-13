package com.projeto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.projeto.entity.Nfse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PdfJasperController {
	
	@GetMapping("/generate-pdf")
    public void generatePdf(HttpServletResponse response) throws IOException, JRException {
        // Load the JasperReport template
        InputStream templateStream = getClass().getResourceAsStream("./resources/relatorio/template.jasper");
        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

        // Create a data source (example with a list of objects)
        List<Nfse> dataList = getDataFromDatabase(); // Retrieve your data from the database
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

        // Set report parameters (if needed)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Your Report Title");

        // Generate the PDF report
        byte[] pdfBytes = JasperRunManager.runReportToPdf(jasperReport, parameters, dataSource);

        // Set the response headers
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"your-report.pdf\"");

        // Write the PDF bytes to the response output stream
        response.getOutputStream().write(pdfBytes);
    }

    // Dummy method to simulate data retrieval from a database
    private List<Nfse> getDataFromDatabase() {
    	List<Nfse> nfses = new ArrayList<Nfse>();
    	
    	Nfse n = new Nfse();
    	n.setId(1L);
    	n.setLocalPrestacao("teste");
    	n.setValorServico(new BigDecimal("100"));
    	nfses.add(n);
    	
        return nfses;
    }

}
