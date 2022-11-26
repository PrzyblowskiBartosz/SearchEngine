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
    public double calculateInverseDocumentFrequency(String word) {
        int documentCountWithWord = storage.getDocumentIdsWithToken(word).size();
        int getDocumentStoreStorage = storage.getDocumentStoreSize();
        double log = ((double) getDocumentStoreStorage) / ((double) documentCountWithWord);

        return Math.log(log);
    }

    @Override
    public double getScore(String word, String documentId) {
        List<String> documentTokens = storage.getDocumentById(documentId).orElse(Collections.emptyList());

        double termFrequency = calculateTermFrequency(word, documentTokens);
        double inverseDocumentFrequency = calculateInverseDocumentFrequency(word);

        return termFrequency * inverseDocumentFrequency;
    }

    @Override
    public void clearContext() {
        storage.clearContext();
    }
}
