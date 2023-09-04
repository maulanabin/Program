package com.doyatama.university.controller;


import com.doyatama.university.payload.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        try {
            // Mendapatkan nama file asli
            String originalFileName = file.getOriginalFilename();
            // Mendapatkan ekstensi file
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            // Mendapatkan timestamp saat ini
            String timestamp = String.valueOf(System.currentTimeMillis());
            // Membuat UUID baru
            String uuid = UUID.randomUUID().toString();
            // Menggabungkan timestamp dan UUID
            String newFileName = "file_" + timestamp + "_" + uuid + fileExtension;
            String hdfsDir = "hdfs://hadoop-primary:9000/uploads/" + newFileName;
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsDir), configuration);
            FSDataOutputStream outputStream = fs.create(new Path(hdfsDir));
            // Menyalin data file langsung ke output stream Hadoop
            IOUtils.copy(file.getInputStream(), outputStream);
            outputStream.close();
            fs.close();
            return ResponseEntity.created(URI.create(""))
                    .body(new ApiResponse(true, "Upload Successfully"));
        } catch (IOException e) {
            // Penanganan kesalahan saat mengunggah file
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Cannot Upload File into Hadoop"));
        }
    }
}