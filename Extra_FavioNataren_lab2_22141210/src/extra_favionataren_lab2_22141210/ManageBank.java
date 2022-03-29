/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra_favionataren_lab2_22141210;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 *
 * @author berli
 */
public class ManageBank {

    File Archivo;
    File Carpeta;
    RandomAccessFile Cfile;

    public ManageBank() {
        Carpeta = new File("BancoRegistros");

        Carpeta.mkdirs();
        Archivo = new File("Banco//Cuentas.bnk?");

        try {
            Cfile = new RandomAccessFile("BancoRegistros//Cuentas.bnc", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean addCuenta(int cod, String nombre, TipoCuenta tipo) throws AccountAlreadyExist, IOException {

        if (Buscar(cod) == 1) {
            throw new AccountAlreadyExist(cod);

        } else {
            try {

                Date fecha = new Date();
                Cfile.seek(Cfile.length());

                Cfile.writeInt(cod);
                Cfile.writeUTF(nombre);
                Cfile.writeLong(fecha.getTime());
                Cfile.writeDouble(tipo.getTasaInterez());
                Cfile.writeDouble(tipo.getMiniSaldo());
                Cfile.writeInt(tipo.ordinal());

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        }

    }

    public long Buscar(int EnteredCode) {
        try {
            Cfile.seek(0);

            while (Cfile.getFilePointer() < Cfile.length()) {

                int code = Cfile.readInt();
                long pos = Cfile.getFilePointer();
                Cfile.readUTF();

                Cfile.skipBytes(28);
                if (EnteredCode == code) {
                    Cfile.seek(pos);
                    return 1;

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;

    }

    public void deposito(int cod, double monto) {
        if (Buscar(cod) == 1) {

            try {
                Cfile.readUTF();
                Cfile.skipBytes(16);
                long pos = Cfile.getFilePointer();
                double MontoActual = Cfile.readDouble();
                Cfile.seek(pos);

                Cfile.writeDouble(MontoActual + monto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void retiro(int cod, double monto) {
        double MontoActual;
        long pos;

        if (Buscar(cod) == 1) {

            try {
                Cfile.readUTF();
                Cfile.skipBytes(16);
                pos = Cfile.getFilePointer();
                MontoActual = Cfile.readDouble();

                if (MontoActual > monto) {
                    Cfile.seek(pos);
                    Cfile.writeDouble(MontoActual - monto);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void registrarIntereses() {
        long pos;
        TipoCuenta tipo;
        double tasa;
        double saldo;
        try {
            Cfile.seek(0);

            while (Cfile.getFilePointer() < Cfile.length()) {
                Cfile.readInt();
                Cfile.readUTF();

                Cfile.skipBytes(8);
                pos = Cfile.getFilePointer();

                tasa = Cfile.readDouble();
                saldo = Cfile.readDouble();
                System.out.println(tasa + "  " + saldo);
                tipo = TipoCuenta.values()[Cfile.readInt()];

                Cfile.seek(pos);

                Cfile.writeDouble(saldo * tipo.getTasaInterez());
                Cfile.skipBytes(12);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Import(String FileName) {
        File Archivo = new File(FileName);
        String line = "";
        try {

            Archivo.createNewFile();

            Cfile.seek(0);

            FileWriter entry = new FileWriter(Archivo);

            BufferedWriter writer = new BufferedWriter(entry);

            while (Cfile.getFilePointer() < Cfile.length()) {

                int code = Cfile.readInt();

                String nombre = Cfile.readUTF();
                Cfile.skipBytes(8);

                double tasa = Cfile.readDouble();
                double saldo = Cfile.readDouble();
                String tipo = TipoCuenta.values()[Cfile.readInt()].toString();

                line = code + "--" + nombre + "--Lps." + saldo + "--" + "Tipo:" + tipo + "\n";

                writer.write(line);

                writer.flush();

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
