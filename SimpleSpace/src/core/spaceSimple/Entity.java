package core.spaceSimple;
import java.lang.Math;



public class Entity {
	
	private class InitialState{
		public double v0_val;
		public double v0_direction;//use degree as parameter
		//v0_direction describes the angle between v0 and r0, with the counterclockwise as the positive direction.
		public double r0_val;
		public double r0_direction;//use degree as parameter,the usually usage is the same as polar cooradinate
		public InitialState() {
		}
	}
	
	InitialState initialState;
    double ap,apSpeed;
    double pe,peSpeed;
    double semimajorAxis;
    double semifocallength;
    double semiminorAxis;
    double eccentricity;
    double period;
    public double rotate;//when paint ,we should use this parameter to describe a rotated Oval
    //rotate is a parameter of the whole orbit of entity,not a live parameter of entity's situation
    public Phase phase;
    public Maneuver maneuver;
    public OrbitCalculator orbitCalculator;
    CenterEntity center;
    public Entity(CenterEntity center){
    	this.center=center;
    	phase=new Phase(this);
    	maneuver=new Maneuver(this);
    	orbitCalculator=new OrbitCalculator(this);
    	rotate=0;//directly use degree ,setRotation() of fxml use degree as prameter!
    	initialState=new InitialState();
    }
    public void setInitialState(double v0_val,double v0_direction,double r0_val,double r0_direction){
        initialState.v0_val=v0_val;
        initialState.v0_direction=v0_direction;
        initialState.r0_val=r0_val;
        initialState.r0_direction=r0_direction;
        correctParameter();
    }
    private void correctParameter(){//base on Kepler's second law of planetary motion
    	pe=initialState.r0_val;
        rotate=initialState.r0_direction;
        ap=(initialState.v0_val*initialState.v0_val*pe*pe)/(2*center.massG-initialState.v0_val*initialState.v0_val*pe);
        peSpeed=initialState.v0_val;
        apSpeed=(2*center.massG-initialState.v0_val*initialState.v0_val*pe)/(initialState.v0_val*pe);
        if(ap<pe){
            double lf=ap;ap=pe;pe=lf;
            lf=apSpeed;apSpeed=peSpeed;peSpeed=lf;
        }
        semimajorAxis=(ap+pe)/2;
        semifocallength=(ap-pe)/2;
        semiminorAxis=Math.sqrt(ap*pe);
        eccentricity=semifocallength/semimajorAxis;
        period=2*Math.PI*Math.sqrt(semimajorAxis*semimajorAxis*semimajorAxis/(center.massG));
    }
    public double getAP(){
        return(ap);
    }
    public double getAPSpeed(){
        return(apSpeed);
    }
    public double getPE(){
        return(pe);
    }
    public double getPESpeed(){
        return(peSpeed);
    }
    public double getSemimajorAxis(){
        return(semimajorAxis);
    }
    public double getEccentricity(){
        return(eccentricity);
    }
    public double getSemiminorAxis(){
        return(semiminorAxis);
    }
    public double getSemifocallength(){
        return(semifocallength);
    }
    public double getPeriod(){
        return(period);
    }
    
}
