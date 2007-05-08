/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Table Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaTableGenerator()
 * @model kind="class"
 * @generated
 */
public class JavaTableGenerator extends JavaGenerator
	implements ITableGenerator
{
	private final AnnotationElementAdapter tableAdapter;

	private final AnnotationElementAdapter catalogAdapter;

	private final AnnotationElementAdapter schemaAdapter;

	private final AnnotationElementAdapter pkColumnNameAdapter;

	private final AnnotationElementAdapter valueColumnNameAdapter;

	private final AnnotationElementAdapter pkColumnValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.TABLE_GENERATOR);

	private static final DeclarationAnnotationElementAdapter NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter INITIAL_VALUE_ADAPTER = buildNumberAdapter(JPA.TABLE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter ALLOCATION_SIZE_ADAPTER = buildNumberAdapter(JPA.TABLE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter TABLE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__TABLE);

	private static final DeclarationAnnotationElementAdapter CATALOG_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__CATALOG);

	private static final DeclarationAnnotationElementAdapter SCHEMA_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__SCHEMA);

	private static final DeclarationAnnotationElementAdapter PK_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_NAME);

	private static final DeclarationAnnotationElementAdapter VALUE_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__VALUE_COLUMN_NAME);

	private static final DeclarationAnnotationElementAdapter PK_COLUMN_VALUE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_VALUE);

	/**
	 * The default value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedTable() <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTable()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedTable() <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTable()
	 * @generated
	 * @ordered
	 */
	protected String specifiedTable = SPECIFIED_TABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultTable() <em>Default Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTable()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultTable() <em>Default Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTable()
	 * @generated
	 * @ordered
	 */
	protected String defaultTable = DEFAULT_TABLE_EDEFAULT;

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
	 * The default value of the '{@link #getPkColumnName() <em>Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String PK_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedPkColumnName() <em>Specified Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_PK_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedPkColumnName() <em>Specified Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedPkColumnName = SPECIFIED_PK_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultPkColumnName() <em>Default Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_PK_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultPkColumnName() <em>Default Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected String defaultPkColumnName = DEFAULT_PK_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueColumnName() <em>Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedValueColumnName() <em>Specified Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_VALUE_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedValueColumnName() <em>Specified Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedValueColumnName = SPECIFIED_VALUE_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultValueColumnName() <em>Default Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValueColumnName() <em>Default Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected String defaultValueColumnName = DEFAULT_VALUE_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPkColumnValue() <em>Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected static final String PK_COLUMN_VALUE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedPkColumnValue() <em>Specified Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_PK_COLUMN_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedPkColumnValue() <em>Specified Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected String specifiedPkColumnValue = SPECIFIED_PK_COLUMN_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultPkColumnValue() <em>Default Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_PK_COLUMN_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultPkColumnValue() <em>Default Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultPkColumnValue = DEFAULT_PK_COLUMN_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUniqueConstraints() <em>Unique Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IUniqueConstraint> uniqueConstraints;

	protected JavaTableGenerator() {
		throw new UnsupportedOperationException("Use JavaTableGenerator(Member) instead");
	}

	protected JavaTableGenerator(Member member) {
		super(member);
		this.tableAdapter = this.buildAdapter(TABLE_ADAPTER);
		this.catalogAdapter = this.buildAdapter(CATALOG_ADAPTER);
		this.schemaAdapter = this.buildAdapter(SCHEMA_ADAPTER);
		this.pkColumnNameAdapter = this.buildAdapter(PK_COLUMN_NAME_ADAPTER);
		this.valueColumnNameAdapter = this.buildAdapter(VALUE_COLUMN_NAME_ADAPTER);
		this.pkColumnValueAdapter = this.buildAdapter(PK_COLUMN_VALUE_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(ITableGenerator.class)) {
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
				this.tableAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
				this.catalogAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
				this.schemaAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
				this.valueColumnNameAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
				this.pkColumnNameAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
				this.pkColumnValueAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				uniqueConstraintsChanged(notification);
				break;
			default :
				break;
		}
	}
	
	void uniqueConstraintsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				uniqueConstraintAdded(notification.getPosition(), (IUniqueConstraint) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				uniqueConstraintsAdded(notification.getPosition(), (List<IUniqueConstraint>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				uniqueConstraintRemoved(notification.getPosition(), (IUniqueConstraint) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					uniqueConstraintsCleared((List<IUniqueConstraint>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					uniqueConstraintsRemoved((int[]) notification.getNewValue(), (List<IUniqueConstraint>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					uniqueConstraintSet(notification.getPosition(), (IUniqueConstraint) notification.getOldValue(), (IUniqueConstraint) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				uniqueConstraintMoved(notification.getOldIntValue(), notification.getPosition(), (IUniqueConstraint) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	// ********** initialization **********
	protected DeclarationAnnotationAdapter annotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter nameAdapter() {
		return NAME_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter initialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter allocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_TABLE_GENERATOR;
	}

	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	/**
	 * Returns the value of the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Table</em>' attribute.
	 * @see #setSpecifiedTable(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedTable()
	 * @model
	 * @generated
	 */
	public String getSpecifiedTable() {
		return specifiedTable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedTable <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table</em>' attribute.
	 * @see #getSpecifiedTable()
	 * @generated
	 */
	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = specifiedTable;
		specifiedTable = newSpecifiedTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE, oldSpecifiedTable, specifiedTable));
	}

	/**
	 * Returns the value of the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultTable()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultTable() {
		return defaultTable;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	public String getSpecifiedCatalog() {
		return specifiedCatalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog</em>' attribute.
	 * @see #getSpecifiedCatalog()
	 * @generated
	 */
	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = specifiedCatalog;
		specifiedCatalog = newSpecifiedCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG, oldSpecifiedCatalog, specifiedCatalog));
	}

	/**
	 * Returns the value of the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultCatalog()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultCatalog() {
		return defaultCatalog;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSchema() {
		return specifiedSchema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema</em>' attribute.
	 * @see #getSpecifiedSchema()
	 * @generated
	 */
	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = specifiedSchema;
		specifiedSchema = newSpecifiedSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA, oldSpecifiedSchema, specifiedSchema));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultSchema()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA, oldDefaultSchema, this.defaultSchema));
	}

	public String getPkColumnName() {
		return (this.getSpecifiedPkColumnName() == null) ? getDefaultPkColumnName() : this.getSpecifiedPkColumnName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Pk Column Name</em>' attribute.
	 * @see #setSpecifiedPkColumnName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedPkColumnName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedPkColumnName() {
		return specifiedPkColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedPkColumnName <em>Specified Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Pk Column Name</em>' attribute.
	 * @see #getSpecifiedPkColumnName()
	 * @generated
	 */
	public void setSpecifiedPkColumnName(String newSpecifiedPkColumnName) {
		String oldSpecifiedPkColumnName = specifiedPkColumnName;
		specifiedPkColumnName = newSpecifiedPkColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME, oldSpecifiedPkColumnName, specifiedPkColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Default Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Pk Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultPkColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultPkColumnName() {
		return defaultPkColumnName;
	}

	public String getValueColumnName() {
		return (this.getSpecifiedValueColumnName() == null) ? getDefaultValueColumnName() : this.getSpecifiedValueColumnName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Value Column Name</em>' attribute.
	 * @see #setSpecifiedValueColumnName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedValueColumnName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedValueColumnName() {
		return specifiedValueColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedValueColumnName <em>Specified Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Value Column Name</em>' attribute.
	 * @see #getSpecifiedValueColumnName()
	 * @generated
	 */
	public void setSpecifiedValueColumnName(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = specifiedValueColumnName;
		specifiedValueColumnName = newSpecifiedValueColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME, oldSpecifiedValueColumnName, specifiedValueColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Default Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultValueColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultValueColumnName() {
		return defaultValueColumnName;
	}

	public String getPkColumnValue() {
		return (this.getSpecifiedPkColumnValue() == null) ? getDefaultPkColumnValue() : this.getSpecifiedPkColumnValue();
	}

	/**
	 * Returns the value of the '<em><b>Specified Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Pk Column Value</em>' attribute.
	 * @see #setSpecifiedPkColumnValue(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_SpecifiedPkColumnValue()
	 * @model
	 * @generated
	 */
	public String getSpecifiedPkColumnValue() {
		return specifiedPkColumnValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator#getSpecifiedPkColumnValue <em>Specified Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Pk Column Value</em>' attribute.
	 * @see #getSpecifiedPkColumnValue()
	 * @generated
	 */
	public void setSpecifiedPkColumnValue(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = specifiedPkColumnValue;
		specifiedPkColumnValue = newSpecifiedPkColumnValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE, oldSpecifiedPkColumnValue, specifiedPkColumnValue));
	}

	/**
	 * Returns the value of the '<em><b>Default Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Pk Column Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_DefaultPkColumnValue()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultPkColumnValue() {
		return defaultPkColumnValue;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITableGenerator_UniqueConstraints()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IUniqueConstraint" containment="true"
	 * @generated
	 */
	public EList<IUniqueConstraint> getUniqueConstraints() {
		if (uniqueConstraints == null) {
			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS);
		}
		return uniqueConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				return ((InternalEList<?>) getUniqueConstraints()).basicRemove(otherEnd, msgs);
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
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__TABLE :
				return getTable();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
				return getSpecifiedTable();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_TABLE :
				return getDefaultTable();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__CATALOG :
				return getCatalog();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
				return getSpecifiedCatalog();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_CATALOG :
				return getDefaultCatalog();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SCHEMA :
				return getSchema();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
				return getSpecifiedSchema();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA :
				return getDefaultSchema();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_NAME :
				return getPkColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
				return getSpecifiedPkColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME :
				return getDefaultPkColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__VALUE_COLUMN_NAME :
				return getValueColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
				return getSpecifiedValueColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME :
				return getDefaultValueColumnName();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_VALUE :
				return getPkColumnValue();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
				return getSpecifiedPkColumnValue();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE :
				return getDefaultPkColumnValue();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				return getUniqueConstraints();
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
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
				setSpecifiedTable((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
				setSpecifiedCatalog((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
				setSpecifiedSchema((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
				setSpecifiedPkColumnName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
				setSpecifiedValueColumnName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
				setSpecifiedPkColumnValue((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
				getUniqueConstraints().addAll((Collection<? extends IUniqueConstraint>) newValue);
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
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
				setSpecifiedTable(SPECIFIED_TABLE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
				setSpecifiedCatalog(SPECIFIED_CATALOG_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
				setSpecifiedSchema(SPECIFIED_SCHEMA_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
				setSpecifiedPkColumnName(SPECIFIED_PK_COLUMN_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
				setSpecifiedValueColumnName(SPECIFIED_VALUE_COLUMN_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
				setSpecifiedPkColumnValue(SPECIFIED_PK_COLUMN_VALUE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
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
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__TABLE :
				return TABLE_EDEFAULT == null ? getTable() != null : !TABLE_EDEFAULT.equals(getTable());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
				return SPECIFIED_TABLE_EDEFAULT == null ? specifiedTable != null : !SPECIFIED_TABLE_EDEFAULT.equals(specifiedTable);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_TABLE :
				return DEFAULT_TABLE_EDEFAULT == null ? defaultTable != null : !DEFAULT_TABLE_EDEFAULT.equals(defaultTable);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__CATALOG :
				return CATALOG_EDEFAULT == null ? getCatalog() != null : !CATALOG_EDEFAULT.equals(getCatalog());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
				return SPECIFIED_CATALOG_EDEFAULT == null ? specifiedCatalog != null : !SPECIFIED_CATALOG_EDEFAULT.equals(specifiedCatalog);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_CATALOG :
				return DEFAULT_CATALOG_EDEFAULT == null ? defaultCatalog != null : !DEFAULT_CATALOG_EDEFAULT.equals(defaultCatalog);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SCHEMA :
				return SCHEMA_EDEFAULT == null ? getSchema() != null : !SCHEMA_EDEFAULT.equals(getSchema());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
				return SPECIFIED_SCHEMA_EDEFAULT == null ? specifiedSchema != null : !SPECIFIED_SCHEMA_EDEFAULT.equals(specifiedSchema);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA :
				return DEFAULT_SCHEMA_EDEFAULT == null ? defaultSchema != null : !DEFAULT_SCHEMA_EDEFAULT.equals(defaultSchema);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_NAME :
				return PK_COLUMN_NAME_EDEFAULT == null ? getPkColumnName() != null : !PK_COLUMN_NAME_EDEFAULT.equals(getPkColumnName());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
				return SPECIFIED_PK_COLUMN_NAME_EDEFAULT == null ? specifiedPkColumnName != null : !SPECIFIED_PK_COLUMN_NAME_EDEFAULT.equals(specifiedPkColumnName);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME :
				return DEFAULT_PK_COLUMN_NAME_EDEFAULT == null ? defaultPkColumnName != null : !DEFAULT_PK_COLUMN_NAME_EDEFAULT.equals(defaultPkColumnName);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__VALUE_COLUMN_NAME :
				return VALUE_COLUMN_NAME_EDEFAULT == null ? getValueColumnName() != null : !VALUE_COLUMN_NAME_EDEFAULT.equals(getValueColumnName());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
				return SPECIFIED_VALUE_COLUMN_NAME_EDEFAULT == null ? specifiedValueColumnName != null : !SPECIFIED_VALUE_COLUMN_NAME_EDEFAULT.equals(specifiedValueColumnName);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME :
				return DEFAULT_VALUE_COLUMN_NAME_EDEFAULT == null ? defaultValueColumnName != null : !DEFAULT_VALUE_COLUMN_NAME_EDEFAULT.equals(defaultValueColumnName);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_VALUE :
				return PK_COLUMN_VALUE_EDEFAULT == null ? getPkColumnValue() != null : !PK_COLUMN_VALUE_EDEFAULT.equals(getPkColumnValue());
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
				return SPECIFIED_PK_COLUMN_VALUE_EDEFAULT == null ? specifiedPkColumnValue != null : !SPECIFIED_PK_COLUMN_VALUE_EDEFAULT.equals(specifiedPkColumnValue);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE :
				return DEFAULT_PK_COLUMN_VALUE_EDEFAULT == null ? defaultPkColumnValue != null : !DEFAULT_PK_COLUMN_VALUE_EDEFAULT.equals(defaultPkColumnValue);
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
				return uniqueConstraints != null && !uniqueConstraints.isEmpty();
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
		if (baseClass == ITableGenerator.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__TABLE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__TABLE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_TABLE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_TABLE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_TABLE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__CATALOG :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__CATALOG;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_CATALOG;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_CATALOG :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_CATALOG;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SCHEMA :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SCHEMA;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_SCHEMA;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_SCHEMA;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__PK_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__VALUE_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__VALUE_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_VALUE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__PK_COLUMN_VALUE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE;
				case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS :
					return JpaCoreMappingsPackage.ITABLE_GENERATOR__UNIQUE_CONSTRAINTS;
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
		if (baseClass == ITableGenerator.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__TABLE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__TABLE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_TABLE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_TABLE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_TABLE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_TABLE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__CATALOG :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__CATALOG;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_CATALOG :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_CATALOG;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_CATALOG :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_CATALOG;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SCHEMA :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SCHEMA;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_SCHEMA :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_SCHEMA;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_SCHEMA :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_SCHEMA;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__PK_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__VALUE_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__VALUE_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_VALUE_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_VALUE_COLUMN_NAME;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__PK_COLUMN_VALUE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__PK_COLUMN_VALUE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__SPECIFIED_PK_COLUMN_VALUE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__DEFAULT_PK_COLUMN_VALUE;
				case JpaCoreMappingsPackage.ITABLE_GENERATOR__UNIQUE_CONSTRAINTS :
					return JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS;
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
		result.append(" (specifiedTable: ");
		result.append(specifiedTable);
		result.append(", defaultTable: ");
		result.append(defaultTable);
		result.append(", specifiedCatalog: ");
		result.append(specifiedCatalog);
		result.append(", defaultCatalog: ");
		result.append(defaultCatalog);
		result.append(", specifiedSchema: ");
		result.append(specifiedSchema);
		result.append(", defaultSchema: ");
		result.append(defaultSchema);
		result.append(", specifiedPkColumnName: ");
		result.append(specifiedPkColumnName);
		result.append(", defaultPkColumnName: ");
		result.append(defaultPkColumnName);
		result.append(", specifiedValueColumnName: ");
		result.append(specifiedValueColumnName);
		result.append(", defaultValueColumnName: ");
		result.append(defaultValueColumnName);
		result.append(", specifiedPkColumnValue: ");
		result.append(specifiedPkColumnValue);
		result.append(", defaultPkColumnValue: ");
		result.append(defaultPkColumnValue);
		result.append(')');
		return result.toString();
	}

	// ********** java annotations -> persistence model **********
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setSpecifiedTable((String) this.tableAdapter.getValue(astRoot));
		setSpecifiedCatalog((String) this.catalogAdapter.getValue(astRoot));
		setSpecifiedSchema((String) this.schemaAdapter.getValue(astRoot));
		setSpecifiedPkColumnName((String) this.pkColumnNameAdapter.getValue(astRoot));
		setSpecifiedValueColumnName((String) this.valueColumnNameAdapter.getValue(astRoot));
		setSpecifiedPkColumnValue((String) this.pkColumnValueAdapter.getValue(astRoot));
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY));
	}

	// ********** jpa model -> java annotations **********
	////////////////////////////////////////////////////////
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void uniqueConstraintAdded(int index, IUniqueConstraint uniqueConstraint) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaUniqueConstraint) uniqueConstraint).annotation(getMember().astRoot()) == null) {
			this.synchUniqueConstraintAnnotationsAfterAdd(index + 1);
			((JavaUniqueConstraint) uniqueConstraint).newAnnotation();
		}
	}

	// bjv look at this
	public void uniqueConstraintsAdded(int index, List<IUniqueConstraint> uniqueConstraints) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (!uniqueConstraints.isEmpty() && ((JavaUniqueConstraint) uniqueConstraints.get(0)).annotation(getMember().astRoot()) == null) {
			this.synchUniqueConstraintAnnotationsAfterAdd(index + uniqueConstraints.size());
			for (IUniqueConstraint uniqueConstraint : uniqueConstraints) {
				((JavaUniqueConstraint) uniqueConstraint).newAnnotation();
			}
		}
	}

	public void uniqueConstraintRemoved(int index, IUniqueConstraint uniqueConstraint) {
		((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		this.synchUniqueConstraintAnnotationsAfterRemove(index);
	}

	public void uniqueConstraintsRemoved(int[] indexes, List<IUniqueConstraint> uniqueConstraints) {
		for (IUniqueConstraint uniqueConstraint : uniqueConstraints) {
			((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		}
		this.synchUniqueConstraintAnnotationsAfterRemove(indexes[0]);
	}

	public void uniqueConstraintsCleared(List<IUniqueConstraint> uniqueConstraints) {
		for (IUniqueConstraint uniqueConstraint : uniqueConstraints) {
			((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		}
	}

	public void uniqueConstraintSet(int index, IUniqueConstraint oldUniqueConstraint, IUniqueConstraint newUniqueConstraint) {
		((JavaUniqueConstraint) newUniqueConstraint).newAnnotation();
	}

	public void uniqueConstraintMoved(int sourceIndex, int targetIndex, IUniqueConstraint uniqueConstraint) {
		List<IUniqueConstraint> uniqueConstraints = this.getUniqueConstraints();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch(uniqueConstraints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterAdd(int index) {
		List<IUniqueConstraint> uniqueConstraints = this.getUniqueConstraints();
		for (int i = uniqueConstraints.size(); i-- > index;) {
			this.synch(uniqueConstraints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterRemove(int index) {
		List<IUniqueConstraint> joinColumns = this.getUniqueConstraints();
		for (int i = index; i < joinColumns.size(); i++) {
			this.synch(joinColumns.get(i), i);
		}
	}
	
	private void synch(IUniqueConstraint uniqueConstraint, int index) {
		((JavaUniqueConstraint) uniqueConstraint).moveAnnotation(index);
	}

	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter buildNumberAdapter(String elementName) {
		return buildNumberAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}
} // JavaTableGenerator
