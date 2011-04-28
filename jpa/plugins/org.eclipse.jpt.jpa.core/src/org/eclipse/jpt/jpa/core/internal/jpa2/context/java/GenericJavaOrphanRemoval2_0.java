/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OwningRelationshipMapping2_0Annotation;

/**
 * Java orphan removal
 */
public class GenericJavaOrphanRemoval2_0
		extends AbstractJavaJpaContextNode
		implements JavaOrphanRemovable2_0
{
	protected Boolean specifiedOrphanRemoval;
	protected boolean defaultOrphanRemoval;


	public GenericJavaOrphanRemoval2_0(JavaOrphanRemovalHolder2_0 parent) {
		super(parent);
		this.specifiedOrphanRemoval = this.buildSpecifiedOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedOrphanRemoval_(this.buildSpecifiedOrphanRemoval());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultOrphanRemoval(this.buildDefaultOrphanRemoval());
	}


	// ********** orphan removal **********

	public boolean isOrphanRemoval() {
		return (this.specifiedOrphanRemoval != null) ? this.specifiedOrphanRemoval.booleanValue() : this.defaultOrphanRemoval;
	}

	public Boolean getSpecifiedOrphanRemoval() {
		return this.specifiedOrphanRemoval;
	}

	public void setSpecifiedOrphanRemoval(Boolean orphanRemoval) {
		if (this.valuesAreDifferent(orphanRemoval, this.specifiedOrphanRemoval)) {
			this.getMappingAnnotationForUpdate().setOrphanRemoval(orphanRemoval);
			this.setSpecifiedOrphanRemoval_(orphanRemoval);
		}
	}

	protected void setSpecifiedOrphanRemoval_(Boolean orphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = orphanRemoval;
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}

	protected Boolean buildSpecifiedOrphanRemoval() {
		OwningRelationshipMapping2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getOrphanRemoval();
	}

	public boolean isDefaultOrphanRemoval() {
		return this.defaultOrphanRemoval;
	}

	protected void setDefaultOrphanRemoval(boolean orphanRemoval) {
		boolean old = this.defaultOrphanRemoval;
		this.defaultOrphanRemoval = orphanRemoval;
		this.firePropertyChanged(DEFAULT_ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}

	protected boolean buildDefaultOrphanRemoval() {
		return DEFAULT_ORPHAN_REMOVAL;
	}


	// ********** misc **********

	@Override
	public JavaOrphanRemovalHolder2_0 getParent() {
		return (JavaOrphanRemovalHolder2_0) super.getParent();
	}

	protected JavaAttributeMapping getMapping() {
		return (JavaAttributeMapping) this.getParent();
	}

	protected OwningRelationshipMapping2_0Annotation getMappingAnnotation() {
		return (OwningRelationshipMapping2_0Annotation) this.getMapping().getMappingAnnotation();
	}

	protected OwningRelationshipMapping2_0Annotation getMappingAnnotationForUpdate() {
		return (OwningRelationshipMapping2_0Annotation) this.getMapping().getAnnotationForUpdate();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getMapping().getValidationTextRange(astRoot);
	}

	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		OwningRelationshipMapping2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getOrphanRemovalTextRange(astRoot);
	}
}
