import java.io.Serializable;

abstract class Embarcacao implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int linha;
  private int coluna1;
  private int coluna2;
  private int coluna3;
  private int coluna4;

  public Embarcacao(int linha, int coluna1, int coluna2, int coluna3, int coluna4) {
    super();
    this.linha = linha;
    this.coluna1 = coluna1;
    this.coluna2 = coluna2;
    this.coluna3 = coluna3;
    this.coluna4 = coluna4;
  }

  public int getLinha() {
    return linha;
  }

  public void setLinha(int linha) {
    this.linha = linha;
  }

  public int getColuna1() {
    return coluna1;
  }

  public void setColuna1(int coluna1) {
    this.coluna1 = coluna1;
  }

  public int getColuna2() {
    return coluna2;
  }

  public void setColuna2(int coluna2) {
    this.coluna2 = coluna2;
  }

  public int getColuna3() {
    return coluna3;
  }

  public void setColuna3(int coluna3) {
    this.coluna3 = coluna3;
  }

  public int getColuna4() {
    return coluna4;
  }

  public void setColuna4(int coluna4) {
    this.coluna4 = coluna4;
  }

  public abstract char getInicial();
}