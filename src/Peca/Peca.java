package Peca;

import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public interface Peca {
    Cor getCor();
    TipoPeca  getTipo();

    // metodo que contem as regras de movimentação para cada uma da implementações
    boolean podeMover(Casa origem, Casa destino);
    // metodo para regras de captura de pecas
    boolean podeCapturar(Casa origem, Casa destino);

    int getX();
    int getY();

    default void setTabuleiro(Tabuleiro tabuleiro) {
        // Peças que consultam o tabuleiro sobrescrevem este método.
    }
}
