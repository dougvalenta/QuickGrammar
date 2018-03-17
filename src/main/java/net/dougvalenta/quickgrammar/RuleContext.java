/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
public interface RuleContext extends Grammar {

	public boolean optional();
	
	public int select(int count);
	
}
