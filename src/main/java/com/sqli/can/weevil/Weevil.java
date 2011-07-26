package com.sqli.can.weevil;

import com.sqli.can.weevil.text.Chain;

public abstract class Weevil {

	public Chain toChain() {
		return new Chain(toString());
	}

}
