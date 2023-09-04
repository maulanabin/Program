package com.doyatama.university.controller;

import com.doyatama.university.payload.*;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload-file")
public class UploadFileController {
    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        if (!file.isEmpty()) {
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
                String newFileName = "file_" + timestamp + "_" + uuid;
//                String filePath = "/home/osboxes/uploads" + "/" + newFileName + fileExtension;
                String filePath = "F:/Skripsi" + "/" + newFileName + fileExtension;
                File newFile = new File(filePath);

                // Menyimpan file ke lokasi yang ditentukan di server
                file.transferTo(newFile);

                return ResponseEntity.created(URI.create(""))
                        .body(new ApiResponse(true, "Upload Successfully"));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Cannot Upload File into VM"));
            }
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "File Kosong"));
        }
    }

}
