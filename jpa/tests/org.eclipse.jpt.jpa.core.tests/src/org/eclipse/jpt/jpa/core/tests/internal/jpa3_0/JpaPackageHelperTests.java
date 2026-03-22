/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0;

import junit.framework.TestCase;
import org.eclipse.jpt.jpa.core.internal.utility.JpaPackageHelper;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.jpa2_2.JpaProject2_2;
import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.jpt.jpa.core.jpa3_1.JpaProject3_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Tests for {@link JpaPackageHelper}.
 */
@SuppressWarnings("nls")
public class JpaPackageHelperTests extends TestCase {

	public JpaPackageHelperTests(String name) {
		super(name);
	}


	// ---- getPackage(IProjectFacetVersion) ----

	public void testGetPackage_facet_jpa1_0() {
		assertEquals(JPA.JAVAX_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject2_0.FACET.getVersion("1.0")));
	}

	public void testGetPackage_facet_jpa2_0() {
		assertEquals(JPA.JAVAX_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject2_0.FACET_VERSION));
	}

	public void testGetPackage_facet_jpa2_1() {
		assertEquals(JPA.JAVAX_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject2_1.FACET_VERSION));
	}

	public void testGetPackage_facet_jpa2_2() {
		assertEquals(JPA.JAVAX_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject2_2.FACET_VERSION));
	}

	public void testGetPackage_facet_jpa3_0() {
		assertEquals(JPA.JAKARTA_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject3_0.FACET_VERSION));
	}

	public void testGetPackage_facet_jpa3_1() {
		assertEquals(JPA.JAKARTA_PACKAGE,
				JpaPackageHelper.getPackage(JpaProject3_1.FACET_VERSION));
	}

	public void testGetPackage_facet_null() {
		assertEquals(JPA.JAVAX_PACKAGE,
				JpaPackageHelper.getPackage((org.eclipse.wst.common.project.facet.core.IProjectFacetVersion) null));
	}


	// ---- getPackage(String) ----

	public void testGetPackage_version_1_0() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("1.0"));
	}

	public void testGetPackage_version_2_0() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("2.0"));
	}

	public void testGetPackage_version_2_1() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("2.1"));
	}

	public void testGetPackage_version_2_2() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("2.2"));
	}

	public void testGetPackage_version_3_0() {
		assertEquals(JPA.JAKARTA_PACKAGE, JpaPackageHelper.getPackage("3.0"));
	}

	public void testGetPackage_version_3_1() {
		assertEquals(JPA.JAKARTA_PACKAGE, JpaPackageHelper.getPackage("3.1"));
	}

	public void testGetPackage_version_major_only_3() {
		assertEquals(JPA.JAKARTA_PACKAGE, JpaPackageHelper.getPackage("3"));
	}

	public void testGetPackage_version_major_only_2() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("2"));
	}

	public void testGetPackage_version_null() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage((String) null));
	}

	public void testGetPackage_version_empty() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage(""));
	}

	public void testGetPackage_version_malformed() {
		assertEquals(JPA.JAVAX_PACKAGE, JpaPackageHelper.getPackage("invalid"));
	}


	// ---- isJakarta(IProjectFacetVersion) ----

	public void testIsJakarta_facet_jpa2_2_false() {
		assertFalse(JpaPackageHelper.isJakarta(JpaProject2_2.FACET_VERSION));
	}

	public void testIsJakarta_facet_jpa3_0_true() {
		assertTrue(JpaPackageHelper.isJakarta(JpaProject3_0.FACET_VERSION));
	}

	public void testIsJakarta_facet_jpa3_1_true() {
		assertTrue(JpaPackageHelper.isJakarta(JpaProject3_1.FACET_VERSION));
	}

	public void testIsJakarta_facet_null_false() {
		assertFalse(JpaPackageHelper.isJakarta(
				(org.eclipse.wst.common.project.facet.core.IProjectFacetVersion) null));
	}


	// ---- isJakarta(String) ----

	public void testIsJakarta_string_2_2_false() {
		assertFalse(JpaPackageHelper.isJakarta("2.2"));
	}

	public void testIsJakarta_string_3_0_true() {
		assertTrue(JpaPackageHelper.isJakarta("3.0"));
	}

	public void testIsJakarta_string_3_1_true() {
		assertTrue(JpaPackageHelper.isJakarta("3.1"));
	}

	public void testIsJakarta_string_null_false() {
		assertFalse(JpaPackageHelper.isJakarta((String) null));
	}


	// ---- translateAnnotationName ----

	public void testTranslateAnnotationName_jpa2x_unchanged() {
		String name = "javax.persistence.Entity";
		// JPA 2.x facet — name must stay as-is
		assertEquals(name,
				JpaPackageHelper.translateAnnotationName(name, JpaProject2_2.FACET_VERSION));
	}

	public void testTranslateAnnotationName_jpa3x_translated() {
		assertEquals("jakarta.persistence.Entity",
				JpaPackageHelper.translateAnnotationName(
						"javax.persistence.Entity", JpaProject3_0.FACET_VERSION));
	}

	public void testTranslateAnnotationName_nonJpa_unchanged() {
		String name = "java.lang.Override";
		assertEquals(name,
				JpaPackageHelper.translateAnnotationName(name, JpaProject3_0.FACET_VERSION));
	}

	public void testTranslateAnnotationName_null_name() {
		assertNull(JpaPackageHelper.translateAnnotationName(null, JpaProject3_0.FACET_VERSION));
	}

	public void testTranslateAnnotationName_alreadyJakarta_unchanged() {
		// already jakarta — must not be double-translated
		String name = "jakarta.persistence.Entity";
		assertEquals(name,
				JpaPackageHelper.translateAnnotationName(name, JpaProject3_0.FACET_VERSION));
	}
}
