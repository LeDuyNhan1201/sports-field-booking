package org.jakartaee5g23.sportsfieldbooking.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface MinioClientService {

    String uploadChunk(MultipartFile file, String fileName, int chunkNumber, int totalChunks, String contentType);

    void storeObject(File file, String fileName, String contentType);

    String getObjectUrl(String objectKey);

    void deleteObject(String objectKey);

}
