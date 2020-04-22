package me.jinwenjie.util;

import org.junit.Assert;
import org.junit.Test;

public class JwtUtilTest {

    final Integer UID = (int) (Math.random() * 10000);

    @Test
    public void sign() {
        System.out.println(JwtUtil.sign(UID,true));
    }

    @Test
    public void testSign() {
        System.out.println(JwtUtil.sign(UID, true));
    }

    @Test
    public void testSign1() {
        System.out.println(JwtUtil.sign(UID, true, 5000));
    }

    @Test
    public void verify() {
        String token = JwtUtil.sign(UID);
        Assert.assertTrue(JwtUtil.verify(token));
        token = "ajidwadmaskcl";
        Assert.assertFalse(JwtUtil.verify(token));
    }

    @Test
    public void expire() {
        String token = JwtUtil.sign(UID, false, 100);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(JwtUtil.verify(token));
    }

    @Test
    public void getUserId() {
        String token = JwtUtil.sign(UID);
        Assert.assertEquals(JwtUtil.getUserId(token), UID);
    }

    @Test
    public void checkAdmin() {
        String token = JwtUtil.sign(UID, false);
        Assert.assertEquals(JwtUtil.checkAdmin(token), false);
        token = JwtUtil.sign(UID, true);
        Assert.assertEquals(JwtUtil.checkAdmin(token), true);
    }
}