# Trabalho Prático AEDs 3 - Parte 3  

## Descrição  

Este trabalho amplia o sistema CRUD desenvolvido nas etapas anteriores, integrando **consultas otimizadas**, **índices 
inversos** e outras melhorias avançadas de processamento de dados. Nesta fase, também aplicamos o relacionamento 1:N 
entre **Tarefa** e **Categoria** a cenários mais complexos, utilizando estruturas como **Árvore B+**, **Lista Invertida** 
e **Tabela Hash Extensível** para otimizar o acesso e a manipulação dos dados.  

## Estrutura do Projeto  

### Diretórios  
   Contém as classes responsáveis pela lógica de controle do sistema, implementando as operações CRUD e otimizando a interação com as estruturas de dados.  
   - **Classes principais**:  
     - `ArquivoCategoria`: Gerencia a persistência e manipulação de categorias.  
     - `ArquivoTarefa`: Gerencia a persistência e manipulação de tarefas.  
     - `IndiceInvertido`: Implementa um índice para buscas eficientes baseadas em termos.  
    
   Contém as estruturas de dados utilizadas pelo sistema, como Árvores B+, Tabelas Hash e representações de entidades.  
   - **Classes principais**:  
     - `Categoria` e `Tarefa`: Representam as entidades do sistema.  
     - `ArvoreBMais`: Implementa a Árvore B+ usada no relacionamento 1:N e outras buscas.  
     - `HashExtensivel`: Gerencia índices indiretos por meio de uma tabela hash extensível.  
     - `ListaInvertida`: Implementa o índice invertido para busca textual.  
     - `StopWords`: Gerencia palavras irrelevantes para buscas textuais (com suporte a um arquivo externo `stopword.txt`).  
 
   Contém utilitários para entrada/saída e operações auxiliares.  
   - **Classes principais**:  
     - `IO`: Oferece métodos para leitura e escrita em arquivos e auxilia na manipulação de dados.  
 
   Contém a interface de interação com o usuário, organizando menus e exibições de dados.  
   - **Classes principais**:  
     - `CategoriasView` e `TarefasView`: Interfaces para interagir com os dados das entidades.  
     - `PrincipalView`: Classe principal que centraliza o fluxo de interação.  

## Estrutura de Dados  

1. **Árvore B+**  
   - Relaciona categorias e tarefas, garantindo acesso eficiente e ordenado.  

2. **Hash Extensível**  
   - Gerencia índices indiretos para buscas rápidas por nome de categoria.  

3. **Lista Invertida**  
   - Facilita buscas por termos em descrições de tarefas, ignorando palavras irrelevantes (`stopword.txt`).  

4. **Estrutura de Arquivos Externos**  
   - `data`: Diretório para armazenamento dos arquivos binários do sistema.  
   - `stopword.txt`: Arquivo com palavras irrelevantes usadas pelo índice invertido.  

## Desafios e Aprendizados  

Os principais desafios desta parte envolveram a otimização do desempenho do sistema para consultas complexas e a 
implementação de relatórios, que demandaram o processamento eficiente de grandes volumes de dados. A experiência 
permitiu aprofundar conhecimentos em:  
- Uso avançado de estruturas de dados como Árvores B+ e tabelas hash.  
- Processamento de dados em massa com integridade e consistência.  
- Planejamento de sistemas modulares, permitindo fácil expansão.  

## Checklist  

- O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida?
    ```
    SIM
    ```

- O CRUD de rótulos foi implementado?
    ```
    SIM
    ```

- No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? 
    ```
    SIM
    ```

- É possível buscar tarefas por palavras usando o índice invertido?
    ```
    SIM
    ```

- É possível buscar tarefas por rótulos usando uma árvore B+? 
    ```
    SIM
    ```

- O trabalho está completo?
    ```
    SIM
    ```

- O trabalho é original e não a cópia de um trabalho de um colega?
    ```
    SIM
    ```

#👨‍💻Integrantes

Alexandre Augusto

Gabriel Valedo

Henrique Gilbert

Leonardo Amaral

