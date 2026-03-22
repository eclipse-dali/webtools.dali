/*******************************************************************************
 * Copyright (c) 2009, 2025 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Binary model for a JPA {@code @Entity} annotation.
 * <p>
 * Supports both <code>javax.persistence.Entity</code> (JPA 2.x) and
 * <code>jakarta.persistence.Entity</code> (JPA 3.x). The default constructor
 * uses <code>javax.persistence.Entity</code>. For JPA 3.x supply the
 * annotation FQN to
 * {@link #BinaryEntityAnnotation(JavaResourceAnnotatedElement, IAnnotation, String)}.
 */
public final class BinaryEntityAnnotation
	extends BinaryAnnotation
	implements EntityAnnotation
{
	private String name;

	/**
	 * The annotation name as reported by {@link #getAnnotationName()}.
	 * Defaults to {@link EntityAnnotation#ANNOTATION_NAME} (javax).
	 */
	private final String annotationName;


	// ---- constructors ----

	/**
	 * Default constructor — reports <code>javax.persistence.Entity</code>.
	 */
	public BinaryEntityAnnotation(JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation) {
		this(parent, jdtAnnotation, ANNOTATION_NAME);
	}

	/**
	 * Package-aware constructor. Pass the fully qualified annotation name
	 * (e.g. {@code "jakarta.persistence.Entity"}).
	 */
	public BinaryEntityAnnotation(JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation, String annotationFqn) {
		super(parent, jdtAnnotation);
		this.annotationName = annotationFqn;
		this.name = this.buildName();
	}


	// ---- Annotation ----

	public String getAnnotationName() {
		return this.annotationName;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ---- EntityAnnotation ----

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(JPA.ENTITY__NAME);
	}

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}
}
