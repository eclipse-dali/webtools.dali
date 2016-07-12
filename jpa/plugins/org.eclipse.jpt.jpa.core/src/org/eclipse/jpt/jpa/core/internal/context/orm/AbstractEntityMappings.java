/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
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
	extends AbstractOrmXmlContextModel<OrmXml>
	implements EntityMappings2_1
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

	protected final Vector<OrmManagedType> managedTypes = new Vector<>();
	protected final ManagedTypeContainerAdapter managedTypeContainerAdapter = new ManagedTypeContainerAdapter();

	protected final ContextListContainer<OrmSequenceGenerator, XmlSequenceGenerator> sequenceGeneratorContainer;

	protected final ContextListContainer<OrmTableGenerator, XmlTableGenerator> tableGeneratorContainer;

	protected final OrmQueryContainer queryContainer;

	// Lookup of short class name to fully qualified class name for primitives, wrappers, array primitives
	protected static Map<String, String> PRIMITIVE_CLASSES = null;

	protected final Vector<OrmPersistentType> structureChildren = new Vector<>();


	protected AbstractEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent);
		this.xmlEntityMappings = xmlEntityMappings;

		this.description = this.xmlEntityMappings.getDescription();
		this.package_ = this.xmlEntityMappings.getPackage();

		this.specifiedAccess = this.buildSpecifiedAccess();
		this.specifiedCatalog = this.xmlEntityMappings.getCatalog();
		this.specifiedSchema = this.xmlEntityMappings.getSchema();

		this.persistenceUnitMetadata = this.buildPersistenceUnitMetadata();

		this.initializeManagedTypes();

		this.sequenceGeneratorContainer = this.buildSequenceGeneratorContainer();
		this.tableGeneratorContainer = this.buildTableGeneratorContainer();
		this.queryContainer = this.buildQueryContainer();

		this.initializeStructureChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);

		this.setDescription_(this.xmlEntityMappings.getDescription());
		this.setPackage_(this.xmlEntityMappings.getPackage());

		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.setSpecifiedCatalog_(this.xmlEntityMappings.getCatalog());
		this.setSpecifiedSchema_(this.xmlEntityMappings.getSchema());

		this.persistenceUnitMetadata.synchronizeWithResourceModel(monitor);

		this.syncManagedTypes(monitor);

		this.syncSequenceGenerators(monitor);
		this.syncTableGenerators(monitor);

		this.queryContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setDefaultAccess(this.buildDefaultAccess());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultSchema(this.buildDefaultSchema());

		this.persistenceUnitMetadata.update(monitor);

		this.updateModels(this.getManagedTypes(), monitor);

		this.updateModels(this.getSequenceGenerators(), monitor);
		this.updateModels(this.getTableGenerators(), monitor);

		this.queryContainer.update(monitor);
		this.updateStructureChildren();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.getResource().equals(jpaFile.getFile())) {
			rootStructureNodes.add(this);
		} else {
			for (PersistentType persistentType : this.getPersistentTypes()) {
				persistentType.addRootStructureNodesTo(jpaFile, rootStructureNodes);
			}
		}
	}

	// ********** overrides **********

	@Override
	public OrmXml getOrmXml() {
		return this.parent;
	}

	@Override
	public EntityMappings getMappingFileRoot() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<EntityMappings> getStructureType() {
		return EntityMappings.class;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlEntityMappings.getSelectionTextRange();
	}

	/**
	 * For now, we exclude converter types.
	 */
	protected void initializeStructureChildren() {
		CollectionTools.addAll(this.structureChildren, this.getPersistentTypes());
	}

	protected void updateStructureChildren() {
		this.synchronizeCollection(this.getPersistentTypes(), this.structureChildren, STRUCTURE_CHILDREN_COLLECTION);
	}

	public Iterable<OrmPersistentType> getStructureChildren() {
		return IterableTools.cloneLive(this.structureChildren);
	}

	public int getStructureChildrenSize() {
		return this.structureChildren.size();
	}

	public TextRange getFullTextRange() {
		return this.xmlEntityMappings.getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.xmlEntityMappings.containsOffset(textOffset);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode child : this.getStructureChildren()) {
			if (child.containsOffset(textOffset)) {
				return child.getStructureNode(textOffset);
			}
		}
		return this;
	}


	// ********** PersistentType.Parent implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		return this.isXmlMappingMetadataComplete() ? this.specifiedAccess : null;
	}

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getAccess();
	}

	public String getDefaultPersistentTypePackage() {
		return this.package_;
	}

	protected boolean isXmlMappingMetadataComplete() {
		return this.getPersistenceUnit().isXmlMappingMetadataComplete();
	}


	// ********** persistence unit metadata **********

	public OrmPersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.persistenceUnitMetadata;
	}

	protected OrmPersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return this.getContextModelFactory().buildOrmPersistenceUnitMetadata(this);
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
		int sourceIndex = this.managedTypes.indexOf(ormPersistentType);
		this.managedTypes.remove(sourceIndex);
		oldMapping.removeXmlTypeMappingFrom(this.xmlEntityMappings);
		int targetIndex = this.calculateInsertionIndex(ormPersistentType);
		this.managedTypes.add(targetIndex, ormPersistentType);
		newMapping.addXmlTypeMappingTo(this.xmlEntityMappings);

		newMapping.initializeFrom(oldMapping);
		//not sure where else to put this, need to set the access on the resource model
		ormPersistentType.setSpecifiedAccess(savedAccess);
		this.fireItemMoved(MANAGED_TYPES_LIST, targetIndex, sourceIndex);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlEntityMappings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getOrmXml().getValidationTextRange();
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


	// ********** managed types **********

	public ListIterable<OrmManagedType> getManagedTypes() {
		return IterableTools.cloneLive(this.managedTypes);
	}

	public int getManagedTypesSize() {
		return this.managedTypes.size();
	}

	public OrmManagedType getManagedType(String typeName) {
		for (OrmManagedType ormManagedType : this.getManagedTypes()) {
			if (ormManagedType.isFor(typeName)) {
				return ormManagedType;
			}
		}
		return null;
	}

	public boolean containsManagedType(String typeName) {
		return this.getManagedType(typeName) != null;
	}


	// ********** persistent types **********

	public Iterable<OrmPersistentType> getPersistentTypes() {
		return IterableTools.downcast(IterableTools.filter(this.getManagedTypes(), TYPE_IS_PERSISTENT_TYPE));
	}
		
	protected static final Predicate<OrmManagedType> TYPE_IS_PERSISTENT_TYPE = new TypeIsPersistentType();

	public static class TypeIsPersistentType
		extends PredicateAdapter<OrmManagedType>
	{
		@Override
		public boolean evaluate(OrmManagedType mt) {
			return mt.getManagedTypeType() == PersistentType.class;
		}
	}

	public OrmPersistentType getPersistentType(String typeName) {
		ManagedType mt = this.getManagedType(typeName);
		if ((mt != null) && (mt.getManagedTypeType() == PersistentType.class)) {
			return (OrmPersistentType) mt;
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

	public JavaResourceAbstractType resolveJavaResourceType(String className, JavaResourceAnnotatedElement.AstNodeType astNodeType) {
		JavaResourceAbstractType resourceType = this.resolveJavaResourceType(className);
		if (resourceType == null || resourceType.getAstNodeType() != astNodeType) {
			return null;
		}
		return resourceType;
	}

	public IType resolveJdtType(String className) {
		return (IType) this.resolveType(JDT_TYPE_LOOKUP_ADAPTER, className);
	}

	/**
	 * If the specified class name does not include a package, prefix it with
	 * the entity mapping's package.
	 * <br>
	 * If the specified class is a member class, the member class name must be
	 * separated from the declaring class name with a <code>'$'</code>.
	 * The returned class name will have any <code>'$'</code>s replaced with
	 * <code>'.'</code>s.
	 */
	public String qualify(String className) {
		if (StringTools.isBlank(className)) {
			return null;
		}
		String primitiveClassName = getPrimitiveClassName(className);
		if (primitiveClassName != null) {
			return primitiveClassName;
		}

	    // If the global package is not defined or the class name is qualified,
		// simply use the class name. 
		// Otherwise, prepend the global package name.
		if ((this.package_ != null) && (className.indexOf('.') == -1)) {
			className = this.prependGlobalPackage(className);
		}
		// member classes listed in orm.xml will use '$' - replace with '.'
		className = className.replace('$', '.');
		return className;
	}

	protected Object resolveType(TypeLookupAdapter adapter, String className) {
		return (className == null) ? null : adapter.resolveType(this, this.qualify(className));
	}

	/**
	 * Pre-conditions:<ul>
	 * <li>the entity mapping's package is not <code>null</code>
	 * <li>the specified unqualified class name is not qualified;
	 * i.e. it does not contain any <code>'.'</code>s (it <em>may</em>
	 * contain <code>'$'</code>s)
	 * </ul>
	 */
	protected String prependGlobalPackage(String unqualifiedClassName) {
		// Format of global package is "foo.bar"
		return this.package_ + '.' + unqualifiedClassName;
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
				return JavaProjectTools.findType(javaProject, className);
			}
		};

	protected static String getPrimitiveClassName(String className) {
		if (PRIMITIVE_CLASSES == null) {
			PRIMITIVE_CLASSES = new HashMap<>();
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

	public OrmPersistentType addPersistentType(String mappingKey, String className) {
		OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(mappingKey);
		XmlTypeMapping xmlManagedType = md.buildResourceMapping(this.getResourceModelFactory());
		return (OrmPersistentType) this.addManagedType(xmlManagedType, className);
	}

	/**
	 * We have to calculate the new managed type's index.
	 * We will use the type's short name if the entity mappings's
	 * package is the same as the type's package.
	 */
	protected OrmManagedType addManagedType(XmlManagedType xmlManagedType, String className) {
		// adds short name if package name is relevant
		xmlManagedType.setClassName(this.normalizeClassName(className));

		OrmManagedType managedType = this.buildManagedType(xmlManagedType);
		int index = this.calculateInsertionIndex(managedType);
		this.addItemToList(index, managedType, this.managedTypes, MANAGED_TYPES_LIST);
		managedType.addXmlManagedTypeTo(this.xmlEntityMappings);
		return managedType;
	}

	//TODO add API - added this post-M6
	public void addPersistentTypes(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 3);
		if (sm.isCanceled()) {
			return;
		}
		this.addMappedSuperclasses(typeConfigs, sm.newChild(1));
		if (sm.isCanceled()) {
			return;
		}
		this.addEntities(typeConfigs, sm.newChild(1));
		if (sm.isCanceled()) {
			return;
		}
		this.addEmbeddables(typeConfigs, sm.newChild(1));
	}

	protected void addMappedSuperclasses(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptJpaCoreMessages.MAKE_PERSISTENT_PROCESSING_MAPPED_SUPERCLASSES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(typeConfigs, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlMappedSuperclass> mappedSuperclasses = new ArrayList<>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			mappedSuperclasses.add((XmlMappedSuperclass) persistentType.getXmlManagedType());	
		}
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getMappedSuperclasses().addAll(mappedSuperclasses);
		sm.worked(1);
	}

	protected void addEntities(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptJpaCoreMessages.MAKE_PERSISTENT_PROCESSING_ENTITIES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(typeConfigs, MappingKeys.ENTITY_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlEntity> entities = new ArrayList<>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			entities.add((XmlEntity) persistentType.getXmlManagedType());	
		}
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getEntities().addAll(0, entities);
		sm.worked(1);
	}

	protected void addEmbeddables(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 5);
		sm.setTaskName(JptJpaCoreMessages.MAKE_PERSISTENT_PROCESSING_EMBEDDABLES);
		List<OrmPersistentType> addedItems = this.addOrmPersistentTypes(typeConfigs, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, sm.newChild(4));
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return;
		}
		List<XmlEmbeddable> embeddables = new ArrayList<>(addedItems.size());
		for (OrmPersistentType persistentType : addedItems) {
			embeddables.add((XmlEmbeddable) persistentType.getXmlManagedType());	
		}
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL);
		//use addAll to minimize change notifications to our model
		this.xmlEntityMappings.getEmbeddables().addAll(0, embeddables);
		sm.worked(1);
	}

	protected List<OrmPersistentType> addOrmPersistentTypes(PersistentType.Config[] typeConfigs, String mappingKey, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 10);
		List<OrmPersistentType> addedItems = new ArrayList<>();
		for(PersistentType.Config typeConfig : typeConfigs) {
			if (typeConfig.getMappingKey() == mappingKey) {
				String typeName = typeConfig.getName();
				sm.subTask(NLS.bind(JptJpaCoreMessages.MAKE_PERSISTENT_BUILDING_PERSISTENT_TYPE, typeName));
				OrmTypeMappingDefinition md = this.getMappingFileDefinition().getTypeMappingDefinition(typeConfig.getMappingKey());
				XmlTypeMapping xmlTypeMapping = md.buildResourceMapping(this.getResourceModelFactory());

				// adds short name if package name is relevant
				typeName = this.normalizeClassName(typeName);
				xmlTypeMapping.setClassName(typeName);

				addedItems.add((OrmPersistentType) this.buildManagedType(xmlTypeMapping));
			}
		}
		if (addedItems.size() == 0 || sm.isCanceled()) {
			return addedItems;
		}
		sm.worked(1);

		int index = this.calculateInsertionIndex(addedItems.get(0));
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_UPDATING_JPA_MODEL);
		this.addItemsToList(index, addedItems, this.managedTypes, MANAGED_TYPES_LIST);
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

	protected OrmManagedType buildManagedType(XmlManagedType xmlManagedType) {
		OrmManagedTypeDefinition md = this.getMappingFileDefinition().getManagedTypeDefinition(xmlManagedType.getManagedTypeType());
		return md.buildContextManagedType(this, xmlManagedType, this.getContextModelFactory());
	}

	protected int calculateInsertionIndex(OrmManagedType ormManagedType) {
		return ListTools.insertionIndexOf(this.managedTypes, ormManagedType, MAPPING_COMPARATOR);
	}

	protected static final Comparator<OrmManagedType> MAPPING_COMPARATOR =
		new Comparator<OrmManagedType>() {
			public int compare(OrmManagedType o1, OrmManagedType o2) {
				int o1Sequence = o1.getXmlSequence();
				int o2Sequence = o2.getXmlSequence();
				if (o1Sequence < o2Sequence) {
					return -1;
				}
				if (o1Sequence == o2Sequence) {
					return 0;
				}
				return 1;
			}
		};

	public void removeManagedType(int index) {
		OrmManagedType managedType = this.removeManagedType_(index);
		managedType.removeXmlManagedTypeFrom(this.xmlEntityMappings);
	}

	protected OrmManagedType removeManagedType_(int index) {
		return this.removeItemFromList(index, this.managedTypes, MANAGED_TYPES_LIST);
	}

	public void removeManagedType(OrmManagedType managedType) {
		this.removeManagedType(this.managedTypes.indexOf(managedType));
	}

	protected void initializeManagedTypes() {
		for (XmlManagedType xmlManagedType : this.getXmlManagedTypes()) {
			this.managedTypes.add(this.buildManagedType(xmlManagedType));
		}
	}

	protected void syncManagedTypes(IProgressMonitor monitor) {
		ContextContainerTools.synchronizeWithResourceModel(this.managedTypeContainerAdapter, monitor);
	}

	protected Iterable<XmlManagedType> getXmlManagedTypes() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlManagedTypes_());
	}

	// ********** managed types **********

	protected List<XmlManagedType> getXmlManagedTypes_() {
		// convert lists to arrays to *reduce* risk of ConcurrentModificationException
		ArrayList<XmlManagedType> types = new ArrayList<>();
		CollectionTools.addAll(types, this.xmlEntityMappings.getMappedSuperclasses().toArray(EMPTY_XML_MANAGED_TYPE_ARRAY));
		CollectionTools.addAll(types, this.xmlEntityMappings.getEntities().toArray(EMPTY_XML_MANAGED_TYPE_ARRAY));
		CollectionTools.addAll(types, this.xmlEntityMappings.getEmbeddables().toArray(EMPTY_XML_MANAGED_TYPE_ARRAY));
		if (this.isJpa2_1Compatible()) {
			CollectionTools.addAll(types, this.getXml2_1Converters().toArray(EMPTY_XML_MANAGED_TYPE_ARRAY));
		}
		return types;
	}

	protected List<XmlConverter> getXml2_1Converters() {
		return this.xmlEntityMappings.getConverters();
	}

	protected static final XmlManagedType[] EMPTY_XML_MANAGED_TYPE_ARRAY = new XmlManagedType[0];

	protected void moveManagedType_(int index, OrmManagedType managedType) {
		this.moveItemInList(index, managedType, this.managedTypes, MANAGED_TYPES_LIST);
	}

	protected void addManagedType_(int index, XmlManagedType xmlManagedType) {
		this.addItemToList(index, this.buildManagedType(xmlManagedType), this.managedTypes, MANAGED_TYPES_LIST);
	}

	protected void removeManagedType_(OrmManagedType managedType) {
		this.removeManagedType_(this.managedTypes.indexOf(managedType));
	}

	/**
	 * managed type container adapter
	 */
	protected class ManagedTypeContainerAdapter
		implements ContextContainerTools.Adapter<OrmManagedType, XmlManagedType>
	{
		public Iterable<OrmManagedType> getContextElements() {
			return AbstractEntityMappings.this.getManagedTypes();
		}
		public Iterable<XmlManagedType> getResourceElements() {
			return AbstractEntityMappings.this.getXmlManagedTypes();
		}
		public XmlManagedType getResourceElement(OrmManagedType contextElement) {
			return contextElement.getXmlManagedType();
		}
		public void moveContextElement(int index, OrmManagedType element) {
			AbstractEntityMappings.this.moveManagedType_(index, element);
		}
		public void addContextElement(int index, XmlManagedType resourceElement) {
			AbstractEntityMappings.this.addManagedType_(index, resourceElement);
		}
		public void removeContextElement(OrmManagedType element) {
			AbstractEntityMappings.this.removeManagedType_(element);
		}
	}


	// ********** sequence generators **********

	public ListIterable<OrmSequenceGenerator> getSequenceGenerators() {
		return this.sequenceGeneratorContainer;
	}

	public int getSequenceGeneratorsSize() {
		return this.sequenceGeneratorContainer.size();
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
		return this.getContextModelFactory().buildOrmSequenceGenerator(this, xmlSequenceGenerator);
	}

	public void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator) {
		this.removeSequenceGenerator(this.sequenceGeneratorContainer.indexOf(sequenceGenerator));
	}

	public void removeSequenceGenerator(int index) {
		this.sequenceGeneratorContainer.remove(index);
		this.xmlEntityMappings.getSequenceGenerators().remove(index);
	}

	public void moveSequenceGenerator(int targetIndex, int sourceIndex) {
		this.sequenceGeneratorContainer.move(targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncSequenceGenerators(IProgressMonitor monitor) {
		this.sequenceGeneratorContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlSequenceGenerator> getXmlSequenceGenerators() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlEntityMappings.getSequenceGenerators());
	}

	protected ContextListContainer<OrmSequenceGenerator, XmlSequenceGenerator> buildSequenceGeneratorContainer() {
		return this.buildSpecifiedContextListContainer(SEQUENCE_GENERATORS_LIST, new SequenceGeneratorContainerAdapter());
	}

	/**
	 * sequence generator container adapter
	 */
	public class SequenceGeneratorContainerAdapter
		extends AbstractContainerAdapter<OrmSequenceGenerator, XmlSequenceGenerator>
	{
		public OrmSequenceGenerator buildContextElement(XmlSequenceGenerator resourceElement) {
			return AbstractEntityMappings.this.buildSequenceGenerator(resourceElement);
		}
		public ListIterable<XmlSequenceGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlSequenceGenerators();
		}
		public XmlSequenceGenerator extractResourceElement(OrmSequenceGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
	}


	// ********** table generators **********

	public ListIterable<OrmTableGenerator> getTableGenerators() {
		return this.tableGeneratorContainer;
	}

	public int getTableGeneratorsSize() {
		return this.tableGeneratorContainer.size();
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
		return this.getContextModelFactory().buildOrmTableGenerator(this, xmlTableGenerator);
	}

	public void removeTableGenerator(OrmTableGenerator tableGenerator) {
		this.removeTableGenerator(this.tableGeneratorContainer.indexOf(tableGenerator));
	}

	public void removeTableGenerator(int index) {
		this.tableGeneratorContainer.remove(index);
		this.xmlEntityMappings.getTableGenerators().remove(index);
	}

	public void moveTableGenerator(int targetIndex, int sourceIndex) {
		this.tableGeneratorContainer.move(targetIndex, sourceIndex);
		this.xmlEntityMappings.getTableGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncTableGenerators(IProgressMonitor monitor) {
		this.tableGeneratorContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlTableGenerator> getXmlTableGenerators() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlEntityMappings.getTableGenerators());
	}

	protected ContextListContainer<OrmTableGenerator, XmlTableGenerator> buildTableGeneratorContainer() {
		return this.buildSpecifiedContextListContainer(TABLE_GENERATORS_LIST, new TableGeneratorContainerAdapter());
	}

	/**
	 * table generator container adapter
	 */
	public class TableGeneratorContainerAdapter
		extends AbstractContainerAdapter<OrmTableGenerator, XmlTableGenerator>
	{
		public OrmTableGenerator buildContextElement(XmlTableGenerator resourceElement) {
			return AbstractEntityMappings.this.buildTableGenerator(resourceElement);
		}
		public ListIterable<XmlTableGenerator> getResourceElements() {
			return AbstractEntityMappings.this.getXmlTableGenerators();
		}
		public XmlTableGenerator extractResourceElement(OrmTableGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
	}


	// ********** converter types **********

	public Iterable<OrmConverterType2_1> getConverterTypes() {
		return IterableTools.downcast(IterableTools.filter(this.getManagedTypes(), TYPE_IS_CONVERTER_TYPE));
	}

	protected static final Predicate<OrmManagedType> TYPE_IS_CONVERTER_TYPE = new TypeIsConverterType();

	public static class TypeIsConverterType
		extends PredicateAdapter<OrmManagedType>
	{
		@Override
		public boolean evaluate(OrmManagedType mt) {
			return mt.getManagedTypeType() == ConverterType2_1.class;
		}
	}

	public OrmConverterType2_1 getConverterType(String typeName) {
		ManagedType mt = this.getManagedType(typeName);
		if ((mt != null) && (mt.getManagedTypeType() == ConverterType2_1.class)) {
			return (OrmConverterType2_1) mt;
		}
		return null;
	}

	public boolean containsConverterType(String typeName) {
		return this.getConverterType(typeName) != null;
	}

	public OrmConverterType2_1 addConverterType(String className) {
		return (OrmConverterType2_1) this.addManagedType(this.buildXmlConverter(), className);
	}

	protected XmlConverter buildXmlConverter() {
		return EFactoryTools.create(
			this.getResourceModelFactory(), 
			OrmPackage.eINSTANCE.getXmlConverter(), 
			XmlConverter.class);
	}


	// ********** query container **********

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected OrmQueryContainer buildQueryContainer() {
		return this.getContextModelFactory().buildOrmQueryContainer(this, this.xmlEntityMappings);
	}


	// ********** queries/generators **********

	@SuppressWarnings("unchecked")
	public Iterable<Query> getMappingFileQueries() {
		return IterableTools.concatenate(
					this.queryContainer.getQueries(),
					this.getTypeMappingQueries()
				);
	}

	protected Iterable<Query> getTypeMappingQueries() {
		return IterableTools.children(this.getTypeMappings(), TypeMapping.QUERIES_TRANSFORMER);
	}

	@SuppressWarnings("unchecked")
	public Iterable<Generator> getMappingFileGenerators() {
		return IterableTools.concatenate(
					this.getSequenceGenerators(),
					this.getTableGenerators(),
					this.getTypeMappingGenerators()
				);
	}

	protected Iterable<Generator> getTypeMappingGenerators() {
		return IterableTools.children(this.getTypeMappings(), TypeMapping.GENERATORS_TRANSFORMER);
	}

	protected Iterable<OrmTypeMapping> getTypeMappings() {
		return IterableTools.downcast(IterableTools.transform(this.getPersistentTypes(), PersistentType.MAPPING_TRANSFORMER));
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
		for (OrmManagedType managedType : this.getManagedTypes()) {
			this.validateManagedType(managedType, messages, reporter);
		}
	}

	protected void validateVersion(List<IMessage> messages) {
		if (! this.getLatestDocumentVersion().equals(this.xmlEntityMappings.getDocumentVersion())) {
			messages.add(
				ValidationMessageTools.buildValidationMessage(
					this.getResource(),
					this.xmlEntityMappings.getVersionTextRange(),
					JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST
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

	protected void validateManagedType(OrmManagedType managedType, List<IMessage> messages, IReporter reporter) {
		try {
			managedType.validate(messages, reporter);
		} catch (Throwable exception) {
			JptJpaCorePlugin.instance().logError(exception);
		}
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return IterableTools.children(this.getManagedTypes(), new DeleteTypeRefactoringParticipant.DeleteTypeEditsTransformer(type));
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.children(this.getManagedTypes(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(this.getManagedTypes(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				this.createManagedTypeRenamePackageEdits(originalPackage, newName),
				this.createRenamePackageEdit(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createManagedTypeRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(this.getManagedTypes(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createRenamePackageEdit(IPackageFragment originalPackage, String newName) {
		return ObjectTools.equals(this.package_, originalPackage.getElementName()) ?
				new SingleElementIterable<>(this.xmlEntityMappings.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
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
		for (OrmManagedType managedType : this.getManagedTypes()) {
			result = managedType.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
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
