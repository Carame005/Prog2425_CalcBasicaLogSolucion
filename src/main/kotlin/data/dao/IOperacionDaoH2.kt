package es.prog2425.calclog.data.dao

import es.prog2425.calclog.model.Operacion
import es.prog2425.calclog.model.Operador
import java.sql.Statement


interface IOperacionDaoH2 {
    fun obtenerTodos() : List<Operacion>
    fun insertar(primerNumero : Double, operador: Operador, segundoNumero : Double, resultado : Double)
    fun crearTabla()
    fun realizarConsulta()
}