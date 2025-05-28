package com.epam.dealzone.service.impl;

import com.epam.dealzone.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final String uploadFile = "upload/";
    @Override
    public String saveFile(MultipartFile file, String dir) {
        try {
            String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
            Path targerDir = Paths.get(uploadFile,dir);

            Files.createDirectories(targerDir);

            Path targetFile = targerDir.resolve(fileName);
            Files.copy(file.getInputStream(),targetFile);

            return fileName;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
