package com.example.Bayt.Service;

import com.example.Bayt.Job;
import com.google.maps.model.GeocodingResult;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService {
    @Autowired
    GeocodingService geocodingService;


    public List<Job> fetchJobsFromRssFeed(String rssUrl) {
        List<Job> jobs = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                String title = entry.getTitle();
                String location = parseLocationFromDescription(entry.getDescription().getValue());

                GeocodingResult[] geocodingResults = geocodingService.geocode(location);

                if (geocodingResults != null && geocodingResults.length > 0) {
                    double latitude = geocodingResults[0].geometry.location.lat;
                    double longitude = geocodingResults[0].geometry.location.lng;

                    jobs.add(new Job(title, location, latitude, longitude));
                } else {
                    jobs.add(new Job(title, location, 0, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    private String parseLocationFromDescription(String description) {

        String[] parts = description.split(",");
        if (parts.length >= 2) {
            return parts[0].trim() + ", " + parts[1].trim();
        } else {
            return "Unknown Location";
        }
    }
}
