package com.project.shopapp.responses;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileUploadResponse {
    private String filePath;
    private LocalDateTime dateTime;
}
