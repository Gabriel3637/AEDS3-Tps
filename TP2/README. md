# AEDS3 - TP2: Relacionamento N:N entre Séries e Atores

## Descrição Geral

Neste trabalho prático, evoluímos o sistema desenvolvido no TP1 para lidar com **relacionamentos do tipo N:N** entre entidades, mais especificamente entre **Séries** e **Atores**.

Nosso sistema é capaz de:

- Armazenar séries, episódios e atores em arquivos binários utilizando `RandomAccessFile`.
- Realizar operações completas de **CRUD** para todas as entidades.
- Implementar relacionamentos N:N entre Séries e Atores através de duas **árvores B+**, permitindo a consulta eficiente de:
  - Quais atores participam de determinada série.
  - Em quais séries um determinado ator aparece.
- Garantir **consistência dos dados**, impedindo, por exemplo, a exclusão de um ator ainda vinculado a uma série.

A modelagem foi orientada a objetos e o projeto foi modularizado para facilitar testes, manutenção e evolução.

---

## Participantes

- Alexandre Niess
- Gabriel Valedo
- Henrique Gilberti
- Leonardo Amaral

---

## Estrutura de Classes

### Model:

#### 'ParIdId', ParIDEndereço, ParNomeId

Índices para armazenar pares de dados na árvore B+.

#### 'Atuacao' 

Classe que representa o relacionamento entre um ator e uma série.

#### 'Ator','Episodio'

Classes modelo que representam uma entidade armazenada

#### 'Serie'

Classe modelo que representa uma entidade armazenada

### 'View'

Classes responsáveis pela interação com o usuário

#### 'MenuAtores'

- Métodos principais:
  - incluirAtor() – Recebe os dados e chama o metodo para criar o ator na memória secundária
  - buscarAtor() – Busca o ator na memória secundária pelo seu nome
  - alterarAtor() – Seleciona o ator pelo nome e recebe os dados para altera-lo na memória secundária
  - excluirAtor() – Seleciona o ator pelo nome e chama o metodo para excluir da memória secundária
  - listaratuacoes() – Seleciona o ator pelo nome e chama o metodo para exibir todas as suas atuações
 
#### 'MenuEpisodio'
- Métodos principais:
  - incluirEpisodio() – Recebe os dados e chama o metodo para criar o episódio na memória secundária
  - buscarEpisodio() – Busca o episódio na memória secundária pelo seu nome
  - alterarEpisodio() – Seleciona o episódio pelo nome e recebe os dados para altera-lo na memória secundária
  - excluirEpisodio() – Seleciona o episódio pelo nome e chama o metodo para excluir da memória secundária

#### 'MenuSeries' 
- Métodos principais:
  - incluirSerie() – Recebe os dados e chama o metodo para criar a série na memória secundária
  - buscarSerie() – Busca a série na memória secundária pelo seu nome
  - alterarSerie() – Seleciona a série pelo nome e recebe os dados para altera-lo na memória secundária
  - excluirSerie() – Seleciona a série pelo nome e chama o metodo para excluir da memória secundária
  - EpisodioSerie() – Exibe o MenuEpisodio referente uma serie previamente selecionada pelo nome
  - AtuacaoSerie() – Exibe o MenuAtuacao referente uma serie previamente selecionada pelo nome

#### 'MenuAtuacao'
- Métodos principais:
  - incluirAtuacao() – Recebe os dados e chama o metodo para criar a atuação na memória secundária
  - buscarAtuacao() – Busca a atuação na memória secundária pelo seu nome
  - alterarAtuacao() – Seleciona a atuação pelo nome e recebe os dados para altera-lo na memória secundária
  - excluirAtuacao() – Seleciona a atuação pelo nome e chama o metodo para excluir da memória secundária
  - listarAtuacao() -  Chama o metodo para exibir todas as atuações da série selecionda previamente

### 'Arquivos'

#### 'ArqAtuacao'

Classe que gerencia o CRUD das atuações, mantendo dois índices B+:

- Métodos principais:
  - 'readAtorSerie(int atorId, int serieId)' – retorna um array com as relações entre um ator e uma série
  - 'readSerie(int serieId)' – retorna todas as atuações de uma série
  - 'readAtor(int atorId)' – retorna todas as atuações de um ator

