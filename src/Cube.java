
public class Cube {

	public static void main(String[] args) {
		String[] testingArgs = new String[1];
		testingArgs[0] = "R L'"; // "L U2 U' R' U' R' U' R' U' R' U' R' U' R' U' R' U' R' U' R'";
		String[] scramble = testingArgs[0].split(" ");
		Cube myCube = new Cube();
		myCube.doMoves(scramble);
		
		int[] movePath = myCube.solve();
		
		// display solution
		System.out.println();
		System.out.print("Solution:    ");
		for (int i = 0; movePath[i] != -1; i++) {
			System.out.print(myCube.TwoByTwoMoves[movePath[i]].getName() + ' ');
			// is it okay that there will be a space after the last move?
		}
	}
	
	private int[] solve() {
		int i;
		int depth = 0; // same as index of last moveName in movePath
		int[] movePath = new int[20]; // holds the indexes of the moves that have been applied
		for (int j = 0; j < movePath.length; j++) {
			movePath[j] = -1;
		}
		
		movePath[0] = 0;
		TwoByTwoMoves[movePath[depth]].execute();

		System.out.println("Solving...");
		
		while (!isSolvedInAnyDirection()) {
//			if(depth > 14) {
//				throw new Error("No solution found");
//			}
			
			// move cube to the next state
			
			if (movePath[depth] < 5) {
				TwoByTwoMoves[movePath[depth]].undo();
				movePath[depth]++;
				
				if (depth>0 && movePath[depth]<4 && (movePath[depth]%3 == movePath[depth-1]%3)) {
					movePath[depth]++;
				}
				
				TwoByTwoMoves[movePath[depth]].execute();
			} else {
				// pull out
				for (i = depth; ; i--) {
				// implement bottom level seperately to be more efficient?
					
					if (i < 0) {
						// go one level deeper
						depth++;
						System.out.println("depth is now " + depth);
						break;
						
					} else if (movePath[i] != 5) {
						// next move
						TwoByTwoMoves[movePath[i]].undo();
						movePath[i]++;
						
						if (i>0 && (movePath[i]%6 == movePath[i-1]%6)) {
							movePath[i]++;
						}
						if (movePath[i] < 6) {
							TwoByTwoMoves[movePath[i]].execute();
						} else {
							// clean up after optimization
							// go in to the last state in this part of the tree
							for (; i <= depth; i++) {
							// I could avoid this with a flag, but it would be messy
								movePath[i] = 5;
								TwoByTwoMoves[movePath[i]].execute();
							}
							// since i == depth + 1, we avoid "going in" below
						}
						break;
					}

					TwoByTwoMoves[movePath[i]].undo(); // really hard to read....
				}
				
				// go in
				i++; // so that we don't overwrite the current move
				for (;i <= depth; i++) {
					movePath[i] = 0; // those are all on the same side (so I can optimize here)
					TwoByTwoMoves[movePath[i]].execute();
				}
				// current value of i (depth+1) should not be used
			}
		}
		
		return movePath;
	}
	
	Cube(){
		for (byte i = 0; i < 24; i++) {
			corners[i] = i;
		}
	}
	
