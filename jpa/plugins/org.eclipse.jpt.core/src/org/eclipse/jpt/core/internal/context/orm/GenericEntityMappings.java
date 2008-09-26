/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHolder;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericEntityMappings
	extends AbstractOrmJpaContextNode
	implements EntityMappings, PersistenceUnit.OrmGeneratorHolder, PersistenceUnit.OrmQueryHolder
{
	protected XmlEntityMappings xmlEntityMappings;
	
	protected String version;
	
	protected String description;

	protected String package_;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;
		
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected final PersistenceUnitMetadata persistenceUnitMetadata;

	protected final List<OrmPersistentType> persistentTypes;

	protected final List<OrmSequenceGenerator> sequenceGenerators;
	
	protected final List<OrmTableGenerator> tableGenerators;

	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;


	public GenericEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.persistenceUnitMetadata = getJpaFactory().buildPersistenceUnitMetadata(this, xmlEntityMappings);
		this.persistentTypes = new ArrayList<OrmPersistentType>();
		this.sequenceGenerators = new ArrayList<OrmSequenceGenerator>();
		this.tableGenerators = new ArrayList<OrmTableGenerator>();
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
		this.initialize(xmlEntityMappings);
	}
	
	public String getId() {
		return OrmStructureNodes.ENTITY_MAPPINGS_ID;
	}
	
	@Override
	public EntityMappings getEntityMappings() {
		return this;
	}
	
	@Override
	public OrmXml getParent() {
		return (OrmXml) super.getParent();
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
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


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.xmlEntityMappings.setAccess(AccessType.toXmlResourceModel(access));
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.xmlEntityMappings.setSchema(schema);
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}


	// ********** catalog **********

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}
	
	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.xmlEntityMappings.setCatalog(catalog);
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		if (catalog == null) {
			return null;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog);
	}


	// ********** schema container **********

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}


	// ********** persistent types **********

	public ListIterator<OrmPersistentType> ormPersistentTypes() {
		return new CloneListIterator<OrmPersistentType>(this.persistentTypes);
	}
	
	public int ormPersistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public OrmPersistentType addOrmPersistentType(String mappingKey, String className) {
		OrmPersistentType persistentType = getJpaFactory().buildOrmPersistentType(this, mappingKey);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + '.')) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		AbstractXmlTypeMapping typeMapping = persistentType.getMapping().addToResourceModel(this.xmlEntityMappings);
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
				int o1Sequence = o1.getMapping().getXmlSequence();
				int o2Sequence = o2.getMapping().getXmlSequence();
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
		ormPersistentType.dispose();
		this.persistentTypes.remove(index);
		ormPersistentType.getMapping().removeFromResourceModel(this.xmlEntityMappings);
		fireItemRemoved(PERSISTENT_TYPES_LIST, index, ormPersistentType);		
	}
	
	public void removeOrmPersistentType(OrmPersistentType ormPersistentType) {
		removeOrmPersistentType(this.persistentTypes.indexOf(ormPersistentType));	
	}
	
	protected void removeOrmPersistentType_(OrmPersistentType ormPersistentType) {
		ormPersistentType.dispose();
		removeItemFromList(ormPersistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	public void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping) {
		ormPersistentType.dispose();
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
	
	public OrmSequenceGenerator addSequenceGenerator(int index) {
		XmlSequenceGenerator resourceSequenceGenerator = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		OrmSequenceGenerator contextSequenceGenerator =  buildSequenceGenerator(resourceSequenceGenerator);
		this.sequenceGenerators.add(index, contextSequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().add(index, resourceSequenceGenerator);
		fireItemAdded(SEQUENCE_GENERATORS_LIST, index, contextSequenceGenerator);
		return contextSequenceGenerator;
	}

	protected void addSequenceGenerator(int index, OrmSequenceGenerator sequenceGenerator) {
		addItemToList(index, sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
	}

	protected void addSequenceGenerator(OrmSequenceGenerator sequenceGenerator) {
		this.addSequenceGenerator(this.sequenceGenerators.size(), sequenceGenerator);
	}

	public void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator) {
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
	
	public OrmTableGenerator addTableGenerator(int index) {
		XmlTableGenerator resourceTableGenerator = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		OrmTableGenerator contextTableGenerator = buildTableGenerator(resourceTableGenerator);
		this.tableGenerators.add(index, contextTableGenerator);
		this.xmlEntityMappings.getTableGenerators().add(index, resourceTableGenerator);
		fireItemAdded(TABLE_GENERATORS_LIST, index, contextTableGenerator);
		return contextTableGenerator;
	}
	
	protected void addTableGenerator(int index, OrmTableGenerator tableGenerator) {
		addItemToList(index, tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
	}

	protected void addTableGenerator(OrmTableGenerator tableGenerator) {
		this.addTableGenerator(this.tableGenerators.size(), tableGenerator);
	}

	public void removeTableGenerator(OrmTableGenerator tableGenerator) {
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
		XmlNamedQuery resourceNamedQuery = OrmFactory.eINSTANCE.createXmlNamedQuery();
		OrmNamedQuery contextNamedQuery = buildNamedQuery(resourceNamedQuery);
		this.namedQueries.add(index, contextNamedQuery);
		this.xmlEntityMappings.getNamedQueries().add(index, resourceNamedQuery);
		this.fireItemAdded(QueryHolder.NAMED_QUERIES_LIST, index, contextNamedQuery);
		return contextNamedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, QueryHolder.NAMED_QUERIES_LIST);
	}
	
	protected void addNamedQuery(OrmNamedQuery namedQuery) {
		this.addNamedQuery(this.namedQueries.size(), namedQuery);
	}
	
	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}

	public void removeNamedQuery(int index) {
		OrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.xmlEntityMappings.getNamedQueries().remove(index);
		fireItemRemoved(QueryHolder.NAMED_QUERIES_LIST, index, namedQuery);
	}

	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, QueryHolder.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, QueryHolder.NAMED_QUERIES_LIST);		
	}
	
	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<OrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery resourceNamedNativeQuery = OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
		OrmNamedNativeQuery namedNativeQuery = buildNamedNativeQuery(resourceNamedNativeQuery);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.xmlEntityMappings.getNamedNativeQueries().add(index, resourceNamedNativeQuery);
		this.fireItemAdded(QueryHolder.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, QueryHolder.NAMED_NATIVE_QUERIES_LIST);
	}
	
	protected void addNamedNativeQuery(OrmNamedNativeQuery namedNativeQuery) {
		this.addNamedNativeQuery(this.namedNativeQueries.size(), namedNativeQuery);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.xmlEntityMappings.getNamedNativeQueries().remove(index);
		fireItemRemoved(QueryHolder.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, QueryHolder.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedNativeQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, QueryHolder.NAMED_NATIVE_QUERIES_LIST);		
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

	public PersistenceUnitDefaults getPersistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	protected void initialize(XmlEntityMappings entityMappings) {
		this.xmlEntityMappings = entityMappings;
		this.version = entityMappings.getVersion();
		this.description = entityMappings.getDescription();
		this.package_ = entityMappings.getPackage();

		this.defaultAccess = this.getPersistenceUnit().getDefaultAccess();
		this.specifiedAccess = this.buildSpecifiedAccess(entityMappings);

		this.defaultCatalog = this.getPersistenceUnit().getDefaultCatalog();
		this.specifiedCatalog = entityMappings.getCatalog();

		this.defaultSchema = this.getPersistenceUnit().getDefaultSchema();
		this.specifiedSchema = entityMappings.getSchema();

		this.initializePersistentTypes(entityMappings);
		this.initializeTableGenerators(entityMappings);
		this.initializeSequenceGenerators(entityMappings);
		this.initializeNamedQueries(entityMappings);
		this.initializeNamedNativeQueries(entityMappings);
		this.updatePersistenceUnitGeneratorsAndQueries();
	}
	
	protected void initializePersistentTypes(XmlEntityMappings entityMappings) {
		this.initializeMappedSuperclasses(entityMappings);
		this.initializeEntities(entityMappings);
		this.initializeEmbeddables(entityMappings);
	}
	
	protected void initializeMappedSuperclasses(XmlEntityMappings entityMappings) {
		for (XmlMappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclasses()) {
			OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(mappedSuperclass);
			this.persistentTypes.add(ormPersistentType);
		}	
	}
	
	protected void initializeEntities(XmlEntityMappings entityMappings) {
		for (XmlEntity entity : entityMappings.getEntities()) {
			OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.ENTITY_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(entity);
			this.persistentTypes.add(ormPersistentType);
		}				
	}
	
	protected void initializeEmbeddables(XmlEntityMappings entityMappings) {
		for (XmlEmbeddable embeddable : entityMappings.getEmbeddables()) {
			OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			ormPersistentType.initialize(embeddable);
			this.persistentTypes.add(ormPersistentType);
		}
	}
	
	protected void initializeTableGenerators(XmlEntityMappings entityMappings) {
		for (XmlTableGenerator tableGenerator : entityMappings.getTableGenerators()) {
			this.tableGenerators.add(buildTableGenerator(tableGenerator));
		}
	}
	
	protected void initializeSequenceGenerators(XmlEntityMappings entityMappings) {
		for (XmlSequenceGenerator sequenceGenerator : entityMappings.getSequenceGenerators()) {
			this.sequenceGenerators.add(buildSequenceGenerator(sequenceGenerator));
		}
	}
	
	protected void initializeNamedQueries(XmlEntityMappings entityMappings) {
		for (XmlNamedQuery namedQuery : entityMappings.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(XmlEntityMappings entityMappings) {
		for (XmlNamedNativeQuery namedNativeQuery : entityMappings.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
		}
	}

	public void update(XmlEntityMappings entityMappings) {
		this.xmlEntityMappings = entityMappings;
		this.setDescription(entityMappings.getDescription());
		this.setPackage(entityMappings.getPackage());

		this.setDefaultAccess(this.getPersistenceUnit().getDefaultAccess());
		this.setSpecifiedAccess(this.buildSpecifiedAccess(entityMappings));

		this.setDefaultCatalog(this.getPersistenceUnit().getDefaultCatalog());
		this.setSpecifiedCatalog(entityMappings.getCatalog());

		this.setDefaultSchema(this.getPersistenceUnit().getDefaultSchema());
		this.setSpecifiedSchema(entityMappings.getSchema());

		this.persistenceUnitMetadata.update(entityMappings);
		this.updatePersistentTypes(entityMappings);
		this.updateTableGenerators(entityMappings);
		this.updateSequenceGenerators(entityMappings);
		this.updateNamedQueries(entityMappings);
		this.updateNamedNativeQueries(entityMappings);
		this.updatePersistenceUnitGeneratorsAndQueries();
	}
	
	protected AccessType buildSpecifiedAccess(XmlEntityMappings entityMappings) {
		return AccessType.fromXmlResourceModel(entityMappings.getAccess());
	}
	
	protected void updatePersistentTypes(XmlEntityMappings entityMappings) {
		ListIterator<OrmPersistentType> ormPersistentTypes = this.ormPersistentTypes();
		this.updateMappedSuperclasses(entityMappings, ormPersistentTypes);
		this.updateEntities(entityMappings, ormPersistentTypes);
		this.updateEmbeddables(entityMappings, ormPersistentTypes);
		
		while (ormPersistentTypes.hasNext()) {
			this.removeOrmPersistentType_(ormPersistentTypes.next());
		}		
	}
	
	protected void updateMappedSuperclasses(XmlEntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		ListIterator<XmlMappedSuperclass> mappedSuperclasses = new CloneListIterator<XmlMappedSuperclass>(entityMappings.getMappedSuperclasses());//prevent ConcurrentModificiationException
		for (XmlMappedSuperclass mappedSuperclass :  CollectionTools.iterable(mappedSuperclasses)) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(mappedSuperclass);
			}
			else {
				OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(mappedSuperclass);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateEntities(XmlEntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		ListIterator<XmlEntity> entities = new CloneListIterator<XmlEntity>(entityMappings.getEntities());//prevent ConcurrentModificiationException
		for (XmlEntity entity : CollectionTools.iterable(entities)) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(entity);
			}
			else {
				OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.ENTITY_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(entity);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateEmbeddables(XmlEntityMappings entityMappings, ListIterator<OrmPersistentType> ormPersistentTypes) {
		ListIterator<XmlEmbeddable> embeddables = new CloneListIterator<XmlEmbeddable>(entityMappings.getEmbeddables());//prevent ConcurrentModificiationException
		for (XmlEmbeddable embeddable : CollectionTools.iterable(embeddables)) {
			if (ormPersistentTypes.hasNext()) {
				ormPersistentTypes.next().update(embeddable);
			}
			else {
				OrmPersistentType ormPersistentType = getJpaFactory().buildOrmPersistentType(this, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
				ormPersistentType.initialize(embeddable);
				addOrmPersistentType(ormPersistentType);
			}
		}
	}
	
	protected void updateTableGenerators(XmlEntityMappings entityMappings) {
		ListIterator<OrmTableGenerator> contextTableGenerators = tableGenerators();
		ListIterator<XmlTableGenerator> resourceTableGenerators = new CloneListIterator<XmlTableGenerator>(entityMappings.getTableGenerators());//prevent ConcurrentModificiationException
		while (contextTableGenerators.hasNext()) {
			OrmTableGenerator contextTableGenerator = contextTableGenerators.next();
			if (resourceTableGenerators.hasNext()) {
				contextTableGenerator.update(resourceTableGenerators.next());
			}
			else {
				removeTableGenerator_(contextTableGenerator);
			}
		}
		
		while (resourceTableGenerators.hasNext()) {
			addTableGenerator(buildTableGenerator(resourceTableGenerators.next()));
		}
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

	protected void updateSequenceGenerators(XmlEntityMappings entityMappings) {
		ListIterator<OrmSequenceGenerator> contextSequenceGenerators = sequenceGenerators();
		ListIterator<XmlSequenceGenerator> resourceSequenceGenerators = new CloneListIterator<XmlSequenceGenerator>(entityMappings.getSequenceGenerators());//prevent ConcurrentModificiationException
		while (contextSequenceGenerators.hasNext()) {
			OrmSequenceGenerator contextSequenceGenerator = contextSequenceGenerators.next();
			if (resourceSequenceGenerators.hasNext()) {
				contextSequenceGenerator.update(resourceSequenceGenerators.next());
			}
			else {
				removeSequenceGenerator_(contextSequenceGenerator);
			}
		}
		
		while (resourceSequenceGenerators.hasNext()) {
			addSequenceGenerator(buildSequenceGenerator(resourceSequenceGenerators.next()));
		}
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}
	
	protected void updateNamedQueries(XmlEntityMappings entityMappings) {
		ListIterator<OrmNamedQuery> contextNamedQueries = namedQueries();
		ListIterator<XmlNamedQuery> resourceNamedQueries = new CloneListIterator<XmlNamedQuery>(entityMappings.getNamedQueries());//prevent ConcurrentModificiationException
		
		while (contextNamedQueries.hasNext()) {
			OrmNamedQuery contextNamedQuery = contextNamedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				contextNamedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(contextNamedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(buildNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery resourceNamedQuery) {
		return getJpaFactory().buildOrmNamedQuery(this, resourceNamedQuery);
	}

	protected void updateNamedNativeQueries(XmlEntityMappings entityMappings) {
		ListIterator<OrmNamedNativeQuery> contextQueries = namedNativeQueries();
		ListIterator<XmlNamedNativeQuery> resourceQueries = new CloneListIterator<XmlNamedNativeQuery>(entityMappings.getNamedNativeQueries());//prevent ConcurrentModificiationException
		
		while (contextQueries.hasNext()) {
			OrmNamedNativeQuery namedQuery = contextQueries.next();
			if (resourceQueries.hasNext()) {
				namedQuery.update(resourceQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery(resourceQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery resourceNamedQuery) {
		return getJpaFactory().buildOrmNamedNativeQuery(this, resourceNamedQuery);
	}
	
	protected void updatePersistenceUnitGeneratorsAndQueries() {
		for (Generator generator : CollectionTools.iterable(tableGenerators())) {
			getPersistenceUnit().addGenerator(generator);
		}
		
		for (Generator generator : CollectionTools.iterable(sequenceGenerators())) {
			getPersistenceUnit().addGenerator(generator);
		}
		
		for (Query query : CollectionTools.iterable(namedQueries())) {
			getPersistenceUnit().addQuery(query);
		}
		
		for (Query query : CollectionTools.iterable(namedNativeQueries())) {
			getPersistenceUnit().addQuery(query);
		}
	}


	// ********** validation **********

	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentType persistentType: CollectionTools.iterable(ormPersistentTypes())) {
			if (persistentType.contains(textOffset)) {
				return persistentType.getStructureNode(textOffset);
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (this.xmlEntityMappings == null) {
			return false;
		}
		return this.xmlEntityMappings.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.xmlEntityMappings.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		return null;
	}
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateGenerators(messages);
		this.validateQueries(messages);
		for (Iterator<OrmPersistentType> stream = this.ormPersistentTypes(); stream.hasNext(); ) {
			this.validatePersistentType(stream.next(), messages);
		}
	}

	protected void validateGenerators(List<IMessage> messages) {
		try {
			this.getPersistenceUnit().validateGenerators(this, messages);
		} catch (Throwable exception) {
			JptCorePlugin.log(exception);  // unlikely...
		}
	}

	/**
	 * Return all the generators, table and sequence.
	 */
	@SuppressWarnings("unchecked")
	public Iterator<OrmGenerator> generators() {
		return new CompositeIterator<OrmGenerator>(
						this.tableGenerators(),
						this.sequenceGenerators()
				);
	}

	protected void validateQueries(List<IMessage> messages) {
		try {
			this.getPersistenceUnit().validateQueries(this, messages);
		} catch (Throwable exception) {
			JptCorePlugin.log(exception);  // unlikely...
		}
	}

	/**
	 * Return all the queries, named and named native.
	 */
	@SuppressWarnings("unchecked")
	public Iterator<OrmQuery> queries() {
		return new CompositeIterator<OrmQuery>(
						this.namedQueries(),
						this.namedNativeQueries()
				);
	}

	protected void validatePersistentType(OrmPersistentType persistentType, List<IMessage> messages) {
		try {
			persistentType.validate(messages);
		} catch (Throwable exception) {
			JptCorePlugin.log(exception);			
		}
	}


	// ********** dispose **********

	public void dispose() {
		for (OrmPersistentType  ormPersistentType : CollectionTools.iterable(ormPersistentTypes())) {
			ormPersistentType.dispose();
		}
	}
}
