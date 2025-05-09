package es.prog2425.calclog.data.dao

import es.prog2425.calclog.model.Operador

interface IErrorDao {
    fun insertar(mensaje : String)
    fun crearTabla()
    fun realizarConsulta()
}