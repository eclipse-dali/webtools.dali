/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDForm;

public class GenericJavaXmlSchema
		extends AbstractJavaContextNode
		implements XmlSchema {
	
	protected String specifiedNamespace;
	
	protected String location;
	
	protected XmlNsForm specifiedAttributeFormDefault;
	
	protected XmlNsForm specifiedElementFormDefault;
	
	protected final XmlNsPrefixContainer xmlNsPrefixContainer;
	
	
	public GenericJavaXmlSchema(JaxbPackageInfo parent) {
		super(parent);
		this.specifiedNamespace = this.getResourceNamespace();
		this.location = this.getResourceLocation();
		this.specifiedAttributeFormDefault = getResourceAttributeFormDefault();
		this.specifiedElementFormDefault = getResourceElementFormDefault();
		this.xmlNsPrefixContainer = new XmlNsPrefixContainer();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedNamespace_(this.getResourceNamespace());
		this.setLocation_(this.getResourceLocation());
		this.setSpecifiedAttributeFormDefault_(getResourceAttributeFormDefault());
		this.setSpecifiedElementFormDefault_(getResourceElementFormDefault());
		this.syncXmlNsPrefixes();
	}
	
	@Override
	public void update() {
		super.update();
		this.updateXmlNsPrefixes();
	}
	
	@Override
	public JaxbPackageInfo getParent() {
		return (JaxbPackageInfo) super.getParent();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getParent().getJaxbPackage();
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
		return (this.specifiedNamespace == null) ? "" : this.specifiedNamespace;
	}
	
	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}

	public void setSpecifiedNamespace(String namespace) {
		this.getXmlSchemaAnnotation().setNamespace(namespace);
		this.setSpecifiedNamespace_(namespace);	
	}
	
	protected void setSpecifiedNamespace_(String namespace) {
		String old = this.specifiedNamespace;
		this.specifiedNamespace = namespace;
		this.firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, old, namespace);
	}
	
	protected String getResourceNamespace() {
		return getXmlSchemaAnnotation().getNamespace();
	}
	
	
	// ********** location **********
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.getXmlSchemaAnnotation().setLocation(location);
		this.setLocation_(location);	
	}
	
	protected void setLocation_(String location) {
		String old = this.location;
		this.location = location;
		this.firePropertyChanged(LOCATION_PROPERTY, old, location);
	}
	
	protected String getResourceLocation() {
		return getXmlSchemaAnnotation().getLocation();
	}
	
	
	// ********** attribute form default **********
	
	public XmlNsForm getAttributeFormDefault() {
		return (this.specifiedAttributeFormDefault == null) ? XmlNsForm.UNSET : this.specifiedAttributeFormDefault;
	}
	
	public XmlNsForm getSpecifiedAttributeFormDefault() {
		return this.specifiedAttributeFormDefault;
	}
	
	public void setSpecifiedAttributeFormDefault(XmlNsForm attributeFormDefault) {
		getXmlSchemaAnnotation().setAttributeFormDefault(XmlNsForm.toJavaResourceModel(attributeFormDefault));
		setSpecifiedAttributeFormDefault_(attributeFormDefault);
	}
	
	protected void setSpecifiedAttributeFormDefault_(XmlNsForm attributeFormDefault) {
		XmlNsForm old = this.specifiedAttributeFormDefault;
		this.specifiedAttributeFormDefault = attributeFormDefault;
		firePropertyChanged(SPECIFIED_ATTRIBUTE_FORM_DEFAULT_PROPERTY, old, attributeFormDefault);
	}
	
	protected XmlNsForm getResourceAttributeFormDefault() {
		return XmlNsForm.fromJavaResourceModel(getXmlSchemaAnnotation().getAttributeFormDefault());
	}
	
	
	// ********** element form default **********
	
	public XmlNsForm getElementFormDefault() {
		return (this.specifiedElementFormDefault == null) ? XmlNsForm.UNSET : this.specifiedElementFormDefault;
	}
	
	public XmlNsForm getSpecifiedElementFormDefault() {
		return this.specifiedElementFormDefault;
	}
	
	public void setSpecifiedElementFormDefault(XmlNsForm elementFormDefault) {
		getXmlSchemaAnnotation().setElementFormDefault(XmlNsForm.toJavaResourceModel(elementFormDefault));
		setSpecifiedElementFormDefault_(elementFormDefault);
	}
	
	protected void setSpecifiedElementFormDefault_(XmlNsForm elementFormDefault) {
		XmlNsForm old = this.specifiedElementFormDefault;
		this.specifiedElementFormDefault = elementFormDefault;
		firePropertyChanged(SPECIFIED_ELEMENT_FORM_DEFAULT_PROPERTY, old, elementFormDefault);
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
	
	protected void updateXmlNsPrefixes() {
		this.xmlNsPrefixContainer.update();
	}

	protected ListIterable<XmlNsAnnotation> getXmlNsAnnotations() {
		return getXmlSchemaAnnotation().getXmlns();
	}
	
	
	// **************** content assist ****************************************
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (namespaceTouches(pos, astRoot)) {
			return getNamespaceProposals(filter);
		}
		
		return EmptyIterable.instance();
	}
	
	protected boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return getXmlSchemaAnnotation().namespaceTouches(pos, astRoot);
	}
	
	protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
						getJaxbProject().getSchemaLibrary().getSchemaLocations().keySet(), filter));
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getXmlSchemaAnnotation().getTextRange(astRoot);
	}
	
	
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		XsdSchema schema = getJaxbPackage().getXsdSchema();
		if (schema != null) {
			if (formConflicts(getAttributeFormDefault(), schema.getXSDComponent().getAttributeFormDefault())) {
				messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_SCHEMA__MISMATCHED_ATTRIBUTE_FORM_DEFAULT,
					this,
					getXmlSchemaAnnotation().getAttributeFormDefaultTextRange(astRoot)));
			}
			
			if (formConflicts(getElementFormDefault(), schema.getXSDComponent().getElementFormDefault())) {
				messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_SCHEMA__MISMATCHED_ELEMENT_FORM_DEFAULT,
					this,
					getXmlSchemaAnnotation().getElementFormDefaultTextRange(astRoot)));
			}
		}
	}
	
	protected boolean formConflicts(XmlNsForm form, XSDForm xsdForm) {
		return (form == XmlNsForm.QUALIFIED) ^ (xsdForm == XSDForm.QUALIFIED_LITERAL);
	}
	
	
	/**
	 * xml ns prefix container adapter
	 */
	protected class XmlNsPrefixContainer
			extends ContextListContainer<XmlNs, XmlNsAnnotation> {
		
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
