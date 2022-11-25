package com.findwise;

import java.util.List;

public interface TFIDFCalculator extends Testable {
    float calculateTermFrequency(String word, List<String> documentTokens);

    double calculateInverseDocumentFrequency(String word);

    double getScore(String word, String documentId);
}
