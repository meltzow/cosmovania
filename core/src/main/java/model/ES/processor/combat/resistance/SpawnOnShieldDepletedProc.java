package model.ES.processor.combat.resistance;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.commonLogic.Spawner;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.combat.resistance.SpawnOnShieldDepleted;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.BlueprintLibrary;

public class SpawnOnShieldDepletedProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnShieldDepleted.class, Attrition.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		SpawnOnShieldDepleted spawn = e.get(SpawnOnShieldDepleted.class);
		Attrition att = e.get(Attrition.class);
		
		if(att.getActualShield() != spawn.getLastShieldValue()){
			if(att.getActualShield() == 0){
				Spawner.spawnWithSpawnerStance(entityData, e.getId(), spawn.getBlueprintNames());
			}
			setComp(e, new SpawnOnShieldDepleted(spawn.getBlueprintNames(), att.getActualShield()));
		}
	}
}
