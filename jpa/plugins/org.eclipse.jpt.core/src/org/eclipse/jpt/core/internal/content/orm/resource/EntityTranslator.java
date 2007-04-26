/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.resource.AssociationOverrideTranslator.AssociationOverrideBuilder;
import org.eclipse.jpt.core.internal.content.orm.resource.AttributeOverrideTranslator.AttributeOverrideBuilder;
import org.eclipse.jpt.core.internal.content.orm.resource.PrimaryKeyJoinColumnTranslator.PrimaryKeyJoinColumnBuilder;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityTranslator extends TypeMappingTranslator
{	
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	
	private TableTranslator tableTranslator;
	private SecondaryTableTranslator secondaryTableTranslator;
	private DiscriminatorColumnTranslator discriminatorColumnTranslator;
	
	private IEntity entity;
	
	public EntityTranslator() {
		super(ENTITY);
		this.tableTranslator = createTableTranslator();
		this.secondaryTableTranslator = createSecondaryTableTranslator();
		this.discriminatorColumnTranslator = createDiscriminatorColumnTranslator();
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		this.entity = JPA_CORE_XML_FACTORY.createXmlEntityInternal();
		this.tableTranslator.setEntity(this.entity);
		this.secondaryTableTranslator.setEntity(this.entity);
		this.discriminatorColumnTranslator.setEntity(this.entity);
		return this.entity;
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createJavaClassTranslator(),
			createAccessTypeTranslator(),
			createMetadataCompleteTranslator(),
			createPlaceHolderTranslator(ENTITY__DESCRIPTION),
			getTableTranslator(),
			getSecondaryTableTranslator(),
			createPrimaryKeyJoinColumnsTranslator(),
			createPlaceHolderTranslator(ENTITY__ID_CLASS),
			createInheritanceTranslator(),
			createDiscriminatorValueTranslator(),
			getDiscriminatorColumnTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createPlaceHolderTranslator(ENTITY__SQL_RESULT_SET_MAPPING),
			createPlaceHolderTranslator(ENTITY__EXCLUDE_DEFAULT_LISTENERS),
			createPlaceHolderTranslator(ENTITY__EXCLUDE_SUPERCLASS_LISTENERS),
			createPlaceHolderTranslator(ENTITY__ENTITY_LISTENERS),
			createPlaceHolderTranslator(ENTITY__PRE_PERSIST),
			createPlaceHolderTranslator(ENTITY__POST_PERSIST),
			createPlaceHolderTranslator(ENTITY__PRE_REMOVE),
			createPlaceHolderTranslator(ENTITY__POST_REMOVE),
			createPlaceHolderTranslator(ENTITY__PRE_UPDATE),
			createPlaceHolderTranslator(ENTITY__POST_UPDATE),
			createPlaceHolderTranslator(ENTITY__POST_LOAD),
			createAttributeOverridesTranslator(),
			createAssociationOverridesTranslator(),
			createPersistentAttributesTranslator()
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAME, MAPPINGS_PKG.getIEntity_SpecifiedName(), DOM_ATTRIBUTE);
	}
	
	private Translator getTableTranslator() {
		return this.tableTranslator;
	}
	
	private Translator getSecondaryTableTranslator() {
		return this.secondaryTableTranslator;
	}	
	
	private Translator getDiscriminatorColumnTranslator() {
		return this.discriminatorColumnTranslator;
	}
	
	private TableTranslator createTableTranslator() {
		return new TableTranslator();
	}
	
	private SecondaryTableTranslator createSecondaryTableTranslator() {
		return new SecondaryTableTranslator();
	}
	
	private DiscriminatorColumnTranslator createDiscriminatorColumnTranslator() {
		return new DiscriminatorColumnTranslator();
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, JpaCoreMappingsPackage.eINSTANCE.getIEntity_TableGenerator());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, JpaCoreMappingsPackage.eINSTANCE.getIEntity_SequenceGenerator());
	}
	
	private Translator createNamedQueryTranslator() {
		return new NamedQueryTranslator(NAMED_QUERY, JpaCoreMappingsPackage.eINSTANCE.getIEntity_NamedQueries());
	}
	
	private Translator createNamedNativeQueryTranslator() {
		return new NamedNativeQueryTranslator(NAMED_NATIVE_QUERY, JpaCoreMappingsPackage.eINSTANCE.getIEntity_NamedNativeQueries());
	}
	
	protected Translator createDiscriminatorValueTranslator() {
		return new Translator(ENTITY__DISCRIMINATOR_VALUE, MAPPINGS_PKG.getIEntity_SpecifiedDiscriminatorValue(), NO_STYLE);
	}
	
	protected Translator createInheritanceTranslator() {
		return new EnumeratorTranslator(ENTITY__INHERITANCE + "/" + STRATEGY, MAPPINGS_PKG.getIEntity_InheritanceStrategy(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPrimaryKeyJoinColumnsTranslator() {
		return new PrimaryKeyJoinColumnTranslator(
			ENTITY__PRIMARY_KEY_JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getIEntity_SpecifiedPrimaryKeyJoinColumns(),
				buildPrimaryKeyJoinColumnsBuilder());
	}
	
	private PrimaryKeyJoinColumnBuilder buildPrimaryKeyJoinColumnsBuilder() {
		return new PrimaryKeyJoinColumnBuilder() {
			public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new IEntity.PrimaryKeyJoinColumnOwner(entity));
			}
		};
	}
	
	private Translator createAttributeOverridesTranslator() {
		return new AttributeOverrideTranslator(ENTITY__ATTRIBUTE_OVERRIDE, MAPPINGS_PKG.getIEntity_SpecifiedAttributeOverrides(), buildAttributeOverrideBuilder());
	}
	
	private AttributeOverrideBuilder buildAttributeOverrideBuilder() {
		return new AttributeOverrideBuilder() {
			public IAttributeOverride createAttributeOverride() {
				return EntityTranslator.this.entity.createAttributeOverride(0);
			}
		};
	}
	
	private Translator createAssociationOverridesTranslator() {
		return new AssociationOverrideTranslator(ENTITY__ASSOCIATION_OVERRIDE, MAPPINGS_PKG.getIEntity_SpecifiedAssociationOverrides(), buildAssociationOverrideBuilder());
	}
	
	private AssociationOverrideBuilder buildAssociationOverrideBuilder() {
		return new AssociationOverrideBuilder() {
			public IAssociationOverride createAssociationOverride() {
				return EntityTranslator.this.entity.createAssociationOverride(0);
			}
		};
	}
}
