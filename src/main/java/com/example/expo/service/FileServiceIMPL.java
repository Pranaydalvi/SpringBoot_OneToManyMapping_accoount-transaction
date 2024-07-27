package com.example.expo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.expo.entity.FileData;
import com.example.expo.repo.FileRepository;

@Service
public class FileServiceIMPL implements FileServiceI {

	@Autowired
	private FileRepository fr;

	@Override
	public void uploadFile(MultipartFile file) {
		FileData fileData = new FileData();
		String fileUploadDir1 = "H:\\df\\filedemo\\";
		String fileUploadDir2 = "H:\\df\\fileHandling\\";
		try {
			fileUploadOnmultiServerPath(file.getBytes(), file.getOriginalFilename(), fileUploadDir1);
			fileUploadOnmultiServerPath(file.getBytes(), file.getOriginalFilename(), fileUploadDir2);
//		    fileUploadOnServerPath(file.getBytes(),file.getOriginalFilename());
			fileData.setFileName(file.getOriginalFilename());
			fileData.setFileType(file.getContentType());
			fileData.setFileSize(file.getSize());

			fileData.setFileData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		fr.save(fileData);

	}

	private static String fileUploadOnServerPath(byte[] filedata, String filename) {

		int i = filename.lastIndexOf(".");
		String ext = filename.substring(i);
//		System.out.println(ext);
		Path path = Paths.get("H:\\df\\fileHandling\\demo" + ext);
//		Path path = Paths.get("H:"+File.separator+"df"+File.separator+"fileHandling"+File.separator+"demo"+ext);
		try {
			Files.write(path, filedata);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "File Writing done on server";
	}

	private static String fileUploadOnmultiServerPath(byte[] filedata, String filename, String directory) {

		int i = filename.lastIndexOf(".");
		String ext = filename.substring(i);
		System.out.println(ext);
		Path path = Paths.get(directory + "demo" + ext);
		try {
			Files.write(path, filedata);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "File Writing done on server";
	}
}

