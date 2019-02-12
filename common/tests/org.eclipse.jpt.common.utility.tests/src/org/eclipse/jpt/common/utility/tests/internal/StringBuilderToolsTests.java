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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jpt.common.utility.internal.SimplePair;
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

	// ********** JSON **********

	public void testAppendJSONMap() throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<>();
		map.put("left", "name");
		map.put("right", "Fred");
		StringBuilderTools.appendJSON(sb, map);
		assertEquals("{\"left\":\"name\",\"right\":\"Fred\"}", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) map);
		assertEquals("{\"left\":\"name\",\"right\":\"Fred\"}", sb.toString());
	}

	public void testAppendJSONMap_reflection() throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<Object, String> map = new HashMap<>();
		map.put(Integer.valueOf(42), "Fred");
		assertFalse(map.keySet().isEmpty()); // trigger creation of key set
		assertFalse(map.entrySet().isEmpty()); // trigger creation of entry set
		StringBuilderTools.appendJSON(sb, map);
		String expected = "{\"entrySet\":[{\"hash\":42,\"key\":42,\"next\":null,\"value\":\"Fred\"}],\"keySet\":[42],\"loadFactor\":0.75,\"modCount\":1,\"size\":1,\"table\":[null,null,null,null,null,null,null,null,null,null,{\"hash\":42,\"key\":42,\"next\":null,\"value\":\"Fred\"},null,null,null,null,null],\"threshold\":12,\"values\":null}";
		assertEquals(expected, sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) map);
		assertEquals(expected, sb.toString());
	}

	public void testAppendJSONMap_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, new HashMap<>());
		assertEquals("{}", sb.toString());
	}

	public void testAppendJSONMap_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = null;
		StringBuilderTools.appendJSON(sb, map);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONString() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "foo";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"foo\"", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) s);
		assertEquals("\"foo\"", sb.toString());
	}

	public void testAppendJSONString_specialChars() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "\"foo\"";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\\"foo\\\"\"", sb.toString());

		sb = new StringBuilder();
		s = "\\";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\\\\"", sb.toString());

		sb = new StringBuilder();
		s = "\b";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\b\"", sb.toString());

		sb = new StringBuilder();
		s = "\f";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\f\"", sb.toString());

		sb = new StringBuilder();
		s = "\n";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\n\"", sb.toString());

		sb = new StringBuilder();
		s = "\r";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\r\"", sb.toString());

		sb = new StringBuilder();
		s = "\t";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\t\"", sb.toString());

		sb = new StringBuilder();
		s = "\u0012";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\u0012\"", sb.toString());

		sb = new StringBuilder();
		s = "\u0002";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\\u0002\"", sb.toString());
	}

	public void testAppendJSONString_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "";
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("\"\"", sb.toString());
	}

	public void testAppendJSONString_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = null;
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONContentString() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "foo";
		StringBuilderTools.appendJSONContent(sb, s);
		assertEquals("foo", sb.toString());
	}

	public void testAppendJSONContentString_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "";
		StringBuilderTools.appendJSONContent(sb, s);
		assertEquals("", sb.toString());
	}

	public void testAppendJSONContentString_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = null;
		StringBuilderTools.appendJSONContent(sb, s);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONCharArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "foo";
		StringBuilderTools.appendJSON(sb, s.toCharArray());
		assertEquals("\"foo\"", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) s.toCharArray());
		assertEquals("\"foo\"", sb.toString());
	}

	public void testAppendJSONCharArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "";
		StringBuilderTools.appendJSON(sb, s.toCharArray());
		assertEquals("\"\"", sb.toString());
	}

	public void testAppendJSONCharArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		char[] s = null;
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONContentCharArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "foo";
		StringBuilderTools.appendJSONContent(sb, s.toCharArray());
		assertEquals("foo", sb.toString());
	}

	public void testAppendJSONContentCharArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		String s = "";
		StringBuilderTools.appendJSONContent(sb, s.toCharArray());
		assertEquals("", sb.toString());
	}

	public void testAppendJSONContentCharArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		char[] s = null;
		StringBuilderTools.appendJSONContent(sb, s);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONIterable() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[] {
				new SimplePair<>("name", "Fred"),
				new SimplePair<>("age", Integer.valueOf(42)),
				new SimplePair<>("sex", "male"),
				null
			};
		Iterable<?> iterable = IterableTools.iterable(array);
		StringBuilderTools.appendJSON(sb, iterable);
		String expected = "[{\"left\":\"name\",\"right\":\"Fred\"},{\"left\":\"age\",\"right\":42},{\"left\":\"sex\",\"right\":\"male\"},null]";
		assertEquals(expected, sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) iterable);
		assertEquals(expected, sb.toString());
	}

	public void testAppendJSONIterable_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[0];
		Iterable<?> iterable = IterableTools.iterable(array);
		StringBuilderTools.appendJSON(sb, iterable);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONIterable_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Iterable<?> iterable = null;
		StringBuilderTools.appendJSON(sb, iterable);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONObjectArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[] {
				new SimplePair<>("name", "Fred"),
				new SimplePair<>("age", Integer.valueOf(42)),
				new SimplePair<>("sex", "male"),
				null
			};
		StringBuilderTools.appendJSON(sb, array);
		String expected = "[{\"left\":\"name\",\"right\":\"Fred\"},{\"left\":\"age\",\"right\":42},{\"left\":\"sex\",\"right\":\"male\"},null]";
		assertEquals(expected, sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals(expected, sb.toString());
	}

	public void testAppendJSONObjectArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = new Object[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONObjectArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Object[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONObject() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, new SimplePair<>("name", "Fred"));
		assertEquals("{\"left\":\"name\",\"right\":\"Fred\"}", sb.toString());
	}

	public void testAppendJSONObject_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, new Object());
		assertEquals("{}", sb.toString());
	}

	public void testAppendJSONBooleanArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Boolean[] array = new Boolean[] { Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[true,false,true,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object[]) array);
		assertEquals("[true,false,true,null]", sb.toString());
	}

	public void testAppendJSONBooleanArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Boolean[] array = new Boolean[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object[]) array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONBooleanArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Boolean[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONBoolean() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, Boolean.valueOf(true));
		assertEquals("true", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, Boolean.valueOf(false));
		assertEquals("false", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) Boolean.valueOf(true));
		assertEquals("true", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) Boolean.valueOf(false));
		assertEquals("false", sb.toString());
	}

	public void testAppendJSONBooleanPrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		boolean[] array = new boolean[] { true, false, true };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[true,false,true]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[true,false,true]", sb.toString());
	}

	public void testAppendJSONBooleanPrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		boolean[] array = new boolean[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONBooleanPrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		boolean[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONBooleanPrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, true);
		assertEquals("true", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, false);
		assertEquals("false", sb.toString());
	}

	public void testAppendJSONNumber() throws Exception {
		Number n;
		StringBuilder sb;

		sb = new StringBuilder();
		n = Integer.valueOf(7);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7", sb.toString());

		sb = new StringBuilder();
		n = Double.valueOf(7.7);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7.7", sb.toString());

		sb = new StringBuilder();
		n = Byte.valueOf((byte) 7);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7", sb.toString());

		sb = new StringBuilder();
		n = Float.valueOf(7.7f);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7.7", sb.toString());

		sb = new StringBuilder();
		n = Long.valueOf(123456789123456789L);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("123456789123456789", sb.toString());

		sb = new StringBuilder();
		n = Short.valueOf((short) 7);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7", sb.toString());

		sb = new StringBuilder();
		n = BigDecimal.valueOf(123456789123456789L, 5);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("1234567891234.56789", sb.toString());

		sb = new StringBuilder();
		n = BigInteger.valueOf(123456789123456789L);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("123456789123456789", sb.toString());

		sb = new StringBuilder();
		n = new AtomicInteger(7);
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("7", sb.toString());

		sb = new StringBuilder();
		n = null;
		StringBuilderTools.appendJSON(sb, n);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONIntegerArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Integer i0 = Integer.valueOf(7);
		Integer i1 = Integer.valueOf(-3);
		Integer i2 = Integer.valueOf(0);
		Integer[] array = new Integer[] { i0, i1, i2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[7,-3,0,null]", sb.toString());
	}

	public void testAppendJSONIntegerArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Integer[] array = new Integer[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONIntegerArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Integer[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONInteger() throws Exception {
		StringBuilder sb = new StringBuilder();
		Integer i = Integer.valueOf(7);
		StringBuilderTools.appendJSON(sb, i);
		assertEquals("7", sb.toString());
	}

	public void testAppendJSONIntegerPrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		int i0 = 7;
		int i1 = -3;
		int i2 = 0;
		int[] array = new int[] { i0, i1, i2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[7,-3,0]", sb.toString());
	}

	public void testAppendJSONIntegerPrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		int[] array = new int[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONIntegerPrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		int[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONIntegerPrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 7;
		StringBuilderTools.appendJSON(sb, i);
		assertEquals("7", sb.toString());
	}

	public void testAppendJSONDoubleArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Double d0 = Double.valueOf(7.7);
		Double d1 = Double.valueOf(-3.2);
		Double d2 = Double.valueOf(0.0);
		Double[] array = new Double[] { d0, d1, d2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7.7,-3.2,0.0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[7.7,-3.2,0.0,null]", sb.toString());
	}

	public void testAppendJSONDoubleArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Double[] array = new Double[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONDoubleArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Double[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONDouble() throws Exception {
		StringBuilder sb = new StringBuilder();
		Double d = Double.valueOf(7.0);
		StringBuilderTools.appendJSON(sb, d);
		assertEquals("7.0", sb.toString());
	}

	public void testAppendJSONDoublePrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		double d0 = 7.7;
		double d1 = -3.2;
		double d2 = 0.0;
		double[] array = new double[] { d0, d1, d2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7.7,-3.2,0.0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[7.7,-3.2,0.0]", sb.toString());
	}

	public void testAppendJSONDoublePrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		double[] array = new double[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONDoublePrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		double[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONDoublePrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		double d = 7.0;
		StringBuilderTools.appendJSON(sb, d);
		assertEquals("7.0", sb.toString());
	}

	public void testAppendJSONByteArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Byte b0 = Byte.valueOf((byte) 7);
		Byte b1 = Byte.valueOf((byte) -3);
		Byte b2 = Byte.valueOf((byte) 0);
		Byte[] array = new Byte[] { b0, b1, b2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[7,-3,0,null]", sb.toString());
	}

	public void testAppendJSONByteArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Byte[] array = new Byte[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONByteArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Byte[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONByte() throws Exception {
		StringBuilder sb = new StringBuilder();
		Byte b = Byte.valueOf((byte) 7);
		StringBuilderTools.appendJSON(sb, b);
		assertEquals("7", sb.toString());
	}

	public void testAppendJSONBytePrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		byte b0 = 7;
		byte b1 = -3;
		byte b2 = 0;
		byte[] array = new byte[] { b0, b1, b2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[7,-3,0]", sb.toString());
	}

	public void testAppendJSONBytePrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		byte[] array = new byte[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONBytePrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		byte[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONBytePrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		byte b = 7;
		StringBuilderTools.appendJSON(sb, b);
		assertEquals("7", sb.toString());
	}

	public void testAppendJSONFloatArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Float f0 = Float.valueOf(7.7f);
		Float f1 = Float.valueOf(-3.2f);
		Float f2 = Float.valueOf(0.0f);
		Float[] array = new Float[] { f0, f1, f2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7.7,-3.2,0.0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[7.7,-3.2,0.0,null]", sb.toString());
	}

	public void testAppendJSONFloatArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Float[] array = new Float[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONFloatArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Float[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONFloat() throws Exception {
		StringBuilder sb = new StringBuilder();
		Float f = Float.valueOf(7.0f);
		StringBuilderTools.appendJSON(sb, f);
		assertEquals("7.0", sb.toString());
	}

	public void testAppendJSONFloatPrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		float f0 = 7.7f;
		float f1 = -3.2f;
		float f2 = 0.0f;
		float[] array = new float[] { f0, f1, f2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7.7,-3.2,0.0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[7.7,-3.2,0.0]", sb.toString());
	}

	public void testAppendJSONFloatPrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		float[] array = new float[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONFloatPrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		float[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONFloatPrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		float f = 7.0f;
		StringBuilderTools.appendJSON(sb, f);
		assertEquals("7.0", sb.toString());
	}

	public void testAppendJSONLongArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Long l0 = Long.valueOf(123456789123456789L);
		Long l1 = Long.valueOf(-123456789123456789L);
		Long l2 = Long.valueOf(0);
		Long[] array = new Long[] { l0, l1, l2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[123456789123456789,-123456789123456789,0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[123456789123456789,-123456789123456789,0,null]", sb.toString());
	}

	public void testAppendJSONLongArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Long[] array = new Long[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONLongArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Long[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONLong() throws Exception {
		StringBuilder sb = new StringBuilder();
		Long l = Long.valueOf(123456789123456789L);
		StringBuilderTools.appendJSON(sb, l);
		assertEquals("123456789123456789", sb.toString());
	}

	public void testAppendJSONLongPrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		long l0 = 123456789123456789L;
		long l1 = -123456789123456789L;
		long l2 = 0;
		long[] array = new long[] { l0, l1, l2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[123456789123456789,-123456789123456789,0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[123456789123456789,-123456789123456789,0]", sb.toString());
	}

	public void testAppendJSONLongPrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		long[] array = new long[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONLongPrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		long[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONLongPrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		long l = 123456789123456789L;
		StringBuilderTools.appendJSON(sb, l);
		assertEquals("123456789123456789", sb.toString());
	}

	public void testAppendJSONShortArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Short s0 = Short.valueOf((short) 5);
		Short s1 = Short.valueOf((short) -3);
		Short s2 = Short.valueOf((short) 0);
		Short[] array = new Short[] { s0, s1, s2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[5,-3,0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[5,-3,0,null]", sb.toString());
	}

	public void testAppendJSONShortArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Short[] array = new Short[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONShortArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Short[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONShort() throws Exception {
		StringBuilder sb = new StringBuilder();
		Short s = Short.valueOf((short) 5);
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("5", sb.toString());
	}

	public void testAppendJSONShortPrimitiveArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		short s0 = 5;
		short s1 = -3;
		short s2 = 0;
		short[] array = new short[] { s0, s1, s2 };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[5,-3,0]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object) array);
		assertEquals("[5,-3,0]", sb.toString());
	}

	public void testAppendJSONShortPrimitiveArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		short[] array = new short[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONShortPrimitiveArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		short[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONShortPrimitive() throws Exception {
		StringBuilder sb = new StringBuilder();
		short s = 5;
		StringBuilderTools.appendJSON(sb, s);
		assertEquals("5", sb.toString());
	}

	public void testAppendJSONBigDecimalArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigDecimal bd0 = BigDecimal.valueOf(123456789123456789L, 5);
		BigDecimal bd1 = BigDecimal.valueOf(987654321987654321L, 9);
		BigDecimal bd2 = BigDecimal.valueOf(987654321987654321L, -9);
		BigDecimal[] array = new BigDecimal[] { bd0, bd1, bd2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[1234567891234.56789,987654321.987654321,9.87654321987654321E+26,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[1234567891234.56789,987654321.987654321,9.87654321987654321E+26,null]", sb.toString());
	}

	public void testAppendJSONBigDecimalArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigDecimal[] array = new BigDecimal[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONBigDecimalArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigDecimal[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONBigDecimal() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigDecimal bd = BigDecimal.valueOf(123456789123456789L, 5);
		StringBuilderTools.appendJSON(sb, bd);
		assertEquals("1234567891234.56789", sb.toString());
	}

	public void testAppendJSONBigIntegerArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigInteger bi0 = BigInteger.valueOf(123456789123456789L);
		BigInteger bi1 = BigInteger.valueOf(987654321987654321L);
		BigInteger[] array = new BigInteger[] { bi0, bi1, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[123456789123456789,987654321987654321,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Number[]) array);
		assertEquals("[123456789123456789,987654321987654321,null]", sb.toString());
	}

	public void testAppendJSONBigIntegerArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigInteger[] array = new BigInteger[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONBigIntegerArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigInteger[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}

	public void testAppendJSONBigInteger() throws Exception {
		StringBuilder sb = new StringBuilder();
		BigInteger bi = BigInteger.valueOf(123456789123456789L);
		StringBuilderTools.appendJSON(sb, bi);
		assertEquals("123456789123456789", sb.toString());
	}

	public void testAppendJSONFraction() throws Exception {
		StringBuilder sb = new StringBuilder();
		org.apache.commons.lang.math.Fraction f = org.apache.commons.lang.math.Fraction.getFraction(2, 7);
		StringBuilderTools.appendJSON(sb, f);
		assertEquals("{\"denominator\":7,\"hashCode\":0,\"numerator\":2,\"toProperString\":null,\"toString\":\"2/7\"}", sb.toString());
	}

	public void testAppendJSONNumberArray() throws Exception {
		StringBuilder sb = new StringBuilder();
		Number n0 = new AtomicInteger(7);
		Number n1 = new AtomicInteger(-3);
		Number n2 = new AtomicInteger(0);
		Number[] array = new Number[] { n0, n1, n2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object[]) array);
		assertEquals("[7,-3,0,null]", sb.toString());
	}

	public void testAppendJSONNumberArray_mixed() throws Exception {
		StringBuilder sb = new StringBuilder();
		Number n0 = new AtomicInteger(7);
		Number n1 = Integer.valueOf(-3);
		Number n2 = Double.valueOf(0.33);
		Number[] array = new Number[] { n0, n1, n2, null };
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[7,-3,0.33,null]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object[]) array);
		assertEquals("[7,-3,0.33,null]", sb.toString());
	}

	public void testAppendJSONNumberArray_empty() throws Exception {
		StringBuilder sb = new StringBuilder();
		Number[] array = new Number[0];
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("[]", sb.toString());
		sb = new StringBuilder();
		StringBuilderTools.appendJSON(sb, (Object[]) array);
		assertEquals("[]", sb.toString());
	}

	public void testAppendJSONNumberArray_null() throws Exception {
		StringBuilder sb = new StringBuilder();
		Number[] array = null;
		StringBuilderTools.appendJSON(sb, array);
		assertEquals("null", sb.toString());
	}


	// ********** array/iterable **********

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


	// ********** toString() stuff **********

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
