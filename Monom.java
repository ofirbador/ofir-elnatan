package myMath;
import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom EULER = new Monom(2.71,0);
	public static final Monom PHI = new Monom(3.14,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom m) 
	{
		this(m.get_coefficient(), m.get_power());
	}

	public double get_coefficient(){
		return this._coefficient;
	}
	public int get_power(){
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative(){
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x){
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ****** add your code below *********

	public Monom(String s) {	

		if(s==null || s=="" || s=="^" )throw new RuntimeException( "null/empty/Incorrect feed"); //The number is invalid
		if(s=="0"){new Monom(ZERO);}  // number is 0
		if(s=="-x") {this.set_coefficient(-1);this.set_power(1);} //negative x
		if(s=="x") {this.set_coefficient(1);this.set_power(1);}	  //positive x
		if(!s.contains("x") && !s.contains("^")) ////check String for only number
			try {				
				double r=Double.parseDouble(s);
				this.set_coefficient(r);
				this.set_power(0);
			}
		catch (Exception e) {throw new RuntimeException("isn't number");}

		if(s.contains("x") && !s.contains("^")) //// for constant*x
		{
			if(s.charAt(s.length()-1)!='x'){throw new RuntimeException("no power, x isn't last");}
			
				try {
					String p = s.substring(0 , s.indexOf('x'));
					double r=Double.parseDouble(p);
					this.set_coefficient(r);
					this.set_power(1);
				}
				catch (Exception e) {throw new RuntimeException("coefficient isn't number");}
			
		}
		
		if(s.contains("x^") && s.lastIndexOf(s)!='^') //constant*x^integer
		{
			try {
				String f = s.substring(s.indexOf('^')+1);
				int r=Integer.parseInt(f);
				this.set_power(r);
				if(s.charAt(0)== 'x'){this.set_coefficient(1);}
				else if(s.charAt(0)=='-' && s.charAt(1)=='x') {this.set_coefficient(-1);}
				else if(s.charAt(0)=='+' && s.charAt(1)=='x') {this.set_coefficient(1);}
				else
				{
					String v = s.substring(0, s.indexOf('x'));
					double z=Double.parseDouble(v);
					this.set_coefficient(z);
				}
			}
			catch (Exception e) {throw new RuntimeException("trouble with coefficient or power");}
		}
	}

	public void add(Monom m){
		
		if(this.get_power()==m.get_power())
		{
			if((this._coefficient+m._coefficient)==0)
			{
				this.set_coefficient(0);
				this.set_power(0);
			}
			else{this.set_coefficient(this._coefficient+m._coefficient);}
		}
		
	}

	public void multipy(Monom d){
		if(this.get_coefficient()*d.get_coefficient()==0)
		{
			this.set_coefficient(0);
			this.set_power(0);
		}
		else  
		{
			this.set_coefficient((this.get_coefficient()*d.get_coefficient()));
			this.set_power(this.get_power()+d.get_power());
		}
		
	}

	public String toString(){
		String ans = get_coefficient()+"x^"+get_power();
		return ans;
	}

	public boolean equals(Monom m){
		if (this.get_coefficient() == 0 && m.get_coefficient() == 0)
			return true;

		else {
			if (this.get_coefficient() == m.get_coefficient() && this.get_power() == m.get_power())
				return true;

			else
				return false;
		}
	}

	//****** Private Methods and Data *******


	private void set_coefficient(double a){
		this._coefficient = a;
	}

	private void set_power(int p){
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
}