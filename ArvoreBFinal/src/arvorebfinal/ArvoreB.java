package arvorebfinal;

public class ArvoreB {
    /*
      * Nó raiz;
      * Ordem da Arvore B;
      * Contador para a quantidade de elementos na arvore B;
    */
   
    private No raiz;
    private int ordem;
    private int nElementos; 

    /*
      * Cria um novo nó para a raiz, seta a ordem passada como parâmetro e inicializa
      * O atributo contador de numeros de elementos
    */
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
    
    /*
      * Método para inserir na árvore;
      * Checa se o nó está vazio, se estiver vazio, adiciona o valor
      * Se não estiver vazio, verifica como está a raiz , divide o nó e insere no nó não cheio
      * Realiza a divisão dos nós e adiciona em um nó não cheio a partir da raiz;
      * Caso contrário, adiciona a partir da raiz;
      * incrementa o número de elementos da árvore;
    */
    
    public void inserir(int chave){
        if(BuscaChave(raiz, chave) == null){
            if(raiz.getN() == 0){
                raiz.getChave().set(0, chave);
                raiz.setN(raiz.getN()+1);
            } else {
                
                No r = raiz;
                if (r.getN() == ordem -1){
                    No s = new No(ordem);
                    raiz = s;
                    s.setFolha(false);
                    s.setN(0);
                    s.getFilho().set(0, r);
                    divideNo(s, 0, r);
                    inserirNoNaoCheio(s, chave);
                } else{
                    inserirNoNaoCheio(r, chave);
                }
            }
            nElementos++;
        }
        
    }
    
    
    /* 
        * Método para realizar a inserção em um nó não cheio;
        * noI = nó que será inserido e chave = chave que será inserida       
    */
    public void inserirNoNaoCheio(No noI, int chave){
        int i = noI.getN() - 1;
        // Checa se o nó é uma folha e se for, pega a posição correta para inserir a chave
        if(noI.isFolha()){
            while (i >= 1 && chave < noI.getChave().get(i)) {
                noI.getChave().set(i + 1, noI.getChave().get(i));
                i--;
            }
            //Realiza a inserção da chave na posição i
            i++;
            noI.getChave().set(i, chave);
            noI.setN(noI.getN()+ 1);
            
            // Se o nó não for folha, ele pega a posição correta para poder inserir a chave
        } else {
            while ((i >= 0 && chave < noI.getChave().get(i))){
               i--;
            }
            //Se o filho estiver cheio, ele realiza a divisão
            i++;
            if ((noI.getFilho().get(i).getN() == ordem -1)){
                divideNo(noI, i, noI.getFilho().get(i));
                if(chave > noI.getChave().get(i)){
                   i++;
                }
                // Realiza a inserção da chave no filho i do noI
                inserirNoNaoCheio(noI.getFilho().get(i), chave);
            }
        }
    
    }
    
    
    /*
        * Math.Floor retorna o maior número int que é menor ou igual ao argumento
        * Nop = Nó pai, noF = Nó filho e o i = indíce que o noF é o i-ésimo filho de x;
        * O método realiza a divisão dos nós
    */
    public void divideNo(No noP, int i, No noF) {
        int t = (int) Math.floor((ordem - 1) / 2);
        //Nó Auxiliar é utilizado para poder realizar a divisão
        No noAux = new No(ordem);
        noAux.setFolha(noF.isFolha());
        noAux.setN(t);

        //Passa as T últimas chaves do noF para o noExtra
        for (int j = 0; j < t; j++) {
            if ((ordem - 1) % 2 == 0) {
                noAux.getChave().set(j, noF.getChave().get(j + t));
            } else {
                noAux.getChave().set(j, noF.getChave().get(j + t + 1));
            }
            noF.setN(noF.getN() - 1);
        }

        //Se o nó filho não for folha, ele passa os t+1 últimos filhos de noF para noExtra
        if (!noF.isFolha()) {
            for (int j = 0; j < t + 1; j++) {
                if ((ordem - 1) % 2 == 0) {
                    noAux.getFilho().set(j, noF.getFilho().get(j + t));
                } else {
                    noAux.getFilho().set(j, noF.getFilho().get(j + t + 1));
                }
            }
        }
        //Realiza a definição do novo valor das quantidade de chaves de noF
        noF.setN(t);

        //Pega os filhos do nó Pai e descola para a direita
        for (int j = noP.getN(); j > i; j--) { 
            noP.getFilho().set(j + 1, noP.getFilho().get(j));
        }
        // Define o noAux como filho do noP na posição i+1
        noP.getFilho().set(i + 1, noAux);

        // Passa as chaves do noP para uma posição a direita, para realizar a substituição de noF
        for (int j = noP.getN(); j > i; j--) {
            noP.getChave().set(j, noP.getChave().get(j - 1));
        }

        //Passa a chave do noF para o noExtra
        if ((ordem - 1) % 2 == 0) {
            noP.getChave().set(i, noF.getChave().get(t - 1));
            noF.setN(noF.getN() - 1);
            
        } else {
            noP.getChave().set(i, noF.getChave().get(t));
        }

        //Incrementa o número de chaves do nó Pai (noP)
        noP.setN(noP.getN() + 1);

    }
  


}
