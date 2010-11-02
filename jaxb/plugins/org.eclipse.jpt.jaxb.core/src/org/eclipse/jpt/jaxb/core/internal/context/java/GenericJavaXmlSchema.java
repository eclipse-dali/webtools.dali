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

import java.util.Vector;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

public class GenericJavaXmlSchema
	extends AbstractJaxbContextNode
	implements XmlSchema
{

	protected String namespace;

	protected String specifiedLocation;

	protected XmlNsForm specifiedAttributeFormDefault;

	protected XmlNsForm specifiedElementFormDefault;

	protected final Vector<XmlNs> xmlNsPrefixes = new Vector<XmlNs>();
	protected final XmlNsPrefixContainerAdapter xmlNsPrefixContainerAdapter = new XmlNsPrefixContainerAdapter();

	public GenericJavaXmlSchema(JaxbPackageInfo parent) {
		super(parent);
		this.namespace = this.getResourceNamespace();
		this.specifiedLocation = this.getResourceLocation();
		this.specifiedAttributeFormDefault = this.getResourceAttributeFormDefault();
		this.specifiedElementFormDefault = this.getResourceElementFormDefault();
		this.initializeXmlNsPrefixes();
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
		return new LiveCloneListIterable<XmlNs>(this.xmlNsPrefixes);
	}

	public int getXmlNsPrefixesSize() {
		return this.xmlNsPrefixes.size();
	}

	public XmlNs getXmlNsPrefix(int index) {
		return this.xmlNsPrefixes.get(index);
	}

	public XmlNs addXmlNsPrefix() {
		return this.addXmlNsPrefix(this.xmlNsPrefixes.size());
	}

	public XmlNs addXmlNsPrefix(int index) {
		XmlNsAnnotation annotation = this.getXmlSchemaAnnotation().addXmlns(index);
		return this.addXmlNsPrefix_(index, annotation);
	}

	public void removeXmlNsPrefix(XmlNs xmlNsPrefix) {
		this.removeXmlNsPrefix(this.xmlNsPrefixes.indexOf(xmlNsPrefix));
	}

	public void removeXmlNsPrefix(int index) {
		this.getXmlSchemaAnnotation().removeXmlns(index);
		this.removeXmlNsPrefix_(index);
	}

	protected void removeXmlNsPrefix_(int index) {
		this.removeItemFromList(index, this.xmlNsPrefixes, XML_NS_PREFIXES_LIST);
	}

	public void moveXmlNsPrefix(int targetIndex, int sourceIndex) {
		this.getXmlSchemaAnnotation().moveXmlns(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.xmlNsPrefixes, XML_NS_PREFIXES_LIST);
	}

	protected void initializeXmlNsPrefixes() {
		for (XmlNsAnnotation xmlNsAnnotation : this.getXmlNsAnnotations()) {
			this.xmlNsPrefixes.add(this.buildXmlNs(xmlNsAnnotation));
		}
	}

	protected XmlNs buildXmlNs(XmlNsAnnotation xmlNsAnnotation) {
		return this.getFactory().buildJavaXmlNs(this, xmlNsAnnotation);
	}

	protected void syncXmlNsPrefixes() {
		ContextContainerTools.synchronizeWithResourceModel(this.xmlNsPrefixContainerAdapter);
	}

	protected Iterable<XmlNsAnnotation> getXmlNsAnnotations() {
		return getXmlSchemaAnnotation().getXmlns();
	}

	protected void moveXmlNsPrefix_(int index, XmlNs xmlNs) {
		this.moveItemInList(index, xmlNs, this.xmlNsPrefixes, XML_NS_PREFIXES_LIST);
	}

	protected XmlNs addXmlNsPrefix_(int index, XmlNsAnnotation xmlNsAnnotation) {
		XmlNs xmlNs = this.buildXmlNs(xmlNsAnnotation);
		this.addItemToList(index, xmlNs, this.xmlNsPrefixes, XML_NS_PREFIXES_LIST);
		return xmlNs;
	}

	protected void removeXmlNsPrefix_(XmlNs xmlNs) {
		this.removeXmlNsPrefix_(this.xmlNsPrefixes.indexOf(xmlNs));
	}


	/**
	 * xml ns prefix container adapter
	 */
	protected class XmlNsPrefixContainerAdapter
		implements ContextContainerTools.Adapter<XmlNs, XmlNsAnnotation>
	{
		public Iterable<XmlNs> getContextElements() {
			return GenericJavaXmlSchema.this.getXmlNsPrefixes();
		}
		public Iterable<XmlNsAnnotation> getResourceElements() {
			return GenericJavaXmlSchema.this.getXmlSchemaAnnotation().getXmlns();
		}
		public XmlNsAnnotation getResourceElement(XmlNs contextElement) {
			return contextElement.getResourceXmlNs();
		}
		public void moveContextElement(int index, XmlNs element) {
			GenericJavaXmlSchema.this.moveXmlNsPrefix_(index, element);
		}
		public void addContextElement(int index, XmlNsAnnotation resourceElement) {
			GenericJavaXmlSchema.this.addXmlNsPrefix_(index, resourceElement);
		}
		public void removeContextElement(XmlNs element) {
			GenericJavaXmlSchema.this.removeXmlNsPrefix_(element);
		}
	}

}
