/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlTransient;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVersion;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getAttributes()
 * @model kind="class"
 * @generated
 */
public class Attributes extends org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.Attributes
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
		return EclipseLink2_0OrmPackage.Literals.ATTRIBUTES;
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink2_0OrmPackage.eINSTANCE.getAttributes(), 
			buildTranslatorChildren());
	}
	
	public static Translator buildTranslator() {
		return buildTranslator(
			EclipseLink2_0.ATTRIBUTES, 
			OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			XmlId.buildTranslator(EclipseLink2_0.ID, OrmPackage.eINSTANCE.getAttributes_Ids()),
			XmlEmbeddedId.buildTranslator(EclipseLink2_0.EMBEDDED_ID, OrmPackage.eINSTANCE.getAttributes_EmbeddedIds()),
			XmlBasic.buildTranslator(EclipseLink2_0.BASIC, OrmPackage.eINSTANCE.getAttributes_Basics()),
			XmlBasicCollection.buildTranslator(EclipseLink2_0.BASIC_COLLECTION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicCollections()),
			XmlBasicMap.buildTranslator(EclipseLink2_0.BASIC_MAP, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicMaps()),
			XmlVersion.buildTranslator(EclipseLink2_0.VERSION, OrmPackage.eINSTANCE.getAttributes_Versions()),
			XmlManyToOne.buildTranslator(EclipseLink2_0.MANY_TO_ONE, OrmPackage.eINSTANCE.getAttributes_ManyToOnes()),
			XmlOneToMany.buildTranslator(EclipseLink2_0.ONE_TO_MANY, OrmPackage.eINSTANCE.getAttributes_OneToManys()),
			XmlOneToOne.buildTranslator(EclipseLink2_0.ONE_TO_ONE, OrmPackage.eINSTANCE.getAttributes_OneToOnes()),
			XmlVariableOneToOne.buildTranslator(EclipseLink2_0.VARIABLE_ONE_TO_ONE, EclipseLinkOrmPackage.eINSTANCE.getAttributes_VariableOneToOnes()),
			XmlManyToMany.buildTranslator(EclipseLink2_0.MANY_TO_MANY, OrmPackage.eINSTANCE.getAttributes_ManyToManys()),
			XmlElementCollection.buildTranslator(EclipseLink2_0.ELEMENT_COLLECTION, OrmV2_0Package.eINSTANCE.getXmlAttributes_2_0_ElementCollections()),
			XmlEmbedded.buildTranslator(EclipseLink2_0.EMBEDDED, OrmPackage.eINSTANCE.getAttributes_Embeddeds()),
			XmlTransformation.buildTranslator(EclipseLink2_0.TRANSFORMATION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_Transformations()),
			XmlTransient.buildTranslator(EclipseLink2_0.TRANSIENT, OrmPackage.eINSTANCE.getAttributes_Transients()),
		};
	}
}
