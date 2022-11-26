package com.findwise.storage;

import com.findwise.Tokenizer;
import com.findwise.TokenizerImpl;

import java.util.*;

public class DocumentStorageImpl implements DocumentStorage {

    private final Map<String, List<String>> documentStorage = new HashMap<>();

    Tokenizer tokenizer = TokenizerImpl.getInstance();

    @Override
    public void clearContext() {
        documentStorage.clear();
    }

    private static class Singleton{
        private static final DocumentStorage INSTANCE = new DocumentStorageImpl();
    }

    private DocumentStorageImpl(){}
    public static DocumentStorage getInstance() {
        return Singleton.INSTANCE;
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
