package org.example.service;

import org.example.dto.DnaRequest;
import org.example.validation.ValidDnaSequence;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MIN_SEQUENCES_FOR_MUTANT = 2;

    public boolean isMutant(@ValidDnaSequence String[] dna) {

        if(dna == null || dna.length == 0) return false;

        final int n = dna.length;
        final char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MIN_SEQUENCES_FOR_MUTANT) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MIN_SEQUENCES_FOR_MUTANT) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MIN_SEQUENCES_FOR_MUTANT) return true;
                    }
                }
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MIN_SEQUENCES_FOR_MUTANT) return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row][col + i] != base) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col] != base) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col + i] != base) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row - i][col + i] != base) {
                return false;
            }
        }
        return true;
    }

}

