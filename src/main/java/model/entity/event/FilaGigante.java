package main.java.model.entity.event;

import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Cantina;
import main.java.model.entity.world.Local;

// Evento de Fila Gigante
public class FilaGigante extends Evento {

    // Constrói o evento
    public FilaGigante() {
        super("Fila Gigante", "A fila lotou! Está enorme...", 0.2);

        // Escolha de Desistir e sua consequência
        Consequencia cDesistir = new Consequencia(0, -20, 0, 0, -10);
        Escolha desistir = new Escolha("Não enfrentar a fila e desistir.", cDesistir);

        // Escolha de Enfrentar e sua consequência
        Consequencia cEnfrentar = new Consequencia(-15, -10, 0, 0, +5);
        Escolha enfrentar = new Escolha("Ir com tudo e garantir o lanche!", cEnfrentar);

        // Adiciona escolhas na ArrayList do evento
        escolhas.add(enfrentar);
        escolhas.add(desistir);
    }

    // O evento só pode ocorrer na primeira semana do semestre e na cantina
    @Override
    public boolean condicaoOcorrencia(Tempo tempo, Local local) {
        return tempo.getSemanaAtual() == 1 && (local instanceof Cantina);
    }
}