package es.prog2425.calclog.data.dao

import es.prog2425.calclog.app.InfoCalcException
import es.prog2425.calclog.model.Operacion
import es.prog2425.calclog.model.Operador
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.sql.DataSource
import kotlin.use


class OperacionDaoH2(private val dataSource: DataSource) : IOperacionDaoH2 {

    /**
     * Devuelve una lista de Operaciones
     */

    override fun obtenerTodos(): List<Operacion> {
        val operacion = mutableListOf<Operacion>()
        try {
            dataSource.connection.use  { con ->
                con?.createStatement().use { stmt ->
                    val sql = "Select * from operaciones"
                    stmt?.executeQuery(sql).use { rs ->
                        if (rs == null){
                            return emptyList()
                        }
                        while (rs.next()){
                            val primerNumero = rs.getDouble("primerNumero")
                            val simbolo = rs.getString("operador").first()
                            val operador = Operador.getOperador(simbolo) ?: throw InfoCalcException("El operador no es válido!")
                            val segundoNumero = rs.getDouble("segundoNumero")
                            val resultado = rs.getDouble("resultado")
                            operacion.add(Operacion(primerNumero,operador,segundoNumero,resultado))
                        }
                    }
                }
            }
            operacion
        } catch (e: NoSuchElementException){
           throw IllegalStateException("ERROR", e)
        } catch (e: SQLException){
            throw IllegalStateException("ERROR", e)
        }
        return operacion
    }

    /**
     * Función que actualiza la tabla Operaciones cuando se usa la calculadora
     */

    override fun insertar(primerNumero : Double, operador: Operador, segundoNumero : Double, resultado : Double) {
        crearTabla()
        dataSource.connection.use  { con->
            con?.createStatement().use { stm ->
                val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val sql = """
                    INSERT INTO OPERACIONES (fecha,primerNumero, operador, segundoNumero, resultado)
                    VALUES('$fecha','$primerNumero','$operador','$segundoNumero','$resultado')
                    """
                stm?.executeUpdate(sql)
            }
        }
    }

    /**
     * Función que muestra por pantalla la tabla Operaciones
     */

    override fun realizarConsulta() {
        try{
            dataSource.connection.use  { con ->
                con?.createStatement().use { stm ->
                    stm?.executeQuery("""
                    Select * from operaciones
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

    /**
     * Funcion que sirve para crear la tabla operaciones si esta no existe
     */

    override fun crearTabla() {
        try{
            dataSource.connection.use  { con ->
                con?.createStatement().use { stm ->
                    val sql = """
                    CREATE TABLE IF NOT EXISTS OPERACIONES (
                        fecha TIMESTAMP,
                        primerNumero DOUBLE,
                        operador VARCHAR,
                        segundoNumero DOUBLE,
                        resultado DOUBLE
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
}