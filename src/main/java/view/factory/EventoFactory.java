package main.java.view.factory;

public interface EventoFactory {
    DisplayScene construirEventoFormatura();
    DisplayScene construirEventoGameOver();
    DisplayScene construirEventoFilaGigante();
    DisplayScene construirEventoGreve();
    DisplayScene construirEventoProvaSurpresa();
    DisplayScene construirEventoMilagreAcademico();
    DisplayScene construirEventoMaterialCaro();
    DisplayScene construirEventoProva();
    DisplayScene construirEventoInteracaoNPC();
    DisplayScene construirEventoResumoSemanal();
}