#### 'ArqSerie'

Classe que gerencia o índice de série e o armazenamento das séries

Métodos principais:
  - 'readNome(String nome)' - recebe um nome e retorna um array com as séries por meio da árvore B+
  - 'create(Serie s)' - cria uma série, atualiza o indice e salva na árvore B+

#### 'ArqAtor'

Classe que gerencia os índices de ator e o armazenamento dos atores.

Métodos principais:
  - 'readNome(String nome)' - recebe um nome e retorna um array com os atores  por meio da árvore B+
  - 'create(Ator s)' - cria um ator, atualiza o indice e salva na árvore B+


#### 'ArqEpisodio'

Classe que gerencia os índices do episódio e o armazenamento dos episódios.

Métodos principais:
  - 'readNomeSerieId(String nome, int serieId)' - recebe um nome e uma série e retorna um array com os episódios por meio da árvore B+
  - 'create(Episodio s)' - cria um episódio, atualiza o indice e salva na árvore B+

---

## O que o sistema faz?

- Permite cadastrar, consultar, atualizar e excluir séries, episódios e **atores.
- Implementa o CRUD completo da classe 'Atuacao' com relacionamento N:N, incluindo persistência em disco.
- Garante integridade dos dados durante operações de exclusão:
  - Não é possível excluir um ator se ele estiver vinculado a alguma série.
  - Ao excluir uma série, todas as suas atuações são removidas.
- Permite consultas bidirecionais do relacionamento N:N:
  - Séries → Atores
  - Atores → Séries

---

## Relato da Experiência

O trabalho foi dividido entre os membros do grupo para otimizar o tempo e aprofundar o aprendizado em áreas específicas. A parte de **relacionamento N:N (Atuações)** foi particularmente desafiadora, especialmente em acessar o papel pelo menu de atores. Evidencia-se também com relação à busca de mais de um episódio e mais de um ator em atuações.

### Principais Desafios:

- **Manter a consistência entre os arquivos de dados e os índices** (inclusão e exclusão dupla).
- **Testar as operações compostas** (excluir série, listar atores, etc.).
- **Garantir que não haja alteração imprópria ao atualizar os vínculos** entre entidades.

### Aprendizados:

- A importância de índices auxiliares para garantir performance e consistência.
- O uso de estruturas como **árvores B+** para busca eficiente em arquivos.
- Técnicas de integração entre entidades com arquivos distintos.

### Conclusão:

Todos os requisitos foram implementados com sucesso, com testes cobrindo inclusão, busca, exclusão e visualização dos vínculos entre atores e séries.

---

## Checklist de Verificação

| Requisito                                                                                                           | Status |
|--------------------------------------------------------------------------------------------------------------------|--------|
| As operações de inclusão, busca, alteração e exclusão de atores estão implementadas e funcionando corretamente?    | ✔ SIM  |
| O relacionamento entre séries e atores foi implementado com árvores B+ e funciona corretamente, assegurando a consistência entre as duas entidades? | ✔ SIM  |
| É possível consultar quais são os atores de uma série?                                                             | ✔ SIM  |
| É possível consultar quais são as séries de um ator?                                                               | ✔ SIM  |
| A remoção de séries remove os seus vínculos de atores?                                                             | ✔ SIM  |
| A inclusão de um ator em uma série se limita aos atores existentes?                                                | ✔ SIM  |
| A remoção de um ator checa se há alguma série vinculada a ele?                                                     | ✔ SIM  |
| O trabalho está funcionando corretamente?                                                                           | ✔ SIM  |
| O trabalho está completo?                                                                                          | ✔ SIM  |
| O trabalho é original e não a cópia de um trabalho de outro grupo?                                                 | ✔ SIM  |


---

## Observações Finais

O código está estruturado de forma que novas entidades e relacionamentos possam ser adicionados com facilidade. O uso de árvores B+ provou ser eficaz para a indexação bidirecional, e o padrão de projeto adotado favorece reuso e manutenção.

Para executar, basta compilar as classes e iniciar pelo menu principal. Os arquivos de dados e índices são criados automaticamente na primeira execução.
