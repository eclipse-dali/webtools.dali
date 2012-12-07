/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJaxbType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;

public abstract class AbstractOxmJaxbType<T extends JavaType>
		extends AbstractJaxbContextNode
		implements OxmJaxbType<T> {
	
	protected String qualifiedName;
	
	// hold on to java resource type for validation purposes
	protected JavaResourceType javaResourceType;
	protected T javaType;
	
	
	public AbstractOxmJaxbType(OxmXmlBindings parent) {
		super(parent);
		// 'javaType' is resolved in the update
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncJavaType();
	}
	
	@Override
	public void update() {
		super.update();
		updateJavaType();
	}
	
	
	// ***** qualified name *****
	
	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	protected void setQualifiedName_(String newName) {
		String oldName = this.qualifiedName;
		this.qualifiedName = newName;
		if (firePropertyChanged(QUALIFIED_NAME_PROPERTY, oldName, newName)) {
			// If the qualified name changes, null out the java type to avoid syncing *it*.
			// That's just wasted cycles.
			// It'll get reset in update.
			setJavaType(null);
		}
	}
	
	protected abstract String buildQualifiedName();
	
	public String getSimpleName() {
		return TypeDeclarationTools.simpleName(this.qualifiedName);
	}
	
	
	// ***** java type *****
	
	public T getJavaType() {
		return this.javaType;
	}
	
	protected void setJavaType(T javaType) {
		T oldJavaType = this.javaType;
		this.javaType = javaType;
		firePropertyChanged(JAVA_TYPE_PROPERTY, oldJavaType, javaType);
	}
	
	protected void syncJavaType() {
		if (this.javaType != null) {
			this.javaType.synchronizeWithResourceModel();
		}
	}
	
	protected void updateJavaType() {
		if (StringTools.isBlank(getQualifiedName())) {
			setJavaType(null);
		}
		else {
			JavaResourceType resourceType = this.resolveJavaResourceType();
			if (resourceType != null) {
				if (this.javaType == null 
						// using == here because it is possible that the names are the same, but the location has changed
						// (e.g. the java resource type has moved from binary to source, or vice versa)
						|| this.javaType.getJavaResourceType() != resourceType) {
					setJavaType(buildJavaType(resourceType));
				}
				else if (this.javaType != null) {
					this.javaType.update();
				}
			}
			else {
				setJavaType(null);
			}
		}
	}
	
	protected abstract T buildJavaType(JavaResourceType resourceType);
	
	protected JavaResourceType resolveJavaResourceType() {
		if (StringTools.isBlank(getQualifiedName())) {
			return null;
		}
		// return type whether it's a class, interface, or enum. 
		// building javaType will resolve problem if there is one.
		return (JavaResourceType) getJaxbProject().getJavaResourceType(getQualifiedName());
	}
}
