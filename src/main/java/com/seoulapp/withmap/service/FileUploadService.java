package com.seoulapp.withmap.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	String upload(MultipartFile image);
}
