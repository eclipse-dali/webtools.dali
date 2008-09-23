/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityMappingsTranslator extends RootTranslator
	implements OrmXmlMapper
{
	public static EntityMappingsTranslator INSTANCE = new EntityMappingsTranslator();
	
	
	private Translator[] children;
	
	
	public EntityMappingsTranslator() {
		super(ENTITY_MAPPINGS, ORM_PKG.getXmlEntityMappings());
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			createVersionTranslator(),
			new ConstantAttributeTranslator(XML_NS, ORM_NS_URL),
			new ConstantAttributeTranslator(XML_NS_XSI, XSI_NS_URL),
			new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, ORM_NS_URL + ' ' + ORM_SCHEMA_LOC_1_0),
			createDescriptionTranslator(),
			createPersistenceUnitMetadataTranslator(),
			createPackageTranslator(),
			createSchemaTranslator(),
			createCatalogTranslator(),
			createAccessTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createSqlResultSetMappingTranslator(),
			createMappedSuperclassTranslator(),
			createEntityTranslator(),
			createEmbeddableTranslator()
		};
	}	
	
	private Translator createVersionTranslator() {
		return new Translator(VERSION, ORM_PKG.getXmlEntityMappings_Version(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getXmlEntityMappings_Description());
	}
	
	private Translator createPersistenceUnitMetadataTranslator() {
		return new PersistenceUnitMetadataTranslator(PERSISTENCE_UNIT_METADATA, ORM_PKG.getXmlEntityMappings_PersistenceUnitMetadata());
	}
	
	private Translator createPackageTranslator() {
		return new Translator(PACKAGE, ORM_PKG.getXmlEntityMappings_Package());
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ORM_PKG.getXmlEntityMappings_Schema());
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getXmlEntityMappings_Catalog());
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlEntityMappings_Access());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getXmlEntityMappings_SequenceGenerators());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getXmlEntityMappings_TableGenerators());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ORM_PKG.getXmlEntityMappings_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ORM_PKG.getXmlEntityMappings_NamedNativeQueries());
	}
	
	private Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ORM_PKG.getXmlEntityMappings_SqlResultSetMappings());
	}
	
	private Translator createMappedSuperclassTranslator() {
		return new MappedSuperclassTranslator(MAPPED_SUPERCLASS, ORM_PKG.getXmlEntityMappings_MappedSuperclasses());
	}
	
	private Translator createEntityTranslator() {
		return new EntityTranslator(ENTITY, ORM_PKG.getXmlEntityMappings_Entities());
	}
	
	private Translator createEmbeddableTranslator() {
		return new EmbeddableTranslator(EMBEDDABLE, ORM_PKG.getXmlEntityMappings_Embeddables());
	}
}
