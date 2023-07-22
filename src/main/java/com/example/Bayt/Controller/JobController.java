package com.example.Bayt.Controller;

//import ch.qos.logback.core.model.Model;
import com.example.Bayt.Job;
import com.example.Bayt.Service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.ui.Model;


@Controller
public class JobController {
    @Autowired
    ParserService parser;
//    @GetMapping("/jobs")
//    public String displayJobs(Model model) {
//        String rssUrl = "https://careers.moveoneinc.com/rss/all-rss.xml/";
//        List<String> jobTitles = parser.fetchJobTitlesFromRssFeed(rssUrl);
//        model.addAttribute("jobTitles", jobTitles);
//        return "job-list";
//    }

    @GetMapping("/jobs")
    public String displayJobs(Model model) {
        String rssUrl = "https://careers.moveoneinc.com/rss/all-rss.xml/";
        List<Job> jobs = parser.fetchJobsFromRssFeed(rssUrl);

        model.addAttribute("jobs", jobs); // Pass job titles, locations, and coordinates to the template

        return "job-list";
    }
}
