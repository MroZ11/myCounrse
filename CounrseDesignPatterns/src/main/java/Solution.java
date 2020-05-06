import cn.hutool.core.math.MathUtil;
import com.alibaba.fastjson.JSON;
import javolution.lang.MathLib;
import org.jscience.economics.money.Money;
import org.jscience.geography.coordinates.Time;
import org.jscience.physics.amount.Amount;
import org.jscience.physics.amount.AmountFormat;

import javax.measure.quantity.Duration;
import javax.measure.quantity.Length;
import javax.measure.quantity.LuminousIntensity;
import javax.measure.quantity.Velocity;
import javax.measure.unit.BaseUnit;
import javax.measure.unit.TransformedUnit;
import javax.measure.unit.Unit;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cloud on 2020/3/16.
 */
public class Solution {


    public static void main(String[] args) {
        String s = "a good   example";
        String[] strs = s.split(" +");
        System.out.println(JSON.toJSONString(strs));

        Pattern pattern = Pattern.compile(" +");

    }

    public int[] quicksorted (int[] arr){
        //选一个基准数，进行排序，保证基准数左边都比他小，右边都比他打，然后以基准数
        // ，分成两半，再次分别排序，直到不可在分
        //举例{5,8,1,2,6,9,7}
        //第一次选中基准数5，排序后{1,2,5,8,6,9,7}
        //分成两半，{1,2} {8,6,9,7}
        //左边选中基准1 排序后{1,2} 不可再排。右边选中基准数8，排序后{7,6,8,9},再分两半，{7,6}，{9}再排{6,7}{8}{9}
        //组合后{1,2,5,6,7,8,9}



        return null;
    }


    private static int getIndex(int[] arr, int low, int high) {
        //首先做最简单的把一个arr中的一个数放到他的正确下标处
        //https://blog.csdn.net/nrsc272420199/article/details/82587933
        int number = arr[0];




        return 0;




    }

    public int[][] gameOfLife(int[][] board) {
        //<2死亡 2-3存活  >3死亡  等于3复活
        if(board.length==0||board[0].length==0){

        }else{
            int[][] boardCopy = new int[board.length][board[0].length];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    int nowX=i, nowY=j;
                    int nowLive = board[i][j];
                    int aroundLiveCount = calcAroundLiveCount(board,i,j);
                    int nextLife =0;
                    if(nowLive==1){
                        //当前存活
                        if(aroundLiveCount>=2&&aroundLiveCount<=3) {
                            nextLife = 1;
                        }
                    }else{
                        //当前死亡
                        if(aroundLiveCount==3){
                            nextLife = 1;
                        }
                    }
                    boardCopy[i][j]=nextLife;
                }
            }
            board=boardCopy;
        }

        return board;
    }

    private int calcAroundLiveCount(int[][] board,int nowX,int nowY){
        int count = 0;
        //上
        int x1 = nowX-1, y1 = nowY;
        count+=isLive(x1,y1,board)?1:0;
        //下
        int x2= nowX+1, y2 = nowY;
        count+=isLive(x2,y2,board)?1:0;
        //左
        int x3= nowX, y3 = nowY+1;
        count+=isLive(x3,y3,board)?1:0;
        //右
        int x4= nowX, y4 = nowY-1;
        count+=isLive(x4,y4,board)?1:0;
        //左上
        int x5= nowX-1, y5 = nowY-1;
        count+=isLive(x5,y5,board)?1:0;
        //右上
        int x6= nowX-1, y6 = nowY+1;
        count+=isLive(x6,y6,board)?1:0;
        //左下
        int x7= nowX+1, y7 = nowY-1;
        count+=isLive(x7,y7,board)?1:0;
        //右下
        int x8= nowX+1, y8 = nowY+1;
        count+=isLive(x8,y8,board)?1:0;
        System.out.println(count);
        return count;
    }

    private boolean isLive(int x,int y,int[][] board){
       return x>=0&&x<board.length&&y>=0&&y<board[0].length&&board[x][y]==1;
    }

    public void rotate(int[][] matrix) {
        int lengthX = matrix.length;
        int lengthY = matrix[0].length;

        int[][] re = new int[lengthY][lengthX];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int currentNum =matrix[i][j];
                re[lengthY-1-j][i] = currentNum;
            }
        }

    }

    public void reverseWords(String s) {
        s = "a good   example";
        String[] strs = s.split(" ");
        System.out.println(JSON.toJSONString(strs));
    }

}
