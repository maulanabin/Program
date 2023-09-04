package com.doyatama.university.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class HadoopConfig {

    @Bean
    public FileSystem fileSystem() throws IOException {
        Configuration configuration = new Configuration();
        // Konfigurasi tambahan yang diperlukan, misalnya URI HDFS
        return FileSystem.get(configuration);
    }
}