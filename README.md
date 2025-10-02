# CapacityDash - Sistema de Gestão de Motos Mottu

## 📊 Descrição da Solução
O CapacityDash é uma aplicação web para gestão das motos dentro do ambiente Mottu, permitindo o controle e monitoramento da frota de veículos.

## 💼 Benefícios para o Negócio
- **Otimização de Recursos**: Reduz sobrecarga aos funcionários, automatizando trabalho manual
- **Melhoria no Planejamento**: Visibilidade clara da disponibilidade de motos
- **Tomada de Decisão**: Dados em tempo real para gestão da frota
- **Redução de Custos**: Alocação eficiente de recursos e motos

## 🛠️ Tecnologias Utilizadas
- **Backend**: Java Spring Boot 3.5.5
- **Frontend**: Thymeleaf + Bootstrap
- **Banco de Dados**: PostgreSQL Azure (Flexible Server)
- **Cloud**: Microsoft Azure App Service
- **Autenticação**: OAuth2 Google
- **Build**: Maven
- **Java**: Version 21

## 🚀 Deploy na Azure

### Pré-requisitos
- Azure CLI instalado
- Conta Azure ativa
- Java 21 e Maven
- Git

### Passo a Passo do Deploy

#### 1. Clone o Repositório
```bash
git clone https://github.com/seu-usuario/capacitydash-azure.git
cd capacitydash-azure
az login
SCRIPT 1: Criar Resource Group
az group create --name capacitydash-group --location brazilsouth

SCRIPT 2: Criar PostgreSQL
az postgres flexible-server create `
    --resource-group capacitydash-group `
    --name capacitydash-db `
    --admin-user adminuser `
    --admin-password "SenhaForte123!" `
    --location brazilsouth `
    --tier Burstable `
    --sku-name Standard_B1ms `
    --version 13

SCRIPT 3: Criar Database
az postgres flexible-server db create `
    --resource-group capacitydash-group `
    --server-name capacitydash-db `
    --database-name capacitydash


SCRIPT 4: Configurar Firewall
az postgres flexible-server firewall-rule create `
    --resource-group capacitydash-group `
    --name capacitydash-db `
    --rule-name allow-azure-services `
    --start-ip-address 0.0.0.0 `
    --end-ip-address 0.0.0.0

SCRIPT 5: App Service Plan
az appservice plan create `
    --name capacitydash-plan `
    --resource-group capacitydash-group `
    --sku B1 `
    --is-linux

SCRIPT 6: Web App
az webapp create `
    --resource-group capacitydash-group `
    --plan capacitydash-plan `
    --name capacitydash-app `
    --runtime "JAVA:21-java21"

SCRIPT 7: Configurar Variáveis
az webapp config appsettings set `
    --resource-group capacitydash-group `
    --name capacitydash-app `
    --settings `
    JDBC_DATABASE_URL="jdbc:postgresql://capacitydash-db.postgres.database.azure.com:5432/capacitydash" `
    JDBC_DATABASE_USERNAME="adminuser" `
    JDBC_DATABASE_PASSWORD="SenhaForte123!" `
   

SCRIPT 8: Deploy App
mvn clean package -DskipTests
az webapp deploy `
    --resource-group capacitydash-group `
    --name capacitydash-app `
    --src-path target/capacitydash-0.0.1-SNAPSHOT.jar `
    --type jar

```

🌐 Acesso à Aplicação
Após o deploy, acesse: https://capacitydash-app.azurewebsites.net

🧪 Testes do Sistema
O sistema possui front-end completo para testar todas as funcionalidades:

Cadastro de motos

Gestão de capacidade

Relatórios e dashboard

📊 Script do Banco de Dados
Ver arquivo: script_bd.sql para estrutura completa das tabelas.
