package com.sm.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    private final Map<String, String> nameMapping = new HashMap<>();

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(originalName.substring(originalName.lastIndexOf(".")));
        String filePath = path + File.separator + randomFileName;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs(); // Use mkdirs() to create any necessary parent directories
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        
        // Store the mapping of original name to random name
        nameMapping.put(originalName, randomFileName);
       
        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // Retrieve the random file name from the mapping using the original name
        String randomFileName = nameMapping.get(fileName);
        if (randomFileName == null) {
          throw new FileNotFoundException("File not found: " + fileName);
        }

        String fullPath = path + File.separator + randomFileName;
        return new FileInputStream(fullPath);
    }
}
