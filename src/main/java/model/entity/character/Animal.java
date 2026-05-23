package main.java.model.entity.character;

import main.java.model.entity.world.LEDS;
import main.java.model.entity.world.Local;
import main.java.model.entity.world.Sala;

import java.util.ArrayList;

// Animal presente no mundo do jogo
public class Animal extends Personagem {

    // Constrói o animal
    public Animal(String nome, Local local, ArrayList<String> dialogos) {
        super(nome, local, dialogos);
    }

    // Animais não podem acessar salas de aula nem o LEDS
    @Override
    public boolean podeAcessar(Local l) {
        return !(l instanceof Sala) && !(l instanceof LEDS);
    }

    // Aumenta a motivação do jogador ao fazer carinho no animal
    private void receberCarinho(Jogador jogador) {
        jogador.setMotivacao(jogador.getMotivacao() + 10);
    }

    // A interação específica do animal é receber carinho do jogador
    @Override
    public void interacaoEspecifica(Jogador jogador) {
        this.receberCarinho(jogador);
    }
}