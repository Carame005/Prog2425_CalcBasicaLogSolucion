package es.prog2425.calclog.utils


import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import kotlin.contracts.ConditionalEffect

interface IUtilsBD {
    fun obtenerConexion(): Connection?
    fun cerrarConexion(conexion : Connection?)
    fun crearStatement(conexion: Connection?) : Statement
    fun ejecutarQuery(statement: java.sql.Statement, query : String) : ResultSet?
    fun actualizarQuery(statement: java.sql.Statement, query : String) : Int
    fun crearDataSource() : HikariDataSource?
    fun prepararConsulta(conexion: Connection?,query: String): PreparedStatement?
    fun obtenerConexionDataSource(datasource : HikariDataSource?) : Connection?
    fun ejecutarQueryPreparada(statement: PreparedStatement?) : ResultSet?
    fun actualizarQueryPreparada(statement: PreparedStatement?) : Int
    fun historialConexiones(dataSource: HikariDataSource?)
}