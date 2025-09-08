package com.nttdata.app.health;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

import static org.junit.jupiter.api.Assertions.*;

class CustomHealthIndicatorTest {

    @Test
    void testHealthUp() {
        CustomHealthIndicator customHealthIndicator = new CustomHealthIndicator();
        Health health = customHealthIndicator.health();
        assertEquals(Health.up().build(), health);
    }

    @Test
    void testHealthDown() {
        CustomHealthIndicator customHealthIndicator = new CustomHealthIndicator() {
            @Override
            public Health health() {
                return Health.down().withDetail("Error Code", 1).build();
            }
        };
        Health health = customHealthIndicator.health();
        assertEquals(Health.down().withDetail("Error Code", 1).build(), health);
    }

    @Test
    void testHealthWhenCheckReturnsZero() {
        CustomHealthIndicator customHealthIndicator = new CustomHealthIndicator() {
            @Override
            protected int check() {
                return 0;
            }
        };
        Health health = customHealthIndicator.health();
        assertEquals(Health.up().build(), health);
    }

    @Test
    void testHealthWhenCheckReturnsNonZero() {
        CustomHealthIndicator customHealthIndicator = new CustomHealthIndicator() {
            @Override
            protected int check() {
                return 1;
            }
        };
        Health health = customHealthIndicator.health();
        assertEquals(Health.down().withDetail("Error Code", 1).build(), health);
    }


}