/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.DiscriminatorColumn
 */
public final class SourceDiscriminatorColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> DISCRIMINATOR_TYPE_ADAPTER = buildDiscriminatorTypeAdapter();
	private final AnnotationElementAdapter<String> discriminatorTypeAdapter;
	private DiscriminatorType discriminatorType;

	private final DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> lengthAdapter;
	private Integer length;


	public SourceDiscriminatorColumnAnnotation(JavaResourcePersistentType parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.discriminatorTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, DISCRIMINATOR_TYPE_ADAPTER);
		this.lengthDeclarationAdapter = this.buildIntegerElementAdapter(JPA.DISCRIMINATOR_COLUMN__LENGTH);
		this.lengthAdapter = this.buildShortCircuitIntegerElementAdapter(this.lengthDeclarationAdapter);
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
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setLength(this.buildLength(astRoot));
		this.setDiscriminatorType(this.buildDiscriminatorType(astRoot));
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	String getColumnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	// ********** DiscriminatorColumn implementation **********

	// ***** discriminator type
	public DiscriminatorType getDiscriminatorType() {
		return this.discriminatorType;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		if (this.attributeValueHasNotChanged(this.discriminatorType, discriminatorType)) {
			return;
		}
		DiscriminatorType old = this.discriminatorType;
		this.discriminatorType = discriminatorType;
		this.discriminatorTypeAdapter.setValue(DiscriminatorType.toJavaAnnotationValue(discriminatorType));
		this.firePropertyChanged(DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}

	private DiscriminatorType buildDiscriminatorType(CompilationUnit astRoot) {
		return DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astRoot));
	}

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (this.attributeValueHasNotChanged(this.length, length)) {
			return;
		}
		Integer old = this.length;
		this.length = length;
		this.lengthAdapter.setValue(length);
		this.firePropertyChanged(LENGTH_PROPERTY, old, length);
	}

	private Integer buildLength(CompilationUnit astRoot) {
		return this.lengthAdapter.getValue(astRoot);
	}


	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		DiscriminatorColumnAnnotation oldColumn = (DiscriminatorColumnAnnotation) oldAnnotation;
		this.setLength(oldColumn.getLength());
		this.setDiscriminatorType(oldColumn.getDiscriminatorType());
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildDiscriminatorTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}

}
