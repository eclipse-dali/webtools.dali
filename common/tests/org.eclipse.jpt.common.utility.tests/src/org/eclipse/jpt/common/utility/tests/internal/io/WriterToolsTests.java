/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.io;

import java.io.StringWriter;
import java.io.Writer;
import org.eclipse.jpt.common.utility.internal.io.WriterTools;
import org.eclipse.jpt.common.utility.tests.internal.AbstractStringBuilderToolsTests;

public class WriterToolsTests
	extends AbstractStringBuilderToolsTests
{
	public WriterToolsTests(String name) {
		super(name);
	}

	@Override
	protected void verifyConvertToCharArray(String string) throws Exception {
		// NOP
	}

	@Override
	protected void verifyReverse(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.reverse(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.reverse(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCenter(String expected, String s, int len) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.center(sb, s, len);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.center(sb, s.toCharArray(), len);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyPad(String expected, String string, int length) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.pad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.pad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyFit(String expected, String string, int length) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.fit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.fit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroPad(String expected, String string, int length) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.zeroPad(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.zeroPad(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyZeroFit(String expected, String string, int length) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.zeroFit(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.zeroFit(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRepeat(String expected, String string, int length) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.repeat(sb, string, length);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.repeat(sb, string.toCharArray(), length);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifySeparate(String expected, String string, char separator, int segmentLength) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.separate(sb, string, separator, segmentLength);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.separate(sb, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyDelimit(String expected, String string, String delimiter) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.delimit(sb, string, delimiter);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.delimit(sb, string.toCharArray(), delimiter.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyQuote(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.quote(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.quote(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.removeFirstOccurrence(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.removeFirstOccurrence(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllOccurrences(String expected, String string, char charToRemove) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.removeAllOccurrences(sb, string, charToRemove);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.removeAllOccurrences(sb, string.toCharArray(), charToRemove);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllSpaces(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.removeAllSpaces(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.removeAllSpaces(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyRemoveAllWhitespace(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.removeAllWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.removeAllWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCompressWhitespace(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.compressWhitespace(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.compressWhitespace(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyCapitalize(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.capitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUncapitalize(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.uncapitalize(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToHexString(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.convertToHexString(sb, string.getBytes());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCaps(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int maxLength) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(sb, string, maxLength);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(sb, string.toCharArray(), maxLength);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.convertAllCapsToCamelCase(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertAllCapsToCamelCase(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst) throws Exception {
		Writer sb;
		sb = new StringWriter();
		WriterTools.convertAllCapsToCamelCase(sb, string, capFirst);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertAllCapsToCamelCase(sb, string.toCharArray(), capFirst);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimit(String expected, String s) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.undelimit(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.undelimit(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyUndelimitCount(String expected, String s, int count) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.undelimit(sb, s, count);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.undelimit(sb, s.toCharArray(), count);
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteral(String expected, String s) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToJavaStringLiteral(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToJavaStringLiteral(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToJavaStringLiteralContent(String expected, String s) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToJavaStringLiteralContent(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToJavaStringLiteralContent(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlAttributeValue(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToDoubleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToDoubleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToDoubleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToSingleQuotedXmlAttributeValue(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToSingleQuotedXmlAttributeValue(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToSingleQuotedXmlAttributeValueContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToSingleQuotedXmlAttributeValueContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementText(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToXmlElementText(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToXmlElementText(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATA(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToXmlElementCDATA(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToXmlElementCDATA(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected void verifyConvertToXmlElementCDATAContent(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToXmlElementCDATAContent(sb, string);
		assertEquals(expected, sb.toString());

		sb = new StringWriter();
		WriterTools.convertToXmlElementCDATAContent(sb, string.toCharArray());
		assertEquals(expected, sb.toString());
	}

	@Override
	protected Class<?> getToolsClass() {
		return WriterTools.class;
	}
}
