/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;

/**
 *  GenericOrmOneToManyMapping2_0
 */
public class GenericOrmOneToManyMapping2_0
	extends GenericOrmOneToManyMapping
{
	protected Boolean specifiedOrphanRemoval;
	
	// ********** constructor **********
	public GenericOrmOneToManyMapping2_0(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		super(parent, resourceMapping);
		
		this.specifiedOrphanRemoval = this.getResourceOrphanRemoval();
	}
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedOrphanRemoval_(this.getResourceOrphanRemoval());
	}
	
	protected Boolean getResourceOrphanRemoval() {
		return this.resourceAttributeMapping.getOrphanRemoval();
	}
	
	// ********** OrphanRemovable2_0 implementation **********

	@Override
	public boolean isOrphanRemoval() {
		return (this.getSpecifiedOrphanRemoval() == null) ? this.isDefaultOrphanRemoval() : this.getSpecifiedOrphanRemoval().booleanValue();
	}

	@Override
	public boolean isDefaultOrphanRemoval() {
		return OrphanRemovable2_0.DEFAULT_ORPHAN_REMOVAL;
	}

	@Override
	public Boolean getSpecifiedOrphanRemoval() {
		return this.specifiedOrphanRemoval;
	}

	@Override
	public void setSpecifiedOrphanRemoval(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		this.resourceAttributeMapping.setOrphanRemoval(newOrphanRemoval);
		firePropertyChanged(OrphanRemovable2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}
	
	protected void setSpecifiedOrphanRemoval_(Boolean newOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newOrphanRemoval;
		firePropertyChanged(OrphanRemovable2_0.SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newOrphanRemoval);
	}
	
}
