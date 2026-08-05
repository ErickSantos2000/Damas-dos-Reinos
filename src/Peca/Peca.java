package Peca;

import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public interface Peca {
    Cor getCor();
    TipoPeca  getTipo();

    // metodo que contem as regras de movimentação para cada uma da implementações
    boolean podeMover(Casa origem, Casa destino, Tabuleiro tabuleiro);
    // metodo para regras de captura de pecas
    boolean podeCapturar(Casa origem, Casa destino, Tabuleiro tabuleiro);

    int getX();
    int getY();

    boolean promover(Casa destino, Tabuleiro tabuleiro);
}
