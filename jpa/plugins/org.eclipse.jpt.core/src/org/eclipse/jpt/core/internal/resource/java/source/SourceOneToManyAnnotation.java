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
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * javax.persistence.OneToMany
 */
public final class SourceOneToManyAnnotation
	extends SourceRelationshipMappingAnnotation
	implements OneToMany2_0Annotation
{
	protected static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;

	//added in JPA2.0
	private static final DeclarationAnnotationElementAdapter<Boolean> ORPHAN_REMOVAL_ADAPTER = buildOrphanRemovalAdapter();
	private final AnnotationElementAdapter<Boolean> orphanRemovalAdapter;
	private Boolean orphanRemoval;

	public SourceOneToManyAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
		this.orphanRemovalAdapter = this.buildBooleanAnnotationElementAdapter(ORPHAN_REMOVAL_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.mappedBy = this.buildMappedBy(astRoot);
		this.orphanRemoval = this.buildOrphanRemoval(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncMappedBy(this.buildMappedBy(astRoot));
		this.syncOrphanRemoval(this.buildOrphanRemoval(astRoot));
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


	// ********** OwnableRelationshipMappingAnnotation implementation **********

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
		return getElementTextRange(MAPPED_BY_ADAPTER, astRoot);
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(MAPPED_BY_ADAPTER, pos, astRoot);
	}

	// ********** OneToMany2_0Annotation implementation **********

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

	public TextRange getOrphanRemovalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ORPHAN_REMOVAL_ADAPTER, astRoot);
	}

	private Boolean buildOrphanRemoval(CompilationUnit astRoot) {
		return this.orphanRemovalAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__MAPPED_BY, false); // false = do not remove annotation when empty
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__FETCH);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter() {
		return buildOrphanRemovalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ONE_TO_MANY__ORPHAN_REMOVAL);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, false, BooleanExpressionConverter.instance());
	}

}
