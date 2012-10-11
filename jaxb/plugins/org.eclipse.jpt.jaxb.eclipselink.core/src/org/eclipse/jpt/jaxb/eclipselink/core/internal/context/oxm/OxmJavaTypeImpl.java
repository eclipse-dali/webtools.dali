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

import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;

public class OxmJavaTypeImpl
		extends AbstractJaxbContextNode
		implements OxmJavaType {
	
	protected EJavaType eJavaType;
	
	protected String specifiedName;
	protected String qualifiedName;
	
	
	public OxmJavaTypeImpl(OxmXmlBindings parent, EJavaType eJavaType) {
		super(parent);
		this.eJavaType = eJavaType;
		this.specifiedName = buildSpecifiedName();
		this.qualifiedName = buildQualifiedName();
	}
	
	
	public OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) getParent();
	}
	
	public EJavaType getEJavaType() {
		return this.eJavaType;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
	}
	
	
	// ***** name *****
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newName) {
		this.eJavaType.setName(newName);
		setSpecifiedName_(newName);
	}
	
	protected void setSpecifiedName_(String newName) {
		String oldName = this.specifiedName;
		this.specifiedName = newName;
		this.qualifiedName = buildQualifiedName();
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldName, newName);
	}
	
	protected String buildSpecifiedName() {
		return this.eJavaType.getName();
	}
	
	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	protected String buildQualifiedName() {
		return getXmlBindings().getQualifiedName(this.specifiedName);
	}
	
	public String getSimpleName() {
		return ClassName.getSimpleName(this.qualifiedName);
	}
}
