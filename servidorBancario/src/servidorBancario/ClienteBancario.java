package servidorBancario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteBancario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		String ipServidor = "localhost";
		int puerto = 5557;
		
		try {
			Socket cliente = new Socket(ipServidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			PrintStream salida = new PrintStream(cliente.getOutputStream(), true);
			
			String linea_recibida, line;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
			System.out.println("Conexión aceptada: [address="+ipServidor+" localport="+puerto+"]");
			line = in.readLine();
			while (!line.equals("f") && line != null) {
				salida.println(line);
				salida.flush();
				linea_recibida = entrada.readLine();
				System.out.println("Orden: " + linea_recibida);
			    line = in.readLine();
			}
			salida.println(line);

			entrada.close();
			cliente.close();
		
		} catch (UnknownHostException excepcion) {
		System.err.println("No se puede encontrar el servidor, en la dirección" + ipServidor);
		
		} catch (Exception e) {
		System.err.println("Error: " + e);
		
		}
		
		
	}

}
