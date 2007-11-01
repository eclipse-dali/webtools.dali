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
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class JoinTableImpl extends AbstractTableResource implements JoinTable
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__CATALOG);
	
	private final List<NestableJoinColumn> joinColumns;
	
	private final List<NestableJoinColumn> inverseJoinColumns;
	
	private final JoinColumnsContainerAnnotation joinColumnsContainerAnnotation;
	
	private final InverseJoinColumnsContainerAnnotation inverseJoinColumnsContainerAnnotation;

	protected JoinTableImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
		this.joinColumns = new ArrayList<NestableJoinColumn>();
		this.inverseJoinColumns = new ArrayList<NestableJoinColumn>();
		this.joinColumnsContainerAnnotation = new JoinColumnsContainerAnnotation();
		this.inverseJoinColumnsContainerAnnotation = new InverseJoinColumnsContainerAnnotation();
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.joinColumnsContainerAnnotation);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.inverseJoinColumnsContainerAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(@SuppressWarnings("unused") DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return CATALOG_ADAPTER;
	}
	
	@Override
	protected NestableUniqueConstraint createUniqueConstraint(int index) {
		return UniqueConstraintImpl.createJoinTableUniqueConstraint(this, this.getMember(), index);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateJoinColumnsFromJava(astRoot);
		this.updateInverseJoinColumnsFromJava(astRoot);
	}
	
	private void updateJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.joinColumnsContainerAnnotation);
	}
	
	private void updateInverseJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.inverseJoinColumnsContainerAnnotation);
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
	
	public JoinColumn addJoinColumn(int index) {
		NestableJoinColumn joinColumn = createJoinColumn(index);
		addJoinColumn(index, joinColumn);
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index+1, this.joinColumnsContainerAnnotation);
		joinColumn.newAnnotation();
		return joinColumn;
	}
	
	protected void addJoinColumn(int index, NestableJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public void removeJoinColumn(int index) {
		NestableJoinColumn joinColumn = this.joinColumns.get(index);
		removeJoinColumn(joinColumn);
		joinColumn.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.joinColumnsContainerAnnotation);
	}
	
	protected void removeJoinColumn(NestableJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public void moveJoinColumn(int oldIndex, int newIndex) {
		moveItemInList(newIndex, oldIndex, this.joinColumns, JOIN_COLUMNS_LIST);
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.joinColumnsContainerAnnotation);
	}

	public ListIterator<JoinColumn> inverseJoinColumns() {
		return new CloneListIterator<JoinColumn>(this.inverseJoinColumns);
	}
	
	public int inverseJoinColumnsSize() {
		return this.inverseJoinColumns.size();
	}
	
	public NestableJoinColumn inverseJoinColumnAt(int index) {
		return this.inverseJoinColumns.get(index);
	}
	
	public int indexOfInverseJoinColumn(JoinColumn joinColumn) {
		return this.inverseJoinColumns.indexOf(joinColumn);
	}
	
	public JoinColumn addInverseJoinColumn(int index) {
		NestableJoinColumn joinColumn = createInverseJoinColumn(index);
		addInverseJoinColumn(index, joinColumn);
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index+1, this.inverseJoinColumnsContainerAnnotation);
		joinColumn.newAnnotation();
		return joinColumn;
	}
	
	private void addInverseJoinColumn(int index, NestableJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.inverseJoinColumns, INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void removeInverseJoinColumn(int index) {
		NestableJoinColumn joinColumn = this.inverseJoinColumns.get(index);
		this.removeInverseJoinColumn(joinColumn);
		joinColumn.removeAnnotation();
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.inverseJoinColumnsContainerAnnotation);
	}

	protected void removeInverseJoinColumn(NestableJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.inverseJoinColumns, INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void moveInverseJoinColumn(int oldIndex, int newIndex) {
		moveItemInList(newIndex, oldIndex, this.inverseJoinColumns, INVERSE_JOIN_COLUMNS_LIST);
		ContainerAnnotationTools.synchAnnotationsAfterMove(newIndex, oldIndex, this.inverseJoinColumnsContainerAnnotation);
	}

	protected NestableJoinColumn createJoinColumn(int index) {
		return JoinColumnImpl.createJoinTableJoinColumn(this, getMember(), index);
	}

	protected NestableJoinColumn createInverseJoinColumn(int index) {
		return JoinColumnImpl.createJoinTableInverseJoinColumn(this, getMember(), index);
	}

	private class JoinColumnsContainerAnnotation extends AbstractContainerAnnotation {

		public void addInternal(int index) {
			NestableJoinColumn joinColumn = JoinTableImpl.this.createJoinColumn(index);
			JoinTableImpl.this.joinColumns.add(index, joinColumn);
		}
		
		public NestableJoinColumn add(int index) {
			NestableJoinColumn joinColumn = JoinTableImpl.this.createJoinColumn(index);
			JoinTableImpl.this.addJoinColumn(index, joinColumn);
			return joinColumn;
		}

		public int indexOf(NestableJoinColumn joinColumn) {
			return JoinTableImpl.this.indexOfJoinColumn(joinColumn);
		}

		public void move(int oldIndex, int newIndex) {
			JoinTableImpl.this.joinColumns.add(newIndex, JoinTableImpl.this.joinColumns.remove(oldIndex));
		}

		public NestableJoinColumn nestedAnnotationAt(int index) {
			return JoinTableImpl.this.joinColumnAt(index);
		}

		public ListIterator<NestableJoinColumn> nestedAnnotations() {
			return new CloneListIterator<NestableJoinColumn>(JoinTableImpl.this.joinColumns);
		}

		public int nestedAnnotationsSize() {
			return joinColumnsSize();
		}

		public void remove(NestableJoinColumn nestedAnnotation) {
			JoinTableImpl.this.removeJoinColumn(nestedAnnotation);	
		}
	}
	
	private class InverseJoinColumnsContainerAnnotation extends AbstractContainerAnnotation {

		public void addInternal(int index) {
			NestableJoinColumn joinColumn = JoinTableImpl.this.createInverseJoinColumn(index);
			JoinTableImpl.this.inverseJoinColumns.add(index, joinColumn);
		}
		
		public NestableJoinColumn add(int index) {
			NestableJoinColumn joinColumn = JoinTableImpl.this.createInverseJoinColumn(index);
			JoinTableImpl.this.addInverseJoinColumn(index, joinColumn);
			return joinColumn;
		}

		public int indexOf(NestableJoinColumn joinColumn) {
			return JoinTableImpl.this.indexOfInverseJoinColumn(joinColumn);
		}

		public void move(int oldIndex, int newIndex) {
			JoinTableImpl.this.moveInverseJoinColumn(oldIndex, newIndex);
		}

		public NestableJoinColumn nestedAnnotationAt(int index) {
			return JoinTableImpl.this.inverseJoinColumnAt(index);
		}

		public ListIterator<NestableJoinColumn> nestedAnnotations() {
			return new CloneListIterator<NestableJoinColumn>(JoinTableImpl.this.inverseJoinColumns);
		}

		public int nestedAnnotationsSize() {
			return inverseJoinColumnsSize();
		}

		public void remove(NestableJoinColumn nestedAnnotation) {
			JoinTableImpl.this.removeInverseJoinColumn(nestedAnnotation);	
		}
	}

	private abstract class AbstractContainerAnnotation extends AbstractResource implements ContainerAnnotation<NestableJoinColumn> {

		public AbstractContainerAnnotation() {
			super(JoinTableImpl.this);
		}
		
		public void initialize(CompilationUnit astRoot) {
			//nothing to initialize
		}
		
		public String getAnnotationName() {
			return JoinTableImpl.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.JOIN_COLUMN;
		}
		
		public void remove(int index) {
			this.remove(nestedAnnotationAt(index));	
		}		

		public NestableJoinColumn nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableJoinColumn joinColumn : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == joinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return joinColumn;
				}
			}
			return null;
		}

		public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
			return JoinTableImpl.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			JoinTableImpl.this.newAnnotation();
		}

		public void removeAnnotation() {
			JoinTableImpl.this.removeAnnotation();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			JoinTableImpl.this.updateFromJava(astRoot);
		}
		
		public ITextRange textRange(CompilationUnit astRoot) {
			return JoinTableImpl.this.textRange(astRoot);
		}
	}
	public static class JoinTableAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final JoinTableAnnotationDefinition INSTANCE = new JoinTableAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private JoinTableAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new JoinTableImpl(parent, member);
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
