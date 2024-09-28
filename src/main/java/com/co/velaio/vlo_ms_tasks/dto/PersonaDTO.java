package com.co.velaio.vlo_ms_tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDTO {

	private Long id;

	private Long edad;
	
    private String nombreCompleto;
    
    private List<String> habilidades;
    
}

