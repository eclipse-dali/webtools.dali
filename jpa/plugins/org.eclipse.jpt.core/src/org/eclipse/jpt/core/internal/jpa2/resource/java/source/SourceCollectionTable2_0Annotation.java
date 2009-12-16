/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.AnnotationContainerTools;
import org.eclipse.jpt.core.internal.resource.java.source.SourceBaseTableAnnotation;
import org.eclipse.jpt.core.internal.resource.java.source.SourceJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.CollectionTable
 */
public class SourceCollectionTable2_0Annotation
	extends SourceBaseTableAnnotation
	implements CollectionTable2_0Annotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(CollectionTable2_0Annotation.ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__CATALOG);


	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();


	public SourceCollectionTable2_0Annotation(JavaResourceNode parent, Member member) {
		this(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public SourceCollectionTable2_0Annotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		AnnotationContainerTools.initialize(this.joinColumnsContainer, astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		AnnotationContainerTools.update(this.joinColumnsContainer, astRoot);
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @CollectionTable is never nested
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getSchemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @CollectionTable is never nested
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getCatalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @CollectionTable is never nested
		return CATALOG_ADAPTER;
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA2_0.COLLECTION_TABLE__UNIQUE_CONSTRAINTS;
	}

	// ********** CollectionTable2_0Annotation implementation **********

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

	public JoinColumnAnnotation initializeJoinColumns() {
		return this.addJoinColumnInternal();
	}


	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		CollectionTable2_0Annotation oldCollectionTable = (CollectionTable2_0Annotation) oldAnnotation;
		for (JoinColumnAnnotation oldJoinColumn : CollectionTools.iterable(oldCollectionTable.joinColumns())) {
			NestableJoinColumnAnnotation newJoinColumn = this.addJoinColumn(oldCollectionTable.indexOfJoinColumn(oldJoinColumn));
			newJoinColumn.initializeFrom((NestableAnnotation) oldJoinColumn);
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
			return SourceCollectionTable2_0Annotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
			return SourceCollectionTable2_0Annotation.this.getJdtAnnotation(astRoot);
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
			return JPA2_0.COLLECTION_TABLE__JOIN_COLUMNS;
		}

		public ListIterator<NestableJoinColumnAnnotation> nestedAnnotations() {
			return SourceCollectionTable2_0Annotation.this.nestableJoinColumns();
		}

		public int nestedAnnotationsSize() {
			return SourceCollectionTable2_0Annotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotationInternal() {
			return SourceCollectionTable2_0Annotation.this.addJoinColumnInternal();
		}

		public void nestedAnnotationAdded(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceCollectionTable2_0Annotation.this.joinColumnAdded(index, nestedAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceCollectionTable2_0Annotation.this.moveJoinColumnInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceCollectionTable2_0Annotation.this.joinColumnMoved(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
			return SourceCollectionTable2_0Annotation.this.removeJoinColumnInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceCollectionTable2_0Annotation.this.joinColumnRemoved(index, nestedAnnotation);
		}
	}
}
