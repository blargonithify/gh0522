import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement
{
    public RentalAgreement(Tool t)
    {
        tool=t;
    }
    public Tool tool;
    public Tool.ToolCode getToolCode()
    {
        return tool.code;
    }
    public Tool.ToolType getToolType()
    {
        return tool.toolType;
    }
    public Tool.Brand getToolBrand()
    {
        return tool.brand;
    }
    public int rentalDays;
    public LocalDate checkoutDate;
    public LocalDate dueDate;
    public BigDecimal getDailyRentalCharge()
    {return tool.dailyCharge;}
    public int chargeDays;
    public BigDecimal preDiscountCharge;
    public int discountPercent;
    public BigDecimal discountAmount;
    public BigDecimal finalCharge;
    public void print()
    {
        System.out.println("Tool code: "+tool.code.toString());
        System.out.println("Tool type: "+tool.toolType.toString());
        System.out.println("Tool brand: "+tool.brand.toString());
        System.out.println("Rental days: "+rentalDays);
        System.out.println("Checkout date: "+checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Due date: "+dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Daily rental charge: $"+getDailyRentalCharge());
        System.out.println("Charge days: "+chargeDays);
        System.out.println("Pre-discount charge: $"+preDiscountCharge);
        System.out.println("Discount percent: "+discountPercent+"%");
        DecimalFormat df= new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("Discount amount: $"+df.format(discountAmount.doubleValue()));
        System.out.println("Final charge: $"+df.format(finalCharge.doubleValue()));
    }
}
