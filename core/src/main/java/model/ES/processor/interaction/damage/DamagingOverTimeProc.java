package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;

import controller.ECS.LogicLoop;
import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.damage.DamageOverTime;
import model.ES.component.interaction.Damaging;

public class DamagingOverTimeProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Damaging.class, DamageOverTime.class, LifeTime.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Damaging damaging = e.get(Damaging.class);
		Attrition att = entityData.getComponent(damaging.target, Attrition.class);
		if(att != null){
			// target is damageable by attrition
			DamageOverTime dot = e.get(DamageOverTime.class);
			int timeSinceLastTick = dot.getTimeSinceLastTick();
			
			int timePerTick = (int)Math.round(1000d/dot.getTickPerSecond());
			if(timeSinceLastTick > timePerTick){
				timeSinceLastTick -= timePerTick;
				int damagePerTick = dot.getAmountPerSecond()/dot.getTickPerSecond();
				
				DamageApplier applier = new DamageApplier(att, dot.getType(), damagePerTick);
				entityData.setComponent(damaging.target, applier.getResult());

				if(applier.getDamageOnShield() > 0)
					DamageFloatingLabelCreator.create(entityData, damaging.target, dot.getType(), applier.getDamageOnShield(), true, true);
				if(applier.getDamageOnHitPoints() > 0)
					DamageFloatingLabelCreator.create(entityData, damaging.target, dot.getType(), applier.getDamageOnHitPoints(), false, true);

			}
			timeSinceLastTick += LogicLoop.getMillisPerTick();
			setComp(e, new DamageOverTime(dot.getType(), dot.getAmountPerSecond(), dot.getTickPerSecond(), timeSinceLastTick));
		} else
			setComp(e, new ToRemove());
	}
}