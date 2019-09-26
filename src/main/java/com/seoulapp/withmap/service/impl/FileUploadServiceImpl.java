package com.seoulapp.withmap.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
//	private static Storage storage = null;
//
//	static {
//		storage = StorageOptions.getDefaultInstance().getService();
//	}
//
	@Override
	public String upload(MultipartFile image) {
//
//		final String fileName = image.getName();
//		final String bucketName = "withamp-253307.appspot.com";
//		DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
//		DateTime dt = DateTime.now(DateTimeZone.UTC);
//		String dtString = dt.toString(dtf);
//		final String gcpFileName = fileName + dtString;
//
//		// the inputstream is closed by default, so we don't need to close it here
//		BlobInfo blobInfo = storage
//				.create(BlobInfo.newBuilder(bucketName, gcpFileName)
//				// Modify access list to allow all users with link to read file
//				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
//				image.getInputStream());
//		// return the public download link
//		return blobInfo.getMediaLink();
		return null;
	}

}
