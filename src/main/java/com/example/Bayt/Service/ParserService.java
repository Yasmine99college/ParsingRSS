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
            URL url = new URL("https://careers.moveoneinc.com/rss/all-rss.xml/");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                String title = entry.getTitle();
                String location = parseLocationFromDescription(entry.getDescription().getValue());

                // Use the GeocodingService to get coordinates for the location
                GeocodingResult[] geocodingResults = geocodingService.geocode(location);

                if (geocodingResults != null && geocodingResults.length > 0) {
                    // Get the first result (assuming it's the most accurate)
                    double latitude = geocodingResults[0].geometry.location.lat;
                    double longitude = geocodingResults[0].geometry.location.lng;

                    jobs.add(new Job(title, location, latitude, longitude));
                } else {
                    jobs.add(new Job(title, location, 0, 0)); // Default to 0,0 if coordinates not found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    private String parseLocationFromDescription(String description) {
        // Implement logic to extract location from the job description or other fields
        // You may use regular expressions, string manipulation, or any other suitable approach.
        // For simplicity, let's assume the location is in the format "City, Country" in the description.
        String[] parts = description.split(",");
        if (parts.length >= 2) {
            return parts[0].trim() + ", " + parts[1].trim();
        } else {
            return "Unknown Location";
        }
    }
}
