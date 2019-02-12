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

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class StringToolsTests
	extends AbstractStringToolsTests
{
	public StringToolsTests(String name) {
		super(name);
	}

	@Override
	protected String CR() {
		return StringTools.CR;
	}

	@Override
	protected void verifyReverse(String expected, String string) {
		assertEquals(expected, StringTools.reverse(string));
	}

	@Override
	protected void verifyLast(char expected, String string) {
		assertEquals(expected, StringTools.last(string));
	}

	@Override
	protected void verifyConcatenate(String expected, String[] array) {
		assertEquals(expected, StringTools.concatenate(array));
		Iterable<String> iterable = IterableTools.iterable(array);
		assertEquals(expected, StringTools.concatenate(iterable));
		assertEquals(expected, StringTools.concatenate(iterable.iterator()));
	}

	@Override
	protected void verifyConcatenate(String expected, String[] array, String delim) {
		assertEquals(expected, StringTools.concatenate(array, delim));
		Iterable<String> iterable = IterableTools.iterable(array);
		assertEquals(expected, StringTools.concatenate(iterable, delim));
		assertEquals(expected, StringTools.concatenate(iterable.iterator(), delim));
	}

	@Override
	protected void verifyCenter(String expected, String string, int len) {
		assertEquals(expected, StringTools.center(string, len));
	}

	@Override
	protected void verifyPad(String expected, String string, int len) {
		assertEquals(expected, StringTools.pad(string, len));
	}

	@Override
	protected void verifyFit(String expected, String string, int len) {
		assertEquals(expected, StringTools.fit(string, len));
	}

	@Override
	protected void verifyZeroPad(String expected, String string, int len) {
		assertEquals(expected, StringTools.zeroPad(string, len));
	}

	@Override
	protected void verifyZeroFit(String expected, String string, int len) {
		assertEquals(expected, StringTools.zeroFit(string, len));
	}

	@Override
	protected void verifyRepeat(String expected, String string, int length) {
		assertEquals(expected, StringTools.repeat(string, length));
	}

	@Override
	protected void verifySeparate(String expected, String string, char separator, int segmentLength) {
		assertEquals(expected, StringTools.separate(string, separator, segmentLength));
	}

	@Override
	protected void verifyQuote(String expected, String string) {
		assertEquals(expected, StringTools.quote(string));
	}

	@Override
	protected void verifyCharDelimiter(String expected, String string, char delimiter) {
		Transformer<String, String> transformer = this.buildCharDelimiter(delimiter);
		assertEquals(expected, transformer.transform(string));
	}

	@Override
	protected Transformer<String, String> buildCharDelimiter(char delimiter) {
		return new StringTools.CharDelimiter(delimiter);
	}

	@Override
	protected void verifyDelimit(String expected, String string, String delimiter) {
		assertEquals(expected, StringTools.delimit(string, delimiter));
	}

	@Override
	protected void verifyStringDelimiter(String expected, String string, String delimiter) {
		Transformer<String, String> transformer = this.buildStringDelimiter(delimiter);
		assertEquals(expected, transformer.transform(string));
	}

	@Override
	protected Transformer<String, String> buildStringDelimiter(String string) {
		return new StringTools.StringDelimiter(string);
	}

	@Override
	protected void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) {
		assertEquals(expected, StringTools.removeFirstOccurrence(string, charToRemove));
	}

	@Override
	protected void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) {
		assertEquals(expected, StringTools.removeAllOccurrences(string, charToRemove));
	}

	@Override
	protected void verifyRemoveAllSpaces(String expected, String string) {
		assertEquals(expected, StringTools.removeAllSpaces(string));
	}

	@Override
	protected void verifyRemoveAllWhitespace(String expected, String string) {
		assertEquals(expected, StringTools.removeAllWhitespace(string));
	}

	@Override
	protected void verifyCompressWhitespace(String expected, String string) {
		assertEquals(expected, StringTools.compressWhitespace(string));
	}

	@Override
	protected void verifyCommonPrefixLength(int expected, String string1, String string2) {
		assertEquals(expected, StringTools.commonPrefixLength(string1, string2));
	}

	@Override
	protected void verifyCommonPrefixLengthMax(int expected, String string1, String string2, int max) {
		assertEquals(expected, StringTools.commonPrefixLength(string1, string2, max));
	}

	@Override
	protected void verifyCapitalize(String expected, String string) {
		assertEquals(expected, StringTools.capitalize(string));
		assertEquals(expected, StringTools.CAPITALIZER.transform(string));
	}

	@Override
	protected Object getCapitalizer() {
		return StringTools.CAPITALIZER;
	}

	@Override
	protected void verifyUncapitalize(String expected, String string) {
		assertEquals(expected, StringTools.uncapitalize(string));
		assertEquals(expected, StringTools.UNCAPITALIZER.transform(string));
	}

	@Override
	protected Object getUncapitalizer() {
		return StringTools.UNCAPITALIZER;
	}

	@Override
	protected void verifyIsBlank(boolean expected, String string) {
		assertEquals(expected, StringTools.isBlank(string));
		assertEquals(expected, StringTools.IS_BLANK.evaluate(string));
	}

	@Override
	protected Object getIsBlankPredicate() {
		return StringTools.IS_BLANK;
	}

	@Override
	protected void verifyIsNotBlank(boolean expected, String string) {
		assertEquals(expected, StringTools.isNotBlank(string));
		assertEquals(expected, StringTools.IS_NOT_BLANK.evaluate(string));
	}

	@Override
	protected Object getIsNotBlankPredicate() {
		return StringTools.IS_NOT_BLANK;
	}

	@Override
	protected void verifyEqualsIgnoreCase(boolean expected, String string1, String string2) {
		assertEquals(expected, StringTools.equalsIgnoreCase(string1, string2));
	}

	@Override
	protected void verifyStartsWithIgnoreCase(boolean expected, String string, String prefix) {
		assertEquals(expected, StringTools.startsWithIgnoreCase(string, prefix));
		Predicate<String> predicate = new StringTools.StartsWithIgnoreCase(prefix);
		assertEquals(expected, predicate.evaluate(string));
	}

	@Override
	protected void verifyIsUppercase(String string) {
		assertTrue(StringTools.isUppercase(string));
	}

	@Override
	protected void denyIsUppercase(String string) {
		assertFalse(StringTools.isUppercase(string));
	}

	@Override
	protected void verifyIsLowercase(String string) {
		assertTrue(StringTools.isLowercase(string));
	}

	@Override
	protected void denyIsLowercase(String string) {
		assertFalse(StringTools.isLowercase(string));
	}

	@Override
	protected byte[] convertHexStringToByteArray(String string) {
		return StringTools.convertHexStringToByteArray(string);
	}

	@Override
	protected void verifyConvertCamelCaseToAllCaps(String expected, String string) {
		assertEquals(expected, StringTools.convertCamelCaseToAllCaps(string));
	}

	@Override
	protected void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int max) {
		assertEquals(expected, StringTools.convertCamelCaseToAllCaps(string, max));
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string) {
		assertEquals(expected, StringTools.convertAllCapsToCamelCase(string));
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) {
		assertEquals(expected, StringTools.convertAllCapsToCamelCase(string, capFirst));
	}

	@Override
	protected void verifyIsQuoted(String string) {
		assertTrue(StringTools.isQuoted(string));
	}

	@Override
	protected void denyIsQuoted(String string) {
		assertFalse(StringTools.isQuoted(string));
	}

	@Override
	protected void verifyIsParenthetical(String string) {
		assertTrue(StringTools.isParenthetical(string));
	}

	@Override
	protected void denyIsParenthetical(String string) {
		assertFalse(StringTools.isParenthetical(string));
	}

	@Override
	protected void verifyIsBracketed(String string) {
		assertTrue(StringTools.isBracketed(string));
	}

	@Override
	protected void denyIsBracketed(String string) {
		assertFalse(StringTools.isBracketed(string));
	}

	@Override
	protected void verifyIsBraced(String string) {
		assertTrue(StringTools.isBraced(string));
	}

	@Override
	protected void denyIsBraced(String string) {
		assertFalse(StringTools.isBraced(string));
	}

	@Override
	protected void verifyIsChevroned(String string) {
		assertTrue(StringTools.isChevroned(string));
	}

	@Override
	protected void denyIsChevroned(String string) {
		assertFalse(StringTools.isChevroned(string));
	}

	@Override
	protected void verifyIsDelimited(String string, char c) {
		assertTrue(StringTools.isDelimited(string, c));
	}

	@Override
	protected void denyIsDelimited(String string, char c) {
		assertFalse(StringTools.isDelimited(string, c));
	}

	@Override
	protected void verifyIsDelimited2(String string, char start, char end) {
		assertTrue(StringTools.isDelimited(string, start, end));
	}

	@Override
	protected void denyIsDelimited2(String string, char start, char end) {
		assertFalse(StringTools.isDelimited(string, start, end));
	}

	@Override
	protected void verifyUndelimit(String expected, String string) {
		assertEquals(expected, StringTools.undelimit(string));
	}

	@Override
	protected void verifyUndelimitInt(String expected, String string, int count) {
		assertEquals(expected, StringTools.undelimit(string, count));
	}

	@Override
	protected void verifyConvertToJavaStringLiteral(String expected, String string) {
		assertEquals(expected, StringTools.convertToJavaStringLiteral(string));
		assertEquals(expected, StringTools.JAVA_STRING_LITERAL_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getJavaStringLiteralTransformer() {
		return StringTools.JAVA_STRING_LITERAL_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToJavaStringLiteralContent(String expected, String string) {
		assertEquals(expected, StringTools.convertToJavaStringLiteralContent(string));
		assertEquals(expected, StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getJavaStringLiteralContentTransformer() {
		return StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlAttributeValue(String expected, String string) {
		assertEquals(expected, StringTools.convertToXmlAttributeValue(string));
		assertEquals(expected, StringTools.XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getXmlAttributeValueTransformer() {
		return StringTools.XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) {
		assertEquals(expected, StringTools.convertToDoubleQuotedXmlAttributeValue(string));
		assertEquals(expected, StringTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getDoubleQuotedXmlAttributeValueTransformer() {
		return StringTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) {
		assertEquals(expected, StringTools.convertToDoubleQuotedXmlAttributeValueContent(string));
		assertEquals(expected, StringTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getDoubleQuotedXmlAttributeValueContentTransformer() {
		return StringTools.DOUBLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) {
		assertEquals(expected, StringTools.convertToSingleQuotedXmlAttributeValue(string));
		assertEquals(expected, StringTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getSingleQuotedXmlAttributeValueTransformer() {
		return StringTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) {
		assertEquals(expected, StringTools.convertToSingleQuotedXmlAttributeValueContent(string));
		assertEquals(expected, StringTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getSingleQuotedXmlAttributeValueContentTransformer() {
		return StringTools.SINGLE_QUOTED_XML_ATTRIBUTE_VALUE_CONTENT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementText(String expected, String string) {
		assertEquals(expected, StringTools.convertToXmlElementText(string));
		assertEquals(expected, StringTools.XML_ELEMENT_TEXT_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getXmlElementTextTransformer() {
		return StringTools.XML_ELEMENT_TEXT_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementCDATA(String expected, String string) {
		assertEquals(expected, StringTools.convertToXmlElementCDATA(string));
		assertEquals(expected, StringTools.XML_ELEMENT_CDATA_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getXmlElementCDATATransformer() {
		return StringTools.XML_ELEMENT_CDATA_TRANSFORMER;
	}

	@Override
	protected void verifyConvertToXmlElementCDATAContent(String expected, String string) {
		assertEquals(expected, StringTools.convertToXmlElementCDATAContent(string));
		assertEquals(expected, StringTools.XML_ELEMENT_CDATA_CONTENT_TRANSFORMER.transform(string));
	}

	@Override
	protected Object getXmlElementCDATAContentTransformer() {
		return StringTools.XML_ELEMENT_CDATA_CONTENT_TRANSFORMER;
	}

	@Override
	protected Class<?> getToolsClass() {
		return StringTools.class;
	}

	// ********** StringTools-specific **********

	public void testCharArrayTransformer() throws Exception {
		Transformer<String, char[]> transformer = StringTools.CHAR_ARRAY_TRANSFORMER;
		TestTools.assertEquals("foo", transformer.transform("foo"));
		assertEquals("CharArrayTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}
}
