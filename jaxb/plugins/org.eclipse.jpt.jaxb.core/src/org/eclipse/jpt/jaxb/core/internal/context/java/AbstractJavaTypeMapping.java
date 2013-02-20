/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.context.AbstractQName;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaTypeMapping
		extends AbstractJavaContextNode
		implements JavaTypeMapping {
	
	protected boolean xmlTransient;
	
	protected final JaxbQName qName;
	
	protected XmlRootElement xmlRootElement;
	
	protected XmlSeeAlso xmlSeeAlso;
	
	
	protected AbstractJavaTypeMapping(JavaType parent) {
		super(parent);
		initXmlTransient();
		this.qName = buildQName(); 
		initXmlRootElement();
		initializeXmlSeeAlso();
	}
	
	
	public JavaType getJavaType() {
		return (JavaType) getParent();
	}
	
	protected JavaResourceAbstractType getJavaResourceType() {
		return getJavaType().getJavaResourceType();
	}
	
	public TypeKind getTypeKind() {
		return getJavaType().getKind();
	}
	
	public TypeName getTypeName() {
		return getJavaType().getTypeName();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getJavaType().getJaxbPackage();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlTransient();
		this.qName.synchronizeWithResourceModel();
		syncXmlRootElement();
		syncXmlSeeAlso();
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
		updateXmlRootElement();
		updateXmlSeeAlso();
	}
	
	
	// ***** XmlTransient *****
	
	public boolean isXmlTransient() {
		return this.xmlTransient;
	}
	
	public void setXmlTransient(boolean newValue) {
		if (newValue) {
			getJavaResourceType().addAnnotation(JAXB.XML_TRANSIENT);
		}
		else {
			getJavaResourceType().removeAnnotation(JAXB.XML_TRANSIENT);
		}
		setXmlTransient_(newValue);
	}
	
	protected void setXmlTransient_(boolean newValue) {
		boolean old = this.xmlTransient;
		this.xmlTransient = newValue;
		firePropertyChanged(XML_TRANSIENT_PROPERTY, old, newValue);
	}
	
	protected XmlTransientAnnotation getXmlTransientAnnotation() {
		return (XmlTransientAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_TRANSIENT);
	}
	
	protected void initXmlTransient() {
		this.xmlTransient = getXmlTransientAnnotation() != null;
	}
	
	protected void syncXmlTransient() {
		setXmlTransient_(getXmlTransientAnnotation() != null);
	}
	
	
	// ***** XmlType.name and namespace *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlTypeQName(this);
	}
	
	protected XmlTypeAnnotation getXmlTypeAnnotation() {
		return (XmlTypeAnnotation) getJavaResourceType().getNonNullAnnotation(JAXB.XML_TYPE);
	}
	
	
	// ***** XmlRootElement *****
	
	public XmlRootElement getXmlRootElement() {
		return this.xmlRootElement;
	}
	
	protected void setXmlRootElement_(XmlRootElement rootElement) {
		XmlRootElement old = this.xmlRootElement;
		this.xmlRootElement = rootElement;
		this.firePropertyChanged(XML_ROOT_ELEMENT_PROPERTY, old, rootElement);
	}
	
	public XmlRootElement addXmlRootElement() {
		if (this.xmlRootElement != null) {
			throw new IllegalStateException();
		}
		
		XmlRootElementAnnotation annotation 
				= (XmlRootElementAnnotation) getJavaResourceType().addAnnotation(JAXB.XML_ROOT_ELEMENT);
		
		XmlRootElement xmlRootElement = buildXmlRootElement(annotation);
		setXmlRootElement_(xmlRootElement);
		return xmlRootElement;
	}
	
	public void removeXmlRootElement() {
		if (this.xmlRootElement == null) {
			throw new IllegalStateException();
		}
		
		getJavaResourceType().removeAnnotation(JAXB.XML_ROOT_ELEMENT);
		setXmlRootElement_(null);
	}
	
	protected XmlRootElementAnnotation getXmlRootElementAnnotation() {
		return (XmlRootElementAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_ROOT_ELEMENT);
	}
	
	protected XmlRootElement buildXmlRootElement() {
		XmlRootElementAnnotation annotation = getXmlRootElementAnnotation();
		return annotation == null ? null : buildXmlRootElement(annotation);
	}
	
	protected XmlRootElement buildXmlRootElement(XmlRootElementAnnotation resourceRootElement) {
		return getFactory().buildJavaXmlRootElement(this, resourceRootElement);
	}
	
	protected void initXmlRootElement() {
		this.xmlRootElement = this.buildXmlRootElement();
	}
	
	protected void syncXmlRootElement() {
		XmlRootElementAnnotation annotation = getXmlRootElementAnnotation();
		if (annotation != null) {
			if (this.xmlRootElement != null) {
				this.xmlRootElement.synchronizeWithResourceModel();
			}
			else {
				setXmlRootElement_(buildXmlRootElement(annotation));
			}
		}
		else if (this.xmlRootElement != null) {
			setXmlRootElement_(null);
		}
	}
	
	protected void updateXmlRootElement() {
		if (this.xmlRootElement != null) {
			this.xmlRootElement.update();
		}
	}
	
	
	// ***** XmlSeeAlso *****
	
	public XmlSeeAlso getXmlSeeAlso() {
		return this.xmlSeeAlso;
	}
	
	protected void setXmlSeeAlso_(XmlSeeAlso xmlSeeAlso) {
		XmlSeeAlso old = this.xmlSeeAlso;
		this.xmlSeeAlso = xmlSeeAlso;
		firePropertyChanged(XML_SEE_ALSO_PROPERTY, old, xmlSeeAlso);
	}
	
	public XmlSeeAlso addXmlSeeAlso() {
		if (this.xmlSeeAlso != null) {
			throw new IllegalStateException();
		}
		XmlSeeAlsoAnnotation annotation 
				= (XmlSeeAlsoAnnotation) getJavaResourceType().addAnnotation(JAXB.XML_SEE_ALSO);
		
		XmlSeeAlso xmlSeeAlso = buildXmlSeeAlso(annotation);
		setXmlSeeAlso_(xmlSeeAlso);
		return xmlSeeAlso;
	}
	
	public void removeXmlSeeAlso() {
		if (this.xmlSeeAlso == null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().removeAnnotation(JAXB.XML_SEE_ALSO);
		setXmlSeeAlso_(null);
	}
	
	protected XmlSeeAlsoAnnotation getXmlSeeAlsoAnnotation() {
		return (XmlSeeAlsoAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_SEE_ALSO);
	}
	
	protected XmlSeeAlso buildXmlSeeAlso(XmlSeeAlsoAnnotation annotation) {
		return new GenericJavaXmlSeeAlso(this, annotation);
	}
	
	protected void initializeXmlSeeAlso() {
		XmlSeeAlsoAnnotation annotation = getXmlSeeAlsoAnnotation();
		this.xmlSeeAlso = (annotation == null) ?
				null
				: buildXmlSeeAlso(annotation);
	}
	
	protected void syncXmlSeeAlso() {
		XmlSeeAlsoAnnotation annotation = getXmlSeeAlsoAnnotation();
		if (annotation != null) {
			if (this.xmlSeeAlso != null) {
				this.xmlSeeAlso.synchronizeWithResourceModel();
			}
			else {
				setXmlSeeAlso_(buildXmlSeeAlso(annotation));
			}
		}
		else {
			setXmlSeeAlso_(null);
		}
	}
	
	protected void updateXmlSeeAlso() {
		if (this.xmlSeeAlso != null) {
			this.xmlSeeAlso.update();
		}
	}
	
	
	// ***** misc *****
	
	public final Iterable<String> getReferencedXmlTypeNames() {
		return (this.xmlTransient) ?
				getTransientReferencedXmlTypeNames()
				: getNonTransientReferencedXmlTypeNames(); 
	}
	
	protected Iterable<String> getTransientReferencedXmlTypeNames() {
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getNonTransientReferencedXmlTypeNames() {
		if (this.xmlSeeAlso != null) {
			return this.xmlSeeAlso.getReferencedXmlTypeNames();
		}
		return EmptyIterable.instance();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		
		JaxbPackage jaxbPackage = getJaxbPackage();
		if (jaxbPackage != null) {
			getJaxbProject().getSchemaLibrary().refreshSchema(jaxbPackage.getNamespace());
		}
		
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlRootElement != null) {
			result = this.xmlRootElement.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = getXmlTypeAnnotation().getTextRange();
		return (textRange != null) ? textRange : getJavaType().getValidationTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (! this.xmlTransient) {
			this.qName.validate(messages, reporter);
			
			if (this.xmlRootElement != null) {
				this.xmlRootElement.validate(messages, reporter);
			}
		}
	}
	
	
	// ***** misc *****
	
	public XsdTypeDefinition getXsdTypeDefinition() {
		JaxbPackage jaxbPackage = getJaxbPackage();
		XsdSchema xsdSchema = (jaxbPackage == null) ? null : jaxbPackage.getXsdSchema();
		if (xsdSchema == null) {
			return null;
		}
		
		if (! StringTools.isBlank(this.qName.getName())) {
			return xsdSchema.getTypeDefinition(this.qName.getNamespace(), this.qName.getName());
		}
		
		if (this.xmlRootElement != null) {
			XsdElementDeclaration xsdElement 
					= xsdSchema.getElementDeclaration(
							this.xmlRootElement.getQName().getNamespace(), 
							this.xmlRootElement.getQName().getName());
			if (xsdElement != null) {
				return xsdElement.getType();
			}
		}
		
		return null;
	}
	
	public boolean hasRootElementInHierarchy() {
		return this.xmlRootElement != null;
	}
	
	
	protected class XmlTypeQName
			extends AbstractQName {
		
		protected XmlTypeQName(JaxbContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return AbstractJavaTypeMapping.this.getJavaType().getJaxbPackage();
		}
		
		@Override
		protected String buildDefaultNamespace() {
			JaxbPackage jaxbPackage = AbstractJavaTypeMapping.this.getJavaType().getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		protected String buildDefaultName() {
			return Introspector.decapitalize(AbstractJavaTypeMapping.this.getJavaType().getTypeName().getSimpleName());
		}
		
		@Override
		protected Iterable<String> getNamespaceProposals() {
			XsdSchema schema = this.getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getNamespaceProposals();
		}
		
		@Override
		protected Iterable<String> getNameProposals() {
			XsdSchema schema = this.getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getTypeNameProposals(getNamespace());
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_TYPE_DESC;
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter) {
			// do not call super... - it is not an error if the name is ""
			// if name is absent (""), namespace cannot be different from package namespace
			if ("".equals(getName()) 
					&& ! ObjectTools.equals(
							getNamespace(),
							buildDefaultNamespace())) {
				messages.add(
					this.buildErrorValidationMessage(
						JptJaxbCoreValidationMessages.XML_TYPE__UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE,
						getXmlTypeAnnotation().getNamespaceTextRange()));
			}
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			// if name is not absent (""), type must be from schema associated with this package
			String name = getName();
			String namespace = getNamespace();
			
			if (! StringTools.isBlank(name)) {
				XsdSchema schema = this.getXsdSchema();
				if (schema != null) {
					XsdTypeDefinition schemaType = schema.getTypeDefinition(namespace, name);
					if (schemaType == null) {
						messages.add(getUnresolveSchemaComponentMessage());
					}
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return AbstractJavaTypeMapping.this.getXmlTypeAnnotation();
		}
	}
}
