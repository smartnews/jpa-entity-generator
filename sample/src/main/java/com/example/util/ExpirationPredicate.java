package com.example.util;

public interface ExpirationPredicate {

    Integer getExpirationTimestamp();

    default boolean isValid() {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        return currentTimestamp <= getExpirationTimestamp();
    }

}
