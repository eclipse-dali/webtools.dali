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
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.JakartaAnnotationDefinitionAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Tests for {@link JakartaAnnotationDefinitionAdapter}.
 */
@SuppressWarnings("nls")
public class JakartaAnnotationDefinitionAdapterTests extends TestCase {

	public JakartaAnnotationDefinitionAdapterTests(String name) {
		super(name);
	}


	// ---- name translation ----

	public void testJavaxPersistenceNameIsTranslatedToJakarta() {
		AnnotationDefinition delegate = stubDefinition("javax.persistence.Entity");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertEquals("jakarta.persistence.Entity", adapter.getAnnotationName());
	}

	public void testJavaxPersistencePrefixReplacedOnly() {
		// Only the package prefix must be replaced, not arbitrary occurrences
		AnnotationDefinition delegate = stubDefinition("javax.persistence.NamedNativeQuery");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertEquals("jakarta.persistence.NamedNativeQuery", adapter.getAnnotationName());
	}

	public void testNonJpaPersistenceNameIsUnchanged() {
		AnnotationDefinition delegate = stubDefinition("java.lang.Override");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertEquals("java.lang.Override", adapter.getAnnotationName());
	}

	public void testAlreadyJakartaNameIsUnchanged() {
		// should not double-translate an already-jakarta name
		AnnotationDefinition delegate = stubDefinition("jakarta.persistence.Entity");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertEquals("jakarta.persistence.Entity", adapter.getAnnotationName());
	}

	public void testNullAnnotationNameIsPreservedAsNull() {
		AnnotationDefinition delegate = stubDefinition(null);
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertNull(adapter.getAnnotationName());
	}

	public void testJavaxNotSuffixNotTranslated() {
		// "javax.persistence" must appear as a prefix
		AnnotationDefinition delegate = stubDefinition("com.example.javax.persistence.Foo");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertEquals("com.example.javax.persistence.Foo", adapter.getAnnotationName());
	}


	// ---- translated name uses jakarta prefix ----

	public void testTranslatedNameStartsWithJakartaPackage() {
		AnnotationDefinition delegate = stubDefinition(JPA.JAVAX_PACKAGE + ".Column");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertTrue(adapter.getAnnotationName().startsWith(JPA.JAKARTA_PACKAGE));
	}

	public void testTranslatedNameDoesNotStartWithJavaxPackage() {
		AnnotationDefinition delegate = stubDefinition(JPA.JAVAX_PACKAGE + ".Column");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertFalse(adapter.getAnnotationName().startsWith(JPA.JAVAX_PACKAGE));
	}


	// ---- toString ----

	public void testToStringContainsJakartaName() {
		AnnotationDefinition delegate = stubDefinition("javax.persistence.Basic");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		assertTrue(adapter.toString().contains("jakarta.persistence.Basic"));
	}


	// ---- delegation of build methods ----

	public void testBuildAnnotationDelegatesToWrappedDefinition() {
		TrackingAnnotationDefinition delegate = new TrackingAnnotationDefinition("javax.persistence.Entity");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		adapter.buildAnnotation(null, (AnnotatedElement) null);
		assertTrue("buildAnnotation(parent, element) was not delegated",
				delegate.buildAnnotationSourceCalled);
	}

	public void testBuildNullAnnotationDelegatesToWrappedDefinition() {
		TrackingAnnotationDefinition delegate = new TrackingAnnotationDefinition("javax.persistence.Entity");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		adapter.buildNullAnnotation(null);
		assertTrue("buildNullAnnotation was not delegated",
				delegate.buildNullAnnotationCalled);
	}

	public void testBuildAnnotationFromJdtDelegatesToWrappedDefinition() {
		TrackingAnnotationDefinition delegate = new TrackingAnnotationDefinition("javax.persistence.Entity");
		JakartaAnnotationDefinitionAdapter adapter =
				new JakartaAnnotationDefinitionAdapter(delegate);
		adapter.buildAnnotation(null, (IAnnotation) null);
		assertTrue("buildAnnotation(parent, jdtAnnotation) was not delegated",
				delegate.buildAnnotationBinaryCalled);
	}


	// ---- helpers ----

	/** Creates a stub {@link AnnotationDefinition} that returns the given name. */
	private static AnnotationDefinition stubDefinition(final String name) {
		return new AnnotationDefinition() {
			public String getAnnotationName() { return name; }
			public Annotation buildAnnotation(JavaResourceAnnotatedElement p, AnnotatedElement e) { return null; }
			public Annotation buildNullAnnotation(JavaResourceAnnotatedElement p) { return null; }
			public Annotation buildAnnotation(JavaResourceAnnotatedElement p, IAnnotation a) { return null; }
		};
	}

	/** An {@link AnnotationDefinition} stub that tracks which build methods were called. */
	private static final class TrackingAnnotationDefinition implements AnnotationDefinition {
		private final String name;
		boolean buildAnnotationSourceCalled;
		boolean buildNullAnnotationCalled;
		boolean buildAnnotationBinaryCalled;

		TrackingAnnotationDefinition(String name) { this.name = name; }

		public String getAnnotationName() { return this.name; }

		public Annotation buildAnnotation(JavaResourceAnnotatedElement p, AnnotatedElement e) {
			this.buildAnnotationSourceCalled = true;
			return null;
		}

		public Annotation buildNullAnnotation(JavaResourceAnnotatedElement p) {
			this.buildNullAnnotationCalled = true;
			return null;
		}

		public Annotation buildAnnotation(JavaResourceAnnotatedElement p, IAnnotation a) {
			this.buildAnnotationBinaryCalled = true;
			return null;
		}
	}
}
