package reservAMF.Reservas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar reservas", description = "Listar todas as reservas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de reservas")
    })
    public ResponseEntity<Page<ReservaResponse>> listarReservas(@PageableDefault(size = 10) Pageable paginacao) {
        Page<ReservaResponse> reservas = reservaService.listarReservas(paginacao);
        return ResponseEntity.ok(reservas);
    }


    @PostMapping
    @Operation(summary = "Criar reserva", description = "Criar uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação da serva. Dados inválidos")
    })
    public ResponseEntity<ReservaResponse> criarReserva(@Valid @RequestBody ReservaRequest reservaRequest) {
        ReservaResponse novaReserva = reservaService.criarReserva(reservaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaReserva);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar reserva pelo ID", description = "Listar reserva específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
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
    @Operation(summary = "Editar reserva", description = "Editar uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva editada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização da reserva. Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
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
    @Operation(summary = "Editar status da reserva", description = "Editar o status da reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da reserva alterado"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do status. Dado inválido"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
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
    @Operation(summary = "Deletar reserva", description = "Deletar uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
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
