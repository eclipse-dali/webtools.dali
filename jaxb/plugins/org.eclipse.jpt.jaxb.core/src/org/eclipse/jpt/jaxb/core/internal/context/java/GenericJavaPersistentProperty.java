/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentProperty;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;

public class GenericJavaPersistentProperty
		extends GenericJavaPersistentAttribute
		implements JaxbPersistentProperty {


	protected final JavaResourceMethod resourceGetter;

	protected final JavaResourceMethod resourceSetter;

	public GenericJavaPersistentProperty(JaxbPersistentClass parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.resourceGetter = resourceGetter;
		this.resourceSetter = resourceSetter;
	}

	public JavaResourceMethod getResourceGetterMethod() {
		return this.resourceGetter;
	}

	public JavaResourceMethod getResourceSetterMethod() {
		return this.resourceSetter;
	}

	public String getName() {
		if (this.resourceGetter != null) {
			return this.resourceGetter.getName();
		}
		return this.resourceSetter.getName();
	}


	public boolean isFor(JavaResourceField resourceField) {
		return false;
	}

	public boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (this.resourceGetter == getterMethod) && (this.resourceSetter == setterMethod);
	}

}
