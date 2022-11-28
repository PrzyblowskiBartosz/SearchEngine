import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.TFIDFCalculator;
import com.findwise.TFIDFCalculatorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
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
        Map<String, List<String>> documents = storage.getDocumentsWithTokens(Collections.singleton("honda"));
        List<String> documentTokens = documents.get("D3");

        float result = calculator.calculateTermFrequency("honda", documentTokens);

        Assert.assertEquals("Frequency of 'honda' token should be equal 1/4 ", (double) 1/4 , result, 0.0);
    }

    @Test
    public void should_return_inverse_document_frequency() {
        prepareStorage2();

        double result = calculator.calculateInverseDocumentFrequency("model");

        Assert.assertEquals("Inverse document frequency should be equal 0.602", 0.602, result, 0.001);
    }

    @Test
    public void should_get_scores() {
        prepareStorage2();

        double result = calculator.getScore("name", "D1");

        Assert.assertEquals("Scores for word 'name' should be equal 0.301", 0.301, result, 0.0001);
    }

    private void prepareStorage2() {
        Map<String, String> documents = new HashMap<>();
        documents.put("D1", "name bartosz");
        documents.put("D2", "car honda");
        documents.put("D3", "car drive honda civic");
        documents.put("D4", "car civic model honda !!");

        for (Map.Entry<String, String> document : documents.entrySet()) {
            storage.addDocument(document.getKey(), document.getValue());
        }
    }
}
