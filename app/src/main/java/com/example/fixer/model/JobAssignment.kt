package com.example.fixer.model

data class JobAssignment(
    var idAssignment: Int = 0,
    var worker: MyUser = MyUser(), // Usuario del tipo "worker"
    var serviceRequest: ServiceRequest = ServiceRequest(), // La solicitud original del cliente
    var estadoAsignacion: EstadoAsignacion = EstadoAsignacion.ASIGNADO, // Estado inicial
    var fechaAsignacion: String = ""
) {
    // Constructor vacío para Firebase
    constructor() : this(0, MyUser(), ServiceRequest(), EstadoAsignacion.ASIGNADO, "")
}

enum class EstadoAsignacion {
    ASIGNADO,
    EN_CURSO,
    COMPLETADO,
    CANCELADO
}
