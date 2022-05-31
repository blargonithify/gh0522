import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Month;
//import java.util.Date;
import java.time.LocalDate;
//import java.util.Calendar;


public class Main
{
    public static void main(String[] args) throws DiscountException, RentalDayException
    {

        RentalAgreement ra=Checkout(Tool.ToolCode.LADW,10,99,LocalDate.now());
        ra.print();
    }
    public static RentalAgreement Checkout(Tool.ToolCode toolCode, int rentalDays, int discount, LocalDate date) throws RentalDayException, DiscountException
    {
        Tool tool=new Tool(toolCode);//generates tool from toolcode
        RentalAgreement rentalAgreement= new RentalAgreement(tool);//make new rental agreement, assign tool
        //check for exceptions
        if(!(rentalDays>=1))throw new RentalDayException("Rental days cannot be less than 1");
        if(discount<0||discount>100)throw new DiscountException("Discount must be within range 0-100");
        //assign discount
        rentalAgreement.discountPercent=discount;
        //calculate rental period
        rentalAgreement.rentalDays=rentalDays;
        rentalAgreement.checkoutDate=date;
        rentalAgreement.dueDate=rentalAgreement.checkoutDate.plusDays(rentalDays);
        //count chargable days
        LocalDate chargeDayCalendar=date;
        int chargeDays=0;//count of charge days
        int rentalDay=1;


        do//iterate thru the calendar one day at a time, adding to the count of charge days
        {
            //independence day
            if(chargeDayCalendar.getMonth()==Month.JULY&&chargeDayCalendar.getDayOfMonth()==4)
            {
                //indepenedence day charges
                //landed on saturday
                if(chargeDayCalendar.getDayOfWeek()==DayOfWeek.SATURDAY)
                {
                    //uncount previous day
                    if(!tool.holidayCharge)chargeDays--;
                    //count for today
                    if(tool.weekendCharge)chargeDays++;
                }
                //landed on sunday
                if(chargeDayCalendar.getDayOfWeek()==DayOfWeek.SUNDAY)
                {
                    //count todays weekend charge
                    if(tool.weekendCharge)chargeDays++;
                    //count tomorrows charge as holiday, then skip tomorrow
                    if(tool.holidayCharge)chargeDays++;
                    chargeDayCalendar=chargeDayCalendar.plusDays(1);
                }
            }
            else if(chargeDayCalendar.getMonth()==Month.SEPTEMBER&&
                    chargeDayCalendar.getDayOfWeek()==DayOfWeek.MONDAY&&
                    chargeDayCalendar.getDayOfMonth()<7)//labor day
            {
                //labor day charges according to tool
                if(tool.holidayCharge)chargeDays++;
            }
            else//not holiday
            {
                if(chargeDayCalendar.getDayOfWeek()==DayOfWeek.SATURDAY||
                        chargeDayCalendar.getDayOfWeek()==DayOfWeek.SUNDAY)//weekend
                {
                    //weekend charges according to tool
                    if(tool.weekendCharge)chargeDays++;
                }
                else chargeDays++;//not weekend, weekday non holiday, all tools get charged
            }
            System.out.println("Charging for day "+rentalDay+" of rental agreement, chargedays="+chargeDays+" today is "+chargeDayCalendar.getDayOfWeek().name()+" the "+chargeDayCalendar.getDayOfMonth()+" of "+ chargeDayCalendar.getMonth().name()+" "+chargeDayCalendar.getYear());
            rentalDay++;
            chargeDayCalendar=chargeDayCalendar.plusDays(1);

        }while (chargeDayCalendar.isBefore(rentalAgreement.dueDate));


        //assign charge days
        rentalAgreement.chargeDays=chargeDays;
        //Calculate pre-discount charge
        rentalAgreement.preDiscountCharge=tool.dailyCharge.multiply(BigDecimal.valueOf(chargeDays));
        rentalAgreement.preDiscountCharge.setScale(2, RoundingMode.HALF_UP);
        //Calculuate discount amount
        //rentalAgreement.discountAmount=rentalAgreement.preDiscountCharge.multiply(BigDecimal.valueOf((long)rentalAgreement.discountPercent/100));
        double preDiscountCharge=rentalAgreement.preDiscountCharge.doubleValue();
        double discountPercent=((double)rentalAgreement.discountPercent)/100;
        double discountAmount=preDiscountCharge*discountPercent;
        rentalAgreement.discountAmount=BigDecimal.valueOf(discountAmount);
        rentalAgreement.discountAmount.setScale(2,RoundingMode.HALF_UP);
        //Calculate final charge
        rentalAgreement.finalCharge=rentalAgreement.preDiscountCharge.subtract(rentalAgreement.discountAmount);
        rentalAgreement.finalCharge.setScale(2,RoundingMode.HALF_UP);
        return rentalAgreement;
    }

}
