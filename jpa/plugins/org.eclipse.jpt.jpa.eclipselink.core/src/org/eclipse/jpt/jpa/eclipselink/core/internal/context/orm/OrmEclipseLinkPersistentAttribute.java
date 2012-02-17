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

import java.util.List;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.SpecifiedOrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkPersistentAttributeValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

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
		return this.getXmlAttributeMapping();
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


	@Override
	protected JavaPersistentAttribute getCachedJavaAttribute() {
		if (this.getAccess() == EclipseLinkAccessType.VIRTUAL) {
			//if VIRTUAL we will always have to build and cache the 'javaAttribute'
			//we clear out the cached 'javaAttribute in setSpecifiedAccess_() and setDefaultAccess_()
			if (this.cachedJavaPersistentAttribute == null) {
				this.cachedJavaPersistentAttribute = new VirtualJavaPersistentAttribute(this.getParent(), this.getXmlAttributeMapping());
			}
			return this.cachedJavaPersistentAttribute;
		}
		return super.getCachedJavaAttribute();
	}

	@Override
	protected XmlAttributeMapping getXmlAttributeMapping() {
		return (XmlAttributeMapping) super.getXmlAttributeMapping();
	}


	// ********** validation **********

	
	//TODO validate that the attribute-type exists on the classpath
	@Override
	protected void validateAttribute(List<IMessage> messages, IReporter reporter) {
		super.validateAttribute(messages, reporter);
		//TODO many-to-one needs to specify target-entity instead of attribute-type, move this validation to the mappings?
		if (getAccess() == EclipseLinkAccessType.VIRTUAL) {
			if (getTypeName() == null) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						EclipseLinkJpaValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED,
						new String[] {
							this.getName()
						},
						this.mapping,
						this.mapping.getSelectionTextRange()
					)
				);				
			}
		}
	}

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new EclipseLinkPersistentAttributeValidator(this, buildTextRangeResolver());
	}
}
