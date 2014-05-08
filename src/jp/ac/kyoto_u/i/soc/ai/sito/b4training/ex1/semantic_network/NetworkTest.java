package jp.ac.kyoto_u.i.soc.ai.sito.b4training.ex1.semantic_network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NetworkTest {
	Network network = new Network();
	
	@Before
	public void setUp(){
		network.setUp();
	}

	@Test
	public void testMakeLink() {
		String result = network.ask("S.Jobs is-a crazy_man");
		assertEquals("Success!", result);
	}
	
	@Test
	public void testWhatSearch() {
		String result = network.ask("?x is-a Apple's_Founder");
		assertEquals("S.Jobs AND S.Wozniak", result);
	}
	
	@Test
	public void testTFSearch1() {
		String result = network.ask("Jack is-a Apple's_Founder?");
		assertEquals("False", result);
	}
	
	@Test
	public void testTFSearch2() {
		String result = network.ask("S.Wozniak is-a Apple's_Founder?");
		assertEquals("True", result);
	}
	
	@Test
	public void testAndSearch1(){
		String result = network.ask("S.Jobs is-a Apple's_Founder? AND S.Jobs is-a MicroSoft's_Founder?");
		assertEquals("False", result);
	}
	
	@Test
	public void testAndSearch2(){
		String result = network.ask("?x is-a Apple's_Founder AND ?x likes The_Beatles");
		assertEquals("S.Jobs", result);
	}
	
	@Test
	public void testOrSearch(){
		String result = network.ask("S.Jobs is-a Apple's_Founder? OR S.Jobs is-a MicroSoft's_Founder?");
		assertEquals("True", result);
	}
	
	@Test
	public void testDeepSearch1(){
		String result = network.ask("S.Jobs is-a ?x");
		assertEquals("Former_Apple_CEO AND Apple's_Founder AND Genius", result);
	}
	
	@Test
	public void testDeepSearch2(){
		String result = network.ask("S.Jobs is-a Genius?");
		assertEquals("True", result);
	}

}
