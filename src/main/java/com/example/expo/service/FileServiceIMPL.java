package com.example.expo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.expo.entity.FileData;
import com.example.expo.repo.FileRepository;

@Service
public class FileServiceIMPL implements FileServiceI{

	@Autowired
	private FileRepository fr;
	
	@Override
	public void uploadFile(MultipartFile file) {
		FileData fileData = new FileData();
		fileData.setFileName(file.getOriginalFilename());
		fileData.setFileType(file.getContentType());
		fileData.setFileSize(file.getSize());
		try {
			fileData.setFileData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		fr.save(fileData);
		
	}
}
