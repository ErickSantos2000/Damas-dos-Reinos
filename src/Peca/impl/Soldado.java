package Peca.impl;

import Peca.Peca;
import Peca.Cor;
import tabuleiro.Casa;
import Peca.TipoPeca;

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

        int xOrigem = origem.getX();
        int yOrigem = origem.getY();
        int xDestino = destino.getX();
        int yDestino = destino.getY();

        int diferencaX = xDestino - xOrigem;
        int diferencaY = yDestino - yOrigem;
        int absX = Math.abs(diferencaX);
        int absY = Math.abs(diferencaY);

        boolean ehParaFrenteSimples = (this.getCor() == Cor.BRANCA && diferencaY == 1) ||
                (this.getCor() == Cor.VERMELHA && diferencaY == -1);

        if (absX == 1 && absY == 1 && ehParaFrenteSimples) {
            return destino.getPeca() == null;
        }

        boolean ehParaFrenteSalto = (this.getCor() == Cor.BRANCA && diferencaY == 2) ||
                (this.getCor() == Cor.VERMELHA && diferencaY == -2);

        if (absX == 2 && absY == 2 && ehParaFrenteSalto) {

            if (destino.getPeca() != null) {
                return false;
            }

            return true;
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
