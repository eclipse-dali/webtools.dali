/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericPackage
		extends AbstractJaxbContextNode
		implements JaxbPackage {
	
	protected final String name;
	
	protected JaxbPackageInfo packageInfo;
	
	
	public GenericPackage(JaxbContextRoot parent, String name) {
		super(parent);
		this.name = name;
		JavaResourcePackage jrp = getJaxbProject().getAnnotatedJavaResourcePackage(this.name);
		if (jrp != null) {
			this.packageInfo = buildPackageInfo(jrp);
		}
	}
	
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		if (this.packageInfo != null) { 
			this.packageInfo.synchronizeWithResourceModel();
		}
	}

	//Building/removing of the packageInfo is in the update because this is dependent
	//on a JaxbFile being added/removed which only causes an update of the model.
	@Override
	public void update() {
		super.update();
		JavaResourcePackage jrp = getJaxbProject().getAnnotatedJavaResourcePackage(this.name);
		if (jrp == null) {
			this.setPackageInfo_(null);
		}
		else {
			if (this.packageInfo == null) {
				this.setPackageInfo_(this.buildPackageInfo(jrp));
			}
			else {
				this.packageInfo.update();
			}
		}
	}
	
	
	// **************** name **************************************************
	
	public String getName() {
		return this.name;
	}
	
	
	// **************** package info ******************************************

	public JaxbPackageInfo getPackageInfo() {
		return this.packageInfo;
	}
	
	protected void setPackageInfo_(JaxbPackageInfo packageInfo) {
		JaxbPackageInfo old = this.packageInfo;
		this.packageInfo = packageInfo;
		firePropertyChanged(PACKAGE_INFO_PROPERTY, old, this.packageInfo);
	}
	
	protected JaxbPackageInfo buildPackageInfo(JavaResourcePackage resourcePackage) {
		return getFactory().buildJavaPackageInfo(this, resourcePackage);
	}
	
	public boolean isEmpty() {
		return getPackageInfo() == null && getRegistry() == null;
	}
	
	public XmlRegistry getRegistry() {
		Iterable<XmlRegistry> registries = getContextRoot().getXmlRegistries(this);
		if (IterableTools.isEmpty(registries)) {
			return null;
		}
		return IterableTools.get(registries, 0);
	}
	
	
	// **************** misc **************************************************
	
	public String getNamespace() {
		return (getPackageInfo() == null) ? "" : getPackageInfo().getXmlSchema().getNamespace(); //$NON-NLS-1$
	}
	
	public XmlNsForm getAttributeFormDefault() {
		return (getPackageInfo() == null) ? XmlNsForm.UNSET : getPackageInfo().getXmlSchema().getAttributeFormDefault();
	}
	
	public XmlNsForm getElementFormDefault() {
		return (getPackageInfo() == null) ? XmlNsForm.UNSET : getPackageInfo().getXmlSchema().getElementFormDefault();
	}
	
	public XsdSchema getXsdSchema() {
		return getJaxbProject().getSchemaLibrary().getSchema(getNamespace());
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (getJaxbProject().getSchemaLibrary().getSchema(getNamespace()) == null) {
			messages.add(
					this.buildValidationMessage(
						JptJaxbCoreValidationMessages.PACKAGE_NO_SCHEMA_FOR_NAMESPACE,
						getNamespace(),
						this.name
					));
		}
		
		if (this.packageInfo != null) {
			this.packageInfo.validate(messages, reporter);
		}
	}
}
