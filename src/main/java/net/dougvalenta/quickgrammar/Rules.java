/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

import java.util.Collection;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
public class Rules {

	public static Rule empty() {
		return Rule.empty();
	}
	
	public static Rule text(final String text) {
		return new TextRule(text);
	}
	
	public static Rule optional(final Rule rule) {
		return new OptionalRule(rule);
	}
	
	public static Rule symbol(final String symbol) {
		return new SymbolRule(symbol);
	}
	
	public static Rule memo(final Rule rule, final String memo) {
		return new MemoRule(rule, memo);
	}
	
	public static Rule compound(final Rule... rules) {
		return new CompoundRule(rules);
	}
	
	public static Rule compound(final Collection<? extends Rule> rules) {
		Rule[] rulesArray = new Rule[rules.size()];
		rulesArray = rules.toArray(rulesArray);
		return new CompoundRule(rulesArray);
	}
	
	public static Rule selection(final Rule... rules) {
		return new SelectionRule(rules);
	}
	
	public static Rule selection(final Collection<? extends Rule> rules) {
		Rule[] rulesArray = new Rule[rules.size()];
		rulesArray = rules.toArray(rulesArray);
		return new SelectionRule(rulesArray);
	}
	
	private Rules() {}
	
}
