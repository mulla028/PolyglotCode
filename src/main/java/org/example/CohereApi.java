package org.example;
import okhttp3.*;

import org.json.JSONObject;


public class CohereApi {

    public static String callApi(String content, String language) throws Exception {

        String contentJson = JSONObject.quote(content);

        String rawResponse = "";
        OkHttpClient client = new OkHttpClient();

        String requestText = getMsg(false, language, contentJson);
        String json = requestText + ", \"model\": \"command-r\" }";

//        System.out.println("JSON request is: " + json);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://api.cohere.ai/v1/chat")
                .post(body)
                .addHeader("Authorization", "Bearer " + Api.key)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseString = response.body().string();
            JSONObject jsonObject = new JSONObject(responseString);
            rawResponse = jsonObject.getString("text");
//            System.out.println("\n" + rawResponse);
            return rawResponse;
        } else {
            return "Request failed: " + response.code();
        }
    }

    static String getMsg(Boolean c, String language, String fileContent) {
        String comments = "";
        if (c) { comments = "Comment line by line or block by block and "; }
        return "{ \"message\": \"" + comments + "Translate this code in " + language + "\\" + fileContent;

    }
}

