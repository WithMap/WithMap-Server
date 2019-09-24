package com.seoulapp.withmap.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Override
	public String upload(MultipartFile image) {
		String imagePath = "upload/" + image.getOriginalFilename();

		File targetFile = new File(imagePath);

		try {
			InputStream fileStream = image.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);
			e.printStackTrace();
		}

		return imagePath;
	}

}
