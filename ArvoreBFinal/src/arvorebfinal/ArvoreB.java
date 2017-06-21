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
  
     /*
        * Método de Remoção de uma determinada chave da arvore B
        * Verifica se a chave que será removida existe
        * Adiciona ela a um nó e pega a posição correta dela.
        * Faz a checagem se é folha ou não
        * Se for folha, elaremove e balanceira a folha;
        * Se não for folha, ela substitui pelo antecessor e balanceia a folha onde o mesmo estava
        * realiza o deslocamento das chaves quando tiver mais de uma;
    */
    public void Remove(int chave) {
        if (BuscaChave(this.raiz, chave) != null) {
            No temp = BuscaChave(this.raiz, chave);
            int i = 1;
            while (temp.getChave().get(i - 1) < chave) {
                i++;
            }

            if (temp.isFolha()) {
                for (int j = i + 1; j <= temp.getN(); j++) {
                    temp.getChave().set(j - 2, temp.getChave().get(j - 1));
                }
                temp.setN(temp.getN() - 1);
                if (temp != this.raiz) {
                    Balanceia_Folha(temp);
                }
            } else {
                /* 
                  * Y é o antecessor de chave
                  * A chave do n é susbtituida pelo y
                  * Pega o no S e balanceia
                  * Depois decrementa o número de elementos
                */
                No S = Antecessor(this.raiz, chave);
                int y = S.getChave().get(S.getN() - 1);
                S.setN(S.getN() - 1);
                temp.getChave().set(i - 1, y);
                Balanceia_Folha(S);
            }
            nElementos--;
        }
    }
   
    
    private void Balanceia_Folha(No nFolha) {
        if (nFolha.getN() < Math.floor((ordem - 1) / 2)) {
            No P = getPai(raiz, nFolha);//P é o pai de F
            int j = 1;

            //adquire a posição de F em P
            while (P.getFilho().get(j - 1) != nFolha) {
                j++;
            }

            //verifica se o irmão à esquerda de F não tem chaves para emprestar
            if (j == 1 || (P.getFilho().get(j - 2)).getN() == Math.floor((ordem - 1) / 2)) {
                //verifica se o irmão à direita de F não tem chaves para emprestar
                if (j == P.getN() + 1 || (P.getFilho().get(j).getN() == Math.floor((ordem - 1) / 2))) {
                    Diminui_Altura(nFolha); //nenhum irmão tem chaves para emprestar eh necessario diminuir a altura
                } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Dir_Esq
                    Balanceia_Dir_Esq(P, j - 1, P.getFilho().get(j), nFolha);
                }
            } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Esq_Dir
                Balanceia_Esq_Dir(P, j - 2, P.getFilho().get(j - 2), nFolha);
            }
        }
    }
    
    
    /*
        * Ele retorna o nó onde a chave antecessora de chave está
        * Faz o incremento para saver a posição;
        * após encontrar, chega se é um nó folha, se for, retorna o nó, se não for, retorna o antecessor com a posição e a chave
    */
   
    private No Antecessor(No noIni, int chave){
        int i = 1;
        while (i <= noIni.getN() && noIni.getChave().get(i - 1) < chave) {
            i++;
        }
        if (noIni.isFolha()) {
            return noIni;
        } else {
            return Antecessor(noIni.getFilho().get(i - 1), chave);
        }
    }

    
    //Metodo que retorna o nó pai de N
    //Parâmetros: T - Nó onde começa a busca, N - nó que deve se buscar o pai
    
    /*
        * Método retorna o nó pai de N 
        * Checa se é raiz, se for, retorna nulo por não ter pai;
        * Se não for, inicializa o for para achar a posição do nó.
        * Se o filho do noIn for igual ao noPai retorna o noIn
        * Se for diferente de folha, pega um nó temporário e adiciona o pai, passando a posição do filho e o pai.
        * Sendo diferente de nulo, retorna o resultado da variável temporária.
    */
    private No getPai(No noI, No noBPai) {
        if (this.raiz == noBPai) {
            return null;
        }
        for (int i = 0; i <= noI.getN(); i++) {
            if (noI.getFilho().get(i) == noBPai) {
                return noI;
            }
            if (!noI.getFilho().get(i).isFolha()) {
                No temp = getPai(noI.getFilho().get(i), noBPai);
                if (temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }
    
   //Método para diminuir a altura
    //Parâmetros: X - nó onde vai ser diminuido a altura
    private void Diminui_Altura(No X) {
        int j;
        No P = new No(ordem);

        //verifica se X é a raiz
        if (X == this.raiz) {
            //verifica se X está vazio
            if (X.getN() == 0) {
                this.raiz = X.getFilho().get(0);//o filho o de x passa a ser raiz
                X.getFilho().set(0, null); // desaloca o filho de x
            }
        } else {//caso contrario(X nao é raiz)
            int t = (int) Math.floor((ordem - 1) / 2);
            //verifica se o numero de chaves de X é menor que o permitido
            if (X.getN() < t) {
                P = getPai(raiz, X);//P é pai de X
                j = 1;

                //adquire a posicao correta do filho X em P
                while (P.getFilho().get(j - 1) != X) {
                    j++;
                }

                //junta os nós
                if (j > 1) {
                    Juncao_No(getPai(raiz, X), j - 1);
                } else {
                    Juncao_No(getPai(raiz, X), j);
                }
                Diminui_Altura(getPai(raiz, X));
            }
        }
    }
    
    //Mótodo de Balancear da esquerda para a direita
    //Parâmetros: P - Nó pai, e - indica que Esq é o e-ésimo filho de P, Esq - Nó da esquerda, Dir - Nó da direita
    private void Balanceia_Esq_Dir(No P, int e, No Esq, No Dir) {
        //Desloca as chave de Dir uma posicao para a direita
        for (int i = 0; i < Dir.getN(); i++) {
            Dir.getChave().set(i + 1, Dir.getChave().get(i));
        }

        //Se Dir nao for folha descola seu filhos uma posicao para a direita
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 0; i > Dir.getN(); i++) {
                Dir.getFilho().set(i + 1, Dir.getFilho().get(i));
            }
        }
        Dir.setN(Dir.getN() + 1);//Incrementa a quantidades de chaves em Dir
        Dir.getChave().set(0, P.getChave().get(e));//"desce" uma chave de P para Dir
        P.getChave().set(e, Esq.getChave().get(Esq.getN() - 1));//"sobe" uma chave de Esq para P
        Dir.getFilho().set(0, Esq.getFilho().get(Esq.getN()));//Seta o ultimo filho de Esq como primeiro filho de Dir
        Esq.setN(Esq.getN() - 1);//Decrementa a quantidade de chaves em Esq

    }

    //Método de Balancear da direita para a esquerda
    //Parâmetros: P - Nó pai, e - indica que Dir é o e-ésimo filho de P, Dir - Nó da direita, Esq - Nó da esquerda
    private void Balanceia_Dir_Esq(No P, int e, No Dir, No Esq) {

        Esq.setN(Esq.getN() + 1);//Incrementa a quantidade de chaves em Esq
        Esq.getChave().set(Esq.getN() - 1, P.getChave().get(e));//"desce" uma chave de P para Esq
        P.getChave().set(e, Dir.getChave().get(0));//"sobe" uma chave de Dir para P
        Esq.getFilho().set(Esq.getN(), Dir.getFilho().get(0));//Seta o primeiro filho de Dir como último filho de Esq

        //descola as chaves de Dir uma posição para a esquerda
        for (int j = 1; j < Dir.getN(); j++) {
            Dir.getChave().set(j - 1, Dir.getChave().get(j));
        }

        //se Dir nao for folha, desloca todos os filhos de Dir uma posicao a esquerda
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 1; i < Dir.getN()+1 ; i++) {
                Dir.getFilho().set(i - 1, Dir.getFilho().get(i));
            }
        }

        //descrementa a quantidade de chaves de Dir
        Dir.setN(Dir.getN() - 1);

    }

    //Método para junção do nó
    //Parâmetros: X - No pai, i - posicao do filho de X onde vai ser juntado
    private void Juncao_No(No X, int i) {
        No Y = X.getFilho().get(i - 1); //cria Y
        No Z = X.getFilho().get(i);//cria Z

        int k = Y.getN();
        Y.getChave().set(k, X.getChave().get(i - 1));//"desce" uma chave de X para X

        //descola as de chaves de Z para Y
        for (int j = 1; j <= Z.getN(); j++) {
            Y.getChave().set(j + k, Z.getChave().get(j - 1));
        }

        //se Z nao for folha, descola seus filhos tbm
        if (!Z.isFolha()) {
            for (int j = 1; j <= Z.getN(); j++) {
                Y.getFilho().set(j + k, Z.getFilho().get(j - 1));
            }
        }

        //seta a quantidades de chaves em Y
        Y.setN(Y.getN() + Z.getN() + 1);
        
        X.getFilho().set(i, null);//desaloca o Z setando o filho de X que apontava pra Z pra null

        //descola os filhos e as chaves de X uma para a esquera, para "fechar o buraco"
        for (int j = i; j <= X.getN() - 1; j++) {//ainda nao
            X.getChave().set(j - 1, X.getChave().get(j));
            X.getFilho().set(j, X.getFilho().get(j + 1));
        }

        //decrementa a quantidade de chaves em X
        X.setN(X.getN() - 1);
    }

    
    
    /*
        * Método para imprimir a àrvore;
        * Imprime o nó raíz;
        * Realiza a impressão da árvore em pré-ordem
    */
    
    public void imprimir(No n){
        for (int i = 0; i < n.getN(); i++){
            System.out.println(n.getChave().get(i)+"");
        }
        
        if(!n.isFolha()){
            for(int j = 0; j <= n.getN(); j++){
                if(n.getFilho().get(j) != null){
                    System.out.println();
                    imprimir(n.getFilho().get(j));
                }
            }
        }
    
    }
    
    /*
        * Realizar a impressão da chave
        * Cria um nó temporário e pesquisa o nó
        * Se o no estiver null, informa que a chave não existe.
        * Se estiver com algo, retorna o valor encontrado.
    */
    public void ImprimirNodoPesquisa(ArvoreB b, int v){
        No temp = new No(ordem);
        
        temp = BuscaChave(b.raiz, v);
        
        if (temp == null){
            System.out.println("Essa chave não existe na árvore");
        } else{
            imprimir(temp);
        }
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




}
