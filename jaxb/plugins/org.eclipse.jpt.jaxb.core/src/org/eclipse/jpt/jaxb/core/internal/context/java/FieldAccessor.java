/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.Accessor;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;

public class FieldAccessor
		extends AbstractJavaContextNode
		implements Accessor {
	
	protected final JavaResourceField resourceField;
	
	
	public FieldAccessor(JaxbClassMapping parent, JavaResourceField resourceField) {
		super(parent);
		this.resourceField = resourceField;
	}
	
	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.getResourceField();
	}
	
	public String getJavaResourceAttributeBaseTypeName() {
		return AccessorTools.getBaseTypeName(this.getJavaResourceAttribute());
	}
	
	public boolean isJavaResourceAttributeCollectionType() {
		return AccessorTools.isCollectionType(getJavaResourceAttribute());
	}
	
	public boolean isJavaResourceAttributeTypeSubTypeOf(String typeName) {
		return this.getJavaResourceAttribute().getTypeBinding().isSubTypeOf(typeName);
	}

	public JavaResourceField getResourceField() {
		return this.resourceField;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return this.resourceField == resourceField;
	}

	public boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return false;
	}

	@Override
	public TextRange getValidationTextRange() {
		return null;
	}

}
