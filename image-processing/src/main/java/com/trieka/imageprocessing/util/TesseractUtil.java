package com.trieka.imageprocessing.util;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.sourceforge.tess4j.Tesseract;

public class TesseractUtil {
	
	public static Tesseract getInstance(String qualifierName) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		return BeanFactoryAnnotationUtils.qualifiedBeanOfType(ctx, Tesseract.class, qualifierName);
	}

}
