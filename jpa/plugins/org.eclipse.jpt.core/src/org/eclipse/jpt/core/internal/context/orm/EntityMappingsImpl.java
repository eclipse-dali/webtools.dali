/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;


public class EntityMappingsImpl extends JpaContextNode implements EntityMappings
{
	protected org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings;
	
	protected String version;
	
	protected String description;

	protected String package_;

	protected String defaultSchema;

	protected String specifiedSchema;

	protected String defaultCatalog;

	protected String specifiedCatalog;

	protected AccessType defaultAccess;

	protected AccessType specifiedAccess;
		
	protected final PersistenceUnitMetadata persistenceUnitMetadata;

//	protected EList<XmlTypeMapping> typeMappings;
//
//	protected EList<XmlPersistentType> persistentTypes;
//
//	protected EList<XmlSequenceGenerator> sequenceGenerators;
//
//	protected EList<XmlTableGenerator> tableGenerators;
//
//	protected EList<XmlNamedQuery> namedQueries;
//
//	protected EList<XmlNamedNativeQuery> namedNativeQueries;


	public EntityMappingsImpl(OrmXml parent) {
		super(parent);
		this.persistenceUnitMetadata = jpaFactory().createPersistenceUnitMetadata(this);
	}
	
	public PersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.persistenceUnitMetadata;
	}
//
//	@Override
//	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
//		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
//		insignificantFeatureIds.add(OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
//	}
//	public PersistenceUnitMetadataForXml getPersistenceUnitMetadataForXml() {
//		if (getPersistenceUnitMetadataInternal().isAllFeaturesUnset()) {
//			return null;
//		}
//		return getPersistenceUnitMetadataInternal();
//	}
//
//	public void setPersistenceUnitMetadataForXmlGen(PersistenceUnitMetadataForXml newPersistenceUnitMetadataForXml) {
//		PersistenceUnitMetadataForXml oldValue = newPersistenceUnitMetadataForXml == null ? (PersistenceUnitMetadataForXml) getPersistenceUnitMetadata() : null;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML, oldValue, newPersistenceUnitMetadataForXml));
//	}
//
//	public void setPersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml newPersistenceUnitMetadataForXml) {
//		setPersistenceUnitMetadataForXmlGen(newPersistenceUnitMetadataForXml);
//		if (newPersistenceUnitMetadataForXml == null) {
//			getPersistenceUnitMetadataInternal().unsetAllAttributes();
//		}
//	}
//
//	public void makePersistenceUnitMetadataForXmlNull() {
//		setPersistenceUnitMetadataForXmlGen(null);
//	}
//
//	public void makePersistenceUnitMetadataForXmlNonNull() {
//		setPersistenceUnitMetadataForXmlGen(getPersistenceUnitMetadataForXml());
//	}
//
//	public PersistenceUnitMetadata getPersistenceUnitMetadata() {
//		return getPersistenceUnitMetadataInternal();
//	}
//
//	public PersistenceUnitMetadataInternal getPersistenceUnitMetadataInternal() {
//		return persistenceUnitMetadataInternal;
//	}
//	public NotificationChain basicSetPersistenceUnitMetadataInternal(PersistenceUnitMetadataInternal newPersistenceUnitMetadataInternal, NotificationChain msgs) {
//		PersistenceUnitMetadataInternal oldPersistenceUnitMetadataInternal = persistenceUnitMetadataInternal;
//		persistenceUnitMetadataInternal = newPersistenceUnitMetadataInternal;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL, oldPersistenceUnitMetadataInternal, newPersistenceUnitMetadataInternal);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}

	public String getPackage() {
		return this.package_;
	}
	
	public void setPackage(String newPackage) {
		String oldPackage = this.package_;
		this.package_ = newPackage;
		this.entityMappings.setPackage(newPackage);
		firePropertyChanged(PACKAGE_PROPERTY, oldPackage, newPackage);
	}

	public String getVersion() {
		return this.version;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String newDescription) {
		String oldDescription = this.description;
		this.description = newDescription;
		this.entityMappings.setDescription(newDescription);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldDescription, newDescription);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		this.entityMappings.setSchema(newSpecifiedSchema);
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? this.getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		this.entityMappings.setCatalog(newSpecifiedCatalog);
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? this.getDefaultCatalog() : this.getSpecifiedCatalog();
	}
	
	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, newDefaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldSpecifiedAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		this.entityMappings.setAccess(AccessType.toXmlResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, newSpecifiedAccess);
	}

