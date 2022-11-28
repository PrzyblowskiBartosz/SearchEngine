package com.findwise.storage;

import com.findwise.IndexEntry;
import com.findwise.IndexEntryImpl;

import java.util.*;
import java.util.stream.Collectors;

public class IndexEntryStorageImpl implements IndexEntryStorage{

    /**
     * K - token
     * V - Set<IndexEntry> (id - documentId, scores - TF-IDF results)
     * */
    private final Map<String, Set<IndexEntry>> tokenIndexEntriesStorage = new HashMap<>();

    private static class Singleton {
        private static final IndexEntryStorage INSTANCE = new IndexEntryStorageImpl();
    }

    private IndexEntryStorageImpl(){}
    public static IndexEntryStorage getInstance(){
        return Singleton.INSTANCE;
    }

    @Override
    public void addEditIndexEntry(String token, String documentId, double scores) {
        Set<IndexEntry> indexEntries = tokenIndexEntriesStorage.get(token);
        if (indexEntries == null) {
            addIndexEntry(token, documentId, scores);
        } else {
            editIndexEntry(documentId, scores, indexEntries);
        }
    }

    private void addIndexEntry(String token, String documentId, double scores) {
        Set<IndexEntry> newTokensIndexEntries = new HashSet<>();
        newTokensIndexEntries.add(new IndexEntryImpl(documentId, scores));
        tokenIndexEntriesStorage.put(token, newTokensIndexEntries);
    }

    private void editIndexEntry(String documentId, double scores, Set<IndexEntry> indexEntries) {
        Optional<IndexEntry> indexEntry1 = indexEntries.stream()
                .filter(index -> Objects.equals(index.getId(), documentId))
                .findAny();

        if (indexEntry1.isPresent()) {
            indexEntry1.get().setScore(scores);
            indexEntries.remove(indexEntry1.get());
            indexEntries.add(indexEntry1.get());
        } else {
            indexEntries.add(new IndexEntryImpl(documentId, scores));
        }
    }

    @Override
    public Optional<Set<IndexEntry>> getIndexEntriesByToken(String token) {
        Set<IndexEntry> indexEntries = tokenIndexEntriesStorage.get(token.toLowerCase());
        if (indexEntries == null || indexEntries.isEmpty())
            return Optional.empty();

        return Optional.of(indexEntries);
    }

    @Override
    public Map<String, Set<IndexEntry>> getIndexEntriesForTokens(Set<String> token) {
        return tokenIndexEntriesStorage.entrySet().stream()
                .filter(entry -> token.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void clearContext() {
        tokenIndexEntriesStorage.clear();
    }
}
