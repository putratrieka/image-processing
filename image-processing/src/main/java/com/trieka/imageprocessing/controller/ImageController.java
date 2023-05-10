package com.trieka.imageprocessing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trieka.imageprocessing.service.IImageService;

@RestController
@RequestMapping("image-processing")
public class ImageController {
	
	@Autowired
	IImageService imageService;
	
	@PostMapping("upload")
	public String upload(@RequestPart(name = "image", required = true) MultipartFile imageFile) {
		return imageService.uploadImage(imageFile);
	}
	
	@PostMapping("proceed")
	public String proceed(@RequestPart(name = "image", required = true) MultipartFile imageFile, @RequestPart(name = "lang") String lang) {
		return imageService.extraxtText(imageFile, lang);

	}

}
