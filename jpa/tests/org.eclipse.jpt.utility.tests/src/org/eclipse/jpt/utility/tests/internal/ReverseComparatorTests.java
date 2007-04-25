/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.ReverseComparator;

public class ReverseComparatorTests extends TestCase {
	private Comparator<String> naturalReverseComparator;
	private Comparator<String> customComparator;
	private Comparator<String> customReverseComparator;

	public ReverseComparatorTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.naturalReverseComparator = new ReverseComparator<String>();
		this.customComparator = this.buildCustomComparator();
		this.customReverseComparator = new ReverseComparator<String>(this.customComparator);
	}

	private Comparator<String> buildCustomComparator() {
		return new Comparator<String>() {
			public int compare(String s1, String s2) {
				String lower1 = s1.toLowerCase();
				String lower2 = s2.toLowerCase();
				int result = lower1.compareTo(lower2);
				if (result == 0) {
					return s1.compareTo(s2); // use case to differentiate "equal" strings
				}
				return result;
			}
		};
	}

	private List<String> buildUnsortedList() {
		List<String> result = new ArrayList<String>();
		result.add("T");
		result.add("Z");
		result.add("Y");
		result.add("M");
		result.add("m");
		result.add("a");
		result.add("B");
		result.add("b");
		result.add("A");
		return result;
	}

	private List<String> buildNaturallySortedList() {
		List<String> result = new ArrayList<String>(this.buildUnsortedList());
		Collections.sort(result);
		return result;
	}

	private List<String> buildCustomSortedList() {
		List<String> result = new ArrayList<String>(this.buildUnsortedList());
		Collections.sort(result, this.customComparator);
		return result;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testNatural() {
		List<String> list = this.buildUnsortedList();
		Collections.sort(list, this.naturalReverseComparator);
		this.verifyList(this.buildNaturallySortedList(), list);
	}

	public void testCustom() {
		List<String> list = this.buildUnsortedList();
		Collections.sort(list, this.customReverseComparator);
		this.verifyList(this.buildCustomSortedList(), list);
	}

	private void verifyList(List<String> normal, List<String> reverse) {
		int size = normal.size();
		int max = size - 1;
		for (int i = 0; i < size; i++) {
			assertEquals(normal.get(i), reverse.get(max - i));
		}
	}
}
