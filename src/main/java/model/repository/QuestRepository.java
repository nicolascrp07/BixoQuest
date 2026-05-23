package main.java.model.repository;

import main.java.model.entity.event.Quest;
import main.java.model.entity.event.QuestAtributo;
import main.java.model.entity.event.QuestVisita;
import main.java.model.entity.event.Recompensa;
import main.java.model.entity.world.Local;
import main.java.model.entity.world.Universidade;

import java.util.ArrayList;

// Repositório responsável por armazenar e gerenciar as quests do jogo
public class QuestRepository {

    private ArrayList<Quest> qb = new ArrayList<>(); // Base de dados das quests

    // Salva a quest se ela ainda não estiver cadastrada | Retorna null caso contrário
    public Quest salvar(Quest quest) {
        if (!qb.contains(quest)) {
            qb.add(quest);
            return quest;
        }
        return null;
    }

    // Retorna uma cópia da lista com todas as quests cadastradas
    public ArrayList<Quest> buscarTodas() {
        return new ArrayList<>(qb);
    }

    // Instancia e salva todas as quests possíveis do jogo
    public void criarQuestsPadrao(Universidade uni) {

        // Prepara recompensas padronizadas
        Recompensa recPequena = new Recompensa(10.0, 5, 5);
        Recompensa recMedia   = new Recompensa(25.0, 15, 10);
        Recompensa recGrande  = new Recompensa(50.0, 20, 20);

        // Cria as Quests de Atributo (Podemos deixar a origem nula para quests globais)
        this.salvar(new QuestAtributo("Rato de Biblioteca", null, "De cara nos livros! Alcance 80 em conhecimento.", recGrande, QuestAtributo.CONHECIMENTO, 80));
        this.salvar(new QuestAtributo("Zen", null, "Mantenha sua energia no máximo (100) para encarar os desafios do curso.", recPequena, QuestAtributo.ENERGIA, 100));
        this.salvar(new QuestAtributo("Inabalável", null, "Não deixe a universidade te derrubar. Alcance 90 de motivação!", recMedia, QuestAtributo.MOTIVACAO, 90));

        // Localiza os destinos
        Local cantina   = null;
        Local colegiado = null;
        Local leds      = null;

        for (Local l : uni.getLocais()) {
            if (l.getNome().equalsIgnoreCase("Cantina"))   cantina = l;
            if (l.getNome().equalsIgnoreCase("Colegiado")) colegiado = l;
            if (l.getNome().equalsIgnoreCase("LEDS"))      leds = l;
        }

        // Cria as Quests de Visita atrelando aos locais
        if (cantina != null) {
            this.salvar(new QuestVisita("Larica Monstra", null, "Que fome! Vá até a cantina e confira o cardápio.", recPequena, cantina));
        }

        if (colegiado != null) {
            this.salvar(new QuestVisita("Socorro Maeli!", null, "Faça uma visita ao Colegiado. Talvez a Maeli possa te dar uma luz.", recMedia, colegiado));
        }

        if (leds != null) {
            this.salvar(new QuestVisita("Fissurado em Hardware", null, "Dê uma passada no LEDS para checar os protoboards.", recMedia, leds));
        }
    }
}