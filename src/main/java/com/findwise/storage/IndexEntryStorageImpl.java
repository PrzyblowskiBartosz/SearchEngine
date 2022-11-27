package com.findwise.storage;

import com.findwise.IndexEntry;

import java.util.*;
import java.util.stream.Collectors;

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
    public Map<String, List<IndexEntry>> getDocumentsWithToken(String token) {
        return documentIndexEntries.entrySet().stream().filter(entry -> {
            for (IndexEntry indexEntry : entry.getValue()) {
                if (indexEntry.getId().equals(token))
                    return true;
            }
            return false;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void clearContext() {
        documentIndexEntries.clear();
    }
}
