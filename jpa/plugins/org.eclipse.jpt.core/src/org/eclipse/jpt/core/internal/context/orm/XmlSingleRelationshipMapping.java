/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping;


public abstract class XmlSingleRelationshipMapping<T extends SingleRelationshipMapping>
	extends XmlRelationshipMapping<SingleRelationshipMapping> implements ISingleRelationshipMapping
{
	
//	protected EList<IJoinColumn> specifiedJoinColumns;
//
//	protected EList<IJoinColumn> defaultJoinColumns;

	protected Boolean secifiedOptional;
	protected Boolean defaultOptional;

	protected XmlSingleRelationshipMapping(XmlPersistentAttribute parent) {
		super(parent);
//		this.getDefaultJoinColumns().add(this.createJoinColumn(new JoinColumnOwner(this)));
//		this.eAdapters().add(this.buildListener());
	}

//	private IJoinColumn createJoinColumn(IJoinColumn.Owner owner) {
//		return OrmFactory.eINSTANCE.createXmlJoinColumn(owner);
//	}
//
//	private Adapter buildListener() {
//		return new AdapterImpl() {
//			public void notifyChanged(Notification notification) {
//				XmlSingleRelationshipMapping.this.notifyChanged(notification);
//			}
//		};
//	}
//
//	/**
//	 * check for changes to the 'specifiedJoinColumns' and
//	 * 'specifiedInverseJoinColumns' lists so we can notify the
//	 * model adapter of any changes;
//	 * also listen for changes to the 'defaultJoinColumns' and
//	 * 'defaultInverseJoinColumns' lists so we can spank the developer
//	 */
//	void notifyChanged(Notification notification) {
//		switch (notification.getFeatureID(ISingleRelationshipMapping.class)) {
//			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
//				defaultJoinColumnsChanged(notification);
//				break;
//			default :
//				break;
//		}
//	}
//
//	void defaultJoinColumnsChanged(Notification notification) {
//		throw new IllegalStateException("'defaultJoinColumns' cannot be changed");
//	}
//
//	public EList<IJoinColumn> getJoinColumns() {
//		return this.getSpecifiedJoinColumns().isEmpty() ? this.getDefaultJoinColumns() : this.getSpecifiedJoinColumns();
//	}
//
//	public EList<IJoinColumn> getSpecifiedJoinColumns() {
//		if (specifiedJoinColumns == null) {
//			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS);
//		}
//		return specifiedJoinColumns;
//	}
//
//	public EList<IJoinColumn> getDefaultJoinColumns() {
//		if (defaultJoinColumns == null) {
//			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS);
//		}
//		return defaultJoinColumns;
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
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL, oldOptional, optional));
//	}
//
//
//
//	@Override
//	public void initializeFromXmlSingleRelationshipMapping(XmlSingleRelationshipMapping oldMapping) {
//		super.initializeFromXmlSingleRelationshipMapping(oldMapping);
//		setFetch(oldMapping.getFetch());
//	}
//
//	public IJoinColumn createJoinColumn(int index) {
//		return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(this));
//	}
//
//	public boolean containsSpecifiedJoinColumns() {
//		return !this.getSpecifiedJoinColumns().isEmpty();
//	}
	
	public Boolean getOptional() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Boolean getDefaultOptional() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Boolean getSpecifiedOptional() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		// TODO Auto-generated method stub
		
	}
	
	public <T extends IJoinColumn> ListIterator<T> joinColumns() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public <T extends IJoinColumn> ListIterator<T> defaultJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public <T extends IJoinColumn> ListIterator<T> specifiedJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int specifiedJoinColumnsSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public IJoinColumn addSpecifiedJoinColumn(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		// TODO Auto-generated method stub
		
	}
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean containsSpecifiedJoinColumns() {
		// TODO Auto-generated method stub
		return false;
	}
}
