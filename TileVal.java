
public enum TileVal {
	EMPTY("Empty", "Empty"), 
	X("X", "Green"), 
	O("O", "Red");
	private String name;
	private String colour;
	
	private TileVal(String name, String colour){
		this.name = name;
		this.colour = colour;
	}
	
	@Override
	public String toString(){
		return (TicTacToe.Ximg == null ? this.colour : this.name);
	}
	
}
