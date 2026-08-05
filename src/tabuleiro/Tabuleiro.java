package tabuleiro;

import Peca.Peca;
import Peca.Cor;
import Peca.impl.Cavaleiro;
import Peca.impl.Mago;
import Peca.impl.Soldado;

import java.util.ArrayList;
import java.util.List;

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
        inicializarPecas();
    }

    // metodo para criar e posicionar as casas no tabuleiro
    public void inicializarCasas() {
        for (int linha = 0; linha < this.getLinhas(); linha++) {
            for (int coluna = 0; coluna < this.getColunas(); coluna++) {
                String corCasa = (linha + coluna) % 2 == 0 ? "CLARA" : "ESCURA";
                casas[linha][coluna] = new Casa(corCasa, linha, coluna, null);
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

    private void inicializarPecas() {
        List<Peca> pecasBrancas = new ArrayList<>();
        List<Peca> pecasVermelhas = new ArrayList<>();

        // vermelhas
        pecasVermelhas.add(new Mago(0, 3, Cor.VERMELHA));
        pecasVermelhas.add(new Mago(1, 4, Cor.VERMELHA));
        pecasVermelhas.add(new Mago(2, 3, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(0, 0, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(1, 1, Cor.VERMELHA));
        pecasVermelhas.add(new Cavaleiro(2, 0, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(1, 7, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(2, 6, Cor.VERMELHA));
        pecasVermelhas.add(new Soldado(0, 6, Cor.VERMELHA));

        pecasBrancas.add(new Mago(6, 3, Cor.BRANCA));
        pecasBrancas.add(new Mago(5, 4, Cor.BRANCA));
        pecasBrancas.add(new Mago(7, 4, Cor.BRANCA));
        pecasBrancas.add(new Soldado(6, 6, Cor.BRANCA));
        pecasBrancas.add(new Soldado(5, 7, Cor.BRANCA));
        pecasBrancas.add(new Soldado(7, 7, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(5, 1, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(6, 0, Cor.BRANCA));
        pecasBrancas.add(new Cavaleiro(7, 1, Cor.BRANCA));

        posicionarPecas(pecasBrancas);
        posicionarPecas(pecasVermelhas);
    }

    private void posicionarPecas(List<Peca> pecas) {
        for (Peca peca : pecas) {
            this.getCasa(peca.getY(), peca.getX()).colocarPeca(peca);
        }
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
