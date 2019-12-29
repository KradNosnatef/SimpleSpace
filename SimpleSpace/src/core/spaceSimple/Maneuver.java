package core.spaceSimple;

public class Maneuver {//Maneuver and keep Phase correct
	Entity entity;
	public Maneuver(Entity entity) {
		this.entity=entity;
	}
	public void APAccelerate(double per) {
		entity.orbitCalculator.angleDelta=entity.orbitCalculator.getAngle();
		entity.phase.phaseDelta=-Phase.val;
		entity.setVAPPE(entity.getAPSpeed()*(1+per), entity.getAP());
	}
	public void PEAccelerate(double per) {
		entity.orbitCalculator.angleDelta=entity.orbitCalculator.getAngle();
		entity.phase.phaseDelta=-Phase.val;
		entity.setVAPPE(entity.getPESpeed()*(1+per), entity.getPE());
	}
}
