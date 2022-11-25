import com.findwise.SearchEngineStorage;
import com.findwise.SearchEngingeStorageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class SearchEngineStorageTest {

    SearchEngineStorage storage = SearchEngingeStorageImpl.getInstance();

    @After
    public void clearStorage() {
        storage.clearContext();
    }

    @Test
    public void should_add_document_to_storage() {
        String documentId = "document1";
        String documentContent = "the brown fox jumped over the brown dog";

        storage.addDocument(documentId, documentContent);
        Optional<String> result = storage.getDocument(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", documentContent, result.get());
    }

    @Test
    public void should_overrite_document_content_in_storage() {
        String documentId = "document1";
        String documentContent = "the brown fox jumped over the brown dog";
        String newDocumentContent = "the lazy brown dog sat in the corner";

        storage.addDocument(documentId, documentContent);
        storage.addDocument(documentId, newDocumentContent);
        Optional<String> result = storage.getDocument(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", newDocumentContent, result.get());
    }

    @Test
    public void should_return_content_with_empty_content() {
        String documentId = "document1";
        String documentContent = "";

        storage.addDocument(documentId, documentContent);
        Optional<String> result = storage.getDocument(documentId);

        Assert.assertFalse("Document content should not be empty", result.isEmpty());
        Assert.assertEquals("Document content shouldnt be different!", documentContent, result.get());
    }

    @Test
    public void should_return_empty_results() {
        String documentId = "document1";
        String documentContent = "";

        storage.addDocument(documentId, documentContent);
        Optional<String> result = storage.getDocument("document2");

        Assert.assertTrue("Results should be empty", result.isEmpty());
    }

}
