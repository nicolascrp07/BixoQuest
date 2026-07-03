package main.java.model.service;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.character.Personagem;
import main.java.model.entity.event.Evento;
import main.java.model.entity.game.Partida;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Local;
import main.java.model.entity.world.Universidade;
import main.java.model.repository.*;

import java.util.ArrayList;
import java.util.UUID;

// Service responsável por todas as regras de negócio da partida e dos demais services do sistema
public class PartidaService {

    private AcademicoService academicoService;
    private ExplorarService explorarService;
    private EventoService eventoService;
    private ProfessorRepository profRepo;
    private DisciplinaRepository discRepo;
    private UniversidadeRepository uniRepo;
    private PartidaRepository partRepo;
    private EventoRepository eventoRepo;
    private QuestRepository questRepo;

    public static final int TOTAL_DISCIPLINAS = 24;

    // Recebe e mapeia todos os repositórios e services internos necessários
    public PartidaService(AcademicoService ac, ExplorarService es, EventoService ev, ProfessorRepository pr, DisciplinaRepository dr, UniversidadeRepository ur, PartidaRepository par, EventoRepository er, QuestRepository qr) {
        this.academicoService = ac;
        this.explorarService  = es;
        this.eventoService    = ev;
        this.profRepo         = pr;
        this.discRepo         = dr;
        this.uniRepo          = ur;
        this.partRepo         = par;
        this.eventoRepo       = er;
        this.questRepo        = qr;
    }

    // Processa a mudança de turno, liberação de bloqueios e fechamento de semestre
    public void avancarSemana(Partida p) {
        int semestreAnterior = p.getTempo().getSemestreAtual();
        p.getTempo().avancarSemana();

        // Reseta o status de bloqueio dos locais da universidade para o novo turno
        for (Local l : p.getUniversidade().getLocais()) {
            l.setInteragiu(false);
        }

        // Reseta o status de interação dos NPCs para permitir novas interações
        for (Personagem ps : p.getUniversidade().getPersonagens()) {
            ps.setInteragiu(false);
        }

        // Redistribui os NPC 's por toda a universidade
        explorarService.atualizarLocal(p.getUniversidade());

        // Identifica virada de semestre para processar aprovações e novas matrículas
        if (p.getTempo().getSemestreAtual() > semestreAnterior) {
            ArrayList<Disciplina> aprovadas = academicoService.fecharSemestre(p.getJogador());
            explorarService.atualizarSalas(aprovadas, p.getUniversidade(), p.getGradeCompleta());
            academicoService.matricularNovoSemestre(p.getJogador(), p.getGradeCompleta());
        }

        // Aloca o jogador na posição padrão caso esteja em uma posição imprópria
        if (p.getJogador().getLocalAtual() == null) {
            p.getJogador().setLocalAtual(p.getUniversidade().getLocais().getFirst());
        }

        // Persiste o progresso da partida
        partRepo.salvarPartida(p);
    }

    // Retorna true se a hsitórico do jogador atingir o total de disciplinas da grade
    public boolean verificarFimDeJogo(Jogador j) {
        return j.getHistoricoAprovadas().size() == TOTAL_DISCIPLINAS;
    }

    // Popula o mundo inicial, cadastra estruturas bases e retorna uma nova instância de partida
    public Partida iniciarJogo(String nomeJogador, String caminhoAvatar, String caminhoIconeAvatar) {
        profRepo.criarProfessores();
        discRepo.criarGrade(profRepo.buscarTodos());
        uniRepo.criarMundo(discRepo.buscarTodas());
        eventoRepo.criarEventosPadrao();

        ArrayList<Disciplina> grade   = discRepo.buscarTodas();
        Universidade uni              = uniRepo.buscarPorNome("UEFS");
        ArrayList<Evento> eventos     = eventoRepo.buscarTodos();
        questRepo.criarQuestsPadrao(uni);

        Jogador jogador = new Jogador(nomeJogador, 100, 0, 50, 100, 50.0, 0.0, uni.getLocais().getFirst());

        jogador.setCaminhoAvatar(caminhoAvatar);
        jogador.setCaminhoIconeAvatar(caminhoIconeAvatar);

        academicoService.matricularNovoSemestre(jogador, grade);

        Tempo tempo     = new Tempo(1, 1);
        Partida partida = new Partida(jogador, tempo, uni, null, false, eventos, grade, UUID.randomUUID());

        partRepo.salvarPartida(partida);
        return partida;
    }

    // Recria as instâncias globais padronizadas e devolve a lista de saves em disco
    public ArrayList<Partida> carregarJogo() {
        profRepo.criarProfessores();
        discRepo.criarGrade(profRepo.buscarTodos());
        uniRepo.criarMundo(discRepo.buscarTodas());
        eventoRepo.criarEventosPadrao();
        Universidade uni = uniRepo.buscarPorNome("UEFS");
        questRepo.criarQuestsPadrao(uni);
        return partRepo.buscarJogosSalvos(uni);
    }

    // Avalia o esgotamento fatal de atributos
    public boolean verificarGameOver(Jogador j) {
        return j.getSaude() == 0 || j.getMotivacao() == 0;
    }

    // Apaga definitivamente os dados persistidos da partida
    public void deletarJogoFinalizado(Partida partida) {
        partRepo.deletarSave(partida);
    }
}