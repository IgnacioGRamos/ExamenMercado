package org.example.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest  // ← Levanta contexto completo de Spring
@AutoConfigureMockMvc  // ← Simula requests HTTP
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;  // ← Para hacer requests simulados

    @Test
    @DisplayName("POST /mutant debe retornar 200 para mutante")
    void testCheckMutant_ReturnOk_WhenIsMutant() throws Exception {
        String jsonRequest = """
            {
              "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
            }
            """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());  // ← Verifica HTTP 200
    }

    @Test
    @DisplayName("POST /mutant debe retornar 200 para mutante")
    void testCheckMutantReturns403ForHuman() throws Exception {
        String jsonRequest = """
            {
              "dna": ["ATGCGA","CAGTGC","TTATCT","AGAAGG","CTCCTA","TCACTG"]
            }
            """;

        mockMvc.perform(
                        post("/mutant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isForbidden());  // 403 Forbidden
    }

}