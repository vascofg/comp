package parser;
import java.util.ArrayList;


public class Automaton {
	private ArrayList<State> states;
	
	public Automaton()
	{
		states = new ArrayList<>();
	}
	
	public void addState(State s)
	{
		states.add(s.getID(), s);
	}
	
	public State getState(int id)
	{
		return states.get(id);
	}
	
	public int getNumStates()
	{
		return this.states.size();
	}
	
	public void dump()
	{
		for(int i=0;i<states.size();i++)
		{
			State currentState = states.get(i);
			System.out.println("S" + currentState.getID() + (currentState.isFinal() ? " final":"") + ":");
			for(int j=0;j<states.get(i).getNumConnections();j++)
			{ 
				Connection currentConnection = currentState.getConnection(j);
				System.out.println("  -> " + "S" + currentConnection.getDestination().getID()+ " with " + currentConnection.getTransitionChar());
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
}