package tabuleiro;

import Peca.Peca;

public class Tabuleiro {
    private Casa[][] casas;
    private int linhas;
    private int colunas;

    // construtor que inicializa o tabuleiro com um tamanho padrão 8x8
    public Tabuleiro(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.casas = new Casa[linhas][colunas];
        inicializarCasas();
    }

    // metodo para criar e posicionar as casas no tabuleiro
    private void inicializarCasas() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                String coordenada = i + "," + j;

                String corCasa = (i + j) % 2 == 0 ? "CLARA" : "ESCURA";

                casas[i][j] = new Casa(corCasa, coordenada, null);
            }
        }
    }

    // retorna uma casa especifica com base na linha e coluna
    public Casa getCasa(int linha, int coluna) {
        if (linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas) {
            return casas[linha][coluna];
        }
        throw new IndexOutOfBoundsException("Posição inválida no tabuleiro!");
    }

    public boolean simularMovimento(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        return true;
    }

    public boolean deveContinuarJogando(int linhaAtual, int colunaAtual) {
        return false;
    }

    // Getters
    public Casa[][] getCasas() {
        return casas;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }
}