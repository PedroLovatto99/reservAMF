package reservAMF.Reservas;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reservAMF.Salas.SalaResponse;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @GetMapping
    public ResponseEntity<Page<ReservaResponse>> listarReservas(@PageableDefault(size = 10) Pageable paginacao) {
        Page<ReservaResponse> reservas = reservaService.listarReservas(paginacao);
        return ResponseEntity.ok(reservas);
    }


    @PostMapping
    public ResponseEntity<ReservaResponse> criarReserva(@Valid @RequestBody ReservaRequest reservaRequest) {
        ReservaResponse novaReserva = reservaService.criarReserva(reservaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaReserva);
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
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @Valid @RequestBody AlterarStatusRequest Statusrequest){
        ReservaResponse reserva = reservaService.alterarStatus(id, Statusrequest);

        if(reserva != null) {
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva não encontrada");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        if(reservaService.listarReservaPorId(id) != null) {
            reservaService.deletarReserva(id);
            return ResponseEntity.ok("Reserva deletada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva deletada com sucesso");
        }
    }




}
