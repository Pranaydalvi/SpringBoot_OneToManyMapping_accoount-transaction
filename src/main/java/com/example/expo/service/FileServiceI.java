package com.example.expo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceI {

	void uploadFile(MultipartFile file);

}
