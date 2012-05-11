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
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

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

	public void synchronizeWith(CompilationUnit astRoot) {
		AnnotationContainerTools.synchronize(this, astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.joinColumns);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.JOIN_COLUMNS__VALUE;
	}

	public String getNestedAnnotationName() {
		return JoinColumnAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableJoinColumnAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableJoinColumnAnnotation>(this.joinColumns);
	}

	public int getNestedAnnotationsSize() {
		return this.joinColumns.size();
	}

	public NestableJoinColumnAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.joinColumns.size());
	}

	private NestableJoinColumnAnnotation addNestedAnnotation(int index) {
		NestableJoinColumnAnnotation joinColumn = this.buildJoinColumn(index);
		this.joinColumns.add(joinColumn);
		return joinColumn;
	}

	public void syncAddNestedAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		int index = this.joinColumns.size();
		NestableJoinColumnAnnotation joinColumn = this.addNestedAnnotation(index);
		joinColumn.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(JOIN_COLUMNS_LIST, index, joinColumn);
	}

	private NestableJoinColumnAnnotation buildJoinColumn(int index) {
		return SourceJoinColumnAnnotation.createNestedJoinColumn(this, this.member, index, this.daa);
	}

	public NestableJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.joinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableJoinColumnAnnotation removeNestedAnnotation(int index) {
		return this.joinColumns.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.joinColumns, JOIN_COLUMNS_LIST);
	}

}
