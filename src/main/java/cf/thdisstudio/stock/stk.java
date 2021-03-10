package cf.thdisstudio.stock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class stk extends Thread{
    public int thdisstudio = 20;
    public int minecraft = 20;
    public int yalmefarm = 20;
    public DateTimeFormatter myFormatObj;
    public String formattedDate;
    public void run() {
        try{
            while (true) {
                Random ran = new Random();
                /*스디스 스튜디오*/
                if(ran.nextInt(2)==1 || thdisstudio < 150) {
                    thdisstudio += ran.nextInt(100);
                }else {
                    thdisstudio -= ran.nextInt(100);
                }
                /*마인크래프트*/
                if(ran.nextInt(2)==1 || thdisstudio < 150) {
                    minecraft += ran.nextInt(100);
                }else {
                    minecraft -= ran.nextInt(100);
                }
                /*열매 농장*/
                if(ran.nextInt(2)==1 || thdisstudio < 150) {
                    yalmefarm += ran.nextInt(100);
                }else {
                    yalmefarm -= ran.nextInt(100);
                }
                LocalDateTime myDateObj = LocalDateTime.now();
                myFormatObj = DateTimeFormatter.ofPattern("MM월dd일, HH시mm분ss초애 변경됨");
                formattedDate = myDateObj.format(myFormatObj);
                Thread.sleep(300000);
            }
        }catch(Exception e){
        }
    }
}
