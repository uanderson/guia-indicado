DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `pais_id` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `sigla` varchar(3) NOT NULL,
  PRIMARY KEY (`pais_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `estado_id` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `sigla` varchar(2) NOT NULL,
  `pais_id` int(11) NOT NULL,
  PRIMARY KEY (`estado_id`),
  KEY `fk_estados_paises1_idx` (`pais_id`),
  CONSTRAINT `fk_estados_paises1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`pais_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cidade`;
CREATE TABLE `cidade` (
  `cidade_id` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `latitude` decimal(12,6) NOT NULL,
  `longitude` decimal(12,6) NOT NULL,
  `indicada` tinyint(4) NOT NULL,
  `estado_id` int(11) NOT NULL,
  PRIMARY KEY (`cidade_id`),
  KEY `fk_cidades_estados_idx` (`estado_id`),
  CONSTRAINT `fk_cidades_estados` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`estado_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cep`;
CREATE TABLE `cep` (
  `cep_id` varchar(8) NOT NULL,
  `rua` varchar(255) NOT NULL DEFAULT '',
  `bairro` varchar(255) NOT NULL DEFAULT '',
  `cidade_id` int(11) NOT NULL,
  PRIMARY KEY (`cep_id`),
  KEY `fk_ceps_cidades1_idx` (`cidade_id`),
  CONSTRAINT `fk_ceps_cidades1` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`cidade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `permissao`;
CREATE TABLE `permissao` (
  `permissao_id` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`permissao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(70) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(64) NOT NULL,
  `habilitado` tinyint(4) NOT NULL,
  `receber_email` tinyint(4) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `cidade_id` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_usuarios_cidades1_idx` (`cidade_id`),
  CONSTRAINT `fk_usuarios_cidades1` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`cidade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usuario_x_permissao`;
CREATE TABLE `usuario_x_permissao` (
  `usuario_id` int(11) NOT NULL,
  `permissao_id` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`,`permissao_id`),
  KEY `fk_usuarios_has_permissoes_permissoes1_idx` (`permissao_id`),
  KEY `fk_usuarios_has_permissoes_usuarios1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuarios_has_permissoes_usuarios1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_has_permissoes_permissoes1` FOREIGN KEY (`permissao_id`) REFERENCES `permissao` (`permissao_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `token_usuario`;
CREATE TABLE `token_usuario` (
  `token_usuario_id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) NOT NULL,
  `tipo` int(11) NOT NULL,
  `data_hora` datetime NOT NULL,
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`token_usuario_id`),
  KEY `fk_token_usuario_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_token_usuario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `anunciante`;
CREATE TABLE `anunciante` (
  `anunciante_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `imagem` varchar(100) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `site` varchar(255) NOT NULL,
  `data_hora_cadastro` datetime NOT NULL,
  `cidade_id` int(11) NOT NULL,
  PRIMARY KEY (`anunciante_id`),
  KEY `fk_anunciante_cidade1_idx` (`cidade_id`),
  CONSTRAINT `fk_anunciante_cidade1` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`cidade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `banner_id` int(11) NOT NULL AUTO_INCREMENT,
  `imagem` varchar(100) NOT NULL,
  `url` varchar(255) NOT NULL,
  `posicao` int(11) NOT NULL,
  `tipo_media` int(11) NOT NULL,
  `ativo` tinyint(4) NOT NULL,
  `anunciante_id` int(11) NOT NULL,
  PRIMARY KEY (`banner_id`),
  KEY `fk_banner_anunciante1_idx` (`anunciante_id`),
  CONSTRAINT `fk_banner_anunciante1` FOREIGN KEY (`anunciante_id`) REFERENCES `anunciante` (`anunciante_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `categoria_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `referencia` varchar(120) NOT NULL,
  PRIMARY KEY (`categoria_id`),
  UNIQUE KEY `nome_UNIQUE` (`nome`),
  UNIQUE KEY `referencia_UNIQUE` (`referencia`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `categoria_direta`;
CREATE TABLE `categoria_direta` (
  `categoria_direta_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(14) NOT NULL,
  PRIMARY KEY (`categoria_direta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `categoria_direta_x_categoria`;
CREATE TABLE `categoria_direta_x_categoria` (
  `categoria_direta_id` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  PRIMARY KEY (`categoria_direta_id`,`categoria_id`),
  KEY `fk_categoria_direta_has_categoria_categoria1_idx` (`categoria_id`),
  KEY `fk_categoria_direta_has_categoria_categoria_direta1_idx` (`categoria_direta_id`),
  CONSTRAINT `fk_categoria_direta_has_categoria_categoria_direta1` FOREIGN KEY (`categoria_direta_id`) REFERENCES `categoria_direta` (`categoria_direta_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_categoria_direta_has_categoria_categoria1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `categoria_relacionada`;
CREATE TABLE `categoria_relacionada` (
  `categoria_relacionada_id` int(11) NOT NULL AUTO_INCREMENT,
  `categoria_id` int(11) NOT NULL,
  PRIMARY KEY (`categoria_relacionada_id`),
  KEY `fk_categoria_relacionada_categoria1_idx` (`categoria_id`),
  CONSTRAINT `fk_categoria_relacionada_categoria1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE `empresa` (
  `empresa_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `referencia` varchar(200) NOT NULL,
  `descricao` text NOT NULL,
  `tags` text NOT NULL,
  `cep` varchar(8) NOT NULL,
  `endereco` varchar(70) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `bairro` varchar(70) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `celular` varchar(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `site` varchar(255) NOT NULL,
  `latitude` decimal(12,6) NOT NULL,
  `longitude` decimal(12,6) NOT NULL,
  `status` int(11) NOT NULL,
  `cidade_id` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `data_hora_cadastro` datetime NOT NULL,
  `usuario_cadastro` int(11) NOT NULL,
  PRIMARY KEY (`empresa_id`),
  KEY `fk_empresas_cidades1_idx` (`cidade_id`),
  KEY `fk_empresa_categoria1_idx` (`categoria_id`),
  KEY `fk_empresa_usuario1_idx` (`usuario_cadastro`),
  KEY `index_empresa_referencia` (`referencia`),
  CONSTRAINT `fk_empresas_cidades1` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`cidade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_categoria1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresa_usuario1` FOREIGN KEY (`usuario_cadastro`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1827 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `avaliacao`;
CREATE TABLE `avaliacao` (
  `avaliacao_id` int(11) NOT NULL AUTO_INCREMENT,
  `nota` int(11) NOT NULL,
  `empresa_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `data_hora` datetime NOT NULL,
  `ip` varchar(40) NOT NULL,
  PRIMARY KEY (`avaliacao_id`),
  KEY `fk_classificacao_empresa_empresa1_idx` (`empresa_id`),
  KEY `fk_classificacao_empresa_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_classificacao_empresa_empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`empresa_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_classificacao_empresa_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `imagem`;
CREATE TABLE `imagem` (
  `imagem_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `principal` tinyint(4) NOT NULL,
  `empresa_id` int(11) NOT NULL,
  PRIMARY KEY (`imagem_id`),
  KEY `fk_imagem_empresa1_idx` (`empresa_id`),
  CONSTRAINT `fk_imagem_empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`empresa_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `pais` VALUES (0,'Não Informado','NI'),(76,'Brasil','BRA');
INSERT INTO `estado` VALUES (0,'Não Informado','NI',0),(11,'Rondônia','RO',76),(12,'Acre','AC',76),(13,'Amazonas','AM',76),(14,'Roraima','RR',76),(15,'Pará','PA',76),(16,'Amapá','AP',76),(17,'Tocantins','TO',76),(21,'Maranhão','MA',76),(22,'Piauí','PI',76),(23,'Ceará','CE',76),(24,'Rio Grande do Norte','RN',76),(25,'Paraíba','PB',76),(26,'Pernambuco','PE',76),(27,'Alagoas','AL',76),(28,'Sergipe','SE',76),(29,'Bahia','BA',76),(31,'Minas Gerais','MG',76),(32,'Espírito Santo','ES',76),(33,'Rio de Janeiro','RJ',76),(35,'São Paulo','SP',76),(41,'Paraná','PR',76),(42,'Santa Catarina','SC',76),(43,'Rio Grande do Sul','RS',76),(50,'Mato Grosso do Sul','MS',76),(51,'Mato Grosso','MT',76),(52,'Goiás','GO',76),(53,'Distrito Federal','DF',76);

INSERT INTO `categoria` VALUES (0,'Sem categoria','sem-categoria'),(1,'Academia','academia'),(2,'Acessório','acessorio'),(3,'Advogado','advogado'),(4,'Agricultura e pecuária','agricultura-e-pecuaria'),(5,'Alfaiate','alfaiate'),(6,'Alimento','alimento'),(7,'Análise clínica','analise-clinica'),(8,'Animal de estimação','animal-de-estimacao'),(9,'Antiguidade','antiguidade'),(10,'Antiquário','antiquario'),(11,'Arma e munição','arma-e-municao'),(12,'Armarinho','armarinho'),(13,'Armazém','armazem'),(14,'Arquitetura','arquitetura'),(15,'Arte e artesanato','arte-e-artesanato'),(16,'Auto elétrica','auto-eletrica'),(17,'Auto escola','auto-escola'),(18,'Automóvel','automovel'),(19,'Auto peça','auto-peca'),(20,'Aviamentos','aviamentos'),(21,'Banco','banco'),(22,'Bares','bar'),(23,'Bebê','bebe'),(24,'Bebida','bebida'),(25,'Bicicletaria','bicicletaria'),(26,'Boate','boate'),(27,'Bombas','bomba'),(28,'Borracharia','borracharia'),(29,'Bufê','bufe'),(30,'Cabeleireiro','cabelereiro'),(31,'Caça e pesca','caca-e-pesca'),(32,'Café','cafe'),(33,'Calçado','calcado'),(34,'Camping','camping'),(35,'Caridade','caridade'),(36,'Cartório e tabelião','cartorio-e-tabeliao'),(37,'Casa e jardim','casa-e-jardim'),(38,'Casa de carne','casa-de-carne'),(39,'Choperia','choperia'),(40,'Cinema','cinema'),(41,'Climatização','climatizacao'),(42,'Clínica','clinica'),(43,'Club','club'),(44,'Colégio','colegio'),(45,'Computador','computador'),(46,'Comunicação','comunicacao'),(47,'Confecção','confeccao'),(48,'Confeitaria','confeitaria'),(49,'Construção','construcao'),(50,'Contabilidade','contabilidade'),(51,'Costureira','costureira'),(52,'Curso','curso'),(53,'Curso superior','curso-superior'),(54,'Dentista','dentista'),(55,'Despachante','despachante'),(56,'Doceria','doceria'),(57,'Editora','editora'),(58,'Educação','educacao'),(59,'Eletrônico','eletronico'),(60,'Embalagem','embalagem'),(61,'Engenharia','engenharia'),(62,'Ensino a distância','ensino-a-distancia'),(63,'Ensino fundamental','ensino-fundamental'),(64,'Ensino médio','ensino-medio'),(65,'Entretenimento','entretenimento'),(66,'Equipamento','equipamento'),(67,'Especialização','especializacao'),(68,'Esporte','esporte'),(69,'Estética e beleza','estetica-e-beleza'),(70,'Faculdade','faculdade'),(71,'Farmácia','farmacia'),(72,'Ferragem','ferragem'),(73,'Ferramenta','ferramenta'),(74,'Festa e evento','festa-e-evento'),(75,'Financeira','financeira'),(76,'Floricultura','floricultura'),(77,'Fotografia','fotografia'),(78,'Frigorífico','frigorifico'),(79,'Funerária','funeraria'),(80,'Gráfica','grafica'),(81,'Hobby','hobby'),(82,'Hospital','hospital'),(83,'Hotel','hotel'),(84,'Imobiliária','imobiliaria'),(85,'Importação e exportação','importacao-e-exportacao'),(86,'Indústria','industria'),(87,'Informática','informatica'),(88,'Internet','internet'),(89,'Joalheria','joalheria'),(90,'Jogo','jogo'),(91,'Laboratório','laboratorio'),(92,'Lanchonete','lanchonete'),(93,'Livraria','livraria'),(94,'Loja','loja'),(95,'Lubrificante','lubrificante'),(96,'Máquina','maquina'),(97,'Material','material'),(98,'Material elétrico','material-eletrico'),(99,'Mecânica','mecanica'),(101,'Médico','medico'),(102,'Mercearia','mercearia'),(103,'Mestrado','mestrado'),(104,'Moda','moda'),(105,'Motel','motel'),(106,'Moto','moto'),(107,'Música','musica'),(108,'Negócio','negocio'),(109,'Nutricionista','nutricionista'),(110,'Oficina','oficina'),(111,'Orgão público','orgao-publico'),(112,'Ótica','otica'),(113,'Padaria','padaria'),(114,'Paisagismo','paisagismo'),(115,'Papelaria','papelaria'),(116,'Peixaria','peixaria'),(117,'Pizzaria','pizzaria'),(118,'Pós-graduação','pos-graduacao'),(119,'Posto de combustível','posto-de-combustivel'),(120,'Pousada','pousada'),(121,'Presente','presente'),(122,'Produto artesanal','produto-artesanal'),(123,'Publicidade','publicidade'),(124,'Quitanda','quitanda'),(125,'Relojoaria','relojoaria'),(126,'Restaurante','restaurante'),(127,'Revenda','revenda'),(128,'Saúde','saude'),(129,'Segurança','seguranca'),(130,'Seguro','seguro'),(131,'Serviço','servico'),(132,'Som','som'),(133,'Supermercado','supermercado'),(134,'Táxi','taxi'),(135,'Telecomunicação','telecomunicacao'),(136,'Tinta','tinta'),(137,'Transporte','transporte'),(138,'Turismo','turismo'),(139,'Universidade','universidade'),(140,'Veículo','veiculo'),(141,'Vidraçaria','vidracaria'),(142,'Escola de linguas','escola-de-linguas'),(143,'Moveis','moveis'),(144,'Tapecaria','tapecaria'),(145,'Distribuidora','distribuidora'),(146,'Gas','gas'),(147,'Deposito','deposito'),(148,'Clinica veterinaria','clinica-veterinaria'),(149,'Xerocadora','xerocadora'),(150,'Lavanderia','lavanderia'),(151,'Loterica','loterica'),(152,'Eletrodomesticos','eletrodomesticos'),(153,'Decoracao','decoracao'),(154,'Colchoes','colchoes');

INSERT INTO `categoria_direta` VALUES (1,'Empresas'),(2,'Produtos'),(3,'Serviços'),(4,'Empresas'),(5,'Produtos'),(6,'Serviços');
INSERT INTO `categoria_direta_x_categoria` VALUES (1,1),(1,2),(1,8),(1,12),(1,14),(1,15),(1,17),(1,18),(3,18),(1,21),(1,22),(1,30),(1,32),(1,37),(2,41),(2,49),(2,58),(2,65),(2,68),(2,69),(2,71),(2,74),(2,76),(2,77),(2,83),(2,84),(2,87),(3,92),(3,104),(3,105),(3,113),(3,117),(3,119),(3,126),(3,128),(3,129),(3,131),(3,133),(3,137);

INSERT INTO `permissao` VALUES (1,'ROLE_USUARIO'),(2,'ROLE_ADMINISTRADOR');
