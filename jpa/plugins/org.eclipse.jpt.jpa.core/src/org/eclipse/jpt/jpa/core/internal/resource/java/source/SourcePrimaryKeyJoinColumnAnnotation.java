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
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;

/**
 * <code>javax.persistence.PrimaryKeyJoinColumn</code>
 */
public final class SourcePrimaryKeyJoinColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements PrimaryKeyJoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.PRIMARY_KEY_JOIN_COLUMNS);

	private DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;
	private AnnotationElementAdapter<String> referencedColumnNameAdapter;
	private String referencedColumnName;
	private TextRange referencedColumnNameTextRange;


	public static SourcePrimaryKeyJoinColumnAnnotation buildSourcePrimaryKeyJoinColumnAnnotation(
		JavaResourceNode parent, 
		AnnotatedElement element) {
	
		return new SourcePrimaryKeyJoinColumnAnnotation(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourcePrimaryKeyJoinColumnAnnotation buildSourcePrimaryKeyJoinColumnAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement element, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildPrimaryKeyJoinColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildPrimaryKeyJoinColumnAnnotationAdapter(element, idaa);
		return new SourcePrimaryKeyJoinColumnAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}
	
	public static SourcePrimaryKeyJoinColumnAnnotation buildNestedSourcePrimaryKeyJoinColumnAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourcePrimaryKeyJoinColumnAnnotation(parent, element, idaa);
	}

	private SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildReferencedColumnNameDeclarationAdapter();
		this.referencedColumnNameAdapter = this.buildReferencedColumnNameAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.referencedColumnName = this.buildReferencedColumnName(astAnnotation);
		this.referencedColumnNameTextRange = this.buildReferencedColumnNameTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncReferencedColumnName(this.buildReferencedColumnName(astAnnotation));
		this.referencedColumnNameTextRange = this.buildReferencedColumnNameTextRange(astAnnotation);
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** PrimaryKeyJoinColumn implementation **********

	// ***** referenced column name
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		if (this.attributeValueHasChanged(this.referencedColumnName, referencedColumnName)) {
			this.referencedColumnName = referencedColumnName;
			this.referencedColumnNameAdapter.setValue(referencedColumnName);
		}
	}

	private void syncReferencedColumnName(String astReferencedColumnName) {
		String old = this.referencedColumnName;
		this.referencedColumnName = astReferencedColumnName;
		this.firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, old, astReferencedColumnName);
	}

	private String buildReferencedColumnName(Annotation astAnnotation) {
		return this.referencedColumnNameAdapter.getValue(astAnnotation);
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.referencedColumnNameTextRange;
	}

	private TextRange buildReferencedColumnNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.referencedColumnNameDeclarationAdapter, astAnnotation);
	}

	public boolean referencedColumnNameTouches(int pos) {
		return this.textRangeTouches(this.referencedColumnNameTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String> buildReferencedColumnNameDeclarationAdapter() {
		return this.buildStringElementAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
	}

	private AnnotationElementAdapter<String> buildReferencedColumnNameAdapter() {
		return this.buildStringElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.referencedColumnName == null);
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildPrimaryKeyJoinColumnAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildPrimaryKeyJoinColumnDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
