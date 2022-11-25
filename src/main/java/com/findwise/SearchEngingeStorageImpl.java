package com.findwise;

import com.findwise.model.IndexEntry;

import java.util.*;

public class SearchEngingeStorageImpl implements SearchEngineStorage {

    private final Map<String, List<IndexEntry>> documentIndexEntries = new HashMap<>();
    private final Map<String, List<String>> documentStorage = new HashMap<>();

    Tokenizer tokenizer = TokenizerImpl.getInstance();

    @Override
    public void clearContext() {
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
    public void addIndexEntries(String documentId, List<IndexEntry> indexEntry) {
        documentIndexEntries.put(documentId, indexEntry);
    }

    @Override
    public Optional<IndexEntry> getIndexEntryById(String documentId, String token) {
        List<IndexEntry> indexEntries = documentIndexEntries.get(documentId);
        if (indexEntries.isEmpty())
            return Optional.empty();

        return indexEntries.stream().filter(indexEntry -> indexEntry.getId().equals(token)).findFirst();
    }

    @Override
    public void updateIndexEntry(String documentId, IndexEntry updatedIndexEntry) {
        Optional<IndexEntry> elemenToUpdate = getIndexEntryById(documentId, updatedIndexEntry.getId());
        elemenToUpdate.ifPresent(indexEntry -> indexEntry.setScore(updatedIndexEntry.getScore()));
    }

    @Override
    public void addDocument(String documentId, String content) {
        List<String> documentTokens = tokenizer.getTokens(content);
        documentStorage.put(documentId, documentTokens);
    }

    @Override
    public Optional<List<String>> getDocumentById(String documentId) {
        List<String> content = documentStorage.get(documentId);
        return content != null ? Optional.of(content) : Optional.empty();
    }

    @Override
    public Set<String> getDocumentIdsWithToken(String token) {
        Set<String> documentIds = new HashSet<>();
        for(Map.Entry<String, List<String>> document : documentStorage.entrySet()) {
            for (String documentToken : document.getValue()) {
                if (token.equals(documentToken)) {
                    documentIds.add(document.getKey());
                }
            }
        }

        return documentIds;
    }

    @Override
    public Map<String, List<String>> getDocumentsWithToken(String token) {
        Map<String, List<String>> documentsWithToken = new HashMap<>();
        for (Map.Entry<String, List<String>> document : documentStorage.entrySet()) {
            for (String documentToken : document.getValue()) {
                if (documentToken.equals(token)) {
                    documentsWithToken.put(document.getKey(), document.getValue());
                    break;
                }
            }
        }
        return documentsWithToken;
    }

    @Override
    public int getDocumentStoreSize() {
        return documentStorage.size();
    }
}
