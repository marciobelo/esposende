SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `Esposende` DEFAULT CHARACTER SET latin1 ;
USE `Esposende` ;

-- -----------------------------------------------------
-- Table `NumeroProtocolar`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `NumeroProtocolar` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `ano` INT(11) NOT NULL ,
  `seq` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Baixa`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Baixa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `dataBaixaContabil` DATETIME NULL DEFAULT NULL ,
  `dataCriacao` DATETIME NOT NULL ,
  `dataProcesso` DATETIME NULL DEFAULT NULL ,
  `dataTermoBaixa` DATETIME NULL DEFAULT NULL ,
  `justificativa` VARCHAR(255) NOT NULL ,
  `numeroProcesso` VARCHAR(255) NULL DEFAULT NULL ,
  `protocolo_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_BAIXA_NP` (`protocolo_id` ASC) ,
  CONSTRAINT `FK_BAIXA_NP`
    FOREIGN KEY (`protocolo_id` )
    REFERENCES `NumeroProtocolar` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `DocumentoDigital` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `documento` LONGBLOB NOT NULL ,
  `tipoMime` INT(11) NOT NULL ,
  `titulo` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Baixa_DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Baixa_DocumentoDigital` (
  `Baixa_id` BIGINT(20) NOT NULL ,
  `comprovantes_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`Baixa_id`, `comprovantes_id`) ,
  UNIQUE INDEX `comprovantes_id` (`comprovantes_id` ASC) ,
  INDEX `IND_FK_BDD_BAIXA` (`Baixa_id` ASC) ,
  INDEX `IND_FK_BDD_DD` (`comprovantes_id` ASC) ,
  CONSTRAINT `FK7FC9BFK_BDD_DDFA87`
    FOREIGN KEY (`comprovantes_id` )
    REFERENCES `DocumentoDigital` (`id` ),
  CONSTRAINT `FK_BDD_BAIXA`
    FOREIGN KEY (`Baixa_id` )
    REFERENCES `Baixa` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Responsavel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Responsavel` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(255) NULL DEFAULT NULL ,
  `matricula` VARCHAR(255) NOT NULL ,
  `nome` VARCHAR(60) NOT NULL ,
  `foto_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_RESP_DD` (`foto_id` ASC) ,
  CONSTRAINT `FK_RESP_DD`
    FOREIGN KEY (`foto_id` )
    REFERENCES `DocumentoDigital` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Origem`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Origem` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `detalhe` VARCHAR(255) NULL DEFAULT NULL ,
  `resumo` VARCHAR(40) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `CodigoContabil`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CodigoContabil` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `codigo` VARCHAR(255) NULL DEFAULT NULL ,
  `descricao` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Tombamento`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Tombamento` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `codTombamento` VARCHAR(255) NOT NULL ,
  `dataTombamento` DATE NOT NULL ,
  `documentoHabil` VARCHAR(255) NULL DEFAULT NULL ,
  `historicoOperacao` VARCHAR(255) NULL DEFAULT NULL ,
  `tipoOperacao` VARCHAR(255) NOT NULL ,
  `valorOperacao` DECIMAL(19,2) NOT NULL ,
  `codigoContabil_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_TOMB_CC` (`codigoContabil_id` ASC) ,
  CONSTRAINT `FK_TOMB_CC`
    FOREIGN KEY (`codigoContabil_id` )
    REFERENCES `CodigoContabil` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `LocalPermanencia`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `LocalPermanencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `BemPermanente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `BemPermanente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(255) NULL DEFAULT NULL ,
  `baixa_id` BIGINT(20) NULL DEFAULT NULL ,
  `localPermanencia_id` BIGINT(20) NULL DEFAULT NULL ,
  `origem_id` BIGINT(20) NULL DEFAULT NULL ,
  `responsavel_id` BIGINT(20) NULL DEFAULT NULL ,
  `tombamento_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_BemPermanente_Origem` (`origem_id` ASC) ,
  INDEX `IND_FK_BemPermanente_LocalPermanencia` (`localPermanencia_id` ASC) ,
  INDEX `IND_FK_BemPermanente_Tombamento` (`tombamento_id` ASC) ,
  INDEX `IND_FK_BemPermanente_Baixa` (`baixa_id` ASC) ,
  INDEX `IND_FK_BemPermanente_Responsavel` (`responsavel_id` ASC) ,
  CONSTRAINT `IND_FK_BemPermanente_Responsavel`
    FOREIGN KEY (`responsavel_id` )
    REFERENCES `Responsavel` (`id` ),
  CONSTRAINT `FK_BemPermanente_Origem`
    FOREIGN KEY (`origem_id` )
    REFERENCES `Origem` (`id` ),
  CONSTRAINT `IND_FK_BemPermanente_Baixa`
    FOREIGN KEY (`baixa_id` )
    REFERENCES `Baixa` (`id` ),
  CONSTRAINT `FK_BemPermanente_Tombamento`
    FOREIGN KEY (`tombamento_id` )
    REFERENCES `Tombamento` (`id` ),
  CONSTRAINT `FK_BemPermanente_LocalPermanencia`
    FOREIGN KEY (`localPermanencia_id` )
    REFERENCES `LocalPermanencia` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `BemPermanente_DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `BemPermanente_DocumentoDigital` (
  `BemPermanente_id` BIGINT(20) NOT NULL ,
  `fotos_id` BIGINT(20) NOT NULL ,
  UNIQUE INDEX `fotos_id` (`fotos_id` ASC) ,
  INDEX `IND_FK_BemPermanenteDocumentoDigital_DocumentoDigital` (`fotos_id` ASC) ,
  INDEX `IND_FK_BemPermanenteDocumentoDigital_BemPermanente` (`BemPermanente_id` ASC) ,
  CONSTRAINT `FK_BemPermanenteDocumentoDigital_BemPermanente`
    FOREIGN KEY (`BemPermanente_id` )
    REFERENCES `BemPermanente` (`id` ),
  CONSTRAINT `FK_BemPermanenteDocumentoDigital_DocumentoDigital`
    FOREIGN KEY (`fotos_id` )
    REFERENCES `DocumentoDigital` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `RegistroOcorrencia`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `RegistroOcorrencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `data` DATE NULL DEFAULT NULL ,
  `descricao` VARCHAR(255) NULL DEFAULT NULL ,
  `tipoRegistroOcorrencia` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `BemPermanente_RegistroOcorrencia`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `BemPermanente_RegistroOcorrencia` (
  `BemPermanente_id` BIGINT(20) NOT NULL ,
  `registrosOcorrencia_id` BIGINT(20) NOT NULL ,
  UNIQUE INDEX `registrosOcorrencia_id` (`registrosOcorrencia_id` ASC) ,
  INDEX `IND_FK_BemPermanenteRegistroOcorrencia_RegistroOcorrencia` (`registrosOcorrencia_id` ASC) ,
  INDEX `IND_FK_BemPermanenteRegistroOcorrencia_BemPermanente` (`BemPermanente_id` ASC) ,
  CONSTRAINT `FK_BemPermanenteRegistroOcorrencia_BemPermanente`
    FOREIGN KEY (`BemPermanente_id` )
    REFERENCES `BemPermanente` (`id` ),
  CONSTRAINT `FK_BemPermanenteRegistroOcorrencia_RegistroOcorrencia`
    FOREIGN KEY (`registrosOcorrencia_id` )
    REFERENCES `RegistroOcorrencia` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Inventario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Inventario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `dataEmissao` DATE NOT NULL ,
  `dataFechamento` DATE NULL DEFAULT NULL ,
  `protocolo_id` BIGINT(20) NULL DEFAULT NULL ,
  `responsavel_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_Inventario_Responsavel` (`responsavel_id` ASC) ,
  INDEX `IND_FK_Inventario_NumeroProtocolar` (`protocolo_id` ASC) ,
  CONSTRAINT `FK_Inventario_NumeroProtocolar`
    FOREIGN KEY (`protocolo_id` )
    REFERENCES `NumeroProtocolar` (`id` ),
  CONSTRAINT `IND_FK_Inventario_Responsavel`
    FOREIGN KEY (`responsavel_id` )
    REFERENCES `Responsavel` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Confere`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Confere` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `situacao` VARCHAR(255) NULL DEFAULT NULL ,
  `bemPermanente_id` BIGINT(20) NOT NULL ,
  `inventario_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_Confere_BemPermanente` (`bemPermanente_id` ASC) ,
  INDEX `IND_FK_Confere_Inventario` (`inventario_id` ASC) ,
  CONSTRAINT `FK_Confere_Inventario`
    FOREIGN KEY (`inventario_id` )
    REFERENCES `Inventario` (`id` ),
  CONSTRAINT `IND_FK_Confere_BemPermanente`
    FOREIGN KEY (`bemPermanente_id` )
    REFERENCES `BemPermanente` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Configuracoes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Configuracoes` (
  `id` BIGINT(20) NOT NULL ,
  `responsavelinstitucional` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_Configuracoes_Responsavel` (`responsavelinstitucional` ASC) ,
  CONSTRAINT `FK_Configuracoes_Responsavel`
    FOREIGN KEY (`responsavelinstitucional` )
    REFERENCES `Responsavel` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Inventario_Confere`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Inventario_Confere` (
  `Inventario_id` BIGINT(20) NOT NULL ,
  `conferidos_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`Inventario_id`, `conferidos_id`) ,
  UNIQUE INDEX `conferidos_id` (`conferidos_id` ASC) ,
  INDEX `IND_FK_InventarioConfere_Inventario` (`Inventario_id` ASC) ,
  INDEX `IND_FK_InventarioConfere_Confere` (`conferidos_id` ASC) ,
  CONSTRAINT `IND_FK_InventarioConfere_Confere`
    FOREIGN KEY (`conferidos_id` )
    REFERENCES `Confere` (`id` ),
  CONSTRAINT `FK_InventarioConfere_Inventario`
    FOREIGN KEY (`Inventario_id` )
    REFERENCES `Inventario` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `Inventario_DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Inventario_DocumentoDigital` (
  `Inventario_id` BIGINT(20) NOT NULL ,
  `relatorioAssinado_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`Inventario_id`, `relatorioAssinado_id`) ,
  UNIQUE INDEX `relatorioAssinado_id` (`relatorioAssinado_id` ASC) ,
  INDEX `IND_FK_InventarioDocumentoDigital_DocumentoDigital` (`relatorioAssinado_id` ASC) ,
  INDEX `IND_FK_InventarioDocumentoDigital_Inventario` (`Inventario_id` ASC) ,
  CONSTRAINT `FK_InventarioDocumentoDigital_Inventario`
    FOREIGN KEY (`Inventario_id` )
    REFERENCES `Inventario` (`id` ),
  CONSTRAINT `FK_InventarioDocumentoDigital_DocumentoDigital`
    FOREIGN KEY (`relatorioAssinado_id` )
    REFERENCES `DocumentoDigital` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TermoSubRogo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TermoSubRogo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `dataEmissao` DATE NULL DEFAULT NULL ,
  `dataEncerramento` DATE NULL DEFAULT NULL ,
  `dataPrevistaEncerramento` DATE NULL DEFAULT NULL ,
  `dataSubRogo` DATE NULL DEFAULT NULL ,
  `proposito` VARCHAR(255) NULL DEFAULT NULL ,
  `protocolo_id` BIGINT(20) NULL DEFAULT NULL ,
  `subrogado_id` BIGINT(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `IND_FK_TermoSubRogo_Responsavel` (`subrogado_id` ASC) ,
  INDEX `IND_FK_TermoSubRogo_NumeroProtocolar` (`protocolo_id` ASC) ,
  CONSTRAINT `FK_TermoSubRogo_NumeroProtocolar`
    FOREIGN KEY (`protocolo_id` )
    REFERENCES `NumeroProtocolar` (`id` ),
  CONSTRAINT `FK_TermoSubRogo_Responsavel`
    FOREIGN KEY (`subrogado_id` )
    REFERENCES `Responsavel` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TermoSubRogo_BemPermanente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TermoSubRogo_BemPermanente` (
  `TermoSubRogo_id` BIGINT(20) NOT NULL ,
  `arrolados_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`TermoSubRogo_id`, `arrolados_id`) ,
  INDEX `IND_FK_TermoSubRogoBemPermanente_TermoSubRogo` (`TermoSubRogo_id` ASC) ,
  INDEX `IND_FK_TermoSubRogoBemPermanente_BemPermanente` (`arrolados_id` ASC) ,
  CONSTRAINT `FK_TermoSubRogoBemPermanente_BemPermanente`
    FOREIGN KEY (`arrolados_id` )
    REFERENCES `BemPermanente` (`id` ),
  CONSTRAINT `FK_TermoSubRogoBemPermanente_TermoSubRogo`
    FOREIGN KEY (`TermoSubRogo_id` )
    REFERENCES `TermoSubRogo` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `TermoSubRogo_DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TermoSubRogo_DocumentoDigital` (
  `TermoSubRogo_id` BIGINT(20) NOT NULL ,
  `termosAssinados_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`TermoSubRogo_id`, `termosAssinados_id`) ,
  UNIQUE INDEX `termosAssinados_id` (`termosAssinados_id` ASC) ,
  INDEX `IND_FK_TermoSubRogoDocumentoDigital_TermoSubRogo` (`TermoSubRogo_id` ASC) ,
  INDEX `IND_FK_TermoSubRogoDocumentoDigital_DocumentoDigital` (`termosAssinados_id` ASC) ,
  CONSTRAINT `FK_TermoSubRogoDocumentoDigital_DocumentoDigital`
    FOREIGN KEY (`termosAssinados_id` )
    REFERENCES `DocumentoDigital` (`id` ),
  CONSTRAINT `FK_TermoSubRogoDocumentoDigital_TermoSubRogo`
    FOREIGN KEY (`TermoSubRogo_id` )
    REFERENCES `TermoSubRogo` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `Tombamento_DocumentoDigital`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Tombamento_DocumentoDigital` (
  `Tombamento_id` BIGINT(20) NOT NULL ,
  `comprovantesOperacao_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`Tombamento_id`, `comprovantesOperacao_id`) ,
  UNIQUE INDEX `comprovantesOperacao_id` (`comprovantesOperacao_id` ASC) ,
  INDEX `IND_FK_TombamentoDocumentoDigital_DocumentoDigital` (`comprovantesOperacao_id` ASC) ,
  INDEX `IND_FK_TombamentoDocumentoDigital_Tombamento` (`Tombamento_id` ASC) ,
  CONSTRAINT `FK_TombamentoDocumentoDigital_Tombamento`
    FOREIGN KEY (`Tombamento_id` )
    REFERENCES `Tombamento` (`id` ),
  CONSTRAINT `FK_TombamentoDocumentoDigital_DocumentoDigital`
    FOREIGN KEY (`comprovantesOperacao_id` )
    REFERENCES `DocumentoDigital` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
