package com.seoulapp.withmap.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	String upload(MultipartFile image) throws IOException;
}
