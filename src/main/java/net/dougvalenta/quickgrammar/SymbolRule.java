/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

import java.io.IOException;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
class SymbolRule extends Rule {

	private final String symbol;
	
	SymbolRule(final String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public void evaluate(final RuleContext context, final Appendable appendable) throws IOException {
		context.getRule(symbol).evaluate(context, appendable);
	} 
	
}
