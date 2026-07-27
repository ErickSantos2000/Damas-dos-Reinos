package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;

public class Soldado implements Peca {

    private int linha, coluna;
    private Cor cor;

    public Soldado(int linha, int coluna, Cor cor) {
        this.linha = linha;
        this.coluna = coluna;
        this.cor = cor;
    }

    public Soldado() {
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public Peca getTipo() {
        return null;
    }

    @Override
    public boolean regraMovimento(Casa origem, Casa destino) {
        return true;
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
