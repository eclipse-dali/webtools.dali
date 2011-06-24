/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlEmbeddable_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlEmbeddable_2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStruct_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Embeddable</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCopyPolicy <em>Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCloneCopyPolicy <em>Clone Copy Policy</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable()
 * @model kind="class"
 * @generated
 */
public class XmlEmbeddable extends org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable implements XmlEmbeddable_2_1, XmlEmbeddable_2_2, XmlEmbeddable_2_3, XmlCustomizerHolder, XmlChangeTrackingHolder, XmlConvertersHolder, XmlPropertyContainer
{
	/**
	 * The cached value of the '{@link #getAccessMethods() <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessMethods()
	 * @generated
	 * @ordered
	 */
	protected XmlAccessMethods accessMethods;

	/**
	 * The cached value of the '{@link #getAttributeOverrides() <em>Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> attributeOverrides;

	/**
	 * The cached value of the '{@link #getAssociationOverrides() <em>Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAssociationOverride> associationOverrides;

	/**
	 * The default value of the '{@link #getParentClass() <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClass()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentClass() <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentClass()
	 * @generated
	 * @ordered
	 */
	protected String parentClass = PARENT_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPlsqlRecords() <em>Plsql Records</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlsqlRecords()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPlsqlRecord_2_3> plsqlRecords;

	/**
	 * The cached value of the '{@link #getPlsqlTables() <em>Plsql Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlsqlTables()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPlsqlTable> plsqlTables;

	/**
	 * The cached value of the '{@link #getStruct() <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStruct()
	 * @generated
	 * @ordered
	 */
	protected XmlStruct_2_3 struct;

	/**
	 * The cached value of the '{@link #getCustomizer() <em>Customizer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomizer()
	 * @generated
	 * @ordered
	 */
	protected XmlClassReference customizer;

	/**
	 * The cached value of the '{@link #getChangeTracking() <em>Change Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeTracking()
	 * @generated
	 * @ordered
	 */
	protected XmlChangeTracking changeTracking;

	/**
	 * The cached value of the '{@link #getConverters() <em>Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConverter> converters;

	/**
	 * The cached value of the '{@link #getTypeConverters() <em>Type Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTypeConverter> typeConverters;

	/**
	 * The cached value of the '{@link #getObjectTypeConverters() <em>Object Type Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectTypeConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlObjectTypeConverter> objectTypeConverters;

	/**
	 * The cached value of the '{@link #getStructConverters() <em>Struct Converters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructConverters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlStructConverter> structConverters;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlProperty> properties;

	/**
	 * The cached value of the '{@link #getCopyPolicy() <em>Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCopyPolicy copyPolicy;

	/**
	 * The cached value of the '{@link #getInstantiationCopyPolicy() <em>Instantiation Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstantiationCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlInstantiationCopyPolicy instantiationCopyPolicy;

	/**
	 * The cached value of the '{@link #getCloneCopyPolicy() <em>Clone Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCloneCopyPolicy()
	 * @generated
	 * @ordered
	 */
	protected XmlCloneCopyPolicy cloneCopyPolicy;

