package main.java.controller;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.character.Personagem;
import main.java.model.entity.event.Escolha;
import main.java.model.entity.event.Evento;
import main.java.model.entity.event.Quest;
import main.java.model.entity.game.Partida;
import main.java.model.entity.world.Local;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;
import main.java.model.exception.QuestJaAtivaException;
import main.java.model.exception.SaldoInsuficienteException;
import main.java.model.service.*;
import main.java.view.observer.AtributosObserver;

import java.util.ArrayList;
import java.util.List;

// Controller do jogo
public class BixoController {

    private PartidaService   partidaService;
    private AcademicoService academicoService;
    private ExplorarService  explorarService;
    private EventoService    eventoService;
    private QuestService     questService;
    private Partida          partida;

    // Lista de observadores para escutar atualizações do jogador
    private final List<AtributosObserver> observers = new ArrayList<>();

    // Injeta os serviços necessários para o funcionamento da partida
    public BixoController(PartidaService ps, AcademicoService as, ExplorarService es, EventoService evs, QuestService qs) {
        this.partidaService   = ps;
        this.academicoService = as;
        this.explorarService  = es;
        this.eventoService    = evs;
        this.questService     = qs;
    }

    // Registra um novo observer e dispara a primeira atualização de interface
    public void registrarObserver(AtributosObserver observer) {
        this.observers.add(observer);
        notificarObservers();
    }

    // Remove um observer da lista
    public void removerObserver(AtributosObserver observer) {
        this.observers.remove(observer);
    }

    // Envia os dados atualizados do jogador para todos os observadores
    public void notificarObservers() {
        if (partida != null && partida.getJogador() != null && partida.getTempo() != null) {
            Jogador j = partida.getJogador();

            for (AtributosObserver observer : observers) {
                observer.atualizarHUD(
                        j.getEnergia(), j.getNivelConhecimento(), j.getMotivacao(),
                        j.getSaude(), j.getDinheiro(), j.getDesempenhoAcademico(),
                        partida.getTempo().getSemanaAtual(), partida.getTempo().getSemestreAtual(),
                        j.getDisciplinas().size(), j.getHistoricoAprovadas().size(), j.getCaminhoIconeAvatar()
                );
            }
        }
    }

    // Busca e retorna a referência de um local do mapa com base no nome
    public Local getLocalPorNome(String nome) {
        if (partida.getUniversidade().getNome().equalsIgnoreCase(nome)) return partida.getUniversidade();

        for (Local l : partida.getUniversidade().getLocais()) {
            if (l.getNome().equalsIgnoreCase(nome)) return l;
        }
        return null;
    }

