/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility;

import java.util.Locale;
import junit.framework.TestCase;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;

@SuppressWarnings("nls")
public class MessageLoaderTests
	extends TestCase
{
	public MessageLoaderTests(String name) {
		super(name);
	}

	public void testBuildLocaleSuffixes1() {
		String[] suffixes = this.buildLocaleSuffixes("en", "US", "WIN");
		assertEquals(4, suffixes.length);
		assertEquals(suffixes[0], "_en_US_WIN.properties");
		assertEquals(suffixes[1], "_en_US.properties");
		assertEquals(suffixes[2], "_en.properties");
		assertEquals(suffixes[3], ".properties");
	}

	public void testBuildLocaleSuffixes2() {
		String[] suffixes = this.buildLocaleSuffixes("en", "US", "");
		assertEquals(3, suffixes.length);
		assertEquals(suffixes[0], "_en_US.properties");
		assertEquals(suffixes[1], "_en.properties");
		assertEquals(suffixes[2], ".properties");
	}

	public void testBuildLocaleSuffixes3() {
		String[] suffixes = this.buildLocaleSuffixes("en", "", "WIN");
		assertEquals(3, suffixes.length);
		assertEquals(suffixes[0], "_en__WIN.properties");  // !
		assertEquals(suffixes[1], "_en.properties");
		assertEquals(suffixes[2], ".properties");
	}

	public void testBuildLocaleSuffixes4() {
		String[] suffixes = this.buildLocaleSuffixes("en", "", "");
		assertEquals(2, suffixes.length);
		assertEquals(suffixes[0], "_en.properties");
		assertEquals(suffixes[1], ".properties");
	}

	public void testBuildLocaleSuffixes5() {
		String[] suffixes = this.buildLocaleSuffixes("", "US", "WIN");
		assertEquals(3, suffixes.length);
		assertEquals(suffixes[0], "__US_WIN.properties");
		assertEquals(suffixes[1], "__US.properties");
		assertEquals(suffixes[2], ".properties");
	}

	public void testBuildLocaleSuffixes6() {
		String[] suffixes = this.buildLocaleSuffixes("", "US", "");
		assertEquals(2, suffixes.length);
		assertEquals(suffixes[0], "__US.properties");
		assertEquals(suffixes[1], ".properties");
	}

	public void testBuildLocaleSuffixes7() {
		String[] suffixes = this.buildLocaleSuffixes("", "", "WIN");
		assertEquals(2, suffixes.length);
		assertEquals(suffixes[0], "___WIN.properties");
		assertEquals(suffixes[1], ".properties");
	}

	public void testBuildLocaleSuffixes8() {
		String[] suffixes = this.buildLocaleSuffixes("", "", "");
		assertEquals(1, suffixes.length);
		assertEquals(suffixes[0], ".properties");
	}

	private String[] buildLocaleSuffixes(String language, String country, String variant) {
		return buildLocaleSuffixes(new Locale(language, country, variant));
	}

	private String[] buildLocaleSuffixes(Locale locale) {
		return (String[]) ClassTools.execute(ValidationMessageLoader.class, "buildLocaleSuffixes", Locale.class, locale);
	}

	public void testPropertiesFileNames1() {
		String[] names = this.buildPropertiesFileNames("foo");
		// System.out.println(java.util.Arrays.toString(names));

		Locale locale = Locale.getDefault();
		// System.out.println(locale.toString()); // typically "en_US"
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		int expectedSize = 1;
		if (language.length() != 0) {
			expectedSize++;
		}
		if (country.length() != 0) {
			expectedSize++;
		}
		if (variant.length() != 0) {
			expectedSize++;
		}
		assertEquals(expectedSize, names.length);

		String[] expected = ArrayTools.fill(new String[expectedSize], "foo");
		if (expectedSize > 1) {
			for (int i = 0; i < expectedSize - 1; i++) {
				expected[i] = expected[i] + '_' + language;
			}
		}
		if (expectedSize > 2) {
			for (int i = 0; i < expectedSize - 2; i++) {
				expected[i] = expected[i] + '_' + country;
			}
		}
		if (expectedSize > 3) {
			for (int i = 0; i < expectedSize - 3; i++) {
				expected[i] = expected[i] + '_' + variant;
			}
		}
	}

	private String[] buildPropertiesFileNames(String bundleName) {
		return (String[]) ClassTools.execute(ValidationMessageLoader.class, "buildPropertiesFileNames", String.class, bundleName);
	}
}
