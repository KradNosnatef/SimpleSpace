package core.spaceSimple;

public class Maneuver {//Maneuver and keep Phase correct
	Entity entity;
	public Maneuver(Entity entity) {
		this.entity=entity;
	}
	public void APAccelerate(double per) {
		entity.orbitCalculator.angleDelta=entity.orbitCalculator.getAngle();
		entity.phase.phaseDelta=-Phase.val;
		entity.setInitialState(entity.getAPSpeed()*(1+per),90, entity.getAP(),0);
	}
	public void PEAccelerate(double per) {
		entity.orbitCalculator.angleDelta=entity.orbitCalculator.getAngle();
		entity.phase.phaseDelta=-Phase.val;
		entity.setInitialState(entity.getPESpeed()*(1+per),90, entity.getPE(),0);
	}
}
