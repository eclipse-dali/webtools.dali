/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Table;


public class XmlBasicMapping extends XmlAttributeMapping
	implements IBasicMapping
{
	protected final XmlColumn column;
	
//	protected static final DefaultEagerFetchType FETCH_EDEFAULT = DefaultEagerFetchType.DEFAULT;
//
//	protected DefaultEagerFetchType fetch = FETCH_EDEFAULT;
//
//	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;
//
//	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;
//
//	protected static final boolean LOB_EDEFAULT = false;
//
//	protected boolean lob = LOB_EDEFAULT;
//
//	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;
//
//	protected TemporalType temporal = TEMPORAL_EDEFAULT;
//
//	protected static final EnumType ENUMERATED_EDEFAULT = EnumType.DEFAULT;
//
//	protected EnumType enumerated = ENUMERATED_EDEFAULT;

	protected Basic basic;
	
	protected XmlBasicMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.column = new XmlColumn(this, this);
	}

//
//	public DefaultEagerFetchType getFetch() {
//		return fetch;
//	}
//
//	public void setFetch(DefaultEagerFetchType newFetch) {
//		DefaultEagerFetchType oldFetch = fetch;
//		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__FETCH, oldFetch, fetch));
//	}
//
//	public DefaultTrueBoolean getOptional() {
//		return optional;
//	}
//
//	public void setOptional(DefaultTrueBoolean newOptional) {
//		DefaultTrueBoolean oldOptional = optional;
//		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__OPTIONAL, oldOptional, optional));
//	}
//
//	public boolean isLob() {
//		return lob;
//	}
//
//	public void setLob(boolean newLob) {
//		boolean oldLob = lob;
//		lob = newLob;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__LOB, oldLob, lob));
//	}
//
//	public TemporalType getTemporal() {
//		return temporal;
//	}
//
//	public void setTemporal(TemporalType newTemporal) {
//		TemporalType oldTemporal = temporal;
//		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__TEMPORAL, oldTemporal, temporal));
//	}
//
//	public EnumType getEnumerated() {
//		return enumerated;
//	}
//
//	public void setEnumerated(EnumType newEnumerated) {
//		EnumType oldEnumerated = enumerated;
//		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__ENUMERATED, oldEnumerated, enumerated));
//	}
//

	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlBasicMapping(this);
	}

	@Override
	public void initializeFromXmlIdMapping(XmlIdMapping oldMapping) {
		super.initializeFromXmlIdMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
	}

	@Override
	public int xmlSequence() {
		return 1;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public EnumType getDefaultEnumerated() {
		// TODO Auto-generated method stub
		return null;
	}

	public FetchType getDefaultFetch() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getDefaultOptional() {
		// TODO Auto-generated method stub
		return null;
	}

	public EnumType getEnumerated() {
		// TODO Auto-generated method stub
		return null;
	}

	public FetchType getFetch() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getOptional() {
		// TODO Auto-generated method stub
		return null;
	}

	public EnumType getSpecifiedEnumerated() {
		// TODO Auto-generated method stub
		return null;
	}

	public FetchType getSpecifiedFetch() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getSpecifiedOptional() {
		// TODO Auto-generated method stub
		return null;
	}

	public TemporalType getTemporal() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLob() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLob(boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated) {
		// TODO Auto-generated method stub
		
	}

	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		// TODO Auto-generated method stub
		
	}

	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		// TODO Auto-generated method stub
		
	}

	public void setTemporal(TemporalType value) {
		// TODO Auto-generated method stub
		
	}

	public String attributeName() {
		return this.persistentAttribute().getName();
	}

	public String defaultColumnName() {		
		//TODO check java column for the case where this is a virtual mapping
		return attributeName();
	}

	public String defaultTableName() {
		//TODO check java column for the case where this is a virtual mapping
		return typeMapping().getTableName();
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void initialize(Basic basic) {
		this.basic = basic;
		this.column.initialize(basic);
	}
	
	public void update(Basic basic) {
		this.basic = basic;
		this.column.update(basic);
	}
	
	@Override
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		Basic basic = OrmFactory.eINSTANCE.createBasic();
		if (typeMapping.getAttributes() == null) {
			typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getBasics().remove(this.basic);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
