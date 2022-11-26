package com.findwise.storage;

import com.findwise.model.IndexEntry;

import java.util.List;
import java.util.Optional;

public interface IndexEntryStorage {
    void addIndexEntries(String documentId, List<IndexEntry> indexEntry);

    Optional<IndexEntry> getIndexEntryById(String documentId, String token);

    void updateIndexEntry(String documentId, IndexEntry updatedIndexEntry);
}
