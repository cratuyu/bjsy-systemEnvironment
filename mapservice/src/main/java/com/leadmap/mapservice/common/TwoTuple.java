package com.leadmap.mapservice.common;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2019/1/3 15:27
 */
public class TwoTuple<A, B> {
    public final A first;
    private final B second;

    public TwoTuple(A a, B b){
        first = a;
        second = b;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond(){
        return second;
    }

    @Override
    public String toString(){
        return "(" + first + ", " + second + ")";
    }
}
