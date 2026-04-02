package com.groupeisi.evcharging.controller;

import com.groupeisi.evcharging.dto.ChargingSessionRequest;
import com.groupeisi.evcharging.dto.ChargingSessionResponse;
import com.groupeisi.evcharging.service.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Sessions de recharge", description = "API de gestion des sessions de recharge VE")
public class SessionController {

    private final ISessionService sessionService;

    public SessionController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    @Operation(
            summary = "Démarrer une session de recharge",
            description = "Crée une nouvelle session de recharge après vérification et verrouillage de la borne."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Session créée",
            content = @Content(schema = @Schema(implementation = ChargingSessionResponse.class))
    )
    public ResponseEntity<ChargingSessionResponse> startSession(
            @org.springframework.web.bind.annotation.RequestBody
            @Valid ChargingSessionRequest request) {
        return new ResponseEntity<>(sessionService.startSession(request), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/stop")
    @Operation(
            summary = "Arrêter une session de recharge",
            description = "Clôture la session, libère la borne et publie un événement pour la facturation."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Session arrêtée",
            content = @Content(schema = @Schema(implementation = ChargingSessionResponse.class))
    )
    public ResponseEntity<ChargingSessionResponse> stopSession(@PathVariable Long id) {
        return new ResponseEntity<>(sessionService.stopSession(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une session",
            description = "Retourne les détails d'une session de recharge."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Session trouvée",
            content = @Content(schema = @Schema(implementation = ChargingSessionResponse.class))
    )
    public ResponseEntity<ChargingSessionResponse> getSession(@PathVariable Long id) {
        return new ResponseEntity<>(sessionService.getSession(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Lister les sessions d'un utilisateur",
            description = "Retourne toutes les sessions de recharge associées à un utilisateur donné."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des sessions",
            content = @Content(schema = @Schema(implementation = ChargingSessionResponse.class))
    )
    public ResponseEntity<List<ChargingSessionResponse>> getUserSessions(@RequestParam Long userId) {
        return new ResponseEntity<>(sessionService.getUserSessions(userId), HttpStatus.OK);
    }
}

