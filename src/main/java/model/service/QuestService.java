package main.java.model.service;

import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Quest;
import main.java.model.entity.world.Universidade;
import main.java.model.repository.QuestRepository;

import java.util.ArrayList;

// Service responsável pelas regras de negócio das quests
public class QuestService {
    QuestRepository qr;

    public QuestService(QuestRepository qr){
        this.qr = qr;
    }

    // Inicia a quest para o jogador, desde que ele ainda não a tenha ativa
    public void aceitarQuest(Jogador j, Quest q) {
        if (j.getQuestsAtivas().contains(q)) {
            return;
        }
        q.iniciar(j);
    }

    // Percorre as quests ativas e marca como concluídas as que já atingiram seu objetivo
    public void verificarAndamento(Jogador j) {
        ArrayList<Quest> jq = j.getQuestsAtivas();
        for (Quest quest : jq) {
            if (!quest.isStatusConcluida()) {
                quest.setStatusConcluida(quest.checarProgresso(j));
            }
        }
    }

    // Aplica a recompensa e remove a quest da lista ativa, desde que já esteja concluída
    public void entregarQuest(Jogador j, Quest q) {
        if (q.isStatusConcluida()) {
            q.getRecompensa().aplicar(j);
            j.getQuestsAtivas().remove(q);
        }
    }

    public void gerarQuest(Jogador j){
        ArrayList<Quest> quests = qr.buscarTodas();
        int indice = (int) (Math.random() * quests.size());
        this.aceitarQuest(j, quests.get(indice));
    }
}