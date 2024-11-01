package org.example;

import org.json.JSONObject;

public class Tokens {
  private final int inputTokens;

  private final int outputTokens;

  public Tokens(int inputTokens, int outputTokens) {
    this.inputTokens = inputTokens;
    this.outputTokens = outputTokens;
  }

  public int getInputTokens() {
    return inputTokens;
  }

  public int getOutputTokens() {
    return outputTokens;
  }

  public static Tokens fromJson(JSONObject meta) {
    if (meta.has("tokens")) {
      JSONObject tokens = meta.getJSONObject("tokens");
      int inputTokens = tokens.optInt("input_tokens", 0);
      int outputTokens = tokens.optInt("output_tokens", 0);
      return new Tokens(inputTokens, outputTokens);
    }
    return new Tokens(0, 0);
  }
}
