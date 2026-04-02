package com.groupeisi.evcharging.controller;

import com.groupeisi.evcharging.dto.InvoiceResponse;
import com.groupeisi.evcharging.dto.TariffRequest;
import com.groupeisi.evcharging.service.IBillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Facturation", description = "API de facturation des sessions de recharge et gestion des tarifs")
public class BillingController {

    private final IBillingService billingService;

    public BillingController(IBillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une facture",
            description = "Retourne le détail d'une facture liée à une session de recharge."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Facture trouvée",
            content = @Content(schema = @Schema(implementation = InvoiceResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Facture introuvable")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        return new ResponseEntity<>(billingService.getInvoice(id), HttpStatus.OK);
    }

    @PostMapping("/tariffs")
    @Operation(
            summary = "Créer ou ajouter un tarif pour une station",
            description = "Crée un tarif de facturation pour une station donnée. Le dernier tarif créé est utilisé pour la facturation."
    )
    @ApiResponse(responseCode = "201", description = "Tarif créé")
    public ResponseEntity<Void> createTariff(
            @org.springframework.web.bind.annotation.RequestBody
            @jakarta.validation.Valid TariffRequest request) {
        billingService.createTariff(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

