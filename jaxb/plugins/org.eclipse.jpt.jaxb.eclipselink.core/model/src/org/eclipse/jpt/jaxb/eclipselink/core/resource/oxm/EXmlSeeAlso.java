/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml See Also</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso#getClasses <em>Classes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSeeAlso()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlSeeAlso extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getClasses() <em>Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASSES_EDEFAULT = "";
	
	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected String classes = CLASSES_EDEFAULT;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlSeeAlso()
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
		return OxmPackage.Literals.EXML_SEE_ALSO;
	}

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' attribute.
	 * @see #setClasses(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSeeAlso_Classes()
	 * @model default=""
	 * @generated
	 */
	public String getClasses()
	{
		return classes;
	}
	
	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso#getClasses <em>Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classes</em>' attribute.
	 * @see #getClasses()
	 * @generated
	 */
	public void setClasses(String newClasses)
	{
		String oldClasses = classes;
		classes = newClasses;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_SEE_ALSO__CLASSES, oldClasses, classes));
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
			case OxmPackage.EXML_SEE_ALSO__CLASSES:
				return getClasses();
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
			case OxmPackage.EXML_SEE_ALSO__CLASSES:
				setClasses((String)newValue);
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
			case OxmPackage.EXML_SEE_ALSO__CLASSES:
				setClasses(CLASSES_EDEFAULT);
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
			case OxmPackage.EXML_SEE_ALSO__CLASSES:
				return CLASSES_EDEFAULT == null ? classes != null : !CLASSES_EDEFAULT.equals(classes);
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
		result.append(" (classes: ");
		result.append(classes);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** misc *****
	
	public ListIterable<String> getClassesList() {
		if (StringTools.isBlank(this.classes)) {
			return EmptyListIterable.instance();
		}
		String[] classes = this.classes.split("\\s+");
		return IterableTools.listIterable(classes);
	}
	
	public void addClass(int index, String clazz) {
		// easy case 1: existing classes is empty - set classes to added class
		if (StringTools.isBlank(this.classes)) {
			setClasses(clazz);
			return;
		}
		
		// easy case 2: add new class to beginning of existing classes, use default delim of one space
		if (index == 0) {
			setClasses(StringTools.concatenate(clazz, " ", this.classes));
			return;
		}
		
		String[] classArray = this.classes.split("\\s+");
		String[] newClassArray = ArrayTools.add(classArray, index, clazz);
		setClasses(StringTools.concatenate(newClassArray, " "));
	}
	
	public void removeClass(int index) {
		String[] classArray = this.classes.split("\\s+");
		String[] newClassArray = ArrayTools.removeElementAtIndex(classArray, index);
		setClasses(StringTools.concatenate(newClassArray, " "));
	}
	
	public void moveClass(int targetIndex, int sourceIndex) {
		String[] classArray = this.classes.split("\\s+");
		String[] newClassArray = ArrayTools.move(classArray, targetIndex, sourceIndex);
		setClasses(StringTools.concatenate(newClassArray, " "));
	}
	
	
	// ***** translators *****
	
	public static Translator buildTranslator() {
		return new SimpleTranslator(
				Oxm.XML_SEE_ALSO, 
				OxmPackage.eINSTANCE.getEAbstractTypeMapping_XmlSeeAlso(),
				Translator.EMPTY_CONTENT_IS_SIGNIFICANT,
				buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassesTranslator()
		};
	}
	
	protected static Translator buildClassesTranslator() {
			return new SimpleTranslator(
				Translator.TEXT_ATTRIBUTE_VALUE,
				OxmPackage.eINSTANCE.getEXmlSeeAlso_Classes());
	}
}