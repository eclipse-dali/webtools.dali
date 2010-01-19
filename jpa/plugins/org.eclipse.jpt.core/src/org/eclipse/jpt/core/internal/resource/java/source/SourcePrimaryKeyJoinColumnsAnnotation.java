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

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestablePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.PrimaryKeyJoinColumns
 */
public final class SourcePrimaryKeyJoinColumnsAnnotation
	extends SourceAnnotation<Member>
	implements PrimaryKeyJoinColumnsAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestablePrimaryKeyJoinColumnAnnotation> pkJoinColumns = new Vector<NestablePrimaryKeyJoinColumnAnnotation>();


	public SourcePrimaryKeyJoinColumnsAnnotation(JavaResourceNode parent, Member member) {
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
		sb.append(this.pkJoinColumns);
	}


	// ********** AnnotationContainer Implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerAstAnnotation(CompilationUnit astRoot) {
		return this.getAstAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMNS__VALUE;
	}

	public String getNestableAnnotationName() {
		return PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestablePrimaryKeyJoinColumnAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestablePrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	public int nestedAnnotationsSize() {
		return this.pkJoinColumns.size();
	}

	public NestablePrimaryKeyJoinColumnAnnotation addNestedAnnotationInternal() {
		NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn = this.buildPrimaryKeyJoinColumn(this.pkJoinColumns.size());
		this.pkJoinColumns.add(pkJoinColumn);
		return pkJoinColumn;
	}

	private NestablePrimaryKeyJoinColumnAnnotation buildPrimaryKeyJoinColumn(int index) {
		return SourcePrimaryKeyJoinColumnAnnotation.createNestedPrimaryKeyJoinColumn(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestablePrimaryKeyJoinColumnAnnotation nestedAnnotation) {
		this.fireItemAdded(PK_JOIN_COLUMNS_LIST, index, nestedAnnotation);
	}

	public NestablePrimaryKeyJoinColumnAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.pkJoinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(PK_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public NestablePrimaryKeyJoinColumnAnnotation removeNestedAnnotationInternal(int index) {
		return this.pkJoinColumns.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestablePrimaryKeyJoinColumnAnnotation nestedAnnotation) {
		this.fireItemRemoved(PK_JOIN_COLUMNS_LIST, index, nestedAnnotation);
	}

}
