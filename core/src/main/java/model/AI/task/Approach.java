package model.AI.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import model.AI.blackboard.ShipBlackboard;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class Approach extends LeafTask<ShipBlackboard> {
	@Override
	public void run() {
		ShipBlackboard bb = getObject();
		bb.entityData.setComponent(bb.eid, new PlanarNeededThrust(bb.getVectorToEnemy()));
		success();
	}

	@Override
	protected Task<ShipBlackboard> copyTo(Task<ShipBlackboard> task) {
		return task;
	}

}
