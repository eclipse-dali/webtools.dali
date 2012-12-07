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

import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbClass;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmJavaTypeImpl
		extends AbstractOxmJaxbType<JavaClass>
		implements OxmJavaType {
	
	protected EJavaType eJavaType;
	
	protected String specifiedName;
	
	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;
	
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	protected final Vector<OxmJavaAttribute> specifiedAttributes;
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter;
	
	
	public OxmJavaTypeImpl(OxmXmlBindings parent, EJavaType eJavaType) {
		super(parent);
		this.eJavaType = eJavaType;
		
		this.specifiedAttributes = new Vector<OxmJavaAttribute>();
		this.specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();
		
		initSpecifiedName();
		initQualifiedName();
		initSpecifiedAccessOrder();
		initDefaultAccessOrder();
		initSpecifiedAccessType();
		initDefaultAccessType();
		initSpecifiedAttributes();
	}
	
	
	public OxmXmlBindings getXmlBindings() {
		return (OxmXmlBindings) getParent();
	}
	
	public EJavaType getEJavaType() {
		return this.eJavaType;
	}
	
	@Override
	protected JavaClass buildJavaType(JavaResourceType resourceType) {
		return new GenericJavaJaxbClass(this, resourceType);
	}
	
	protected JaxbClassMapping getJavaClassMapping() {
		return (this.javaType == null) ? null : this.javaType.getMapping();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		setQualifiedName_(buildQualifiedName());
		syncSpecifiedAccessOrder();
		syncSpecifiedAccessType();
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedAttributeContainerAdapter);
	}
	
	@Override
	public void update() {
		super.update();
		updateDefaultAccessOrder();
		updateDefaultAccessType();
		ContextContainerTools.update(this.specifiedAttributeContainerAdapter);
	}
	
	
	// ***** name *****
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newName) {
		this.eJavaType.setName(newName);
		setSpecifiedName_(newName);
	}
	
	protected void setSpecifiedName_(String newName) {
		String oldName = this.specifiedName;
		this.specifiedName = newName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldName, newName);
	}
	
	protected void initSpecifiedName() {
		this.specifiedName = buildSpecifiedName();
	}
	
	protected String buildSpecifiedName() {
		return this.eJavaType.getName();
	}
	
	protected void initQualifiedName() {
		this.qualifiedName = buildQualifiedName();
	}
	
	@Override
	protected String buildQualifiedName() {
		return getXmlBindings().getQualifiedName(this.specifiedName);
	}
	
	
	// ***** XmlAccessorOrder *****
	
	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.defaultAccessOrder;
	}
	
	public XmlAccessOrder getDefaultAccessOrder() {
		return this.defaultAccessOrder;
	}
	
	protected void setDefaultAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.defaultAccessOrder;
		this.defaultAccessOrder = accessOrder;
		firePropertyChanged(DEFAULT_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}
	
	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		this.eJavaType.setXmlAccessorOrder(ELXmlAccessOrder.toOxmResourceModel(accessOrder));
		setSpecifiedAccessOrder_(accessOrder);
	}
	
	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}
	
	protected void initDefaultAccessOrder() {
		this.defaultAccessOrder = buildDefaultAccessOrder();
	}
	
	protected void updateDefaultAccessOrder() {
		setDefaultAccessOrder_(buildDefaultAccessOrder());
	}
	
	/**
	 * Default access order rules are TBD.  For now we
	 * - check if specified on java class
	 * - if not, check if specified on xml bindings
	 * - if not, return UNDEFINED
	 */
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder access;
		
		// TODO
