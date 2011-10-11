/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaTypeMapping
		extends AbstractJavaContextNode
		implements JaxbTypeMapping {
	
	protected boolean xmlTransient;
	
	protected final JaxbQName qName;
	
	protected XmlRootElement xmlRootElement;
	
	protected XmlSeeAlso xmlSeeAlso;
	
	
	protected AbstractJavaTypeMapping(JaxbType parent) {
		super(parent);
		initXmlTransient();
		this.qName = buildQName(); 
		initXmlRootElement();
		initializeXmlSeeAlso();
	}
	
	
	public JaxbType getJaxbType() {
		return (JaxbType) getParent();
	}
	
	protected JavaResourceAbstractType getJavaResourceType() {
		return getJaxbType().getJavaResourceType();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getJaxbType().getJaxbPackage();
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
		getJavaResourceType().addAnnotation(JAXB.XML_TRANSIENT);
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
		if (! this.xmlTransient) {
			return getNonTransientReferencedXmlTypeNames(); 
		}
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
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		getJaxbProject().getSchemaLibrary().refreshSchema(getJaxbPackage().getNamespace());
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlRootElement != null) {
			result = this.xmlRootElement.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getXmlTypeAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : getJaxbType().getValidationTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		// TODO - validation for xml transient and anything else
		
		if (! this.xmlTransient) {
			this.qName.validate(messages, reporter, astRoot);
			
			if (this.xmlRootElement != null) {
				this.xmlRootElement.validate(messages, reporter, astRoot);
			}
		}
	}
	
	
	// ***** misc *****
	
	public XsdTypeDefinition getXsdTypeDefinition() {
		XsdSchema xsdSchema = getJaxbPackage().getXsdSchema();
		if (xsdSchema == null) {
			return null;
		}
		
		if (! StringTools.stringIsEmpty(this.qName.getName())) {
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
			extends AbstractJavaQName {
		
		protected XmlTypeQName(JavaContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		public String getDefaultNamespace() {
			return AbstractJavaTypeMapping.this.getJaxbType().getJaxbPackage().getNamespace();
		}
		
		@Override
		public String getDefaultName() {
			return Introspector.decapitalize(AbstractJavaTypeMapping.this.getJaxbType().getSimpleName());
		}
		
		@Override
		protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = AbstractJavaTypeMapping.this.getJaxbType().getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected Iterable<String> getNameProposals(Filter<String> filter) {
			XsdSchema schema = AbstractJavaTypeMapping.this.getJaxbType().getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getTypeNameProposals(getNamespace(), filter);
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_TYPE_DESC;
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// do not call super... - it is not an error if the name is ""
			// if name is absent (""), namespace cannot be different from package namespace
			if ("".equals(getName()) 
					&& ! StringTools.stringsAreEqual(
							getNamespace(), 
							AbstractJavaTypeMapping.this.getJaxbType().getJaxbPackage().getNamespace())) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE,
						this,
						getXmlTypeAnnotation().getNamespaceTextRange(astRoot)));
			}
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// if name is not absent (""), type must be from schema associated with this package
			String name = getName();
			String namespace = getNamespace();
			
			if (! StringTools.stringIsEmpty(name)) {
				XsdSchema schema = AbstractJavaTypeMapping.this.getJaxbType().getJaxbPackage().getXsdSchema();
				
				if (schema != null) {
					XsdTypeDefinition schemaType = schema.getTypeDefinition(namespace, name);
					if (schemaType == null) {
						messages.add(getUnresolveSchemaComponentMessage(astRoot));
					}
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return AbstractJavaTypeMapping.this.getXmlTypeAnnotation();
		}
	}
	
	
	
}
