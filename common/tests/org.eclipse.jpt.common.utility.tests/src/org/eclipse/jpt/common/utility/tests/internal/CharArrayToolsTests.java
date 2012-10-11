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
import org.eclipse.jpt.common.utility.internal.ByteArrayTools;
import org.eclipse.jpt.common.utility.internal.CharArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CharArrayToolsTests
	extends TestCase
{
	// ********** padding/truncating/centering **********

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
		String s = "74657374"; // Unicode values
		assertEquals("test", new String(CharArrayTools.convertHexStringToByteArray(s.toCharArray())));
	}

	public void testConvertHexStringToByteArray_negative() throws Exception {
		String s = "636166E9"; // Unicode values
		assertEquals("café", new String(CharArrayTools.convertHexStringToByteArray(s.toCharArray())));
	}

	public void testConvertHexStringToByteArray_lowercase() throws Exception {
		String s = "636166e9"; // Unicode values
		assertEquals("café", new String(CharArrayTools.convertHexStringToByteArray(s.toCharArray())));
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
}
