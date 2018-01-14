package com.szhis.frsoft.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.szhis.frsoft.common.FRUtils;
import com.szhis.frsoft.common.exception.ThrowException;

public class BigDecimalUtils {
	
	public static final MathContext mathContext1 = new MathContext(1, RoundingMode.HALF_UP);
	public static final MathContext mathContext2 = new MathContext(2, RoundingMode.HALF_UP);
    public static final MathContext mathContext4 = new MathContext(4, RoundingMode.HALF_UP);   
    public static final MathContext mathContext6 = new MathContext(6, RoundingMode.HALF_UP);
    
	public final static Boolean isZero(BigDecimal a) {
		if (a == null) {
			return false;
		}	
		return a.compareTo(BigDecimal.ZERO) == 0;
	}
	
	public final static Boolean greaterThan(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) == 1;
	}
	
	public final static Boolean isPositive(BigDecimal a) {
		if (a == null) {
			return false;
		}
		return a.compareTo(BigDecimal.ZERO) == 1;
	}
	
	public final static Boolean isNegative(BigDecimal a) {
		if (a == null) {
			return false;
		}	
		return a.compareTo(BigDecimal.ZERO) == -1;
	}
	
	public final static Boolean isNullOrNegative(BigDecimal a) {
		if (a == null) {
			return true;
		}
		return a.compareTo(BigDecimal.ZERO) == -1;
	}
	
	public final static Boolean isNullOrZero(BigDecimal a) {
		if (a == null) {
			return true;
		}	
		return a.compareTo(BigDecimal.ZERO) == 0;
	}	
	
	public final static BigDecimal multiply(BigDecimal a, BigDecimal b, int... scale) {
		return a.multiply(b).setScale((scale.length == 0) ? 4 : scale[0], RoundingMode.HALF_UP);
	}
	
	public final static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
		return a.divide(b, scale, RoundingMode.HALF_UP);
	}	
	
	
	public final static Boolean greaterThanOrEqual(BigDecimal a, BigDecimal b) {
		a = a == null ? BigDecimal.ZERO : a;
		b = b == null ? BigDecimal.ZERO : b;		
		return a.compareTo(b) != -1;
	}

	/**
	 * BigDecimal比较相等不能用equals方法
	 * @param a
	 * @param b
	 * @return
	 */
	public final static Boolean equal(BigDecimal a, BigDecimal b) {
		a = a == null ? BigDecimal.ZERO : a;
		b = b == null ? BigDecimal.ZERO : b;		
		return a.compareTo(b) == 0;
	}	
	
	public final static Boolean lessThan(BigDecimal a, BigDecimal b) {
		a = a == null ? BigDecimal.ZERO : a;
		b = b == null ? BigDecimal.ZERO : b;
		return a.compareTo(b) == -1;
	}
	
	public final static Boolean lessThanOrEqual(BigDecimal a, BigDecimal b) {
		a = a == null ? BigDecimal.ZERO : a;
		b = b == null ? BigDecimal.ZERO : b;		
		return a.compareTo(b) != 1;
	}	
	
	public final static BigDecimal valueOf(BigDecimal value){
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		return value;
	}
	
	/**
	 * 金额=4788.00,单价=16.0134，数量=299， 单价*数量=4788.0066=4788.01不相等，差1分。
	 * 金额=1049.00,单价=3.1502,数量=333, 单价*数量=1049.0166=1049.02，差2分。
	 * 部分业务场景根据单价乘以数量来算金额，也有根据金额除以数量来算单价的情况，所以
	 * 这里两种方式都校验，只要满足其一，就认为等式满足。
	 * @param priceTitle
	 * @param price
	 * @param quantityTitle
	 * @param quantity
	 * @param totalTitle
	 * @param total
	 * @param scale
	 */	
	public final static void validatePriceQuantityTotal(String priceTitle, BigDecimal price, String quantityTitle, 
			BigDecimal quantity, String totalTitle, BigDecimal total, int... scale) {
		int aScale = (scale.length == 0) ? 2 : scale[0];
		BigDecimal fee = BigDecimalUtils.multiply(price, quantity, aScale);
		if (!BigDecimalUtils.equal(fee, total)) {
			BigDecimal price2 = total.divide(quantity, price.scale(), RoundingMode.HALF_UP);
			if (price2.compareTo(price) != 0) {
				ThrowException.runtime("priceMultiQuantity.NotEqual.Total", 
						FRUtils.getMessage(priceTitle), price, 
						FRUtils.getMessage(quantityTitle), quantity, 
						FRUtils.getMessage(totalTitle), total);					
			}
		}
	}	
}
