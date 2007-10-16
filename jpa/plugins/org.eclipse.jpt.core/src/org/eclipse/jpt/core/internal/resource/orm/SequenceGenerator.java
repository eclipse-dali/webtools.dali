/**
 * <copyright>
 * </copyright>
 *
 * $Id: SequenceGenerator.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sequence Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getSequenceName <em>Sequence Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize <em>Allocation Size</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class SequenceGenerator extends JptEObject implements IJptEObject
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
	 * The default value of the '{@link #getSequenceName() <em>Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSequenceName() <em>Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceName()
	 * @generated
	 * @ordered
	 */
	protected String sequenceName = SEQUENCE_NAME_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SequenceGenerator()
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
		return OrmPackage.Literals.SEQUENCE_GENERATOR;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.SEQUENCE_GENERATOR__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Name</em>' attribute.
	 * @see #setSequenceName(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator_SequenceName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSequenceName()
	{
		return sequenceName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getSequenceName <em>Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Name</em>' attribute.
	 * @see #getSequenceName()
	 * @generated
	 */
	public void setSequenceName(String newSequenceName)
	{
		String oldSequenceName = sequenceName;
		sequenceName = newSequenceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.SEQUENCE_GENERATOR__SEQUENCE_NAME, oldSequenceName, sequenceName));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator_InitialValue()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	public int getInitialValue()
	{
		return initialValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue <em>Initial Value</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE, oldInitialValue, initialValue, !oldInitialValueESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue <em>Initial Value</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE, oldInitialValue, INITIAL_VALUE_EDEFAULT, oldInitialValueESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getInitialValue <em>Initial Value</em>}' attribute is set.
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getSequenceGenerator_AllocationSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	public int getAllocationSize()
	{
		return allocationSize;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize <em>Allocation Size</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE, oldAllocationSize, allocationSize, !oldAllocationSizeESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize <em>Allocation Size</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE, oldAllocationSize, ALLOCATION_SIZE_EDEFAULT, oldAllocationSizeESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator#getAllocationSize <em>Allocation Size</em>}' attribute is set.
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OrmPackage.SEQUENCE_GENERATOR__NAME:
				return getName();
			case OrmPackage.SEQUENCE_GENERATOR__SEQUENCE_NAME:
				return getSequenceName();
			case OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE:
				return new Integer(getInitialValue());
			case OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE:
				return new Integer(getAllocationSize());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.SEQUENCE_GENERATOR__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.SEQUENCE_GENERATOR__SEQUENCE_NAME:
				setSequenceName((String)newValue);
				return;
			case OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE:
				setInitialValue(((Integer)newValue).intValue());
				return;
			case OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE:
				setAllocationSize(((Integer)newValue).intValue());
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
			case OrmPackage.SEQUENCE_GENERATOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.SEQUENCE_GENERATOR__SEQUENCE_NAME:
				setSequenceName(SEQUENCE_NAME_EDEFAULT);
				return;
			case OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE:
				unsetInitialValue();
				return;
			case OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE:
				unsetAllocationSize();
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
			case OrmPackage.SEQUENCE_GENERATOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.SEQUENCE_GENERATOR__SEQUENCE_NAME:
				return SEQUENCE_NAME_EDEFAULT == null ? sequenceName != null : !SEQUENCE_NAME_EDEFAULT.equals(sequenceName);
			case OrmPackage.SEQUENCE_GENERATOR__INITIAL_VALUE:
				return isSetInitialValue();
			case OrmPackage.SEQUENCE_GENERATOR__ALLOCATION_SIZE:
				return isSetAllocationSize();
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
		result.append(", sequenceName: ");
		result.append(sequenceName);
		result.append(", initialValue: ");
		if (initialValueESet) result.append(initialValue); else result.append("<unset>");
		result.append(", allocationSize: ");
		if (allocationSizeESet) result.append(allocationSize); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // SequenceGenerator
