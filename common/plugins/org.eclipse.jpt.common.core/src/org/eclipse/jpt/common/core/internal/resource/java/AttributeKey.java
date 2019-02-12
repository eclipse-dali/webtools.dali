/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java;

/**
 * Value object that defines an attribute with its type and attribute names.
 */
public class AttributeKey {

	private final String typeName;

	private final String attributeName;


	public AttributeKey(String typeName, String attributeName) {
		super();
		if ((typeName == null) || (attributeName == null)) {
			throw new NullPointerException();
		}
		this.typeName = typeName;
		this.attributeName = attributeName;
	}


	@Override
	public boolean equals(Object obj) {
		if ( ! (obj instanceof AttributeKey)) {
			return false;
		}
		AttributeKey other = (AttributeKey) obj;
		return this.typeName.equals(other.typeName)
				&& this.attributeName.equals(other.attributeName);
	}

	@Override
	public int hashCode() {
		return this.typeName.hashCode() ^ this.attributeName.hashCode();
	}
}
