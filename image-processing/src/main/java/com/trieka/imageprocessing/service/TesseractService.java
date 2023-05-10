package com.trieka.imageprocessing.service;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.Tesseract;

public interface TesseractService {
	
	public boolean lang(String lang);
	
	public Tesseract getTesseract();
	
	public void print(BufferedImage img) throws Exception;

}
