
public class Connection {
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
}
