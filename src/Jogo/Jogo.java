package Jogo;

import Jogador.GerenciadorTurno;
import Jogador.Jogador;
import Peca.Cor;
import Peca.Peca;
import Peca.TipoPeca;
import Peca.impl.Cavaleiro;
import Peca.impl.Mago;
import Peca.impl.Soldado;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final Jogador jogadorBranco;
    private final Jogador jogadorPreto;
    private final GerenciadorTurno gerenciadorTurno;
    private boolean capturaObrigatoria;

    public Jogo() {
        this.tabuleiro = new Tabuleiro(8, 8);
        this.jogadorBranco = new Jogador("Reino Branco", 0);
        this.jogadorPreto = new Jogador("Reino Preto", 0);
        this.gerenciadorTurno = new GerenciadorTurno(jogadorBranco, jogadorPreto, jogadorBranco);
        this.capturaObrigatoria = false;

        inicializarCasas();
        inicializarPecas();
    }

    private void inicializarCasas() {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa casa = tabuleiro.getCasa(linha, coluna);
                casa.setX(linha);
                casa.setY(coluna);
            }
        }
    }

    private void inicializarPecas() {
        List<Peca> pecasBrancas = new ArrayList<>();
        List<Peca> pecasPretas = new ArrayList<>();

        pecasBrancas.add(new Soldado(5, 1, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(5, 3, Cor.BRANCA));
        pecasBrancas.add(new Mago(5, 5, Cor.BRANCA));
        pecasBrancas.add(new Soldado(5, 7, Cor.BRANCA));
        pecasBrancas.add(new Soldado(6, 0, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(6, 2, Cor.BRANCA));
        pecasBrancas.add(new Mago(6, 4, Cor.BRANCA));
        pecasBrancas.add(new Soldado(6, 6, Cor.BRANCA));
        pecasBrancas.add(new Soldado(7, 1, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(7, 3, Cor.BRANCA));
        pecasBrancas.add(new Mago(7, 5, Cor.BRANCA));
        pecasBrancas.add(new Soldado(7, 7, Cor.BRANCA));

        pecasPretas.add(new Soldado(0, 0, Cor.VERMELHA));
        pecasPretas.add(new Cavaleiro(0, 2, Cor.VERMELHA));
        pecasPretas.add(new Mago(0, 4, Cor.VERMELHA));
        pecasPretas.add(new Soldado(0, 6, Cor.VERMELHA));
        pecasPretas.add(new Soldado(1, 1, Cor.VERMELHA));
        pecasPretas.add(new Cavaleiro(1, 3, Cor.VERMELHA));
        pecasPretas.add(new Mago(1, 5, Cor.VERMELHA));
        pecasPretas.add(new Soldado(1, 7, Cor.VERMELHA));
        pecasPretas.add(new Soldado(2, 0, Cor.VERMELHA));
        pecasPretas.add(new Cavaleiro(2, 2, Cor.VERMELHA));
        pecasPretas.add(new Mago(2, 4, Cor.VERMELHA));
        pecasPretas.add(new Soldado(2, 6, Cor.VERMELHA));

        for (Peca peca : pecasBrancas) {
            vincularTabuleiro(peca);
            localizarCasa(peca).colocarPeca(peca);
        }

        for (Peca peca : pecasPretas) {
            vincularTabuleiro(peca);
            localizarCasa(peca).colocarPeca(peca);
        }
    }

    private void vincularTabuleiro(Peca peca) {
        try {
            Field field = peca.getClass().getDeclaredField("tabuleiro");
            field.setAccessible(true);
            field.set(peca, tabuleiro);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Não foi possível vincular o tabuleiro à peça: " + peca.getClass().getSimpleName(), e);
        }
    }

    private Casa localizarCasa(Peca peca) {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa casa = tabuleiro.getCasa(linha, coluna);
                if (casa.getPeca() == peca) {
                    return casa;
                }
            }
        }
        throw new IllegalArgumentException("Peça não localizada no tabuleiro");
    }

    public boolean mover(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Casa origem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
        Casa destino = tabuleiro.getCasa(linhaDestino, colunaDestino);

        if (origem == null || destino == null || origem.getPeca() == null) {
            return false;
        }

        Peca peca = origem.getPeca();
        Jogador vez = gerenciadorTurno.getVez();

        if (vez == jogadorBranco && peca.getCor() != Cor.BRANCA) {
            return false;
        }

        if (vez == jogadorPreto && peca.getCor() != Cor.VERMELHA) {
            return false;
        }

        if (destino.getPeca() != null && destino.getPeca().getCor() == peca.getCor()) {
            return false;
        }

        boolean movimentoValido;

        if (destino.getPeca() == null) {
            movimentoValido = peca.podeMover(origem, destino);
        } else {
            movimentoValido = peca.podeCapturar(origem, destino);
        }

        if (!movimentoValido) {
            return false;
        }

        origem.removerPeca();
        destino.colocarPeca(peca);
        gerenciadorTurno.mudarTurno();
        verificarEstadoDoJogo();
        return true;
    }

    private void verificarEstadoDoJogo() {
        if (!existePecaDoTipo(Cor.BRANCA)) {
            return;
        }

        if (!existePecaDoTipo(Cor.VERMELHA)) {
            return;
        }
    }

    private boolean existePecaDoTipo(Cor cor) {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa casa = tabuleiro.getCasa(linha, coluna);
                if (casa.getPeca() != null && casa.getPeca().getCor() == cor) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCapturaObrigatoria() {
        return capturaObrigatoria;
    }

    public void setCapturaObrigatoria(boolean capturaObrigatoria) {
        this.capturaObrigatoria = capturaObrigatoria;
    }

    public Jogador getJogadorBranco() {
        return jogadorBranco;
    }

    public Jogador getJogadorPreto() {
        return jogadorPreto;
    }

    public GerenciadorTurno getGerenciadorTurno() {
        return gerenciadorTurno;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public TipoPeca getTipoPeca(Casa casa) {
        if (casa == null || casa.getPeca() == null) {
            return null;
        }
        return casa.getPeca().getTipo();
    }
}
