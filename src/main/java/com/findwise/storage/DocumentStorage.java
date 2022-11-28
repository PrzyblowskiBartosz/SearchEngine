package com.findwise.storage;

import com.findwise.Testable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DocumentStorage extends Testable {

    void addDocument(String documentId, String content);

    Optional<List<String>> getDocumentContentById(String documentId);

    Set<String> getDocumentIdsWithTokens(Set<String> token);

    Map<String, List<String>> getDocumentsWithTokens(Set<String> token);

    int getDocumentStoreSize();
}
