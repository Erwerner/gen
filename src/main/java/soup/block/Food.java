package soup.block;


public class Food implements iBlock {

	public Boolean isBarrier() {
		return false;
	}

	public BlockType getBlockType() {
		return BlockType.FOOD;
	}
	
}
