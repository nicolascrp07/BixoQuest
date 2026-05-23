package test.java.model.repository;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Evento;
import main.java.model.entity.game.Partida;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Universidade;
import main.java.model.repository.*;
import main.java.model.service.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

// Testa o repositório de partidas e a persistência em disco
public class PartidaRepositoryTeste {
    PartidaRepository partRepo;
    ProfessorRepository profRepo;
    DisciplinaRepository discRepo;
    UniversidadeRepository uniRepo;
    EventoRepository eventoRepo;
    AcademicoService as;
    Partida p;

    @Rule
    public TemporaryFolder pastaTemporaria = new TemporaryFolder(); // Pasta temporária isolada para cada teste

    // Constrói o ambiente de teste com mundo completo e uma partida pronta antes de cada teste
    @Before
    public void setUp() {
        profRepo = new ProfessorRepository();
        discRepo = new DisciplinaRepository();
        uniRepo = new UniversidadeRepository();
        eventoRepo = new EventoRepository();
        partRepo = new PartidaRepository(discRepo, eventoRepo);
        partRepo.setDiretorio(pastaTemporaria.getRoot().getPath());
        as = new AcademicoService();

        profRepo.criarProfessores();
        discRepo.criarGrade(profRepo.buscarTodos());
        uniRepo.criarMundo(discRepo.buscarTodas());
        eventoRepo.criarEventosPadrao();

        ArrayList<Disciplina> grade = discRepo.buscarTodas();
        Universidade uni = uniRepo.buscarPorNome("UEFS");
        ArrayList<Evento> eventos = eventoRepo.buscarTodos();

        Jogador jogador = new Jogador("Teste", 100, 0, 100, 100, 50.0, 0.0, uni);
        as.matricularNovoSemestre(jogador, grade);

        Tempo tempo = new Tempo(1, 1);
        p = new Partida(jogador, tempo, uni, null, false, eventos, grade, UUID.randomUUID());
    }

    // Confirma que salvar uma partida cria o arquivo JSON correspondente no disco
    @Test
    public void salvarCriaArquivo() {
        String caminhoEsperado = Path.of(pastaTemporaria.getRoot().getPath(), p.getId().toString() + ".json").toString();
        partRepo.salvarPartida(p);
        assertTrue(new File(caminhoEsperado).exists());
    }

    // Confirma que os dados carregados do disco correspondem aos dados originais da partida salva
    @Test
    public void jogoSalvoIgualAoCarregado() {
        Universidade uni = uniRepo.buscarPorNome("UEFS");
        partRepo.salvarPartida(p);
        Partida partidaCarregada = partRepo.buscarPorNomeDoJogador("Teste", uni);

        assertNotNull(partidaCarregada);
        assertEquals("Teste", partidaCarregada.getJogador().getNome());
        assertEquals(100, partidaCarregada.getJogador().getEnergia());
        assertEquals(3, partidaCarregada.getJogador().getDisciplinas().size());
    }

    // Confirma que deletar uma partida salva retorna true
    @Test
    public void deletarPartidaSalva() {
        partRepo.salvarPartida(p);
        assertTrue(partRepo.deletarSave(p));
    }

    // Confirma que tentar deletar uma partida não salva retorna false
    @Test
    public void deletarPartidaNaoSalva() {
        assertFalse(partRepo.deletarSave(p));
    }

    // Confirma que deletar uma partida remove o arquivo físico do disco
    @Test
    public void deletarSaveDeletaArquivo() {
        String caminhoEsperado = Path.of(pastaTemporaria.getRoot().getPath(), p.getId().toString() + ".json").toString();
        File arquivo = new File(caminhoEsperado);

        partRepo.salvarPartida(p);
        partRepo.deletarSave(p);

        assertFalse(arquivo.exists());
    }

    // Confirma que buscar por nome existente retorna a partida correta
    @Test
    public void buscarJogador() {
        partRepo.salvarPartida(p);
        Universidade uni = uniRepo.buscarPorNome("UEFS");
        Partida encontrada = partRepo.buscarPorNomeDoJogador("Teste", uni);

        assertNotNull(encontrada);
        assertEquals("Teste", encontrada.getJogador().getNome());
    }

    // Confirma que buscar por nome inexistente retorna null
    @Test
    public void buscarJogadorInexistente() {
        partRepo.salvarPartida(p);
        Universidade uni = uniRepo.buscarPorNome("UEFS");
        assertNull(partRepo.buscarPorNomeDoJogador("Nicolas Lindo", uni));
    }

    // Confirma que buscarJogosSalvos retorna todas as partidas salvas com os dados corretos
    @Test
    public void buscarJogosSalvosRetornaTodos() {
        partRepo.salvarPartida(p);
        Universidade uni = uniRepo.buscarPorNome("UEFS");

        Jogador j2 = new Jogador("Nicolas", 100, 100, 100, 100, 100.0, 10.0, uni.getLocais().getFirst());
        Partida p2 = new Partida(j2, p.getTempo(), uni, null, false, eventoRepo.buscarTodos(), null, UUID.randomUUID());
        partRepo.salvarPartida(p2);

        ArrayList<Partida> listaDeSaves = partRepo.buscarJogosSalvos(uni);

        assertEquals(2, listaDeSaves.size());

        Partida salvoTeste = partRepo.buscarPorNomeDoJogador("Teste", uni);
        Partida salvoNicolas = partRepo.buscarPorNomeDoJogador("Nicolas", uni);

        assertNotNull(salvoTeste);
        assertNotNull(salvoNicolas);
        assertEquals(0, salvoTeste.getJogador().getNivelConhecimento());
        assertEquals(100, salvoNicolas.getJogador().getNivelConhecimento());
    }
}