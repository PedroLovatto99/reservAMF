package reservAMF.Reservas;

import org.springframework.stereotype.Service;
import reservAMF.Salas.SalaMapper;
import reservAMF.Salas.SalaModel;
import reservAMF.Salas.SalaRepository;
import reservAMF.Salas.SalaResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    SalaRepository salaRepository;
    SalaMapper salaMapper;
    ReservaRepository reservaRepo;
    ReservaMapper reservaMapper;

    public ReservaService(SalaRepository salaRepository, SalaMapper salaMapper, ReservaRepository reservaRepo, ReservaMapper reservaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
        this.reservaRepo = reservaRepo;
        this.reservaMapper = reservaMapper;
    }

    public List<ReservaResponse> listarReservas() {
           List<ReservaModel> reservas = reservaRepo.findAll();
            return reservas.stream()
                    .map(reservaMapper::toResponse)
                    .collect(Collectors.toList());
    }

    public ReservaResponse criarReserva(ReservaRequest reservaRequest) {

        SalaModel sala = salaRepository.findById(reservaRequest.sala())
                .orElse(null);

        if(sala != null) {
            ReservaModel reserva = reservaMapper.toModel(reservaRequest, sala);
            reserva = reservaRepo.save(reserva);
            return reservaMapper.toResponse(reserva);
        } else {
            return null;
        }

    }

    public ReservaResponse listarReservaPorId(Long id) {
        Optional<ReservaModel> reserva = reservaRepo.findById(id);
        return reserva.map(reservaMapper::toResponse).orElse(null);
    }

    public ReservaResponse editarReserva(Long id, ReservaRequest reservaRequest) {

        ReservaModel reserva = reservaRepo.findById(id)
                .orElse(null);

        SalaModel sala = salaRepository.findById(reservaRequest.sala())
                .orElse(null);

        if(reserva != null && sala != null) {
            reservaMapper.atualizarModel(reservaRequest, reserva, sala);
            ReservaModel reservaSalva = reservaRepo.save(reserva);
            return reservaMapper.toResponse(reservaSalva);
        } else {
            return null;
        }

    }

    public ReservaResponse alterarStatus(Long id, AlterarStatusRequest alterarStatus) {
        ReservaModel reserva = reservaRepo.findById(id)
                .orElse(null);

        if(reserva != null) {
            reserva.setStatus(alterarStatus.status());
            ReservaModel reservaStatusAtualizado = reservaRepo.save(reserva);
            return reservaMapper.toResponse(reservaStatusAtualizado);
        } else {
            return null;
        }
    }

    public void deletarReserva(Long id) {
        Optional<ReservaModel> reserva = reservaRepo.findById(id);
        if(reserva.isPresent()) {
            reservaRepo.delete(reserva.get());
        }

    }

}
