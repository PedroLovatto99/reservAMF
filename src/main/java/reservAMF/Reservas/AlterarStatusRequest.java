package reservAMF.Reservas;

import jakarta.validation.constraints.NotNull;

public record AlterarStatusRequest(@NotNull ReservaStatus status) {
}
