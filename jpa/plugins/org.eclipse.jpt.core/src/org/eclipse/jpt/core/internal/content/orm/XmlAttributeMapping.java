/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Attribute Mapping</b></em>'.
 * Discussion of morphing mappings:<ol>

 * <li> Each concrete subclass must override the method initializeOn(MWMapping newMapping)
 * and call the appropriate initializeFromMW___Mapping(MW___Mapping oldMapping).
 * [This is call double-dispatching.]
 * We could have overloaded the same method name (e.g. initializeFrom(MW___Mapping oldMapping))
 * but the resulting confusion is not worth it. "Upcasting" just makes this really fuzzy....
 *
 * <il> If necessary, each subclass (concrete and abstract) should override
 * the initializeFromMW___Mapping(MW___Mapping oldMapping) method. This override
 * should first call super.initializeFromMW___Mapping(MW___Mapping oldMapping); then
 * it should initialize only the properties that are defined by it that have
 * corresponding properties in the oldMapping.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping#getPersistentAttribute <em>Persistent Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlAttributeMapping extends XmlEObject
	implements IAttributeMapping
{
	/**
	 * The cached value of the '{@link #getPersistentAttribute() <em>Persistent Attribute</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistentAttribute()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistentAttribute persistentAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlAttributeMapping() {
		super();
	}

	protected INamedColumn.Owner buildOwner() {
		return new INamedColumn.Owner() {
			public ITextRange getTextRange() {
				return XmlAttributeMapping.this.getTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return XmlAttributeMapping.this.typeMapping();
			}

			public Table dbTable(String tableName) {
				return getTypeMapping().dbTable(tableName);
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_ATTRIBUTE_MAPPING;
	}

	public XmlPersistentType getPersistentType() {
		return (XmlPersistentType) eContainer();
	}

	/**
	 * Returns the value of the '<em><b>Persistent Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistent Attribute</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistent Attribute</em>' containment reference.
	 * @see #setPersistentAttribute(XmlPersistentAttribute)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeMapping_PersistentAttribute()
	 * @model containment="true" required="true"
	 * @generated
	 */
	public XmlPersistentAttribute getPersistentAttribute() {
		return persistentAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistentAttribute(XmlPersistentAttribute newPersistentAttribute, NotificationChain msgs) {
		XmlPersistentAttribute oldPersistentAttribute = persistentAttribute;
		persistentAttribute = newPersistentAttribute;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE, oldPersistentAttribute, newPersistentAttribute);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping#getPersistentAttribute <em>Persistent Attribute</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistent Attribute</em>' containment reference.
	 * @see #getPersistentAttribute()
	 * @generated
	 */
	public void setPersistentAttribute(XmlPersistentAttribute newPersistentAttribute) {
		if (newPersistentAttribute != persistentAttribute) {
			NotificationChain msgs = null;
			if (persistentAttribute != null)
				msgs = ((InternalEObject) persistentAttribute).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE, null, msgs);
			if (newPersistentAttribute != null)
				msgs = ((InternalEObject) newPersistentAttribute).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE, null, msgs);
			msgs = basicSetPersistentAttribute(newPersistentAttribute, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE, newPersistentAttribute, newPersistentAttribute));
	}

	public boolean isDefault() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE :
				return basicSetPersistentAttribute(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE :
				return getPersistentAttribute();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE :
				setPersistentAttribute((XmlPersistentAttribute) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE :
				setPersistentAttribute((XmlPersistentAttribute) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE :
				return persistentAttribute != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * IMPORTANT:  See XmlAttributeMapping class comment.
	 * Subclasses should override this method to call the
	 * appropriate initializeFrom___Mapping() method.
	 */
	protected abstract void initializeOn(XmlAttributeMapping newMapping);

	public void initializeFromXmlAttributeMapping(XmlAttributeMapping oldMapping) {
		setPersistentAttribute(oldMapping.getPersistentAttribute());
	}

	public void initializeFromXmlBasicMapping(XmlBasic oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlIdMapping(XmlId oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlTransientMapping(XmlTransient oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedMapping(XmlEmbedded oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedIdMapping(XmlEmbeddedId oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlVersionMapping(XmlVersion oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlMulitRelationshipMapping(XmlMultiRelationshipMappingInternal oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlSingleRelationshipMapping(XmlSingleRelationshipMapping oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToManyMapping(XmlOneToMany oldMapping) {
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToOneMapping(XmlManyToOne oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToOneMapping(XmlOneToOne oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToManyMapping(XmlManyToMany oldMapping) {
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public IJpaContentNode getContentNode(int offset) {
		return getPersistentAttribute();
	}

	/**
	 * Attributes are a sequence in the orm schema. We must keep
	 * the list of attributes in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlAttributeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	public abstract int xmlSequence();

	public void initialize() {
	//do nothing as this will be handle by the Xml Translators
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {}

	public String primaryKeyColumnName() {
		return null;
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentType().getMapping();
	}

	public boolean isVirtual() {
		return getPersistentType().getVirtualAttributeMappings().contains(this);
	}
	
	public void setVirtual(boolean virtual) {
		getPersistentType().setMappingVirtual(this, virtual);
	}

	@Override
	public ITextRange getTextRange() {
		if (isVirtual()) {
			return getPersistentType().getAttributesTextRange();
		}
		else {
			return super.getTextRange();
		}
	}

	public ITextRange getNameTextRange() {
		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
		if (nameNode != null) {
			return buildTextRange(nameNode);
		}
		else {
			return getTextRange();
		}
	}

	public boolean isOverridableAttributeMapping() {
		return false;
	}

	public boolean isOverridableAssociationMapping() {
		return false;
	}

	public boolean isIdMapping() {
		return false;
	}
}