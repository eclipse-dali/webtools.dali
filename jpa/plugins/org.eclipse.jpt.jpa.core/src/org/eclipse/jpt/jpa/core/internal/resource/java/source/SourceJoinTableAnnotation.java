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
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * <code>javax.persistence.JoinTable</code>
 */
public final class SourceJoinTableAnnotation
	extends SourceBaseTableAnnotation
	implements JoinTableAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JoinTableAnnotation.ANNOTATION_NAME);


	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();

	private final InverseJoinColumnsAnnotationContainer inverseJoinColumnsContainer = new InverseJoinColumnsAnnotationContainer();


	public SourceJoinTableAnnotation(JavaResourceModel parent, AnnotatedElement element) {
		this(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public SourceJoinTableAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.joinColumnsContainer.initializeFromContainerAnnotation(astAnnotation);
		this.inverseJoinColumnsContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.joinColumnsContainer.synchronize(astAnnotation);
		this.inverseJoinColumnsContainer.synchronize(astAnnotation);
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.JOIN_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.JOIN_TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.JOIN_TABLE__CATALOG);
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS;
	}

	// ********** JoinTableAnnotation implementation **********

	// **************** join columns *************************************************

	public ListIterable<JoinColumnAnnotation> getJoinColumns() {
		return this.joinColumnsContainer.getNestedAnnotations();
	}

	public int getJoinColumnsSize() {
		return this.joinColumnsContainer.getNestedAnnotationsSize();
	}

	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumnsContainer.getNestedAnnotation(index);
	}

	public JoinColumnAnnotation addJoinColumn(int index) {
		return this.joinColumnsContainer.addNestedAnnotation(index);
	}
	
	/* CU private */ JoinColumnAnnotation buildJoinColumn(int index) {
		return SourceJoinColumnAnnotation.buildNestedSourceJoinColumnAnnotation(
				this, this.annotatedElement, this.buildJoinColumnIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildJoinColumnIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		this.joinColumnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeJoinColumn(int index) {
		this.joinColumnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the collection table's join columns
	 */
	class JoinColumnsAnnotationContainer 
		extends AnnotationContainer<JoinColumnAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return JOIN_COLUMNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JPA.JOIN_TABLE__JOIN_COLUMNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JPA.JOIN_COLUMN;
		}
		@Override
		protected JoinColumnAnnotation buildNestedAnnotation(int index) {
			return SourceJoinTableAnnotation.this.buildJoinColumn(index);
		}
	}

	// **************** inverse join columns *************************************************

	public ListIterable<JoinColumnAnnotation> getInverseJoinColumns() {
		return this.inverseJoinColumnsContainer.getNestedAnnotations();
	}

	public int getInverseJoinColumnsSize() {
		return this.inverseJoinColumnsContainer.getNestedAnnotationsSize();
	}

	public JoinColumnAnnotation inverseJoinColumnAt(int index) {
		return this.inverseJoinColumnsContainer.getNestedAnnotation(index);
	}

	public JoinColumnAnnotation addInverseJoinColumn(int index) {
		return this.inverseJoinColumnsContainer.addNestedAnnotation(index);
	}
	
	/* CU private */ JoinColumnAnnotation buildInverseJoinColumn(int index) {
		return SourceJoinColumnAnnotation.buildNestedSourceJoinColumnAnnotation(
				this, this.annotatedElement, buildInverseJoinColumnIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildInverseJoinColumnIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	public void moveInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.inverseJoinColumnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeInverseJoinColumn(int index) {
		this.inverseJoinColumnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the collection table's join columns
	 */
	class InverseJoinColumnsAnnotationContainer 
		extends AnnotationContainer<JoinColumnAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return INVERSE_JOIN_COLUMNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JPA.JOIN_COLUMN;
		}
		@Override
		protected JoinColumnAnnotation buildNestedAnnotation(int index) {
			return SourceJoinTableAnnotation.this.buildInverseJoinColumn(index);
		}
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumnsContainer.isEmpty() &&
				this.inverseJoinColumnsContainer.isEmpty();
	}
}
