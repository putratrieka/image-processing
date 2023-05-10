package com.trieka.imageprocessing.adapter;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfEncodings;
import com.itextpdf.text.pdf.PdfWriter;
import com.trieka.imageprocessing.service.TesseractService;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;

@Slf4j
@Service
public class TesseractChineseAdapter implements TesseractService {

	private static final String LANG = "chi_sim";

	@Autowired
	@Qualifier("chi_sim")
	Tesseract tesseract;

	@Override
	public Tesseract getTesseract() {
		return tesseract;
	}

	@Override
	public boolean lang(String lang) {
		return LANG.equalsIgnoreCase(lang);
	}

	@Override
	public void print(BufferedImage img) throws Exception {
		String str = tesseract.doOCR(img);
		log.info(str);
		
		String[] strs = str.split("\\s");

		Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(LANG.concat("-") + UUID.randomUUID().toString() + ".pdf"));
			document.open();
			BaseFont bf = BaseFont.createFont("src/main/resources/font/bkai00mp.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font f = new Font(bf, 16);
			f.setColor(BaseColor.BLACK);
			
			Font fBlue = new Font(bf, 16);
			fBlue.setColor(BaseColor.BLUE);

			for (String s : strs) {
				if (s.toUpperCase().contains("O")) {
					document.add(new Paragraph(s, fBlue));
				}else {
					document.add(new Paragraph(s, f));
				}
				
				document.add(new Paragraph("\n"));
			}
			

			document.close();


	}

}
