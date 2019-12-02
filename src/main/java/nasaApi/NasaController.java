package nasaApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class NasaController {
    private static final String RES = "https://api.nasa.gov";
    private static final String GET_PICTURE_OF_THE_DAY = "/planetary/apod?";
    private static final String API_KEY = "UgZR1Lu9l9l5I5sxKPTpptKah8gtMPhvBl1GkEDe";
    private static final String CONTENT_TYPE = "application/json";


    public Optional<String> getRawResponse(String url, String contentType, String requestBody) throws IOException, MalformedURLException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestMethod("GET");

        connection.setDoOutput(true);
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        }
    }

    public static void main(String[] args) throws IOException {
        NasaController nasa = new NasaController();
        System.out.println(nasa.getRawResponse(RES + GET_PICTURE_OF_THE_DAY, CONTENT_TYPE, "api_key=" + API_KEY));
    }

}
