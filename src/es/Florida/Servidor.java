package es.Florida;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static ServerSocket server = null;
	public static  ServerSocket client = null;
	
	public static void main(String[] args) throws IOException {
		System.err.println("SERVER >>> Start the server, wait for request");
		try {
			server = new ServerSocket(1234);
		} catch (IOException e) {
			System.err.println("SERVER >>> Error");
			return;
		}
		while(true) {
			Socket client = server.accept();
			System.err.println("SERVER >>> Connection received --> Launch Request class thread");
			Peticion c = new Peticion(client);
			Thread hilo = new Thread(c);
			hilo.start();
			
		}
	}

}
