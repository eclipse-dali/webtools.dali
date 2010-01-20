/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToMany_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many To Many</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToMany()
 * @model kind="class"
 * @generated
 */
public class XmlManyToMany extends AbstractXmlMultiRelationshipMapping implements XmlManyToMany_2_0
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToMany()
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
		return OrmPackage.Literals.XML_MANY_TO_MANY;
	}
	
	public String getMappingKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildMappedByTranslator(),
			buildOrderByTranslator(),
			XmlOrderColumn.buildTranslator(JPA2_0.ORDER_COLUMN, OrmV2_0Package.eINSTANCE.getXmlOrderable_2_0_OrderColumn()),		
			buildMapKeyTranslator(),
			buildMapKeyClassTranslator(),
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			buildMapKeyAttributeOverrideTranslator(),
			buildMapKeyColumnTranslator(),
			buildMapKeyJoinColumnTranslator(),
			buildJoinTableTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildMappedByTranslator() {
		return new Translator(JPA.MAPPED_BY, OrmPackage.eINSTANCE.getXmlMappedByMapping_MappedBy(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMapKeyClassTranslator() {
		return XmlMapKeyClass.buildTranslator(JPA2_0.MAP_KEY_CLASS, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyClass());
	}
	
	protected static Translator buildMapKeyTemporalTranslator() {
		return new Translator(JPA2_0.MAP_KEY_TEMPORAL, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyTemporal());
	}
	
	protected static Translator buildMapKeyEnumeratedTranslator() {
		return new Translator(JPA2_0.MAP_KEY_ENUMERATED, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyEnumerated());
	}
	
	protected static Translator buildMapKeyAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA2_0.MAP_KEY_ATTRIBUTE_OVERRIDE, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyAttributeOverrides());
	}
	
	protected static Translator buildMapKeyColumnTranslator() {
		return XmlColumn.buildTranslator(JPA2_0.MAP_KEY_COLUMN, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyColumn());
	}
	
	protected static Translator buildMapKeyJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA2_0.MAP_KEY_JOIN_COLUMN, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyJoinColumns());
	}
	
	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableMapping_JoinTable());
	}
}
