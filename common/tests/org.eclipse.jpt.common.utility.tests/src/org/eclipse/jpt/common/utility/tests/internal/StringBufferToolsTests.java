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

import org.eclipse.jpt.common.utility.internal.StringBufferTools;
import org.junit.Assert;

public class StringBufferToolsTests
	extends AbstractStringBuilderToolsTests
{
	public StringBufferToolsTests(String name) {
		super(name);
	}

	@Override
	protected void verifyConvertToCharArray(String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		sb.append(string);
		Assert.assertArrayEquals(string.toCharArray(), StringBufferTools.convertToCharArray(sb));
	}

	@Override
	protected void verifyReverse(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.reverse(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.reverse(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCenter(String expected, String s, int len) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.center(sb, s, len);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.center(sb, s.toCharArray(), len);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyPad(String expected, String string, int length) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.pad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.pad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyFit(String expected, String string, int length) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.fit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.fit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroPad(String expected, String string, int length) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.zeroPad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.zeroPad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroFit(String expected, String string, int length) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.zeroFit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.zeroFit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRepeat(String expected, String string, int length) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.repeat(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.repeat(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifySeparate(String expected, String string, char separator, int segmentLength) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.separate(sb, string, separator, segmentLength);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.separate(sb, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyDelimit(String expected, String string, String delimiter) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.delimit(sb, string, delimiter);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.delimit(sb, string.toCharArray(), delimiter.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyQuote(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.quote(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.quote(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.removeFirstOccurrence(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.removeFirstOccurrence(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.removeAllOccurrences(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.removeAllOccurrences(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllSpaces(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.removeAllSpaces(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.removeAllSpaces(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllWhitespace(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.removeAllWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.removeAllWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCompressWhitespace(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.compressWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.compressWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCapitalize(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.capitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUncapitalize(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.uncapitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToHexString(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.convertToHexString(sb, string.getBytes());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCaps(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int maxLength) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string, maxLength);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string.toCharArray(), maxLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.convertAllCapsToCamelCase(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertAllCapsToCamelCase(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.convertAllCapsToCamelCase(sb, string, capFirst);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertAllCapsToCamelCase(sb, string.toCharArray(), capFirst);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimit(String expected, String s) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimitCount(String expected, String s, int count) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s, count);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s.toCharArray(), count);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteral(String expected, String s) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteral(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteral(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteralContent(String expected, String s) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteralContent(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteralContent(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlAttributeValue(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToDoubleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToDoubleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToSingleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToSingleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToSingleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToSingleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementText(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToXmlElementText(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToXmlElementText(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATA(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToXmlElementCDATA(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToXmlElementCDATA(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATAContent(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToXmlElementCDATAContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToXmlElementCDATAContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected Class<?> getToolsClass() {
		return StringBufferTools.class;
	}
}
