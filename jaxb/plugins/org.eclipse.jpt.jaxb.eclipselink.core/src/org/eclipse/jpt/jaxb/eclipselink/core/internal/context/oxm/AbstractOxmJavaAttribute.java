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

import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;

public abstract class AbstractOxmJavaAttribute<A extends EJavaAttribute>
		extends AbstractJaxbContextNode
		implements OxmJavaAttribute<A> {
	
	protected A eJavaAttribute;
	
	protected String javaAttributeName;
	
	
	protected AbstractOxmJavaAttribute(OxmJavaType parent, A eJavaAttribute) {
		super(parent);
		this.eJavaAttribute = eJavaAttribute;
		this.javaAttributeName = buildJavaAttributeName();
	}
	
	
	public A getEJavaAttribute() {
		return this.eJavaAttribute;
	}
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setJavaAttributeName_(buildJavaAttributeName());
	}
	
	
	// ***** java attribute name *****
	
	public String getJavaAttributeName() {
		return this.javaAttributeName;
	}
	
	public void setJavaAttributeName(String newName) {
		this.eJavaAttribute.setJavaAttribute(newName);
		setJavaAttributeName_(newName);
	}
	
	protected void setJavaAttributeName_(String newName) {
		String oldName = this.javaAttributeName;
		this.javaAttributeName = newName;
		firePropertyChanged(JAVA_ATTRIBUTE_NAME_PROPERTY, oldName, newName);
	}
	
	protected String buildJavaAttributeName() {
		return this.eJavaAttribute.getJavaAttribute();
	}
}
