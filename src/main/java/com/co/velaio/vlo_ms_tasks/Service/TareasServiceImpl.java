package com.co.velaio.vlo_ms_tasks.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.velaio.vlo_ms_tasks.Persistence.TareasDAO;
import com.co.velaio.vlo_ms_tasks.dto.PersonaDTO;
import com.co.velaio.vlo_ms_tasks.dto.TareaDTO;

@Service
public class TareasServiceImpl implements TareasService {

    @Autowired
    private TareasDAO tareasDAO;

    @Override
    public List<TareaDTO> listarTareas() {
        return tareasDAO.obtenerTareas();
    }
    
    @Override
    public List<TareaDTO> obtenerTareas(String estado) {
        return tareasDAO.obtenerTareasPorEstado(estado);
    }

    @Override
    public TareaDTO obtenerTareaPorId(Long id) {
        return tareasDAO.obtenerTareaPorId(id);
    }

    @Override
    public void crearTarea(TareaDTO tarea) {
        tareasDAO.crearTarea(tarea);
    }

    @Override
    public void actualizarTarea(Long id, TareaDTO tarea) {
        tareasDAO.actualizarTarea(id, tarea);
    }

    @Override
    public void borrarTarea(Long id) {
        tareasDAO.borrarTarea(id);
    }
    
    @Override
    public List<PersonaDTO> listarPersonas() {
        return tareasDAO.listarTodasLasPersonas();
    }

    @Override
    public PersonaDTO obtenerPersonaPorId(Long personaId) {
        return tareasDAO.obtenerPersonaPorId(personaId);
    }

    @Override
    public void crearPersona(PersonaDTO persona) {
    	tareasDAO.crearPersona(persona);
    }

    @Override
    public void editarPersona(Long personaId, PersonaDTO persona) {
    	tareasDAO.editarPersona(personaId, persona);
    }

    @Override
    public void borrarPersona(Long personaId) {
    	
    	tareasDAO.desasociarPersonaDeTareas(personaId);
    	tareasDAO.borrarPersona(personaId);
    	
    }
}
