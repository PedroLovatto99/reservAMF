package reservAMF.Salas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    @Operation(summary = "Listar salas", description = "Listar todas as salas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de salas")
    })
    public ResponseEntity<Page<SalaResponse>> listarSalas(@PageableDefault(size = 10) Pageable paginacao) {
        Page<SalaResponse> salas = salaService.listarSalas(paginacao);
        return ResponseEntity.ok(salas);
    }

    @PostMapping
    @Operation(summary = "Criar sala", description = "Criar uma sala")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sala criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na criação da sala. Dados inválidos")
    })
    public ResponseEntity<SalaResponse> criarSala(@Valid @RequestBody SalaRequest salaRequest) {
        SalaResponse sala = salaService.criarSala(salaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sala);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar sala pelo ID", description = "Listar sala específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    public ResponseEntity<?> listarSalaPorId(@PathVariable Long id) {

        SalaResponse sala = salaService.listarSalaPorId(id);

        if(sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sala não encontrada");
        }

    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar sala", description = "Editar uma sala")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sala editada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na atualização da sala. Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    public ResponseEntity<?> editarSala(@PathVariable Long id, @RequestBody SalaRequest salaAtualizada) {

        SalaResponse sala = salaService.editarSala(id, salaAtualizada);

        if(sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sala não encontrada");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sala", description = "Deletar uma sala")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sala deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    public ResponseEntity<String> deletarSala(@PathVariable Long id) {
        if(salaService.listarSalaPorId(id) != null) {
            salaService.deletarSala(id);
            return ResponseEntity.ok("Sala deletada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sala não encontrada");
        }
    }

}
