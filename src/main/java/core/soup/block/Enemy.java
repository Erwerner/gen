package core.soup.block;

import java.util.Random;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import globals.Helpers;

public class Enemy extends Block {
	Decisions mCurrentDirection;
	private iBlockGrid mBlockGrid;

	public Enemy(iBlockGrid pBlockGrid) {
		super(BlockType.ENEMY);
		setRandomDirection();
		mBlockGrid = pBlockGrid;
	}

	public void step() {
		setDirection();
		move();
	}

	private void move() {
		while (true)
			try {
				Pos lNewPos;
				Pos lDetectIdvmPos = null;
				if (Helpers.checkChance(0.8))
					lDetectIdvmPos = detectIdvmPos();
				if (lDetectIdvmPos != null) {
					lNewPos = lDetectIdvmPos;
				} else {
					lNewPos = mPos.getPosFromDirection(mCurrentDirection);
				}
				setPosition(lNewPos);
				break;
			} catch (PosIsOutOfGrid e) {
				mCurrentDirection = mCurrentDirection.opposite();
				move();
			}
	}

	// REF move to BlockGrid
	private Pos detectIdvmPos() {
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++) {
				if (x == 0 || y == 0) {
					Pos lDetectPos = new Pos(mPos.x + x, mPos.y + y);
					try {
						iBlock lDetectedBlock;
						lDetectedBlock = mBlockGrid.getBlock(lDetectPos);
						if (lDetectedBlock == null)
							continue;
						switch (lDetectedBlock.getBlockType()) {
						case DEFENCE:
						case LIFE:
						case SENSOR:
						case MOVE:
							return lDetectPos;
						default:
							break;
						}
					} catch (PosIsOutOfGrid e) {
						// No grid position
					}
				}
			}
		return null;
	}

	private void setDirection() {
		if (Helpers.rndInt(15) == 1)
			setRandomDirection();
	}

	private void setRandomDirection() {
		Decisions[] lDirections = { Decisions.UP, Decisions.DOWN, Decisions.LEFT, Decisions.RIGHT, };
		mCurrentDirection = (Decisions) Helpers.rndArrayEntry(lDirections);
	}

}
