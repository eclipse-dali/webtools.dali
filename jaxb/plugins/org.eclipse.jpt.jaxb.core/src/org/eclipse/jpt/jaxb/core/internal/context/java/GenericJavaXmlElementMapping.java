/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementWrapper;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlIDREF.ValidatableReference;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlElementMapping
		extends AbstractJavaXmlNamedNodeMapping<XmlElementAnnotation>
		implements XmlElementMapping {
	
	protected final XmlElement xmlElement;
	
	protected XmlElementWrapper xmlElementWrapper;
	
	
	public GenericJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlElement = buildXmlElement();
		initializeXmlElementWrapper();			
	}
	
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return JAXB.XML_ELEMENT;
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlElement.synchronizeWithResourceModel();
		syncXmlElementWrapper();
	}
	
	@Override
	public void update() {
		super.update();
		this.xmlElement.update();
		updateXmlElementWrapper();
	}
	
	
	// ***** XmlElement *****
	
	public XmlElement getXmlElement() {
		return this.xmlElement;
	}
	
	protected XmlElement buildXmlElement() {
		return new GenericJavaXmlElement(this, new XmlElementContext());
	}
	
	
	// ***** XmlElementWrapper *****
	
	public XmlElementWrapper getXmlElementWrapper() {
		return this.xmlElementWrapper;
	}
	
	protected XmlElementWrapper buildXmlElementWrapper() {
		return new GenericJavaXmlElementWrapper(this, new GenericJavaXmlElementWrapper.SimpleContext(getXmlElementWrapperAnnotation()));
	}
	
	public XmlElementWrapper addXmlElementWrapper() {
		if (this.xmlElementWrapper != null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().addAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		XmlElementWrapper xmlElementWrapper = buildXmlElementWrapper();
		setXmlElementWrapper_(xmlElementWrapper);
		return xmlElementWrapper;
	}
	
	public void removeXmlElementWrapper() {
		if (this.xmlElementWrapper == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		setXmlElementWrapper_(null);
	}
	
	protected void initializeXmlElementWrapper() {
		if (getXmlElementWrapperAnnotation() != null) {
			this.xmlElementWrapper = buildXmlElementWrapper();
		}
	}
	
	protected XmlElementWrapperAnnotation getXmlElementWrapperAnnotation() {
		return (XmlElementWrapperAnnotation) getJavaResourceAttribute().getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}
	
	protected void syncXmlElementWrapper() {
		if (getXmlElementWrapperAnnotation() != null) {
			if (getXmlElementWrapper() != null) {
				getXmlElementWrapper().synchronizeWithResourceModel();
			}
			else {
				setXmlElementWrapper_(buildXmlElementWrapper());
			}
		}
		else {
			setXmlElementWrapper_(null);
		}
	}
	
	protected void updateXmlElementWrapper() {
		if (getXmlElementWrapper() != null) {
			getXmlElementWrapper().update();
		}
	}
	
	protected void setXmlElementWrapper_(XmlElementWrapper xmlElementWrapper) {
		XmlElementWrapper oldXmlElementWrapper = this.xmlElementWrapper;
		this.xmlElementWrapper = xmlElementWrapper;
		firePropertyChanged(XML_ELEMENT_WRAPPER_PROPERTY, oldXmlElementWrapper, xmlElementWrapper);
	}
	
	
	// ***** XmlList *****
	
	@Override
	protected boolean calculateDefaultXmlList() {
		return false;
	}
	
	
	// ***** XmlIDREF *****
	
	@Override
	protected GenericJavaXmlIDREF.Context buildXmlIDREFContext() {
		return new XmlIDREFContext();
	}
	
	
	// ***** misc *****
	
	@Override
	public Iterable<String> getReferencedXmlTypeNames() {
		return new CompositeIterable<String>(
				super.getReferencedXmlTypeNames(),
				this.xmlElement.getReferencedXmlTypeNames());
	}
	
	@Override
	public boolean isParticleMapping() {
		return true;
	}
	
	public XsdFeature getXsdFeature() {
		XsdTypeDefinition xsdType = getJaxbClassMapping().getXsdTypeDefinition();
		if (xsdType != null) {
			XmlElementWrapper elementWrapper = this.xmlElementWrapper;
			
			if (elementWrapper == null) {
				return xsdType.getElement(this.xmlElement.getQName().getNamespace(), this.xmlElement.getQName().getName());
			}
			else {
				XsdElementDeclaration xsdWrapperElement = elementWrapper.getXsdElementDeclaration();
				if (xsdWrapperElement != null) {
					return xsdWrapperElement.getElement(this.xmlElement.getQName().getNamespace(), this.xmlElement.getQName().getName());
				}
			}
		}		
		return null;	
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlElement.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlElementWrapper != null) {
			result = this.xmlElementWrapper.getCompletionProposals(pos);
			if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		this.xmlElement.validate(messages, reporter);
		
		if (this.xmlElementWrapper != null) {
			this.xmlElementWrapper.validate(messages, reporter);
		}
	}
	
	
	protected class XmlElementContext
			implements GenericJavaXmlElement.Context {
		
		public XmlElementAnnotation getAnnotation(boolean createIfNull) {
			return (createIfNull) ? 
					GenericJavaXmlElementMapping.this.getOrCreateAnnotation() 
					: GenericJavaXmlElementMapping.this.getAnnotation();
		}
		
		public JaxbAttributeMapping getAttributeMapping() {
			return GenericJavaXmlElementMapping.this;
		}
		
		public String getDefaultType() {
			return GenericJavaXmlElementMapping.this.getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
		}
		
		public XmlElementWrapper getElementWrapper() {
			return GenericJavaXmlElementMapping.this.getXmlElementWrapper();
		}
		
		public boolean hasXmlID() {
			return GenericJavaXmlElementMapping.this.getXmlID() != null;
		}
		
		public boolean hasXmlIDREF() {
			return GenericJavaXmlElementMapping.this.getXmlIDREF() != null;
		}
		
		public boolean hasXmlList() {
			return GenericJavaXmlElementMapping.this.isXmlList();
		}
		
		public boolean hasXmlSchemaType() {
			return getXmlSchemaType() != null;
		}
		
		public XmlSchemaType getXmlSchemaType() {
			return GenericJavaXmlElementMapping.this.getXmlSchemaType();
		}
	}
	
	
	protected class XmlIDREFContext
			extends AbstractJavaXmlNamedNodeMapping.XmlIDREFContext {
		
		public Iterable<ValidatableReference> getReferences() {
			
			return new SingleElementIterable<ValidatableReference>(
					
					new ValidatableReference() {
						
						public String getFullyQualifiedType() {
							return GenericJavaXmlElementMapping.this.xmlElement.getType();
						}
						
						public TextRange getTypeTextRange() {
							// 1) if we're getting here, XmlIDREF will not be null
							// 2) if there is an @XmlElement annotation, use that, otherwise use the @XmlIDREF
							return (GenericJavaXmlElementMapping.this.getAnnotation() == null) ?
									GenericJavaXmlElementMapping.this.getXmlIDREF().getValidationTextRange()
									: GenericJavaXmlElementMapping.this.xmlElement.getTypeTextRange();
						}
						
						public XsdFeature getXsdFeature() {
							return GenericJavaXmlElementMapping.this.getXsdFeature();
						}
						
						public TextRange getXsdFeatureTextRange() {
							return GenericJavaXmlElementMapping.this.xmlElement.getQName().getNameTextRange();
						}
					});
		}
	}
}
