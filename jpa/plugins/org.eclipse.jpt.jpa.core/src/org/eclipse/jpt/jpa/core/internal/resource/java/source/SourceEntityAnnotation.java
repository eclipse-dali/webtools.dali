/*******************************************************************************
 * Copyright (c) 2007, 2026 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.JakartaAwareDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Source model for a JPA {@code @Entity} annotation.
 * <p>
 * Supports both <code>javax.persistence.Entity</code> (JPA 2.x) and
 * <code>jakarta.persistence.Entity</code> (JPA 3.x). The default no-extra-arg
 * constructor uses <code>javax.persistence.Entity</code>. For JPA 3.x pass
 * the annotation FQN to {@link #SourceEntityAnnotation(JavaResourceAnnotatedElement, AnnotatedElement, String)}.
 */
public final class SourceEntityAnnotation
	extends SourceAnnotation
	implements EntityAnnotation
{
	/**
	 * Adapter for the default <code>javax.persistence.Entity</code> annotation
	 * (JPA 2.x). Used as a convenience constant by legacy callers.
	 */
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER =
			JakartaAwareDeclarationAnnotationAdapter.forJavax(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER =
			buildNameAdapter(DECLARATION_ANNOTATION_ADAPTER);

	// ---- instance state ----

	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;


	// ---- constructors ----

	/**
	 * Default constructor — binds to <code>javax.persistence.Entity</code>.
	 */
	public SourceEntityAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new AnnotatedElementAnnotationElementAdapter<>(element, NAME_ADAPTER);
	}

	/**
	 * Package-aware constructor. Pass the fully qualified annotation name
	 * (e.g. {@code "jakarta.persistence.Entity"}) to bind to the correct
	 * annotation in the AST.
	 */
	public SourceEntityAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement element, String annotationFqn) {
		this(parent, element, JakartaAwareDeclarationAnnotationAdapter.forJakarta(annotationFqn));
	}

	/**
	 * Low-level constructor accepting an explicit {@link DeclarationAnnotationAdapter}.
	 */
	public SourceEntityAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		DeclarationAnnotationElementAdapter<String> elemAdapter =
				buildNameAdapter(daa);
		this.nameAdapter = new AnnotatedElementAnnotationElementAdapter<>(element, elemAdapter);
	}


	// ---- Annotation ----

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() && (this.name == null);
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
		if (ObjectTools.notEquals(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		// Use a locally derived element adapter so we can resolve against the
		// right daa (which may differ from the static NAME_ADAPTER).
		return this.getElementTextRange(
				buildNameAdapter(this.daa), astAnnotation);
	}


	// ---- static helpers ----

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter(
			DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.ENTITY__NAME);
	}
}
