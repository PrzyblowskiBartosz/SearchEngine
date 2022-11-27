package com.findwise;

import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SearchEngineImpl implements SearchEngine {

    private final IndexEntryStorage indexEntryStorage;
    private final TFIDFCalculator tfidfCalculator;
    private final Tokenizer tokenizer;

    private static class Singleton {
        private static final SearchEngine INSTANCE = new SearchEngineImpl();
    }

    private SearchEngineImpl() {
        this.indexEntryStorage = IndexEntryStorageImpl.getInstance();
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
            Optional<IndexEntry> indexEntry = indexEntryStorage.getIndexEntryById(documentId, token);
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
        indexEntryStorage.updateIndexEntry(documentId, indexEntry);
    }

    //todo
    private void createNewIndexEntry(String documentId, String token) {
        double scores = tfidfCalculator.getScore(token, documentId);
        List<IndexEntry> indexEntry = Collections.singletonList(new IndexEntryImpl(documentId, scores));
        indexEntryStorage.addIndexEntries(documentId, indexEntry);
    }

    //todo
    @Override
    public List<IndexEntry> search(String term) {
        indexEntryStorage.getDocumentsWithToken(term);

        return null;
    }
}
