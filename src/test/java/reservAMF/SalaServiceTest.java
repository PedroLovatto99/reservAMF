package reservAMF;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reservAMF.DTO.Mapper.SalaMapper;
import reservAMF.DTO.Request.SalaRequest;
import reservAMF.DTO.Response.SalaResponse;
import reservAMF.Models.SalaModel;
import reservAMF.Repository.SalaRepository;
import reservAMF.Service.SalaService;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private SalaMapper salaMapper;

    @InjectMocks
    private SalaService salaService;


    @Test
    void todasAsSalasListadasComSucesso() {

        // ARRANGE
        Pageable paginacao = PageRequest.of(0, 10);

        SalaModel sala1 = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        SalaModel sala2 = new SalaModel(2L, "Mini Auditório 3", 3, "Sala", true, null);
        Page<SalaModel> paginaSimuladaDoBanco = new PageImpl<>(List.of(sala1, sala2));

        SalaResponse response1 = new SalaResponse(1L, "Mini Auditório 6", 6, "Sala", true);
        SalaResponse response2 = new SalaResponse(2L, "Mini Auditório 3", 3, "Sala", true);

        when(salaRepository.findAll(any(Pageable.class))).thenReturn(paginaSimuladaDoBanco);
        when(salaMapper.toResponse(sala1)).thenReturn(response1);
        when(salaMapper.toResponse(sala2)).thenReturn(response2);

        // ACT
        Page<SalaResponse> salas = salaService.listarSalas(paginacao);

        // ASSERT
        assertEquals(2, salas.getTotalElements());

    }

    @Test
    void criarSalaComSucesso() {

        SalaRequest sala1 = new SalaRequest("Mini Auditório 6", 6, "Sala", true);
        SalaModel salaModel = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        SalaResponse salaResponse = new SalaResponse(1L, "Mini Auditório 6", 6, "Sala", true);

        when(salaMapper.toModel(sala1)).thenReturn(salaModel);
        when(salaRepository.save(any(SalaModel.class))).thenReturn(salaModel);
        when(salaMapper.toResponse(salaModel)).thenReturn(salaResponse);

        SalaResponse salaTeste = salaService.criarSala(sala1);

        assertNotNull(salaTeste);
        assertEquals("Mini Auditório 6", salaTeste.nome());
        assertEquals(1L, salaTeste.id());


    }

    @Test
    void buscarSalaPeloIdSucesso() {

        Long id = 1L;

        SalaModel salaModel = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        SalaResponse salaResponse = new SalaResponse(1L, "Mini Auditório 6", 6, "Sala", true);

        when(salaRepository.findById(id)).thenReturn(Optional.of(salaModel));

        when(salaMapper.toResponse(salaModel)).thenReturn((salaResponse));

        SalaResponse sala = salaService.listarSalaPorId(id);

       // assertNotNull(sala, "O resultado não deveria ser nulo");
        assertEquals(1L, sala.id());

    }

    @Test
    void nãoRetornarSalaQuandoIdNaoExiste() {

        Long id = 1L;

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        SalaResponse sala = salaService.listarSalaPorId(id);

        assertNull(sala, "O resultado deveria ser nulo porque o Id não existe");
    }


    @Test
    void editarSalaComSucesso() {

        Long id = 1L;

        SalaRequest sala1 = new SalaRequest("Mini Auditório 6", 6, "Sala", true);
        SalaModel salaModel = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        SalaResponse salaResponse = new SalaResponse(1L, "Mini Auditório 6", 6, "Sala", true);

        when(salaRepository.findById(id)).thenReturn(Optional.of(salaModel));
        when(salaMapper.toModel(sala1)).thenReturn(salaModel);
        when(salaRepository.save(any(SalaModel.class))).thenReturn(salaModel);
        when(salaMapper.toResponse(salaModel)).thenReturn(salaResponse);

        SalaResponse sala = salaService.editarSala(id, sala1);

        assertNotNull(sala);
        assertEquals("Mini Auditório 6", sala.nome());
        assertEquals(1L, sala.id());

    }

    @Test
    void naoEditarQuandoIdNaoEncontrado() {

        Long id = 1L;
        SalaRequest sala = new SalaRequest("Mini Auditório 6", 6, "Sala", true);

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        SalaResponse salaTeste = salaService.editarSala(id, sala);

        assertNull(salaTeste, "O resultado deveria ser nulo porque o Id não existe");


    }



    @Test
    void deletarSalaSucesso() {
        Long id = 1L;
        SalaModel salaModel = new SalaModel(1L, "Mini Auditório 6", 6, "Sala", true, null);
        when(salaRepository.findById(id)).thenReturn(Optional.of(salaModel));

        salaService.deletarSala(id);

        verify(salaRepository, times(1)).deleteById(id);

    }

    @Test
    void naoDeletarQuandoSalaNaoExistir() {
        Long id = 2L;

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        salaService.deletarSala(id);

        verify(salaRepository, times(0)).deleteById(id);


    }



}
