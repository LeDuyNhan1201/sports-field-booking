package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.AppFileMetadata;
import org.springframework.stereotype.Service;

@Service
public interface FileMetadataService {

    AppFileMetadata findById(String id);

    AppFileMetadata create(AppFileMetadata item);

    void delete(AppFileMetadata item);

}
