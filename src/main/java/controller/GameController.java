package main.java.controller;

import main.java.model.entity.academic.Avaliacao;
import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Personagem;
import main.java.model.entity.event.Escolha;
import main.java.model.entity.event.Quest;
import main.java.model.entity.game.Partida;
import main.java.model.entity.world.Local;
import main.java.model.service.*;

import java.util.ArrayList;

// Controller central que media a View e os Services da partida
public class GameController {

    // Services e estado da partida em andamento
    private PartidaService   partidaService;
    private AcademicoService academicoService;
    private ExplorarService  explorarService;
    private EventoService    eventoService;
    private QuestService     questService;
    private Partida          partida;

    // Constrói o controller com todos os services necessários
    public GameController(PartidaService ps, AcademicoService as, ExplorarService es, EventoService evs, QuestService qs) {
        this.partidaService   = ps;
        this.academicoService = as;
        this.explorarService  = es;
        this.eventoService    = evs;
        this.questService     = qs;
    }

    public void iniciarJogo(String nomeJogador) {
        this.partida = partidaService.iniciarJogo(nomeJogador);
    }

    public void selecionarSave(Partida partida) {
        this.partida = partida;
    }

    public ArrayList<Partida> carregarJogo() {
        return partidaService.carregarJogo();
    }

    public void avancarSemana() {
        partidaService.avancarSemana(this.partida);
    }

    public boolean verificarFimDeJogo() {
        return partidaService.verificarFimDeJogo(this.partida.getJogador());
    }

    public boolean verificarGameOver() {
        return partidaService.verificarGameOver(this.partida.getJogador());
    }

    public void deletarJogo() {
        partidaService.deletarJogoFinalizado(this.partida);
    }

    public void moverPara(String nomeLocal) {
        for (Local l : this.partida.getUniversidade().getLocais()) {
            if (l.getNome().equalsIgnoreCase(nomeLocal)) {
                explorarService.moverPara(this.partida.getJogador(), l);
                return;
            }
        }
    }

    public boolean executarAcao(Local l) {
        return explorarService.executarAcao(this.partida.getJogador(), l);
    }

    public void interacaoNPC(Personagem npc) {
        explorarService.interacaoNPC(this.partida.getJogador(), npc);
    }

    public String dialogoNPC(Personagem npc) {
        return explorarService.dialogoNPC(this.partida.getJogador(), npc, this.partida.getUniversidade());
    }

    public void aplicarProva(Disciplina d, Avaliacao a, double nota) {
        academicoService.aplicarProva(this.partida.getJogador(), d, a, nota);
    }

    public void processarEscolha(Escolha escolha) {
        eventoService.processarEscolha(this.partida.getJogador(), escolha);
    }

    public void entregarQuest(Quest q) {
        questService.entregarQuest(this.partida.getJogador(), q);
    }
}