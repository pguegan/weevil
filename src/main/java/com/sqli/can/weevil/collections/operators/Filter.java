package com.sqli.can.weevil.collections.operators;

public interface Filter<E> {

	boolean include(E element);

}