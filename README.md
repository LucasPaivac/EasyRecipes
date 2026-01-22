# EasyRecipes — Android App (Jetpack Compose)

EasyRecipes é um aplicativo Android desenvolvido em Kotlin, utilizando Jetpack Compose e arquitetura MVVM, que permite ao usuário descobrir, buscar e visualizar receitas detalhadas, consumindo a API pública Spoonacular.
O projeto foi desenvolvido com foco em boas práticas modernas, separação de responsabilidades, performance e controle de consumo de API.

## Preview do App
Telas principais do aplicativo:

<img src = "https://github.com/user-attachments/assets/91d94717-8d7b-47d5-8b25-8433510a97d1" width = 260 /> <img src = "https://github.com/user-attachments/assets/785071d5-d048-4cd5-a1c9-6bec575a520f" width = 260 /> <img src = "https://github.com/user-attachments/assets/b24174e3-2a01-45a0-a7c7-dd7d11488c50" width = 260 />

## Funcionalidades Principais
### Busca de Receitas em Tempo Real
  - Campo de busca com debounce para evitar múltiplas requisições
  - Atualização da lista atual, sem navegação para outra tela
  - Retorno automático para a lista inicial ao limpar o campo

### Lista de Receitas
  - Carregamento inicial com receitas aleatórias
  - Cache em memória para evitar chamadas desnecessárias
  - Scroll performático com LazyColumn

### Tela de Detalhe da Receita
  - Informações completas da receita:
    - Imagem
    - Tempo de preparo
    - Porções
    - Calorias
    - Lista de ingredientes detalhada

### Consumo otimizado da API usando:
  - information?includeNutrition=true
  - Reaproveitamento de dados já disponíveis

### Arquitetura & Conceitos Aplicados
  - MVVM (Model–View–ViewModel)
  - Separação clara entre:
    - UI (Compose)
    - ViewModel
    - Service (Retrofit)
  - Uso de StateFlow para controle de estado reativo

### State Management
  - MutableStateFlow + collectAsState()
  - UI reativa baseada em estado
  - Tratamento de loading e atualização automática da tela

### Debounce & Performance
   - Uso de debounce() e distinctUntilChanged() no fluxo de busca
   - Redução significativa de chamadas à API
   - Melhor experiência para o usuário

### Cache em Memória
  - Cache da lista principal (receitas aleatórias)
  - Cache de buscas por termo
  - Evita requisições repetidas ao voltar de telas ou refazer buscas

### Consumo de API
  - API: Spoonacular
  - Biblioteca: Retrofit + OkHttp
  - Interceptor para autenticação via header (x-api-key)
  - Endpoints utilizados:
    - recipes/random
    - recipes/complexSearch
    - recipes/{id}/information?includeNutrition=true

### Controle de Quota
  - Evita chamadas duplicadas
  - Busca condicionada por tamanho mínimo do texto
  - Reuso de dados já carregados

### UI & Jetpack Compose
  - UI totalmente declarativa com Jetpack Compose
  - Componentes customizados:
    - SearchBar
    - RecipeCard
    - IngredientList
  - Uso de:
    - Material 3
    - AndroidView para renderização de HTML
    - Layout responsivo e moderno

### Tecnologias Utilizadas
  - Kotlin
  - Jetpack Compose
  - Material 3
  - MVVM
  - StateFlow / Coroutines
  - Retrofit
  - OkHttp
  - Spoonacular API

### Aprendizados Destacados

Este projeto consolidou conhecimentos importantes como:
 - Arquitetura MVVM na prática
 - Gerenciamento de estado com Flow
 - Performance em buscas
 - Otimização de consumo de APIs
 - Desenvolvimento moderno Android com Compose

### Autor
Lucas Paiva
Estudante de Desenvolvimento Android
Foco em Kotlin, Jetpack Compose e Arquitetura MVVM
[LinkedIn]([url](https://www.linkedin.com/in/lucas-paiva-cedro-149452122/))
