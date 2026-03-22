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
package org.eclipse.jpt.jpa.core.internal.utility;

import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Utility for resolving the correct JPA annotations package prefix
 * (<code>javax.persistence</code> vs <code>jakarta.persistence</code>)
 * from project context.
 * <p>
 * JPA 2.2 and earlier use <code>javax.persistence</code>.<br>
 * JPA 3.0 and later use <code>jakarta.persistence</code>.
 * <p>
 * Use this class wherever the JPA version is known at runtime (validators,
 * platform factories, annotation definition providers). For compile-time
 * constants see {@link JPA} (javax) and
 * {@link org.eclipse.jpt.jpa.core.resource.java.JPA3_0} (jakarta).
 */
public final class JpaPackageHelper {

	/**
	 * Returns the JPA annotations package for the given JPA project facet
	 * version. Returns {@link JPA#JAKARTA_PACKAGE} for JPA 3.0+, otherwise
	 * {@link JPA#JAVAX_PACKAGE}.
	 */
	public static String getPackage(IProjectFacetVersion jpaFacetVersion) {
		if (jpaFacetVersion != null
				&& jpaFacetVersion.compareTo(JpaProject3_0.FACET_VERSION) >= 0) {
			return JPA.JAKARTA_PACKAGE;
		}
		return JPA.JAVAX_PACKAGE;
	}

	/**
	 * Returns the JPA annotations package for the given JPA platform. Returns
	 * {@link JPA#JAKARTA_PACKAGE} for JPA 3.0+, otherwise
	 * {@link JPA#JAVAX_PACKAGE}.
	 */
	public static String getPackage(JpaPlatform jpaPlatform) {
		if (jpaPlatform != null) {
			return getPackage(jpaPlatform.getJpaVersion().getJpaVersion());
		}
		return JPA.JAVAX_PACKAGE;
	}

	/**
	 * Returns the JPA annotations package for the given JPA version string
	 * (e.g. {@code "3.0"}, {@code "2.2"}). Returns
	 * {@link JPA#JAKARTA_PACKAGE} for version 3.0+, otherwise
	 * {@link JPA#JAVAX_PACKAGE}.
	 */
	public static String getPackage(String jpaVersionString) {
		if (jpaVersionString != null && !jpaVersionString.isEmpty()) {
			try {
				// Compare only the major version number so "3.0", "3.1" all map correctly.
				int dotIndex = jpaVersionString.indexOf('.');
				int major = Integer.parseInt(
						dotIndex >= 0 ? jpaVersionString.substring(0, dotIndex) : jpaVersionString);
				if (major >= 3) {
					return JPA.JAKARTA_PACKAGE;
				}
			} catch (NumberFormatException e) {
				// fall through to default
			}
		}
		return JPA.JAVAX_PACKAGE;
	}

	/**
	 * Returns {@code true} when the given JPA project facet version requires
	 * the <code>jakarta.persistence</code> package (JPA 3.0+).
	 */
	public static boolean isJakarta(IProjectFacetVersion jpaFacetVersion) {
		return JPA.JAKARTA_PACKAGE.equals(getPackage(jpaFacetVersion));
	}

	/**
	 * Returns {@code true} when the given JPA version string maps to the
	 * <code>jakarta.persistence</code> package (version &gt;= 3.0).
	 */
	public static boolean isJakarta(String jpaVersionString) {
		return JPA.JAKARTA_PACKAGE.equals(getPackage(jpaVersionString));
	}

	/**
	 * Given a base annotation name such as {@code "javax.persistence.Entity"},
	 * returns the equivalent name for the supplied JPA facet version.
	 * <p>
	 * If the facet version is JPA 3.0+, {@code javax.persistence} is replaced
	 * with {@code jakarta.persistence}. Otherwise the name is returned
	 * unchanged.
	 */
	public static String translateAnnotationName(String annotationName,
			IProjectFacetVersion jpaFacetVersion) {
		if (isJakarta(jpaFacetVersion) && annotationName != null
				&& annotationName.startsWith(JPA.JAVAX_PACKAGE)) {
			return JPA.JAKARTA_PACKAGE + annotationName.substring(JPA.JAVAX_PACKAGE.length());
		}
		return annotationName;
	}

	// utility class
	private JpaPackageHelper() {
		throw new UnsupportedOperationException();
	}
}
