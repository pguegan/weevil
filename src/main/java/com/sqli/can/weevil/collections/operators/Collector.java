package com.sqli.can.weevil.collections.operators;

public interface Collector<E, F> {

	F process(E e);

}
