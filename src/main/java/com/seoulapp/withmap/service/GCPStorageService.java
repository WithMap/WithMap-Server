package com.seoulapp.withmap.service;

import org.springframework.web.multipart.MultipartFile;

public interface GCPStorageService {

	String upload(MultipartFile image);
}
