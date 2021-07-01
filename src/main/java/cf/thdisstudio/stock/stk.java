package cf.thdisstudio.stock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class stk extends Thread{
    public int thdisstudio = 20;
    public int minecraft = 20;
    public int yalmefarm = 20;
    public int twit_musk = 20;
    public DateTimeFormatter myFormatObj;
    public String formattedDate;
    public void run() {
        try{
            while (true) {
                Random ran = new Random();
                /*스디스 스튜디오*/
                if(ran.nextInt(3)==1 || thdisstudio < 100) {
                    thdisstudio += ran.nextInt(100);
                }else {
                    thdisstudio -= ran.nextInt(100);
                }
                /*마인크래프트*/
                if(ran.nextInt(3)==1 || minecraft < 100) {
                    minecraft += ran.nextInt(100);
                }else {
                    minecraft -= ran.nextInt(100);
                }
                /*열매 농장*/
                if(ran.nextInt(3)==1 || minecraft < 100) {
                    yalmefarm += ran.nextInt(100);
                }else {
                    yalmefarm -= ran.nextInt(100);
                }
                /*트윗 머스크*/
                if(ran.nextInt(3)==1 || minecraft < 100) {
                    twit_musk += ran.nextInt(100);
                }else {
                    twit_musk -= ran.nextInt(100);
                }
                LocalDateTime myDateObj = LocalDateTime.now();
                myFormatObj = DateTimeFormatter.ofPattern("MM월dd일, HH시mm분ss초에 변경됨");
                formattedDate = myDateObj.format(myFormatObj);
                Thread.sleep(300000);
            }
        }catch(Exception e){
        }
    }
}
