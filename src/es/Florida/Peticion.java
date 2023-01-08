package es.Florida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Peticion implements Runnable {
	static OutputStream os;
	static PrintWriter pw;
	static InputStream is;
	static InputStreamReader isr;
	static BufferedReader bf;
	static Socket socket;
	static String turn;
	static Integer file, column, indicevalue;
	static String value = "O";
	static int accountant;
	static String[][] board = { { "", "", "" }, { "", "", "" }, { "", "", "" } };

	public Peticion(Socket socket) {
		this.socket = socket;
	}

	public static void machine() throws ClassNotFoundException, IOException {

		boolean positionFound = false;
		
		while (positionFound == false ) {
			file = (int) (Math.random() * 3);
			column = (int) (Math.random() * 3);
			
			if (board[file][column].equals("")) {
				accountant ++;
				System.out.println("A VALID POSITION HAS BEEN FOUND!");
				System.err.println("SERVER >>> Make play --> Row: " + file + " // Column: " + column);
				board[file][column] = value.toUpperCase();
				senObject(file, column);
				positionFound = true;
			} else if(accountant == 4) {
				System.out.println("Game Over");
				positionFound = true;
			} else {
				System.out.println("A VALID POSITION HAS BEEN FOUND!");
			}
		}
		recivePositions();
	}
	
	public static void senObject(int file, int column) throws IOException {

		try {

			String fileStr = String.valueOf(file);
			String columStr = String.valueOf(column);
			setTurno("Cliente");

			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			pw.write(fileStr.toString() + "\n");
			pw.write(columStr.toString() + "\n");
			pw.write(value.toString() + "\n");
			pw.write(getTurno().toString() + "\n");
			pw.flush();

			System.err.println("SERVER>>> Positions sent to the player: Row -> " + file + " // Column -> "
					+ column + " // value -> " + value + " // Turn -> " + getTurno());

			if (board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")) {
				winner("machineWins");
				System.exit(0);
				Cliente.socket.close();
			} else if (board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O")) {
				winner("machineWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X")) {
				winner("playerWins");
				Cliente.socket.close();
				System.exit(0);
			} else if (board[0][0] != "" && board[0][1] != "" && board[0][2] != "" && board[1][0] != ""
					&& board[1][1] != "" && board[0][2] != "" && board[2][0] != "" && board[2][1] != ""
					&& board[2][2] != "") {
				JOptionPane.showMessageDialog(new JFrame(), "All squares are occupied", "Game over",
						JOptionPane.INFORMATION_MESSAGE);
				Cliente.socket.close();
				System.exit(0);
			
		}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	public static void winner(String winner) throws IOException {

		boolean gameOver = false;

		if (winner.equals("matrizCompleta")) {
			JOptionPane.showMessageDialog(new JFrame(), "All squares are occupied", "Game over",
					JOptionPane.INFORMATION_MESSAGE);
			gameOver = true;
		} else if (winner.equals("maquinaGana")) {
			JOptionPane.showMessageDialog(new JFrame(), "Machine Wins", "Game over",
					JOptionPane.INFORMATION_MESSAGE);
			gameOver = true;
		} else if (winner.equals("jugadorGana")) {
			JOptionPane.showMessageDialog(new JFrame(), "Player Wins", "Game Over",
					JOptionPane.INFORMATION_MESSAGE);
			gameOver = true;
		}

		if (gameOver) {
			Cliente.getBtn00().setEnabled(false);
			Cliente.getBtn01().setEnabled(false);
			Cliente.getBtn02().setEnabled(false);
			Cliente.getBtn10().setEnabled(false);
			Cliente.getBtn11().setEnabled(false);
			Cliente.getBtn12().setEnabled(false);
			Cliente.getBtn20().setEnabled(false);
			Cliente.getBtn21().setEnabled(false);
			Cliente.getBtn22().setEnabled(false);

		}

	}
	
	public static void recivePositions() throws ClassNotFoundException, IOException {

		is = socket.getInputStream();
		isr = new InputStreamReader(is);
		bf = new BufferedReader(isr);

		String file = bf.readLine();
		String column = bf.readLine();
		String value = bf.readLine();
		String turn = bf.readLine();
		setTurno(turn);

		System.err.println("SERVER>>> Positions sent to the player: Row -> " + file + " // Column -> " + column
				+ " // value -> " + value + " // Turn -> " + getTurno());

		int fileInt = Integer.parseInt(file);
		int columnInt = Integer.parseInt(column);

		board[fileInt][columnInt] = value;

		machine();

		if (board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")) {
			winner("machineWins");
			System.exit(0);
			Cliente.socket.close();
		} else if (board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O")) {
			winner("machineWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X")) {
			winner("playerWins");
			Cliente.socket.close();
			System.exit(0);
		} else if (board[0][0] != "" && board[0][1] != "" && board[0][2] != "" && board[1][0] != ""
				&& board[1][1] != "" && board[0][2] != "" && board[2][0] != "" && board[2][1] != ""
				&& board[2][2] != "") {
			JOptionPane.showMessageDialog(new JFrame(), "All squares are occupied", "Game over",
					JOptionPane.INFORMATION_MESSAGE);
			Cliente.socket.close();
			System.exit(0);
		
	}
		
	}
	
	
	public void run() {

		try {
			Random r = new Random();
			int valueDado = r.nextInt(9) + 1;
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			pw.write(valueDado + "\n");
			pw.flush();

			//recibimos la answer del cliente ( 0 / 1 )
			
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			bf = new BufferedReader(isr);
			String answer = bf.readLine();
			System.err.println("SERVER >>> Receives response from Client: " + answer);
			
			//dependiendo de la answer del cliente se elige quien empieza la partida
			
			if(answer.equals("Client")) {
				setTurno("Client");
				recivePositions();

			} else {
				setTurno("Server");
				machine();

			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String getTurno() {
		return turn;
	}

	public static void setTurno(String turn) {
		Peticion.turn = turn;
	}
}