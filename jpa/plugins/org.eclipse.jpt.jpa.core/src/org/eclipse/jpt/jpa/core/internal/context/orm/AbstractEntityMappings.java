/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFileRoot;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
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

	protected String package_;  // "package" is a Java keyword

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected final OrmPersistenceUnitMetadata persistenceUnitMetadata;

	protected final Vector<OrmPersistentType> persistentTypes = new Vector<OrmPersistentType>();
	protected final PersistentTypeContainerAdapter persistentTypeContainerAdapter = new PersistentTypeContainerAdapter();

	protected final Vector<OrmSequenceGenerator> sequenceGenerators = new Vector<OrmSequenceGenerator>();
	protected final SequenceGeneratorContainerAdapter sequenceGeneratorContainerAdapter = new SequenceGeneratorContainerAdapter();

	protected final Vector<OrmTableGenerator> tableGenerators = new Vector<OrmTableGenerator>();
	protected final TableGeneratorContainerAdapter tableGeneratorContainerAdapter = new TableGeneratorContainerAdapter();

	protected final OrmQueryContainer queryContainer;


	protected AbstractEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.xmlEntityMappings = xmlEntityMappings;

		this.description = this.xmlEntityMappings.getDescription();
		this.package_ = this.xmlEntityMappings.getPackage();

		this.specifiedAccess = this.buildSpecifiedAccess();
		this.specifiedCatalog = this.xmlEntityMappings.getCatalog();
		this.specifiedSchema = this.xmlEntityMappings.getSchema();

		this.persistenceUnitMetadata = this.buildPersistenceUnitMetadata();

		this.initializePersistentTypes();
		this.initializeSequenceGenerators();
		this.initializeTableGenerators();

		this.queryContainer = this.buildQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		this.setDescription_(this.xmlEntityMappings.getDescription());
		this.setPackage_(this.xmlEntityMappings.getPackage());

		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.setSpecifiedCatalog_(this.xmlEntityMappings.getCatalog());
		this.setSpecifiedSchema_(this.xmlEntityMappings.getSchema());

		this.persistenceUnitMetadata.synchronizeWithResourceModel();

		this.syncPersistentTypes();
		this.syncSequenceGenerators();
		this.syncTableGenerators();

		this.queryContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();

		this.setDefaultAccess(this.buildDefaultAccess());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultSchema(this.buildDefaultSchema());

		this.persistenceUnitMetadata.update();

		this.updateNodes(this.getPersistentTypes());
		this.updateNodes(this.getSequenceGenerators());
		this.updateNodes(this.getTableGenerators());

		this.queryContainer.update();
	}


	// ********** overrides **********

	@Override
	public OrmXml getParent() {
		return (OrmXml) super.getParent();
	}

	public OrmXml getOrmXml() {
		return this.getParent();
	}

	@Override
	public MappingFileRoot getMappingFileRoot() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return OrmStructureNodes.ENTITY_MAPPINGS_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (OrmPersistentType persistentType: this.getPersistentTypes()) {
			if (persistentType.contains(textOffset)) {
				return persistentType.getStructureNode(textOffset);
			}
		}
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlEntityMappings.getSelectionTextRange();
	}

	public void dispose() {
		for (OrmPersistentType ormPersistentType : this.getPersistentTypes()) {
			ormPersistentType.dispose();
		}
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		return this.isXmlMappingMetadataComplete() ? this.specifiedAccess : null;
	}

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getAccess();
	}

	public String getDefaultPersistentTypePackage() {
		return this.getPackage();
	}

	protected boolean isXmlMappingMetadataComplete() {
		return this.getPersistenceUnit().isXmlMappingMetadataComplete();
	}


	// ********** persistence unit metadata **********

	public OrmPersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.persistenceUnitMetadata;
	}

	protected OrmPersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return this.getContextNodeFactory().buildOrmPersistenceUnitMetadata(this);
	}


	// ********** misc **********

	public XmlEntityMappings getXmlEntityMappings() {
		return this.xmlEntityMappings;
	}

	public String getVersion() {
		return this.xmlEntityMappings.getVersion();
	}

	public void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping) {
		AccessType savedAccess = ormPersistentType.getSpecifiedAccess();
		int sourceIndex = this.persistentTypes.indexOf(ormPersistentType);
		this.persistentTypes.remove(sourceIndex);
		oldMapping.removeXmlTypeMappingFrom(this.xmlEntityMappings);
		int targetIndex = this.calculateInsertionIndex(ormPersistentType);
		this.persistentTypes.add(targetIndex, ormPersistentType);
		newMapping.addXmlTypeMappingTo(this.xmlEntityMappings);

		newMapping.initializeFrom(oldMapping);
		//not sure where else to put this, need to set the access on the resource model
		ormPersistentType.setSpecifiedAccess(savedAccess);
		this.fireItemMoved(PERSISTENT_TYPES_LIST, targetIndex, sourceIndex);
	}

	public TextRange getValidationTextRange() {
		return null;
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlEntityMappings != null) && this.xmlEntityMappings.containsOffset(textOffset);
	}


	// ********** description **********

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.setDescription_(description);
		this.xmlEntityMappings.setDescription(description);
	}

	protected void setDescription_(String description) {
		String old = this.description;
		this.description = description;
		this.firePropertyChanged(DESCRIPTION_PROPERTY, old, description);
	}


	// ********** package **********

	public String getPackage() {
		return this.package_;
	}

	public void setPackage(String package_) {
		this.setPackage_(package_);
		this.xmlEntityMappings.setPackage(package_);
	}

	/**
	 * TODO If the package changes, we should probably clear out all resolved
	 * references to persistent types:<ul>
	 * <li>{@link OrmIdClassReference#getIdClass()}
	 * <li>{@link OrmPersistentType#getJavaPersistentType()}
	 * </ul>
	 */
	protected void setPackage_(String package_) {
		String old = this.package_;
		this.package_ = package_;
		this.firePropertyChanged(PACKAGE_PROPERTY, old, package_);
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		this.setSpecifiedAccess_(access);
		this.xmlEntityMappings.setAccess(AccessType.toOrmResourceModel(access));
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.xmlEntityMappings.getAccess());
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildDefaultAccess() {
		return this.getPersistenceUnit().getDefaultAccess();
	}


	// ********** catalog **********

	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		this.setSpecifiedCatalog_(catalog);
		this.xmlEntityMappings.setCatalog(catalog);
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
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

	protected String buildDefaultCatalog() {
		return this.getPersistenceUnit().getDefaultCatalog();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}


	// ********** schema **********

	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		this.setSpecifiedSchema_(schema);
		this.xmlEntityMappings.setSchema(schema);
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
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

	protected String buildDefaultSchema() {
		return this.getPersistenceUnit().getDefaultSchema();
	}

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}


	// ********** schema container **********

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}


	// ********** persistent types **********

	public ListIterable<OrmPersistentType> getPersistentTypes() {
		return new LiveCloneListIterable<OrmPersistentType>(this.persistentTypes);
	}

	public int getPersistentTypesSize() {
		return this.persistentTypes.size();
	}

	public OrmPersistentType getPersistentType(String className) {
		for (OrmPersistentType ormPersistentType : this.getPersistentTypes()) {
			if (ormPersistentType.isFor(className)) {
				return ormPersistentType;
			}
		}
		return null;
	}

	public boolean containsPersistentType(String className) {
		return this.getPersistentType(className) != null;
	}

	public PersistentType resolvePersistentType(String className) {
		return (PersistentType) this.resolvePersistentType(PERSISTENT_TYPE_LOOKUP_ADAPTER, className);
	}

	public JavaResourcePersistentType resolveJavaResourcePersistentType(String className) {
		return (JavaResourcePersistentType) this.resolvePersistentType(RESOURCE_PERSISTENT_TYPE_LOOKUP_ADAPTER, className);
	}

	protected Object resolvePersistentType(PersistentTypeLookupAdapter adapter, String className) {
		if (className == null) {
			return null;
		}

		// static inner class listed in orm.xml will use '$', replace with '.'
		className = className.replace('$', '.');

		// first try to resolve using only the locally specified name...
		Object persistentType = adapter.resolvePersistentType(this, className);
		if (persistentType != null) {
			return persistentType;
		}

		// ...then try to resolve by prepending the global package name
		if (this.getPackage() == null) {
			return null;
		}
		return adapter.resolvePersistentType(this, this.getPackage() + '.' + className);
	}

	protected interface PersistentTypeLookupAdapter {
		Object resolvePersistentType(EntityMappings entityMappings, String className);
	}

	protected static final PersistentTypeLookupAdapter PERSISTENT_TYPE_LOOKUP_ADAPTER =
		new PersistentTypeLookupAdapter() {
			public Object resolvePersistentType(EntityMappings entityMappings, String className) {
				return entityMappings.getPersistenceUnit().getPersistentType(className);
			}
		};

	protected static final PersistentTypeLookupAdapter RESOURCE_PERSISTENT_TYPE_LOOKUP_ADAPTER =
		new PersistentTypeLookupAdapter() {
			public Object resolvePersistentType(EntityMappings entityMappings, String className) {
				return entityMappings.getJpaProject().getJavaResourcePersistentType(className);
			}
		};

	/**
	 * We have to calculate the new persistent type's index.
	 * We will use the type's short name if the entity mappings's
	 * package is the same as the type's package.
	 */
	public OrmPersistentType addPersistentType(String mappingKey, String className) {
		OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(mappingKey);
		XmlTypeMapping xmlTypeMapping = md.buildResourceMapping(this.getResourceNodeFactory());
		OrmPersistentType persistentType = this.buildPersistentType(xmlTypeMapping);
		int index = this.calculateInsertionIndex(persistentType);
		this.addItemToList(index, persistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);

		persistentType.getMapping().addXmlTypeMappingTo(this.xmlEntityMappings);

		// adds short name if package name is relevant
		className = this.normalizeClassName(className);
		persistentType.getMapping().setClass(className);

		return persistentType;
	}

	/**
	 * Shorten the specified class name if it is in the entity mappings's package.
	 */
	protected String normalizeClassName(String className) {
		return ((this.package_ != null) &&
				className.startsWith(this.package_) &&
				(className.charAt(this.package_.length()) == '.')) ?
						className.substring(this.package_.length() + 1) :
						className;
	}

	protected OrmPersistentType buildPersistentType(XmlTypeMapping xmlTypeMapping) {
		return this.getContextNodeFactory().buildOrmPersistentType(this, xmlTypeMapping);
	}

	protected int calculateInsertionIndex(OrmPersistentType ormPersistentType) {
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
		OrmPersistentType persistentType = this.removePersistentType_(index);
		persistentType.getMapping().removeXmlTypeMappingFrom(this.xmlEntityMappings);
	}

	/**
	 * dispose and return the persistent type
	 */
	protected OrmPersistentType removePersistentType_(int index) {
		OrmPersistentType persistentType = this.removeItemFromList(index, this.persistentTypes, PERSISTENT_TYPES_LIST);
		persistentType.dispose();
		return persistentType;
	}

	public void removePersistentType(OrmPersistentType persistentType) {
		this.removePersistentType(this.persistentTypes.indexOf(persistentType));
	}

	protected void initializePersistentTypes() {
		for (XmlTypeMapping xmlTypeMapping : this.getXmlTypeMappings()) {
			this.persistentTypes.add(this.buildPersistentType(xmlTypeMapping));
		}
	}

	protected void syncPersistentTypes() {
		ContextContainerTools.synchronizeWithResourceModel(this.persistentTypeContainerAdapter);
	}

	protected Iterable<XmlTypeMapping> getXmlTypeMappings() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlTypeMapping>(this.xmlEntityMappings.getTypeMappings());
	}

	protected void movePersistentType_(int index, OrmPersistentType persistentType) {
		this.moveItemInList(index, persistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	protected void addPersistentType_(int index, XmlTypeMapping xmlTypeMapping) {
		this.addItemToList(index, this.buildPersistentType(xmlTypeMapping), this.persistentTypes, PERSISTENT_TYPES_LIST);
	}

	protected void removePersistentType_(OrmPersistentType persistentType) {
		this.removePersistentType_(this.persistentTypes.indexOf(persistentType));
	}

	/**
	 * persistent type container adapter
	 */
	protected class PersistentTypeContainerAdapter
		implements ContextContainerTools.Adapter<OrmPersistentType, XmlTypeMapping>
	{
		public Iterable<OrmPersistentType> getContextElements() {
			return AbstractEntityMappings.this.getPersistentTypes();
		}
		public Iterable<XmlTypeMapping> getResourceElements() {
			return AbstractEntityMappings.this.getXmlTypeMappings();
		}
		public XmlTypeMapping getResourceElement(OrmPersistentType contextElement) {
			return contextElement.getMapping().getXmlTypeMapping();
		}
		public void moveContextElement(int index, OrmPersistentType element) {
			AbstractEntityMappings.this.movePersistentType_(index, element);
		}
		public void addContextElement(int index, XmlTypeMapping resourceElement) {
			AbstractEntityMappings.this.addPersistentType_(index, resourceElement);
		}
		public void removeContextElement(OrmPersistentType element) {
			AbstractEntityMappings.this.removePersistentType_(element);
		}
	}


	// ********** sequence generators **********

	public ListIterable<OrmSequenceGenerator> getSequenceGenerators() {
		return new LiveCloneListIterable<OrmSequenceGenerator>(this.sequenceGenerators);
	}

	public int getSequenceGeneratorsSize() {
		return this.sequenceGenerators.size();
	}

	public OrmSequenceGenerator addSequenceGenerator() {
		return this.addSequenceGenerator(this.sequenceGenerators.size());
	}

	public OrmSequenceGenerator addSequenceGenerator(int index) {
		XmlSequenceGenerator xmlGenerator = this.buildXmlSequenceGenerator();
		OrmSequenceGenerator sequenceGenerator = this.addSequenceGenerator_(index, xmlGenerator);
		this.xmlEntityMappings.getSequenceGenerators().add(index, xmlGenerator);
		return sequenceGenerator;
	}

	protected XmlSequenceGenerator buildXmlSequenceGenerator() {
		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator xmlSequenceGenerator) {
		return this.getContextNodeFactory().buildOrmSequenceGenerator(this, xmlSequenceGenerator);
	}

	public void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator) {
		this.removeSequenceGenerator(this.sequenceGenerators.indexOf(sequenceGenerator));
	}

	public void removeSequenceGenerator(int index) {
		this.removeSequenceGenerator_(index);
		this.xmlEntityMappings.getSequenceGenerators().remove(index);
	}

	protected void removeSequenceGenerator_(int index) {
		this.removeItemFromList(index, this.sequenceGenerators, SEQUENCE_GENERATORS_LIST);
	}

	public void moveSequenceGenerator(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.sequenceGenerators, SEQUENCE_GENERATORS_LIST);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
	}

	protected void initializeSequenceGenerators() {
		for (XmlSequenceGenerator sequenceGenerator : this.getXmlSequenceGenerators()) {
			this.sequenceGenerators.add(this.buildSequenceGenerator(sequenceGenerator));
		}
	}

	protected void syncSequenceGenerators() {
		ContextContainerTools.synchronizeWithResourceModel(this.sequenceGeneratorContainerAdapter);
	}

	protected Iterable<XmlSequenceGenerator> getXmlSequenceGenerators() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlSequenceGenerator>(this.xmlEntityMappings.getSequenceGenerators());
	}

	protected void moveSequenceGenerator_(int index, OrmSequenceGenerator sequenceGenerator) {
		this.moveItemInList(index, sequenceGenerator, this.sequenceGenerators, SEQUENCE_GENERATORS_LIST);
	}

	protected OrmSequenceGenerator addSequenceGenerator_(int index, XmlSequenceGenerator xmlSequenceGenerator) {
		OrmSequenceGenerator sequenceGenerator = this.buildSequenceGenerator(xmlSequenceGenerator);
		this.addItemToList(index, sequenceGenerator, this.sequenceGenerators, SEQUENCE_GENERATORS_LIST);
		return sequenceGenerator;
	}

	protected void removeSequenceGenerator_(OrmSequenceGenerator sequenceGenerator) {
		this.removeSequenceGenerator_(this.sequenceGenerators.indexOf(sequenceGenerator));
	}

	/**
	 * sequence generator container adapter
	 */
	protected class SequenceGeneratorContainerAdapter
		implements ContextContainerTools.Adapter<OrmSequenceGenerator, XmlSequenceGenerator>
	{
		public Iterable<OrmSequenceGenerator> getContextElements() {
			return AbstractEntityMappings.this.getSequenceGenerators();
		}
		public Iterable<XmlSequenceGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlSequenceGenerators();
		}
		public XmlSequenceGenerator getResourceElement(OrmSequenceGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
		public void moveContextElement(int index, OrmSequenceGenerator element) {
			AbstractEntityMappings.this.moveSequenceGenerator_(index, element);
		}
		public void addContextElement(int index, XmlSequenceGenerator resourceElement) {
			AbstractEntityMappings.this.addSequenceGenerator_(index, resourceElement);
		}
		public void removeContextElement(OrmSequenceGenerator element) {
			AbstractEntityMappings.this.removeSequenceGenerator_(element);
		}
	}


	// ********** table generators **********

	public ListIterable<OrmTableGenerator> getTableGenerators() {
		return new LiveCloneListIterable<OrmTableGenerator>(this.tableGenerators);
	}

	public int getTableGeneratorsSize() {
		return this.tableGenerators.size();
	}

	public OrmTableGenerator addTableGenerator() {
		return this.addTableGenerator(this.tableGenerators.size());
	}

	public OrmTableGenerator addTableGenerator(int index) {
		XmlTableGenerator xmlTableGenerator = this.buildXmlTableGenerator();
		OrmTableGenerator tableGenerator = this.addTableGenerator_(index, xmlTableGenerator);
		this.xmlEntityMappings.getTableGenerators().add(index, xmlTableGenerator);
		return tableGenerator;
	}

	protected XmlTableGenerator buildXmlTableGenerator() {
		return OrmFactory.eINSTANCE.createXmlTableGenerator();
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator xmlTableGenerator) {
		return this.getContextNodeFactory().buildOrmTableGenerator(this, xmlTableGenerator);
	}

	public void removeTableGenerator(OrmTableGenerator tableGenerator) {
		this.removeTableGenerator(this.tableGenerators.indexOf(tableGenerator));
	}

	public void removeTableGenerator(int index) {
		this.removeTableGenerator_(index);
		this.xmlEntityMappings.getTableGenerators().remove(index);
	}

	protected void removeTableGenerator_(int index) {
		this.removeItemFromList(index, this.tableGenerators, TABLE_GENERATORS_LIST);
	}

	public void moveTableGenerator(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.tableGenerators, TABLE_GENERATORS_LIST);
		this.xmlEntityMappings.getTableGenerators().move(targetIndex, sourceIndex);
	}

	protected void initializeTableGenerators() {
		for (XmlTableGenerator tableGenerator : this.getXmlTableGenerators()) {
			this.tableGenerators.add(this.buildTableGenerator(tableGenerator));
		}
	}

	protected void syncTableGenerators() {
		ContextContainerTools.synchronizeWithResourceModel(this.tableGeneratorContainerAdapter);
	}

	protected Iterable<XmlTableGenerator> getXmlTableGenerators() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlTableGenerator>(this.xmlEntityMappings.getTableGenerators());
	}

	protected void moveTableGenerator_(int index, OrmTableGenerator tableGenerator) {
		this.moveItemInList(index, tableGenerator, this.tableGenerators, TABLE_GENERATORS_LIST);
	}

	protected OrmTableGenerator addTableGenerator_(int index, XmlTableGenerator xmlTableGenerator) {
		OrmTableGenerator tableGenerator = this.buildTableGenerator(xmlTableGenerator);
		this.addItemToList(index, tableGenerator, this.tableGenerators, TABLE_GENERATORS_LIST);
		return tableGenerator;
	}

	protected void removeTableGenerator_(OrmTableGenerator tableGenerator) {
		this.removeTableGenerator_(this.tableGenerators.indexOf(tableGenerator));
	}

	/**
	 * table generator container adapter
	 */
	protected class TableGeneratorContainerAdapter
		implements ContextContainerTools.Adapter<OrmTableGenerator, XmlTableGenerator>
	{
		public Iterable<OrmTableGenerator> getContextElements() {
			return AbstractEntityMappings.this.getTableGenerators();
		}
		public Iterable<XmlTableGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlTableGenerators();
		}
		public XmlTableGenerator getResourceElement(OrmTableGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
		public void moveContextElement(int index, OrmTableGenerator element) {
			AbstractEntityMappings.this.moveTableGenerator_(index, element);
		}
		public void addContextElement(int index, XmlTableGenerator resourceElement) {
			AbstractEntityMappings.this.addTableGenerator_(index, resourceElement);
		}
		public void removeContextElement(OrmTableGenerator element) {
			AbstractEntityMappings.this.removeTableGenerator_(element);
		}
	}


	// ********** query container **********

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected OrmQueryContainer buildQueryContainer() {
		return this.getContextNodeFactory().buildOrmQueryContainer(this, this.xmlEntityMappings);
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
		if (! this.getLatestDocumentVersion().equals(this.xmlEntityMappings.getVersion())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.LOW_SEVERITY,
					JpaValidationMessages.XML_VERSION_NOT_LATEST,
					this,
					this.xmlEntityMappings.getVersionTextRange()
				)
			);
		}
	}

	/**
	 * Return the latest version of the document supported by the platform
	 */
	protected String getLatestDocumentVersion() {
		return this.getJpaPlatform().getMostRecentSupportedResourceType(this.getContentType()).getVersion();
	}

	protected IContentType getContentType() {
		return JptJpaCorePlugin.ORM_XML_CONTENT_TYPE;
	}

	protected void validateGenerators(List<IMessage> messages) {
		for (OrmGenerator localGenerator : this.getGenerators()) {
			String name = localGenerator.getName();
			if (StringTools.stringIsEmpty(name)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY, 
								JpaValidationMessages.GENERATOR_NAME_UNDEFINED, 
								new String[] {},
								localGenerator,
								localGenerator.getNameTextRange()
						));
			} else {
				List<String> reportedNames = new ArrayList<String>();
				for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
					if (localGenerator.duplicates(globalGenerators.next()) && !reportedNames.contains(name)) {
						messages.add(
								DefaultJpaValidationMessages.buildMessage(
										IMessage.HIGH_SEVERITY,
										JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
										new String[] {localGenerator.getName()},
										localGenerator,
										localGenerator.getNameTextRange()
								)
						);
						reportedNames.add(name);
					}
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
			JptJpaCorePlugin.log(exception);
		}
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(final IType type) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<DeleteEdit>>(this.getPersistentTypes()) {
				@Override
				protected Iterable<DeleteEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createDeleteTypeEdits(type);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(this.getPersistentTypes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(this.getPersistentTypes()) {
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
			new TransformationIterable<OrmPersistentType, Iterable<ReplaceEdit>>(this.getPersistentTypes()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmPersistentType persistentType) {
					return persistentType.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createRenamePackageEdit(final IPackageFragment originalPackage, final String newName) {
		return Tools.valuesAreEqual(this.package_, originalPackage.getElementName()) ?
				new SingleElementIterable<ReplaceEdit>(this.xmlEntityMappings.createRenamePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}
}
