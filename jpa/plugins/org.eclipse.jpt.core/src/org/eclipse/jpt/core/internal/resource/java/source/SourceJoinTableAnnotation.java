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

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;

/**
 * <code>javax.persistence.JoinTable</code>
 */
public final class SourceJoinTableAnnotation
	extends SourceBaseTableAnnotation
	implements JoinTableAnnotation
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

	void syncAddJoinColumn(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
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

	// ***** inverse join columns
	public ListIterator<JoinColumnAnnotation> inverseJoinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.inverseJoinColumns);
	}

	Iterable<NestableJoinColumnAnnotation> getNestableInverseJoinColumns() {
		return new LiveCloneIterable<NestableJoinColumnAnnotation>(this.inverseJoinColumns);
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

	private NestableJoinColumnAnnotation addInverseJoinColumn() {
		return this.addInverseJoinColumn(this.inverseJoinColumns.size());
	}

	public NestableJoinColumnAnnotation addInverseJoinColumn(int index) {
		return (NestableJoinColumnAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.inverseJoinColumnsContainer);
	}

	NestableJoinColumnAnnotation addInverseJoinColumn_() {
		return this.addInverseJoinColumn_(this.inverseJoinColumns.size());
	}

	private NestableJoinColumnAnnotation addInverseJoinColumn_(int index) {
		NestableJoinColumnAnnotation joinColumn = this.buildInverseJoinColumn(index);
		this.inverseJoinColumns.add(index, joinColumn);
		return joinColumn;
	}

	void syncAddInverseJoinColumn(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		int index = this.inverseJoinColumns.size();
		NestableJoinColumnAnnotation joinColumn = this.addInverseJoinColumn_(index);
		joinColumn.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
	}

	private NestableJoinColumnAnnotation buildInverseJoinColumn(int index) {
		return new SourceJoinColumnAnnotation(this, this.annotatedElement, buildInverseJoinColumnAnnotationAdapter(index));
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

	NestableJoinColumnAnnotation moveInverseJoinColumn_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.inverseJoinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	void inverseJoinColumnMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void removeInverseJoinColumn(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.inverseJoinColumnsContainer);
	}

	NestableJoinColumnAnnotation removeInverseJoinColumn_(int index) {
		return this.inverseJoinColumns.remove(index);
	}

	void syncRemoveInverseJoinColumns(int index) {
		this.removeItemsFromList(index, this.inverseJoinColumns, INVERSE_JOIN_COLUMNS_LIST);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumns.isEmpty() &&
				this.inverseJoinColumns.isEmpty();
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

		List<Map<String, Object>> inverseJoinColumnsState = this.buildStateList(this.inverseJoinColumns.size());
		for (NestableJoinColumnAnnotation joinColumn : this.getNestableInverseJoinColumns()) {
			Map<String, Object> joinColumnState = new HashMap<String, Object>();
			joinColumn.storeOn(joinColumnState);
			inverseJoinColumnsState.add(joinColumnState);
		}
		map.put(INVERSE_JOIN_COLUMNS_LIST, inverseJoinColumnsState);
		this.inverseJoinColumns.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> joinColumnsState = (List<Map<String, Object>>) map.get(JOIN_COLUMNS_LIST);
		for (Map<String, Object> joinColumnState : joinColumnsState) {
			this.addJoinColumn().restoreFrom(joinColumnState);
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> inverseJoinColumnsState = (List<Map<String, Object>>) map.get(INVERSE_JOIN_COLUMNS_LIST);
		for (Map<String, Object> joinColumnState : inverseJoinColumnsState) {
			this.addInverseJoinColumn().restoreFrom(joinColumnState);
		}
	}


	// ********** annotation containers **********

	abstract class AbstractJoinColumnAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation>
	{
		public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceJoinTableAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getNestedAnnotationName() {
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

		public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
			return SourceJoinTableAnnotation.this.getNestableJoinColumns();
		}

		public int getNestedAnnotationsSize() {
			return SourceJoinTableAnnotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotation() {
			return SourceJoinTableAnnotation.this.addJoinColumn_();
		}

		public void syncAddNestedAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			SourceJoinTableAnnotation.this.syncAddJoinColumn(astAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceJoinTableAnnotation.this.moveJoinColumn_(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotation(int index) {
			return SourceJoinTableAnnotation.this.removeJoinColumn_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceJoinTableAnnotation.this.syncRemoveJoinColumns(index);
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

		public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
			return SourceJoinTableAnnotation.this.getNestableInverseJoinColumns();
		}

		public int getNestedAnnotationsSize() {
			return SourceJoinTableAnnotation.this.inverseJoinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotation() {
			return SourceJoinTableAnnotation.this.addInverseJoinColumn_();
		}

		public void syncAddNestedAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			SourceJoinTableAnnotation.this.syncAddInverseJoinColumn(astAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceJoinTableAnnotation.this.moveInverseJoinColumn_(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotation(int index) {
			return SourceJoinTableAnnotation.this.removeInverseJoinColumn_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceJoinTableAnnotation.this.syncRemoveInverseJoinColumns(index);
		}
	}
}
