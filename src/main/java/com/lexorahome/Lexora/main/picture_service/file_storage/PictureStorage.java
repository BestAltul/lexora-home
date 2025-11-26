package com.lexorahome.Lexora.main.picture_service.file_storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureStorage {
    String save(MultipartFile file) throws IOException;
    byte[] load(String path) throws IOException;
    void delete(String path) throws IOException;
}
