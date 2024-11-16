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
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.COULD_NOT_READ_FILE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileMetadataServiceImpl implements FileMetadataService {

    FileMetadataRepository fileMetadataRepository;

    @Override
    public FileMetadata findById(String id) {
        return fileMetadataRepository.findById(id)
                .orElseThrow(() -> new FileException(COULD_NOT_READ_FILE, BAD_REQUEST));
    }

    @Override
    @Transactional
    public FileMetadata create(FileMetadata item) {
        return fileMetadataRepository.save(item);
    }

    @Override
    public FileMetadata update(FileMetadata item) {
        FileMetadata existing = findById(item.getId());
        existing.setObjectKey(item.getObjectKey());
        return fileMetadataRepository.save(item);
    }

    @Override
    @Transactional
    public void delete(FileMetadata item) {
        fileMetadataRepository.delete(item);
    }

}
