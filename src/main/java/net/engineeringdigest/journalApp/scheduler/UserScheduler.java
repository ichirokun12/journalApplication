package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {

    @Autowired
    private EmailSrvice emailSrvice;

    @Autowired
    private UserRepositoryImpl userRepository;


    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 21 ? * SUN ")
    public void fetchUserAndSaMail() {
        List<User> users = userRepository.getUsersForSA();
        for(User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments) {
                if(sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1 );
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment , Integer> entry : sentimentCount.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailSrvice.sendEmail(user.getEmail(), "sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void ClearAppCache() {
        appCache.init();
    }
}
