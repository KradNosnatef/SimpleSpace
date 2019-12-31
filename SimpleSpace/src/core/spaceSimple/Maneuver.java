package core.spaceSimple;

public class Maneuver {//Maneuver and keep Phase correct
	Entity entity;
	public Maneuver(Entity entity) {
		this.entity=entity;
	}
	public void radialAcceleration(double dv){
		double ra=entity.orbitCalculator.getAngle();
		double rapa=ra;
		double ifObtuse=0;
		if(rapa>180) {
			rapa=360-rapa;
			ifObtuse=1;
		}
		double r=entity.getSemiminorAxis()*entity.getSemiminorAxis()/(entity.getSemimajorAxis()-entity.getSemifocallength()*Math.cos(Math.toRadians(Double.valueOf(ra))));
		double rcpa=Math.toDegrees(Math.asin((Math.sin(Math.toRadians(rapa))*r)/(2*entity.semimajorAxis-r)));
		
		double p=entity.initialState.v0_val*entity.initialState.v0_val*entity.initialState.r0_val*entity.initialState.r0_val;
		p=p*Math.sin(Math.toRadians(entity.initialState.v0_direction))*Math.sin(Math.toRadians(entity.initialState.v0_direction));
		p=p/entity.center.massG;
		
		
		if((2*entity.semimajorAxis-r)<(p))rcpa=180-rcpa;
		
		double va=(rapa+rcpa)/2;
		if(ifObtuse==1)va=180-va;
		System.out.println("rapa="+rapa+",rcpa="+rcpa);
		double v=(entity.initialState.r0_val*entity.initialState.v0_val*Math.sin(Math.toRadians(entity.initialState.v0_direction)))/(r*Math.sin(Math.toRadians(va)));
		v=v+dv;
		ra+=entity.rotate;
		entity.phase.phaseDelta=-Phase.val;
		System.out.println("v="+v+", va="+va+", r="+r+", ra="+ra);
		entity.setInitialState(v, va, r, ra);
	}
}
