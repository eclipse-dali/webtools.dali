/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.resource.orm.translators.EmbeddableTranslator;
import org.eclipse.jpt.core.internal.resource.orm.translators.EntityTranslator;
import org.eclipse.jpt.core.internal.resource.orm.translators.MappedSuperclassTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleRootTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.xml.XML;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlEntityMappings()
 * @model kind="class"
 * @generated
 */
public class XmlEntityMappings extends org.eclipse.jpt.core.resource.orm.XmlEntityMappings
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
		return Orm2_0Package.Literals.XML_ENTITY_MAPPINGS;
	}
	
	// ********** translators **********

	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();

	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA.ENTITY_MAPPINGS,
				Orm2_0Package.eINSTANCE.getXmlEntityMappings(),
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
			XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_TableGenerators()),
			XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_SequenceGenerators()),
			XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries()),
			XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries()),
			SqlResultSetMapping.buildTranslator(JPA.SQL_RESULT_SET_MAPPING, OrmPackage.eINSTANCE.getXmlEntityMappings_SqlResultSetMappings()),
			XmlMappedSuperclass.buildTranslator(JPA.MAPPED_SUPERCLASS, OrmPackage.eINSTANCE.getXmlEntityMappings_MappedSuperclasses()),
			XmlEntity.buildTranslator(JPA.ENTITY, OrmPackage.eINSTANCE.getXmlEntityMappings_Entities()),
			XmlEmbeddable.buildTranslator(JPA.EMBEDDABLE, OrmPackage.eINSTANCE.getXmlEntityMappings_Embeddables()),
		};
	}

	private static Translator buildSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XML.XSI_SCHEMA_LOCATION, JPA.NAMESPACE_URL + ' ' + JPA.SCHEMA_LOCATION_2_0);
	}
	
	protected static Translator buildMappedSuperclassTranslator() {
		return new MappedSuperclassTranslator(JPA.MAPPED_SUPERCLASS, OrmPackage.eINSTANCE.getXmlEntityMappings_MappedSuperclasses());
	}
	
	protected static Translator buildEntityTranslator() {
		return new EntityTranslator(JPA.ENTITY, OrmPackage.eINSTANCE.getXmlEntityMappings_Entities());
	}
	
	protected static Translator buildEmbeddableTranslator() {
		return new EmbeddableTranslator(JPA.EMBEDDABLE, OrmPackage.eINSTANCE.getXmlEntityMappings_Embeddables());
	}
} // XmlEntityMappings
