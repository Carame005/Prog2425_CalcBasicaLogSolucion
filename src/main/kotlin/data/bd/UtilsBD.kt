package es.prog2425.calclog.data.bd

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.h2.jdbcx.JdbcDataSource
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

object UtilsBD{
    // Definimos los parámetros de conexión
    val JDBCURL = "jdbc:h2:file:./data/Calculadora"  // URL para la base de datos H2 en archivo
    val USER = "sa"  // Nombre de usuario para la base de datos
    val PASSWORD = ""  // Contraseña para la base de datos (vacía en este caso)

    fun getDataSource(mode: Mode = Mode.HIKARI): DataSource {
        return when (mode) {
            Mode.HIKARI -> {
                val config = HikariConfig().apply {
                    jdbcUrl = JDBCURL
                    username = USER
                    password = PASSWORD
                    driverClassName = "org.h2.Driver"
                    maximumPoolSize = 10
                }
                HikariDataSource(config)
            }
            Mode.SIMPLE -> {
                JdbcDataSource().apply {
                    setURL(JDBCURL)
                    user = USER
                    password = PASSWORD
                }
            }
        }
    }
}