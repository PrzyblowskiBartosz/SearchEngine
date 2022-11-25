package com.findwise;

import com.findwise.model.IndexEntry;

import java.util.Optional;

public interface SearchEngineStorage extends Clerable {

    void addIndexEntry(IndexEntry indexEntry);

    Optional<IndexEntry> getIndexEntryById(String id);

    void updateIndexEntry(IndexEntry indexEntry);

    void addDocument(String documentId, String content);

    Optional<String> getDocument(String documentId);
}
