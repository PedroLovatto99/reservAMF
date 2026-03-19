package reservAMF.Reservas;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarReservas() {
        List<ReservaResponse> reservas = reservaService.listarReservas();
        return ResponseEntity.ok(reservas);
    }

    @PostMapping
    public ResponseEntity<String> criarReserva(@Valid @RequestBody ReservaRequest reservaRequest) {
        ReservaResponse novaReserva = reservaService.criarReserva(reservaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Reserva criada com sucesso" + novaReserva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarReservaPorId(@PathVariable Long id) {
        ReservaResponse reserva = reservaService.listarReservaPorId(id);

        if(reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva não encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequest reservaAtualizada) {
        ReservaResponse reserva = reservaService.editarReserva(id, reservaAtualizada);

        if(reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva não encontrada");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?>  alterarStatus(@PathVariable Long id, @Valid @RequestBody AlterarStatusRequest Statusrequest){
        ReservaResponse reserva = reservaService.alterarStatus(id, Statusrequest);

        if(reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva não encontrada");
        }

    }




}