//	public EList<XmlTypeMapping> getTypeMappingsGen() {
//		if (typeMappings == null) {
//			typeMappings = new EObjectContainmentEList<XmlTypeMapping>(XmlTypeMapping.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS);
//		}
//		return typeMappings;
//	}
//
//	public EList<XmlTypeMapping> getTypeMappings() {
//		if (typeMappings == null) {
//			typeMappings = new TypeMappingsList<XmlTypeMapping>();
//		}
//		return getTypeMappingsGen();
//	}
//
//	/**
//	 * Returns the value of the '<em><b>Persistent Types</b></em>' reference list.
//	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType}.
//	 * <!-- begin-user-doc -->
//	 * <p>
//	 * If the meaning of the '<em>Persistent Types</em>' reference list isn't clear,
//	 * there really should be more of a description here...
//	 * </p>
//	 * <!-- end-user-doc -->
//	 * @return the value of the '<em>Persistent Types</em>' reference list.
//	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_PersistentTypes()
//	 * @model resolveProxies="false"
//	 * @generated
//	 */
//	public EList<XmlPersistentType> getPersistentTypes() {
//		if (persistentTypes == null) {
//			persistentTypes = new EObjectEList<XmlPersistentType>(XmlPersistentType.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
//		}
//		return persistentTypes;
//	}
//
//	public boolean containsPersistentType(IType type) {
//		if (type == null) {
//			return false;
//		}
//		for (XmlPersistentType each : getPersistentTypes()) {
//			if (type.equals(each.findJdtType())) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public EList<XmlTableGenerator> getTableGenerators() {
//		if (tableGenerators == null) {
//			tableGenerators = new EObjectContainmentEList<XmlTableGenerator>(XmlTableGenerator.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS);
//		}
//		return tableGenerators;
//	}
//
//	public EList<XmlNamedQuery> getNamedQueries() {
//		if (namedQueries == null) {
//			namedQueries = new EObjectContainmentEList<XmlNamedQuery>(XmlNamedQuery.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES);
//		}
//		return namedQueries;
//	}

