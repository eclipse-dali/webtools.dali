/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OrphanRemovable2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  GenericJavaOrphanRemoval2_0
 */
public class GenericJavaOrphanRemoval2_0
		extends AbstractJavaJpaContextNode 
		implements JavaOrphanRemovable2_0
{
	protected boolean defaultOrphanRemoval;
	protected Boolean specifiedOrphanRemoval;
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public GenericJavaOrphanRemoval2_0(JavaOrphanRemovalHolder2_0 parent) {
		super(parent);
	}
	
	@Override
	public JavaOrphanRemovalHolder2_0 getParent() {
		return (JavaOrphanRemovalHolder2_0) super.getParent();
	}
	
	protected String getAnnotationName() {
		return ((JavaAttributeMapping)getParent()).getAnnotationName();
	}
	
	protected OrphanRemovable2_0Annotation getResourceOrphanRemovable() {
		return (OrphanRemovable2_0Annotation) this.resourcePersistentAttribute.getAnnotation(this.getAnnotationName());
	}
	
	// ********** OrphanRemovable2_0 implementation **********

	public boolean isOrphanRemoval() {
		return this.specifiedOrphanRemoval != null ? this.specifiedOrphanRemoval.booleanValue() : this.defaultOrphanRemoval; 
	}
	
	public Boolean getSpecifiedOrphanRemoval() {
		return this.specifiedOrphanRemoval;
	}
	
	public boolean isDefaultOrphanRemoval() {
		return this.defaultOrphanRemoval;
	}
	
	protected void setDefaultOrphanRemoval(boolean newDefaultOrphanRemoval) {
		boolean old = this.defaultOrphanRemoval;
		this.defaultOrphanRemoval = newDefaultOrphanRemoval;
		this.firePropertyChanged(DEFAULT_ORPHAN_REMOVAL_PROPERTY, old, newDefaultOrphanRemoval);
	}
	
	public void setSpecifiedOrphanRemoval(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		this.getResourceOrphanRemovable().setOrphanRemoval(newOrphanRemoval);
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}
	
	protected void setSpecifiedOrphanRemoval_(Boolean newSpecifiedOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newSpecifiedOrphanRemoval;
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newSpecifiedOrphanRemoval);
	}

	// ********** initialize/update **********
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		OrphanRemovable2_0Annotation resource = this.getResourceOrphanRemovable();
		this.specifiedOrphanRemoval = this.getSpecifiedOrphanRemovalFrom(resource);
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		OrphanRemovable2_0Annotation resource = this.getResourceOrphanRemovable();
		this.setSpecifiedOrphanRemoval_(this.getSpecifiedOrphanRemovalFrom(resource));
	}
	
	private Boolean getSpecifiedOrphanRemovalFrom(OrphanRemovable2_0Annotation resource) {
		return (resource == null) ? null : resource.getOrphanRemoval();
	}

	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		// TODO
		return null;
	}
	

}