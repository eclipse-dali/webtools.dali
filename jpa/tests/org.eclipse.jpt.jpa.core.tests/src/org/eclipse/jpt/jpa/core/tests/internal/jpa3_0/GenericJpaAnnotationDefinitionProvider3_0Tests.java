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
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.GenericJpaAnnotationDefinitionProvider3_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Tests for {@link GenericJpaAnnotationDefinitionProvider3_0}.
 * <p>
 * Verifies that the JPA 3.0 annotation definition provider exposes all
 * annotation definitions under the <code>jakarta.persistence</code> namespace
 * and that none remain bound to the old <code>javax.persistence</code> namespace.
 */
@SuppressWarnings("nls")
public class GenericJpaAnnotationDefinitionProvider3_0Tests extends TestCase {

	private JpaAnnotationDefinitionProvider provider;

	public GenericJpaAnnotationDefinitionProvider3_0Tests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.provider = GenericJpaAnnotationDefinitionProvider3_0.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		this.provider = null;
		super.tearDown();
	}


	// ---- singleton ----

	public void testInstanceIsSingleton() {
		assertSame(GenericJpaAnnotationDefinitionProvider3_0.instance(),
				GenericJpaAnnotationDefinitionProvider3_0.instance());
	}


	// ---- annotation definitions ----

	public void testAnnotationDefinitionsNotEmpty() {
		assertFalse(this.provider.getAnnotationDefinitions().isEmpty());
	}

	public void testNoAnnotationDefinitionUsesJavaxPackage() {
		for (AnnotationDefinition def : this.provider.getAnnotationDefinitions()) {
			String name = def.getAnnotationName();
			assertFalse(
					"Annotation definition must not use javax.persistence: " + name,
					name != null && name.startsWith(JPA.JAVAX_PACKAGE));
		}
	}

	public void testAllPersistenceAnnotationDefinitionsUseJakartaPackage() {
		for (AnnotationDefinition def : this.provider.getAnnotationDefinitions()) {
			String name = def.getAnnotationName();
			if (name != null && name.contains(".persistence.")) {
				assertTrue(
						"Annotation definition must use jakarta.persistence: " + name,
						name.startsWith(JPA.JAKARTA_PACKAGE));
			}
		}
	}

	public void testEntityAnnotationDefinitionUsesJakartaPackage() {
		boolean found = false;
		for (AnnotationDefinition def : this.provider.getAnnotationDefinitions()) {
			if ("jakarta.persistence.Entity".equals(def.getAnnotationName())) {
				found = true;
				break;
			}
		}
		assertTrue("jakarta.persistence.Entity annotation definition not found", found);
	}

	public void testBasicAnnotationDefinitionUsesJakartaPackage() {
		boolean found = false;
		for (AnnotationDefinition def : this.provider.getAnnotationDefinitions()) {
			if ("jakarta.persistence.Basic".equals(def.getAnnotationName())) {
				found = true;
				break;
			}
		}
		assertTrue("jakarta.persistence.Basic annotation definition not found", found);
	}

	public void testColumnAnnotationDefinitionUsesJakartaPackage() {
		boolean found = false;
		for (AnnotationDefinition def : this.provider.getAnnotationDefinitions()) {
			if ("jakarta.persistence.Column".equals(def.getAnnotationName())) {
				found = true;
				break;
			}
		}
		assertTrue("jakarta.persistence.Column annotation definition not found", found);
	}


	// ---- nestable annotation definitions ----

	public void testNestableAnnotationDefinitionsNotEmpty() {
		assertFalse(this.provider.getNestableAnnotationDefinitions().isEmpty());
	}

	public void testNoNestableAnnotationDefinitionUsesJavaxPackage() {
		for (NestableAnnotationDefinition def : this.provider.getNestableAnnotationDefinitions()) {
			String nestableName = def.getNestableAnnotationName();
			assertFalse(
					"Nestable annotation must not use javax.persistence: " + nestableName,
					nestableName != null && nestableName.startsWith(JPA.JAVAX_PACKAGE));

			String containerName = def.getContainerAnnotationName();
			assertFalse(
					"Container annotation must not use javax.persistence: " + containerName,
					containerName != null && containerName.startsWith(JPA.JAVAX_PACKAGE));
		}
	}

	public void testAllPersistenceNestableAnnotationsUseJakartaPackage() {
		for (NestableAnnotationDefinition def : this.provider.getNestableAnnotationDefinitions()) {
			String nestableName = def.getNestableAnnotationName();
			if (nestableName != null && nestableName.contains(".persistence.")) {
				assertTrue(
						"Nestable annotation must use jakarta.persistence: " + nestableName,
						nestableName.startsWith(JPA.JAKARTA_PACKAGE));
			}
			String containerName = def.getContainerAnnotationName();
			if (containerName != null && containerName.contains(".persistence.")) {
				assertTrue(
						"Container annotation must use jakarta.persistence: " + containerName,
						containerName.startsWith(JPA.JAKARTA_PACKAGE));
			}
		}
	}


	// ---- annotation count parity ----

	public void testAnnotationDefinitionCountMatchesJpa2_1Provider() {
		JpaAnnotationDefinitionProvider jpa21Provider =
				org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaAnnotationDefinitionProvider2_1.instance();
		assertEquals(
				"JPA 3.0 provider must expose same number of annotation definitions as JPA 2.1",
				jpa21Provider.getAnnotationDefinitions().size(),
				this.provider.getAnnotationDefinitions().size());
	}

	public void testNestableAnnotationDefinitionCountMatchesJpa2_1Provider() {
		JpaAnnotationDefinitionProvider jpa21Provider =
				org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaAnnotationDefinitionProvider2_1.instance();
		assertEquals(
				"JPA 3.0 provider must expose same number of nestable annotation definitions as JPA 2.1",
				jpa21Provider.getNestableAnnotationDefinitions().size(),
				this.provider.getNestableAnnotationDefinitions().size());
	}
}
