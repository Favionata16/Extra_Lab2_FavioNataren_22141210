/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra_favionataren_lab2_22141210;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author berli
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static int EntradaInt(String msg) {
        int num = 0;
        try {

            System.out.print(msg);
            num = sc.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero entero!");
            sc.next();
            sc.useDelimiter("\\n");
            return EntradaInt(msg);

        }

        return num;

    }

    public static void main(String args[]) throws IOException {
        int opc = 0;

        ManageBank MB = new ManageBank();

        do {

            System.out.println("opciones:"
                    + "\n1.Agregar Cuenta"
                    + "\n2.Depositar"
                    + "\n3.Retirar"
                    + "\n4.Registrar Intereses"
                    + "\n5.Import"
                    + "\n6.Elija una opcion:");
            opc = sc.nextInt();

            switch (opc) {

                case 1: {
                    System.out.print("Nombre de la cuenta: ");
                    TipoCuenta tipo;
                    sc.useDelimiter("\\n");
                    String nombre = sc.next();

                    System.out.println("Codigo: ");
                    int code = sc.nextInt();

                    for (int i = 0; i < TipoCuenta.values().length; i++) {

                        System.out.println(i + "--" + TipoCuenta.values()[i].toString());

                    }
                    System.out.println("Ingrese un valor: ");
                    int pos = sc.nextInt();
                    try {
                        tipo = TipoCuenta.values()[pos];

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error numero fuera de la lista");
                        break;

                    }

                    try {
                        MB.addCuenta(code, nombre, tipo);
                    } catch (AccountAlreadyExist e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                }

                case 2: {
                    System.out.println("Depositar");

                    System.out.println("Codigo de cuenta a depositar: ");
                    int code = sc.nextInt();

                    if (MB.Buscar(code) == 1) {
                        System.out.print("Monto>>");
                        double monto = sc.nextDouble();

                        MB.deposito(code, monto);
                        System.out.println("Se ha depositado " + monto + "  en la cuenta!");

                    } else {

                        System.out.println("Esa cuenta no existe!");
                    }

                    break;

                }

                case 3: {
                    System.out.println("Retirar");

                    System.out.println("Codigo de cuenta para retirar: ");
                    int code = sc.nextInt();

                    if (MB.Buscar(code) == 1) {
                        System.out.print("Monto a retirar: ");
                        double monto = sc.nextDouble();

                        MB.retiro(code, monto);
                        System.out.println("Se ha retirado " + monto + "  de la cuenta!!!");

                    } else {

                        System.out.println("Cuenta no existente");
                    }
                    break;

                }
                case 4: {
                    System.out.println("Agregando intereses");

                    MB.registrarIntereses();

                    break;

                }
                case 5: {

                    System.out.println("Ingrese el nombre o direccion de donde desearia guardar el archivo "
                            + "\n tambien inlcluir nombre al final del archivo con us respectiva extension");
                    sc.useDelimiter("\\n");
                    System.out.print("Direccion y Nomrbre: ");
                    String path = sc.next();

                    MB.Import(path);

                    System.out.println("Guardando en: " + path);

                    break;
                }

                case 6:
                    System.out.println("saliendo");
                    break;

            }

        } while (opc != 6);

    }
}
