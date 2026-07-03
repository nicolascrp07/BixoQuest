package main.java.model.entity.event;

import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Local;

// Evento de Greve dos Professores
public class Greve extends Evento {

    // Constrói o evento
    public Greve() {
        super("Greve", "Os professores anunciaram uma greve inesperada. As aulas foram suspensas por tempo indeterminado.", 0.05);

        // Escolha de Descansar e sua consequência
        Consequencia cFolga = new Consequencia(10, 20, -10, -15, 10);
        Escolha folga = new Escolha("Aproveitar a greve e descansar.", cFolga);

        // Escolha de Estudar e sua consequência
        Consequencia cEstudar = new Consequencia(-10, 5, 10, 20, -5);
        Escolha estudar = new Escolha("Estudar durante este periodo", cEstudar);

        // Escolha de Participar dos Protestos e sua consequência
        Consequencia cParticipar = new Consequencia(-15, 10, -5, 10, -15);
        Escolha participar = new Escolha("Participar dos protestos estudantis.", cParticipar);

        // Adiciona escolhas na ArrayList do evento
        escolhas.add(folga);
        escolhas.add(estudar);
        escolhas.add(participar);
    }

    // O evento só pode ocorrer a partir do segundo semestre
    @Override
    public boolean condicaoOcorrencia(Tempo tempo, Local local) {
        return tempo.getSemestreAtual() >= 2;
    }
}