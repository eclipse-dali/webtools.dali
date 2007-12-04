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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


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

	protected final List<XmlPersistentType> persistentTypes;
	
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
		this.persistentTypes = new ArrayList<XmlPersistentType>();
	}
	
	public PersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.persistenceUnitMetadata;
	}

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

	public ListIterator<XmlPersistentType> xmlPersistentTypes() {
		return new CloneListIterator<XmlPersistentType>(this.persistentTypes);
	}
	
	public int xmlPersistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public XmlPersistentType addXmlPersistentType(String className, String mappingKey) {
		XmlPersistentType persistentType = jpaFactory().createXmlPersistentType(this, mappingKey, this.entityMappings);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + ".")) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		persistentType.createAndAddOrmResourceMapping(mappingKey, className);
		fireItemAdded(PERSISTENT_TYPES_LIST, index, persistentType);
		return persistentType;
	}

	protected int insertionIndex(XmlPersistentType persistentType) {
		return CollectionTools.insertionIndexOf(this.persistentTypes, persistentType, buildMappingComparator());
	}

	private Comparator<XmlPersistentType> buildMappingComparator() {
		return new Comparator<XmlPersistentType>() {
			public int compare(XmlPersistentType o1, XmlPersistentType o2) {
				int o1Sequence = o1.getMapping().xmlSequence();
				int o2Sequence = o2.getMapping().xmlSequence();
				if (o1Sequence < o2Sequence) {
					return -1;
				}
				if (o1Sequence == o2Sequence) {
					return 0;
				}
				return 1;
			}
		};
	}

	public void removeXmlPersistentType(int index) {
		XmlPersistentType xmlPersistentType = this.persistentTypes.get(index);		
		this.persistentTypes.remove(index);
		xmlPersistentType.getMapping().removeFromResourceModel();
		fireItemRemoved(PERSISTENT_TYPES_LIST, index, xmlPersistentType);		
	}
	
	protected void removeXmlPersistentType(XmlPersistentType xmlPersistentType) {
		removeItemFromList(xmlPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	public void changeMapping(XmlPersistentType xmlPersistentType, XmlTypeMapping oldMapping, XmlTypeMapping newMapping) {
		int sourceIndex = this.persistentTypes.indexOf(xmlPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeFromResourceModel();
		int targetIndex = insertionIndex(xmlPersistentType);
		this.persistentTypes.add(targetIndex, xmlPersistentType);
		newMapping.addToResourceModel(this.entityMappings);
		newMapping.initializeFrom(oldMapping);
		//TODO are the source and target correct in this case, or is target off by one???
		fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}
	
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

	public PersistenceUnitDefaults persistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	public void initialize(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		this.version = entityMappings.getVersion();
		this.description = entityMappings.getDescription();
		this.package_ = entityMappings.getPackage();
		this.specifiedSchema = entityMappings.getSchema();
		this.specifiedCatalog = entityMappings.getCatalog();
		this.specifiedAccess = this.specifiedAccess(entityMappings);
		this.persistenceUnitMetadata.initialize(entityMappings);
		this.defaultAccess = persistenceUnit().getDefaultAccess();
		this.defaultCatalog = persistenceUnit().getDefaultCatalog();
		this.defaultSchema = persistenceUnit().getDefaultSchema();
		this.initializePersistentTypes(entityMappings);
	}
	
	protected void initializePersistentTypes(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.initializeMappedSuperclasses(entityMappings);
		this.initializeEntities(entityMappings);
		this.initializeEmbeddables(entityMappings);
	}
	
	protected void initializeMappedSuperclasses(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (MappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, entityMappings);
			((XmlMappedSuperclass) xmlPersistentType.getMapping()).initialize(mappedSuperclass);
			this.persistentTypes.add(xmlPersistentType);
		}	
	}
	
	protected void initializeEntities(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (Entity entity : entityMappings.getEntities()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.ENTITY_TYPE_MAPPING_KEY, entityMappings);
			((XmlEntity) xmlPersistentType.getMapping()).initialize(entity);
			this.persistentTypes.add(xmlPersistentType);
		}				
	}
	
	protected void initializeEmbeddables(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (Embeddable embeddable : entityMappings.getEmbeddables()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, entityMappings);
			((XmlEmbeddable) xmlPersistentType.getMapping()).initialize(embeddable);
			this.persistentTypes.add(xmlPersistentType);
		}
	}
	
	public void update(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
		this.setDescription(entityMappings.getDescription());
		this.setPackage(entityMappings.getPackage());
		this.setSpecifiedSchema(entityMappings.getSchema());
		this.setSpecifiedCatalog(entityMappings.getCatalog());
		this.setSpecifiedAccess(this.specifiedAccess(entityMappings));
		this.persistenceUnitMetadata.update(entityMappings);
		this.setDefaultAccess(persistenceUnit().getDefaultAccess());
		this.setDefaultCatalog(persistenceUnit().getDefaultCatalog());
		this.setDefaultSchema(persistenceUnit().getDefaultSchema());
		this.updatePersistentTypes(entityMappings);
	}
	
	protected AccessType specifiedAccess(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		return AccessType.fromXmlResourceModel(entityMappings.getAccess());
	}
	
