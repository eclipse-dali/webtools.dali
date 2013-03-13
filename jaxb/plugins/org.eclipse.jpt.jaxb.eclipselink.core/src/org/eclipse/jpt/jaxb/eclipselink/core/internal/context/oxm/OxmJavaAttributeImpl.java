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

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFileDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;

public class OxmJavaAttributeImpl
		extends AbstractJaxbContextNode
		implements OxmJavaAttribute {
	
	protected String javaAttributeName;
	
	protected XmlAccessType accessType;
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	protected static final String JAVA_RESOURCE_ATTRIBUTE_PROPERTY = "javaResourceAttribute";  //$NON-NLS-1$
	protected JavaResourceAttribute javaResourceAttribute;
	
	protected OxmAttributeMapping mapping;  // never null
	
	
	protected OxmJavaAttributeImpl(OxmJavaType parent, EJavaAttribute eJavaAttribute) {
		super(parent);
		initMapping(eJavaAttribute);
		initJavaAttributeName();
		initAccessType();
	}
	
	
	public OxmJavaType getClassMapping() {
		return (OxmJavaType) getParent();
	}
	
	protected OxmFileDefinition getOxmFileDefinition() {
		return getClassMapping().getXmlBindings().getOxmFile().getDefinition();
	}
	
	public EJavaAttribute getEJavaAttribute() {
		return this.mapping.getEJavaAttribute();
	}
	
	public String getName() {
		return getJavaAttributeName();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncJavaAttributeName();
		syncAccessType();
		syncMapping();
	}
	
	@Override
	public void update() {
		super.update();
		updateAccessType();
		updateMapping();
	}
	
	
	// ***** java attribute name *****
	
	public String getJavaAttributeName() {
		return this.javaAttributeName;
	}
	
	public void setJavaAttributeName(String newName) {
		getEJavaAttribute().setJavaAttribute(newName);
		setJavaAttributeName_(newName);
	}
	
	protected void setJavaAttributeName_(String newName) {
		String oldName = this.javaAttributeName;
		this.javaAttributeName = newName;
		firePropertyChanged(JAVA_ATTRIBUTE_NAME_PROPERTY, oldName, newName);
	}
	
	protected String getResourceJavaAttributeName() {
		return getEJavaAttribute().getJavaAttribute();
	}
	
	protected void initJavaAttributeName() {
		this.javaAttributeName = getResourceJavaAttributeName();
	}
	
	protected void syncJavaAttributeName() {
		setJavaAttributeName_(getResourceJavaAttributeName());
	}
	
	
	// ***** access type *****
	
	public XmlAccessType getAccessType() {
		return this.accessType;
	}
	
	protected void setAccessType_(XmlAccessType accessType) {
		XmlAccessType old = this.accessType;
		this.accessType = accessType;
		firePropertyChanged(ACCESS_TYPE_PROPERTY, old, accessType);
	}
	
	public XmlAccessType getDefaultAccessType() {
		return this.defaultAccessType;
	}
	
	protected void setDefaultAccessType_(XmlAccessType accessType) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = accessType;
		firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, accessType);
	}
	
	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType accessType) {
		setSpecifiedAccessType_(accessType);
		getEJavaAttribute().setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(accessType));
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType accessType) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = accessType;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, accessType);
	}
	
	protected void initAccessType() {
		this.specifiedAccessType = getResourceAccessType();
	}
	
	protected void syncAccessType() {
		setSpecifiedAccessType_(getResourceAccessType());
	}
	
	protected void updateAccessType() {
		XmlAccessType defaultAccessType = getClassMapping().getAccessType();
		setDefaultAccessType_(defaultAccessType);
		XmlAccessType accessType = (this.specifiedAccessType != null) ? 
				this.specifiedAccessType : this.defaultAccessType;
		setAccessType_(accessType);
	}
	
	protected XmlAccessType getResourceAccessType() {
		return ELXmlAccessType.fromOxmResourceModel(getEJavaAttribute().getXmlAccessorType());
	}
	
	
	// ***** mapping *****
	
	public OxmAttributeMapping getMapping() {
		return this.mapping;
	}
	
	protected void setMapping_(OxmAttributeMapping mapping) {
		OxmAttributeMapping old = this.mapping;
		this.mapping = mapping;
		firePropertyChanged(MAPPING_PROPERTY, old, mapping);
		getClassMapping().attributeMappingChanged(this, old, mapping);
	}
	
	public String getMappingKey() {
		return this.mapping.getKey();
	}
	
	public JaxbAttributeMapping setMappingKey(String key) {
		if (valuesAreDifferent(getMappingKey(), key)) {
			OxmAttributeMappingDefinition mappingDefinition = 
					getOxmFileDefinition().getAttributeMappingDefinitionForKey(key);
			EJavaAttribute eJavaAttribute = 
					mappingDefinition.buildEJavaAttribute();
			setMapping_(mappingDefinition.buildContextMapping(this, eJavaAttribute));
		}
		return this.mapping;
	}
	
	protected void initMapping(EJavaAttribute eJavaAttribute) {
		OxmAttributeMappingDefinition mappingDef = 
				getOxmFileDefinition().getAttributeMappingDefinitionForElement(eJavaAttribute.getElementName());
		this.mapping = mappingDef.buildContextMapping(this, eJavaAttribute);
	}
	
	protected void syncMapping() {
		// if resource element has changed, the entire attribute will be rebuilt
		this.mapping.synchronizeWithResourceModel();
	}
	
	protected void updateMapping() {
		this.mapping.update();
	}
	
	
	// ***** declaring class / inheritance *****
	
	public boolean isInherited() {
		return ObjectTools.notEquals(getDeclaringTypeName(), getClassMapping().getTypeName());
	}
	
	public TypeName getDeclaringTypeName() {
		// TODO - should this be altered for inherited attributes?
		return getClassMapping().getTypeName();
	}
	
	
	// ***** java type information *****
	
	public String getJavaResourceAttributeBaseTypeName() {
		// TODO
		return Object.class.getName();
	}
	
	public boolean isJavaResourceAttributeCollectionType() {
		// TODO
		return false;
	}
	
	public boolean isJavaResourceAttributeTypeSubTypeOf(String typeName) {
		// TODO
		return false;
	}
}
