package es.prog2425.calclog.data

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

interface IRepoBaseDatos {
    fun crearTablas(statement: Statement, lista : List<String>)
    fun actualizarTablas(statement: Statement, lista: List<String>)
    fun realizarConsulta(statement: Statement,query: String)
    fun realizarVariasConsultas(statement: Statement, lista: List<String>, pausa : Boolean)
    fun ejecutarConsultaPreparada(statement: PreparedStatement?)
    fun actualizarConsultaPreparada(statement: PreparedStatement?)
    fun crearActualizarLogs(statement: Statement)
}