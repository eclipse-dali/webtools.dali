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
import org.eclipse.jpt.core.internal.IJpaPlatform;
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
	private static final String ANNOTATION_NAME = JPA.JOIN_TABLE;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__CATALOG);
	
	private final List<NestableJoinColumn> joinColumns;
	
	private final JoinColumnsContainerAnnotation joinColumnsContainerAnnotation;

	protected JoinTableImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
		this.joinColumns = new ArrayList<NestableJoinColumn>();
		this.joinColumnsContainerAnnotation = new JoinColumnsContainerAnnotation();
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
		return UniqueConstraintImpl.createJoinTableUniqueConstraint(new UniqueConstraintOwner(this), this.getMember(), index);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateJoinColumnsFromJava(astRoot);
	}
	
	/**
	 * here we just worry about getting the unique constraints lists the same size;
	 * then we delegate to the unique constraints to synch themselves up
	 */
	private void updateJoinColumnsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.joinColumnsContainerAnnotation);
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
		addJoinColumn(joinColumn);
		joinColumn.newAnnotation();
		synchJoinColumnAnnotationsAfterAdd(index);
		return joinColumn;
	}
	
	private void addJoinColumn(NestableJoinColumn joinColumn) {
		this.joinColumns.add(joinColumn);
		//property change notification
	}
	
	public void removeJoinColumn(int index) {
		NestableJoinColumn joinColumn = this.joinColumns.remove(index);
		joinColumn.removeAnnotation();
		synchJoinColumnAnnotationsAfterRemove(index);
	}

	public void moveJoinColumn(int oldIndex, int newIndex) {
		this.joinColumns.add(newIndex, this.joinColumns.remove(oldIndex));
		joinColumnMoved(newIndex, oldIndex);
	}

	private void joinColumnMoved(int sourceIndex, int targetIndex) {		
		ContainerAnnotationTools.synchAnnotationsAfterMove(sourceIndex, targetIndex, this.joinColumnsContainerAnnotation);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchJoinColumnAnnotationsAfterAdd(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index, this.joinColumnsContainerAnnotation);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchJoinColumnAnnotationsAfterRemove(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.joinColumnsContainerAnnotation);
	}
	
	protected NestableJoinColumn createJoinColumn(int index) {
		return JoinColumnImpl.createJoinTableJoinColumn(this, getMember(), index);
	}

	
	private class JoinColumnsContainerAnnotation implements ContainerAnnotation<NestableJoinColumn> {

		public NestableJoinColumn add(int index) {
			NestableJoinColumn joinColumn = createNestedAnnotation(index);
			JoinTableImpl.this.addJoinColumn(joinColumn);
			return joinColumn;
		}

		public NestableJoinColumn createNestedAnnotation(int index) {
			return JoinTableImpl.this.createJoinColumn(index);
		}

		public String getAnnotationName() {
			return JoinTableImpl.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.JOIN_COLUMN;
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

		public NestableJoinColumn nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableJoinColumn joinColumn : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == joinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return joinColumn;
				}
			}
			return null;
		}

		public ListIterator<NestableJoinColumn> nestedAnnotations() {
			return new CloneListIterator<NestableJoinColumn>(JoinTableImpl.this.joinColumns);
		}

		public int nestedAnnotationsSize() {
			return JoinTableImpl.this.joinColumns.size();
		}

		public void remove(NestableJoinColumn joinColumn) {
			this.remove(indexOf(joinColumn));
		}

		public void remove(int index) {
			JoinTableImpl.this.removeJoinColumn(index);	
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

		public IJpaPlatform jpaPlatform() {
			return JoinTableImpl.this.jpaPlatform();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			JoinTableImpl.this.updateFromJava(astRoot);
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
