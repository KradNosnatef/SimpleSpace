package core.spaceSimple;
import java.lang.Math;

public class Entity {
    double speed0;
    double ap,apSpeed;
    double pe,peSpeed;
    double semimajorAxis;
    double semifocallength;
    double semiminorAxis;
    double eccentricity;
    double period;
    public Phase phase;
    public Maneuver maneuver;
    public OrbitCalculator orbitCalculator;
    CenterEntity center;
    public Entity(CenterEntity center){
    	this.center=center;
    	phase=new Phase(this);
    	maneuver=new Maneuver(this);
    	orbitCalculator=new OrbitCalculator(this);
    }
    public void setVAPPE(double speed,double APPE){
        this.speed0=speed;
        this.pe=APPE;
        correctParameter();
    }
    private void correctParameter(){//base on Kepler's second law of planetary motion
        ap=(speed0*speed0*pe*pe)/(2*center.massG-speed0*speed0*pe);
        peSpeed=speed0;
        apSpeed=(2*center.massG-speed0*speed0*pe)/(speed0*pe);
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
