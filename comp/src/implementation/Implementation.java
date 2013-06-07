package implementation;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import parser.Automaton;
import parser.State;


public abstract class Implementation {
	static Automaton a;

	static void initialize(String filename) {
		try {
			a = Automaton.load(filename);
			System.out.println("loaded");
		} catch (IOException e) {
			System.out.println("Failed to load file. Generate again.");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			current_state = a.getState(0);
		}
	}

	static State current_state;

	static void transition_to_state(char transition) throws Exception {
		for (int i = 0; i < current_state.getNumConnections(); i++) {
			if (current_state.getConnection(i).getTransitionChar() == transition) {
				current_state = current_state.getConnection(i).getDestination();
				return;
			}
		}
		throw (new Exception());
	}

	static Boolean input_string(String exp) {
		try {
			for (int i = 0; i < exp.length(); i++) {
				transition_to_state(exp.charAt(i));
			}
		} catch (Exception e) {
			return false;
		}
		return current_state.isFinal();
	}

	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		System.out.print("File name: ");
		String filename = input.nextLine();
		initialize(filename);

		int opt;
		do {
			System.out.print("Enter the expression: ");
			String expression = input.nextLine();
			if (input_string(expression))
				System.out.println("Accepted");
			else
				System.out.println("Rejected");
			System.out
					.print("0 to exit, 1 to continue: ");
			try{
				opt = input.nextInt();
				input.nextLine();
			}
			catch (InputMismatchException e)
			{
				break;
			}
		} while (opt != 0);

		input.close();
	}
}
