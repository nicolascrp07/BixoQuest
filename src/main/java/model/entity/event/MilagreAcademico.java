package main.java.model.entity.event;

import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.AmbienteAula;
import main.java.model.entity.world.Local;

// Evento de Milagre Acadêmico
public class MilagreAcademico extends Evento {

    // Constrói o evento
    public MilagreAcademico() {
        super("Milagre Acadêmico", "Mesmo sem estar preparado, sua nota foi surpreendentemente alta.", 0.01);

        // Escolha de Aceitar o Resultado e sua consequência
        Consequencia cAceitar = new Consequencia(+10, +10, 0, 0, 0);
        Escolha aceitar = new Escolha("Aceitar o resultado e seguir em frente.", cAceitar);

        // Escolha de Questionar o Professor e sua consequência
        Consequencia cQuestionar = new Consequencia(-10, -15, 0, +10, -5);
        Escolha questionar = new Escolha("Questionar o professor sobre a nota.", cQuestionar);

        // Adiciona escolhas na ArrayList do evento
        escolhas.add(aceitar);
        escolhas.add(questionar);
    }

    // O evento só pode ocorrer na semana de provas e nos ambientes de aula
    @Override
    public boolean condicaoOcorrencia(Tempo tempo, Local local) {
        return tempo.getSemanaAtual() == 4 && local instanceof AmbienteAula;
    }
}