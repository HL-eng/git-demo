import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.IOException;

public class UploadFileWithoutLuanMa {

    public static void main(String[] args) {
        String url = "http://example.com/upload"; // Replace with the target URL
        String filePath = "/path/to/your/file";   // Replace with the actual file path

        try {
            uploadFile(url, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(String url, String filePath) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(url);

            // Prepare the file to be uploaded
            File file = new File(filePath);

            // Build the multipart entity
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(
                    "file",
                    file,
                    ContentType.DEFAULT_BINARY,
                    file.getName()
            );

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Handle the response, if needed
            // int statusCode = response.getStatusLine().getStatusCode();
            // String responseContent = EntityUtils.toString(response.getEntity());
            // System.out.println("Response code: " + statusCode);
            // System.out.println("Response content: " + responseContent);
        } finally {
            // Close the HttpClient
            httpClient.close();
        }
    }
}

