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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.JoinColumns
 */
public final class SourceJoinColumnsAnnotation
	extends SourceAnnotation<Member>
	implements JoinColumnsAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableJoinColumnAnnotation> joinColumns = new Vector<NestableJoinColumnAnnotation>();


	public SourceJoinColumnsAnnotation(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		AnnotationContainerTools.initialize(this, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		AnnotationContainerTools.update(this, astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.joinColumns);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.JOIN_COLUMNS__VALUE;
	}

	public String getNestableAnnotationName() {
		return JoinColumnAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableJoinColumnAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableJoinColumnAnnotation>(this.joinColumns);
	}

	public int nestedAnnotationsSize() {
		return this.joinColumns.size();
	}

	public NestableJoinColumnAnnotation addNestedAnnotationInternal() {
		NestableJoinColumnAnnotation joinColumn = this.buildJoinColumn(this.joinColumns.size());
		this.joinColumns.add(joinColumn);
		return joinColumn;
	}

	private NestableJoinColumnAnnotation buildJoinColumn(int index) {
		return SourceJoinColumnAnnotation.createNestedJoinColumn(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableJoinColumnAnnotation nestedAnnotation) {
		this.fireItemAdded(JOIN_COLUMNS_LIST, index, nestedAnnotation);
	}

	public NestableJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.joinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public NestableJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
		return this.joinColumns.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableJoinColumnAnnotation nestedAnnotation) {
		this.fireItemRemoved(JOIN_COLUMNS_LIST, index, nestedAnnotation);
	}

}
