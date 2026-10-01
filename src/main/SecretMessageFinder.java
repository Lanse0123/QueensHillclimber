package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/** Code written by Lance Nelkin
 * <br><br>
 * I know that python was preferred, but I am much better at writing in java, and it was on the
 * list of allowed languages. This was written in java 25 since I was just working on my Steam game,
 * and that is also written in java 25. As for documentation, I do not always comment this much,
 * but I usually put something at the top of my classes to say who it's from, and a brief description. **/
public class SecretMessageFinder {

//    public static void main(String[] args){
//        printSecretMessage("https://docs.google.com/document/d/e/2PACX-1vTMOmshQe8YvaRXi6gEPKKlsC6UpFJSMAk4mQjLm_u1gmHdVVTaeh7nBNFBRlui0sTZ-snGwZM4DBCT/pub");
//    }

    // I really could not think of a more creative name
    public static void printSecretMessage(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        //I really do not like throwing exceptions outside the scope of my functions if possible
        String html;
        try {
            html = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            System.out.println("Failed to send HTTP request");
            return;
        }

        String text = htmlToText(html);

        // Skip all parts before the coordinate table given. If the format is different from what
        // the example showed, then the start index should be 0 instead of after the header.
        int start = text.indexOf("y-coordinate") + "y-coordinate".length();
        if (start < "y-coordinate".length()) {
            System.out.println("Could not find coordinate table");
            return;
        }

        // Incase of any weird white spacing, trim it to just 1 space
        String[] parts = text.substring(start).trim().split("\\s+");

        Map<String, String> grid = new HashMap<>();
        int maxX = 0;
        int maxY = 0;

        for (int i = 0; i + 2 < parts.length; i += 3) {
            try {
                int x = Integer.parseInt(parts[i]);
                int y = Integer.parseInt(parts[i + 2]);

                grid.put(x + "," + y, parts[i + 1]);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);

            } catch (NumberFormatException ignored) {
                //Header row or some other weird area, so ignore it
            }
        }

        for (int y = maxY; y >= 0; y--) {
            StringBuilder line = new StringBuilder();

            for (int x = 0; x <= maxX; x++) {
                line.append(grid.getOrDefault(x + "," + y, " "));
            }

            System.out.println(line);
        }
    }

    // I don't usually write code dealing with HTML, but rather working on applications, so I had to
    // mess around a bit with putting the HTML into something easier to work with.
    private static String htmlToText(String html) {
        StringBuilder text = new StringBuilder();
        boolean insideTag = false;

        for (char character : html.toCharArray()) {
            if (character == '<') {
                insideTag = true;
                text.append(' ');
            } else if (character == '>') {
                insideTag = false;
                text.append(' ');
            } else if (!insideTag) {
                text.append(character);
            }
        }

        return text.toString();
    }
}