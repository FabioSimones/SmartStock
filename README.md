## Projeto Smart Stock

--([Fábio Simones](https://github.com/FabioSimones))

### Resumo do projeto
<p>
  O projeto Smart Stock, desenvolvido para automatizar o processo de recompra de itens em uma empresa de e-commerce. 
  Atualmente, o processo de recompra é realizado de forma manual: quando a quantidade de um item no estoque cai abaixo 
  do nível mínimo, um analista negocia diretamente com o setor de compras.
</p>

![image](https://github.com/user-attachments/assets/ee4d4ffe-dd0b-4208-8e5c-e67f9deac256)

<p>
  Com o Smart Stock, vamos construir uma aplicação Spring Boot que automatiza esse fluxo. O sistema vai:
</p>

<ul>
  <li>Ler um arquivo CSV gerado por um sistema legado que contém os dados de estoque;</li>
  <li>Aplicar regras de negócio para identificar os itens que precisam ser repostos;</li>
  <li>Integrar com a API do setor de compras, utilizando um token de autenticação para garantir a segurança da comunicação;</li>
  <li>Persistir os dados no MongoDB para acompanhamento e auditoria.</li>
</ul>


### Tecnologias utilizadas
<p>
  Foi utilizado neste projeto as seguintes tecnologias: Docker, MongoDB, Postman, Mockoon.
</p>

### Regras de negócio
<ul>
  <li> Deveremos consumir o arquivo diário no formato CSV.</li>
  <ul>
    <li> Ele terá os seguintes campos:</li>
    <ul>
      <li> item_id (uuid)</li>
      <li> item_name</li>
      <li> quantity</li>
      <li> reorder_threshold</li>
      <li> supplier_name</li>
      <li> supplier_email</li>
      <li> last_stock_update_time</li>
    </ul>
  </ul>
  <li>
     Quando um item estiver abaixo da quantidade mínima em estoque, deveremos solicitar a recompra com a quantidade mínimia + 20% de margem de segurança
  </li>
  <li> Para interagir com a API do Setor de Compras, deveremos:</li>
  <ul>
    <li> Autenticar na API</li>
    <li> Realizar a chamada utilizando o token da etapa anterior</li>
  </ul>
  <li>
     Deveremos manter armazenado todos os itens que tiveram uma solicitação de recompra, possuindo as seguintes informações:
  </li>
  <ul>
    <li> Identificação do Item</li>
    <li> Nome do Item</li>
    <li> Quantidade de estoque</li>
    <li> Quantidade mínima de itens do estoque</li>
    <li> Nome do fornecedor</li>
    <li> Email do fornecedor</li>
    <li> Última atualização do item no estoque</li>
    <li> Quantidade de recompra solicitada</li>
    <li> Indicativo se a recompra foi enviada com sucesso ou falha</li>
    <li> Data e hora do envio da solicitação de recompra.</li>
  </ul>
</ul>
