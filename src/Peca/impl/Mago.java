package Peca.impl;

import Peca.Peca;
import Peca.Cor;

public class Mago implements Peca {
    private int linha, coluna;
    private Cor cor;

    public Mago(int linha, int coluna, Cor cor) {
        this.linha = linha;
        this.coluna = coluna;
        this.cor = cor;
    }

    public Mago() {
    }

    @Override
    public void mover(int linhaAlvo, int colunaAlvo) {
        if (linhaAlvo == colunaAlvo && (linhaAlvo != this.linha && colunaAlvo != this.coluna)) {
            this.linha = linhaAlvo;
            this.coluna = colunaAlvo;
        }
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public Peca getTipo() {
        return null;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
