package com.epam.dealzone.service;

import com.epam.dealzone.service.impl.FileStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {
    private final String testDir = "test-dir";
    private FileStorageServiceImpl storageService;

    @BeforeEach
    public void setUp() {
        storageService = new FileStorageServiceImpl();
    }


    @Test
    public void saveFile_successfullySavesFile() throws IOException {
        String fileName = "test-image.jpg";
        byte[] fileContent = "testimage".getBytes();

        MultipartFile mockFile = new MockMultipartFile(
                "testimage",
                fileName,
                "image/jpeg",
                fileContent
        );

        String savedFileName = storageService.saveFile(mockFile, testDir);

        Path savedFilePath = Paths.get("upload", testDir, savedFileName);
        assertTrue(Files.exists(savedFilePath));
        assertArrayEquals(fileContent, Files.readAllBytes(savedFilePath));

        Files.deleteIfExists(savedFilePath);
        Files.deleteIfExists(savedFilePath.getParent());
    }
}
