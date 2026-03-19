package reservAMF.Reservas;

import java.time.LocalDateTime;

public record ReservaResponse(

    Long id,
    Long sala_id,
    String solicitante,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    String motivo,
    ReservaStatus status

) { }
