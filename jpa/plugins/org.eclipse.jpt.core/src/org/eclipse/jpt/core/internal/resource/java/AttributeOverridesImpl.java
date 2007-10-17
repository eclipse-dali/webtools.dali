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
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AttributeOverridesImpl extends AbstractAnnotationResource<Member> implements AttributeOverrides
{
	private static final String ANNOTATION_NAME = JPA.ATTRIBUTE_OVERRIDES;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableAttributeOverride> attributesOverrides;
	
	protected AttributeOverridesImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.attributesOverrides = new ArrayList<NestableAttributeOverride>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JPA.ATTRIBUTE_OVERRIDE;
	}
		
	public ListIterator<NestableAttributeOverride> nestedAnnotations() {
		return new CloneListIterator<NestableAttributeOverride>(this.attributesOverrides);
	}
	
	public int nestedAnnotationsSize() {
		return this.attributesOverrides.size();
	}	

	public NestableAttributeOverride add(int index) {
		NestableAttributeOverride attributeOverride = createAttributeOverride(index);
		add(index, attributeOverride);
		return attributeOverride;
	}
	
	private void add(int index, NestableAttributeOverride attributeOverride) {
		this.attributesOverrides.add(index, attributeOverride);
		//TODO event notification
	}

	public void remove(NestableAttributeOverride attributeOverride) {
		this.attributesOverrides.remove(attributeOverride);		
	}
	
	public void remove(int index) {
		this.attributesOverrides.remove(index);
	}
	
	public int indexOf(NestableAttributeOverride attributeOverride) {
		return this.attributesOverrides.indexOf(attributeOverride);
	}
	
	public NestableAttributeOverride nestedAnnotationAt(int index) {
		return this.attributesOverrides.get(index);
	}
	
	public NestableAttributeOverride nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestableAttributeOverride attributeOverride : this.attributesOverrides) {
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
	
	public NestableAttributeOverride createNestedAnnotation(int index) {
		return createAttributeOverride(index);
	}
	
	private AttributeOverrideImpl createAttributeOverride(int index) {
		return AttributeOverrideImpl.createNestedAttributeOverride(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	
	public static class AttributeOverridesAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final AttributeOverridesAnnotationDefinition INSTANCE = new AttributeOverridesAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private AttributeOverridesAnnotationDefinition() {
			super();
		}

		public AttributeOverrides buildAnnotation(JavaResource parent, Member member) {
			return new AttributeOverridesImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
