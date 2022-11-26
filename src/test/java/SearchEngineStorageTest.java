import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.Tokenizer;
import com.findwise.TokenizerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SearchEngineStorageTest {

    DocumentStorage storage = DocumentStorageImpl.getInstance();
    Tokenizer tokenizer = TokenizerImpl.getInstance();

    @After
    public void clearStorage() {
        storage.clearContext();
    }

    @Test
    public void should_add_document_to_storage() {
        String documentId = "document1";
        String documentContent = "the brown fox jumped over the brown dog";

        storage.addDocument(documentId, documentContent);
        Optional<List<String>> result = storage.getDocumentById(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", tokenizer.getTokens(documentContent), result.get());
    }

    @Test
    public void should_overrite_document_content_in_storage() {
        String documentId = "document1";
        String documentContent = "the brown fox jumped over the brown dog";
        String newDocumentContent = "the lazy brown dog sat in the corner";

        storage.addDocument(documentId, documentContent);
        storage.addDocument(documentId, newDocumentContent);
        Optional<List<String>> result = storage.getDocumentById(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", tokenizer.getTokens(newDocumentContent), result.get());
    }

    @Test
    public void should_return_content_with_empty_content() {
        String documentId = "document1";
        String documentContent = "";

        storage.addDocument(documentId, documentContent);
        Optional<List<String>> result = storage.getDocumentById(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", tokenizer.getTokens(documentContent), result.get());
    }

    @Test
    public void should_return_empty_results() {
        String documentId = "document1";
        String documentContent = "";

        storage.addDocument(documentId, documentContent);
        Optional<List<String>> result = storage.getDocumentById("document2");

        Assert.assertTrue("Results should be empty", result.isEmpty());
    }

    @Test
    public void should_receive_2_documents_containing_token() {
        prepareStorage();

        Set<String> result = storage.getDocumentIdsWithToken("brown");

        Assert.assertEquals("Result set should contains 2 documentIds", 2, result.size());

        Iterator<String> iterator = result.iterator();
        Assert.assertEquals("First document should be 'Document 1'", "Document 1", iterator.next());
        Assert.assertEquals("First document should be 'Document 2'", "Document 2", iterator.next());
    }

    @Test
    public void should_receive_empty_set_for_uknown_token() {
        prepareStorage();

        Set<String> result = storage.getDocumentIdsWithToken("unnownToken");

        Assert.assertTrue("Result list should be empty", result.isEmpty());
    }

    @Test
    public void should_receive_store_size() {
        prepareStorage();

       int result =  storage.getDocumentStoreSize();

       Assert.assertEquals("Store size should be 3", 3, result);
    }


    private void prepareStorage() {
        Map<String, String> documents = new HashMap<>();
        documents.put("Document 1", "the brown fox jumped over the brown dog");
        documents.put("Document 2", "the lazy brown dog sat in the corner");
        documents.put("Document 3", "the red fox bit the lazy dog");

        for (Map.Entry<String, String> document : documents.entrySet()) {
            storage.addDocument(document.getKey(), document.getValue());
        }
    }
}
