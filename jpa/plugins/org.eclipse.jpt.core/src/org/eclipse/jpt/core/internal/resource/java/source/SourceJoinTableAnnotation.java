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

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinTableAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.JoinTable
 */
public final class SourceJoinTableAnnotation
	extends SourceBaseTableAnnotation
	implements NestableJoinTableAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JoinTableAnnotation.ANNOTATION_NAME);


	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();

	private final Vector<NestableJoinColumnAnnotation> inverseJoinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final InverseJoinColumnsContainerAnnotation inverseJoinColumnsContainer = new InverseJoinColumnsContainerAnnotation();


	public SourceJoinTableAnnotation(JavaResourceNode parent, Member member) {
		this(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public SourceJoinTableAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
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
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		AnnotationContainerTools.synchronize(this.joinColumnsContainer, astRoot);
		AnnotationContainerTools.synchronize(this.inverseJoinColumnsContainer, astRoot);
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.JOIN_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getSchemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.JOIN_TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getCatalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, JPA.JOIN_TABLE__CATALOG);
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS;
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
		return new SourceJoinColumnAnnotation(this, this.member, buildJoinColumnAnnotationAdapter(index));
	}

	private IndexedDeclarationAnnotationAdapter buildJoinColumnAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(this.daa, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
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
		return new SourceJoinColumnAnnotation(this, this.member, buildInverseJoinColumnAnnotationAdapter(index));
	}

	private IndexedDeclarationAnnotationAdapter buildInverseJoinColumnAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(this.daa, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
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

	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		JoinTableAnnotation oldJoinTable = (JoinTableAnnotation) oldAnnotation;
		for (JoinColumnAnnotation oldJoinColumn : CollectionTools.iterable(oldJoinTable.joinColumns())) {
			NestableJoinColumnAnnotation newJoinColumn = this.addJoinColumn(oldJoinTable.indexOfJoinColumn(oldJoinColumn));
			newJoinColumn.initializeFrom((NestableAnnotation) oldJoinColumn);
		}
		for (JoinColumnAnnotation oldInverseJoinColumn : CollectionTools.iterable(oldJoinTable.inverseJoinColumns())) {
			NestableJoinColumnAnnotation newInverseJoinColumn = this.addInverseJoinColumn(oldJoinTable.indexOfInverseJoinColumn(oldInverseJoinColumn));
			newInverseJoinColumn.initializeFrom((NestableAnnotation) oldInverseJoinColumn);
		}
	}

	public void moveAnnotation(int newIndex) {
		// the only place where a join table annotation is nested is in an
		// association override; and that only nests a single join table, not an array
		// of join tables; so #moveAnnotation(int) is never called
		// TODO maybe NestableAnnotation should be split up;
		// moving this method to something like IndexableAnnotation
		throw new UnsupportedOperationException();
	}

	// ********** annotation containers **********

	abstract class AbstractJoinColumnAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation>
	{
		public String getContainerAnnotationName() {
			return SourceJoinTableAnnotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerAstAnnotation(CompilationUnit astRoot) {
			return SourceJoinTableAnnotation.this.getAstAnnotation(astRoot);
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
