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
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public EntityTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
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
		return new Translator(NAME, ORM_PKG.getXmlEntity_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getAbstractXmlTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getAbstractXmlTypeMapping_Access(), DOM_ATTRIBUTE);
	}
	
	private Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getAbstractXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getAbstractXmlTypeMapping_Description());
	}
	
	private Translator createTableTranslator() {
		return new TableTranslator(TABLE, ORM_PKG.getXmlEntity_Table());
	}
	
	private Translator createSecondaryTableTranslator() {
		return new SecondaryTableTranslator(SECONDARY_TABLE, ORM_PKG.getXmlEntity_SecondaryTables());
	}	
	
	private Translator createPrimaryKeyJoinColumnTranslator() {
		return new PrimaryKeyJoinColumnTranslator(PRIMARY_KEY_JOIN_COLUMN, ORM_PKG.getXmlEntity_PrimaryKeyJoinColumns());
	}
	
	private Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getXmlEntity_IdClass());
	}
	
	private Translator createInheritanceTranslator() {
		return new InheritanceTranslator(INHERITANCE, ORM_PKG.getXmlEntity_Inheritance());
	}
	
	private Translator createDiscriminatorValueTranslator() {
		return new Translator(DISCRIMINATOR_VALUE, ORM_PKG.getXmlEntity_DiscriminatorValue());
	}
	
	private Translator createDiscriminatorColumnTranslator() {
		return new DiscriminatorColumnTranslator(DISCRIMINATOR_COLUMN, ORM_PKG.getXmlEntity_DiscriminatorColumn());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getXmlEntity_SequenceGenerator());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getXmlEntity_TableGenerator());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ORM_PKG.getXmlEntity_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ORM_PKG.getXmlEntity_NamedNativeQueries());
	}
	
	private Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ORM_PKG.getXmlEntity_SqlResultSetMappings());
	}
	
	private Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getXmlEntity_ExcludeDefaultListeners());
	}
	
	private Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getXmlEntity_ExcludeSuperclassListeners());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getXmlEntity_EntityListeners());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getXmlEntity_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getXmlEntity_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getXmlEntity_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getXmlEntity_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getXmlEntity_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getXmlEntity_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getXmlEntity_PostLoad());
	}
	
	private Translator createAttributeOverrideTranslator() {
		return new AttributeOverrideTranslator(ATTRIBUTE_OVERRIDE, ORM_PKG.getXmlEntity_AttributeOverrides());
	}
	
	private Translator createAssociationOverrideTranslator() {
		return new AssociationOverrideTranslator(ASSOCIATION_OVERRIDE, ORM_PKG.getXmlEntity_AssociationOverrides());
	}
	
	private Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getAbstractXmlTypeMapping_Attributes());
	}
}
