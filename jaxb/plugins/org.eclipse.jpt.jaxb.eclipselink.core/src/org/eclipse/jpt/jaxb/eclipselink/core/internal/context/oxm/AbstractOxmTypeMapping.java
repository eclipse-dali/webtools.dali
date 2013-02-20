/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.context.AbstractQName;
import org.eclipse.jpt.jaxb.core.context.AbstractQName.ResourceProxy;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlRootElement;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSeeAlso;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlRootElement;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOxmTypeMapping
		extends AbstractJaxbContextNode
		implements OxmTypeMapping {
	
	protected EAbstractTypeMapping eTypeMapping;
	
	// typeName - never null
	protected TypeName typeName;
	
	// hold on to java resource type for validation purposes
	protected JavaResourceType javaResourceType;
	protected JavaType javaType;
	
	// xmlTransient
	protected boolean defaultXmlTransient =  false;
	protected Boolean specifiedXmlTransient;
	protected boolean xmlTransient;
	
	// qName - never null
	protected final JaxbQName qName;
	
	// xmlRootElement
	protected XmlRootElement defaultXmlRootElement;
	protected OxmXmlRootElement specifiedXmlRootElement;
	protected XmlRootElement xmlRootElement;
	
	// xmlSeeAlso
	protected XmlSeeAlso defaultXmlSeeAlso;
	protected OxmXmlSeeAlso specifiedXmlSeeAlso;
	protected XmlSeeAlso xmlSeeAlso;
	
	
	public AbstractOxmTypeMapping(OxmXmlBindings parent, EAbstractTypeMapping eTypeMapping) {
		super(parent);
		this.eTypeMapping = eTypeMapping;
		// 'javaType' is resolved in the update
		initTypeName();
		initXmlTransient();
		this.qName = buildQName();
		initXmlRootElement();
		initXmlSeeAlso();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncJavaType();
		syncXmlTransient();
		this.qName.synchronizeWithResourceModel();
		syncXmlRootElement();
		syncXmlSeeAlso();
	}
	
	@Override
	public void update() {
		super.update();
		updateTypeName();
		updateJavaType();
		updateXmlTransient();
		this.qName.update();
		updateXmlRootElement();
		updateXmlSeeAlso();
	}
	
	
	// ***** misc ****
	
	public EAbstractTypeMapping getETypeMapping() {
		return this.eTypeMapping;
	}
	
	public OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) super.getParent();
	}
	
	public ELJaxbPackage getJaxbPackage() {
		return getXmlBindings().getOxmFile().getJaxbPackage();
	}
	
	
	// ***** type name *****
	
	public TypeName getTypeName() {
		return this.typeName;
	}
	
	protected void setTypeName_(TypeName typeName) {
		TypeName old = this.typeName;
		this.typeName = typeName;
		if (firePropertyChanged(TYPE_NAME_PROPERTY, old, typeName)) {
			// If the qualified name changes, null out the java type to avoid syncing *it*.
			// That's just wasted cycles.
			// It'll get reset in update.
			setJavaType(null);
		}
	}
	
	protected void initTypeName() {
		this.typeName = buildTypeName();
	}
	
	protected abstract void updateTypeName();
	
	protected abstract TypeName buildTypeName();
	
	
	// ***** java type *****
	
	public JavaType getJavaType() {
		return this.javaType;
	}
	
	protected void setJavaType(JavaType javaType) {
		JavaType oldJavaType = this.javaType;
		this.javaType = javaType;
		firePropertyChanged(JAVA_TYPE_PROPERTY, oldJavaType, javaType);
	}
	
	protected void syncJavaType() {
		if (this.javaType != null) {
			this.javaType.synchronizeWithResourceModel();
		}
	}
	
	protected void updateJavaType() {
		if (StringTools.isBlank(this.typeName.getFullyQualifiedName())) {
			setJavaType(null);
		}
		else {
			JavaResourceAbstractType resourceType = this.resolveJavaResourceType();
			if (resourceType != null) {
				if (this.javaType == null 
						// using == here because it is possible that the names are the same, but the location has changed
						// (e.g. the java resource type has moved from binary to source, or vice versa)
						|| this.javaType.getJavaResourceType() != resourceType) {
					setJavaType(buildJavaType(resourceType));
				}
				else if (this.javaType != null) {
					this.javaType.update();
				}
			}
			else {
				setJavaType(null);
			}
		}
	}
	
	protected abstract JavaType buildJavaType(JavaResourceAbstractType resourceType);
	
	protected JavaResourceAbstractType resolveJavaResourceType() {
		String fqName = getTypeName().getFullyQualifiedName();
		if (StringTools.isBlank(fqName)) {
			return null;
		}
		// return type whether it's a class, interface, or enum. 
		// building javaType will resolve problem if there is one.
		return (JavaResourceAbstractType) getJaxbProject().getJavaResourceType(fqName);
	}
	
	public JavaTypeMapping getJavaTypeMapping() {
		return (this.javaType == null) ? null : this.javaType.getMapping();
	}
	
	
	// ***** xml transient *****
	
	public boolean isDefaultXmlTransient() {
		return this.defaultXmlTransient;
	}
	
	protected void setDefaultXmlTransient_(boolean newValue) {
		boolean old = this.defaultXmlTransient;
		this.defaultXmlTransient = newValue;
		firePropertyChanged(DEFAULT_XML_TRANSIENT_PROPERTY, old, newValue);
	}
	
	public Boolean getSpecifiedXmlTransient() {
		return this.specifiedXmlTransient;
	}
	
	public void setSpecifiedXmlTransient(Boolean newValue) {
		setSpecifiedXmlTransient_(newValue);
		this.eTypeMapping.setXmlTransient(newValue);
	}
	
	protected void setSpecifiedXmlTransient_(Boolean newValue) {
		Boolean old = this.specifiedXmlTransient;
		this.specifiedXmlTransient = newValue;
		firePropertyChanged(SPECIFIED_XML_TRANSIENT_PROPERTY, old, newValue);
	}
	
	public boolean isXmlTransient() {
		return this.xmlTransient;
	}
	
	protected void setXmlTransient_(boolean newValue) {
		boolean oldValue = this.xmlTransient;
		this.xmlTransient = newValue;
		firePropertyChanged(XML_TRANSIENT_PROPERTY, oldValue, newValue);
	}
	
	protected void initXmlTransient() {
		this.specifiedXmlTransient = this.eTypeMapping.getXmlTransient();
	}
	
	protected void syncXmlTransient() {
		setSpecifiedXmlTransient_(this.eTypeMapping.getXmlTransient());
	}
	
	protected void updateXmlTransient() {
		boolean defaultXmlTransient = false;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JavaTypeMapping javaMapping = getJavaTypeMapping();
			if (javaMapping != null) {
				defaultXmlTransient = javaMapping.isXmlTransient();
			}
		}
		
		setDefaultXmlTransient_(defaultXmlTransient);
		
		boolean xmlTransient = 
				(this.specifiedXmlTransient != null) ? 
						this.specifiedXmlTransient.booleanValue() 
						: this.defaultXmlTransient;
						
		setXmlTransient_(xmlTransient);
	}
	
	
	// ***** qName *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	protected JaxbQName buildQName() {
		return new XmlTypeQName(this);
	}
	
	
	// ***** xml root element *****
	
	public XmlRootElement getDefaultXmlRootElement() {
		return this.defaultXmlRootElement;
	}
	
	protected void setDefaultXmlRootElement_(XmlRootElement xmlRootElement) {
		XmlRootElement old = this.defaultXmlRootElement;
		this.defaultXmlRootElement = xmlRootElement;
		firePropertyChanged(DEFAULT_XML_ROOT_ELEMENT_PROPERTY, old, xmlRootElement);
	}
	
	public OxmXmlRootElement getSpecifiedXmlRootElement() {
		return this.specifiedXmlRootElement;
	}
	
	protected void setSpecifiedXmlRootElement_(OxmXmlRootElement xmlRootElement) {
		OxmXmlRootElement old = this.specifiedXmlRootElement;
		this.specifiedXmlRootElement = xmlRootElement;
		firePropertyChanged(SPECIFIED_XML_ROOT_ELEMENT_PROPERTY, old, xmlRootElement);
	}
	
	public OxmXmlRootElement addSpecifiedXmlRootElement() {
		EXmlRootElement eXmlRootElement = OxmFactory.eINSTANCE.createEXmlRootElement();
		OxmXmlRootElement xmlRootElement = buildSpecifiedXmlRootElement(eXmlRootElement);
		setSpecifiedXmlRootElement_(xmlRootElement);
		this.eTypeMapping.setXmlRootElement(eXmlRootElement);
		return xmlRootElement;
	}
	
	public void removeSpecifiedXmlRootElement() {
		this.eTypeMapping.setXmlRootElement(null);
		setSpecifiedXmlRootElement_(null);
	}
	
	protected OxmXmlRootElement buildSpecifiedXmlRootElement(EXmlRootElement eXmlRootElement) {
		return new OxmXmlRootElementImpl(this, eXmlRootElement);
	}
	
	public XmlRootElement getXmlRootElement() {
		return this.xmlRootElement;
	}
	
	protected void setXmlRootElement_(XmlRootElement xmlRootElement) {
		XmlRootElement old = this.xmlRootElement;
		this.xmlRootElement = xmlRootElement;
		firePropertyChanged(XML_ROOT_ELEMENT_PROPERTY, old, xmlRootElement);
	}
	
	protected void initXmlRootElement() {
		EXmlRootElement eXmlRootElement = this.eTypeMapping.getXmlRootElement();
		this.specifiedXmlRootElement = (eXmlRootElement == null) ? null : buildSpecifiedXmlRootElement(eXmlRootElement);
	}
	
	protected void syncXmlRootElement() {
		EXmlRootElement eXmlRootElement = this.eTypeMapping.getXmlRootElement();
		if (eXmlRootElement != null) {
			if (this.specifiedXmlRootElement != null) {
				this.specifiedXmlRootElement.synchronizeWithResourceModel();
			}
			else {
				setSpecifiedXmlRootElement_(buildSpecifiedXmlRootElement(eXmlRootElement));
			}
		}
		else {
			if (this.specifiedXmlRootElement != null) {
				setSpecifiedXmlRootElement_(null);
			}
		}
	}
	
	protected void updateXmlRootElement() {
		XmlRootElement defaultXmlRootElement = null;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JavaTypeMapping javaMapping = getJavaTypeMapping();
			if (javaMapping != null) {
				defaultXmlRootElement = javaMapping.getXmlRootElement();
			}
		}
		
		setDefaultXmlRootElement_(defaultXmlRootElement);
		
		XmlRootElement xmlRootElement = 
				(this.specifiedXmlRootElement != null) ?
						this.specifiedXmlRootElement
						: this.defaultXmlRootElement;
		setXmlRootElement_(xmlRootElement);
		
		if (this.specifiedXmlRootElement != null) {
			this.specifiedXmlRootElement.update();
		}
	}
	
	
	// ***** xml see also *****
	
	public XmlSeeAlso getDefaultXmlSeeAlso() {
		return this.defaultXmlSeeAlso;
	}
	
	protected void setDefaultXmlSeeAlso_(XmlSeeAlso xmlSeeAlso) {
		XmlSeeAlso old = this.defaultXmlSeeAlso;
		this.defaultXmlSeeAlso = xmlSeeAlso;
		firePropertyChanged(DEFAULT_XML_SEE_ALSO_PROPERTY, old, xmlSeeAlso);
	}
	
	public OxmXmlSeeAlso getSpecifiedXmlSeeAlso() {
		return this.specifiedXmlSeeAlso;
	}
	
	protected void setSpecifiedXmlSeeAlso_(OxmXmlSeeAlso xmlSeeAlso) {
		OxmXmlSeeAlso old = this.specifiedXmlSeeAlso;
		this.specifiedXmlSeeAlso = xmlSeeAlso;
		firePropertyChanged(SPECIFIED_XML_SEE_ALSO_PROPERTY, old, xmlSeeAlso);
	}
	
	public OxmXmlSeeAlso addSpecifiedXmlSeeAlso() {
		EXmlSeeAlso eXmlSeeAlso = OxmFactory.eINSTANCE.createEXmlSeeAlso();
		OxmXmlSeeAlso xmlSeeAlso = buildSpecifiedXmlSeeAlso(eXmlSeeAlso);
		setSpecifiedXmlSeeAlso_(xmlSeeAlso);
		this.eTypeMapping.setXmlSeeAlso(eXmlSeeAlso);
		return xmlSeeAlso;
	}
	
	public void removeSpecifiedXmlSeeAlso() {
		this.eTypeMapping.setXmlSeeAlso(null);
		setSpecifiedXmlSeeAlso_(null);
	}
	
	protected OxmXmlSeeAlso buildSpecifiedXmlSeeAlso(EXmlSeeAlso eXmlSeeAlso) {
		return new OxmXmlSeeAlsoImpl(this, eXmlSeeAlso);
	}
	
	public XmlSeeAlso getXmlSeeAlso() {
		return this.xmlSeeAlso;
	}
	
	protected void setXmlSeeAlso_(XmlSeeAlso xmlSeeAlso) {
		XmlSeeAlso old = this.xmlSeeAlso;
		this.xmlSeeAlso = xmlSeeAlso;
		firePropertyChanged(XML_SEE_ALSO_PROPERTY, old, xmlSeeAlso);
	}
	
	protected void initXmlSeeAlso() {
		EXmlSeeAlso eXmlSeeAlso = this.eTypeMapping.getXmlSeeAlso();
		this.specifiedXmlSeeAlso = (eXmlSeeAlso == null) ? null : buildSpecifiedXmlSeeAlso(eXmlSeeAlso);
	}
	
	protected void syncXmlSeeAlso() {
		EXmlSeeAlso eXmlSeeAlso = this.eTypeMapping.getXmlSeeAlso();
		if (eXmlSeeAlso != null) {
			if (this.specifiedXmlSeeAlso != null) {
				this.specifiedXmlSeeAlso.synchronizeWithResourceModel();
			}
			else {
				setSpecifiedXmlSeeAlso_(buildSpecifiedXmlSeeAlso(eXmlSeeAlso));
			}
		}
		else {
			if (this.specifiedXmlSeeAlso != null) {
				setSpecifiedXmlSeeAlso_(null);
			}
		}
	}
	
	protected void updateXmlSeeAlso() {
		XmlSeeAlso defaultXmlSeeAlso = null;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JavaTypeMapping javaMapping = getJavaTypeMapping();
			if (javaMapping != null) {
				defaultXmlSeeAlso = javaMapping.getXmlSeeAlso();
			}
		}
		
		setDefaultXmlSeeAlso_(defaultXmlSeeAlso);
		
		XmlSeeAlso xmlSeeAlso = 
				(this.specifiedXmlSeeAlso != null) ?
						this.specifiedXmlSeeAlso
						: this.defaultXmlSeeAlso;
		setXmlSeeAlso_(xmlSeeAlso);
		
		if (this.specifiedXmlSeeAlso != null) {
			this.specifiedXmlSeeAlso.update();
		}
	}
	
	
	// ***** misc *****
	
	public Iterable<String> getReferencedXmlTypeNames() {
		return (isXmlTransient()) ?
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
			super(parent, new EXmlTypeProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return AbstractOxmTypeMapping.this.getJaxbPackage();
		}
		
		@Override
		protected String buildDefaultNamespace() {
			if (! AbstractOxmTypeMapping.this.getXmlBindings().isXmlMappingMetadataComplete()) {
				JaxbTypeMapping javaMapping = AbstractOxmTypeMapping.this.getJavaTypeMapping();
				if (javaMapping != null) {
					String namespace = javaMapping.getQName().getSpecifiedNamespace();
					if (namespace != null) {
						return namespace;
					}
				}
			}
			
			return getXmlBindings().getXmlSchema().getNamespace();
		}
		
		@Override
		protected String buildDefaultName() {
			if (! AbstractOxmTypeMapping.this.getXmlBindings().isXmlMappingMetadataComplete()) {
				JaxbTypeMapping javaMapping = AbstractOxmTypeMapping.this.getJavaTypeMapping();
				if (javaMapping != null) {
					String name = javaMapping.getQName().getSpecifiedName();
					if (name != null) {
						return name;
					}
				}
			}
			
			return Introspector.decapitalize(AbstractOxmTypeMapping.this.getTypeName().getSimpleName());
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
							this.getNamespace(),
							this.buildDefaultNamespace())) {
				messages.add(
					this.buildErrorValidationMessage(
						this.proxy.getNamespaceTextRange(),
						JptJaxbCoreValidationMessages.XML_TYPE__UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE
					));
			}
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			// if name is not absent (""), type must be from schema associated with this package
			String name = this.getName();
			String namespace = this.getNamespace();
			
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
	
	
	protected class EXmlTypeProxy
			implements ResourceProxy {
		
		protected EXmlType getEXmlType(boolean createIfNull) {
			EXmlType eXmlType = AbstractOxmTypeMapping.this.eTypeMapping.getXmlType();
			if (eXmlType == null && createIfNull) {
				eXmlType = OxmFactory.eINSTANCE.createEXmlType();
				AbstractOxmTypeMapping.this.eTypeMapping.setXmlType(eXmlType);
			}
			return eXmlType;
		}
		
		public String getNamespace() {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? null : eXmlType.getNamespace();
		}
		
		public void setNamespace(String namespace) {
			getEXmlType(true).setNamespace(namespace);
		}
		
		public boolean namespaceTouches(int pos) {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? false : eXmlType.namespaceTouches(pos);
		}
		
		public TextRange getNamespaceTextRange() {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? null : eXmlType.getNamespaceTextRange();
		}
		
		public String getName() {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? null : eXmlType.getName();
		}
		
		public void setName(String name) {
			getEXmlType(true).setName(name);
		}
		
		public boolean nameTouches(int pos) {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? false : eXmlType.nameTouches(pos);
		}
		
		public TextRange getNameTextRange() {
			EXmlType eXmlType = getEXmlType(false);
			return (eXmlType == null) ? null : eXmlType.getNameTextRange();
		}
	}
}
