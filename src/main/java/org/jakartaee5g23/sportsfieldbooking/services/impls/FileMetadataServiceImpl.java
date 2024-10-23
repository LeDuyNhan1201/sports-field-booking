package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.repositories.FileMetadataRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FileMetadataService;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.COULD_NOT_READ_FILE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileMetadataServiceImpl implements FileMetadataService {

    FileMetadataRepository fileMetadataRepository;

    MinioClientService minioClientService;

    @Override
    public FileMetadata findById(String id) {
        return fileMetadataRepository.findById(id)
                .orElseThrow(() -> new FileException(COULD_NOT_READ_FILE, BAD_REQUEST));
    }

    @Override
    public String getObjectUrl(FileMetadata fileMetadata) {
        String objectKey = fileMetadata.getObjectKey();
        try {
            return minioClientService.getObjectUrl(objectKey);

        } catch (Exception e) {
            throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public void uploadFiles(List<MultipartFile> files) {
        List<FileMetadata> results = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileId = UUID.randomUUID().toString();
            results.add(FileMetadata.builder()
                    .id(fileId)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .build());
        }
        fileMetadataRepository.saveAll(results);
    }

    @Override
    public void deleteFile(String id) {
        fileMetadataRepository.deleteById(id);
    }

}
