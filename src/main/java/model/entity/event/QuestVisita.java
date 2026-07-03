package main.java.model.entity.event;

import main.java.model.entity.character.Jogador;
import main.java.model.entity.character.Personagem;
import main.java.model.entity.world.Local;

// Quest para visitar um local específico do mapa
public class QuestVisita extends Quest {

    private Local localDestino; // Local que o jogador precisa visitar para concluir a quest

    // Constrói a quest
    public QuestVisita(String nome, Personagem origem, String objetivo, Recompensa recompensa, Local localDestino) {
        super(nome, origem, objetivo, recompensa);
        this.localDestino = localDestino;
    }

    // Verifica se o jogador está no local de destino | Retorna falso se o local atual for nulo
    @Override
    public boolean checarProgresso(Jogador jogador) {
        if (jogador.getLocalAtual() == null || localDestino == null) {
            return false;
        }

        return jogador.getLocalAtual().getNome().equalsIgnoreCase(localDestino.getNome());
    }

    // Retorna o local que o jogador precisa visitar
    public Local getLocalDestino() { return localDestino; }
}