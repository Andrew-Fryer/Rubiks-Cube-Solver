
public class Cube {

	public static void main(String[] args) {
		String[] testingArgs = new String[1];
		testingArgs[0] = "L U2 R2 F2 L2 U2 R' B2 U2 R D2 U R2 D' F L' D L2 F' D2 F'";
		String[] scramble = testingArgs[0].split(" ");
		Cube myCube = new Cube();
		myCube.doMoves(scramble);
		System.out.println("Solving...");
		myCube.solve();
	}
	
	Cube(){
		for (byte i = 0; i < 24; i++) {
			edges[i] = i;
			//corners[i] = i;
		}
	}
	
	private void doMoves(String[] scramble) {
		for (String moveName : scramble) {
			getMove(moveName).execute();
		}
	}
	
	private Move getMove(String moveName) {
		Move result;
		switch (moveName) {
			case "R":
				result = moves[0];
				break;
			case "U":
				result = moves[1];
				break;
			case "L":
				result = moves[2];
				break;
			case "F":
				result = moves[3];
				break;
			case "D":
				result = moves[4];
				break;
			case "B":
				result = moves[5];
				break;
			case "R'":
				result = moves[6];
				break;
			case "U'":
				result = moves[7];
				break;
			case "L'":
				result = moves[8];
				break;
			case "F'":
				result = moves[9];
				break;
			case "D'":
				result = moves[10];
				break;
			case "B'":
				result = moves[11];
				break;
			case "R2":
				result = moves[12];
				break;
			case "U2":
				result = moves[13];
				break;
			case "L2":
				result = moves[14];
				break;
			case "F2":
				result = moves[15];
				break;
			case "D2":
				result = moves[16];
				break;
			case "B2":
				result = moves[17];
				break;
			default:
				throw new Error("Not a legit move notation.");
		}
		return result;
	}
	
	//private String[20] currentSolution = [];
	private byte depth = 0; // same as index of last moveName in movePath
	private String[] movePath = new String[20];
	
	private void solve() {
		if (this.isSolved()) {
			this.displaySolution();
			//System.exit(0); // this will exit as soon as we get a solution
		}
		if (depth < 20) {
			depth++;
			for (Move move : moves) {
				move.execute();
				log(move);
				this.solve();
				//unlog(move); just let the garbage sit there :)         <- this is a little sloppy
				move.undo();
			}
			depth--;
		}
	}
	
	private void log(Move move) {
		movePath[depth-1] = move.getName();
	}
	/*
	private void unlog() {
		movePath.pop();
	}
	*/
	
	private boolean isSolved() {
		for (byte i = 0; i < 24; i++) {
			if (edges[i]!=i) {// || corners[i]!=i) {
				return false;
			}
		}
		return true;
	}

	private void displaySolution() {
		System.out.println();
		System.out.print("Solution:    ");
		for (int i = 0; i < depth; i++) {
			System.out.print(movePath[i] + ' ');
		}
	}
	
	private byte[] edges = new byte[24];
	//private byte[] corners = new byte[24];
	
	private interface Algorithm {
		public String getName();
		public void execute();
		// public String[] composition;
	}
	
	private abstract class Move implements Algorithm {
		public void undo() {
			execute(); // really inefficient!
			execute();
			execute();
		};
	}
	
	private final Move[] moves = { // static?
			new R(),
			new U(),
			new L(),
			new F(),
			new D(),
			new B(),
			new R3(),
			new U3(),
			new L3(),
			new F3(),
			new D3(),
			new B3(),
			new R2(),
			new U2(),
			new L2(),
			new F2(),
			new D2(),
			new B2(),
	};
	
	private class R extends Move{
		private static final String name = "R";
		public final String getName() {return name;};
		public void execute() {
			rotate(12,1,9,21,19);
		}
	}
	private class U extends Move{
		public final String getName() {return "U";};
		public void execute() {
			rotate(0,16,4,8,12);
		}
	}
	private class L extends Move{
		public final String getName() {return "L";};
		public void execute() {
			rotate(4,3,17,23,11);
		}
	}
	private class F extends Move{
		public final String getName() {return "F";};
		public void execute() {
			rotate(8,2,5,20,15);
		}
	}
	private class D extends Move{
		public final String getName() {return "D";};
		public void execute() {
			rotate(20,10,6,18,14);
		}
	}
	private class B extends Move{
		public final String getName() {return "B";};
		public void execute() {
			rotate(16,22,7,0,13);
		}
	}
	
	private class R2 extends Move{
		public final String getName() {return "R2";};
		public void execute() {
			rotate(12,1,9,21,19);
			rotate(12,1,9,21,19);
		}
	}
	private class U2 extends Move{
		public final String getName() {return "U2";};
		public void execute() {
			rotate(0,16,4,8,12);
			rotate(0,16,4,8,12);
		}
	}
	private class L2 extends Move{
		public final String getName() {return "L2";};
		public void execute() {
			rotate(4,3,17,23,11);
			rotate(4,3,17,23,11);
		}
	}
	private class F2 extends Move{
		public final String getName() {return "F2";};
		public void execute() {
			rotate(8,2,5,20,15);
			rotate(8,2,5,20,15);
		}
	}
	private class D2 extends Move{
		public final String getName() {return "D2";};
		public void execute() {
			rotate(20,10,6,18,14);
			rotate(20,10,6,18,14);
		}
	}
	private class B2 extends Move{
		public final String getName() {return "B2";};
		public void execute() {
			rotate(16,22,7,0,13);
			rotate(16,22,7,0,13);
		}
	}
	
	private class R3 extends Move{
		public final String getName() {return "R3";};
		public void execute() {
			rotate(12,1,9,21,19);
			rotate(12,1,9,21,19);
			rotate(12,1,9,21,19);
		}
	}
	private class U3 extends Move{
		public final String getName() {return "U3";};
		public void execute() {
			rotate(0,16,4,8,12);
			rotate(0,16,4,8,12);
			rotate(0,16,4,8,12);
		}
	}
	private class L3 extends Move{
		public final String getName() {return "L3";};
		public void execute() {
			rotate(4,3,17,23,11);
			rotate(4,3,17,23,11);
			rotate(4,3,17,23,11);
		}
	}
	private class F3 extends Move{
		public final String getName() {return "F3";};
		public void execute() {
			rotate(8,2,5,20,15);
			rotate(8,2,5,20,15);
			rotate(8,2,5,20,15);
		}
	}
	private class D3 extends Move{
		public final String getName() {return "D3";};
		public void execute() {
			rotate(20,10,6,18,14);
			rotate(20,10,6,18,14);
			rotate(20,10,6,18,14);
		}
	}
	private class B3 extends Move{
		public final String getName() {return "B3";};
		public void execute() {
			rotate(16,22,7,0,13);
			rotate(16,22,7,0,13);
			rotate(16,22,7,0,13);
		}
	}
	
	
	
	private void rotate(int i,int j,int k,int l,int m) {
		byte temp;
		
		temp = edges[i];
		edges[i] = edges[i+1];
		edges[i+1] = edges[i+2];
		edges[i+2] = edges[i+3];
		edges[i+3] = temp;
		
		temp = edges[j];
		edges[j] = edges[k];
		edges[k] = edges[l];
		edges[l] = edges[m];
		edges[m] = temp;
	}
	
}
