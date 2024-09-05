package com.sparta.onboarding.domain.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileService {

    private final Path rootLocation;

    public FileService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public String store(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isEmpty()) {
            throw new RuntimeException("Invalid file name!");
        }

        // Check file type
        String contentType = file.getContentType();
        if (!isSupportedContentType(contentType)) {
            throw new RuntimeException("Unsupported file type: " + contentType);
        }

        Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
        file.transferTo(destinationFile);
        return filename;
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Stream<Path> loadAll() throws IOException {
        return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
    }

    private boolean isSupportedContentType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/jpg".equals(contentType);
    }
}
