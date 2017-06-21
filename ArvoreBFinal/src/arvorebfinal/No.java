//Essa é a implementação da classe nó em uma árvore balanceada
package arvorebfinal;


/*
  * Variáveis que determinam a ordem da árvore;
  * Número de chaves no nó
  * Array com os valores das chaves
  * Array com as referências do filho
  * Checa se o nodo é folha ou não
  * Pai do nó atual
*/


public class No {
    
    private static int t;
    private int cont;
    private int key[];
    private No filho[];
    private boolean folha;
    private No pai;
    
    public No(){}
    
    /*
       * Construtor que será chamado na árvore B.java para um novo nó com valores;
       * pai = Marca o pai;
       * key = array de tamanho adequado;
       * filho = Array de referência com tamanho próprio;
       * folha = inicialmente todo nó é uma folha
       * contador até adicionar as chaves
    */
    
    public No(int t, No pai){
        this.t = t;
        this.pai = pai;
        key = new int[2*t-1];
        filho = new No[2*t];
        folha = true;
        cont = 0;
    }
    
    // Retorna a chave na posição do indice
    public int getValor(int indice){
        return key[indice];
    }
    
    // retorna o indice do nó filho
    
    public No getFilho(int indice){
        return filho[indice];
    }
    
    
}
