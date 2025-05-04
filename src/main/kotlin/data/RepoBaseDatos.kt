package es.prog2425.calclog.data

import es.prog2425.calclog.utils.IUtilsBD
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RepoBaseDatos(private val baseDatos: IUtilsBD) : IRepoBaseDatos {
    var contador = 1

    /**
     * Función que sirve para crear tablas y muestra por pantalla si se ha cambiado o no
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param lista Una lista de String en la que estan almacenadas las querys de las tablas
     */
    override fun crearTablas(statement: Statement, lista: List<String>) {
        lista.forEach {
            baseDatos.actualizarQuery(statement, it)
            println("Tabla $contador creada!")
            contador+=1
        }
    }

    /**
     * Función que sirve para actualizar las tablas y muestra por pantalla un aviso
     * de que se ha cambiado
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param lista La lista que contiene las actualizaciones de las tablas
     */
    override fun actualizarTablas(statement: Statement, lista: List<String>) {
        lista.forEach {
            val resultado = baseDatos.actualizarQuery(statement, it)
            println("Se han actualizado $resultado columnas")
        }
    }

    /**
     * Función que realiza una consulta y muestra por pantalla lo que devuelve.
     * Esta al ejecutarse saca los metadatos del objeto ResulSet y los une en forma
     * de columna.
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param query La consulta que se busca recrear
     */
    override fun realizarConsulta(statement: Statement, query: String) {
        val rs = baseDatos.ejecutarQuery(statement, query)
        if (rs == null){
            println("El ResultSet es nulo")
            return
        }
        val cols = rs.metaData.columnCount

        while (rs.next()) {
            val fila = (1..cols).joinToString(" | ") { rs.getString(it) }
            println(fila)
        }
    }

    /**
     * Función que hace uso de realizarConsulta, es basicamente lo mismo pero permitiendo realizar
     * varias consultas seguidas.
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param lista La lista con las consultas que se buscan
     */
    override fun realizarVariasConsultas(statement: Statement, lista: List<String>, pausa : Boolean) {
        lista.forEachIndexed {i, consulta ->
            println("Consulta ${i + 1}")
            realizarConsulta(statement,consulta)
            println()
            if (pausa == true){
                readln()
            }
        }
    }

    /**
     * Función que realiza una consulta ya preparada y muestra por pantalla lo que devuelve.
     * Esta al ejecutarse saca los metadatos del objeto ResulSet y los une en forma
     * de columna.
     *
     * @param statement El objeto PreparedStatement que debe haber sido creado previamente
     *
     * @throws SQLException Lanza una excepción si la consulta falla
     * @throws Exception Lanza una excepcion generica
     */
    override fun ejecutarConsultaPreparada(statement: PreparedStatement?) {
        try {
            val rs = baseDatos.ejecutarQueryPreparada(statement)
            if (rs == null) {
                println("El ResultSet es nulo")
                return
            }
            val cols = rs.metaData.columnCount

            while (rs.next()) {
                val fila = (1..cols).joinToString(" | ") { rs.getString(it) }
                println(fila)
            }
        } catch (e: SQLException) {
            println("Error al ejecutar la consulta preparada: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }

    /**
     * Función que sirve para actualizar las tablas y muestra por pantalla un aviso
     * de que se ha cambiado
     *
     * @param statement El objeto PreparedStatement que debe haber sido creado previamente
     *
     * @throws SQLException Lanza una excepcion si la actualizacion de la consulta falla
     * @throws Exception Lanza una excepcion generica
     */
    override fun actualizarConsultaPreparada(statement: PreparedStatement?) {
        try {
            if (statement == null) {
                println("Statement nulo. No se puede ejecutar la actualización.")
                return
            }
            val filasAfectadas = baseDatos.actualizarQueryPreparada(statement)
            println("Se han actualizado $filasAfectadas columnas")
        } catch (e: SQLException) {
            println("Error al ejecutar la actualización preparada: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }

    override fun crearActualizarLogs(statement: Statement) {
        crearTablas(statement, listOf(
            """
        CREATE TABLE IF NOT EXISTS logs (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(255),
            fecha TIMESTAMP
        )
        """
        ))

        val fechalog = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val nombrelog = "log${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}.txt"

        actualizarTablas(statement, listOf(
            """
        INSERT INTO logs (nombre, fecha)
        VALUES ('$nombrelog', '$fechalog')
        """
        ))
    }


}