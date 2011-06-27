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
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentProperty;

public class GenericJavaPersistentProperty
		extends GenericJavaPersistentAttribute
		implements JaxbPersistentProperty {


	protected final JavaResourceMethod resourceGetter;

	protected final JavaResourceMethod resourceSetter;

	public GenericJavaPersistentProperty(JaxbPersistentClass parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.resourceGetter = resourceGetter;
		this.resourceSetter = resourceSetter;
		this.initializeMapping();
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.calculateResourceMethodToAnnotate();
	}

	public String getJavaResourceAttributeTypeName() {
		JavaResourceAttribute getterMethod = getResourceGetterMethod();
		//it's invalid to have a setter without a getter, so just return null in this case
		//rather than attempting to define the type from the setter's parameters
		return getterMethod == null ? null : getJavaResourceAttributeType(getterMethod);
	}

	public boolean isJavaResourceAttributeTypeArray() {
		JavaResourceAttribute getterMethod = getResourceGetterMethod();
		//it's invalid to have a setter without a getter, so just return false in this case
		//rather than attempting to use the setter's parameters
		return getterMethod == null ? false : typeIsArray(getterMethod);
	}

	public boolean isJavaResourceAttributeTypeSubTypeOf(String typeName) {
		JavaResourceAttribute getterMethod = getResourceGetterMethod();
		//it's invalid to have a setter without a getter, so just return false in this case
		//rather than attempting to use the setter's parameters
		return getterMethod == null ? false : typeIsSubTypeOf(getterMethod, typeName);
	}

	public JavaResourceMethod getResourceGetterMethod() {
		return this.resourceGetter;
	}

	public JavaResourceMethod getResourceSetterMethod() {
		return this.resourceSetter;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return false;
	}

	public boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (this.resourceGetter == getterMethod) && (this.resourceSetter == setterMethod);
	}

	//since this is based on a preference as well as annotation location
	//we will just calculate it instead of handling it in sync and storing it.
	protected JavaResourceMethod calculateResourceMethodToAnnotate() {
		if (getterIsAnnotated()) {
			if (setterIsAnnotated()) {
				//use preference for which one to set the primary annotation on.
				return getAnnotateGetterPreference() ? this.resourceGetter : this.resourceSetter;
			}
			return this.resourceGetter;
		}
		else if (setterIsAnnotated()) {
			return this.resourceSetter;
		}
		else if (getAnnotateGetterPreference()&& this.resourceGetter != null) {
			return this.resourceGetter;
		}
		return this.resourceSetter;
	}

	protected boolean getterIsAnnotated() {
		return this.resourceGetter == null ? false : this.resourceGetter.isAnnotated(); 		
	}

	protected boolean setterIsAnnotated() {
		return this.resourceSetter == null ? false : this.resourceSetter.isAnnotated(); 
	}

	//TODO bug 333483 - make this a preference for the user to select whether the getter or setter should be annotated.
	protected boolean getAnnotateGetterPreference() {
		return true;
	}

	//TODO validation - bug 333484
	//if (getterIsAnnotated() && setterIsAnnotated()) error to user
}