package com.co.velaio.vlo_ms_tasks.Persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.co.velaio.vlo_ms_tasks.dto.PersonaDTO;
import com.co.velaio.vlo_ms_tasks.dto.TareaDTO;

@Repository
public class TareasDAOImpl implements TareasDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapper para convertir los resultados de la base de datos en objetos TareaDTO
    private final RowMapper<TareaDTO> tareaMapper = new RowMapper<TareaDTO>() {
        @Override
        public TareaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TareaDTO tarea = new TareaDTO();
            tarea.setId(rs.getLong("id")); // Asegúrate de tener el ID en TareaDTO
            tarea.setNombreTarea(rs.getString("nombre_tarea"));
            tarea.setEstado(rs.getString("estado"));
            tarea.setFechaLimite(rs.getDate("fecha_limite"));
            // Asignar personas asociadas después de obtener la tarea
            tarea.setPersonasAsociadas(obtenerPersonasPorTarea(tarea.getId()));
            return tarea;
        }
    };

    // Mapper para las personas asociadas a las tareas
    private final RowMapper<PersonaDTO> personaMapper = new RowMapper<PersonaDTO>() {
        @Override
        public PersonaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PersonaDTO persona = new PersonaDTO();
            persona.setId(rs.getLong("persona_id")); // Asegúrate de tener el ID en PersonaDTO
            persona.setNombreCompleto(rs.getString("nombre_completo"));
            persona.setEdad(rs.getLong("edad"));
            persona.setHabilidades(obtenerHabilidadesPorPersona(persona.getId()));
            return persona;
        }
    };

    // Obtener tareas
    @Override
    public List<TareaDTO> obtenerTareas() {
        String sql = "SELECT * FROM tareas WHERE borrado_logico = FALSE";
        return jdbcTemplate.query(sql, tareaMapper);
    }

    // Obtener tareas filtradas por estado
    @Override
    public List<TareaDTO> obtenerTareasPorEstado(String estado) {
        String sql = "SELECT * FROM tareas WHERE estado = ? AND borrado_logico = FALSE";
        return jdbcTemplate.query(sql, tareaMapper, estado);
    }

    // Obtener tarea por ID
    @Override
    public TareaDTO obtenerTareaPorId(Long id) {
        String sql = "SELECT * FROM tareas WHERE id = ? AND borrado_logico = FALSE";
        return jdbcTemplate.queryForObject(sql, tareaMapper, id);
    }

    // Crear una nueva tarea
    @Override
    public void crearTarea(TareaDTO tarea) {
        // Generar un ID único para la tarea
        Long tareaId = (UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)  % 1000000; // Generar un ID único

        // Insertar la nueva tarea con el ID generado
        String sql = "INSERT INTO tareas (id, estado, nombre_tarea, fecha_limite, borrado_logico) VALUES (?, ?, ?, ?, FALSE)";
        jdbcTemplate.update(sql, tareaId, tarea.getEstado(), tarea.getNombreTarea(), tarea.getFechaLimite());

        // Insertar las personas asociadas a la tarea
        insertarPersonasAsociadas(tareaId, tarea.getPersonasAsociadas());
    }

    // Actualizar una tarea existente
    @Override
    public void actualizarTarea(Long id, TareaDTO tarea) {
        String sql = "UPDATE tareas SET estado = ?, nombre_tarea = ?, fecha_limite = ? WHERE id = ? AND borrado_logico = FALSE";
        jdbcTemplate.update(sql, tarea.getEstado(), tarea.getNombreTarea(), tarea.getFechaLimite(), id);

        // Actualizar personas asociadas
        eliminarPersonasAsociadas(id);
        insertarPersonasAsociadas(id, tarea.getPersonasAsociadas());
    }

    // Borrado lógico de una tarea
    @Override
    public void borrarTarea(Long id) {
        String sql = "UPDATE tareas SET borrado_logico = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Obtener personas asociadas a una tarea
    private List<PersonaDTO> obtenerPersonasPorTarea(Long tareaId) {
        String sql = "SELECT p.id AS persona_id, p.nombre_completo, p.edad " +
                     "FROM personas p " +
                     "JOIN tareas_personas tp ON p.id = tp.persona_id " +
                     "WHERE tp.tarea_id = ?";
        return jdbcTemplate.query(sql, personaMapper, tareaId);
    }

    // Obtener habilidades de una persona
    private List<String> obtenerHabilidadesPorPersona(Long personaId) {
        String sql = "SELECT h.nombre_habilidad " +
                     "FROM habilidades h " +
                     "JOIN personas_habilidades ph ON h.id = ph.habilidad_id " +
                     "WHERE ph.persona_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, personaId);
    }

    // Insertar personas asociadas a una tarea
    private void insertarPersonasAsociadas(Long tareaId, List<PersonaDTO> personas) {
        if (personas != null) {
            for (PersonaDTO persona : personas) {

                Long personaId = persona.getId();

                // Insertar relación tarea-persona
                String sqlRelacion = "INSERT INTO tareas_personas (tarea_id, persona_id) VALUES (?, ?) ";
                jdbcTemplate.update(sqlRelacion, tareaId, personaId);
            }
        }
    }

    // Insertar habilidades de una persona
    private void insertarHabilidadesPersona(Long personaId, List<String> habilidades) {
        if (habilidades != null) {
            for (String habilidad : habilidades) {
                // Insertar habilidad si no existe
                String sqlHabilidad = "INSERT INTO habilidades (nombre_habilidad) VALUES (?) ";
                jdbcTemplate.update(sqlHabilidad, habilidad);

                // Obtener el ID de la habilidad
                Long habilidadId = jdbcTemplate.queryForObject("SELECT id FROM habilidades WHERE nombre_habilidad = ?", Long.class, habilidad);

                // Insertar relación persona-habilidad
                String sqlRelacion = "INSERT INTO personas_habilidades (persona_id, habilidad_id) VALUES (?, ?) ";
                jdbcTemplate.update(sqlRelacion, personaId, habilidadId);
            }
        }
    }

    // Eliminar personas asociadas a una tarea
    private void eliminarPersonasAsociadas(Long tareaId) {
        String sql = "DELETE FROM tareas_personas WHERE tarea_id = ?";
        jdbcTemplate.update(sql, tareaId);
    }
    
    @Override
    public List<PersonaDTO> listarTodasLasPersonas() {
        String sql = "SELECT id AS persona_id, nombre_completo, edad FROM personas";
        return jdbcTemplate.query(sql, personaMapper);
    } 
    
    @Override
    public PersonaDTO obtenerPersonaPorId(Long id) {
        String sql = "SELECT id AS persona_id, nombre_completo, edad FROM personas WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, personaMapper, id);
    }
    
    // Método para crear una persona
    @Override
    public void crearPersona(PersonaDTO persona) {
        Long personaId = (UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)  % 1000000; // Generar un ID único

        // Insertar la persona en la base de datos
        String sql = "INSERT INTO personas (id, nombre_completo, edad) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, personaId, persona.getNombreCompleto(), persona.getEdad());

        // Insertar las habilidades de la persona, si existen
        insertarHabilidadesPersona(personaId, persona.getHabilidades());
    }

    // Método para editar una persona existente
    @Override
    public void editarPersona(Long personaId, PersonaDTO persona) {
        // Actualizar el nombre de la persona
        String sql = "UPDATE personas SET nombre_completo = ? WHERE id = ?";
        jdbcTemplate.update(sql, persona.getNombreCompleto(), personaId);

        // Actualizar las habilidades asociadas a la persona
        actualizarHabilidadesPersona(personaId, persona.getHabilidades());
    }
    
    // Método para actualizar las habilidades de una persona
    private void actualizarHabilidadesPersona(Long personaId, List<String> habilidades) {
        // Eliminar las habilidades actuales de la persona
        String sqlEliminar = "DELETE FROM personas_habilidades WHERE persona_id = ?";
        jdbcTemplate.update(sqlEliminar, personaId);

        // Insertar las nuevas habilidades
        insertarHabilidadesPersona(personaId, habilidades);
    }
    
    @Override
    public void borrarPersona(Long personaId) {
    	String sqlHabilidades = "DELETE FROM personas_habilidades WHERE persona_id = ?";
        jdbcTemplate.update(sqlHabilidades, personaId);
        
        String sql = "DELETE FROM personas WHERE id = ?";
        jdbcTemplate.update(sql, personaId);
    }
    
    @Override
    public void desasociarPersonaDeTareas(Long personaId) {
        String sql = "DELETE FROM tareas_personas WHERE persona_id = ?";
        jdbcTemplate.update(sql, personaId);
    }
}
