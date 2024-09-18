package org.example;
import okhttp3.*;

import org.json.JSONObject;

// CohereApi class to do and manage api
public class CohereApi {

    // call api method receives 4 strings
    // content - content from the files
    // language - language to translate file in
    // api - api-key
    // baseURL - baseURL default or specified by user
    public static JSONObject callApi(String content, String language, String api, String baseURL) throws Exception {

        // Transform content into the JSON object
        String contentJson = JSONObject.quote(content);

        // Create a client that sends requests to the api
        OkHttpClient client = new OkHttpClient();

        // Complete user's request
        String requestText = getMsg(false, language, contentJson);
        String json = requestText + ", \"model\": \"command-r\" }";

//        System.out.println("JSON request is: " + json);

        // Request's body
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        // Define a request sent to the server
        Request request = new Request.Builder()
                .url(baseURL)
                .post(body)
                .addHeader("Authorization", "Bearer " + api)
                .addHeader("Content-Type", "application/json")
                .build();

        // Retrieve a response from the server
        Response response = client.newCall(request).execute();
        // if -else statement
        // if response is successful transform from JSON
        // into String object
        // otherwise throw an exception
        if (response.isSuccessful()) {
            String responseString = response.body().string();
            return new JSONObject(responseString);
        } else {
            throw new Exception("Request failed: " + response.code() + " :c");
        }
    }

    // getMsg method, completes and returns json request
    static String getMsg(Boolean c, String language, String fileContent) {
        String comments = "";
        if (c) { comments = "Comment line by line or block by block and "; }
        return "{ \"message\": \"" + comments + "Translate this code in " + language + "\\" + fileContent;

    }
}

