// package Compilation;
// import java.io.*;

%% 
%include Jflex.include

%{     /* code dans la clase : attibuts et méthodes utiles pour les actions */
%}

%init{ /* code dans le constructeur : action initiale */
System.out.println("Analyse Lexicale Sample0 (type any text) :");
%init}

%eof{  /* code en action final */
System.out.println("Bye!");
%eof}

// %caseless            /* confondre minuscules/majuscules   */
// %state   ETAT, ETAT2 /* Etats inclusifs du super-automate */
// %xstate  STATE       /* Etats exclusifs du super-automate */

ANY   = [^]
BLANC = [ \t]

%% 
[a-zA-Z] [a-zA-Z0-9]* { ECHO("IDENT"); }
{BLANC}+              { /* ignore */ }
\R                    { ECHO();      }
{ANY}                 { WARN("Invalid char '"+yytext()+"'"); }