//	public EList<XmlNamedNativeQuery> getNamedNativeQueries() {
//		if (namedNativeQueries == null) {
//			namedNativeQueries = new EObjectContainmentEList<XmlNamedNativeQuery>(XmlNamedNativeQuery.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES);
//		}
//		return namedNativeQueries;
//	}
//
//	public EList<XmlSequenceGenerator> getSequenceGenerators() {
//		if (sequenceGenerators == null) {
//			sequenceGenerators = new EObjectContainmentEList<XmlSequenceGenerator>(XmlSequenceGenerator.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS);
//		}
//		return sequenceGenerators;
//	}
//
//	public void addMapping(String className, String mappingKey) {
//		XmlPersistentType persistentType = OrmFactory.eINSTANCE.createXmlPersistentType();
//		XmlTypeMapping typeMapping = buildXmlTypeMapping(persistentType.typeMappingProviders(), mappingKey);
//		if (className.startsWith(getPackage() + ".")) {
//			// adds short name if package name is specified
//			className = className.substring(getPackage().length() + 1);
//		}
//		typeMapping.getPersistentType().setClass(className);
//		insertTypeMapping(typeMapping);
//	}
//
//	public void changeMapping(XmlTypeMapping oldMapping, String newMappingKey) {
//		XmlTypeMapping newTypeMapping = buildXmlTypeMapping(oldMapping.getPersistentType().typeMappingProviders(), newMappingKey);
//		newTypeMapping.setPersistentType(oldMapping.getPersistentType());
//		getTypeMappings().remove(oldMapping);
//		newTypeMapping.initializeFrom(oldMapping);
//		insertTypeMapping(newTypeMapping);
//	}
//
//	private XmlTypeMapping buildXmlTypeMapping(Collection<IXmlTypeMappingProvider> providers, String key) {
//		for (IXmlTypeMappingProvider provider : providers) {
//			if (provider.key().equals(key)) {
//				return provider.buildTypeMapping();
//			}
//		}
//		//TODO throw an exception? what about the NullJavaTypeMapping?
//		return null;
//	}
//
//	private void insertTypeMapping(XmlTypeMapping newMapping) {
//		int newIndex = CollectionTools.insertionIndexOf(getTypeMappings(), newMapping, buildMappingComparator());
//		getTypeMappings().add(newIndex, newMapping);
//	}
//
//	private Comparator<XmlTypeMapping> buildMappingComparator() {
//		return new Comparator<XmlTypeMapping>() {
//			public int compare(XmlTypeMapping o1, XmlTypeMapping o2) {
//				int o1Sequence = o1.xmlSequence();
//				int o2Sequence = o2.xmlSequence();
//				if (o1Sequence < o2Sequence) {
//					return -1;
//				}
//				if (o1Sequence == o2Sequence) {
//					return 0;
//				}
//				return 1;
//			}
//		};
//	}
//
//	/* @see IJpaContentNode#getId() */
//	public Object getId() {
//		return IXmlContentNodes.ENTITY_MAPPINGS_ID;
//	}
//
//	public IJpaContentNode getContentNode(int offset) {
//		for (Iterator i = getTypeMappings().iterator(); i.hasNext();) {
//			XmlTypeMapping mapping = (XmlTypeMapping) i.next();
//			if (mapping.getNode().contains(offset)) {
//				return mapping.getContentNode(offset);
//			}
//		}
//		return this;
//	}
//
//	public void javaElementChanged(ElementChangedEvent event) {
//	}

	
	public void initialize(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		this.version = entityMappings.getVersion();
		this.description = entityMappings.getDescription();
		this.package_ = entityMappings.getPackage();
		this.specifiedSchema = entityMappings.getSchema();
		this.specifiedCatalog = entityMappings.getCatalog();
		this.specifiedAccess = this.specifiedAccess(entityMappings);
		this.persistenceUnitMetadata.initialize(entityMappings);
		//TODO one we support persistenceUnitDefaults
		//this.defaultAccess = persistenceUnit().persistenceUnitDefaults().getAccess();
		//this.defaultCatalog = persistenceUnit().persistenceUnitDefaults().getAccess();
		//this.defaultSchema = persistenceUnit().persistenceUnitDefaults().getAccess();
	}
	
	public void update(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		this.setDescription(entityMappings.getDescription());
		this.setPackage(entityMappings.getPackage());
		this.setSpecifiedSchema(entityMappings.getSchema());
		this.setSpecifiedCatalog(entityMappings.getCatalog());
		this.setSpecifiedAccess(this.specifiedAccess(entityMappings));
		this.persistenceUnitMetadata.update(entityMappings);
		//TODO one we support persistenceUnitDefaults
		//this.setDefaultAccess(persistenceUnit().persistenceUnitDefaults().getAccess());
		//this.setDefaultCatalog(persistenceUnit().persistenceUnitDefaults().getAccess());
		//this.setDefaultSchema(persistenceUnit().persistenceUnitDefaults().getAccess());
		
	}
	
	protected AccessType specifiedAccess(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		return AccessType.fromXmlResourceModel(entityMappings.getAccess());
	}

	
	//	private class TypeMappingsList<E>
//		extends EObjectContainmentEList<XmlTypeMapping>
//	{
//		private TypeMappingsList() {
//			super(XmlTypeMapping.class, EntityMappingsInternal.this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS);
//		}
//
//		@Override
//		protected void didAdd(int index, XmlTypeMapping newObject) {
//			XmlPersistentType type = newObject.getPersistentType();
//			if (getPersistentTypes().contains(type)) {
//				// the type has been remapped.  don't remove, simply move.
//				getPersistentTypes().move(index, type);
//			}
//			else {
//				getPersistentTypes().add(index, type);
//			}
//		}
//
//		@Override
//		protected void didChange() {
//			// TODO Auto-generated method stub
//			super.didChange();
//		}
//
//		@Override
//		protected void didClear(int size, Object[] oldObjects) {
//			getPersistentTypes().clear();
//		}
//
//		@Override
//		protected void didMove(int index, XmlTypeMapping movedObject, int oldIndex) {
//			getPersistentTypes().move(index, movedObject.getPersistentType());
//		}
//
//		@Override
//		protected void didRemove(int index, XmlTypeMapping oldObject) {
//			XmlPersistentType type = oldObject.getPersistentType();
//			if (type != null) {
//				// the type has been remapped.  don't remove, simply move.
//				// (see didAdd(int, XmlTypeMapping) )
//				getPersistentTypes().remove(oldObject.getPersistentType());
//			}
//		}
//
//		@Override
//		protected void didSet(int index, XmlTypeMapping newObject, XmlTypeMapping oldObject) {
//			getPersistentTypes().set(index, newObject.getPersistentType());
//		}
//	}
}
