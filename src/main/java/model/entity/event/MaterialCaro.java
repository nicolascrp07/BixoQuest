package main.java.model.entity.event;

import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.AmbienteAula;
import main.java.model.entity.world.Local;

// Evento de Material Caro
public class MaterialCaro extends Evento {

    // Constrói o evento
    public MaterialCaro() {
        super("Material Caro", "Opa! O material exigido para a aula foi cobrado! Bem caro por sinal...", 0.3);

        // Escolha de Comprar e sua consequência
        Consequencia cComprar = new Consequencia(0, -10, -20, 0, 0);
        Escolha comprar = new Escolha("Comprar o material.", cComprar);

        // Escolha de Pedir Emprestado e sua consequência
        Consequencia cEmprestado = new Consequencia(-10, -5, 0, -10, 0);
        Escolha emprestado = new Escolha("Esperar que um colega acabe antes com o material para solicitá-lo emprestado.", cEmprestado);

        // Adiciona escolhas na ArrayList do evento
        escolhas.add(comprar);
        escolhas.add(emprestado);
    }

    // Evento pode ocorrer em qualquer semana, porém somente nos ambientes de aula
    @Override
    public boolean condicaoOcorrencia(Tempo tempo, Local local) {
        return local instanceof AmbienteAula;
    }

}