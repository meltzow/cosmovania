package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.combat.Attackable;
import model.ES.component.combat.damage.Projectile;
import model.ES.component.motion.Touching;

public class IsEnemyTouching extends LeafTask<ShipBlackboard> {

	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		
		Touching touching = bb.entityData.getComponent(bb.eid, Touching.class);
		if(touching != null)
			if(bb.entityData.getComponent(touching.getTouched(), Attackable.class) != null){
				bb.enemyDetected = touching.getTouched();
				success();
			} else if(bb.entityData.getComponent(touching.getTouched(), Projectile.class) != null){
				bb.enemyDetected = bb.entityData.getComponent(touching.getTouched(), Projectile.class).sender;
				success();
			}
		else
			fail();

	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
