/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra_favionataren_lab2_22141210;

/**
 *
 * @author berli
 */
public enum TipoCuenta {

    NORMAL(0.02, 500), PlANILLA(0, 200), VIP(0.04, 100);

    double tasaInterez;
    double miniSaldo;

    TipoCuenta(double tasa, double miniSaldo) {

        tasaInterez = tasa;
        this.miniSaldo = miniSaldo;

    }

    public double getTasaInterez() {
        return tasaInterez;
    }

    public double getMiniSaldo() {
        return miniSaldo;
    }
}
