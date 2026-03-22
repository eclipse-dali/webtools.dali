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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JPA 3.0 / jakarta.persistence unit-test suite.
 * <p>
 * These tests cover the javax→jakarta package split introduced in JPA 3.0:
 * <ul>
 *   <li>{@link JpaConstantsTests} — verifies {@code JPA} and {@code JPA3_0}
 *       interface constants</li>
 *   <li>{@link JpaPackageHelperTests} — verifies {@code JpaPackageHelper}
 *       package resolution logic</li>
 *   <li>{@link JakartaAnnotationDefinitionAdapterTests} — verifies name
 *       translation in {@code JakartaAnnotationDefinitionAdapter}</li>
 *   <li>{@link GenericJpaAnnotationDefinitionProvider3_0Tests} — verifies that
 *       the JPA 3.0 annotation definition provider exposes only
 *       jakarta-namespaced definitions</li>
 *   <li>{@link AnnotationDefinitionJakartaTests} — directly tests the
 *       {@code instance(String jpaPackage)} factory on every migrated
 *       annotation definition class (28 non-nestable + 9 nestable)</li>
 * </ul>
 */
public class JptJpaCore3_0Tests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCore3_0Tests.class.getPackage().getName());
		suite.addTestSuite(JpaConstantsTests.class);
		suite.addTestSuite(JpaPackageHelperTests.class);
		suite.addTestSuite(JakartaAnnotationDefinitionAdapterTests.class);
		suite.addTestSuite(GenericJpaAnnotationDefinitionProvider3_0Tests.class);
		suite.addTestSuite(AnnotationDefinitionJakartaTests.class);
		return suite;
	}

	private JptJpaCore3_0Tests() {
		super();
		throw new UnsupportedOperationException();
	}
}
