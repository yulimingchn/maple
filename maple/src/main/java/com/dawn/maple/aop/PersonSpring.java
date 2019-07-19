package com.dawn.maple.aop;

/**
 * @author yuliming
 */
public class PersonSpring implements Speakable {

    @Override
    public void sayHi() {
        try {
            Thread.sleep(30);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Hi!");
    }

    @Override
    public void sayBye() {
        try {
            Thread.sleep(10);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Bye!!");
    }
}
