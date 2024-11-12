package org.jakartaee5g23.sportsfieldbooking.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface MinioClientService {

    long uploadChunk(MultipartFile file, String fileMetadataId, String chunkHash, long startByte, long totalSize, String contentType);

    void storeObject(File file, String fileName, String contentType, String bucketName);

    String getObjectUrl(String objectKey);

    void deleteObject(String objectKey, String bucketName);

}
