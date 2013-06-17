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
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ManyToManyAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.ManyToMany</code>
 */
public final class SourceManyToManyAnnotation
	extends SourceRelationshipMappingAnnotation
	implements ManyToManyAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;
	private TextRange mappedByTextRange;


	public SourceManyToManyAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.mappedBy = this.buildMappedBy(astAnnotation);
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncMappedBy(this.buildMappedBy(astAnnotation));
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.mappedBy == null);
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


	//**************** OwnableRelationshipMappingAnnotation implementation **************

	// ***** mapped by
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		if (ObjectTools.notEquals(this.mappedBy, mappedBy)) {
			this.mappedBy = mappedBy;
			this.mappedByAdapter.setValue(mappedBy);
		}
	}

	private void syncMappedBy(String astMappedBy) {
		String old = this.mappedBy;
		this.mappedBy = astMappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, astMappedBy);
	}

	private String buildMappedBy(Annotation astAnnotation) {
		return this.mappedByAdapter.getValue(astAnnotation);
	}

	public TextRange getMappedByTextRange() {
		return this.mappedByTextRange;
	}

	private TextRange buildMappedByTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(MAPPED_BY_ADAPTER, astAnnotation);
	}

	public boolean mappedByTouches(int pos) {
		return this.textRangeTouches(this.mappedByTextRange, pos);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__MAPPED_BY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__FETCH);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__CASCADE);
	}

}
