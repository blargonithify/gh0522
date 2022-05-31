import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tool
{
    public Tool(ToolCode toolCode)
    {
        code = toolCode;
        generateToolType();
        generateBrand();
        setDailyCharge();
    }
    public void setDailyCharge()
    {
        switch (code)
        {
            case LADW -> dailyCharge=new BigDecimal("1.99");
            case CHNS -> dailyCharge=new BigDecimal("1.49");
            case JAKD, JAKR -> dailyCharge= new BigDecimal("2.99");
        }
        switch (code)
        {
            case LADW: weekdayCharge=true;weekendCharge=true;holidayCharge=false;break;
            case CHNS: weekdayCharge=true;weekendCharge=false;holidayCharge=true;break;
            case JAKD:
            case JAKR:
                weekdayCharge=true;weekendCharge=false;holidayCharge=false;break;

        }
        dailyCharge.setScale(2, RoundingMode.HALF_UP);
    }
    public void generateBrand()
    {
        switch (code)
        {
            case CHNS -> brand=Brand.Stihl;
            case LADW -> brand=Brand.Werner;
            case JAKD -> brand=Brand.DeWalt;
            case JAKR -> brand=Brand.Rigid;
        }
    }
    public void generateToolType()
    {
        switch (code)
        {
            case CHNS -> toolType=ToolType.Chainsaw;
            case LADW -> toolType=ToolType.Ladder;
            case JAKD, JAKR -> toolType=ToolType.Jackhammer;
        }
    }

    public enum ToolCode
    {
        CHNS,
        LADW,
        JAKD,
        JAKR
    }
    public ToolCode code;
    public enum  ToolType
    {
        Chainsaw,
        Ladder,
        Jackhammer
    }
    public ToolType toolType;
    public enum Brand
    {
        Stihl,
        Werner,
        DeWalt,
        Rigid
    }
    public Brand brand;
    public BigDecimal dailyCharge;
    public boolean weekdayCharge;
    public boolean weekendCharge;
    public boolean holidayCharge;
}
