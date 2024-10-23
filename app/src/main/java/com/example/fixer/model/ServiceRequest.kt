package com.example.fixer.model

data class ServiceRequest(
    var idRequest: Int = 0,
    var customer: MyUser = MyUser(), // Usuario del tipo "customer"
    var tipoServicio: TipoServicio = TipoServicio.OTROS, // Tipo de servicio solicitado
    var descripcionProblema: String = "",
    var direccion: String = "",
    var estado: EstadoSolicitud = EstadoSolicitud.PENDIENTE, // Estado inicial
    var fechaSolicitud: String = ""
) {
    // Constructor vacío para Firebase
    constructor() : this(0, MyUser(), TipoServicio.OTROS, "", "", EstadoSolicitud.PENDIENTE, "")
}

enum class TipoServicio {
    FONTANERIA,
    ELECTRICIDAD,
    JARDINERIA,
    LIMPIEZA,
    PINTURA,
    OTROS
}

enum class EstadoSolicitud {
    PENDIENTE,
    ASIGNADO,
    EN_PROCESO,
    COMPLETADO,
    CANCELADO
}
