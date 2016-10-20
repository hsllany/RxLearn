package com.hsllany.rxlearn;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.Iterator;

/**
 * Created by YangTao on 2016/10/20.
 *
 * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Blocking-Observable-Operators">Blocking-Observable-Operators</a>
 */
public class Blocking {

    @Test
    public void forEach() {
        Observable.from(Main.sTestStr).toBlocking().forEach(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
                System.out.println(Thread.currentThread());
            }
        });
    }

    @Test
    public void single() {
        Observable.from(new String[]{"hello world"}).toBlocking().single();
    }

    @Test
    public void next() {
        Iterable<String> iterable = Observable.from(Main.sTestStr).toBlocking().next();
        Iterator<String> itr = iterable.iterator();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }


    }
}
