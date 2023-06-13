package com.projeto.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;


@Controller
public class PdfFlyingSaucerController {

	@GetMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public void generatePdf(HttpServletResponse response) throws Exception {
        // Load HTML template from file
        ClassPathResource templateResource = new ClassPathResource("relatorios/nfses.html");
        InputStream templateInputStream = templateResource.getInputStream();
        byte[] templateBytes = templateInputStream.readAllBytes();
        String htmlTemplate = new String(templateBytes, StandardCharsets.UTF_8);
 
       //Load the data -  Replace the {table_data} placeholder with the actual table rows HTML
        String tableRowsHtml = "<tr><td>Row 1, Column 1</td><td>Row 1, Column 2</td><td>Row 1, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
                             + "<tr><td>Row 2, Column 1</td><td>Row 2, Column 2</td><td>Row 2, Column 3</td></tr>"
        		;
        String htmlContent = htmlTemplate.replace("{table_data}", tableRowsHtml);

        //create pdf
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=generated.pdf");
        
        OutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(outputStream.toByteArray());
        responseOutputStream.close();
    }
	
//    @GetMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    @ResponseBody
//    public void generatePdf(HttpServletResponse response) throws Exception {
//        String htmlContent = "<html><body><h1>Hello, World!</h1></body></html>";
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(htmlContent);
//        renderer.layout();
//        renderer.createPDF(outputStream);
//        renderer.finishPDF();
//
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=generated.pdf");
//
//        OutputStream responseOutputStream = response.getOutputStream();
//        responseOutputStream.write(outputStream.toByteArray());
//        responseOutputStream.close();
//    }
    
    
    


}
