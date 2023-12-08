import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class LeitorEscritorArquivos {
    private static final String FILENAME = "C:\\jogo\\jogo_salvo.txt";
    private JogoBatalhaNaval jogo; // Variável para armazenar a instância do jogo

    public LeitorEscritorArquivos(JogoBatalhaNaval jogo) {
        this.jogo = jogo;
    }

    public void setJogo(JogoBatalhaNaval jogo) {
        this.jogo = jogo;
    }

    public void gravarJogo(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write(tabuleiroJogador.toString());
            writer.newLine();
            writer.write("\n"); // Separador entre os tabuleiros
            writer.write(tabuleiroComputador.toString());
            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    public Tabuleiro[] carregarJogo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String tabuleiroJogadorStr = reader.readLine();
            reader.readLine(); // Ignora a linha vazia
            String tabuleiroComputadorStr = reader.readLine();

            Tabuleiro tabuleiroJogador = Tabuleiro.fromString(tabuleiroJogadorStr);
            Tabuleiro tabuleiroComputador = Tabuleiro.fromString(tabuleiroComputadorStr);

            if (tabuleiroJogador != null && tabuleiroComputador != null) {
                System.out.println("Jogo carregado com sucesso!");

                // Mostrando os tabuleiros no console após carregar o jogo
                System.out.println("\nTabuleiro do Jogador:");
                tabuleiroJogador.mostrarTabuleiroDoJogador();
                System.out.println("\nTabuleiro do Computador:");
                tabuleiroComputador.mostrarTabuleiro();

                // Atualizar o estado interno do objeto JogoBatalhaNaval
                reiniciarEstadoDoJogo(tabuleiroJogador, tabuleiroComputador);

                return new Tabuleiro[]{tabuleiroJogador, tabuleiroComputador};
            } else {
                System.out.println("Erro: Tabuleiros não foram carregados corretamente.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o jogo: " + e.getMessage());
        }

        return null;                       
    }
    
    public Tabuleiro[] loadGame() {
    	Tabuleiro tabuleiroJogador = null;
    	Tabuleiro tabuleiroComputador = null;
    	 Path path = Paths.get(FILENAME);
    	try {
			List<String> linhasArquivo =  Files.readAllLines(path, Charset.forName("ISO-8859-1"));
			String s = "";
			String j = "";
			for(int i = 0; i < linhasArquivo.size(); i ++) {
				
				if(i <= 9) {
					s = s+linhasArquivo.get(i)+"__";
				}
				if(i > 11 ) {
					j = j+linhasArquivo.get(i)+"__";
				}
				
			}
			tabuleiroJogador = Tabuleiro.fromString(s);	
			tabuleiroComputador = Tabuleiro.fromString(j);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	reiniciarEstadoDoJogo(tabuleiroJogador, tabuleiroComputador);
    	return new Tabuleiro[]{tabuleiroJogador, tabuleiroComputador};
    	
    }

    private void reiniciarEstadoDoJogo(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) {
        // Atualizar o estado interno do objeto JogoBatalhaNaval
        if (jogo != null) {
            jogo.reiniciarJogo(tabuleiroJogador, tabuleiroComputador);
        } else {
            System.out.println("Erro: Jogo não foi inicializado.");
        }
    }
}