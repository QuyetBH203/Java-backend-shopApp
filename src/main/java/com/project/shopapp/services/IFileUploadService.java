package com.project.shopapp.services;

import com.project.shopapp.responses.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileUploadService {
    List<FileUploadResponse> uploadFiles(List<MultipartFile> multipartFile);
    FileUploadResponse uploadSingleFile(MultipartFile multipartFile);
    List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> multipartFile);
}
