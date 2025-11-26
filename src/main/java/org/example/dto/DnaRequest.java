package org.example.dto;


import lombok.*;
import org.example.validation.ValidDnaSequence;

@Data
public class DnaRequest {

    @ValidDnaSequence
    private String[] dna;
}