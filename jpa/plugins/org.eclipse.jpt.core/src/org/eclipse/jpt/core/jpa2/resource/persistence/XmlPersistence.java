/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.persistence;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleRootTranslator;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.resource.xml.XML;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistence</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.jpa2.resource.persistence.Persistence2_0Package#getXmlPersistence()
 * @model kind="class"
 * @generated
 */
public class XmlPersistence extends org.eclipse.jpt.core.resource.persistence.XmlPersistence
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistence()
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
		return Persistence2_0Package.Literals.XML_PERSISTENCE;
	}
	
	// ********** translators **********

	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();

	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA2_0.PERSISTENCE,
				Persistence2_0Package.eINSTANCE.getXmlPersistence(),
				buildTranslatorChildren()
			);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
				buildVersionTranslator(),
				buildNamespaceTranslator(),
				buildSchemaNamespaceTranslator(),
				buildSchemaLocationTranslator(),
				XmlPersistenceUnit.buildTranslator(JPA2_0.PERSISTENCE_UNIT, PersistencePackage.eINSTANCE.getXmlPersistence_PersistenceUnits())
			};
	}
	
	private static Translator buildSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XML.XSI_SCHEMA_LOCATION, JPA2_0.SCHEMA_NAMESPACE + ' ' + JPA2_0.SCHEMA_LOCATION);
	}
} // XmlPersistence
