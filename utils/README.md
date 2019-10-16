# Environnement pour le cours Compilation CSC4536

#### Utilisation simple
- Mettre le directory `./bin` dans la variable d'environnement __ PATH __
- Utiliser les commandes de `./bin` pour créer un projet
    - ` Compil-new-jflex monLexer `  =>  Arboresence pour JFlex Alone
    - ` Compil-new-cup monParser`  => Arboresence pour JFlex + CUP
    - ` Compil-new-link`  => crestion lien ./lib vers les librairies JFlex/Cup
    - ` Compil-new-spec old new`  => copie old.jflex et old.cup en new.jflex et new.cup
    
    - jflex, cup, mars : racourcis pour les outils JFlex, CUP, MARS
    - javacup : commande java avec runtime CUP. (CLASSPATH=./bin:xxx/java-cup-runtim.jar)
    
- Decouvrir le reste avec `make` ou `ant`

#### Contenu
- `./bin`   Quelques commandes pratiques
- `./lib`   .jar des outils JFlex, CUP et MARS
- `./editor`  mode JFlex pour editeurs emacs ou vim
- `./proto-jflex`   squelette de projet JFlex (cf Compil-new-jflex)
- `./proto-cup`   squelette de projet CUP + JFlex (cf Compil-new-cup)
- `./unused`  May be usefull ?!


