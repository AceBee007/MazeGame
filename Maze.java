import java.util.Random;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;

class Mazedata {
	public final static int P = 10;
	public final static int L = (2 * P + 1);

	long seed = (Runtime.getRuntime().freeMemory())*System.currentTimeMillis(); //ランダム関数の初期化
	Random r = new Random(seed);
	char mazewall[][] = new char[L][L];

	int mazewall_int[][] = new int[L][L];
	//Arrays.fill(mazewall_int,0);

	public void initMaze() {//迷路の初期化
		for(int i = 0; i < L; i++) {
			for(int j = 0; j < L; j++) {
				this.mazewall[i][j] = '*';
			}
		}
		for(int i = 1; i < L - 1; i++) {
			for(int j = 1; j < L - 1; j++) {
				this.mazewall[i][j] = ' ';
			}
		}
		for(int i = 2; i < L - 2; i = i + 2) {
			for(int j = 2; j < L - 2; j = j + 2) {
				this.mazewall[i][j] = '*';
			}
		}
		for(int i = 0; i < L; i++) {
			for(int j = 0; j < L; j++) {
				this.mazewall_int[i][j] = 0;
			}
		}

	}

	public void printMaze() {//迷路を表示する
			System.out.print('\n');
		for(int i = 0; i < L; i++) {
			System.out.print(i + "\t");
			for(int j = 0; j < L; j++) {
				if(this.mazewall[i][j] == 'S') {

					/*
					 * ターミナルの文字に色付け
					 * 色付けしたい文字の前に"\u001b[3y;4ym"を付け加える
					 * 3y の時は前景色 4y の時は背景色 00はリセット
					 * black   -> y == 0
					 * red     -> y == 1
					 * green   -> y == 2
					 * yellow  -> y == 3
					 * blue    -> y == 4
					 * magenta -> y == 5
					 * cyan    -> y == 6
					 * white   -> y == 7
					 * */
					System.out.print("\u001b[00;42m"+this.mazewall[i][j]+"\u001b[00m");
				} else if(this.mazewall[i][j] == 'G') {
					System.out.print("\u001b[00;41m"+this.mazewall[i][j]+"\u001b[00m");
				} else if(this.mazewall[i][j] != ' ') {
					System.out.print("\u001b[37;47m"+this.mazewall[i][j]+"\u001b[00m");
				} else {
					System.out.print(this.mazewall[i][j]);
				}
			}
			System.out.print('\n');
		}
	}

	public void randwall_UDLR() {//迷路の上三行の壁をランダム設置
		int rlt_of_r, i;
		i = 2;
		for(int j = 2; j < L-2; j = j+2){
			rlt_of_r = this.r.nextInt(4);
			if(rlt_of_r == 3){
				this.mazewall[i-1][j] = 'U';
			}
			else if (rlt_of_r == 0){
				this.mazewall[i+1][j] = 'D';
			}
			else if(rlt_of_r == 1){
				this.mazewall[i][j+1] = 'R';
			}
			else if (rlt_of_r == 2){
				this.mazewall[i][j-1] = 'L';
			}
		}
	}

	public void randwall_DLR() {//迷路の他の行の壁をランダム設置
		int rlt_of_r;

		for(int i = 4; i < L - 2; i = i + 2) {
			for(int j = 2; j < L - 2; j = j + 2) {
				rlt_of_r = this.r.nextInt(3);
				if (rlt_of_r == 0){
					this.mazewall[i+1][j] = 'D';
				}
				else if(rlt_of_r == 1){
					this.mazewall[i][j+1] = 'R';
				}
				else if (rlt_of_r == 2){
					this.mazewall[i][j-1] = 'L';
				}
			}
		}
	}

