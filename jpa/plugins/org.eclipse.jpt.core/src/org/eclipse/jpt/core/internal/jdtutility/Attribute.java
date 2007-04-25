/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.IMember;

/**
 * Combine behavior common to FieldAttribute and MethodAttribute.
 */
public abstract class Attribute extends Member {

	Attribute(IMember jdtMember) {
		super(jdtMember);
	}

	public boolean isField() {
		return false;
	}

	public boolean isMethod() {
		return false;
	}

	public abstract String attributeName();

	public abstract String typeSignature();

	public boolean isPrimitiveType() {
		return JDTTools.signatureIsPrimitive(this.typeSignature());
	}

	public boolean typeIs(String fullyQualifiedTypeName) {
		return fullyQualifiedTypeName.equals(this.resolvedTypeName());
	}

	/**
	 * Resolve the attribute.
	 * Return the fully-qualified type name or return null if it cannot be
	 * resolved unambiguously.
	 */
	public String resolvedTypeName() {
		return JDTTools.resolveSignature(this.typeSignature(), this.getJdtMember().getDeclaringType());
	}

}
