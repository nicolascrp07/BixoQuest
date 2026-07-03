package main.java.model.repository;

import main.java.model.entity.academic.Disciplina;
import main.java.model.entity.character.Jogador;
import main.java.model.entity.event.Quest;
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

    private DisciplinaRepository discRepo;
    private EventoRepository eventoRepo;
    private QuestRepository questRepo;
    private String diretorioSaves = "saves/";

    public PartidaRepository(DisciplinaRepository discRepo, EventoRepository eventoRepo, QuestRepository questRepo) {
        this.discRepo = discRepo;
        this.eventoRepo = eventoRepo;
        this.questRepo = questRepo;
    }

    public void setDiretorio(String d) { this.diretorioSaves = d; }

    // Converte a Partida atual em PartidaDTO e grava o JSON no diretório de saves
    public void salvarPartida(Partida partida) {
        try {
            Files.createDirectories(Path.of(diretorioSaves));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<String> nomesAprovadas = new ArrayList<>(), nomesDisciplinas = new ArrayList<>();
        for (Disciplina d : partida.getJogador().getHistoricoAprovadas()) nomesAprovadas.add(d.getNome());
        for (Disciplina d : partida.getJogador().getDisciplinas()) nomesDisciplinas.add(d.getNome());

        ArrayList<String> nomesLocaisInteragidos = new ArrayList<>();
        for (Local l : partida.getUniversidade().getLocais()) {
            if (l.isInteragiu()) {
                nomesLocaisInteragidos.add(l.getNome());
            }
        }

        ArrayList<String> qNomes = new ArrayList<>();
        for (Quest q : partida.getJogador().getQuestsAtivas()) qNomes.add(q.getNome());

        PartidaDTO save = new PartidaDTO(
                partida.getId().toString(), partida.getJogador().getNome(), partida.getJogador().getEnergia(),
                partida.getJogador().getNivelConhecimento(), partida.getJogador().getMotivacao(),
                partida.getJogador().getSaude(), partida.getJogador().getDinheiro(),
                partida.getJogador().getDesempenhoAcademico(), partida.getTempo().getSemanaAtual(),
                partida.getTempo().getSemestreAtual(), partida.getJogador().getLocalAtual().getNome(),
                nomesAprovadas, nomesDisciplinas, nomesLocaisInteragidos, qNomes,
                partida.getJogador().getCaminhoAvatar(),
                partida.getJogador().getCaminhoIconeAvatar()
        );

        Gson gson = new Gson();
        String caminhoArquivo = Path.of(diretorioSaves, partida.getId().toString() + ".json").toString();
        try (FileWriter escritor = new FileWriter(caminhoArquivo)) { gson.toJson(save, escritor); }
        catch (IOException e) { System.out.println(e.getMessage()); }
    }

    // Reconstrói uma Partida a partir do DTO, remontando local atual, flags de interação, histórico e quests
    private Partida dtoToPartida(PartidaDTO dto, Universidade universidade) {
        Tempo tempo = new Tempo(dto.getSemanaAtual(), dto.getSemestreAtual());

        Local localAtual = null;

        if (universidade.getNome().equalsIgnoreCase(dto.getNomeLocalAtual())) {
            localAtual = universidade;
        }

        for (Local l : universidade.getLocais()) {
            if (localAtual == null && l.getNome().equalsIgnoreCase(dto.getNomeLocalAtual())) {
                localAtual = l;
            }

            if (dto.getLocaisInteragidosNomes() != null && dto.getLocaisInteragidosNomes().contains(l.getNome())) {
                l.setInteragiu(true);
            } else {
                l.setInteragiu(false);
            }
        }

        ArrayList<Disciplina> historico = new ArrayList<>(), ativas = new ArrayList<>();
        for (String nome : dto.getHistoricoAprovadasNomes()) historico.add(discRepo.buscarPorNome(nome));
        for (String nome : dto.getDisciplinasAtuaisNomes()) ativas.add(discRepo.buscarPorNome(nome));

        Jogador jogador = new Jogador(dto.getNomeJogador(), dto.getEnergia(), dto.getNivelConhecimento(), dto.getMotivacao(),
                dto.getSaude(), dto.getDinheiro(), dto.getDesempenhoAcademico(), localAtual);
        jogador.setHistoricoAprovadas(historico);
        jogador.setDisciplinas(ativas);

        jogador.setCaminhoAvatar(dto.getCaminhoAvatar());
        jogador.setCaminhoIconeAvatar(dto.getCaminhoIconeAvatar());

        if (dto.getQuestsAtivasNomes() != null) {
            for (String nomeQuest : dto.getQuestsAtivasNomes()) {
                Quest q = questRepo.buscarPorNome(nomeQuest);
                jogador.addQuest(q);
            }
        }

        return new Partida(jogador, tempo, universidade, null, false, eventoRepo.buscarTodos(),
                discRepo.buscarTodas(), UUID.fromString(dto.getId()));
    }

    // Lê todos os arquivos .json do diretório de saves e retorna as partidas reconstruídas
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
        }
        return saves;
    }

    // Procura entre os saves existentes um jogador com o nome informado
    public Partida buscarPorNomeDoJogador(String nome, Universidade uni) {
        ArrayList<Partida> jogosSalvos = this.buscarJogosSalvos(uni);
        for (Partida p : jogosSalvos)
            if (p.getJogador() != null && p.getJogador().getNome().equalsIgnoreCase(nome)) return p;
        return null;
    }

    // Remove o arquivo de save correspondente à partida, se existir
    public boolean deletarSave(Partida partida) {
        String caminhoArquivo = Path.of(diretorioSaves, partida.getId().toString() + ".json").toString();
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            return arquivo.delete();
        }
        return false;
    }
}