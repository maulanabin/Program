package com.doyatama.university.controller;


import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload-file")
public class UploadFileController {

    @PostMapping
    public ResponseEntity<Void> uploadFileToHadoop(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Generate a unique file name to avoid conflicts
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = "file_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + fileExtension;
            String hdfsDir = "hdfs://hadoop-primary:9000/uploads/" + newFileName;

            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", "hdfs://hadoop-primary:9000"); // Set the default HDFS URL

            FileSystem fs = FileSystem.get(configuration);

            try (FSDataOutputStream out = fs.create(new Path(hdfsDir));
                 InputStream inputStream = file.getInputStream()) {
                byte[] buffer = new byte[32*1024]; // Adjust buffer size as needed 16 default
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.hflush();
            }

            fs.close();

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}