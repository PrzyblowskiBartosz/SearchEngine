import com.findwise.Tokenizer;
import com.findwise.TokenizerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TokenizerTest {

    Tokenizer tokenizer = TokenizerImpl.getInstance();

    @Test
    public void should_return_tokens() {
        String text = "the brown fox jumped over the brown dog";

        List<String> result = tokenizer.getTokens(text);

        Assert.assertEquals("Should return 8 tokens", 8, result.size());
        Assert.assertEquals("jumped", result.get(3));
    }

    @Test
    public void should_return_empty_list() {
        String text = "";

        List<String> result = tokenizer.getTokens(text);

        Assert.assertTrue("Should return empty list", result.isEmpty());
    }
}
