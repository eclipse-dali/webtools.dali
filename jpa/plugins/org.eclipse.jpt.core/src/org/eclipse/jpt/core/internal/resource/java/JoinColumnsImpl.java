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
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class JoinColumnsImpl extends AbstractAnnotationResource<Member> implements JoinColumns
{
	private List<NestableJoinColumn> joinColumns;
	
	protected JoinColumnsImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.joinColumns = new ArrayList<NestableJoinColumn>();
	}

	public String getAnnotationName() {
		return JPA.JOIN_COLUMNS;
	}

	public String getNestableAnnotationName() {
		return JPA.JOIN_COLUMN;
	}
		
	public ListIterator<NestableJoinColumn> nestedAnnotations() {
		return new CloneListIterator<NestableJoinColumn>(this.joinColumns);
	}
	
	public int nestedAnnotationsSize() {
		return this.joinColumns.size();
	}	

	public NestableJoinColumn add(int index) {
		JoinColumnImpl joinColumn = createJoinColumn(index);
		add(index, joinColumn);
		return joinColumn;
	}
	
	private void add(int index, NestableJoinColumn joinColumn) {
		this.joinColumns.add(index, joinColumn);
		//TODO event notification
	}

	public void remove(NestableJoinColumn joinColumn) {
		this.joinColumns.remove(joinColumn);		
	}
	
	public void remove(int index) {
		this.joinColumns.remove(index);
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
	
	public void move(int oldIndex, int newIndex) {
		this.joinColumns.add(newIndex, this.joinColumns.remove(oldIndex));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	public NestableJoinColumn createNestedAnnotation(int index) {
		return createJoinColumn(index);
	}
	
	private JoinColumnImpl createJoinColumn(int index) {
		return JoinColumnImpl.createNestedJoinColumn(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

}
