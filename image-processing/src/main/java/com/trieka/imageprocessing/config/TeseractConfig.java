package com.trieka.imageprocessing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import net.sourceforge.tess4j.Tesseract;

@Configuration
public class TeseractConfig {

	@Bean(name = "eng")
	@Primary
	Tesseract eng() {
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("src/main/resources/tessdata/");
		
		return tesseract;
	}
	
	@Bean(name = "chi_sim")
	Tesseract chinese() {
		Tesseract tesseract = new Tesseract();
		tesseract.setLanguage("chi_sim");
		tesseract.setDatapath("src/main/resources/tessdata/");
		
		return tesseract;
	}
	
}
