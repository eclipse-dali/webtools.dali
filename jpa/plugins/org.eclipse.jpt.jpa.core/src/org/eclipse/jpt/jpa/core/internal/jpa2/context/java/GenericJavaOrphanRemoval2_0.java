/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OwningRelationshipMappingAnnotation2_0;

/**
 * Java orphan removal
 */
public class GenericJavaOrphanRemoval2_0
		extends AbstractJavaContextModel<OrphanRemovalMapping2_0>
		implements OrphanRemovable2_0
{
	protected Boolean specifiedOrphanRemoval;
	protected boolean defaultOrphanRemoval;


	public GenericJavaOrphanRemoval2_0(OrphanRemovalMapping2_0 parent) {
		super(parent);
		this.specifiedOrphanRemoval = this.buildSpecifiedOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedOrphanRemoval_(this.buildSpecifiedOrphanRemoval());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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
		if (ObjectTools.notEquals(orphanRemoval, this.specifiedOrphanRemoval)) {
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
		OwningRelationshipMappingAnnotation2_0 annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getOrphanRemoval();
	}

	public boolean getDefaultOrphanRemoval() {
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

	protected JavaAttributeMapping getMapping() {
		return (JavaAttributeMapping) this.parent;
	}

	protected OwningRelationshipMappingAnnotation2_0 getMappingAnnotation() {
		return (OwningRelationshipMappingAnnotation2_0) this.getMapping().getMappingAnnotation();
	}

	protected OwningRelationshipMappingAnnotation2_0 getMappingAnnotationForUpdate() {
		return (OwningRelationshipMappingAnnotation2_0) this.getMapping().getAnnotationForUpdate();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		OwningRelationshipMappingAnnotation2_0 annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getOrphanRemovalTextRange();
	}
}
