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
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.AssociationOverride
 */
public abstract class SourceAssociationOverrideAnnotation
	extends SourceOverrideAnnotation
	implements NestableAssociationOverrideAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();
	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();


	// ********** construction/initialization **********

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
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		AnnotationContainerTools.update(this.joinColumnsContainer, astRoot);
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
		return SourceJoinColumnAnnotation.createAssociationOverrideJoinColumn(this.daa, this, this.member, index);
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


	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AssociationOverrideAnnotation oldOverride = (AssociationOverrideAnnotation) oldAnnotation;
		for (JoinColumnAnnotation oldJoinColumn : CollectionTools.iterable(oldOverride.joinColumns())) {
			NestableJoinColumnAnnotation newJoinColumn = this.addJoinColumn(oldOverride.indexOfJoinColumn(oldJoinColumn));
			newJoinColumn.initializeFrom((NestableAnnotation) oldJoinColumn);
		}
	}

	// ********** join column container **********

	/**
	 * adapt the AnnotationContainer interface to the override's join columns
	 */
	class JoinColumnsAnnotationContainer
		implements AnnotationContainer<NestableJoinColumnAnnotation> 
	{
		public String getContainerAnnotationName() {
			return SourceAssociationOverrideAnnotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
			return SourceAssociationOverrideAnnotation.this.getJdtAnnotation(astRoot);
		}

		public String getElementName() {
			return JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
		}

		public String getNestableAnnotationName() {
			return JoinColumnAnnotation.ANNOTATION_NAME;
		}

		public ListIterator<NestableJoinColumnAnnotation> nestedAnnotations() {
			return SourceAssociationOverrideAnnotation.this.nestableJoinColumns();
		}

		public int nestedAnnotationsSize() {
			return SourceAssociationOverrideAnnotation.this.joinColumnsSize();
		}

		public NestableJoinColumnAnnotation addNestedAnnotationInternal() {
			return SourceAssociationOverrideAnnotation.this.addJoinColumnInternal();
		}

		public void nestedAnnotationAdded(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceAssociationOverrideAnnotation.this.joinColumnAdded(index, nestedAnnotation);
		}

		public NestableJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceAssociationOverrideAnnotation.this.moveJoinColumnInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceAssociationOverrideAnnotation.this.joinColumnMoved(targetIndex, sourceIndex);
		}

		public NestableJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
			return SourceAssociationOverrideAnnotation.this.removeJoinColumnInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableJoinColumnAnnotation nestedAnnotation) {
			SourceAssociationOverrideAnnotation.this.joinColumnRemoved(index, nestedAnnotation);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}

}
