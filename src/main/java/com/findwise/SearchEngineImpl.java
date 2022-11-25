package com.findwise;

import com.findwise.model.IndexEntry;
import com.findwise.model.IndexEntryImpl;

import java.util.*;

public class SearchEngineImpl implements SearchEngine {

    private final SearchEngineStorage storage;
    private final TFIDFCalculator tfidfCalculator;
    private final Tokenizer tokenizer;

    private static class Singleton {
        private static final SearchEngine INSTANCE = new SearchEngineImpl();
    }

    private SearchEngineImpl() {
        this.storage = SearchEngingeStorageImpl.getInstance();
        this.tokenizer = TokenizerImpl.getInstance();
        this.tfidfCalculator = TFIDFCalculatorImpl.getInstance();
    }

    public static SearchEngine getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void indexDocument(String id, String content) {
        tokenizeDocument(id, content);
    }

    //todo
    private void tokenizeDocument(String documentId, String content) {
        List<String> tokens = tokenizer.getTokens(content);

        for (String token : tokens) {
            Optional<IndexEntry> indexEntry = storage.getIndexEntryById(documentId, token);
            if (indexEntry.isPresent()) {
                updateIndexEntry(documentId, token, indexEntry.get());
            } else {
                createNewIndexEntry(documentId, token);
            }
        }
    }

    //todo
    private void updateIndexEntry(String documentId, String token, IndexEntry indexEntry) {
        double newScore = tfidfCalculator.getScore(token, documentId);
        indexEntry.setScore(newScore);
        storage.updateIndexEntry(documentId, indexEntry);
    }

    //todo
    private void createNewIndexEntry(String documentId, String token) {
        double scores = tfidfCalculator.getScore(token, documentId);
        List<IndexEntry> indexEntry = Collections.singletonList(new IndexEntryImpl(documentId, scores));
        storage.addIndexEntries(documentId, indexEntry);
    }

    //todo
    @Override
    public List<IndexEntry> search(String term) {


        return null;
    }
}
