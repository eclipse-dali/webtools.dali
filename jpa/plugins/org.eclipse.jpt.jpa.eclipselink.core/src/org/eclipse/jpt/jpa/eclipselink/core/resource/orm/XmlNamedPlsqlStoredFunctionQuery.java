/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaEObject;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Plsql Stored Function Query</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedPlsqlStoredFunctionQuery()
 * @model kind="class"
 * @generated
 */
public class XmlNamedPlsqlStoredFunctionQuery extends AbstractJpaEObject implements XmlNamedPlsqlStoredFunctionQuery_2_3
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlNamedPlsqlStoredFunctionQuery()
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
		return EclipseLinkOrmPackage.Literals.XML_NAMED_PLSQL_STORED_FUNCTION_QUERY;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature,
			EclipseLinkOrmPackage.eINSTANCE.getXmlNamedPlsqlStoredFunctionQuery(),
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
		};
	}
} // XmlNamedPlsqlStoredFunctionQuery
