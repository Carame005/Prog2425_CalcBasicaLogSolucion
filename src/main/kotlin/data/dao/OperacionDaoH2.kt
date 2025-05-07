package es.prog2425.calclog.data.dao

import es.prog2425.calclog.app.InfoCalcException
import es.prog2425.calclog.model.Operacion
import es.prog2425.calclog.model.Operador
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import es.prog2425.calclog.data.bd.UtilsBD
import kotlin.use


class OperacionDaoH2 : IOperacionDaoH2 {

    override fun obtenerTodos(): List<Operacion> {
        val operacion = mutableListOf<Operacion>()
        try {
            UtilsBD.obtenerConexion().use { con ->
                con?.createStatement().use { stmt ->
                    val sql = "Select * from usuario"
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
    override fun insertar(primerNumero : Double, operador: Operador, segundoNumero : Double, resultado : Double) {
        crearTabla()
        UtilsBD.obtenerConexion().use { con->
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

    override fun crearTabla() {
        try{
            UtilsBD.obtenerConexion().use { con ->
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