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

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ByteArrayTools;
import org.eclipse.jpt.common.utility.internal.CharArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.junit.Assert;

@SuppressWarnings("nls")
public class CharArrayToolsTests
	extends TestCase
{
	// ********** padding/truncating/centering/repeating **********

	public void testCenter() {
		TestTools.assertEquals("fred", CharArrayTools.center("fred".toCharArray(), 4));
		TestTools.assertEquals(" fred ", CharArrayTools.center("fred".toCharArray(), 6));
		TestTools.assertEquals(" fred  ", CharArrayTools.center("fred".toCharArray(), 7));
		TestTools.assertEquals("re", CharArrayTools.center("fred".toCharArray(), 2));
		TestTools.assertEquals("fre", CharArrayTools.center("fred".toCharArray(), 3));
	}

	public void testPad() {
		TestTools.assertEquals("fred", CharArrayTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 4));
		TestTools.assertEquals("fred  ", CharArrayTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 6));
		boolean exThrown = false;
		try {
			TestTools.assertEquals("fr", CharArrayTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testFit() {
		TestTools.assertEquals("fred", CharArrayTools.fit(new char[] { 'f', 'r', 'e', 'd' }, 4));
		TestTools.assertEquals("fred  ", CharArrayTools.fit(new char[] { 'f', 'r', 'e', 'd' }, 6));
		TestTools.assertEquals("fr", CharArrayTools.fit(new char[] { 'f', 'r', 'e', 'd' }, 2));
	}

	public void testZeroPad() {
		TestTools.assertEquals("1234", CharArrayTools.zeroPad(new char[] { '1', '2', '3', '4' }, 4));
		TestTools.assertEquals("001234", CharArrayTools.zeroPad(new char[] { '1', '2', '3', '4' }, 6));
		boolean exThrown = false;
		try {
			TestTools.assertEquals("12", CharArrayTools.zeroPad(new char[] { '1', '2', '3', '4' }, 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroFit() {
		TestTools.assertEquals("1234", CharArrayTools.zeroFit(new char[] { '1', '2', '3', '4' }, 4));
		TestTools.assertEquals("001234", CharArrayTools.zeroFit(new char[] { '1', '2', '3', '4' }, 6));
		TestTools.assertEquals("34", CharArrayTools.zeroFit(new char[] { '1', '2', '3', '4' }, 2));
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
		TestTools.assertEquals(expected, CharArrayTools.repeat(string.toCharArray(), length));
	}

	// ********** separating **********

	public void testSeparate() {
		this.verifySeparate("012345", '-', 22, "012345");
		this.verifySeparate("012345", '-',  6, "012345");
		this.verifySeparate("012345", '-',  5, "01234-5");
		this.verifySeparate("012345", '-',  4, "0123-45");
		this.verifySeparate("012345", '-',  3, "012-345");
		this.verifySeparate("012345", '-',  2, "01-23-45");
		this.verifySeparate("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparate(String string, char separator, int segmentLength, String expected) {
		TestTools.assertEquals(expected, CharArrayTools.separate(string.toCharArray(), separator, segmentLength));
	}

	// ********** removing characters **********

	public void testRemoveFirstOccurrence() {
		this.verifyRemoveFirstOccurrence("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrence("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrence("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrence("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrence("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrence(String string, char charToRemove, String expectedString) {
		TestTools.assertEquals(expectedString, CharArrayTools.removeFirstOccurrence(string.toCharArray(), charToRemove));
	}

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) {
		TestTools.assertEquals(expectedString, CharArrayTools.removeAllOccurrences(string.toCharArray(), charToRemove));
	}

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) {
		TestTools.assertEquals(expectedString, CharArrayTools.removeAllWhitespace(string.toCharArray()));
	}

	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) {
		TestTools.assertEquals(expectedString, CharArrayTools.compressWhitespace(string.toCharArray()));
	}

	// ********** common prefix **********

	// ********** capitalization **********

	public void testCapitalize() {
		this.verifyCapitalize("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalize("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalize("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalize("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalize("", new char[0]);
		this.verifyCapitalize("A", new char[] { 'a' });
		this.verifyCapitalize("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalize(String expected, char[] string) {
		TestTools.assertEquals(expected, CharArrayTools.capitalize(string));
	}

	public void testUnapitalize() {
		this.verifyUncapitalize("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalize("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalize("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalize("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalize("", new char[0]);
		this.verifyUncapitalize("a", new char[] { 'A' });
		this.verifyUncapitalize("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalize(String expected, char[] string) {
		TestTools.assertEquals(expected, CharArrayTools.uncapitalize(string));
	}

	// ********** queries **********

	public void testIsBlank() {
		assertTrue(CharArrayTools.isBlank((char[]) null));
		this.verifyIsBlank("");
		this.verifyIsBlank("      \t\t   ");
		this.verifyIsBlank("      ");
		this.verifyIsBlank("      \t\t   " + StringTools.CR);
	}

	private void verifyIsBlank(String string) {
		assertTrue(CharArrayTools.isBlank(string.toCharArray()));
	}

	public void testEqualsIgnoreCase() {
		assertTrue(CharArrayTools.equalsIgnoreCase((char[]) null, (char[]) null));
		assertFalse(CharArrayTools.equalsIgnoreCase((char[]) null, "asdf".toCharArray()));
		assertFalse(CharArrayTools.equalsIgnoreCase("asdf".toCharArray(), (char[]) null));
		assertTrue(CharArrayTools.equalsIgnoreCase("asdf".toCharArray(), "asdf".toCharArray()));
		assertTrue(CharArrayTools.equalsIgnoreCase("asdf".toCharArray(), "ASDF".toCharArray()));
	}

	public void testStartsWithIgnoreCase() {
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "as".toCharArray()));
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "aS".toCharArray()));
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "".toCharArray()));
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "A".toCharArray()));
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "ASDF".toCharArray()));
		assertTrue(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "asdf".toCharArray()));

		assertFalse(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "bsdf".toCharArray()));
		assertFalse(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "g".toCharArray()));
		assertFalse(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "asdg".toCharArray()));
		assertFalse(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "asdfg".toCharArray()));
		assertFalse(CharArrayTools.startsWithIgnoreCase("asdf".toCharArray(), "asdfgggggg".toCharArray()));
	}

	public void testIsUppercase() {
		this.verifyIsUppercase("FOO");
		this.verifyIsUppercase("FOO2");
		this.verifyIsUppercase("F O O");
		this.denyIsUppercase("Foo");
		this.denyIsUppercase("");
	}

	private void verifyIsUppercase(String s) {
		assertTrue(CharArrayTools.isUppercase(s.toCharArray()));
	}

	private void denyIsUppercase(String s) {
		assertFalse(CharArrayTools.isUppercase(s.toCharArray()));
	}

	public void testIsLowercase() {
		this.verifyIsLowercase("foo");
		this.verifyIsLowercase("foo2");
		this.verifyIsLowercase("f o o");
		this.denyIsLowercase("Foo");
		this.denyIsLowercase("");
	}

	private void verifyIsLowercase(String s) {
		assertTrue(CharArrayTools.isLowercase(s.toCharArray()));
	}

	private void denyIsLowercase(String s) {
		assertFalse(CharArrayTools.isLowercase(s.toCharArray()));
	}

	// ********** byte arrays **********

	public void testConvertHexStringToByteArray_empty() throws Exception {
		char[] s = CharArrayTools.EMPTY_CHAR_ARRAY;
		byte[] byteArray = CharArrayTools.convertHexStringToByteArray(s);
		assertEquals(0, byteArray.length);
		assertTrue(Arrays.equals(ByteArrayTools.EMPTY_BYTE_ARRAY, byteArray));
	}

	public void testConvertHexStringToByteArray_oddLength() throws Exception {
		String s = "CAFEE";
		boolean exCaught = false;
		try {
			byte[] byteArray = CharArrayTools.convertHexStringToByteArray(s.toCharArray());
			fail("bogus byte array: " + Arrays.toString(byteArray));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConvertHexStringToByteArray_illegalCharacter1() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFEX0CAFE");
	}

	public void testConvertHexStringToByteArray_illegalCharacter2() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFE0XCAFE");
	}

	private void verifyConvertHexStringToByteArray_illegalCharacter(String s) throws Exception {
		boolean exCaught = false;
		try {
			byte[] byteArray = CharArrayTools.convertHexStringToByteArray(s.toCharArray());
			fail("bogus byte array: " + Arrays.toString(byteArray));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConvertHexStringToByteArray_ok() throws Exception {
		String s = "74657374"; // UTF-8 values
		TestTools.assertEquals("test", CharArrayTools.convertHexStringToByteArray(s.toCharArray()));
	}

	public void testConvertHexStringToByteArray_negative() throws Exception {
		String s = this.getHexCafe();
		TestTools.assertEquals("caf\u00E9", CharArrayTools.convertHexStringToByteArray(s.toCharArray()));
	}

	public void testConvertHexStringToByteArray_lowercase() throws Exception {
		String s = this.getHexCafe().toLowerCase();
		TestTools.assertEquals("caf\u00E9", CharArrayTools.convertHexStringToByteArray(s.toCharArray()));
	}

	private String getHexCafe() {
		return StringToolsTests.getHexCafe();
	}

	// ********** convert camel-case to all-caps **********

	// ********** convert all-caps to camel case **********

	// ********** delimiting **********

	public void testIsQuoted() {
		this.denyIsQuoted("foo");
		this.verifyIsQuoted("\"foo\"");

		this.denyIsQuoted("");
		this.verifyIsQuoted("\"\"");

		this.denyIsQuoted("\"");
		this.denyIsQuoted(" ");
		this.denyIsQuoted("''");
		this.denyIsQuoted("'foo'");
	}

	private void verifyIsQuoted(String s) {
		assertTrue(CharArrayTools.isQuoted(s.toCharArray()));
	}

	private void denyIsQuoted(String s) {
		assertFalse(CharArrayTools.isQuoted(s.toCharArray()));
	}

	public void testIsParenthetical() {
		this.denyIsParenthetical("foo");
		this.verifyIsParenthetical("(foo)");

		this.denyIsParenthetical("");
		this.verifyIsParenthetical("()");

		this.denyIsParenthetical("(");
		this.denyIsParenthetical(" ");
		this.denyIsParenthetical("''");
		this.denyIsParenthetical("'foo'");
	}

	private void verifyIsParenthetical(String s) {
		assertTrue(CharArrayTools.isParenthetical(s.toCharArray()));
	}

	private void denyIsParenthetical(String s) {
		assertFalse(CharArrayTools.isParenthetical(s.toCharArray()));
	}

	public void testIsBracketed() {
		this.denyIsBracketed("foo");
		this.verifyIsBracketed("[foo]");

		this.denyIsBracketed("");
		this.verifyIsBracketed("[]");

		this.denyIsBracketed("[");
		this.denyIsBracketed(" ");
		this.denyIsBracketed("''");
		this.denyIsBracketed("'foo'");
	}

	private void verifyIsBracketed(String s) {
		assertTrue(CharArrayTools.isBracketed(s.toCharArray()));
	}

	private void denyIsBracketed(String s) {
		assertFalse(CharArrayTools.isBracketed(s.toCharArray()));
	}

	public void testIsBraced() {
		this.denyIsBraced("foo");
		this.verifyIsBraced("{foo}");

		this.denyIsBraced("");
		this.verifyIsBraced("{}");

		this.denyIsBraced("{");
		this.denyIsBraced(" ");
		this.denyIsBraced("''");
		this.denyIsBraced("'foo'");
	}

	private void verifyIsBraced(String s) {
		assertTrue(CharArrayTools.isBraced(s.toCharArray()));
	}

	private void denyIsBraced(String s) {
		assertFalse(CharArrayTools.isBraced(s.toCharArray()));
	}

	public void testIsChevroned() {
		this.denyIsChevroned("foo");
		this.verifyIsChevroned("<foo>");

		this.denyIsChevroned("");
		this.verifyIsChevroned("<>");

		this.denyIsChevroned("{");
		this.denyIsChevroned(" ");
		this.denyIsChevroned("''");
		this.denyIsChevroned("'foo'");
	}

	private void verifyIsChevroned(String s) {
		assertTrue(CharArrayTools.isChevroned(s.toCharArray()));
	}

	private void denyIsChevroned(String s) {
		assertFalse(CharArrayTools.isChevroned(s.toCharArray()));
	}

	public void testIsDelimited() {
		this.denyIsDelimited("foo", '?');
		this.verifyIsDelimited("?foo?", '?');

		this.denyIsDelimited("", '?');
		this.verifyIsDelimited("\"\"", '"');
		this.verifyIsDelimited("?xx?", '?');
		this.denyIsDelimited("?xx]", '?');

		this.denyIsDelimited("\"", '"');
		this.denyIsDelimited(" ", ' ');
		this.denyIsDelimited("''", '"');
		this.denyIsDelimited("'foo'", '?');
	}

	private void verifyIsDelimited(String s, char c) {
		assertTrue(CharArrayTools.isDelimited(s.toCharArray(), c));
	}

	private void denyIsDelimited(String s, char c) {
		assertFalse(CharArrayTools.isDelimited(s.toCharArray(), c));
	}

	public void testIsDelimited2() {
		this.denyIsDelimited2("foo", '[', ']');
		this.verifyIsDelimited2("{foo}", '{', '}');

		this.denyIsDelimited2("", '[', ']');
		this.verifyIsDelimited2("[]", '[', ']');
		this.verifyIsDelimited2("[xx]", '[', ']');
		this.denyIsDelimited2("?xx]", '[', ']');

		this.denyIsDelimited2("\"", '[', ']');
		this.denyIsDelimited2(" ", '[', ']');
		this.denyIsDelimited2("''", '[', ']');
		this.denyIsDelimited2("'foo'", '[', ']');
	}

	private void verifyIsDelimited2(String s, char start, char end) {
		assertTrue(CharArrayTools.isDelimited(s.toCharArray(), start, end));
	}

	private void denyIsDelimited2(String s, char start, char end) {
		assertFalse(CharArrayTools.isDelimited(s.toCharArray(), start, end));
	}

	// ********** undelimiting **********

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
		TestTools.assertEquals(expected, CharArrayTools.undelimit(s.toCharArray()));
	}

	public void testUndelimitInt() {
		this.verifyUndelimitInt("\"foo\"", 2, "o");
		this.verifyUndelimitInt("\"\"foo\"\"", 2, "foo");
		this.verifyUndelimitInt("'foo'", 2, "o");
	}

	private void verifyUndelimitInt(String s, int count, String expected) {
		TestTools.assertEquals(expected, CharArrayTools.undelimit(s.toCharArray(), count));
	}

	public void testUndelimitIntException() {
		this.denyUndelimitInt("\"\"", 2);
		this.denyUndelimitInt("'o'", 2);
	}

	private void denyUndelimitInt(String s, int count) {
		boolean exCaught = false;
		try {
			char[] bogus = CharArrayTools.undelimit(s.toCharArray(), count);
			fail("invalid string: " + new String(bogus));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	// ********** converting to Java string literal **********

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
		TestTools.assertEquals(expected, CharArrayTools.convertToJavaStringLiteral(s.toCharArray()));
	}

	// ********** converting to XML **********

	public void testConvertToXmlAttributeValue() {
		this.verifyConvertToXmlAttributeValue("", "\"\"");
		this.verifyConvertToXmlAttributeValue("\"", "'\"'");
		this.verifyConvertToXmlAttributeValue("\"\"", "'\"\"'");
		this.verifyConvertToXmlAttributeValue("'", "\"'\"");
		this.verifyConvertToXmlAttributeValue("''", "\"''\"");
		this.verifyConvertToXmlAttributeValue("\"'\"", "\"&quot;'&quot;\"");
		this.verifyConvertToXmlAttributeValue("\"''\"", "\"&quot;''&quot;\"");
		this.verifyConvertToXmlAttributeValue("'foo'", "\"'foo'\"");
		this.verifyConvertToXmlAttributeValue("\"foo\"", "'\"foo\"'");
		this.verifyConvertToXmlAttributeValue("\"foo\" 'bar'", "\"&quot;foo&quot; 'bar'\"");
		this.verifyConvertToXmlAttributeValue("foo & bar", "\"foo &amp; bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo & bar\"", "'\"foo &amp; bar\"'");
		this.verifyConvertToXmlAttributeValue("foo <<< bar", "\"foo &lt;&lt;&lt; bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo <<< bar\"", "'\"foo &lt;&lt;&lt; bar\"'");
	}

	private void verifyConvertToXmlAttributeValue(String s, String expected) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlAttributeValue(s.toCharArray()));
	}

	public void testConvertToXmlElementText() {
		this.verifyConvertToXmlElementText("", "");
		this.verifyConvertToXmlElementText("\"", "\"");
		this.verifyConvertToXmlElementText("\"\"", "\"\"");
		this.verifyConvertToXmlElementText("'", "'");
		this.verifyConvertToXmlElementText("''", "''");
		this.verifyConvertToXmlElementText("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementText("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementText("'foo'", "'foo'");
		this.verifyConvertToXmlElementText("foo & bar", "foo &amp; bar");
		this.verifyConvertToXmlElementText("foo &", "foo &amp;");
		this.verifyConvertToXmlElementText("& bar", "&amp; bar");
		this.verifyConvertToXmlElementText("\"foo & bar\"", "\"foo &amp; bar\"");
		this.verifyConvertToXmlElementText("foo <<< bar", "foo &lt;&lt;&lt; bar");
		this.verifyConvertToXmlElementText("\"foo <<< bar\"", "\"foo &lt;&lt;&lt; bar\"");
	}

	private void verifyConvertToXmlElementText(String s, String expected) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlElementText(s.toCharArray()));
	}

	public void testConvertToXmlElementCDATA() {
		String START = "<![CDATA[";
		String END = "]]>";
		this.verifyConvertToXmlElementCDATA("", START + END);
		this.verifyConvertToXmlElementCDATA("\"", START + "\"" + END);
		this.verifyConvertToXmlElementCDATA("\"\"", START + "\"\"" + END);
		this.verifyConvertToXmlElementCDATA("'", START + "'" + END);
		this.verifyConvertToXmlElementCDATA("''", START + "''" + END);
		this.verifyConvertToXmlElementCDATA("\"'\"", START + "\"'\"" + END);
		this.verifyConvertToXmlElementCDATA("\"''\"", START + "\"''\"" + END);
		this.verifyConvertToXmlElementCDATA("'foo'", START + "'foo'" + END);
		this.verifyConvertToXmlElementCDATA("foo & bar", START + "foo & bar" + END);
		this.verifyConvertToXmlElementCDATA("foo &", START + "foo &" + END);
		this.verifyConvertToXmlElementCDATA("& bar", START + "& bar" + END);
		this.verifyConvertToXmlElementCDATA("\"foo & bar\"", START + "\"foo & bar\"" + END);
		this.verifyConvertToXmlElementCDATA("foo <<< bar", START + "foo <<< bar" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <<< bar\"", START + "\"foo <<< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <&< bar\"", START + "\"foo <&< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]< bar\"", START + "\"foo <]< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]]< bar\"", START + "\"foo <]]< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("\"foo <]]>< bar\"", START + "\"foo <]]&gt;< bar\"" + END);
		this.verifyConvertToXmlElementCDATA("foo <]", START + "foo <]" + END);
		this.verifyConvertToXmlElementCDATA("foo <]]", START + "foo <]]" + END);
		this.verifyConvertToXmlElementCDATA("foo <]]>", START + "foo <]]&gt;" + END);
		this.verifyConvertToXmlElementCDATA("]foo", START + "]foo" + END);
		this.verifyConvertToXmlElementCDATA("]]foo", START + "]]foo" + END);
		this.verifyConvertToXmlElementCDATA("]]>foo", START + "]]&gt;foo" + END);
	}

	private void verifyConvertToXmlElementCDATA(String s, String expected) {
		TestTools.assertEquals(expected, CharArrayTools.convertToXmlElementCDATA(s.toCharArray()));
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
		assertEquals(s1.contains(sb), CharArrayTools.contains(s1.toCharArray(), s2));
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
