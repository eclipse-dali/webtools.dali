/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence;

import java.util.Comparator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Class Ref</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef#getJavaClass <em>Java Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlJavaClassRef()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class XmlJavaClassRef extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getJavaClass() <em>Java Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaClass()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_CLASS_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getJavaClass() <em>Java Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaClass()
	 * @generated
	 * @ordered
	 */
	protected String javaClass = JAVA_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlJavaClassRef()
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
		return PersistencePackage.Literals.XML_JAVA_CLASS_REF;
	}

	/**
	 * Returns the value of the '<em><b>Java Class</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Class</em>' attribute.
	 * @see #setJavaClass(String)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlJavaClassRef_JavaClass()
	 * @model default="" unique="false" ordered="false"
	 * @generated
	 */
	public String getJavaClass()
	{
		return javaClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef#getJavaClass <em>Java Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Class</em>' attribute.
	 * @see #getJavaClass()
	 * @generated
	 */
	public void setJavaClass(String newJavaClass)
	{
		String oldJavaClass = javaClass;
		javaClass = newJavaClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_JAVA_CLASS_REF__JAVA_CLASS, oldJavaClass, javaClass));
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
			case PersistencePackage.XML_JAVA_CLASS_REF__JAVA_CLASS:
				return getJavaClass();
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
			case PersistencePackage.XML_JAVA_CLASS_REF__JAVA_CLASS:
				setJavaClass((String)newValue);
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
			case PersistencePackage.XML_JAVA_CLASS_REF__JAVA_CLASS:
				setJavaClass(JAVA_CLASS_EDEFAULT);
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
			case PersistencePackage.XML_JAVA_CLASS_REF__JAVA_CLASS:
				return JAVA_CLASS_EDEFAULT == null ? javaClass != null : !JAVA_CLASS_EDEFAULT.equals(javaClass);
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
		result.append(" (javaClass: ");
		result.append(javaClass);
		result.append(')');
		return result.toString();
	}


	// *********** refactoring ***********

	public ReplaceEdit createRenameEdit(IType originalType, String newName) {
		String originalName = originalType.getTypeQualifiedName();
		int nameIndex = this.javaClass.lastIndexOf(originalName);
		int offset = getTextNode().getStartOffset();
		return new ReplaceEdit(offset + nameIndex, originalName.length(), newName);
	}

	public ReplaceEdit createRenamePackageEdit(String newPackageName) {
		int packageLength = this.javaClass.lastIndexOf('.');
		if (newPackageName == "") {//$NON-NLS-1$
			//moving to the default package, remove the '.'
			packageLength++;
		}
		if (packageLength == -1) {
			//moving from the default package or unspecified package
			packageLength = 0;
			newPackageName = newPackageName + '.';
		}
		int offset = getTextNode().getStartOffset();
		return new ReplaceEdit(offset, packageLength, newPackageName);
	}


	// ********** misc **********

	public TextRange getJavaClassTextRange(){
		TextRange textRange = this.buildTextRange(this.getTextNode());
		return textRange == null ? this.getValidationTextRange() : textRange;
	}

	public static final Comparator<XmlJavaClassRef> COMPARATOR = new JavaClassComparator();
	public static class JavaClassComparator
		implements Comparator<XmlJavaClassRef>
	{
		public int compare(XmlJavaClassRef ref1, XmlJavaClassRef ref2) {
			return ref1.getJavaClass().compareTo(ref2.getJavaClass());
		}
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
				elementName,
				structuralFeature,
				Translator.END_TAG_NO_INDENT,
				buildTranslatorChildren()
			);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
				buildClassNameTranslator(),
			};
	}

	private static Translator buildClassNameTranslator() {
		return new Translator(
				Translator.TEXT_ATTRIBUTE_VALUE,
				PersistencePackage.eINSTANCE.getXmlJavaClassRef_JavaClass()
			);
	}
}
