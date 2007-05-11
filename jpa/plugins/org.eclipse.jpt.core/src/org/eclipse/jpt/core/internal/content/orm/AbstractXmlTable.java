/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import java.util.Set;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedNameForXml <em>Specified Name For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedCatalogForXml <em>Specified Catalog For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedSchemaForXml <em>Specified Schema For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlTable extends XmlEObject implements ITable
{
	private Owner owner;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedName = SPECIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected String defaultName = DEFAULT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected String specifiedCatalog = SPECIFIED_CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected String defaultCatalog = DEFAULT_CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected String specifiedSchema = SPECIFIED_SCHEMA_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected String defaultSchema = DEFAULT_SCHEMA_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUniqueConstraints() <em>Unique Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IUniqueConstraint> uniqueConstraints;

	/**
	 * The default value of the '{@link #getSpecifiedNameForXml() <em>Specified Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedNameForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedCatalogForXml() <em>Specified Catalog For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalogForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_CATALOG_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedSchemaForXml() <em>Specified Schema For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchemaForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SCHEMA_FOR_XML_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlTable() {
		super();
	}

	protected AbstractXmlTable(Owner owner) {
		super();
		this.owner = owner;
	}

	@Override
	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__NAME);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_NAME);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__SCHEMA);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_SCHEMA);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__CATALOG);
		insignificantXmlFeatureIds.add(OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_CATALOG);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.ABSTRACT_XML_TABLE;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedNameGen(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	public void setSpecifiedName(String newSpecifiedName) {
		setSpecifiedNameGen(newSpecifiedName);
		if (newSpecifiedName != SPECIFIED_NAME_EDEFAULT) {
			makeTableForXmlNonNull();
		}
		setSpecifiedNameForXml(newSpecifiedName);
		if (isAllFeaturesUnset()) {
			makeTableForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_Catalog()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	/**
	 * Returns the value of the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Catalog</em>' attribute.
	 * @see #setSpecifiedCatalog(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	public String getSpecifiedCatalog() {
		return specifiedCatalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog</em>' attribute.
	 * @see #getSpecifiedCatalog()
	 * @generated
	 */
	public void setSpecifiedCatalogGen(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = specifiedCatalog;
		specifiedCatalog = newSpecifiedCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG, oldSpecifiedCatalog, specifiedCatalog));
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		setSpecifiedCatalogGen(newSpecifiedCatalog);
		if (newSpecifiedCatalog != SPECIFIED_CATALOG_EDEFAULT) {
			makeTableForXmlNonNull();
		}
		setSpecifiedCatalogForXml(newSpecifiedCatalog);
		if (isAllFeaturesUnset()) {
			makeTableForXmlNull();
		}
	}

	protected abstract void makeTableForXmlNull();

	protected abstract void makeTableForXmlNonNull();

	/**
	 * Returns the value of the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_DefaultCatalog()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_Schema()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	/**
	 * Returns the value of the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Schema</em>' attribute.
	 * @see #setSpecifiedSchema(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSchema() {
		return specifiedSchema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema</em>' attribute.
	 * @see #getSpecifiedSchema()
	 * @generated
	 */
	public void setSpecifiedSchemaGen(String newSpecifiedSchema) {
		String oldSpecifiedSchema = specifiedSchema;
		specifiedSchema = newSpecifiedSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA, oldSpecifiedSchema, specifiedSchema));
	}

	public void setSpecifiedSchema(String newSpecifiedSchema) {
		setSpecifiedSchemaGen(newSpecifiedSchema);
		if (newSpecifiedSchema != SPECIFIED_SCHEMA_EDEFAULT) {
			makeTableForXmlNonNull();
		}
		setSpecifiedSchemaForXml(newSpecifiedSchema);
		if (isAllFeaturesUnset()) {
			makeTableForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_DefaultSchema()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	/**
	 * Returns the value of the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Constraints</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITable_UniqueConstraints()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IUniqueConstraint" containment="true"
	 * @generated
	 */
	public EList<IUniqueConstraint> getUniqueConstraints() {
		if (uniqueConstraints == null) {
			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS);
		}
		return uniqueConstraints;
	}

	/**
	 * Returns the value of the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name For Xml</em>' attribute.
	 * @see #setSpecifiedNameForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable_SpecifiedNameForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedNameForXml() {
		return getSpecifiedName();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedNameForXml <em>Specified Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name For Xml</em>' attribute.
	 * @see #getSpecifiedNameForXml()
	 * @generated NOT
	 */
	public void setSpecifiedNameForXml(String newSpecifiedNameForXml) {
		setSpecifiedNameGen(newSpecifiedNameForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML, newSpecifiedNameForXml + " ", newSpecifiedNameForXml));
	}

	/**
	 * Returns the value of the '<em><b>Specified Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Catalog For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Catalog For Xml</em>' attribute.
	 * @see #setSpecifiedCatalogForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable_SpecifiedCatalogForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedCatalogForXml() {
		return getSpecifiedCatalog();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedCatalogForXml <em>Specified Catalog For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog For Xml</em>' attribute.
	 * @see #getSpecifiedCatalogForXml()
	 * @generated NOT
	 */
	public void setSpecifiedCatalogForXml(String newSpecifiedCatalogForXml) {
		setSpecifiedCatalogGen(newSpecifiedCatalogForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML, newSpecifiedCatalogForXml + " ", newSpecifiedCatalogForXml));
	}

	/**
	 * Returns the value of the '<em><b>Specified Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Schema For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Schema For Xml</em>' attribute.
	 * @see #setSpecifiedSchemaForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlTable_SpecifiedSchemaForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedSchemaForXml() {
		return getSpecifiedSchema();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable#getSpecifiedSchemaForXml <em>Specified Schema For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema For Xml</em>' attribute.
	 * @see #getSpecifiedSchemaForXml()
	 * @generated NOT
	 */
	public void setSpecifiedSchemaForXml(String newSpecifiedSchemaForXml) {
		setSpecifiedSchemaGen(newSpecifiedSchemaForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML, newSpecifiedSchemaForXml + " ", newSpecifiedSchemaForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
				return ((InternalEList<?>) getUniqueConstraints()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	//TODO should we allow setting through the ecore, that would make this method
	//public and part of the ITable api.  only the model needs to be setting the default,
	//but the ui needs to be listening for changes to the default.
	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TABLE__DEFAULT_NAME, oldDefaultName, this.defaultName));
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TABLE__DEFAULT_SCHEMA, oldDefaultSchema, this.defaultSchema));
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TABLE__DEFAULT_CATALOG, oldDefaultCatalog, this.defaultCatalog));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.ABSTRACT_XML_TABLE__NAME :
				return getName();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME :
				return getSpecifiedName();
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_NAME :
				return getDefaultName();
			case OrmPackage.ABSTRACT_XML_TABLE__CATALOG :
				return getCatalog();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG :
				return getSpecifiedCatalog();
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_CATALOG :
				return getDefaultCatalog();
			case OrmPackage.ABSTRACT_XML_TABLE__SCHEMA :
				return getSchema();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA :
				return getSpecifiedSchema();
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_SCHEMA :
				return getDefaultSchema();
			case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
				return getUniqueConstraints();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML :
				return getSpecifiedNameForXml();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML :
				return getSpecifiedCatalogForXml();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML :
				return getSpecifiedSchemaForXml();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG :
				setSpecifiedCatalog((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA :
				setSpecifiedSchema((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
				getUniqueConstraints().addAll((Collection<? extends IUniqueConstraint>) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML :
				setSpecifiedCatalogForXml((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML :
				setSpecifiedSchemaForXml((String) newValue);
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
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG :
				setSpecifiedCatalog(SPECIFIED_CATALOG_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA :
				setSpecifiedSchema(SPECIFIED_SCHEMA_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml(SPECIFIED_NAME_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML :
				setSpecifiedCatalogForXml(SPECIFIED_CATALOG_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML :
				setSpecifiedSchemaForXml(SPECIFIED_SCHEMA_FOR_XML_EDEFAULT);
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
			case OrmPackage.ABSTRACT_XML_TABLE__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case OrmPackage.ABSTRACT_XML_TABLE__CATALOG :
				return CATALOG_EDEFAULT == null ? getCatalog() != null : !CATALOG_EDEFAULT.equals(getCatalog());
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG :
				return SPECIFIED_CATALOG_EDEFAULT == null ? specifiedCatalog != null : !SPECIFIED_CATALOG_EDEFAULT.equals(specifiedCatalog);
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_CATALOG :
				return DEFAULT_CATALOG_EDEFAULT == null ? defaultCatalog != null : !DEFAULT_CATALOG_EDEFAULT.equals(defaultCatalog);
			case OrmPackage.ABSTRACT_XML_TABLE__SCHEMA :
				return SCHEMA_EDEFAULT == null ? getSchema() != null : !SCHEMA_EDEFAULT.equals(getSchema());
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA :
				return SPECIFIED_SCHEMA_EDEFAULT == null ? specifiedSchema != null : !SPECIFIED_SCHEMA_EDEFAULT.equals(specifiedSchema);
			case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_SCHEMA :
				return DEFAULT_SCHEMA_EDEFAULT == null ? defaultSchema != null : !DEFAULT_SCHEMA_EDEFAULT.equals(defaultSchema);
			case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
				return uniqueConstraints != null && !uniqueConstraints.isEmpty();
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME_FOR_XML :
				return SPECIFIED_NAME_FOR_XML_EDEFAULT == null ? getSpecifiedNameForXml() != null : !SPECIFIED_NAME_FOR_XML_EDEFAULT.equals(getSpecifiedNameForXml());
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG_FOR_XML :
				return SPECIFIED_CATALOG_FOR_XML_EDEFAULT == null ? getSpecifiedCatalogForXml() != null : !SPECIFIED_CATALOG_FOR_XML_EDEFAULT.equals(getSpecifiedCatalogForXml());
			case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA_FOR_XML :
				return SPECIFIED_SCHEMA_FOR_XML_EDEFAULT == null ? getSpecifiedSchemaForXml() != null : !SPECIFIED_SCHEMA_FOR_XML_EDEFAULT.equals(getSpecifiedSchemaForXml());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ITable.class) {
			switch (derivedFeatureID) {
				case OrmPackage.ABSTRACT_XML_TABLE__NAME :
					return JpaCoreMappingsPackage.ITABLE__NAME;
				case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME;
				case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_NAME :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME;
				case OrmPackage.ABSTRACT_XML_TABLE__CATALOG :
					return JpaCoreMappingsPackage.ITABLE__CATALOG;
				case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG;
				case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_CATALOG :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG;
				case OrmPackage.ABSTRACT_XML_TABLE__SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__SCHEMA;
				case OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA;
				case OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA;
				case OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS :
					return JpaCoreMappingsPackage.ITABLE__UNIQUE_CONSTRAINTS;
				default :
					return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ITable.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ITABLE__NAME :
					return OrmPackage.ABSTRACT_XML_TABLE__NAME;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME :
					return OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME :
					return OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_NAME;
				case JpaCoreMappingsPackage.ITABLE__CATALOG :
					return OrmPackage.ABSTRACT_XML_TABLE__CATALOG;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG :
					return OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_CATALOG;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG :
					return OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_CATALOG;
				case JpaCoreMappingsPackage.ITABLE__SCHEMA :
					return OrmPackage.ABSTRACT_XML_TABLE__SCHEMA;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA :
					return OrmPackage.ABSTRACT_XML_TABLE__SPECIFIED_SCHEMA;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA :
					return OrmPackage.ABSTRACT_XML_TABLE__DEFAULT_SCHEMA;
				case JpaCoreMappingsPackage.ITABLE__UNIQUE_CONSTRAINTS :
					return OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS;
				default :
					return -1;
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
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (specifiedName: ");
		result.append(specifiedName);
		result.append(", defaultName: ");
		result.append(defaultName);
		result.append(", specifiedCatalog: ");
		result.append(specifiedCatalog);
		result.append(", defaultCatalog: ");
		result.append(defaultCatalog);
		result.append(", specifiedSchema: ");
		result.append(specifiedSchema);
		result.append(", defaultSchema: ");
		result.append(defaultSchema);
		result.append(')');
		return result.toString();
	}

	/**
	 * Call this when the table tag is removed from the xml,
	 * need to make sure all the model attributes are set to the default
	 */
	protected void unsetAllAttributes() {
		eUnset(OrmPackage.XML_TABLE__SPECIFIED_NAME);
		eUnset(OrmPackage.XML_TABLE__SPECIFIED_SCHEMA);
		eUnset(OrmPackage.XML_TABLE__SPECIFIED_CATALOG);
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultCatalog((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY));
		setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY));
	}

	public ITextRange nameTextRange() {
		if (node == null) {
			return owner.validationTextRange();
		}
		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
		return (nameNode == null) ? validationTextRange() : buildTextRange(nameNode);
	}

	public ITextRange schemaTextRange() {
		if (node == null) {
			return owner.validationTextRange();
		}
		IDOMNode schemaNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.SCHEMA);
		return (schemaNode == null) ? validationTextRange() : buildTextRange(schemaNode);
	}

	@Override
	public ITextRange validationTextRange() {
		return (node == null) ? owner.validationTextRange() : super.validationTextRange();
	}

	public Owner getOwner() {
		return owner;
	}

	public Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(getName());
	}

	public Schema dbSchema() {
		return getJpaProject().connectionProfile().getDatabase().schemaNamed(getSchema());
	}

	public boolean hasResolvedSchema() {
		return dbSchema() != null;
	}

	public boolean isResolved() {
		return dbTable() != null;
	}

	public IUniqueConstraint createUniqueConstraint(int index) {
		return createXmlJavaUniqueConstraint(index);
	}

	protected XmlUniqueConstraint createXmlJavaUniqueConstraint(int index) {
		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
	}
}