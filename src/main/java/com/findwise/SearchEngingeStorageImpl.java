package com.findwise;

import com.findwise.model.IndexEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SearchEngingeStorageImpl implements SearchEngineStorage, Clerable {

    Map<String, IndexEntry> storage = new HashMap<>();
    private List<IndexEntry> indexEntries;

    Map<String, String> documentStorage = new HashMap<>();

    @Override
    public void clear() {
        documentStorage.clear();
    }

    private static class Singleton{
        private static final SearchEngineStorage INSTANCE = new SearchEngingeStorageImpl();
    }

    private SearchEngingeStorageImpl(){}
    public static SearchEngineStorage getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void addIndexEntry(IndexEntry indexEntry) {
        storage.put(indexEntry.getId(), indexEntry);
    }

    @Override
    public Optional<IndexEntry> getIndexEntryById(String id) {
        IndexEntry indexEntry = storage.get(id);

        return indexEntry != null ? Optional.of(indexEntry) : Optional.empty();
    }

    @Override
    public void updateIndexEntry(IndexEntry indexEntry) {
        storage.put(indexEntry.getId(), indexEntry);
    }

    @Override
    public void addDocument(String documentId, String content) {
        documentStorage.put(documentId, content);
    }

    @Override
    public Optional<String> getDocument(String documentId) {
        String content = documentStorage.get(documentId);
        return content != null ? Optional.of(content) : Optional.empty();
    }

}
