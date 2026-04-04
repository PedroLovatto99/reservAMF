package reservAMF;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reservAMF.DTO.Mapper.ReservaMapper;
import reservAMF.DTO.Request.ReservaRequest;
import reservAMF.DTO.Response.ReservaResponse;
import reservAMF.DTO.Response.SalaResponse;
import reservAMF.Enum.ReservaStatus;
import reservAMF.Models.ReservaModel;
import reservAMF.Models.SalaModel;
import reservAMF.Repository.ReservaRepository;
import reservAMF.Service.ReservaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reservAMF.Enum.ReservaStatus.AGENDADA;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;


    @Test
    void todasAsReservasListadasComSucesso() {

        Pageable paginacao = PageRequest.of(0, 10);
        SalaModel sala = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        ReservaModel reserva = new ReservaModel(1L, sala, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC2", ReservaStatus.AGENDADA);

        Page<ReservaModel> paginaSimuladaDoBanco = new PageImpl<>(List.of(reserva));

        ReservaResponse response = new ReservaResponse(1L, 1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC2", ReservaStatus.AGENDADA);

        when(reservaRepository.findAll(any(Pageable.class))).thenReturn(paginaSimuladaDoBanco);
        when(reservaMapper.toResponse(reserva)).thenReturn(response);


        Page<ReservaResponse> reservas = reservaService.listarReservas(paginacao);

        assertEquals(1, reservas.getTotalElements());

    }


    @Test
    void criarReservaComSucesso() {
        SalaModel sala = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        ReservaRequest reservaRequest = new ReservaRequest(1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC");
        ReservaModel reservaModel = new ReservaModel(1L, sala, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);
        ReservaResponse reservaResponse = new ReservaResponse(1L, 1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);

        when(reservaMapper.toModel(reservaRequest, sala)).thenReturn(reservaModel);
        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reservaModel);
        when(reservaMapper.toResponse(reservaModel)).thenReturn(reservaResponse);

        ReservaResponse reservaTeste = reservaService.criarReserva(reservaRequest);

        assertNotNull(reservaTeste);
        assertEquals(1L, reservaTeste.id());


    }

    @Test
    void buscarReservaPeloIdSucesso() {
        Long id = 1L;

        SalaModel sala = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        ReservaModel reservaModel = new ReservaModel(1L, sala, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);
        ReservaResponse reservaResponse = new ReservaResponse(1L, 1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaModel));

        when(reservaMapper.toResponse(reservaModel)).thenReturn((reservaResponse));

        ReservaResponse reservaTeste = reservaService.listarReservaPorId(id);


        assertEquals(1L, reservaTeste.id());


    }

    @Test
    void nãoRetornarReservaQuandoIdNaoExiste() {

        Long id = 1L;

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        ReservaResponse reservaTeste = reservaService.listarReservaPorId(id);

        assertNull(reservaTeste, "O resultado deveria ser nulo porque o Id não existe");
    }

    @Test
    void editarReservaComSucesso() {

        Long id = 1L;

        SalaModel sala = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        ReservaRequest reservaRequest = new ReservaRequest(1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC");
        ReservaModel reservaModel = new ReservaModel(1L, sala, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);
        ReservaResponse reservaResponse = new ReservaResponse(1L, 1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaModel));
        when(reservaMapper.toModel(reservaRequest, sala)).thenReturn(reservaModel);
        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reservaModel);
        when(reservaMapper.toResponse(reservaModel)).thenReturn(reservaResponse);

        ReservaResponse reservaTeste = reservaService.criarReserva(reservaRequest);

        assertNotNull(reservaTeste);
        assertEquals(1L, reservaTeste.id());

    }

    @Test
    void naoEditarQuandoIdNaoEncontrado() {

        Long id = 1L;

        ReservaRequest reservaRequest = new ReservaRequest(1L, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC");

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        ReservaResponse reservaTeste = reservaService.listarReservaPorId(id);

        assertNull(reservaTeste, "O resultado deveria ser nulo porque o Id não existe");

    }

    @Test
    void deletarReservaSucesso() {

        Long id = 1L;
        SalaModel sala = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        ReservaModel reserva = new ReservaModel(1L, sala, "Pedro", LocalDateTime.parse("2026-01-20T14:00:00"), LocalDateTime.parse("2026-02-20T16:00:00"), "Apresentação de TCC", ReservaStatus.AGENDADA);

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        reservaService.deletarReserva(id);

        verify(reservaRepository, times(1)).delete(reserva);

    }

    @Test
    void naoDeletarQuandoReservaNaoExistir() {
        Long id = 2L;

        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        reservaService.deletarReserva(id);

        verify(reservaRepository, times(0)).deleteById(id);




    }





}
