package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;

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
    public Cor getCor() {
        return cor;
    }

    @Override
    public Peca getTipo() {
        return null;
    }

    @Override
    public boolean regraMovimento(Casa origem, Casa destino) {
        if (origem == null || destino == null || origem.equals(destino)) {
            return false;
        }

        int diferencaX = destino.getX() - origem.getX();
        int diferencaY = destino.getY() - origem.getY();
        int absX = Math.abs(diferencaX);
        int absY = Math.abs(diferencaY);

        if (absX == absY && absX > 0) {
            if (destino.getPeca() == null) {
                return true;
            }
            if (destino.getPeca().getCor() != this.getCor()) {
                return true;
            }
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
