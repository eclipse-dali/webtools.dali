/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.nio.charset.Charset;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CharArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.junit.Assert;

@SuppressWarnings("nls")
public class CharArrayToolsTests
	extends AbstractStringToolsTests
{
	public CharArrayToolsTests(String name) {
		super(name);
	}

	@Override
	protected String CR() {
		return String.valueOf(CharArrayTools.CR);
	}

	@Override
	protected void verifyReverse(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.reverse(string.toCharArray()));
	}

	@Override
	protected void verifyLast(char expected, String string) {
		assertEquals(expected, CharArrayTools.last(string.toCharArray()));
	}

	@Override
	protected void verifyConcatenate(String expected, String[] array) {
		TestTools.assertEquals(expected, CharArrayTools.concatenate(this.convert(array)));
		Iterable<char[]> iterable = IterableTools.transform(IterableTools.iterable(array), StringTools.CHAR_ARRAY_TRANSFORMER);
		TestTools.assertEquals(expected, CharArrayTools.concatenate(iterable));
		TestTools.assertEquals(expected, CharArrayTools.concatenate(iterable.iterator()));
	}

	private char[][] convert(String[] stringArray) {
		char[][] array = new char[stringArray.length][];
		for (int i = 0; i < array.length; i++) {
			array[i] = stringArray[i].toCharArray();
		}
		return array;
	}

	@Override
	protected void verifyConcatenate(String expected, String[] array, String delim) {
		TestTools.assertEquals(expected, CharArrayTools.concatenate(this.convert(array), delim.toCharArray()));
		Iterable<char[]> iterable = IterableTools.transform(IterableTools.iterable(array), StringTools.CHAR_ARRAY_TRANSFORMER);
		TestTools.assertEquals(expected, CharArrayTools.concatenate(iterable, delim.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.concatenate(iterable.iterator(), delim.toCharArray()));
	}

	@Override
	protected void verifyCenter(String expected, String string, int len) {
		TestTools.assertEquals(expected, CharArrayTools.center(string.toCharArray(), len));
	}

	@Override
	protected void verifyPad(String expected, String string, int len) {
		TestTools.assertEquals(expected, CharArrayTools.pad(string.toCharArray(), len));
	}

	@Override
	protected void verifyFit(String expected, String string, int len) {
		TestTools.assertEquals(expected, CharArrayTools.fit(string.toCharArray(), len));
	}

	@Override
	protected void verifyZeroPad(String expected, String string, int len) {
		TestTools.assertEquals(expected, CharArrayTools.zeroPad(string.toCharArray(), len));
	}

	@Override
	protected void verifyZeroFit(String expected, String string, int len) {
		TestTools.assertEquals(expected, CharArrayTools.zeroFit(string.toCharArray(), len));
	}

	@Override
	protected void verifyRepeat(String expected, String string, int length) {
		TestTools.assertEquals(expected, CharArrayTools.repeat(string.toCharArray(), length));
	}

	@Override
	protected void verifySeparate(String expected, String string, char separator, int segmentLength) {
		TestTools.assertEquals(expected, CharArrayTools.separate(string.toCharArray(), separator, segmentLength));
	}

	@Override
	protected void verifyQuote(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.quote(string.toCharArray()));
	}

	@Override
	protected void verifyCharDelimiter(String expected, String string, char delimiter) {
		Transformer<char[], char[]> transformer = this.buildCharDelimiter(delimiter);
		TestTools.assertEquals(expected, transformer.transform(string.toCharArray()));
	}

	@Override
	protected Transformer<char[], char[]> buildCharDelimiter(char delimiter) {
		return new CharArrayTools.CharDelimiter(delimiter);
	}

	@Override
	protected void verifyDelimit(String expected, String string, String delimiter) {
		TestTools.assertEquals(expected, CharArrayTools.delimit(string.toCharArray(), delimiter.toCharArray()));
	}

	@Override
	protected void verifyStringDelimiter(String expected, String string, String delimiter) {
		Transformer<char[], char[]> transformer = this.buildStringDelimiter(delimiter);
		TestTools.assertEquals(expected, transformer.transform(string.toCharArray()));
	}

	@Override
	protected Transformer<char[], char[]> buildStringDelimiter(String delimiter) {
		return new CharArrayTools.CharArrayDelimiter(delimiter.toCharArray());
	}

	@Override
	protected void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) {
		TestTools.assertEquals(expected, CharArrayTools.removeFirstOccurrence(string.toCharArray(), charToRemove));
	}

	@Override
	protected void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) {
		TestTools.assertEquals(expected, CharArrayTools.removeAllOccurrences(string.toCharArray(), charToRemove));
	}

	@Override
	protected void verifyRemoveAllSpaces(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.removeAllSpaces(string.toCharArray()));
	}

	@Override
	protected void verifyRemoveAllWhitespace(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.removeAllWhitespace(string.toCharArray()));
	}

	@Override
	protected void verifyCompressWhitespace(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.compressWhitespace(string.toCharArray()));
	}

	@Override
	protected void verifyCommonPrefixLength(int expected, String string1, String string2) {
		assertEquals(expected, CharArrayTools.commonPrefixLength(string1.toCharArray(), string2.toCharArray()));
	}

	@Override
	protected void verifyCommonPrefixLengthMax(int expected, String string1, String string2, int max) {
		assertEquals(expected, CharArrayTools.commonPrefixLength(string1.toCharArray(), string2.toCharArray(), max));
	}

	@Override
	protected void verifyCapitalize(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.capitalize(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.CAPITALIZER.transform(string.toCharArray()));
	}

	@Override
	protected Object getCapitalizer() {
		return CharArrayTools.CAPITALIZER;
	}

	@Override
	protected void verifyUncapitalize(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.uncapitalize(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.UNCAPITALIZER.transform(string.toCharArray()));
	}

	@Override
	protected Object getUncapitalizer() {
		return CharArrayTools.UNCAPITALIZER;
	}

	@Override
	protected void verifyIsBlank(boolean expected, String string) {
		assertEquals(expected, CharArrayTools.isBlank(this.convert(string)));
		assertEquals(expected, CharArrayTools.IS_BLANK.evaluate(this.convert(string)));
	}

	@Override
	protected Object getIsBlankPredicate() {
		return CharArrayTools.IS_BLANK;
	}

	@Override
	protected void verifyIsNotBlank(boolean expected, String string) {
		assertEquals(expected, CharArrayTools.isNotBlank(this.convert(string)));
		assertEquals(expected, CharArrayTools.IS_NOT_BLANK.evaluate(this.convert(string)));
	}

	@Override
	protected Object getIsNotBlankPredicate() {
		return CharArrayTools.IS_NOT_BLANK;
	}

	@Override
	protected void verifyEqualsIgnoreCase(boolean expected, String string1, String string2) {
		assertEquals(expected, CharArrayTools.equalsIgnoreCase(this.convert(string1), this.convert(string2)));
	}

	/**
	 * add <code>null</code> check
	 */
	private char[] convert(String string) {
		return (string == null) ? null : string.toCharArray();
	}

	@Override
	protected void verifyStartsWithIgnoreCase(boolean expected, String string, String prefix) {
		assertEquals(expected, CharArrayTools.startsWithIgnoreCase(string.toCharArray(), prefix.toCharArray()));
		Predicate<char[]> predicate = new CharArrayTools.StartsWithIgnoreCase(prefix.toCharArray());
		assertEquals(expected, predicate.evaluate(string.toCharArray()));
	}

	@Override
	protected void verifyIsUppercase(String string) {
		assertTrue(CharArrayTools.isUppercase(string.toCharArray()));
	}

	@Override
	protected void denyIsUppercase(String string) {
		assertFalse(CharArrayTools.isUppercase(string.toCharArray()));
	}

	@Override
	protected void verifyIsLowercase(String string) {
		assertTrue(CharArrayTools.isLowercase(string.toCharArray()));
	}

	@Override
	protected void denyIsLowercase(String string) {
		assertFalse(CharArrayTools.isLowercase(string.toCharArray()));
	}

	@Override
	protected byte[] convertHexStringToByteArray(String string) {
		return CharArrayTools.convertHexStringToByteArray(string.toCharArray());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCaps(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertCamelCaseToAllCaps(string.toCharArray()));
	}

	@Override
	protected void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int max) {
		TestTools.assertEquals(expected, CharArrayTools.convertCamelCaseToAllCaps(string.toCharArray(), max));
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertAllCapsToCamelCase(string.toCharArray()));
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) {
		TestTools.assertEquals(expected, CharArrayTools.convertAllCapsToCamelCase(string.toCharArray(), capFirst));
	}

	@Override
	protected void verifyIsQuoted(String string) {
		assertTrue(CharArrayTools.isQuoted(string.toCharArray()));
	}

	@Override
	protected void denyIsQuoted(String string) {
		assertFalse(CharArrayTools.isQuoted(string.toCharArray()));
	}

	@Override
	protected void verifyIsParenthetical(String string) {
		assertTrue(CharArrayTools.isParenthetical(string.toCharArray()));
	}

	@Override
	protected void denyIsParenthetical(String string) {
		assertFalse(CharArrayTools.isParenthetical(string.toCharArray()));
	}

	@Override
	protected void verifyIsBracketed(String string) {
		assertTrue(CharArrayTools.isBracketed(string.toCharArray()));
	}

	@Override
	protected void denyIsBracketed(String string) {
		assertFalse(CharArrayTools.isBracketed(string.toCharArray()));
	}

	@Override
	protected void verifyIsBraced(String string) {
		assertTrue(CharArrayTools.isBraced(string.toCharArray()));
	}

	@Override
	protected void denyIsBraced(String string) {
		assertFalse(CharArrayTools.isBraced(string.toCharArray()));
	}

	@Override
	protected void verifyIsChevroned(String string) {
		assertTrue(CharArrayTools.isChevroned(string.toCharArray()));
	}

	@Override
	protected void denyIsChevroned(String string) {
		assertFalse(CharArrayTools.isChevroned(string.toCharArray()));
	}

	@Override
	protected void verifyIsDelimited(String string, char c) {
		assertTrue(CharArrayTools.isDelimited(string.toCharArray(), c));
	}

	@Override
	protected void denyIsDelimited(String string, char c) {
		assertFalse(CharArrayTools.isDelimited(string.toCharArray(), c));
	}

	@Override
	protected void verifyIsDelimited2(String string, char start, char end) {
		assertTrue(CharArrayTools.isDelimited(string.toCharArray(), start, end));
	}

	@Override
	protected void denyIsDelimited2(String string, char start, char end) {
		assertFalse(CharArrayTools.isDelimited(string.toCharArray(), start, end));
	}

	@Override
	protected void verifyUndelimit(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.undelimit(string.toCharArray()));
	}

	@Override
	protected void verifyUndelimitInt(String expected, String string, int count) {
		TestTools.assertEquals(expected, CharArrayTools.undelimit(string.toCharArray(), count));
	}

	@Override
	protected void verifyConvertToJavaStringLiteral(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToJavaStringLiteral(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.JAVA_STRING_LITERAL_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getJavaStringLiteralTransformer() {
		return CharArrayTools.JAVA_STRING_LITERAL_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToJavaStringLiteralContent(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToJavaStringLiteralContent(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getJavaStringLiteralContentTransformer() {
		return CharArrayTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlAttributeValue(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlAttributeValue(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getXmlAttributeValueTransformer() {
		return CharArrayTools.XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToDoubleQuotedXmlAttributeValue(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getDoubleQuotedXmlAttributeValueTransformer() {
		return CharArrayTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToDoubleQuotedXmlAttributeValueContent(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getDoubleQuotedXmlAttributeValueContentTransformer() {
		return CharArrayTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToSingleQuotedXmlAttributeValue(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getSingleQuotedXmlAttributeValueTransformer() {
		return CharArrayTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToSingleQuotedXmlAttributeValueContent(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getSingleQuotedXmlAttributeValueContentTransformer() {
		return CharArrayTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementText(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlElementText(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.XML_ELEMENT_TEXT_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getXmlElementTextTransformer() {
		return CharArrayTools.XML_ELEMENT_TEXT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementCDATA(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlElementCDATA(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.XML_ELEMENT_CDATA_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getXmlElementCDATATransformer() {
		return CharArrayTools.XML_ELEMENT_CDATA_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementCDATAContent(String expected, String string) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlElementCDATAContent(string.toCharArray()));
		TestTools.assertEquals(expected, CharArrayTools.XML_ELEMENT_CDATA_CONTENT_TRANSFORMER.transform(string.toCharArray()));
	}

	@Override
	protected Object getXmlElementCDATAContentTransformer() {
		return CharArrayTools.XML_ELEMENT_CDATA_CONTENT_TRANSFORMER;
	}

	@Override
	protected Class<?> getToolsClass() {
		return CharArrayTools.class;
	}

	// ********** CharArrayTools-specific **********

	public void testStringTransformer() throws Exception {
		Transformer<char[], String> transformer = CharArrayTools.STRING_TRANSFORMER;
		assertEquals("foo", transformer.transform("foo".toCharArray()));
		assertEquals("StringTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	// ********** String methods **********

	public void testCodePointAtInt() {
		this.verifyCodePointAtInt("foo-bar-baz", 7);
		this.verifyCodePointAtInt("caf\u00E9", 3);
	}

	private void verifyCodePointAtInt(String s, int index) {
		assertEquals(s.codePointAt(index), CharArrayTools.codePointAt(s.toCharArray(), index));
	}

	public void testCodePointBeforeInt() {
		this.verifyCodePointBeforeInt("foo-bar-baz", 7);
		this.verifyCodePointBeforeInt("caf\u00E9", 4);
	}

	private void verifyCodePointBeforeInt(String s, int index) {
		assertEquals(s.codePointBefore(index), CharArrayTools.codePointBefore(s.toCharArray(), index));
	}

	public void testCodePointCountIntInt() {
		this.verifyCodePointCountIntInt("foo-bar-baz");
		this.verifyCodePointCountIntInt("caf\u00E9");
	}

	private void verifyCodePointCountIntInt(String s) {
		assertEquals(s.codePointCount(0, s.length()), CharArrayTools.codePointCount(s.toCharArray(), 0, s.length()));
	}

	public void testCompareTo() {
		this.verifyCompareTo("foo", "bar");
		this.verifyCompareTo("foo", "foo");
		this.verifyCompareTo("foo", "zoo");
	}

	private void verifyCompareTo(String s1, String s2) {
		assertEquals(s1.compareTo(s2), CharArrayTools.compareTo(s1.toCharArray(), s2.toCharArray()));
	}

	public void testCompareToIgnoreCase() {
		this.verifyCompareToIgnoreCase("foo", "bar");
		this.verifyCompareToIgnoreCase("foo", "BAR");
		this.verifyCompareToIgnoreCase("foo", "foo");
		this.verifyCompareToIgnoreCase("foo", "FOO");
		this.verifyCompareToIgnoreCase("foo", "zoo");
		this.verifyCompareToIgnoreCase("foo", "ZOO");
		this.verifyCompareToIgnoreCase("foo", "fooo");
		this.verifyCompareToIgnoreCase("fooo", "foo");
	}

	private void verifyCompareToIgnoreCase(String s1, String s2) {
		assertEquals(s1.compareToIgnoreCase(s2), CharArrayTools.compareToIgnoreCase(s1.toCharArray(), s2.toCharArray()));
	}

	public void testConcat() {
		this.verifyConcat("foo", "bar");
		this.verifyConcat("foo", "foo");
		this.verifyConcat("foo", "zoo");
		this.verifyConcat("foo", "");
		this.verifyConcat("", "zoo");
	}

	private void verifyConcat(String s1, String s2) {
		TestTools.assertEquals(s1.concat(s2), CharArrayTools.concat(s1.toCharArray(), s2.toCharArray()));
	}

	public void testContainsCharSequence() {
		this.verifyContainsCharSequence("foo", "bar");
		this.verifyContainsCharSequence("foo", "foo");
		this.verifyContainsCharSequence("foo", "zoo");
		this.verifyContainsCharSequence("foo", "");
		this.verifyContainsCharSequence("", "zoo");
		this.verifyContainsCharSequence("foo", "f");
		this.verifyContainsCharSequence("foo", "o");
		this.verifyContainsCharSequence("foo", "z");
		this.verifyContainsCharSequence("foo", "oo");
	}

	private void verifyContainsCharSequence(String s1, String s2) {
		StringBuilder sb = new StringBuilder(s2);
		assertEquals(s1.contains(sb), CharArrayTools.contains(s1.toCharArray(), sb));
	}

	public void testContainsCharArray() {
		this.verifyContainsCharArray("foo", "bar");
		this.verifyContainsCharArray("foo", "foo");
		this.verifyContainsCharArray("foo", "zoo");
		this.verifyContainsCharArray("foo", "");
		this.verifyContainsCharArray("", "zoo");
		this.verifyContainsCharArray("foo", "f");
		this.verifyContainsCharArray("foo", "o");
		this.verifyContainsCharArray("foo", "z");
		this.verifyContainsCharArray("foo", "oo");
	}

	private void verifyContainsCharArray(String s1, String s2) {
		StringBuilder sb = new StringBuilder(s2);
		assertEquals(s1.contains(sb), CharArrayTools.contains(s1.toCharArray(), s2.toCharArray()));
	}

	public void testContentEqualsCharSequence() {
		this.verifyContentEqualsCharSequence("foo", "bar");
		this.verifyContentEqualsCharSequence("foo", "foo");
		this.verifyContentEqualsCharSequence("foo", "zoo");
		this.verifyContentEqualsCharSequence("foo", "");
		this.verifyContentEqualsCharSequence("", "zoo");
		this.verifyContentEqualsCharSequence("foo", "f");
		this.verifyContentEqualsCharSequence("foo", "o");
		this.verifyContentEqualsCharSequence("foo", "z");
		this.verifyContentEqualsCharSequence("foo", "oo");
	}

	private void verifyContentEqualsCharSequence(String s1, String s2) {
		StringBuilder sb = new StringBuilder(s2);
		assertEquals(s1.contentEquals(sb), CharArrayTools.contentEquals(s1.toCharArray(), sb));
	}

	public void testContentEqualsStringBuffer() {
		this.verifyContentEqualsStringBuffer("foo", "bar");
		this.verifyContentEqualsStringBuffer("foo", "foo");
		this.verifyContentEqualsStringBuffer("foo", "zoo");
		this.verifyContentEqualsStringBuffer("foo", "");
		this.verifyContentEqualsStringBuffer("", "zoo");
		this.verifyContentEqualsStringBuffer("foo", "f");
		this.verifyContentEqualsStringBuffer("foo", "o");
		this.verifyContentEqualsStringBuffer("foo", "z");
		this.verifyContentEqualsStringBuffer("foo", "oo");
	}

	private void verifyContentEqualsStringBuffer(String s1, String s2) {
		StringBuffer sb = new StringBuffer(s2);
		assertEquals(s1.contentEquals(sb), CharArrayTools.contentEquals(s1.toCharArray(), sb));
	}

	public void testEndsWith() {
		this.verifyEndsWith("foo", "bar");
		this.verifyEndsWith("foo", "foo");
		this.verifyEndsWith("foo", "zoo");
		this.verifyEndsWith("foo", "");
		this.verifyEndsWith("", "zoo");
		this.verifyEndsWith("foo", "f");
		this.verifyEndsWith("foo", "o");
		this.verifyEndsWith("foo", "z");
		this.verifyEndsWith("foo", "oo");
	}

	private void verifyEndsWith(String s1, String s2) {
		assertEquals(s1.endsWith(s2), CharArrayTools.endsWith(s1.toCharArray(), s2.toCharArray()));
	}

	public void testGetBytes() {
		this.verifyGetBytes("foo");
	}

	private void verifyGetBytes(String string) {
		Assert.assertArrayEquals(string.getBytes(), CharArrayTools.getBytes(string.toCharArray()));
	}

	public void testGetBytesCharSet() throws Exception {
		this.verifyGetBytesCharSet("foo");
	}

	private void verifyGetBytesCharSet(String string) throws Exception {
		String charset = Charset.defaultCharset().name();
		Assert.assertArrayEquals(string.getBytes(charset), CharArrayTools.getBytes(string.toCharArray(), charset));
	}

	public void testGetChars() {
		this.verifyGetChars("foo-bar-baz", 0, 11, 0);
		this.verifyGetChars("foo-bar-baz", 11, 11, 11);
		this.verifyGetChars("foo-bar-baz", 4, 5, 0);
		this.verifyGetChars("foo-bar-baz", 4, 11, 0);
	}

	private void verifyGetChars(String s, int srcBegin, int srcEnd, int destBegin) {
		char[] a1 = ArrayTools.fill(new char[42], 'x');
		s.getChars(srcBegin, srcEnd, a1, destBegin);
		char[] a2 = ArrayTools.fill(new char[42], 'x');
		CharArrayTools.getChars(s.toCharArray(), srcBegin, srcEnd, a2, destBegin);
		Assert.assertArrayEquals(a1, a2);
	}

	public void testIndexOfChar() {
		this.verifyIndexOfChar("foo-bar-baz", ' ');
		this.verifyIndexOfChar("foo-bar-baz", 'f');
		this.verifyIndexOfChar("foo-bar-baz", 'b');
		this.verifyIndexOfChar("foo-bar-baz", 'z');

		this.verifyIndexOfChar("", ' ');
		this.verifyIndexOfChar("", 'f');
		this.verifyIndexOfChar("", 'b');
		this.verifyIndexOfChar("", 'z');
	}

	private void verifyIndexOfChar(String string, char c) {
		assertEquals(string.indexOf(c), CharArrayTools.indexOf(string.toCharArray(), c));
	}

	public void testIndexOfCharIndex() {
		this.verifyIndexOfCharIndex("foo-bar-baz", ' ');
		this.verifyIndexOfCharIndex("foo-bar-baz", 'f');
		this.verifyIndexOfCharIndex("foo-bar-baz", 'b');
		this.verifyIndexOfCharIndex("foo-bar-baz", 'z');

		this.verifyIndexOfCharIndex("", ' ');
		this.verifyIndexOfCharIndex("", 'f');
		this.verifyIndexOfCharIndex("", 'b');
		this.verifyIndexOfCharIndex("", 'z');
	}

	private void verifyIndexOfCharIndex(String string, char c) {
		assertEquals(string.indexOf(c, 3), CharArrayTools.indexOf(string.toCharArray(), c, 3));
	}

	public void testIndexOfCharSequence() {
		this.verifyIndexOfCharSequence("foo-bar-baz", "");
		this.verifyIndexOfCharSequence("foo-bar-baz", "foo-bar-baz");
		this.verifyIndexOfCharSequence("foo-bar-baz", "bar");
		this.verifyIndexOfCharSequence("foo-bar-baz", "bbb");

		this.verifyIndexOfCharSequence("", "");
		this.verifyIndexOfCharSequence("", "foo-bar-baz");
		this.verifyIndexOfCharSequence("", "bar");
		this.verifyIndexOfCharSequence("", "bbb");
	}

	private void verifyIndexOfCharSequence(String s1, String s2) {
		assertEquals(s1.indexOf(s2), CharArrayTools.indexOf(s1.toCharArray(), s2));
		assertEquals(s1.indexOf(s2), CharArrayTools.indexOf(s1.toCharArray(), s2, -3));
	}

	public void testIndexOfCharArray() {
		this.verifyIndexOfCharArray("foo-bar-baz", "");
		this.verifyIndexOfCharArray("foo-bar-baz", "foo-bar-baz");
		this.verifyIndexOfCharArray("foo-bar-baz", "bar");
		this.verifyIndexOfCharArray("foo-bar-baz", "bbb");

		this.verifyIndexOfCharArray("", "");
		this.verifyIndexOfCharArray("", "foo-bar-baz");
		this.verifyIndexOfCharArray("", "bar");
		this.verifyIndexOfCharArray("", "bbb");
	}

	private void verifyIndexOfCharArray(String s1, String s2) {
		assertEquals(s1.indexOf(s2), CharArrayTools.indexOf(s1.toCharArray(), s2.toCharArray()));
		assertEquals(s1.indexOf(s2), CharArrayTools.indexOf(s1.toCharArray(), s2.toCharArray(), -3));
	}

	public void testLastIndexOf() {
		this.verifyLastIndexOf("foo-bar-baz", "");
		this.verifyLastIndexOf("foo-bar-baz", "foo-bar-baz");
		this.verifyLastIndexOf("foo-bar-baz", "bar");
		this.verifyLastIndexOf("foo-bar-baz", "bbb");

		this.verifyLastIndexOf("", "");
		this.verifyLastIndexOf("", "foo-bar-baz");
		this.verifyLastIndexOf("", "bar");
		this.verifyLastIndexOf("", "bbb");
	}

	private void verifyLastIndexOf(String s1, String s2) {
		assertEquals(s1.lastIndexOf(s2), CharArrayTools.lastIndexOf(s1.toCharArray(), s2.toCharArray()));
		assertEquals(-1, CharArrayTools.lastIndexOf(s1.toCharArray(), s2.toCharArray(), -3));
	}

	public void testLastIndexOfChar() {
		this.verifyLastIndexOfChar("foo-bar-baz", ' ');
		this.verifyLastIndexOfChar("foo-bar-baz", 'f');
		this.verifyLastIndexOfChar("foo-bar-baz", 'v');
		this.verifyLastIndexOfChar("foo-bar-baz", 'z');

		this.verifyLastIndexOfChar("", ' ');
		this.verifyLastIndexOfChar("", 'f');
		this.verifyLastIndexOfChar("", 'b');
		this.verifyLastIndexOfChar("", 'z');
	}

	private void verifyLastIndexOfChar(String string, char c) {
		assertEquals(string.lastIndexOf(c), CharArrayTools.lastIndexOf(string.toCharArray(), c));
		assertEquals(-1, CharArrayTools.lastIndexOf(string.toCharArray(), c, -3));
	}

	public void testOffsetByCodePoints() {
		this.verifyOffsetByCodePoints("foo-bar-baz", 3, 3);
		this.verifyOffsetByCodePoints("aaaaab", 3, 3);
	}

	private void verifyOffsetByCodePoints(String string, int index, int codePointOffset) {
		assertEquals(string.offsetByCodePoints(index, codePointOffset), CharArrayTools.offsetByCodePoints(string.toCharArray(), index, codePointOffset));
	}

	public void testMatches() {
		this.verifyMatches("foo-bar-baz", "");
		this.verifyMatches("aaaaab", "a*b");
	}

	private void verifyMatches(String s1, String s2) {
		assertEquals(s1.matches(s2), CharArrayTools.matches(s1.toCharArray(), s2.toCharArray()));
	}

	public void testRegionMatchesIgnoreCase() {
		this.verifyRegionMatchesBoolean("foo-bar-baz", "", true, 0, 0, 5);
		this.verifyRegionMatchesBoolean("foo-bar-baz", "foo", true, 0, 0, 3);
		this.verifyRegionMatchesBoolean("foo-bar-baz", "FOO", true, 0, 0, 3);
		this.verifyRegionMatchesBoolean("foo-bar-baz", "XXX", true, 0, 0, 3);
	}

	public void testRegionMatchesCaseSensitive() {
		this.verifyRegionMatchesBoolean("foo-bar-baz", "", false, 0, 0, 5);
		this.verifyRegionMatchesBoolean("foo-bar-baz", "foo", false, 0, 0, 3);
		this.verifyRegionMatchesBoolean("foo-bar-baz", "xxx", false, 0, 0, 3);
	}

	private void verifyRegionMatchesBoolean(String s1, String s2, boolean ignoreCase, int offset1, int offset2, int len) {
		assertEquals(s1.regionMatches(ignoreCase, offset1, s2, offset2, len), CharArrayTools.regionMatches(s1.toCharArray(), ignoreCase, offset1, s2.toCharArray(), offset2, len));
	}

	public void testRegionMatches() {
		this.verifyRegionMatches("foo-bar-baz", "", 0, 0, 5);
		this.verifyRegionMatches("foo-bar-baz", "foo", 0, 0, 3);
		this.verifyRegionMatches("foo-bar-baz", "xxx", 0, 0, 3);
	}

	private void verifyRegionMatches(String s1, String s2, int offset1, int offset2, int len) {
		assertEquals(s1.regionMatches(offset1, s2, offset2, len), CharArrayTools.regionMatches(s1.toCharArray(), offset1, s2.toCharArray(), offset2, len));
	}

	public void testReplace() {
		this.verifyReplace("foo-bar-baz", 'b', 'x');
		this.verifyReplace("foo-bar-baz", 'X', 'T');
		this.verifyReplace("foo-bar-baz", 'X', 'X');
	}

	private void verifyReplace(String string, char oldChar, char newChar) {
		char[] array = CharArrayTools.replace(string.toCharArray(), oldChar, newChar);
		TestTools.assertEquals(string.replace(oldChar, newChar), array);
	}

	public void testStartsWith() {
		this.verifyStartsWith("foo-bar-baz", "foo");
		this.verifyStartsWith("foo-bar-baz", "FOO");
		this.verifyStartsWith("foo-bar-baz", "bar");
	}

	private void verifyStartsWith(String s1, String s2) {
		assertEquals(s1.startsWith(s2), CharArrayTools.startsWith(s1.toCharArray(), s2.toCharArray()));
	}

	public void testStartsWithOffset() {
		this.verifyStartsWithOffset("foo-bar-baz", "foo", 0);
		this.verifyStartsWithOffset("foo-bar-baz", "foo", 4);
		this.verifyStartsWithOffset("foo-bar-baz", "bar", 0);
		this.verifyStartsWithOffset("foo-bar-baz", "bar", 4);
		this.verifyStartsWithOffset("foo-bar-baz", "bar", 22);
		this.verifyStartsWithOffset("foo-bar-baz", "bar", -3);
	}

	private void verifyStartsWithOffset(String s1, String s2, int offset) {
		assertEquals(s1.startsWith(s2, offset), CharArrayTools.startsWith(s1.toCharArray(), s2.toCharArray(), offset));
	}

	public void testSubSequence() {
		this.verifySubSequence("foo-bar-baz", 0, 11);
		this.verifySubSequence("foo-bar-baz", 0, 3);
		this.verifySubSequence("foo-bar-baz", 0, 0);
		this.verifySubSequence("foo-bar-baz", 2, 11);
		this.verifySubSequence("foo-bar-baz", 2, 3);
		this.verifySubSequence("foo-bar-baz", 2, 2);
	}

	private void verifySubSequence(String string, int beginIndex, int endIndex) {
		char[] array = CharArrayTools.subSequence(string.toCharArray(), beginIndex, endIndex);
		TestTools.assertEquals(string.subSequence(beginIndex, endIndex), array);
	}

	public void testTrim() {
		this.verifyTrim("foo-bar-baz");
		this.verifyTrim(" foo-bar-baz ");
		this.verifyTrim("     foo-bar-baz ");
	}

	private void verifyTrim(String string) {
		TestTools.assertEquals(string.trim(), CharArrayTools.trim(string.toCharArray()));
	}
}
