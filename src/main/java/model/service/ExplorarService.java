package main.java.model.service;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.*;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.*;
import main.java.model.exception.InteracaoEsgotadaException;
import main.java.model.exception.MovimentoInvalidoException;

import java.util.ArrayList;

// Service responsável pelas regras de negócio da movimentação do jogador e das interações com o mundo
public class ExplorarService {

    private QuestService questService;

    // Constrói o service
    public ExplorarService(QuestService qs) {
        this.questService = qs;
    }

    // Verifica se o jogador pode transitar entre dois locais usando a estrutura de GRAFO
    public boolean podeTransitar(Jogador j, Local destino) {
        // O jogador só pode ir para um local se este estiver na lista de conexões do local atual
        return j.getLocalAtual().getConexoes().contains(destino);
    }

    // Move o jogador para o destino, validando o grafo de conexões, dispara a interação do local e checa quests
    public void moverPara(Jogador j, Local destino) throws MovimentoInvalidoException {
        if (j.getLocalAtual() != null && !podeTransitar(j, destino)) {
            throw new MovimentoInvalidoException("Caminho bloqueado! Você não pode ir para "
                    + destino.getNome() + " a partir de " + j.getLocalAtual().getNome() + ".");
        }

        j.setLocalAtual(destino);
        destino.interagir(j);
        questService.verificarAndamento(j);
    }

    // Executa a ação específica do local e retorna true se o jogador pegou o ônibus; lança exceção se o local já foi usado ou se faltar energia para aula
    public boolean executarAcao(Jogador j, Local l) throws InteracaoEsgotadaException {
        if (l.isInteragiu()) {
            throw new InteracaoEsgotadaException("Você já realizou a ação deste local (" + l.getNome() + ") nesta semana! Avance o tempo para interagir novamente.");
        }

        if (l instanceof AmbienteAula && j.getEnergia() <= 10) {
            throw new InteracaoEsgotadaException("Você está exausto demais para assistir aula! Encontre uma forma de recuperar energia (Cantina, Colegiado ou Ponto de Ônibus).");
        }

        l.acaoEspecifica(j);
        l.setInteragiu(true); // Trava o local para novas ações no mesmo turno

        questService.verificarAndamento(j);
        return l instanceof PontoDeOnibus;
    }

    // Realiza a interação entre o jogador e um NPC, desde que estejam no mesmo local e o NPC ainda não tenha sido usado nesta semana
    public void interacaoNPC(Jogador j, Personagem npc) throws InteracaoEsgotadaException {
        if (npc.isInteragiu()) {
            throw new InteracaoEsgotadaException(npc.getNome() + " já conversou com você esta semana. Deixe a pessoa respirar!");
        }

        if (j.getEnergia() <= 10) {
            throw new InteracaoEsgotadaException("Você está sem energia para socializar. Tudo o que você consegue fazer agora é se arrastar (transitar) pelo campus!");
        }

        if (j.getLocalAtual() == npc.getLocalAtual()) {
            npc.interacaoEspecifica(j);
            npc.setInteragiu(true);
            questService.verificarAndamento(j);
        }
    }

    // Retorna a fala do NPC, ou uma mensagem padrão se o jogador não estiver no mesmo local
    public String dialogoNPC(Jogador j, Personagem npc) {
        if (j.getLocalAtual().getNome().equalsIgnoreCase(npc.getLocalAtual().getNome())) {
            return npc.getDialogo();
        }
        return "Você está longe demais para ouvir alguma coisa.";
    }

    // Atualiza as disciplinas das salas e do LEDS após o fechamento de semestre, avançando cada ambiente para a próxima matéria da grade
    public void atualizarSalas(ArrayList<Disciplina> aprovadas, Universidade uni, ArrayList<Disciplina> grade) {
        for (Disciplina aprovada : aprovadas) {

            Disciplina proxima = null;
            for (Disciplina d : grade) {
                if (aprovada.equals(d.getPreRequisito())) {
                    proxima = d;
                    break;
                }
            }

            if (proxima != null) {
                for (Local l : uni.getLocais()) {
                    if (l instanceof AmbienteAula ambiente && ambiente.getDisciplinaAtual().equals(aprovada)) {

                        // Tira o professor antigo da sala antes de trocar a matéria
                        if (ambiente.getDisciplinaAtual().getProfessor() != null) {
                            ambiente.getDisciplinaAtual().getProfessor().setLocal(null);
                        }

                        ambiente.setDisciplinaAtual(proxima);
                        proxima.getProfessor().setLocal(ambiente);
                    }
                }
            }
        }
    }

    // Redistribui aleatoriamente os personagens pelos locais válidos, exceto os professores
    public void atualizarLocal(Universidade uni) {
        ArrayList<Personagem> ps = uni.getPersonagens();
        ArrayList<Local> lu = uni.getLocais();

        for (Personagem p : ps) {
            if (!p.seMoveAleatoriamente()) {
                continue;
            }

            boolean encontrouLocalValido = false;
            while (!encontrouLocalValido) {
                int indice = (int) (Math.random() * lu.size());
                Local localSorteado = lu.get(indice);

                if (p.podeAcessar(localSorteado)) {
                    p.setLocal(localSorteado);
                    encontrouLocalValido = true;
                }
            }
        }
    }
}