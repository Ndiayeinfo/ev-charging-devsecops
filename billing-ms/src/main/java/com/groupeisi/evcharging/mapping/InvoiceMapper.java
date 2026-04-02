package com.groupeisi.evcharging.mapping;

import com.groupeisi.evcharging.dto.InvoiceResponse;
import com.groupeisi.evcharging.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceResponse toResponse(Invoice invoice);
}

