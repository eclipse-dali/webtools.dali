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
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleRootTranslator;
import org.eclipse.jpt.core.jpa2.resource.orm.SqlResultSetMapping;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.xml.XML;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlEntityMappings()
 * @model kind="class"
 * @generated
 */
public class XmlEntityMappings extends org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEntityMappings
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntityMappings()
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
		return EclipseLink2_0OrmPackage.Literals.XML_ENTITY_MAPPINGS;
	}
	// ********** translators **********

	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();

	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA.ENTITY_MAPPINGS,
				EclipseLink2_0OrmPackage.eINSTANCE.getXmlEntityMappings(),
				buildTranslatorChildren()
			);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildVersionTranslator(),
			buildNamespaceTranslator(),
			buildSchemaNamespaceTranslator(),
			buildSchemaLocationTranslator(),
			buildDescriptionTranslator(),
			XmlPersistenceUnitMetadata.buildTranslator(JPA.PERSISTENCE_UNIT_METADATA, OrmPackage.eINSTANCE.getXmlEntityMappings_PersistenceUnitMetadata()),
			buildPackageTranslator(),
			buildSchemaTranslator(),
			buildCatalogTranslator(),
			buildAccessTranslator(),
			XmlConverter.buildTranslator(JPA.CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_Converters()),
			XmlTypeConverter.buildTranslator(JPA.TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_TypeConverters()),
			XmlObjectTypeConverter.buildTranslator(JPA.OBJECT_TYPE_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_ObjectTypeConverters()),
			XmlStructConverter.buildTranslator(JPA.STRUCT_CONVERTER, EclipseLinkOrmPackage.eINSTANCE.getXmlConvertersHolder_StructConverters()),
			XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_SequenceGenerators()),
			XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_TableGenerators()),
			XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries()),
			XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries()),
			XmlNamedStoredProcedureQuery.buildTranslator(JPA.NAMED_STORED_PROCEDURE_QUERY, EclipseLinkOrmPackage.eINSTANCE.getXmlQueryContainer_NamedStoredProcedureQueries()),
			SqlResultSetMapping.buildTranslator(JPA.SQL_RESULT_SET_MAPPING, OrmPackage.eINSTANCE.getXmlEntityMappings_SqlResultSetMappings()),
			XmlMappedSuperclass.buildTranslator(JPA.MAPPED_SUPERCLASS, OrmPackage.eINSTANCE.getXmlEntityMappings_MappedSuperclasses()),
			XmlEntity.buildTranslator(JPA.ENTITY, OrmPackage.eINSTANCE.getXmlEntityMappings_Entities()),
			XmlEmbeddable.buildTranslator(JPA.EMBEDDABLE, OrmPackage.eINSTANCE.getXmlEntityMappings_Embeddables()),
		};
	}
	
	private static Translator buildNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML.NAMESPACE, JPA.NAMESPACE_URL);
	}
	
	private static Translator buildSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XML.XSI_SCHEMA_LOCATION, JPA.NAMESPACE_URL + ' ' + JPA.SCHEMA_LOCATION_1_1);
	}
} // XmlEntityMappings
