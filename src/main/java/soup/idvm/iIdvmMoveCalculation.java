package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import soup.block.iBlock;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;
import genes.MovementSequence;

public interface iIdvmMoveCalculation {
	public Pos calcPosFromDirection(Direction pDirection, Pos pIdvmPos, ArrayList<iBlock> pIdvmBlocks) throws ExOutOfGrid;

	public Direction getTargetDirection(IdvmState pState,
			HashMap<Pos, Sensor> pDetectedPos);

	public Pos getMovingPosition(iIdvm pIdvm, HashMap<IdvmState, MovementSequence> pMovementSequences) throws ExOutOfGrid;

}
