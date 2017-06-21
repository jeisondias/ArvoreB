package arvorebfinal;

public class ArvoreB {

    //Atributos da Classe ArvoreB
    private No raiz; //Atributo do Nó raiz;
    private int ordem; //Ordem da Arvore-B;
    private int nElementos; //Contador para a quantidade de elementos na arvore B;

    //Construtor da Classe ArvoreB
    //Cria um novo nó para a raiz, seta a ordem passada como paâmetro e inicializa
    //o atributo contador de numeros de elementos
    public ArvoreB(int n) {
        this.raiz = new No(n);
        this.ordem = n;
        nElementos = 0;
    }  

    //Getters e Setters dos atributos nElementos,ordem e raiz
    public int getnElementos() {
        return nElementos;
    }
    
    public void setnElementos(int nElementos){
        this.nElementos = nElementos;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }

    public No getRaiz() {
        return raiz;
    }
    
    public void setRaiz(No raiz){
        this.raiz = raiz;
    }

    /*
      * Método de busca da chave que será utilizado na adição e remoção de valores na árvore;
      * Retorna o No onde a chave buscada foi encontrada;
      * noBusca é o nó por onde a busca começa; chave é a chave a ser buscada;
      * Faz a incrementação para checar se o tamanho não vai estourar e se o valor não é maior que o procurado
      * Se o tamanho não for maior e a chave for encontrada, retorna ela;
      * Se não for igual, o tamanho ficou maior, então chega se é folha. Sendo folha, não checa mais nada.
      * Se não for folha, faz a recursão para achar o filho
    */
    
    public No BuscaChave(No noBusca, int chave){
        int i = 0;
        
        while ((i <= noBusca.getN()) && (chave > noBusca.getChave().get(i))){
            i++;
        }
       
        if ((i <= noBusca.getN()) && (chave == noBusca.getChave().get(i))){
            return noBusca;
        }
        
        if (noBusca.isFolha()){
            return null;
        } else{
            return (BuscaChave(noBusca.getFilho().get(i), chave));
        }
    }
    
  


}
