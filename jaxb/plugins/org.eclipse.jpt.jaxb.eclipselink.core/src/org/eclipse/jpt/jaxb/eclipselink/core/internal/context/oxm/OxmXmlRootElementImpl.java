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

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.context.AbstractQName;
import org.eclipse.jpt.jaxb.core.context.AbstractQName.ResourceProxy;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlRootElement;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmXmlRootElementImpl
		extends AbstractJaxbContextNode
		implements OxmXmlRootElement {
	
	protected final EXmlRootElement eXmlRootElement;
	
	protected final JaxbQName qName;
	
	
	public OxmXmlRootElementImpl(OxmTypeMapping parent, EXmlRootElement eXmlRootElement) {
		super(parent);
		this.eXmlRootElement = eXmlRootElement;
		this.qName = buildQName();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.qName.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
	}
	
	
	// ***** qName *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlRootElementQName(this);
	}
	
	
	// ***** misc ****
	
	public OxmTypeMapping getTypeMapping() {
		return (OxmTypeMapping) getParent();
	}
	
	protected OxmXmlBindings getXmlBindings() {
		return getTypeMapping().getXmlBindings();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getTypeMapping().getJaxbPackage();
	}
	
	protected JavaTypeMapping getJavaTypeMapping() {
		JavaType javaType = getTypeMapping().getJavaType();
		return (javaType == null) ? null : javaType.getMapping();
	}
	
	
	protected class XmlRootElementQName
			extends AbstractQName {
		
		protected XmlRootElementQName(JaxbContextNode parent) {
			super(parent, new EXmlRootElementProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return OxmXmlRootElementImpl.this.getJaxbPackage();
		}
		
		@Override
		protected String buildDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		protected String buildDefaultName() {
			return Introspector.decapitalize(OxmXmlRootElementImpl.this.getTypeMapping().getTypeName().getSimpleName());
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
			return schema.getElementNameProposals(getNamespace());
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			String name = getName();
			String namespace = getNamespace();
			XsdSchema schema = this.getXsdSchema();
			
			if (schema != null) {
				// element must resolve
				XsdElementDeclaration schemaElement = schema.getElementDeclaration(namespace, name);
				if (schemaElement == null) {
					messages.add(getUnresolveSchemaComponentMessage());
				}
				else {
					// element type must agree with parent's schema type
					XsdTypeDefinition schemaType = OxmXmlRootElementImpl.this.getTypeMapping().getXsdTypeDefinition();
					if (schemaType != null) {
						if (! schemaType.equals(schemaElement.getType())) {
							messages.add(
									this.buildValidationMessage(
										getValidationTextRange(),
										JptJaxbCoreValidationMessages.XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE,
										name,
										namespace));
						}
					}
				}
			}
		}
	}
	
	
	protected class EXmlRootElementProxy
			implements ResourceProxy {
		
		protected EXmlRootElement getEXmlRootElement() {
			return OxmXmlRootElementImpl.this.eXmlRootElement;
		}
		
		public String getNamespace() {
			return getEXmlRootElement().getNamespace();
		}
		
		public void setNamespace(String namespace) {
			getEXmlRootElement().setNamespace(namespace);
		}
		
		public boolean namespaceTouches(int pos) {
			return getEXmlRootElement().namespaceTouches(pos);
		}
		
		public TextRange getNamespaceValidationTextRange() {
			return getEXmlRootElement().getNamespaceValidationTextRange();
		}
		
		public String getName() {
			return getEXmlRootElement().getName();
		}
		
		public void setName(String name) {
			getEXmlRootElement().setName(name);
		}
		
		public boolean nameTouches(int pos) {
			return getEXmlRootElement().nameTouches(pos);
		}
		
		public TextRange getNameValidationTextRange() {
			return getEXmlRootElement().getNameValidationTextRange();
		}
	}
}
