/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> file
 * <br>
 * <code>entity-mappings</code> element
 */
public abstract class AbstractEntityMappings
	extends AbstractOrmXmlContextNode
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

	protected final PersistenceUnitMetadata persistenceUnitMetadata;

	protected final List<OrmPersistentType> persistentTypes;

	protected final List<OrmSequenceGenerator> sequenceGenerators;
	
	protected final List<OrmTableGenerator> tableGenerators;

	protected final OrmQueryContainer queryContainer;


	protected AbstractEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.xmlEntityMappings = xmlEntityMappings;
		this.persistentTypes = new ArrayList<OrmPersistentType>();
		this.sequenceGenerators = new ArrayList<OrmSequenceGenerator>();
		this.tableGenerators = new ArrayList<OrmTableGenerator>();
		this.queryContainer = this.getXmlContextNodeFactory().buildOrmQueryContainer(this, xmlEntityMappings);

		this.persistenceUnitMetadata = this.buildPersistenceUnitMetadata();
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
	}
	
	protected PersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return this.getXmlContextNodeFactory().buildPersistenceUnitMetadata(this);
	}
	
	protected OrmPersistentType buildPersistentType(XmlTypeMapping resourceMapping) {
		return this.getXmlContextNodeFactory().buildOrmPersistentType(this, resourceMapping);
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
		return OrmStructureNodes.ENTITY_MAPPINGS_ID;
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
		return this.persistenceUnitMetadata.isXmlMappingMetadataComplete();
	}
	
	public boolean isDefaultPersistentTypeMetadataComplete() {
		return this.isXmlMappingMetadataComplete();
	}
	
	
	// **************** EntityMappings impl ************************************
	
	public XmlEntityMappings getXmlEntityMappings() {
		return this.xmlEntityMappings;
	}
	
	public void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping) {
		AccessType savedAccess = ormPersistentType.getSpecifiedAccess();
		ormPersistentType.dispose();
		int sourceIndex = this.persistentTypes.indexOf(ormPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeFromResourceModel(this.xmlEntityMappings);
		int targetIndex = insertionIndex(ormPersistentType);
		this.persistentTypes.add(targetIndex, ormPersistentType);
		newMapping.addToResourceModel(this.xmlEntityMappings);
		
		newMapping.initializeFrom(oldMapping);
		//not sure where else to put this, need to set the access on the resource model
		ormPersistentType.setSpecifiedAccess(savedAccess);
		fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}
	
	public OrmPersistentType getPersistentType(String fullyQualifiedTypeName) {
		for (OrmPersistentType ormPersistentType : this.getPersistentTypes()) {
			if (ormPersistentType.isFor(fullyQualifiedTypeName)) {
				return ormPersistentType;
			}
		}
		return null;
	}

	public boolean containsPersistentType(String fullyQualifiedTypeName) {
		return getPersistentType(fullyQualifiedTypeName) != null;
	}

	public PersistentType resolvePersistentType(String className) {
		if (className == null) {
			return null;
		}

		//static inner class listed in orm.xml will use '$', replace with '.'
		className = className.replace('$', '.');

		// first try to resolve using only the locally specified name...
		PersistentType persistentType = getPersistenceUnit().getPersistentType(className);
		if (persistentType != null) {
			return persistentType;
		}

		// ...then try to resolve by prepending the global package name
		if (getPackage() == null) {
			return null;
		}
		return getPersistenceUnit().getPersistentType(getPackage() + '.' + className);
	}

	public JavaResourcePersistentType resolveJavaResourcePersistentType(String className) {
		if (className == null) {
			return null;
		}

		//static inner class listed in orm.xml will use '$', replace with '.'
		className = className.replace('$', '.');

		// first try to resolve using only the locally specified name...
		JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(className);
		if (jrpt != null) {
			return jrpt;
		}

		// ...then try to resolve by prepending the global package name
		if (getPackage() == null) {
			return null;
		}
		return getJpaProject().getJavaResourcePersistentType(getPackage() + '.' +  className);
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
		this.xmlEntityMappings.setAccess(AccessType.toOrmResourceModel(access));
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

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.getDbCatalog(catalog);
	}


	// ********** schema container **********

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
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
	
	public ListIterable<OrmPersistentType> getPersistentTypes() {
		return new LiveCloneListIterable<OrmPersistentType>(this.persistentTypes);
	}
	
	public int getPersistentTypesSize() {
		return this.persistentTypes.size();
	}
	
	public OrmPersistentType addPersistentType(String mappingKey, String className) {
		OrmTypeMappingDefinition mappingDefinition = 
				getMappingFileDefinition().getOrmTypeMappingDefinition(mappingKey);
		XmlTypeMapping typeMapping = 
				mappingDefinition.buildResourceMapping(getResourceNodeFactory());
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

	protected int insertionIndex(OrmPersistentType ormPersistentType) {
		return CollectionTools.insertionIndexOf(this.persistentTypes, ormPersistentType, MAPPING_COMPARATOR);
	}

	protected static final Comparator<OrmPersistentType> MAPPING_COMPARATOR =
		new Comparator<OrmPersistentType>() {
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

	public ListIterable<OrmSequenceGenerator> getSequenceGenerators() {
		return new LiveCloneListIterable<OrmSequenceGenerator>(this.sequenceGenerators);
	}
	
	public int getSequenceGeneratorsSize() {
		return this.sequenceGenerators.size();
	}
	
	public OrmSequenceGenerator addSequenceGenerator(int index) {
		XmlSequenceGenerator resourceSequenceGenerator = this.buildResourceSequenceGenerator();
		OrmSequenceGenerator contextSequenceGenerator =  this.buildSequenceGenerator(resourceSequenceGenerator);
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
	
	protected XmlSequenceGenerator buildResourceSequenceGenerator() {
		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
	}

	// ********** table generators **********

	public ListIterable<OrmTableGenerator> getTableGenerators() {
		return new LiveCloneListIterable<OrmTableGenerator>(this.tableGenerators);
	}

	public int getTableGeneratorsSize() {
		return this.tableGenerators.size();
	}
	
	public OrmTableGenerator addTableGenerator(int index) {
		XmlTableGenerator resourceTableGenerator = buildResourceTableGenerator();
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
	
	protected XmlTableGenerator buildResourceTableGenerator() {
		return OrmFactory.eINSTANCE.createXmlTableGenerator();
	}


	// ********** named queries **********
	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}


	// ********** misc **********

	public OrmPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return this.persistenceUnitMetadata.getPersistenceUnitDefaults();
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
		getQueryContainer().update();
	}
	
	protected AccessType getResourceAccess() {
		return AccessType.fromOrmResourceModel(this.xmlEntityMappings.getAccess());
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
				fireItemAdded(PERSISTENT_TYPES_LIST, getPersistentTypesSize(), ormPersistentType);
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

		for (OrmTableGenerator contextGenerator : this.getTableGenerators()) {
			if (xmlGenerators.hasNext()) {
				contextGenerator.update(xmlGenerators.next());
			} else {
				this.removeTableGenerator_(contextGenerator);
			}
		}
		
		while (xmlGenerators.hasNext()) {
			addTableGenerator(buildTableGenerator(xmlGenerators.next()));
		}
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getXmlContextNodeFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

	protected void updateSequenceGenerators() {
		// make a copy of the XML sequence generators (to prevent ConcurrentModificationException)
		Iterator<XmlSequenceGenerator> xmlSequenceGenerators = new CloneIterator<XmlSequenceGenerator>(this.xmlEntityMappings.getSequenceGenerators());//prevent ConcurrentModificiationException

		for (OrmSequenceGenerator contextSequenceGenerator : this.getSequenceGenerators()) {
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
		return getXmlContextNodeFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		for (PersistentType persistentType : this.getPersistentTypes()) {
			persistentType.postUpdate();
		}
	}

	// ********** text **********

	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentType persistentType: this.getPersistentTypes()) {
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
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateVersion(messages);
		this.validateGenerators(messages);
		this.queryContainer.validate(messages, reporter);
		for (OrmPersistentType  ormPersistentType : this.getPersistentTypes()) {
			this.validatePersistentType(ormPersistentType, messages, reporter);
		}
	}
	
	protected void validateVersion(List<IMessage> messages) {
		if (! latestDocumentVersion().equals(this.xmlEntityMappings.getVersion())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.LOW_SEVERITY,
						JpaValidationMessages.XML_VERSION_NOT_LATEST,
						this,
						this.xmlEntityMappings.getVersionTextRange()));
		}
	}
	
	/**
	 * Return the latest version of the document supported by the platform
	 */
	protected abstract String latestDocumentVersion();
	
	protected void validateGenerators(List<IMessage> messages) {
		for (OrmGenerator localGenerator : this.getGenerators()) {
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
	protected Iterable<OrmGenerator> getGenerators() {
		return new CompositeIterable<OrmGenerator>(
						this.getTableGenerators(),
						this.getSequenceGenerators()
				);
	}
	

	protected void validatePersistentType(OrmPersistentType persistentType, List<IMessage> messages, IReporter reporter) {
		try {
			persistentType.validate(messages, reporter);
		} catch (Throwable exception) {
			JptCorePlugin.log(exception);			
		}
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(final IType type) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<DeleteEdit>>(getPersistentTypes()) {
				@Override
				protected Iterable<DeleteEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createDeleteTypeEdits(type);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(getPersistentTypes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(getPersistentTypes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createPersistentTypeRenamePackageEdits(originalPackage, newName),
			this.createRenamePackageEdit(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createPersistentTypeRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(getPersistentTypes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createRenamePackageEdit(final IPackageFragment originalPackage, final String newName) {
		if (this.package_ != null && originalPackage.getElementName().equals(this.package_)) {
			return new SingleElementIterable<ReplaceEdit>(this.xmlEntityMappings.createRenamePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}


	// ********** dispose **********
	
	public void dispose() {
		for (OrmPersistentType ormPersistentType : this.getPersistentTypes()) {
			ormPersistentType.dispose();
		}
	}
}
