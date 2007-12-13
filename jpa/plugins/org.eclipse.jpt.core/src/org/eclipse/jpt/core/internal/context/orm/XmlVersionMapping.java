/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.db.internal.Table;


public class XmlVersionMapping extends XmlAttributeMapping
	implements IVersionMapping
{
	protected final XmlColumn column;

//	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;
//
//	protected TemporalType temporal = TEMPORAL_EDEFAULT;

	protected Version version;
	
	protected XmlVersionMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.column = new XmlColumn(this, this);
	}

	@Override
	public int xmlSequence() {
		return 2;
	}

	public String getKey() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlVersionMapping(this);
	}

	public XmlColumn getColumn() {
		return this.column;
	}

//	public TemporalType getTemporal() {
//		return temporal;
//	}
//
//	public void setTemporal(TemporalType newTemporal) {
//		TemporalType oldTemporal = temporal;
//		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_VERSION__TEMPORAL, oldTemporal, temporal));
//	}
//	
//	public XmlColumn getColumnForXml() {
//		if (((XmlColumn) getColumn()).isAllFeaturesUnset()) {
//			return null;
//		}
//		return (XmlColumn) getColumn();
//	}
//
//	public void setColumnForXmlGen(XmlColumn newColumnForXml) {
//		XmlColumn oldValue = newColumnForXml == null ? (XmlColumn) getColumn() : null;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_VERSION__COLUMN_FOR_XML, oldValue, newColumnForXml));
//	}
//
//	public void setColumnForXml(XmlColumn newColumnForXml) {
//		setColumnForXmlGen(newColumnForXml);
//		if (newColumnForXml == null) {
//			((XmlColumn) getColumn()).unsetAllAttributes();
//		}
//	}
//
//	public void makeColumnForXmlNonNull() {
//		setColumnForXmlGen(getColumnForXml());
//	}
//
//	public void makeColumnForXmlNull() {
//		setColumnForXmlGen(null);
//	}
//
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
		typeMapping.getAttributes().getVersions().remove(this.version);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public TemporalType getTemporal() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTemporal(TemporalType value) {
		// TODO Auto-generated method stub
		
	}

	public String defaultTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}

	public String defaultColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void initialize(Version version) {
		this.version = version;
		this.column.initialize(version);
	}
	
	public void update(Version version) {
		this.version = version;
		this.column.update(version);
	}
}
