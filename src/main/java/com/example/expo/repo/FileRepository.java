package com.example.expo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expo.entity.FileData;

public interface FileRepository extends JpaRepository<FileData, Integer>{

}
