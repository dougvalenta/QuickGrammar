/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
public class DefaultGrammar implements Grammar {

	protected final Map<String, Rule> rules = new HashMap<>();
	
	@Override
	public Rule getRule(final String name) {
		Rule rule = rules.get(name);
		if (rule != null) {
			return rule;
		}
		else
		{
			return Rule.empty();
		}
	}

	@Override
	public void putRule(final String name, final Rule rule) {
		rules.put(name, rule);
	}
	
	public void read(final Reader reader, final RuleCompiler compiler) throws IOException {
		final BufferedReader bufferedReader;
		if (reader instanceof BufferedReader) {
			bufferedReader = (BufferedReader) reader;
		} else {
			bufferedReader = new BufferedReader(reader);
		}
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			final int equalsIndex = line.indexOf('=');
			if (equalsIndex == -1) continue;
			final String name = line.substring(0, equalsIndex).trim();
			if (name.isEmpty()) continue;
			final String ruletext = line.substring(equalsIndex + 1);
			putRule(name, compiler.compile(ruletext));
		}
	}
	
}
