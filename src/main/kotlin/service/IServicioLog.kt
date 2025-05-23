package es.prog2425.calclog.service

import es.prog2425.calclog.model.Calculo
import es.prog2425.calclog.model.Operacion
import es.prog2425.calclog.model.Operador

interface IServicioLog {
    fun registrarEntradaLog(mensaje: String)
    fun registrarEntradaLog(calculo: Calculo)
    fun getInfoUltimoLog(): List<String>
    fun crearNuevoLog()
    fun crearRutaLog(ruta: String): Boolean
    fun insertar(primerNumero : Double, operador: Operador, segundoNumero : Double, resultado : Double)
    fun obtenerOperaciones() : List<Operacion>
    fun realizarConsulta()
    fun insertarError(mensaje: String)
    fun consultarErrores()
}