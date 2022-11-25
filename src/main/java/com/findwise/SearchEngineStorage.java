package com.findwise;

import com.findwise.model.IndexEntry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SearchEngineStorage extends Testable {

    void addIndexEntries(String documentId, List<IndexEntry> indexEntry);

    Optional<IndexEntry> getIndexEntryById(String documentId, String id);

    void updateIndexEntry(String documentId, IndexEntry indexEntry);

    void addDocument(String documentId, String content);

    Optional<List<String>> getDocumentById(String documentId);

    Set<String> getDocumentIdsWithToken(String token);

    Map<String, List<String>> getDocumentsWithToken(String token);

    int getDocumentStoreSize();
}
