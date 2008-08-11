/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.EmptyTagBooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
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
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlEntity_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createClassTranslator() {
		return new Translator(CLASS, ECLIPSELINK_ORM_PKG.getAbstractXmlTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ECLIPSELINK_ORM_PKG.getAbstractXmlTypeMapping_Access(), DOM_ATTRIBUTE);
	}
	
	private Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ECLIPSELINK_ORM_PKG.getAbstractXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	private Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ECLIPSELINK_ORM_PKG.getAbstractXmlTypeMapping_Description());
	}
	
	private Translator createTableTranslator() {
		return new TableTranslator(TABLE, ECLIPSELINK_ORM_PKG.getXmlEntity_Table());
	}
	
	private Translator createSecondaryTableTranslator() {
		return new SecondaryTableTranslator(SECONDARY_TABLE, ECLIPSELINK_ORM_PKG.getXmlEntity_SecondaryTables());
	}	
	
	private Translator createPrimaryKeyJoinColumnTranslator() {
		return new PrimaryKeyJoinColumnTranslator(PRIMARY_KEY_JOIN_COLUMN, ECLIPSELINK_ORM_PKG.getXmlEntity_PrimaryKeyJoinColumns());
	}
	
	private Translator createIdClassTranslator() {
		return new IdClassTranslator(ID_CLASS, ECLIPSELINK_ORM_PKG.getXmlEntity_IdClass());
	}
	
	private Translator createInheritanceTranslator() {
		return new InheritanceTranslator(INHERITANCE, ECLIPSELINK_ORM_PKG.getXmlEntity_Inheritance());
	}
	
	private Translator createDiscriminatorValueTranslator() {
		return new Translator(DISCRIMINATOR_VALUE, ECLIPSELINK_ORM_PKG.getXmlEntity_DiscriminatorValue());
	}
	
	private Translator createDiscriminatorColumnTranslator() {
		return new DiscriminatorColumnTranslator(DISCRIMINATOR_COLUMN, ECLIPSELINK_ORM_PKG.getXmlEntity_DiscriminatorColumn());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ECLIPSELINK_ORM_PKG.getXmlEntity_SequenceGenerator());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ECLIPSELINK_ORM_PKG.getXmlEntity_TableGenerator());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, ECLIPSELINK_ORM_PKG.getXmlEntity_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, ECLIPSELINK_ORM_PKG.getXmlEntity_NamedNativeQueries());
	}
	
	private Translator createSqlResultSetMappingTranslator() {
		return new SqlResultSetMappingTranslator(SQL_RESULT_SET_MAPPING, ECLIPSELINK_ORM_PKG.getXmlEntity_SqlResultSetMappings());
	}
	
	private Translator createExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_LISTENERS, ECLIPSELINK_ORM_PKG.getXmlEntity_ExcludeDefaultListeners());
	}
	
	private Translator createExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_SUPERCLASS_LISTENERS, ECLIPSELINK_ORM_PKG.getXmlEntity_ExcludeSuperclassListeners());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ECLIPSELINK_ORM_PKG.getXmlEntity_EntityListeners());
	}
	
	private Translator createPrePersistTranslator() {
		return new EventMethodTranslator(PRE_PERSIST, ECLIPSELINK_ORM_PKG.getXmlEntity_PrePersist());
	}
	
	private Translator createPostPersistTranslator() {
		return new EventMethodTranslator(POST_PERSIST, ECLIPSELINK_ORM_PKG.getXmlEntity_PostPersist());
	}
	
	private Translator createPreRemoveTranslator() {
		return new EventMethodTranslator(PRE_REMOVE, ECLIPSELINK_ORM_PKG.getXmlEntity_PreRemove());
	}
	
	private Translator createPostRemoveTranslator() {
		return new EventMethodTranslator(POST_REMOVE, ECLIPSELINK_ORM_PKG.getXmlEntity_PostRemove());
	}
	
	private Translator createPreUpdateTranslator() {
		return new EventMethodTranslator(PRE_UPDATE, ECLIPSELINK_ORM_PKG.getXmlEntity_PreUpdate());
	}
	
	private Translator createPostUpdateTranslator() {
		return new EventMethodTranslator(POST_UPDATE, ECLIPSELINK_ORM_PKG.getXmlEntity_PostUpdate());
	}
	
	private Translator createPostLoadTranslator() {
		return new EventMethodTranslator(POST_LOAD, ECLIPSELINK_ORM_PKG.getXmlEntity_PostLoad());
	}
	
	private Translator createAttributeOverrideTranslator() {
		return new AttributeOverrideTranslator(ATTRIBUTE_OVERRIDE, ECLIPSELINK_ORM_PKG.getXmlEntity_AttributeOverrides());
	}
	
	private Translator createAssociationOverrideTranslator() {
		return new AssociationOverrideTranslator(ASSOCIATION_OVERRIDE, ECLIPSELINK_ORM_PKG.getXmlEntity_AssociationOverrides());
	}
	
	private Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ECLIPSELINK_ORM_PKG.getAbstractXmlTypeMapping_Attributes());
	}
}
