/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.orm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEventMethod()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface EventMethod extends org.eclipse.jpt.core.resource.orm.EventMethod
{

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getEventMethod_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.orm.EventMethod#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

//	// ********** translators **********
//
//	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
//		return new SimpleTranslator(
//			elementName, 
//			structuralFeature, 
//			Orm2_0Package.eINSTANCE.getEventMethod(), 
//			buildTranslatorChildren());
//	}
//
//	private static Translator[] buildTranslatorChildren() {
//		return new Translator[] {
//			buildMethodNameTranslator(),
//			buildDescriptionTranslator()
//		};
//	}
//	
//	protected static Translator buildDescriptionTranslator() {
//		return new Translator(JPA.METHOD_NAME, Orm2_0Package.eINSTANCE.getEventMethod_Description());
//	}

} // EventMethod
