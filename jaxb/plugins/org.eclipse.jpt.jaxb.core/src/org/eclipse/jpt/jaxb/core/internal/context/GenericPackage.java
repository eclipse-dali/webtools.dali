/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
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
	
	
	public void synchronizeWithResourceModel() {
		if (this.packageInfo != null) { 
			this.packageInfo.synchronizeWithResourceModel();
		}
	}

	//Building/removing of the packageInfo is in the update because this is dependent
	//on a JaxbFile being added/removed which only causes an update of the model.
	public void update() {
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
		return getPackageInfo() == null;
	}
	
	
	// **************** misc **************************************************
	
	public String getNamespace() {
		return (getPackageInfo() == null) ? "" : getPackageInfo().getXmlSchema().getNamespace();
	}
	
	
	// **************** validation ********************************************
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (! getJaxbProject().getSchemaLibrary().getSchemaLocations().containsKey(getNamespace())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JaxbValidationMessages.PACKAGE_NO_SCHEMA_FOR_NAMESPACE,
						new String[] {getNamespace(), this.name},
						this));
		}
	}
}
