package myMath;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.junit.Test;

public class MonomTest {
	Monom m1 =new Monom(2,3);
	Monom m2 = new Monom("2x^3");
	String b="3x^1";

	@Test
	public void MonomString() 
	{
		Monom mS = new Monom(b);
		mS.toString();
		System.out.println(mS);
	}

	@Test
	public void Monomadd() 
	{
		m1.add(m2);
		System.out.println(m1);
	}

	@Test
	public void Monommultipy() 
	{
		m1.multipy(m2);
		System.out.println(m1);
	}

	@Test
	public void Monomequals() 
	{
		if(m1.equals(m2))
			System.out.println("m1==m2");
	}

	@Test
	public void MonomCP() 
	{
		System.out.println("coefficient ="+m1.get_coefficient());
		System.out.println("power ="+m1.get_power());
	}


}