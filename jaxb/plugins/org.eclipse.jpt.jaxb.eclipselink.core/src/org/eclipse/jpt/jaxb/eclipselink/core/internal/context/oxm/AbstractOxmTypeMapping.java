/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
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
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping;
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
	protected JaxbQName qName;
	
	
	public AbstractOxmTypeMapping(OxmXmlBindings parent, EAbstractTypeMapping eTypeMapping) {
		super(parent);
		this.eTypeMapping = eTypeMapping;
		// 'javaType' is resolved in the update
		initTypeName();
		initXmlTransient();
		this.qName = buildQName(); 
	}
	
	
	protected OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) super.getParent();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncJavaType();
		syncXmlTransient();
		this.qName.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		updateTypeName();
		updateJavaType();
		updateXmlTransient();
		this.qName.update();
	}
	
	
	// *****
	
	public EAbstractTypeMapping getETypeMapping() {
		return this.eTypeMapping;
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
			JavaResourceType resourceType = this.resolveJavaResourceType();
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
	
	protected abstract JavaType buildJavaType(JavaResourceType resourceType);
	
	protected JavaResourceType resolveJavaResourceType() {
		String fqName = getTypeName().getFullyQualifiedName();
		if (StringTools.isBlank(fqName)) {
			return null;
		}
		// return type whether it's a class, interface, or enum. 
		// building javaType will resolve problem if there is one.
		return (JavaResourceType) getJaxbProject().getJavaResourceType(fqName);
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
	
	public XmlRootElement getXmlRootElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public XmlRootElement addXmlRootElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeXmlRootElement() {
		// TODO Auto-generated method stub
		
	}
	
	
	// ***** xml see also *****
	
	public XmlSeeAlso getXmlSeeAlso() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public XmlSeeAlso addXmlSeeAlso() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeXmlSeeAlso() {
		// TODO Auto-generated method stub
		
	}
	
	
	// ***** misc *****
	
	public Iterable<String> getReferencedXmlTypeNames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public XsdTypeDefinition getXsdTypeDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean hasRootElementInHierarchy() {
		// TODO Auto-generated method stub
		return false;
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
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_TYPE__UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE,
						this,
						this.proxy.getNamespaceTextRange()));
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
