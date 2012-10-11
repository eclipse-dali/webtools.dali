/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.io;

import java.io.StringWriter;
import java.io.Writer;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.io.WriterTools;

@SuppressWarnings("nls")
public class WriterToolsTests
	extends TestCase
{
	public WriterToolsTests(String name) {
		super(name);
	}

	// ********** padding/truncating/centering **********

	public void testCenter() throws Exception {
		this.verifyCenter("fred", "fred", 4);
		this.verifyCenter(" fred ", "fred", 6);
		this.verifyCenter(" fred  ", "fred", 7);
		this.verifyCenter("re", "fred", 2);
		this.verifyCenter("fre", "fred", 3);
	}

	private void verifyCenter(String expected, String s, int len) throws Exception {
		Writer writer;
		writer = new StringWriter();
		WriterTools.center(writer, s, len);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		WriterTools.center(writer, s.toCharArray(), len);
		assertEquals(expected, writer.toString());
	}

	public void testPad() throws Exception {
		Writer writer;
		writer = new StringWriter();
		WriterTools.pad(writer, "fred", 4);
		assertEquals("fred", writer.toString());

		writer = new StringWriter();
		WriterTools.pad(writer, "fred", 6);
		assertEquals("fred  ", writer.toString());

		writer = new StringWriter();
		boolean exThrown = false;
		try {
			WriterTools.pad(writer, "fred", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testFit() throws Exception {
		this.verifyFit("fred", "fred", 4);
		this.verifyFit("fred  ", "fred", 6);
		this.verifyFit("fr", "fred", 2);
	}

	private void verifyFit(String expected, String string, int length) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.fit(writer, string, length);
		assertEquals(expected, writer.toString());
	}

	public void testZeroPad() throws Exception {
		Writer writer;
		writer = new StringWriter();
		WriterTools.zeroPad(writer, "1234", 4);
		assertEquals("1234", writer.toString());

		writer = new StringWriter();
		WriterTools.zeroPad(writer, "1234", 6);
		assertEquals("001234", writer.toString());

		writer = new StringWriter();
		boolean exThrown = false;
		try {
			WriterTools.zeroPad(writer, "1234", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroFit() throws Exception {
		this.verifyZeroFit("1234", "1234", 4);
		this.verifyZeroFit("001234", "1234", 6);
		this.verifyZeroFit("34", "1234", 2);
	}

	private void verifyZeroFit(String expected, String string, int length) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.zeroFit(writer, string, length);
		assertEquals(expected, writer.toString());
	}

	public void testSeparateOnStringCharInt() throws Exception {
		this.verifySeparate("012345", '-', 22, "012345");
		this.verifySeparate("012345", '-',  6, "012345");
		this.verifySeparate("012345", '-',  5, "01234-5");
		this.verifySeparate("012345", '-',  4, "0123-45");
		this.verifySeparate("012345", '-',  3, "012-345");
		this.verifySeparate("012345", '-',  2, "01-23-45");
		this.verifySeparate("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparate(String string, char separator, int segmentLength, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.separate(writer, string, separator, segmentLength);
		assertEquals(expected, writer.toString());
	}

	public void testSeparateOnCharArrayCharInt() throws Exception {
		this.verifySeparateCharArray("012345", '-', 22, "012345");
		this.verifySeparateCharArray("012345", '-',  6, "012345");
		this.verifySeparateCharArray("012345", '-',  5, "01234-5");
		this.verifySeparateCharArray("012345", '-',  4, "0123-45");
		this.verifySeparateCharArray("012345", '-',  3, "012-345");
		this.verifySeparateCharArray("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArray("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArray(String string, char separator, int segmentLength, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.separate(writer, string.toCharArray(), separator, segmentLength);
		assertEquals(expected, writer.toString());
	}

	public void testDelimit() throws Exception {
		this.verifyDelimit("Employee", "123", "123Employee123");
		this.verifyDelimit("123", "123", "123123123");
		this.verifyDelimit("", "123", "123123");
	}

	private void verifyDelimit(String string, String delimiter, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.delimit(writer, string, delimiter);
		assertEquals(expectedString, writer.toString());
	}

	public void testQuote() throws Exception {
		this.verifyQuote("Employee", "\"Employee\"");
		this.verifyQuote("123", "\"123\"");
		this.verifyQuote("", "\"\"");
		this.verifyQuote("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuote(String string, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.quote(writer, string);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveFirstOccurrence() throws Exception {
		this.verifyRemoveFirstOccurrence("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrence("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrence("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrence("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrence("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrence(String string, char charToRemove, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.removeFirstOccurrence(writer, string, charToRemove);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveAllOccurrences() throws Exception {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.removeAllOccurrences(writer, string, charToRemove);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveAllWhitespace() throws Exception {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.removeAllWhitespace(writer, string);
		assertEquals(expectedString, writer.toString());
	}

	public void testCompressWhitespace() throws Exception {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.compressWhitespace(writer, string);
		assertEquals(expectedString, writer.toString());
	}

	public void testCapitalizeOnStringWriter() throws Exception {
		this.verifyCapitalizeOnStringWriter("Oracle", "Oracle");
		this.verifyCapitalizeOnStringWriter("Oracle", "oracle");
		this.verifyCapitalizeOnStringWriter("   ", "   ");
		this.verifyCapitalizeOnStringWriter("ORACLE", "ORACLE");
		this.verifyCapitalizeOnStringWriter("", "");
		this.verifyCapitalizeOnStringWriter("A", "a");
		this.verifyCapitalizeOnStringWriter("\u00C9cole", "\u00E9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyCapitalizeOnStringWriter(String expected, String string) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.capitalize(writer, string);
		assertEquals(expected, writer.toString());
	}

	public void testCapitalizeOnCharArray() throws Exception {
		this.verifyCapitalizeOnCharArray("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArray("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeOnCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeOnCharArray("", new char[0]);
		this.verifyCapitalizeOnCharArray("A", new char[] { 'a' });
		this.verifyCapitalizeOnCharArray("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeOnCharArray(String expected, char[] string) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.capitalize(writer, string);
		assertEquals(expected, writer.toString());
	}

	public void testUncapitalizeOnStringWriter() throws Exception {
		this.verifyUncapitalizeOnStringWriter("oracle", "Oracle");
		this.verifyUncapitalizeOnStringWriter("oracle", "oracle");
		this.verifyUncapitalizeOnStringWriter("   ", "   ");
		this.verifyUncapitalizeOnStringWriter("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnStringWriter("", "");
		this.verifyUncapitalizeOnStringWriter("a", "A");
		this.verifyUncapitalizeOnStringWriter("\u00E9cole", "\u00C9cole"); // ï¿½cole->ï¿½COLE
	}

	private void verifyUncapitalizeOnStringWriter(String expected, String string) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.uncapitalize(writer, string);
		assertEquals(expected, writer.toString());
	}

	public void testUncapitalizeOnCharArray() throws Exception {
		this.verifyUncapitalizeOnCharArray("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArray("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeOnCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeOnCharArray("", new char[0]);
		this.verifyUncapitalizeOnCharArray("a", new char[] { 'A' });
		this.verifyUncapitalizeOnCharArray("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeOnCharArray(String expected, char[] string) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.uncapitalize(writer, string);
		assertEquals(expected, writer.toString());
	}

	public void testConvertToHexString() throws Exception {
		this.verifyConvertToHexString("74657374", "test"); // Unicode values
	}

	public void testConvertToHexString_negative() throws Exception {
		this.verifyConvertToHexString("636166E9", "café"); // Unicode values
	}

	private void verifyConvertToHexString(String expected, String string) throws Exception {
		Writer sb = new StringWriter();
		WriterTools.convertToHexString(sb, string.getBytes());
		assertEquals(expected, sb.toString());
	}

	public void testConvertCamelCaseToAllCaps() throws Exception {
		this.verifyConvertCamelCaseToAllCaps("TEST", "test");
		this.verifyConvertCamelCaseToAllCaps("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	private void verifyConvertCamelCaseToAllCaps(String expected, String string) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(writer, string);
		assertEquals(expected, writer.toString());
	}

	public void testConvertCamelCaseToAllCapsMaxLength() throws Exception {
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

	private void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int max) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.convertCamelCaseToAllCaps(writer, string, max);
		assertEquals(expected, writer.toString());
	}

	public void testConvertUnderscoresToCamelCase() throws Exception {
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

	public void testConvertUnderscoresToCamelCaseLowercase() throws Exception {
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

	private void verifyConvertUnderscoresToCamelCase(String expected, String string, boolean capitalizeFirstLetter) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.convertAllCapsToCamelCase(writer, string, capitalizeFirstLetter);
		assertEquals(expected, writer.toString());
	}

	public void testUndelimit() throws Exception {
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

	private void verifyUndelimit(String s, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.undelimit(writer, s);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		WriterTools.undelimit(writer, s.toCharArray());
		assertEquals(expected, writer.toString());
	}

	public void testUndelimitCount() throws Exception {
		this.verifyUndelimitCount("\"foo\"", 2, "o");
		this.verifyUndelimitCount("\"\"\"\"", 2, "");
		this.verifyUndelimitCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitCount(String s, int count, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.undelimit(writer, s, count);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		WriterTools.undelimit(writer, s.toCharArray(), count);
		assertEquals(expected, writer.toString());
	}

	public void testConvertToJavaStringLiteral() throws Exception {
		this.verifyConvertToJavaStringLiteral("", "\"\"");
		this.verifyConvertToJavaStringLiteral("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteral("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteral("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteral("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteral("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteral("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteral(String s, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.convertToJavaStringLiteral(writer, s);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		WriterTools.convertToJavaStringLiteral(writer, s.toCharArray());
		assertEquals(expected, writer.toString());
	}

	public void testConvertToJavaStringLiteralContent() throws Exception {
		this.verifyConvertToJavaStringLiteralContent("", "");
		this.verifyConvertToJavaStringLiteralContent("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContent(String s, String expected) throws Exception {
		Writer writer = new StringWriter();
		WriterTools.convertToJavaStringLiteralContent(writer, s);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		WriterTools.convertToJavaStringLiteralContent(writer, s.toCharArray());
		assertEquals(expected, writer.toString());
	}
}
