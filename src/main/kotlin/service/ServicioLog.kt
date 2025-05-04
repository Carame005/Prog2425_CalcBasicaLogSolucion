package es.prog2425.calclog.service

import es.prog2425.calclog.data.IRepoBaseDatos
import es.prog2425.calclog.data.IRepoLog
import es.prog2425.calclog.model.Calculo
import java.sql.PreparedStatement
import java.sql.Statement

/**
 * Servicio que actúa como intermediario entre la lógica de aplicación y el repositorio de logs.
 * Encapsula la lógica de negocio relacionada con la gestión de registros de log.
 */
class ServicioLog(private val repositorio: IRepoLog, private val repoBD : IRepoBaseDatos) : IServicioLog {


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

    override fun crearTablas(statement: Statement, lista: List<String>) {
        repoBD.crearTablas(statement,lista)
    }

    override fun actualizarTablas(statement: Statement, lista: List<String>) {
        repoBD.actualizarTablas(statement,lista)
    }

    override fun realizarConsulta(statement: Statement, query: String) {
        repoBD.realizarConsulta(statement,query)
    }

    override fun realizarVariasConsultas(
        statement: Statement,
        lista: List<String>,
        pausa: Boolean
    ) {
        repoBD.realizarVariasConsultas(statement,lista,pausa)
    }

    override fun ejecutarConsultaPreparada(statement: PreparedStatement?){
        return repoBD.ejecutarConsultaPreparada(statement)
    }

    override fun actualizarConsultaPreparada(statement: PreparedStatement?) {
        repoBD.actualizarConsultaPreparada(statement)
    }

    override fun crearActualizarLogs(statement: Statement) {
        repoBD.crearActualizarLogs(statement)
    }
}