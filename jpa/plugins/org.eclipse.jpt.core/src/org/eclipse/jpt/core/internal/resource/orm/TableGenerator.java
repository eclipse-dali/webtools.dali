/**
 * <copyright>
 * </copyright>
 *
 * $Id: TableGenerator.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnName <em>Pk Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getValueColumnName <em>Value Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnValue <em>Pk Column Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize <em>Allocation Size</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getUniqueConstraints <em>Unique Constraints</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class TableGenerator extends JptEObject implements IJptEObject
{
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
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected String table = TABLE_EDEFAULT;

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
	 * The cached value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected String catalog = CATALOG_EDEFAULT;

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
	 * The cached value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected String schema = SCHEMA_EDEFAULT;

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
	 * The cached value of the '{@link #getPkColumnName() <em>Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkColumnName()
	 * @generated
	 * @ordered
	 */
	protected String pkColumnName = PK_COLUMN_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getValueColumnName() <em>Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueColumnName()
	 * @generated
	 * @ordered
	 */
	protected String valueColumnName = VALUE_COLUMN_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getPkColumnValue() <em>Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkColumnValue()
	 * @generated
	 * @ordered
	 */
	protected String pkColumnValue = PK_COLUMN_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitialValue() <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialValue()
	 * @generated
	 * @ordered
	 */
	protected static final int INITIAL_VALUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInitialValue() <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialValue()
	 * @generated
	 * @ordered
	 */
	protected int initialValue = INITIAL_VALUE_EDEFAULT;

	/**
	 * This is true if the Initial Value attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean initialValueESet;

	/**
	 * The default value of the '{@link #getAllocationSize() <em>Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected static final int ALLOCATION_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAllocationSize() <em>Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected int allocationSize = ALLOCATION_SIZE_EDEFAULT;

	/**
	 * This is true if the Allocation Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean allocationSizeESet;

	/**
	 * The cached value of the '{@link #getUniqueConstraints() <em>Unique Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<UniqueConstraint> uniqueConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableGenerator()
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
		return OrmPackage.Literals.TABLE_GENERATOR;
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
	 * @see #setName(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see #setTable(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTable()
	{
		return table;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(String newTable)
	{
		String oldTable = table;
		table = newTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__TABLE, oldTable, table));
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
	 * @see #setCatalog(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getCatalog <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog</em>' attribute.
	 * @see #getCatalog()
	 * @generated
	 */
	public void setCatalog(String newCatalog)
	{
		String oldCatalog = catalog;
		catalog = newCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__CATALOG, oldCatalog, catalog));
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
	 * @see #setSchema(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getSchema <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see #getSchema()
	 * @generated
	 */
	public void setSchema(String newSchema)
	{
		String oldSchema = schema;
		schema = newSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__SCHEMA, oldSchema, schema));
	}

	/**
	 * Returns the value of the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pk Column Name</em>' attribute.
	 * @see #setPkColumnName(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_PkColumnName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPkColumnName()
	{
		return pkColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnName <em>Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pk Column Name</em>' attribute.
	 * @see #getPkColumnName()
	 * @generated
	 */
	public void setPkColumnName(String newPkColumnName)
	{
		String oldPkColumnName = pkColumnName;
		pkColumnName = newPkColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__PK_COLUMN_NAME, oldPkColumnName, pkColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Column Name</em>' attribute.
	 * @see #setValueColumnName(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_ValueColumnName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getValueColumnName()
	{
		return valueColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getValueColumnName <em>Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Column Name</em>' attribute.
	 * @see #getValueColumnName()
	 * @generated
	 */
	public void setValueColumnName(String newValueColumnName)
	{
		String oldValueColumnName = valueColumnName;
		valueColumnName = newValueColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__VALUE_COLUMN_NAME, oldValueColumnName, valueColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pk Column Value</em>' attribute.
	 * @see #setPkColumnValue(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_PkColumnValue()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPkColumnValue()
	{
		return pkColumnValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getPkColumnValue <em>Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pk Column Value</em>' attribute.
	 * @see #getPkColumnValue()
	 * @generated
	 */
	public void setPkColumnValue(String newPkColumnValue)
	{
		String oldPkColumnValue = pkColumnValue;
		pkColumnValue = newPkColumnValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__PK_COLUMN_VALUE, oldPkColumnValue, pkColumnValue));
	}

	/**
	 * Returns the value of the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Value</em>' attribute.
	 * @see #isSetInitialValue()
	 * @see #unsetInitialValue()
	 * @see #setInitialValue(int)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_InitialValue()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	public int getInitialValue()
	{
		return initialValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Value</em>' attribute.
	 * @see #isSetInitialValue()
	 * @see #unsetInitialValue()
	 * @see #getInitialValue()
	 * @generated
	 */
	public void setInitialValue(int newInitialValue)
	{
		int oldInitialValue = initialValue;
		initialValue = newInitialValue;
		boolean oldInitialValueESet = initialValueESet;
		initialValueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__INITIAL_VALUE, oldInitialValue, initialValue, !oldInitialValueESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInitialValue()
	 * @see #getInitialValue()
	 * @see #setInitialValue(int)
	 * @generated
	 */
	public void unsetInitialValue()
	{
		int oldInitialValue = initialValue;
		boolean oldInitialValueESet = initialValueESet;
		initialValue = INITIAL_VALUE_EDEFAULT;
		initialValueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.TABLE_GENERATOR__INITIAL_VALUE, oldInitialValue, INITIAL_VALUE_EDEFAULT, oldInitialValueESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getInitialValue <em>Initial Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Initial Value</em>' attribute is set.
	 * @see #unsetInitialValue()
	 * @see #getInitialValue()
	 * @see #setInitialValue(int)
	 * @generated
	 */
	public boolean isSetInitialValue()
	{
		return initialValueESet;
	}

	/**
	 * Returns the value of the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation Size</em>' attribute.
	 * @see #isSetAllocationSize()
	 * @see #unsetAllocationSize()
	 * @see #setAllocationSize(int)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_AllocationSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	public int getAllocationSize()
	{
		return allocationSize;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize <em>Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation Size</em>' attribute.
	 * @see #isSetAllocationSize()
	 * @see #unsetAllocationSize()
	 * @see #getAllocationSize()
	 * @generated
	 */
	public void setAllocationSize(int newAllocationSize)
	{
		int oldAllocationSize = allocationSize;
		allocationSize = newAllocationSize;
		boolean oldAllocationSizeESet = allocationSizeESet;
		allocationSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE, oldAllocationSize, allocationSize, !oldAllocationSizeESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize <em>Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAllocationSize()
	 * @see #getAllocationSize()
	 * @see #setAllocationSize(int)
	 * @generated
	 */
	public void unsetAllocationSize()
	{
		int oldAllocationSize = allocationSize;
		boolean oldAllocationSizeESet = allocationSizeESet;
		allocationSize = ALLOCATION_SIZE_EDEFAULT;
		allocationSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE, oldAllocationSize, ALLOCATION_SIZE_EDEFAULT, oldAllocationSizeESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator#getAllocationSize <em>Allocation Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Allocation Size</em>' attribute is set.
	 * @see #unsetAllocationSize()
	 * @see #getAllocationSize()
	 * @see #setAllocationSize(int)
	 * @generated
	 */
	public boolean isSetAllocationSize()
	{
		return allocationSizeESet;
	}

	/**
	 * Returns the value of the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Constraints</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getTableGenerator_UniqueConstraints()
	 * @model containment="true"
	 * @generated
	 */
	public EList<UniqueConstraint> getUniqueConstraints()
	{
		if (uniqueConstraints == null)
		{
			uniqueConstraints = new EObjectContainmentEList<UniqueConstraint>(UniqueConstraint.class, this, OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS);
		}
		return uniqueConstraints;
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
			case OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS:
				return ((InternalEList<?>)getUniqueConstraints()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.TABLE_GENERATOR__NAME:
				return getName();
			case OrmPackage.TABLE_GENERATOR__TABLE:
				return getTable();
			case OrmPackage.TABLE_GENERATOR__CATALOG:
				return getCatalog();
			case OrmPackage.TABLE_GENERATOR__SCHEMA:
				return getSchema();
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_NAME:
				return getPkColumnName();
			case OrmPackage.TABLE_GENERATOR__VALUE_COLUMN_NAME:
				return getValueColumnName();
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_VALUE:
				return getPkColumnValue();
			case OrmPackage.TABLE_GENERATOR__INITIAL_VALUE:
				return new Integer(getInitialValue());
			case OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE:
				return new Integer(getAllocationSize());
			case OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS:
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.TABLE_GENERATOR__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__TABLE:
				setTable((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__CATALOG:
				setCatalog((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__SCHEMA:
				setSchema((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_NAME:
				setPkColumnName((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__VALUE_COLUMN_NAME:
				setValueColumnName((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_VALUE:
				setPkColumnValue((String)newValue);
				return;
			case OrmPackage.TABLE_GENERATOR__INITIAL_VALUE:
				setInitialValue(((Integer)newValue).intValue());
				return;
			case OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE:
				setAllocationSize(((Integer)newValue).intValue());
				return;
			case OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS:
				getUniqueConstraints().clear();
				getUniqueConstraints().addAll((Collection<? extends UniqueConstraint>)newValue);
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
			case OrmPackage.TABLE_GENERATOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__TABLE:
				setTable(TABLE_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_NAME:
				setPkColumnName(PK_COLUMN_NAME_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__VALUE_COLUMN_NAME:
				setValueColumnName(VALUE_COLUMN_NAME_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_VALUE:
				setPkColumnValue(PK_COLUMN_VALUE_EDEFAULT);
				return;
			case OrmPackage.TABLE_GENERATOR__INITIAL_VALUE:
				unsetInitialValue();
				return;
			case OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE:
				unsetAllocationSize();
				return;
			case OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS:
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.TABLE_GENERATOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.TABLE_GENERATOR__TABLE:
				return TABLE_EDEFAULT == null ? table != null : !TABLE_EDEFAULT.equals(table);
			case OrmPackage.TABLE_GENERATOR__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case OrmPackage.TABLE_GENERATOR__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_NAME:
				return PK_COLUMN_NAME_EDEFAULT == null ? pkColumnName != null : !PK_COLUMN_NAME_EDEFAULT.equals(pkColumnName);
			case OrmPackage.TABLE_GENERATOR__VALUE_COLUMN_NAME:
				return VALUE_COLUMN_NAME_EDEFAULT == null ? valueColumnName != null : !VALUE_COLUMN_NAME_EDEFAULT.equals(valueColumnName);
			case OrmPackage.TABLE_GENERATOR__PK_COLUMN_VALUE:
				return PK_COLUMN_VALUE_EDEFAULT == null ? pkColumnValue != null : !PK_COLUMN_VALUE_EDEFAULT.equals(pkColumnValue);
			case OrmPackage.TABLE_GENERATOR__INITIAL_VALUE:
				return isSetInitialValue();
			case OrmPackage.TABLE_GENERATOR__ALLOCATION_SIZE:
				return isSetAllocationSize();
			case OrmPackage.TABLE_GENERATOR__UNIQUE_CONSTRAINTS:
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", table: ");
		result.append(table);
		result.append(", catalog: ");
		result.append(catalog);
		result.append(", schema: ");
		result.append(schema);
		result.append(", pkColumnName: ");
		result.append(pkColumnName);
		result.append(", valueColumnName: ");
		result.append(valueColumnName);
		result.append(", pkColumnValue: ");
		result.append(pkColumnValue);
		result.append(", initialValue: ");
		if (initialValueESet) result.append(initialValue); else result.append("<unset>");
		result.append(", allocationSize: ");
		if (allocationSizeESet) result.append(allocationSize); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // TableGenerator
