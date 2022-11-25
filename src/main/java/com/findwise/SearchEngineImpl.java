package com.findwise;

import java.util.List;

public class SearchEngineImpl implements SearchEngine {

    private static class Singleton {
        private static final SearchEngine INSTANCE = new SearchEngineImpl();
    }

    public static SearchEngine getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void indexDocument(String id, String content) {

    }

    @Override
    public List<IndexEntry> search(String term) {
        return null;
    }
}
