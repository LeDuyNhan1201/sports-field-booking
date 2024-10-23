package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public interface MinioClientService {

    FileMetadata uploadChunk(MultipartFile file, String fileName, int chunkNumber, int totalChunks) throws Exception;

    void storeObject(File file, String fileName, String contentType) throws Exception;

    String getObjectUrl(String objectKey) throws Exception;

    void deleteObject(String objectKey) throws Exception;

}
