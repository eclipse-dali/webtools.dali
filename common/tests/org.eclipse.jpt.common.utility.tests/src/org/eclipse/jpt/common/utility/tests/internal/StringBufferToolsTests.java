/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StringBufferTools;

@SuppressWarnings("nls")
public class StringBufferToolsTests
	extends TestCase
{
	public StringBufferToolsTests(String name) {
		super(name);
	}

	// ********** padding/truncating/centering/repeating **********

	public void testCenter() {
		this.verifyCenter("fred", "fred", 4);
		this.verifyCenter(" fred ", "fred", 6);
		this.verifyCenter(" fred  ", "fred", 7);
		this.verifyCenter("re", "fred", 2);
		this.verifyCenter("fre", "fred", 3);
	}

	private void verifyCenter(String expected, String s, int len) {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.center(sb, s, len);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.center(sb, s.toCharArray(), len);
		assertEquals(expected, sb.toString());
	}

	public void testPad() {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.pad(sb, "fred", 4);
		assertEquals("fred", sb.toString());

		sb = new StringBuffer();
		StringBufferTools.pad(sb, "fred", 6);
		assertEquals("fred  ", sb.toString());

		sb = new StringBuffer();
		boolean exThrown = false;
		try {
			StringBufferTools.pad(sb, "fred", 2);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.fit(sb, string, length);
		assertEquals(expected, sb.toString());
	}

	public void testZeroPad() {
		StringBuffer sb;
		sb = new StringBuffer();
		StringBufferTools.zeroPad(sb, "1234", 4);
		assertEquals("1234", sb.toString());

		sb = new StringBuffer();
		StringBufferTools.zeroPad(sb, "1234", 6);
		assertEquals("001234", sb.toString());

		sb = new StringBuffer();
		boolean exThrown = false;
		try {
			StringBufferTools.zeroPad(sb, "1234", 2);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.zeroFit(sb, string, length);
		assertEquals(expected, sb.toString());
	}

	public void testRepeat() {
		this.verifyRepeat("", "1234", 0);
		this.verifyRepeat("12", "1234", 2);
		this.verifyRepeat("1234", "1234", 4);
		this.verifyRepeat("123412", "1234", 6);
		this.verifyRepeat("12341234", "1234", 8);
		this.verifyRepeat("123412341234123412341", "1234", 21);
	}

	private void verifyRepeat(String expected, String string, int length) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.repeat(sb, string, length);
		assertEquals(expected, sb.toString());
	}

	public void testRepeatCharArray() {
		this.verifyRepeatCharArray("", "1234", 0);
		this.verifyRepeatCharArray("12", "1234", 2);
		this.verifyRepeatCharArray("1234", "1234", 4);
		this.verifyRepeatCharArray("123412", "1234", 6);
		this.verifyRepeatCharArray("12341234", "1234", 8);
		this.verifyRepeatCharArray("123412341234123412341", "1234", 21);
	}

	private void verifyRepeatCharArray(String expected, String string, int length) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.repeat(sb, string.toCharArray(), length);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.separate(sb, string, separator, segmentLength);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.separate(sb, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, sb.toString());
	}

	public void testDelimit() {
		this.verifyDelimit("Employee", "123", "123Employee123");
		this.verifyDelimit("123", "123", "123123123");
		this.verifyDelimit("", "123", "123123");
	}

	private void verifyDelimit(String string, String delimiter, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.delimit(sb, string, delimiter);
		assertEquals(expectedString, sb.toString());
	}

	public void testQuote() {
		this.verifyQuote("Employee", "\"Employee\"");
		this.verifyQuote("123", "\"123\"");
		this.verifyQuote("", "\"\"");
		this.verifyQuote("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuote(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.quote(sb, string);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.removeFirstOccurrence(sb, string, charToRemove);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.removeAllOccurrences(sb, string, charToRemove);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.removeAllWhitespace(sb, string);
		assertEquals(expectedString, sb.toString());
	}

	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.compressWhitespace(sb, string);
		assertEquals(expectedString, sb.toString());
	}

	public void testCapitalizeOnStringStringBuffer() {
		this.verifyCapitalizeOnStringStringBuffer("Oracle", "Oracle");
		this.verifyCapitalizeOnStringStringBuffer("Oracle", "oracle");
		this.verifyCapitalizeOnStringStringBuffer("   ", "   ");
		this.verifyCapitalizeOnStringStringBuffer("ORACLE", "ORACLE");
		this.verifyCapitalizeOnStringStringBuffer("", "");
		this.verifyCapitalizeOnStringStringBuffer("A", "a");
		this.verifyCapitalizeOnStringStringBuffer("\u00C9cole", "\u00E9cole"); // e'cole -> E'cole
	}

	private void verifyCapitalizeOnStringStringBuffer(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.capitalize(sb, string);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.capitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnString() {
		this.verifyUncapitalizeOnString("oracle", "Oracle");
		this.verifyUncapitalizeOnString("oracle", "oracle");
		this.verifyUncapitalizeOnString("   ", "   ");
		this.verifyUncapitalizeOnString("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnString("", "");
		this.verifyUncapitalizeOnString("a", "A");
		this.verifyUncapitalizeOnString("\u00E9cole", "\u00C9cole"); // E'cole -> e'cole
	}

	private void verifyUncapitalizeOnString(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.uncapitalize(sb, string);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.uncapitalize(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToHexString() {
		this.verifyConvertToHexString("74657374", "test"); // UTF-8 values
	}

	public void testConvertToHexString_negative() {
		this.verifyConvertToHexString(this.getHexCafe(), "caf\u00E9"); // UTF-8 values
	}

	private String getHexCafe() {
		return StringToolsTests.getHexCafe();
	}

	private void verifyConvertToHexString(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToHexString(sb, string.getBytes());
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string);
		assertEquals(expected, sb.toString());
	}

	public void testConvertCamelCaseToAllCapsMaxLength() {
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "test", 44);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "test", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TES", "test", 3);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "TEST", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TE", "TEST", 2);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST", "testTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TES", "testTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_T", "testTest", 6);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_", "testTest", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST", "testTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST", "TestTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST", "TestTest", 1100);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_", "testTESTTest", 10);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST", "TestTESTTest", 14);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST_T", "TestTESTTestT", 16);
		this.verifyConvertCamelCaseToAllCapsMaxLength("TEST_TEST_TEST_", "TestTESTTestT", 15);
	}

	private void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int max) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertCamelCaseToAllCaps(sb, string, max);
		assertEquals(expected, sb.toString());
	}

	public void testConvertUnderscoresToCamelCase() {
		this.verifyConvertUnderscoresToCamelCase("test", "TEST", false);
		this.verifyConvertUnderscoresToCamelCase("test", "TEST_", false);
		this.verifyConvertUnderscoresToCamelCase("test", "TEST____", false);
		this.verifyConvertUnderscoresToCamelCase("Test", "TEST", true);
		this.verifyConvertUnderscoresToCamelCase("test", "TeST", false);
		this.verifyConvertUnderscoresToCamelCase("testTest", "TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCase("testTest", "TEST___TEST", false);
		this.verifyConvertUnderscoresToCamelCase("TestTest", "TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCase("testTestTest", "TEST_TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCase("TestTestTest", "TEST_TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "_TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "__TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "_TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "__TEST_TEST_TEST_T", true);
	}

	public void testConvertUnderscoresToCamelCaseLowercase() {
		this.verifyConvertUnderscoresToCamelCase("test", "test", false);
		this.verifyConvertUnderscoresToCamelCase("test", "test_", false);
		this.verifyConvertUnderscoresToCamelCase("test", "test____", false);
		this.verifyConvertUnderscoresToCamelCase("Test", "test", true);
		this.verifyConvertUnderscoresToCamelCase("test", "test", false);
		this.verifyConvertUnderscoresToCamelCase("testTest", "test_test", false);
		this.verifyConvertUnderscoresToCamelCase("testTest", "test___test", false);
		this.verifyConvertUnderscoresToCamelCase("TestTest", "test_test", true);
		this.verifyConvertUnderscoresToCamelCase("testTestTest", "test_test_test", false);
		this.verifyConvertUnderscoresToCamelCase("TestTestTest", "test_test_test", true);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "_test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCase("testTestTestT", "__test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "_test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCase("TestTestTestT", "__test_test_test_t", true);
	}

	private void verifyConvertUnderscoresToCamelCase(String expected, String string, boolean capitalizeFirstLetter) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertAllCapsToCamelCase(sb, string, capitalizeFirstLetter);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}

	public void testUndelimitCount() {
		this.verifyUndelimitCount("\"foo\"", 2, "o");
		this.verifyUndelimitCount("\"\"\"\"", 2, "");
		this.verifyUndelimitCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitCount(String s, int count, String expected) {
		StringBuffer sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s, count);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.undelimit(sb, s.toCharArray(), count);
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteral(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteral(sb, s.toCharArray());
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
		StringBuffer sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteralContent(sb, s);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringBufferTools.convertToJavaStringLiteralContent(sb, s.toCharArray());
		assertEquals(expected, sb.toString());
	}
}
