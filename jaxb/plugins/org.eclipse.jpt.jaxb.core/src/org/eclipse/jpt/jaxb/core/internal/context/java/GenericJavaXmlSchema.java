/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

public class GenericJavaXmlSchema
	extends AbstractJaxbContextNode
	implements XmlSchema
{

	protected String namespace;

	protected String specifiedLocation;

	protected XmlNsForm specifiedAttributeFormDefault;

	protected XmlNsForm specifiedElementFormDefault;

	protected final XmlNsPrefixContainer xmlNsPrefixContainer;

	public GenericJavaXmlSchema(JaxbPackageInfo parent) {
		super(parent);
		this.namespace = this.getResourceNamespace();
		this.specifiedLocation = this.getResourceLocation();
		this.specifiedAttributeFormDefault = this.getResourceAttributeFormDefault();
		this.specifiedElementFormDefault = this.getResourceElementFormDefault();
		this.xmlNsPrefixContainer = new XmlNsPrefixContainer();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setNamespace_(this.getResourceNamespace());
		this.setSpecifiedLocation_(this.getResourceLocation());
		this.setSpecifiedAttributeFormDefault_(this.getResourceAttributeFormDefault());
		this.setSpecifiedElementFormDefault_(this.getResourceElementFormDefault());
		this.syncXmlNsPrefixes();
	}

	public void update() {
		this.updateNodes(getXmlNsPrefixes());
	}


	@Override
	public JaxbPackageInfo getParent() {
		return (JaxbPackageInfo) super.getParent();
	}

	protected JavaResourcePackage getResourcePackage() {
		return getParent().getResourcePackage();
	}


	// ********** xml schema annotation **********

	protected XmlSchemaAnnotation getXmlSchemaAnnotation() {
		return (XmlSchemaAnnotation) this.getResourcePackage().getNonNullAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
	}

	// ********** namespace **********

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.getXmlSchemaAnnotation().setNamespace(namespace);
		this.setNamespace_(namespace);	
	}

	protected void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	protected String getResourceNamespace() {
		return getXmlSchemaAnnotation().getNamespace();
	}

	// ********** location **********

	public String getLocation() {
		return (this.specifiedLocation != null) ? this.specifiedLocation : this.getDefaultLocation();
	}

	public String getSpecifiedLocation() {
		return this.specifiedLocation;
	}

	public void setSpecifiedLocation(String location) {
		this.getXmlSchemaAnnotation().setLocation(location);
		this.setSpecifiedLocation_(location);	
	}

	protected void setSpecifiedLocation_(String location) {
		String old = this.specifiedLocation;
		this.specifiedLocation = location;
		this.firePropertyChanged(SPECIFIED_LOCATION_PROPERTY, old, location);
	}

	public String getDefaultLocation() {
		return DEFAULT_LOCATION;
	}

	protected String getResourceLocation() {
		return getXmlSchemaAnnotation().getLocation();
	}

	// ********** attribute form default **********

	public XmlNsForm getAttributeFormDefault() {
		return (this.specifiedAttributeFormDefault != null) ? this.specifiedAttributeFormDefault : this.getDefaultAttributeFormDefault();
	}

	public XmlNsForm getSpecifiedAttributeFormDefault() {
		return this.specifiedAttributeFormDefault;
	}

	public void setSpecifiedAttributeFormDefault(XmlNsForm xmlNsForm) {
		this.getXmlSchemaAnnotation().setAttributeFormDefault(XmlNsForm.toJavaResourceModel(xmlNsForm));
		this.setSpecifiedAttributeFormDefault_(xmlNsForm);
	}

	protected void setSpecifiedAttributeFormDefault_(XmlNsForm xmlNsForm) {
		XmlNsForm old = this.specifiedAttributeFormDefault;
		this.specifiedAttributeFormDefault = xmlNsForm;
		this.firePropertyChanged(SPECIFIED_ATTRIBUTE_FROM_DEFAULT_PROPERTY, old, xmlNsForm);
	}

	public XmlNsForm getDefaultAttributeFormDefault() {
		return XmlNsForm.UNSET;
	}

	protected XmlNsForm getResourceAttributeFormDefault() {
		return XmlNsForm.fromJavaResourceModel(getXmlSchemaAnnotation().getAttributeFormDefault());
	}

	// ********** element form default **********

	public XmlNsForm getElementFormDefault() {
		return (this.specifiedElementFormDefault != null) ? this.specifiedElementFormDefault : this.getDefaultElementFormDefault();
	}

	public XmlNsForm getSpecifiedElementFormDefault() {
		return this.specifiedElementFormDefault;
	}

	public void setSpecifiedElementFormDefault(XmlNsForm xmlNsForm) {
		this.getXmlSchemaAnnotation().setElementFormDefault(XmlNsForm.toJavaResourceModel(xmlNsForm));
		this.setSpecifiedElementFormDefault_(xmlNsForm);
	}

	protected void setSpecifiedElementFormDefault_(XmlNsForm xmlNsForm) {
		XmlNsForm old = this.specifiedElementFormDefault;
		this.specifiedElementFormDefault = xmlNsForm;
		this.firePropertyChanged(SPECIFIED_ELEMENT_FROM_DEFAULT_PROPERTY, old, xmlNsForm);
	}

	public XmlNsForm getDefaultElementFormDefault() {
		return XmlNsForm.UNSET;
	}

	protected XmlNsForm getResourceElementFormDefault() {
		return XmlNsForm.fromJavaResourceModel(getXmlSchemaAnnotation().getElementFormDefault());
	}


	// ********** xml namespace prefixes **********

	public ListIterable<XmlNs> getXmlNsPrefixes() {
		return this.xmlNsPrefixContainer.getContextElements();
	}

	public int getXmlNsPrefixesSize() {
		return this.xmlNsPrefixContainer.getContextElementsSize();
	}

	public XmlNs addXmlNsPrefix(int index) {
		XmlNsAnnotation annotation = this.getXmlSchemaAnnotation().addXmlns(index);
		return this.xmlNsPrefixContainer.addContextElement(index, annotation);
	}

	public void removeXmlNsPrefix(XmlNs xmlNsPrefix) {
		this.removeXmlNsPrefix(this.xmlNsPrefixContainer.indexOfContextElement(xmlNsPrefix));
	}

	public void removeXmlNsPrefix(int index) {
		this.getXmlSchemaAnnotation().removeXmlns(index);
		this.xmlNsPrefixContainer.removeContextElement(index);
	}

	public void moveXmlNsPrefix(int targetIndex, int sourceIndex) {
		this.getXmlSchemaAnnotation().moveXmlns(targetIndex, sourceIndex);
		this.xmlNsPrefixContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected XmlNs buildXmlNs(XmlNsAnnotation xmlNsAnnotation) {
		return this.getFactory().buildJavaXmlNs(this, xmlNsAnnotation);
	}

	protected void syncXmlNsPrefixes() {
		this.xmlNsPrefixContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNsAnnotation> getXmlNsAnnotations() {
		return getXmlSchemaAnnotation().getXmlns();
	}


	/**
	 * xml ns prefix container adapter
	 */
	protected class XmlNsPrefixContainer
		extends ContextListContainer<XmlNs, XmlNsAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return XML_NS_PREFIXES_LIST;
		}
		@Override
		protected XmlNs buildContextElement(XmlNsAnnotation resourceElement) {
			return GenericJavaXmlSchema.this.buildXmlNs(resourceElement);
		}
		@Override
		protected ListIterable<XmlNsAnnotation> getResourceElements() {
			return GenericJavaXmlSchema.this.getXmlNsAnnotations();
		}
		@Override
		protected XmlNsAnnotation getResourceElement(XmlNs contextElement) {
			return contextElement.getResourceXmlNs();
		}
	}

}
