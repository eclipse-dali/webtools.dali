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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumns;
import org.eclipse.jpt.core.resource.java.NestableJoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class JoinColumnsImpl extends AbstractAnnotationResource<Member> implements JoinColumns
{
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableJoinColumn> joinColumns;
	
	protected JoinColumnsImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.joinColumns = new ArrayList<NestableJoinColumn>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JoinColumnAnnotation.ANNOTATION_NAME;
	}
		
	public ListIterator<NestableJoinColumn> nestedAnnotations() {
		return new CloneListIterator<NestableJoinColumn>(this.joinColumns);
	}
	
	public int nestedAnnotationsSize() {
		return this.joinColumns.size();
	}	

	public NestableJoinColumn addInternal(int index) {
		JoinColumnImpl joinColumn = createJoinColumn(index);
		this.joinColumns.add(index, joinColumn);
		return joinColumn;
	}
	
	public NestableJoinColumn add(int index) {
		JoinColumnImpl joinColumn = createJoinColumn(index);
		add(index, joinColumn);
		return joinColumn;
	}
	
	protected void add(int index, NestableJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}

	public void remove(NestableJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public int indexOf(NestableJoinColumn joinColumn) {
		return this.joinColumns.indexOf(joinColumn);
	}
	
	public NestableJoinColumn nestedAnnotationAt(int index) {
		return this.joinColumns.get(index);
	}
	
	public NestableJoinColumn nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestableJoinColumn joinColumn : this.joinColumns) {
			if (jdtAnnotation == joinColumn.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return joinColumn;
			}
		}
		return null;
	}
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.joinColumns, JOIN_COLUMNS_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.joinColumns, targetIndex, sourceIndex);
	}

	public String getElementName() {
		return "value";
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
		
	private JoinColumnImpl createJoinColumn(int index) {
		return JoinColumnImpl.createNestedJoinColumn(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	
	public static class JoinColumnsAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final JoinColumnsAnnotationDefinition INSTANCE = new JoinColumnsAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private JoinColumnsAnnotationDefinition() {
			super();
		}

		public JoinColumns buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new JoinColumnsImpl(parent, member);
		}
		
		public JoinColumns buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
