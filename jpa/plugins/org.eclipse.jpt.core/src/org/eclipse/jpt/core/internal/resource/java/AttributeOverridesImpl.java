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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AttributeOverridesImpl extends AbstractAnnotationResource<Member> implements AttributeOverrides
{
	private List<AttributeOverride> attributesOverrides;
	
	protected AttributeOverridesImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.attributesOverrides = new ArrayList<AttributeOverride>();
	}

	public String getAnnotationName() {
		return JPA.ATTRIBUTE_OVERRIDES;
	}

	public String getNestableAnnotationName() {
		return JPA.ATTRIBUTE_OVERRIDE;
	}
		
	public ListIterator<AttributeOverride> nestedAnnotations() {
		return new CloneListIterator<AttributeOverride>(this.attributesOverrides);
	}
	
	public int nestedAnnotationsSize() {
		return this.attributesOverrides.size();
	}
	
	public AttributeOverride add(int index) {
		AttributeOverride attributeOverride = createAttributeOverride(index);
		this.attributesOverrides.add(index, attributeOverride);
		return attributeOverride;
	}
	public void remove(Object attributeOverride) {
		this.attributesOverrides.remove(attributeOverride);		
	}
	
	public void remove(int index) {
		this.attributesOverrides.remove(index);
	}
	
	public int indexOf(Object attributeOverride) {
		return this.attributesOverrides.indexOf(attributeOverride);
	}
	
	public AttributeOverride nestedAnnotationAt(int index) {
		return this.attributesOverrides.get(index);
	}
	
	public AttributeOverride nestedAnnotationFor(Annotation jdtAnnotation) {
		for (AttributeOverride attributeOverride : CollectionTools.iterable(nestedAnnotations())) {
			if (jdtAnnotation == attributeOverride.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return attributeOverride;
			}
		}
		return null;
	}
	
	public void move(int oldIndex, int newIndex) {
		this.attributesOverrides.add(newIndex, this.attributesOverrides.remove(oldIndex));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	public AttributeOverride createNestedAnnotation(int index) {
		return createAttributeOverride(index);
	}
	
	private AttributeOverride createAttributeOverride(int index) {
		return AttributeOverrideImpl.createNestedAttributeOverride(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

}
