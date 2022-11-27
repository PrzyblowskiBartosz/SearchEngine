package com.findwise.storage;

import com.findwise.Testable;
import com.findwise.IndexEntry;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IndexEntryStorage extends Testable {
    void addIndexEntries(String documentId, List<IndexEntry> indexEntry);

    Optional<IndexEntry> getIndexEntryById(String documentId, String token);

    void updateIndexEntry(String documentId, IndexEntry updatedIndexEntry);

    Map<String, List<IndexEntry>> getDocumentsWithToken(String token);
}