	public  void set_GnS(int tmp){
		int i_1, i_2, j_1, j_2;

		i_1 = this.r.nextInt(L/2)*2+1;
		j_1 = this.r.nextInt(L/2)*2+1;

		if (tmp == 0){
			if (this.mazewall[i_1][j_1] == ' '){
				this.mazewall[i_1][j_1] = 'S';
				tmp = tmp+1;
				System.out.print("i1 = "+i_1+"\tj1 = "+j_1+"\n");
				System.out.print("tmp = "+tmp+"\n");
			}
			else{
				this.set_GnS(tmp);
				return ;
			}
		}
		i_2 = this.r.nextInt(L/2)*2+1;
		j_2 = this.r.nextInt(L/2)*2+1;
		if (this.mazewall[i_2][j_2] == ' '){
			this.mazewall[i_2][j_2] = 'G';
			System.out.print("i2 = "+i_2+"\tj2 = "+j_2+"\n");
		}
		else {
			this.set_GnS(tmp);
			return ;
		}
	}

	public void createMaze(){//上のメソッドを使い、迷路を初期化し、壁を置き、スタート地点とゴール地点を設置する。
		this.initMaze();
		this.randwall_UDLR();
		this.randwall_DLR();
		int tmp = 0;
		this.set_GnS(tmp);
		this.CharToInt();
	}

	public void CharToInt(){
		for(int i = 0; i < L; i++) {
			for(int j = 0; j < L; j++) {
				if(this.mazewall[i][j] == 'S') {
					mazewall_int[i][j] = 2;
				} else if(this.mazewall[i][j] == 'G') {
					mazewall_int[i][j] = 3;

				} else if(this.mazewall[i][j] != ' ') {
					mazewall_int[i][j] = 1;
				}
			}
		}
	}

	public void printIntMaze(){
			System.out.print('\n');
		for(int i = 0; i < this.L; i++) {
			System.out.print(i + "\t");
			for(int j = 0; j < this.L; j++) {
				if(this.mazewall_int[i][j] == 2) {
					/*
					 * ターミナルの文字に色付け
					 * 色付けしたい文字の前に"\u001b[3y;4ym"を付け加える
					 * 3y の時は前景色 4y の時は背景色 00はリセット
					 * black   -> y == 0
					 * red     -> y == 1
					 * green   -> y == 2
					 * yellow  -> y == 3
					 * blue    -> y == 4
					 * magenta -> y == 5
					 * cyan    -> y == 6
					 * white   -> y == 7
					 * */
					System.out.print("\u001b[00;42m"+this.mazewall_int[i][j]+"\u001b[00m");
				} else if(this.mazewall_int[i][j] == 3) {
					System.out.print("\u001b[00;41m"+this.mazewall_int[i][j]+"\u001b[00m");
				} else if(this.mazewall_int[i][j] != 0) {
					System.out.print("\u001b[36;47m"+this.mazewall_int[i][j]+"\u001b[00m");
				} else {
					System.out.print(this.mazewall_int[i][j]);
				}
			}
			System.out.print('\n');
		}

	}
}


class testmaze{
	public static void main(String argv[]){
		Mazedata md = new Mazedata(); //md <- MazeData
		md.createMaze();
		md.printMaze();
		//		for(int i = 0; i < md.L; i++) {
		//			System.out.print(i + "\t");
		//			for(int j = 0; j < md.L; j++) {
		//				if(md.mazewall[i][j] == 'S') {
		//					/*
		//					 * ターミナルの文字に色付け
		//					 * 色付けしたい文字の前に"\u001b[3y;4ym"を付け加える
		//					 * 3y の時は前景色 4y の時は背景色 00はリセット
		//					 * black   -> y == 0
		//					 * red     -> y == 1
		//					 * green   -> y == 2
		//					 * yellow  -> y == 3
		//					 * blue    -> y == 4
		//					 * magenta -> y == 5
		//					 * cyan    -> y == 6
		//					 * white   -> y == 7
		//					 * */
		//					System.out.print("\u001b[00;42m"+md.mazewall[i][j]+"\u001b[00m");
		//				} else if(md.mazewall[i][j] == 'G') {
		//					System.out.print("\u001b[00;41m"+md.mazewall[i][j]+"\u001b[00m");
		//				} else if(md.mazewall[i][j] != ' ') {
		//					System.out.print("\u001b[37;47m"+md.mazewall[i][j]+"\u001b[00m");
		//				} else {
		//					System.out.print(md.mazewall[i][j]);
		//				}
		//			}
		//			System.out.print('\n');
		//		}
		md.printIntMaze();
	}

}
