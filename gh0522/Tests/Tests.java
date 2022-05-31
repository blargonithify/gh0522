import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
//import java.util.Calendar;
//import java.util.Date;
import java.time.Month;

public class Tests
{
    @Test
    public void test()
    {
        Assert.assertTrue(true);
    }
    @Test
    public void Test1() throws DiscountException, RentalDayException
    {
        Assert.assertThrows(DiscountException.class,()->{Main.Checkout(Tool.ToolCode.JAKR,5,101,LocalDate.of(2015, Month.SEPTEMBER,3));});
    }
    @Test
    public void Test2() throws DiscountException, RentalDayException
    {
        RentalAgreement ra=Main.Checkout(Tool.ToolCode.LADW,3,10, LocalDate.of(2020, Month.JULY,2));
        //Assert.assertEquals(ra.finalCharge, new BigDecimal(3.58));

        Assert.assertEquals("3.58",ra.finalCharge.toString().substring(0,ra.finalCharge.toString().indexOf('.')+3));
    }
    @Test
    public void Test3() throws DiscountException, RentalDayException
    {
        RentalAgreement ra=Main.Checkout(Tool.ToolCode.CHNS,5,25, LocalDate.of(2015, Month.JULY,2));
        //Assert.assertEquals(ra.finalCharge, new BigDecimal(3.35));
        DecimalFormat df= new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        Assert.assertEquals("3.35",df.format(ra.finalCharge.doubleValue()));
    }
    @Test
    public void Test4() throws DiscountException, RentalDayException
    {
        RentalAgreement ra=Main.Checkout(Tool.ToolCode.JAKD,6,0,LocalDate.of(2015, Month.SEPTEMBER,3));
        Assert.assertEquals("11.96",ra.finalCharge.toString());
    }
    @Test
    public void Test5() throws DiscountException, RentalDayException
    {
        RentalAgreement ra=Main.Checkout(Tool.ToolCode.JAKR,9,0, LocalDate.of(2015, Month.JULY,2));
        //Assert.assertEquals(ra.finalCharge,new BigDecimal(17.94));
        Assert.assertEquals("17.94",ra.finalCharge.toString());
    }
    @Test
    public void Test6() throws DiscountException, RentalDayException
    {
        RentalAgreement ra=Main.Checkout(Tool.ToolCode.JAKR,4,50, LocalDate.of(2020, Month.SEPTEMBER,2));
        //Assert.assertTrue(ra.finalCharge.equals(new BigDecimal(4.49)));
        DecimalFormat df= new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        Assert.assertEquals("4.49",df.format(ra.finalCharge.doubleValue()));
    }
}
