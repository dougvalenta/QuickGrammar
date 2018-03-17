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
class SelectionRule extends Rule {

	private final Rule[] rules;
	
	SelectionRule(final Rule[] rules) {
		this.rules = rules;
	}
	
	@Override
	public void evaluate(RuleContext context, Appendable appendable) throws IOException {
		rules[context.select(rules.length)].evaluate(context, appendable);
	}
	
}
