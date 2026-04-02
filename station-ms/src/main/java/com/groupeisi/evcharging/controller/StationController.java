package com.groupeisi.evcharging.controller;

import com.groupeisi.evcharging.dto.ChargerCreateRequest;
import com.groupeisi.evcharging.dto.StationCreateRequest;
import com.groupeisi.evcharging.dto.StationDto;
import com.groupeisi.evcharging.service.IStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@Tag(name = "Stations", description = "API de gestion des stations et bornes de recharge")
public class StationController {

    private final IStationService stationService;

    public StationController(IStationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les stations",
            description = "Retourne la liste complète des stations avec leurs bornes associées."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des stations",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StationDto.class))
    )
    public ResponseEntity<List<StationDto>> getStations() {
        return new ResponseEntity<>(stationService.getStations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une station par son identifiant",
            description = "Retourne le détail d'une station de recharge."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Station trouvée",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StationDto.class))
    )
    @ApiResponse(responseCode = "404", description = "Station introuvable")
    public ResponseEntity<StationDto> getStation(@PathVariable Long id) {
        return new ResponseEntity<>(stationService.getStation(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Créer une nouvelle station",
            description = "Crée une station de recharge avec un nom et une localisation."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Station créée",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StationDto.class))
    )
    public ResponseEntity<StationDto> createStation(
            @org.springframework.web.bind.annotation.RequestBody
            @jakarta.validation.Valid StationCreateRequest request) {
        return new ResponseEntity<>(stationService.createStation(request), HttpStatus.CREATED);
    }

    @PostMapping("/{stationId}/chargers/{chargerId}/lock")
    @Operation(
            summary = "Verrouiller une borne",
            description = "Réserve une borne de recharge pour une session en cours."
    )
    @ApiResponse(responseCode = "204", description = "Borne verrouillée")
    public ResponseEntity<Void> lockCharger(@PathVariable Long stationId, @PathVariable Long chargerId) {
        stationService.lockCharger(stationId, chargerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{stationId}/chargers/{chargerId}/unlock")
    @Operation(
            summary = "Déverrouiller une borne",
            description = "Libère une borne de recharge après la fin d'une session."
    )
    @ApiResponse(responseCode = "204", description = "Borne déverrouillée")
    public ResponseEntity<Void> unlockCharger(@PathVariable Long stationId, @PathVariable Long chargerId) {
        stationService.unlockCharger(stationId, chargerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{stationId}/chargers")
    @Operation(
            summary = "Ajouter une borne à une station",
            description = "Crée une nouvelle borne de recharge pour une station existante."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Borne créée et station mise à jour",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StationDto.class))
    )
    public ResponseEntity<StationDto> addCharger(
            @PathVariable Long stationId,
            @org.springframework.web.bind.annotation.RequestBody
            @jakarta.validation.Valid ChargerCreateRequest request) {
        return new ResponseEntity<>(stationService.addCharger(stationId, request), HttpStatus.CREATED);
    }
}

