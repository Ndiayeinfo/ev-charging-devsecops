package com.groupeisi.evcharging.service;

import com.groupeisi.evcharging.common.ChargingSessionFinishedEvent;
import com.groupeisi.evcharging.dto.InvoiceResponse;
import com.groupeisi.evcharging.dto.TariffRequest;

public interface IBillingService {

    void handleSessionFinished(ChargingSessionFinishedEvent event);

    InvoiceResponse getInvoice(Long id);

    void createTariff(TariffRequest request);
}

