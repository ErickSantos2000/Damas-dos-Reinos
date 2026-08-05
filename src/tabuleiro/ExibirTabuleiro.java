package tabuleiro;

import Peca.Cor;
import Peca.Peca;
import Peca.TipoPeca;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;


  public class ExibirTabuleiro {

      public void exibirTabuleiro(Tabuleiro tabuleiro) {
          System.out.println();
          System.out.println("      0  1  2  3  4  5  6  7");
          System.out.println("    ┌─────────────────────────┐");

          for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
              System.out.print(" " + linha + "  │ ");

              for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                  Casa casa = tabuleiro.getCasa(linha, coluna);

                  if (!casa.temPeca()) {
                      String emojiCasa = (linha + coluna) % 2 == 0 ? "⬜" : "⬛";
                      System.out.print(emojiCasa + " ");
                  } else {
                      System.out.print(obterEmoji(casa.getPeca()) + " ");
                  }
              }

              System.out.println("│");
          }

          System.out.println("    └─────────────────────────┘");
          System.out.println();
      }

      private String obterEmoji(Peca peca) {
          switch (peca.getTipo()) {
              case SOLDADO:
                  return peca.getCor() == Cor.BRANCA ? "⚪" : "🔴";

              case SOLDADO_REAL:
                  return peca.getCor() == Cor.BRANCA ? "👑" : "⚜️";

              case CAVALEIRO:
                  return peca.getCor() == Cor.BRANCA ? "CB" : "CV";

              case MAGO:
                  return peca.getCor() == Cor.BRANCA ? "💫" : "🪄";

              default:
                  return "❓";
          }
      }

  }


