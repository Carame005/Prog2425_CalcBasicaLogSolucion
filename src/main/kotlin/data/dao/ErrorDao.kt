package es.prog2425.calclog.data.dao

import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.sql.DataSource
import kotlin.use

class ErrorDao(private val dataSource: DataSource) : IErrorDao {

    /**
     * Funcion que sirve para guardar un error en el historial
     *
     * @param mensaje El mensaje de error que se guarda en el historial
     */
    override fun insertar(
        mensaje : String
    ) {
        crearTabla()
        dataSource.connection.use { con->
            con?.createStatement().use { stm ->
                val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val sql = """
                    INSERT INTO ERRORES (fecha,mensaje)
                    VALUES('$fecha','$mensaje')
                    """
                stm?.executeUpdate(sql)
            }
        }
    }

    /**
     * Funcion simple que crea la tabla errores si no existe
     */

    override fun crearTabla() {
        try{
            dataSource.connection.use  { con ->
                con?.createStatement().use { stm ->
                    val sql = """
                    CREATE TABLE IF NOT EXISTS ERRORES (
                        fecha TIMESTAMP,
                        MENSAJE VARCHAR(255)
                    )
                    """
                    stm?.executeUpdate(sql)
                }
            }
        }catch (e: SQLException){
            throw IllegalArgumentException("ERROR, los valores no están en un formato válido", e)
        }catch (e : Exception){
            throw Exception("OCURRIO UN ERROR INSESPERADO",e)
        }
    }

    /**
     * Funcion que devuelve la tabla entera a modo de historial
     */

    override fun realizarConsulta() {
        try{
            dataSource.connection.use { con ->
                con?.createStatement().use { stm ->
                    stm?.executeQuery("""
                    Select * from errores
                    """.trimIndent()).use { rs ->
                        if (rs == null) {
                            println("El ResultSet es nulo")
                            return
                        }
                        val cols = rs.metaData.columnCount

                        while (rs.next()) {
                            val fila = (1..cols).joinToString(" | ") { rs.getString(it) }
                            println(fila)
                        }
                        readln()
                    }
                }
            }

        }catch (e: SQLException){
            throw IllegalArgumentException("ERROR, los valores no están en un formato válido", e)
        }catch (e : Exception){
            throw Exception("OCURRIO UN ERROR INSESPERADO",e)
        }
    }
}