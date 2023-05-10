package com.trieka.imageprocessing.adapter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.trieka.imageprocessing.service.Uploader;
import com.trieka.imageprocessing.util.GDriveHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GDriveAdapter implements Uploader {

	private String filename;
	private MultipartFile multipartFile;
	private static final String FOLDER_ID = "1OQx41RR-H-x8dhJGHD7r7wZ_m8a3Jq3b";

	public GDriveAdapter(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
		this.filename = multipartFile.getOriginalFilename();

	}

	@Override
	public String upload() {
		try {

			final NetHttpTransport HTTP_TRANSFORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive drive = new Drive.Builder(HTTP_TRANSFORT, GDriveHelper.JSON_FACTORY,
					GDriveHelper.getCredentials(HTTP_TRANSFORT)).setApplicationName(GDriveHelper.APPLICATION_NAME)
					.build();

			File fileMetadata = new File();
			List<String> parentids = new ArrayList<>();
			parentids.add(FOLDER_ID);
			fileMetadata.setName(filename);
			fileMetadata.setParents(parentids);

			java.io.File filePath = new java.io.File("/Users/triputra/Documents/upload/".concat(filename));
			multipartFile.transferTo(filePath);
			FileContent mediaContent = new FileContent(multipartFile.getContentType(), filePath);
			File file = drive.files().create(fileMetadata, mediaContent).setFields("id").execute();
			allCanSeePermissionSetter(file.getId(), drive);
			return "OK";
		} catch (Exception e) {
			log.error("Failed upload file", e);
			return "Failed Upload File To GDrive";
		}
	}

	private void allCanSeePermissionSetter(String id, Drive drive) throws Exception {
		Permission permission = new Permission().setAllowFileDiscovery(false).setRole("reader").setType("anyone");
		drive.permissions().create(id, permission).setFields("id").execute();
	}

}
