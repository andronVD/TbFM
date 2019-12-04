package nasaApi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
@RequestMapping("/nasaApi")
public class NasaController {

    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() throws IOException {
        StringBuilder result = new StringBuilder();
        String apiUrl = "https://api.nasa.gov/planetary/apod";
        String apiKey = "api_key=UgZR1Lu9l9l5I5sxKPTpptKah8gtMPhvBl1GkEDe";

        URLConnection connection = new URL(apiUrl + "?" + apiKey).openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}