    // Filtra e retorna todos os NPCs instanciados no local especificado
    public ArrayList<Personagem> getPersonagensNoLocal(String nomeLocal) {
        ArrayList<Personagem> resultado = new ArrayList<>();
        for (Personagem p : partida.getUniversidade().getPersonagens()) {
            if (p.getLocalAtual() != null &&
                    p.getLocalAtual().getNome().equalsIgnoreCase(nomeLocal)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Getters de acesso rápido aos dados de estado atual da partida
    public Local getLocalAtual() { return partida.getJogador().getLocalAtual(); }
    public ArrayList<Quest> getQuestsAtivas() { return new ArrayList<>(partida.getJogador().getQuestsAtivas()); }
    public boolean getStatusJogo() { return partida.isJogoEncerrado(); }
    public Evento getEventoAtual() { return partida.getEventoAtual(); }
    public int getSemanaAtual() { return this.partida.getTempo().getSemanaAtual(); }

    // Prepara os dados base e inicializa uma nova partida
    public void iniciarJogo(String nomeJogador, String caminhoAvatar, String caminhoIconeAvatar) {
        this.partida = partidaService.iniciarJogo(nomeJogador, caminhoAvatar, caminhoIconeAvatar);
        notificarObservers();
    }

    // Sobrescreve o estado atual com os dados de um save carregado
    public void selecionarSave(Partida partida) {
        this.partida = partida;

        // Sincroniza as salas com o histórico real do save selecionado,
        explorarService.atualizarSalas(partida.getJogador().getHistoricoAprovadas(), partida.getUniversidade(), partida.getGradeCompleta());

        notificarObservers();
    }

    // Busca o último save do jogador atual no banco e recarrega a cena correspondente
    public void recarregarPartidaAtual() {
        if (this.partida != null && this.partida.getJogador() != null) {
            String nomeAtual = this.partida.getJogador().getNome();
            ArrayList<Partida> saves = partidaService.carregarJogo();

            if (saves != null) {
                for (Partida save : saves) {
                    if (save.getJogador().getNome().equalsIgnoreCase(nomeAtual)) {
                        this.selecionarSave(save);
                        GerenciadorCenas.getInstance().navegarParaLocalAtual();
                        return;
                    }
                }
            }
        }
    }

    // Retorna a lista de todas as partidas persistidas
    public ArrayList<Partida> carregarJogo() {
        return partidaService.carregarJogo();
    }

    // Encaminha a passagem de turno e atualiza a interface
    public void avancarSemana() {
        partidaService.avancarSemana(this.partida);
        notificarObservers();
    }

    // Valida a movimentação no mapa, desloca o jogador e avalia gatilhos de eventos aleatórios
    public void moverPara(String nomeLocal) throws MovimentoInvalidoException {
        for (Local l : this.partida.getUniversidade().getLocais()) {
            if (l.getNome().equalsIgnoreCase(nomeLocal)) {
                explorarService.moverPara(this.partida.getJogador(), l);
                partida.setEventoAtual(eventoService.gerarEvento(partida.getEventos(), partida.getTempo(), l));
                notificarObservers();
                return;
            }
        }
        throw new MovimentoInvalidoException("Local '" + nomeLocal + "' não encontrado no mapa.");
    }

    // Consome a interação do local e retorna true caso envolva o uso do ônibus
    public boolean executarAcao(Local l) throws InteracaoEsgotadaException, SaldoInsuficienteException {
        boolean pegouOnibus = explorarService.executarAcao(this.partida.getJogador(), l);
        notificarObservers();
        return pegouOnibus;
    }

    // Registra a interação do jogador com um NPC e aplica os efeitos resultantes
    public void interacaoNPC(Personagem npc) throws InteracaoEsgotadaException {
        explorarService.interacaoNPC(this.partida.getJogador(), npc);
    }

    // Retorna o diálogo atribuído ao NPC
    public String dialogoNPC(Personagem npc) {
        return explorarService.dialogoNPC(this.partida.getJogador(), npc);
    }

    // Processa a pontuação do minigame e bloqueia a ação do botão após realizá-lo
    public void finalizarMinigameProva(Disciplina disciplina, int acertos, int totalPerguntas) {
        if (disciplina != null) {
            academicoService.aplicarProva(partida.getJogador(), disciplina, acertos, totalPerguntas);

            if (partida.getJogador().getLocalAtual() != null) {
                partida.getJogador().getLocalAtual().setInteragiu(true);
            }
            notificarObservers();
        }
    }

    // Executa as consequências da opção selecionada no painel de eventos
    public void processarEscolha(Escolha escolha) {
        eventoService.processarEscolha(this.partida.getJogador(), escolha);
        notificarObservers();
        partida.setEventoAtual(null); // Libera o jogador do evento
    }

    // Delega uma nova missão ao jogador, caso não haja nenhuma em andamento
    public void gerarQuestParaJogador() throws QuestJaAtivaException {
        if (partida != null && partida.getJogador() != null) {
            questService.gerarQuest(partida.getJogador());
            notificarObservers();
        }
    }

    // Formata e retorna a missão atual para exibição no HUD
    public String getInformacaoQuestAtiva() {
        if (this.partida.getJogador().getQuestsAtivas().isEmpty()) {
            return "Nenhuma missão ativa no momento. Procure um NPC para arrumar algo para fazer!";
        } else {
            Quest q = this.partida.getJogador().getQuestsAtivas().getFirst();
            return "OBJETIVO: " + q.getObjetivo();
        }
    }

    // Extrai o caminho do ícone do jogador
    public String getCaminhoIconeAvatar() {
        if (this.partida != null && this.partida.getJogador() != null) {
            return this.partida.getJogador().getCaminhoIconeAvatar();
        }
        return null;
    }

    // Checagens de condição de encerramento
    public boolean verificarFimDeJogo() { return partidaService.verificarFimDeJogo(this.partida.getJogador()); }
    public boolean verificarGameOver()  { return partidaService.verificarGameOver(this.partida.getJogador()); }

    // Remove o save persistido do disco após formatura
    public void deletarJogo()           { partidaService.deletarJogoFinalizado(this.partida); }
}