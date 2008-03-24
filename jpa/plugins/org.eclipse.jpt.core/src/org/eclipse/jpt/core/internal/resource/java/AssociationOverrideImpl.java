/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverride;
import org.eclipse.jpt.core.resource.java.NestableJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AssociationOverrideImpl 
	extends OverrideImpl  
	implements NestableAssociationOverride
{		
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
		
	private final List<NestableJoinColumn> joinColumns;
	
	private final JoinColumnsContainerAnnotation joinColumnsContainerAnnotation;
	
	protected AssociationOverrideImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.joinColumns = new ArrayList<NestableJoinColumn>();
		this.joinColumnsContainerAnnotation = new JoinColumnsContainerAnnotation();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {		
		super.initialize(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.joinColumnsContainerAnnotation);
	}
	
	public String getAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AssociationOverrideAnnotation oldAssociationOverride = (AssociationOverrideAnnotation) oldAnnotation;
		for (JoinColumnAnnotation joinColumn : CollectionTools.iterable(oldAssociationOverride.joinColumns())) {
			NestableJoinColumn newJoinColumn = addJoinColumn(oldAssociationOverride.indexOfJoinColumn(joinColumn));
			newJoinColumn.initializeFrom((NestableAnnotation) joinColumn);
		}
	}
	

	// ************* Association implementation *******************
	
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return new CloneListIterator<JoinColumnAnnotation>(this.joinColumns);
	}
	
	public int joinColumnsSize() {
		return this.joinColumns.size();
	}
	
	public NestableJoinColumn joinColumnAt(int index) {
		return this.joinColumns.get(index);
	}
	
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}
	
	public NestableJoinColumn addJoinColumn(int index) {
		NestableJoinColumn joinColumn = (NestableJoinColumn) ContainerAnnotationTools.addNestedAnnotation(index, this.joinColumnsContainerAnnotation);
		fireItemAdded(AssociationOverrideAnnotation.JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	private void addJoinColumn(int index, NestableJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.joinColumns, AssociationOverrideAnnotation.JOIN_COLUMNS_LIST);
	}
	
	public void removeJoinColumn(int index) {
		NestableJoinColumn joinColumn = this.joinColumns.get(index);
		removeJoinColumn(joinColumn);
		joinColumn.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.joinColumnsContainerAnnotation);
	}

	private void removeJoinColumn(NestableJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.joinColumns, AssociationOverrideAnnotation.JOIN_COLUMNS_LIST);
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		moveJoinColumnInternal(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, this.joinColumnsContainerAnnotation);
		fireItemMoved(AssociationOverrideAnnotation.JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}
	
	protected void moveJoinColumnInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.joinColumns, targetIndex, sourceIndex);
	}

	protected NestableJoinColumn createJoinColumn(int index) {
		return JoinColumnImpl.createAssociationOverrideJoinColumn(getDeclarationAnnotationAdapter(), this, getMember(), index);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateJoinColumnsFromJava(astRoot);
	}
	
	private void updateJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.joinColumnsContainerAnnotation);
	}


	// ********** static methods **********
	static AssociationOverrideImpl createAssociationOverride(JavaResourceNode parent, Member member) {
		return new AssociationOverrideImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static AssociationOverrideImpl createNestedAssociationOverride(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new AssociationOverrideImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, JPA.ASSOCIATION_OVERRIDE);
	}
	
	private class JoinColumnsContainerAnnotation extends AbstractJavaResourceNode 
		implements ContainerAnnotation<NestableJoinColumn> 
	{
		public JoinColumnsContainerAnnotation() {
			super(AssociationOverrideImpl.this);
		}

		public void initialize(CompilationUnit astRoot) {
			//nothing to initialize
		}
		
		public NestableJoinColumn addInternal(int index) {
			NestableJoinColumn joinColumn = AssociationOverrideImpl.this.createJoinColumn(index);
			AssociationOverrideImpl.this.joinColumns.add(index, joinColumn);
			return joinColumn;
		}
		
		public NestableJoinColumn add(int index) {
			NestableJoinColumn joinColumn = AssociationOverrideImpl.this.createJoinColumn(index);
			AssociationOverrideImpl.this.addJoinColumn(index, joinColumn);
			return joinColumn;
		}
		
		public int indexOf(NestableJoinColumn pkJoinColumn) {
			return AssociationOverrideImpl.this.indexOfJoinColumn(pkJoinColumn);
		}

		public void move(int targetIndex, int sourceIndex) {
			AssociationOverrideImpl.this.moveJoinColumn(targetIndex, sourceIndex);
		}

		public void moveInternal(int targetIndex, int sourceIndex) {
			AssociationOverrideImpl.this.moveJoinColumnInternal(targetIndex, sourceIndex);			
		}
		
		public NestableJoinColumn nestedAnnotationAt(int index) {
			return AssociationOverrideImpl.this.joinColumnAt(index);
		}

		public ListIterator<NestableJoinColumn> nestedAnnotations() {
			return new CloneListIterator<NestableJoinColumn>(AssociationOverrideImpl.this.joinColumns);
		}

		public int nestedAnnotationsSize() {
			return joinColumnsSize();
		}

		public void remove(int index) {
			this.remove(nestedAnnotationAt(index));
		}		

		public void remove(NestableJoinColumn joinColumn) {
			AssociationOverrideImpl.this.removeJoinColumn(joinColumn);
		}

		public String getAnnotationName() {
			return AssociationOverrideImpl.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.JOIN_COLUMN;
		}

		public NestableJoinColumn nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableJoinColumn pkJoinColumn : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == pkJoinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return pkJoinColumn;
				}
			}
			return null;
		}

		public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
			return AssociationOverrideImpl.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			AssociationOverrideImpl.this.newAnnotation();
		}

		public void removeAnnotation() {
			AssociationOverrideImpl.this.removeAnnotation();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			AssociationOverrideImpl.this.updateFromJava(astRoot);
		}
		
		public TextRange textRange(CompilationUnit astRoot) {
			return AssociationOverrideImpl.this.textRange(astRoot);
		}
		
		public String getElementName() {
			return "joinColumns";
		}

	}

	public static class AssociationOverrideAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final AssociationOverrideAnnotationDefinition INSTANCE = new AssociationOverrideAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private AssociationOverrideAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return AssociationOverrideImpl.createAssociationOverride(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return AssociationOverrideAnnotation.ANNOTATION_NAME;
		}
	}
}
