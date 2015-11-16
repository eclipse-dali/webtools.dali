/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.junit.Assert;

@SuppressWarnings("nls")
public class StringBuilderToolsTests
	extends AbstractStringBuilderToolsTests
{
	public StringBuilderToolsTests(String name) {
		super(name);
	}

	@Override
	protected void verifyConvertToCharArray(String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append(string);
		Assert.assertArrayEquals(string.toCharArray(), StringBuilderTools.convertToCharArray(sb));
	}

	@Override
	protected void verifyReverse(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.reverse(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.reverse(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCenter(String expected, String s, int len) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.center(sb, s, len);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.center(sb, s.toCharArray(), len);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyPad(String expected, String string, int length) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.pad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.pad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyFit(String expected, String string, int length) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.fit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.fit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroPad(String expected, String string, int length) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.zeroPad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.zeroPad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroFit(String expected, String string, int length) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.zeroFit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.zeroFit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRepeat(String expected, String string, int length) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.repeat(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.repeat(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifySeparate(String expected, String string, char separator, int segmentLength) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.separate(sb, string, separator, segmentLength);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.separate(sb, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyDelimit(String expected, String string, String delimiter) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.delimit(sb, string, delimiter);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.delimit(sb, string.toCharArray(), delimiter.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyQuote(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.quote(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.quote(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.removeFirstOccurrence(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.removeFirstOccurrence(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.removeAllOccurrences(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.removeAllOccurrences(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllSpaces(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.removeAllSpaces(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.removeAllSpaces(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllWhitespace(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.removeAllWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.removeAllWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCompressWhitespace(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.compressWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.compressWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCapitalize(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.capitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUncapitalize(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.uncapitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToHexString(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.convertToHexString(sb, string.getBytes());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCaps(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.convertCamelCaseToAllCaps(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertCamelCaseToAllCaps(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int maxLength) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.convertCamelCaseToAllCaps(sb, string, maxLength);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertCamelCaseToAllCaps(sb, string.toCharArray(), maxLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.convertAllCapsToCamelCase(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertAllCapsToCamelCase(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.convertAllCapsToCamelCase(sb, string, capFirst);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertAllCapsToCamelCase(sb, string.toCharArray(), capFirst);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimit(String expected, String s) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimitCount(String expected, String s, int count) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s, count);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s.toCharArray(), count);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteral(String expected, String s) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteral(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteral(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteralContent(String expected, String s) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteralContent(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteralContent(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlAttributeValue(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToSingleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToSingleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToSingleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToSingleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementText(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementText(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementText(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATA(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementCDATA(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementCDATA(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATAContent(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementCDATAContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToXmlElementCDATAContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected Class<?> getToolsClass() {
		return StringBuilderTools.class;
	}

	// ********** StringBuilderTools-specific **********

	public void testAppendObjectArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = null;
		StringBuilderTools.append(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendObjectArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[0];
		StringBuilderTools.append(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendObjectArray_one() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[] {"foo"};
		StringBuilderTools.append(sb, array);
		assertEquals("[foo]", sb.toString());
	}

	public void testAppendObjectArray_multiple() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[] {"foo", "bar", "baz"};
		StringBuilderTools.append(sb, array);
		assertEquals("[foo, bar, baz]", sb.toString());
	}

	public void testAppendIterable_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Iterable<Object> iterable = IterableTools.iterable(new Object[0]);
		StringBuilderTools.append(sb, iterable);
		assertEquals("[]", sb.toString());
	}

	public void testAppendIterable_one() throws Exception {
		StringBuilder sb = new StringBuilder();
		Iterable<Object> iterable = IterableTools.iterable(new Object[] {"foo"});
		StringBuilderTools.append(sb, iterable);
		assertEquals("[foo]", sb.toString());
	}

	public void testAppendIterable_multiple() throws Exception {
		StringBuilder sb = new StringBuilder();
		Iterable<Object> iterable = IterableTools.iterable(new Object[] {"foo", "bar", "baz"});
		StringBuilderTools.append(sb, iterable);
		assertEquals("[foo, bar, baz]", sb.toString());
	}

	public void testAppendHashCodeToString() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object object = new Object();
		StringBuilderTools.appendHashCodeToString(sb, object);
		String string = sb.toString();
		String prefix = "Object[";
		assertTrue(string.startsWith(prefix));
		assertTrue(string.endsWith("]"));
		for (int i = prefix.length(); i < string.length() - 1; i++) {
			char c = string.charAt(i);
			assertTrue(((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'F')) || (c == '-'));
		}
	}

	public void testAppendIdentityToString() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object object = new Object();
		StringBuilderTools.appendIdentityToString(sb, object); // "java.lang.Object@3cb5cdba"
		String string = sb.toString();
		String prefix = "java.lang.Object@";
		assertTrue(string.startsWith(prefix));
		for (int i = prefix.length(); i < string.length(); i++) {
			char c = string.charAt(i);
			assertTrue(((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')));
		}
	}
}
