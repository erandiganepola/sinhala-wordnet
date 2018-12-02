package org.sinhala.wordnet.css.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeaningRequestHandler {

    public MeaningRequestHandler() {
        // enable this line if necessary
        this.setProxy("cache.mrt.ac.lk", "3128");
    }

    private void setProxy(String host, String port) {
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port);
    }

    public List<String> getIntersection(List<List<String>> meaningsList) {
        if (meaningsList.size() > 0) {
            List<String> intersection = new ArrayList<String>(meaningsList.get(0));
            for (int i = 0; i < meaningsList.size(); i++) {
                intersection.retainAll(meaningsList.get(i));
            }
            return intersection;
        } else {
            return new ArrayList<>();
        }
    }

    public List<List<String>> getMeaningLists(List<String> wordList) {
        List<List<String>> outList = new ArrayList<>();

        if (wordList.size() > 0) {
            for (String word : wordList) {
                outList.add(this.getMeanings(word));
            }
        }

        return outList;
    }

    private List<String> getMeanings(String word) {
        String url = "https://www.maduraonline.com/?find=" + word;
        ArrayList<String> meaningList = new ArrayList<String>();

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return meaningList;
        }

        Elements table = doc.getElementsByTag("table");
        Elements rows = table.select("td[class=td]");
        List<String> meanings = rows.eachText();
        for (String meaning : meanings) {
            if (meaning != null && !meaning.isEmpty()) {
                meaningList.add(meaning);
            }
        }

        return meaningList;
    }

}
