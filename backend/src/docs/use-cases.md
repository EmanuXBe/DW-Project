# Casos de uso

En este documento se incluyen los casos de uso del proyecto de Desarrollo Web. Cada caso incluye: actores, precondición, disparador, flujo principal, flujos alternativos/excepciones, postcondición, entidades implicadas y criterios de aceptación sugeridos.

---

## Índice

* [UC01 — CRUD Modelos de Barco](#uc01---crud-modelos-de-barco)
* [UC02 — CRUD Jugadores](#uc02---crud-jugadores)
* [UC03 — CRUD Barcos](#uc03---crud-barcos)
* [UC04 — Crear / Editar Mapa](#uc04---crear--editar-mapa)
* [UC05 — DbInitializer (batch)](#uc05---dbinitializer-batch)
* [UC06 — Crear Partida / Sala de Carrera](#uc06---crear-partida--sala-de-carrera)
* [UC07 — Unirse a Carrera](#uc07---unirse-a-carrera)
* [UC08 — Iniciar Carrera](#uc08---iniciar-carrera)
* [UC09 — Tomar Turno / Decidir Cambio de Velocidad](#uc09---tomar-turno--decidir-cambio-de-velocidad)
* [UC10 — Resolver Movimiento / Simular Turno](#uc10---resolver-movimiento--simular-turno)
* [UC11 — Detectar Colisión / Destrucción](#uc11---detectar-colisi%C3%B3n--destrucci%C3%B3n)
* [UC12 — Llegar a Meta y Determinar Ganador](#uc12---llegar-a-meta-y-determinar-ganador)
* [UC13 — Ver Estado de Partida / Espectador](#uc13---ver-estado-de-partida--espectador)
* [UC14 — Autenticación y Gestión de Roles](#uc14---autenticaci%C3%B3n-y-gesti%C3%B3n-de-roles)
* [UC15 — Pruebas Automatizadas y Exportar Resultados](#uc15---pruebas-automatizadas-y-exportar-resultados)

---

## UC01 — CRUD Modelos de Barco

**Actores:** Administrador

**Precondición:** Administrador autenticado (en entregas con auth). Para la 1ª entrega puede implementarse sin autenticación en modo local.

**Disparador:** Administrador selecciona “Modelos” → acción CRUD (Crear/Leer/Actualizar/Eliminar).

**Flujo principal:**

1. Abrir pantalla/listado de Modelos de Barco.
2. Seleccionar acción (Nuevo / Editar / Eliminar / Ver).
3. Completar formulario (nombre, color, atributos opcionales) y guardar.
4. Sistema valida los datos y persiste la entidad `ModeloBarco`.
5. Sistema muestra confirmación y lista actualizada.

**Flujos alternativos / excepciones:**

* Nombre duplicado → mostrar error de validación.
* Intento de borrar un modelo enlazado a barcos existentes → pedir confirmación o impedir borrado.

**Postcondición:** Modelo creado/actualizado/borrado en la BD.

**Entidades implicadas:** `ModeloBarco` (id, nombre, color), relación con `Barco`.

**Criterios de aceptación sugeridos:**

* Endpoint `POST /api/modelos` devuelve 201 y el objeto creado.
* Validación: nombre no vacío y único.
* UI: formulario con validaciones cliente/servidor.

---

## UC02 — CRUD Jugadores

**Actores:** Administrador

**Precondición:** BD disponible; en entregas con auth los jugadores deben poder autenticarse.

**Disparador:** Acceso a “Jugadores” → Crear/Editar/Eliminar/Listar.

**Flujo principal:**

1. Abrir listado de Jugadores.
2. Crear jugador (nombre, email, contacto, rol) o editar existente.
3. Validar y persistir en `Jugador`.

**Flujos alternativos:**

* Eliminación de jugador con barcos asociados → manejar reasignación o bloqueo.

**Postcondición:** Jugador registrado/modificado/eliminado.

**Entidades:** `Jugador` (id, nombre, email, rol, metadata).

**Criterios de aceptación:**

* `POST /api/jugadores` retorna 201 y objeto creado.
* Email único; validación de formato.

---

## UC03 — CRUD Barcos

**Actores:** Administrador

**Precondición:** Existen `ModeloBarco` y `Jugador`.

**Disparador:** Administrador gestiona Barcos.

**Flujo principal:**

1. Crear barco asignando modelo y jugador dueño.
2. Inicializar posición y velocidad.
3. Persistir `Barco` y mostrar en listado con filtros.

**Flujos alternativos:**

* Reglas de negocio: límite de barcos por jugador.

**Postcondición:** Barco disponible para asignación en partidas.

**Entidades:** `Barco` (id, modelo\_id, jugador\_id, posicion, velocidad, estado).

**Criterios de aceptación:**

* Endpoints CRUD con respuestas correctas y validaciones.

---

## UC04 — Crear / Editar Mapa

**Actores:** Administrador

**Precondición:** Editor de mapas disponible (visual o importación de fichero).

**Disparador:** Crear un nuevo mapa o editar uno existente.

**Flujo principal:**

1. Definir dimensiones (NxM) y nombre.
2. Marcar tipo de cada celda: Agua, Pared, Partida (P), Meta (M).
3. Validar: al menos una P y una M; opcionalmente comprobar alcanze (pathfinding).
4. Persistir `Mapa` y `Celda`.

**Flujos alternativos / excepciones:**

* Falta de P o M → error de validación.
* Pared que bloquea completamente la meta → advertencia.

**Postcondición:** Mapa jugable almacenado.

**Entidades:** `Mapa`, `Celda` (x, y, tipo).

**Criterios de aceptación:**

* `POST /api/mapas` crea mapa válido.
* UI: editor con rejilla y controles.

---

## UC05 — DbInitializer (Batch)

**Actores:** Sistema / Desarrollador

**Precondición:** Esquema de BD creado y accesible.

**Disparador:** Ejecución del inicializador (al arrancar en modo dev o tarea manual).

**Flujo principal:**

1. Crear un mapa jugable de ejemplo.
2. Crear 5 jugadores de prueba.
3. Crear 10 modelos de barco.
4. Crear 50 barcos (ej. 10 por jugador) y ubicarlos en celdas de partida.
5. Persistir y loguear resumen.

**Flujos alternativos:**

* Conflictos de claves → manejar con flags (sobrescribir/ignorar).

**Postcondición:** BD poblada con datos iniciales para pruebas.

**Entidades:** `Mapa`, `Jugador`, `ModeloBarco`, `Barco`.

**Criterios de aceptación:**

* Initializer idempotente o con flag para reset.
* Logs claros de operaciones realizadas.

---

## UC06 — Crear Partida / Sala de Carrera

**Actores:** Jugador (creador), Administrador, Sistema

**Precondición:** Mapa disponible; jugadores listos para unirse.

**Disparador:** Un jugador crea una nueva partida especificando mapa y parámetros.

**Flujo principal:**

1. Creador selecciona mapa y parámetros (max jugadores, turnos, reglas).
2. Sistema crea `Partida` en estado “En Espera”.
3. Jugadores pueden ver/solicitar unirse.
4. Barcos se asignan a posiciones P.

**Flujos alternativos:**

* Partida llena → denegar unión.
* Inicio automático si se completa el lobby.

**Postcondición:** Partida creada y lista para iniciar.

**Entidades:** `Partida`, `Mapa`, `Barco`, `Jugador`.

**Criterios de aceptación:**

* `POST /api/partidas` crea partida con estado correcto.
* Vista de sala con lista de participantes.

---

## UC07 — Unirse a Carrera

**Actores:** Jugador

**Precondición:** Partida en estado “En Espera”.

**Disparador:** Jugador pulsa “Unirse” en la sala de una partida.

**Flujo principal:**

1. Seleccionar partida.
2. Elegir o recibir asignación de barco.
3. Sistema registra al jugador en la `Partida` y posiciona el barco en P.
4. Confirmación en UI.

**Flujos alternativos:**

* Si no hay barcos libres → asignación automática o rechazo.

**Postcondición:** Jugador inscrito en partida.

**Entidades:** `Partida`, `Jugador`, `Barco`.

**Criterios de aceptación:**

* `POST /api/partidas/{id}/unirse` añade participante.

---

## UC08 — Iniciar Carrera

**Actores:** Creador de la partida / Sistema

**Precondición:** Partida con participantes y en estado “En Espera”.

**Disparador:** Creador pulsa “Iniciar” o se cumple condición automática.

**Flujo principal:**

1. Cambiar estado de `Partida` a “En Curso”.
2. Inicializar turno 1 y notificar jugadores (websocket/polling).
3. Velocidades iniciales configuradas.

**Flujos alternativos:**

* Jugador ausente al iniciar → política de timeout/default.

**Postcondición:** Partida en curso y primer turno activo.

**Entidades:** `Partida`, `Turno`, `Barco`.

**Criterios de aceptación:**

* Estado de partida actualizado.
* Notificaciones a clientes funcionando.

---

## UC09 — Tomar Turno / Decidir Cambio de Velocidad

**Actores:** Jugador

**Precondición:** Partida en curso y turno activo.

**Disparador:** Interfaz muestra controles para cambiar componentes de velocidad (vx, vy).

**Flujo principal:**

1. Jugador visualiza posición y velocidad actuales.
2. Elige delta para vx y vy (ej. -1, 0, +1).
3. Envía decisión al servidor.
4. Sistema valida y registra la acción para el turno.

**Flujos alternativos:**

* No respuesta → aplicar default (mantener velocidad).

**Postcondición:** Decisión registrada y lista para resolución.

**Entidades:** `AccionTurno`, `Barco`, `Turno`.

**Criterios de aceptación:**

* Endpoint para enviar acción acepta validaciones y retorna 200.

---

## UC10 — Resolver Movimiento / Simular Turno

**Actores:** Sistema (motor de juego)

**Precondición:** Decisiones de todos los jugadores registradas o tiempo expirado.

**Disparador:** Fin del periodo de decisiones de un turno.

**Flujo principal:**

1. Para cada barco calcular nueva velocidad `v' = v + delta`.
2. Calcular trayectoria discreta entre posición actual y destino (ej. algoritmo tipo Bresenham para celdas intermedias).
3. Iterar celdas de la trayectoria:

   * Si celda = Pared → marcar destrucción (UC11).
   * Si celda = Meta → marcar llegada (UC12).
   * Si celda = Agua → continuar.
4. Actualizar posición y velocidad final del barco si no fue destruido.
5. Registrar eventos (colisiones, llegadas) y notificar a clientes.

**Flujos alternativos / excepciones:**

* Varios barcos en la misma celda → regla de coexistencia o colisión según diseño.
* Salida del mapa → tratar como destrucción o bloqueo según regla.

**Postcondición:** Estado de la simulación actualizado; posible finalización de la partida.

**Entidades:** `Barco`, `Mapa`, `Celda`, `EventoTurno`.

**Criterios de aceptación:**

* Simulación reproduce trayectoria por celdas intermedias (no solo posición destino).
* Eventos de destrucción/llegada quedan registrados.

---

## UC11 — Detectar Colisión / Destrucción

**Actores:** Sistema

**Precondición:** Durante la resolución de turno se detecta una celda de tipo Pared o condición de destrucción.

**Disparador:** Encuentro de celda Pared o regla equivalente.

**Flujo principal:**

1. Marcar `Barco.estado = destruido`.
2. Retirar barco de simulación activa.
3. Registrar evento en el historial de la `Partida`.
4. Notificar al jugador afectado.

**Postcondición:** Barco fuera de carrera.

**Entidades:** `Barco`, `EventoTurno`.

**Criterios de aceptación:**

* Evento de destrucción almacenado y comunicado.

---

## UC12 — Llegar a Meta y Determinar Ganador

**Actores:** Sistema, Jugadores

**Precondición:** Un barco atraviesa la celda Meta durante la resolución de turno.

**Disparador:** Detección de celda M en la trayectoria.

**Flujo principal:**

1. Marcar `Barco.estado = llegado`.
2. Registrar número de turno y orden de llegada.
3. Si es el primer barco que llega → declarar ganador y finalizar `Partida` (según reglas).
4. Si varias llegadas en el mismo turno → aplicar regla de desempate (ej. menor distancia restante, orden de envío).
5. Guardar resultado en historial y notificar a participantes.

**Postcondición:** Partida finalizada y ganador registrado.

**Entidades:** `Partida`, `Barco`, `Resultado`.

**Criterios de aceptación:**

* Resultado persistido con orden de llegada y turnos.

---

## UC13 — Ver Estado de Partida / Espectador

**Actores:** Jugador (participante), Espectador, Admin

**Precondición:** Partida creada y en curso.

**Disparador:** Usuario abre la vista de la partida.

**Flujo principal:**

1. Mostrar mapa y posiciones actuales de barcos.
2. Mostrar turno actual, velocidades y logs de eventos recientes.
3. Actualizar en tiempo real (websocket o polling).

**Flujos alternativos:**

* Privacidad: si la partida es privada, denegar acceso a no participantes.

**Postcondición:** Usuario informado del estado en tiempo real.

**Entidades:** `Partida`, `Barco`, `EventoTurno`, `Mapa`.

**Criterios de aceptación:**

* Vista de espectador con actualización en tiempo real.

---

## UC14 — Autenticación y Gestión de Roles

**Actores:** Jugador, Administrador

**Precondición:** Usuarios existen en BD.

**Disparador:** Usuario intenta acceder a recursos protegidos.

**Flujo principal:**

1. Usuario ingresa credenciales (email/password) o inicia sesión vía OAuth (si aplica).
2. Sistema valida y devuelve token/sesión.
3. Determinar rol y habilitar acciones según permisos.

**Flujos alternativos:**

* Credenciales inválidas → error.
* Cuenta bloqueada → mensaje.

**Postcondición:** Sesión iniciada con permisos adecuados.

**Entidades:** `Jugador`, `TokenSesion`, `Rol`.

**Criterios de aceptación:**

* Endpoints protegidos retornan 401/403 según caso.

---

## UC15 — Pruebas Automatizadas y Exportar Resultados

**Actores:** Desarrollador / Sistema CI

**Precondición:** Servicios REST y UI implementados.

**Disparador:** Ejecución de pipeline de CI o petición manual de pruebas.

**Flujo principal:**

1. Ejecutar pruebas unitarias e integración.
2. Ejecutar prueba de sistema (por ejemplo: crear partida, simular carrera hasta finalización).
3. Generar reportes y artefactos de prueba.

**Flujos alternativos:**

* Fallos → registrar detalles y notificar equipos.

**Postcondición:** Reportes de pruebas y trazabilidad de resultados.

**Entidades:** Endpoints, suites de prueba, base de datos de prueba.

**Criterios de aceptación:**

* Pipeline devuelve artefactos legibles (logs, junit, coverage).

---

## Entidades clave (resumen)

* `Mapa` — id, nombre, dimensiones (N, M).
* `Celda` — mapa\_id, x, y, tipo ∈ {Agua, Pared, Partida(P), Meta(M)}.
* `Jugador` — id, nombre, email, rol.
* `ModeloBarco` — id, nombre, color.
* `Barco` — id, modelo\_id, jugador\_id, posicion(x,y), velocidad(vx,vy), estado.
* `Partida` — id, mapa\_id, estado, participantes, turno\_actual.
* `Turno` / `AccionTurno` — decisiones por barco en cada turno.
* `EventoTurno` / `Resultado` — logs y resultados finales.

---

## Mapeo a entregas sugerido

* **1ª entrega (MPA / CRUD / DBInitializer):** UC01, UC02, UC03, UC04 (editor mockup), UC05.
* **2ª entrega (SPA + REST + motor de simulación):** UC06, UC07, UC08, UC09, UC10, UC11, UC12, UC13.
* **3ª entrega (auth, roles, pruebas):** UC14, UC15 y cobertura de pruebas e integración.

---

## Recomendaciones rápidas para integración en el repo

* Guardar este archivo en `docs/use-cases.md` o `USE_CASES.md` en la raíz.
* Incluir un enlace desde `README.md` hacia este documento.
* Versionar cambios mediante PRs para mantener trazabilidad.

---

*Fin del documento.*
