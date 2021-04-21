package com.fluffy.spring.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Допоміжний клас, призначений для спрощення опрацювання процесу завантаження
 * файлу на сервер.
 * @author Сивоконь Вадим
 */
public final class FileUploadUtil {
    private FileUploadUtil() { }

    /**
     * Зберігає файл за вказаним шляхом.
     * @param uploadDir папка для завантаження
     * @param fileName назва файлу
     * @param multipartFile файл, що передається формою
     * @throws IOException якщо виникла помилка під час роботи із файловою
     *         системою
     */
    public static void saveFile(final String uploadDir, final String fileName,
                                final MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Не вдалося зберегти файл: " + fileName, ioe);
        }
    }
}
