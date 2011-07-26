package com.sqli.can.weevil.scalars;

public interface Incrementable {

	Incrementable increment();

	Incrementable increment(int value);

	Incrementable decrement();

	Incrementable decrement(int value);

}
