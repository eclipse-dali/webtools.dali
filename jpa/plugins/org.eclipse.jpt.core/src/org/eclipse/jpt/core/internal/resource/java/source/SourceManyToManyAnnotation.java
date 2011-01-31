/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.ManyToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.ManyToMany
 */
public final class SourceManyToManyAnnotation
	extends SourceRelationshipMappingAnnotation
	implements ManyToMany2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;


	public SourceManyToManyAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.mappedBy = this.buildMappedBy(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncMappedBy(this.buildMappedBy(astRoot));
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
		if (this.attributeValueHasChanged(this.mappedBy, mappedBy)) {
			this.mappedBy = mappedBy;
			this.mappedByAdapter.setValue(mappedBy);
		}
	}

	private void syncMappedBy(String astMappedBy) {
		String old = this.mappedBy;
		this.mappedBy = astMappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, astMappedBy);
	}

	private String buildMappedBy(CompilationUnit astRoot) {
		return this.mappedByAdapter.getValue(astRoot);
	}

	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(MAPPED_BY_ADAPTER, astRoot);
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(MAPPED_BY_ADAPTER, pos, astRoot);
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
