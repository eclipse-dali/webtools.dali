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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public abstract class SourceAssociationOverrideAnnotation
	extends SourceOverrideAnnotation
	implements NestableAssociationOverrideAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();


	protected SourceAssociationOverrideAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
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

	
	// ********** SourceOverrideAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.ASSOCIATION_OVERRIDE__NAME;
	}


	// ********** AssociationOverrideAnnotation implementation **********

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
		return SourceJoinColumnAnnotation.createAssociationOverrideJoinColumn(this.daa, this, this.annotatedElement, index);
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


	// ********** join column container **********

	/**
	 * adapt the AnnotationContainer interface to the override's join columns
	 */
	class JoinColumnsAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation> 
	{
		public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceAssociationOverrideAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
		}

		public String getNestedAnnotationName() {
			return JoinColumnAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
			return SourceAssociationOverrideAnnotation.this.getNestableJoinColumns();
		}

		public int getNestedAnnotationsSize() {
			return SourceAssociationOverrideAnnotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotation() {
			return SourceAssociationOverrideAnnotation.this.addJoinColumn_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceAssociationOverrideAnnotation.this.syncAddJoinColumn(astAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceAssociationOverrideAnnotation.this.moveJoinColumn_(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotation(int index) {
			return SourceAssociationOverrideAnnotation.this.removeJoinColumn_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceAssociationOverrideAnnotation.this.syncRemoveJoinColumns(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumns.isEmpty();
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
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
	public void restoreFrom(Map<String,Object> map) {
		super.restoreFrom(map);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> joinColumnsState = (List<Map<String, Object>>) map.get(JOIN_COLUMNS_LIST);
		for (Map<String, Object> joinColumnState : joinColumnsState) {
			this.addJoinColumn().restoreFrom(joinColumnState);
		}
	}
}
