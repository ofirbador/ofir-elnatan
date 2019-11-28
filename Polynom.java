package myMath;
import java.util.ArrayList;
import java.util.Iterator;
import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> pol;
	/**
	 * Zero (empty polynom)

	 */
	public Polynom(){
		pol=new ArrayList<Monom>();
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)(-1.2x-7.1)", "(3-3.4x+1)((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom (String s){
		pol=new ArrayList<Monom>();
		String temp = "";
		int cnt=0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(0)=='+' || s.charAt(0)=='-' )
			{	
				if(s.charAt(0)=='-')
				{
					temp= "-";
				}
				s=s.substring(i+1);

			}
			if(s.charAt(i) == '+' || s.charAt(i) == '-')
			{
				temp = temp+s.substring(cnt,i);
				Monom m =new Monom(temp);
				pol.add(m);	
				temp="";
				cnt=i;
			}
		}
		if(cnt<s.length())
		{
			temp = s.substring(cnt);
			Monom m = new Monom(temp);
			pol.add(m);	
		}
	}

		

	@Override
	public double f(double x){
		double f=0;
		Iterator<Monom> fun = pol.iterator();
		while(fun.hasNext())
		{
			Monom m= fun.next();
			f+=m.f(x);
		}
		return f;
	}

	@Override
	public void add(Polynom_able p1){
		Iterator<Monom> addI = p1.iteretor();
		while(addI.hasNext())
		{
			Monom m = addI.next();
			this.add(m);;
		}
	}

	@Override
	public void add(Monom m1){
		Monom m = new Monom("0");
		int cnt=0;
		for(int i=0;i<pol.size();i++)
		{
			m=pol.get(i);
			if(m1.get_power()==m.get_power())
			{
				m.add(m1);
				pol.set(i, m);
			}
			else {cnt++;}
		}
		if(cnt==pol.size()) {pol.add(m1);}
	}
	
	public static void main(String[] args)
	{
		Polynom p1 = new Polynom("3+3x+x^2");
		Polynom p2 = new Polynom("3+3+2x+5x^2");
		p1.substract(p2);
		System.out.println(p1);
		
	}

	@Override
	public void substract(Polynom_able p1){
		Iterator<Monom> polup=this.iteretor();
		Polynom sub=new Polynom();
		while(polup.hasNext())
		{
			boolean power_match = false;
			Monom mu=polup.next();
			Iterator<Monom> poldown=p1.iteretor();
			while(poldown.hasNext() && !power_match )
			{
				Monom md = poldown.next();
				if(md.get_power()==mu.get_power())
				{
					power_match = true;
					double a=mu.get_coefficient();
					double b=md.get_coefficient();
					Monom cul = new Monom(a-b,md.get_power());
					
					if(cul.get_coefficient() != 0) 	{sub.add(cul);}
					else {poldown.remove();}
				}
			}
			
			if(!power_match)
			{
				Monom neg = new Monom("-1");
				mu.multipy(neg);
				sub.add(mu);
			}

			if(!poldown.hasNext())
			{
				Monom zero = new Monom("0");
				sub.add(zero);
				this.pol=sub.pol;
			}
		}
		this.pol=sub.pol;
	}

	@Override
	public void multiply(Polynom_able p1){
		Polynom multiplication = new Polynom();
		Iterator<Monom> pol1 = p1.iteretor();
		while( pol1.hasNext())
		{
			Monom m1=pol1.next();
			for(int i=0;i<pol.size();i++)	
			{
				m1.multipy(pol.get(i));
				multiplication.add(m1);
			}
		}
		this.pol=multiplication.pol;
	}

	@Override
	public boolean equals(Polynom_able p1){
		boolean ans=true;
		Iterator<Monom> pol1 = pol.iterator();
		Iterator<Monom> pol2 = p1.iteretor();
		Monom m1=pol1.next();
		Monom m2=pol2.next();
		while(ans && pol1.hasNext())
		{
			m1=pol1.next();
			m2=pol2.next();
			if(m1.equals(m2)) {ans=true;}
			else { ans=false;}
		}
		return ans;
	}

	@Override
	public boolean isZero() {
		if(pol.size() == 0) {return true;}
		else return false;
	}

	@Override
	public double root(double x0, double x1, double eps){
		double l = x0;
		double r = x1;

		if (l >r || this.f(l)*this.f(r)>0) {
			throw new RuntimeException("parameters problem");
		}
		while(r-l > eps) {
			double mid=(r+l)/2;
			if(this.f(l)*this.f(mid) <= 0) r=mid;
			else l=mid;
		}
		return (l+r)/2;
	}

	@Override
	public Polynom_able copy(){	
		int cnt=0;
		Iterator<Monom> polcopy = pol.iterator();
		Monom m= polcopy.next();
		while(polcopy.hasNext())
		{
			pol.set(cnt,m);
			cnt++;
			m=polcopy.next();
		}

		return this;
	}

	@Override
	public Polynom_able derivative() {
		Polynom poldev = new Polynom();
		Iterator<Monom> dev = this.iteretor();
		while(dev.hasNext()) {
			Monom m = dev.next();
			poldev.add(m.derivative());
		}
		return poldev;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double sum=0;
		double f=0;
		while(f<=sum)
		{
			Iterator<Monom> area = this.iteretor();
			while(area.hasNext())
			{
				Monom m=area.next();
				sum=sum+m.f(f);
			}

		}
		return sum;
	}

	@Override
	public void multiply(Monom m1){
		Monom m = new Monom("0");

		for(int i=0;i<pol.size();i++)
		{
			m=pol.get(i);
			m.multipy(m1);	
			pol.set(i, m);	
		}


	}
	public String toString() {
		if(pol.size() == 0 )  return "Polynomial not initialized";

		Iterator<Monom> i = pol.iterator();
		Monom f = i.next();
		String polString = f.toString();
		while(i.hasNext()) {

			f = i.next();
			if(f.get_coefficient()>0)polString =polString + "+"  + f.toString();
			else if(f.get_coefficient()<0)polString =polString  + f.toString();
		}
		return polString ;
	}

	@Override
	public Iterator<Monom> iteretor() {
		return pol.iterator();
	}


}