/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.comparator;

import java.text.Collator;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorAdapter;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;

@SuppressWarnings("nls")
public class ComparatorToolsTests
	extends TestCase
{
	public ComparatorToolsTests(String name) {
		super(name);
	}

	public void testMinObjectObject() {
		String foo = "foo";
		String bar = "bar";
		assertEquals(bar, ComparatorTools.min(foo, bar));
		assertEquals(bar, ComparatorTools.min(bar, foo));
	}

	public void testMinObjectObjectComparator() {
		String foo = "foo";
		String bar = "bar";
		assertEquals(foo, ComparatorTools.min(foo, bar, ComparatorTools.<String>reverseComparator()));
		assertEquals(foo, ComparatorTools.min(bar, foo, ComparatorTools.<String>reverseComparator()));
	}

	public void testMaxObjectObject() {
		String foo = "foo";
		String bar = "bar";
		assertEquals(foo, ComparatorTools.max(foo, bar));
		assertEquals(foo, ComparatorTools.max(bar, foo));
	}

	public void testMaxObjectObjectComparator() {
		String foo = "foo";
		String bar = "bar";
		assertEquals(bar, ComparatorTools.max(foo, bar, ComparatorTools.<String>reverseComparator()));
		assertEquals(bar, ComparatorTools.max(bar, foo, ComparatorTools.<String>reverseComparator()));
	}

	public void testComparableComparator() {
		assertTrue(ComparatorTools.<String>comparableComparator().compare("foo", "foo") == 0);
		assertTrue(ComparatorTools.<String>comparableComparator().compare("foo", "bar") > 0);
		assertTrue(ComparatorTools.<String>comparableComparator().compare("bar", "foo") < 0);
	}

	public void testNaturalComparator() {
		assertTrue(ComparatorTools.<String>naturalComparator().compare("foo", "foo") == 0);
		assertTrue(ComparatorTools.<String>naturalComparator().compare("foo", "bar") > 0);
		assertTrue(ComparatorTools.<String>naturalComparator().compare("bar", "foo") < 0);
	}

	public void testComparatorChain() throws Exception {
		@SuppressWarnings("unchecked")
		Comparator<Person> comparator = ComparatorTools.chain(Person.LAST_NAME_COMPARATOR, Person.FIRST_NAME_COMPARATOR, Person.BIRTH_DATE_COMPARATOR);
		Person john = new Person("John", "Smith", DateFormat.getDateInstance(DateFormat.SHORT).parse("10/11/1955"));
		Person jane = new Person("Jane", "Smith", DateFormat.getDateInstance(DateFormat.SHORT).parse("10/11/1955"));
		assertTrue(comparator.compare(john, john) == 0);
		assertTrue(comparator.compare(jane, john) < 0);
		assertTrue(comparator.compare(john, jane) > 0);
		Person oldJohn = new Person("John", "Smith", DateFormat.getDateInstance(DateFormat.SHORT).parse("10/11/1933"));
		assertTrue(comparator.compare(oldJohn, oldJohn) == 0);
		assertTrue(comparator.compare(oldJohn, john) < 0);
		assertTrue(comparator.compare(john, oldJohn) > 0);
	}

	public void testNullsFirstComparator() {
		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare("foo", "foo") == 0);
		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare("foo", "bar") > 0);
		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare("bar", "foo") < 0);

		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare(null, null) == 0);
		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare("foo", null) > 0);
		assertTrue(ComparatorTools.nullsFirst(ComparatorTools.stringCollator()).compare(null, "foo") < 0);
	}

	public void testNullsLastComparator() {
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", "foo") == 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", "bar") > 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("bar", "foo") < 0);

		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare(null, null) == 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", null) < 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare(null, "foo") > 0);
	}

	public void testTransformationComparator() {
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", "foo") == 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", "bar") > 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("bar", "foo") < 0);

		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare(null, null) == 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare("foo", null) < 0);
		assertTrue(ComparatorTools.nullsLast(ComparatorTools.stringCollator()).compare(null, "foo") > 0);
	}

	public static class Person {
		public final String firstName;
		public final String lastName;
		public final Date birthDate;
		public Person(String firstName, String lastName, Date birthDate) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.birthDate = birthDate;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.firstName + ' ' + this.lastName);
		}

		public static final Comparator<Person> FIRST_NAME_COMPARATOR = new FirstNameComparator();
		public static class FirstNameComparator
			extends ComparatorAdapter<Person>
		{
			@Override
			public int compare(Person p1, Person p2) {
				return Collator.getInstance().compare(p1.firstName, p2.firstName);
			}
		}

		public static final Comparator<Person> LAST_NAME_COMPARATOR = new LastNameComparator();
		public static class LastNameComparator
			extends ComparatorAdapter<Person>
		{
			@Override
			public int compare(Person p1, Person p2) {
				return Collator.getInstance().compare(p1.lastName, p2.lastName);
			}
		}

		public static final Comparator<Person> BIRTH_DATE_COMPARATOR = new BirthDateComparator();
		public static class BirthDateComparator
			extends ComparatorAdapter<Person>
		{
			@Override
			public int compare(Person p1, Person p2) {
				return p1.birthDate.compareTo(p2.birthDate);
			}
		}
	}
}
