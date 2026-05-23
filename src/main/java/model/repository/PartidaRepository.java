package main.java.model.repository;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.game.Partida;
import main.java.model.entity.game.Tempo;
import main.java.model.entity.world.Local;
import main.java.model.entity.world.Universidade;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

// Repositório responsável por persistir e recuperar partidas salvas em disco no formato JSON
public class PartidaRepository {

    private DisciplinaRepository discRepo; // Usado para reconstruir as disciplinas
    private EventoRepository eventoRepo;   // Usado para reinjetar os eventos
    private String diretorioSaves = "saves/"; // Caminho da pasta onde os arquivos JSON de save são armazenados

    // Constrói o repository com as dependências necessárias para reconstruir uma partida a partir do disco
    public PartidaRepository(DisciplinaRepository discRepo, EventoRepository eventoRepo) {
        this.discRepo = discRepo;
        this.eventoRepo = eventoRepo;
    }

    // Permite sobrescrever o diretório de saves
    public void setDiretorio(String d) { this.diretorioSaves = d; }

    // Serializa a partida em um PartidaDTO e grava em disco como {uuid}.json dentro da pasta de saves
    public void salvarPartida(Partida partida) {

        // Garante que a pasta de saves existe antes de tentar escrever
        try {
            Files.createDirectories(Path.of(diretorioSaves));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Extrai apenas os nomes das disciplinas pertencentes ao jogador para evitar serializar todas elas na grade
        ArrayList<String> nomesAprovadas = new ArrayList<>(), nomesDisciplinas = new ArrayList<>();
        for (Disciplina d : partida.getJogador().getHistoricoAprovadas()) nomesAprovadas.add(d.getNome());
        for (Disciplina d : partida.getJogador().getDisciplinas()) nomesDisciplinas.add(d.getNome());

        // Monta o DTO (Data Transfer Object) com os dados que representam o progresso do jogador
        PartidaDTO save = new PartidaDTO(partida.getId().toString(), partida.getJogador().getNome(), partida.getJogador().getEnergia(),
                partida.getJogador().getNivelConhecimento(), partida.getJogador().getMotivacao(), partida.getJogador().getSaude(), partida.getJogador().getDinheiro(),
                partida.getJogador().getDesempenhoAcademico(), partida.getTempo().getSemanaAtual(), partida.getTempo().getSemestreAtual(), partida.getJogador().getLocalAtual().getNome(),
                nomesAprovadas, nomesDisciplinas);

        // Serializa o DTO para JSON e grava no arquivo correspondente ao UUID da partida
        Gson gson = new Gson();
        String caminhoArquivo = Path.of(diretorioSaves, partida.getId().toString() + ".json").toString();
        try (FileWriter escritor = new FileWriter(caminhoArquivo)) { gson.toJson(save, escritor); }
        catch (IOException e) { System.out.println(e.getMessage()); }
    }

    // Reconstrói uma Partida completa a partir de um PartidaDTO, reinjetando o mundo fixo já recriado
    private Partida dtoToPartida(PartidaDTO dto, Universidade universidade) {
        Tempo tempo = new Tempo(dto.getSemanaAtual(), dto.getSemestreAtual());

        // Localiza o objeto Local correspondente ao nome salvo no DTO
        Local localAtual = null;
        for (Local l : universidade.getLocais()) if (l.getNome().equalsIgnoreCase(dto.getNomeLocalAtual())) { localAtual = l; break; }

        // Recupera os objetos Disciplina a partir dos nomes salvos no DTO
        ArrayList<Disciplina> historico = new ArrayList<>(), ativas = new ArrayList<>();
        for (String nome : dto.getHistoricoAprovadasNomes()) historico.add(discRepo.buscarPorNome(nome));
        for (String nome : dto.getDisciplinasAtuaisNomes()) ativas.add(discRepo.buscarPorNome(nome));

        // Reconstrói o jogador com os atributos salvos e injeta seu histórico e grade ativa
        Jogador jogador = new Jogador(dto.getNomeJogador(), dto.getEnergia(), dto.getNivelConhecimento(), dto.getMotivacao(),
                dto.getSaude(), dto.getDinheiro(), dto.getDesempenhoAcademico(), localAtual);
        jogador.setHistoricoAprovadas(historico);
        jogador.setDisciplinas(ativas);

        // Monta e retorna a Partida com o mundo fixo recriado e o UUID original preservado
        return new Partida(jogador, tempo, universidade, null, false, eventoRepo.buscarTodos(),
                discRepo.buscarTodas(), UUID.fromString(dto.getId()));
    }

    // Lê todos os arquivos JSON da pasta de saves e os converte em objetos Partida
    public ArrayList<Partida> buscarJogosSalvos(Universidade uni) {

        ArrayList<Partida> saves = new ArrayList<>();
        Gson gson = new Gson();
        File pasta = new File(diretorioSaves);
        File[] arquivosNaPasta = pasta.listFiles();

        if (arquivosNaPasta != null) {
            for (File arquivo : arquivosNaPasta) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".json")) {
                    try (FileReader leitor = new FileReader(arquivo)) {
                        PartidaDTO saveRecuperado = gson.fromJson(leitor, PartidaDTO.class);
                        if (saveRecuperado != null) {
                            saves.add(dtoToPartida(saveRecuperado, uni));
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            return saves;
        } else {
            return saves; // Retorna lista vazia se a pasta ainda não existir
        }
    }

    // Percorre todos os saves e retorna a primeira partida cujo jogador tenha o nome informado
    public Partida buscarPorNomeDoJogador(String nome, Universidade uni) {
        ArrayList<Partida> jogosSalvos = this.buscarJogosSalvos(uni);
        for (Partida p : jogosSalvos)
            if (p.getJogador() != null && p.getJogador().getNome().equalsIgnoreCase(nome)) return p;
        return null;
    }

    // Apaga o arquivo JSON correspondente à partida do disco
    public boolean deletarSave(Partida partida) {
        String caminhoArquivo = Path.of(diretorioSaves, partida.getId().toString() + ".json").toString();
        File arquivo = new File(caminhoArquivo);

        if (arquivo.exists()) {
            return arquivo.delete(); // Retorna true se o arquivo foi deletado com sucesso
        }
        return false;
    }
}