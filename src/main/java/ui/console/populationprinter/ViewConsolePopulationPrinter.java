package ui.console.populationprinter;

import java.util.HashMap;
import java.util.List;

import core.population.PopulationGene;
import core.soup.block.IdvmCell;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;
import devutils.Debug;
import globals.Config;
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
		Debug.printCurrentChange();

		int lPartnerCount = 0;
		for (Idvm iIdvm : mPopulation.getIdvmList()) {
			lPartnerCount += iIdvm.getPartnerCount();
		}

		System.out.println("Average partner: " + 100 * lPartnerCount / mPopulation.getPopulationSize() + "/100 * "
				+ mPopulation.getPopulationSize());
//		printBlockStats(mPopulation.getIdvmList(), 4);
		printPopulationStats();
	}

	private void printPopulationStats() {

		try {
			List<PopulationGene> lSortedGenes = mPopulation.getGenesSortedByRank();

			// initialCellGrow
			for (PopulationGene iGene : lSortedGenes) {
				if (iGene.getOriginGene().getClass() == IdvmCell.class && iGene.mSequenceIndex < 4)
					if (iGene.getHostCounter() > mPopulation.getPopulationSize() / 3)
						System.out.println("\t" + iGene);
			}
			System.out.println("- - - - - - - - - - - - - - -");

			for (int i = 0; i < 100; i++) {
				PopulationGene lCurrentGene = lSortedGenes.get(i);
				if (lCurrentGene.mSequenceIndex > 2)
					if (lCurrentGene.getHostCounter() > mPopulation.getPopulationSize() / 3)
						System.out.println("\t" + lCurrentGene);
			}
			System.out.println("- - - - - - - Hunger - - - - - - - -");
			HashMap<Integer, Integer> lHungerValueList = mPopulation.getHungerValueList();
			for (int iHungerValue = 0; iHungerValue <= Config.cMaxHunger; iHungerValue++) {
				if (lHungerValueList.containsKey(iHungerValue))
					if (lHungerValueList.get(iHungerValue) > mPopulation.getPopulationSize() / 4)
						System.out.println("Hunger #" + lHungerValueList.get(iHungerValue) + ": " + iHungerValue);
			}

		} catch (PopulationEmpty e) {
			e.printStackTrace();
		}
	}

//	private static void printBlockStats(List<Idvm> pList, int pCount) {
//		System.out.print(pCount + " Cells: ");
//		BlockType[] lCellBlocks = { BlockType.DEFENCE, BlockType.MOVE, BlockType.LIFE, BlockType.SENSOR,
//				BlockType.NULL };
//		for (BlockType iBlockType : lCellBlocks) {
//			int lTotalBlockCount = 0;
//			for (Idvm iIdvm : pList) {
//				for (int idx = 0; idx < (pCount); idx++) {
//					IdvmCell lCellGrow = iIdvm.getGenomeOrigin().cellGrow.get(idx);
//					if (lCellGrow.getBlockType() == iBlockType)
//						lTotalBlockCount++;
//				}
//			}
//			System.out.print(100 * lTotalBlockCount / pList.size() / (pCount) + "% " + iBlockType + "; ");
//		}
//		System.out.println("");
//	}

}
