import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class MultipartFormDataExample {

    public static void main(String[] args) throws IOException {
        String url = "http://example.com/your-post-endpoint";
        String authenticityToken = "your-authenticity-token";
        String projectName = "your-project-name";
        String projectIdentifier = "your-project-identifier";
        String parentId = "your-parent-id";

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(url);

        // Build the multipart form data
        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("authenticity_token", new StringBody(authenticityToken, ContentType.TEXT_PLAIN))
                .addPart("project[name]", new StringBody(projectName, ContentType.TEXT_PLAIN))
                .addPart("project[identifier]", new StringBody(projectIdentifier, ContentType.TEXT_PLAIN))
                .addPart("project[parent_id]", new StringBody(parentId, ContentType.TEXT_PLAIN))
                .build();

        httpPost.setEntity(entity);

        // Set the Content-Type header
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.toString());

        // Send the request and get the response
        HttpResponse response = httpClient.execute(httpPost);

        // Print the response status code
        System.out.println("Response Status Code: " + response.getStatusLine().getStatusCode());

        // Print the response body
        // You can handle the response body based on your requirements

        // Close the HttpClient
        //httpClient.close();
    }
}
