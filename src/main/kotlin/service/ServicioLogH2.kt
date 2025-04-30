package es.prog2425.calclog.service

import es.prog2425.calclog.data.IRepoLogH2

class ServicioLogH2(private val repositorio: IRepoLogH2) : IServicioLogH2 {

    override fun guardarLog(mensaje: String) {
        repositorio.guardarLog("INFO", mensaje)
    }
}

