package com.co.velaio.vlo_ms_tasks.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.velaio.vlo_ms_tasks.Service.TareasService;
import com.co.velaio.vlo_ms_tasks.dto.PersonaDTO;
import com.co.velaio.vlo_ms_tasks.dto.TareaDTO;

@CrossOrigin()
@RestController
@RequestMapping("/tareas")
public class TareasController {

    @Autowired
    private TareasService tareasService;

    @GetMapping
    public List<TareaDTO> listarTareas() {
        return tareasService.listarTareas();
    }
    
    @GetMapping("/estado/{estado}")
    public List<TareaDTO> obtenerTareas(@PathVariable String estado) {
        return tareasService.obtenerTareas(estado);
    }

    @GetMapping("/{id}")
    public TareaDTO obtenerTareaPorId(@PathVariable Long id) {
        return tareasService.obtenerTareaPorId(id);
    }

    @PostMapping
    public void crearTarea(@RequestBody TareaDTO tarea) {
        tareasService.crearTarea(tarea);
    }

    @PutMapping("/{id}")
    public void actualizarTarea(@PathVariable Long id, @RequestBody TareaDTO tarea) {
        tareasService.actualizarTarea(id, tarea);
    }

    @DeleteMapping("/{id}")
    public void borrarTarea(@PathVariable Long id) {
        tareasService.borrarTarea(id);
    }
    
    @GetMapping("/personas")
    public List<PersonaDTO> listarPersonas() {
        return tareasService.listarPersonas();
    }

    @GetMapping("/personas/{personaId}")
    public PersonaDTO obtenerPersonaPorId(@PathVariable Long personaId) {
        return tareasService.obtenerPersonaPorId(personaId);
    }

    @PostMapping("/personas")
    public void crearPersona(@RequestBody PersonaDTO persona) {
        tareasService.crearPersona(persona);
    }

    @PutMapping("/personas/{personaId}")
    public void editarPersona(@PathVariable Long personaId, @RequestBody PersonaDTO persona) {
        tareasService.editarPersona(personaId, persona);
    }

    @DeleteMapping("/personas/{personaId}")
    public void borrarPersona(@PathVariable Long personaId) {
        tareasService.borrarPersona(personaId);
    }
}
