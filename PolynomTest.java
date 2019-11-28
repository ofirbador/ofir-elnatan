package myMath;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomTest {
	Polynom p1=new Polynom("1+2x^2");
	Polynom p2=new Polynom("1+2x^2");
	Polynom p3=new Polynom();
	String p="3+2x^2";
	Monom m = new Monom("3x");
	
	@Test
	public void testString() 
	{
		Polynom toS =new Polynom(p);
		System.out.println(toS);
	}
	
	@Test
	public void AddMonToPol() 
	{
		p1.add(m);
		System.out.println(p1);
		p2.add(p2);
		System.out.println(p2);
	}
	
	@Test
	public void Polvalue() 
	{
		System.out.println(p1.f(2));
	}
	
	@Test
	public void Polmultiply() 
	{
		p1.multiply(p1);
		System.out.println(p1);
	}
	
	@Test
	public void PolmultiplyMon() 
	{
		p1.multiply(m);
		System.out.println(p1);
	}
	
	@Test
	public void Polequals() 
	{
		if(p1.equals(p2))
		System.out.println("p1==p2");
	}
	
	@Test
	public void PolZero() 
	{
	
		System.out.println(p3.isZero());
	}
	
	@Test
	public void Polroot() 
	{
		String g="1-2x";
		Polynom pt=new Polynom(g);
		
		System.out.println(pt.root(-1, 1, 0.00000001));
	}
	
	@Test
	public void Polcopy() 
	{
		System.out.println("copy is : "+p1.copy());
	}
	
	@Test
	public void Polderivative() 
	{
		System.out.println("derivative of pol is : "+p1.derivative());
	}
	
	@Test
	public void Polarea() 
	{
		System.out.println("area of pol is : "+p1.area(1,2,0.01));
	}
	
}