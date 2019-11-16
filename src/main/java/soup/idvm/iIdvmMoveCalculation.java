package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import soup.block.iBlock;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;
import genes.MoveProbability;

public interface iIdvmMoveCalculation {
	public Pos calcPosFromDirection(Direction pDirection, Pos pIdvmPos,
			ArrayList<iBlock> pIdvmBlocks) throws ExOutOfGrid;

	public Direction getTargetDirection(IdvmState pState,
			HashMap<Pos, Sensor> pDetectedPos);

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveProbability>> pMovementSequences)
			throws ExOutOfGrid;

	Direction calcMovingDirection(
			HashMap<IdvmState, ArrayList<MoveProbability>> pMovementSequences,
			IdvmState pLState, Direction pTargetDirection);

}
