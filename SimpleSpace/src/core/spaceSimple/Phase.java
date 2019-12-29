package core.spaceSimple;


public class Phase {
	public static double val=0;
	double phaseDelta=0;
	Entity entity;
	public Phase(Entity entity) {
		this.entity=entity;
	}
	public double getSum() {
		return(val+phaseDelta);
	}
	public void setDelta(double phaseDelta) {
		this.phaseDelta=phaseDelta;
	}
	public void optimization() {
		if(getSum()>entity.period)phaseDelta-=entity.period;
	}
}