	/**
	 * The default value of the '{@link #getExcludeDefaultMappings() <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExcludeDefaultMappings() <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 * @ordered
	 */
	protected Boolean excludeDefaultMappings = EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEmbeddable()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return EclipseLinkOrmPackage.Literals.XML_EMBEDDABLE;
	}

	/**
	 * Returns the value of the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Methods</em>' containment reference.
	 * @see #setAccessMethods(XmlAccessMethods)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder_AccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	public XmlAccessMethods getAccessMethods()
	{
		return accessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessMethods(XmlAccessMethods newAccessMethods, NotificationChain msgs)
	{
		XmlAccessMethods oldAccessMethods = accessMethods;
		accessMethods = newAccessMethods;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getAccessMethods <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Methods</em>' containment reference.
	 * @see #getAccessMethods()
	 * @generated
	 */
	public void setAccessMethods(XmlAccessMethods newAccessMethods)
	{
		if (newAccessMethods != accessMethods)
		{
			NotificationChain msgs = null;
			if (accessMethods != null)
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS, newAccessMethods, newAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeOverrideContainer_AttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getAttributeOverrides()
	{
		if (attributeOverrides == null)
		{
			attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES);
		}
		return attributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAssociationOverrideContainer_AssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAssociationOverride> getAssociationOverrides()
	{
		if (associationOverrides == null)
		{
			associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' attribute.
	 * @see #setParentClass(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_2_2_ParentClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getParentClass()
	{
		return parentClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getParentClass <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' attribute.
	 * @see #getParentClass()
	 * @generated
	 */
	public void setParentClass(String newParentClass)
	{
		String oldParentClass = parentClass;
		parentClass = newParentClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS, oldParentClass, parentClass));
	}

	/**
	 * Returns the value of the '<em><b>Plsql Records</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Records</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Records</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_2_3_PlsqlRecords()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPlsqlRecord_2_3> getPlsqlRecords()
	{
		if (plsqlRecords == null)
		{
			plsqlRecords = new EObjectContainmentEList<XmlPlsqlRecord_2_3>(XmlPlsqlRecord_2_3.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS);
		}
		return plsqlRecords;
	}

	/**
	 * Returns the value of the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_2_3_PlsqlTables()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPlsqlTable> getPlsqlTables()
	{
		if (plsqlTables == null)
		{
			plsqlTables = new EObjectContainmentEList<XmlPlsqlTable>(XmlPlsqlTable.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES);
		}
		return plsqlTables;
	}

	/**
	 * Returns the value of the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct</em>' containment reference.
	 * @see #setStruct(XmlStruct_2_3)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_2_3_Struct()
	 * @model containment="true"
	 * @generated
	 */
	public XmlStruct_2_3 getStruct()
	{
		return struct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStruct(XmlStruct_2_3 newStruct, NotificationChain msgs)
	{
		XmlStruct_2_3 oldStruct = struct;
		struct = newStruct;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT, oldStruct, newStruct);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getStruct <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct</em>' containment reference.
	 * @see #getStruct()
	 * @generated
	 */
	public void setStruct(XmlStruct_2_3 newStruct)
	{
		if (newStruct != struct)
		{
			NotificationChain msgs = null;
			if (struct != null)
				msgs = ((InternalEObject)struct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT, null, msgs);
			if (newStruct != null)
				msgs = ((InternalEObject)newStruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT, null, msgs);
			msgs = basicSetStruct(newStruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT, newStruct, newStruct));
	}

	/**
	 * Returns the value of the '<em><b>Customizer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Customizer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Customizer</em>' containment reference.
	 * @see #setCustomizer(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizerHolder_Customizer()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getCustomizer()
	{
		return customizer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomizer(XmlClassReference newCustomizer, NotificationChain msgs)
	{
		XmlClassReference oldCustomizer = customizer;
		customizer = newCustomizer;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER, oldCustomizer, newCustomizer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCustomizer <em>Customizer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Customizer</em>' containment reference.
	 * @see #getCustomizer()
	 * @generated
	 */
	public void setCustomizer(XmlClassReference newCustomizer)
	{
		if (newCustomizer != customizer)
		{
			NotificationChain msgs = null;
			if (customizer != null)
				msgs = ((InternalEObject)customizer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER, null, msgs);
			if (newCustomizer != null)
				msgs = ((InternalEObject)newCustomizer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER, null, msgs);
			msgs = basicSetCustomizer(newCustomizer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER, newCustomizer, newCustomizer));
	}

	/**
	 * Returns the value of the '<em><b>Change Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Tracking</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Tracking</em>' containment reference.
	 * @see #setChangeTracking(XmlChangeTracking)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingHolder_ChangeTracking()
	 * @model containment="true"
	 * @generated
	 */
	public XmlChangeTracking getChangeTracking()
	{
		return changeTracking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangeTracking(XmlChangeTracking newChangeTracking, NotificationChain msgs)
	{
		XmlChangeTracking oldChangeTracking = changeTracking;
		changeTracking = newChangeTracking;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING, oldChangeTracking, newChangeTracking);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getChangeTracking <em>Change Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change Tracking</em>' containment reference.
	 * @see #getChangeTracking()
	 * @generated
	 */
	public void setChangeTracking(XmlChangeTracking newChangeTracking)
	{
		if (newChangeTracking != changeTracking)
		{
			NotificationChain msgs = null;
			if (changeTracking != null)
				msgs = ((InternalEObject)changeTracking).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING, null, msgs);
			if (newChangeTracking != null)
				msgs = ((InternalEObject)newChangeTracking).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING, null, msgs);
			msgs = basicSetChangeTracking(newChangeTracking, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING, newChangeTracking, newChangeTracking));
	}

	/**
	 * Returns the value of the '<em><b>Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_Converters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConverter> getConverters()
	{
		if (converters == null)
		{
			converters = new EObjectContainmentEList<XmlConverter>(XmlConverter.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS);
		}
		return converters;
	}

	/**
	 * Returns the value of the '<em><b>Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_TypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTypeConverter> getTypeConverters()
	{
		if (typeConverters == null)
		{
			typeConverters = new EObjectContainmentEList<XmlTypeConverter>(XmlTypeConverter.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS);
		}
		return typeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Object Type Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_ObjectTypeConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlObjectTypeConverter> getObjectTypeConverters()
	{
		if (objectTypeConverters == null)
		{
			objectTypeConverters = new EObjectContainmentEList<XmlObjectTypeConverter>(XmlObjectTypeConverter.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS);
		}
		return objectTypeConverters;
	}

	/**
	 * Returns the value of the '<em><b>Struct Converters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct Converters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Converters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConvertersHolder_StructConverters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStructConverter> getStructConverters()
	{
		if (structConverters == null)
		{
			structConverters = new EObjectContainmentEList<XmlStructConverter>(XmlStructConverter.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS);
		}
		return structConverters;
	}

	/**
	 * Returns the value of the '<em><b>Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copy Policy</em>' containment reference.
	 * @see #setCopyPolicy(XmlCopyPolicy)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_CopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCopyPolicy getCopyPolicy()
	{
		return copyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCopyPolicy(XmlCopyPolicy newCopyPolicy, NotificationChain msgs)
	{
		XmlCopyPolicy oldCopyPolicy = copyPolicy;
		copyPolicy = newCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY, oldCopyPolicy, newCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCopyPolicy <em>Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Copy Policy</em>' containment reference.
	 * @see #getCopyPolicy()
	 * @generated
	 */
	public void setCopyPolicy(XmlCopyPolicy newCopyPolicy)
	{
		if (newCopyPolicy != copyPolicy)
		{
			NotificationChain msgs = null;
			if (copyPolicy != null)
				msgs = ((InternalEObject)copyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY, null, msgs);
			if (newCopyPolicy != null)
				msgs = ((InternalEObject)newCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY, null, msgs);
			msgs = basicSetCopyPolicy(newCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY, newCopyPolicy, newCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Instantiation Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instantiation Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instantiation Copy Policy</em>' containment reference.
	 * @see #setInstantiationCopyPolicy(XmlInstantiationCopyPolicy)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_InstantiationCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlInstantiationCopyPolicy getInstantiationCopyPolicy()
	{
		return instantiationCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInstantiationCopyPolicy(XmlInstantiationCopyPolicy newInstantiationCopyPolicy, NotificationChain msgs)
	{
		XmlInstantiationCopyPolicy oldInstantiationCopyPolicy = instantiationCopyPolicy;
		instantiationCopyPolicy = newInstantiationCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY, oldInstantiationCopyPolicy, newInstantiationCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getInstantiationCopyPolicy <em>Instantiation Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instantiation Copy Policy</em>' containment reference.
	 * @see #getInstantiationCopyPolicy()
	 * @generated
	 */
	public void setInstantiationCopyPolicy(XmlInstantiationCopyPolicy newInstantiationCopyPolicy)
	{
		if (newInstantiationCopyPolicy != instantiationCopyPolicy)
		{
			NotificationChain msgs = null;
			if (instantiationCopyPolicy != null)
				msgs = ((InternalEObject)instantiationCopyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY, null, msgs);
			if (newInstantiationCopyPolicy != null)
				msgs = ((InternalEObject)newInstantiationCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY, null, msgs);
			msgs = basicSetInstantiationCopyPolicy(newInstantiationCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY, newInstantiationCopyPolicy, newInstantiationCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Clone Copy Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clone Copy Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clone Copy Policy</em>' containment reference.
	 * @see #setCloneCopyPolicy(XmlCloneCopyPolicy)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_CloneCopyPolicy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCloneCopyPolicy getCloneCopyPolicy()
	{
		return cloneCopyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCloneCopyPolicy(XmlCloneCopyPolicy newCloneCopyPolicy, NotificationChain msgs)
	{
		XmlCloneCopyPolicy oldCloneCopyPolicy = cloneCopyPolicy;
		cloneCopyPolicy = newCloneCopyPolicy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY, oldCloneCopyPolicy, newCloneCopyPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getCloneCopyPolicy <em>Clone Copy Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clone Copy Policy</em>' containment reference.
	 * @see #getCloneCopyPolicy()
	 * @generated
	 */
	public void setCloneCopyPolicy(XmlCloneCopyPolicy newCloneCopyPolicy)
	{
		if (newCloneCopyPolicy != cloneCopyPolicy)
		{
			NotificationChain msgs = null;
			if (cloneCopyPolicy != null)
				msgs = ((InternalEObject)cloneCopyPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY, null, msgs);
			if (newCloneCopyPolicy != null)
				msgs = ((InternalEObject)newCloneCopyPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY, null, msgs);
			msgs = basicSetCloneCopyPolicy(newCloneCopyPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY, newCloneCopyPolicy, newCloneCopyPolicy));
	}

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPropertyContainer_Properties()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new EObjectContainmentEList<XmlProperty>(XmlProperty.class, this, EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * Returns the value of the '<em><b>Exclude Default Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Default Mappings</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Default Mappings</em>' attribute.
	 * @see #setExcludeDefaultMappings(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlEmbeddable_ExcludeDefaultMappings()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getExcludeDefaultMappings()
	{
		return excludeDefaultMappings;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable#getExcludeDefaultMappings <em>Exclude Default Mappings</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Default Mappings</em>' attribute.
	 * @see #getExcludeDefaultMappings()
	 * @generated
	 */
	public void setExcludeDefaultMappings(Boolean newExcludeDefaultMappings)
	{
		Boolean oldExcludeDefaultMappings = excludeDefaultMappings;
		excludeDefaultMappings = newExcludeDefaultMappings;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS, oldExcludeDefaultMappings, excludeDefaultMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS:
				return ((InternalEList<?>)getPlsqlRecords()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES:
				return ((InternalEList<?>)getPlsqlTables()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT:
				return basicSetStruct(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER:
				return basicSetCustomizer(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING:
				return basicSetChangeTracking(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS:
				return ((InternalEList<?>)getConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS:
				return ((InternalEList<?>)getTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS:
				return ((InternalEList<?>)getObjectTypeConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS:
				return ((InternalEList<?>)getStructConverters()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY:
				return basicSetCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY:
				return basicSetInstantiationCopyPolicy(null, msgs);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY:
				return basicSetCloneCopyPolicy(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES:
				return getAttributeOverrides();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS:
				return getParentClass();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS:
				return getPlsqlRecords();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES:
				return getPlsqlTables();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT:
				return getStruct();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER:
				return getCustomizer();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING:
				return getChangeTracking();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS:
				return getConverters();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS:
				return getTypeConverters();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS:
				return getObjectTypeConverters();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS:
				return getStructConverters();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES:
				return getProperties();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY:
				return getCopyPolicy();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY:
				return getInstantiationCopyPolicy();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY:
				return getCloneCopyPolicy();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS:
				return getExcludeDefaultMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				getAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS:
				setParentClass((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS:
				getPlsqlRecords().clear();
				getPlsqlRecords().addAll((Collection<? extends XmlPlsqlRecord_2_3>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES:
				getPlsqlTables().clear();
				getPlsqlTables().addAll((Collection<? extends XmlPlsqlTable>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT:
				setStruct((XmlStruct_2_3)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER:
				setCustomizer((XmlClassReference)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING:
				setChangeTracking((XmlChangeTracking)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS:
				getConverters().clear();
				getConverters().addAll((Collection<? extends XmlConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS:
				getTypeConverters().clear();
				getTypeConverters().addAll((Collection<? extends XmlTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				getObjectTypeConverters().addAll((Collection<? extends XmlObjectTypeConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS:
				getStructConverters().clear();
				getStructConverters().addAll((Collection<? extends XmlStructConverter>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends XmlProperty>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlInstantiationCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCloneCopyPolicy)newValue);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS:
				setExcludeDefaultMappings((Boolean)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS:
				setParentClass(PARENT_CLASS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS:
				getPlsqlRecords().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES:
				getPlsqlTables().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT:
				setStruct((XmlStruct_2_3)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER:
				setCustomizer((XmlClassReference)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING:
				setChangeTracking((XmlChangeTracking)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS:
				getConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS:
				getTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS:
				getObjectTypeConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS:
				getStructConverters().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES:
				getProperties().clear();
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY:
				setCopyPolicy((XmlCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY:
				setInstantiationCopyPolicy((XmlInstantiationCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY:
				setCloneCopyPolicy((XmlCloneCopyPolicy)null);
				return;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS:
				setExcludeDefaultMappings(EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES:
				return attributeOverrides != null && !attributeOverrides.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS:
				return PARENT_CLASS_EDEFAULT == null ? parentClass != null : !PARENT_CLASS_EDEFAULT.equals(parentClass);
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS:
				return plsqlRecords != null && !plsqlRecords.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES:
				return plsqlTables != null && !plsqlTables.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT:
				return struct != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER:
				return customizer != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING:
				return changeTracking != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS:
				return converters != null && !converters.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS:
				return typeConverters != null && !typeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS:
				return objectTypeConverters != null && !objectTypeConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS:
				return structConverters != null && !structConverters.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__COPY_POLICY:
				return copyPolicy != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__INSTANTIATION_COPY_POLICY:
				return instantiationCopyPolicy != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__CLONE_COPY_POLICY:
				return cloneCopyPolicy != null;
			case EclipseLinkOrmPackage.XML_EMBEDDABLE__EXCLUDE_DEFAULT_MAPPINGS:
				return EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT == null ? excludeDefaultMappings != null : !EXCLUDE_DEFAULT_MAPPINGS_EDEFAULT.equals(excludeDefaultMappings);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_1.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_2.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS: return EclipseLinkOrmV2_2Package.XML_EMBEDDABLE_22__PARENT_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_3.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS: return EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__PLSQL_RECORDS;
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES: return EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__PLSQL_TABLES;
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT: return EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__STRUCT;
				default: return -1;
			}
		}
		if (baseClass == XmlCustomizerHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER: return EclipseLinkOrmPackage.XML_CUSTOMIZER_HOLDER__CUSTOMIZER;
				default: return -1;
			}
		}
		if (baseClass == XmlChangeTrackingHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING: return EclipseLinkOrmPackage.XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS;
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES: return EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_1.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES: return EclipseLinkOrmPackage.XML_EMBEDDABLE__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES: return EclipseLinkOrmPackage.XML_EMBEDDABLE__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_2.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_2Package.XML_EMBEDDABLE_22__PARENT_CLASS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__PARENT_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbeddable_2_3.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__PLSQL_RECORDS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_RECORDS;
				case EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__PLSQL_TABLES: return EclipseLinkOrmPackage.XML_EMBEDDABLE__PLSQL_TABLES;
				case EclipseLinkOrmV2_3Package.XML_EMBEDDABLE_23__STRUCT: return EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT;
				default: return -1;
			}
		}
		if (baseClass == XmlCustomizerHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CUSTOMIZER_HOLDER__CUSTOMIZER: return EclipseLinkOrmPackage.XML_EMBEDDABLE__CUSTOMIZER;
				default: return -1;
			}
		}
		if (baseClass == XmlChangeTrackingHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CHANGE_TRACKING_HOLDER__CHANGE_TRACKING: return EclipseLinkOrmPackage.XML_EMBEDDABLE__CHANGE_TRACKING;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertersHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__CONVERTERS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__OBJECT_TYPE_CONVERTERS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__OBJECT_TYPE_CONVERTERS;
				case EclipseLinkOrmPackage.XML_CONVERTERS_HOLDER__STRUCT_CONVERTERS: return EclipseLinkOrmPackage.XML_EMBEDDABLE__STRUCT_CONVERTERS;
				default: return -1;
			}
		}
		if (baseClass == XmlPropertyContainer.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PROPERTY_CONTAINER__PROPERTIES: return EclipseLinkOrmPackage.XML_EMBEDDABLE__PROPERTIES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (parentClass: ");
		result.append(parentClass);
		result.append(", excludeDefaultMappings: ");
		result.append(excludeDefaultMappings);
		result.append(')');
		return result.toString();
	}
		
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlEmbeddable(), 
			buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassTranslator(),
			buildParentClassTranslator(),
			buildAccessTranslator(),
			buildMetadataCompleteTranslator(),
			buildExcludeDefaultMappingsTranslator(),
			buildDescriptionTranslator(),
			buildAccessMethodsTranslator(),
			buildCustomizerTranslator(),
			buildChangeTrackingTranslator(),
			XmlStruct.buildTranslator(EclipseLink2_3.STRUCT, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlEmbeddable_2_3_Struct()),
			buildConverterTranslator(),
			buildTypeConverterTranslator(),
			buildObjectTypeConverterTranslator(),
			buildStructConverterTranslator(),
			buildCopyPolicyTranslator(),
			buildInstantiationCoypPolicyTranslator(),
			buildCloneCopyPolicyTranslator(),
			XmlPlsqlRecord.buildTranslator(EclipseLink2_3.PLSQL_RECORD, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlEmbeddable_2_3_PlsqlRecords()),
			XmlPlsqlTable.buildTranslator(EclipseLink2_3.PLSQL_TABLE, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlEmbeddable_2_3_PlsqlTables()),
			buildPropertyTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			Attributes.buildTranslator()};
	}
	
	protected static Translator buildExcludeDefaultMappingsTranslator() {
		return new Translator(EclipseLink.EXCLUDE_DEFAULT_MAPPINGS, EclipseLinkOrmPackage.eINSTANCE.getXmlEmbeddable_ExcludeDefaultMappings(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildCustomizerTranslator() {
		return XmlClassReference.buildTranslator(EclipseLink.CUSTOMIZER, EclipseLinkOrmPackage.eINSTANCE.getXmlCustomizerHolder_Customizer());
	}
	
	protected static Translator buildChangeTrackingTranslator() {
		return XmlChangeTracking.buildTranslator(EclipseLink.CHANGE_TRACKING, EclipseLinkOrmPackage.eINSTANCE.getXmlChangeTrackingHolder_ChangeTracking());
	}
	
	protected static Translator buildConverterTranslator() {
		return XmlConverter.buildTranslator(EclipseLink.CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_Converters());
	}
	
	protected static Translator buildTypeConverterTranslator() {
		return XmlTypeConverter.buildTranslator(EclipseLink.TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_TypeConverters());
	}
	
	protected static Translator buildObjectTypeConverterTranslator() {
		return XmlObjectTypeConverter.buildTranslator(EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_ObjectTypeConverters());
	}
	
	protected static Translator buildStructConverterTranslator() {
		return XmlStructConverter.buildTranslator(EclipseLink.STRUCT_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_StructConverters());
	}
	
	protected static Translator buildCopyPolicyTranslator() {
		return XmlCopyPolicy.buildTranslator(EclipseLink.COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEmbeddable_CopyPolicy());
	}
	
	protected static Translator buildInstantiationCoypPolicyTranslator() {
		return XmlInstantiationCopyPolicy.buildTranslator(EclipseLink.INSTANTIATION_COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEmbeddable_InstantiationCopyPolicy());
	}
	
	protected static Translator buildCloneCopyPolicyTranslator() {
		return XmlCloneCopyPolicy.buildTranslator(EclipseLink.CLONE_COPY_POLICY, EclipseLinkOrmPackage.eINSTANCE.getXmlEmbeddable_CloneCopyPolicy());
	}
	
	protected static Translator buildPropertyTranslator() {
		return XmlProperty.buildTranslator(EclipseLink.PROPERTY, EclipseLinkOrmPackage.eINSTANCE.getXmlPropertyContainer_Properties());
	}

	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

	protected static Translator buildParentClassTranslator() {
		return new Translator(EclipseLink2_2.PARENT_CLASS, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlEmbeddable_2_2_ParentClass(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA.ATTRIBUTE_OVERRIDE, OrmPackage.eINSTANCE.getXmlAttributeOverrideContainer_AttributeOverrides());
	}
	
	protected static Translator buildAssociationOverrideTranslator() {
		return XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, OrmPackage.eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides());
	}
}
