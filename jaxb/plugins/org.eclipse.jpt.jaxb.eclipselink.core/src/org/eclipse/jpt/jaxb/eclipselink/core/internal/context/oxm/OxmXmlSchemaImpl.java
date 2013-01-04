/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSchema;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

public class OxmXmlSchemaImpl
		extends AbstractJaxbContextNode
		implements OxmXmlSchema {
	
	protected String namespace;
	protected String defaultNamespace;
	protected String specifiedNamespace;
	
	
	public OxmXmlSchemaImpl(OxmXmlBindings parent) {
		super(parent);
		initNamespace();
	}
	
	
	protected OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) super.getParent();
	}
	
	protected EXmlSchema getEXmlSchema(boolean createIfAbsent) {
		EXmlBindings eXmlBindings = getXmlBindings().getEXmlBindings();
		EXmlSchema eXmlSchema = eXmlBindings.getXmlSchema();
		if (eXmlSchema == null && createIfAbsent) {
			eXmlSchema = OxmFactory.eINSTANCE.createEXmlSchema();
			eXmlBindings.setXmlSchema(eXmlSchema);
		}
		return eXmlSchema;
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncNamespace();
	}
	
	@Override
	public void update() {
		super.update();
		updateNamespace();
	}
	
	
	// ***** namespace *****
	
	public String getNamespace() {
		return this.namespace;
	}
	
	protected void setNamespace_(String namespace) {
		String oldNamespace = this.namespace;
		this.namespace = namespace;
		firePropertyChanged(NAMESPACE_PROPERTY, oldNamespace, namespace);
	}
	
	public String getDefaultNamespace() {
		return this.defaultNamespace;
	}
	
	protected void setDefaultNamespace_(String namespace) {
		String oldNamespace = this.defaultNamespace;
		this.defaultNamespace = namespace;
		firePropertyChanged(DEFAULT_NAMESPACE_PROPERTY, oldNamespace, namespace);
	}
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}
	
	public void setSpecifiedNamespace(String namespace) {
		getEXmlSchema(true).setNamespace(namespace);
		setSpecifiedNamespace_(namespace);
	}
	
	protected void setSpecifiedNamespace_(String namespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = namespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, namespace);
	}
	
	protected String getResourceNamespace() {
		EXmlSchema eXmlSchema = getEXmlSchema(false);
		return (eXmlSchema == null) ? null : eXmlSchema.getNamespace();
	}
	
	protected String buildDefaultNamespace() {
		JaxbPackage jaxbPackage = getXmlBindings().getOxmFile().getJaxbPackage();
		return (jaxbPackage != null) ? jaxbPackage.getNamespace() : "";
	}
	
	protected void initNamespace() {
		this.specifiedNamespace = getResourceNamespace();
	}
	
	protected void syncNamespace() {
		setSpecifiedNamespace_(getResourceNamespace());
	}
	
	protected void updateNamespace() {
		setDefaultNamespace_(buildDefaultNamespace());
		
		String namespace = (this.specifiedNamespace != null) ? this.specifiedNamespace : this.defaultNamespace;
		setNamespace_(namespace);
	}
	
	
	// ***** location *****
	
	public String getLocation() {
		// TODO
		return null;
	}
	
	
	// ***** attribute form default *****
	
	public XmlNsForm getAttributeFormDefault() {
		// TODO 
		return null;
	}
	
	public XmlNsForm getSpecifiedAttributeFormDefault() {
		// TODO
		return null;
	}
	
	public void setSpecifiedAttributeFormDefault(XmlNsForm attributeFormDefault) {
		// TODO
	}
	
	
	// ***** element form default *****
	
	public XmlNsForm getElementFormDefault() {
		// TODO 
		return null;
	}
	
	public XmlNsForm getSpecifiedElementFormDefault() {
		// TODO
		return null;
	}
	
	public void setSpecifiedElementFormDefault(XmlNsForm attributeFormDefault) {
		// TODO
	}
	
	
	// ***** xml ns ****
	
	public ListIterable<XmlNs> getXmlNsPrefixes() {
		// TODO
		return EmptyListIterable.instance();
	}
	
	public int getXmlNsPrefixesSize() {
		// TODO
		return 0;
	}
	
	public XmlNs addXmlNsPrefix(int index) {
		// TODO 
		return null;
	}
	
	public void removeXmlNsPrefix(int index) {
		// TODO		
	}
	
	public void removeXmlNsPrefix(XmlNs xmlNsPrefix) {
		// TODO	
	}
	
	public void moveXmlNsPrefix(int targetIndex, int sourceIndex) {
		// TODO	
	}
}
