/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public EntityTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createClassTranslator(),
			createAccessTranslator(),
			createMetadataCompleteTranslator(),
			createDescriptionTranslator(),
			createTableTranslator(),
			createSecondaryTableTranslator(),
			createPrimaryKeyJoinColumnTranslator(),
			createIdClassTranslator(),
			createInheritanceTranslator(),
			createDiscriminatorValueTranslator(),
			createDiscriminatorColumnTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createSqlResultSetMappingTranslator(),
			createExcludeDefaultListenersTranslator(),
			createExcludeSuperclassListenersTranslator(),
			createEntityListenersTranslator(),
			createPrePersistTranslator(),
			createPostPersistTranslator(),
			createPreRemoveTranslator(),
			createPostRemoveTranslator(),
			createPreUpdateTranslator(),
			createPostUpdateTranslator(),
			createPostLoadTranslator(),
			createAttributeOverrideTranslator(),
			createAssociationOverrideTranslator(),
			createAttributesTranslator()
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getEntity_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getEntity_ClassName(), DOM_ATTRIBUTE);
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getEntity_Access(), DOM_ATTRIBUTE);
	}
	
	private Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getEntity_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getEntity_Description());
	}
	
	private Translator createTableTranslator() {
		return new TableTranslator(TABLE, ORM_PKG.getEntity_Tables());
	}
	
	private Translator createSecondaryTableTranslator() {
		return new SecondaryTableTranslator(SECONDARY_TABLE, ORM_PKG.getEntity_SecondaryTables());
	}	
	
	private Translator createPrimaryKeyJoinColumnTranslator() {
		return new PrimaryKeyJoinColumnTranslator(PRIMARY_KEY_JOIN_COLUMN, ORM_PKG.getEntity_PrimaryKeyJoinColumns());
	}
	
	private Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getEntity_IdClass());
	}
	
	private Translator createInheritanceTranslator() {
		return new InheritanceTranslator(INHERITANCE, ORM_PKG.getEntity_Inheritance());
	}
	
	private Translator createDiscriminatorValueTranslator() {
		return new Translator(DISCRIMINATOR_VALUE, ORM_PKG.getEntity_DiscriminatorValue());
	}
	
	private Translator createDiscriminatorColumnTranslator() {
		return new DiscriminatorColumnTranslator(DISCRIMINATOR_COLUMN, ORM_PKG.getEntity_DiscriminatorColumn());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getEntity_SequenceGenerator());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getEntity_TableGenerator());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ORM_PKG.getEntity_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ORM_PKG.getEntity_NamedNativeQueries());
	}
	
	private Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ORM_PKG.getEntity_SqlResultSetMappings());
	}
	
	private Translator createExcludeDefaultListenersTranslator() {
		return new Translator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getEntity_ExcludeDefaultListeners());
	}
	
	private Translator createExcludeSuperclassListenersTranslator() {
		return new Translator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getEntity_ExcludeSuperclassListeners());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getEntity_EntityListeners());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getEntity_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getEntity_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getEntity_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getEntity_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getEntity_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getEntity_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getEntity_PostLoad());
	}
	
	private Translator createAttributeOverrideTranslator() {
		return new AttributeOverrideTranslator(ATTRIBUTE_OVERRIDE, ORM_PKG.getEntity_AttributeOverrides());
	}
	
	private Translator createAssociationOverrideTranslator() {
		return new AssociationOverrideTranslator(ASSOCIATION_OVERRIDE, ORM_PKG.getEntity_AssociationOverrides());
	}
	
	private Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getEntity_Attributes());
	}
}
