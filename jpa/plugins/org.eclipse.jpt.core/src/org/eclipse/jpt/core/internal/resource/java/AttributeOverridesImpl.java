/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAttributeOverride;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AttributeOverridesImpl extends AbstractResourceAnnotation<Member> implements AttributeOverrides
{	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final List<NestableAttributeOverride> attributesOverrides;
	
	protected AttributeOverridesImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.attributesOverrides = new ArrayList<NestableAttributeOverride>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}
		
	public ListIterator<NestableAttributeOverride> nestedAnnotations() {
		return new CloneListIterator<NestableAttributeOverride>(this.attributesOverrides);
	}
	
	public int nestedAnnotationsSize() {
		return this.attributesOverrides.size();
	}	

	public NestableAttributeOverride addInternal(int index) {
		NestableAttributeOverride attributeOverride = createAttributeOverride(index);
		this.attributesOverrides.add(index, attributeOverride);	
		return attributeOverride;
	}
	
	public NestableAttributeOverride add(int index) {
		NestableAttributeOverride attributeOverride = createAttributeOverride(index);
		add(index, attributeOverride);
		return attributeOverride;
	}
	
	private void add(int index, NestableAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.attributesOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}

	public void remove(NestableAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.attributesOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.attributesOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public int indexOf(NestableAttributeOverride attributeOverride) {
		return this.attributesOverrides.indexOf(attributeOverride);
	}
	
	public NestableAttributeOverride nestedAnnotationAt(int index) {
		return this.attributesOverrides.get(index);
	}
	
	public NestableAttributeOverride nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestableAttributeOverride attributeOverride : this.attributesOverrides) {
			if (jdtAnnotation == attributeOverride.getJdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return attributeOverride;
			}
		}
		return null;
	}
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.attributesOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.attributesOverrides, targetIndex, sourceIndex);
	}

	public String getElementName() {
		return "value";
	}

	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
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

		public AttributeOverrides buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new AttributeOverridesImpl(parent, member);
		}
		
		public AttributeOverrides buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