//	protected AccessType defaultAccess(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
//		return persistenceUnit().persistenceUnitDefaults().getAccess();
//	}
	
	protected void updatePersistentTypes(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		ListIterator<XmlPersistentType> xmlPersistentTypes = this.xmlPersistentTypes();
		this.updateMappedSuperclasses(entityMappings, xmlPersistentTypes);
		this.updateEntities(entityMappings, xmlPersistentTypes);
		this.updateEmbeddables(entityMappings, xmlPersistentTypes);
		
		while (xmlPersistentTypes.hasNext()) {
			this.removeXmlPersistentType(xmlPersistentTypes.next());
		}		
	}
	
	protected void updateMappedSuperclasses(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings, ListIterator<XmlPersistentType> xmlPersistentTypes) {
		for (MappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			if (xmlPersistentTypes.hasNext()) {
				XmlPersistentType xmlPersistentType = xmlPersistentTypes.next();
				if (xmlPersistentType.getMappingKey() == IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
					((XmlMappedSuperclass) xmlPersistentType.getMapping()).update(mappedSuperclass);
				}
				else {
					xmlPersistentType.setMappingKey_(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
					((XmlMappedSuperclass) xmlPersistentType.getMapping()).initialize(mappedSuperclass);
				}
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, entityMappings);
				((XmlMappedSuperclass) xmlPersistentType.getMapping()).initialize(mappedSuperclass);
				this.persistentTypes.add(xmlPersistentType);
			}
		}
	}
	
	protected void updateEntities(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings, ListIterator<XmlPersistentType> xmlPersistentTypes) {
		for (Entity entity : entityMappings.getEntities()) {
			if (xmlPersistentTypes.hasNext()) {
				XmlPersistentType xmlPersistentType = xmlPersistentTypes.next();
				if (xmlPersistentType.getMappingKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
					((XmlEntity) xmlPersistentType.getMapping()).update(entity);
				}
				else {
					xmlPersistentType.setMappingKey_(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
					((XmlEntity) xmlPersistentType.getMapping()).initialize(entity);					
				}
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.ENTITY_TYPE_MAPPING_KEY, entityMappings);
				((XmlEntity) xmlPersistentType.getMapping()).initialize(entity);
				this.persistentTypes.add(xmlPersistentType);
			}
		}
	}
	
	protected void updateEmbeddables(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings, ListIterator<XmlPersistentType> xmlPersistentTypes) {
		for (Embeddable embeddable : entityMappings.getEmbeddables()) {
			if (xmlPersistentTypes.hasNext()) {
				XmlPersistentType xmlPersistentType = xmlPersistentTypes.next();
				if (xmlPersistentType.getMappingKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
					((XmlEmbeddable) xmlPersistentType.getMapping()).update(embeddable);
				}
				else {
					xmlPersistentType.setMappingKey_(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
					((XmlEmbeddable) xmlPersistentType.getMapping()).initialize(embeddable);				
				}
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, entityMappings);
				((XmlEmbeddable) xmlPersistentType.getMapping()).initialize(embeddable);
				this.persistentTypes.add(xmlPersistentType);
			}
		}
	}

}
