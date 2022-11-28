package com.findwise;

import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;

import java.util.Collections;
import java.util.List;

public class TFIDFCalculatorImpl implements TFIDFCalculator {

    private final DocumentStorage storage = DocumentStorageImpl.getInstance();

    private static class Singleton{
        private static final TFIDFCalculator INSTANCE = new TFIDFCalculatorImpl();
    }

    private TFIDFCalculatorImpl(){}
    public static TFIDFCalculator getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public float calculateTermFrequency(String word, List<String> documentTokens) {
        float wordCount = 0;
        for (String token : documentTokens) {
            if (token.equals(word)) {
                wordCount++;
            }
        }

        return wordCount / documentTokens.size();
    }

    @Override
    public double calculateInverseDocumentFrequency(String term) {
        int getDocumentStoreStorage = storage.getDocumentStoreSize();
        int documentCountWithWord = storage.getDocumentIdsWithTokens(Collections.singleton(term)).size();
        double log = ((double) getDocumentStoreStorage) / ((double) documentCountWithWord);

        return Math.log10(log);
    }

    @Override
    public double getScore(String term, String documentId) {
        List<String> documentTokens = storage.getDocumentContentById(documentId).orElse(Collections.emptyList());

        double termFrequency = calculateTermFrequency(term, documentTokens);
        double inverseDocumentFrequency = calculateInverseDocumentFrequency(term);

        return termFrequency * inverseDocumentFrequency;
    }

    @Override
    public void clearContext() {
        storage.clearContext();
    }
}
