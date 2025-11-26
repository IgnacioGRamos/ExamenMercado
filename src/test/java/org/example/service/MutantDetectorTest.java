package org.example.service;

import org.example.service.MutantDetector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



class MutantDetectorTest {

    MutantDetector detector = new MutantDetector();

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna1 = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",  // ← Horizontal: CCCC
                "TCACTG"
        };
        // La diagonal principal también tiene una secuencia: A, A, A, A
        assertTrue(detector.isMutant(dna1));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias vertical")
    void testMutantWithVerticalSequences(){
        String[] dna2 = {
                "ATGCGA",
                "CTGTGC",
                "TTATGT",
                "ATAAGG",
                "CACCTA",
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna2));
    }


    @Test
    @DisplayName("Debe detectar mutante con secuencias Horizontales")
    void testMutantWithMultipleHorizontalSequences(){
        String[] dna3 = {
                "AAAAGA",
                "CTGTGC",
                "TGATTT",
                "ATAAGG",
                "CCCCTA",  // ← Horizontal: CCCC
                "TCACTG"
        };

        assertTrue(detector.isMutant(dna3));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendentes y descendentes")
    void testMutantWithBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTAATT",  // Modificado para crear secuencias
                "AGAAGG",
                "CACCTA",
                "ACACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con Matriz de 10x10")
    void testMutantWithLargeDna(){
        String[] dna5 = {
                "AAAAGACTAG",
                "CTGTGCCGGT",
                "TGATTTCAAG",
                "ATAAGGCCTA",
                "CCCCTAGGTT",
                "TCACTGTAAG",
                "AAAAGACTAG",
                "CTGTGCCGGT",
                "TGATTTCAAG",
                "ATAAGGCCTA"
        };

        assertTrue(detector.isMutant(dna5));
    }

    @Test
    @DisplayName("Debe detectar mutante con Todo igual (AAAA...)")
    void testMutantAllSameCharacter(){
        String[] dna6 = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };

        assertTrue(detector.isMutant(dna6));
    }

    @Test
    @DisplayName("Debe detectar un Humano, Solo 1 secuencia encontrada")
    void testNotMutantWithOnlyOneSequence(){
        String[] dna7 = {
                "ATGCGA",
                "CAGTGC",
                "TTATCT",
                "AGAAGG",
                "CDCCTA",  // ← Horizontal: CCCC
                "TCACTG"
        };
        // La diagonal principal también tiene una secuencia: A, A, A, A
        assertFalse(detector.isMutant(dna7));
    }

    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNotMutantWithNoSequences() {
        String[] dna8 = {
                "ATGCC",
                "CAGTA",
                "TTATG",
                "AGACG"
        };
        assertFalse(detector.isMutant(dna8));
    }

    @Test
    @DisplayName("Matriz 4x4 sin secuencias")
    void testNotMutantSmallDna(){
        String[] dna9 = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna9));
    }

    @Test
    @DisplayName("Debe rechazar ADN nulo")
    void testNotMutantWithNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    @DisplayName("Debe rechazar ADN vacío")
    void testNotMutantWithEmptyDna() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }


    @Test
    @DisplayName("Debe rechazar matriz no cuadrada")
    void testNotMutantWithNonSquareDna() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar caracteres inválidos")
    void testNotMutantWithInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC",  // ← 'X' es inválido
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar fila nula en el array")
    void testNotMutantWithNullRow() {
        String[] dna = {
                "ATGCGA",
                null,      // ← Fila nula
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar Dna demasiado pequeño")
    void testNotMutantWithTooSmallDna() {
        String[] dna = {
                "AAA",
                "CGT",
                "TAG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNotMutantWithSequenceLongerThanFour() {

        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "ATAAGG",
                "CACCGA",
                "TCACTG"
        };

        assertFalse(detector.isMutant(dna));

    }
}