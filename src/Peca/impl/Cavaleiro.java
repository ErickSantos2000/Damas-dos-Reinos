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
        return true;
    }

    @Override
    public boolean podeMover(Casa origem, Casa destino) {
        if (origem == null || destino == null || origem.equals(destino)) {
            return false;
        }

        int absX = Math.abs(destino.getX() - origem.getX());
        int absY = Math.abs(destino.getY() - origem.getY());

        boolean ehMovimentoL = (absX == 2 && absY == 1) || (absX == 1 && absY == 2);

        if (ehMovimentoL) {

            if (destino.getPeca() == null) {
                return true;
            }
            return destino.getPeca().getCor() != this.getCor();
        }

        return false;
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
