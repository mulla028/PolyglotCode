package org.example;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.FileConfigBuilder;
import java.io.File;

public class Config {
  FileConfig config;

  Config() {
    File conigFile = new File(System.getProperty("user.home"), ".polyglotcode-config.toml");
    FileConfigBuilder builder = (FileConfigBuilder) FileConfig.builder(conigFile).sync();
    config = builder.build();

    try {
      config.load();
    } catch (Exception e) {
      throw new RuntimeException("Config file is not valid.");
    }

    if (config.isEmpty()) load_default();
  }

  private void load_default() {
    config.set("api.api_key", "");
    config.set("api.base_url", "https://api.cohere.ai/v1/chat");
    config.set("output.display_token", false);
    config.set("output.stream_response", false);
    config.save();
  }

  public String getApiKey() {
    return config.get("api.api_key");
  }

  public String getBaseUrl() {
    return config.get("api.base_url");
  }

  public boolean displayToken() {
    return config.get("output.display_token");
  }

  public boolean streamResponse() {
    return config.get("output.stream_response");
  }
}
