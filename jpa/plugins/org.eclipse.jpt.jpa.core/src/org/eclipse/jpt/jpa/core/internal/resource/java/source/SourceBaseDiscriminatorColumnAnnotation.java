/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;

/**
 * <code>javax.persistence.DiscriminatorColumn</code>
 */
public abstract class SourceBaseDiscriminatorColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{

	private DeclarationAnnotationElementAdapter<String> discriminatorTypeDeclarationAdapter;
	private AnnotationElementAdapter<String> discriminatorTypeAdapter;
	private DiscriminatorType discriminatorType;

	private DeclarationAnnotationElementAdapter<Integer> lengthDeclarationAdapter;
	private AnnotationElementAdapter<Integer> lengthAdapter;
	private Integer length;



	protected SourceBaseDiscriminatorColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.discriminatorTypeDeclarationAdapter = this.buildDiscriminatorTypeDeclarationAdapter();
		this.discriminatorTypeAdapter = this.buildDiscriminatorTypeAdapter();
		this.lengthDeclarationAdapter = this.buildLengthDeclarationAdapter();
		this.lengthAdapter = this.buildLengthAdapter();
	}

	protected SourceBaseDiscriminatorColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	protected SourceBaseDiscriminatorColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.discriminatorType = this.buildDiscriminatorType(astAnnotation);
		this.length = this.buildLength(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncLength(this.buildLength(astAnnotation));
		this.syncDiscriminatorType(this.buildDiscriminatorType(astAnnotation));
	}


	// ********** DiscriminatorColumn implementation **********

	// ***** discriminator type
	public DiscriminatorType getDiscriminatorType() {
		return this.discriminatorType;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		if (ObjectTools.notEquals(this.discriminatorType, discriminatorType)) {
			this.discriminatorType = discriminatorType;
			this.discriminatorTypeAdapter.setValue(DiscriminatorType.toJavaAnnotationValue(discriminatorType));
		}
	}

	private void syncDiscriminatorType(DiscriminatorType astDiscriminatorType) {
		DiscriminatorType old = this.discriminatorType;
		this.discriminatorType = astDiscriminatorType;
		this.firePropertyChanged(DISCRIMINATOR_TYPE_PROPERTY, old, astDiscriminatorType);
	}

	private DiscriminatorType buildDiscriminatorType(Annotation astAnnotation) {
		return DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astAnnotation));
	}

	private DeclarationAnnotationElementAdapter<String> buildDiscriminatorTypeDeclarationAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(this.daa, this.getDiscriminatorTypeElementName());
	}

	private AnnotationElementAdapter<String> buildDiscriminatorTypeAdapter() {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, this.discriminatorTypeDeclarationAdapter);
	}

	protected abstract String getDiscriminatorTypeElementName();

	// ***** length
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (ObjectTools.notEquals(this.length, length)) {
			this.length = length;
			this.lengthAdapter.setValue(length);
		}
	}

	private void syncLength(Integer astLength) {
		Integer old = this.length;
		this.length = astLength;
		this.firePropertyChanged(LENGTH_PROPERTY, old, astLength);
	}

	private Integer buildLength(Annotation astAnnotation) {
		return this.lengthAdapter.getValue(astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<Integer> buildLengthDeclarationAdapter() {
		return this.buildIntegerElementAdapter(this.getLengthElementName());
	}

	private AnnotationElementAdapter<Integer> buildLengthAdapter() {
		return this.buildIntegerElementAdapter(this.lengthDeclarationAdapter);
	}

	protected abstract String getLengthElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.discriminatorType == null) &&
				(this.length == null);
	}

}
