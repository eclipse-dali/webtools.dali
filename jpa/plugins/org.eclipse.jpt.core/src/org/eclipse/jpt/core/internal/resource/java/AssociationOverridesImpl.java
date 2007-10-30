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

public class AssociationOverridesImpl extends AbstractAnnotationResource<Member> implements AssociationOverrides
{
	private static final String ANNOTATION_NAME = JPA.ASSOCIATION_OVERRIDES;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private List<NestableAssociationOverride> associationOverrides;
	
	protected AssociationOverridesImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.associationOverrides = new ArrayList<NestableAssociationOverride>();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getNestableAnnotationName() {
		return JPA.ASSOCIATION_OVERRIDE;
	}
		
	public ListIterator<NestableAssociationOverride> nestedAnnotations() {
		return new CloneListIterator<NestableAssociationOverride>(this.associationOverrides);
	}
	
	public int nestedAnnotationsSize() {
		return this.associationOverrides.size();
	}	

	public NestableAssociationOverride add(int index) {
		NestableAssociationOverride associationOverride = createAssociationOverride(index);
		add(index, associationOverride);
		return associationOverride;
	}
	
	private void add(int index, NestableAssociationOverride associationOverride) {
		this.associationOverrides.add(index, associationOverride);
		//TODO event notification
	}

	public void remove(NestableAssociationOverride associationOverride) {
		this.associationOverrides.remove(associationOverride);		
	}
	
	public void remove(int index) {
		this.associationOverrides.remove(index);
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
	
	public void move(int oldIndex, int newIndex) {
		this.associationOverrides.add(newIndex, this.associationOverrides.remove(oldIndex));
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

		public AssociationOverrides buildAnnotation(JavaResource parent, Member member) {
			return new AssociationOverridesImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
