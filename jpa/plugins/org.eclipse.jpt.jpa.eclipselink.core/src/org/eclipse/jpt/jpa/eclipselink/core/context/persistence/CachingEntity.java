/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 *  Entity
 */
public class CachingEntity extends AbstractModel implements Cloneable, Serializable
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
	public CachingEntity(Caching parent, String name) {
		this(parent);
		this.initialize(name);
	}
	
	private CachingEntity(Caching parent) {
		this.parent = parent;
	}
	
	private void initialize(String name) {
		if(StringTools.isBlank(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof CachingEntity)) {
			return false;
		}
		CachingEntity other = (CachingEntity) o;
		return (
			(this.cacheType == null ?
				other.cacheType == null : this.cacheType.equals(other.cacheType)) &&
			(this.cacheIsShared == null ?
				other.cacheIsShared == null : this.cacheIsShared.equals(other.cacheIsShared)) &&
			(this.cacheSize == null ?
				other.cacheSize == null : this.cacheSize.equals(other.cacheSize)));
	}

	@Override
	public int hashCode() {
		return (this.cacheType == null) ? 0 : this.cacheType.hashCode();
	}
	
	 @Override
	 public CachingEntity clone() {
		 try {
			 return (CachingEntity)super.clone();
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
		return ! StringTools.isBlank(this.name);
	}

	public Caching getParent() {
		return this.parent;
	}
	
	// ********** name **********
	public String getName() {
		return this.name;
	}
	public static final Transformer<CachingEntity, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<CachingEntity, String>
	{
		@Override
		public String transform(CachingEntity entity) {
			return entity.getName();
		}
	}

	// ********** cacheType **********
	public CacheType getCacheType() {
		return this.cacheType;
	}

	public void setCacheType(CacheType cacheType) {
		CacheType old = this.cacheType;
		this.cacheType = cacheType;
		this.firePropertyChanged(CACHE_TYPE_PROPERTY, old, cacheType);
	}

	// ********** cacheSize **********
	public Integer getCacheSize() {
		return this.cacheSize;
	}

	public void setCacheSize(Integer cacheSize) {
		Integer old = this.cacheSize;
		this.cacheSize = cacheSize;
		this.firePropertyChanged(CACHE_SIZE_PROPERTY, old, cacheSize);
	}

	// ********** cacheIsShared **********
	public Boolean cacheIsShared() {
		return this.cacheIsShared;
	}

	public void setSharedCache(Boolean isShared) {
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
