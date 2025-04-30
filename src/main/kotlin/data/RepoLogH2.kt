package es.prog2425.calclog.data

import java.sql.DriverManager
import java.sql.SQLException



class RepoLogH2 : IRepoLogH2 {

    private val url = "jdbc:h2:./data/dbtest"
    private val user = "tu_usuario"
    private val password = "tu_contraseña"

    init {
        try {
            Class.forName("org.h2.Driver")
            DriverManager.getConnection(url, user, password).use { connection ->
                connection.createStatement().use { statement ->
                    statement.executeUpdate(
                        """
                        CREATE TABLE IF NOT EXISTS logs (
                            id IDENTITY PRIMARY KEY,
                            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            level VARCHAR(20),
                            message VARCHAR(255)
                        );
                        """.trimIndent()
                    )
                }
            }
        } catch (e: SQLException) {
            println("Error inicializando la base de datos: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Driver H2 no encontrado: ${e.message}")
        }
    }

    override fun guardarLog(level: String, message: String) {
        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val insertSql = "INSERT INTO logs (level, message) VALUES (?, ?)"
                connection.prepareStatement(insertSql).use { preparedStatement ->
                    preparedStatement.setString(1, level)
                    preparedStatement.setString(2, message)
                    preparedStatement.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Error guardando log: ${e.message}")
        }
    }

    override fun leerTodosLosLogs(): List<String> {
        val logs = mutableListOf<String>()
        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val querySql = "SELECT timestamp, level, message FROM logs"
                connection.createStatement().use { statement ->
                    val resultSet = statement.executeQuery(querySql)
                    while (resultSet.next()) {
                        val timestamp = resultSet.getTimestamp("timestamp")
                        val level = resultSet.getString("level")
                        val message = resultSet.getString("message")
                        logs.add("[$timestamp][$level] $message")
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error leyendo logs: ${e.message}")
        }
        return logs
    }
}

