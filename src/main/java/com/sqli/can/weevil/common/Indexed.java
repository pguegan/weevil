package com.sqli.can.weevil.common;

public interface Indexed<E> {

	Indexed<E> add(int index, E element);

	E remove(int index);

	E get(int index);

	Indexed<E> set(int index, E element);

	Indexed<E> sub(int from, int to);

	Indexed<E> reverse();

	Indexed<E> shuffle();

	E first();

	E last();

}
