/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentField;

public class GenericJavaPersistentField
		extends GenericJavaPersistentAttribute
		implements JaxbPersistentField {


	protected final JavaResourceField resourceField;

	public GenericJavaPersistentField(JaxbPersistentClass parent, JavaResourceField resourceField) {
		super(parent);
		this.resourceField = resourceField;
		this.initializeMapping();
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.getResourceField();
	}

	public String getJavaResourceAttributeTypeName() {
		return getJavaResourceAttributeType(this.getJavaResourceAttribute());
	}

	public boolean isJavaResourceAttributeTypeArray() {
		return typeIsArray(this.getJavaResourceAttribute());
	}

	public boolean isJavaResourceAttributeTypeSubTypeOf(String typeName) {
		return typeIsSubTypeOf(this.getJavaResourceAttribute(), typeName);
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

}
