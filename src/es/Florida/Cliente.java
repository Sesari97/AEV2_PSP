package es.Florida;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Cliente extends JFrame {
	private JPanel contentPane;
	public static Socket socket;
	private static String turn;
	static InputStream is; 
	static InputStreamReader isr;
	static BufferedReader bf;

	public static String gameStart;
	
	static JButton btn00;
	static JButton btn01;
	static JButton btn02;
	static JButton btn10;
	static JButton btn11;
	static JButton btn12;
	static JButton btn20;
	static JButton btn21;
	static JButton btn22;
	

	static String[][] board = { { "", "", "" }, { "", "", "" }, { "", "", "" } };
	
	//Make the connection between the client and the server
	public static void Conection() throws UnknownHostException, IOException, ClassNotFoundException {
		String host = "localhost";
		int puerto = 1234;
		System.out.println("CLIENT >> Start client -> waiting for message from server... ");
		socket = new Socket(host, puerto);
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bfr = new BufferedReader(isr);
		String result = bfr.readLine();
		System.out.println("CLIENT >>> Receives result: " + result);
		int pickedNumber = Integer.parseInt(result);

		String[] options = { "EVEN", "ODD" };
		int userDecision = JOptionPane.showOptionDialog(null, "Choose one of the two options: ", "Customer",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		
		if (pickedNumber % 2 == 0 && userDecision == 0 || pickedNumber % 2 != 0 && userDecision == 1) {
			System.out.println("Start the client");
			JOptionPane.showMessageDialog(new JFrame(), "Player starts game", "GAME START",
					JOptionPane.INFORMATION_MESSAGE);
			setTurno("Client");
			gameStart = "Client";
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.write(gameStart + "\n");
			pw.flush();
			
			player();

		} else {
			System.out.println("Start the server");
			JOptionPane.showMessageDialog(new JFrame(), "Server starts game", "GAME START",
					JOptionPane.INFORMATION_MESSAGE);
			setTurno("Server");
			gameStart = "Server";
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.write(gameStart + "\n");
			pw.flush();
			
			reciveObject();
		}


	}
	
	//Choose a position and make the play
	public static void play(int file, int column, String value) throws ClassNotFoundException {

		if (board[file][column].equals("")) {
			board[file][column] = value;
			sendPositions(file, column, value);
		} 

	}
	
	//Send the position to the server for it to respond
	public static void sendPositions(int file, int column, String value){
		try {

			String filaStr = String.valueOf(file);
			String columStr = String.valueOf(column);
			String turn = "Server";
			setTurno(turn);

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.write(filaStr.toString() + "\n");
			pw.write(columStr.toString() + "\n");
			pw.write(value.toString() + "\n");
			pw.write(turn.toString() + "\n");
			pw.flush();

			System.out.println("CLIENT>>> Positions sent to the server: Row -> " + file + " // Column -> "
					+ column + " // Value -> " + value + " // Turn -> " + getTurno());
			
		
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

			
			
			reciveObject();
			
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	//Message saying who has won or if the board is full
	public static void winner(String winner) throws IOException {

		boolean finPartida = false;

		if (winner.equals("matrizCompleta")) {
			JOptionPane.showMessageDialog(new JFrame(), "All squares are occupied", "Game over",
					JOptionPane.INFORMATION_MESSAGE);
			finPartida = true;
		} else if (winner.equals("machineWins")) {
			JOptionPane.showMessageDialog(new JFrame(), "Machine Wins", "Game over",
					JOptionPane.INFORMATION_MESSAGE);
			finPartida = true;
		} else if (winner.equals("playerWins")) {
			JOptionPane.showMessageDialog(new JFrame(), "Player Wins", "Game Over",
					JOptionPane.INFORMATION_MESSAGE);
			finPartida = true;
		}

		if (finPartida) {
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
	
	//Receive the object from the server with the position
	public static void reciveObject() throws ClassNotFoundException, IOException {

		is = socket.getInputStream();
		isr = new InputStreamReader(is);
		bf = new BufferedReader(isr);

		String file = bf.readLine();
		String column = bf.readLine();
		String value = bf.readLine();
		String turn = bf.readLine();
		setTurno(turn);

		System.out.println("CLIENT>>> Positions received from the server: Row -> " + file + " // Column -> " + column
				+ " // value -> " + value + " // Turn -> " + getTurno());

		int filaInt = Integer.parseInt(file);
		int columnInt = Integer.parseInt(column);

		board[filaInt][columnInt] = value;

		String boton = file + column;

		machine(boton, value, filaInt, columnInt);

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
	
	public static void machine(String boton, String value, int file, int column) throws IOException {
		if (boton.equals("00"))
			getBtn00().setText(value);
		if (boton.equals("01"))
			getBtn01().setText(value);
		if (boton.equals("02"))
			getBtn02().setText(value);
		if (boton.equals("10"))
			getBtn10().setText(value);
		if (boton.equals("11"))
			getBtn11().setText(value);
		if (boton.equals("12"))
			getBtn12().setText(value);
		if (boton.equals("20"))
			getBtn20().setText(value);
		if (boton.equals("21"))
			getBtn21().setText(value);
		if (boton.equals("22"))
			getBtn22().setText(value);

		board[file][column] = value;

		player();
	}

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Conection();
	}

	/**
	 * Create the frame.
	 */
	public Cliente() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn00 = new JButton("");
		btn00.setBounds(43, 30, 100, 100);
		contentPane.add(btn00);
		
		btn01 = new JButton("");
		btn01.setBounds(165, 30, 100, 100);
		contentPane.add(btn01);
		
		btn02 = new JButton("");
		btn02.setBounds(291, 30, 100, 100);
		contentPane.add(btn02);
		
		btn10 = new JButton("");
		btn10.setBounds(43, 163, 100, 100);
		contentPane.add(btn10);
		
		btn11 = new JButton("");
		btn11.setBounds(165, 163, 100, 100);
		contentPane.add(btn11);
		
		btn12 = new JButton("");
		btn12.setBounds(291, 163, 100, 100);
		contentPane.add(btn12);
		
		btn20 = new JButton("");
		btn20.setBounds(43, 289, 100, 100);
		contentPane.add(btn20);
		
		btn21 = new JButton("");
		btn21.setBounds(165, 289, 100, 100);
		contentPane.add(btn21);
		
		btn22 = new JButton("");
		btn22.setBounds(291, 289, 100, 100);
		contentPane.add(btn22);

	}

	public static void player() throws IOException {

		String caracter = "X";
		
		ActionListener actionListenerButton_00 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn00().setText(caracter);
				try {
					play(0, 0, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn00().addActionListener(actionListenerButton_00);

		ActionListener actionListenerButton_01 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn01().setText(caracter);
				try {
					play(0, 1, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn01().addActionListener(actionListenerButton_01);

		ActionListener actionListenerButton_02 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn02().setText(caracter);
				try {
					play(0, 2, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn02().addActionListener(actionListenerButton_02);

		ActionListener actionListenerButton_10 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn10().setText(caracter);
				try {
					play(1, 0, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn10().addActionListener(actionListenerButton_10);

		ActionListener actionListenerButton_11 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn11().setText(caracter);
				try {
					play(1, 1, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn11().addActionListener(actionListenerButton_11);

		ActionListener actionListenerButton_12 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn12().setText(caracter);
				try {
					play(1, 2, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn12().addActionListener(actionListenerButton_12);

		ActionListener actionListenerButton_20 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn20().setText(caracter);
				try {
					play(2, 0, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn20().addActionListener(actionListenerButton_20);

		ActionListener actionListenerButton_21 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn21().setText(caracter);
				try {
					play(2, 1, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn21().addActionListener(actionListenerButton_21);

		ActionListener actionListenerButton_22 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBtn22().setText(caracter);
				try {
					play(2, 2, caracter);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		getBtn22().addActionListener(actionListenerButton_22);


	
	}
	
	public static JButton getBtn00() {
		return btn00;
	}


	public static JButton getBtn01() {
		return btn01;
	}


	public static JButton getBtn02() {
		return btn02;
	}

	public static JButton getBtn10() {
		return btn10;
	}

	public static JButton getBtn11() {
		return btn11;
	}

	public static JButton getBtn12() {
		return btn12;
	}


	public static JButton getBtn20() {
		return btn20;
	}

	public static JButton getBtn21() {
		return btn21;
	}

	public static JButton getBtn22() {
		return btn22;
	}


	public static String getTurno() {
		return turn;
	}

	public static void setTurno(String turn) {
		Cliente.turn = turn;
	}
}
