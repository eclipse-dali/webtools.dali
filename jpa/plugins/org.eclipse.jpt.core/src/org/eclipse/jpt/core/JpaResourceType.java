/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 * Describes the file content type and version for JPA resources.
 * This is a value object that defines an {@link #equals(Object)} method.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JpaResourceType
{
	private final IContentType contentType;

	private final String version;


	/**
	 * Version used when version can not be determined or when there is no
	 * sense of version (e.g. Java).
	 */
	public static final String UNDETERMINED_VERSION = "<undetermined>"; //$NON-NLS-1$


	public JpaResourceType(IContentType contentType) {
		this(contentType, UNDETERMINED_VERSION);
	}

	public JpaResourceType(IContentType contentType, String version) {
		super();
		if (contentType == null) {
			throw new NullPointerException("content type"); //$NON-NLS-1$
		}
		if (version == null) {
			throw new NullPointerException("version"); //$NON-NLS-1$
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
		if (obj == this) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		JpaResourceType other = (JpaResourceType) obj;
		return this.contentType.equals(other.contentType) && this.version.equals(other.version);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.contentType.hashCode();
		hash = hash * prime + this.version.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.toStringClassNameForObject(this));
		sb.append("(content = "); //$NON-NLS-1$
		sb.append(this.contentType);
		sb.append(", "); //$NON-NLS-1$
		sb.append("version = "); //$NON-NLS-1$
		sb.append(this.version);
		sb.append(')');
		return sb.toString();
	}
}
