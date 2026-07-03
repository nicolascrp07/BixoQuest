package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.controller.BixoController;
import main.java.controller.GerenciadorCenas;
import main.java.model.repository.*;
import main.java.model.service.*;

// Classe principal que isntancia toda a base do jogo
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Instancia os repositórios
        ProfessorRepository   profRepo   = new ProfessorRepository();
        DisciplinaRepository  discRepo   = new DisciplinaRepository();
        EventoRepository      eventoRepo = new EventoRepository();
        QuestRepository       questRepo  = new QuestRepository();
        UniversidadeRepository uniRepo   = new UniversidadeRepository();
        PartidaRepository     partRepo   = new PartidaRepository(discRepo, eventoRepo, questRepo);

        // Instancia os services
        AcademicoService academicoService = new AcademicoService();
        QuestService     questService     = new QuestService(questRepo);
        ExplorarService  explorarService  = new ExplorarService(questService);
        EventoService    eventoService    = new EventoService();
        PartidaService   partidaService   = new PartidaService(
                academicoService, explorarService, eventoService,
                profRepo, discRepo, uniRepo, partRepo, eventoRepo, questRepo
        );

        // Instancia o controller principal do jogo
        BixoController controller = new BixoController(
                partidaService, academicoService, explorarService, eventoService, questService
        );

        // Instancia o Gerenciador de Cenas, aplica as restrições da janela e exibe o jogo
        GerenciadorCenas sm = GerenciadorCenas.getInstance();
        sm.setStage(stage);
        sm.setBixoController(controller);

        stage.setTitle("BixoQuest");
        stage.setResizable(false); // Trava o redimensionamento para manter as artes intactas

        GerenciadorCenas.getInstance().irParaMenuInicial();
        stage.show();
    }

    // Chama a thread nativa do JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}