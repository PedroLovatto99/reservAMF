package reservAMF.Salas;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
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
    public ResponseEntity<List<SalaResponse>> listarSalas() {
        List<SalaResponse> salas = salaService.listarSalas();
        return ResponseEntity.ok(salas);
    }

    @PostMapping
    public ResponseEntity<String> criarSala(@Valid @RequestBody SalaRequest salaRequest) {
        SalaResponse sala = salaService.criarSala(salaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Sala criada com sucesso: " + sala);

    }

    @GetMapping("/{id}")
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
    public ResponseEntity<?> editarSala(@PathVariable Long id, @RequestBody SalaRequest salaAtualizada) {

        SalaResponse sala = salaService.editarSala(id, salaAtualizada);

        if(sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sala não encontrada");
        }
    }

    @DeleteMapping
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
