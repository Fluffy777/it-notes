package com.fluffy.spring.services.impls;

import com.fluffy.spring.services.IconStorageService;
import com.fluffy.spring.services.exceptions.IconStorageException;
import com.fluffy.spring.services.exceptions.IconStorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class FileSystemIconStorageService implements IconStorageService {
    private final Path rootLocation;

    public FileSystemIconStorageService(IconStorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new IconStorageException("Не вдалося ініціалізувати сховище", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IconStorageException("Не вдалося записати порожній файл.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new IconStorageException(
                        "Не вдалося записати файл за межами поточної директорії.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new IconStorageException("Не вдалося записати файл.", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new IconStorageFileNotFoundException(
                        "Не вдалося зчитати файл: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new IconStorageFileNotFoundException("Не вдалося зчитати файл: " + filename, e);
        }
    }

    @Override
    public boolean isImage(MultipartFile file) {
        if (!Arrays.asList(
                "image/jpeg",
                "image/jpg",
                "image/png"
        ).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        } else {
            return true;
        }
    }
}
