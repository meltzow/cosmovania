package model.ES.processor.command;

import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarStance;
import util.math.Angle;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.ECS.LogicLoop;
import controller.ECS.Processor;

public class NeededRotationProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarNeededRotation.class, MotionCapacity.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		PlanarStance stance = e.get(PlanarStance.class); 
		
		double maxRotation = capacity.maxRotationSpeed * LogicLoop.getSecondPerTick();
		maxRotation = Math.min(Math.abs(neededRotation.angle.getValue()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.angle.getValue());
		
		PlanarStance newStance = new PlanarStance(stance.coord, new Angle(stance.orientation.getValue() + possibleRotation), stance.elevation, stance.upVector);
		
		setComp(e, newStance);
		removeComp(e, PlanarNeededRotation.class);
	}
}
