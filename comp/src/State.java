import java.util.ArrayList;


public class State {
	private ArrayList<Connection> connections;
	private int id;
	private Boolean finalState;
	
	public State(int id)
	{
		this.id=id;
		this.connections=new ArrayList<>();
		this.finalState=false;
	}
	
	public void setFinalState(Boolean finalState)
	{
		this.finalState = finalState;
	}
	
	public Boolean isFinal()
	{
		return this.finalState;
	}
	
	public void addConnection(State dest, char transChar)
	{
		this.connections.add(new Connection(dest,transChar));
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public int getNumConnections()
	{
		return this.connections.size();
	}
	
	public Connection getConnection(int i)
	{
		return this.connections.get(i);
	}
}
