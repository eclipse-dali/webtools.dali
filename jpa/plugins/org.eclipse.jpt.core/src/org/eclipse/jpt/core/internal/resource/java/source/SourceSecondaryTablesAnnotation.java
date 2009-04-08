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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableSecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.SecondaryTables
 */
public final class SourceSecondaryTablesAnnotation
	extends SourceAnnotation<Member>
	implements SecondaryTablesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableSecondaryTableAnnotation> secondaryTables = new Vector<NestableSecondaryTableAnnotation>();


	public SourceSecondaryTablesAnnotation(JavaResourcePersistentType parent, Member member) {
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
		sb.append(this.secondaryTables);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.SECONDARY_TABLES__VALUE;
	}

	public String getNestableAnnotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableSecondaryTableAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableSecondaryTableAnnotation>(this.secondaryTables);
	}

	public int nestedAnnotationsSize() {
		return this.secondaryTables.size();
	}

	public NestableSecondaryTableAnnotation addNestedAnnotationInternal() {
		NestableSecondaryTableAnnotation secondaryTable = this.buildSecondaryTable(this.secondaryTables.size());
		this.secondaryTables.add(secondaryTable);
		return secondaryTable;
	}

	private NestableSecondaryTableAnnotation buildSecondaryTable(int index) {
		return SourceSecondaryTableAnnotation.createNestedSecondaryTable(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableSecondaryTableAnnotation nestedAnnotation) {
		this.fireItemAdded(SECONDARY_TABLES_LIST, index, nestedAnnotation);
	}

	public NestableSecondaryTableAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.secondaryTables, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(SECONDARY_TABLES_LIST, targetIndex, sourceIndex);
	}

	public NestableSecondaryTableAnnotation removeNestedAnnotationInternal(int index) {
		return this.secondaryTables.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableSecondaryTableAnnotation nestedAnnotation) {
		this.fireItemRemoved(SECONDARY_TABLES_LIST, index, nestedAnnotation);
	}

}
