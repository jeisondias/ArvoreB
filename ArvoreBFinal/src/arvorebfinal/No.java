//Essa é a implementação da classe nó em uma árvore balanceada
package arvorebfinal;


/*
  * 
  * 
  * 
  *
*/


public class No {
    
    private int n; //Atributo que guarda a quantidade de chaves no nó
    private int[] chave; //vetor das chaves
    private No[] filho;//vetor dos filhos
    private boolean folha;//Atributo que indica se a nó eh folha ou nao
          
    public No(int n) {
        this.chave = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            this.chave = null;
        }
        this.filho = new No[n];
        for (int i = 0; i < n; i++) {
            this.filho = null;
        }
        this.folha = true;
        this.n = 0;
    }

    
    public boolean isFolha() {
        return folha;
    }

    public void setFolha(boolean folha) {
        this.folha = folha;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int[] getChave() {
        return chave;
    }

    public void setChave(int[] chave) {
        this.chave = chave;
    }

    public No[] getFilho() {
        return filho;
    }

    public void setFilho(No[] filho) {
        this.filho = filho;
    }
    
    

}
