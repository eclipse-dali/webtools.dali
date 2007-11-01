/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AssociationOverrideImpl 
	extends AbstractAnnotationResource<Member>  
	implements NestableAssociationOverride
{		
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;
	
	private String name;
		
	private final List<NestableJoinColumn> joinColumns;
	
	private final JoinColumnsContainerAnnotation joinColumnsContainerAnnotation;
	
	protected AssociationOverrideImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.ASSOCIATION_OVERRIDE__NAME, false); // false = do not remove annotation when empty
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(),this.nameDeclarationAdapter);
		this.joinColumns = new ArrayList<NestableJoinColumn>();
		this.joinColumnsContainerAnnotation = new JoinColumnsContainerAnnotation();
	}

	public void initialize(CompilationUnit astRoot) {		
		this.name = this.name(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.joinColumnsContainerAnnotation);
	}
	
	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
			
	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		AssociationOverride oldAssociationOverride = (AssociationOverride) oldAnnotation;
		setName(oldAssociationOverride.getName());
		for (JoinColumn joinColumn : CollectionTools.iterable(oldAssociationOverride.joinColumns())) {
			NestableJoinColumn newJoinColumn = addJoinColumn(oldAssociationOverride.indexOfJoinColumn(joinColumn));
			newJoinColumn.initializeFrom((NestableAnnotation) joinColumn);
		}
	}
	

	// ************* Association implementation *******************
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public ListIterator<JoinColumn> joinColumns() {
		return new CloneListIterator<JoinColumn>(this.joinColumns);
	}
	
	public int joinColumnsSize() {
		return this.joinColumns.size();
	}
	
	public NestableJoinColumn joinColumnAt(int index) {
		return this.joinColumns.get(index);
	}
	
	public int indexOfJoinColumn(JoinColumn joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}
	
	public NestableJoinColumn addJoinColumn(int index) {
		NestableJoinColumn joinColumn = createJoinColumn(index);
		addJoinColumn(index, joinColumn);
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index+1, this.joinColumnsContainerAnnotation);
		joinColumn.newAnnotation();
		return joinColumn;
	}
	
	private void addJoinColumn(int index, NestableJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public void removeJoinColumn(int index) {
		NestableJoinColumn joinColumn = this.joinColumns.get(index);
		removeJoinColumn(joinColumn);
		joinColumn.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.joinColumnsContainerAnnotation);
	}

	private void removeJoinColumn(NestableJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}

	public void moveJoinColumn(int oldIndex, int newIndex) {
		moveItemInList(newIndex, oldIndex, this.joinColumns, JOIN_COLUMNS_LIST);
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.joinColumnsContainerAnnotation);
	}


	protected NestableJoinColumn createJoinColumn(int index) {
		return JoinColumnImpl.createAssociationOverrideJoinColumn(getDeclarationAnnotationAdapter(), this, getMember(), index);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.updateJoinColumnsFromJava(astRoot);
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	private void updateJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.joinColumnsContainerAnnotation);
	}


	// ********** static methods **********
	static AssociationOverrideImpl createAssociationOverride(JavaResource parent, Member member) {
		return new AssociationOverrideImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static AssociationOverrideImpl createNestedAssociationOverride(JavaResource parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new AssociationOverrideImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, JPA.ASSOCIATION_OVERRIDE);
	}
	
	private class JoinColumnsContainerAnnotation extends AbstractResource 
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

		public void move(int oldIndex, int newIndex) {
			moveJoinColumn(oldIndex, newIndex);
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
		
		public ITextRange textRange(CompilationUnit astRoot) {
			return AssociationOverrideImpl.this.textRange(astRoot);
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return AssociationOverrideImpl.createAssociationOverride(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
