package org.example;

import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokensTest {

    @Test
    @Tag("unit")
    void testValidJSON() {
        JSONObject meta = new JSONObject("{ `tokens`: { `input_tokens`: 69, `output_tokens`: 420 } }".replace('`', '"'));
        Tokens tokens = Tokens.fromJson(meta);
        assertEquals(tokens.getInputTokens(), 69);
        assertEquals(tokens.getOutputTokens(), 420);
    }

    @Test
    @Tag("unit")
    void testJSONMissingTokens() {
        JSONObject meta = new JSONObject("{}");
        Tokens tokens = Tokens.fromJson(meta);
        assertEquals(tokens.getInputTokens(), 0);
        assertEquals(tokens.getOutputTokens(), 0);
    }

    @Test
    @Tag("unit")
    void testJSONMissingInputTokens() {
        JSONObject meta = new JSONObject("{ `tokens`: { `output_tokens`: 420 } }".replace('`', '"'));
        Tokens tokens = Tokens.fromJson(meta);
        assertEquals(tokens.getInputTokens(), 0);
        assertEquals(tokens.getOutputTokens(), 420);
    }

    @Test
    @Tag("unit")
    void testJSONMissingOutputTokens() {
        JSONObject meta = new JSONObject("{ `tokens`: { `input_tokens`: 69 } }".replace('`', '"'));
        Tokens tokens = Tokens.fromJson(meta);
        assertEquals(tokens.getInputTokens(), 69);
        assertEquals(tokens.getOutputTokens(), 0);
    }
}
