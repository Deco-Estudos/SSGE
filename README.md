# 📘 Sistema de Gestão de Gastos Escolares (SGGE)

O **SGGE** é um sistema web para controle e análise dos **gastos de escolas estaduais**.  
Ele permite que a **Secretaria da Educação** crie **combos de gastos** que devem ser preenchidos por órgãos intermediários (regionais, departamentos) e escolas.  
Com isso, é possível calcular e comparar o **gasto por aluno** de cada escola, trazendo mais transparência e eficiência para a gestão pública.

---

## 🚀 Funcionalidades

- 👤 **Perfis de usuários**: Secretaria (ADM), Regionais, Departamentos e Escolas.  
- 🏛️ **Hierarquia organizacional**: Secretaria → Órgãos intermediários → Escolas.  
- 📦 **Combos de gastos**: criados pela Secretaria e atribuídos às escolas.  
- 📝 **Preenchimento de gastos**: realizado por diferentes níveis da hierarquia, mas sempre associado a uma escola final.  
- 📊 **Cálculo de indicadores**: gasto total e gasto por aluno.  
- 🔒 **Controle de acesso** com base no perfil do usuário.  
- 🗂️ **Histórico de preenchimentos** para auditoria.  

---

## 🗄️ Modelo de Dados

O sistema segue um modelo **relacional**.  
Entidades principais:  
- **Secretaria**  
- **Departamentos**
- **Diretorias Regionais**  
- **Escola**  
- **Combo de Gastos**  
- **PreenchimentoCombo**  

---

## 🛠️ Tecnologias

- **Backend:** Java 
- **Banco de Dados:** MySQL
- **Frontend:** React.js
- **Containerização:** Docker
