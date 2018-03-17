/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */

package net.dougvalenta.quickgrammar;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Doug Valenta
 * @since 3/17/2018
 */
public class DefaultRuleCompiler implements RuleCompiler {

	private static final char DEFAULT_BEGIN_SYMBOL_CHARACTER = '[';
	private static final char DEFAULT_END_SYMBOL_CHARACTER = ']';
	private static final char DEFAULT_BEGIN_OPTION_CHARACTER = '(';
	private static final char DEFAULT_END_OPTION_CHARACTER = ')';
	private static final char DEFAULT_DIVIDE_SELECTION_CHARACTER = '|';
	private static final char DEFAULT_BEGIN_MEMO_CHARACTER = '<';
	private static final char DEFAULT_END_MEMO_CHARACTER = '>';
	private static final char DEFAULT_DIVIDE_MEMO_CHARACTER = '$';
	private static final char DEFAULT_ESCAPE_CHARACTER = '\\';
	
	private final char beginSymbolCharacter;
	private final char endSymbolCharacter;
	private final char beginOptionCharacter;
	private final char endOptionCharacter;
	private final char divideSelectionCharacter;
	private final char beginMemoCharacter;
	private final char endMemoCharacter;
	private final char divideMemoCharacter;
	private final char escapeCharacter;
	
	public DefaultRuleCompiler(
		final char beginSymbolCharacter,
		final char endSymbolCharacter,
		final char beginOptionCharacter,
		final char endOptionCharacter,
		final char divideSelectionCharacter,
		final char beginMemoCharacter,
		final char endMemoCharacter,
		final char divideMemoCharacter,
		final char escapeCharacter
	) {
		this.beginSymbolCharacter = beginSymbolCharacter;
		this.endSymbolCharacter = endSymbolCharacter;
		this.beginOptionCharacter = beginOptionCharacter;
		this.endOptionCharacter = endOptionCharacter;
		this.divideSelectionCharacter = divideSelectionCharacter;
		this.beginMemoCharacter = beginMemoCharacter;
		this.endMemoCharacter = endMemoCharacter;
		this.divideMemoCharacter = divideMemoCharacter;
		this.escapeCharacter = escapeCharacter;
	}
	
	public DefaultRuleCompiler() {
		this(DEFAULT_BEGIN_SYMBOL_CHARACTER,
			DEFAULT_END_SYMBOL_CHARACTER,
			DEFAULT_BEGIN_OPTION_CHARACTER,
			DEFAULT_END_OPTION_CHARACTER,
			DEFAULT_DIVIDE_SELECTION_CHARACTER,
			DEFAULT_BEGIN_MEMO_CHARACTER,
			DEFAULT_END_MEMO_CHARACTER,
			DEFAULT_DIVIDE_MEMO_CHARACTER,
			DEFAULT_ESCAPE_CHARACTER);
	}
	
	private final StringBuilder builder = new StringBuilder();
	
	@Override
	public Rule compile(final String ruletext) {
		return compile(new CharIterator(ruletext));
	}
	
	private Rule compile(final CharIterator iterator) {
		boolean escape = false;
		boolean symbol = false;
		builder.setLength(0);
		final List<Rule> rules = new LinkedList<>();
		List<Rule> selections = null;
		while (iterator.hasNext()) {
			final char character = iterator.next();
			if (escape) {
				if (character == 'n') builder.append('\n');
				else builder.append(character);
				escape = false;
			} else {
				if (character == escapeCharacter) {
					escape = true;
				} else if (character == beginSymbolCharacter && !symbol) {
					flush(rules);
					symbol = true;
				} else if (character == endSymbolCharacter && symbol) {
					flushSymbol(rules);
					symbol = false;
				} else if (character == beginOptionCharacter || character == beginMemoCharacter) {
					flush(rules);
					rules.add(compile(iterator));
				} else if (character == divideSelectionCharacter || character == divideMemoCharacter) {
					flush(rules);
					if (selections == null) {
						selections = new LinkedList<Rule>();
					}
					selections.add(buildRule(rules));
				} else if (character == endOptionCharacter) {
					flush(rules);
					if (selections == null) {
						if (rules.isEmpty()) {
							return Rule.empty();
						} else {
							return Rules.optional(buildRule(rules));
						}
					} else {
						selections.add(buildRule(rules));
						return Rules.selection(selections);
					}
				} else if (character == endMemoCharacter) {
					if (builder.length() > 0 && !selections.isEmpty()) {
						rules.add(Rules.memo(selections.get(0), builder.toString()));
						builder.setLength(0);
						selections.clear();
					}
				} else {
					builder.append(character);
				}
			}
		}
		flush(rules);
		return buildRule(rules);
	}
	
	private void flush(List<Rule> rules) {
		if (builder.length() > 0) {
			rules.add(Rules.text(builder.toString()));
			builder.setLength(0);
		}
	}
	
	private void flushSymbol(List<Rule> rules) {
		if (builder.length() > 0) {
			rules.add(Rules.symbol(builder.toString()));
			builder.setLength(0);
		}
	}
	
	private Rule buildRule(List<Rule> rules) {
		Rule rule;
		if (rules.isEmpty()) {
			rule = Rule.empty();
		} else if (rules.size() == 1) {
			rule = rules.get(0);
		} else {
			rule = Rules.compound(rules);
		}
		rules.clear();
		return rule;
	}
	
	private class CharIterator {
		
		private final CharSequence sequence;
		
		private int index;
		
		private CharIterator(final CharSequence sequence) {
			this.sequence = sequence;
		}
		
		private boolean hasNext() {
			return index < sequence.length();
		}
		
		private char next() {
			return sequence.charAt(index++);
		}
		
	}

}
