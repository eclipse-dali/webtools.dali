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
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.AssociationOverrides
 */
public class SourceAssociationOverridesAnnotation
	extends SourceAnnotation<Member>
	implements AssociationOverridesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableAssociationOverrideAnnotation> associationOverrides = new Vector<NestableAssociationOverrideAnnotation>();


	public SourceAssociationOverridesAnnotation(JavaResourceNode parent, Member member) {
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
		sb.append(this.associationOverrides);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.ASSOCIATION_OVERRIDES__VALUE;
	}

	public String getNestableAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableAssociationOverrideAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableAssociationOverrideAnnotation>(this.associationOverrides);
	}

	public int nestedAnnotationsSize() {
		return this.associationOverrides.size();
	}

	public NestableAssociationOverrideAnnotation addNestedAnnotationInternal() {
		NestableAssociationOverrideAnnotation associationOverride = this.buildAssociationOverride(this.associationOverrides.size());
		this.associationOverrides.add(associationOverride);
		return associationOverride;
	}

	protected NestableAssociationOverrideAnnotation buildAssociationOverride(int index) {
		return SourceAssociationOverrideAnnotation.createNestedAssociationOverride(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableAssociationOverrideAnnotation nestedAnnotation) {
		this.fireItemAdded(ASSOCIATION_OVERRIDES_LIST, index, nestedAnnotation);
	}

	public NestableAssociationOverrideAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.associationOverrides, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);
	}

	public NestableAssociationOverrideAnnotation removeNestedAnnotationInternal(int index) {
		return this.associationOverrides.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableAssociationOverrideAnnotation nestedAnnotation) {
		this.fireItemRemoved(ASSOCIATION_OVERRIDES_LIST, index, nestedAnnotation);
	}

}
