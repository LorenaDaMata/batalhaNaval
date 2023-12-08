import java.io.Serializable;

class IntermediateShip extends Embarcacao implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public IntermediateShip(int linha, int coluna1, int coluna2, int coluna3, int coluna4) {
    super(linha, coluna1,  coluna2,  coluna3, 0);
  }

  @Override
  public char getInicial() {
    return 'I'; // 'I' representa o navio intermediário
  }
}

