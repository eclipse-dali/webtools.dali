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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.OneToOne</code>
 */
public final class SourceOneToOneAnnotation
	extends SourceRelationshipMappingAnnotation
	implements OneToOne2_0Annotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;
	private TextRange mappedByTextRange;

	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();
	private final AnnotationElementAdapter<Boolean> optionalAdapter;
	private Boolean optional;
	private TextRange optionalTextRange;

	//added in JPA 2.0
	private static final DeclarationAnnotationElementAdapter<Boolean> ORPHAN_REMOVAL_ADAPTER = buildOrphanRemovalAdapter();
	private final AnnotationElementAdapter<Boolean> orphanRemovalAdapter;
	private Boolean orphanRemoval;
	private TextRange orphanRemovalTextRange;

	public SourceOneToOneAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
		this.optionalAdapter = this.buildBooleanAnnotationElementAdapter(OPTIONAL_ADAPTER);
		this.orphanRemovalAdapter = this.buildBooleanAnnotationElementAdapter(ORPHAN_REMOVAL_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);

		this.mappedBy = this.buildMappedBy(astRoot);
		this.mappedByTextRange = this.buildMappedByTextRange(astRoot);

		this.optional = this.buildOptional(astRoot);
		this.optionalTextRange = this.buildOptionalTextRange(astRoot);

		this.orphanRemoval = this.buildOrphanRemoval(astRoot);
		this.orphanRemovalTextRange = this.buildOrphanRemovalTextRange(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);

		this.syncMappedBy(this.buildMappedBy(astRoot));
		this.mappedByTextRange = this.buildMappedByTextRange(astRoot);

		this.syncOptional(this.buildOptional(astRoot));
		this.optionalTextRange = this.buildOptionalTextRange(astRoot);

		this.syncOrphanRemoval(this.buildOrphanRemoval(astRoot));
		this.orphanRemovalTextRange = this.buildOrphanRemovalTextRange(astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.mappedBy == null) &&
				(this.optional == null) &&
				(this.orphanRemoval == null);
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


	// ********** OneToOneAnnotation implementation **********

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

	public TextRange getMappedByTextRange() {
		return this.mappedByTextRange;
	}

	private TextRange buildMappedByTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(MAPPED_BY_ADAPTER, astRoot);
	}

	public boolean mappedByTouches(int pos) {
		return this.textRangeTouches(mappedByTextRange, pos);
	}

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

	private Boolean buildOptional(CompilationUnit astRoot) {
		return this.optionalAdapter.getValue(astRoot);
	}

	public TextRange getOptionalTextRange() {
		return this.optionalTextRange;
	}

	private TextRange buildOptionalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	// ********** OneToOne2_0Annotation implementation **********

	public Boolean getOrphanRemoval() {
		return this.orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		if (this.attributeValueHasChanged(this.orphanRemoval, orphanRemoval)) {
			this.orphanRemoval = orphanRemoval;
			this.orphanRemovalAdapter.setValue(orphanRemoval);
		}
	}

	private void syncOrphanRemoval(Boolean astOrphanRemoval) {
		Boolean old = this.orphanRemoval;
		this.orphanRemoval = astOrphanRemoval;
		this.firePropertyChanged(ORPHAN_REMOVAL_PROPERTY, old, astOrphanRemoval);
	}

	public TextRange getOrphanRemovalTextRange() {
		return this.orphanRemovalTextRange;
	}

	private TextRange buildOrphanRemovalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ORPHAN_REMOVAL_ADAPTER, astRoot);
	}

	private Boolean buildOrphanRemoval(CompilationUnit astRoot) {
		return this.orphanRemovalAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__MAPPED_BY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__FETCH);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__OPTIONAL);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter() {
		return buildOrphanRemovalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ONE_TO_ONE__ORPHAN_REMOVAL);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, BooleanExpressionConverter.instance());
	}
}
