package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.springframework.stereotype.Service;

@Service
public interface FileMetadataService {

    FileMetadata findById(String id);

    FileMetadata create(FileMetadata item);

    FileMetadata update(FileMetadata item);

    void delete(FileMetadata item);

}
