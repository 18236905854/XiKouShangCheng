package com.xk.mall.utils;

import java.math.BigDecimal;

/**
 * @author yyt
 * @package com.xikou.common.utils
 * @className CalculateUtils
 * @description 计算工具类
 * @date 2019/6/11 10:57
 **/
public class CalculateUtils {
	/**
	 * Double类型数据相加
	 *
	 * @author yyt
	 * @param a 相加数
	 * @param b 相加数
	 * @return java.lang.Double
	 * @date 2019/3/26 10:25
	 */
	public static Double addDouble(Double a, Double b) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a + b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相加
	 *
	 * @author yyt
	 * @param a 相加数
	 * @param b 相加数
	 * @param scale 小数位数
	 * @return java.lang.Double
	 * @date 2019/3/26 10:25
	 */
	public static Double addDouble(Double a, Double b, Integer scale) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a + b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相减
	 *
	 * @author yyt
	 * @param a 减数
	 * @param b 被减数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:03
	 */
	public static Double divideDouble(Double a, Double b) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a - b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相减
	 *
	 * @author yyt
	 * @param a 减数
	 * @param b 被减数
	 * @param scale 小数位数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:03
	 */
	public static Double divideDouble(Double a, Double b, Integer scale) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a - b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相乘
	 *
	 * @author yyt
	 * @param a 乘数
	 * @param b 被乘数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:02
	 */
	public static Double multiplyDouble(Double a, Double b) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a * b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相乘,取小數點后兩位，小數點第三位四捨五入
	 *
	 * @author yyt
	 * @param a 乘数
	 * @param b 被乘数
	 * @return java.lang.Double
	 * @date 2020/1/10 11:02
	 */
	public static Double multiplyHalfUpDouble(Double a, Double b) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a * b;
			result=(double) Math.round(result*100) / 100;
		}
		return result;
	}

	/**
	 * Double类型数据相乘
	 *
	 * @author yyt
	 * @param a 乘数
	 * @param b 被乘数
	 * @param scale 小数位数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:02
	 */
	public static Double multiplyDouble(Double a, Double b, Integer scale) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a * b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型与Integer类型数据相乘
	 *
	 * @author yyt
	 * @param a 乘数
	 * @param b 被乘数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:02
	 */
	public static Double multiplyDoubleAndInt(Double a, Integer b) {
		Double result = 0d;
		if (null != a && null != b) {
			result = a * b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相除
	 *
	 * @author yyt
	 * @param a 除数
	 * @param b 被除数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:01
	 */
	public static Double subtractDouble(Double a, Double b) {
		Double result = 0d;
		if (null != a && null != b && 0 != b) {
			result = a / b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Double类型数据相除
	 *
	 * @author yyt
	 * @param a 除数
	 * @param b 被除数
	 * @param scale 小数位数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:01
	 */
	public static Double subtractDouble(Double a, Double b, Integer scale) {
		Double result = 0d;
		if (null != a && null != b && 0 != b) {
			result = a / b;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * Integer 类型数据相除
	 *
	 * @author yyt
	 * @param a 除数
	 * @param b 被除数
	 * @param scale 小数位数
	 * @return java.lang.Double
	 * @date 2019/6/11 11:01
	 */
	public static Double subtractInteger(Integer a, Integer b, Integer scale) {
		Double result = 0d;
		if (null != a && null != b && 0 != b) {
			result = new BigDecimal((float) a / b).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * 价格除100操作
	 */
	public static Double subDoubleHundred(Integer a){
		Double result = 0d;
		if (null != a ) {
			result = a / 100.0;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

}
