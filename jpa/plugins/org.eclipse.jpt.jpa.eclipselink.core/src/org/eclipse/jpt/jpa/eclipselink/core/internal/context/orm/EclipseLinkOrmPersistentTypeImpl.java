/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.SpecifiedOrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_1JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * <code>eclipselink-orm.xml</code> persistent type:<ul>
 * <li>mapping
 * <li>access
 * <li>access-methods
 * <li>attributes
 * <li>super persistent type
 * <li>Java persistent type
 * </ul>
 */
public class EclipseLinkOrmPersistentTypeImpl
		extends SpecifiedOrmPersistentType
		implements EclipseLinkOrmPersistentType {
	
	protected String specifiedGetMethod;
	protected String defaultGetMethod;

	protected String specifiedSetMethod;
	protected String defaultSetMethod;

	protected boolean dynamic;

	public EclipseLinkOrmPersistentTypeImpl(EntityMappings parent, XmlTypeMapping xmlTypeMapping) {
		super(parent, xmlTypeMapping);
		this.specifiedGetMethod = this.buildSpecifiedGetMethod();
		this.specifiedSetMethod = this.buildSpecifiedSetMethod();
	}

	@Override
	protected XmlTypeMapping getXmlTypeMapping() {
		return (XmlTypeMapping) super.getXmlTypeMapping();
	}

	@Override
	public EclipseLinkOrmTypeMapping getMapping() {
		return (EclipseLinkOrmTypeMapping) super.getMapping();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedGetMethod_(this.buildSpecifiedGetMethod());
		this.setSpecifiedSetMethod_(this.buildSpecifiedSetMethod());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultGetMethod(this.buildDefaultGetMethod());
		this.setDefaultSetMethod(this.buildDefaultSetMethod());
		this.setDynamic(this.buildDynamic());
	}


	//*************** dynamic *****************

	public boolean isDynamic() {
		return this.dynamic;
	}

	protected void setDynamic(boolean dynamic) {
		boolean old = this.dynamic;
		this.dynamic = dynamic;
		if (this.firePropertyChanged(DYNAMIC_PROPERTY, old, this.dynamic)) {
			// clear out the Java managed type here, it will be rebuilt during "update"
			if (this.javaManagedType != null) {
				this.setJavaManagedType(null);
			}
		}
	}

	//Base the dynamic state only on the JavaResourceType being null.
	//Otherwise, the access type affects the hierarchy
	//and then the hierarchy affects the access type and we get stuck in an update.
	//Validation will check that virtual access is set if it is dynamic.
	protected boolean buildDynamic() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLink2_1JpaPlatformFactory.VERSION)
			&& this.resolveJavaResourceType() == null;
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}

	protected boolean isVirtualAccess() {
		return this.getAccess() == EclipseLinkAccessType.VIRTUAL;
	}

	@Override
	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt) {
		return this.isDynamic() ?
				this.buildVirtualJavaPersistentType() :
				super.buildJavaManagedType(jrt);
	}

	protected JavaPersistentType buildVirtualJavaPersistentType() {
		return new VirtualJavaPersistentType(this, this.getXmlTypeMapping());
	}


	@Override
	public PersistentType getOverriddenPersistentType() {
		if (this.isDynamic()) {
			return null;
		}
		return super.getOverriddenPersistentType();
	}

	public OrmPersistentAttribute addVirtualAttribute(String attributeName, String mappingKey, String attributeType, String targetType) {
		// force the creation of an empty xml attribute container beforehand or it will trigger
		// a sync and, if we do this after adding the attribute, clear out our context attributes
		Attributes xmlAttributes = this.getXmlAttributesForUpdate();
		this.getXmlTypeMapping().setAttributes(xmlAttributes);  // possibly a NOP

		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlMapping = (XmlAttributeMapping) md.buildResourceMapping(this.getResourceModelFactory());
		xmlMapping.setName(attributeName);
		xmlMapping.setVirtualAttributeTypes(attributeType, targetType);
		if (this.getAccess() != EclipseLinkAccessType.VIRTUAL) {
			xmlMapping.setAccess(EclipseLinkAccessType.VIRTUAL.getOrmAccessType());
		}

		OrmPersistentAttribute specifiedAttribute = this.buildSpecifiedAttribute(xmlMapping);
		// we need to add the attribute to the right spot in the list - stupid spec...
		int specifiedIndex = this.getSpecifiedAttributeInsertionIndex(specifiedAttribute);
		this.addItemToList(specifiedIndex, specifiedAttribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
		specifiedAttribute.getMapping().addXmlAttributeMappingTo(xmlAttributes);

		return specifiedAttribute;
	}
	
	@Override
	public TypeBinding getAttributeTypeBinding(ReadOnlyPersistentAttribute attribute) {
		if (isDynamic()) {
			PersistentType superPersistentType = getSuperPersistentType();
			return (superPersistentType == null) ? null : superPersistentType.getAttributeTypeBinding(attribute);
		}
		return super.getAttributeTypeBinding(attribute);
	}
	
	
	//*************** get method *****************

	public String getGetMethod() {
		String specifiedGetMethod = this.getSpecifiedGetMethod();
		return (specifiedGetMethod != null) ? specifiedGetMethod : this.defaultGetMethod;
	}

	public String getDefaultGetMethod() {
		return this.defaultGetMethod;
	}

	//TODO get the default get method from the java VirtualAccessMethods annotation and from the super type
	protected String buildDefaultGetMethod() {
		if (getAccess() == EclipseLinkAccessType.VIRTUAL) {
			String defaultGetMethod = getEntityMappings().getDefaultGetMethod();
			return defaultGetMethod != null ? defaultGetMethod : DEFAULT_GET_METHOD;
		}
		return null;
	}

	protected void setDefaultGetMethod(String getMethod) {
		String old = this.defaultGetMethod;
		this.defaultGetMethod = getMethod;
		this.firePropertyChanged(DEFAULT_GET_METHOD_PROPERTY, old, getMethod);
	}

	public String getSpecifiedGetMethod() {
		return this.specifiedGetMethod;
	}

	public void setSpecifiedGetMethod(String getMethod) {
		if (this.valuesAreDifferent(this.specifiedGetMethod, getMethod)) {
			XmlAccessMethods xmlAccessMethods = this.getXmlAccessMethodsForUpdate();
			this.setSpecifiedGetMethod_(getMethod);
			xmlAccessMethods.setGetMethod(getMethod);
			this.removeXmlAccessMethodsIfUnset();
		}
	}

	protected void setSpecifiedGetMethod_(String getMethod) {
		String old = this.specifiedGetMethod;
		this.specifiedGetMethod = getMethod;
		this.firePropertyChanged(SPECIFIED_GET_METHOD_PROPERTY, old, getMethod);
	}

	protected String buildSpecifiedGetMethod() {
		XmlAccessMethods accessMethods = this.getXmlAccessMethods();
		return accessMethods != null ? accessMethods.getGetMethod() : null;
	}


	//*************** set method *****************

	public String getSetMethod() {
		String specifiedSetMethod = this.getSpecifiedSetMethod();
		return (specifiedSetMethod != null) ? specifiedSetMethod : this.defaultSetMethod;
	}

	public String getDefaultSetMethod() {
		return this.defaultSetMethod;
	}

	protected void setDefaultSetMethod(String setMethod) {
		String old = this.defaultSetMethod;
		this.defaultSetMethod = setMethod;
		this.firePropertyChanged(DEFAULT_SET_METHOD_PROPERTY, old, setMethod);
	}

	protected String buildDefaultSetMethod() {
		if (getAccess() == EclipseLinkAccessType.VIRTUAL) {
			//TODO get the default set method from the java VirtualAccessMethods annotation/super persistent type, embedded parent, etc
			String defaultSetMethod = getEntityMappings().getDefaultSetMethod();
			return defaultSetMethod != null ? defaultSetMethod : DEFAULT_SET_METHOD;
		}
		return null;
	}

	public String getSpecifiedSetMethod() {
		return this.specifiedSetMethod;
	}

	public void setSpecifiedSetMethod(String setMethod) {
		if (this.valuesAreDifferent(this.specifiedSetMethod, setMethod)) {
			XmlAccessMethods xmlAccessMethods = this.getXmlAccessMethodsForUpdate();
			this.setSpecifiedSetMethod_(setMethod);
			xmlAccessMethods.setSetMethod(setMethod);
			this.removeXmlAccessMethodsIfUnset();
		}
	}

	protected void setSpecifiedSetMethod_(String setMethod) {
		String old = this.specifiedSetMethod;
		this.specifiedSetMethod = setMethod;
		this.firePropertyChanged(SPECIFIED_SET_METHOD_PROPERTY, old, setMethod);
	}

	protected String buildSpecifiedSetMethod() {
		XmlAccessMethods accessMethods = this.getXmlAccessMethods();
		return accessMethods != null ? accessMethods.getSetMethod() : null;
	}

	//*************** XML access methods *****************

	protected XmlAccessMethodsHolder getXmlAccessMethodsHolder() {
		return this.getXmlTypeMapping();
	}

	protected XmlAccessMethods getXmlAccessMethods() {
		return getXmlAccessMethodsHolder().getAccessMethods();
	}

	/**
	 * Build the XML access methods (and XML defaults and XML metadata if necessary) if it does not exist.
	 */
	protected XmlAccessMethods getXmlAccessMethodsForUpdate() {
		XmlAccessMethods xmlAccessMethods = this.getXmlAccessMethodsHolder().getAccessMethods();
		return (xmlAccessMethods != null) ? xmlAccessMethods : this.buildXmlAccessMethods();
	}

	protected XmlAccessMethods buildXmlAccessMethods() {
		XmlAccessMethods xmlAccessMethods = this.buildXmlAccessMethods_();
		this.getXmlAccessMethodsHolder().setAccessMethods(xmlAccessMethods);
		return xmlAccessMethods;
	}

	protected XmlAccessMethods buildXmlAccessMethods_() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods();
	}

	/**
	 * clear the XML access methods if appropriate
	 */
	protected void removeXmlAccessMethodsIfUnset() {
		if (this.getXmlAccessMethods().isUnset()) {
			this.getXmlAccessMethodsHolder().setAccessMethods(null);
		}
	}

	@Override
	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) super.getEntityMappings();
	}

	@Override
	protected void validateClassResolves(List<IMessage> messages) {
		if (this.isDynamic() && !this.isVirtualAccess()) {
			super.validateClassResolves(messages);
		}
	}

	// ********** metamodel **********

	@Override
	public PersistentType2_0 getMetamodelType() {
		return (this.isDynamic()) ? null : this;
	}

}
