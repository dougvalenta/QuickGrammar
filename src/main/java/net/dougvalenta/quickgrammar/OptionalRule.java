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
class OptionalRule extends Rule {

	private final Rule rule;
	
	OptionalRule(final Rule rule) {
		this.rule = rule;
	}
	
	@Override
	public void evaluate(final RuleContext context, final Appendable appendable) throws IOException {
		if (context.optional()) {
			rule.evaluate(context, appendable);
		}
	}
	
}
