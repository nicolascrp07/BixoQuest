package main.java.model.service;

import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Quest;
import main.java.model.repository.QuestRepository;
import main.java.model.exception.QuestJaAtivaException;

import java.util.ArrayList;

// Service responsável por delegar, monitorar e consolidar o fluxo de conclusão e de recompensas das quests
public class QuestService {

    private QuestRepository qr;

    public QuestService(QuestRepository qr){
        this.qr = qr;
    }

    // Atribui a missão ao jogador e reconsidera caso o mesmo já esteja executando-a
    private void aceitarQuest(Jogador j, Quest q) throws QuestJaAtivaException {
        if (j.getQuestsAtivas().contains(q)) {
            throw new QuestJaAtivaException("Você já está com esta missão em andamento!");
        }
        q.iniciar(j);
    }

    // Faz a varredura nas missões ativas e desencadeia a conclusão caso o objetivo seja alcançado
    public void verificarAndamento(Jogador j) {
        ArrayList<Quest> questsParaEntregar = new ArrayList<>();

        // Identifica e sinaliza as quests concluídas
        for (Quest quest : j.getQuestsAtivas()) {
            if (!quest.isStatusConcluida()) {
                if (quest.checarProgresso(j)) {
                    quest.setStatusConcluida(true);
                    questsParaEntregar.add(quest);
                }
            }
        }

        // Entrega as recompensas isoladamente para evitar concorrência na lista
        for (Quest quest : questsParaEntregar) {
            this.entregarQuest(j, quest);
            System.out.println("Missão concluída e recompensa entregue: " + quest.getNome());
        }
    }

    // Atribui a recompensa nos atributos do jogador e exclui a missão do array
    public void entregarQuest(Jogador j, Quest q) {
        if (q.isStatusConcluida()) {
            q.getRecompensa().aplicar(j);
            j.getQuestsAtivas().remove(q);
        }
    }

    // Seleciona de forma aleatória uma missão livre e força o aceite
    public void gerarQuest(Jogador j) throws QuestJaAtivaException {
        // Bloqueia múltiplas quests simultâneas
        if (!j.getQuestsAtivas().isEmpty()) {
            throw new QuestJaAtivaException("Você já possui uma missão em andamento! Termine-a primeiro para aceitar outra.");
        }

        ArrayList<Quest> quests = qr.buscarTodas();

        // Filtra quests não concluídas no repositório
        ArrayList<Quest> questsDisponiveis = new ArrayList<>();
        for (Quest q : quests) {
            if (!q.isStatusConcluida()) {
                questsDisponiveis.add(q);
            }
        }

        if (questsDisponiveis.isEmpty()) {
            throw new QuestJaAtivaException("Não há mais missões disponíveis na universidade!");
        }

        // Calcula índice e repassa a quest sorteada
        int indiceSorteado = (int) (Math.random() * questsDisponiveis.size());
        Quest questSorteada = questsDisponiveis.get(indiceSorteado);
        aceitarQuest(j, questSorteada);
    }
}