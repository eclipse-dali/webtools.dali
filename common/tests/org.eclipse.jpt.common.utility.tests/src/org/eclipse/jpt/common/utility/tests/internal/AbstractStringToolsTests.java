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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ByteArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.SystemTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class AbstractStringToolsTests
	extends TestCase
{
	protected AbstractStringToolsTests(String name) {
		super(name);
	}

	protected abstract String CR();

	// ********** reverse **********

	public void testReverse() {
		this.verifyReverse("derf", "fred");
		this.verifyReverse("wilma", "amliw");
		this.verifyReverse("f", "f");
		this.verifyReverse("", "");
	}

	protected abstract void verifyReverse(String expected, String string);

	// ********** last **********

	public void testLast() {
		this.verifyLast('d', "fred");
		this.verifyLast('a', "wilma");
		this.verifyLast('f', "f");

		boolean exCaught = false;
		try {
			this.verifyLast('X', "");
			fail();
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyLast(char expected, String string);

	// ********** concatenate **********

	public void testConcatenate_empty() throws Exception {
		String[] array = new String[0];
		this.verifyConcatenate("", array);
		this.verifyConcatenate("", array, "");
		this.verifyConcatenate("", array, ";");
	}
		
	public void testConcatenate_one() throws Exception {
		String[] array = new String[] {"foo"};
		this.verifyConcatenate("foo", array);
		this.verifyConcatenate("foo", array, "");
		this.verifyConcatenate("foo", array, ";");
	}

	public void testConcatenate_three() throws Exception {
		String[] array = new String[] {"foo", "bar", "baz"};
		this.verifyConcatenate("foobarbaz", array);
		this.verifyConcatenate("foobarbaz", array, "");
		this.verifyConcatenate("foo;bar;baz", array, ";");
	}

	public void testConcatenate_threeEmpty() throws Exception {
		String[] array = new String[] {"", "", ""};
		this.verifyConcatenate("", array);
		this.verifyConcatenate("", array, "");
		this.verifyConcatenate(";;", array, ";");
	}

	public void testConcatenate_oneEmpty() throws Exception {
		String[] array = new String[] {"foo", "", "baz"};
		this.verifyConcatenate("foobaz", array);
		this.verifyConcatenate("foobaz", array, "");
		this.verifyConcatenate("foo;;baz", array, ";");
	}

	protected abstract void verifyConcatenate(String expected, String[] array);

	protected abstract void verifyConcatenate(String expected, String[] array, String delim);

	// ********** padding/truncating/centering/repeating **********

	public void testCenter() {
		this.verifyCenter("", "fred", 0);
		this.verifyCenter("fred", "fred", 4);
		this.verifyCenter(" fred ", "fred", 6);
		this.verifyCenter(" fred  ", "fred", 7);
		this.verifyCenter("re", "fred", 2);
		this.verifyCenter("fre", "fred", 3);
	}

	protected abstract void verifyCenter(String expected, String string, int len);

	public void testPad() {
		this.verifyPad("fred", "fred", 4);
		this.verifyPad("fred  ", "fred", 6);

		boolean exThrown = false;
		try {
			this.verifyPad("BOGUS", "fred", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	protected abstract void verifyPad(String expected, String string, int len);

	public void testFit() {
		this.verifyFit("", "fred", 0);
		this.verifyFit("fred", "fred", 4);
		this.verifyFit("fred  ", "fred", 6);
		this.verifyFit("fr", "fred", 2);
	}

	protected abstract void verifyFit(String expected, String string, int len);

	public void testZeroPad() {
		this.verifyZeroPad("1234", "1234", 4);
		this.verifyZeroPad("001234", "1234", 6);

		boolean exThrown = false;
		try {
			this.verifyZeroPad("BOGUS", "1234", 2);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	protected abstract void verifyZeroPad(String expected, String string, int len);

	public void testZeroFit() {
		this.verifyZeroFit("", "1234", 0);
		this.verifyZeroFit("1234", "1234", 4);
		this.verifyZeroFit("001234", "1234", 6);
		this.verifyZeroFit("34", "1234", 2);
	}

	protected abstract void verifyZeroFit(String expected, String string, int len);

	public void testRepeat() {
		this.verifyRepeat("", "1234", 0);
		this.verifyRepeat("12", "1234", 2);
		this.verifyRepeat("1234", "1234", 4);
		this.verifyRepeat("123412", "1234", 6);
		this.verifyRepeat("12341234", "1234", 8);
		this.verifyRepeat("123412341234123412341", "1234", 21);
	}

	protected abstract void verifyRepeat(String expected, String string, int length);

	// ********** separating **********

	public void testSeparateCharInt() {
		this.verifySeparate("012345",      "012345", '-', 22);
		this.verifySeparate("012345",      "012345", '-',  6);
		this.verifySeparate("01234-5",     "012345", '-',  5);
		this.verifySeparate("0123-45",     "012345", '-',  4);
		this.verifySeparate("012-345",     "012345", '-',  3);
		this.verifySeparate("01-23-45",    "012345", '-',  2);
		this.verifySeparate("0-1-2-3-4-5", "012345", '-',  1);
	}

	public void testSeparateCharInt_ex() {
		this.verifySeparateCharInt_ex("012345", '-', 0);
		this.verifySeparateCharInt_ex("012345", '-', -3);
	}

	private void verifySeparateCharInt_ex(String string, char separator, int segmentLength) {
		boolean exThrown = false;
		try {
			this.verifySeparate("BOGUS", string, separator, segmentLength);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	protected abstract void verifySeparate(String expected, String string, char separator, int segmentLength);

	// ********** delimiting **********

	public void testQuote() {
		this.verifyQuote("\"Employee\"", "Employee");
		this.verifyQuote("\"123\"", "123");
		this.verifyQuote("\"\"", "");
		this.verifyQuote("\"Emp\"\"loyee\"", "Emp\"loyee");
	}

	protected abstract void verifyQuote(String expected, String string);

	public void testCharDelimiter() {
		this.verifyCharDelimiter("\"Employee\"", "Employee", '"');
		this.verifyCharDelimiter("_123_", "123", '_');
		this.verifyCharDelimiter("XX", "", 'X');
		this.verifyCharDelimiter("\"Emp\"\"loyee\"", "Emp\"loyee", '"');
	}

	protected abstract void verifyCharDelimiter(String expected, String string, char delimiter);

	public void testCharDelimiter_toString() {
		Object delimiter = this.buildCharDelimiter('"');
		assertTrue(delimiter.toString(), delimiter.toString().startsWith(this.getSimpleToolsClassName() + ".CharDelimiter"));
		assertTrue(delimiter.toString(), delimiter.toString().endsWith("(\")"));
	}

	protected abstract Object buildCharDelimiter(char c);

	public void testDelimit() {
		this.verifyDelimit("123Employee123", "Employee", "123");
		this.verifyDelimit("123123123", "123", "123");
		this.verifyDelimit("123123", "", "123");

		this.verifyDelimit("Employee", "Employee", "");
		this.verifyDelimit("123", "123", "");
		this.verifyDelimit("", "", "");

		this.verifyDelimit("XEmployeeX", "Employee", "X");
		this.verifyDelimit("X123X", "123", "X");
		this.verifyDelimit("XX", "", "X");
	}

	protected abstract void verifyDelimit(String expected, String string, String delimiter);

	public void testStringDelimiter() {
		this.verifyStringDelimiter("123Employee123", "Employee", "123");
		this.verifyStringDelimiter("123123123", "123", "123");
		this.verifyStringDelimiter("123123", "", "123");
	}

	protected abstract void verifyStringDelimiter(String expected, String string, String delimiter);

	public void testStringDelimiter_toString() {
		Object delimiter = this.buildStringDelimiter("123");
		String simpleClassName = this.getSimpleToolsClassName();
		String prefix = simpleClassName.substring(0, simpleClassName.length() - "Tools".length());
		assertTrue(delimiter.toString(), delimiter.toString().startsWith(simpleClassName + "." + prefix + "Delimiter"));
		assertTrue(delimiter.toString(), delimiter.toString().endsWith("(\"123\")"));
	}

	protected abstract Object buildStringDelimiter(String string);

	// ********** removing characters **********

	public void testRemoveFirstOccurrence() {
		this.verifyRemoveFirstOccurrence("Employee", "Employee", '&');
		this.verifyRemoveFirstOccurrence("Employee", "Emplo&yee", '&');
		this.verifyRemoveFirstOccurrence("Employee&", "Emplo&yee&", '&');
		this.verifyRemoveFirstOccurrence("Employee Foo", "Employee &Foo", '&');
		this.verifyRemoveFirstOccurrence("Employee", "Employee&", '&');
		this.verifyRemoveFirstOccurrence("Employee", "&Employee", '&');
	}

	protected abstract void verifyRemoveFirstOccurrence(String expected, String string, char charToRemove);

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee", "Employee", ' ');
		this.verifyRemoveAllOccurrences("EmployeeFred", "Employee Fred", ' ');
		this.verifyRemoveAllOccurrences("Employee", " Employee ", ' ');
		this.verifyRemoveAllOccurrences("EmployeeFoo", "Employee   Foo", ' ');
		this.verifyRemoveAllOccurrences("EmployeeFoo", " Emp loyee   Foo", ' ');
	}

	protected abstract void verifyRemoveAllOccurrences(String expected, String string, char charToRemove);

	public void testRemoveAllSpaces() {
		this.verifyRemoveAllSpaces("Employee", "Employee");
		this.verifyRemoveAllSpaces("EmployeeFred", "Employee Fred");
		this.verifyRemoveAllSpaces("Employee", " Employee ");
		this.verifyRemoveAllSpaces("EmployeeFoo", "Employee   Foo");
		this.verifyRemoveAllSpaces("EmployeeFoo", " Emp loyee   Foo");
	}

	protected abstract void verifyRemoveAllSpaces(String expected, String string);

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("EmployeeFred", "EmployeeFred");
		this.verifyRemoveAllWhitespace("EmployeeFred", "Employee Fred\t");
		this.verifyRemoveAllWhitespace("Employee", "\tEmployee\n");
		this.verifyRemoveAllWhitespace("EmployeeFoo", "Employee \t Foo");
		this.verifyRemoveAllWhitespace("EmployeeFoo", " Emp\tloyee \n Foo");
	}

	protected abstract void verifyRemoveAllWhitespace(String expected, String string);

	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("EmployeeFred", "EmployeeFred");
		this.verifyCompressWhitespace("Employee Fred ", "Employee      Fred\t");
		this.verifyCompressWhitespace(" Employee ", "\tEmployee  \n");
		this.verifyCompressWhitespace("Employee Foo", "Employee \t Foo");
		this.verifyCompressWhitespace(" Emp loyee Foo ", " Emp\tloyee \n Foo ");
	}

	protected abstract void verifyCompressWhitespace(String expected, String string);

	// ********** common prefix **********

	public void testCommonPrefixLength() {
		this.verifyCommonPrefixLength(3, "fooZZZ", "fooBBB");
		this.verifyCommonPrefixLength(3, "foo", "fooBBB");
		this.verifyCommonPrefixLength(3, "fooZZZ", "foo");
		this.verifyCommonPrefixLength(3, "foo", "foo");
	}

	protected abstract void verifyCommonPrefixLength(int expected, String string1, String string2);

	public void testCommonPrefixLengthMax() {
		this.verifyCommonPrefixLengthMax(2, "fooZZZ", "fooBBB", 2);
		this.verifyCommonPrefixLengthMax(2, "foo", "fooBBB", 2);
		this.verifyCommonPrefixLengthMax(2, "fooZZZ", "foo", 2);
		this.verifyCommonPrefixLengthMax(2, "foo", "foo", 2);
	}

	protected abstract void verifyCommonPrefixLengthMax(int expected, String string1, String string2, int max);

	// ********** capitalization **********

	public void testCapitalize() {
		this.verifyCapitalize("Oracle", "Oracle");
		this.verifyCapitalize("Oracle", "oracle");
		this.verifyCapitalize("   ", "   ");
		this.verifyCapitalize("ORACLE", "ORACLE");
		this.verifyCapitalize("", "");
		this.verifyCapitalize("A", "a");
		this.verifyCapitalize("\u00C9cole", "\u00E9cole"); // e'cole -> E'cole
	}

	protected abstract void verifyCapitalize(String expected, String string);

	public void testCapitalizer_methods() throws Exception {
		Object capitalizer = this.getCapitalizer();
		assertEquals("Capitalizer", capitalizer.toString());
		assertSame(capitalizer, TestTools.serialize(capitalizer));
	}

	protected abstract Object getCapitalizer();

	public void testUncapitalize() {
		this.verifyUncapitalize("oracle", "Oracle");
		this.verifyUncapitalize("oracle", "oracle");
		this.verifyUncapitalize("   ", "   ");
		this.verifyUncapitalize("ORACLE", "ORACLE");
		this.verifyUncapitalize("ORacle", "ORacle");
		this.verifyUncapitalize("oRacle", "oRacle");
		this.verifyUncapitalize("", "");
		this.verifyUncapitalize("a", "A");
		this.verifyUncapitalize("\u00E9cole", "\u00C9cole"); // E'cole -> e'cole
	}

	protected abstract void verifyUncapitalize(String expected, String string);

	public void testUncapitalizer_methods() throws Exception {
		Object uncapitalizer = this.getUncapitalizer();
		assertEquals("Uncapitalizer", uncapitalizer.toString());
		assertSame(uncapitalizer, TestTools.serialize(uncapitalizer));
	}

	protected abstract Object getUncapitalizer();

	// ********** queries **********

	public void testIsBlank() {
		this.verifyIsBlank(true, (String) null);
		this.verifyIsBlank(true, "");
		this.verifyIsBlank(true, "      ");
		this.verifyIsBlank(true, "      \t\t   ");
		this.verifyIsBlank(true, "      \t\t   " + this.CR());

		this.verifyIsBlank(false, "XXX");
	}

	protected abstract void verifyIsBlank(boolean expected, String string);

	public void testIsBlankPredicate_methods() throws Exception {
		Object predicate = this.getIsBlankPredicate();
		assertEquals("IsBlank", predicate.toString());
		assertSame(predicate, TestTools.serialize(predicate));
	}

	protected abstract Object getIsBlankPredicate();

	public void testIsNotBlank() {
		this.verifyIsNotBlank(false, (String) null);
		this.verifyIsNotBlank(false, "");
		this.verifyIsNotBlank(false, "      ");
		this.verifyIsNotBlank(false, "      \t\t   ");
		this.verifyIsNotBlank(false, "      \t\t   " + this.CR());

		this.verifyIsNotBlank(true, "XXX");
	}

	protected abstract void verifyIsNotBlank(boolean expected, String string);

	public void testIsNotBlankPredicate_methods() throws Exception {
		Object predicate = this.getIsNotBlankPredicate();
		assertEquals("IsNotBlank", predicate.toString());
		assertSame(predicate, TestTools.serialize(predicate));
	}

	protected abstract Object getIsNotBlankPredicate();

	public void testEqualsIgnoreCase() {
		this.verifyEqualsIgnoreCase(true, (String) null, (String) null);
		verifyEqualsIgnoreCase(false, null, "asdf");
		verifyEqualsIgnoreCase(false, "asdf", null);
		verifyEqualsIgnoreCase(true, "asdf", "asdf");
		verifyEqualsIgnoreCase(true, "asdf", "ASDF");
		verifyEqualsIgnoreCase(false, "asdf", "ASDD");
		verifyEqualsIgnoreCase(false, "asdf", "ASDFF");
	}

	protected abstract void verifyEqualsIgnoreCase(boolean expected, String string1, String string2);

	public void testStartsWithIgnoreCase() {
		this.verifyStartsWithIgnoreCase(true, "asdf", "as");
		this.verifyStartsWithIgnoreCase(true, "asdf", "aS");
		this.verifyStartsWithIgnoreCase(true, "asdf", "");
		this.verifyStartsWithIgnoreCase(true, "asdf", "A");

		this.verifyStartsWithIgnoreCase(false, "asdf", "bsdf");
		this.verifyStartsWithIgnoreCase(false, "asdf", "g");
		this.verifyStartsWithIgnoreCase(false, "asdf", "asdg");
		this.verifyStartsWithIgnoreCase(false, "asdf", "asdfg");
		this.verifyStartsWithIgnoreCase(false, "asdf", "asdfgggggg");
	}

	protected abstract void verifyStartsWithIgnoreCase(boolean expected, String string, String prefix);

	public void testIsUppercase() {
		this.verifyIsUppercase("FOO");
		this.verifyIsUppercase("FOO2");
		this.verifyIsUppercase("F O O");
		this.denyIsUppercase("Foo");
		this.denyIsUppercase("");
	}

	protected abstract void verifyIsUppercase(String string);

	protected abstract void denyIsUppercase(String string);

	public void testIsLowercase() {
		this.verifyIsLowercase("foo");
		this.verifyIsLowercase("foo2");
		this.verifyIsLowercase("f o o");
		this.denyIsLowercase("Foo");
		this.denyIsLowercase("");
	}

	protected abstract void verifyIsLowercase(String string);

	protected abstract void denyIsLowercase(String string);

	// ********** byte arrays **********

	public void testConvertHexStringToByteArray_empty() throws Exception {
		byte[] byteArray = this.convertHexStringToByteArray("");
		assertEquals(0, byteArray.length);
		assertTrue(Arrays.equals(ByteArrayTools.EMPTY_BYTE_ARRAY, byteArray));
	}

	protected abstract byte[] convertHexStringToByteArray(String string);

	public void testConvertHexStringToByteArray_oddLength() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFEE");
	}

	public void testConvertHexStringToByteArray_illegalCharacter1() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFEX0CAFEX0");
	}

	public void testConvertHexStringToByteArray_illegalCharacter2() throws Exception {
		this.verifyConvertHexStringToByteArray_illegalCharacter("CAFE0XCAFE0x");
	}

	protected void verifyConvertHexStringToByteArray_illegalCharacter(String string) throws Exception {
		boolean exCaught = false;
		try {
			byte[] byteArray = this.convertHexStringToByteArray(string);
			fail("bogus byte array: " + Arrays.toString(byteArray));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConvertHexStringToByteArray_ok() throws Exception {
		String s = "74657374"; // UTF-8 values
		assertEquals("test", new String(this.convertHexStringToByteArray(s)));
	}

	public void testConvertHexStringToByteArray_negative() throws Exception {
		String s = getHexCafe();
		assertEquals("caf\u00E9", new String(this.convertHexStringToByteArray(s)));
	}

	public void testConvertHexStringToByteArray_lowercase() throws Exception {
		String s = getHexCafe().toLowerCase();
		assertEquals("caf\u00E9", new String(this.convertHexStringToByteArray(s)));
	}

	public static String getHexCafe() {
		if (SystemTools.fileEncodingIsWindows()) {
			return "636166E9";
		}
		if (SystemTools.fileEncodingIsUTF8()) {
			return "636166C3A9";
		}
		return null;
	}

	// ********** convert camel-case to all-caps **********

	public void testConvertCamelCaseToAllCaps() {
		this.verifyConvertCamelCaseToAllCaps("", "");
		this.verifyConvertCamelCaseToAllCaps("TEST", "test");
		this.verifyConvertCamelCaseToAllCaps("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCaps("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	protected abstract void verifyConvertCamelCaseToAllCaps(String expected, String string);

	public void testConvertCamelCaseToAllCapsMaxLength() {
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "", 0);
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "", 44);
		this.verifyConvertCamelCaseToAllCapsMaxLength("", "test", 0);
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

	protected abstract void verifyConvertCamelCaseToAllCapsMaxLength(String expected, String string, int max);

	// ********** convert all-caps to camel case **********

	public void testConvertAllCapsToCamelCase() {
		this.verifyConvertAllCapsToCamelCase("", "");
		this.verifyConvertAllCapsToCamelCase("", "", true);
		this.verifyConvertAllCapsToCamelCase("", "", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST_", false);
		this.verifyConvertAllCapsToCamelCase("test", "TEST____", false);
		this.verifyConvertAllCapsToCamelCase("Test", "TEST");
		this.verifyConvertAllCapsToCamelCase("Test", "TEST", true);
		this.verifyConvertAllCapsToCamelCase("test", "TeST", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "TEST_TEST", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "TEST___TEST", false);
		this.verifyConvertAllCapsToCamelCase("TestTest", "TEST_TEST");
		this.verifyConvertAllCapsToCamelCase("TestTest", "TEST_TEST", true);
		this.verifyConvertAllCapsToCamelCase("testTestTest", "TEST_TEST_TEST", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "TEST_TEST_TEST");
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "TEST_TEST_TEST", true);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "_TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "__TEST_TEST_TEST_T", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "TEST_TEST_TEST_T", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_TEST_TEST_TEST_T", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__TEST_TEST_TEST_T");
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__TEST_TEST_TEST_T", true);
	}

	protected abstract void verifyConvertAllCapsToCamelCase(String expected, String string);

	protected abstract void verifyConvertAllCapsToCamelCase(String expected, String string, boolean capFirst);

	public void testConvertAllCapsToCamelCaseLowercase() {
		this.verifyConvertAllCapsToCamelCase("test", "test", false);
		this.verifyConvertAllCapsToCamelCase("test", "test_", false);
		this.verifyConvertAllCapsToCamelCase("test", "test____", false);
		this.verifyConvertAllCapsToCamelCase("Test", "test", true);
		this.verifyConvertAllCapsToCamelCase("test", "test", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "test_test", false);
		this.verifyConvertAllCapsToCamelCase("testTest", "test___test", false);
		this.verifyConvertAllCapsToCamelCase("TestTest", "test_test", true);
		this.verifyConvertAllCapsToCamelCase("testTestTest", "test_test_test", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTest", "test_test_test", true);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "_test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("testTestTestT", "__test_test_test_t", false);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "test_test_test_t", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "_test_test_test_t", true);
		this.verifyConvertAllCapsToCamelCase("TestTestTestT", "__test_test_test_t", true);
	}

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

	protected abstract void verifyIsQuoted(String string);

	protected abstract void denyIsQuoted(String string);

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

	protected abstract void verifyIsParenthetical(String string);

	protected abstract void denyIsParenthetical(String string);

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

	protected abstract void verifyIsBracketed(String string);

	protected abstract void denyIsBracketed(String string);

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

	protected abstract void verifyIsBraced(String string);

	protected abstract void denyIsBraced(String string);

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

	protected abstract void verifyIsChevroned(String string);

	protected abstract void denyIsChevroned(String string);

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

	protected abstract void verifyIsDelimited(String string, char c);

	protected abstract void denyIsDelimited(String string, char c);

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

	protected abstract void verifyIsDelimited2(String string, char start, char end);

	protected abstract void denyIsDelimited2(String string, char start, char end);

	// ********** undelimiting **********

	public void testUndelimit() {
		this.verifyUndelimit("foo", "\"foo\"");
		this.verifyUndelimit("", "\"\"");
		this.verifyUndelimit("foo", "'foo'");
		this.verifyUndelimit("fo\"o", "\"fo\"\"o\"");
		this.verifyUndelimit("foo\"", "\"foo\"\"\"");
		this.verifyUndelimit("\"foo", "\"\"\"foo\"");
		this.verifyUndelimit("foo", "[foo]");
		this.verifyUndelimit("\"", "\"\"\"");
		this.verifyUndelimit("foo\"", "\"foo\"bar\"");
		this.verifyUndelimit("foo\"", "\"foo\"\"");
	}

	public void testUndelimit_ex() {
		boolean exCaught = false;
		try {
			this.verifyUndelimit("BOGUS", "x");
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyUndelimit(String expected, String string);

	public void testUndelimitInt() {
		this.verifyUndelimitInt("\"foo\"", "\"foo\"", 0);
		this.verifyUndelimitInt("o", "\"foo\"", 2);
		this.verifyUndelimitInt("foo", "\"\"foo\"\"", 2);
		this.verifyUndelimitInt("o", "'foo'", 2);
		this.verifyUndelimitInt("", "foof", 2);
	}

	public void testUndelimitInt_ex() {
		this.verifyUndelimitInt_ex("\"\"", 2);
		this.verifyUndelimitInt_ex("'o'", 2);
	}

	protected void verifyUndelimitInt_ex(String string, int count) {
		boolean exCaught = false;
		try {
			this.verifyUndelimitInt("BOGUS", string, count);
			fail();
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected abstract void verifyUndelimitInt(String expected, String string, int count);

	// ********** converting to Java string literal **********

	public void testConvertToJavaStringLiteral() {
		this.verifyConvertToJavaStringLiteral("\"\"", "");
		this.verifyConvertToJavaStringLiteral("\"\\\"\\\"\"", "\"\"");
		this.verifyConvertToJavaStringLiteral("\"'foo'\"", "'foo'");
		this.verifyConvertToJavaStringLiteral("\"foo\\bbar\"", "foo\bbar");
		this.verifyConvertToJavaStringLiteral("\"foo\\n\\tbar\"", "foo\n\tbar");
		this.verifyConvertToJavaStringLiteral("\"foo\\\"bar\"", "foo\"bar");
		this.verifyConvertToJavaStringLiteral("\"foo\\\\bar\"", "foo\\bar");
	}

	protected abstract void verifyConvertToJavaStringLiteral(String expected, String string);

	public void testConvertToJavaStringLiteralTransformer_methods() throws Exception {
		Object transformer = this.getJavaStringLiteralTransformer();
		assertEquals("JavaStringLiteralTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getJavaStringLiteralTransformer();

	public void testConvertToJavaStringLiteralContent() {
		this.verifyConvertToJavaStringLiteralContent("", "");
		this.verifyConvertToJavaStringLiteralContent("\\\"\\\"", "\"\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\\bbar", "foo\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\\n\\tbar", "foo\n\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\\\"bar", "foo\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\\\bar", "foo\\bar");
	}

	protected abstract void verifyConvertToJavaStringLiteralContent(String expected, String string);

	public void testConvertToJavaStringLiteralContentTransformer_methods() throws Exception {
		Object transformer = this.getJavaStringLiteralContentTransformer();
		assertEquals("JavaStringLiteralContentTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getJavaStringLiteralContentTransformer();

	// ********** converting to XML **********

	public void testConvertToXmlAttributeValue() {
		this.verifyConvertToXmlAttributeValue("\"\"", "");
		this.verifyConvertToXmlAttributeValue("'\"'", "\"");
		this.verifyConvertToXmlAttributeValue("'\"\"'", "\"\"");
		this.verifyConvertToXmlAttributeValue("\"'\"", "'");
		this.verifyConvertToXmlAttributeValue("\"''\"", "''");
		this.verifyConvertToXmlAttributeValue("\"&quot;'&quot;\"", "\"'\"");
		this.verifyConvertToXmlAttributeValue("\"&quot;''&quot;\"", "\"''\"");
		this.verifyConvertToXmlAttributeValue("\"'foo'\"", "'foo'");
		this.verifyConvertToXmlAttributeValue("'\"foo\"'", "\"foo\"");
		this.verifyConvertToXmlAttributeValue("\"&quot;foo&quot; 'bar'\"", "\"foo\" 'bar'");
		this.verifyConvertToXmlAttributeValue("\"foo &amp; bar\"", "foo & bar");
		this.verifyConvertToXmlAttributeValue("'\"foo &amp; bar\"'", "\"foo & bar\"");
		this.verifyConvertToXmlAttributeValue("\"foo &lt;&lt;&lt; bar\"", "foo <<< bar");
		this.verifyConvertToXmlAttributeValue("'\"foo &lt;&lt;&lt; bar\"'", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToXmlAttributeValue(String expected, String string);

	public void testConvertToXmlAttributeValueTransformer_methods() throws Exception {
		Object transformer = this.getXmlAttributeValueTransformer();
		assertEquals("XmlAttributeValueTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getXmlAttributeValueTransformer();

	public void testConvertToDoubleQuotedXmlAttributeValue() {
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"\"", "");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;\"", "\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;&quot;\"", "\"\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"'\"", "'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"''\"", "''");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;'&quot;\"", "\"'\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;''&quot;\"", "\"''\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"'foo'\"", "'foo'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo&quot;\"", "\"foo\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo&quot; 'bar'\"", "\"foo\" 'bar'");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"foo &amp; bar\"", "foo & bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo &amp; bar&quot;\"", "\"foo & bar\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"foo &lt;&lt;&lt; bar\"", "foo <<< bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValue("\"&quot;foo &lt;&lt;&lt; bar&quot;\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToDoubleQuotedXmlAttributeValue(String expected, String string);

	public void testConvertToDoubleQuotedXmlAttributeValueTransformer_methods() throws Exception {
		Object transformer = this.getDoubleQuotedXmlAttributeValueTransformer();
		assertEquals("DoubleQuotedXmlAttributeValueTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getDoubleQuotedXmlAttributeValueTransformer();

	public void testConvertToDoubleQuotedXmlAttributeValueContent() {
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("", "");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;", "\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;&quot;", "\"\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("'", "'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("''", "''");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;'&quot;", "\"'\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;''&quot;", "\"''\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("'foo'", "'foo'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo&quot;", "\"foo\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo&quot; 'bar'", "\"foo\" 'bar'");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("foo &amp; bar", "foo & bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo &amp; bar&quot;", "\"foo & bar\"");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToDoubleQuotedXmlAttributeValueContent("&quot;foo &lt;&lt;&lt; bar&quot;", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToDoubleQuotedXmlAttributeValueContent(String expected, String string);

	public void testConvertToDoubleQuotedXmlAttributeValueContentTransformer_methods() throws Exception {
		Object transformer = this.getDoubleQuotedXmlAttributeValueContentTransformer();
		assertEquals("DoubleQuotedXmlAttributeValueContentTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getDoubleQuotedXmlAttributeValueContentTransformer();

	public void testConvertToSingleQuotedXmlAttributeValue() {
		this.verifyConvertToSingleQuotedXmlAttributeValue("''", "");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"'", "\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"\"'", "\"\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;'", "'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;&apos;'", "''");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"&apos;\"'", "\"'\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"&apos;&apos;\"'", "\"''\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'&apos;foo&apos;'", "'foo'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo\"'", "\"foo\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo\" &apos;bar&apos;'", "\"foo\" 'bar'");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'foo &amp; bar'", "foo & bar");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo &amp; bar\"'", "\"foo & bar\"");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'foo &lt;&lt;&lt; bar'", "foo <<< bar");
		this.verifyConvertToSingleQuotedXmlAttributeValue("'\"foo &lt;&lt;&lt; bar\"'", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToSingleQuotedXmlAttributeValue(String expected, String string);

	public void testConvertToSingleQuotedXmlAttributeValueTransformer_methods() throws Exception {
		Object transformer = this.getSingleQuotedXmlAttributeValueTransformer();
		assertEquals("SingleQuotedXmlAttributeValueTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getSingleQuotedXmlAttributeValueTransformer();

	public void testConvertToSingleQuotedXmlAttributeValueContent() {
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("", "");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"", "\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"\"", "\"\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;", "'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;&apos;", "''");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"&apos;\"", "\"'\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"&apos;&apos;\"", "\"''\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("&apos;foo&apos;", "'foo'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo\"", "\"foo\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo\" &apos;bar&apos;", "\"foo\" 'bar'");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("foo &amp; bar", "foo & bar");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo &amp; bar\"", "\"foo & bar\"");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToSingleQuotedXmlAttributeValueContent("\"foo &lt;&lt;&lt; bar\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToSingleQuotedXmlAttributeValueContent(String expected, String string);

	public void testConvertToSingleQuotedXmlAttributeValueContentTransformer_methods() throws Exception {
		Object transformer = this.getSingleQuotedXmlAttributeValueContentTransformer();
		assertEquals("SingleQuotedXmlAttributeValueContentTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getSingleQuotedXmlAttributeValueContentTransformer();

	public void testConvertToXmlElementText() {
		this.verifyConvertToXmlElementText("", "");
		this.verifyConvertToXmlElementText("\"", "\"");
		this.verifyConvertToXmlElementText("\"\"", "\"\"");
		this.verifyConvertToXmlElementText("'", "'");
		this.verifyConvertToXmlElementText("''", "''");
		this.verifyConvertToXmlElementText("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementText("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementText("'foo'", "'foo'");
		this.verifyConvertToXmlElementText("foo &amp; bar", "foo & bar");
		this.verifyConvertToXmlElementText("foo &amp;", "foo &");
		this.verifyConvertToXmlElementText("&amp; bar", "& bar");
		this.verifyConvertToXmlElementText("\"foo &amp; bar\"", "\"foo & bar\"");
		this.verifyConvertToXmlElementText("foo &lt;&lt;&lt; bar", "foo <<< bar");
		this.verifyConvertToXmlElementText("\"foo &lt;&lt;&lt; bar\"", "\"foo <<< bar\"");
	}

	protected abstract void verifyConvertToXmlElementText(String expected, String string);

	public void testConvertToXmlElementTextTransformer_methods() throws Exception {
		Object transformer = this.getXmlElementTextTransformer();
		assertEquals("XmlElementTextTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getXmlElementTextTransformer();

	public void testConvertToXmlElementCDATA() {
		String START = "<![CDATA[";
		String END = "]]>";
		this.verifyConvertToXmlElementCDATA(START + END, "");
		this.verifyConvertToXmlElementCDATA(START + "\"" + END, "\"");
		this.verifyConvertToXmlElementCDATA(START + "\"\"" + END, "\"\"");
		this.verifyConvertToXmlElementCDATA(START + "'" + END, "'");
		this.verifyConvertToXmlElementCDATA(START + "''" + END, "''");
		this.verifyConvertToXmlElementCDATA(START + "\"'\"" + END, "\"'\"");
		this.verifyConvertToXmlElementCDATA(START + "\"''\"" + END, "\"''\"");
		this.verifyConvertToXmlElementCDATA(START + "'foo'" + END, "'foo'");
		this.verifyConvertToXmlElementCDATA(START + "foo & bar" + END, "foo & bar");
		this.verifyConvertToXmlElementCDATA(START + "foo &" + END, "foo &");
		this.verifyConvertToXmlElementCDATA(START + "& bar" + END, "& bar");
		this.verifyConvertToXmlElementCDATA(START + "\"foo & bar\"" + END, "\"foo & bar\"");
		this.verifyConvertToXmlElementCDATA(START + "foo <<< bar" + END, "foo <<< bar");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <<< bar\"" + END, "\"foo <<< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <&< bar\"" + END, "\"foo <&< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]< bar\"" + END, "\"foo <]< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]]< bar\"" + END, "\"foo <]]< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "\"foo <]]&gt;< bar\"" + END, "\"foo <]]>< bar\"");
		this.verifyConvertToXmlElementCDATA(START + "foo <]" + END, "foo <]");
		this.verifyConvertToXmlElementCDATA(START + "foo <]]" + END, "foo <]]");
		this.verifyConvertToXmlElementCDATA(START + "foo <]]&gt;" + END, "foo <]]>");
		this.verifyConvertToXmlElementCDATA(START + "]foo" + END, "]foo");
		this.verifyConvertToXmlElementCDATA(START + "]]foo" + END, "]]foo");
		this.verifyConvertToXmlElementCDATA(START + "]]&gt;foo" + END, "]]>foo");
	}

	protected abstract void verifyConvertToXmlElementCDATA(String expected, String string);

	public void testConvertToXmlElementCDATATransformer_methods() throws Exception {
		Object transformer = this.getXmlElementCDATATransformer();
		assertEquals("XmlElementCDATATransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getXmlElementCDATATransformer();

	public void testConvertToXmlElementCDATAContent() throws Exception {
		this.verifyConvertToXmlElementCDATAContent("", "");
		this.verifyConvertToXmlElementCDATAContent("\"", "\"");
		this.verifyConvertToXmlElementCDATAContent("\"\"", "\"\"");
		this.verifyConvertToXmlElementCDATAContent("'", "'");
		this.verifyConvertToXmlElementCDATAContent("''", "''");
		this.verifyConvertToXmlElementCDATAContent("\"'\"", "\"'\"");
		this.verifyConvertToXmlElementCDATAContent("\"''\"", "\"''\"");
		this.verifyConvertToXmlElementCDATAContent("'foo'", "'foo'");
		this.verifyConvertToXmlElementCDATAContent("foo & bar", "foo & bar");
		this.verifyConvertToXmlElementCDATAContent("foo &", "foo &");
		this.verifyConvertToXmlElementCDATAContent("& bar", "& bar");
		this.verifyConvertToXmlElementCDATAContent("\"foo & bar\"", "\"foo & bar\"");
		this.verifyConvertToXmlElementCDATAContent("foo <<< bar", "foo <<< bar");
		this.verifyConvertToXmlElementCDATAContent("\"foo <<< bar\"", "\"foo <<< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <&< bar\"", "\"foo <&< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]< bar\"", "\"foo <]< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]]< bar\"", "\"foo <]]< bar\"");
		this.verifyConvertToXmlElementCDATAContent("\"foo <]]&gt;< bar\"", "\"foo <]]>< bar\"");
		this.verifyConvertToXmlElementCDATAContent("foo <]", "foo <]");
		this.verifyConvertToXmlElementCDATAContent("foo <]]", "foo <]]");
		this.verifyConvertToXmlElementCDATAContent("foo <]]&gt;", "foo <]]>");
		this.verifyConvertToXmlElementCDATAContent("]foo", "]foo");
		this.verifyConvertToXmlElementCDATAContent("]]foo", "]]foo");
		this.verifyConvertToXmlElementCDATAContent("]]&gt;foo", "]]>foo");
	}

	protected abstract void verifyConvertToXmlElementCDATAContent(String expected, String string);

	public void testConvertToXmlElementCDATAContentTransformer_methods() throws Exception {
		Object transformer = this.getXmlElementCDATAContentTransformer();
		assertEquals("XmlElementCDATAContentTransformer", transformer.toString());
		assertSame(transformer, TestTools.serialize(transformer));
	}

	protected abstract Object getXmlElementCDATAContentTransformer();

	// ********** constructor **********

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(this.getToolsClass());
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	protected String getSimpleToolsClassName() {
		return this.getToolsClass().getSimpleName();
	}

	protected abstract Class<?> getToolsClass();
}
