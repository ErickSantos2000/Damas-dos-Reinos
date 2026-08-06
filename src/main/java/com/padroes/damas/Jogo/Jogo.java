package com.padroes.damas.Jogo;

import com.padroes.damas.Jogador.GerenciadorTurno;
import com.padroes.damas.Jogador.Jogador;
import com.padroes.damas.Peca.Cor;
import com.padroes.damas.Peca.Peca;
import com.padroes.damas.Peca.TipoPeca;
import com.padroes.damas.tabuleiro.Casa;
import com.padroes.damas.tabuleiro.Tabuleiro;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final Jogador jogadorBranco;
    private final Jogador jogadorPreto;
    private final GerenciadorTurno gerenciadorTurno;
    private boolean capturaObrigatoria;
    private boolean encerrado;
    private Jogador vencedor;
    private String motivoEncerramento;

    public Jogo(boolean capturaObrigatoria) {
        this.tabuleiro = new Tabuleiro(8, 8);
        this.jogadorBranco = new Jogador("jogador Branco", 0);
        this.jogadorPreto = new Jogador("Reino Negro", 0);
        this.gerenciadorTurno = new GerenciadorTurno(jogadorBranco, jogadorPreto, jogadorBranco);
        this.capturaObrigatoria = capturaObrigatoria;
        this.encerrado = false;
    }

    public boolean mover(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        if (encerrado) return false;
        Casa origem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
        Casa destino;

        origem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
        destino = tabuleiro.getCasa(linhaDestino, colunaDestino);


        if (origem.getPeca() == null) return false;

        Peca peca = origem.getPeca();
        Jogador vez = gerenciadorTurno.getVez();

        if (vez == jogadorBranco && peca.getCor() != Cor.BRANCA) return false;

        if (vez == jogadorPreto && peca.getCor() != Cor.PRETA) return false;

        if (destino.temPeca() && destino.getPeca().getCor() == peca.getCor()) return false;

        boolean destinoVazio = !destino.temPeca();
        boolean captura = peca.podeCapturar(origem, destino, tabuleiro);
        boolean capturaPorSalto = destinoVazio && captura;
        boolean movimentoValido = destinoVazio
                ? peca.podeMover(origem, destino, tabuleiro) || capturaPorSalto
                : captura;

        if (!movimentoValido) return false;

        if (capturaObrigatoria && existeCapturaDisponivel(peca.getCor()) && !captura) return false;

        // O Mago captura a primeira peça inimiga visível na diagonal sem sair
        // da casa de origem; para as demais peças, a captura acompanha o movimento.
        if (peca.getTipo() == TipoPeca.MAGO && captura) {
            destino.removerPeca();
            gerenciadorTurno.mudarTurno();
            verificarEstadoDoJogo();
            return true;
        }

        if (capturaPorSalto) removerPecaCapturadaNoSalto(origem, destino);

        origem.removerPeca();
        destino.colocarPeca(peca);
        promoverSoldadoSeNecessario(destino);
        gerenciadorTurno.mudarTurno();
        verificarEstadoDoJogo();
        return true;
    }

    private void removerPecaCapturadaNoSalto(Casa origem, Casa destino) {
        int meioX = origem.getX() + (destino.getX() - origem.getX()) / 2;
        int meioY = origem.getY() + (destino.getY() - origem.getY()) / 2;
        tabuleiro.getCasa(meioX, meioY).removerPeca();
    }

    private void promoverSoldadoSeNecessario(Casa destino) {
        Peca peca = destino.getPeca();
        peca.promover(destino, tabuleiro);
    }

    private void verificarEstadoDoJogo() {
        Cor corDaVez = corDoJogador(gerenciadorTurno.getVez());
        Jogador adversario = adversarioDe(gerenciadorTurno.getVez());

        if (!existePecaDoTipo(corDaVez)) {
            finalizarPartida(adversario, "todas as peças adversárias foram eliminadas");
        } else if (!temMovimentoValido(corDaVez)) {
            finalizarPartida(adversario, "o adversário não possui movimentos válidos");
        }
    }

    private boolean existeCapturaDisponivel(Cor cor) {
        return existeAcaoValida(cor, true);
    }

    private boolean temMovimentoValido(Cor cor) {
        return existeAcaoValida(cor, false);
    }

    private boolean existeAcaoValida(Cor cor, boolean somenteCapturas) {
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                Casa origem = tabuleiro.getCasa(linha, coluna);
                Peca peca = origem.getPeca();
                if (peca == null || peca.getCor() != cor) {
                    continue;
                }

                for (int destinoLinha = 0; destinoLinha < tabuleiro.getLinhas(); destinoLinha++) {
                    for (int destinoColuna = 0; destinoColuna < tabuleiro.getColunas(); destinoColuna++) {
                        Casa destino = tabuleiro.getCasa(destinoLinha, destinoColuna);
                        if (destino.temPeca() && destino.getPeca().getCor() == cor) {
                            continue;
                        }

                        boolean captura = peca.podeCapturar(origem, destino, tabuleiro);
                        if (captura || (!somenteCapturas && !destino.temPeca()
                                && peca.podeMover(origem, destino, tabuleiro))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void finalizarPartida(Jogador vencedor, String motivo) {
        this.encerrado = true;
        this.vencedor = vencedor;
        this.motivoEncerramento = motivo;
    }

    private Cor corDoJogador(Jogador jogador) {
        return jogador == jogadorBranco ? Cor.BRANCA : Cor.PRETA;
    }

    private Jogador adversarioDe(Jogador jogador) {
        return jogador == jogadorBranco ? jogadorPreto : jogadorBranco;
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

    public GerenciadorTurno getGerenciadorTurno() {
        return gerenciadorTurno;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public Jogador getVencedor() {
        return vencedor;
    }

    public String getMotivoEncerramento() {
        return motivoEncerramento;
    }
}