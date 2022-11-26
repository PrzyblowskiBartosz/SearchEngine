package com.findwise.storage;

import com.findwise.model.IndexEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IndexEntryStorageImpl implements IndexEntryStorage{
    private final Map<String, List<IndexEntry>> documentIndexEntries = new HashMap<>();

    private static class Singleton {
        private static final IndexEntryStorage INSTANCE = new IndexEntryStorageImpl();
    }

    private IndexEntryStorageImpl(){};
    public static IndexEntryStorage getInstance(){
        return Singleton.INSTANCE;
    }


    @Override
    public void addIndexEntries(String documentId, List<IndexEntry> indexEntry) {
        documentIndexEntries.put(documentId, indexEntry);
    }

    @Override
    public Optional<IndexEntry> getIndexEntryById(String documentId, String token) {
        List<IndexEntry> indexEntries = documentIndexEntries.get(documentId);
        if (indexEntries == null || indexEntries.isEmpty())
            return Optional.empty();

        return indexEntries.stream().filter(indexEntry -> indexEntry.getId().equals(token)).findFirst();
    }

    @Override
    public void updateIndexEntry(String documentId, IndexEntry updatedIndexEntry) {
        Optional<IndexEntry> elemenToUpdate = getIndexEntryById(documentId, updatedIndexEntry.getId());
        elemenToUpdate.ifPresent(indexEntry -> indexEntry.setScore(updatedIndexEntry.getScore()));
    }

    @Override
    public void clearContext() {
        documentIndexEntries.clear();
    }
}
