
package br.com.instituicao.gestao.service;

import br.com.instituicao.gestao.dto.InformacaoRequest;
import br.com.instituicao.gestao.model.Informacao;
import br.com.instituicao.gestao.repository.InformacaoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InformacaoServiceTest {

    @Mock
    InformacaoRepository repository;

    @InjectMocks
    InformacaoService service;

    private InformacaoRequest criarRequest() {
        return new InformacaoRequest(
                "Comunicado",
                "Reunião",
                "Aviso geral",
                LocalDate.of(2026, 11, 16)
        );
    }

    @Test
    void deveCadastrarInformacaoRemovendoEspacosExcedentes() {
        InformacaoRequest request = new InformacaoRequest(
                " Comunicado ",
                " Reunião ",
                " Aviso geral ",
                LocalDate.of(2026, 11, 16)
        );

        when(repository.save(any(Informacao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Informacao salvo = service.cadastrar(request);

        assertEquals("Comunicado", salvo.getCategoria());
        assertEquals("Reunião", salvo.getTitulo());
        assertEquals("Aviso geral", salvo.getDescricao());

        verify(repository).save(any(Informacao.class));
    }

    @Test
    void deveAtualizarInformacaoExistente() {
        Informacao existente = new Informacao();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Informacao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Informacao atualizado = service.atualizar(1L, criarRequest());

        assertEquals("Comunicado", atualizado.getCategoria());
        assertEquals("Reunião", atualizado.getTitulo());

        verify(repository).findById(1L);
        verify(repository).save(existente);
    }

    @Test
    void deveLancarErroQuandoRegistroNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(99L, criarRequest())
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(repository, never()).save(any(Informacao.class));
    }

    @Test
    void deveConsultarTodasAsInformacoesQuandoFiltrosEstaoVazios() {
        List<Informacao> resultado = List.of(new Informacao());

        when(repository.findAllByOrderByDataReferenciaDesc())
                .thenReturn(resultado);

        assertSame(resultado, service.consultar(null, null));

        verify(repository).findAllByOrderByDataReferenciaDesc();
    }

    @Test
    void deveConsultarPorTitulo() {
        List<Informacao> resultado = List.of(new Informacao());

        when(repository.findByTituloContainingIgnoreCaseOrderByDataReferenciaDesc("Reunião"))
                .thenReturn(resultado);

        assertSame(resultado, service.consultar(" Reunião ", null));

        verify(repository)
                .findByTituloContainingIgnoreCaseOrderByDataReferenciaDesc("Reunião");
    }

    @Test
    void deveConsultarPorCategoria() {
        List<Informacao> resultado = List.of(new Informacao());

        when(repository.findByCategoriaIgnoreCaseOrderByDataReferenciaDesc("Comunicado"))
                .thenReturn(resultado);

        assertSame(resultado, service.consultar(null, " Comunicado "));

        verify(repository)
                .findByCategoriaIgnoreCaseOrderByDataReferenciaDesc("Comunicado");
    }

    @Test
    void deveConsultarPorTituloECategoria() {
        List<Informacao> resultado = List.of(new Informacao());

        when(repository
                .findByTituloContainingIgnoreCaseAndCategoriaIgnoreCaseOrderByDataReferenciaDesc(
                        "Reunião", "Comunicado"))
                .thenReturn(resultado);

        assertSame(resultado, service.consultar("Reunião", "Comunicado"));

        verify(repository)
                .findByTituloContainingIgnoreCaseAndCategoriaIgnoreCaseOrderByDataReferenciaDesc(
                        "Reunião", "Comunicado");
    }
}
