/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.resource.orm.translators.EntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEntityMappingsTranslator extends EntityMappingsTranslator
	implements EclipseLinkOrmXmlMapper
{
	public static EclipseLinkEntityMappingsTranslator INSTANCE = new EclipseLinkEntityMappingsTranslator();
	
	protected EclipseLinkEntityMappingsTranslator(String domNameAndPath, EClass eClass) {
		super(domNameAndPath, eClass);
	}
	
	public EclipseLinkEntityMappingsTranslator() {
		this(ENTITY_MAPPINGS, ECLIPSELINK_ORM_PKG.getXmlEntityMappings());
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createVersionTranslator(),
			createNamespaceTranslator(),
			createSchemaNamespaceTranslator(),
			createSchemaLocationTranslator(),
			createDescriptionTranslator(),
			createPersistenceUnitMetadataTranslator(),
			createPackageTranslator(),
			createSchemaTranslator(),
			createCatalogTranslator(),
			createAccessTranslator(),
			createConverterTranslator(),
			createTypeConverterTranslator(),
			createObjectTypeConverterTranslator(),
			createStructConverterTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createNamedStoredProcedureQueryTranslator(),
			createSqlResultSetMappingTranslator(),
			createMappedSuperclassTranslator(),
			createEntityTranslator(),
			createEmbeddableTranslator()
		};
	}
	
	@Override
	protected Translator createNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML_NS, ECLIPSELINK_ORM_NS_URL);
	}
	
	@Override
	protected Translator createSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, ECLIPSELINK_ORM_NS_URL + ' ' + ECLIPSELINK_ORM_SCHEMA_LOC_1_0);
	}
	
	@Override
	protected Translator createPersistenceUnitMetadataTranslator() {
		return new EclipseLinkPersistenceUnitMetadataTranslator(PERSISTENCE_UNIT_METADATA, ORM_PKG.getXmlEntityMappings_PersistenceUnitMetadata());
	}
	
	@Override
	protected Translator createEmbeddableTranslator() {
		return new EclipseLinkEmbeddableTranslator(EMBEDDABLE, ORM_PKG.getXmlEntityMappings_Embeddables());
	}
	
	@Override
	protected Translator createEntityTranslator() {
		return new EclipseLinkEntityTranslator(ENTITY, ORM_PKG.getXmlEntityMappings_Entities());
	}
	
	@Override
	protected Translator createMappedSuperclassTranslator() {
		return new EclipseLinkMappedSuperclassTranslator(MAPPED_SUPERCLASS, ORM_PKG.getXmlEntityMappings_MappedSuperclasses());
	}
	
	
	protected Translator createConverterTranslator() {
		return new ConverterTranslator(CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConvertersHolder_Converters());
	}
	
	protected Translator createTypeConverterTranslator() {
		return new TypeConverterTranslator(TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConvertersHolder_TypeConverters());
	}
	
	protected Translator createObjectTypeConverterTranslator() {
		return new ObjectTypeConverterTranslator(OBJECT_TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConvertersHolder_ObjectTypeConverters());
	}
	
	protected Translator createStructConverterTranslator() {
		return new StructConverterTranslator(STRUCT_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConvertersHolder_StructConverters());
	}
	
	protected Translator createNamedStoredProcedureQueryTranslator() {
		return new NamedStoredProcedureQueryTranslator(NAMED_STORED_PROCEDURE_QUERY, ECLIPSELINK_ORM_PKG.getXmlEntityMappings_NamedStoredProcedureQueries());
	}
}
