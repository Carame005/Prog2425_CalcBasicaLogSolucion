package es.prog2425.calclog.model

/**
 * Data class que representa una operación matemática entre dos números.
 *
 * Esta clase modela una fila de la tabla de operaciones, incluyendo los operandos,
 * el operador utilizado y el resultado obtenido.
 *
 * @property primerNumero Primer número de la operación.
 * @property operador Operador utilizado en la operación (suma, resta, etc.).
 * @property segundoNumero Segundo número de la operación.
 * @property resultado Resultado calculado de la operación.
 */
data class Operacion(val primerNumero : Double, val operador : Operador, val segundoNumero : Double, val resultado : Double)
