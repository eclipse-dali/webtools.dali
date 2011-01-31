/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching;

import java.io.Serializable;

import org.eclipse.jpt.eclipselink.core.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 *  Entity
 */
public class Entity extends AbstractModel implements Cloneable, Serializable
{
	private String name;
	private Caching parent;

	public static final String CACHE_TYPE_PROPERTY = Caching.CACHE_TYPE_PROPERTY;
	public static final String CACHE_SIZE_PROPERTY = Caching.CACHE_SIZE_PROPERTY;
	public static final String SHARED_CACHE_PROPERTY = Caching.SHARED_CACHE_PROPERTY;

	// ********** EclipseLink properties **********
	private CacheType cacheType;
	private Integer cacheSize;
	private Boolean cacheIsShared;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public Entity(Caching parent, String name) {
		this(parent);
		this.initialize(name);
	}
	
	private Entity(Caching parent) {
		this.parent = parent;
	}
	
	private void initialize(String name) {
		if(StringTools.stringIsEmpty(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		Entity entity = (Entity) o;
		return (
			(this.cacheType == null ?
				entity.cacheType == null : this.cacheType.equals(entity.cacheType)) &&
			(this.cacheIsShared == null ?
				entity.cacheIsShared == null : this.cacheIsShared.equals(entity.cacheIsShared)) &&
			(this.cacheSize == null ?
				entity.cacheSize == null : this.cacheSize.equals(entity.cacheSize)));
	}
	
	 @Override
	 public Entity clone() {
		 try {
			 return (Entity)super.clone();
		 }
		 catch (CloneNotSupportedException ex) {
			 throw new InternalError();
		 }
	 }

	public boolean isEmpty() {
		return (this.cacheType == null) &&
					(this.cacheSize == null) &&
					(this.cacheIsShared == null);
	}
	
	public boolean entityNameIsValid() {
		return ! StringTools.stringIsEmpty(this.name);
	}

	public Caching getParent() {
		return this.parent;
	}
	
	// ********** name **********
	public String getName() {
		return this.name;
	}

	// ********** cacheType **********
	protected CacheType getCacheType() {
		return this.cacheType;
	}

	protected void setCacheType(CacheType cacheType) {
		CacheType old = this.cacheType;
		this.cacheType = cacheType;
		this.firePropertyChanged(CACHE_TYPE_PROPERTY, old, cacheType);
	}

	// ********** cacheSize **********
	protected Integer getCacheSize() {
		return this.cacheSize;
	}

	protected void setCacheSize(Integer cacheSize) {
		Integer old = this.cacheSize;
		this.cacheSize = cacheSize;
		this.firePropertyChanged(CACHE_SIZE_PROPERTY, old, cacheSize);
	}

	// ********** cacheIsShared **********
	protected Boolean cacheIsShared() {
		return this.cacheIsShared;
	}

	protected void setSharedCache(Boolean isShared) {
		Boolean old = this.cacheIsShared;
		this.cacheIsShared = isShared;
		this.firePropertyChanged(SHARED_CACHE_PROPERTY, old, isShared);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("name: "); //$NON-NLS-1$
		sb.append(this.name);
		sb.append(", cacheType: "); //$NON-NLS-1$
		sb.append(this.cacheType);
		sb.append(", cacheSize: "); //$NON-NLS-1$
		sb.append(this.cacheSize);
		sb.append(", cacheIsShared: "); //$NON-NLS-1$
		sb.append(this.cacheIsShared);
	}
}
