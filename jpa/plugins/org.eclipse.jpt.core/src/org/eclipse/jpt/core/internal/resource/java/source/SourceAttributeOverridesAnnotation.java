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

import java.util.Vector;

import org.eclipse.jdt.core.dom.Annotation;
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
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

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

	public void synchronizeWith(CompilationUnit astRoot) {
		AnnotationContainerTools.synchronize(this, astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.attributesOverrides);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.ATTRIBUTE_OVERRIDES__VALUE;
	}

	public String getNestedAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableAttributeOverrideAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableAttributeOverrideAnnotation>(this.attributesOverrides);
	}

	public int getNestedAnnotationsSize() {
		return this.attributesOverrides.size();
	}

	public NestableAttributeOverrideAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.attributesOverrides.size());
	}

	private NestableAttributeOverrideAnnotation addNestedAnnotation(int index) {
		NestableAttributeOverrideAnnotation attributeOverride = this.buildAttributeOverride(index);
		this.attributesOverrides.add(attributeOverride);
		return attributeOverride;
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.attributesOverrides.size();
		NestableAttributeOverrideAnnotation attributeOverride = this.addNestedAnnotation(index);
		attributeOverride.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
	}

	private NestableAttributeOverrideAnnotation buildAttributeOverride(int index) {
		return SourceAttributeOverrideAnnotation.buildNestedAttributeOverride(this, this.annotatedElement, index, this.daa);
	}

	public NestableAttributeOverrideAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.attributesOverrides, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableAttributeOverrideAnnotation removeNestedAnnotation(int index) {
		return this.attributesOverrides.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.attributesOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}

}
