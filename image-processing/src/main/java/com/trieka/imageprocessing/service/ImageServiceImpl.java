package com.trieka.imageprocessing.service;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.trieka.imageprocessing.adapter.GDriveAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements IImageService{
	
	private List<String> ACCEPTED_FILE_EXTENSION = Arrays.asList("JPG","PNG","JPEG","GIF");
	
	@Autowired
	private List<TesseractService> tesseracts;

	@Override
	public String uploadImage(MultipartFile imageFile) {
		String fileExtension = StringUtils.substringAfterLast(imageFile.getOriginalFilename(), ".");
		if (!ACCEPTED_FILE_EXTENSION.stream().anyMatch( s -> s.equalsIgnoreCase(fileExtension) )){
			return "File type doesn't match";
		}
		System.out.println("UPLOAD FILEc "+ imageFile);
		return new GDriveAdapter(imageFile).upload();
	}

	@Override
	public String extraxtText(MultipartFile imageFile, String lang) {
		
		try {
			BufferedImage ipimage = ImageIO.read(imageFile.getInputStream());
			
			TesseractService tesseract = tesseracts.stream().filter(t -> t.lang(lang)).findFirst().orElse(null);
			if (tesseract != null) {
				tesseract.print(ipimage);
			}else {
				return "Language Not Found";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "NOT OK";
		}

		return "OK";
	}

}
