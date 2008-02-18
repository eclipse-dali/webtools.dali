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
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.orm.NamedQuery;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.orm.TableGenerator;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class EntityMappingsImpl extends JpaContextNode implements EntityMappings
{
	protected org.eclipse.jpt.core.internal.resource.orm.EntityMappings xmlEntityMappings;
	
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

	protected final List<XmlSequenceGenerator> sequenceGenerators;
	
	protected final List<XmlTableGenerator> tableGenerators;

	protected final List<XmlNamedQuery> namedQueries;

	protected final List<XmlNamedNativeQuery> namedNativeQueries;


	public EntityMappingsImpl(OrmXml parent) {
		super(parent);
		this.persistenceUnitMetadata = jpaFactory().createPersistenceUnitMetadata(this);
		this.persistentTypes = new ArrayList<XmlPersistentType>();
		this.sequenceGenerators = new ArrayList<XmlSequenceGenerator>();
		this.tableGenerators = new ArrayList<XmlTableGenerator>();
		this.namedQueries = new ArrayList<XmlNamedQuery>();
		this.namedNativeQueries = new ArrayList<XmlNamedNativeQuery>();
	}
	
	public String getId() {
		return IOrmStructureNodes.ENTITY_MAPPINGS_ID;
	}
	
	@Override
	public EntityMappings entityMappings() {
		return this;
	}
	
	public XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName) {
		for (XmlPersistentType xmlPersistentType : CollectionTools.iterable(xmlPersistentTypes())) {
			if (xmlPersistentType.isFor(fullyQualifiedTypeName)) {
				return xmlPersistentType;
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

	public ListIterator<XmlPersistentType> xmlPersistentTypes() {
		return new CloneListIterator<XmlPersistentType>(this.persistentTypes);
	}
	
	public int xmlPersistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public XmlPersistentType addXmlPersistentType(String mappingKey, String className) {
		XmlPersistentType persistentType = jpaFactory().createXmlPersistentType(this, mappingKey);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + ".")) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		TypeMapping typeMapping = persistentType.getMapping().addToResourceModel(this.xmlEntityMappings);
		typeMapping.setClassName(className);
		fireItemAdded(PERSISTENT_TYPES_LIST, index, persistentType);
		return persistentType;
	}
	
	protected void addXmlPersistentType(XmlPersistentType xmlPersistentType) { 
		addItemToList(xmlPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
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
		xmlPersistentType.getMapping().removeFromResourceModel(this.xmlEntityMappings);
		fireItemRemoved(PERSISTENT_TYPES_LIST, index, xmlPersistentType);		
	}
	
	public void removeXmlPersistentType(XmlPersistentType xmlPersistentType) {
		removeXmlPersistentType(this.persistentTypes.indexOf(xmlPersistentType));	
	}
	
	protected void removeXmlPersistentType_(XmlPersistentType xmlPersistentType) {
		removeItemFromList(xmlPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	public void changeMapping(XmlPersistentType xmlPersistentType, XmlTypeMapping<? extends TypeMapping> oldMapping, XmlTypeMapping<? extends TypeMapping> newMapping) {
		int sourceIndex = this.persistentTypes.indexOf(xmlPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(this.xmlEntityMappings);
		int targetIndex = insertionIndex(xmlPersistentType);
		this.persistentTypes.add(targetIndex, xmlPersistentType);
		newMapping.addToResourceModel(this.xmlEntityMappings);
		newMapping.initializeFrom(oldMapping);
		//TODO are the source and target correct in this case, or is target off by one???
		fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}
	
	public ListIterator<XmlSequenceGenerator> sequenceGenerators() {
		return new CloneListIterator<XmlSequenceGenerator>(this.sequenceGenerators);
	}
	
	public int sequenceGeneratorsSize() {
		return this.sequenceGenerators.size();
	}
	
	public ISequenceGenerator addSequenceGenerator(int index) {
		XmlSequenceGenerator xmlSequenceGenerator = new XmlSequenceGenerator(this);
		this.sequenceGenerators.add(index, xmlSequenceGenerator);
		SequenceGenerator sequenceGenerator = OrmFactory.eINSTANCE.createSequenceGeneratorImpl();
		xmlSequenceGenerator.initialize(sequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().add(index, sequenceGenerator);
		fireItemAdded(SEQUENCE_GENERATORS_LIST, index, xmlSequenceGenerator);
		return xmlSequenceGenerator;
	}

	protected void addSequenceGenerator(int index, XmlSequenceGenerator sequenceGenerator) {
		addItemToList(index, sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
	}

	public void removeSequenceGenerator(ISequenceGenerator sequenceGenerator) {
		removeSequenceGenerator(this.sequenceGenerators.indexOf(sequenceGenerator));
	}
	
	public void removeSequenceGenerator(int index) {
		XmlSequenceGenerator removedSequenceGenerator = this.sequenceGenerators.remove(index);
		fireItemRemoved(SEQUENCE_GENERATORS_LIST, index, removedSequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().remove(index);
	}
	
	protected void removeSequenceGenerator_(XmlSequenceGenerator sequenceGenerator) {
		removeItemFromList(sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
	}

	public void moveSequenceGenerator(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.sequenceGenerators, targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
		fireItemMoved(EntityMappings.SEQUENCE_GENERATORS_LIST, targetIndex, sourceIndex);	
	}

	public ListIterator<XmlTableGenerator> tableGenerators() {
		return new CloneListIterator<XmlTableGenerator>(this.tableGenerators);
	}

	public int tableGeneratorsSize() {
		return this.tableGenerators.size();
	}
	
	public ITableGenerator addTableGenerator(int index) {
		XmlTableGenerator xmlTableGenerator = new XmlTableGenerator(this);
		this.tableGenerators.add(index, xmlTableGenerator);
		TableGenerator tableGenerator = OrmFactory.eINSTANCE.createTableGeneratorImpl();
		xmlTableGenerator.initialize(tableGenerator);
		this.xmlEntityMappings.getTableGenerators().add(index, tableGenerator);
		fireItemAdded(TABLE_GENERATORS_LIST, index, xmlTableGenerator);
		return xmlTableGenerator;
	}
	
	protected void addTableGenerator(int index, XmlTableGenerator tableGenerator) {
		addItemToList(index, tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
	}

	public void removeTableGenerator(ITableGenerator tableGenerator) {
		removeTableGenerator(this.tableGenerators.indexOf(tableGenerator));
	}

	public void removeTableGenerator(int index) {
		XmlTableGenerator removedTableGenerator = this.tableGenerators.remove(index);
		this.xmlEntityMappings.getTableGenerators().remove(index);
		fireItemRemoved(TABLE_GENERATORS_LIST, index, removedTableGenerator);
	}
	
	protected void removeTableGenerator_(XmlTableGenerator tableGenerator) {
		removeItemFromList(tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
	}

	public void moveTableGenerator(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.tableGenerators, targetIndex, sourceIndex);
		this.xmlEntityMappings.getTableGenerators().move(targetIndex, sourceIndex);
		fireItemMoved(EntityMappings.TABLE_GENERATORS_LIST, targetIndex, sourceIndex);	
	}

	public ListIterator<XmlNamedQuery> namedQueries() {
		return new CloneListIterator<XmlNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public XmlNamedQuery addNamedQuery(int index) {
		XmlNamedQuery namedQuery = new XmlNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.xmlEntityMappings.getNamedQueries().add(index, OrmFactory.eINSTANCE.createNamedQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, XmlNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(XmlNamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}

	public void removeNamedQuery(int index) {
		XmlNamedQuery namedQuery = this.namedQueries.remove(index);
		this.xmlEntityMappings.getNamedQueries().remove(index);
		fireItemRemoved(EntityMappings.NAMED_QUERIES_LIST, index, namedQuery);
	}

	protected void removeNamedQuery_(XmlNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);		
	}
	
	public ListIterator<XmlNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<XmlNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public XmlNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery namedNativeQuery = new XmlNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.xmlEntityMappings.getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createNamedNativeQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, XmlNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, EntityMappings.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(XmlNamedNativeQuery namedNativeQuery) {
		removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		XmlNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.xmlEntityMappings.getNamedNativeQueries().remove(index);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(XmlNamedNativeQuery namedNativeQuery) {
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
		for (XmlPersistentType persistentType : CollectionTools.iterable(xmlPersistentTypes())) {
			if (persistentType.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public PersistenceUnitDefaults persistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	public void initialize(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
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
	
	protected void initializePersistentTypes(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.initializeMappedSuperclasses(entityMappings);
		this.initializeEntities(entityMappings);
		this.initializeEmbeddables(entityMappings);
	}
	
	protected void initializeMappedSuperclasses(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (MappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			xmlPersistentType.initialize(mappedSuperclass);
			this.persistentTypes.add(xmlPersistentType);
		}	
	}
	
	protected void initializeEntities(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (Entity entity : entityMappings.getEntities()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
			xmlPersistentType.initialize(entity);
			this.persistentTypes.add(xmlPersistentType);
		}				
	}
	
	protected void initializeEmbeddables(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (Embeddable embeddable : entityMappings.getEmbeddables()) {
			XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			xmlPersistentType.initialize(embeddable);
			this.persistentTypes.add(xmlPersistentType);
		}
	}
	
	protected void initializeTableGenerators(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (TableGenerator tableGenerator : entityMappings.getTableGenerators()) {
			XmlTableGenerator xmlTableGenerator = new XmlTableGenerator(this);
			xmlTableGenerator.initialize(tableGenerator);
			this.tableGenerators.add(xmlTableGenerator);
		}
	}
	
	protected void initializeSequenceGenerators(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (SequenceGenerator sequenceGenerator : entityMappings.getSequenceGenerators()) {
			XmlSequenceGenerator xmlSequenceGenerator = new XmlSequenceGenerator(this);
			xmlSequenceGenerator.initialize(sequenceGenerator);
			this.sequenceGenerators.add(xmlSequenceGenerator);
		}
	}
	
	protected void initializeNamedQueries(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (NamedQuery namedQuery : entityMappings.getNamedQueries()) {
			this.namedQueries.add(createNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		for (NamedNativeQuery namedNativeQuery : entityMappings.getNamedNativeQueries()) {
			this.namedNativeQueries.add(createNamedNativeQuery(namedNativeQuery));
		}
	}

	public void update(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
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
	
	protected AccessType specifiedAccess(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		return AccessType.fromXmlResourceModel(entityMappings.getAccess());
	}
	
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
				xmlPersistentTypes.next().update(mappedSuperclass);
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
				xmlPersistentType.initialize(mappedSuperclass);
				addXmlPersistentType(xmlPersistentType);
			}
		}
	}
	
	protected void updateEntities(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings, ListIterator<XmlPersistentType> xmlPersistentTypes) {
		for (Entity entity : entityMappings.getEntities()) {
			if (xmlPersistentTypes.hasNext()) {
				xmlPersistentTypes.next().update(entity);
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
				xmlPersistentType.initialize(entity);
				addXmlPersistentType(xmlPersistentType);
			}
		}
	}
	
	protected void updateEmbeddables(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings, ListIterator<XmlPersistentType> xmlPersistentTypes) {
		for (Embeddable embeddable : entityMappings.getEmbeddables()) {
			if (xmlPersistentTypes.hasNext()) {
				xmlPersistentTypes.next().update(embeddable);
			}
			else {
				XmlPersistentType xmlPersistentType = jpaFactory().createXmlPersistentType(this, IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
				xmlPersistentType.initialize(embeddable);
				addXmlPersistentType(xmlPersistentType);
			}
		}
	}
	
	protected void updateTableGenerators(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		ListIterator<XmlTableGenerator> tableGenerators = tableGenerators();
		ListIterator<TableGenerator> resourceTableGenerators = entityMappings.getTableGenerators().listIterator();
		while (tableGenerators.hasNext()) {
			XmlTableGenerator tableGenerator = tableGenerators.next();
			if (resourceTableGenerators.hasNext()) {
				tableGenerator.update(resourceTableGenerators.next());
			}
			else {
				removeTableGenerator(tableGenerator);
			}
		}
		
		while (resourceTableGenerators.hasNext()) {
			addTableGenerator(tableGeneratorsSize(), createTableGenerator(resourceTableGenerators.next()));
		}
	}

	protected XmlTableGenerator createTableGenerator(TableGenerator tableGeneratorResource) {
		XmlTableGenerator tableGenerator = new XmlTableGenerator(this);
		tableGenerator.initialize(tableGeneratorResource);
		return tableGenerator;
	}

	protected void updateSequenceGenerators(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		ListIterator<XmlSequenceGenerator> sequenceGenerators = sequenceGenerators();
		ListIterator<SequenceGenerator> resourceSequenceGenerators = entityMappings.getSequenceGenerators().listIterator();
		while (sequenceGenerators.hasNext()) {
			XmlSequenceGenerator sequenceGenerator = sequenceGenerators.next();
			if (resourceSequenceGenerators.hasNext()) {
				sequenceGenerator.update(resourceSequenceGenerators.next());
			}
			else {
				removeSequenceGenerator(sequenceGenerator);
			}
		}
		
		while (resourceSequenceGenerators.hasNext()) {
			addSequenceGenerator(sequenceGeneratorsSize(), createSequenceGenerator(resourceSequenceGenerators.next()));
		}
	}

	protected XmlSequenceGenerator createSequenceGenerator(SequenceGenerator sequenceGeneratorResource) {
		XmlSequenceGenerator sequenceGenerator = new XmlSequenceGenerator(this);
		sequenceGenerator.initialize(sequenceGeneratorResource);
		return sequenceGenerator;
	}
	
	protected void updateNamedQueries(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		ListIterator<XmlNamedQuery> namedQueries = namedQueries();
		ListIterator<NamedQuery> resourceNamedQueries = entityMappings.getNamedQueries().listIterator();
		
		while (namedQueries.hasNext()) {
			XmlNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected XmlNamedQuery createNamedQuery(NamedQuery namedQuery) {
		XmlNamedQuery xmlNamedQuery = new XmlNamedQuery(this);
		xmlNamedQuery.initialize(namedQuery);
		return xmlNamedQuery;
	}

	protected void updateNamedNativeQueries(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		ListIterator<XmlNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<NamedNativeQuery> resourceNamedNativeQueries = entityMappings.getNamedNativeQueries().listIterator();
		
		while (namedNativeQueries.hasNext()) {
			XmlNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected XmlNamedNativeQuery createNamedNativeQuery(NamedNativeQuery namedQuery) {
		XmlNamedNativeQuery xmlNamedNativeQuery = new XmlNamedNativeQuery(this);
		xmlNamedNativeQuery.initialize(namedQuery);
		return xmlNamedNativeQuery;
	}

	
	// *************************************************************************
	
	public IJpaStructureNode structureNode(int textOffset) {
		for (XmlPersistentType persistentType: CollectionTools.iterable(xmlPersistentTypes())) {
			if (persistentType.containsOffset(textOffset)) {
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
	
	public ITextRange selectionTextRange() {
		return xmlEntityMappings.selectionTextRange();
	}
}
