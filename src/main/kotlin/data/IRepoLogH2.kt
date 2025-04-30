package es.prog2425.calclog.data

interface IRepoLogH2 {
    fun guardarLog(level: String, message: String)
    fun leerTodosLosLogs(): List<String>
}

