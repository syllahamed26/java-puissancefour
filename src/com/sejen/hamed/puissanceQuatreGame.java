package com.sejen.hamed;

import java.util.Scanner;

public class puissanceQuatreGame {
    private final static int vide =0;
    private final static int jaune =1;
    private final static int rouge =2;
    private static Scanner key = new Scanner(System.in);

    static int[][] initGrille(int[][] grille){
        for (int i=0; i<grille.length; ++i){
            for (int j = 0; j<grille[i].length; ++j){
                grille[i][j] = vide;
            }
        }
        return grille;
    }

    static void afficheGrille(int[][] grille){
        for (int i=0; i<grille.length; ++i){
            System.out.print("| ");
            for (int j = 0; j<grille[i].length; ++j){
                if (grille[i][j] == 0){
                    System.out.print("  | ");
                }else if (grille[i][j]==1){
                    System.out.print("X | "); //case jaune
                }else {
                    System.out.print("O | "); //case rouge
                }
                //System.out.print(grille[i][j]+" | ");
            }
            System.out.println();
        }

        //System.out.print("=");
        for (int i=0; i<grille[0].length;++i){
            System.out.print("  "+(i+1)+" ");
        }
        System.out.println();
    }

    static boolean joue(int[][] grille, int colonne, int color){
        boolean valide =false;
        for (int i= grille.length-1; i>=0;--i){
            if (grille[i][colonne] == 0){
                grille[i][colonne] = color;
                valide = true;
                break;
            }
        }
        return valide;
    }

    static boolean jeuxPlein(int[][] grille){
        boolean full = true;
        for (int i=0; i<grille.length; ++i){
            for (int j = 0; j<grille[i].length; ++j){
                if (grille[i][j] == 0) {
                    full = false;
                    break;
                }
            }
        }
        return full;
    }
    static int changePlayer(int color){
        if(color == rouge){
            color = jaune;
        }else {
            color = rouge;
        }
        return color;
    }
    static void demande(int color){
        if (color == jaune){
            System.out.println("Joueur X : enter the column number between 1 and 7");
        }else {
            System.out.println("Joueur O : enter the column number between 1 and 7");
        }
    }

    static int lectureColonne(){
        int col;
        do {
            col = key.nextInt();
        }while (col<1 || col>7);
        return --col;
    }

    static void demandeEtJoue(int[][] grille, int color){
        do {
            demande(color);
            int col = lectureColonne();
            boolean valid = joue(grille,col,color);
            if(!valid){
                System.out.println("impossible to game at this position");
            }
            afficheGrille(grille);
            color = changePlayer(color);
        }while (!jeuxPlein(grille) && !estGagne(grille,color));
    }

    static boolean estGagne(int[][] grille, int color){

        for (int i=0; i<grille.length; ++i){
            for (int j = 0; j<grille[i].length; ++j){
                int colorcase = grille[i][j];
                if (colorcase == color){
                    final int ligneMax = grille.length -4;
                    final int colonneMax = grille[i].length -4;
                    if (
                           // diagonale vers le haut et la droite
                           (i>= 3 && j <= colonneMax &&
                           compte(grille,i,j,-1,+1)>=4) ||
                           // horizontale vers la droite
                           (j<=colonneMax && compte(grille,i,j,0,+1)>=4) ||
                           // en diagonale vers le bas et la droite
                           (i<= ligneMax && j<=colonneMax && compte(grille,i,j,+1,+1)>=4) ||
                           // verticale vers le bas
                           (i<= ligneMax -4 && compte(grille,i,j,+1,0)>=4)
                   ){ return true;}
                }
            }
        }
        return false;
    }

    static int compte(int[][] grille, int ligneDepart, int colonneDepart, int dirLigne, int dirColonne){
        int cpt =0;
        int ligne = ligneDepart;
        int colonne = colonneDepart;
        while (grille[ligne][colonne] == grille[ligneDepart][colonneDepart] && ligne>=0 && ligne<grille.length
                && colonne>=0 && colonne<grille[ligne].length){
            ++cpt;
            ligne = ligne + dirLigne;
            colonne = colonne + dirColonne;
        }
        return cpt;
    }

    public static void main(String[] args) {
        int[][] grille = new int[6][7];

        grille = initGrille(grille);

        afficheGrille(grille);

        int couleurJoueur = jaune;

        demandeEtJoue(grille, couleurJoueur);

        boolean gagne = estGagne(grille,couleurJoueur);
        if (gagne){
            if (couleurJoueur == rouge){
                System.out.println(">>>>>Winner: O");
            }else {
                System.out.println(">>>>>Winner: X");
            }
        }else{
            System.out.println(">>>>>GAME OVER<<<<<<<");
        }
    }
}
