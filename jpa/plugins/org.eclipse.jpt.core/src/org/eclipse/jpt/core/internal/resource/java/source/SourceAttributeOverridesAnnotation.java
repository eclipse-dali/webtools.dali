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
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAttributeOverrideAnnotation;

/**
 * <code>javax.persistence.AttributeOverrides</code>
 */
public final class SourceAttributeOverridesAnnotation
	extends SourceAnnotation<Member>
	implements AttributeOverridesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableAttributeOverrideAnnotation> attributeOverrides = new Vector<NestableAttributeOverrideAnnotation>();


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
	public boolean isUnset() {
		return super.isUnset() &&
				this.attributeOverrides.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.attributeOverrides);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.ATTRIBUTE_OVERRIDES__VALUE;
	}

	public String getNestedAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableAttributeOverrideAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableAttributeOverrideAnnotation>(this.attributeOverrides);
	}

	public int getNestedAnnotationsSize() {
		return this.attributeOverrides.size();
	}

	public void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.nestStandAloneAnnotation(standAloneAnnotation, this.attributeOverrides.size());
	}

	private void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation, int index) {
		standAloneAnnotation.convertToNested(this, this.daa, index);
	}

	public void addNestedAnnotation(int index, NestableAnnotation annotation) {
		this.attributeOverrides.add(index, (NestableAttributeOverrideAnnotation) annotation);
	}

	public void convertLastNestedAnnotationToStandAlone() {
		this.attributeOverrides.remove(0).convertToStandAlone();
	}

	public NestableAttributeOverrideAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.attributeOverrides.size());
	}

	private NestableAttributeOverrideAnnotation addNestedAnnotation(int index) {
		NestableAttributeOverrideAnnotation attributeOverride = this.buildAttributeOverride(index);
		this.attributeOverrides.add(index, attributeOverride);
		return attributeOverride;
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.attributeOverrides.size();
		NestableAttributeOverrideAnnotation attributeOverride = this.addNestedAnnotation(index);
		attributeOverride.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
	}

	private NestableAttributeOverrideAnnotation buildAttributeOverride(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourceAttributeOverrideAnnotation.buildNestedAttributeOverride(this.parent, this.annotatedElement, index, this.daa);
	}

	public NestableAttributeOverrideAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.attributeOverrides, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableAttributeOverrideAnnotation removeNestedAnnotation(int index) {
		return this.attributeOverrides.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.attributeOverrides, ATTRIBUTE_OVERRIDES_LIST);
	}
}
