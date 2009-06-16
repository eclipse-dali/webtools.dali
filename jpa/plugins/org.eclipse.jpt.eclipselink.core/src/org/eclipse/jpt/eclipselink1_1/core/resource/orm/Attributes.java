/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.JPA;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getAttributes()
 * @model kind="class"
 * @generated
 */
public class Attributes extends org.eclipse.jpt.eclipselink.core.resource.orm.Attributes
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Attributes()
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
		return EclipseLink1_1OrmPackage.Literals.ATTRIBUTES;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink1_1OrmPackage.eINSTANCE.getAttributes(), 
			buildTranslatorChildren());
	}
	
	public static Translator buildTranslator() {
		return buildTranslator(
			JPA.ATTRIBUTES, 
			OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			XmlId.buildTranslator(JPA.ID, OrmPackage.eINSTANCE.getAttributes_Ids()),
			XmlEmbeddedId.buildTranslator(JPA.EMBEDDED_ID, OrmPackage.eINSTANCE.getAttributes_EmbeddedIds()),
			XmlBasic.buildTranslator(JPA.BASIC, OrmPackage.eINSTANCE.getAttributes_Basics()),
			XmlBasicCollection.buildTranslator(JPA.BASIC_COLLECTION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicCollections()),
			XmlBasicMap.buildTranslator(JPA.BASIC_MAP, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicMaps()),
			XmlVersion.buildTranslator(JPA.VERSION, OrmPackage.eINSTANCE.getAttributes_Versions()),
			XmlManyToOne.buildTranslator(JPA.MANY_TO_ONE, OrmPackage.eINSTANCE.getAttributes_ManyToOnes()),
			XmlOneToMany.buildTranslator(JPA.ONE_TO_MANY, OrmPackage.eINSTANCE.getAttributes_OneToManys()),
			XmlOneToOne.buildTranslator(JPA.ONE_TO_ONE, OrmPackage.eINSTANCE.getAttributes_OneToOnes()),
			XmlVariableOneToOne.buildTranslator(JPA.VARIABLE_ONE_TO_ONE, EclipseLinkOrmPackage.eINSTANCE.getAttributes_VariableOneToOnes()),
			XmlManyToMany.buildTranslator(JPA.MANY_TO_MANY, OrmPackage.eINSTANCE.getAttributes_ManyToManys()),
			XmlEmbedded.buildTranslator(JPA.EMBEDDED, OrmPackage.eINSTANCE.getAttributes_Embeddeds()),
			XmlTransformation.buildTranslator(JPA.TRANSFORMATION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_Transformations()),
			buildTransientTranslator()
		};
	}
} // Attributes
