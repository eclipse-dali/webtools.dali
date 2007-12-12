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
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Table;


public class XmlIdMapping extends XmlAttributeMapping
	implements IIdMapping//, IXmlColumnMapping
{
//	protected IColumn column;
//	protected IGeneratedValue generatedValue;
//	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;
//	protected TemporalType temporal = TEMPORAL_EDEFAULT;
//	protected ITableGenerator tableGenerator;
//	protected ISequenceGenerator sequenceGenerator;

	protected Id id;
	
	protected XmlIdMapping(XmlPersistentAttribute parent) {
		super(parent);
//		this.column = OrmFactory.eINSTANCE.createXmlColumn(buildOwner());
//		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__COLUMN, null, null);
	}

//	public IColumn getColumn() {
//		return column;
//	}
//
//	public IGeneratedValue getGeneratedValue() {
//		return generatedValue;
//	}
//
//	public NotificationChain basicSetGeneratedValue(IGeneratedValue newGeneratedValue, NotificationChain msgs) {
//		IGeneratedValue oldGeneratedValue = generatedValue;
//		generatedValue = newGeneratedValue;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__GENERATED_VALUE, oldGeneratedValue, newGeneratedValue);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setGeneratedValue(IGeneratedValue newGeneratedValue) {
//		if (newGeneratedValue != generatedValue) {
//			NotificationChain msgs = null;
//			if (generatedValue != null)
//				msgs = ((InternalEObject) generatedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__GENERATED_VALUE, null, msgs);
//			if (newGeneratedValue != null)
//				msgs = ((InternalEObject) newGeneratedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__GENERATED_VALUE, null, msgs);
//			msgs = basicSetGeneratedValue(newGeneratedValue, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__GENERATED_VALUE, newGeneratedValue, newGeneratedValue));
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
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__TEMPORAL, oldTemporal, temporal));
//	}
//
//	public ITableGenerator getTableGenerator() {
//		return tableGenerator;
//	}
//
//	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
//		ITableGenerator oldTableGenerator = tableGenerator;
//		tableGenerator = newTableGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setTableGenerator(ITableGenerator newTableGenerator) {
//		if (newTableGenerator != tableGenerator) {
//			NotificationChain msgs = null;
//			if (tableGenerator != null)
//				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__TABLE_GENERATOR, null, msgs);
//			if (newTableGenerator != null)
//				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__TABLE_GENERATOR, null, msgs);
//			msgs = basicSetTableGenerator(newTableGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
//	}
//	
//	public ISequenceGenerator getSequenceGenerator() {
//		return sequenceGenerator;
//	}
//
//	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
//		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
//		sequenceGenerator = newSequenceGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
//		if (newSequenceGenerator != sequenceGenerator) {
//			NotificationChain msgs = null;
//			if (sequenceGenerator != null)
//				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__SEQUENCE_GENERATOR, null, msgs);
//			if (newSequenceGenerator != null)
//				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__SEQUENCE_GENERATOR, null, msgs);
//			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
//	}
//
//	public XmlColumn getColumnForXml() {
//		if (((XmlColumn) getColumn()).isAllFeaturesUnset()) {
//			return null;
//		}
//		return (XmlColumn) getColumn();
//	}

//	public void setColumnForXmlGen(XmlColumn newColumnForXml) {
//		XmlColumn oldValue = newColumnForXml == null ? (XmlColumn) getColumn() : null;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ID__COLUMN_FOR_XML, oldValue, newColumnForXml));
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



	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlIdMapping(this);
	}

	@Override
	public void initializeFromXmlBasicMapping(XmlBasicMapping oldMapping) {
		super.initializeFromXmlBasicMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
	}

	@Override
	public int xmlSequence() {
		return 0;
	}

	@Override
	public String primaryKeyColumnName() {
		return this.getColumn().getName();
	}

//	public IGeneratedValue createGeneratedValue() {
//		return OrmFactory.eINSTANCE.createXmlGeneratedValue();
//	}
//
//	public ISequenceGenerator createSequenceGenerator() {
//		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
//	}
//
//	public ITableGenerator createTableGenerator() {
//		return OrmFactory.eINSTANCE.createXmlTableGenerator();
//	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}
	
	@Override
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		Id id = OrmFactory.eINSTANCE.createId();
		if (typeMapping.getAttributes() == null) {
			typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getIds().remove(this.id);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public IGeneratedValue addGeneratedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public ISequenceGenerator addSequenceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITableGenerator addTableGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public IGeneratedValue getGeneratedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public ISequenceGenerator getSequenceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITableGenerator getTableGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public TemporalType getTemporal() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeGeneratedValue() {
		// TODO Auto-generated method stub
		
	}

	public void removeSequenceGenerator() {
		// TODO Auto-generated method stub
		
	}

	public void removeTableGenerator() {
		// TODO Auto-generated method stub
		
	}

	public void setTemporal(TemporalType value) {
		// TODO Auto-generated method stub
		
	}

	public IColumn getColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	public Column columnResource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String defaultTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table dbTable(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String defaultColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void initialize(Id id) {
		this.id = id;
	}
	
	public void update(Id id) {
		this.id = id;
	}
}
