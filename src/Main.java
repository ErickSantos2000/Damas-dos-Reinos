import Jogo.Jogo;
import tabuleiro.ExibirTabuleiro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        ExibirTabuleiro tela = new ExibirTabuleiro();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            tela.exibirTabuleiro(jogo.getTabuleiro());

            System.out.println("\nVez de: " + jogo.getGerenciadorTurno().getVez().getNome());
            System.out.println("Informe as coordenadas passo a passo");

            try {
                System.out.print("Linha de origem: ");
                String entradaOrigem = scanner.nextLine().trim();
                if (entradaOrigem.equalsIgnoreCase("sair")) {
                    break;
                }
                int linhaOrigem = Integer.parseInt(entradaOrigem);

                System.out.print("Coluna de origem: ");
                String entradaColunaOrigem = scanner.nextLine().trim();
                if (entradaColunaOrigem.equalsIgnoreCase("sair")) {
                    break;
                }
                int colunaOrigem = Integer.parseInt(entradaColunaOrigem);

                System.out.print("Linha de destino: ");
                String entradaDestino = scanner.nextLine().trim();
                if (entradaDestino.equalsIgnoreCase("sair")) {
                    break;
                }
                int linhaDestino = Integer.parseInt(entradaDestino);

                System.out.print("Coluna de destino: ");
                String entradaColunaDestino = scanner.nextLine().trim();
                if (entradaColunaDestino.equalsIgnoreCase("sair")) {
                    break;
                }
                int colunaDestino = Integer.parseInt(entradaColunaDestino);

                boolean movimentoValido = jogo.mover(
                        linhaOrigem,
                        colunaOrigem,
                        linhaDestino,
                        colunaDestino
                );

                if (!movimentoValido) {
                    System.out.println("Movimento inválido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Use apenas números inteiros para as coordenadas.");
            }
        }

        scanner.close();
        System.out.println("Jogo encerrado.");
    }
}