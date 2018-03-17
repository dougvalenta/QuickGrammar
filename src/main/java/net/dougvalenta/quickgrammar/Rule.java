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
public abstract class Rule {
	
	private static final Rule EMPTY = new Rule() {
		
		@Override
		public void evaluate(RuleContext context, Appendable appendable) {
			// NOP
		}
		
	};
	
	public static Rule empty() {
		return EMPTY;
	}

	public CharSequence evaluate(RuleContext context) {
		final StringBuilder builder = new StringBuilder();
		try {
			evaluate(context, builder);
		} catch (IOException e) {
			// Unreachable
			throw new IllegalStateException("Unexpected exception", e);
		}
		return builder;
	}
	
	public abstract void evaluate(RuleContext context, Appendable appendable) throws IOException;
	
}
