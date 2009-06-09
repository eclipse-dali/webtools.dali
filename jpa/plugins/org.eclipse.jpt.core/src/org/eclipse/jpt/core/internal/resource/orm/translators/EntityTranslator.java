/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.EmptyTagBooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public EntityTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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

	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlEntity_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getXmlTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlAccessHolder_Access(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getXmlTypeMapping_Description());
	}
	
	protected Translator createTableTranslator() {
		return new TableTranslator(TABLE, ORM_PKG.getXmlEntity_Table());
	}
	
	protected Translator createSecondaryTableTranslator() {
		return new SecondaryTableTranslator(SECONDARY_TABLE, ORM_PKG.getXmlEntity_SecondaryTables());
	}	
	
	protected Translator createPrimaryKeyJoinColumnTranslator() {
		return new PrimaryKeyJoinColumnTranslator(PRIMARY_KEY_JOIN_COLUMN, ORM_PKG.getXmlEntity_PrimaryKeyJoinColumns());
	}
	
	protected Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ORM_PKG.getXmlEntity_IdClass());
	}
	
	protected Translator createInheritanceTranslator() {
		return new InheritanceTranslator(INHERITANCE, ORM_PKG.getXmlEntity_Inheritance());
	}
	
	protected Translator createDiscriminatorValueTranslator() {
		return new Translator(DISCRIMINATOR_VALUE, ORM_PKG.getXmlEntity_DiscriminatorValue());
	}
	
	protected Translator createDiscriminatorColumnTranslator() {
		return new DiscriminatorColumnTranslator(DISCRIMINATOR_COLUMN, ORM_PKG.getXmlEntity_DiscriminatorColumn());
	}
	
	protected Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getXmlGeneratorContainer_SequenceGenerator());
	}
	
	protected Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getXmlGeneratorContainer_TableGenerator());
	}
	
	protected Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ORM_PKG.getXmlQueryContainer_NamedQueries());
	}
	
	protected Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ORM_PKG.getXmlQueryContainer_NamedNativeQueries());
	}
	
	protected Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ORM_PKG.getXmlEntity_SqlResultSetMappings());
	}
	
	protected Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ORM_PKG.getXmlEntity_ExcludeDefaultListeners());
	}
	
	protected Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ORM_PKG.getXmlEntity_ExcludeSuperclassListeners());
	}
	
	protected Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getXmlEntity_EntityListeners());
	}
	
	protected Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ORM_PKG.getXmlEntity_PrePersist());
	}
	
	protected Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ORM_PKG.getXmlEntity_PostPersist());
	}
	
	protected Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ORM_PKG.getXmlEntity_PreRemove());
	}
	
	protected Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ORM_PKG.getXmlEntity_PostRemove());
	}
	
	protected Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ORM_PKG.getXmlEntity_PreUpdate());
	}
	
	protected Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ORM_PKG.getXmlEntity_PostUpdate());
	}
	
	protected Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ORM_PKG.getXmlEntity_PostLoad());
	}
	
	protected Translator createAttributeOverrideTranslator() {
		return new AttributeOverrideTranslator(ATTRIBUTE_OVERRIDE, ORM_PKG.getXmlEntity_AttributeOverrides());
	}
	
	protected Translator createAssociationOverrideTranslator() {
		return new AssociationOverrideTranslator(ASSOCIATION_OVERRIDE, ORM_PKG.getXmlEntity_AssociationOverrides());
	}
	
	protected Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getXmlTypeMapping_Attributes());
	}
}
