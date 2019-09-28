package com.seoulapp.withmap.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.seoulapp.withmap.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Value("${spring.cloud.gcp.stroage.bucket}")
    private String bucketName;
    
	private static Storage storage = null;
	
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String upload(MultipartFile image) throws IOException {
		
		String url = "https://storage.cloud.google.com/";

		final String fileName = image.getOriginalFilename();
		final String ext = fileName.substring(fileName.lastIndexOf('.'));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		final String gcpFileName = dtString + ext;

		BlobInfo blobInfo = storage
				.create(BlobInfo.newBuilder(bucketName, gcpFileName)
				// Modify access list to allow all users with link to read file
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
				image.getInputStream());
		// return the public download link
		
		url += bucketName + "/" + gcpFileName;
		return url;
	}

}
