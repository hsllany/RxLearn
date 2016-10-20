package com.hsllany.rxlearn;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class Main {

    public static String[] sTestStr;

    static {
        sTestStr = new String[20];
        for (int i = 0; i < sTestStr.length; i++) {
            sTestStr[i] = (i + 1) + "";
        }
    }

    public static void main(String... args){
        helloworld();
    }

    @Test
    public static void helloworld() {
        Observable.from(sTestStr).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("done");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println(Thread.currentThread() + s);

            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.println("lalala" + i + Thread.currentThread());
        }

    }

    @Test
    public static void createSynchronous(String... msgs) {
        Observable<String> obj = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                for (int i = 0; i < msgs.length; i++) {
                    subscriber.onNext(msgs[i]);
                }
                subscriber.onCompleted();
            }
        });

        obj.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println(Thread.currentThread() + "onComplete");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });


    }

    @Test
    public static void createAsynchronous(String... msgs) {
        Observable<String> obj = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                new Thread(() -> {
                    subscriber.onStart();
                    for (int i = 0; i < msgs.length; i++) {
                        subscriber.onNext(msgs[i]);
                    }
                    subscriber.onCompleted();
                }).start();
            }
        });


        obj.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println(Thread.currentThread() + " onComplete");

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(Thread.currentThread() + s);
            }
        });
    }

    @Test
    public static void chainOperators(String... msg) {
        Observable observable = Observable.from(msg).skip(2).take(5).map(s -> {
            return s + "_lala";
        });
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onComplete");
            }

            @Override
            public void onError(Throwable throwable) {
                log(throwable.toString());
            }

            @Override
            public void onNext(String o) {
                log(o);
            }
        });
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
