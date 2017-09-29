package com.wyj.springboot.im.tools;


import java.math.BigDecimal;
import java.text.NumberFormat;


public class NumUtil {
    /**
     * 转换为BigDecimal
     */
    public static BigDecimal toBig(Object o) {
        if (o == null || o.toString().equals("") || o.toString().equals("NaN")) {
            return new BigDecimal(0);
        }
        return new BigDecimal(o.toString());
    }
    
    /**
     * 计算百分比
     */
    public static String getPercent(Object divisor, Object dividend){
        if(divisor == null || dividend == null){
            return "";
        }
        NumberFormat percent = NumberFormat.getPercentInstance();   
        //建立百分比格式化引用   
        percent.setMaximumFractionDigits(2);
        BigDecimal a = toBig(divisor);
        BigDecimal b = toBig(dividend);
        if(a.equals(toBig(0)) || b.equals(toBig(0)) || a.equals(toBig(0.0)) || b.equals(toBig(0.0))){
       	 return "0.00%";
        }
        BigDecimal c = a.divide(b, 4, BigDecimal.ROUND_DOWN);
        return percent.format(c);
    }
    
    /**
     * 计算比例
     */
    public static String divideNumber(Object divisor, Object dividend){
    	if(divisor == null || dividend == null){
            return "";
        }
    	 BigDecimal a = toBig(divisor);
         BigDecimal b = toBig(dividend);
         if(a.equals(toBig(0)) || b.equals(toBig(0))){
        	 return "0";
         }
         BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_DOWN);
         return c.toString();
    }
    
    /**
     * 去两个数的平均值，四舍五入
     */
    public static int averageNumber(Object divisor, Object dividend){
    	if(divisor == null || dividend == null){
            return 0;
        }
    	BigDecimal a = toBig(divisor);
        BigDecimal b = toBig(dividend);
        if(a.equals(toBig(0)) || b.equals(toBig(0))){
       	 	return 0;
        }
        BigDecimal c = a.divide(b, 0, BigDecimal.ROUND_HALF_UP);
        return c.intValue();
    }
}