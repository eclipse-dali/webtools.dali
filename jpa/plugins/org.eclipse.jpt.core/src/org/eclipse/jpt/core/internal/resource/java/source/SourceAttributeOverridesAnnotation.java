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
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAttributeOverrideAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.AttributeOverrides
 */
public final class SourceAttributeOverridesAnnotation
	extends SourceAnnotation<Member>
	implements AttributeOverridesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableAttributeOverrideAnnotation> attributesOverrides = new Vector<NestableAttributeOverrideAnnotation>();


	public SourceAttributeOverridesAnnotation(JavaResourceNode parent, Member member) {
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
		sb.append(this.attributesOverrides);
	}


	// ********** AnnotationContainer implementation **********

	public String getContainerAnnotationName() {
		return this.getAnnotationName();
	}

	public org.eclipse.jdt.core.dom.Annotation getContainerJdtAnnotation(CompilationUnit astRoot) {
		return this.getJdtAnnotation(astRoot);
	}

	public String getElementName() {
		return JPA.ATTRIBUTE_OVERRIDES__VALUE;
	}

	public String getNestableAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	public ListIterator<NestableAttributeOverrideAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableAttributeOverrideAnnotation>(this.attributesOverrides);
	}

	public int nestedAnnotationsSize() {
		return this.attributesOverrides.size();
	}

	public NestableAttributeOverrideAnnotation addNestedAnnotationInternal() {
		NestableAttributeOverrideAnnotation attributeOverride = this.buildAttributeOverride(this.attributesOverrides.size());
		this.attributesOverrides.add(attributeOverride);
		return attributeOverride;
	}

	private NestableAttributeOverrideAnnotation buildAttributeOverride(int index) {
		return SourceAttributeOverrideAnnotation.createNestedAttributeOverride(this, this.member, index, this.daa);
	}

	public void nestedAnnotationAdded(int index, NestableAttributeOverrideAnnotation nestedAnnotation) {
		this.fireItemAdded(ATTRIBUTE_OVERRIDES_LIST, index, nestedAnnotation);
	}

	public NestableAttributeOverrideAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.attributesOverrides, targetIndex, sourceIndex).get(targetIndex);
	}

	public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);
	}

	public NestableAttributeOverrideAnnotation removeNestedAnnotationInternal(int index) {
		return this.attributesOverrides.remove(index);
	}

	public void nestedAnnotationRemoved(int index, NestableAttributeOverrideAnnotation nestedAnnotation) {
		this.fireItemRemoved(ATTRIBUTE_OVERRIDES_LIST, index, nestedAnnotation);
	}

}
