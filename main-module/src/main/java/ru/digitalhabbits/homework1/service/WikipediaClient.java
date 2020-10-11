package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.digitalhabbits.homework1.wikiJsonFormat.Page;
import ru.digitalhabbits.homework1.wikiJsonFormat.Query;
import ru.digitalhabbits.homework1.wikiJsonFormat.jsonStructure;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        // TODO: Done
        String jsonString;
        String result = "";
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                jsonString = EntityUtils.toString(entity);
                result = parseFromJsonToString(jsonString);
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return result;
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
    private String parseFromJsonToString(String jsonString) {
        Gson gson = new Gson();
        final jsonStructure root = gson.fromJson(jsonString, jsonStructure.class);
        final Query query = root.getQuery();
        final Map<String, Page> pages = query.getPages();
        String result = "";
        for (Page page : pages.values()) {
            result += page.getExtract();
        }
        result = result.replaceAll("\\\\n", "\n");
        return result;
    }
}
