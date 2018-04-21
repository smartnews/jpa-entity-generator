package com.example.util;

public interface ExpirationPredicate {

    Integer getExpirationTimestamp();

    default boolean isValid() {
        long currentTimstamp = System.currentTimeMillis() / 1000;
        return currentTimstamp <= getExpirationTimestamp();
    }

}
