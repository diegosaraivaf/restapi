package com.projeto.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class JasperReportUtil {
	
	public ResponseEntity gerarPdf(List list,List<String> parametros) {
		try {
			//dynamic parameters required for report
			Map<String, Object> empParams = new HashMap<String, Object>();
			empParams.put("CompanyName", "TechGeekNext");
			empParams.put("employeeData", new JRBeanCollectionDataSource(list));
	
			JasperPrint empReport =
					JasperFillManager.fillReport
				   (
							JasperCompileManager.compileReport(
							ResourceUtils.getFile("classpath:relatorios/nfse.jrxml")
									.getAbsolutePath()) // path of the jasper report
							, empParams // dynamic parameters
							, list == null? new JREmptyDataSource() : new JRBeanCollectionDataSource(list)
					);
	
			HttpHeaders headers = new HttpHeaders();
			//set the PDF format
			headers.setContentType(MediaType.APPLICATION_PDF);
	//		headers.setContentDispositionFormData("filename", "employees-details.pdf"); //faz apenas o download do arquivo
			headers.add("Content-Disposition", "inline; filename=employees-details.pdf");//faz o pdf ser exibido na pagina
			
			//create the report in PDF format
			return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
		} 
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
