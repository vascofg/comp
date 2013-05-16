package parser;
import java.util.ArrayList;


public class State {
	private ArrayList<Connection> connections;
	private int id;
	private Boolean finalState;
	private Boolean flag; //used for generating needed connections when multiplicity is *
	
	public State(int id)
	{
		this.id=id;
		this.connections=new ArrayList<>();
		this.finalState=false;
		this.flag=false;
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
	
	public Boolean getFlag()
	{
		return this.flag;
	}
	
	public void setFlag(Boolean flag)
	{
		this.flag=flag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connections == null) ? 0 : connections.hashCode());
		result = prime * result
				+ ((finalState == null) ? 0 : finalState.hashCode());
		result = prime * result + id;
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
		State other = (State) obj;
		if (connections == null) {
			if (other.connections != null)
				return false;
		} else if (!connections.equals(other.connections))
			return false;
		if (finalState == null) {
			if (other.finalState != null)
				return false;
		} else if (!finalState.equals(other.finalState))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
