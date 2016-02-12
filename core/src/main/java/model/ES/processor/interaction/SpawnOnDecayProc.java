package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.SpawnOnDecay;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.BlueprintLibrary;
import model.ES.serial.PrototypeCreator;

/**
 * Must be called before ToRemove processor
 * @author Beno�t
 *
 */
public class SpawnOnDecayProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnDecay.class, ToRemove.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		SpawnOnDecay spawn = e.get(SpawnOnDecay.class);

		for(String bpName : spawn.getBlueprintNames()) {
			EntityId spawned = BlueprintLibrary.getBlueprint(bpName).createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(e.getId(), PlanarStance.class);
			if(stance != null && entityData.getComponent(spawned, PlanarStance.class) != null)
				entityData.setComponent(spawned, stance);
		}
	}
}
