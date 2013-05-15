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
}