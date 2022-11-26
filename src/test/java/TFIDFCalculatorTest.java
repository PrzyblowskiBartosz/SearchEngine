import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.TFIDFCalculator;
import com.findwise.TFIDFCalculatorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDFCalculatorTest {

    TFIDFCalculator calculator = TFIDFCalculatorImpl.getInstance();
    DocumentStorage storage = DocumentStorageImpl.getInstance();

    @After
    public void clearStorage() {
        calculator.clearContext();
    }

    @Test
    public void should_return_term_frequency() {
        prepareStorage2();
        Map<String, List<String>> documents = storage.getDocumentsWithToken("Hyundai");
        List<String> documentTokens = documents.get("D3");

        float result = calculator.calculateTermFrequency("Hyundai", documentTokens);

        Assert.assertEquals("Frequency of 'brown' token should be equal 0.125 ", 0.125 , result, 0.0);
    }

    @Test
    public void should_return_inverse_document_frequency() {
        prepareStorage2();

        double result = calculator.calculateInverseDocumentFrequency("model");

        Assert.assertEquals("Inverse document frequency should be equal 1.386", 1.386, result, 0.001);
    }

    @Test
    public void should_get_scores() {
        prepareStorage2();

        double result = calculator.getScore("name", "D1");

        Assert.assertEquals("Scores for word 'name' should be equal 0.301", 0.301, result, 0.0001);
    }

    private void prepareStorage2() {
        Map<String, String> documents = new HashMap<>();
        documents.put("D1", "My name is Naftal");
        documents.put("D2", "My car is Hyundai");
        documents.put("D3", "The car I drive is a Hyundai Sonata");
        documents.put("D4", "My car is a Sonata model by Hyundai !!");

        for (Map.Entry<String, String> document : documents.entrySet()) {
            storage.addDocument(document.getKey(), document.getValue());
        }
    }
}
