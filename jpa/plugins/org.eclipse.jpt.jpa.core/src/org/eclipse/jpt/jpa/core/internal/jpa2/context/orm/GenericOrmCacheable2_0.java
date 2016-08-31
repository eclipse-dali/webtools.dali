/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;

/**
 * <code>orm.xml</code> cacheable
 */
public class GenericOrmCacheable2_0
	extends AbstractOrmXmlContextModel<OrmCacheableReference2_0>
	implements Cacheable2_0
{
	protected Boolean specifiedCacheable;
	protected boolean defaultCacheable;
	protected boolean cacheable;


	public GenericOrmCacheable2_0(OrmCacheableReference2_0 parent) {
		super(parent);
		this.specifiedCacheable = this.getXmlCacheable().getCacheable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedCacheable_(this.getXmlCacheable().getCacheable());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultCacheable(this.buildDefaultCacheable());
		this.setCacheable(this.buildCacheable());
	}


	// ********** cacheable **********

	public boolean isCacheable() {
		return this.cacheable;
	}

	protected void setCacheable(boolean cacheable) {
		boolean old = this.cacheable;
		this.firePropertyChanged(CACHEABLE_PROPERTY, old, this.cacheable = cacheable);
	}

	protected boolean buildCacheable() {
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

	public boolean getDefaultCacheable() {
		return this.defaultCacheable;
	}

	protected void setDefaultCacheable(boolean cacheable) {
		boolean old = this.defaultCacheable;
		this.firePropertyChanged(DEFAULT_CACHEABLE_PROPERTY, old, this.defaultCacheable = cacheable);
	}

	protected boolean buildDefaultCacheable() {
		return this.getCacheableReference().calculateDefaultCacheable();
	}


	// ********** misc **********

	protected OrmCacheableReference2_0 getCacheableReference() {
		return this.parent;
	}

	protected XmlCacheable_2_0 getXmlCacheable() {
		return this.getCacheableReference().getXmlCacheable();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlCacheable().getCacheableTextRange();
		return (textRange != null) ? textRange : this.getCacheableReference().getValidationTextRange();
	}
}
