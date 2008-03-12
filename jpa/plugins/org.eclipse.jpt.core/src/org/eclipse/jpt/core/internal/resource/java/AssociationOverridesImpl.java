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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrides;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverride;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class AssociationOverridesImpl extends AbstractResourceAnnotation<Member> implements AssociationOverrides
{	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected final List<NestableAssociationOverride> associationOverrides;
	
	protected AssociationOverridesImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.associationOverrides = new ArrayList<NestableAssociationOverride>();
	}

	public void initialize(CompilationUnit astRoot) {
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}
		
	public ListIterator<NestableAssociationOverride> nestedAnnotations() {
		return new CloneListIterator<NestableAssociationOverride>(this.associationOverrides);
	}
	
	public int nestedAnnotationsSize() {
		return this.associationOverrides.size();
	}	

	public NestableAssociationOverride addInternal(int index) {
		NestableAssociationOverride associationOverride = createAssociationOverride(index);
		this.associationOverrides.add(index, associationOverride);
		return associationOverride;
	}
	
	public NestableAssociationOverride add(int index) {
		NestableAssociationOverride associationOverride = createAssociationOverride(index);
		add(index, associationOverride);
		return associationOverride;
	}
	
	private void add(int index, NestableAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.associationOverrides, ASSOCIATION_OVERRIDES_LIST);
	}

	public void remove(NestableAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.associationOverrides, ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void remove(int index) {
		removeItemFromList(index, this.associationOverrides, ASSOCIATION_OVERRIDES_LIST);
	}
	
	public int indexOf(NestableAssociationOverride associationOverride) {
		return this.associationOverrides.indexOf(associationOverride);
	}
	
	public NestableAssociationOverride nestedAnnotationAt(int index) {
		return this.associationOverrides.get(index);
	}
	
	public NestableAssociationOverride nestedAnnotationFor(Annotation jdtAnnotation) {
		for (NestableAssociationOverride associationOverride : this.associationOverrides) {
			if (jdtAnnotation == associationOverride.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
				return associationOverride;
			}
		}
		return null;
	}
	
	public void move(int targetIndex, int sourceIndex) {
		moveItemInList(targetIndex, sourceIndex, this.associationOverrides, ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.associationOverrides, targetIndex, sourceIndex);		
	}

	public String getElementName() {
		return "value";
	}

	public void updateFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this);
	}
	
	private AssociationOverrideImpl createAssociationOverride(int index) {
		return AssociationOverrideImpl.createNestedAssociationOverride(this, getMember(), index, getDeclarationAnnotationAdapter());
	}

	public static class AssociationOverridesAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final AssociationOverridesAnnotationDefinition INSTANCE = new AssociationOverridesAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private AssociationOverridesAnnotationDefinition() {
			super();
		}

		public AssociationOverrides buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new AssociationOverridesImpl(parent, member);
		}
		
		public AssociationOverrides buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
