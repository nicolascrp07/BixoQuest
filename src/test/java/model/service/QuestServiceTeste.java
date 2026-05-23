package test.java.model.service;

import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.QuestAtributo;
import main.java.model.entity.event.QuestVisita;
import main.java.model.entity.event.Recompensa;
import main.java.model.entity.world.Colegiado;
import main.java.model.entity.world.Universidade;
import main.java.model.repository.QuestRepository;
import main.java.model.service.QuestService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// Testa o service das quests
public class QuestServiceTeste {
    QuestService questService;
    QuestAtributo questAtributo;
    QuestVisita questVisita;
    Jogador jogador;
    Recompensa recompensa;

    // Constrói o sistema de teste das quests antes de cada teste
    @Before
    public void setUp(){
        questService = new QuestService(new QuestRepository());
        recompensa = new Recompensa(15.0, 10, 5);
        questAtributo = new QuestAtributo("Alcançar 60 em Nível de Conhecimento", null, "De cara nos livros! Alcance 60 em conhecimento!", recompensa, QuestAtributo.CONHECIMENTO, 60);
        questVisita = new QuestVisita("Visite a cantina", null, "Que larica! Vá até a cantina e confira o cardápio.", recompensa, new Colegiado("Colegiado Teste", "Instância do Colegiado"));
        jogador = new Jogador("Nicolas", 100, 50, 50, 100, 50.0, 0.0, new Universidade("UEFS", "Universidade"));
    }

    // Confirma que duas quests distintas são adicionadas corretamente a lista de quests ativas
    @Test
    public void adicionarNovasQuests() {
        questService.aceitarQuest(jogador, questVisita);
        questService.aceitarQuest(jogador, questAtributo);
        assertTrue(jogador.getQuestsAtivas().contains(questVisita));
        assertTrue(jogador.getQuestsAtivas().contains(questAtributo));
    }

    // Confirma que a mesma quest não é adicionada duas vezes à lista de questsAtivas
    @Test
    public void naoDuplicarQuests(){
        questService.aceitarQuest(jogador, questVisita);
        questService.aceitarQuest(jogador, questVisita);
        assertEquals(1, jogador.getQuestsAtivas().size());
    }

    // Confirma que a QuestAtributo não é marcada como concluída quando o critério não foi atingido
    @Test
    public void questAtributoNaoConcluida() {
        questService.aceitarQuest(jogador, questAtributo);
        questService.verificarAndamento(jogador);
        assertFalse(questAtributo.isStatusConcluida());
    }

    // Confirma que a QuestAtributo é marcada como concluída quando o nível de conhecimento atinge o valor exigido
    @Test
    public void questAtributoConcluida() {
        jogador.setNivelConhecimento(65);
        questService.aceitarQuest(jogador, questAtributo);
        questService.verificarAndamento(jogador);
        assertTrue(questAtributo.isStatusConcluida());
    }

    // Confirma que a QuestVisita não é marcada como concluída quando o jogador ainda não visitou o local
    @Test
    public void questVisitaNaoConcluida() {
        questService.aceitarQuest(jogador, questVisita);
        questService.verificarAndamento(jogador);
        assertFalse(questVisita.isStatusConcluida());
    }

    // Confirma que a QuestVisita é marcada como concluída quando o jogador está no local
    @Test
    public void questVisitaConcluida() {
        jogador.setLocalAtual(questVisita.getLocalDestino());
        questService.aceitarQuest(jogador, questVisita);
        questService.verificarAndamento(jogador);
        assertTrue(questVisita.isStatusConcluida());
    }

    // Ao entregar uma quest concluída, aplica a recompensa nos atributos e remove a quest da lista ativa
    @Test
    public void aplicarQuestConcluida() {
        jogador.setNivelConhecimento(65);
        questService.aceitarQuest(jogador, questAtributo);
        questService.verificarAndamento(jogador);
        questService.entregarQuest(jogador, questAtributo);
        assertEquals(70, jogador.getNivelConhecimento());
        assertEquals(60, jogador.getMotivacao());
        assertEquals(65.0, jogador.getDinheiro(), 0.0001);
        assertEquals(0, jogador.getQuestsAtivas().size());
    }

    // Tentar entregar uma quest não concluída não aplica recompensa e mantém a quest na lista ativa
    @Test
    public void aplicarQuestNaoConcluida() {
        questService.aceitarQuest(jogador, questAtributo);
        questService.verificarAndamento(jogador);
        questService.entregarQuest(jogador, questAtributo);
        assertEquals(50, jogador.getNivelConhecimento());
        assertEquals(50, jogador.getMotivacao());
        assertEquals(50.0, jogador.getDinheiro(), 0.0001);
        assertEquals(1, jogador.getQuestsAtivas().size());
    }
}