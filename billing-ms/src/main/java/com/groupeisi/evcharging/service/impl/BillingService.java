package com.groupeisi.evcharging.service.impl;

import com.groupeisi.evcharging.common.ChargingSessionFinishedEvent;
import com.groupeisi.evcharging.common.InvoiceStatus;
import com.groupeisi.evcharging.dao.InvoiceRepository;
import com.groupeisi.evcharging.dao.TariffRepository;
import com.groupeisi.evcharging.dto.InvoiceResponse;
import com.groupeisi.evcharging.dto.TariffRequest;
import com.groupeisi.evcharging.entities.Invoice;
import com.groupeisi.evcharging.entities.Tariff;
import com.groupeisi.evcharging.mapping.InvoiceMapper;
import com.groupeisi.evcharging.service.IBillingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class BillingService implements IBillingService {

    private final TariffRepository tariffRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public BillingService(TariffRepository tariffRepository,
                          InvoiceRepository invoiceRepository,
                          InvoiceMapper invoiceMapper) {
        this.tariffRepository = tariffRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public void handleSessionFinished(ChargingSessionFinishedEvent event) {
        Tariff tariff = tariffRepository.findFirstByStationIdOrderByIdDesc(event.getStationId())
                .orElseThrow(() -> new IllegalStateException("No tariff configured for station " + event.getStationId()));

        double energyCost = event.getEnergyKwh() * tariff.getPricePerKwh();
        double timeCost = event.getDurationMinutes() * tariff.getPricePerMinute();
        double amount = energyCost + timeCost;

        Invoice invoice = new Invoice();
        invoice.setSessionId(event.getSessionId());
        invoice.setUserId(event.getUserId());
        invoice.setAmount(amount);
        invoice.setCurrency("EUR");
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setCreatedAt(LocalDateTime.now());

        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public void createTariff(TariffRequest request) {
        Tariff tariff = new Tariff();
        tariff.setStationId(request.getStationId());
        tariff.setPricePerKwh(request.getPricePerKwh());
        tariff.setPricePerMinute(request.getPricePerMinute());
        tariff.setIdlePenaltyPerMinute(request.getIdlePenaltyPerMinute());
        tariffRepository.save(tariff);
    }
}

