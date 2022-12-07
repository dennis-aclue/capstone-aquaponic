package de.voelkldennis.backend.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// Initialize the cache for guava
// guava against brute force attacks
@Service
public class LoginAttemptService {
    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private final LoadingCache<String, Integer> loginAttemptsCache;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LoginAttemptService() {
        super();
        loginAttemptsCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    // instead of looking for wrong user, you can use IP address
    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptsCache.invalidate(username);
    }

    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptsCache.get(username);
        } catch (ExecutionException e) {
            logger.error("***** :: Exception addUserToLoginAttemptCache :: ***** {}", e.getMessage());
        }
        loginAttemptsCache.put(username, attempts);
    }

    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptsCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            logger.error("***** :: Exception hasExceededMaxAttempts :: ***** {}", e.getMessage());
        }
        return false;
    }

}
