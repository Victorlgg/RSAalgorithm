/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
    
/**
 *
 * @author Guia de Del Alex Narvaez Programming
 */
public class RSA {
    
    static private BigInteger phi_n, d, e, n, p, q;

    BigInteger encriptar(String mensaje) throws IOException {
        byte[] bytes = mensaje.getBytes();
        String valores = "";
        for (byte value : bytes) {
            valores += value;
        }
        BigInteger message = new BigInteger(valores);
        return message.modPow(this.e, this.n);
    }

    void desencriptar(BigInteger cifrado) {
        BigInteger a = cifrado.modPow(this.d, this.n);//return encrypted.modPow(privateKey, modulus);
        String mensajenuevo="";
        String mensaje = a.toString();
        for (int i = 0; i < mensaje.length(); i += 2) {
            char c = (char) Integer.parseInt((mensaje.substring(i, i + 2)));
            mensajenuevo+=c;
            if (c == 0) {
                break;
            }
//           System.out.print(c);
                
        }
        JOptionPane.showMessageDialog(null, mensajenuevo);
    }
    
//n=p*q , phi_n=(p-1)(q-1) , d=inv(e) mod n
    public static void main(String[] argsa) throws IOException {
        
        e = new BigInteger("65537");//65537 //publickey
        Scanner sc = new Scanner(System.in);
        p = new BigInteger("30762542250301270692051460539586166927291732754961");
        BigInteger q = new BigInteger("29927402397991286489627837734179186385188296382227");
//    BigInteger cifrado = sc.nextBigInteger();//Si se quiere recibir por consola
        n = new BigInteger(p.multiply(q).toString());//n=p*q , modulus=p*q
        phi_n = p.subtract(BigInteger.valueOf(1));//phi_n -> O
        phi_n = phi_n.multiply(q.subtract(BigInteger.valueOf(1)));

        d = e.modInverse(phi_n); //private key
        String opciones[] = {"Encriptar","Desencriptar","Salir"};
        while(true){
        int opcion=JOptionPane.showOptionDialog(null, "Que desea realizar", "Encriptar/desencriptar mensaje",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        RSA RS = new RSA();
        switch(opcion){
            case 0://Encriptar
//                String mensaje = RS.escogerArchivo();
                String mensaje = JOptionPane.showInputDialog("Ingrese el mensaje");
                //        String mensaje = RS.leerTxt("./src/ArchivosC3/texto.txt");
                BigInteger cifrado = RS.encriptar(mensaje);
                FileWriter rutaNueva = null;
                rutaNueva = new FileWriter("./src/ArchivosC3/encriptado.txt");
                rutaNueva.append(cifrado.toString());
                rutaNueva.close();
            break;
            case 1://Desencriptar
//                RSA RS = new RSA();
            String textocifrado = RS.escogerArchivo();
//            System.out.println(textocifrado);
            BigInteger texto = new BigInteger(textocifrado);
            RS.desencriptar(texto);
            break;
            case 2:
                System.exit(0);
            break;
        
        
        }
        }
        
        
//        BigInteger cifrado1 = new BigInteger(RS.leerTxt("./src/ArchivosC3/MensajeENC1.txt"));
//        BigInteger cifrado2 = new BigInteger(RS.leerTxt("./src/ArchivosC3/MensajeENC2.txt"));
//        RS.desencriptar(cifrado);
//        System.out.println();
//        RS.desencriptar(cifrado1);
//        System.out.println();
//        RS.desencriptar(cifrado2);

    }

    public String leerTxt(String dirArchivo) throws FileNotFoundException, IOException {
        FileReader lector = new FileReader(dirArchivo);
        BufferedReader datos = new BufferedReader(lector);
        String cadena = datos.readLine();
        datos.close();
        lector.close();
        return cadena;
    }

    
    public String escogerArchivo(){
    Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser("./src/ArchivosC3/");
        fileChooser.showOpenDialog(fileChooser);
        String respuesta="";
        try {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            File f = new File(ruta);
            entrada = new Scanner(f);
            
            while (entrada.hasNext()) {
                respuesta=entrada.nextLine();
            }
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return respuesta;
    }
    
}
/*
30762542250301270692051460539586166927291732754961
29927402397991286489627837734179186385188296382227
424236952206057066872700453503661773567827006571091351397488406910437574827532103275742945321419387
*/
