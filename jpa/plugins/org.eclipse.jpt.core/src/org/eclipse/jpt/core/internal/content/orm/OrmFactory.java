/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.ITable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage
 * @generated
 */
public class OrmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OrmFactory init() {
		try {
			OrmFactory theOrmFactory = (OrmFactory) EPackage.Registry.INSTANCE.getEFactory("jpt.orm.xmi");
			if (theOrmFactory != null) {
				return theOrmFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OrmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmFactory() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case OrmPackage.XML_ROOT_CONTENT_NODE :
				return createXmlRootContentNode();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL :
				return createEntityMappingsInternal();
			case OrmPackage.XML_PERSISTENT_TYPE :
				return createXmlPersistentType();
			case OrmPackage.XML_MAPPED_SUPERCLASS :
				return createXmlMappedSuperclass();
			case OrmPackage.XML_ENTITY_INTERNAL :
				return createXmlEntityInternal();
			case OrmPackage.XML_EMBEDDABLE :
				return createXmlEmbeddable();
			case OrmPackage.XML_NULL_ATTRIBUTE_MAPPING :
				return createXmlNullAttributeMapping();
			case OrmPackage.XML_BASIC :
				return createXmlBasic();
			case OrmPackage.XML_ID :
				return createXmlId();
			case OrmPackage.XML_TRANSIENT :
				return createXmlTransient();
			case OrmPackage.XML_EMBEDDED :
				return createXmlEmbedded();
			case OrmPackage.XML_EMBEDDED_ID :
				return createXmlEmbeddedId();
			case OrmPackage.XML_VERSION :
				return createXmlVersion();
			case OrmPackage.XML_ONE_TO_MANY :
				return createXmlOneToMany();
			case OrmPackage.XML_MANY_TO_MANY :
				return createXmlManyToMany();
			case OrmPackage.XML_PERSISTENT_ATTRIBUTE :
				return createXmlPersistentAttribute();
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL :
				return createPersistenceUnitMetadataInternal();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL :
				return createPersistenceUnitDefaultsInternal();
			case OrmPackage.XML_TABLE :
				return createXmlTable();
			case OrmPackage.XML_COLUMN :
				return createXmlColumn();
			case OrmPackage.XML_JOIN_COLUMN :
				return createXmlJoinColumn();
			case OrmPackage.XML_MANY_TO_ONE :
				return createXmlManyToOne();
			case OrmPackage.XML_ONE_TO_ONE :
				return createXmlOneToOne();
			case OrmPackage.XML_JOIN_TABLE :
				return createXmlJoinTable();
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE :
				return createXmlAttributeOverride();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE :
				return createXmlAssociationOverride();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN :
				return createXmlDiscriminatorColumn();
			case OrmPackage.XML_SECONDARY_TABLE :
				return createXmlSecondaryTable();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN :
				return createXmlPrimaryKeyJoinColumn();
			case OrmPackage.XML_GENERATED_VALUE :
				return createXmlGeneratedValue();
			case OrmPackage.XML_SEQUENCE_GENERATOR :
				return createXmlSequenceGenerator();
			case OrmPackage.XML_TABLE_GENERATOR :
				return createXmlTableGenerator();
			case OrmPackage.XML_ORDER_BY :
				return createXmlOrderBy();
			case OrmPackage.XML_NAMED_QUERY :
				return createXmlNamedQuery();
			case OrmPackage.XML_NAMED_NATIVE_QUERY :
				return createXmlNamedNativeQuery();
			case OrmPackage.XML_QUERY_HINT :
				return createXmlQueryHint();
			default :
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlRootContentNode createXmlRootContentNode() {
		XmlRootContentNode xmlRootContentNode = new XmlRootContentNode();
		return xmlRootContentNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityMappingsInternal createEntityMappingsInternal() {
		EntityMappingsInternal entityMappingsInternal = new EntityMappingsInternal();
		return entityMappingsInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistentType createXmlPersistentType() {
		XmlPersistentType xmlPersistentType = new XmlPersistentType();
		return xmlPersistentType;
	}

	public XmlPersistentType createXmlPersistentType(String mappingKey) {
		XmlPersistentType xmlPersistentType = new XmlPersistentType(mappingKey);
		return xmlPersistentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMappedSuperclass createXmlMappedSuperclassGen() {
		XmlMappedSuperclass xmlMappedSuperclass = new XmlMappedSuperclass();
		return xmlMappedSuperclass;
	}

	public XmlMappedSuperclass createXmlMappedSuperclass() {
		XmlMappedSuperclass mappedSuperclass = createXmlMappedSuperclassGen();
		XmlPersistentType persistentType = createXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		mappedSuperclass.setPersistentType(persistentType);
		return mappedSuperclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEntityInternal createXmlEntityInternalGen() {
		XmlEntityInternal xmlEntityInternal = new XmlEntityInternal();
		return xmlEntityInternal;
	}

	public XmlEntityInternal createXmlEntityInternal() {
		XmlEntityInternal entity = createXmlEntityInternalGen();
		XmlPersistentType persistentType = createXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		entity.setPersistentType(persistentType);
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddable createXmlEmbeddableGen() {
		XmlEmbeddable xmlEmbeddable = new XmlEmbeddable();
		return xmlEmbeddable;
	}

	public XmlEmbeddable createXmlEmbeddable() {
		XmlEmbeddable embeddable = createXmlEmbeddableGen();
		XmlPersistentType persistentType = createXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		embeddable.setPersistentType(persistentType);
		return embeddable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNullAttributeMapping createXmlNullAttributeMappingGen() {
		XmlNullAttributeMapping xmlNullAttributeMapping = new XmlNullAttributeMapping();
		return xmlNullAttributeMapping;
	}

	public XmlNullAttributeMapping createXmlNullAttributeMapping() {
		XmlNullAttributeMapping xmlNullAttributeMapping = createXmlNullAttributeMappingGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlNullAttributeMapping.setPersistentAttribute(persistentAttribute);
		return xmlNullAttributeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistentAttribute createXmlPersistentAttribute() {
		XmlPersistentAttribute xmlPersistentAttribute = new XmlPersistentAttribute();
		return xmlPersistentAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasic createXmlBasicGen() {
		XmlBasic xmlBasic = new XmlBasic();
		return xmlBasic;
	}

	public XmlBasic createXmlBasic() {
		XmlBasic basic = createXmlBasicGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		basic.setPersistentAttribute(persistentAttribute);
		return basic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlId createXmlIdGen() {
		XmlId xmlId = new XmlId();
		return xmlId;
	}

	public XmlId createXmlId() {
		XmlId id = createXmlIdGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		id.setPersistentAttribute(persistentAttribute);
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransient createXmlTransientGen() {
		XmlTransient xmlTransient = new XmlTransient();
		return xmlTransient;
	}

	public XmlTransient createXmlTransient() {
		XmlTransient xmlTransient = createXmlTransientGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlTransient.setPersistentAttribute(persistentAttribute);
		return xmlTransient;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbedded createXmlEmbeddedGen() {
		XmlEmbedded xmlEmbedded = new XmlEmbedded();
		return xmlEmbedded;
	}

	public XmlEmbedded createXmlEmbedded() {
		XmlEmbedded xmlEmbedded = createXmlEmbeddedGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlEmbedded.setPersistentAttribute(persistentAttribute);
		return xmlEmbedded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddedId createXmlEmbeddedIdGen() {
		XmlEmbeddedId xmlEmbeddedId = new XmlEmbeddedId();
		return xmlEmbeddedId;
	}

	public XmlEmbeddedId createXmlEmbeddedId() {
		XmlEmbeddedId xmlEmbeddedId = createXmlEmbeddedIdGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlEmbeddedId.setPersistentAttribute(persistentAttribute);
		return xmlEmbeddedId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVersion createXmlVersionGen() {
		XmlVersion xmlVersion = new XmlVersion();
		return xmlVersion;
	}

	public XmlVersion createXmlVersion() {
		XmlVersion xmlVersion = createXmlVersionGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlVersion.setPersistentAttribute(persistentAttribute);
		return xmlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToMany createXmlOneToManyGen() {
		XmlOneToMany xmlOneToMany = new XmlOneToMany();
		return xmlOneToMany;
	}

	public XmlOneToMany createXmlOneToMany() {
		XmlOneToMany oneToMany = createXmlOneToManyGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		oneToMany.setPersistentAttribute(persistentAttribute);
		return oneToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToMany createXmlManyToManyGen() {
		XmlManyToMany xmlManyToMany = new XmlManyToMany();
		return xmlManyToMany;
	}

	public XmlManyToMany createXmlManyToMany() {
		XmlManyToMany manyToMany = createXmlManyToManyGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		manyToMany.setPersistentAttribute(persistentAttribute);
		return manyToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceUnitMetadataInternal createPersistenceUnitMetadataInternal() {
		PersistenceUnitMetadataInternal persistenceUnitMetadataInternal = new PersistenceUnitMetadataInternal();
		return persistenceUnitMetadataInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceUnitDefaultsInternal createPersistenceUnitDefaultsInternal() {
		PersistenceUnitDefaultsInternal persistenceUnitDefaultsInternal = new PersistenceUnitDefaultsInternal();
		return persistenceUnitDefaultsInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTable createXmlTable() {
		XmlTable xmlTable = new XmlTable();
		return xmlTable;
	}

	public XmlTable createXmlTable(ITable.Owner owner) {
		return new XmlTable(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlColumn createXmlColumn() {
		XmlColumn xmlColumn = new XmlColumn();
		return xmlColumn;
	}

	public XmlColumn createXmlColumn(IColumn.Owner owner) {
		return new XmlColumn(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public XmlJoinColumn createXmlJoinColumn() {
		throw new UnsupportedOperationException("Use createXmlJoinColumn(IColumn.Owner) instead");
	}

	public XmlJoinColumn createXmlJoinColumn(IJoinColumn.Owner owner) {
		return new XmlJoinColumn(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToOne createXmlManyToOneGen() {
		XmlManyToOne xmlManyToOne = new XmlManyToOne();
		return xmlManyToOne;
	}

	public XmlManyToOne createXmlManyToOne() {
		XmlManyToOne xmlManyToOne = createXmlManyToOneGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlManyToOne.setPersistentAttribute(persistentAttribute);
		return xmlManyToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToOne createXmlOneToOneGen() {
		XmlOneToOne xmlOneToOne = new XmlOneToOne();
		return xmlOneToOne;
	}

	public XmlOneToOne createXmlOneToOne() {
		XmlOneToOne xmlOneToOne = createXmlOneToOneGen();
		XmlPersistentAttribute persistentAttribute = createXmlPersistentAttribute();
		xmlOneToOne.setPersistentAttribute(persistentAttribute);
		return xmlOneToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJoinTable createXmlJoinTable() {
		XmlJoinTable xmlJoinTable = new XmlJoinTable();
		return xmlJoinTable;
	}

	public XmlJoinTable createXmlJoinTable(IJoinTable.Owner owner) {
		return new XmlJoinTable(owner);
	}

	public XmlAttributeOverride createXmlAttributeOverride() {
		throw new UnsupportedOperationException("Use createXmlAttributeOverride(IAttributeOverride.Owner) instead");
	}

	public XmlAttributeOverride createXmlAttributeOverride(IAttributeOverride.Owner owner) {
		XmlAttributeOverride xmlAttributeOverride = new XmlAttributeOverride(owner);
		return xmlAttributeOverride;
	}

	public XmlAssociationOverride createXmlAssociationOverride() {
		throw new UnsupportedOperationException("Use createXmlAssociationOverride(IAssociationOverride.Owner) instead");
	}

	public XmlAssociationOverride createXmlAssociationOverride(IAssociationOverride.Owner owner) {
		XmlAssociationOverride xmlAssociationOverride = new XmlAssociationOverride(owner);
		return xmlAssociationOverride;
	}

	public XmlDiscriminatorColumn createXmlDiscriminatorColumn() {
		throw new UnsupportedOperationException("Use createXmlDiscriminatorColumn(INamedColumn.Owner) instead");
	}
	
	public XmlDiscriminatorColumn createXmlDiscriminatorColumn(INamedColumn.Owner owner) {
		XmlDiscriminatorColumn xmlDiscriminatorColumn = new XmlDiscriminatorColumn(owner);
		return xmlDiscriminatorColumn;
	}

	public XmlSecondaryTable createXmlSecondaryTable() {
		throw new UnsupportedOperationException();
	}

	public XmlPrimaryKeyJoinColumn createXmlPrimaryKeyJoinColumn() {
		throw new UnsupportedOperationException("Use createXmlPrimaryKeyJoinColumn(INamedColumn.Owner) instead");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlGeneratedValue createXmlGeneratedValue() {
		XmlGeneratedValue xmlGeneratedValue = new XmlGeneratedValue();
		return xmlGeneratedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlSequenceGenerator createXmlSequenceGenerator() {
		XmlSequenceGenerator xmlSequenceGenerator = new XmlSequenceGenerator();
		return xmlSequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTableGenerator createXmlTableGenerator() {
		XmlTableGenerator xmlTableGenerator = new XmlTableGenerator();
		return xmlTableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOrderBy createXmlOrderBy() {
		XmlOrderBy xmlOrderBy = new XmlOrderBy();
		return xmlOrderBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedQuery createXmlNamedQuery() {
		XmlNamedQuery xmlNamedQuery = new XmlNamedQuery();
		return xmlNamedQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlNamedNativeQuery createXmlNamedNativeQuery() {
		XmlNamedNativeQuery xmlNamedNativeQuery = new XmlNamedNativeQuery();
		return xmlNamedNativeQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlQueryHint createXmlQueryHint() {
		XmlQueryHint xmlQueryHint = new XmlQueryHint();
		return xmlQueryHint;
	}

	public XmlPrimaryKeyJoinColumn createXmlPrimaryKeyJoinColumn(IAbstractJoinColumn.Owner owner) {
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn(owner);
		return xmlPrimaryKeyJoinColumn;
	}

	public XmlSecondaryTable createXmlSecondaryTable(ITable.Owner owner) {
		XmlSecondaryTable xmlSecondaryTable = new XmlSecondaryTable(owner);
		return xmlSecondaryTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmPackage getOrmPackage() {
		return (OrmPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OrmPackage getPackage() {
		return OrmPackage.eINSTANCE;
	}
} //JpaCoreXmlFactory