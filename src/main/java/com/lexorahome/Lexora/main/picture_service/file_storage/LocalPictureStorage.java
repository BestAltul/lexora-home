package com.lexorahome.Lexora.main.picture_service.file_storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalPictureStorage implements PictureStorage{

    private final Path rootLocation =Paths.get("uploads");

    public  LocalPictureStorage() throws IOException{
        Files.createDirectories(rootLocation);
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID()+"-"+file.getOriginalFilename();
        Path destination = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

    @Override
    public byte[] load(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    @Override
    public void delete(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }
}
