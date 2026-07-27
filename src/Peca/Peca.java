package Peca;

import tabuleiro.Casa;

public interface Peca {
    Cor getCor();
    Peca getTipo();

    // metodo que contem as regras de movimentação para cada uma da implementações
    boolean regraMovimento(Casa origem, Casa destino);
}
