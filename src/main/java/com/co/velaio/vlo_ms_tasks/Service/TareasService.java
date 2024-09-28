package com.co.velaio.vlo_ms_tasks.Service;

import java.util.List;

import com.co.velaio.vlo_ms_tasks.dto.PersonaDTO;
import com.co.velaio.vlo_ms_tasks.dto.TareaDTO;

public interface TareasService {

	List<TareaDTO> listarTareas();
	
	List<TareaDTO> obtenerTareas(String estado);
	
    TareaDTO obtenerTareaPorId(Long id);
    
    void crearTarea(TareaDTO tarea);
    
    void actualizarTarea(Long id, TareaDTO tarea);
    
    void borrarTarea(Long id);
    
    List<PersonaDTO> listarPersonas();

    PersonaDTO obtenerPersonaPorId(Long personaId);

    void crearPersona(PersonaDTO persona);

    void editarPersona(Long personaId, PersonaDTO persona);

    void borrarPersona(Long personaId);
}
