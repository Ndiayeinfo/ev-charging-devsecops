package com.groupeisi.evcharging.config;

import com.groupeisi.evcharging.common.ChargingSessionFinishedEvent;
import com.groupeisi.evcharging.service.IBillingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SessionEventsListener {

    private final IBillingService billingService;

    public SessionEventsListener(IBillingService billingService) {
        this.billingService = billingService;
    }

    @KafkaListener(topics = "charging-sessions-events", groupId = "billing-ms")
    public void onSessionFinished(ChargingSessionFinishedEvent event) {
        billingService.handleSessionFinished(event);
    }
}

