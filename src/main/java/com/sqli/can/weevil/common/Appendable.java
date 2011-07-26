package com.sqli.can.weevil.common;

public interface Appendable extends java.lang.Appendable {

	Appendable append(Object o);

	Appendable pad(Object o, int length);

	Appendable prepend(Object o);

	Appendable prepad(Object o, int length);

}
