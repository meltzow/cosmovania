package model.ES.processor.interaction;

import model.ES.component.lifeCycle.DecayOnTouch;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.Touching;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class DestroyedOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(DecayOnTouch.class, Touching.class);

	}

	@Override
	protected void onEntityEachTick(Entity e) {
		setComp(e, new ToRemove());
	}

}
