package com.co.velaio.vlo_ms_tasks.Persistence;

import java.util.List;

import com.co.velaio.vlo_ms_tasks.dto.PersonaDTO;
import com.co.velaio.vlo_ms_tasks.dto.TareaDTO;

public interface TareasDAO {
	
	List<TareaDTO> obtenerTareas();
	
    List<TareaDTO> obtenerTareasPorEstado(String estado);
    
    TareaDTO obtenerTareaPorId(Long id);
    
    void crearTarea(TareaDTO tarea);
    
    void actualizarTarea(Long id, TareaDTO tarea);
    
    void borrarTarea(Long id);
    
    List<PersonaDTO> listarTodasLasPersonas();
    
    PersonaDTO obtenerPersonaPorId(Long id);
    
    void crearPersona(PersonaDTO persona);
    
    void editarPersona(Long personaId, PersonaDTO persona);
    
    void borrarPersona(Long personaId);

    void desasociarPersonaDeTareas(Long personaId);
    
}
