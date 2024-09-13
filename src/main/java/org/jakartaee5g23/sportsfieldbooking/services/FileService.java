package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService<T> {

    FileMetadata findById(String id);

    String getObjectUrl(FileMetadata file);

    void uploadFiles(T owner, List<MultipartFile> files);

    void deleteFile(String fileId);

    void deleteFolder(T owner);

}
