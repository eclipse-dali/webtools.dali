/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
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
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.osgi.util.NLS;
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

	protected final ContextListContainer<OrmSequenceGenerator, XmlSequenceGenerator> sequenceGeneratorContainer;

	protected final ContextListContainer<OrmTableGenerator, XmlTableGenerator> tableGeneratorContainer;

	protected final OrmQueryContainer queryContainer;

	// Lookup of short class name to fully qualified class name for primitives, wrappers, array primitives
	protected static Map<String, String> PRIMITIVE_CLASSES = null;


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
		this.sequenceGeneratorContainer = this.buildSequenceGeneratorContainer();
		this.tableGeneratorContainer = this.buildTableGeneratorContainer();

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

	@Override
	public OrmXml getOrmXml() {
		return this.getParent();
	}

	@Override
	public EntityMappings getMappingFileRoot() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<EntityMappings> getType() {
		return EntityMappings.class;
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
		return this.xmlEntityMappings.getDocumentVersion();
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
		TextRange textRange = this.xmlEntityMappings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getOrmXml().getValidationTextRange();
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
		return AccessType.fromOrmResourceModel(this.xmlEntityMappings.getAccess(), this.getJpaPlatform(), this.getResourceType());
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
		return (PersistentType) this.resolveType(PERSISTENT_TYPE_LOOKUP_ADAPTER, className);
	}

	public JavaResourceAbstractType resolveJavaResourceType(String className) {
		return (JavaResourceAbstractType) this.resolveType(RESOURCE_TYPE_LOOKUP_ADAPTER, className);
	}

	public JavaResourceAbstractType resolveJavaResourceType(String className, JavaResourceAbstractType.Kind kind) {
		JavaResourceAbstractType resourceType = this.resolveJavaResourceType(className);
		if (resourceType == null || resourceType.getKind() != kind) {
			return null;
		}
		return resourceType;
	}

	public IType resolveJdtType(String className) {
		return (IType) this.resolveType(JDT_TYPE_LOOKUP_ADAPTER, className);
	}

	/**
	 * className passed in must have - $ for inner class
	 * className is qualified or not qualified
	 * append package if the name is not qualified
	 */
	public String getFullyQualifiedName(String className) {
		if (StringTools.stringIsEmpty(className)) {
			return null;
		}
		String primitiveClassName = getPrimitiveClassName(className);
		if (primitiveClassName != null) {
			return primitiveClassName;
		}

	    //No global package defined or the class name is qualified, use the className. 
		//Otherwise, prepend the global package name.
		if (this.getPackage() != null && className.indexOf('.') == -1) {
			className = this.prependGlobalPackage(className);
		}
		// static inner class listed in orm.xml will use '$', replace with '.'
		className = className.replace('$', '.');
		return className;
	}

	protected Object resolveType(TypeLookupAdapter adapter, String className) {
		if (className == null) {
			return null;
		}
		return adapter.resolveType(this, this.getFullyQualifiedName(className));
	}

	/**
	 * preconditions: 
	 * 		getPackage() is not null
	 * 		unqualifiedClassName is not qualified (contains no '.')
	 */
	protected String prependGlobalPackage(String unqualifiedClassName) {
		// Format of global package is "foo.bar"
		return this.getPackage() + '.' + unqualifiedClassName;
	}

	protected interface TypeLookupAdapter {
		Object resolveType(EntityMappings entityMappings, String className);
	}

	protected static final TypeLookupAdapter PERSISTENT_TYPE_LOOKUP_ADAPTER =
		new TypeLookupAdapter() {
			public Object resolveType(EntityMappings entityMappings, String className) {
				return entityMappings.getPersistenceUnit().getPersistentType(className);
			}
		};

	protected static final TypeLookupAdapter RESOURCE_TYPE_LOOKUP_ADAPTER =
		new TypeLookupAdapter() {
			public Object resolveType(EntityMappings entityMappings, String className) {
				return entityMappings.getJpaProject().getJavaResourceType(className);
			}
		};

	protected static final TypeLookupAdapter JDT_TYPE_LOOKUP_ADAPTER =
		new TypeLookupAdapter() {
			public Object resolveType(EntityMappings entityMappings, String className) {
				IJavaProject javaProject = entityMappings.getJpaProject().getJavaProject();
				return JDTTools.findType(javaProject, className);
			}
		};

	protected static String getPrimitiveClassName(String className) {
		if (PRIMITIVE_CLASSES == null) {
			PRIMITIVE_CLASSES = new HashMap<String, String>();
			PRIMITIVE_CLASSES.put("Boolean", Boolean.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Byte", Byte.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Character", Character.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Double", Double.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Float", Float.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Integer", Integer.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Long", Long.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Number", Number.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("Short", Short.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("String", String.class.getName()); //$NON-NLS-1$
			PRIMITIVE_CLASSES.put("boolean", "boolean"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("byte", "byte"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("char", "char"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("double", "double"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("float", "float"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("int", "int"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("long", "long"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("short", "short"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("byte[]", "byte[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("char[]", "char[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("boolean[]", "boolean[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("double[]", "double[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("float[]", "float[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("int[]", "int[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("long[]", "long[]"); //$NON-NLS-1$ //$NON-NLS-2$
			PRIMITIVE_CLASSES.put("short[]", "short[]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        return (className == null) ? null : PRIMITIVE_CLASSES.get(className); 
    }

	/**
	 * We have to calculate the new persistent type's index.
	 * We will use the type's short name if the entity mappings's
	 * package is the same as the type's package.
	 */
	public OrmPersistentType addPersistentType(String mappingKey, String className) {
		OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(mappingKey);
		XmlTypeMapping xmlTypeMapping = md.buildResourceMapping(this.getResourceNodeFactory());

		// adds short name if package name is relevant
		className = this.normalizeClassName(className);
		xmlTypeMapping.setClassName(className);

		OrmPersistentType persistentType = this.buildPersistentType(xmlTypeMapping);
		int index = this.calculateInsertionIndex(persistentType);
		this.addItemToList(index, persistentType, this.persistentTypes, PERSISTENT_TYPES_LIST);

		persistentType.getMapping().addXmlTypeMappingTo(this.xmlEntityMappings);

		return persistentType;
	}

	//TODO add API - added this post-M6
	public void addPersistentTypes(AbstractPersistenceUnit.MappedType[] mappedTypes, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 3);
		if (sm.isCanceled()) {
			return;
		}
		this.addMappedSuperclasses(mappedTypes, sm.newChild(1));
		if (sm.isCanceled()) {
			return;
		}
		this.addEntities(mappedTypes, sm.newChild(1));
		if (sm.isCanceled()) {
			return;
		}
		this.addEmbeddables(mappedTypes, sm.newChild(1));
	}

	protected void addMappedSuperclasses(AbstractPersistenceUnit.MappedType[] types, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptCoreMessages.MAKE_PERSISTENT_PROCESSING_MAPPED_SUPERCLASSES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(types, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlMappedSuperclass> mappedSuperclasses = new ArrayList<XmlMappedSuperclass>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			mappedSuperclasses.add((XmlMappedSuperclass) persistentType.getMapping().getXmlTypeMapping());	
		}
		sm.subTask(JptCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getMappedSuperclasses().addAll(mappedSuperclasses);
		sm.worked(1);
	}

	protected void addEntities(AbstractPersistenceUnit.MappedType[] types, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptCoreMessages.MAKE_PERSISTENT_PROCESSING_ENTITIES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(types, MappingKeys.ENTITY_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlEntity> entities = new ArrayList<XmlEntity>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			entities.add((XmlEntity) persistentType.getMapping().getXmlTypeMapping());	
		}
		sm.subTask(JptCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getEntities().addAll(0, entities);
		sm.worked(1);
	}

	protected void addEmbeddables(AbstractPersistenceUnit.MappedType[] types, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptCoreMessages.MAKE_PERSISTENT_PROCESSING_EMBEDDABLES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(types, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlEmbeddable> embeddables = new ArrayList<XmlEmbeddable>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			embeddables.add((XmlEmbeddable) persistentType.getMapping().getXmlTypeMapping());	
		}
		sm.subTask(JptCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getEmbeddables().addAll(0, embeddables);
		sm.worked(1);
	}

	protected List<OrmPersistentType> addOrmPersistentTypes(AbstractPersistenceUnit.MappedType[] types, String mappingKey, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 10);
		List<OrmPersistentType> addedItems = new ArrayList<OrmPersistentType>();
		for(AbstractPersistenceUnit.MappedType type : types) {
			if (type.getMappingKey() == mappingKey) {
				String className = type.getFullyQualifiedName();
				sm.subTask(NLS.bind(JptCoreMessages.MAKE_PERSISTENT_BUILDING_PERSISTENT_TYPE, className));
				OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(type.getMappingKey());
				XmlTypeMapping xmlTypeMapping = md.buildResourceMapping(this.getResourceNodeFactory());

				// adds short name if package name is relevant
				className = this.normalizeClassName(className);
				xmlTypeMapping.setClassName(className);

				addedItems.add(this.buildPersistentType(xmlTypeMapping));
			}
		}
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return addedItems;
		}
		sm.worked(1);

		int index = this.calculateInsertionIndex(addedItems.get(0));
		sm.subTask(JptCoreMessages.MAKE_PERSISTENT_UPDATING_JPA_MODEL);
		this.addItemsToList(index, addedItems, this.persistentTypes, PERSISTENT_TYPES_LIST);
		sm.worked(9);
		return addedItems;
	}

	/**
	 * Shorten the specified class name if it is in the entity mappings's package.
	 */
	protected String normalizeClassName(String className) {
		return ((this.package_ != null) &&
				className.startsWith(this.package_) &&
				(className.charAt(this.package_.length()) == '.') &&
				(className.indexOf('.', this.package_.length() + 1) == -1)) ?
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
		return this.sequenceGeneratorContainer.getContextElements();
	}

	public int getSequenceGeneratorsSize() {
		return this.sequenceGeneratorContainer.getContextElementsSize();
	}

	public OrmSequenceGenerator addSequenceGenerator() {
		return this.addSequenceGenerator(this.getSequenceGeneratorsSize());
	}

	public OrmSequenceGenerator addSequenceGenerator(int index) {
		XmlSequenceGenerator xmlGenerator = this.buildXmlSequenceGenerator();
		OrmSequenceGenerator sequenceGenerator = this.sequenceGeneratorContainer.addContextElement(index, xmlGenerator);
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
		this.removeSequenceGenerator(this.sequenceGeneratorContainer.indexOfContextElement(sequenceGenerator));
	}

	public void removeSequenceGenerator(int index) {
		this.sequenceGeneratorContainer.removeContextElement(index);
		this.xmlEntityMappings.getSequenceGenerators().remove(index);
	}

	public void moveSequenceGenerator(int targetIndex, int sourceIndex) {
		this.sequenceGeneratorContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncSequenceGenerators() {
		this.sequenceGeneratorContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlSequenceGenerator> getXmlSequenceGenerators() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlSequenceGenerator>(this.xmlEntityMappings.getSequenceGenerators());
	}

	protected ContextListContainer<OrmSequenceGenerator, XmlSequenceGenerator> buildSequenceGeneratorContainer() {
		SequenceGeneratorContainer container = new SequenceGeneratorContainer();
		container.initialize();
		return container;
	}

	/**
	 * sequence generator container
	 */
	protected class SequenceGeneratorContainer
		extends ContextListContainer<OrmSequenceGenerator, XmlSequenceGenerator>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SEQUENCE_GENERATORS_LIST;
		}
		@Override
		protected OrmSequenceGenerator buildContextElement(XmlSequenceGenerator resourceElement) {
			return AbstractEntityMappings.this.buildSequenceGenerator(resourceElement);
		}
		@Override
		protected ListIterable<XmlSequenceGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlSequenceGenerators();
		}
		@Override
		protected XmlSequenceGenerator getResourceElement(OrmSequenceGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
	}


	// ********** table generators **********

	public ListIterable<OrmTableGenerator> getTableGenerators() {
		return this.tableGeneratorContainer.getContextElements();
	}

	public int getTableGeneratorsSize() {
		return this.tableGeneratorContainer.getContextElementsSize();
	}

	public OrmTableGenerator addTableGenerator() {
		return this.addTableGenerator(this.getTableGeneratorsSize());
	}

	public OrmTableGenerator addTableGenerator(int index) {
		XmlTableGenerator xmlTableGenerator = this.buildXmlTableGenerator();
		OrmTableGenerator tableGenerator = this.tableGeneratorContainer.addContextElement(index, xmlTableGenerator);
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
		this.removeTableGenerator(this.tableGeneratorContainer.indexOfContextElement(tableGenerator));
	}

	public void removeTableGenerator(int index) {
		this.tableGeneratorContainer.removeContextElement(index);
		this.xmlEntityMappings.getTableGenerators().remove(index);
	}

	public void moveTableGenerator(int targetIndex, int sourceIndex) {
		this.tableGeneratorContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlEntityMappings.getTableGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncTableGenerators() {
		this.tableGeneratorContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlTableGenerator> getXmlTableGenerators() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlTableGenerator>(this.xmlEntityMappings.getTableGenerators());
	}

	protected ContextListContainer<OrmTableGenerator, XmlTableGenerator> buildTableGeneratorContainer() {
		TableGeneratorContainer container = new TableGeneratorContainer();
		container.initialize();
		return container;
	}

	/**
	 * table generator container
	 */
	protected class TableGeneratorContainer
		extends ContextListContainer<OrmTableGenerator, XmlTableGenerator>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return TABLE_GENERATORS_LIST;
		}
		@Override
		protected OrmTableGenerator buildContextElement(XmlTableGenerator resourceElement) {
			return AbstractEntityMappings.this.buildTableGenerator(resourceElement);
		}
		@Override
		protected ListIterable<XmlTableGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlTableGenerators();
		}
		@Override
		protected XmlTableGenerator getResourceElement(OrmTableGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
	}


	// ********** query container **********

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected OrmQueryContainer buildQueryContainer() {
		return this.getContextNodeFactory().buildOrmQueryContainer(this, this.xmlEntityMappings);
	}


	// ********** queries/generators **********

	@SuppressWarnings("unchecked")
	public Iterable<Query> getMappingFileQueries() {
		return new CompositeIterable<Query>(
					this.queryContainer.getQueries(),
					this.getTypeMappingQueries()
				);
	}

	protected Iterable<Query> getTypeMappingQueries() {
		return new CompositeIterable<Query>(this.getTypeMappingQueryLists());
	}

	protected Iterable<Iterable<Query>> getTypeMappingQueryLists() {
		return new TransformationIterable<OrmTypeMapping, Iterable<Query>>(this.getTypeMappings()) {
					@Override
					protected Iterable<Query> transform(OrmTypeMapping typeMapping) {
						return typeMapping.getQueries();
					}
				};
	}

	@SuppressWarnings("unchecked")
	public Iterable<Generator> getMappingFileGenerators() {
		return new CompositeIterable<Generator>(
					this.getSequenceGenerators(),
					this.getTableGenerators(),
					this.getTypeMappingGenerators()
				);
	}

	protected Iterable<Generator> getTypeMappingGenerators() {
		return new CompositeIterable<Generator>(this.getTypeMappingGeneratorLists());
	}

	protected Iterable<Iterable<Generator>> getTypeMappingGeneratorLists() {
		return new TransformationIterable<OrmTypeMapping, Iterable<Generator>>(this.getTypeMappings()) {
					@Override
					protected Iterable<Generator> transform(OrmTypeMapping typeMapping) {
						return typeMapping.getGenerators();
					}
				};
	}

	protected Iterable<OrmTypeMapping> getTypeMappings() {
		return new TransformationIterable<OrmPersistentType, OrmTypeMapping>(this.getPersistentTypes()) {
					@Override
					protected OrmTypeMapping transform(OrmPersistentType persistentType) {
						return persistentType.getMapping();
					}
				};
	}


	// ********** validation **********

	/**
	 * The generators are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#validateGenerators(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateVersion(messages);
		// generators are validated in the persistence unit
		this.queryContainer.validate(messages, reporter);
		for (OrmPersistentType  ormPersistentType : this.getPersistentTypes()) {
			this.validatePersistentType(ormPersistentType, messages, reporter);
		}
	}

	protected void validateVersion(List<IMessage> messages) {
		if (! this.getLatestDocumentVersion().equals(this.xmlEntityMappings.getDocumentVersion())) {
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
		return XmlEntityMappings.CONTENT_TYPE;
	}

	protected void validatePersistentType(OrmPersistentType persistentType, List<IMessage> messages, IReporter reporter) {
		try {
			persistentType.validate(messages, reporter);
		} catch (Throwable exception) {
			JptJpaCorePlugin.instance().logError(exception);
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
	
	// ************ completion proposals**************
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.queryContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmTableGenerator tableGenerator : this.getTableGenerators()) {
			result = tableGenerator.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		for (OrmSequenceGenerator seqGenerator : this.getSequenceGenerators()) {
			result = seqGenerator.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		result = this.persistenceUnitMetadata.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.packageTouches(pos)) {
			return this.getCandidatePackages();
		}
		for (OrmPersistentType persistentType : this.getPersistentTypes()) {
			result = persistentType.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return EmptyIterable.instance();
	}

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.schemaTouches(pos)) {
			return this.getCandidateSchemata();
		}
		if (this.catalogTouches(pos)) {
			return this.getCandidateCatalogs();
		}
		return null;
	}
	
	// ********** content assist: schema

	protected boolean schemaTouches(int pos) {
		return this.getXmlEntityMappings().schemaTouches(pos);
	}

	protected Iterable<String> getCandidateSchemata() {
		SchemaContainer schemaContainer = this.getDbSchemaContainer();
		return (schemaContainer != null) ? schemaContainer.getSortedSchemaIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** content assist: catalog

	protected boolean catalogTouches(int pos) {
		return this.getXmlEntityMappings().catalogTouches(pos);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}
	
	// ********** content assist: package
	
	private boolean packageTouches(int pos) {
		return this.getXmlEntityMappings().packageTouches(pos);
	}
	
	private Iterable<String> getCandidatePackages() {
		return this.getPersistenceUnit().getPackageNames();
	}

}
