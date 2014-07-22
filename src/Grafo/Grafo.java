/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

import java.util.LinkedList;

/**
 *
 * @author CRISTIAN
 */
public class Grafo {

    /*
     *Matriz de tipo Arista que contiene las aristas que conectan los nodos
     *Se carga de la base de datos en el método cargar()
     */
    private Arista[][] matrizAD;
    /*
     *Lista de nodos que contiene la ciudad, dichos nodos pueden albergar 
     *centros de distribución y dentros de abastecimiento
     */
    private Nodo[] listNodos;
    /*
     *Matriz de nodos
     */
    Nodo[][] matrizNodos;
    int tamano;
    int[][] ruta;
    int rutaCamion[][];
//    int[][] matrAdy;
    double[][] mCostos;
    double [][] mCostosCamion;

    public Grafo(int tamano) {
        this.tamano = tamano;
        this.cargar();
    }

    /**
     * @return the matrizAD
     */
    public Arista[][] getMatrizAD() {
        return matrizAD;
    }

    /**
     * @param matrizAD the matrizAD to set
     */
    public void setMatrizAD(Arista[][] matrizAD) {
        this.matrizAD = matrizAD;
    }

    /**
     * @return the listNodos
     */
    public Nodo[] getListNodos() {
        return listNodos;
    }

    /**
     * @param listNodos the listNodos to set
     */
    public void setListNodos(Nodo[] listNodos) {
        this.listNodos = listNodos;
    }

    private void cargar() {
        Conexion.Persistencia con = new Conexion.Persistencia();
//        this.matrAdy = new int[this.tamano][this.tamano];
        this.matrizAD = con.getMatrArista(this.tamano);
        this.listNodos = con.getListNodos();
        this.mCostos = new double[this.tamano][this.tamano];
        this.mCostosCamion = new double[this.tamano][this.tamano];
        this.ruta = new int[this.tamano][this.tamano];
        this.rutaCamion = new int[this.tamano][this.tamano];
        this.cargaPosAristas();
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (i == j) {
                    this.ruta[i][j] = 0;
                    this.mCostos[i][j] = 0;
                } else {
                    if (this.matrizAD[i][j] != null) {
                        this.mCostos[i][j] = this.matrizAD[i][j].getPeso();
                        this.ruta[i][j] = j;
//                        System.out.println("i: " + i + ", j: " + j + ", peso: " + this.matrizAD[i][j].getPeso());
                    } else {
                        this.mCostos[i][j] = 1000000;
                        this.ruta[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (i == j) {
                    this.rutaCamion[i][j] = 0;
                    this.mCostosCamion[i][j] = 0;
                } else {
                    if (this.matrizAD[i][j] != null && this.matrizAD[i][j].isTipo()) {
                        this.mCostosCamion[i][j] = this.matrizAD[i][j].getPeso();
                        this.rutaCamion[i][j] = j;
//                        System.out.println("i: " + i + ", j: " + j + ", peso: " + this.matrizAD[i][j].getPeso());
                    } else {
                        this.mCostosCamion[i][j] = 1000000;
                        this.rutaCamion[i][j] = -1;
                    }
                }
            }
        }
        this.floydwarshall();
        this.floydwarshallCamion();
    }

    public Nodo[] getNodosRuta(int origen, int destino) {

        return null;
    }

    public void getIntRuta(int origen, int destino, LinkedList<Integer> ruta) {
        if (origen == destino) {
            return;
        }
        ruta.add(this.ruta[origen][destino]);
        getIntRuta(this.ruta[origen][destino], destino, ruta);
    }

    public double[][] floydwarshall() {
        int n = this.tamano;
        double[][] cMA = new double[this.tamano][this.tamano];
        copiarMA(cMA, mCostos);//realizamos una copia de la matriz de adyacencia
        for (int k = 0; k < this.tamano; k++) {
            for (int i = 0; i < this.tamano; i++) {
                for (int j = 0; j < this.tamano; j++) {
                    if (cMA[i][k] + cMA[k][j] < cMA[i][j]) {
                        cMA[i][j] = cMA[i][k] + cMA[k][j];
                        ruta[i][j] = ruta[i][k];
                    }
                }
            }
        }
        return cMA;
    }
    
    public void getIntRutaCamion(int origen, int destino, LinkedList<Integer> ruta) {
        if (origen == destino) {
            return;
        }
        ruta.add(this.rutaCamion[origen][destino]);
        getIntRutaCamion(this.rutaCamion[origen][destino], destino, ruta);
    }
    
    public double[][] floydwarshallCamion() {
        int n = this.tamano;
        double[][] cMA = new double[this.tamano][this.tamano];
        copiarMA(cMA, mCostosCamion);//realizamos una copia de la matriz de adyacencia
        for (int k = 0; k < this.tamano; k++) {
            for (int i = 0; i < this.tamano; i++) {
                for (int j = 0; j < this.tamano; j++) {
//                    double calc = cMA[i][k] + cMA[k][j];
                    if (cMA[i][k] + cMA[k][j] < cMA[i][j]) {
                        cMA[i][j] = cMA[i][k] + cMA[k][j];
                        rutaCamion[i][j] = rutaCamion[i][k];
                    }
                }
            }
        }
        return cMA;
    }

    public void copiarMA(double[][] a, double[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
    }

//    private void dijkstra(int vert) {
//        boolean[] visto = new boolean[this.tamano];
//        int[][] disj = new int[2][this.tamano];
//        for (int i = 0; i < this.tamano; i++) {
//            if (this.matrAdy[vert][i] == 0) {
//                disj[0][i] = Integer.MAX_VALUE;
//            } else {
//                disj[0][i] = this.matrAdy[vert][i];
//                disj[1][i] = vert;
//            }
//        }
//        disj[0][vert] = 0;
//        visto[vert] = true;
//        while (!this.visitados(visto)) {
//            vert = this.minimo(visto, disj);
//            System.out.println("" + vert);
//            visto[vert] = true;
//            for (int i = 0; i < this.tamano; i++) {
//                if (!visto[i] && this.matrAdy[vert][i] != 0) {
//                    if (disj[0][i] > (disj[0][vert] + this.matrAdy[vert][i])) {
//                        disj[0][i] = disj[0][vert] + this.matrAdy[vert][i];
//                        disj[1][i] = vert;
//                    }
//                }
//            }
//        }
//    }
    private boolean visitados(boolean[] visto) {
        for (int i = 0; i < visto.length; i++) {
            if (visto[i] == false) {
                System.out.println("falso visitados");
                return false;
            }
        }
        System.out.println("verdadero2");
        return true;
    }

    private int minimo(boolean[] visto, int[][] disj) {
        int min = Integer.MAX_VALUE;
        int pos = 0;
        for (int i = 0; i < this.tamano; i++) {
            if (min > disj[0][i] && !visto[i]) {
                min = disj[0][i];
                pos = i;
            }
        }
        return pos;
    }

    private void llenaMatrizNodos() {
        this.matrizNodos = new Nodo[10][11];
        int index = 9;
        this.matrizNodos[0][1] = this.listNodos[1];
        this.matrizNodos[0][2] = this.listNodos[2];
        this.matrizNodos[0][3] = this.listNodos[3];
        this.matrizNodos[0][4] = this.listNodos[4];
        this.matrizNodos[0][5] = this.listNodos[5];
        this.matrizNodos[0][6] = this.listNodos[6];
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                this.matrizNodos[i][j] = this.listNodos[index];
                index++;
            }
        }
        System.out.println("llenaMatrizNodos(): " + index);
    }