//		access = getSuperclassAccessOrder();
//		if (access != null) {
//			return access;
//		}
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessOrder();
				if (access != null) {
					return access;
				}
			}
		}
		access = getXmlBindings().getSpecifiedAccessOrder();
		if (access != null) {
			return access;
		}
		
		return XmlAccessOrder.UNDEFINED;
	}
	
	protected void initSpecifiedAccessOrder() {
		this.specifiedAccessOrder = buildSpecifiedAccessOrder();
	}
	
	protected void syncSpecifiedAccessOrder() {
		setSpecifiedAccessOrder_(buildSpecifiedAccessOrder());
	}
	
	protected XmlAccessOrder buildSpecifiedAccessOrder() {
		return ELXmlAccessOrder.fromOxmResourceModel(this.eJavaType.getXmlAccessorOrder());
	}
	
	
	// ***** XmlAccessorType *****
	
	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.defaultAccessType;
	}
	
	public XmlAccessType getDefaultAccessType() {
		return this.defaultAccessType;
	}
	
	protected void setDefaultAccessType_(XmlAccessType access) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = access;
		firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType access) {
		this.eJavaType.setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(access));
		setSpecifiedAccessType_(access);
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	protected void initDefaultAccessType() {
		this.defaultAccessType = buildDefaultAccessType();
	}
	
	protected void updateDefaultAccessType() {
		setDefaultAccessType_(buildDefaultAccessType());
	}
	
	/**
	 * Default access order rules are TBD.  For now we
	 * - check if specified on java class
	 * - if not, check if specified on xml bindings
	 * - if not, return PUBLIC_MEMBER
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType access;
		
		// TODO
//		access = getSuperclassAccessType();
//		if (access != null) {
//			return access;
//		}
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessType();
				if (access != null) {
					return access;
				}
			}
		}
		
		access = getXmlBindings().getSpecifiedAccessType();
		if (access != null) {
			return access;
		}
		
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	protected void initSpecifiedAccessType() {
		this.specifiedAccessType = buildSpecifiedAccessType();
	}
	
	protected void syncSpecifiedAccessType() {
		setSpecifiedAccessType_(buildSpecifiedAccessType());
	}
	
	protected XmlAccessType buildSpecifiedAccessType() {
		return ELXmlAccessType.fromOxmResourceModel(this.eJavaType.getXmlAccessorType());
	}
	
	
	// ***** specified java attributes *****
	
	public ListIterable<OxmJavaAttribute> getSpecifiedAttributes() {
		return new LiveCloneListIterable<OxmJavaAttribute>(this.specifiedAttributes);
	}
	
	public int getSpecifiedAttributesSize() {
		return this.specifiedAttributes.size();
	}
	
	protected void addSpecifiedAttribute_(int index, EJavaAttribute resourceAttribute) {
		OxmJavaAttribute attribute = buildSpecifiedAttribute(resourceAttribute);
		addItemToList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void removeSpecifiedAttribute_(OxmJavaAttribute attribute) {
		this.removeItemFromList(attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void moveSpecifiedAttribute_(int index, OxmJavaAttribute attribute) {
		moveItemInList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void initSpecifiedAttributes() {
		for (EJavaAttribute eJavaAttribute : this.eJavaType.getJavaAttributes()) {
			this.specifiedAttributes.add(buildSpecifiedAttribute(eJavaAttribute));
		}
	}
	
	protected OxmJavaAttribute buildSpecifiedAttribute(EJavaAttribute resourceAttribute) {
		// If this gets weighty or duplicated, we can move it
		String elementName = resourceAttribute.getElementName();
		if (ObjectTools.equals(Oxm.XML_ELEMENT, elementName)) {
			return new OxmXmlElementImpl(this, (EXmlElement) resourceAttribute);
		}
		
		// ?
		return null;
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eJavaType.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
	
	protected TextRange getNameTextRange() {
		return this.eJavaType.getNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateName(messages, reporter);
		
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter) {
		// type name must be specified
		if (StringTools.isBlank(this.specifiedName)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__NAME_NOT_SPECIFIED,
							this,
							getNameTextRange()));
			return;
		}
		
		// package name must be uniform across oxm file
		String packageName = TypeDeclarationTools.packageName(this.specifiedName);
		if (! StringTools.isBlank(packageName) && ! ObjectTools.equals(packageName, getXmlBindings().getPackageName())) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__PACKAGE_NAME_NOT_UNIFORM,
							this,
							getNameTextRange()));
		}
	}
	
	
	/**
	 * specified attribute container adapter
	 */
	protected class SpecifiedAttributeContainerAdapter
			implements ContextContainerTools.Adapter<OxmJavaAttribute, EJavaAttribute> {
		
		public Iterable<OxmJavaAttribute> getContextElements() {
			return OxmJavaTypeImpl.this.getSpecifiedAttributes();
		}
		
		public Iterable<EJavaAttribute> getResourceElements() {
			return OxmJavaTypeImpl.this.eJavaType.getJavaAttributes();
		}
		
		public EJavaAttribute getResourceElement(OxmJavaAttribute contextElement) {
			return contextElement.getEJavaAttribute();
		}
		
		public void addContextElement(int index, EJavaAttribute resourceElement) {
			OxmJavaTypeImpl.this.addSpecifiedAttribute_(index, resourceElement);
		}
		
		public void removeContextElement(OxmJavaAttribute element) {
			OxmJavaTypeImpl.this.removeSpecifiedAttribute_(element);
		}
		
		public void moveContextElement(int index, OxmJavaAttribute element) {
			OxmJavaTypeImpl.this.moveSpecifiedAttribute_(index, element);
		}
	}
}
