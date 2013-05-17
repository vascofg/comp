package tests;
import static org.junit.Assert.*;
import parser.ParseException;
import parser.Regex2Auto;
import parser.TokenMgrError;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import parser.Automaton;
import parser.SimpleNode;
import parser.State;


public class Test {
	
	//GENERATING DFA here: http://hackingoff.com/compilers/regular-expression-to-nfa-dfa

	@org.junit.Test
	public void test1() { //ab
	    Automaton a = new Automaton();
	    State s0 = new State(0);
	    State s1 = new State(1);
	    State s2 = new State(2);
	    s0.addConnection(s1, 'a');
	    s1.addConnection(s2, 'b');
	    s2.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
		compareGeneratedAutomaton("ab", a);
	}
	
	@org.junit.Test
	public void test2() { //a*b
	    Automaton a = new Automaton();
	    State s0 = new State(0);
	    State s1 = new State(1);
	    State s2 = new State(2);
	    s0.addConnection(s1, 'a');
	    s0.addConnection(s2, 'b');
	    s1.addConnection(s1, 'a');
	    s1.addConnection(s2, 'b');
	    s2.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
		compareGeneratedAutomaton("a*b", a);
	}
	
	@org.junit.Test
	public void test3() { //(ab)*
	    Automaton a = new Automaton();
	    State s0 = new State(0);
	    State s1 = new State(1);
	    State s2 = new State(2);
	    s0.addConnection(s1, 'a');
	    s0.setFinalState(true); //falha aqui
	    s1.addConnection(s2, 'b');
	    s2.addConnection(s1, 'a');
	    s2.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
		compareGeneratedAutomaton("(ab)*", a);
	}
	
	@org.junit.Test
	public void test4() { //(a*(bc)d+)
	    Automaton a = new Automaton();
	    State s0 = new State(0);
	    State s1 = new State(1);
	    State s2 = new State(2);
	    State s3 = new State(3);
	    State s4 = new State(4);
	    s0.addConnection(s1, 'a');
	    s0.addConnection(s2, 'b');
	    s1.addConnection(s1, 'a');
	    s1.addConnection(s2, 'b');
	    s2.addConnection(s3, 'c');
	    s3.addConnection(s4, 'd');
	    s4.addConnection(s4, 'd');
	    s4.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
	    a.addState(s3);
	    a.addState(s4);
	    compareGeneratedAutomaton("(a*(bc)d+)", a);
	}
	
	@org.junit.Test
	public void test5() { //a*b*c*
		Automaton a = new Automaton();
		State s0 = new State(0);
		State s1 = new State(1);
		State s2 = new State(2);
		State s3 = new State(3);
		s0.addConnection(s1, 'a');
		s0.addConnection(s2, 'b');
		s0.addConnection(s3, 'c');
		s1.addConnection(s1, 'a');
		s1.addConnection(s2, 'b');
		s1.addConnection(s3, 'c');
		s2.addConnection(s2, 'b');
		s2.addConnection(s3, 'c');
		s3.addConnection(s3, 'c');
		s0.setFinalState(true);
		s1.setFinalState(true);
		s2.setFinalState(true);
		s3.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
	    a.addState(s3);
		compareGeneratedAutomaton("a*b*c*", a);
	}
	
	@org.junit.Test
	public void test6() { //a*b*c
		Automaton a = new Automaton();
		State s0 = new State(0);
		State s1 = new State(1);
		State s2 = new State(2);
		State s3 = new State(3);
		s0.addConnection(s1, 'a');
		s0.addConnection(s2, 'b');
		s0.addConnection(s3, 'c');
		s1.addConnection(s1, 'a');
		s1.addConnection(s2, 'b');
		s1.addConnection(s3, 'c');
		s2.addConnection(s2, 'b');
		s2.addConnection(s3, 'c');
		s3.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
	    a.addState(s3);
		compareGeneratedAutomaton("a*b*c", a);
	}
	
	@org.junit.Test
	public void test7() { //((a*b*)dc*)*
		//need to make connection from * state to all others with *
		Automaton a = new Automaton();
		State s0 = new State(0);
		State s1 = new State(1);
		State s2 = new State(2);
		State s3 = new State(3);
		State s4 = new State(4);
		s0.addConnection(s1, 'a');
		s0.addConnection(s2, 'b');
		s0.addConnection(s3, 'd');
		s1.addConnection(s1, 'a');
		s1.addConnection(s2, 'b');
		s1.addConnection(s3, 'd');
		s2.addConnection(s2, 'b');
		s2.addConnection(s3, 'd');
		s3.addConnection(s1, 'a');
		s3.addConnection(s2, 'b');
		s3.addConnection(s3, 'd');
		s3.addConnection(s4, 'c');
		s4.addConnection(s1, 'a');
		s4.addConnection(s2, 'b');
		s4.addConnection(s3, 'd');
		s4.addConnection(s4, 'c');
		s0.setFinalState(true);
		s3.setFinalState(true);
		s4.setFinalState(true);
	    a.addState(s0);
	    a.addState(s1);
	    a.addState(s2);
	    a.addState(s3);
	    a.addState(s4);
		compareGeneratedAutomaton("((a*b*)dc*)*", a);
	}
	
	public void compareGeneratedAutomaton(String regex, Automaton testAutomaton) {
	    Regex2Auto parser = new Regex2Auto(new ByteArrayInputStream(regex.getBytes(StandardCharsets.UTF_8)));
	    Automaton generatedAutomaton = new Automaton();
	    try
	    {
	      SimpleNode n = parser.Start();
	      n.generateAutomaton(generatedAutomaton);
	      assertEquals(testAutomaton,generatedAutomaton);
	    }
	    catch (ParseException e)
	    {
	      fail(e.getMessage());
	    }
	    catch (TokenMgrError e)
	    {
	      fail("Rejected");
	    }
	    catch (AssertionError e)
	    {
	      testAutomaton.dump();
	      System.out.println("-------NOT EQUAL TO-------");
	      generatedAutomaton.dump();
	      fail();
	    }
	  }
	}