    public void cargaPosAristas() {
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (matrizAD[i][j] != null) {
                    switch (this.getDireccion(this.listNodos[i].getX(), this.listNodos[i].getY(),
                            this.listNodos[j].getX(), this.listNodos[j].getY())) {
                        case 1: {
                            matrizAD[i][j].setPosXO(this.listNodos[i].getX());
                            matrizAD[i][j].setPosYO(this.listNodos[i].getY() - 10);
                            matrizAD[i][j].setPosXD(this.listNodos[j].getX());
                            matrizAD[i][j].setPosYD(this.listNodos[j].getY() + 32);
                        }
                        break;
                        case 2: {
                            matrizAD[i][j].setPosXO(this.listNodos[i].getX() + 32);
                            matrizAD[i][j].setPosYO(this.listNodos[i].getY());
                            matrizAD[i][j].setPosXD(this.listNodos[j].getX() - 10);
                            matrizAD[i][j].setPosYD(this.listNodos[j].getY());
                        }
                        break;
                        case 3: {
                            matrizAD[i][j].setPosXO(this.listNodos[i].getX());
                            matrizAD[i][j].setPosYO(this.listNodos[i].getY() + 37);
                            matrizAD[i][j].setPosXD(this.listNodos[j].getX());
                            matrizAD[i][j].setPosYD(this.listNodos[j].getY() - 15);

                        }
                        break;
                        case 4: {
                            matrizAD[i][j].setPosXO(this.listNodos[i].getX() - 7);
                            matrizAD[i][j].setPosYO(this.listNodos[i].getY());
                            matrizAD[i][j].setPosXD(this.listNodos[j].getX() + 32);
                            matrizAD[i][j].setPosYD(this.listNodos[j].getY());

                        }
                        break;
                    }
                    matrizAD[i][j].crearArea();
                }
            }
        }
    }

    private int valABS(int x, int y) {
        if (x > y) {
            return x - y;
        } else {
            return y - x;
        }
    }

    public void cargaPosUnaArista(int i, int j) {
        if (matrizAD[i][j] != null) {
            switch (this.getDireccion(this.listNodos[i].getX(), this.listNodos[i].getY(),
                    this.listNodos[j].getX(), this.listNodos[j].getY())) {
                case 1: {
                    matrizAD[i][j].setPosXO(this.listNodos[i].getX());
                    matrizAD[i][j].setPosYO(this.listNodos[i].getY() - 10);
                    matrizAD[i][j].setPosXD(this.listNodos[j].getX());
                    matrizAD[i][j].setPosYD(this.listNodos[j].getY() + 32);
                }
                break;
                case 2: {
                    matrizAD[i][j].setPosXO(this.listNodos[i].getX() + 32);
                    matrizAD[i][j].setPosYO(this.listNodos[i].getY());
                    matrizAD[i][j].setPosXD(this.listNodos[j].getX() - 10);
                    matrizAD[i][j].setPosYD(this.listNodos[j].getY());
                }
                break;
                case 3: {
                    matrizAD[i][j].setPosXO(this.listNodos[i].getX());
                    matrizAD[i][j].setPosYO(this.listNodos[i].getY() + 37);
                    matrizAD[i][j].setPosXD(this.listNodos[j].getX());
                    matrizAD[i][j].setPosYD(this.listNodos[j].getY() - 15);

                }
                break;
                case 4: {
                    matrizAD[i][j].setPosXO(this.listNodos[i].getX() - 7);
                    matrizAD[i][j].setPosYO(this.listNodos[i].getY());
                    matrizAD[i][j].setPosXD(this.listNodos[j].getX() + 32);
                    matrizAD[i][j].setPosYD(this.listNodos[j].getY());

                }
                break;
            }
            matrizAD[i][j].crearArea();
        }
    }

    /*
     Calcula la dirección en que debe ir el carro
     */
    private int getDireccion(int xo, int yo, int xd, int yd) {
        int res = 0;
        if (valABS(xd, xo) < 32) {
            if (yd > yo) {
                res = 3;
            } else {
                res = 1;
            }
        } else {
            if (xd > xo) {
                res = 2;
            } else {
                res = 4;
            }
        }
//        System.out.println("Res: " + res);
        return res;
    }

    public void eventoAristaCamion() {
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (i == j) {
                    this.ruta[i][j] = 0;
                    this.mCostos[i][j] = 0;
                } else {
                    if (this.matrizAD[i][j] != null && !this.matrizAD[i][j].isObstruida()) {
                        this.mCostos[i][j] = this.matrizAD[i][j].getPeso();
                        this.ruta[i][j] = j;
//                        System.out.println("i: " + i + ", j: " + j + ", peso: " + this.matrizAD[i][j].getPeso());
                    } else {
                        this.mCostos[i][j] = 1000000;
                        this.ruta[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (i == j) {
                    this.rutaCamion[i][j] = 0;
                    this.mCostosCamion[i][j] = 0;
                } else {
                    if (this.matrizAD[i][j] != null && !this.matrizAD[i][j].isObstruida()
                            && this.matrizAD[i][j].isTipo()) {
                        this.mCostosCamion[i][j] = this.matrizAD[i][j].getPeso();
                        this.rutaCamion[i][j] = j;
//                        System.out.println("i: " + i + ", j: " + j + ", peso: " + this.matrizAD[i][j].getPeso());
                    } else {
                        this.mCostosCamion[i][j] = 1000000;
                        this.rutaCamion[i][j] = -1;
                    }
                }
            }
        }
        this.floydwarshall();
        this.floydwarshallCamion();
    }
    
    public void eventoArista() {
        for (int i = 0; i < this.tamano; i++) {
            for (int j = 0; j < this.tamano; j++) {
                if (i == j) {
                    this.ruta[i][j] = 0;
                    this.mCostos[i][j] = 0;
                } else {
                    if (this.matrizAD[i][j] != null && !this.matrizAD[i][j].isObstruida()) {
                        this.mCostos[i][j] = this.matrizAD[i][j].getPeso();
                        this.ruta[i][j] = j;
//                        System.out.println("i: " + i + ", j: " + j + ", peso: " + this.matrizAD[i][j].getPeso());
                    } else {
                        this.mCostos[i][j] = 1000000;
                        this.ruta[i][j] = -1;
                    }
                }
            }
        }
        this.floydwarshall();
    }
}
