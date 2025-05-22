package es.prog2425.calclog.model

/**
 * Data class que representa un error registrado en la aplicación.
 *
 * Esta clase modela una entrada en la tabla de errores, registrando información
 * relevante como la fecha en que ocurrió y el mensaje del error.
 *
 * @property fecha Fecha en la que se produjo el error, en formato String (puede ser ISO 8601, por ejemplo).
 * @property mensaje Descripción detallada del error ocurrido.
 */
data class Error(val fecha : String, val mensaje : String) {
}