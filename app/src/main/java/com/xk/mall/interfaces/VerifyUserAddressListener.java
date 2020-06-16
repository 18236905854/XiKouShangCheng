package com.xk.mall.interfaces;

/**
 * @ClassName: VerifyUserAddressListener
 * @Description: 检查收货地址是否超出范围
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface VerifyUserAddressListener {
    void verifyAddress(String address);
}
