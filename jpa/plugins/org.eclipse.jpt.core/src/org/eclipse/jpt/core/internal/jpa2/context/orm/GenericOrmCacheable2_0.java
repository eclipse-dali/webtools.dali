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

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <code>orm.xml</code> cacheable
 */
public class GenericOrmCacheable2_0
	extends AbstractOrmXmlContextNode
	implements OrmCacheable2_0
{
	protected Boolean specifiedCacheable;
	protected boolean defaultCacheable;


	public GenericOrmCacheable2_0(OrmCacheableHolder2_0 parent) {
		super(parent);
		this.specifiedCacheable = this.getXmlCacheable().getCacheable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedCacheable_(this.getXmlCacheable().getCacheable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultCacheable(this.buildDefaultCacheable());
	}


	// ********** cacheable **********

	public boolean isCacheable() {
		return (this.specifiedCacheable != null) ? this.specifiedCacheable.booleanValue() : this.defaultCacheable;
	}

	public Boolean getSpecifiedCacheable() {
		return this.specifiedCacheable;
	}

	public void setSpecifiedCacheable(Boolean cacheable) {
		this.setSpecifiedCacheable_(cacheable);
		this.getXmlCacheable().setCacheable(cacheable);
	}

	protected void setSpecifiedCacheable_(Boolean cacheable) {
		Boolean old = this.specifiedCacheable;
		this.specifiedCacheable = cacheable;
		this.firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, old, cacheable);
	}

	public boolean isDefaultCacheable() {
		return this.defaultCacheable;
	}

	protected void setDefaultCacheable(boolean cacheable) {
		boolean old = this.defaultCacheable;
		this.defaultCacheable = cacheable;
		this.firePropertyChanged(DEFAULT_CACHEABLE_PROPERTY, old, cacheable);
	}

	protected boolean buildDefaultCacheable() {
		return this.getCacheableHolder().calculateDefaultCacheable();
	}


	// ********** misc **********

	@Override
	public OrmCacheableHolder2_0 getParent() {
		return (OrmCacheableHolder2_0) super.getParent();
	}

	protected OrmCacheableHolder2_0 getCacheableHolder() {
		return this.getParent();
	}

	protected XmlCacheable_2_0 getXmlCacheable() {
		return this.getCacheableHolder().getXmlCacheable();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlCacheable().getCacheableTextRange();
	}
}
