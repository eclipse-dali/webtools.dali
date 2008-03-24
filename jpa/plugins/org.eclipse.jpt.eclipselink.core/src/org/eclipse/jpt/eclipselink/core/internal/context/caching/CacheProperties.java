/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.caching;

import java.io.Serializable;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * CacheProperties
 */
public class CacheProperties implements Cloneable, Serializable
{
	private String entityName;

	private CacheType type;

	private Integer size;

	private Boolean isShared;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public CacheProperties(String entityName) {
		this.entityName = entityName;
	}

	// ********** behaviors **********
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		CacheProperties cache = (CacheProperties) o;
		return (
			(this.type == null ?
				cache.type == null : this.type.equals(cache.type)) &&
			(this.isShared == null ?
				cache.isShared == null : this.isShared.equals(cache.isShared)) &&
			(this.size == null ?
				cache.size == null : this.size.equals(cache.size)));
	}
	
	 @Override
	 public synchronized CacheProperties clone() {
		 try {
			 return (CacheProperties)super.clone();
		 }
		 catch (CloneNotSupportedException ex) {
			 throw new InternalError();
		 }
	 }

	// ********** getter/setter **********
	public String getEntityName() {
		return entityName;
	}

	public CacheType getType() {
		return type;
	}

	public void setType(CacheType cacheType) {
		this.type = cacheType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer cacheSize) {
		this.size = cacheSize;
	}

	public Boolean isShared() {
		return isShared;
	}

	public void setShared(Boolean isShared) {
		this.isShared = isShared;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	public void toString(StringBuilder sb) {
		sb.append("type: ");
		sb.append(this.type);
		sb.append(", size: ");
		sb.append(this.size);
		sb.append(", isShared: ");
		sb.append(this.isShared);
		sb.append(", entityName: ");
		sb.append(this.entityName);
	}
}