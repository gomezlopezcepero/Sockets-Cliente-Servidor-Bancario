package servidorBancario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServidorBancario {

	

	public static void main(String args[]) {
	
		// donde almacenaremos el mensaje recibido
		String linea_recibida;
		// variables numericas para los calculos de las operaciones
		double saldo = 1000;
		double ingreso = 0;
		double reintegro = 0;
		int cont=0;
		
	
		//iniciamos el servidor en el puerto 5557 y en localhost
		try {
			// Socket que iniciará nuestro servidor
			ServerSocket conexion = new ServerSocket(5557);
			// esperamos una conexion del cliente
			Socket socket_conectado = conexion.accept();
			//conectamos la entrada y la salida con el cliente
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_conectado.getInputStream()));
			PrintStream salida = new PrintStream(socket_conectado.getOutputStream());
	
			boolean salir = false;
			while (!salir) {
				//recibimos una linea string
				linea_recibida = entrada.readLine();
				//realizamos la separacion de las partes(operando y numeros a operar)
				
				
			
					if(linea_recibida.length()==1) {
		
						switch (linea_recibida) {
						case "s":
							
							salida.println("Saldo actual "+saldo);
							break;
							
						case "f":
							
							salida.println("Se ha salido correctamente de la aplicación");
							salir = true;
							break;
						
						default:
							cont++;
							break;

						}		
					}
					else if(linea_recibida.substring(1,2).equals(" ")){

						String linea[] = linea_recibida.split(" ");
						
						Pattern p = Pattern.compile("[0-9]{1,9}");
						Matcher m = p.matcher(linea[1]);
				        boolean b = m.matches();			
						
						if(b) {
						
				        	switch (linea[0]) {
							case "i":
								ingreso=0;
								ingreso=Integer.parseInt(linea[1]);
								saldo=saldo+ingreso;
								salida.println("Ingreso "+linea[1]+" Saldo actual "+saldo);
								break;
								
							case "r":
								reintegro=0;
								reintegro=Integer.parseInt(linea[1]);
								saldo=saldo-reintegro;
								salida.println("Ingreso "+linea[1]+" Saldo actual "+saldo);
								break;
								
							default:
								cont++;
								break;
				        	}
				        	
						}
						else {
							cont++;
						}
				}
				else {
					cont++;
				}	
	
			if(cont>0) {
				salida.println("tienes que introducir un comando válido");
				cont=0;
			}
				
		}
		//cerramos los buffer de conexion con el cliente
		salida.close();
		entrada.close();
		// cerramos el socket para cerrar el servidor
		socket_conectado.close();
		} catch (IOException excepcion) {
		System.out.println(excepcion);
		}
		
	}
	
}
