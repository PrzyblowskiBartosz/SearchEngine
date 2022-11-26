package com.findwise.storage;

import com.findwise.Testable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DocumentStorage extends Testable {

    void addDocument(String documentId, String content);

    Optional<List<String>> getDocumentById(String documentId);

    Set<String> getDocumentIdsWithToken(String token);

    Map<String, List<String>> getDocumentsWithToken(String token);

    int getDocumentStoreSize();
}
