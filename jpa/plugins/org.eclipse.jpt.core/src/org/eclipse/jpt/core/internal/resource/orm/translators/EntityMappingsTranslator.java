/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.resource.xml.XML;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityMappingsTranslator extends RootTranslator
	implements OrmXmlMapper
{
	public static EntityMappingsTranslator INSTANCE = new EntityMappingsTranslator();
	
	
	private Translator[] children;
	
	
	protected EntityMappingsTranslator(String domNameAndPath, EClass eClass) {
		super(domNameAndPath, eClass);
	}
	
	
	public EntityMappingsTranslator() {
		this(ENTITY_MAPPINGS, ORM_PKG.getXmlEntityMappings());
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
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
	
	protected Translator createNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML.NAMESPACE, ORM_NS_URL);		
	}
	
	protected Translator createSchemaNamespaceTranslator() {
		return 	new ConstantAttributeTranslator(XML.NAMESPACE_XSI, XML.XSI_NAMESPACE_URL);
	}
		
	protected Translator createSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XML.XSI_SCHEMA_LOCATION, ORM_NS_URL + ' ' + ORM_SCHEMA_LOC_1_0);
	}
	
	protected Translator createVersionTranslator() {
		return new Translator(VERSION, ORM_PKG.getXmlEntityMappings_Version(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getXmlEntityMappings_Description());
	}
	
	protected Translator createPersistenceUnitMetadataTranslator() {
		return new PersistenceUnitMetadataTranslator(PERSISTENCE_UNIT_METADATA, ORM_PKG.getXmlEntityMappings_PersistenceUnitMetadata());
	}
	
	protected Translator createPackageTranslator() {
		return new Translator(PACKAGE, ORM_PKG.getXmlEntityMappings_Package());
	}
	
	protected Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ORM_PKG.getXmlEntityMappings_Schema());
	}
	
	protected Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getXmlEntityMappings_Catalog());
	}
	
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlEntityMappings_Access());
	}
	
	protected Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getXmlEntityMappings_SequenceGenerators());
	}
	
	protected Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getXmlEntityMappings_TableGenerators());
	}
	
	protected Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ORM_PKG.getXmlEntityMappings_NamedQueries());
	}
	
	protected Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ORM_PKG.getXmlEntityMappings_NamedNativeQueries());
	}
	
	protected Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ORM_PKG.getXmlEntityMappings_SqlResultSetMappings());
	}
	
	protected Translator createMappedSuperclassTranslator() {
		return new MappedSuperclassTranslator(MAPPED_SUPERCLASS, ORM_PKG.getXmlEntityMappings_MappedSuperclasses());
	}
	
	protected Translator createEntityTranslator() {
		return new EntityTranslator(ENTITY, ORM_PKG.getXmlEntityMappings_Entities());
	}
	
	protected Translator createEmbeddableTranslator() {
		return new EmbeddableTranslator(EMBEDDABLE, ORM_PKG.getXmlEntityMappings_Embeddables());
	}
}
