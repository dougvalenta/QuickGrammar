/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
public class GrammarContext implements RuleContext {

	protected final Grammar grammar;
	protected final Random random;
	protected final Map<String, Rule> memos = new HashMap<>();
	
	public GrammarContext(final Grammar grammar, final Random random) {
		this.grammar = grammar;
		this.random = random;
	}
	
	@Override
	public Rule getRule(final String name) {
		Rule rule = memos.get(name);
		if (rule != null) {
			return rule;
		} else {
			return grammar.getRule(name);
		}
	}
	
	@Override
	public void putRule(final String name, final Rule rule) {
		memos.put(name, rule);
	}

	@Override
	public boolean optional() {
		return random.nextBoolean();
	}

	@Override
	public int select(int count) {
		return random.nextInt(count);
	}
	
}
