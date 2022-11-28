package com.findwise;

import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;

import java.util.*;
import java.util.stream.Collectors;

public class SearchEngineImpl implements SearchEngine {

    private final IndexEntryStorage indexEntryStorage;
    private final DocumentStorage documentStorage;
    private final TFIDFCalculator tfidfCalculator;

    private static class Singleton {
        private static final SearchEngine INSTANCE = new SearchEngineImpl();
    }

    private SearchEngineImpl() {
        this.indexEntryStorage = IndexEntryStorageImpl.getInstance();
        this.documentStorage = DocumentStorageImpl.getInstance();
        this.tfidfCalculator = TFIDFCalculatorImpl.getInstance();
    }

    public static SearchEngine getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void indexDocument(String id, String content) {
        documentStorage.addDocument(id, content);
        Optional<List<String>> tokens = documentStorage.getDocumentContentById(id);
        if (tokens.isPresent()) {
            Set<String> uniqueTokens = new HashSet<>(tokens.get());

            Map<String, Set<String>> tokensToUpdate = getTokensToRecalculate(uniqueTokens);
            reCalculateIndexesForTokens(tokensToUpdate);
        }
    }

    //invert map
    private Map<String, Set<String>> getTokensToRecalculate(Set<String> tokens) {
        Map<String, List<String>> documentsWithTokens = documentStorage.getDocumentsWithTokens(tokens);
        return invertMap(documentsWithTokens);
    }

    private static Map<String, Set<String>> invertMap(Map<String, List<String>> documentsWithTokens) {
        Map<String, Set<String>> tokensDocuments = new HashMap<>();

        for (Map.Entry<String, List<String>> documentWithTokens : documentsWithTokens.entrySet()) {
            for (String token : documentWithTokens.getValue()) {
                if (tokensDocuments.containsKey(token)) {
                    tokensDocuments.get(token).add(documentWithTokens.getKey());
                } else {
                    Set<String> documentIds = new HashSet<>();
                    documentIds.add(documentWithTokens.getKey());
                    tokensDocuments.put(token, documentIds);
                }
            }
        }
        return tokensDocuments;
    }

    private void reCalculateIndexesForTokens(Map<String, Set<String>> tokensPosition) {
        Map<String, Set<IndexEntry>> indexedTokens = indexEntryStorage.getIndexEntriesForTokens(tokensPosition.keySet());

        for (Map.Entry<String, Set<String>> tokenDocuments : tokensPosition.entrySet()) {
            if (indexedTokens.containsKey(tokenDocuments.getKey()))
                updateTokenIndexEntries(tokenDocuments.getKey(), tokenDocuments.getValue());
            else {
                addTokenIndexEntries(tokenDocuments.getKey(), tokenDocuments.getValue());
            }
        }
    }

    private void addTokenIndexEntries(String token, Set<String> documentIds) {
        for (String documentId : documentIds) {
            double scores = tfidfCalculator.getScore(token, documentId);
            indexEntryStorage.addEditIndexEntry(token, documentId, scores);
        }
    }

    private void updateTokenIndexEntries(String token, Set<String> documentIds) {
        for (String documentId : documentIds) {
            double scores = tfidfCalculator.getScore(token, documentId);
            indexEntryStorage.addEditIndexEntry(token, documentId, scores);
        }
    }

    @Override
    public List<IndexEntry> search(String term) {
        Optional<Set<IndexEntry>> results = indexEntryStorage.getIndexEntriesByToken(term);
        return results
                .map(indexEntries -> indexEntries.stream()
                        .sorted(Comparator.comparing(IndexEntry::getScore))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