	private boolean isSolvedInAnyDirection() {
		for (int i = 0; i < 6; i++) {
			if (isSolved(i*4)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSolved(int k) {
		for (int i = 0; i < 24; i++) {
			if (corners[i] != (i+k)%24) {
				return false;
			}
		}
		return true;
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

	private byte[] corners = new byte[24];
	
	private interface Move {
		public String getName();
		public void execute();
		public void undo();
	}
 	
	private abstract class Move2 {
		public abstract void execute();
		public void undo() {
			execute();
		}
	}
	
	private final Move[] TwoByTwoMoves = {
			new R(),
			new U(),
			new F(),
			new R3(),
			new U3(),
			new F3()
	};
	
	private final Move[] moves = {
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
	
	private class R implements Move{
		private static final String name = "R";
		public final String getName() {return name;};
		public void execute() {
			rotate(12,1,9,21,19,2,10,22,16);
		}
		public void undo() {
			rotate3(12,1,9,21,19,2,10,22,16);
		}
	}
	private class U implements Move{
		public final String getName() {return "U";};
		public void execute() {
			rotate(0,16,4,8,12,17,5,9,13);
		}
		public void undo() {
			rotate3(0,16,4,8,12,17,5,9,13);
		}
	}
	private class L implements Move{
		public final String getName() {return "L";};
		public void execute() {
			rotate(4,3,17,23,11,0,18,20,8);
		}
		public void undo() {
			rotate3(4,3,17,23,11,0,18,20,8);
		}
	}
	private class F implements Move{
		public final String getName() {return "F";};
		public void execute() {
			rotate(8,2,5,20,15,3,6,21,12);
		}
		public void undo() {
			rotate3(8,2,5,20,15,3,6,21,12);
		}
	}
	private class D implements Move{
		public final String getName() {return "D";};
		public void execute() {
			rotate(20,10,6,18,14,11,7,19,15);
		}
		public void undo() {
			rotate3(20,10,6,18,14,11,7,19,15);
		}
	}
	private class B implements Move{
		public final String getName() {return "B";};
		public void execute() {
			rotate(16,22,7,0,13,23,4,1,14);
		}
		public void undo() {
			rotate3(16,22,7,0,13,23,4,1,14);
		}
	}
	
	private class R2 extends Move2 implements Move{
		public final String getName() {return "R2";};
		public void execute() {
			rotate2(12,1,9,21,19,2,10,22,16);
		}
	}
	private class U2 extends Move2 implements Move{
		public final String getName() {return "U2";};
		public void execute() {
			rotate2(0,16,4,8,12,17,5,9,13);
		}
	}
	private class L2 extends Move2 implements Move{
		public final String getName() {return "L2";};
		public void execute() {
			rotate2(4,3,17,23,11,0,18,20,8);
		}
	}
	private class F2 extends Move2 implements Move{
		public final String getName() {return "F2";};
		public void execute() {
			rotate2(8,2,5,20,15,3,6,21,12);
		}
	}
	private class D2 extends Move2 implements Move{
		public final String getName() {return "D2";};
		public void execute() {
			rotate2(20,10,6,18,14,11,7,19,15);
		}
	}
	private class B2 extends Move2 implements Move{
		public final String getName() {return "B2";};
		public void execute() {
			rotate2(16,22,7,0,13,23,4,1,14);
		}
	}
	
	private class R3 implements Move{
		public final String getName() {return "R'";};
		public void execute() {
			rotate3(12,1,9,21,19,2,10,22,16);
		}
		public void undo() {
			rotate(12,1,9,21,19,2,10,22,16);
		}
	}
	private class U3 implements Move{
		public final String getName() {return "U'";};
		public void execute() {
			rotate3(0,16,4,8,12,17,5,9,13);
		}
		public void undo() {
			rotate(0,16,4,8,12,17,5,9,13);
		}
	}
	private class L3 implements Move{
		public final String getName() {return "L'";};
		public void execute() {
			rotate3(4,3,17,23,11,0,18,20,8);
		}
		public void undo() {
			rotate(4,3,17,23,11,0,18,20,8);
		}
	}
	private class F3 implements Move{
		public final String getName() {return "F'";};
		public void execute() {
			rotate3(8,2,5,20,15,3,6,21,12);
		}
		public void undo() {
			rotate(8,2,5,20,15,3,6,21,12);
		}
	}
	private class D3 implements Move{
		public final String getName() {return "D'";};
		public void execute() {
			rotate3(20,10,6,18,14,11,7,19,15);
		}
		public void undo() {
			rotate(20,10,6,18,14,11,7,19,15);
		}
	}
	private class B3 implements Move{
		public final String getName() {return "B'";};
		public void execute() {
			rotate3(16,22,7,0,13,23,4,1,14);
		}
		public void undo() {
			rotate(16,22,7,0,13,23,4,1,14);
		}
	}
	
	
	
	private void rotate(int i,int j,int k,int l,int m,int n,int o,int p,int q) {
		byte temp;
		
		temp = corners[i];
		corners[i] = corners[i+1];
		corners[i+1] = corners[i+2];
		corners[i+2] = corners[i+3];
		corners[i+3] = temp;
		
		temp = corners[j];
		corners[j] = corners[k];
		corners[k] = corners[l];
		corners[l] = corners[m];
		corners[m] = temp;
		
		temp = corners[n];
		corners[n] = corners[o];
		corners[o] = corners[p];
		corners[p] = corners[q];
		corners[q] = temp;
	}
	
	private void rotate2(int i,int j,int k,int l,int m,int n,int o,int p,int q) {
		byte temp;
		
		temp = corners[i];
		corners[i] = corners[i+2];
		corners[i+2] = temp;
		
		temp = corners[i+1];
		corners[i+1] = corners[i+3];
		corners[i+3] = temp;
		
		temp = corners[j];
		corners[j] = corners[l];
		corners[l] = temp;
		
		temp = corners[k];
		corners[k] = corners[m];
		corners[m] = temp;
		
		temp = corners[n];
		corners[n] = corners[p];
		corners[p] = temp;
		
		temp = corners[o];
		corners[o] = corners[q];
		corners[q] = temp;
	}
	
	private void rotate3(int i,int j,int k,int l,int m,int n,int o,int p,int q) {
		byte temp;
		
		temp = corners[i];
		corners[i] = corners[i+3];
		corners[i+3] = corners[i+2];
		corners[i+2] = corners[i+1];
		corners[i+1] = temp;
		
		temp = corners[j];
		corners[j] = corners[m];
		corners[m] = corners[l];
		corners[l] = corners[k];
		corners[k] = temp;
		
		temp = corners[n];
		corners[n] = corners[o];
		corners[o] = corners[p];
		corners[p] = corners[q];
		corners[q] = temp;
	}
	
}
