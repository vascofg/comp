package parser;
import parser.Node;
import parser.Regex2Auto;
import parser.Regex2AutoTreeConstants;

/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public class SimpleNode implements Node {

	protected Node parent;
	protected Node[] children;
	protected int id;
	protected Object value;
	protected Regex2Auto parser;
	protected int stateID;

	public SimpleNode(int i) {
		id = i;
		stateID = -1;
	}

	public SimpleNode(Regex2Auto p, int i) {
		this(i);
		parser = p;
	}

	public void jjtOpen() {
	}

	public void jjtClose() {
	}

	public void jjtSetParent(Node n) {
		parent = n;
	}

	public Node jjtGetParent() {
		return parent;
	}

	public void jjtAddChild(Node n, int i) {
		if (children == null) {
			children = new Node[i + 1];
		} else if (i >= children.length) {
			Node c[] = new Node[i + 1];
			System.arraycopy(children, 0, c, 0, children.length);
			children = c;
		}
		children[i] = n;
	}

	public Node jjtGetChild(int i) {
		return children[i];
	}

	public int jjtGetPositionInParent() {
		Node parent = jjtGetParent();
		for (int i = 0; i < parent.jjtGetNumChildren(); i++)
			if (parent.jjtGetChild(i) == this)
				return i;
		return -1;
	}

	public Node jjtGetPreviousSibling() {
		int pos = jjtGetPositionInParent();
		if (pos == -1)
			return null;
		else
			return parent.jjtGetChild(pos - 1);
	}
	
	public Node jjtGetNextSibling() {
		int pos = jjtGetPositionInParent();
		if (pos == -1)
			return null;
		else
		{
			try{
				return parent.jjtGetChild(pos + 1);
			}
			catch(ArrayIndexOutOfBoundsException | NullPointerException e)
			{
				return null;
			}
		}
	}
	
	public Node jjtGetFirstStateNode() { //Gets the first node inside a parenthesis which is a valid state
		try{
		if(this.getStateID()>-1)
			return this;
		else
			return ((SimpleNode)this.jjtGetChild(0)).jjtGetFirstStateNode();
		}
		catch(NullPointerException e){
			System.out.println("ESTOUROU");
			return null;
		}
	}
	
	public Node jjtGetNextStateNode() { //Gets the next node which is a valid state
		SimpleNode nextSibling = (SimpleNode) this.jjtGetNextSibling();
		SimpleNode nextNode;
		if(nextSibling!=null)
			nextNode=nextSibling;
		else try{
			nextNode = (SimpleNode) this.jjtGetChild(0);
		}
		catch(NullPointerException e)
		{
			return null;
		}
		if(nextNode.getStateID()>-1)
			return nextNode;
		else
			return nextNode.jjtGetNextStateNode();
	}

	public int jjtGetNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	public void jjtSetValue(Object value) {
		this.value = value;
	}

	public Object jjtGetValue() {
		return value;
	}

	public int getStateID() {
		return stateID;
	}
	
	public Boolean jjtIsNextNodeStar() {
		SimpleNode nextNode = ((SimpleNode)this.jjtGetNextSibling());
		return(nextNode!=null && Regex2AutoTreeConstants.jjtNodeName[nextNode.id]== "Multiplicity" && nextNode.jjtGetValue().toString().charAt(0) == '*');
	}
	
	public Node jjtGetNextNonStarStateNode() {
		SimpleNode nextNode;
		do{
			nextNode=((SimpleNode) jjtGetNextStateNode());
			if(!nextNode.jjtIsNextNodeStar())
				return nextNode;
		} while(nextNode != this);
		return null;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		String ret = Regex2AutoTreeConstants.jjtNodeName[id];
		if (this.jjtGetValue() != null)
			ret += " " + this.jjtGetValue().toString();
		return ret;
	}

	public String toString(String prefix) {
		return prefix + toString();
	}
	
	public void generateAutomaton(Automaton a) {
		int nextStateID = a.getNumStates();
		State sourceState, destinationState;
		char transitionChar;
		switch (Regex2AutoTreeConstants.jjtNodeName[id]) {
		case "Start":
			a.addState(new State(nextStateID)); //Start state
			break;
		case "Multiplicity":
			SimpleNode prevSibling = (SimpleNode) jjtGetPreviousSibling();
			SimpleNode stateNode = (SimpleNode)prevSibling.jjtGetFirstStateNode();
			transitionChar = stateNode.jjtGetValue().toString().charAt(0); //get transition char
			sourceState = a.getState(nextStateID-1); //source state (last inserted one)
			destinationState = a.getState(stateNode.getStateID()); //destination state
			sourceState.addConnection(destinationState, transitionChar);
			SimpleNode nextNode = (SimpleNode) stateNode.jjtGetNextStateNode();
			if(this.jjtGetValue().toString().charAt(0)=='*') //when multiplicity * is found, add bypassing connections to previous States
			{
				sourceState.setFlag(true);
				a.addBypassConnections(destinationState, transitionChar);
				while(nextNode!=null && nextNode.getStateID()!=sourceState.getID())
				{
					sourceState.addConnection(a.getState(nextNode.getStateID()), nextNode.jjtGetValue().toString().charAt(0));
					if(!nextNode.jjtIsNextNodeStar())
						break;
					else
						nextNode = (SimpleNode) nextNode.jjtGetNextStateNode();
				}
			}
			break;
		case "Char":
			this.stateID = nextStateID; //add stateID to node
			State newState = new State(this.stateID);
			a.addState(newState);
			sourceState = a.getState(this.stateID-1); //source state (previously inserted one)
			destinationState = newState; //destination state (the one inserted just now)
			transitionChar = this.jjtGetValue().toString().charAt(0); //get transition char
			sourceState.addConnection(destinationState, transitionChar);
			newState.setFinalState(true); //new state is final
			a.clearPreviousFinalStates(destinationState);
			if(!this.jjtIsNextNodeStar()) //if this char has no multiplicity, add the bypass but clear the final states
			{
				a.addBypassConnections(destinationState, transitionChar);
				a.clearPreviousFinalStates(destinationState);
			}
			break;
		}
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				SimpleNode n = (SimpleNode) children[i];
				n.generateAutomaton(a);
			}
		}
	}

	public void dump(String prefix) {
		System.out.println(toString(prefix));

		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				SimpleNode n = (SimpleNode) children[i];
				if (n != null) {
					n.dump(prefix + " ");
				}
			}
		}
	}
}

/*
 * JavaCC - OriginalChecksum=c182086b4a6c2ffa42add9568197005b (do not edit this
 * line)
 */
