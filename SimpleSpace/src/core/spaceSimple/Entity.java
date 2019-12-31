package core.spaceSimple;
import java.lang.Math;



public class Entity {
	
	public class InitialState{
		public double v0_val;
		public double v0_direction;//use degree as parameter
		//v0_direction describes the angle between v0 and r0, with the counterclockwise as the positive direction.
		public double r0_val;
		public double r0_direction;//use degree as parameter,the usually usage is the same as polar cooradinate
		public InitialState() {
		}
	}
	
	InitialState initialState;
    double ap;
    double pe;
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
        for(;;) {
        	if(v0_direction>360)v0_direction-=180;
        	else break;
        }
        for(;;) {
        	if(v0_direction<0)v0_direction+=180;
        	else break;
        }
        initialState.r0_val=r0_val;
        initialState.r0_direction=r0_direction;
        for(;;) {
        	if(r0_direction>360)r0_direction-=360;
        	else break;
        }
        for(;;) {
        	if(r0_direction<0)r0_direction+=360;
        	else break;
        }
        correctParameter();
    }
    private void correctParameter(){//base on Kepler's second law of planetary motion
    	int ifObtuse;
    	if(initialState.v0_direction>90)ifObtuse=1;
    	else ifObtuse=0;
    	double v0=initialState.v0_val;
    	double r0=initialState.r0_val;
    	double sintheta2=Math.sin(Math.toRadians(initialState.v0_direction))*Math.sin(Math.toRadians(initialState.v0_direction));
    	double eccentricityMG=Math.sqrt(center.massG*center.massG-2*center.massG*v0*v0*r0*sintheta2+v0*v0*v0*v0*r0*r0*sintheta2);
    	double cosmiu0=-(v0*v0*r0*sintheta2-center.massG)/eccentricityMG;
    	pe=(v0*v0*r0*r0*sintheta2)/(center.massG+eccentricityMG);
    	ap=(v0*v0*r0*r0*sintheta2)/(center.massG-eccentricityMG);
    	semimajorAxis=(ap+pe)/2;
    	semiminorAxis=Math.sqrt(ap*pe);
    	semifocallength=(ap-pe)/2;
    	System.out.println(semimajorAxis+","+semiminorAxis+","+semifocallength);
    	eccentricity=eccentricityMG/center.massG;
    	period=2*Math.PI*Math.sqrt(semimajorAxis*semimajorAxis*semimajorAxis/(center.massG));
    	rotate=(360-Math.toDegrees(Math.acos(cosmiu0)));
    	orbitCalculator.angleDelta=(Math.toDegrees(Math.acos(cosmiu0))+360);
    	rotate=rotate+initialState.r0_direction;
    	
    	if(ifObtuse==1) {
    		double rbuf=rotate;
    		rotate=initialState.r0_direction-rotate;
    		orbitCalculator.angleDelta+=(2*rbuf-initialState.r0_direction);
    	}
    	for(;;) {
    		if(rotate>360)rotate=rotate-360;
    		else break;
    	}
    	for(;;) {
    		if(rotate<0)rotate=rotate+360;
    		else break;
    	}
    	/*
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
        period=2*Math.PI*Math.sqrt(semimajorAxis*semimajorAxis*semimajorAxis/(center.massG));*/
    }
    public double getAP(){
        return(ap);
    }
    public double getPE(){
        return(pe);
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
