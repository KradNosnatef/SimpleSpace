package core.spaceSimple;

public class OrbitCalculator {//Paint Dynamic Orbit Movement
	Entity entity;
	public int angleDelta=0;
	double[] arrayOfIsoAngle;
	public OrbitCalculator(Entity entity) {
		this.entity=entity;
		arrayOfIsoAngle=new double[360];
		correctArray();
	}
	private void correctArray(){
		int i;
		double p;
		double l1,l2;
		double scale;
		arrayOfIsoAngle[0]=0;
		arrayOfIsoAngle[1]=1;
		for(i=2;i<=180;i++){
			p=arrayOfIsoAngle[i-1]-arrayOfIsoAngle[i-2];
			l1=entity.getSemiminorAxis()*entity.getSemiminorAxis()/(entity.getSemimajorAxis()-entity.getSemifocallength()*Math.cos(Math.toRadians(Double.valueOf(i-2))));
			l2=entity.getSemiminorAxis()*entity.getSemiminorAxis()/(entity.getSemimajorAxis()-entity.getSemifocallength()*Math.cos(Math.toRadians(Double.valueOf(i))));
			arrayOfIsoAngle[i]=p*(l2/l1)+arrayOfIsoAngle[i-1];
		}
		scale=entity.getPeriod()/(2*arrayOfIsoAngle[180]);
		for(i=0;i<=180;i++)arrayOfIsoAngle[i]=arrayOfIsoAngle[i]*scale;
		for(i=181;i<=359;i++)arrayOfIsoAngle[i]=entity.getPeriod()-arrayOfIsoAngle[360-i];
	}
	public void refreshArray() {
		correctArray();
	}
	public int getAngle(){
		int i;
		double t=entity.phase.getSum();
		t=t+arrayOfIsoAngle[angleDelta];
		for(;;) {
			t=t-entity.getPeriod();
			if(t<0)break;
		}
		t=t+entity.getPeriod();
		i=binarySearch(arrayOfIsoAngle,t);
		return(i);
	}
	private int binarySearch(double[] a,double t){//use linarySearch first
		int i=0;
		if(t>=a[359])i=359;
		else{
			for(;;){
				if(a[i]>t)break;
				i++;
			}
			i--;
		}
		System.out.println("i="+i);
		return(i);
	}
}
