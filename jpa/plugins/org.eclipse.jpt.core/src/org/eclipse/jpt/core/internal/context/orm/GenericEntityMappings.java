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
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
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
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericEntityMappings extends AbstractOrmJpaContextNode implements EntityMappings
{
	protected XmlEntityMappings xmlEntityMappings;
	
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
		OrmPersistentType persistentType = getJpaFactory().buildOrmPersistentType(this, mappingKey);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + ".")) {
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
		OrmSequenceGenerator ormSequenceGenerator = getJpaFactory().buildOrmSequenceGenerator(this);
		this.sequenceGenerators.add(index, ormSequenceGenerator);
		XmlSequenceGenerator sequenceGenerator = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		ormSequenceGenerator.initialize(sequenceGenerator);
		this.xmlEntityMappings.getSequenceGenerators().add(index, sequenceGenerator);
		fireItemAdded(SEQUENCE_GENERATORS_LIST, index, ormSequenceGenerator);
		return ormSequenceGenerator;
	}

	protected void addSequenceGenerator(int index, OrmSequenceGenerator sequenceGenerator) {
		addItemToList(index, sequenceGenerator, this.sequenceGenerators, EntityMappings.SEQUENCE_GENERATORS_LIST);
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
		OrmTableGenerator xmlTableGenerator = getJpaFactory().buildOrmTableGenerator(this);
		this.tableGenerators.add(index, xmlTableGenerator);
		XmlTableGenerator tableGenerator = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		xmlTableGenerator.initialize(tableGenerator);
		this.xmlEntityMappings.getTableGenerators().add(index, tableGenerator);
		fireItemAdded(TABLE_GENERATORS_LIST, index, xmlTableGenerator);
		return xmlTableGenerator;
	}
	
	protected void addTableGenerator(int index, OrmTableGenerator tableGenerator) {
		addItemToList(index, tableGenerator, this.tableGenerators, EntityMappings.TABLE_GENERATORS_LIST);
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
		OrmNamedQuery namedQuery = getJpaFactory().buildOrmNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.xmlEntityMappings.getNamedQueries().add(index, OrmFactory.eINSTANCE.createXmlNamedQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, EntityMappings.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(NamedQuery namedQuery) {
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
		OrmNamedNativeQuery namedNativeQuery = getJpaFactory().buildOrmNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.xmlEntityMappings.getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		this.fireItemAdded(EntityMappings.NAMED_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, EntityMappings.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
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

	public PersistenceUnitDefaults getPersistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	protected void initialize(XmlEntityMappings entityMappings) {
		this.xmlEntityMappings = entityMappings;
		this.version = entityMappings.getVersion();
		this.description = entityMappings.getDescription();
		this.package_ = entityMappings.getPackage();
		this.specifiedSchema = entityMappings.getSchema();
		this.specifiedCatalog = entityMappings.getCatalog();
		this.specifiedAccess = this.specifiedAccess(entityMappings);
		this.defaultAccess = getPersistenceUnit().getDefaultAccess();
		this.defaultCatalog = getPersistenceUnit().getDefaultCatalog();
		this.defaultSchema = getPersistenceUnit().getDefaultSchema();
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
		getJpaFile(entityMappings.getResource().getResourceModel()).addRootStructureNode(this.getMappingFileName(), this);
		this.setDescription(entityMappings.getDescription());
		this.setPackage(entityMappings.getPackage());
		this.setSpecifiedSchema(entityMappings.getSchema());
		this.setSpecifiedCatalog(entityMappings.getCatalog());
		this.setSpecifiedAccess(this.specifiedAccess(entityMappings));
		this.persistenceUnitMetadata.update(entityMappings);
		this.setDefaultAccess(getPersistenceUnit().getDefaultAccess());
		this.setDefaultCatalog(getPersistenceUnit().getDefaultCatalog());
		this.setDefaultSchema(getPersistenceUnit().getDefaultSchema());
		this.updatePersistentTypes(entityMappings);
		this.updateTableGenerators(entityMappings);
		this.updateSequenceGenerators(entityMappings);
		this.updateNamedQueries(entityMappings);
		this.updateNamedNativeQueries(entityMappings);
		this.updatePersistenceUnitGeneratorsAndQueries();
	}
	
	protected String getMappingFileName() {
		return getParent().getParent().getFileName();
	}
	
	protected AccessType specifiedAccess(XmlEntityMappings entityMappings) {
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
		ListIterator<OrmTableGenerator> tableGenerators = tableGenerators();
		ListIterator<XmlTableGenerator> resourceTableGenerators = new CloneListIterator<XmlTableGenerator>(entityMappings.getTableGenerators());//prevent ConcurrentModificiationException
		while (tableGenerators.hasNext()) {
			OrmTableGenerator tableGenerator = tableGenerators.next();
			if (resourceTableGenerators.hasNext()) {
				tableGenerator.update(resourceTableGenerators.next());
			}
			else {
				removeTableGenerator_(tableGenerator);
			}
		}
		
		while (resourceTableGenerators.hasNext()) {
			addTableGenerator(tableGeneratorsSize(), buildTableGenerator(resourceTableGenerators.next()));
		}
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator tableGeneratorResource) {
		OrmTableGenerator tableGenerator = getJpaFactory().buildOrmTableGenerator(this);
		tableGenerator.initialize(tableGeneratorResource);
		return tableGenerator;
	}

	protected void updateSequenceGenerators(XmlEntityMappings entityMappings) {
		ListIterator<OrmSequenceGenerator> sequenceGenerators = sequenceGenerators();
		ListIterator<XmlSequenceGenerator> resourceSequenceGenerators = new CloneListIterator<XmlSequenceGenerator>(entityMappings.getSequenceGenerators());//prevent ConcurrentModificiationException
		while (sequenceGenerators.hasNext()) {
			OrmSequenceGenerator sequenceGenerator = sequenceGenerators.next();
			if (resourceSequenceGenerators.hasNext()) {
				sequenceGenerator.update(resourceSequenceGenerators.next());
			}
			else {
				removeSequenceGenerator_(sequenceGenerator);
			}
		}
		
		while (resourceSequenceGenerators.hasNext()) {
			addSequenceGenerator(sequenceGeneratorsSize(), buildSequenceGenerator(resourceSequenceGenerators.next()));
		}
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator sequenceGeneratorResource) {
		OrmSequenceGenerator sequenceGenerator = getJpaFactory().buildOrmSequenceGenerator(this);
		sequenceGenerator.initialize(sequenceGeneratorResource);
		return sequenceGenerator;
	}
	
	protected void updateNamedQueries(XmlEntityMappings entityMappings) {
		ListIterator<OrmNamedQuery> namedQueries = namedQueries();
		ListIterator<XmlNamedQuery> resourceNamedQueries = new CloneListIterator<XmlNamedQuery>(entityMappings.getNamedQueries());//prevent ConcurrentModificiationException
		
		while (namedQueries.hasNext()) {
			OrmNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), buildNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery namedQuery) {
		OrmNamedQuery ormNamedQuery = getJpaFactory().buildOrmNamedQuery(this);
		ormNamedQuery.initialize(namedQuery);
		return ormNamedQuery;
	}

	protected void updateNamedNativeQueries(XmlEntityMappings entityMappings) {
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<XmlNamedNativeQuery> resourceNamedNativeQueries = new CloneListIterator<XmlNamedNativeQuery>(entityMappings.getNamedNativeQueries());//prevent ConcurrentModificiationException
		
		while (namedNativeQueries.hasNext()) {
			OrmNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedNativeQueriesSize(), buildNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery namedQuery) {
		OrmNamedNativeQuery ormNamedNativeQuery =getJpaFactory().buildOrmNamedNativeQuery(this);
		ormNamedNativeQuery.initialize(namedQuery);
		return ormNamedNativeQuery;
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

	
	// *************************************************************************
	
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
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		try {
			addGeneratorMessages(messages);
			addQueryMessages(messages);
		} catch (Throwable exception) {
			JptCorePlugin.log(exception);
		}
		for (OrmPersistentType ormPersistentType : CollectionTools.iterable(this.ormPersistentTypes())) {
			try {
				ormPersistentType.addToMessages(messages);
			} catch (Throwable exception) {
				JptCorePlugin.log(exception);			
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void addGeneratorMessages(List<IMessage> messages) {
		List<Generator> masterList = CollectionTools.list(getPersistenceUnit().allGenerators());
		
		for (Iterator<OrmGenerator> stream = new CompositeIterator<OrmGenerator>(this.tableGenerators(), this.sequenceGenerators()); stream.hasNext() ; ) {
			OrmGenerator current = stream.next();
			masterList.remove(current);
			
			for (Generator each : masterList) {
				if (! each.overrides(current) && each.getName() != null && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.getNameTextRange())
					);
				}
			}
			masterList.add(current);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void addQueryMessages(List<IMessage> messages) {
		List<Query> masterList = CollectionTools.list(getPersistenceUnit().allQueries());
		
		for (Iterator<OrmQuery> stream = new CompositeIterator<OrmQuery>(this.namedQueries(), this.namedNativeQueries()); stream.hasNext() ; ) {
			OrmQuery current = stream.next();
			masterList.remove(current);
			
			for (Query each : masterList) {
				if (! each.overrides(current) && each.getName() != null && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.getNameTextRange())
					);
				}
			}
			masterList.add(current);
		}
	}
	
	public void dispose() {
		if (this.xmlEntityMappings.getResource() != null) {
			//the resource is null if the orm.xml file was deleted
			//rootStructureNodes are cleared in the dispose of JpaFile
			JpaFile jpaFile = getJpaFile(this.xmlEntityMappings.getResource().getResourceModel());
		
			if (jpaFile != null) {
				//yes, this can also be null, seems that sometimes the resource is null and
				//something it is not yet null, but we will have no jpaFile for it after a delete.
				jpaFile.removeRootStructureNode(getMappingFileName());
			}
		}
		//still need to dispose these even in the case of a file being deleted.  
		//JpaFile.dispose() just removes the root structure nodes for this file, not other files (java files)
		for (OrmPersistentType  ormPersistentType : CollectionTools.iterable(ormPersistentTypes())) {
			ormPersistentType.dispose();
		}
	}
}
