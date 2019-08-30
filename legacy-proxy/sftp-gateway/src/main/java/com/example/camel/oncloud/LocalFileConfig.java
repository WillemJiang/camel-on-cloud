package com.example.camel.oncloud;

import org.springframework.beans.factory.annotation.Value;

public class LocalFileConfig {
    @Value("${file.staging.folder}")
    String stagingFolder;
    @Value("${file.archive.folder}")
    String archiveFolder;
    @Value("${file.checksum.folder}")
    String checksumFolder;
}
