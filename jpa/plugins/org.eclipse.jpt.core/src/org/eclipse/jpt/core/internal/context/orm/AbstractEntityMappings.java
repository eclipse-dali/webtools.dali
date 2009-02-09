/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractEntityMappings
	extends AbstractXmlContextNode
	implements EntityMappings
{
	protected final XmlEntityMappings xmlEntityMappings;
	
	protected String description;

	protected String package_;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;
		
	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected /*final*/ PersistenceUnitMetadata persistenceUnitMetadata;

	protected final List<OrmPersistentType> persistentTypes;

	protected final List<OrmSequenceGenerator> sequenceGenerators;
	
	protected final List<OrmTableGenerator> tableGenerators;

	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;


	protected AbstractEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.xmlEntityMappings = xmlEntityMappings;
		this.persistentTypes = new ArrayList<OrmPersistentType>();
		this.sequenceGenerators = new ArrayList<OrmSequenceGenerator>();
		this.tableGenerators = new ArrayList<OrmTableGenerator>();
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
		
		this.persistenceUnitMetadata = getJpaFactory().buildPersistenceUnitMetadata(this, this.xmlEntityMappings);
		this.description = this.xmlEntityMappings.getDescription();
		this.package_ = this.xmlEntityMappings.getPackage();

		this.defaultAccess = this.getPersistenceUnit().getDefaultAccess();
		this.specifiedAccess = this.getResourceAccess();

		this.defaultCatalog = this.getPersistenceUnit().getDefaultCatalog();
		this.specifiedCatalog = this.xmlEntityMappings.getCatalog();

		this.defaultSchema = this.getPersistenceUnit().getDefaultSchema();
		this.specifiedSchema = this.xmlEntityMappings.getSchema();

		this.initializePersistentTypes();
		this.initializeTableGenerators();
		this.initializeSequenceGenerators();
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public OrmXml getParent() {
		return (OrmXml) super.getParent();
	}

	protected OrmXml getOrmXml() {
		return this.getParent();
	}
	
	
	// **************** JpaContextNode impl ************************************
	
	@Override
	public MappingFileRoot getMappingFileRoot() {
		return this;
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public String getId() {
		return ENTITY_MAPPINGS_ID;
	}
	
	
	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		return this.isXmlMappingMetadataComplete() ? this.getSpecifiedAccess() : null;
	}

	public AccessType getDefaultPersistentTypeAccess() {
		return getAccess();
	}
	
	public String getDefaultPersistentTypePackage() {
		return getPackage();
	}
	
	protected boolean isXmlMappingMetadataComplete() {
		return this.getPersistenceUnitMetadata().isXmlMappingMetadataComplete();
	}
	
	public boolean isDefaultPersistentTypeMetadataComplete() {
		return this.isXmlMappingMetadataComplete();
	}
	
	
	// **************** EntityMappings impl ************************************
	
	public XmlEntityMappings getXmlEntityMappings() {
		return this.xmlEntityMappings;
	}
	
	public void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping) {
		AccessType specifiedAccess = ormPersistentType.getSpecifiedAccess();
		ormPersistentType.dispose();
		int sourceIndex = this.persistentTypes.indexOf(ormPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(this.xmlEntityMappings);
		int targetIndex = insertionIndex(ormPersistentType);
		this.persistentTypes.add(targetIndex, ormPersistentType);
		newMapping.addToResourceModel(this.xmlEntityMappings);
		
		newMapping.initializeFrom(oldMapping);
		//not sure where else to put this, need to set the access on the resource model
		ormPersistentType.setSpecifiedAccess(specifiedAccess);
		fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		for (OrmPersistentType ormPersistentType : CollectionTools.iterable(persistentTypes())) {
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
		return this.xmlEntityMappings.getVersion();
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


	// ********** ORM persistent types **********

	public ListIterator<OrmPersistentType> persistentTypes() {
		return new CloneListIterator<OrmPersistentType>(this.persistentTypes);
	}
	
	public int persistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public OrmPersistentType addPersistentType(String mappingKey, String className) {
		XmlTypeMapping typeMapping = getJpaPlatform().buildOrmResourceTypeMapping(mappingKey, getContentType());
		OrmPersistentType persistentType = buildPersistentType(typeMapping);
		int index = insertionIndex(persistentType);
		this.persistentTypes.add(index, persistentType);
		if (className.startsWith(getPackage() + '.')) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		persistentType.getMapping().addToResourceModel(this.xmlEntityMappings);
		typeMapping.setClassName(className);
		fireItemAdded(PERSISTENT_TYPES_LIST, index, persistentType);
		return persistentType;
	}
	
	protected abstract OrmPersistentType buildPersistentType(XmlTypeMapping typeMapping);

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

	public void removePersistentType(int index) {
		OrmPersistentType persistentType = this.persistentTypes.get(index);
		persistentType.dispose();
		this.persistentTypes.remove(index);
		persistentType.getMapping().removeFromResourceModel(this.xmlEntityMappings);
		fireItemRemoved(PERSISTENT_TYPES_LIST, index, persistentType);		
	}
	
	public void removePersistentType(OrmPersistentType persistentType) {
		removePersistentType(this.persistentTypes.indexOf(persistentType));	
	}
	
	protected void removePersistentType_(OrmPersistentType persistentType) {
		persistentType.dispose();
		removeItemFromList(persistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}
	
	protected void movePersistentType_(int index, OrmPersistentType persistentType) {
		moveItemInList(index, this.persistentTypes.indexOf(persistentType), this.persistentTypes, PERSISTENT_TYPES_LIST);
	}


	// ********** sequence generators **********

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
	

	// ********** table generators **********

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


	// ********** named queries **********

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
		this.fireItemAdded(NAMED_QUERIES_LIST, index, contextNamedQuery);
		return contextNamedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
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
		fireItemRemoved(NAMED_QUERIES_LIST, index, namedQuery);
	}

	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, NAMED_QUERIES_LIST);		
	}
	

	// ********** named native queries **********

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
		this.fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
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
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.xmlEntityMappings.getNamedNativeQueries().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);		
	}


	// ********** misc **********

	//TODO what about qualified name?  package + class
	//this needs to be handled both for className and persistentType.getName().
	//moving on for now since I am just trying to get the ui compiled!  just a warning that this isn't good api
	public boolean containsPersistentType(String className) {
		for (OrmPersistentType persistentType : CollectionTools.iterable(persistentTypes())) {
			if (persistentType.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}

	public IContentType getContentType() {
		return this.getOrmXml().getContentType();
	}
	

	// ********** initialization **********
	
	protected void initializePersistentTypes() {
		for (XmlTypeMapping typeMapping : this.xmlEntityMappings.getTypeMappings()) {
			addPersistentType(typeMapping);
		}	
	}
	
	protected void initializeTableGenerators() {
		for (XmlTableGenerator tableGenerator : this.xmlEntityMappings.getTableGenerators()) {
			this.tableGenerators.add(buildTableGenerator(tableGenerator));
		}
	}
	
	protected void initializeSequenceGenerators() {
		for (XmlSequenceGenerator sequenceGenerator : this.xmlEntityMappings.getSequenceGenerators()) {
			this.sequenceGenerators.add(buildSequenceGenerator(sequenceGenerator));
		}
	}
	
	protected void initializeNamedQueries() {
		for (XmlNamedQuery namedQuery : this.xmlEntityMappings.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries() {
		for (XmlNamedNativeQuery namedNativeQuery : this.xmlEntityMappings.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
		}
	}


	// ********** update **********

	public void update() {
		this.setDescription(this.xmlEntityMappings.getDescription());
		this.setPackage(this.xmlEntityMappings.getPackage());

		this.setDefaultAccess(this.getPersistenceUnit().getDefaultAccess());
		this.setSpecifiedAccess(this.getResourceAccess());

		this.setDefaultCatalog(this.getPersistenceUnit().getDefaultCatalog());
		this.setSpecifiedCatalog(this.xmlEntityMappings.getCatalog());

		this.setDefaultSchema(this.getPersistenceUnit().getDefaultSchema());
		this.setSpecifiedSchema(this.xmlEntityMappings.getSchema());

		this.persistenceUnitMetadata.update();
		this.updatePersistentTypes();
		this.updateTableGenerators();
		this.updateSequenceGenerators();
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
	}
	
	protected AccessType getResourceAccess() {
		return AccessType.fromXmlResourceModel(this.xmlEntityMappings.getAccess());
	}

	protected void updatePersistentTypes() {
		Collection<OrmPersistentType> contextTypesToRemove = CollectionTools.collection(persistentTypes());
		Collection<OrmPersistentType> contextTypesToUpdate = new ArrayList<OrmPersistentType>();
		int resourceIndex = 0;
		
		List<XmlTypeMapping> xmlTypeMappings = this.xmlEntityMappings.getTypeMappings();
		for (XmlTypeMapping xmlTypeMapping : xmlTypeMappings.toArray(new XmlTypeMapping[xmlTypeMappings.size()])) {
			boolean contextAttributeFound = false;
			for (OrmPersistentType contextType : contextTypesToRemove) {
				if (contextType.getMapping().getResourceTypeMapping() == xmlTypeMapping) {
					movePersistentType_(resourceIndex, contextType);
					contextTypesToRemove.remove(contextType);
					contextTypesToUpdate.add(contextType);
					contextAttributeFound = true;
					break;
				}
			}
			if (!contextAttributeFound) {
				OrmPersistentType ormPersistentType = addPersistentType(xmlTypeMapping);
				fireItemAdded(PERSISTENT_TYPES_LIST, persistentTypesSize(), ormPersistentType);
			}
			resourceIndex++;
		}
		for (OrmPersistentType contextType : contextTypesToRemove) {
			removePersistentType_(contextType);
		}
		//first handle adding/removing of the persistent types, then update the others last, 
		//this causes less churn in the update process
		for (OrmPersistentType contextType : contextTypesToUpdate) {
			contextType.update();
		}	
	}
	
	//not firing change notification so this can be reused in initialize and update
	protected OrmPersistentType addPersistentType(XmlTypeMapping resourceMapping) {
		OrmPersistentType ormPersistentType = buildPersistentType(resourceMapping);
		this.persistentTypes.add(ormPersistentType);
		return ormPersistentType;
	}
	
	protected void updateTableGenerators() {
		// make a copy of the XML generators (to prevent ConcurrentModificationException)
		Iterator<XmlTableGenerator> xmlGenerators = new CloneIterator<XmlTableGenerator>(this.xmlEntityMappings.getTableGenerators());

		for (Iterator<OrmTableGenerator> contextGenerators = this.tableGenerators(); contextGenerators.hasNext(); ) {
			OrmTableGenerator contextGenerator = contextGenerators.next();
			if (xmlGenerators.hasNext()) {
				contextGenerator.update(xmlGenerators.next());
			}
			else {
				removeTableGenerator_(contextGenerator);
			}
		}
		
		while (xmlGenerators.hasNext()) {
			addTableGenerator(buildTableGenerator(xmlGenerators.next()));
		}
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

	protected void updateSequenceGenerators() {
		// make a copy of the XML sequence generators (to prevent ConcurrentModificationException)
		Iterator<XmlSequenceGenerator> xmlSequenceGenerators = new CloneIterator<XmlSequenceGenerator>(this.xmlEntityMappings.getSequenceGenerators());//prevent ConcurrentModificiationException

		for (Iterator<OrmSequenceGenerator> contextSequenceGenerators = this.sequenceGenerators(); contextSequenceGenerators.hasNext(); ) {
			OrmSequenceGenerator contextSequenceGenerator = contextSequenceGenerators.next();
			if (xmlSequenceGenerators.hasNext()) {
				contextSequenceGenerator.update(xmlSequenceGenerators.next());
			}
			else {
				removeSequenceGenerator_(contextSequenceGenerator);
			}
		}
		
		while (xmlSequenceGenerators.hasNext()) {
			addSequenceGenerator(buildSequenceGenerator(xmlSequenceGenerators.next()));
		}
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}
	
	protected void updateNamedQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedQuery> xmlQueries = new CloneIterator<XmlNamedQuery>(this.xmlEntityMappings.getNamedQueries());
		
		for (Iterator<OrmNamedQuery> contextQueries = this.namedQueries(); contextQueries.hasNext(); ) {
			OrmNamedQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedQuery(buildNamedQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery resourceNamedQuery) {
		return getJpaFactory().buildOrmNamedQuery(this, resourceNamedQuery);
	}

	protected void updateNamedNativeQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedNativeQuery> xmlQueries = new CloneIterator<XmlNamedNativeQuery>(this.xmlEntityMappings.getNamedNativeQueries());
		
		for (Iterator<OrmNamedNativeQuery> contextQueries = this.namedNativeQueries(); contextQueries.hasNext(); ) {
			OrmNamedNativeQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedNativeQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery resourceNamedQuery) {
		return getJpaFactory().buildOrmNamedNativeQuery(this, resourceNamedQuery);
	}


	// ********** text **********

	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentType persistentType: CollectionTools.iterable(persistentTypes())) {
			if (persistentType.contains(textOffset)) {
				return persistentType.getStructureNode(textOffset);
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		return (this.xmlEntityMappings != null) && this.xmlEntityMappings.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.xmlEntityMappings.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		return null;
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateGenerators(messages);
		this.validateQueries(messages);
		for (Iterator<OrmPersistentType> stream = this.persistentTypes(); stream.hasNext(); ) {
			this.validatePersistentType(stream.next(), messages);
		}
	}
	
	protected void validateGenerators(List<IMessage> messages) {
		for (Iterator<OrmGenerator> localGenerators = this.generators(); localGenerators.hasNext(); ) {
			OrmGenerator localGenerator = localGenerators.next();
			for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
				if (localGenerator.duplicates(globalGenerators.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {localGenerator.getName()},
							localGenerator,
							localGenerator.getNameTextRange()
						)
					);
				}
			}
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
		for (Iterator<OrmQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			OrmQuery localQuery = localQueries.next();
			for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
				if (localQuery.duplicates(globalQueries.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {localQuery.getName()},
							localQuery,
							localQuery.getNameTextRange()
						)
					);
				}
			}
		}
	}
	
	/**
	 * Return all the queries, named and named native.
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<OrmQuery> queries() {
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
		for (OrmPersistentType  ormPersistentType : CollectionTools.iterable(persistentTypes())) {
			ormPersistentType.dispose();
		}
	}

}
