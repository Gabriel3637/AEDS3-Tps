# AEDS3-Tps

# Trabalho Prático - Sistema de Arquivos da Entidade Série

## Descrição do Trabalho

Nosso trabalho consiste na implementação de um sistema de arquivos genérico em Java, capaz de realizar operações de CRUD (Create, Read, Update, Delete) sobre registros de uma entidade séries. O sistema é baseado no uso da classe `RandomAccessFile`, com controle manual de alocação dos dados em disco e suporte a indexação eficiente por meio de **hashing extensível**.

A aplicação é modular, de modo que é possível reutilizá-la com diferentes tipos de dados, desde que estes implementem a interface `Registro`. O sistema garante persistência e acesso eficiente aos dados, mesmo com inserções, exclusões e atualizações realizadas em ordem aleatória.

## Participantes

- Gabriel Valedo
- Henrique Gilberti
- Leonardo Amaral 

## Estrutura de Classes

### `Registro`
Interface que define os métodos básicos que qualquer tipo de dado deve implementar para ser manipulado pelo sistema:
- `byte[] toByteArray()`: Serializa o objeto em um vetor de bytes.
- `void fromByteArray(byte[] ba)`: Reconstrói o objeto a partir de um vetor de bytes.
- `int getID()`: Retorna o identificador único do registro.

### `Arquivo<T extends Registro>`
Classe genérica responsável pelas operações CRUD. Principais métodos:
- `int create(T obj)`: Cria um novo registro no arquivo e retorna seu ID.
- `T read(int id)`: Lê um registro com base no ID.
- `boolean update(T novoObj)`: Atualiza um registro existente.
- `boolean delete(int id)`: Remove logicamente um registro.

### `HashExtensivel<T extends Registro>`
Classe de indexação baseada em hashing extensível. Permite localizar rapidamente os endereços dos registros no arquivo de dados.
- `void create(int chave, long endereco)`: Adiciona uma nova entrada ao índice.
- `long read(int chave)`: Recupera o endereço associado a uma chave.
- `boolean delete(int chave)`: Remove uma entrada do índice.


### `EntidadeExemplo` 
Classe que representa a série. Implementa a interface `Registro` com seus próprios atributos e lógica de serialização.

---

## Relato da Experiência

Implementamos todos os requisitos especificados no trabalho. O desenvolvimento foi dividido entre os membros do grupo, com reuniões frequentes para integração das partes. 

### Principais desafios:
- **Manipulação de arquivos binários** com `RandomAccessFile` exigiu atenção cuidadosa para evitar sobrescritas e garantir alinhamento correto dos dados.
- A **implementação do hashing extensível** foi a parte mais desafiadora, especialmente no controle de splits de buckets e duplicação do diretório.
- Testar as atualizações foi delicado, pois exigia reescrita parcial de dados e atualização do índice.
- 
### Aprendizado:
O projeto proporcionou um entendimento profundo sobre o funcionamento de sistemas de arquivos de baixo nível, organização de dados em disco e técnicas de indexação. Trabalhar com manipulação manual de bytes e endereços simulou bem o comportamento de sistemas reais de banco de dados.

### Resultados:

| Requisito                                                                                         | Status |
|--------------------------------------------------------------------------------------------------|--------|
| O trabalho possui um índice direto implementado com a tabela hash extensível?                   | ✔ SIM  |
| A operação de inclusão insere um novo registro no fim do arquivo e no índice e retorna o ID?   | ✔ SIM  |
| A operação de busca retorna os dados do registro, após localizá-lo por meio do índice direto?  | ✔ SIM  |
| A operação de alteração trata corretamente aumentos e reduções no espaço do registro?          | ✔ SIM  |
| A operação de exclusão marca o registro como excluído e o remove do índice direto?             | ✔ SIM  |
| O trabalho está funcionando corretamente?                                                       | ✔ SIM  |
| O trabalho está completo?                                                                       | ✔ SIM  |
| O trabalho é original e não é cópia de outro grupo?                                             | ✔ SIM  |

---
