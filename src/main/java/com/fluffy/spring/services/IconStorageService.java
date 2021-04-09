package com.fluffy.spring.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IconStorageService {
    void init();

    void store(MultipartFile file);

    Path load(String filename);

    Resource loadAsResource(String filename);

    boolean isImage(MultipartFile file);
}
