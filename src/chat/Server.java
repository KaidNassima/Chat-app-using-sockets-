package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final ServerSocket serverSocket ;
		final Socket clientSocket ;
		final BufferedReader in ;
		final PrintWriter out;
		final Scanner sc = new Scanner(System.in);
		
		try {
			serverSocket=new ServerSocket(1000);
			clientSocket=serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream());
			in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		
		//Sender Thread 
		//contient le code que le serveur utilise pour envoyer des messages au client 
		Thread sender = new Thread(new Runnable() {
			String message ;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					message= sc.nextLine();
					out.println(message);
					out.flush();
			}
		}
		});
		sender.start();
		//receiver Thread 
		Thread reciever = new Thread(new Runnable() {
			String message;
			@Override
			public void run() {
				try {
					message=in.readLine();
					while(message!=null) {
						System.out.println("Sensei: "+message);
						message=in.readLine();
						}
					System.out.println("sensei deconnecté");
					out.close();
					clientSocket.close();
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		reciever.start();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
}

}
