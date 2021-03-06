package ui.console.populationprinter;

import java.util.List;

import core.population.PopulationGene;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;
import ui.mvc.Model;
import ui.mvc.View;
import ui.presenter.iPresentPopulation;

public class ViewConsolePopulationPrinter extends View {
	private final iPresentPopulation mPopulation;

	public ViewConsolePopulationPrinter(Model pModel) {
		super(pModel);
		mPopulation = (iPresentPopulation) pModel.getPresenter(iPresentPopulation.class);
	}

	public void update() {
		int lStepCount = 0;
		for (Idvm iIdvm : mPopulation.getIdvmList()) {
			lStepCount += iIdvm.getStepCount();
		}
		int lPartnerCount = 0;
		for (Idvm iIdvm : mPopulation.getIdvmList()) {
			lPartnerCount += iIdvm.getPartnerCount();
		}
//		System.out.println("Average steps: " + lStepCount / pFittestIdvm.size() + " * " + pFittestIdvm.size());
		System.out.println("Average partner: " + 100 * lPartnerCount / mPopulation.getIdvmList().size() + "/100 * "
				+ mPopulation.getIdvmList().size());
		printBlockStats(mPopulation.getIdvmList(), 4);
//		printBlockStats(pFittestIdvm, 6);
//		printBlockStats(pFittestIdvm, 8);
//		printBlockStats(pFittestIdvm, 10);
//		printBlockStats(pFittestIdvm, 12);

		printPopulationStats(mPopulation);
	}

	private void printPopulationStats(iPresentPopulation pPopulation) {

		try {
			List<PopulationGene> lSortedGenes = pPopulation.getGenomePool().getGenesSortedByRank();

			// initialCellGrow
			for (PopulationGene iGene : lSortedGenes) {
				if (iGene.getOriginGene().getClass() == IdvmCell.class && iGene.mSequenceIndex < 4)
					if (iGene.getHostCounter() > pPopulation.getIdvmList().size() / 3)
						System.out.println("\t" + iGene);
			}
			System.out.println("- - - - - - - - - - - - - - -");

			for (int i = 0; i < 100; i++) {
				PopulationGene lCurrentGene = lSortedGenes.get(i);
				if (lCurrentGene.mSequenceIndex > 3)
					if (lCurrentGene.getHostCounter() > pPopulation.getIdvmList().size() / 3)
						System.out.println("\t" + lCurrentGene);
			}

		} catch (PopulationEmpty e) {
			e.printStackTrace();
		}
	}

	private static void printBlockStats(List<Idvm> pList, int pCount) {
		System.out.print(pCount + " Cells: ");
		BlockType[] lCellBlocks = { BlockType.DEFENCE, BlockType.MOVE, BlockType.LIFE, BlockType.SENSOR,
				BlockType.NULL };
		for (BlockType iBlockType : lCellBlocks) {
			int lTotalBlockCount = 0;
			for (Idvm iIdvm : pList) {
				for (int idx = 0; idx < (pCount); idx++) {
					IdvmCell lCellGrow = iIdvm.getGenomeOrigin().cellGrow.get(idx);
					if (lCellGrow.getBlockType() == iBlockType)
						lTotalBlockCount++;
				}
			}
			System.out.print(100 * lTotalBlockCount / pList.size() / (pCount) + "% " + iBlockType + "; ");
		}
		System.out.println("");
	}

}
