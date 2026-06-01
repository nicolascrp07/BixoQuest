package main.java.view.factory;

public interface MenuFactory {
    DisplayScene construirMenuInicial();
    DisplayScene construirMenuIniciarJogo();
    DisplayScene construirMenuCarregarJogo();
    DisplayScene construirMenuConfiguracoes();
}
