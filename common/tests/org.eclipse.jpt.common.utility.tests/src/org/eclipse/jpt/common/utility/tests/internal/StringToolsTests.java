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

import java.io.StringWriter;
import java.io.Writer;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StringTools;

@SuppressWarnings("nls")
public class StringToolsTests extends TestCase {

	public StringToolsTests(String name) {
		super(name);
	}

	private static void assertEquals(String expected, char[] actual) {
		assertEquals(expected, new String(actual));
	}

	// ********** padding/truncating **********

	public void testPad() {
		assertEquals("fred", StringTools.pad("fred", 4));
		assertEquals("fred  ", StringTools.pad("fred", 6));
		boolean exThrown = false;
		try {
			assertEquals("fr", StringTools.pad("fred", 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testPadCharArray() {
		assertEquals("fred", StringTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 4));
		assertEquals("fred  ", StringTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 6));
		boolean exThrown = false;
		try {
			assertEquals("fr", StringTools.pad(new char[] { 'f', 'r', 'e', 'd' }, 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testPadOnWriter() {
		Writer writer;
		writer = new StringWriter();
		StringTools.padOn("fred", 4, writer);
		assertEquals("fred", writer.toString());

		writer = new StringWriter();
		StringTools.padOn("fred", 6, writer);
		assertEquals("fred  ", writer.toString());

		writer = new StringWriter();
		boolean exThrown = false;
		try {
			StringTools.padOn("fred", 2, writer);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testPadOnStringBuffer() {
		StringBuffer sb;
		sb = new StringBuffer();
		StringTools.padOn("fred", 4, sb);
		assertEquals("fred", sb.toString());

		sb = new StringBuffer();
		StringTools.padOn("fred", 6, sb);
		assertEquals("fred  ", sb.toString());

		sb = new StringBuffer();
		boolean exThrown = false;
		try {
			StringTools.padOn("fred", 2, sb);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testPadOnStringBuilder() {
		StringBuilder sb;
		sb = new StringBuilder();
		StringTools.padOn("fred", 4, sb);
		assertEquals("fred", sb.toString());

		sb = new StringBuilder();
		StringTools.padOn("fred", 6, sb);
		assertEquals("fred  ", sb.toString());

		sb = new StringBuilder();
		boolean exThrown = false;
		try {
			StringTools.padOn("fred", 2, sb);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testPadOrTruncate() {
		assertEquals("fred", StringTools.padOrTruncate("fred", 4));
		assertEquals("fred  ", StringTools.padOrTruncate("fred", 6));
		assertEquals("fr", StringTools.padOrTruncate("fred", 2));
	}

	public void testPadOrTruncateCharArray() {
		assertEquals("fred", StringTools.padOrTruncate(new char[] { 'f', 'r', 'e', 'd' }, 4));
		assertEquals("fred  ", StringTools.padOrTruncate(new char[] { 'f', 'r', 'e', 'd' }, 6));
		assertEquals("fr", StringTools.padOrTruncate(new char[] { 'f', 'r', 'e', 'd' }, 2));
	}

	public void testPadOrTruncateOnWriter() {
		this.verifyPadOrTruncateOnWriter("fred", "fred", 4);
		this.verifyPadOrTruncateOnWriter("fred  ", "fred", 6);
		this.verifyPadOrTruncateOnWriter("fr", "fred", 2);
	}

	private void verifyPadOrTruncateOnWriter(String expected, String string, int length) {
		Writer writer = new StringWriter();
		StringTools.padOrTruncateOn(string, length, writer);
		assertEquals(expected, writer.toString());
	}

	public void testPadOrTruncateOnStringBuffer() {
		this.verifyPadOrTruncateOnStringBuffer("fred", "fred", 4);
		this.verifyPadOrTruncateOnStringBuffer("fred  ", "fred", 6);
		this.verifyPadOrTruncateOnStringBuffer("fr", "fred", 2);
	}

	private void verifyPadOrTruncateOnStringBuffer(String expected, String string, int length) {
		StringBuffer sb = new StringBuffer();
		StringTools.padOrTruncateOn(string, length, sb);
		assertEquals(expected, sb.toString());
	}

	public void testPadOrTruncateOnStringBuilder() {
		this.verifyPadOrTruncateOnStringBuilder("fred", "fred", 4);
		this.verifyPadOrTruncateOnStringBuilder("fred  ", "fred", 6);
		this.verifyPadOrTruncateOnStringBuilder("fr", "fred", 2);
	}

	private void verifyPadOrTruncateOnStringBuilder(String expected, String string, int length) {
		StringBuilder sb = new StringBuilder();
		StringTools.padOrTruncateOn(string, length, sb);
		assertEquals(expected, sb.toString());
	}

	public void testZeroPad() {
		assertEquals("1234", StringTools.zeroPad("1234", 4));
		assertEquals("001234", StringTools.zeroPad("1234", 6));
		boolean exThrown = false;
		try {
			assertEquals("12", StringTools.zeroPad("1234", 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroPadCharArray() {
		assertEquals("1234", StringTools.zeroPad(new char[] { '1', '2', '3', '4' }, 4));
		assertEquals("001234", StringTools.zeroPad(new char[] { '1', '2', '3', '4' }, 6));
		boolean exThrown = false;
		try {
			assertEquals("12", StringTools.zeroPad(new char[] { '1', '2', '3', '4' }, 2));
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroPadOnWriter() {
		Writer writer;
		writer = new StringWriter();
		StringTools.zeroPadOn("1234", 4, writer);
		assertEquals("1234", writer.toString());

		writer = new StringWriter();
		StringTools.zeroPadOn("1234", 6, writer);
		assertEquals("001234", writer.toString());

		writer = new StringWriter();
		boolean exThrown = false;
		try {
			StringTools.zeroPadOn("1234", 2, writer);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroPadOnStringBuffer() {
		StringBuffer sb;
		sb = new StringBuffer();
		StringTools.zeroPadOn("1234", 4, sb);
		assertEquals("1234", sb.toString());

		sb = new StringBuffer();
		StringTools.zeroPadOn("1234", 6, sb);
		assertEquals("001234", sb.toString());

		sb = new StringBuffer();
		boolean exThrown = false;
		try {
			StringTools.zeroPadOn("1234", 2, sb);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroPadOnStringBuilder() {
		StringBuilder sb;
		sb = new StringBuilder();
		StringTools.zeroPadOn("1234", 4, sb);
		assertEquals("1234", sb.toString());

		sb = new StringBuilder();
		StringTools.zeroPadOn("1234", 6, sb);
		assertEquals("001234", sb.toString());

		sb = new StringBuilder();
		boolean exThrown = false;
		try {
			StringTools.zeroPadOn("1234", 2, sb);
			fail();
		} catch (IllegalArgumentException ex) {
			exThrown = true;
		}
		assertTrue(exThrown);
	}

	public void testZeroPadOrTruncate() {
		assertEquals("1234", StringTools.zeroPadOrTruncate("1234", 4));
		assertEquals("001234", StringTools.zeroPadOrTruncate("1234", 6));
		assertEquals("34", StringTools.zeroPadOrTruncate("1234", 2));
	}

	public void testZeroPadOrTruncateCharArray() {
		assertEquals("1234", StringTools.zeroPadOrTruncate(new char[] { '1', '2', '3', '4' }, 4));
		assertEquals("001234", StringTools.zeroPadOrTruncate(new char[] { '1', '2', '3', '4' }, 6));
		assertEquals("34", StringTools.zeroPadOrTruncate(new char[] { '1', '2', '3', '4' }, 2));
	}

	public void testZeroPadOrTruncateOnWriter() {
		this.verifyZeroPadOrTruncateOnWriter("1234", "1234", 4);
		this.verifyZeroPadOrTruncateOnWriter("001234", "1234", 6);
		this.verifyZeroPadOrTruncateOnWriter("34", "1234", 2);
	}

	private void verifyZeroPadOrTruncateOnWriter(String expected, String string, int length) {
		Writer writer = new StringWriter();
		StringTools.zeroPadOrTruncateOn(string, length, writer);
		assertEquals(expected, writer.toString());
	}

	public void testZeroPadOrTruncateOnStringBuffer() {
		this.verifyZeroPadOrTruncateOnStringBuffer("1234", "1234", 4);
		this.verifyZeroPadOrTruncateOnStringBuffer("001234", "1234", 6);
		this.verifyZeroPadOrTruncateOnStringBuffer("34", "1234", 2);
	}

	private void verifyZeroPadOrTruncateOnStringBuffer(String expected, String string, int length) {
		StringBuffer sb = new StringBuffer();
		StringTools.zeroPadOrTruncateOn(string, length, sb);
		assertEquals(expected, sb.toString());
	}

	public void testZeroPadOrTruncateOnStringBuilder() {
		this.verifyZeroPadOrTruncateOnStringBuilder("1234", "1234", 4);
		this.verifyZeroPadOrTruncateOnStringBuilder("001234", "1234", 6);
		this.verifyZeroPadOrTruncateOnStringBuilder("34", "1234", 2);
	}

	private void verifyZeroPadOrTruncateOnStringBuilder(String expected, String string, int length) {
		StringBuilder sb = new StringBuilder();
		StringTools.zeroPadOrTruncateOn(string, length, sb);
		assertEquals(expected, sb.toString());
	}

	// ********** separating **********

	public void testSeparateStringCharInt() {
		this.verifySeparate("012345", '-', 22, "012345");
		this.verifySeparate("012345", '-',  6, "012345");
		this.verifySeparate("012345", '-',  5, "01234-5");
		this.verifySeparate("012345", '-',  4, "0123-45");
		this.verifySeparate("012345", '-',  3, "012-345");
		this.verifySeparate("012345", '-',  2, "01-23-45");
		this.verifySeparate("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparate(String string, char separator, int segmentLength, String expected) {
		assertEquals(expected, StringTools.separate(string, separator, segmentLength));
	}

	public void testSeparateOnStringCharIntWriter() {
		this.verifySeparateOnWriter("012345", '-', 22, "012345");
		this.verifySeparateOnWriter("012345", '-',  6, "012345");
		this.verifySeparateOnWriter("012345", '-',  5, "01234-5");
		this.verifySeparateOnWriter("012345", '-',  4, "0123-45");
		this.verifySeparateOnWriter("012345", '-',  3, "012-345");
		this.verifySeparateOnWriter("012345", '-',  2, "01-23-45");
		this.verifySeparateOnWriter("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateOnWriter(String string, char separator, int segmentLength, String expected) {
		Writer writer = new StringWriter();
		StringTools.separateOn(string, separator, segmentLength, writer);
		assertEquals(expected, writer.toString());
	}

	public void testSeparateOnStringCharIntStringBuffer() {
		this.verifySeparateOnStringBuffer("012345", '-', 22, "012345");
		this.verifySeparateOnStringBuffer("012345", '-',  6, "012345");
		this.verifySeparateOnStringBuffer("012345", '-',  5, "01234-5");
		this.verifySeparateOnStringBuffer("012345", '-',  4, "0123-45");
		this.verifySeparateOnStringBuffer("012345", '-',  3, "012-345");
		this.verifySeparateOnStringBuffer("012345", '-',  2, "01-23-45");
		this.verifySeparateOnStringBuffer("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateOnStringBuffer(String string, char separator, int segmentLength, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.separateOn(string, separator, segmentLength, sb);
		assertEquals(expected, sb.toString());
	}

	public void testSeparateOnStringCharIntStringBuilder() {
		this.verifySeparateOnStringBuilder("012345", '-', 22, "012345");
		this.verifySeparateOnStringBuilder("012345", '-',  6, "012345");
		this.verifySeparateOnStringBuilder("012345", '-',  5, "01234-5");
		this.verifySeparateOnStringBuilder("012345", '-',  4, "0123-45");
		this.verifySeparateOnStringBuilder("012345", '-',  3, "012-345");
		this.verifySeparateOnStringBuilder("012345", '-',  2, "01-23-45");
		this.verifySeparateOnStringBuilder("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateOnStringBuilder(String string, char separator, int segmentLength, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.separateOn(string, separator, segmentLength, sb);
		assertEquals(expected, sb.toString());
	}

	public void testSeparateCharArrayCharInt() {
		this.verifySeparateCharArray("012345", '-', 22, "012345");
		this.verifySeparateCharArray("012345", '-',  6, "012345");
		this.verifySeparateCharArray("012345", '-',  5, "01234-5");
		this.verifySeparateCharArray("012345", '-',  4, "0123-45");
		this.verifySeparateCharArray("012345", '-',  3, "012-345");
		this.verifySeparateCharArray("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArray("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArray(String string, char separator, int segmentLength, String expected) {
		assertEquals(expected, StringTools.separate(string.toCharArray(), separator, segmentLength));
	}

	public void testSeparateOnCharArrayCharIntWriter() {
		this.verifySeparateCharArrayOnWriter("012345", '-', 22, "012345");
		this.verifySeparateCharArrayOnWriter("012345", '-',  6, "012345");
		this.verifySeparateCharArrayOnWriter("012345", '-',  5, "01234-5");
		this.verifySeparateCharArrayOnWriter("012345", '-',  4, "0123-45");
		this.verifySeparateCharArrayOnWriter("012345", '-',  3, "012-345");
		this.verifySeparateCharArrayOnWriter("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArrayOnWriter("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArrayOnWriter(String string, char separator, int segmentLength, String expected) {
		Writer writer = new StringWriter();
		StringTools.separateOn(string.toCharArray(), separator, segmentLength, writer);
		assertEquals(expected, writer.toString());
	}

	public void testSeparateOnCharArrayCharIntStringBuffer() {
		this.verifySeparateCharArrayOnStringBuffer("012345", '-', 22, "012345");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  6, "012345");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  5, "01234-5");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  4, "0123-45");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  3, "012-345");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArrayOnStringBuffer("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArrayOnStringBuffer(String string, char separator, int segmentLength, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.separateOn(string.toCharArray(), separator, segmentLength, sb);
		assertEquals(expected, sb.toString());
	}

	public void testSeparateOnCharArrayCharIntStringBuilder() {
		this.verifySeparateCharArrayOnStringBuilder("012345", '-', 22, "012345");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  6, "012345");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  5, "01234-5");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  4, "0123-45");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  3, "012-345");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  2, "01-23-45");
		this.verifySeparateCharArrayOnStringBuilder("012345", '-',  1, "0-1-2-3-4-5");
	}

	private void verifySeparateCharArrayOnStringBuilder(String string, char separator, int segmentLength, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.separateOn(string.toCharArray(), separator, segmentLength, sb);
		assertEquals(expected, sb.toString());
	}

	// ********** delimiting **********

	public void testDelimit() {
		this.verifyDelimit("Employee", "123", "123Employee123");
		this.verifyDelimit("123", "123", "123123123");
		this.verifyDelimit("", "123", "123123");
	}

	private void verifyDelimit(String string, String delimiter, String expectedString) {
		assertEquals(expectedString, StringTools.delimit(string, delimiter));
	}

	public void testDelimitOnWriter() {
		this.verifyDelimitOnWriter("Employee", "123", "123Employee123");
		this.verifyDelimitOnWriter("123", "123", "123123123");
		this.verifyDelimitOnWriter("", "123", "123123");
	}

	private void verifyDelimitOnWriter(String string, String delimiter, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.delimitOn(string, delimiter, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testDelimitOnStringBuffer() {
		this.verifyDelimitOnStringBuffer("Employee", "123", "123Employee123");
		this.verifyDelimitOnStringBuffer("123", "123", "123123123");
		this.verifyDelimitOnStringBuffer("", "123", "123123");
	}

	private void verifyDelimitOnStringBuffer(String string, String delimiter, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.delimitOn(string, delimiter, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testDelimitOnStringBuilder() {
		this.verifyDelimitOnStringBuilder("Employee", "123", "123Employee123");
		this.verifyDelimitOnStringBuilder("123", "123", "123123123");
		this.verifyDelimitOnStringBuilder("", "123", "123123");
	}

	private void verifyDelimitOnStringBuilder(String string, String delimiter, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.delimitOn(string, delimiter, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testQuote() {
		this.verifyQuote("Employee", "\"Employee\"");
		this.verifyQuote("123", "\"123\"");
		this.verifyQuote("", "\"\"");
		this.verifyQuote("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuote(String string, String expectedString) {
		assertEquals(expectedString, StringTools.quote(string));
	}

	public void testQuoteOnWriter() {
		this.verifyQuoteOnWriter("Employee", "\"Employee\"");
		this.verifyQuoteOnWriter("123", "\"123\"");
		this.verifyQuoteOnWriter("", "\"\"");
		this.verifyQuoteOnWriter("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuoteOnWriter(String string, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.quoteOn(string, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testQuoteOnStringBuffer() {
		this.verifyQuoteOnStringBuffer("Employee", "\"Employee\"");
		this.verifyQuoteOnStringBuffer("123", "\"123\"");
		this.verifyQuoteOnStringBuffer("", "\"\"");
		this.verifyQuoteOnStringBuffer("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuoteOnStringBuffer(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.quoteOn(string, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testQuoteOnStringBuilder() {
		this.verifyQuoteOnStringBuilder("Employee", "\"Employee\"");
		this.verifyQuoteOnStringBuilder("123", "\"123\"");
		this.verifyQuoteOnStringBuilder("", "\"\"");
		this.verifyQuoteOnStringBuilder("Emp\"loyee", "\"Emp\"\"loyee\"");
	}

	private void verifyQuoteOnStringBuilder(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.quoteOn(string, sb);
		assertEquals(expectedString, sb.toString());
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
		assertEquals(expectedString, StringTools.removeFirstOccurrence(string, charToRemove));
	}

	public void testRemoveFirstOccurrenceCharArray() {
		this.verifyRemoveFirstOccurrenceCharArray("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrenceCharArray("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrenceCharArray("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrenceCharArray("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrenceCharArray("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrenceCharArray(String string, char charToRemove, String expectedString) {
		assertEquals(expectedString, StringTools.removeFirstOccurrence(string.toCharArray(), charToRemove));
	}

	public void testRemoveFirstOccurrenceOnWriter() {
		this.verifyRemoveFirstOccurrenceOnWriter("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnWriter("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrenceOnWriter("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrenceOnWriter("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnWriter("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrenceOnWriter(String string, char charToRemove, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.removeFirstOccurrenceOn(string, charToRemove, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveFirstOccurrenceOnStringBuffer() {
		this.verifyRemoveFirstOccurrenceOnStringBuffer("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnStringBuffer("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrenceOnStringBuffer("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrenceOnStringBuffer("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnStringBuffer("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrenceOnStringBuffer(String string, char charToRemove, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.removeFirstOccurrenceOn(string, charToRemove, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveFirstOccurrenceOnStringBuilder() {
		this.verifyRemoveFirstOccurrenceOnStringBuilder("Emplo&yee", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnStringBuilder("Emplo&yee&", '&', "Employee&");
		this.verifyRemoveFirstOccurrenceOnStringBuilder("Employee &Foo", '&', "Employee Foo");
		this.verifyRemoveFirstOccurrenceOnStringBuilder("Employee&", '&', "Employee");
		this.verifyRemoveFirstOccurrenceOnStringBuilder("&Employee", '&', "Employee");
	}

	private void verifyRemoveFirstOccurrenceOnStringBuilder(String string, char charToRemove, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.removeFirstOccurrenceOn(string, charToRemove, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllOccurrences() {
		this.verifyRemoveAllOccurrences("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrences(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrences("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrences(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrences(String string, char charToRemove, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllOccurrences(string, charToRemove));
	}

	public void testRemoveAllOccurrencesCharArray() {
		this.verifyRemoveAllOccurrencesCharArray("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrencesCharArray(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrencesCharArray("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrencesCharArray(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrencesCharArray(String string, char charToRemove, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllOccurrences(string.toCharArray(), charToRemove));
	}

	public void testRemoveAllOccurrencesOnWriter() {
		this.verifyRemoveAllOccurrencesOnWriter("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrencesOnWriter(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrencesOnWriter("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrencesOnWriter(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrencesOnWriter(String string, char charToRemove, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.removeAllOccurrencesOn(string, charToRemove, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveAllOccurrencesOnStringBuffer() {
		this.verifyRemoveAllOccurrencesOnStringBuffer("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrencesOnStringBuffer(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrencesOnStringBuffer("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrencesOnStringBuffer(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrencesOnStringBuffer(String string, char charToRemove, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.removeAllOccurrencesOn(string, charToRemove, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllOccurrencesOnStringBuilder() {
		this.verifyRemoveAllOccurrencesOnStringBuilder("Employee Fred", ' ', "EmployeeFred");
		this.verifyRemoveAllOccurrencesOnStringBuilder(" Employee ", ' ', "Employee");
		this.verifyRemoveAllOccurrencesOnStringBuilder("Employee   Foo", ' ', "EmployeeFoo");
		this.verifyRemoveAllOccurrencesOnStringBuilder(" Emp loyee   Foo", ' ', "EmployeeFoo");
	}

	private void verifyRemoveAllOccurrencesOnStringBuilder(String string, char charToRemove, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.removeAllOccurrencesOn(string, charToRemove, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllWhitespace() {
		this.verifyRemoveAllWhitespace("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespace("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespace("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespace(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespace(String string, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllWhitespace(string));
	}

	public void testRemoveAllWhitespaceCharArray() {
		this.verifyRemoveAllWhitespaceCharArray("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespaceCharArray("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespaceCharArray("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespaceCharArray(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespaceCharArray(String string, String expectedString) {
		assertEquals(expectedString, StringTools.removeAllWhitespace(string.toCharArray()));
	}

	public void testRemoveAllWhitespaceOnWriter() {
		this.verifyRemoveAllWhitespaceOnWriter("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespaceOnWriter("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespaceOnWriter("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespaceOnWriter(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespaceOnWriter(String string, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.removeAllWhitespaceOn(string, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testRemoveAllWhitespaceOnStringBuffer() {
		this.verifyRemoveAllWhitespaceOnStringBuffer("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespaceOnStringBuffer("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespaceOnStringBuffer("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespaceOnStringBuffer(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespaceOnStringBuffer(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.removeAllWhitespaceOn(string, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testRemoveAllWhitespaceOnStringBuilder() {
		this.verifyRemoveAllWhitespaceOnStringBuilder("Employee Fred\t", "EmployeeFred");
		this.verifyRemoveAllWhitespaceOnStringBuilder("\tEmployee\n", "Employee");
		this.verifyRemoveAllWhitespaceOnStringBuilder("Employee \t Foo", "EmployeeFoo");
		this.verifyRemoveAllWhitespaceOnStringBuilder(" Emp\tloyee \n Foo", "EmployeeFoo");
	}

	private void verifyRemoveAllWhitespaceOnStringBuilder(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.removeAllWhitespaceOn(string, sb);
		assertEquals(expectedString, sb.toString());
	}
//////////////////////////////
	public void testCompressWhitespace() {
		this.verifyCompressWhitespace("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespace("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespace("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespace(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespace(String string, String expectedString) {
		assertEquals(expectedString, StringTools.compressWhitespace(string));
	}

	public void testCompressWhitespaceCharArray() {
		this.verifyCompressWhitespaceCharArray("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespaceCharArray("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespaceCharArray("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespaceCharArray(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespaceCharArray(String string, String expectedString) {
		assertEquals(expectedString, StringTools.compressWhitespace(string.toCharArray()));
	}

	public void testCompressWhitespaceOnWriter() {
		this.verifyCompressWhitespaceOnWriter("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespaceOnWriter("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespaceOnWriter("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespaceOnWriter(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespaceOnWriter(String string, String expectedString) {
		Writer writer = new StringWriter();
		StringTools.compressWhitespaceOn(string, writer);
		assertEquals(expectedString, writer.toString());
	}

	public void testCompressWhitespaceOnStringBuffer() {
		this.verifyCompressWhitespaceOnStringBuffer("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespaceOnStringBuffer("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespaceOnStringBuffer("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespaceOnStringBuffer(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespaceOnStringBuffer(String string, String expectedString) {
		StringBuffer sb = new StringBuffer();
		StringTools.compressWhitespaceOn(string, sb);
		assertEquals(expectedString, sb.toString());
	}

	public void testCompressWhitespaceOnStringBuilder() {
		this.verifyCompressWhitespaceOnStringBuilder("Employee      Fred\t", "Employee Fred ");
		this.verifyCompressWhitespaceOnStringBuilder("\tEmployee  \n", " Employee ");
		this.verifyCompressWhitespaceOnStringBuilder("Employee \t Foo", "Employee Foo");
		this.verifyCompressWhitespaceOnStringBuilder(" Emp\tloyee \n Foo ", " Emp loyee Foo ");
	}

	private void verifyCompressWhitespaceOnStringBuilder(String string, String expectedString) {
		StringBuilder sb = new StringBuilder();
		StringTools.compressWhitespaceOn(string, sb);
		assertEquals(expectedString, sb.toString());
	}

	// ********** common prefix **********

	public void testCommonPrefixLength() {
		assertEquals(3, StringTools.commonPrefixLength("fooZZZ", "fooBBB"));
		assertEquals(3, StringTools.commonPrefixLength("foo", "fooBBB"));
		assertEquals(3, StringTools.commonPrefixLength("fooZZZ", "foo"));
		assertEquals(3, StringTools.commonPrefixLength("foo", "foo"));
	}

	public void testCommonPrefixLengthMax() {
		assertEquals(2, StringTools.commonPrefixLength("fooZZZ", "fooBBB", 2));
		assertEquals(2, StringTools.commonPrefixLength("foo", "fooBBB", 2));
		assertEquals(2, StringTools.commonPrefixLength("fooZZZ", "foo", 2));
		assertEquals(2, StringTools.commonPrefixLength("foo", "foo", 2));
	}

	// ********** capitalization **********

	public void testCapitalizeCharArray() {
		this.verifyCapitalizeCharArray("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeCharArray("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeCharArray("", new char[0]);
		this.verifyCapitalizeCharArray("A", new char[] { 'a' });
		this.verifyCapitalizeCharArray("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeCharArray(String expected, char[] string) {
		assertEquals(expected, StringTools.capitalize(string));
	}

	public void testCapitalizeString() {
		this.verifyCapitalizeString("Oracle", "Oracle");
		this.verifyCapitalizeString("Oracle", "oracle");
		this.verifyCapitalizeString("   ", "   ");
		this.verifyCapitalizeString("ORACLE", "ORACLE");
		this.verifyCapitalizeString("", "");
		this.verifyCapitalizeString("A", "a");
		this.verifyCapitalizeString("\u00C9cole", "\u00E9cole"); // �cole->�COLE
	}

	private void verifyCapitalizeString(String expected, String string) {
		assertEquals(expected, StringTools.capitalize(string));
	}

	public void testCapitalizeOnCharArrayStringBuffer() {
		this.verifyCapitalizeOnCharArrayStringBuffer("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayStringBuffer("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayStringBuffer("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeOnCharArrayStringBuffer("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeOnCharArrayStringBuffer("", new char[0]);
		this.verifyCapitalizeOnCharArrayStringBuffer("A", new char[] { 'a' });
		this.verifyCapitalizeOnCharArrayStringBuffer("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeOnCharArrayStringBuffer(String expected, char[] string) {
		StringBuffer sb = new StringBuffer();
		StringTools.capitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testCapitalizeOnCharArrayStringBuilder() {
		this.verifyCapitalizeOnCharArrayStringBuilder("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayStringBuilder("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayStringBuilder("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeOnCharArrayStringBuilder("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeOnCharArrayStringBuilder("", new char[0]);
		this.verifyCapitalizeOnCharArrayStringBuilder("A", new char[] { 'a' });
		this.verifyCapitalizeOnCharArrayStringBuilder("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeOnCharArrayStringBuilder(String expected, char[] string) {
		StringBuilder sb = new StringBuilder();
		StringTools.capitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testCapitalizeOnStringStringBuffer() {
		this.verifyCapitalizeOnStringStringBuffer("Oracle", "Oracle");
		this.verifyCapitalizeOnStringStringBuffer("Oracle", "oracle");
		this.verifyCapitalizeOnStringStringBuffer("   ", "   ");
		this.verifyCapitalizeOnStringStringBuffer("ORACLE", "ORACLE");
		this.verifyCapitalizeOnStringStringBuffer("", "");
		this.verifyCapitalizeOnStringStringBuffer("A", "a");
		this.verifyCapitalizeOnStringStringBuffer("\u00C9cole", "\u00E9cole"); // �cole->�COLE
	}

	private void verifyCapitalizeOnStringStringBuffer(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringTools.capitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testCapitalizeOnStringStringBuilder() {
		this.verifyCapitalizeOnStringStringBuilder("Oracle", "Oracle");
		this.verifyCapitalizeOnStringStringBuilder("Oracle", "oracle");
		this.verifyCapitalizeOnStringStringBuilder("   ", "   ");
		this.verifyCapitalizeOnStringStringBuilder("ORACLE", "ORACLE");
		this.verifyCapitalizeOnStringStringBuilder("", "");
		this.verifyCapitalizeOnStringStringBuilder("A", "a");
		this.verifyCapitalizeOnStringStringBuilder("\u00C9cole", "\u00E9cole"); // �cole->�COLE
	}

	private void verifyCapitalizeOnStringStringBuilder(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringTools.capitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testCapitalizeOnCharArrayWriter() {
		this.verifyCapitalizeOnCharArrayWriter("Oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayWriter("Oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyCapitalizeOnCharArrayWriter("   ", new char[] { ' ', ' ', ' ' });
		this.verifyCapitalizeOnCharArrayWriter("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyCapitalizeOnCharArrayWriter("", new char[0]);
		this.verifyCapitalizeOnCharArrayWriter("A", new char[] { 'a' });
		this.verifyCapitalizeOnCharArrayWriter("\u00C9cole", new char[] { '\u00E9', 'c', 'o', 'l', 'e' });
	}

	private void verifyCapitalizeOnCharArrayWriter(String expected, char[] string) {
		Writer writer = new StringWriter();
		StringTools.capitalizeOn(string, writer);
		assertEquals(expected, writer.toString());
	}

	public void testCapitalizeOnStringWriter() {
		this.verifyCapitalizeOnStringWriter("Oracle", "Oracle");
		this.verifyCapitalizeOnStringWriter("Oracle", "oracle");
		this.verifyCapitalizeOnStringWriter("   ", "   ");
		this.verifyCapitalizeOnStringWriter("ORACLE", "ORACLE");
		this.verifyCapitalizeOnStringWriter("", "");
		this.verifyCapitalizeOnStringWriter("A", "a");
		this.verifyCapitalizeOnStringWriter("\u00C9cole", "\u00E9cole"); // �cole->�COLE
	}

	private void verifyCapitalizeOnStringWriter(String expected, String string) {
		Writer writer = new StringWriter();
		StringTools.capitalizeOn(string, writer);
		assertEquals(expected, writer.toString());
	}

	public void testUnapitalizeCharArray() {
		this.verifyUncapitalizeCharArray("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeCharArray("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeCharArray("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeCharArray("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeCharArray("", new char[0]);
		this.verifyUncapitalizeCharArray("a", new char[] { 'A' });
		this.verifyUncapitalizeCharArray("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeCharArray(String expected, char[] string) {
		assertEquals(expected, StringTools.uncapitalize(string));
	}

	public void testUncapitalizeString() {
		this.verifyUncapitalizeString("oracle", "Oracle");
		this.verifyUncapitalizeString("oracle", "oracle");
		this.verifyUncapitalizeString("   ", "   ");
		this.verifyUncapitalizeString("ORACLE", "ORACLE");
		this.verifyUncapitalizeString("", "");
		this.verifyUncapitalizeString("a", "A");
		this.verifyUncapitalizeString("\u00E9cole", "\u00C9cole"); // �cole->�COLE
	}

	private void verifyUncapitalizeString(String expected, String string) {
		assertEquals(expected, StringTools.uncapitalize(string));
	}

	public void testUncapitalizeOnCharArrayStringBuffer() {
		this.verifyUncapitalizeOnCharArrayStringBuffer("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayStringBuffer("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayStringBuffer("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeOnCharArrayStringBuffer("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeOnCharArrayStringBuffer("", new char[0]);
		this.verifyUncapitalizeOnCharArrayStringBuffer("a", new char[] { 'A' });
		this.verifyUncapitalizeOnCharArrayStringBuffer("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeOnCharArrayStringBuffer(String expected, char[] string) {
		StringBuffer sb = new StringBuffer();
		StringTools.uncapitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnCharArrayStringBuilder() {
		this.verifyUncapitalizeOnCharArrayStringBuilder("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayStringBuilder("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayStringBuilder("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeOnCharArrayStringBuilder("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeOnCharArrayStringBuilder("", new char[0]);
		this.verifyUncapitalizeOnCharArrayStringBuilder("a", new char[] { 'A' });
		this.verifyUncapitalizeOnCharArrayStringBuilder("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeOnCharArrayStringBuilder(String expected, char[] string) {
		StringBuilder sb = new StringBuilder();
		StringTools.uncapitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnStringStringBuffer() {
		this.verifyUncapitalizeOnStringStringBuffer("oracle", "Oracle");
		this.verifyUncapitalizeOnStringStringBuffer("oracle", "oracle");
		this.verifyUncapitalizeOnStringStringBuffer("   ", "   ");
		this.verifyUncapitalizeOnStringStringBuffer("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnStringStringBuffer("", "");
		this.verifyUncapitalizeOnStringStringBuffer("a", "A");
		this.verifyUncapitalizeOnStringStringBuffer("\u00E9cole", "\u00C9cole"); // �cole->�COLE
	}

	private void verifyUncapitalizeOnStringStringBuffer(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringTools.uncapitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnStringStringBuilder() {
		this.verifyUncapitalizeOnStringStringBuilder("oracle", "Oracle");
		this.verifyUncapitalizeOnStringStringBuilder("oracle", "oracle");
		this.verifyUncapitalizeOnStringStringBuilder("   ", "   ");
		this.verifyUncapitalizeOnStringStringBuilder("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnStringStringBuilder("", "");
		this.verifyUncapitalizeOnStringStringBuilder("a", "A");
		this.verifyUncapitalizeOnStringStringBuilder("\u00E9cole", "\u00C9cole"); // �cole->�COLE
	}

	private void verifyUncapitalizeOnStringStringBuilder(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringTools.uncapitalizeOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testUncapitalizeOnCharArrayWriter() {
		this.verifyUncapitalizeOnCharArrayWriter("oracle", new char[] { 'O', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayWriter("oracle", new char[] { 'o', 'r', 'a', 'c', 'l', 'e' });
		this.verifyUncapitalizeOnCharArrayWriter("   ", new char[] { ' ', ' ', ' ' });
		this.verifyUncapitalizeOnCharArrayWriter("ORACLE", new char[] { 'O', 'R', 'A', 'C', 'L', 'E' });
		this.verifyUncapitalizeOnCharArrayWriter("", new char[0]);
		this.verifyUncapitalizeOnCharArrayWriter("a", new char[] { 'A' });
		this.verifyUncapitalizeOnCharArrayWriter("\u00E9cole", new char[] { '\u00C9', 'c', 'o', 'l', 'e' });
	}

	private void verifyUncapitalizeOnCharArrayWriter(String expected, char[] string) {
		Writer writer = new StringWriter();
		StringTools.uncapitalizeOn(string, writer);
		assertEquals(expected, writer.toString());
	}

	public void testUncapitalizeOnStringWriter() {
		this.verifyUncapitalizeOnStringWriter("oracle", "Oracle");
		this.verifyUncapitalizeOnStringWriter("oracle", "oracle");
		this.verifyUncapitalizeOnStringWriter("   ", "   ");
		this.verifyUncapitalizeOnStringWriter("ORACLE", "ORACLE");
		this.verifyUncapitalizeOnStringWriter("", "");
		this.verifyUncapitalizeOnStringWriter("a", "A");
		this.verifyUncapitalizeOnStringWriter("\u00E9cole", "\u00C9cole"); // �cole->�COLE
	}

	private void verifyUncapitalizeOnStringWriter(String expected, String string) {
		Writer writer = new StringWriter();
		StringTools.uncapitalizeOn(string, writer);
		assertEquals(expected, writer.toString());
	}

	// ********** #toString() **********

	public void testBuildToStringClassName_anonymous() {
		Object o = new Object(){/*anonymous subclass of Object*/};
		assertEquals("Object", StringTools.buildToStringClassName(o.getClass()));
	}

	public void testBuildToStringClassName_member() {
		assertEquals("Map.Entry", StringTools.buildToStringClassName(java.util.Map.Entry.class));
	}

	public void testBuildToStringClassName_local() {
		class Foo {
			Bar bar = new Bar();
			class Bar {
				Bar() {
					super();
				}
			}
			Foo() {
				super();
			}
		}
		Foo foo = new Foo();
		assertEquals("StringToolsTests.Foo", StringTools.buildToStringClassName(foo.getClass()));
		assertEquals("StringToolsTests.Foo.Bar", StringTools.buildToStringClassName(foo.bar.getClass()));
	}

	// ********** queries **********

	public void testStringIsEmptyString() {
		assertTrue(StringTools.stringIsEmpty((String) null));
		assertTrue(StringTools.stringIsEmpty(""));
		assertTrue(StringTools.stringIsEmpty("      "));
		assertTrue(StringTools.stringIsEmpty("      \t\t   "));
		assertTrue(StringTools.stringIsEmpty("      \t\t   " + StringTools.CR));
	}

	public void testStringIsEmptyCharArray() {
		assertTrue(StringTools.stringIsEmpty((char[]) null));
		this.verifyStringIsEmptyCharArray("");
		this.verifyStringIsEmptyCharArray("      \t\t   ");
		this.verifyStringIsEmptyCharArray("      ");
		this.verifyStringIsEmptyCharArray("      \t\t   " + StringTools.CR);
	}

	private void verifyStringIsEmptyCharArray(String string) {
		assertTrue(StringTools.stringIsEmpty(string.toCharArray()));
	}

	public void testStringsAreEqualStringString() {
		assertTrue(StringTools.stringsAreEqual((String) null, (String) null));
		assertFalse(StringTools.stringsAreEqual(null, "asdf"));
		assertFalse(StringTools.stringsAreEqual("asdf", null));
		assertTrue(StringTools.stringsAreEqual("asdf", "asdf"));
		assertFalse(StringTools.stringsAreEqual("asdf", "ASDF"));
	}

	public void testStringsAreEqualCharArrayCharArray() {
		assertTrue(StringTools.stringsAreEqual((char[]) null, (char[]) null));
		assertFalse(StringTools.stringsAreEqual((char[]) null, "asdf".toCharArray()));
		assertFalse(StringTools.stringsAreEqual("asdf".toCharArray(), (char[]) null));
		assertTrue(StringTools.stringsAreEqual("asdf".toCharArray(), "asdf".toCharArray()));
		assertFalse(StringTools.stringsAreEqual("asdf".toCharArray(), "ASDF".toCharArray()));
	}

	public void testStringsAreEqualIgnoreCaseStringString() {
		assertTrue(StringTools.stringsAreEqualIgnoreCase((String) null, (String) null));
		assertFalse(StringTools.stringsAreEqualIgnoreCase(null, "asdf"));
		assertFalse(StringTools.stringsAreEqualIgnoreCase("asdf", null));
		assertTrue(StringTools.stringsAreEqualIgnoreCase("asdf", "asdf"));
		assertTrue(StringTools.stringsAreEqualIgnoreCase("asdf", "ASDF"));
	}

	public void testStringsAreEqualIgnoreCaseCharArrayCharArray() {
		assertTrue(StringTools.stringsAreEqualIgnoreCase((char[]) null, (char[]) null));
		assertFalse(StringTools.stringsAreEqualIgnoreCase((char[]) null, "asdf".toCharArray()));
		assertFalse(StringTools.stringsAreEqualIgnoreCase("asdf".toCharArray(), (char[]) null));
		assertTrue(StringTools.stringsAreEqualIgnoreCase("asdf".toCharArray(), "asdf".toCharArray()));
		assertTrue(StringTools.stringsAreEqualIgnoreCase("asdf".toCharArray(), "ASDF".toCharArray()));
	}

	public void testStringStartsWithIgnoreCaseStringString() {
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf", "as"));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf", "aS"));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf", ""));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf", "A"));

		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf", "bsdf"));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf", "g"));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf", "asdg"));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf", "asdfg"));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf", "asdfgggggg"));
	}

	public void testStringStartsWithIgnoreCaseCharArrayCharArray() {
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "as".toCharArray()));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "aS".toCharArray()));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "".toCharArray()));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "A".toCharArray()));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "ASDF".toCharArray()));
		assertTrue(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "asdf".toCharArray()));

		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "bsdf".toCharArray()));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "g".toCharArray()));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "asdg".toCharArray()));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "asdfg".toCharArray()));
		assertFalse(StringTools.stringStartsWithIgnoreCase("asdf".toCharArray(), "asdfgggggg".toCharArray()));
	}

	public void testCharactersAreEqualIgnoreCase() {
		assertTrue(StringTools.charactersAreEqualIgnoreCase('a', 'a'));
		assertTrue(StringTools.charactersAreEqualIgnoreCase('a', 'A'));
		assertTrue(StringTools.charactersAreEqualIgnoreCase('A', 'a'));
		assertTrue(StringTools.charactersAreEqualIgnoreCase('A', 'A'));

		assertFalse(StringTools.charactersAreEqualIgnoreCase('a', 'b'));
		assertFalse(StringTools.charactersAreEqualIgnoreCase('A', 'b'));
	}

	public void testStringIsUppercase() {
		this.verifyStringIsUppercase("FOO");
		this.verifyStringIsUppercase("FOO2");
		this.verifyStringIsUppercase("F O O");
		this.denyStringIsUppercase("Foo");
		this.denyStringIsUppercase("");
	}

	private void verifyStringIsUppercase(String s) {
		assertTrue(StringTools.stringIsUppercase(s));
		assertTrue(StringTools.stringIsUppercase(s.toCharArray()));
	}

	private void denyStringIsUppercase(String s) {
		assertFalse(StringTools.stringIsUppercase(s));
		assertFalse(StringTools.stringIsUppercase(s.toCharArray()));
	}

	public void testStringIsLowercase() {
		this.verifyStringIsLowercase("foo");
		this.verifyStringIsLowercase("foo2");
		this.verifyStringIsLowercase("f o o");
		this.denyStringIsLowercase("Foo");
		this.denyStringIsLowercase("");
	}

	private void verifyStringIsLowercase(String s) {
		assertTrue(StringTools.stringIsLowercase(s));
		assertTrue(StringTools.stringIsLowercase(s.toCharArray()));
	}

	private void denyStringIsLowercase(String s) {
		assertFalse(StringTools.stringIsLowercase(s));
		assertFalse(StringTools.stringIsLowercase(s.toCharArray()));
	}

	// ********** convert camel-case to all-caps **********

	public void testConvertCamelCaseToAllCaps() {
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test"));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("TEST"));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTest"));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest"));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTESTTest"));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTESTTest"));
		assertEquals("TEST_TEST_TEST_T", StringTools.convertCamelCaseToAllCaps("TestTESTTestT"));
	}

	public void testConvertCamelCaseToAllCapsOnWriter() {
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST", "test");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnWriter("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	private void verifyConvertCamelCaseToAllCapsOnWriter(String expected, String string) {
		Writer writer = new StringWriter();
		StringTools.convertCamelCaseToAllCapsOn(string, writer);
		assertEquals(expected, writer.toString());
	}

	public void testConvertCamelCaseToAllCapsOnStringBuffer() {
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST", "test");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuffer("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	private void verifyConvertCamelCaseToAllCapsOnStringBuffer(String expected, String string) {
		StringBuffer sb = new StringBuffer();
		StringTools.convertCamelCaseToAllCapsOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertCamelCaseToAllCapsOnStringBuilder() {
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST", "test");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST", "TEST");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST_TEST", "testTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST_TEST", "TestTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST_TEST_TEST", "testTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST_TEST_TEST", "TestTESTTest");
		this.verifyConvertCamelCaseToAllCapsOnStringBuilder("TEST_TEST_TEST_T", "TestTESTTestT");
	}

	private void verifyConvertCamelCaseToAllCapsOnStringBuilder(String expected, String string) {
		StringBuilder sb = new StringBuilder();
		StringTools.convertCamelCaseToAllCapsOn(string, sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertCamelCaseToAllCapsMaxLength() {
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test", 44));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("test", 4));
		assertEquals("TES", StringTools.convertCamelCaseToAllCaps("test", 3));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("TEST", 5));
		assertEquals("TE", StringTools.convertCamelCaseToAllCaps("TEST", 2));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("testTest", 9));
		assertEquals("TEST_TES", StringTools.convertCamelCaseToAllCaps("testTest", 8));
		assertEquals("TEST_T", StringTools.convertCamelCaseToAllCaps("testTest", 6));
		assertEquals("TEST_", StringTools.convertCamelCaseToAllCaps("testTest", 5));
		assertEquals("TEST", StringTools.convertCamelCaseToAllCaps("testTest", 4));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest", 9));
		assertEquals("TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTest", 1100));
		assertEquals("TEST_TEST_", StringTools.convertCamelCaseToAllCaps("testTESTTest", 10));
		assertEquals("TEST_TEST_TEST", StringTools.convertCamelCaseToAllCaps("TestTESTTest", 14));
		assertEquals("TEST_TEST_TEST_T", StringTools.convertCamelCaseToAllCaps("TestTESTTestT", 16));
		assertEquals("TEST_TEST_TEST_", StringTools.convertCamelCaseToAllCaps("TestTESTTestT", 15));
	}

	public void testConvertCamelCaseToAllCapsMaxLengthOnWriter() {
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST", "test", 44);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST", "test", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TES", "test", 3);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST", "TEST", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TE", "TEST", 2);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST", "testTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TES", "testTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_T", "testTest", 6);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_", "testTest", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST", "testTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST", "TestTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST", "TestTest", 1100);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST_", "testTESTTest", 10);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST_TEST", "TestTESTTest", 14);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST_TEST_T", "TestTESTTestT", 16);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnWriter("TEST_TEST_TEST_", "TestTESTTestT", 15);
	}

	private void verifyConvertCamelCaseToAllCapsMaxLengthOnWriter(String expected, String string, int max) {
		Writer writer = new StringWriter();
		StringTools.convertCamelCaseToAllCapsOn(string, max, writer);
		assertEquals(expected, writer.toString());
	}

	public void testConvertCamelCaseToAllCapsMaxLengthOnStringBuffer() {
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST", "test", 44);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST", "test", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TES", "test", 3);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST", "TEST", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TE", "TEST", 2);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST", "testTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TES", "testTest", 8);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_T", "testTest", 6);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_", "testTest", 5);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST", "testTest", 4);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST", "TestTest", 9);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST", "TestTest", 1100);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST_", "testTESTTest", 10);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST_TEST", "TestTESTTest", 14);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST_TEST_T", "TestTESTTestT", 16);
		this.verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer("TEST_TEST_TEST_", "TestTESTTestT", 15);
	}

	private void verifyConvertCamelCaseToAllCapsMaxLengthOnStringBuffer(String expected, String string, int max) {
		StringBuffer sb = new StringBuffer();
		StringTools.convertCamelCaseToAllCapsOn(string, max, sb);
		assertEquals(expected, sb.toString());
	}

	// ********** convert underscores to all-caps **********

	public void testConvertUnderscoresToCamelCase() {
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("TEST", false));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("TEST_", false));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("TEST____", false));
		assertEquals("Test", StringTools.convertUnderscoresToCamelCase("TEST", true));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("TeST", false));
		assertEquals("testTest", StringTools.convertUnderscoresToCamelCase("TEST_TEST", false));
		assertEquals("testTest", StringTools.convertUnderscoresToCamelCase("TEST___TEST", false));
		assertEquals("TestTest", StringTools.convertUnderscoresToCamelCase("TEST_TEST", true));
		assertEquals("testTestTest", StringTools.convertUnderscoresToCamelCase("TEST_TEST_TEST", false));
		assertEquals("TestTestTest", StringTools.convertUnderscoresToCamelCase("TEST_TEST_TEST", true));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("TEST_TEST_TEST_T", false));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("_TEST_TEST_TEST_T", false));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("__TEST_TEST_TEST_T", false));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("TEST_TEST_TEST_T", true));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("_TEST_TEST_TEST_T", true));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("__TEST_TEST_TEST_T", true));
	}

	public void testConvertUnderscoresToCamelCaseLowercase() {
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("test", false));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("test_", false));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("test____", false));
		assertEquals("Test", StringTools.convertUnderscoresToCamelCase("test", true));
		assertEquals("test", StringTools.convertUnderscoresToCamelCase("test", false));
		assertEquals("testTest", StringTools.convertUnderscoresToCamelCase("test_test", false));
		assertEquals("testTest", StringTools.convertUnderscoresToCamelCase("test___test", false));
		assertEquals("TestTest", StringTools.convertUnderscoresToCamelCase("test_test", true));
		assertEquals("testTestTest", StringTools.convertUnderscoresToCamelCase("test_test_test", false));
		assertEquals("TestTestTest", StringTools.convertUnderscoresToCamelCase("test_test_test", true));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("test_test_test_t", false));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("_test_test_test_t", false));
		assertEquals("testTestTestT", StringTools.convertUnderscoresToCamelCase("__test_test_test_t", false));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("test_test_test_t", true));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("_test_test_test_t", true));
		assertEquals("TestTestTestT", StringTools.convertUnderscoresToCamelCase("__test_test_test_t", true));
	}

	public void testConvertUnderscoresToCamelCaseOnWriter() {
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "TEST_", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "TEST____", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("Test", "TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "TeST", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTest", "TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTest", "TEST___TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTest", "TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTest", "TEST_TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTest", "TEST_TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "_TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "__TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "_TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "__TEST_TEST_TEST_T", true);
	}

	public void testConvertUnderscoresToCamelCaseOnWriterLowercase() {
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "test", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "test_", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "test____", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("Test", "test", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("test", "test", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTest", "test_test", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTest", "test___test", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTest", "test_test", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTest", "test_test_test", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTest", "test_test_test", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "_test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("testTestTestT", "__test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "_test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCaseOnWriter("TestTestTestT", "__test_test_test_t", true);
	}

	private void verifyConvertUnderscoresToCamelCaseOnWriter(String expected, String string, boolean capitalizeFirstLetter) {
		Writer writer = new StringWriter();
		StringTools.convertUnderscoresToCamelCaseOn(string, capitalizeFirstLetter, writer);
		assertEquals(expected, writer.toString());
	}

	public void testConvertUnderscoresToCamelCaseOnStringBuffer() {
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "TEST_", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "TEST____", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("Test", "TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "TeST", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTest", "TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTest", "TEST___TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTest", "TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTest", "TEST_TEST_TEST", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTest", "TEST_TEST_TEST", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "_TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "__TEST_TEST_TEST_T", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "_TEST_TEST_TEST_T", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "__TEST_TEST_TEST_T", true);
	}

	public void testConvertUnderscoresToCamelCaseOnStringBufferLowercase() {
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "test", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "test_", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "test____", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("Test", "test", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("test", "test", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTest", "test_test", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTest", "test___test", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTest", "test_test", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTest", "test_test_test", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTest", "test_test_test", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "_test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("testTestTestT", "__test_test_test_t", false);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "_test_test_test_t", true);
		this.verifyConvertUnderscoresToCamelCaseOnStringBuffer("TestTestTestT", "__test_test_test_t", true);
	}

	private void verifyConvertUnderscoresToCamelCaseOnStringBuffer(String expected, String string, boolean capitalizeFirstLetter) {
		StringBuffer sb = new StringBuffer();
		StringTools.convertUnderscoresToCamelCaseOn(string, capitalizeFirstLetter, sb);
		assertEquals(expected, sb.toString());
	}

	// ********** delimiting **********

	public void testStringIsQuoted() {
		this.denyStringIsQuoted("foo");
		this.verifyStringIsQuoted("\"foo\"");

		this.denyStringIsQuoted("");
		this.verifyStringIsQuoted("\"\"");

		this.denyStringIsQuoted("\"");
		this.denyStringIsQuoted(" ");
		this.denyStringIsQuoted("''");
		this.denyStringIsQuoted("'foo'");
	}

	private void verifyStringIsQuoted(String s) {
		assertTrue(StringTools.stringIsQuoted(s));
		assertTrue(StringTools.stringIsQuoted(s.toCharArray()));
	}

	private void denyStringIsQuoted(String s) {
		assertFalse(StringTools.stringIsQuoted(s));
		assertFalse(StringTools.stringIsQuoted(s.toCharArray()));
	}

	public void testStringIsParenthetical() {
		this.denyStringIsParenthetical("foo");
		this.verifyStringIsParenthetical("(foo)");

		this.denyStringIsParenthetical("");
		this.verifyStringIsParenthetical("()");

		this.denyStringIsParenthetical("(");
		this.denyStringIsParenthetical(" ");
		this.denyStringIsParenthetical("''");
		this.denyStringIsParenthetical("'foo'");
	}

	private void verifyStringIsParenthetical(String s) {
		assertTrue(StringTools.stringIsParenthetical(s));
		assertTrue(StringTools.stringIsParenthetical(s.toCharArray()));
	}

	private void denyStringIsParenthetical(String s) {
		assertFalse(StringTools.stringIsParenthetical(s));
		assertFalse(StringTools.stringIsParenthetical(s.toCharArray()));
	}

	public void testStringIsBracketed() {
		this.denyStringIsBracketed("foo");
		this.verifyStringIsBracketed("[foo]");

		this.denyStringIsBracketed("");
		this.verifyStringIsBracketed("[]");

		this.denyStringIsBracketed("[");
		this.denyStringIsBracketed(" ");
		this.denyStringIsBracketed("''");
		this.denyStringIsBracketed("'foo'");
	}

	private void verifyStringIsBracketed(String s) {
		assertTrue(StringTools.stringIsBracketed(s));
		assertTrue(StringTools.stringIsBracketed(s.toCharArray()));
	}

	private void denyStringIsBracketed(String s) {
		assertFalse(StringTools.stringIsBracketed(s));
		assertFalse(StringTools.stringIsBracketed(s.toCharArray()));
	}

	public void testStringIsBraced() {
		this.denyStringIsBraced("foo");
		this.verifyStringIsBraced("{foo}");

		this.denyStringIsBraced("");
		this.verifyStringIsBraced("{}");

		this.denyStringIsBraced("{");
		this.denyStringIsBraced(" ");
		this.denyStringIsBraced("''");
		this.denyStringIsBraced("'foo'");
	}

	private void verifyStringIsBraced(String s) {
		assertTrue(StringTools.stringIsBraced(s));
		assertTrue(StringTools.stringIsBraced(s.toCharArray()));
	}

	private void denyStringIsBraced(String s) {
		assertFalse(StringTools.stringIsBraced(s));
		assertFalse(StringTools.stringIsBraced(s.toCharArray()));
	}

	public void testStringIsChevroned() {
		this.denyStringIsChevroned("foo");
		this.verifyStringIsChevroned("<foo>");

		this.denyStringIsChevroned("");
		this.verifyStringIsChevroned("<>");

		this.denyStringIsChevroned("{");
		this.denyStringIsChevroned(" ");
		this.denyStringIsChevroned("''");
		this.denyStringIsChevroned("'foo'");
	}

	private void verifyStringIsChevroned(String s) {
		assertTrue(StringTools.stringIsChevroned(s));
		assertTrue(StringTools.stringIsChevroned(s.toCharArray()));
	}

	private void denyStringIsChevroned(String s) {
		assertFalse(StringTools.stringIsChevroned(s));
		assertFalse(StringTools.stringIsChevroned(s.toCharArray()));
	}

	public void testStringIsDelimited() {
		this.denyStringIsDelimited("foo", '?');
		this.verifyStringIsDelimited("?foo?", '?');

		this.denyStringIsDelimited("", '?');
		this.verifyStringIsDelimited("\"\"", '"');
		this.verifyStringIsDelimited("?xx?", '?');
		this.denyStringIsDelimited("?xx]", '?');

		this.denyStringIsDelimited("\"", '"');
		this.denyStringIsDelimited(" ", ' ');
		this.denyStringIsDelimited("''", '"');
		this.denyStringIsDelimited("'foo'", '?');
	}

	private void verifyStringIsDelimited(String s, char c) {
		assertTrue(StringTools.stringIsDelimited(s, c));
		assertTrue(StringTools.stringIsDelimited(s.toCharArray(), c));
	}

	private void denyStringIsDelimited(String s, char c) {
		assertFalse(StringTools.stringIsDelimited(s, c));
		assertFalse(StringTools.stringIsDelimited(s.toCharArray(), c));
	}

	public void testStringIsDelimited2() {
		this.denyStringIsDelimited2("foo", '[', ']');
		this.verifyStringIsDelimited2("{foo}", '{', '}');

		this.denyStringIsDelimited2("", '[', ']');
		this.verifyStringIsDelimited2("[]", '[', ']');
		this.verifyStringIsDelimited2("[xx]", '[', ']');
		this.denyStringIsDelimited2("?xx]", '[', ']');

		this.denyStringIsDelimited2("\"", '[', ']');
		this.denyStringIsDelimited2(" ", '[', ']');
		this.denyStringIsDelimited2("''", '[', ']');
		this.denyStringIsDelimited2("'foo'", '[', ']');
	}

	private void verifyStringIsDelimited2(String s, char start, char end) {
		assertTrue(StringTools.stringIsDelimited(s, start, end));
		assertTrue(StringTools.stringIsDelimited(s.toCharArray(), start, end));
	}

	private void denyStringIsDelimited2(String s, char start, char end) {
		assertFalse(StringTools.stringIsDelimited(s, start, end));
		assertFalse(StringTools.stringIsDelimited(s.toCharArray(), start, end));
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
		assertEquals(expected, StringTools.undelimit(s));
		assertEquals(expected, StringTools.undelimit(s.toCharArray()));
	}

	public void testUndelimitInt() {
		this.verifyUndelimitInt("\"foo\"", 2, "o");
		this.verifyUndelimitInt("\"\"foo\"\"", 2, "foo");
		this.verifyUndelimitInt("'foo'", 2, "o");
	}

	private void verifyUndelimitInt(String s, int count, String expected) {
		assertEquals(expected, StringTools.undelimit(s, count));
		assertEquals(expected, StringTools.undelimit(s.toCharArray(), count));
	}

	public void testUndelimitIntException() {
		this.denyUndelimitInt("\"\"", 2);
		this.denyUndelimitInt("'o'", 2);
	}

	private void denyUndelimitInt(String s, int count) {
		boolean exCaught = false;
		try {
			String bogus = StringTools.undelimit(s, count);
			fail("invalid string: " + bogus);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			char[] bogus = StringTools.undelimit(s.toCharArray(), count);
			fail("invalid string: " + new String(bogus));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUndelimitOnWriter() {
		this.verifyUndelimitOnWriter("\"foo\"", "foo");
		this.verifyUndelimitOnWriter("\"\"", "");
		this.verifyUndelimitOnWriter("'foo'", "foo");
		this.verifyUndelimitOnWriter("\"fo\"\"o\"", "fo\"o");
		this.verifyUndelimitOnWriter("\"foo\"\"\"", "foo\"");
		this.verifyUndelimitOnWriter("\"\"\"foo\"", "\"foo");
		this.verifyUndelimitOnWriter("[foo]", "foo");
		this.verifyUndelimitOnWriter("\"\"\"", "\"");
		this.verifyUndelimitOnWriter("\"foo\"bar\"", "foo\"");
		this.verifyUndelimitOnWriter("\"foo\"\"", "foo\"");
	}

	private void verifyUndelimitOnWriter(String s, String expected) {
		Writer writer = new StringWriter();
		StringTools.undelimitOn(s, writer);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		StringTools.undelimitOn(s.toCharArray(), writer);
		assertEquals(expected, writer.toString());
	}

	public void testUndelimitOnStringBuffer() {
		this.verifyUndelimitOnStringBuffer("\"foo\"", "foo");
		this.verifyUndelimitOnStringBuffer("\"\"", "");
		this.verifyUndelimitOnStringBuffer("'foo'", "foo");
		this.verifyUndelimitOnStringBuffer("\"fo\"\"o\"", "fo\"o");
		this.verifyUndelimitOnStringBuffer("\"foo\"\"\"", "foo\"");
		this.verifyUndelimitOnStringBuffer("\"\"\"foo\"", "\"foo");
		this.verifyUndelimitOnStringBuffer("[foo]", "foo");
		this.verifyUndelimitOnStringBuffer("\"\"\"", "\"");
		this.verifyUndelimitOnStringBuffer("\"foo\"bar\"", "foo\"");
		this.verifyUndelimitOnStringBuffer("\"foo\"\"", "foo\"");
	}

	private void verifyUndelimitOnStringBuffer(String s, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.undelimitOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringTools.undelimitOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testUndelimitOnStringBuilder() {
		this.verifyUndelimitOnStringBuilder("\"foo\"", "foo");
		this.verifyUndelimitOnStringBuilder("\"\"", "");
		this.verifyUndelimitOnStringBuilder("'foo'", "foo");
		this.verifyUndelimitOnStringBuilder("\"fo\"\"o\"", "fo\"o");
		this.verifyUndelimitOnStringBuilder("\"foo\"\"\"", "foo\"");
		this.verifyUndelimitOnStringBuilder("\"\"\"foo\"", "\"foo");
		this.verifyUndelimitOnStringBuilder("[foo]", "foo");
		this.verifyUndelimitOnStringBuilder("\"\"\"", "\"");
		this.verifyUndelimitOnStringBuilder("\"foo\"bar\"", "foo\"");
		this.verifyUndelimitOnStringBuilder("\"foo\"\"", "foo\"");
	}

	private void verifyUndelimitOnStringBuilder(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.undelimitOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringTools.undelimitOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testUndelimitOnWriterCount() {
		this.verifyUndelimitOnWriterCount("\"foo\"", 2, "o");
		this.verifyUndelimitOnWriterCount("\"\"\"\"", 2, "");
		this.verifyUndelimitOnWriterCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitOnWriterCount(String s, int count, String expected) {
		Writer writer = new StringWriter();
		StringTools.undelimitOn(s, count, writer);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		StringTools.undelimitOn(s.toCharArray(), count, writer);
		assertEquals(expected, writer.toString());
	}

	public void testUndelimitOnStringBufferCount() {
		this.verifyUndelimitOnStringBufferCount("\"foo\"", 2, "o");
		this.verifyUndelimitOnStringBufferCount("\"\"\"\"", 2, "");
		this.verifyUndelimitOnStringBufferCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitOnStringBufferCount(String s, int count, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.undelimitOn(s, count, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringTools.undelimitOn(s.toCharArray(), count, sb);
		assertEquals(expected, sb.toString());
	}

	public void testUndelimitOnStringBuilderCount() {
		this.verifyUndelimitOnStringBuilderCount("\"foo\"", 2, "o");
		this.verifyUndelimitOnStringBuilderCount("\"\"\"\"", 2, "");
		this.verifyUndelimitOnStringBuilderCount("XXfooXX", 2, "foo");
	}

	private void verifyUndelimitOnStringBuilderCount(String s, int count, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.undelimitOn(s, count, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringTools.undelimitOn(s.toCharArray(), count, sb);
		assertEquals(expected, sb.toString());
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
		assertEquals(expected, StringTools.convertToJavaStringLiteral(s));
		assertEquals(expected, StringTools.convertToJavaStringLiteral(s.toCharArray()));
	}

	public void testConvertToJavaStringLiteralOnStringBuffer() {
		this.verifyConvertToJavaStringLiteralOnStringBuffer("", "\"\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuffer("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteralOnStringBuffer(String s, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.convertToJavaStringLiteralOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringTools.convertToJavaStringLiteralOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteralOnStringBuilder() {
		this.verifyConvertToJavaStringLiteralOnStringBuilder("", "\"\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteralOnStringBuilder("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteralOnStringBuilder(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.convertToJavaStringLiteralOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringTools.convertToJavaStringLiteralOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteralOnWriter() {
		this.verifyConvertToJavaStringLiteralOnWriter("", "\"\"");
		this.verifyConvertToJavaStringLiteralOnWriter("\"\"", "\"\\\"\\\"\"");
		this.verifyConvertToJavaStringLiteralOnWriter("'foo'", "\"'foo'\"");
		this.verifyConvertToJavaStringLiteralOnWriter("foo\bbar", "\"foo\\bbar\"");
		this.verifyConvertToJavaStringLiteralOnWriter("foo\n\tbar", "\"foo\\n\\tbar\"");
		this.verifyConvertToJavaStringLiteralOnWriter("foo\"bar", "\"foo\\\"bar\"");
		this.verifyConvertToJavaStringLiteralOnWriter("foo\\bar", "\"foo\\\\bar\"");
	}

	private void verifyConvertToJavaStringLiteralOnWriter(String s, String expected) {
		Writer writer = new StringWriter();
		StringTools.convertToJavaStringLiteralOn(s, writer);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		StringTools.convertToJavaStringLiteralOn(s.toCharArray(), writer);
		assertEquals(expected, writer.toString());
	}

	// ********** converting to Java string literal content **********

	public void testConvertToJavaStringLiteralContent() {
		this.verifyConvertToJavaStringLiteralContent("", "\"\"");
		this.verifyConvertToJavaStringLiteralContent("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContent("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContent("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContent("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContent("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContent("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContent(String s, String expected) {
		assertEquals(expected, StringTools.convertToJavaStringLiteralContent(s));
		assertEquals(expected, StringTools.convertToJavaStringLiteralContent(s.toCharArray()));
	}

	public void testConvertToJavaStringLiteralContentOnStringBuffer() {
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("", "\"\"");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuffer("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContentOnStringBuffer(String s, String expected) {
		StringBuffer sb = new StringBuffer();
		StringTools.convertToJavaStringLiteralContentOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuffer();
		StringTools.convertToJavaStringLiteralContentOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteralContentOnStringBuilder() {
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("", "\"\"");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContentOnStringBuilder("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContentOnStringBuilder(String s, String expected) {
		StringBuilder sb = new StringBuilder();
		StringTools.convertToJavaStringLiteralContentOn(s, sb);
		assertEquals(expected, sb.toString());

		sb = new StringBuilder();
		StringTools.convertToJavaStringLiteralContentOn(s.toCharArray(), sb);
		assertEquals(expected, sb.toString());
	}

	public void testConvertToJavaStringLiteralContentOnWriter() {
		this.verifyConvertToJavaStringLiteralContentOnWriter("", "\"\"");
		this.verifyConvertToJavaStringLiteralContentOnWriter("\"\"", "\\\"\\\"");
		this.verifyConvertToJavaStringLiteralContentOnWriter("'foo'", "'foo'");
		this.verifyConvertToJavaStringLiteralContentOnWriter("foo\bbar", "foo\\bbar");
		this.verifyConvertToJavaStringLiteralContentOnWriter("foo\n\tbar", "foo\\n\\tbar");
		this.verifyConvertToJavaStringLiteralContentOnWriter("foo\"bar", "foo\\\"bar");
		this.verifyConvertToJavaStringLiteralContentOnWriter("foo\\bar", "foo\\\\bar");
	}

	private void verifyConvertToJavaStringLiteralContentOnWriter(String s, String expected) {
		Writer writer = new StringWriter();
		StringTools.convertToJavaStringLiteralContentOn(s, writer);
		assertEquals(expected, writer.toString());

		writer = new StringWriter();
		StringTools.convertToJavaStringLiteralContentOn(s.toCharArray(), writer);
		assertEquals(expected, writer.toString());
	}
}
