/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyMapping2_0;

/**
 *  JavaEclipseLinkOneToManyMapping2_0
 */
public class JavaEclipseLinkOneToManyMapping2_0
	extends JavaEclipseLinkOneToManyMapping
	implements EclipseLinkOneToManyMapping2_0
{
	private Boolean specifiedOrphanRemoval;
	
	// ********** constructor **********
	public JavaEclipseLinkOneToManyMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.specifiedOrphanRemoval = this.getResourceOrphanRemoval();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setSpecifiedOrphanRemoval_(this.getResourceOrphanRemoval());
	}
	
	// ********** JPA 2.0 - OrphanRemovable2_0 implementation **********

	@Override
	public boolean isOrphanRemoval() {
		return (this.specifiedOrphanRemoval != null) ? this.specifiedOrphanRemoval.booleanValue() : this.isDefaultOrphanRemoval();
	}

	@Override
	public Boolean getSpecifiedOrphanRemoval() {
		return this.specifiedOrphanRemoval;
	}

	@Override
	public void setSpecifiedOrphanRemoval(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		this.setResourceOrphanRemoval(newOrphanRemoval);
		this.firePropertyChanged(OneToManyMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

	@Override
	public boolean isDefaultOrphanRemoval() {
		return OneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL;
	}

	protected Boolean getResourceOrphanRemoval() {
		return ((OneToMany2_0Annotation) this.mappingAnnotation).getOrphanRemoval();
	}
	
	protected void setResourceOrphanRemoval(Boolean newOrphanRemoval) {
		((OneToMany2_0Annotation) this.mappingAnnotation).setOrphanRemoval(newOrphanRemoval);
	}

	protected void setSpecifiedOrphanRemoval_(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		firePropertyChanged(OneToManyMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

}
