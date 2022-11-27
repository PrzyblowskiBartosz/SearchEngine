package com.findwise;

import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;

import java.util.List;

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

    }


    @Override
    public List<IndexEntry> search(String term) {
        indexEntryStorage.getDocumentsWithToken(term);

        return null;
    }
}
