package es.prog2425.calclog.utils

import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class UtilsBD : IUtilsBD {
    // Definimos los parámetros de conexión
    val jdbcUrl = "jdbc:h2:file:./data/Calculadora"  // URL para la base de datos H2 en archivo
    val user = "sa"  // Nombre de usuario para la base de datos
    val password = ""  // Contraseña para la base de datos (vacía en este caso)

    /**
     * Establece una conexión con la base de datos H2 almacenada en el archivo.
     *
     * Este método intenta conectar con la base de datos H2 ubicada en la ruta ./data/tienda
     * utilizando las credenciales proporcionadas. Si la conexión es exitosa, imprime un mensaje
     * de éxito en la consola. Si ocurre un error, se captura y se imprime un mensaje de error.
     *
     * @throws SQLException Si hay un error en la conexión con la base de datos.
     * @throws Exception Si ocurre un error genérico durante el proceso de conexión.
     */
    override fun obtenerConexion(): Connection? {
        return try {
            val connection = DriverManager.getConnection(jdbcUrl, user, password)
            println("Conexión exitosa!")
            connection
        } catch (e: SQLException) {
            println("Error de conexión: ${e.message}")
            null
        } catch (e: Exception) {
            println("Se ha producido un error: ${e.message}")
            null
        }
    }

    /**
     * Funcion try catch que intenta cerrar la conexión, si no tiene éxito lanza una
     * excepción
     *
     * @param conexion La conexion cómo objeto tipo Connection que debe estar abierta
     *
     * @throws SQLException Si hay un error al cerrar la conexion de la base de datos.
     * @throws Exception Si ocurre un error genérico durante el proceso de conexión.
     */
    override fun cerrarConexion(conexion: Connection?) {
        if (conexion == null) {
            println("La conexión es nula. No hay nada que cerrar.")
            return
        }
        try {
            if (!conexion.isClosed) {
                conexion.close()
                println("Conexión cerrada con éxito!")
            } else {
                println("La conexión ya estaba cerrada.")
            }
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        } catch (e: Exception) {
            println("Se ha producido un error: ${e.message}")
        }
    }


    /**
     * Función que sirve para crear un objeto statement.
     *
     * @param conexion La conexión usada para crear el statement, esta debe estar activa
     *
     * @return java.sql.Statement Retorna un objeto del tipo statement
     */
    override fun crearStatement(conexion: Connection?): Statement {
        requireNotNull(conexion) { "La conexión no puede ser nula al crear el Statement." }
        return conexion.createStatement()
    }


    /**
     * Función que sirve para ejecutar una query.
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param query La query que se desea ejecutar que es de tipo String
     *
     * @throws SQLException Si al ejecutar la query falla
     * @throws Exception Si ocurre otro error
     *
     * @return ResultSet Retorna un objeto del tipo ResultSet
     */
    override fun ejecutarQuery(statement: java.sql.Statement, query: String): ResultSet? {
        return try {
            statement.executeQuery(query)
        } catch (e: SQLException) {
            println("Error al ejecutar la query: ${e.message}")
            null
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
            null
        }
    }

    /**
     * Función que sirve para actualizar una query.
     *
     * @param statement El objeto statement que debe haber sido creado previamente
     * @param query La query que se desea ejecutar que es de tipo String
     *
     * @throws SQLException Si al actualizar la query falla
     * @throws Exception Si ocurre otro error
     *
     * @return Int Retorna un entero que usualmente sirve para señalar cuantos cambios ham sido hechos
     */
    override fun actualizarQuery(statement: java.sql.Statement, query: String): Int {
        return try {
            statement.executeUpdate(query)
        } catch (e: SQLException) {
            println("Error al ejecutar la query: ${e.message}")
            0
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
            0
        }
    }

    /**
     * Función que crea una DataSource y la devuelve como un objeto
     *
     * @throws SQLException Si hay un fallo al crear el DataSource
     * @throws Exception Si ocurre otro error
     *
     * @return HikariDataSource? Retorna un objeto DataSource o nulo
     */
    override fun crearDataSource() : HikariDataSource? {
        return try{
            val datasource = HikariDataSource()
            datasource.jdbcUrl = jdbcUrl
            datasource.username = user
            datasource.password = password
            datasource.maximumPoolSize = 10
            println("DataSource creada!! Tienes un máximo de ${datasource.maximumPoolSize} conexiones.")
            return datasource
        }catch (e: SQLException){
            println("Error al crear la fuente de datos ${e.message}")
            null
        }catch (e: Exception){
            println("Ha ocurrido un error inesperado ${e.message}")
            null
        }
    }

    /**
     * Función que sirve para preparar una consulta
     *
     * @param conexion El objeto conexion que debe haber sido creado previamente
     * @param query La query que se desea preparar que es de tipo String
     *
     * @throws SQLException Si al preparar la consulta falla
     * @throws Exception Si ocurre otro error
     *
     * @return PreparedStatement? Retorna un objeto de tipo PreparedStatement o un nulo
     */
    override fun prepararConsulta(conexion: Connection?, query: String): PreparedStatement? {
        return try {
            conexion?.prepareStatement(query)
        } catch (e: SQLException) {
            println("Error al preparar el statement: ${e.message}")
            null
        } catch (e: Exception) {
            println("Ha ocurrido un error inesperado: ${e.message}")
            null
        }
    }

    /**
     * Establece una conexión con el dataSource proporcionado.
     *
     * @param datasource Objeto tipo DataSource que proporciona la conexión
     *
     * @throws SQLException Si hay un error al obtener la conexión.
     * @throws Exception Si ocurre un error genérico durante el proceso de conexión.
     */
    override fun obtenerConexionDataSource(datasource: HikariDataSource?): Connection? {
        return try {
            val connection = datasource?.connection
            println("Conexión exitosa!")
            connection
        } catch (e: SQLException) {
            println("Error de conexión: ${e.message}")
            null
        } catch (e: Exception) {
            println("Se ha producido un error: ${e.message}")
            null
        }
    }

    /**
     * Función que sirve para ejecutar una query ya preparada.
     *
     * @param statement El objeto PreparedStatement que debe haber sido creado previamente
     *
     * @return ResultSet? Retorna un objeto del tipo ResultSet o nulo.
     */
    override fun ejecutarQueryPreparada(statement: PreparedStatement?): ResultSet? {
        return statement?.executeQuery()
    }

    /**
     * Función que sirve para actualizar una query preparada.
     *
     * @param statement El objeto PreparedStatement que debe haber sido creado previamente
     *
     * @return Int Retorna un entero que usualmente sirve para señalar cuantos cambios ham sido hechos
     */
    override fun actualizarQueryPreparada(statement: PreparedStatement?): Int {
        if (statement == null) {
            return 0
        }
        return statement.executeUpdate()
    }

    /**
     * Funcion simple que muestra el historial de conexiones de una dataSource tipo HikariCP
     *
     * @param dataSource La dataSource cuyos datos se desea ver
     */
    override fun historialConexiones(dataSource : HikariDataSource?) {
        val poolMXBean = dataSource?.hikariPoolMXBean
        println("Conexiones activas: ${poolMXBean?.activeConnections}")
        println("Conexiones en espera: ${poolMXBean?.threadsAwaitingConnection}")
        println("Conexiones totales: ${poolMXBean?.totalConnections}")

    }

}