/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;

@SuppressWarnings("nls")
public class StringBuilderToolsTests
	extends TestCase
{
	public StringBuilderToolsTests(String name) {
		super(name);
	}

	// ********** padding/truncating/centering **********

	public void testCenter() {
		this.verifyCenter("fred", "fred", 4);
		this.verifyCenter(" fred ", "fred", 6);
		this.verifyCenter(" fred  ", "fred", 7);
		this.verifyCenter("re", "fred", 2);
		this.verifyCenter("fre", "fred", 3);
	}

	private void verifyCenter(String expected, String s, int len) {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.center(sb, s, len);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.center(sb, s.toCharArray(), len);
		assertEquals(expected, sb.toString());
	}

	public void testPad() {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.pad(sb, "fred", 4);
		assertEquals("fred", sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.pad(sb, "fred", 6);
		assertEquals("fred  ", sb.toString());

		sb = new StringBuilder();
		boolean exThrown = false;
		try {
			StringBuilderTools.pad(sb, "fred", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testFit() {
		this.verifyFit("fred", "fred", 4);
		this.verifyFit("fred  ", "fred", 6);
		this.verifyFit("fr", "fred", 2);
	}

	private void verifyFit(String expected, String string, int length) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.fit(sb, string, length);
		assertEquals(expected, sb.toString());
	}

	public void testZeroPad() {
		StringBuilder sb;
		sb = new StringBuilder();
		StringBuilderTools.zeroPad(sb, "1234", 4);
		assertEquals("1234", sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.zeroPad(sb, "1234", 6);
		assertEquals("001234", sb.toString());

		sb = new StringBuilder();
		boolean exThrown = false;
		try {
			StringBuilderTools.zeroPad(sb, "1234", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroFit() {
		this.verifyZeroFit("1234", "1234", 4);
		this.verifyZeroFit("001234", "1234", 6);
		this.verifyZeroFit("34", "1234", 2);
	}

	private void verifyZeroFit(String expected, String string, int length) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.zeroFit(sb, string, length);
		assertEquals(expected, sb.toString());
	}

	public void testSeparateOnStringCharInt() {
		this.verifySeparate("012345", '-', 22, "012345");
		this.verifySeparate("012345", '-',  6, "012345");
		this.verifySeparate("012345", '-',  5, "01234-5");
		this.verifySeparate("012345", '-',  4, "0123-45");
		this.verifySeparate("012345", '-',  3, "012-345");
		this.verifySeparate("012345", '-',  2, "01-23-45");
		this.verifySeparate("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparate(String string, char separator, int segmentLength, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.separate(sb, string, separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	public void testSeparateOnCharArrayCharInt() {
		this.verifySeparateCharArray("012345", '-', 22, "012345");
		this.verifySeparateCharArray("012345", '-',  6, "012345");
		this.verifySeparateCharArray("012345", '-',  5, "01234-5");
		this.verifySeparateCharArray("012345", '-',  4, "0123-45");
		this.verifySeparateCharArray("012345", '-',  3, "012-345");
		this.verifySeparateCharArray("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArray("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArray(String string, char separator, int segmentLength, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.separate(sb, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	public void testDelimit() {
		this.verifyDelimit("Employee", "123", "123Employee123");
		this.verifyDelimit("123", "123", "123123123");
		this.verifyDelimit("", "123", "123123");
	}

	private void verifyDelimit(String string, String delimiter, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.delimit(sb, string, delimiter);
		assertEquals(expectedString, sb.toString());
	}

	public void testQuote() {
		this.verifyQuote("Employee", "\"Employee\"");
		this.verifyQuote("123", "\"123\"");
		this.verifyQuote("", "\"\"");
		this.verifyQuote("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuote(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.quote(sb, string);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveFirstOccurrence() {
		this.verifyRemoveFirstOccurrence("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrence("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrence("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrence("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrence("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrence(String string, char charToRemove, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.removeFirstOccurrence(sb, string, charToRemove);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.removeAllOccurrences(sb, string, charToRemove);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.removeAllWhitespace(sb, string);
		assertEquals(expectedString, sb.toString());
	}

	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.compressWhitespace(sb, string);
		assertEquals(expectedString, sb.toString());
	}

	public void testCapitalizeOnString() {
		this.verifyCapitalizeOnString("Oracle", "Oracle");
		this.verifyCapitalizeOnString("Oracle", "oracle");
		this.verifyCapitalizeOnString("   ", "   ");
		this.verifyCapitalizeOnString("ORACLE", "ORACLE");
		this.verifyCapitalizeOnString("", "");
		this.verifyCapitalizeOnString("A", "a");
		this.verifyCapitalizeOnString("\u00C9cole", "\u00E9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyCapitalizeOnString(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testCapitalizeOnCharArray() {
		this.verifyCapitalizeOnCharArray("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArray("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeOnCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeOnCharArray("", new char[0]);
		this.verifyCapitalizeOnCharArray("A", new char[] { 'a' });
		this.verifyCapitalizeOnCharArray("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeOnCharArray(String expected, char[] string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnString() {
		this.verifyUncapitalizeOnString("oracle", "Oracle");
		this.verifyUncapitalizeOnString("oracle", "oracle");
		this.verifyUncapitalizeOnString("   ", "   ");
		this.verifyUncapitalizeOnString("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnString("", "");
		this.verifyUncapitalizeOnString("a", "A");
		this.verifyUncapitalizeOnString("\u00E9cole", "\u00C9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyUncapitalizeOnString(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnCharArray() {
		this.verifyUncapitalizeOnCharArray("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArray("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeOnCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeOnCharArray("", new char[0]);
		this.verifyUncapitalizeOnCharArray("a", new char[] { 'A' });
		this.verifyUncapitalizeOnCharArray("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeOnCharArray(String expected, char[] string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToHexString() {
		this.verifyConvertToHexString("74657374", "test"); // Unicode values
	}

	public void testConvertToHexString_negative() {
		this.verifyConvertToHexString("636166E9", "café"); // Unicode values
	}

	private void verifyConvertToHexString(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToHexString(sb, string.getBytes());
		assertEquals(expected, sb.toString());
	}

	public void testConvertCamelCaseToAllCaps() {
		this.verifyConvertCamelCaseToAllCaps("TEST", "test");
		this.verifyConvertCamelCaseToAllCaps("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	private void verifyConvertCamelCaseToAllCaps(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertCamelCaseToAllCaps(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testUndelimit() {
		this.verifyUndelimit("\"foo\"", "foo");
		this.verifyUndelimit("\"\"", "");
		this.verifyUndelimit("'foo'", "foo");
		this.verifyUndelimit("\"fo\"\"o\"", "fo\"o");
		this.verifyUndelimit("\"foo\"\"\"", "foo\"");
		this.verifyUndelimit("\"\"\"foo\"", "\"foo");
		this.verifyUndelimit("[foo]", "foo");
		this.verifyUndelimit("\"\"\"", "\"");
		this.verifyUndelimit("\"foo\"bar\"", "foo\"");
		this.verifyUndelimit("\"foo\"\"", "foo\"");
	}

	private void verifyUndelimit(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	public void testUndelimitCount() {
		this.verifyUndelimitCount("\"foo\"", 2, "o");
		this.verifyUndelimitCount("\"\"\"\"", 2, "");
		this.verifyUndelimitCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitCount(String s, int count, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s, count);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.undelimit(sb, s.toCharArray(), count);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteral() {
		this.verifyConvertToJavaStringLiteral("", "\"\"");
		this.verifyConvertToJavaStringLiteral("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteral("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteral("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteral("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteral("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteral("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteral(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteral(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteral(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteralContent() {
		this.verifyConvertToJavaStringLiteralContent("", "");
		this.verifyConvertToJavaStringLiteralContent("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContent(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteralContent(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringBuilderTools.convertToJavaStringLiteralContent(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}
}
