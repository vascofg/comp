package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Automaton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<State> states;

	public Automaton() {
		states = new ArrayList<>();
	}

	public void addState(State s) {
		states.add(s.getID(), s);
	}

	public State getState(int id) {
		return states.get(id);
	}

	public int getNumStates() {
		return this.states.size();
	}

	/**
	 * Writes the automata
	 */
	public void dump() {
		for (int i = 0; i < states.size(); i++) {
			State currentState = states.get(i);
			System.out.println("S" + currentState.getID()
					+ (currentState.isFinal() ? " final" : "") + ":");
			for (int j = 0; j < states.get(i).getNumConnections(); j++) {
				Connection currentConnection = currentState.getConnection(j);
				System.out.println("  -> " + "S"
						+ currentConnection.getDestination().getID() + " with "
						+ currentConnection.getTransitionChar());
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automaton other = (Automaton) obj;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		return true;
	}

	public void addBypassConnections(State dest, char transChar) { // adds the
																	// bypass
																	// connections
																	// for *
																	// operations
		// for each node with the flag go to the previous node and add a
		// connection to the destination
		int firstState = dest.getID() - 1; // start from the states before
											// destination one
		this.getState(firstState).setFinalState(true); // first state is also
														// final
		for (int i = firstState; i > 0; i--) {
			State currentState = this.getState(i);
			State previousState = this.getState(i - 1);
			if (!currentState.getFlag())
				return;
			previousState.setFinalState(true); // states before states with flag
												// are final
			previousState.addConnection(dest, transChar);
		}
	}

	public void clearPreviousFinalStates(State last) {
		int firstState = last.getID() - 1; // start from the states before
											// destination one
		for (int i = firstState; i >= 0; i--)
			this.getState(i).setFinalState(false);
	}

	public static void save(Scanner input, Automaton a) {
		try {
			System.out.print("File name: ");
			String filename = input.nextLine();
			input.close();
			File file = new File(filename);
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(a);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Automaton load(Scanner input) throws IOException, ClassNotFoundException {
		System.out.print("File name: ");
		String filename = input.nextLine();
		File file = new File(filename);
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		Automaton a;
		a = (Automaton) in.readObject();
		in.close();
		fileIn.close();
		return a;
	}

	public static void implement(Automaton a) {

	}
}