/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.EntityMappings;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityMappingsTranslator extends RootTranslator
	implements OrmXmlMapper
{
	private Translator[] children;
	
	private PersistenceUnitMetadataTranslator persistenceUnitMetadataTranslator;
	
	public EntityMappingsTranslator() {
		super(ENTITY_MAPPINGS, OrmPackage.eINSTANCE.getEntityMappingsInternal());
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			new ConstantAttributeTranslator(XML_NS, PERSISTENCE_NS_URL),
			new ConstantAttributeTranslator(XML_NS_XSI, XSI_NS_URL),
			new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, PERSISTENCE_NS_URL + ' ' + ORM_SCHEMA_LOC_1_0),
			createVersionTranslator(),
			createPlaceHolderTranslator(ENTITY_MAPPINGS__DESCRIPTION),
			createPersistenceUnitMetadataTranslator(),
			createPackageTranslator(),
			createSchemaTranslator(),
			createCatalogTranslator(),
			createAccessTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createPlaceHolderTranslator(ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPING),
			createTypeMappingsTranslator()
		};
	}
	
	private Translator createPlaceHolderTranslator(String domNameAndPath) {
		return new Translator(domNameAndPath, (EStructuralFeature) null);
	}	

	protected Translator createVersionTranslator() {
		return new Translator(VERSION, OrmPackage.eINSTANCE.getEntityMappingsInternal_Version(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPersistenceUnitMetadataTranslator() {
		this.persistenceUnitMetadataTranslator = new PersistenceUnitMetadataTranslator();
		return this.persistenceUnitMetadataTranslator;
	}
	
	protected Translator createPackageTranslator() {
		return new Translator(ENTITY_MAPPINGS__PACKAGE, OrmPackage.eINSTANCE.getEntityMappingsForXml_PackageForXml());
	}
	
	protected Translator createSchemaTranslator() {
		return new Translator(ENTITY_MAPPINGS__SCHEMA, OrmPackage.eINSTANCE.getEntityMappingsInternal_SpecifiedSchema());
	}
	
	protected Translator createCatalogTranslator() {
		return new Translator(ENTITY_MAPPINGS__CATALOG, OrmPackage.eINSTANCE.getEntityMappingsInternal_SpecifiedCatalog());
	}

	protected Translator createAccessTranslator() {
		return new AccessTypeElementTranslator(ENTITY_MAPPINGS__ACCESS, OrmPackage.eINSTANCE.getEntityMappingsInternal_SpecifiedAccess(), NO_STYLE);
	}
	
	protected Translator createTypeMappingsTranslator() {
		return new TypeMappingsTranslator();
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, OrmPackage.eINSTANCE.getEntityMappingsInternal_TableGenerators());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getEntityMappingsInternal_SequenceGenerators());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, OrmPackage.eINSTANCE.getEntityMappingsInternal_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getEntityMappingsInternal_NamedNativeQueries());
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		EntityMappings entityMappings = (EntityMappings) super.createEMFObject(nodeName, readAheadName);
		this.persistenceUnitMetadataTranslator.setEntityMappings(entityMappings);
		return entityMappings;
	}

}
