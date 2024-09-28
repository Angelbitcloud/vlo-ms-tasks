package com.co.velaio.vlo_ms_tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaDTO {
	
	private Long id;
	
    private String estado;
    
    private String nombreTarea;
    
    private Date fechaLimite;
    
    private List<PersonaDTO> personasAsociadas;
    
}