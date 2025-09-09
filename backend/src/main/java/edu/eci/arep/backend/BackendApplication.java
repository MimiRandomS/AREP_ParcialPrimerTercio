package edu.eci.arep.backend;


import java.net.*;
import java.io.*;

public class BackendApplication {
	static ServerSocket serverSocket;
	static Socket clientSocket;
	static PrintWriter out;
	static BufferedReader in;
	static boolean running = true;

	public static void main(String[] args) throws IOException {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(36000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}

		while (running){
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			handleRequests();
		}
	}

	private static void handleRequests() throws IOException, URISyntaxException {
        try {
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

		String inputLine, outputLine, httpRequest = null;
		boolean firstLine = true;
		URI uri = null;
		while ((inputLine = in.readLine()) != null) {
			if (firstLine){
				httpRequest = inputLine;
				uri = new URI(httpRequest.split(" ")[1]);
				firstLine = false;
			}
			System.out.println("Recibí: " + inputLine);
			if (!in.ready()) {break; }
		}

		System.out.println("httpRequest" + httpRequest);
		System.out.println("uri" + uri);

	}
}

