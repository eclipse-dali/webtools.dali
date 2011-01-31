/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;

/**
 * <code>orm.xml</code> orphan removal
 */
public class GenericOrmOrphanRemoval2_0
	extends AbstractOrmXmlContextNode
	implements OrmOrphanRemovable2_0
{
	protected Boolean specifiedOrphanRemoval;
	protected boolean defaultOrphanRemoval;


	public GenericOrmOrphanRemoval2_0(OrmOrphanRemovalHolder2_0 parent) {
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
		this.setSpecifiedOrphanRemoval_(orphanRemoval);
		this.getXmlOrphanRemovable().setOrphanRemoval(orphanRemoval);
	}

	protected void setSpecifiedOrphanRemoval_(Boolean orphanRemoval) {
		Boolean old = this.specifiedOrphanRemoval;
		this.specifiedOrphanRemoval = orphanRemoval;
		this.firePropertyChanged(SPECIFIED_ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}

	protected Boolean buildSpecifiedOrphanRemoval() {
		return this.getXmlOrphanRemovable().getOrphanRemoval();
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
	public OrmOrphanRemovalHolder2_0 getParent() {
		return (OrmOrphanRemovalHolder2_0) super.getParent();
	}

	protected OrmAttributeMapping getMapping() {
		return (OrmAttributeMapping) this.getParent();
	}

	protected XmlAttributeMapping getXmlMapping() {
		return this.getMapping().getXmlAttributeMapping();
	}

	protected XmlOrphanRemovable_2_0 getXmlOrphanRemovable() {
		return (XmlOrphanRemovable_2_0) this.getXmlMapping();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		// TODO
		return this.getXmlOrphanRemovable().getValidationTextRange();
	}
}
