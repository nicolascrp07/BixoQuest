package main.java.view.factory;

public interface MapaFactory {
    DisplayScene construirMapaPrincipal();
    DisplayScene construirMapaCantina();
    DisplayScene construirMapaColegiado();
    DisplayScene construirMapaModulo3();
    DisplayScene construirMapaModulo5();
    DisplayScene construirMapaSala();
    DisplayScene construirMapaLEDS();
    DisplayScene construirMapaPontoDeOnibus();
}