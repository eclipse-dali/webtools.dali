/*******************************************************************************
 * Copyright (c)2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.Entity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmCacheable2_0 extends AbstractOrmXmlContextNode 
	implements OrmCacheable2_0
{
	protected final XmlCacheable2_0 resource;
	
	protected boolean defaultCacheable;
	
	protected Boolean specifiedCacheable;
	
	
	public GenericOrmCacheable2_0(OrmTypeMapping parent, XmlCacheable2_0 resource) {
		super(parent);
		this.resource = resource;
		this.defaultCacheable = this.calculateDefaultCacheable();
		this.specifiedCacheable = this.getResourceCacheable();
	}

	public boolean isCacheable() {
		return (this.specifiedCacheable != null) ? this.specifiedCacheable.booleanValue() : this.defaultCacheable;
	}
	
	public boolean isDefaultCacheable() {
		return this.defaultCacheable;
	}
	
	protected void setDefaultCacheable(boolean newDefaultCacheable) {
		boolean oldDefaultCacheable = this.defaultCacheable;
		this.defaultCacheable = newDefaultCacheable;
		firePropertyChanged(DEFAULT_CACHEABLE_PROPERTY, oldDefaultCacheable, newDefaultCacheable);
	}
	
	public Boolean getSpecifiedCacheable() {
		return this.specifiedCacheable;
	}
	
	public void setSpecifiedCacheable(Boolean newSpecifiedCacheable) {
		Boolean oldSpecifiedCacheable = this.specifiedCacheable;
		this.specifiedCacheable = newSpecifiedCacheable;
		this.resource.setCacheable(newSpecifiedCacheable);
		firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, oldSpecifiedCacheable, newSpecifiedCacheable);
	}
	
	protected void setSpecifiedCacheable_(Boolean newSpecifiedCacheable) {
		Boolean oldSpecifiedCacheable = this.specifiedCacheable;
		this.specifiedCacheable = newSpecifiedCacheable;
		firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, oldSpecifiedCacheable, newSpecifiedCacheable);
	}
	
	
	// **************** initialize/update **************************************
		
	public void update() {
		setDefaultCacheable(this.calculateDefaultCacheable());
		setSpecifiedCacheable_(this.getResourceCacheable());
	}
	
	protected Boolean getResourceCacheable() {
		return this.resource.getCacheable();
	}
	
	protected boolean calculateDefaultCacheable() {
		return ((Entity2_0) getParent()).calculateDefaultCacheable();
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getCacheableTextRange();
	}
}
