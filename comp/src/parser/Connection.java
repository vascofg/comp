package parser;

import java.io.Serializable;

public class Connection implements Serializable {
	private State destination;
	private char transitionChar;
	
	public Connection(State destination, char transitionChar)
	{
		this.destination=destination;
		this.transitionChar=transitionChar;
	}
	
	public State getDestination()
	{
		return this.destination;
	}
	
	public char getTransitionChar()
	{
		return this.transitionChar;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.getID()); //Compares with State ID only to avoid cyclic comparisons
		result = prime * result + transitionChar;
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
		Connection other = (Connection) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (destination.getID()!=(other.destination.getID())) //Compares with State ID only to avoid cyclic comparisons
			return false;
		if (transitionChar != other.transitionChar)
			return false;
		return true;
	}
}
