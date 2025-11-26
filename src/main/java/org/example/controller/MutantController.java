package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Check if a DNA sequence belongs to a mutant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The DNA sequence belongs to a mutant"),
            @ApiResponse(responseCode = "403", description = "The DNA sequence belongs to a human"),
            @ApiResponse(responseCode = "400", description = "Invalid DNA sequence provided")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Get statistics of DNA verifications")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}