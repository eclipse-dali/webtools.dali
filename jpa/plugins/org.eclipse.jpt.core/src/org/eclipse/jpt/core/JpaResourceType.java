/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 * Describes the file content type and version for JPA resources
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JpaResourceType
{
	/**
	 * Version to be used when version can not be determined, or when there is no
	 * sense of version (e.g. java)
	 */
	public static final String UNDETERMINED_VERSION = "undetermined version";
	
	
	protected IContentType contentType;
	
	protected String version;
	
	
	public JpaResourceType(IContentType contentType) {
		this(contentType, UNDETERMINED_VERSION);
	}
	
	public JpaResourceType(IContentType contentType, String version) {
		if (contentType == null) {
			throw new IllegalArgumentException("Content type may not be null.");
		}
		if (version == null) {
			throw new IllegalArgumentException("Version may not be null.");
		}
		this.contentType = contentType;
		this.version = version;
	}
	
	
	public IContentType getContentType() {
		return this.contentType;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		JpaResourceType other = (JpaResourceType) obj;
		return this.contentType.equals(other.contentType) && this.version.equals(other.version);
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 31 * hash + this.contentType.hashCode();
		hash = 31 * hash + this.version.hashCode();
		return hash;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.toStringClassNameForObject(this));
		sb.append('(');
		sb.append("content = " + this.contentType.toString() + ',');
		sb.append("version = " + this.version);
		sb.append(')');
		return sb.toString();
	}
}
