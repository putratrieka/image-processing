package com.trieka.imageprocessing.adapter;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.trieka.imageprocessing.service.TesseractService;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Slf4j
@Service
public class TesseractEngAdapter implements TesseractService {

	private static final String LANG = "eng";

	@Autowired
	@Qualifier("eng")
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
		String ss = tesseract.doOCR(img);
		log.info(ss);

		ss = ss.replaceAll("[^a-zA-Z ]", "");
		String[] strings = ss.split("\\s");

		Document document = new Document();
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream(LANG.concat("-") + UUID.randomUUID().toString() + ".pdf"));
			document.open();

			Font black = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Font blue = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLUE);

			for (String str : strings) {
				str = str.toUpperCase();
				if (str.contains("O")) {
					document.add(new Chunk(str, blue));
					document.add(new Paragraph("\n"));
				} else {
					document.add(new Chunk(str, black));
					document.add(new Paragraph("\n"));
				}
			}

			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

	}

}
