package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.genes.MoveDecisionsProbability;
import core.soup.block.iBlock;

//TOTO REF delete interface
public interface iIdvmMoveCalculation {
	public Pos calcPosFromDirection(Decisions pDirection, Pos pIdvmPos,
			ArrayList<iBlock> pIdvmBlocks) throws PosIsOutOfGrid;

	public Decisions getTargetDirection(IdvmState pState,
			HashMap<Pos, Sensor> pDetectedPos);

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences)
			throws PosIsOutOfGrid;

	Decisions calcMovingDirection(
			HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences,
			IdvmState pLState, Decisions pTargetDirection);

	Decisions getCalculatedDirection();

}
