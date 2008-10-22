/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlOneToMany.java,v 1.6 2008/10/22 20:26:47 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.EclipseLinkOrmXmlMapper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml One To Many</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOneToMany()
 * @model kind="class"
 * @generated
 */
public class XmlOneToMany extends XmlOneToManyImpl implements XmlPrivateOwned, XmlJoinFetch
{
	/**
	 * The default value of the '{@link #isPrivateOwned() <em>Private Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrivateOwned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRIVATE_OWNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPrivateOwned() <em>Private Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrivateOwned()
	 * @generated
	 * @ordered
	 */
	protected boolean privateOwned = PRIVATE_OWNED_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlJoinFetchType JOIN_FETCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJoinFetch() <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinFetch()
	 * @generated
	 * @ordered
	 */
	protected XmlJoinFetchType joinFetch = JOIN_FETCH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOneToMany()
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
		return EclipseLinkOrmPackage.Literals.XML_ONE_TO_MANY;
	}

	/**
	 * Returns the value of the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Private Owned</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private Owned</em>' attribute.
	 * @see #setPrivateOwned(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned_PrivateOwned()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isPrivateOwned()
	{
		return privateOwned;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany#isPrivateOwned <em>Private Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Private Owned</em>' attribute.
	 * @see #isPrivateOwned()
	 * @generated
	 */
	public void setPrivateOwned(boolean newPrivateOwned)
	{
		boolean oldPrivateOwned = privateOwned;
		privateOwned = newPrivateOwned;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED, oldPrivateOwned, privateOwned));
	}

	/**
	 * Returns the value of the '<em><b>Join Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #setJoinFetch(XmlJoinFetchType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch_JoinFetch()
	 * @model
	 * @generated
	 */
	public XmlJoinFetchType getJoinFetch()
	{
		return joinFetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany#getJoinFetch <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #getJoinFetch()
	 * @generated
	 */
	public void setJoinFetch(XmlJoinFetchType newJoinFetch)
	{
		XmlJoinFetchType oldJoinFetch = joinFetch;
		joinFetch = newJoinFetch == null ? JOIN_FETCH_EDEFAULT : newJoinFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH, oldJoinFetch, joinFetch));
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
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED:
				return isPrivateOwned() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH:
				return getJoinFetch();
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
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED:
				setPrivateOwned(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH:
				setJoinFetch((XmlJoinFetchType)newValue);
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
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED:
				setPrivateOwned(PRIVATE_OWNED_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH:
				setJoinFetch(JOIN_FETCH_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED:
				return privateOwned != PRIVATE_OWNED_EDEFAULT;
			case EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH:
				return joinFetch != JOIN_FETCH_EDEFAULT;
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
		if (baseClass == XmlPrivateOwned.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED: return EclipseLinkOrmPackage.XML_PRIVATE_OWNED__PRIVATE_OWNED;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinFetch.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH: return EclipseLinkOrmPackage.XML_JOIN_FETCH__JOIN_FETCH;
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
		if (baseClass == XmlPrivateOwned.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PRIVATE_OWNED__PRIVATE_OWNED: return EclipseLinkOrmPackage.XML_ONE_TO_MANY__PRIVATE_OWNED;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinFetch.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_JOIN_FETCH__JOIN_FETCH: return EclipseLinkOrmPackage.XML_ONE_TO_MANY__JOIN_FETCH;
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
		result.append(" (privateOwned: ");
		result.append(privateOwned);
		result.append(", joinFetch: ");
		result.append(joinFetch);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getPrivateOwnedTextRange() {
		return getElementTextRange(EclipseLinkOrmXmlMapper.PRIVATE_OWNED);
	}
	
	public TextRange getJoinFetchTextRange() {
		return getElementTextRange(EclipseLinkOrmXmlMapper.JOIN_FETCH);
	}
}