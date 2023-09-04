package com.doyatama.university.controller;

import com.doyatama.university.payload.*;
import com.jcraft.jsch.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload-file")
public class UploadFileController {
    private final String FOLDER_PATH="C:/Users/Doyatama/Videos/";
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // upload file
        try {
            // Mendapatkan nama file asli
            String originalFileName = file.getOriginalFilename();

            // Mendapatkan ekstensi file
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // Mendapatkan timestamp saat ini
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Membuat UUID baru
            JSch jSch = new JSch();
            Session session = jSch.getSession("osboxes", "192.168.50.100", 22); // Ganti dengan informasi VM Anda
            session.setPassword("osboxes.org"); // Ganti dengan password VM Anda
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;

            // Generate a random name for the image
            String randomName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFilename);
            String filePath = FOLDER_PATH+randomName;
            file.transferTo(new File(filePath));

            // Upload file dari Spring Boot ke VM
            String remoteFilePath = "/home/osboxes/storage-fs/"+randomName;
            channelSftp.put(filePath, remoteFilePath);
            channelSftp.exit();
            channel.disconnect();
            session.disconnect();

            return ResponseEntity.created(URI.create(""))
                    .body(new ApiResponse(true, "Upload Successfully"));
        } catch (IOException e) {
            // Penanganan kesalahan saat menyimpan file
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Cannot Upload File into Hadoop"));
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

}
