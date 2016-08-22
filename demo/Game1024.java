package com.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
/**
 * 1024算法测试
 * 
 * @date Aug 18, 2016
 * @author wangXiaodan
 */
public class Game1024 {
	static Random random = new Random();
	final static int WIN_NUM = 1024;
	private static final int[] mk = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private static int surplus = mk.length;
	private static final Set<Integer> inputSet = new HashSet<Integer>();
	
	/**
	 * 初始化命令
	 * @author wangXiaodan
	 */
	public static void init(){
		
		inputSet.add(-1);
		inputSet.add(0);
		inputSet.add(1);
		inputSet.add(2);
		inputSet.add(3);
		randomPositionNum();
	}
	
	/**
	 * 命令输入方法
	 * @author wangXiaodan
	 * @return
	 */
	public static int inputCommand(){
		try {
			Scanner cin=new Scanner(System.in);
			System.out.println("请输入移动指令：-1（退出）\t0（左）\t1（上）\t2（右）\t3（下）");
			int nextInt = cin.nextInt();
			while(!inputSet.contains(nextInt)){
				System.out.println(nextInt + "输入错误，请重新输入:-1（退出）\t0（左）\t1（上）\t2（右）\t3（下）");
				nextInt = cin.nextInt();
			};
			return nextInt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 入口方法
	 * @author wangXiaodan
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		init();
		while(!hasWin()){
			printResult();
			
			int command = inputCommand();
			if(-1==command){
				System.out.println("退出系统");
				break;
			}else{
				while(!move(command)){
					System.out.println("无法移动，请输入其他命令");
					command = inputCommand();
				}
			}
			
			randomPositionNum();
		}
	}
	
	/**
	 * 根据给定指令移动数组(核心)
	 * 指令：0（左）	1（上）	2（右）	3（下）
	 * @author wangXiaodan
	 * @param redirection
	 */
	public static boolean move(int redirection){
		boolean result = false;
		
		int lineStep,step,startN;
		// 0（左）	1（上）	2（右）	3（下）
		switch (redirection) {
		case 0: // 
			lineStep = 4;step = 1;startN = 0;
			break;
		case 1:
			lineStep = 1;step = 4;startN = 0;
			break;
		case 2:
			lineStep = 4;step = -1;startN = 3;
			break;
		case 3:
			lineStep = 1;step = -4;startN = 12;
			break;
		default:
			return false;
		}
		
		
		for(int i=0;i<4;i++){
			int emptyIndex = -1;
			int preNum = -1;
			int start = startN + i*lineStep;
			for(int n=start,m=0;m<4;n+=step,m++){
				
				if(mk[n] == -1 ){
					// 没有数字
					if(emptyIndex== -1){
						emptyIndex = n;
					}
				}else{
					// 存在数字
					if(preNum == mk[n]){
						if(emptyIndex==-1){
							mk[n-step] += mk[n];
							emptyIndex = n;
						}else{
							mk[emptyIndex-step] += mk[n];
						}
						preNum = -1;
						mk[n] = -1;
						result = true;
						surplus++;
					}else if(emptyIndex!=-1){
						mk[emptyIndex] = mk[n];
						preNum = mk[n];
						mk[n] = -1;
						emptyIndex += step;
						result = true;
					}else{
						preNum = mk[n];
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 打印当前游戏结果
	 * @author wangXiaodan
	 */
	public static void printResult(){
		System.out.println("当前结果");
		for(int i=0;i<mk.length;++i){
			if(mk[i] == -1){
				System.out.print("-\t");
			}else{
				System.out.print(mk[i] + "\t");
			}
			
			if((i+1)%4==0){
				System.out.println();
			}
		}
	}
	
	
	/**
	 * 在数组为-1的随机位置生成数字2,如果生成失败，表示游戏结束
	 * @author wangXiaodan
	 * @return	true:生成成功，游戏继续    false:生成失败，游戏结束
	 */
	public static boolean randomPositionNum(){
		if(surplus>0){
			int randomPosition = random.nextInt(surplus);
			
			for(int i=0,flagNum=-1;i<mk.length;++i){
				if(mk[i] == -1){
					flagNum++;
				}
				
				
				if(flagNum==randomPosition){
					mk[i] = 2;
					surplus--;
					break;
				}
				
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 判断是否获胜
	 * @author wangXiaodan
	 * @return   true:获胜    false:还未获胜
	 */
	public static boolean hasWin(){
		for(int x:mk){
			if(x==WIN_NUM){
				System.out.println("您胜利了！");
				return true;
			}
		}
		return false;
	}
}
