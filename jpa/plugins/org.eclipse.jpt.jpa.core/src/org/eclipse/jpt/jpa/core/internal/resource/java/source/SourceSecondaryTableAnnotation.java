/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.jpa.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestablePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableSecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;

/**
 * <code>javax.persistence.SecondaryTable</code>
 */
public final class SourceSecondaryTableAnnotation
	extends SourceBaseTableAnnotation
	implements NestableSecondaryTableAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(SecondaryTableAnnotation.ANNOTATION_NAME);

	private final Vector<NestablePrimaryKeyJoinColumnAnnotation> pkJoinColumns = new Vector<NestablePrimaryKeyJoinColumnAnnotation>();
	private final PkJoinColumnsAnnotationContainer pkJoinColumnsContainer = new PkJoinColumnsAnnotationContainer();


	public SourceSecondaryTableAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		AnnotationContainerTools.initialize(this.pkJoinColumnsContainer, astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		AnnotationContainerTools.synchronize(this.pkJoinColumnsContainer, astRoot);
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__CATALOG);
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ************* SecondaryTableAnnotation implementation *******************

	// ***** pk join columns
	public ListIterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns() {
		return new CloneListIterator<PrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	Iterable<NestablePrimaryKeyJoinColumnAnnotation> getNestablePkJoinColumns() {
		return new LiveCloneIterable<NestablePrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	public int pkJoinColumnsSize() {
		return this.pkJoinColumns.size();
	}

	public NestablePrimaryKeyJoinColumnAnnotation pkJoinColumnAt(int index) {
		return this.pkJoinColumns.get(index);
	}

	public int indexOfPkJoinColumn(PrimaryKeyJoinColumnAnnotation joinColumn) {
		return this.pkJoinColumns.indexOf(joinColumn);
	}

	private NestablePrimaryKeyJoinColumnAnnotation addPkJoinColumn() {
		return this.addPkJoinColumn(this.pkJoinColumns.size());
	}

	public NestablePrimaryKeyJoinColumnAnnotation addPkJoinColumn(int index) {
		return (NestablePrimaryKeyJoinColumnAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.pkJoinColumnsContainer);
	}

	NestablePrimaryKeyJoinColumnAnnotation addPkJoinColumn_() {
		return this.addPkJoinColumn_(this.pkJoinColumns.size());
	}

	private NestablePrimaryKeyJoinColumnAnnotation addPkJoinColumn_(int index) {
		NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn = this.buildPrimaryKeyJoinColumn(index);
		this.pkJoinColumns.add(index, pkJoinColumn);
		return pkJoinColumn;
	}

	void syncAddPkJoinColumn(Annotation astAnnotation) {
		int index = this.pkJoinColumns.size();
		NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn = this.addPkJoinColumn_(index);
		pkJoinColumn.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(PK_JOIN_COLUMNS_LIST, index, pkJoinColumn);
	}

	private NestablePrimaryKeyJoinColumnAnnotation buildPrimaryKeyJoinColumn(int index) {
		return SourcePrimaryKeyJoinColumnAnnotation.createSecondaryTablePrimaryKeyJoinColumn(this.daa, this, this.annotatedElement, index);
	}

	void pkJoinColumnAdded(int index, NestablePrimaryKeyJoinColumnAnnotation joinColumn) {
		this.fireItemAdded(PK_JOIN_COLUMNS_LIST, index, joinColumn);
	}

	public void movePkJoinColumn(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.pkJoinColumnsContainer);
	}

	NestablePrimaryKeyJoinColumnAnnotation movePkJoinColumn_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.pkJoinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public void removePkJoinColumn(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.pkJoinColumnsContainer);
	}

	NestablePrimaryKeyJoinColumnAnnotation removePkJoinColumn_(int index) {
		return this.pkJoinColumns.remove(index);
	}

	void syncRemovePkJoinColumns(int index) {
		this.removeItemsFromList(index, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}


	// ********** NestableAnnotation implementation **********

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.pkJoinColumns.isEmpty();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);

		List<Map<String, Object>> pkJoinColumnsState = this.buildStateList(this.pkJoinColumns.size());
		for (NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn : this.getNestablePkJoinColumns()) {
			Map<String, Object> pkJoinColumnState = new HashMap<String, Object>();
			pkJoinColumn.storeOn(pkJoinColumnState);
			pkJoinColumnsState.add(pkJoinColumnState);
		}
		map.put(PK_JOIN_COLUMNS_LIST, pkJoinColumnsState);
		this.pkJoinColumns.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> pkJoinColumnsState = (List<Map<String, Object>>) map.get(PK_JOIN_COLUMNS_LIST);
		for (Map<String, Object> pkJoinColumnState : pkJoinColumnsState) {
			this.addPkJoinColumn().restoreFrom(pkJoinColumnState);
		}
	}


	// ********** static methods **********

	public static SourceSecondaryTableAnnotation createSecondaryTable(JavaResourceNode parent, Member member) {
		return new SourceSecondaryTableAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SourceSecondaryTableAnnotation createNestedSecondaryTable(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter secondaryTablesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, secondaryTablesAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(member, idaa);
		return new SourceSecondaryTableAnnotation(parent, member, idaa, annotationAdapter);
	}


	// ********** pk join column container **********

	/**
	 * adapt the AnnotationContainer interface to the secondary table's
	 * primary key join columns
	 */
	class PkJoinColumnsAnnotationContainer
		implements AnnotationContainer<NestablePrimaryKeyJoinColumnAnnotation> 
	{
		public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceSecondaryTableAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS;
		}

		public String getNestedAnnotationName() {
			return PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestablePrimaryKeyJoinColumnAnnotation> getNestedAnnotations() {
			return SourceSecondaryTableAnnotation.this.getNestablePkJoinColumns();
		}

		public int getNestedAnnotationsSize() {
			return SourceSecondaryTableAnnotation.this.pkJoinColumnsSize();
		}

		public NestablePrimaryKeyJoinColumnAnnotation addNestedAnnotation() {
			return SourceSecondaryTableAnnotation.this.addPkJoinColumn_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceSecondaryTableAnnotation.this.syncAddPkJoinColumn(astAnnotation);
		}

		public NestablePrimaryKeyJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceSecondaryTableAnnotation.this.movePkJoinColumn_(targetIndex, sourceIndex);
		}

		public NestablePrimaryKeyJoinColumnAnnotation removeNestedAnnotation(int index) {
			return SourceSecondaryTableAnnotation.this.removePkJoinColumn_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceSecondaryTableAnnotation.this.syncRemovePkJoinColumns(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}
}
