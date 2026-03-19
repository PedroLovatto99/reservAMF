package reservAMF.Reservas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ReservaRequest(

        @NotNull
        Long sala,

        @NotNull @Size(max = 100)
        String solicitante,

        @NotNull
        LocalDateTime dataHoraInicio,

        @NotNull
        LocalDateTime dataHoraFim,

        @NotNull @Size(max = 300)
        String motivo

) {

        @JsonIgnore
        @AssertTrue(message = "A data de fim não pode ser anterior à data de início")
        public boolean isDataFimValida() {

                if (dataHoraInicio == null || dataHoraFim == null) {
                        return true;
                }

                return !dataHoraFim.isBefore(dataHoraInicio);
        }
}
