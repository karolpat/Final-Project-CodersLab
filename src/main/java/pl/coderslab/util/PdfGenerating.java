package pl.coderslab.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.thymeleaf.dom.Document;

import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


public class PdfGenerating {
	
	
	private static final Logger log = LoggerFactory.getLogger(PdfGenerating.class);

	
//	public static void generatePDFFromHTML(String filename, String html) throws ParserConfigurationException, IOException, DocumentException {
//		Document document = new Document();
//		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(html+".pdf"));
//		document.open();
//		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
//		document.close();
//		log.info("git");
//	}
	
	public void testPdf(String filename) throws FileNotFoundException {
		File file = new File(filename+".pdf");
		file.getParentFile().mkdirs();
		
		PdfWriter writer = new PdfWriter(filename+".pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		document.add(new Paragraph(filename));
		document.close();
		
	}

}
