import java.util.ArrayList;

public class Sudoku1{
	private int tableau[][]=new int [9][9];

	public class Index
	{
	    private int i,j;
	    public Index(int i,int j){this.i=i;this.j=j;}
	    public int getI() {return i;}
	    public int getJ() {return j;}
	}
	
	public Sudoku1(int tab[][]){
		this.tableau=tab;
	}
	
	public static int[][]clone(int t[][]){
		int res[][]=new int[t.length][t[0].length];
		for(int i=0;i<res.length;i++)	    
			for(int j=0;j<res[0].length;j++)
				res[i][j]=t[i][j]; 
		return res;
	}

	//resolve by increasing the possible values from 1 to 9
	public int [][]  resolveByIncreasing(){
		int actualIndex,possibleValue;
		int solution[][]=clone(tableau);
		//contains every cells that needs to be resolved(filled)
		ArrayList<Index> emptyCellList=new ArrayList<Index>();
		
		for(int line=0;line<solution.length;line++)
			for(int column=0;column<solution.length;column++)
				if(solution[line][column]==0)emptyCellList.add(new Index(line,column));	

		for (actualIndex = 0; actualIndex < emptyCellList.size(); actualIndex++) {
			for (possibleValue = 1; possibleValue < 10; possibleValue++) {
				if (verify(emptyCellList.get(actualIndex), possibleValue, solution)) {
					solution[emptyCellList.get(actualIndex).getI()][emptyCellList.get(actualIndex).getJ()] = possibleValue;break;			
				}
			}
			if (possibleValue == 10) {
				int indexToGoBackTo[] = getIndexToGoBackTo1(emptyCellList, actualIndex - 1, solution);
				solution[emptyCellList.get(indexToGoBackTo[0]).getI()][emptyCellList.get(indexToGoBackTo[0]).getJ()] = indexToGoBackTo[1];
				actualIndex = indexToGoBackTo[0];
			}

		}
		return solution;
	}		   

	//resolve by decreasing the possible values from 9 to 1
	public int [][]  resolveByDecreasing(){
		int actualIndex,possibleValue;
		int solution[][]=clone(tableau);
		ArrayList<Index> emptyCellList=new ArrayList<Index>();
		for(int line=0;line<solution.length;line++)
			for(int column=0;column<solution.length;column++)
				if(solution[line][column]==0)emptyCellList.add(new Index(line,column));	

		for (actualIndex = 0; actualIndex < emptyCellList.size(); actualIndex++) {
			for (possibleValue = 9; possibleValue > 0; possibleValue--) {
				if (verify(emptyCellList.get(actualIndex), possibleValue, solution)) {
					solution[emptyCellList.get(actualIndex).getI()][emptyCellList.get(actualIndex).getJ()] = possibleValue;
					break;			
				}
			}
			if (possibleValue == 0) {
				int indexToGoBackTo[] = getIndexToGoBackTo2(emptyCellList, actualIndex - 1, solution);
				solution[emptyCellList.get(indexToGoBackTo[0]).getI()][emptyCellList.get(indexToGoBackTo[0]).getJ()] = indexToGoBackTo[1];
				actualIndex = indexToGoBackTo[0];
			}

		}
		return solution;
	}	


	public static boolean  verify(Index ind,int value,int tab[][]){

		for(int f=0;f<9;f++)
			if((tab[ind.getI()][f]==value)||(tab[f][ind.getJ()]==value))return false;


		int reste1=ind.getI()%((int)Math.sqrt(9));
		int ligne_min=ind.getI()-reste1;int ligne_max=ligne_min+(int)Math.sqrt(9)-1;
		int reste2=ind.getJ()%((int)Math.sqrt(9));
		int colonne_min=ind.getJ()-reste2;int colonne_max=colonne_min+(int)Math.sqrt(9)-1;
		for(int w=ligne_min;w<=ligne_max;w++)
		{
			for(int y=colonne_min;y<=colonne_max;y++)
				if(tab[w][y]==value)return false;
		}
		return true;   
	}

