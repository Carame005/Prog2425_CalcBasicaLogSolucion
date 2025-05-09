package es.prog2425.calclog.service

import es.prog2425.calclog.data.IRepoLog
import es.prog2425.calclog.data.dao.IOperacionDaoH2
import es.prog2425.calclog.model.Calculo
import es.prog2425.calclog.model.Operacion
import es.prog2425.calclog.model.Operador
import java.sql.PreparedStatement
import java.sql.Statement

/**
 * Servicio que actúa como intermediario entre la lógica de aplicación y el repositorio de logs.
 * Encapsula la lógica de negocio relacionada con la gestión de registros de log.
 */
class ServicioLog(private val repositorio: IRepoLog, private val baseDatos : IOperacionDaoH2) : IServicioLog {


    /**
     * Registra un mensaje de texto en el archivo de log.
     */
    override fun registrarEntradaLog(mensaje: String) {
        repositorio.registrarEntrada(mensaje)
    }

    /**
     * Registra un cálculo formateado en el archivo de log.
     */
    override fun registrarEntradaLog(calculo: Calculo) {
        repositorio.registrarEntrada(calculo)
    }

    /**
     * Recupera el contenido del archivo de log más reciente.
     */
    override fun getInfoUltimoLog(): List<String> {
        return repositorio.getContenidoUltimoLog()
    }

    /**
     * Crea un nuevo archivo de log vacío y lo establece como actual.
     */
    override fun crearNuevoLog() {
        repositorio.crearNuevoLog()
    }

    /**
     * Crea el directorio de logs si no existe y actualiza la ruta en el repositorio.
     */
    override fun crearRutaLog(ruta: String): Boolean {
        repositorio.ruta = ruta
        return repositorio.crearRutaLog()
    }

    override fun insertar(
        primerNumero: Double,
        operador: Operador,
        segundoNumero: Double,
        resultado: Double
    ) {
        baseDatos.insertar(primerNumero,operador,segundoNumero,resultado)
    }

    override fun crearTabla() {
        baseDatos.crearTabla()
    }

    override fun obtenerOperaciones(): List<Operacion> {
        return baseDatos.obtenerTodos()
    }

    override fun realizarConsulta() {
        baseDatos.realizarConsulta()
    }
}