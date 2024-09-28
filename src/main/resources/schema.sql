CREATE TABLE tareas (
    id BIGINT PRIMARY KEY,
    estado VARCHAR(50),
    nombre_tarea VARCHAR(255),
    fecha_limite DATE,
    borrado_logico BOOLEAN DEFAULT FALSE
);

CREATE TABLE personas (
    id BIGINT PRIMARY KEY,
    edad BIGINT,
    nombre_completo VARCHAR(255)
);

CREATE TABLE tareas_personas (
    tarea_id BIGINT,
    persona_id BIGINT,
    PRIMARY KEY (tarea_id, persona_id),
    FOREIGN KEY (tarea_id) REFERENCES tareas(id),
    FOREIGN KEY (persona_id) REFERENCES personas(id)
);

CREATE TABLE habilidades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_habilidad VARCHAR(100)
);

CREATE TABLE personas_habilidades (
    persona_id BIGINT,
    habilidad_id BIGINT,
    PRIMARY KEY (persona_id, habilidad_id),
    FOREIGN KEY (persona_id) REFERENCES personas(id),
    FOREIGN KEY (habilidad_id) REFERENCES habilidades(id)
);
/* 
INSERT INTO tareas (id, estado, nombre_tarea, fecha_limite, borrado_logico)
VALUES
(1, 'Pendiente', 'Desarrollar API', '2024-10-01', FALSE),
(2, 'En progreso', 'Diseñar base de datos', '2024-10-15', FALSE),
(3, 'Completada', 'Escribir documentación', '2024-09-30', FALSE),
(4, 'Pendiente', 'Revisar código', '2024-10-05', FALSE);

INSERT INTO personas (id, nombre_completo, edad)
VALUES
(1, 'Carlos Pérez', 20),
(2, 'María García', 23),
(3, 'Luis Fernández', 29),
(4, 'Ana Martínez', 19);

INSERT INTO tareas_personas (tarea_id, persona_id)
VALUES
(1, 1), -- Carlos Pérez está asignado a 'Desarrollar API'
(2, 2), -- María García está asignada a 'Diseñar base de datos'
(3, 3), -- Luis Fernández está asignado a 'Escribir documentación'
(4, 4), -- Ana Martínez está asignada a 'Revisar código'
(1, 3), -- Luis Fernández también está asignado a 'Desarrollar API'
(2, 4); -- Ana Martínez también está asignada a 'Diseñar base de datos'

INSERT INTO habilidades (nombre_habilidad)
VALUES
('Programación en Java'),
('Diseño de Bases de Datos'),
('Documentación Técnica'),
('Revisión de Código');

INSERT INTO personas_habilidades (persona_id, habilidad_id)
VALUES
(1, 1), -- Carlos Pérez tiene la habilidad de 'Programación en Java'
(2, 2), -- María García tiene la habilidad de 'Diseño de Bases de Datos'
(3, 3), -- Luis Fernández tiene la habilidad de 'Documentación Técnica'
(4, 4), -- Ana Martínez tiene la habilidad de 'Revisión de Código'
(1, 4), -- Carlos Pérez también tiene la habilidad de 'Revisión de Código'
(2, 1); -- María García también tiene la habilidad de 'Programación en Java'
*/