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

            System.out.println("\nVez de: "
                    + jogo.getGerenciadorTurno().getVez().getNome());

            System.out.println("Digite a jogada: linhaOrigem colunaOrigem linhaDestino colunaDestino");
            System.out.println("Exemplo: 5 1 4 0");
            System.out.println("Digite 'sair' para encerrar.");

            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("sair")) {
                break;
            }

            String[] valores = entrada.split("\\s+");

            if (valores.length != 4) {
                System.out.println("Formato inválido. Digite quatro números.");
                continue;
            }

            try {
                int linhaOrigem = Integer.parseInt(valores[0]);
                int colunaOrigem = Integer.parseInt(valores[1]);
                int linhaDestino = Integer.parseInt(valores[2]);
                int colunaDestino = Integer.parseInt(valores[3]);

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
                System.out.println("Use apenas números para as coordenadas.");
            }
        }

        scanner.close();
        System.out.println("Jogo encerrado.");
    }
}