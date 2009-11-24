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

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  GenericOrmOrphanRemoval2_0
 */
public class GenericOrmOrphanRemoval2_0 extends AbstractOrmXmlContextNode 
	implements OrmOrphanRemovable2_0
{
	protected final XmlOrphanRemovable_2_0 resource;
	protected boolean defaultOrphanRemoval;
	protected Boolean specifiedOrphanRemoval;

	// ********** constructor **********
	public GenericOrmOrphanRemoval2_0(OrmOrphanRemovalHolder2_0 parent, XmlOrphanRemovable_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.specifiedOrphanRemoval = this.getResourceOrphanRemoval();
	}

	@Override
	public OrmOrphanRemovalHolder2_0 getParent() {
		return (OrmOrphanRemovalHolder2_0) super.getParent();
	}

	// ********** OrphanRemovable2_0 implementation **********

	public boolean isOrphanRemoval() {
		return (this.specifiedOrphanRemoval != null) ? this.specifiedOrphanRemoval.booleanValue() : this.defaultOrphanRemoval;
	}

	public Boolean getSpecifiedOrphanRemoval() {
		return this.specifiedOrphanRemoval;
	}

	public boolean isDefaultOrphanRemoval() {
		return this.defaultOrphanRemoval;
	}

	public void setSpecifiedOrphanRemoval(Boolean newSpecifiedOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newSpecifiedOrphanRemoval;
		this.resource.setOrphanRemoval(newSpecifiedOrphanRemoval);
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newSpecifiedOrphanRemoval);
	}
	
	protected void setSpecifiedOrphanRemoval_(Boolean newSpecifiedOrphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = newSpecifiedOrphanRemoval;
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, newSpecifiedOrphanRemoval);
	}

	// ********** initialize/update **********
	public void update() {
		this.setSpecifiedOrphanRemoval_(this.getResourceOrphanRemoval());
	}

	// ********** validation **********
	public TextRange getValidationTextRange() {
		// TODO
		return null;
	}
	
	protected Boolean getResourceOrphanRemoval() {
		return this.resource.getOrphanRemoval();
	}
	
}