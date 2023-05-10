package com.trieka.imageprocessing.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

	public String uploadImage(MultipartFile imageFile);

	public String extraxtText(MultipartFile imageFile, String lang);
	
}
