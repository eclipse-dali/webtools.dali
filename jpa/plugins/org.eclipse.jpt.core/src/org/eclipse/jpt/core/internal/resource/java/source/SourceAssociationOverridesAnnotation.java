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
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

/**
 * <code>javax.persistence.AssociationOverrides</code>
 */
public abstract class SourceAssociationOverridesAnnotation
	extends SourceAnnotation<Member>
	implements AssociationOverridesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableAssociationOverrideAnnotation> associationOverrides = new Vector<NestableAssociationOverrideAnnotation>();


	protected SourceAssociationOverridesAnnotation(JavaResourceNode parent, Member member) {
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
				this.associationOverrides.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.associationOverrides);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.ASSOCIATION_OVERRIDES__VALUE;
	}

	public String getNestedAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableAssociationOverrideAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableAssociationOverrideAnnotation>(this.associationOverrides);
	}

	public int getNestedAnnotationsSize() {
		return this.associationOverrides.size();
	}

	public void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.nestStandAloneAnnotation(standAloneAnnotation, this.associationOverrides.size());
	}

	private void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation, int index) {
		standAloneAnnotation.convertToNested(this, this.daa, index);
	}

	public void addNestedAnnotation(int index, NestableAnnotation annotation) {
		this.associationOverrides.add(index, (NestableAssociationOverrideAnnotation) annotation);
	}

	public void convertLastNestedAnnotationToStandAlone() {
		this.associationOverrides.remove(0).convertToStandAlone();
	}

	public NestableAssociationOverrideAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.associationOverrides.size());
	}

	private NestableAssociationOverrideAnnotation addNestedAnnotation(int index) {
		NestableAssociationOverrideAnnotation associationOverride = this.buildAssociationOverride(index);
		this.associationOverrides.add(index, associationOverride);
		return associationOverride;
	}

	protected abstract NestableAssociationOverrideAnnotation buildAssociationOverride(int index);

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.associationOverrides.size();
		NestableAssociationOverrideAnnotation associationOverride = this.addNestedAnnotation(index);
		associationOverride.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
	}

	public NestableAssociationOverrideAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.associationOverrides, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableAssociationOverrideAnnotation removeNestedAnnotation(int index) {
		return this.associationOverrides.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.associationOverrides, ASSOCIATION_OVERRIDES_LIST);
	}
}
