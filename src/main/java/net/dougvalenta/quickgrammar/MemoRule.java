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
class MemoRule extends Rule {

	private final Rule rule;
	private final String memo;
	
	MemoRule(final Rule rule, final String memo) {
		this.rule = rule;
		this.memo = memo;
	}
	
	@Override
	public void evaluate(final RuleContext context, final Appendable appendable) throws IOException {
		final String result = rule.evaluate(context).toString();
		context.putRule(memo, new TextRule(result));
		appendable.append(result);
	}
	
}
