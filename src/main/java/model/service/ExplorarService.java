package main.java.model.service;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.*;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.*;

import java.util.ArrayList;

// Service responsável pelas regras de negócio da movimentação do jogador e das interações com o mundo
public class ExplorarService {

    private QuestService questService;

    // Constrói o service
    public ExplorarService(AcademicoService ac, QuestService qs) {
        this.questService = qs;
    }

    // Verifica se o jogador pode transitar entre dois locais
    public boolean podeTransitar(Jogador j, Local l) {
        if (j.getLocalAtual() instanceof Universidade || l instanceof Universidade) {
            return true;
        }
        return false;
    }

    // Move o jogador para o local indicado e verifica o andamento das quests ativas
    public void moverPara(Jogador j, Local l) {
        l.interagir(j);
        questService.verificarAndamento(j);
    }

    // Executa a ação específica do local e retorna true se o jogador pegou o ônibus
    public boolean executarAcao(Jogador j, Local l) {
        l.acaoEspecifica(j);
        questService.verificarAndamento(j);
        return l instanceof PontoDeOnibus;
    }

    // Realiza a interação entre o jogador e um NPC, desde que estejam no mesmo local
    public void interacaoNPC(Jogador j, Personagem npc) {
        if (j.getLocalAtual() == npc.getLocalAtual()) {
            npc.interacaoEspecifica(j);
        }
    }

    public String dialogoNPC(Jogador j, Personagem npc, Universidade uni) {
        if (j.getLocalAtual() == npc.getLocalAtual()){
            if (j.getQuestsAtivas().isEmpty()) {
                questService.gerarQuest(j);
            }
            return npc.getDialogo();
        }
        return "";
    }

    // Atualiza as disciplinas das salas e do LEDS após o fechamento de semestre
    public void atualizarSalas(ArrayList<Disciplina> aprovadas, Universidade uni, ArrayList<Disciplina> grade) {
        for (Disciplina aprovada : aprovadas) {

            // Busca a próxima disciplina da grade que tem a aprovada como pré-requisito
            Disciplina proxima = null;
            for (Disciplina d : grade) {
                if (aprovada.equals(d.getPreRequisito())) {
                    proxima = d;
                    break;
                }
            }

            // Substitui a disciplina nos locais correspondentes
            if (proxima != null) {
                for (Local l : uni.getLocais()) {
                    if (l instanceof Sala s && s.getDisciplinaAtual().equals(aprovada)) {
                        s.setDisciplinaAtual(proxima);
                    } else if (l instanceof LEDS le && le.getDisciplinaAtual().equals(aprovada)) {
                        le.setDisciplinaAtual(proxima);
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

            // Professores não são reposicionados
            if (p instanceof Professor) {
                continue;
            }

            // Sorteia um local até encontrar um que o personagem possa acessar
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