/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaJaxbClass
		extends AbstractJavaType 
		implements JaxbClass {
	
	protected XmlRegistry xmlRegistry;
	
	
	public GenericJavaJaxbClass(JaxbContextNode parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		initXmlRegistry();
	}
	
	
	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}
	
	public Kind getKind() {
		return JaxbType.Kind.CLASS;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlRegistry();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlRegistry();
	}
	
	
	// ***** mapping *****
	
	@Override
	public JaxbClassMapping getMapping() {
		return (JaxbClassMapping) super.getMapping();
	}
	
	@Override
	protected JaxbClassMapping buildMapping() {
		return getFactory().buildJaxbClassMapping(this);
	}
	
	@Override
	protected boolean isSpecifiedMapped() {
		return getXmlTypeAnnotation() != null
				|| getXmlRootElementAnnotation() != null
				|| getXmlTransientAnnotation() != null;
	}
	
	
	// ***** XmlRegistry *****
	
	public XmlRegistry getXmlRegistry() {
		return this.xmlRegistry;
	}
	
	protected void setXmlRegistry_(XmlRegistry xmlRegistry) {
		XmlRegistry old = this.xmlRegistry;
		this.xmlRegistry = xmlRegistry;
		firePropertyChanged(XML_REGISTRY_PROPERTY, old, xmlRegistry);
	}
	
	protected XmlRegistryAnnotation getXmlRegistryAnnotation() {
		return (XmlRegistryAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_REGISTRY);
	}
	
	protected XmlRegistry buildXmlRegistry() {
		return getFactory().buildXmlRegistry(this);
	}
	
	protected void initXmlRegistry() {
		if (getXmlRegistryAnnotation() != null) {
			this.xmlRegistry = buildXmlRegistry();
		}
	}
	
	protected void syncXmlRegistry() {
		XmlRegistryAnnotation annotation = getXmlRegistryAnnotation();
		if (annotation != null) {
			if (this.xmlRegistry != null) {
				this.xmlRegistry.synchronizeWithResourceModel();
			}
			else {
				setXmlRegistry_(buildXmlRegistry());
			}
		}
		else if (this.xmlRegistry != null) {
			setXmlRegistry_(null);
		}
	}
	
	protected void updateXmlRegistry() {
		if (this.xmlRegistry != null) {
			this.xmlRegistry.update();
		}
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (this.xmlRegistry != null) {
			this.xmlRegistry.validate(messages, reporter);
		}
	}
}
