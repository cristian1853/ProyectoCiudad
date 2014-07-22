/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Implementacion;

/**
 *
 * @author PC
 */
public class Vehiculo {
    private int capacidad;
    private int placa;//id que va desde el 0 hasta el 19
    private boolean tipo;//true si es camión, false si es furgón
    private int idPedido;
    private boolean ocupado;

    public Vehiculo(int capacidad, int placa, boolean tipo) {
        this.capacidad = capacidad;
        this.placa = placa;
        this.tipo = tipo;
        this.idPedido=0;
        this.ocupado = false;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getPlaca() {
        return placa;
    }

    public void setPlaca(int placa) {
        this.placa = placa;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    
}
