/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToManyAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.OneToMany</code>
 */
public final class SourceOneToManyAnnotation
	extends SourceRelationshipMappingAnnotation
	implements OneToManyAnnotation2_0
{
	protected static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();
	private final AnnotationElementAdapter<String> mappedByAdapter;
	private String mappedBy;
	private TextRange mappedByTextRange;

	//added in JPA2.0
	private static final DeclarationAnnotationElementAdapter<Boolean> ORPHAN_REMOVAL_ADAPTER = buildOrphanRemovalAdapter();
	private final AnnotationElementAdapter<Boolean> orphanRemovalAdapter;
	private Boolean orphanRemoval;
	private TextRange orphanRemovalTextRange;

	public SourceOneToManyAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
		this.orphanRemovalAdapter = this.buildBooleanAnnotationElementAdapter(ORPHAN_REMOVAL_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.mappedBy = this.buildMappedBy(astAnnotation);
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);

		this.orphanRemoval = this.buildOrphanRemoval(astAnnotation);
		this.orphanRemovalTextRange = this.buildOrphanRemovalTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncMappedBy(this.buildMappedBy(astAnnotation));
		this.mappedByTextRange = this.buildMappedByTextRange(astAnnotation);

		this.syncOrphanRemoval(this.buildOrphanRemoval(astAnnotation));
		this.orphanRemovalTextRange = this.buildOrphanRemovalTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.mappedBy == null) &&
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


	// ********** OwnableRelationshipMappingAnnotation implementation **********

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

	// ********** OneToMany2_0Annotation implementation **********

	public Boolean getOrphanRemoval() {
		return this.orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		if (ObjectTools.notEquals(this.orphanRemoval, orphanRemoval)) {
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

	private TextRange buildOrphanRemovalTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(ORPHAN_REMOVAL_ADAPTER, astAnnotation);
	}

	private Boolean buildOrphanRemoval(Annotation astAnnotation) {
		return this.orphanRemovalAdapter.getValue(astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__MAPPED_BY);
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
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, BooleanExpressionConverter.instance());
	}

}
