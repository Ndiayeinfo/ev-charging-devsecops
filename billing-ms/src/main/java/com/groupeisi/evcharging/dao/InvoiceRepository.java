package com.groupeisi.evcharging.dao;

import com.groupeisi.evcharging.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}

