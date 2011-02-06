/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.AnnotationContainerTools;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceBaseTableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableJoinColumnAnnotation;

/**
 * <code>javax.persistence.CollectionTable</code>
 */
public final class SourceCollectionTable2_0Annotation
	extends SourceBaseTableAnnotation
	implements CollectionTable2_0Annotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(CollectionTable2_0Annotation.ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__CATALOG);


	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();


	public SourceCollectionTable2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
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
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		AnnotationContainerTools.synchronize(this.joinColumnsContainer, astRoot);
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
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

	Iterable<NestableJoinColumnAnnotation> getNestableJoinColumns() {
		return new LiveCloneIterable<NestableJoinColumnAnnotation>(this.joinColumns);
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

	private NestableJoinColumnAnnotation addJoinColumn() {
		return this.addJoinColumn(this.joinColumns.size());
	}

	public NestableJoinColumnAnnotation addJoinColumn(int index) {
		return (NestableJoinColumnAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.joinColumnsContainer);
	}

	NestableJoinColumnAnnotation addJoinColumn_() {
		return this.addJoinColumn_(this.joinColumns.size());
	}

	private NestableJoinColumnAnnotation addJoinColumn_(int index) {
		NestableJoinColumnAnnotation joinColumn = this.buildJoinColumn(index);
		this.joinColumns.add(index, joinColumn);
		return joinColumn;
	}

	void syncAddJoinColumn(Annotation astAnnotation) {
		int index = this.joinColumns.size();
		NestableJoinColumnAnnotation joinColumn = this.addJoinColumn_(index);
		joinColumn.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(JOIN_COLUMNS_LIST, index, joinColumn);
	}

	private NestableJoinColumnAnnotation buildJoinColumn(int index) {
		return new SourceJoinColumnAnnotation(this, this.annotatedElement, buildJoinColumnAnnotationAdapter(index));
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

	NestableJoinColumnAnnotation moveJoinColumn_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.joinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public void removeJoinColumn(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.joinColumnsContainer);
	}

	NestableJoinColumnAnnotation removeJoinColumn_(int index) {
		return this.joinColumns.remove(index);
	}

	void syncRemoveJoinColumns(int index) {
		this.removeItemsFromList(index, this.joinColumns, JOIN_COLUMNS_LIST);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumns.isEmpty();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);

		List<Map<String, Object>> joinColumnsState = this.buildStateList(this.joinColumns.size());
		for (NestableJoinColumnAnnotation joinColumn : this.getNestableJoinColumns()) {
			Map<String, Object> joinColumnState = new HashMap<String, Object>();
			joinColumn.storeOn(joinColumnState);
			joinColumnsState.add(joinColumnState);
		}
		map.put(JOIN_COLUMNS_LIST, joinColumnsState);
		this.joinColumns.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> joinColumnsState = (List<Map<String, Object>>) map.get(JOIN_COLUMNS_LIST);
		for (Map<String, Object> joinColumnState : joinColumnsState) {
			this.addJoinColumn().restoreFrom(joinColumnState);
		}
	}


	// ********** join column container **********

	/**
	 * adapt the AnnotationContainer interface to the collection table's join columns
	 */
	class JoinColumnsAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation>
	{
		public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceCollectionTable2_0Annotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return JPA2_0.COLLECTION_TABLE__JOIN_COLUMNS;
		}

		public String getNestedAnnotationName() {
			return JoinColumnAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
			return SourceCollectionTable2_0Annotation.this.getNestableJoinColumns();
		}

		public int getNestedAnnotationsSize() {
			return SourceCollectionTable2_0Annotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotation() {
			return SourceCollectionTable2_0Annotation.this.addJoinColumn_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceCollectionTable2_0Annotation.this.syncAddJoinColumn(astAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceCollectionTable2_0Annotation.this.moveJoinColumn_(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotation(int index) {
			return SourceCollectionTable2_0Annotation.this.removeJoinColumn_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceCollectionTable2_0Annotation.this.syncRemoveJoinColumns(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}
}
