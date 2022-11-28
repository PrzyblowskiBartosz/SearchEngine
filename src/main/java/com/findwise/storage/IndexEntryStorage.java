package com.findwise.storage;

import com.findwise.IndexEntry;
import com.findwise.Testable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IndexEntryStorage extends Testable {
    void addEditIndexEntry(String token, String documentId, double scores);

    Optional<Set<IndexEntry>> getIndexEntriesByToken(String token);

    Map<String, Set<IndexEntry>> getIndexEntriesForTokens(Set<String> token);
}
