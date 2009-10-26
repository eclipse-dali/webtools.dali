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

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 *  GenericJavaOneToManyMapping2_0
 */
public class GenericJavaOneToManyMapping2_0
	extends GenericJavaOneToManyMapping<OneToMany2_0Annotation>
{
	protected Boolean specifiedOrphanRemoval = false;

	// ********** constructor **********
	public GenericJavaOneToManyMapping2_0(JavaPersistentAttribute parent) {
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
	
	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			JPA2_0.ONE_TO_MANY__ORPHAN_REMOVAL);
	}
	
	// ********** OrphanRemovable2_0 implementation **********

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
		((OneToMany2_0Annotation) this.mappingAnnotation).setOrphanRemoval(newOrphanRemoval);
		this.firePropertyChanged(OneToManyMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

	@Override
	public boolean isDefaultOrphanRemoval() {
		return OneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL;
	}
	
	protected Boolean getResourceOrphanRemoval() {
		return ((OneToMany2_0Annotation) this.mappingAnnotation).getOrphanRemoval();
	}

	protected void setSpecifiedOrphanRemoval_(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		firePropertyChanged(OneToManyMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

}