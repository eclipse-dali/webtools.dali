/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.DiscriminatorColumn</code>
 */
public final class SourceDiscriminatorColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> DISCRIMINATOR_TYPE_DECLARATION_ADAPTER = buildDiscriminatorTypeDeclarationAdapter();
	private AnnotationElementAdapter<String> discriminatorTypeAdapter;
	private DiscriminatorType discriminatorType;

	private DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	private AnnotationElementAdapter<Integer> lengthAdapter;
	private Integer length;


	public SourceDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.discriminatorTypeAdapter = this.buildDiscriminatorTypeAdapter();
		this.lengthDeclarationAdapter = this.buildLengthDeclarationAdapter();
		this.lengthAdapter = this.buildLengthAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.discriminatorType = this.buildDiscriminatorType(astRoot);
		this.length = this.buildLength(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncLength(this.buildLength(astRoot));
		this.syncDiscriminatorType(this.buildDiscriminatorType(astRoot));
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	// ********** DiscriminatorColumn implementation **********

	// ***** discriminator type
	public DiscriminatorType getDiscriminatorType() {
		return this.discriminatorType;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		if (this.attributeValueHasChanged(this.discriminatorType, discriminatorType)) {
			this.discriminatorType = discriminatorType;
			this.discriminatorTypeAdapter.setValue(DiscriminatorType.toJavaAnnotationValue(discriminatorType));
		}
	}

	private void syncDiscriminatorType(DiscriminatorType astDiscriminatorType) {
		DiscriminatorType old = this.discriminatorType;
		this.discriminatorType = astDiscriminatorType;
		this.firePropertyChanged(DISCRIMINATOR_TYPE_PROPERTY, old, astDiscriminatorType);
	}

	private DiscriminatorType buildDiscriminatorType(CompilationUnit astRoot) {
		return DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astRoot));
	}

	private AnnotationElementAdapter<String> buildDiscriminatorTypeAdapter() {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, DISCRIMINATOR_TYPE_DECLARATION_ADAPTER);
	}

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (this.attributeValueHasChanged(this.length, length)) {
			this.length = length;
			this.lengthAdapter.setValue(length);
		}
	}

	private void syncLength(Integer astLength) {
		Integer old = this.length;
		this.length = astLength;
		this.firePropertyChanged(LENGTH_PROPERTY, old, astLength);
	}

	private Integer buildLength(CompilationUnit astRoot) {
		return this.lengthAdapter.getValue(astRoot);
	}

	private DeclarationAnnotationElementAdapter<Integer> buildLengthDeclarationAdapter() {
		return this.buildIntegerElementAdapter(JPA.DISCRIMINATOR_COLUMN__LENGTH);
	}

	private AnnotationElementAdapter<Integer> buildLengthAdapter() {
		return this.buildIntegerElementAdapter(this.lengthDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.discriminatorType == null) &&
				(this.length == null);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildDiscriminatorTypeDeclarationAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}
}
