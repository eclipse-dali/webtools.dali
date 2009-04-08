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

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.JoinTable
 */
public final class SourceJoinTableAnnotation
	extends SourceBaseTableAnnotation
	implements JoinTableAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JoinTableAnnotation.ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__CATALOG);

	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();

	private final Vector<NestableJoinColumnAnnotation> inverseJoinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final InverseJoinColumnsContainerAnnotation inverseJoinColumnsContainer = new InverseJoinColumnsContainerAnnotation();


	public SourceJoinTableAnnotation(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		AnnotationContainerTools.initialize(this.joinColumnsContainer, astRoot);
		AnnotationContainerTools.initialize(this.inverseJoinColumnsContainer, astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		AnnotationContainerTools.update(this.joinColumnsContainer, astRoot);
		AnnotationContainerTools.update(this.inverseJoinColumnsContainer, astRoot);
	}


	// ********** AbstractBaseTableAnnotation implementation **********

	@Override
	DeclarationAnnotationElementAdapter<String> getNameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return NAME_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<String> getSchemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return SCHEMA_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<String> getCatalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return CATALOG_ADAPTER;
	}

	@Override
	String getUniqueConstraintsElementName() {
		return JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS;
	}

	@Override
	NestableUniqueConstraintAnnotation buildUniqueConstraint(int index) {
		return SourceUniqueConstraintAnnotation.createJoinTableUniqueConstraint(this, this.member, index);
	}


	// ********** JoinTableAnnotation implementation **********

	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.joinColumns);
	}

	ListIterator<NestableJoinColumnAnnotation> nestableJoinColumns() {
		return new CloneListIterator<NestableJoinColumnAnnotation>(this.joinColumns);
	}

	public int joinColumnsSize() {
		return this.joinColumns.size();
	}

	public NestableJoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumns.get(index);
	}

	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}

	public NestableJoinColumnAnnotation addJoinColumn(int index) {
		return (NestableJoinColumnAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.joinColumnsContainer);
	}

	NestableJoinColumnAnnotation addJoinColumnInternal() {
		NestableJoinColumnAnnotation joinColumn = this.buildJoinColumn(this.joinColumns.size());
		this.joinColumns.add(joinColumn);
		return joinColumn;
	}

	private NestableJoinColumnAnnotation buildJoinColumn(int index) {
		return SourceJoinColumnAnnotation.createJoinTableJoinColumn(this, this.member, index);
	}

	void joinColumnAdded(int index, NestableJoinColumnAnnotation joinColumn) {
		this.fireItemAdded(JOIN_COLUMNS_LIST, index, joinColumn);
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.joinColumnsContainer);
	}

	NestableJoinColumnAnnotation moveJoinColumnInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.joinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	void joinColumnMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void removeJoinColumn(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.joinColumnsContainer);
	}

	NestableJoinColumnAnnotation removeJoinColumnInternal(int index) {
		return this.joinColumns.remove(index);
	}

	void joinColumnRemoved(int index, NestableJoinColumnAnnotation joinColumn) {
		this.fireItemRemoved(JOIN_COLUMNS_LIST, index, joinColumn);
	}

	// ***** inverse join columns
	public ListIterator<JoinColumnAnnotation> inverseJoinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.inverseJoinColumns);
	}

	ListIterator<NestableJoinColumnAnnotation> nestableInverseJoinColumns() {
		return new CloneListIterator<NestableJoinColumnAnnotation>(this.inverseJoinColumns);
	}

	public int inverseJoinColumnsSize() {
		return this.inverseJoinColumns.size();
	}

	public NestableJoinColumnAnnotation inverseJoinColumnAt(int index) {
		return this.inverseJoinColumns.get(index);
	}

	public int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.inverseJoinColumns.indexOf(joinColumn);
	}

	public NestableJoinColumnAnnotation addInverseJoinColumn(int index) {
		return (NestableJoinColumnAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.inverseJoinColumnsContainer);
	}

	NestableJoinColumnAnnotation addInverseJoinColumnInternal() {
		NestableJoinColumnAnnotation joinColumn = this.buildInverseJoinColumn(this.inverseJoinColumns.size());
		this.inverseJoinColumns.add(joinColumn);
		return joinColumn;
	}

	private NestableJoinColumnAnnotation buildInverseJoinColumn(int index) {
		return SourceJoinColumnAnnotation.createJoinTableInverseJoinColumn(this, this.member, index);
	}

	void inverseJoinColumnAdded(int index, NestableJoinColumnAnnotation joinColumn) {
		this.fireItemAdded(INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
	}

	public void moveInverseJoinColumn(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.inverseJoinColumnsContainer);
	}

	NestableJoinColumnAnnotation moveInverseJoinColumnInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.inverseJoinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	void inverseJoinColumnMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void removeInverseJoinColumn(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.inverseJoinColumnsContainer);
	}

	NestableJoinColumnAnnotation removeInverseJoinColumnInternal(int index) {
		return this.inverseJoinColumns.remove(index);
	}

	void inverseJoinColumnRemoved(int index, NestableJoinColumnAnnotation joinColumn) {
		this.fireItemRemoved(INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
	}


	// ********** annotation containers **********

	abstract class AbstractJoinColumnAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation>
	{
		public String getContainerAnnotationName() {
			return SourceJoinTableAnnotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
			return SourceJoinTableAnnotation.this.getJdtAnnotation(astRoot);
		}

		public String getNestableAnnotationName() {
			return JoinColumnAnnotation.ANNOTATION_NAME;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	/**
	 * adapt the AnnotationContainer interface to the join table's join columns
	 */
	class JoinColumnsAnnotationContainer
		extends AbstractJoinColumnAnnotationContainer
	{
		public String getElementName() {
			return JPA.JOIN_TABLE__JOIN_COLUMNS;
		}

		public ListIterator<NestableJoinColumnAnnotation> nestedAnnotations() {
			return SourceJoinTableAnnotation.this.nestableJoinColumns();
		}

		public int nestedAnnotationsSize() {
			return SourceJoinTableAnnotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotationInternal() {
			return SourceJoinTableAnnotation.this.addJoinColumnInternal();
		}

		public void nestedAnnotationAdded(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceJoinTableAnnotation.this.joinColumnAdded(index, nestedAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceJoinTableAnnotation.this.moveJoinColumnInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceJoinTableAnnotation.this.joinColumnMoved(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
			return SourceJoinTableAnnotation.this.removeJoinColumnInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceJoinTableAnnotation.this.joinColumnRemoved(index, nestedAnnotation);
		}

	}


	/**
	 * adapt the AnnotationContainer interface to the join table's inverse join columns
	 */
	class InverseJoinColumnsContainerAnnotation
		extends AbstractJoinColumnAnnotationContainer
	{
		public String getElementName() {
			return JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS;
		}

		public ListIterator<NestableJoinColumnAnnotation> nestedAnnotations() {
			return SourceJoinTableAnnotation.this.nestableInverseJoinColumns();
		}

		public int nestedAnnotationsSize() {
			return SourceJoinTableAnnotation.this.inverseJoinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotationInternal() {
			return SourceJoinTableAnnotation.this.addInverseJoinColumnInternal();
		}

		public void nestedAnnotationAdded(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceJoinTableAnnotation.this.inverseJoinColumnAdded(index, nestedAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceJoinTableAnnotation.this.moveInverseJoinColumnInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceJoinTableAnnotation.this.inverseJoinColumnMoved(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
			return SourceJoinTableAnnotation.this.removeInverseJoinColumnInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceJoinTableAnnotation.this.inverseJoinColumnRemoved(index, nestedAnnotation);
		}

	}

}
