package com.findwise;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizerImpl implements Tokenizer {


   private static class Singleton {
        private static final Tokenizer INSTANCE = new TokenizerImpl();
    }

    private TokenizerImpl() {

    }
    public static Tokenizer getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public List<String> getTokens(String content){
        StringTokenizer tokenizer = new StringTokenizer(content);
        List<String> tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken().toLowerCase());
        }

        return tokens;
    }
}
