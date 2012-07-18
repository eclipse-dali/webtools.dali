/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ManyToOne2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.ManyToOne</code>
 */
public final class SourceManyToOneAnnotation
	extends SourceRelationshipMappingAnnotation
	implements ManyToOne2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();
	private final AnnotationElementAdapter<Boolean> optionalAdapter;
	private Boolean optional;
	private TextRange optionalTextRange;


	public SourceManyToOneAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = this.buildBooleanAnnotationElementAdapter(OPTIONAL_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.optional = this.buildOptional(astAnnotation);
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncOptional(this.buildOptional(astAnnotation));
		this.optionalTextRange = this.buildOptionalTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.optional == null);
	}


	// ********** SourceRelationshipMappingAnnotation implementation **********

	@Override
	DeclarationAnnotationElementAdapter<String> getTargetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<String> getFetchAdapter() {
		return FETCH_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<String[]> getCascadeAdapter() {
		return CASCADE_ADAPTER;
	}


	// ********** ManyToOneMappingAnnotation implementation **********

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		if (this.attributeValueHasChanged(this.optional, optional)) {
			this.optional = optional;
			this.optionalAdapter.setValue(optional);
		}
	}

	private void syncOptional(Boolean astOptional) {
		Boolean old = this.optional;
		this.optional = astOptional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, astOptional);
	}

	private Boolean buildOptional(Annotation astAnnotation) {
		return this.optionalAdapter.getValue(astAnnotation);
	}

	public TextRange getOptionalTextRange() {
		return this.optionalTextRange;
	}

	private TextRange buildOptionalTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(OPTIONAL_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__FETCH);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__OPTIONAL);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, BooleanExpressionConverter.instance());
	}
}