	public static int[] getIndexToGoBackTo1( ArrayList<Index> emptyCellList,int indexToGoBackTo,int solution[][]){
		
		//res[0] = index to go back to
		//res[1] = value to put into the "index to go back to"
		
		int valueOfIndexToGoBackTo;
		int res[]=new int[2];
		Index actualIndex=emptyCellList.get(indexToGoBackTo);
		if(solution[actualIndex.getI()][actualIndex.getJ()]!=9){ 
			for(valueOfIndexToGoBackTo=solution[actualIndex.getI()][actualIndex.getJ()]+1;valueOfIndexToGoBackTo<10;valueOfIndexToGoBackTo++)				
				if(verify(actualIndex,valueOfIndexToGoBackTo,solution)){
					res[0]=indexToGoBackTo;res[1]=valueOfIndexToGoBackTo;return res;
				}				
			solution[actualIndex.getI()][actualIndex.getJ()]=0;res=getIndexToGoBackTo1(emptyCellList,indexToGoBackTo-1,solution);
		}
		else{
			solution[actualIndex.getI()][actualIndex.getJ()]=0;res=getIndexToGoBackTo1(emptyCellList,indexToGoBackTo-1,solution);
		}
		return res;
	}

public static int[] getIndexToGoBackTo2( ArrayList<Index> emptyCellList,int indexToGoBackTo,int solution[][]){
		
		//res[0] = index to go back to
		//res[1] = value to put into the "index to go back to"
		
		int valueOfIndexToGoBackTo;
		int res[]=new int[2];
		Index actualIndex=emptyCellList.get(indexToGoBackTo);
		if(solution[actualIndex.getI()][actualIndex.getJ()]!=0){ 
			for(valueOfIndexToGoBackTo=solution[actualIndex.getI()][actualIndex.getJ()]-1;valueOfIndexToGoBackTo>0;valueOfIndexToGoBackTo--)				
				if(verify(actualIndex,valueOfIndexToGoBackTo,solution)){
					res[0]=indexToGoBackTo;res[1]=valueOfIndexToGoBackTo;return res;
				}				
			solution[actualIndex.getI()][actualIndex.getJ()]=0;res=getIndexToGoBackTo2(emptyCellList,indexToGoBackTo-1,solution);
		}
		else{
			solution[actualIndex.getI()][actualIndex.getJ()]=0;res=getIndexToGoBackTo2(emptyCellList,indexToGoBackTo-1,solution);
		}
		return res;
	}

public static void printSudoku(int [][] sudoku) {
	for(int i=0;i<9;i++) {
		for (int j=0;j<9;j++) {
			if(j==8) {
				System.out.println(sudoku[i][j]+ ",");
			}
			else
				System.out.print(sudoku[i][j]+ " ");
		}
	}
}

	public static void main(String args[]) {
		int hardSudoku[][]=
			{
					{0,8,2,  0,1,0,  0,0,0},
					{7,0,0,  0,0,0,  0,3,0},
					{0,0,0,  0,0,6,  0,0,5},

					{0,0,0,  0,0,0,  0,8,0},
					{3,0,0,  7,0,0,  0,0,0},
					{0,0,0,  0,0,0,  1,0,4},

					{4,0,1,  0,0,0,  0,0,6},
					{0,0,0,  0,5,0,  0,0,0},
					{0,0,0,  8,0,0,  0,0,0},
			};
		
		long start = System.currentTimeMillis();
		
		Sudoku1 sudoku1 = new Sudoku1(hardSudoku);
		int solution1[][] = sudoku1.resolveByIncreasing();
		printSudoku(solution1);
		
		long middle = System.currentTimeMillis();
		System.out.println(" "+(middle-start));
		
		int solution2[][] = sudoku1.resolveByDecreasing();
		printSudoku(solution2);
		long end = System.currentTimeMillis();
		
		System.out.println(" "+ (end-middle));
		
		
	}

}
