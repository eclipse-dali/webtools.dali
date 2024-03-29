/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.SchemaEntry;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.XSDForm;

public class GenericJavaXmlSchema
		extends AbstractJavaContextNode
		implements JavaXmlSchema {
	
	protected String namespace;
	protected String specifiedNamespace;
	
	protected String location;
	
	protected XmlNsForm specifiedAttributeFormDefault;
	
	protected XmlNsForm specifiedElementFormDefault;
	
	protected final ContextListContainer<XmlNs, XmlNsAnnotation> xmlNsPrefixContainer;
	
	
	public GenericJavaXmlSchema(JaxbPackageInfo parent) {
		super(parent);
		initNamespace();
		this.location = this.getResourceLocation();
		this.specifiedAttributeFormDefault = getResourceAttributeFormDefault();
		this.specifiedElementFormDefault = getResourceElementFormDefault();
		this.xmlNsPrefixContainer = this.buildXmlNsPrefixContainer();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncNamespace();
		this.setLocation_(this.getResourceLocation());
		this.setSpecifiedAttributeFormDefault_(getResourceAttributeFormDefault());
		this.setSpecifiedElementFormDefault_(getResourceElementFormDefault());
		this.syncXmlNsPrefixes();
	}
	
	@Override
	public void update() {
		super.update();
		updateNamespace();
		this.updateXmlNsPrefixes();
	}
	
	public JaxbPackageInfo getJaxbPackageInfo() {
		return (JaxbPackageInfo) getParent();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getJaxbPackageInfo().getJaxbPackage();
	}
	
	protected JavaResourcePackage getResourcePackage() {
		return getJaxbPackageInfo().getResourcePackage();
	}
	
	
	// ********** xml schema annotation **********
	
	protected XmlSchemaAnnotation getXmlSchemaAnnotation() {
		return (XmlSchemaAnnotation) this.getResourcePackage().getNonNullAnnotation(JAXB.XML_SCHEMA);
	}
	
	
	// ********** namespace **********
	
	public String getNamespace() {
		return this.namespace;
	}
	
	protected void setNamespace_(String namespace) {
		String oldNamespace = this.namespace;
		this.namespace = namespace;
		firePropertyChanged(NAMESPACE_PROPERTY, oldNamespace, namespace);
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
	
	protected void initNamespace() {
		this.specifiedNamespace = getResourceNamespace();
	}
	
	protected void syncNamespace() {
		setSpecifiedNamespace_(getResourceNamespace());
	}
	
	protected void updateNamespace() {
		String namespace = (this.specifiedNamespace != null) ? this.specifiedNamespace : ""; //$NON-NLS-1$
		setNamespace_(namespace);
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

	protected ContextListContainer<XmlNs, XmlNsAnnotation> buildXmlNsPrefixContainer() {
		XmlNsPrefixContainer container = new XmlNsPrefixContainer();
		container.initialize();
		return container;
	}
	
	
	// **************** content assist ****************************************
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (namespaceTouches(pos)) {
			return getNamespaceProposals();
		}
		
		for (XmlNs xmlns : getXmlNsPrefixes()) {
			result = xmlns.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	protected boolean namespaceTouches(int pos) {
		return getXmlSchemaAnnotation().namespaceTouches(pos);
	}
	
	protected Iterable<String> getNamespaceProposals() {
		return IterableTools.transform(
						IterableTools.transform(
								getJaxbProject().getSchemaLibrary().getSchemaEntries(),
								SchemaEntry.NAMESPACE_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange() {
		return getXmlSchemaAnnotation().getTextRange();
	}
	
	
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		// removing this validation message for now:  see jaxb bug 823 (http://java.net/jira/browse/JAXB-823)
//		XsdSchema schema = getJaxbPackage().getXsdSchema();
//		if (schema != null) {
//			if (formConflicts(getAttributeFormDefault(), schema.getXSDComponent().getAttributeFormDefault())) {
//				messages.add(
//				this.buildErrorValidationMessage(
//					IMessage.HIGH_SEVERITY,
//					JaxbValidationMessages.XML_SCHEMA__MISMATCHED_ATTRIBUTE_FORM_DEFAULT,
//					this,
//					getXmlSchemaAnnotation().getAttributeFormDefaultTextRange(astRoot)));
//			}
//			
//			if (formConflicts(getElementFormDefault(), schema.getXSDComponent().getElementFormDefault())) {
//				messages.add(
//				this.buildErrorValidationMessage(
//					IMessage.HIGH_SEVERITY,
//					JaxbValidationMessages.XML_SCHEMA__MISMATCHED_ELEMENT_FORM_DEFAULT,
//					this,
//					getXmlSchemaAnnotation().getElementFormDefaultTextRange(astRoot)));
//			}
//		}
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
