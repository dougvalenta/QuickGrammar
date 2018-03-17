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
class TextRule extends Rule {

	private final String text;
	
	TextRule(final String text) {
		this.text = text;
	}
	
	@Override
	public void evaluate(final RuleContext context, final Appendable appendable) throws IOException {
		appendable.append(text);
	}
	
}
