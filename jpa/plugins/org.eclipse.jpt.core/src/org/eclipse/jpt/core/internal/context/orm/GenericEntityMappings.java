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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class GenericEntityMappings extends AbstractJpaContextNode implements EntityMappings
{
	protected org.eclipse.jpt.core.resource.orm.EntityMappings xmlEntityMappings;
	
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

	protected final List<OrmPersistentType> persistentTypes;

	protected final List<OrmSequenceGenerator> sequenceGenerators;
	
	protected final List<OrmTableGenerator> tableGenerators;

	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;


	public GenericEntityMappings(OrmXml parent) {
		super(parent);
		this.persistenceUnitMetadata = jpaFactory().buildPersistenceUnitMetadata(this);
		this.persistentTypes = new ArrayList<OrmPersistentType>();
		this.sequenceGenerators = new ArrayList<OrmSequenceGenerator>();
		this.tableGenerators = new ArrayList<OrmTableGenerator>();
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
	}
	
	public String getId() {
		return OrmStructureNodes.ENTITY_MAPPINGS_ID;
	}
	
	@Override
	public EntityMappings entityMappings() {
		return this;
	}
	
	public OrmPersistentType persistentTypeFor(String fullyQualifiedTypeName) {
		for (OrmPersistentType ormPersistentType : CollectionTools.iterable(ormPersistentTypes())) {
			if (ormPersistentType.isFor(fullyQualifiedTypeName)) {
				return ormPersistentType;
			}
		}
		return null;
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
		this.xmlEntityMappings.setPackage(newPackage);
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
		this.xmlEntityMappings.setDescription(newDescription);
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
		this.xmlEntityMappings.setSchema(newSpecifiedSchema);
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
		this.xmlEntityMappings.setCatalog(newSpecifiedCatalog);
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
		this.xmlEntityMappings.setAccess(AccessType.toXmlResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, newSpecifiedAccess);
	}

	public ListIterator<OrmPersistentType> ormPersistentTypes() {
		return new CloneListIterator<OrmPersistentType>(this.persistentTypes);
	}
	
	public int ormPersistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public OrmPersistentType addOrmPersistentType(String mappingKey, String className) {
		OrmPersistentType persistentType = jpaFactory().buildOrmPersistentType(this, mappingKey);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + ".")) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		AbstractTypeMapping typeMapping = persistentType.getMapping().addToResourceModel(this.xmlEntityMappings);
		typeMapping.setClassName(className);
		fireItemAdded(PERSISTENT_TYPES_LIST, index, persistentType);
		return persistentType;
	}
	
	protected void addOrmPersistentType(OrmPersistentType ormPersistentType) { 
		addItemToList(ormPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	protected int insertionIndex(OrmPersistentType ormPersistentType) {
		return CollectionTools.insertionIndexOf(this.persistentTypes, ormPersistentType, buildMappingComparator());
	}

	private Comparator<OrmPersistentType> buildMappingComparator() {
		return new Comparator<OrmPersistentType>() {
			public int compare(OrmPersistentType o1, OrmPersistentType o2) {
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

	public void removeOrmPersistentType(int index) {
		OrmPersistentType ormPersistentType = this.persistentTypes.get(index);		
		this.persistentTypes.remove(index);
		ormPersistentType.getMapping().removeFromResourceModel(this.xmlEntityMappings);
		fireItemRemoved(PERSISTENT_TYPES_LIST, index, ormPersistentType);		
	}
	
	public void removeOrmPersistentType(OrmPersistentType ormPersistentType) {
		removeOrmPersistentType(this.persistentTypes.indexOf(ormPersistentType));	
	}
	
	protected void removeOrmPersistentType_(OrmPersistentType ormPersistentType) {
		removeItemFromList(ormPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	public void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping) {
		int sourceIndex = this.persistentTypes.indexOf(ormPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(this.xmlEntityMappings);
		int targetIndex = insertionIndex(ormPersistentType);
		this.persistentTypes.add(targetIndex, ormPersistentType);
		newMapping.addToResourceModel(this.xmlEntityMappings);
		newMapping.initializeFrom(oldMapping);
		//TODO are the source and target correct in this case, or is target off by one???
		fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}
	
	public ListIterator<OrmSequenceGenerator> sequenceGenerators() {
		return new CloneListIterator<OrmSequenceGenerator>(this.sequenceGenerators);
	}
	
	public int sequenceGeneratorsSize() {
		return this.sequenceGenerators.size();
	}
	
	public SequenceGenerator addSequenceGenerator(int index) {
		OrmSequenceGenerator ormSequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		this.sequenceGenerators.add(index, ormSequenceGenerator);
		XmlSequenceGenerator sequenceGenerator = OrmFactory.eINSTANCE.createSequenceGeneratorImpl();
		ormSequenceGenerator.initialize(sequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().add(index, sequenceGenerator);
		fireItemAdded(SEQUENCE_GENERATORS_LIST, index, ormSequenceGenerator);
		return ormSequenceGenerator;
	}

	protected void addSequenceGenerator(int index, OrmSequenceGenerator sequenceGenerator) {
		addItemToList(index, sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
	}

	public void removeSequenceGenerator(SequenceGenerator sequenceGenerator) {
		removeSequenceGenerator(this.sequenceGenerators.indexOf(sequenceGenerator));
	}
	
	public void removeSequenceGenerator(int index) {
		OrmSequenceGenerator removedSequenceGenerator = this.sequenceGenerators.remove(index);
		fireItemRemoved(SEQUENCE_GENERATORS_LIST, index, removedSequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().remove(index);
	}
	
	protected void removeSequenceGenerator_(OrmSequenceGenerator sequenceGenerator) {
		removeItemFromList(sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
	}

	public void moveSequenceGenerator(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.sequenceGenerators, targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
		fireItemMoved(EntityMappings.SEQUENCE_GENERATORS_LIST, targetIndex, sourceIndex);	
	}

	public ListIterator<OrmTableGenerator> tableGenerators() {
		return new CloneListIterator<OrmTableGenerator>(this.tableGenerators);
	}

	public int tableGeneratorsSize() {
		return this.tableGenerators.size();
	}
	
	public TableGenerator addTableGenerator(int index) {
		OrmTableGenerator xmlTableGenerator = jpaFactory().buildOrmTableGenerator(this);
		this.tableGenerators.add(index, xmlTableGenerator);
		XmlTableGenerator tableGenerator = OrmFactory.eINSTANCE.createTableGeneratorImpl();
		xmlTableGenerator.initialize(tableGenerator);
		this.xmlEntityMappings.getTableGenerators().add(index, tableGenerator);
		fireItemAdded(TABLE_GENERATORS_LIST, index, xmlTableGenerator);
		return xmlTableGenerator;
	}
	
	protected void addTableGenerator(int index, OrmTableGenerator tableGenerator) {
		addItemToList(index, tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
	}

	public void removeTableGenerator(TableGenerator tableGenerator) {
		removeTableGenerator(this.tableGenerators.indexOf(tableGenerator));
	}

	public void removeTableGenerator(int index) {
		OrmTableGenerator removedTableGenerator = this.tableGenerators.remove(index);
		this.xmlEntityMappings.getTableGenerators().remove(index);
		fireItemRemoved(TABLE_GENERATORS_LIST, index, removedTableGenerator);
	}
	
	protected void removeTableGenerator_(OrmTableGenerator tableGenerator) {
		removeItemFromList(tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
	}

	public void moveTableGenerator(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.tableGenerators, targetIndex, sourceIndex);
		this.xmlEntityMappings.getTableGenerators().move(targetIndex, sourceIndex);
		fireItemMoved(EntityMappings.TABLE_GENERATORS_LIST, targetIndex, sourceIndex);	
	}

	public ListIterator<OrmNamedQuery> namedQueries() {
		return new CloneListIterator<OrmNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public OrmNamedQuery addNamedQuery(int index) {
		OrmNamedQuery namedQuery = jpaFactory().buildOrmNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.xmlEntityMappings.getNamedQueries().add(index, OrmFactory.eINSTANCE.createNamedQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(OrmNamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}

	public void removeNamedQuery(int index) {
		OrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.xmlEntityMappings.getNamedQueries().remove(index);
		fireItemRemoved(EntityMappings.NAMED_QUERIES_LIST, index, namedQuery);
	}

	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);		
	}
	
	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<OrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = jpaFactory().buildOrmNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.xmlEntityMappings.getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createNamedNativeQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, EntityMappings.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(OrmNamedNativeQuery namedNativeQuery) {
		removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.xmlEntityMappings.getNamedNativeQueries().remove(index);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, EntityMappings.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedNativeQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, EntityMappings.NAMED_NATIVE_QUERIES_LIST);		
	}

	//TODO what about qualified name?  package + class
	//this needs to be handled both for className and persistentType.getName().
	//moving on for now since I am just trying to get the ui compiled!  just a warning that this isn't good api
	public boolean containsPersistentType(String className) {
		for (OrmPersistentType persistentType : CollectionTools.iterable(ormPersistentTypes())) {
			if (persistentType.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public PersistenceUnitDefaults persistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	public void initialize(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		this.xmlEntityMappings = entityMappings;
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
		this.initializeTableGenerators(entityMappings);
		this.initializeSequenceGenerators(entityMappings);
		this.initializeNamedQueries(entityMappings);
		this.initializeNamedNativeQueries(entityMappings);
	}
	
	protected void initializePersistentTypes(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		this.initializeMappedSuperclasses(entityMappings);
		this.initializeEntities(entityMappings);
		this.initializeEmbeddables(entityMappings);
	}
	
	protected void initializeMappedSuperclasses(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlMappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(mappedSuperclass);
			this.persistentTypes.add(ormPersistentType);
		}	
	}
	
	protected void initializeEntities(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlEntity entity : entityMappings.getEntities()) {
			OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.ENTITY_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(entity);
			this.persistentTypes.add(ormPersistentType);
		}				
	}
	
	protected void initializeEmbeddables(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlEmbeddable embeddable : entityMappings.getEmbeddables()) {
			OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(embeddable);
			this.persistentTypes.add(ormPersistentType);
		}
	}
	
	protected void initializeTableGenerators(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlTableGenerator tableGenerator : entityMappings.getTableGenerators()) {
			this.tableGenerators.add(buildTableGenerator(tableGenerator));
		}
	}
	
	protected void initializeSequenceGenerators(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlSequenceGenerator sequenceGenerator : entityMappings.getSequenceGenerators()) {
			this.sequenceGenerators.add(buildSequenceGenerator(sequenceGenerator));
		}
	}
	
	protected void initializeNamedQueries(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlNamedQuery namedQuery : entityMappings.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		for (XmlNamedNativeQuery namedNativeQuery : entityMappings.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
		}
	}

	public void update(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		this.xmlEntityMappings = entityMappings;
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
		this.updateTableGenerators(entityMappings);
		this.updateSequenceGenerators(entityMappings);
		this.updateNamedQueries(entityMappings);
		this.updateNamedNativeQueries(entityMappings);
	}
	
	protected AccessType specifiedAccess(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		return AccessType.fromXmlResourceModel(entityMappings.getAccess());
	}
	
	protected void updatePersistentTypes(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		ListIterator<OrmPersistentType> ormPersistentTypes = this.ormPersistentTypes();
		this.updateMappedSuperclasses(entityMappings, ormPersistentTypes);
		this.updateEntities(entityMappings, ormPersistentTypes);
		this.updateEmbeddables(entityMappings, ormPersistentTypes);
		
		while (ormPersistentTypes.hasNext()) {
			this.removeOrmPersistentType(ormPersistentTypes.next());
		}		
	}
	
	protected void updateMappedSuperclasses(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		for (XmlMappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(mappedSuperclass);
			}
			else {
				OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(mappedSuperclass);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateEntities(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		for (XmlEntity entity : entityMappings.getEntities()) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(entity);
			}
			else {
				OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.ENTITY_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(entity);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateEmbeddables(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		for (XmlEmbeddable embeddable : entityMappings.getEmbeddables()) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(embeddable);
			}
			else {
				OrmPersistentType ormPersistentType = jpaFactory().buildOrmPersistentType(this, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(embeddable);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateTableGenerators(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		ListIterator<OrmTableGenerator> tableGenerators = tableGenerators();
		ListIterator<XmlTableGenerator> resourceTableGenerators = entityMappings.getTableGenerators().listIterator();
		while (tableGenerators.hasNext()) {
			OrmTableGenerator tableGenerator = tableGenerators.next();
			if (resourceTableGenerators.hasNext()) {
				tableGenerator.update(resourceTableGenerators.next());
			}
			else {
				removeTableGenerator(tableGenerator);
			}
		}
		
		while (resourceTableGenerators.hasNext()) {
			addTableGenerator(tableGeneratorsSize(), buildTableGenerator(resourceTableGenerators.next()));
		}
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator tableGeneratorResource) {
		OrmTableGenerator tableGenerator = jpaFactory().buildOrmTableGenerator(this);
		tableGenerator.initialize(tableGeneratorResource);
		return tableGenerator;
	}

	protected void updateSequenceGenerators(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		ListIterator<OrmSequenceGenerator> sequenceGenerators = sequenceGenerators();
		ListIterator<XmlSequenceGenerator> resourceSequenceGenerators = entityMappings.getSequenceGenerators().listIterator();
		while (sequenceGenerators.hasNext()) {
			OrmSequenceGenerator sequenceGenerator = sequenceGenerators.next();
			if (resourceSequenceGenerators.hasNext()) {
				sequenceGenerator.update(resourceSequenceGenerators.next());
			}
			else {
				removeSequenceGenerator(sequenceGenerator);
			}
		}
		
		while (resourceSequenceGenerators.hasNext()) {
			addSequenceGenerator(sequenceGeneratorsSize(), buildSequenceGenerator(resourceSequenceGenerators.next()));
		}
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator sequenceGeneratorResource) {
		OrmSequenceGenerator sequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		sequenceGenerator.initialize(sequenceGeneratorResource);
		return sequenceGenerator;
	}
	
	protected void updateNamedQueries(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		ListIterator<OrmNamedQuery> namedQueries = namedQueries();
		ListIterator<XmlNamedQuery> resourceNamedQueries = entityMappings.getNamedQueries().listIterator();
		
		while (namedQueries.hasNext()) {
			OrmNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), buildNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery namedQuery) {
		OrmNamedQuery ormNamedQuery = jpaFactory().buildOrmNamedQuery(this);
		ormNamedQuery.initialize(namedQuery);
		return ormNamedQuery;
	}

	protected void updateNamedNativeQueries(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<XmlNamedNativeQuery> resourceNamedNativeQueries = entityMappings.getNamedNativeQueries().listIterator();
		
		while (namedNativeQueries.hasNext()) {
			OrmNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), buildNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery namedQuery) {
		OrmNamedNativeQuery ormNamedNativeQuery =jpaFactory().buildOrmNamedNativeQuery(this);
		ormNamedNativeQuery.initialize(namedQuery);
		return ormNamedNativeQuery;
	}

	
	// *************************************************************************
	
	public JpaStructureNode structureNode(int textOffset) {
		for (OrmPersistentType persistentType: CollectionTools.iterable(ormPersistentTypes())) {
			if (persistentType.contains(textOffset)) {
				return persistentType.structureNode(textOffset);
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (xmlEntityMappings == null) {
			return false;
		}
		return xmlEntityMappings.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return xmlEntityMappings.selectionTextRange();
	}
}
