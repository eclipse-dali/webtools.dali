/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.SpecifiedOrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkPersistentAttributeValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder;

/**
 * EclipseLink
 * <code>orm.xml</code> persistent attribute
 */
public class OrmEclipseLinkPersistentAttribute
	extends SpecifiedOrmPersistentAttribute
	implements EclipseLinkAccessMethodsHolder
{
	//TODO defaults from the persistentType if the access is VIRTUAL
	protected String specifiedGetMethod;
	protected String specifiedSetMethod;

	public OrmEclipseLinkPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
		this.specifiedGetMethod = this.buildSpecifiedGetMethod();
		this.specifiedSetMethod = this.buildSpecifiedSetMethod();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedGetMethod_(this.buildSpecifiedGetMethod());
		this.setSpecifiedSetMethod_(this.buildSpecifiedSetMethod());
	}

	//*************** get method *****************

	public String getGetMethod() {
		String specifiedGetMethod = this.getSpecifiedGetMethod();
		return (specifiedGetMethod != null) ? specifiedGetMethod : this.getDefaultGetMethod();
	}

	public String getDefaultGetMethod() {
		return DEFAULT_GET_METHOD;
	}

	public String getSpecifiedGetMethod() {
		return this.specifiedGetMethod;
	}

	public void setSpecifiedGetMethod(String getMethod) {
		this.setSpecifiedGetMethod_(getMethod);
		this.getXmlAccessMethods().setGetMethod(getMethod);
	}

	protected void setSpecifiedGetMethod_(String getMethod) {
		String old = this.specifiedGetMethod;
		this.specifiedGetMethod = getMethod;
		this.firePropertyChanged(SPECIFIED_GET_METHOD_PROPERTY, old, getMethod);
	}

	protected String buildSpecifiedGetMethod() {
		XmlAccessMethods xmlAccessMethods = getXmlAccessMethods();
		return xmlAccessMethods != null ? xmlAccessMethods.getGetMethod() : null;
	}

	protected XmlAccessMethodsHolder getXmlAccessMethodsHolder() {
		return (XmlAccessMethodsHolder) this.getXmlAttributeMapping();
	}

	protected XmlAccessMethods getXmlAccessMethods() {
		return getXmlAccessMethodsHolder().getAccessMethods();
	}


	//*************** set method *****************
	public String getSetMethod() {
		String specifiedSetMethod = this.getSpecifiedSetMethod();
		return (specifiedSetMethod != null) ? specifiedSetMethod : this.getDefaultSetMethod();
	}

	public String getDefaultSetMethod() {
		return DEFAULT_SET_METHOD;
	}

	public String getSpecifiedSetMethod() {
		return this.specifiedSetMethod;
	}
	public void setSpecifiedSetMethod(String setMethod) {
		this.setSpecifiedSetMethod_(setMethod);
		this.getXmlAccessMethods().setSetMethod(setMethod);
	}

	protected void setSpecifiedSetMethod_(String setMethod) {
		String old = this.specifiedSetMethod;
		this.specifiedSetMethod = setMethod;
		this.firePropertyChanged(SPECIFIED_SET_METHOD_PROPERTY, old, setMethod);
	}

	protected String buildSpecifiedSetMethod() {
		XmlAccessMethods xmlAccessMethods = getXmlAccessMethods();
		return xmlAccessMethods != null ? xmlAccessMethods.getSetMethod() : null;
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new EclipseLinkPersistentAttributeValidator(this, buildTextRangeResolver());
	}
}
