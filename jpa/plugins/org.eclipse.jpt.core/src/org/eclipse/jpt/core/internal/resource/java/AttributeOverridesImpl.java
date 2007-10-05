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
import org.eclipse.jpt.core.internal.jdtutility.Member;
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
	
	public void move(int oldIndex, int newIndex) {
		this.attributesOverrides.add(newIndex, this.attributesOverrides.remove(oldIndex));
	}
	
	//TODO this is going to be copied in all ContainerAnnotation implementations, how to solve that??
	public void updateFromJava(CompilationUnit astRoot) {
		List<AttributeOverride> overrides = this.attributesOverrides;
		int persSize = overrides.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			AttributeOverride attributeOverride = overrides.get(i);
			if (attributeOverride.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			attributeOverride.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model attribute overrides beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				overrides.remove(persSize);
			}
		}
		else {
			// add new model attribute overrides until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				AttributeOverride attributeOverride = this.createAttributeOverride(javaSize);
				if (attributeOverride.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					this.attributesOverrides.add(attributeOverride);
					attributeOverride.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}
	
	public AttributeOverride createNestedAnnotation(int index) {
		return createAttributeOverride(index);
	}
	
	private AttributeOverride createAttributeOverride(int index) {
		return AttributeOverrideImpl.createNestedAttributeOverride(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

}
