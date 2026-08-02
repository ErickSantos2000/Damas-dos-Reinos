package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;
import Peca.TipoPeca;

public class Cavaleiro implements Peca {
    private int linha, coluna;
    private Cor cor;

    public Cavaleiro(int linha, int coluna, Cor cor) {
        this.linha = linha;
        this.coluna = coluna;
        this.cor = cor;
    }

    public Cavaleiro() {
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public TipoPeca getTipo() {
        return null;
    }

    @Override
    public boolean podeCapturar(Casa origem, Casa destino){
        int dx = Math.abs(destino.getX() - origem.getX());
        int dy = Math.abs(destino.getY() - origem.getY());

        boolean movimentoL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        return movimentoL && destino.getPeca() != null && destino.getPeca().getCor() != this.getCor();
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino) {
        int dx = Math.abs(destino.getX() - origem.getX());
        int dy = Math.abs(destino.getY() - origem.getY());

        boolean movimentoL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        return movimentoL && destino.getPeca() == null;
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
