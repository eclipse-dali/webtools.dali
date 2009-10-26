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
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 *  GenericJavaOneToOneMapping2_0
 */
public class GenericJavaOneToOneMapping2_0
	extends GenericJavaOneToOneMapping
{
	private Boolean specifiedOrphanRemoval = false;
	
	// ********** constructor **********
	public GenericJavaOneToOneMapping2_0(JavaPersistentAttribute parent) {
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
			JPA2_0.ONE_TO_ONE__ORPHAN_REMOVAL);
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
		((OneToOne2_0Annotation) this.mappingAnnotation).setOrphanRemoval(newOrphanRemoval);
		this.firePropertyChanged(OneToOneMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

	@Override
	public boolean isDefaultOrphanRemoval() {
		return OneToOneMapping2_0.DEFAULT_ORPHAN_REMOVAL;
	}
	
	protected Boolean getResourceOrphanRemoval() {
		return ((OneToOne2_0Annotation) this.mappingAnnotation).getOrphanRemoval();
	}

	protected void setSpecifiedOrphanRemoval_(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		firePropertyChanged(OneToOneMapping2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}

}