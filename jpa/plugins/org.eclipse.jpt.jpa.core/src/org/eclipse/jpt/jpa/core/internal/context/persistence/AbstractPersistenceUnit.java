/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.ManagedTypeContainer;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentTypeContainer;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.connection.NullConnection2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.options.NullOptions2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.schemagen.NullGenericSchemaGeneration2_1;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingFilePersistenceUnitDefaults2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.Options2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceUnit2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>persistence-unit</code> element
 */
public abstract class AbstractPersistenceUnit
	extends AbstractPersistenceXmlContextModel<Persistence>
	implements PersistenceUnit2_1
{
	protected final XmlPersistenceUnit xmlPersistenceUnit;

	protected String name;

	protected Boolean specifiedExcludeUnlistedClasses;

	protected PersistenceUnitTransactionType specifiedTransactionType;
	protected PersistenceUnitTransactionType defaultTransactionType = PersistenceUnitTransactionType.JTA;

	protected String description;

	protected String provider;

	protected String jtaDataSource;
	protected String nonJtaDataSource;

	/**
	 * Big performance enhancement! 
	 * Use with caution since this contains no duplicates (e.g. class is listed in 2 different mappings files)
	 * Rebuilt at the *beginning* of {@link #update()}
	 * 
	 * @see #rebuildManagedTypeMap()
	 */
	protected final Hashtable<String, ManagedType> managedTypeMap = new Hashtable<String, ManagedType>();

	protected final ContextListContainer<MappingFileRef, XmlMappingFileRef> specifiedMappingFileRefContainer;

	/**
	 * Will be null if the implied mapping file should not be part of the context model.
	 * Otherwise will be equal to potentialImpliedMappingFileRef.
	 * 
	 * @see #potentialImpliedMappingFileRef
	 */
	protected MappingFileRef impliedMappingFileRef;

	/**
	 * Store the implied mapping file ref here even if it is not part of the context model.
	 * This allows us to sync it in the syncWithResourceModel. In the update, determine if
	 * it should be part of the context model and set the impliedMappingFileRef appropriately.
	 * 
	 * @see #impliedMappingFileRef
	 * @see #usesImpliedMappingFile()
	 */
	protected final MappingFileRef potentialImpliedMappingFileRef;

	protected final ContextListContainer<JarFileRef, XmlJarFileRef> jarFileRefContainer;

	protected final ContextListContainer<ClassRef, XmlJavaClassRef> specifiedClassRefContainer;

	protected final ContextCollectionContainer<ClassRef, JavaResourceAbstractType> impliedClassRefContainer;

	protected final ContextListContainer<Property, XmlProperty> propertyContainer;

	/* global generator definitions, defined elsewhere in model */
	protected final Vector<Generator> generators = new Vector<Generator>();

	/* global query definitions, defined elsewhere in model */
	protected final Vector<Query> queries = new Vector<Query>();

	protected boolean xmlMappingMetadataComplete;
	protected AccessType defaultAccess;
	protected String defaultCatalog;
	protected String defaultSchema;
	protected boolean defaultCascadePersist;
	protected boolean defaultDelimitedIdentifiers;

	protected final Vector<JpaStructureNode> structureChildren = new Vector<JpaStructureNode>();

	//****** JPA 2.0 features
	protected Connection2_0 connection;
	protected Options2_0 options;

	protected SharedCacheMode2_0 specifiedSharedCacheMode;
	protected SharedCacheMode2_0 defaultSharedCacheMode;

	protected ValidationMode2_0 specifiedValidationMode;
	protected ValidationMode2_0 defaultValidationMode = DEFAULT_VALIDATION_MODE;

	protected final Set<IFile> metamodelFiles = Collections.synchronizedSet(new HashSet<IFile>());

	//****** JPA 2.1 features
	protected SchemaGeneration2_1 schemaGeneration;
	

	// ********** construction/initialization **********

	/**
	 * NB: Be careful changing the order of the statements in this method
	 * (bug 258701 is one reason).
	 */
	protected AbstractPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent);
		this.xmlPersistenceUnit = xmlPersistenceUnit;
		this.name = xmlPersistenceUnit.getName();
		this.specifiedExcludeUnlistedClasses = xmlPersistenceUnit.getExcludeUnlistedClasses();
		this.specifiedTransactionType = this.buildSpecifiedTransactionType();
		this.description = xmlPersistenceUnit.getDescription();
		this.provider = xmlPersistenceUnit.getProvider();
		this.jtaDataSource = xmlPersistenceUnit.getJtaDataSource();
		this.nonJtaDataSource = xmlPersistenceUnit.getNonJtaDataSource();

		// initialize the properties early because other things will need them...(?)
		this.propertyContainer = this.buildPropertyContainer();
		this.initializeProperties();

		this.specifiedMappingFileRefContainer = this.buildSpecifiedMappingFileRefContainer();
		this.potentialImpliedMappingFileRef = this.buildImpliedMappingFileRef();
		this.jarFileRefContainer = this.buildJarFileRefContainer();
		this.specifiedClassRefContainer = this.buildSpecifiedClassRefContainer();
		this.impliedClassRefContainer = this.buildImpliedClassRefContainer();

		this.specifiedSharedCacheMode = this.buildSpecifiedSharedCacheMode();
		this.specifiedValidationMode = this.buildSpecifiedValidationMode();

		this.initializeMetamodelFiles();
		this.initializeStructureChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		this.setName_(this.xmlPersistenceUnit.getName());
		this.setSpecifiedExcludeUnlistedClasses_(this.xmlPersistenceUnit.getExcludeUnlistedClasses());
		this.setSpecifiedTransactionType_(this.buildSpecifiedTransactionType());
		this.setDescription_(this.xmlPersistenceUnit.getDescription());
		this.setProvider_(this.xmlPersistenceUnit.getProvider());
		this.setJtaDataSource_(this.xmlPersistenceUnit.getJtaDataSource());
		this.setNonJtaDataSource_(this.xmlPersistenceUnit.getNonJtaDataSource());

		this.syncSpecifiedMappingFileRefs();
		this.syncImpliedMappingFileRef();

		this.syncJarFileRefs();

		this.syncSpecifiedClassRefs();
		this.synchronizeModelsWithResourceModel(this.getImpliedClassRefs());

		this.syncProperties();

		this.setSpecifiedSharedCacheMode_(this.buildSpecifiedSharedCacheMode());
		this.setSpecifiedValidationMode_(this.buildSpecifiedValidationMode());
	}

	@Override
	public void update() {
		super.update();

		//Rebuild the managed type map first. I *think* if anything changes to cause 
		//this to be out of sync another update would be triggered by that change.
		this.rebuildManagedTypeMap();

		this.setDefaultTransactionType(this.buildDefaultTransactionType());

		// update specified class refs before mapping file refs because of
		// JpaFile root structure nodes - we want the mapping file to "win",
		// as it would in a JPA runtime implementation
		this.updateModels(this.getSpecifiedClassRefs());

		this.updateModels(this.getSpecifiedMappingFileRefs());
		this.updateImpliedMappingFileRef();

		this.updateModels(this.getJarFileRefs());

		// update the implied class refs after all the other types, both
		// specified here and specified in the mapping files, are in place
		this.updateImpliedClassRefs();

		this.updateModels(this.getProperties());

		this.updatePersistenceUnitMetadata();

		this.setGenerators(this.buildGenerators());
		this.setQueries(this.buildQueries());

		this.setDefaultSharedCacheMode(this.buildDefaultSharedCacheMode());
		this.setDefaultValidationMode(this.buildDefaultValidationMode());

		this.updateStructureChildren();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			mappingFileRef.addRootStructureNodesTo(jpaFile, rootStructureNodes);
		}
		for (ClassRef classRef : this.getClassRefs()) {
			classRef.addRootStructureNodesTo(jpaFile, rootStructureNodes);
		}
	}

	// ********** JpaContextModel implementation **********

	protected Persistence getPersistence() {
		return this.parent;
	}

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<PersistenceUnit> getStructureType() {
		return PersistenceUnit.class;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlPersistenceUnit.getSelectionTextRange();
	}

	protected void initializeStructureChildren() {
		CollectionTools.addAll(this.structureChildren, this.getMappingFileRefs());
		CollectionTools.addAll(this.structureChildren, this.getClassRefs());
		CollectionTools.addAll(this.structureChildren, this.getJarFileRefs());		
	}

	protected void updateStructureChildren() {
		Vector<JpaStructureNode> newChildren = new Vector<JpaStructureNode>();
		CollectionTools.addAll(newChildren, this.getMappingFileRefs());
		CollectionTools.addAll(newChildren, this.getClassRefs());
		CollectionTools.addAll(newChildren, this.getJarFileRefs());

		this.synchronizeCollection(newChildren, this.structureChildren, STRUCTURE_CHILDREN_COLLECTION);
	}

	public Iterable<JpaStructureNode> getStructureChildren() {
		return IterableTools.cloneLive(this.structureChildren);
	}

	public int getStructureChildrenSize() {
		return this.structureChildren.size();
	}

	public TextRange getFullTextRange() {
		return this.xmlPersistenceUnit.getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.xmlPersistenceUnit.containsOffset(textOffset);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode child : this.getStructureChildren()) {
			if (child.containsOffset(textOffset)) {
				return child;
			}
		}
		return this;
	}

	public void dispose() {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			mappingFileRef.dispose();
		}
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlPersistenceUnit.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** transaction type **********

	public PersistenceUnitTransactionType getTransactionType() {
		return (this.specifiedTransactionType != null) ? this.specifiedTransactionType : this.defaultTransactionType;
	}

	public PersistenceUnitTransactionType getSpecifiedTransactionType() {
		return this.specifiedTransactionType;
	}

	public void setSpecifiedTransactionType(PersistenceUnitTransactionType transactionType) {
		this.setSpecifiedTransactionType_(transactionType);
		this.xmlPersistenceUnit.setTransactionType(PersistenceUnitTransactionType.toXmlResourceModel(transactionType));
	}

	protected void setSpecifiedTransactionType_(PersistenceUnitTransactionType transactionType) {
		PersistenceUnitTransactionType old = this.specifiedTransactionType;
		this.specifiedTransactionType = transactionType;
		this.firePropertyChanged(SPECIFIED_TRANSACTION_TYPE_PROPERTY, old, transactionType);
	}

	public PersistenceUnitTransactionType getDefaultTransactionType() {
		return this.defaultTransactionType;
	}

	protected void setDefaultTransactionType(PersistenceUnitTransactionType transactionType) {
		PersistenceUnitTransactionType old = this.defaultTransactionType;
		this.defaultTransactionType = transactionType;
		this.firePropertyChanged(DEFAULT_TRANSACTION_TYPE_PROPERTY, old, transactionType);
	}

	protected PersistenceUnitTransactionType buildSpecifiedTransactionType() {
		return PersistenceUnitTransactionType.fromXmlResourceModel(this.xmlPersistenceUnit.getTransactionType());
	}

	/**
	 * TODO - calculate default
	 * From the JPA spec: "In a Java EE environment, if this element is not
	 * specified, the default is JTA. In a Java SE environment, if this element
	 * is not specified, a default of RESOURCE_LOCAL may be assumed."
	 */
	protected PersistenceUnitTransactionType buildDefaultTransactionType() {
		return PersistenceUnitTransactionType.JTA; //return JTA for now, fixing regression in bug 277524
	}


	// ********** description **********

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.setDescription_(description);
		this.xmlPersistenceUnit.setDescription(description);
	}

	protected void setDescription_(String description) {
		String old = this.description;
		this.description = description;
		this.firePropertyChanged(DESCRIPTION_PROPERTY, old, description);
	}


	// ********** provider **********

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.setProvider_(provider);
		this.xmlPersistenceUnit.setProvider(provider);
	}

	protected void setProvider_(String provider) {
		String old = this.provider;
		this.provider = provider;
		this.firePropertyChanged(PROVIDER_PROPERTY, old, provider);
	}


	// ********** JTA data source **********

	public String getJtaDataSource() {
		return this.jtaDataSource;
	}

	public void setJtaDataSource(String jtaDataSource) {
		this.setJtaDataSource_(jtaDataSource);
		this.xmlPersistenceUnit.setJtaDataSource(jtaDataSource);
	}

	protected void setJtaDataSource_(String jtaDataSource) {
		String old = this.jtaDataSource;
		this.jtaDataSource = jtaDataSource;
		this.firePropertyChanged(JTA_DATA_SOURCE_PROPERTY, old, jtaDataSource);
	}


	// ********** non-JTA data source **********

	public String getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}

	public void setNonJtaDataSource(String nonJtaDataSource) {
		this.setNonJtaDataSource_(nonJtaDataSource);
		this.xmlPersistenceUnit.setNonJtaDataSource(nonJtaDataSource);
	}

	protected void setNonJtaDataSource_(String nonJtaDataSource) {
		String old = this.nonJtaDataSource;
		this.nonJtaDataSource = nonJtaDataSource;
		this.firePropertyChanged(NON_JTA_DATA_SOURCE_PROPERTY, old, nonJtaDataSource);
	}


	// ********** mapping file refs **********

	public ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedMappingFileRef == null) ?
				this.getSpecifiedMappingFileRefs() :
				this.getCombinedMappingFileRefs();
	}

	protected ListIterable<MappingFileRef> getCombinedMappingFileRefs() {
		return IterableTools.insert(
				this.impliedMappingFileRef,
				this.getSpecifiedMappingFileRefs()
			);
	}

	public int getMappingFileRefsSize() {
		return (this.impliedMappingFileRef == null) ?
				this.getSpecifiedMappingFileRefsSize() :
				this.getCombinedMappingFileRefsSize();
	}

	protected int getCombinedMappingFileRefsSize() {
		return this.getSpecifiedMappingFileRefsSize() + 1;
	}

	public Iterable<MappingFileRef> getMappingFileRefsContaining(String typeName) {
		return IterableTools.filter(this.getMappingFileRefs(), new ManagedTypeContainer.ContainsType(typeName));
	}

	protected Iterable<MappingFile> getMappingFiles() {
		return IterableTools.removeNulls(this.getMappingFiles_());
	}

	protected Iterable<MappingFile> getMappingFiles_() {
		return IterableTools.transform(this.getMappingFileRefs(), MappingFileRef.MAPPING_FILE_TRANSFORMER);
	}


	// ********** specified mapping file refs **********

	public ListIterable<MappingFileRef> getSpecifiedMappingFileRefs() {
		return this.specifiedMappingFileRefContainer;
	}

	public int getSpecifiedMappingFileRefsSize() {
		return this.specifiedMappingFileRefContainer.size();
	}

	public MappingFileRef addSpecifiedMappingFileRef(String fileName) {
		return this.addSpecifiedMappingFileRef(this.getSpecifiedMappingFileRefsSize(), fileName);
	}

	public MappingFileRef addSpecifiedMappingFileRef(int index, String fileName) {
		XmlMappingFileRef xmlMappingFileRef = this.buildXmlMappingFileRef(fileName);
		MappingFileRef mappingFileRef = this.specifiedMappingFileRefContainer.addContextElement(index, xmlMappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(index, xmlMappingFileRef);
		return mappingFileRef;
	}

	protected XmlMappingFileRef buildXmlMappingFileRef(String fileName) {
		XmlMappingFileRef ref = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		ref.setFileName(fileName);
		return ref;
	}

	protected MappingFileRef buildSpecifiedMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		return this.getContextModelFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}

	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		this.removeSpecifiedMappingFileRef(this.specifiedMappingFileRefContainer.indexOf(mappingFileRef));
	}

	public void removeSpecifiedMappingFileRef(int index) {
		this.removeSpecifiedMappingFileRef_(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
	}

	protected void removeSpecifiedMappingFileRef_(int index) {
		this.specifiedMappingFileRefContainer.remove(index);
	}

	protected void syncSpecifiedMappingFileRefs() {
		this.specifiedMappingFileRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlMappingFileRef> getXmlMappingFileRefs() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlPersistenceUnit.getMappingFiles());
	}

	protected ContextListContainer<MappingFileRef, XmlMappingFileRef> buildSpecifiedMappingFileRefContainer() {
		return new SpecifiedMappingFileRefContainer(SPECIFIED_MAPPING_FILE_REFS_LIST, new SpecifiedMappingFileRefContainerAdapter());
		// return this.buildSpecifiedContextListContainer(SPECIFIED_MAPPING_FILE_REFS_LIST, new SpecifiedMappingFileRefContainerAdapter());
	}

	/**
	 * specified mapping file ref container adapter
	 */
	public class SpecifiedMappingFileRefContainerAdapter
		extends AbstractContainerAdapter<MappingFileRef, XmlMappingFileRef>
	{
		public MappingFileRef buildContextElement(XmlMappingFileRef resourceElement) {
			return AbstractPersistenceUnit.this.buildSpecifiedMappingFileRef(resourceElement);
		}
		public ListIterable<XmlMappingFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlMappingFileRefs();
		}
		public XmlMappingFileRef extractResourceElement(MappingFileRef contextElement) {
			return contextElement.getXmlMappingFileRef();
		}
	}

	// TODO - remove once we remove need for dispose...
	public class SpecifiedMappingFileRefContainer
		extends SpecifiedContextListContainer<MappingFileRef, XmlMappingFileRef>
	{
		public SpecifiedMappingFileRefContainer(String aspectName, Container.Adapter<MappingFileRef, XmlMappingFileRef> adapter) {
			super(aspectName, adapter);
		}
		@Override
		public void clear() {
			Object[] temp = this.elements.toArray();
			super.clear();
			for (Object element : temp) {
				((MappingFileRef) element).dispose();
			}
		}
		@Override
		public MappingFileRef remove(int index) {
			MappingFileRef element = super.remove(index);
			element.dispose();
			return element;
		}
		@Override
		public void remove(MappingFileRef element) {
			super.remove(element);
			element.dispose();
		}
		@Override
		public void removeAll(Iterable<MappingFileRef> contextElements) {
			super.removeAll(contextElements);
			for (MappingFileRef element : contextElements) {
				element.dispose();
			}
		}
	}


	// ********** implied mapping file ref **********

	public MappingFileRef getImpliedMappingFileRef() {
		return this.impliedMappingFileRef;
	}

	protected void setImpliedMappingFileRef(MappingFileRef mappingFileRef) {
		MappingFileRef old = this.impliedMappingFileRef;
		this.impliedMappingFileRef = mappingFileRef;
		this.firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, old, mappingFileRef);
	}

	protected MappingFileRef buildImpliedMappingFileRef() {
		return this.getContextModelFactory().buildVirtualMappingFileRef(this);
	}

	protected void syncImpliedMappingFileRef() {
		this.potentialImpliedMappingFileRef.synchronizeWithResourceModel();
	}

	protected void updateImpliedMappingFileRef() {
		if (this.usesImpliedMappingFile()) {
			this.setImpliedMappingFileRef(this.potentialImpliedMappingFileRef);
			this.impliedMappingFileRef.update();
		}
		else if (this.impliedMappingFileRef != null) {
			this.impliedMappingFileRef.dispose();
			this.setImpliedMappingFileRef(null);
		}
	}

	protected boolean usesImpliedMappingFile() {
		return this.impliedMappingFileIsNotSpecified() && this.impliedMappingFileExists();
	}

	protected boolean impliedMappingFileIsNotSpecified() {
		return ! this.impliedMappingFileIsSpecified();
	}

	protected boolean impliedMappingFileIsSpecified() {
		return this.mappingFileIsSpecified(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
	}

	protected boolean mappingFileIsSpecified(String impliedMappingFileName) {
		for (MappingFileRef specifiedMappingFileRef : this.getSpecifiedMappingFileRefs()) {
			if (ObjectTools.equals(specifiedMappingFileRef.getFileName(), impliedMappingFileName)) {
				return true;
			}
		}
		return false;
	}

	protected boolean impliedMappingFileExists() {
		return this.getJpaProject().getDefaultOrmXmlResource() != null;
	}


	// ********** JAR file refs **********

	public ListIterable<JarFileRef> getJarFileRefs() {
		return this.jarFileRefContainer;
	}

	public int getJarFileRefsSize() {
		return this.jarFileRefContainer.size();
	}

	public JarFileRef addJarFileRef(String fileName) {
		return this.addJarFileRef(this.getJarFileRefsSize(), fileName);
	}

	public JarFileRef addJarFileRef(int index, String fileName) {
		XmlJarFileRef xmlJarFileRef = this.buildXmlJarFileRef(fileName);
		JarFileRef jarFileRef = this.jarFileRefContainer.addContextElement(index, xmlJarFileRef);
		this.xmlPersistenceUnit.getJarFiles().add(index, xmlJarFileRef);
		return jarFileRef;
	}

	protected XmlJarFileRef buildXmlJarFileRef(String fileName) {
		XmlJarFileRef ref = PersistenceFactory.eINSTANCE.createXmlJarFileRef();
		ref.setFileName(fileName);
		return ref;
	}

	protected JarFileRef buildJarFileRef(XmlJarFileRef xmlJarFileRef) {
		return this.getContextModelFactory().buildJarFileRef(this, xmlJarFileRef);
	}

	public void removeJarFileRef(JarFileRef jarFileRef) {
		this.removeJarFileRef(this.jarFileRefContainer.indexOf(jarFileRef));
	}

	public void removeJarFileRef(int index) {
		this.removeJarFileRef_(index);
		this.xmlPersistenceUnit.getJarFiles().remove(index);
	}

	protected void removeJarFileRef_(int index) {
		this.jarFileRefContainer.remove(index);
	}

	protected void syncJarFileRefs() {
		this.jarFileRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJarFileRef> getXmlJarFileRefs() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlPersistenceUnit.getJarFiles());
	}

	protected ContextListContainer<JarFileRef, XmlJarFileRef> buildJarFileRefContainer() {
		return this.buildSpecifiedContextListContainer(JAR_FILE_REFS_LIST, new JarFileRefContainerAdapter());
	}

	/**
	 * JAR file ref container adapter
	 */
	public class JarFileRefContainerAdapter
		extends AbstractContainerAdapter<JarFileRef, XmlJarFileRef>
	{
		public JarFileRef buildContextElement(XmlJarFileRef resourceElement) {
			return AbstractPersistenceUnit.this.buildJarFileRef(resourceElement);
		}
		public ListIterable<XmlJarFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlJarFileRefs();
		}
		public XmlJarFileRef extractResourceElement(JarFileRef contextElement) {
			return contextElement.getXmlJarFileRef();
		}
	}

	// ********** class refs **********

	@SuppressWarnings("unchecked")
	public Iterable<ClassRef> getClassRefs() {
		return IterableTools.concatenate(
						this.getSpecifiedClassRefs(),
						this.getImpliedClassRefs()
					);
	}

	public int getClassRefsSize() {
		return this.getSpecifiedClassRefsSize() + this.getImpliedClassRefsSize();
	}


	// ********** specified class refs **********

	public ListIterable<ClassRef> getSpecifiedClassRefs() {
		return this.specifiedClassRefContainer;
	}

	public int getSpecifiedClassRefsSize() {
		return this.specifiedClassRefContainer.size();
	}

	public ClassRef addSpecifiedClassRef(String className) {
		return this.addSpecifiedClassRef(this.getSpecifiedClassRefsSize(), className);
	}

	public ClassRef addSpecifiedClassRef(int index, String className) {
		XmlJavaClassRef xmlClassRef = this.buildXmlJavaClassRef(className);
		ClassRef classRef = this.specifiedClassRefContainer.addContextElement(index, xmlClassRef);
		this.xmlPersistenceUnit.getClasses().add(index, xmlClassRef);
		return classRef;
	}

	protected Iterable<ClassRef> addSpecifiedClassRefs(Iterable<JavaResourceAbstractType> javaResourceTypes) {
		return this.addSpecifiedClassRefs(this.getSpecifiedClassRefsSize(), javaResourceTypes);
	}

	protected Iterable<ClassRef> addSpecifiedClassRefs(int index, Iterable<JavaResourceAbstractType> javaResourceTypes) {
		ArrayList<XmlJavaClassRef> xmlClassRefs = new ArrayList<XmlJavaClassRef>();
		for (JavaResourceAbstractType javaResourceType : javaResourceTypes) {
			xmlClassRefs.add(this.buildXmlJavaClassRef(javaResourceType.getTypeBinding().getQualifiedName()));
		}
		Iterable<ClassRef> classRefs = this.specifiedClassRefContainer.addContextElements(index, xmlClassRefs);
		this.xmlPersistenceUnit.getClasses().addAll(index, xmlClassRefs);
		return classRefs;
	}

	protected XmlJavaClassRef buildXmlJavaClassRef(String className) {
		XmlJavaClassRef ref = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		ref.setJavaClass(className);
		return ref;
	}

	protected ClassRef buildClassRef(XmlJavaClassRef xmlClassRef) {
		return this.getContextModelFactory().buildClassRef(this, xmlClassRef);
	}

	public void removeSpecifiedClassRef(ClassRef classRef) {
		this.removeSpecifiedClassRef(this.specifiedClassRefContainer.indexOf(classRef));
	}

	public void removeSpecifiedClassRef(int index) {
		this.removeSpecifiedClassRef_(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
	}

	protected void removeSpecifiedClassRef_(int index) {
		this.specifiedClassRefContainer.remove(index);
	}

	public void removeSpecifiedClassRefs(Iterable<ClassRef> classRefs) {
		ArrayList<XmlJavaClassRef> xmlClassRefs = new ArrayList<XmlJavaClassRef>();
		for (ClassRef classRef : classRefs) {
			xmlClassRefs.add(classRef.getXmlClassRef());
		}
		this.specifiedClassRefContainer.removeAll(classRefs);
		this.xmlPersistenceUnit.getClasses().removeAll(xmlClassRefs);
	}

	protected void syncSpecifiedClassRefs() {
		this.specifiedClassRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJavaClassRef> getXmlClassRefs() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlPersistenceUnit.getClasses());
	}

	protected ContextListContainer<ClassRef, XmlJavaClassRef> buildSpecifiedClassRefContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_CLASS_REFS_LIST, new SpecifiedClassRefContainerAdapter());
	}

	/**
	 * specified class ref container adapter
	 */
	public class SpecifiedClassRefContainerAdapter
		extends AbstractContainerAdapter<ClassRef, XmlJavaClassRef>
	{
		public ClassRef buildContextElement(XmlJavaClassRef resourceElement) {
			return AbstractPersistenceUnit.this.buildClassRef(resourceElement);
		}
		public ListIterable<XmlJavaClassRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlClassRefs();
		}
		public XmlJavaClassRef extractResourceElement(ClassRef contextElement) {
			return contextElement.getXmlClassRef();
		}
	}


	// ********** virtual class refs **********

	public Iterable<ClassRef> getImpliedClassRefs() {
		return this.impliedClassRefContainer;
	}

	public int getImpliedClassRefsSize() {
		return this.impliedClassRefContainer.size();
	}

	protected ClassRef buildClassRef(JavaResourceAbstractType jrat) {
		return this.getContextModelFactory().buildClassRef(this, jrat);
	}

	protected void updateImpliedClassRefs() {
		this.impliedClassRefContainer.update();
	}

	protected Iterable<JavaResourceAbstractType> getImpliedClassResourceTypes() {
		return this.excludesUnlistedClasses() ?
				EmptyIterable.<JavaResourceAbstractType>instance() :
				this.getImpliedClassResourceTypes_();
	}

	/**
	 * Return the names of all the Java classes in the JPA project that are
	 * managed (i.e. have the appropriate annotation etc.) but not specified
	 * in the persistence unit or any of its mapping files.
	 */
	protected Iterable<JavaResourceAbstractType> getImpliedClassResourceTypes_() {
		return IterableTools.filter(this.getJpaProject().getPotentialJavaSourceTypes(), new DoesNotSpecifyManagedType());
	}

	public class DoesNotSpecifyManagedType
		extends PredicateAdapter<JavaResourceAbstractType>
	{
		@Override
		public boolean evaluate(JavaResourceAbstractType jrat) {
			return ! AbstractPersistenceUnit.this.specifiesManagedType(jrat.getTypeBinding().getQualifiedName());
		}
	}

	protected ContextCollectionContainer<ClassRef, JavaResourceAbstractType> buildImpliedClassRefContainer() {
		return this.buildVirtualContextCollectionContainer(IMPLIED_CLASS_REFS_COLLECTION, new ImpliedClassRefContainerAdapter());
	}

	/**
	 * default class ref container adapter
	 * <p>
	 * <strong>NB:</strong> The context class ref is matched with a java resource type.
	 * <p>
	 * This is used during <strong>both</strong> <em>sync</em> and
	 * <em>update</em> because the list of implied class refs can be modified
	 * in either situation. In particular, we cannot simply rely on
	 * <em>update</em> because there are situations where a <em>sync</em> is
	 * triggered but a follow-up <em>update</em> is not. (Of course, any
	 * change discovered here will trigger an <em>update</em>.)
	 * <p>
	 * The most obvious example is when the JPA project is configured to
	 * discover annotated classes and a Java class is annotated for the first
	 * time (via code editing, not via the context model). This will trigger
	 * a <em>sync</em>; but, since the unannotated class is not yet in the
	 * context model and, as a result, the context model's state is untouched,
	 * an <em>update</em> will not be triggered.
	 * <p>
	 * Obviously, other context model changes can change this collection (e.g.
	 * setting whether the persistence unit excludes unlisted classes); o the
	 * collection must also be synchronized during <em>update</em>.
	 */
	public class ImpliedClassRefContainerAdapter
		extends AbstractContainerAdapter<ClassRef, JavaResourceAbstractType>
	{
		public ClassRef buildContextElement(JavaResourceAbstractType resourceElement) {
			return AbstractPersistenceUnit.this.buildClassRef(resourceElement);
		}
		public Iterable<JavaResourceAbstractType> getResourceElements() {
			return AbstractPersistenceUnit.this.getImpliedClassResourceTypes();
		}
		public JavaResourceAbstractType extractResourceElement(ClassRef contextElement) {
			return contextElement.getJavaResourceType();
		}
	}


	// ********** exclude unlisted classes **********

	public boolean excludesUnlistedClasses() {
		return (this.specifiedExcludeUnlistedClasses != null) ? this.specifiedExcludeUnlistedClasses.booleanValue() : this.getDefaultExcludeUnlistedClasses();
	}

	public Boolean getSpecifiedExcludeUnlistedClasses() {
		return this.specifiedExcludeUnlistedClasses;
	}

	public void setSpecifiedExcludeUnlistedClasses(Boolean specifiedExcludeUnlistedClasses) {
		this.setSpecifiedExcludeUnlistedClasses_(specifiedExcludeUnlistedClasses);
		this.xmlPersistenceUnit.setExcludeUnlistedClasses(this.specifiedExcludeUnlistedClasses);
	}

	protected void setSpecifiedExcludeUnlistedClasses_(Boolean excludeUnlistedClasses) {
		Boolean old = this.specifiedExcludeUnlistedClasses;
		this.specifiedExcludeUnlistedClasses = excludeUnlistedClasses;
		this.firePropertyChanged(SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY, old, excludeUnlistedClasses);
	}

	public boolean getDefaultExcludeUnlistedClasses() {
		return false;  // ???
	}


	// ********** properties **********

	public ListIterable<Property> getProperties() {
		return this.propertyContainer;
	}

	public int getPropertiesSize() {
		return this.propertyContainer.size();
	}

	public Property getProperty(String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException();
		}
		for (Property property : this.getProperties()) {
			if (propertyName.equals(property.getName())) {
				return property;
			}
		}
		return null;
	}

	public Iterable<Property> getPropertiesNamed(String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException();
		}
		return IterableTools.filter(this.getProperties(), new Property.NameEquals(propertyName));
	}

	public Iterable<Property> getPropertiesWithNamePrefix(final String propertyNamePrefix) {
		if (propertyNamePrefix == null) {
			throw new NullPointerException();
		}
		return IterableTools.filter(this.getProperties(), new Property.NameStartsWith(propertyNamePrefix));
	}

	public Property addProperty() {
		return this.addProperty(this.getPropertiesSize());
	}

	public Property addProperty(int index) {
		XmlProperty xmlProperty = this.buildXmlProperty();
		Property property = this.addProperty_(index, xmlProperty);

		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			xmlProperties = this.buildXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}

		xmlProperties.getProperties().add(index, xmlProperty);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
		return property;
	}

	protected XmlProperty buildXmlProperty() {
		return PersistenceFactory.eINSTANCE.createXmlProperty();
	}

	protected Property buildProperty(XmlProperty xmlProperty) {
		return this.getContextModelFactory().buildProperty(this, xmlProperty);
	}

	protected XmlProperties buildXmlProperties() {
		return PersistenceFactory.eINSTANCE.createXmlProperties();
	}

	public void setProperty(String propertyName, String value) {
		this.setProperty(propertyName, value, false);
	}

	public void setProperty(String propertyName, String value, boolean duplicatePropertyNamesAllowed) {
		Property prev = this.getProperty(propertyName);
		if (prev == null) {
			if (value != null) {
				this.addProperty(propertyName, value);  // add [first] property
			}
		} else {
			if (duplicatePropertyNamesAllowed) {
				if (value == null) {
					// do nothing?
				} else {
					this.addProperty(propertyName, value);  // add [duplicate] property
				}
			} else {
				if (value == null) {
					this.removeProperty(prev);  // remove existing property
				} else {
					prev.setValue(value);  // change existing property
				}
			}
		}
	}

	protected void addProperty(String propertyName, String value) {
		Property property = this.addProperty();
		property.setName(propertyName);
		property.setValue(value);
	}

	public void removeProperty(Property property) {
		this.removeProperty(this.propertyContainer.indexOf(property));
	}

	public void removeProperty(String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException();
		}
		for (Property property : this.getProperties()) {
			if (propertyName.equals(property.getName())) {
				this.removeProperty(property);
				return;
			}
		}
		throw new IllegalArgumentException("invalid property name: " + propertyName); //$NON-NLS-1$
	}

	public void removeProperty(String propertyName, String value) {
		if ((propertyName == null) || (value == null)) {
			throw new NullPointerException();
		}
		for (Property property : this.getProperties()) {
			if (propertyName.equals(property.getName()) && value.equals(property.getValue())) {
				this.removeProperty(property);
				return;
			}
		}
		throw new IllegalArgumentException("invalid property name/value pair: " + propertyName + " = " + value); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void removeProperty(int index) {
		Property removedProperty = this.propertyContainer.remove(index);
		this.xmlPersistenceUnit.getProperties().getProperties().remove(index);

		if (this.xmlPersistenceUnit.getProperties().getProperties().isEmpty()) {
			this.xmlPersistenceUnit.setProperties(null);
		}

		if (removedProperty.getName() != null) {
			this.propertyRemoved(removedProperty.getName());
		}
	}

	public void propertyNameChanged(String oldPropertyName, String newPropertyName, String value) {
		if ((oldPropertyName == null) && (value == null)) {
			//this is a property that is currently being added, we don't need to deal with it until the value is set
			return;
		}
		if (oldPropertyName != null) {
			this.propertyRemoved(oldPropertyName);
		}
		if (newPropertyName != null) {
			this.propertyAdded(newPropertyName, value);
		}
	}

	public void propertyValueChanged(String propertyName, String newValue) {
		this.connection.propertyValueChanged(propertyName, newValue);
		this.options.propertyValueChanged(propertyName, newValue);
		this.schemaGeneration.propertyValueChanged(propertyName, newValue);
	}

	protected void propertyAdded(String propertyName, String value) {
		this.propertyValueChanged(propertyName, value);
	}

	protected void propertyRemoved(String propertyName) {
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
		this.schemaGeneration.propertyRemoved(propertyName);
	}

	protected void initializeProperties() {
		this.connection = this.buildConnection();
		this.options = this.buildOptions();
		this.schemaGeneration = this.buildSchemaGeneration();
	}

	protected Connection2_0 buildConnection() {
		return this.isPersistenceXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildConnection(this) :
				new NullConnection2_0(this);
	}

	protected Options2_0 buildOptions() {
		return this.isPersistenceXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOptions(this) :
				new NullOptions2_0(this);
	}

	protected SchemaGeneration2_1 buildSchemaGeneration() {
		return this.isPersistenceXml2_1Compatible() ?
				this.getContextModelFactory2_1().buildSchemaGeneration(this) :
				new NullGenericSchemaGeneration2_1(this);
	}

	protected void syncProperties() {
		this.propertyContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlProperty> getXmlProperties() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		// clone to reduce chance of concurrency problems
		return (xmlProperties == null) ?
				EmptyListIterable.<XmlProperty>instance() :
				IterableTools.cloneLive(xmlProperties.getProperties());
	}

	protected Property addProperty_(int index, XmlProperty xmlProperty) {
		Property property = this.propertyContainer.addContextElement(index, xmlProperty);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
		return property;
	}

	protected void removeProperty_(Property property) {
		this.propertyContainer.remove(property);
		if (property.getName() != null) {
			this.propertyRemoved(property.getName());
		}
	}

	protected ContextListContainer<Property, XmlProperty> buildPropertyContainer() {
		return new PropertyContainer(PROPERTIES_LIST, new PropertyContainerAdapter());
		// return this.buildSpecifiedContextListContainer(PROPERTIES_LIST, new PropertyContainerAdapter());
	}

	/**
	 * property container adapter
	 */
	public class PropertyContainerAdapter
		extends AbstractContainerAdapter<Property, XmlProperty>
	{
		public Property buildContextElement(XmlProperty resourceElement) {
			return AbstractPersistenceUnit.this.buildProperty(resourceElement);
		}
		public ListIterable<XmlProperty> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlProperties();
		}
		public XmlProperty extractResourceElement(Property contextElement) {
			return contextElement.getXmlProperty();
		}
	}

	public class PropertyContainer
		extends SpecifiedContextListContainer<Property, XmlProperty>
	{
		public PropertyContainer(String aspectName, Container.Adapter<Property, XmlProperty> adapter) {
			super(aspectName, adapter);
		}
		@Override
		protected Property add(int index, Property element) {
			super.add(index, element);
			if (element.getName() != null) {
				propertyAdded(element.getName(), element.getValue());
			}
			return element;
		}
		@Override
		public void clear() {
			Object[] temp = this.elements.toArray();
			super.clear();
			for (Object element : temp) {
				this.dispose((Property) element);
			}
		}
		@Override
		public Property remove(int index) {
			Property element = super.remove(index);
			this.dispose(element);
			return element;
		}
		@Override
		public void remove(Property element) {
			super.remove(element);
			this.dispose(element);
		}
		@Override
		public void removeAll(Iterable<Property> contextElements) {
			super.removeAll(contextElements);
			for (Property element : contextElements) {
				this.dispose(element);
			}
		}
		protected void dispose(Property element) {
			if (element.getName() != null) {
				propertyRemoved(element.getName());
			}
		}
	}


	// ********** mapping file (orm.xml) persistence unit metadata & defaults **********

	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}

	protected void setXmlMappingMetadataComplete(boolean xmlMappingMetadataComplete) {
		boolean old = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = xmlMappingMetadataComplete;
		this.firePropertyChanged(XML_MAPPING_METADATA_COMPLETE_PROPERTY, old, xmlMappingMetadataComplete);
	}

	protected boolean buildXmlMappingMetadataComplete(MappingFilePersistenceUnitMetadata metadata) {
		return (metadata == null) ? false : metadata.isXmlMappingMetadataComplete();
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildDefaultCatalog(MappingFilePersistenceUnitDefaults defaults) {
		String catalog = (defaults == null) ? null : defaults.getCatalog();
		return (catalog != null) ? catalog : this.getJpaProject().getDefaultCatalog();
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildDefaultSchema(MappingFilePersistenceUnitDefaults defaults) {
		String schema = (defaults == null) ? null : defaults.getSchema();
		return (schema != null) ? schema : this.getJpaProject().getDefaultSchema();
	}

	public boolean getDefaultCascadePersist() {
		return this.defaultCascadePersist;
	}

	protected void setDefaultCascadePersist(boolean cascadePersist) {
		boolean old = this.defaultCascadePersist;
		this.defaultCascadePersist = cascadePersist;
		this.firePropertyChanged(DEFAULT_CASCADE_PERSIST_PROPERTY, old, cascadePersist);
	}

	protected boolean buildDefaultCascadePersist(MappingFilePersistenceUnitDefaults defaults) {
		return (defaults == null) ? false : defaults.isCascadePersist();
	}

	public boolean getDefaultDelimitedIdentifiers() {
		return this.defaultDelimitedIdentifiers;
	}

	protected void setDefaultDelimitedIdentifiers(boolean delimitedIdentifiers) {
		boolean old = this.defaultDelimitedIdentifiers;
		this.defaultDelimitedIdentifiers = delimitedIdentifiers;
		this.firePropertyChanged(DEFAULT_DELIMITED_IDENTIFIERS_PROPERTY, old, delimitedIdentifiers);
	}

	protected boolean buildDefaultDelimitedIdentifiers(MappingFilePersistenceUnitDefaults defaults) {
		return ( ! this.isJpa2_0Compatible()) ? false :
				(defaults == null) ? false : ((MappingFilePersistenceUnitDefaults2_0) defaults).isDelimitedIdentifiers();
	}

	protected void updatePersistenceUnitMetadata() {
		MappingFilePersistenceUnitMetadata metadata = this.getMetadata();
		this.setXmlMappingMetadataComplete(this.buildXmlMappingMetadataComplete(metadata));

		MappingFilePersistenceUnitDefaults defaults = (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
		this.setDefaultAccess((defaults == null) ? null : defaults.getAccess());
		this.setDefaultCatalog(this.buildDefaultCatalog(defaults));
		this.setDefaultSchema(this.buildDefaultSchema(defaults));
		this.setDefaultDelimitedIdentifiers(this.buildDefaultDelimitedIdentifiers(defaults));
	}

	/**
	 * return the first persistence unit metadata we encounter
	 * in a mapping file
	 */
	protected MappingFilePersistenceUnitMetadata getMetadata() {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.persistenceUnitMetadataExists()) {
				return mappingFileRef.getPersistenceUnitMetadata();
			}
		}
		return null;
	}


	// ********** PersistenceUnit2_0 implementation **********

	public Connection2_0 getConnection() {
		return this.connection;
	}

	public Options2_0 getOptions() {
		return this.options;
	}


	// ********** PersistenceUnit2_1 implementation **********

	public SchemaGeneration2_1 getSchemaGeneration() {
		return this.schemaGeneration;
	}



	// ********** shared cache mode **********

	public SharedCacheMode2_0 getSharedCacheMode() {
		return (this.specifiedSharedCacheMode != null) ? this.specifiedSharedCacheMode : this.defaultSharedCacheMode;
	}

	public SharedCacheMode2_0 getSpecifiedSharedCacheMode() {
		return this.specifiedSharedCacheMode;
	}

	public void setSpecifiedSharedCacheMode(SharedCacheMode2_0 specifiedSharedCacheMode) {
		this.setSpecifiedSharedCacheMode_(specifiedSharedCacheMode);
		this.xmlPersistenceUnit.setSharedCacheMode(SharedCacheMode2_0.toXmlResourceModel(specifiedSharedCacheMode));
	}

	protected void setSpecifiedSharedCacheMode_(SharedCacheMode2_0 sharedCacheMode) {
		SharedCacheMode2_0 old = this.specifiedSharedCacheMode;
		this.specifiedSharedCacheMode = sharedCacheMode;
		this.firePropertyChanged(SPECIFIED_SHARED_CACHE_MODE_PROPERTY, old, sharedCacheMode);
	}

	public SharedCacheMode2_0 getDefaultSharedCacheMode() {
		return this.defaultSharedCacheMode;
	}

	protected void setDefaultSharedCacheMode(SharedCacheMode2_0 defaultSharedCacheMode) {
		SharedCacheMode2_0 old = this.defaultSharedCacheMode;
		this.defaultSharedCacheMode = defaultSharedCacheMode;
		this.firePropertyChanged(DEFAULT_SHARED_CACHE_MODE_PROPERTY, old, defaultSharedCacheMode);
	}

	public boolean calculateDefaultCacheable() {
		SharedCacheMode2_0 sharedCacheMode = this.getSharedCacheMode();
		if (sharedCacheMode == null) {
			return false;  // this can happen during initial update...
		}
		switch (sharedCacheMode) {
			case NONE:
			case ENABLE_SELECTIVE:
			case UNSPECIFIED:
				return false;
			case ALL:
			case DISABLE_SELECTIVE:
				return true;
			default:
				throw new IllegalStateException("unknown mode: " + sharedCacheMode); //$NON-NLS-1$
		}
	}

	protected SharedCacheMode2_0 buildSpecifiedSharedCacheMode() {
		return SharedCacheMode2_0.fromXmlResourceModel(this.xmlPersistenceUnit.getSharedCacheMode());
	}

	protected SharedCacheMode2_0 buildDefaultSharedCacheMode() {
		return SharedCacheMode2_0.UNSPECIFIED;
	}

	// ********** validation mode **********

	public ValidationMode2_0 getValidationMode() {
		return (this.specifiedValidationMode != null) ? this.specifiedValidationMode : this.defaultValidationMode;
	}

	public ValidationMode2_0 getSpecifiedValidationMode() {
		return this.specifiedValidationMode;
	}

	public void setSpecifiedValidationMode(ValidationMode2_0 specifiedValidationMode) {
		this.setSpecifiedValidationMode_(specifiedValidationMode);
		this.xmlPersistenceUnit.setValidationMode(ValidationMode2_0.toXmlResourceModel(specifiedValidationMode));
	}

	protected void setSpecifiedValidationMode_(ValidationMode2_0 validationMode) {
		ValidationMode2_0 old = this.specifiedValidationMode;
		this.specifiedValidationMode = validationMode;
		this.firePropertyChanged(SPECIFIED_VALIDATION_MODE_PROPERTY, old, validationMode);
	}

	public ValidationMode2_0 getDefaultValidationMode() {
		return this.defaultValidationMode;
	}

	protected void setDefaultValidationMode(ValidationMode2_0 defaultValidationMode) {
		ValidationMode2_0 old = this.defaultValidationMode;
		this.defaultValidationMode = defaultValidationMode;
		this.firePropertyChanged(DEFAULT_VALIDATION_MODE_PROPERTY, old, defaultValidationMode);
	}

	protected ValidationMode2_0 buildSpecifiedValidationMode() {
		return ValidationMode2_0.fromXmlResourceModel(this.xmlPersistenceUnit.getValidationMode());
	}

	protected ValidationMode2_0 buildDefaultValidationMode() {
		return DEFAULT_VALIDATION_MODE;
	}


	// ********** generators **********

	public Iterable<Generator> getGenerators() {
		return IterableTools.cloneLive(this.generators);
	}

	public int getGeneratorsSize() {
		return this.generators.size();
	}

	public Iterable<String> getUniqueGeneratorNames() {
		return CollectionTools.set(this.getNonEmptyGeneratorNames(), this.getGeneratorsSize());
	}

	protected Iterable<String> getNonEmptyGeneratorNames() {
		return IterableTools.filter(this.getGeneratorNames(), StringTools.IS_NOT_BLANK);
	}

	protected Iterable<String> getGeneratorNames() {
		return new TransformationIterable<Generator, String>(this.getGenerators(), JpaNamedContextModel.NAME_TRANSFORMER);
	}

	protected void setGenerators(Iterable<Generator> generators) {
		this.synchronizeCollection(generators, this.generators, GENERATORS_COLLECTION);
	}

	/**
	 * Generators are much like queries.
	 * @see #buildQueries()
	 */
	protected Iterable<Generator> buildGenerators() {
		ArrayList<Generator> result = ListTools.list(this.getMappingFileGenerators());

		HashSet<String> mappingFileGeneratorNames = this.convertToNames(result);
		HashMap<String, ArrayList<Generator>> allJavaGenerators = this.mapByName(this.getAllJavaGenerators());
		for (Map.Entry<String, ArrayList<Generator>> entry : allJavaGenerators.entrySet()) {
			if ( ! mappingFileGeneratorNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<Generator> getMappingFileGenerators() {
		return IterableTools.children(this.getMappingFileRefs(), MappingFileRef.MAPPING_FILE_GENERATORS_TRANSFORMER);
	}

	/**
	 * Include "overridden" Java generators.
	 */
	protected Iterable<Generator> getAllJavaGenerators() {
		return IterableTools.children(this.getAllJavaTypeMappingsUnique(), TypeMapping.GENERATORS_TRANSFORMER);
	}

	// ***** metadata conversion
	public boolean hasConvertibleJavaGenerators() {
		return ! this.getConvertibleJavaGenerators().isEmpty();
	}

	public void convertJavaGenerators(EntityMappings entityMappings, IProgressMonitor monitor) {
		ArrayList<Generator> convertibleJavaGenerators = this.getConvertibleJavaGenerators();
		SubMonitor subMonitor = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, convertibleJavaGenerators.size());
		for (Generator generator : convertibleJavaGenerators) {
			this.convertJavaGenerator(entityMappings, (JavaGenerator) generator, subMonitor.newChild(1));
		}
		subMonitor.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaGenerator(EntityMappings entityMappings, JavaGenerator generator, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_GENERATOR, generator.getName()));
		generator.convertTo(entityMappings);
		generator.delete();  // delete any converted generators
	}

	protected ArrayList<Generator> getConvertibleJavaGenerators() {
		return this.extractConvertibleJavaModels(this.getAllJavaGenerators(), this.getMappingFileGenerators());
	}


	// ********** queries **********

	public Iterable<Query> getQueries() {
		return IterableTools.cloneLive(this.queries);
	}

	public int getQueriesSize() {
		return this.queries.size();
	}

	public void addQuery(Query query) {
		this.queries.add(query);
	}

	protected void setQueries(Iterable<Query> queries) {
		this.synchronizeCollection(queries, this.queries, QUERIES_COLLECTION);
	}

	/**
	 * The persistence unit holds only <em>active</em> queries;
	 * i.e. the mapping file queries and the Java queries that are not
	 * "overridden" by mapping file queries (by query name).
	 * <p>
	 * <strong>NB:</strong> The list can contain queries with duplicate names;
	 * either when there are multiple mapping file queries with the same name
	 * or multiple, non-overridden Java queries with the same name.
	 */
	protected Iterable<Query> buildQueries() {
		ArrayList<Query> result = ListTools.list(this.getMappingFileQueries());

		HashSet<String> mappingFileQueryNames = this.convertToNames(result);
		HashMap<String, ArrayList<Query>> allJavaQueries = this.mapByName(this.getAllJavaQueries());
		for (Map.Entry<String, ArrayList<Query>> entry : allJavaQueries.entrySet()) {
			if ( ! mappingFileQueryNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<Query> getMappingFileQueries() {
		return IterableTools.children(this.getMappingFileRefs(), MappingFileRef.MAPPING_FILE_QUERIES_TRANSFORMER);
	}

	/**
	 * Include "overridden" Java queries.
	 */
	protected Iterable<Query> getAllJavaQueries() {
		return IterableTools.children(this.getAllJavaTypeMappingsUnique(), TypeMapping.QUERIES_TRANSFORMER);
	}

	protected Iterable<TypeMapping> getAllJavaTypeMappingsUnique() {
		return IterableTools.transform(this.getAllJavaPersistentTypesUnique(), PersistentType.MAPPING_TRANSFORMER);
	}

	// ***** metadata conversion
	public boolean hasConvertibleJavaQueries() {
		return ! this.getConvertibleJavaQueries().isEmpty();
	}

	public void convertJavaQueries(EntityMappings entityMappings, IProgressMonitor monitor) {
		OrmQueryContainer queryContainer = entityMappings.getQueryContainer();
		ArrayList<Query> convertibleJavaQueries = this.getConvertibleJavaQueries();
		SubMonitor subMonitor = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, convertibleJavaQueries.size());
		for (Query query : convertibleJavaQueries) {
			this.convertJavaQuery(queryContainer, (JavaQuery) query, subMonitor.newChild(1));
		}
		subMonitor.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaQuery(OrmQueryContainer queryContainer, JavaQuery query, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_QUERY, query.getName()));
		query.convertTo(queryContainer);
		query.delete();  // delete any converted queries
	}

	protected ArrayList<Query> getConvertibleJavaQueries() {
		return this.extractConvertibleJavaModels(this.getAllJavaQueries(), this.getMappingFileQueries());
	}


	// ********** managed types **********

	@SuppressWarnings("unchecked")
	public Iterable<ManagedType> getManagedTypes() {
		return IterableTools.concatenate(
				this.getMappingFileManagedTypes(),
				this.getClassRefManagedTypes(),
				this.getJarFileManagedTypes()
			);
	}

	protected Iterable<ManagedType> getMappingFileManagedTypes() {
		return IterableTools.children(this.getMappingFileRefs(), ManagedTypeContainer.TRANSFORMER);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ManagedType> getJavaManagedTypes() {
		return IterableTools.concatenate(
				this.getClassRefManagedTypes(),
				this.getJarFileManagedTypes()
			);
	}

	/**
	 * Return the non-<code>null</code> class ref managed types,
	 * both specified and implied.
	 */
	protected Iterable<ManagedType> getClassRefManagedTypes() {
		return IterableTools.removeNulls(this.getClassRefManagedTypes_());
	}

	/**
	 * Both specified and implied. May contain <code>null</code>s.
	 * @see #getClassRefManagedTypes()
	 */
	protected Iterable<ManagedType> getClassRefManagedTypes_() {
		return IterableTools.transform(this.getClassRefs(), CLASS_REF_MANAGED_TYPE_TRANSFORMER);
	}

	protected static final Transformer<ClassRef, ManagedType> CLASS_REF_MANAGED_TYPE_TRANSFORMER = TransformerTools.upcast(ClassRef.JAVA_MANAGED_TYPE_TRANSFORMER);

	/**
	 * We only get <em>annotated</em> types from jar files.
	 */
	protected Iterable<ManagedType> getJarFileManagedTypes() {
		return IterableTools.children(this.getJarFileRefs(), ManagedTypeContainer.TRANSFORMER);
	}

	public ManagedType getManagedType(String typeName) {
		return typeName == null ? null : this.managedTypeMap.get(typeName);
	}

	protected void rebuildManagedTypeMap() {
		synchronized (this.managedTypeMap) {
			this.managedTypeMap.clear();
			for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
				for (ManagedType mt : mappingFileRef.getManagedTypes()) {
					if (mt.getName() != null) {
						if (! this.managedTypeMap.containsKey(mt.getName())) {
							this.managedTypeMap.put(mt.getName(), mt);
						}
					}
				}
			}
			for (ClassRef classRef : this.getClassRefs()) {
				ManagedType mt = classRef.getJavaManagedType();
				if (mt != null && mt.getName() != null) {
					if (! this.managedTypeMap.containsKey(mt.getName())) {
						this.managedTypeMap.put(mt.getName(), mt);
					}
				}
			}
			for (JarFileRef jarFileRef : this.getJarFileRefs()) {
				for (ManagedType mt : jarFileRef.getManagedTypes()) {
					if (mt.getName() != null) {
						if (! this.managedTypeMap.containsKey(mt.getName())) {
							this.managedTypeMap.put(mt.getName(), mt);
						}
					}
				}
			}
		}
	}

	/**
	 * Ignore implied class refs and jar files.
	 */
	public boolean specifiesManagedType(String typeName) {
		for (ClassRef classRef : this.getSpecifiedClassRefs()) {
			if (classRef.isFor(typeName)) {
				return true;
			}
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.getManagedType(typeName) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return a list of <em>all</em> the persistence unit's Java persistent
	 * types (including those referenced by the mapping files that are not
	 * marked "metadata complete") with those with duplicate names removed.
	 * Although this may not always be true, assume persistent types with
	 * the same name reference the same Java type. (<strong>NB:</strong>
	 * It's possible that a Java class in a jar file has the same name as a
	 * Java class in the project and they be different....)
	 * <p>
	 * This is really only useful for the calculation of generators and queries,
	 * which can be defined in Java annotations but still be "active" even
	 * though their corresponding Java types/attributes have been overridden in
	 * a mapping file.
	 * <p>
	 * The order of precedence:<ul>
	 * <li>mapping files
	 * <li>persistence unit class refs
	 * <li>jar files
	 * </ul>
	 */
	protected Iterable<PersistentType> getAllJavaPersistentTypesUnique() {
		// order is significant(?)
		HashMap<String, PersistentType> map = new HashMap<String, PersistentType>();
		this.addPersistentTypesTo(this.getJarFilePersistentTypes(), map);
		this.addPersistentTypesTo(this.getClassRefPersistentTypes(), map);
		this.addPersistentTypesTo(this.getMappingFileJavaPersistentTypes(), map);
		return map.values();
	}

	/**
	 * Add the specified persistent types to
	 * the specified map keyed by persistent type name.
	 */
	protected void addPersistentTypesTo(Iterable<? extends PersistentType> persistentTypes, HashMap<String, PersistentType> map) {
		for (PersistentType pt : persistentTypes) {
			String ptName = pt.getName();
			if (ptName != null) {
				map.put(ptName, pt);
			}
		}
	}

	/**
	 * Return the non-<code>null</code> mapping file Java persistent types;
	 * i.e. the Java persistent types corresponding to the mapping file
	 * persistent types that are not marked "metadata complete".
	 * @see #getAllJavaPersistentTypesUnique()
	 */
	protected Iterable<PersistentType> getMappingFileJavaPersistentTypes() {
		return IterableTools.removeNulls(this.getMappingFileJavaPersistentTypes_());
	}

	/**
	 * The returned list will contain a <code>null</code> for each mapping file
	 * persistent type that does not correspond to an existing Java type or is
	 * marked "metadata complete".
	 * @see #getMappingFileJavaPersistentTypes()
	 */
	protected Iterable<PersistentType> getMappingFileJavaPersistentTypes_() {
		return IterableTools.transform(this.getMappingFilePersistentTypes(), PersistentType.OVERRIDDEN_PERSISTENT_TYPE_TRANSFORMER);
	}

	public Iterable<PersistentType> getPersistentTypes() {
		return IterableTools.downCast(IterableTools.filter(this.getManagedTypes(), TYPE_IS_PERSISTENT_TYPE));
	}

	protected static final Predicate<ManagedType> TYPE_IS_PERSISTENT_TYPE = new TypeIsPersistentType();
	public static class TypeIsPersistentType
		extends PredicateAdapter<ManagedType>
	{
		@Override
		public boolean evaluate(ManagedType mt) {
			return mt.getManagedTypeType() == PersistentType.class;
		}
	}


	public PersistentType getPersistentType(String typeName) {
		ManagedType mt = this.getManagedType(typeName);
		if ((mt != null) && (mt.getManagedTypeType() == PersistentType.class)) {
			return (PersistentType) mt;
		}
		return null;
	}

	protected Iterable<PersistentType> getMappingFilePersistentTypes() {
		return IterableTools.children(this.getMappingFileRefs(), PersistentTypeContainer.TRANSFORMER);
	}

	protected Iterable<PersistentType> getClassRefPersistentTypes() {
		return IterableTools.downCast(IterableTools.filter(
			this.getClassRefManagedTypes(), 
			TYPE_IS_PERSISTENT_TYPE));
	}

	protected Iterable<PersistentType> getJarFilePersistentTypes() {
		return IterableTools.downCast(IterableTools.filter(
			this.getJarFileManagedTypes(), 
			TYPE_IS_PERSISTENT_TYPE));
	}

	public Iterable<PersistentType> getJavaPersistentTypes() {
		return IterableTools.downCast(IterableTools.filter(
			this.getJavaManagedTypes(), 
			TYPE_IS_PERSISTENT_TYPE));
	}


	// ********** type mappings **********

	public Entity getEntity(String typeName) {
		TypeMapping typeMapping = this.getTypeMapping(typeName);
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public Embeddable getEmbeddable(String typeName) {
		TypeMapping typeMapping = this.getTypeMapping(typeName);
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}
	
	public IdTypeMapping getIdTypeMapping(String typeName) {
		TypeMapping typeMapping = this.getTypeMapping(typeName);
		return (typeMapping instanceof IdTypeMapping) ? (IdTypeMapping) typeMapping : null;
	}
	
	// TODO bjv - this should probably *not* return Java type mappings when PU is "metadata complete"...
	protected TypeMapping getTypeMapping(String typeName) {
		PersistentType persistentType = this.getPersistentType(typeName);
		return (persistentType == null) ? null : persistentType.getMapping();
	}
	
	public Iterable<Entity> getEntities() {
		return this.filterToEntities(this.getTypeMappings());
	}

	protected Iterable<Entity> filterToEntities(Iterable<TypeMapping> typeMappings) {
		return new SubIterableWrapper<TypeMapping, Entity>(this.filterToEntities_(typeMappings));
	}

	protected Iterable<TypeMapping> filterToEntities_(Iterable<TypeMapping> typeMappings) {
		return IterableTools.filter(typeMappings, PredicateTools.<TypeMapping>instanceOf(Entity.class));
	}

	// TODO bjv - this should probably *not* return Java type mappings when PU is "metadata complete"...
	protected Iterable<TypeMapping> getTypeMappings() {
		return IterableTools.transform(this.getPersistentTypes(), PersistentType.MAPPING_TRANSFORMER);
	}

	/**
	 * Return the "active" entities.
	 * @see #getActiveTypeMappings()
	 */
	protected Iterable<Entity> getActiveEntities() {
		return this.filterToEntities(this.getActiveTypeMappings());
	}

	/**
	 * Return the "active" type mappings, i.e. the mapping file type mappings and
	 * the Java type mappings that are not "overridden" by mapping file
	 * type mappings (by full qualified class name).
	 */
	protected Iterable<TypeMapping> getActiveTypeMappings(){
		ArrayList<TypeMapping> result = ListTools.list(this.getMappingFileTypeMappings());

		HashSet<String> mappingFileClassNames = this.convertToClassNames(result);
		HashMap<String, ArrayList<TypeMapping>> javaTypeMappings = this.mapTypeMappingsByClassName(this.getJavaTypeMappings());
		for (Map.Entry<String, ArrayList<TypeMapping>> entry : javaTypeMappings.entrySet()) {
			if ( ! mappingFileClassNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	/**
	 * Return all the type mappings defined in the persistence unit's mapping
	 * files (i.e. excluding the Java type mappings).
	 */
	protected Iterable<TypeMapping> getMappingFileTypeMappings() {
		return IterableTools.transform(this.getMappingFilePersistentTypes(), PersistentType.MAPPING_TRANSFORMER);
	}

	protected HashSet<String> convertToClassNames(Collection<? extends TypeMapping> typeMappings) {
		HashSet<String> classNames = new HashSet<String>(typeMappings.size());
		for (TypeMapping typeMapping : typeMappings) {
			classNames.add(typeMapping.getPersistentType().getName());
		}
		return classNames;
	}

	/**
	 * Return a map of the type mappings keyed by full qualified class name.
	 * Since there can be duplicate (erroneously) class names,
	 * each class name is mapped to a <em>list</em> of type mappings.
	 */
	protected <M extends TypeMapping> HashMap<String, ArrayList<M>> mapTypeMappingsByClassName(Iterable<M> typeMappings) {
		HashMap<String, ArrayList<M>> map = new HashMap<String, ArrayList<M>>();
		for (M typeMapping : typeMappings) {
			String typeMappingName = typeMapping.getPersistentType().getName();
			ArrayList<M> list = map.get(typeMappingName);
			if (list == null) {
				list = new ArrayList<M>();
				map.put(typeMappingName, list);
			}
			list.add(typeMapping);
		}
		return map;
	}

	/**
	 * These may be overridden in the mapping files.
	 * @see #getJavaPersistentTypes()
	 */
	protected Iterable<TypeMapping> getJavaTypeMappings() {
		return IterableTools.transform(this.getJavaPersistentTypes(), PersistentType.MAPPING_TRANSFORMER);
	}


	// ********** synchronize classes **********

	public void synchronizeClasses(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 4);

		// gather up all the annotated Java types and types listed in the mapping files
		HashSet<JavaResourceAbstractType> newClasses = CollectionTools.set(this.getJpaProject().getPotentialJavaSourceTypes());
		HashSet<String> mappingFileTypeNames = this.getMappingFileTypeNames();

		// calculate the class refs to be removed
		ArrayList<ClassRef> deadClassRefs = new ArrayList<ClassRef>();
		for (ClassRef specifiedClassRef : this.getSpecifiedClassRefs()) {
			JavaManagedType specifiedJMT = specifiedClassRef.getJavaManagedType();
			if (specifiedJMT == null) {
				// specified type cannot be resolved
				deadClassRefs.add(specifiedClassRef);
			} else {
				JavaResourceType specifiedJRT = specifiedJMT.getJavaResourceType();
				if ( ! newClasses.remove(specifiedJRT)) {
					// specified type is resolved but not annotated
					deadClassRefs.add(specifiedClassRef);
				} else if (mappingFileTypeNames.contains(specifiedJRT.getTypeBinding().getQualifiedName())) {
					// specified type is resolved but also listed in a mapping file
					deadClassRefs.add(specifiedClassRef);
				}
			}
		}

		// now check for any remaining newly-discovered types that are already listed in a mapping file
		for (Iterator<JavaResourceAbstractType> stream = newClasses.iterator(); stream.hasNext(); ) {
			JavaResourceAbstractType javaType = stream.next();
			if (mappingFileTypeNames.contains(javaType.getTypeBinding().getQualifiedName())) {
				stream.remove();
			}
		}
		if (sm.isCanceled()) {
			return;
		}
		sm.worked(1);

		this.removeSpecifiedClassRefs(deadClassRefs);
		if (sm.isCanceled()) {
			return;
		}
		sm.worked(1);

		this.addSpecifiedClassRefs(newClasses);
		if (sm.isCanceled()) {
			return;
		}
		sm.worked(1);

		// any changes to the XML file will update the specified class refs list
		this.getXmlPersistenceUnit().sortClasses();
		sm.worked(1);
	}

	/**
	 * Return the names of all the types specified in the persistence unit's
	 * mapping files.
	 */
	protected HashSet<String> getMappingFileTypeNames() {
		HashSet<String> result = new HashSet<String>();
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			for (ManagedType managedType : mappingFileRef.getManagedTypes()) {
				result.add(managedType.getName());
			}
		}
		return result;
	}
	
	// ********** add persistent types **********

	/**
	 * Annotate the given types with the given mapping key
	 * Specify the types in the persistence.xml if listInPersistenceXml is true.
	 */
	public void addPersistentTypes(PersistentType.Config[] typeConfigs, boolean listInPersistenceXml, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 10);
		this.annotateClasses(typeConfigs, sm.newChild(6));
		if (listInPersistenceXml) {
			this.listInPersistenceXml(typeConfigs, sm.newChild(4));
		} else {
			sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_UPDATING_JPA_MODEL);
			//TODO have to call this since I am modifying only the Java resource model
			//in the non-'list in persisistence.xml' case
			this.getJpaProject().synchronizeContextModel();
			sm.worked(4);
		}
	}

	protected void annotateClasses(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, typeConfigs.length);
		sm.setTaskName(JptJpaCoreMessages.MAKE_PERSISTENT_PROCESSING_JAVA_CLASSES);
		// TODO modify the context model - need to have API for creating a JavaPersistentType with a given mappingKey.
		// be careful not to modify the context model in such a way that you end up with updates being run for
		// every persistent type added.
		for (PersistentType.Config typeConfig : typeConfigs) {
			if (sm.isCanceled()) {
				return;
			}
			String typeName = typeConfig.getName();
			sm.subTask(NLS.bind(JptJpaCoreMessages.MAKE_PERSISTENT_ANNOTATING_CLASS, typeName));
			JavaResourceAbstractType type = this.getJpaProject().getJavaResourceType(typeName);
			type.addAnnotation(this.getJavaTypeMappingDefinition(typeConfig.getMappingKey()).getAnnotationName());
			sm.worked(1);
		}
	}

	protected void listInPersistenceXml(PersistentType.Config[] typeConfigs, IProgressMonitor pm) {
		SubMonitor sm = SubMonitor.convert(pm, 11);
		sm.setTaskName(JptJpaCoreMessages.MAKE_PERSISTENT_LISTING_IN_PERSISTENCE_XML);
		Collection<XmlJavaClassRef> addedXmlClassRefs = new ArrayList<XmlJavaClassRef>();
		Collection<ClassRef> addedClassRefs = new ArrayList<ClassRef>();
		for (PersistentType.Config typeConfig : typeConfigs) {
			String typeName = typeConfig.getName();
			XmlJavaClassRef xmlClassRef = this.buildXmlJavaClassRef(typeName);
			addedXmlClassRefs.add(xmlClassRef);
			addedClassRefs.add(this.buildClassRef(xmlClassRef));
		}
		if (sm.isCanceled()) {
			return;
		}
		sm.worked(1);
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_UPDATING_JPA_MODEL);
		this.specifiedClassRefContainer.addAll(this.getSpecifiedClassRefsSize(), addedClassRefs);
		sm.worked(5);
		sm.subTask(JptJpaCoreMessages.MAKE_PERSISTENT_ADD_TO_PERSISTENCE_XML_RESOURCE_MODEL);
		this.xmlPersistenceUnit.getClasses().addAll(addedXmlClassRefs);
		sm.worked(5);
	}

	protected JavaTypeMappingDefinition getJavaTypeMappingDefinition(String key) {
		for (JavaTypeMappingDefinition definition : this.getJpaPlatform().getJavaTypeMappingDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), key)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}
	
	
	// ********** add classes to persistence unit **********

	public void addClasses(Iterable<String> classNames, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, IterableTools.size(classNames));
		for (String className : classNames) {
			if(!classRefExists(className)) {
				this.addSpecifiedClassRef(className);
			}
			sm.worked(1);
		}
		if (sm.isCanceled()) {
			return;
		}
		
		this.getXmlPersistenceUnit().sortClasses();
		sm.worked(1);
	}
	
	private boolean classRefExists(String className) {
		for (ClassRef classRef : this.getSpecifiedClassRefs()) {
			if( classRef.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	// ********** misc **********

	public XmlPersistenceUnit getXmlPersistenceUnit() {
		return this.xmlPersistenceUnit;
	}

	protected HashSet<String> convertToNames(Collection<? extends JpaNamedContextModel> nodes) {
		HashSet<String> names = new HashSet<String>(nodes.size());
		for (JpaNamedContextModel node : nodes) {
			names.add(node.getName());
		}
		return names;
	}

	protected <N extends JpaNamedContextModel> HashMap<String, ArrayList<N>> mapByName(Iterable<N> nodes) {
		HashMap<String, ArrayList<N>> map = new HashMap<String, ArrayList<N>>();
		for (N node : nodes) {
			String nodeName = node.getName();
			ArrayList<N> list = map.get(nodeName);
			if (list == null) {
				list = new ArrayList<N>();
				map.put(nodeName, list);
			}
			list.add(node);
		}
		return map;
	}

	/**
	 * Return the Java models that are neither overridden nor duplicated
	 * (by default any Java models with the same name are "duplicates").
	 */
	protected <M extends JpaNamedContextModel> ArrayList<M> extractConvertibleJavaModels(Iterable<M> allJavaModels, Iterable<M> mappingFileModels) {
		ArrayList<M> convertibleModels = new ArrayList<M>();

		HashSet<String> mappingFileModelNames = this.convertToNames(ListTools.list(mappingFileModels));
		HashMap<String, ArrayList<M>> allJavaModelsByName = this.mapByName(allJavaModels);
		for (Map.Entry<String, ArrayList<M>> entry : allJavaModelsByName.entrySet()) {
			String javaModelName = entry.getKey();
			if (StringTools.isBlank(javaModelName)) {
				continue;  // ignore any nodes with an empty name(?)
			}
			ArrayList<M> javaModelsWithSameName = entry.getValue();
			if ((javaModelsWithSameName.size() == 1) && ! mappingFileModelNames.contains(javaModelName)) {
				convertibleModels.add(javaModelsWithSameName.get(0));
			}
		}

		return convertibleModels;
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

	public Iterable<String> getPackageNames() {
		Set<String> packageNames = new HashSet<String>();
		for (ManagedType mType : this.getJavaManagedTypes()) {
			JavaResourceType jrt = mType.getJavaResourceType();
			packageNames.add(jrt.getTypeBinding().getPackageName());
		}
		return packageNames;
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMappingFiles(messages, reporter);
		this.validateClassRefs(messages, reporter);
		this.validateJarFileRefs(messages, reporter);
		this.validateProperties(messages, reporter);
		this.validateGenerators(messages, reporter);
		this.validateQueries(messages, reporter);
		this.validateEntityNames(messages);
	}

	protected void validateMappingFiles(List<IMessage> messages, IReporter reporter) {
		this.checkForMultiplePersistenceUnitMetadata(messages);
		this.checkForDuplicateMappingFileRefs(messages);
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			mappingFileRef.validate(messages, reporter);
		}
		this.checkForDuplicateMappingFileClasses(messages);
	}

	protected void checkForMultiplePersistenceUnitMetadata(List<IMessage> messages) {
		ArrayList<MappingFileRef> pumdMappingFileRefs = ListTools.list(this.getPersistenceUnitMetadataMappingFileRefs());
		if (pumdMappingFileRefs.size() > 1) {
			for (MappingFileRef mappingFileRef : pumdMappingFileRefs) {
				messages.add(
					this.buildValidationMessage(
						mappingFileRef.getMappingFile(),
						mappingFileRef.getPersistenceUnitMetadata().getValidationTextRange(),
						JptJpaCoreValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA,
						mappingFileRef.getFileName()
					)
				);
			}
		}
	}

	protected Iterable<MappingFileRef> getPersistenceUnitMetadataMappingFileRefs() {
		return IterableTools.filter(this.getMappingFileRefs(), MappingFileRef.PERSISTENCE_UNIT_METADATA_EXISTS);
	}

	protected void checkForDuplicateMappingFileRefs(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<MappingFileRef>> entry : this.mapMappingFileRefsByFileName().entrySet()) {
			String fileName = entry.getKey();
			if (StringTools.isNotBlank(fileName)) {
				ArrayList<MappingFileRef> dups = entry.getValue();
				if (dups.size() > 1) {
					for (MappingFileRef dup : dups) {
						messages.add(
							this.buildValidationMessage(
								dup,
								dup.getValidationTextRange(),
								JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
								fileName
							)
						);
					}
				}
			}
		}
	}

	/**
	 * Return the persistence unit's mapping file refs, both default and
	 * specified, keyed by their file names.
	 */
	protected HashMap<String, ArrayList<MappingFileRef>> mapMappingFileRefsByFileName() {
		HashMap<String, ArrayList<MappingFileRef>> map = new HashMap<String, ArrayList<MappingFileRef>>(this.getMappingFileRefsSize());
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			String fileName = mappingFileRef.getFileName();
			ArrayList<MappingFileRef> list = map.get(fileName);
			if (list == null) {
				list = new ArrayList<MappingFileRef>();
				map.put(fileName, list);
			}
			list.add(mappingFileRef);
		}
		return map;
	}

	protected void checkForDuplicateMappingFileClasses(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<ManagedType>> entry : this.mapMappingFileManagedTypesByName().entrySet()) {
			String mtName = entry.getKey();
			if (StringTools.isNotBlank(mtName)) {
				ArrayList<ManagedType> dups = entry.getValue();
				if (dups.size() > 1) {
					for (ManagedType dup : dups) {
						messages.add(
							this.buildValidationMessage(
								dup,
								dup.getValidationTextRange(),
								JptJpaCoreValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS,
								mtName
							)
						);
					}
				}
			}
		}
	}

	/**
	 * Return the persistence unit's mapping file managed types
	 * keyed by their class names.
	 */
	protected HashMap<String, ArrayList<ManagedType>> mapMappingFileManagedTypesByName() {
		HashMap<String, ArrayList<ManagedType>> map = new HashMap<String, ArrayList<ManagedType>>();
		for (ManagedType managedType : this.getMappingFileManagedTypes()) {
			String mtName = managedType.getName();
			ArrayList<ManagedType> list = map.get(mtName);
			if (list == null) {
				list = new ArrayList<ManagedType>();
				map.put(mtName, list);
			}
			list.add(managedType);
		}
		return map;
	}

	protected void validateClassRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateClassRefs(messages);
		for (ClassRef classRef : this.getClassRefs()) {
			classRef.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateClassRefs(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<ClassRef>> entry : this.mapClassRefsByName().entrySet()) {
			String className = entry.getKey();
			if (StringTools.isNotBlank(className)) {
				ArrayList<ClassRef> dups = entry.getValue();
				if (dups.size() > 1) {
					for (ClassRef dup : dups) {
						messages.add(
							this.buildValidationMessage(
								dup,
								dup.getValidationTextRange(),
								JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
								className
							)
						);
					}
				}
			}
		}
	}

	/**
	 * Return the persistence unit's class refs, both specified and implied,
	 * keyed by their class names.
	 */
	protected HashMap<String, ArrayList<ClassRef>> mapClassRefsByName() {
		HashMap<String, ArrayList<ClassRef>> map = new HashMap<String, ArrayList<ClassRef>>(this.getClassRefsSize());
		for (ClassRef classRef : this.getClassRefs()) {
			String refName = classRef.getClassName();
			ArrayList<ClassRef> list = map.get(refName);
			if (list == null) {
				list = new ArrayList<ClassRef>();
				map.put(refName, list);
			}
			list.add(classRef);
		}
		return map;
	}

	protected void validateJarFileRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateJarFileRefs(messages);
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			jarFileRef.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateJarFileRefs(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<JarFileRef>> entry : this.mapJarFileRefsByName().entrySet()) {
			String fileName = entry.getKey();
			if (StringTools.isNotBlank(fileName)) {
				ArrayList<JarFileRef> dups = entry.getValue();
				if (dups.size() > 1) {
					for (JarFileRef dup : dups) {
						messages.add(
							this.buildValidationMessage(
								dup,
								dup.getValidationTextRange(),
								JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE,
								fileName
							)
						);
					}
				}
			}
		}
	}

	/**
	 * Return the persistence unit's jar file refs
	 * keyed by their file names.
	 */
	protected HashMap<String, ArrayList<JarFileRef>> mapJarFileRefsByName() {
		HashMap<String, ArrayList<JarFileRef>> map = new HashMap<String, ArrayList<JarFileRef>>(this.getJarFileRefsSize());
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			String refName = jarFileRef.getFileName();
			ArrayList<JarFileRef> list = map.get(refName);
			if (list == null) {
				list = new ArrayList<JarFileRef>();
				map.put(refName, list);
			}
			list.add(jarFileRef);
		}
		return map;
	}

	protected abstract void validateProperties(List<IMessage> messages, IReporter reporter);

	/**
	 * We validate generators here because Java persistent types that are
	 * overridden in the <code>orm.xml</code> file are
	 * not validated, but they may contain generators that are still "active"
	 * and need to be validated
	 * (i.e. a generator is <em>not</em> overridden when its entity or ID
	 * mapping is overridden in the <code>orm.xml</code> file).
	 * <p>
	 * <strong>NB:</strong> <em>Any</em> <code>orm.xml</code> generator can
	 * override <em>any</em> Java generator with the same name; they need not
	 * be defined on the same entity or ID mapping. Just a bit inconsistent with
	 * the typical "override" in JPA....
	 */
	protected void validateGenerators(List<IMessage> messages, IReporter reporter) {
		this.checkForGeneratorsWithSameName(messages);
		for (Generator generator : this.getGenerators()) {
			this.validate(generator, messages, reporter);
		}
	}

	protected void checkForGeneratorsWithSameName(List<IMessage> messages) {
		HashMap<String, ArrayList<Generator>> generatorsByName = this.mapByName(this.getGenerators());
		for (Map.Entry<String, ArrayList<Generator>> entry : generatorsByName.entrySet()) {
			String generatorName = entry.getKey();
			if (StringTools.isNotBlank(generatorName)) {  // ignore empty names
				ArrayList<Generator> dups = entry.getValue();
				if (dups.size() > 1) {
					this.validateGeneratorsWithSameName(generatorName, dups, messages);
				}
			}
		}
	}

	/**
	 * All the specified generators have the same specified name.
	 * Mark them appropriately.
	 */
	protected void validateGeneratorsWithSameName(String generatorName, ArrayList<Generator> dups, List<IMessage> messages) {
		for (Generator dup : dups) {
			if (dup.supportsValidationMessages()) {
				messages.add(
					this.buildValidationMessage(
						dup,
						dup.getNameTextRange(),
						JptJpaCoreValidationMessages.GENERATOR_DUPLICATE_NAME,
						generatorName
					)
				);
			}
		}
	}

	protected void validate(Generator generator, List<IMessage> messages, IReporter reporter) {
		if (generator.supportsValidationMessages()) {
			generator.validate(messages, reporter);
		}
	}

	/**
	 * <strong>NB:</strong> We validate queries here.
	 * @see #validateGenerators(List, IReporter)
	 */
	protected void validateQueries(List<IMessage> messages, IReporter reporter) {
		this.checkForQueriesWithSameName(messages);

		JpaJpqlQueryHelper queryHelper = this.createJpqlQueryHelper();

		for (Query query : this.getQueries()) {
			this.validate(query, queryHelper, messages, reporter);
		}
	}

	protected void checkForQueriesWithSameName(List<IMessage> messages) {
		HashMap<String, ArrayList<Query>> queriesByName = this.mapByName(this.getQueries());
		for (Map.Entry<String, ArrayList<Query>> entry : queriesByName.entrySet()) {
			String queryName = entry.getKey();
			if (StringTools.isNotBlank(queryName)) {  // ignore empty names
				ArrayList<Query> dups = entry.getValue();
				if (dups.size() > 1) {
					this.validateQueriesWithSameName(queryName, dups, messages);
				}
			}
		}
	}

	protected void validateQueriesWithSameName(String queryName, ArrayList<Query> dups, List<IMessage> messages) {
		for (Query dup : dups) {
			if (dup.supportsValidationMessages()) {
				messages.add(
					this.buildValidationMessage(
						dup,
						dup.getNameTextRange(),
						JptJpaCoreValidationMessages.QUERY_DUPLICATE_NAME,
						queryName
					)
				);
			}
		}
	}

	protected void validate(Query query, JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		if (query.supportsValidationMessages()) {
			query.validate(queryHelper, messages, reporter);
		}
	}

	protected void validateEntityNames(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<Entity>> entry : this.mapTypeMappingsByName(this.getActiveEntities()).entrySet()) {
			String entityName = entry.getKey();
			if (StringTools.isNotBlank(entityName)) {
				ArrayList<Entity> dups = entry.getValue();
				if (dups.size() > 1) {
					this.validateEntitiesWithSameName(entityName, dups, messages);
				}
			}
		}
	}

	/**
	 * Return a map of the type mappings keyed by type mapping name (typically
	 * the short class name).
	 * Since there can be (erroneously) duplicate type mapping names,
	 * each type mapping name is mapped to a <em>list</em> of type mappings.
	 */
	protected <M extends TypeMapping> HashMap<String, ArrayList<M>> mapTypeMappingsByName(Iterable<M> typeMappings) {
		HashMap<String, ArrayList<M>> map = new HashMap<String, ArrayList<M>>();
		for (M typeMapping : typeMappings) {
			String typeMappingName = typeMapping.getName();
			ArrayList<M> list = map.get(typeMappingName);
			if (list == null) {
				list = new ArrayList<M>();
				map.put(typeMappingName, list);
			}
			list.add(typeMapping);
		}
		return map;
	}

	protected void validateEntitiesWithSameName(String entityName, ArrayList<Entity> dups, List<IMessage> messages) {
		for (Entity dup : dups) {
			if (dup.supportsValidationMessages()) {
				messages.add(
					this.buildValidationMessage(
						dup,
						dup.getNameTextRange(),
						JptJpaCoreValidationMessages.ENTITY_NAME_DUPLICATED,
						entityName
					)
				);
			}
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlPersistenceUnit.getValidationTextRange();
		return (textRange != null) ? textRange : this.getPersistence().getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(final IType type) {
		return IterableTools.children(this.getSpecifiedClassRefs(), new DeleteTypeRefactoringParticipant.DeleteTypeEditsTransformer(type));
	}

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file) {
		return IterableTools.children(this.getSpecifiedMappingFileRefs(), new MappingFileRefactoringParticipant.DeleteMappingFileEditsTransformer(file));
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
					this.createSpecifiedClassRefRenameTypeEdits(originalType, newName),
					this.createPersistenceUnitPropertiesRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.children(this.getSpecifiedClassRefs(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenameTypeEdits(IType originalType, String newName) {
		return this.options.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
			this.createSpecifiedClassRefMoveTypeEdits(originalType, newPackage),
			this.createPersistenceUnitPropertiesMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(this.getSpecifiedClassRefs(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.options.createMoveTypeEdits(originalType, newPackage);
	}


	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
			this.createSpecifiedClassRefRenamePackageEdits(originalPackage, newName),
			this.createPersistenceUnitPropertiesRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(this.getSpecifiedClassRefs(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.options.createRenamePackageEdits(originalPackage, newName);
	}

	public Iterable<ReplaceEdit> createRenameFolderEdits(final IFolder originalFolder, final String newName) {
		return this.createMappingFileRefRenameFolderEdits(originalFolder, newName);
	}

	protected Iterable<ReplaceEdit> createMappingFileRefRenameFolderEdits(IFolder originalFolder, String newName) {
		return IterableTools.children(this.getSpecifiedMappingFileRefs(), new MappingFileRefactoringParticipant.RenameFolderEditsTransformer(originalFolder, newName));
	}

	public Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName) {
		return IterableTools.children(this.getSpecifiedMappingFileRefs(), new MappingFileRefactoringParticipant.RenameMappingFileEditsTransformer(originalFile, newName));
	}

	public int findInsertLocationForMappingFileRef() {
		return this.xmlPersistenceUnit.getLocationToInsertMappingFileRef();
	}

	public Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath destination) {
		return IterableTools.children(this.getMappingFileRefs(), new MappingFileRefactoringParticipant.MoveMappingFileEditsTransformer(originalFile, destination));
	}

	public Iterable<ReplaceEdit> createMoveFolderEdits(final IFolder originalFolder, final IPath runtimeDestination) {
		return this.createMappingFileRefMoveFolderReplaceEdits(originalFolder, runtimeDestination);
	}

	protected Iterable<ReplaceEdit> createMappingFileRefMoveFolderReplaceEdits(IFolder originalFolder, IPath destination) {
		return IterableTools.children(this.getMappingFileRefs(), new MappingFileRefactoringParticipant.MoveFolderEditsTransformer(originalFolder, destination));
	}


	// ********** metamodel **********
	// put metamodel stuff here so it can be shared by Generic and EclipseLink implementations

	public void initializeMetamodel() {
		this.initializeMetamodelFiles_();
	}

	protected void initializeMetamodelFiles() {
		if (this.isJpa2_0Compatible()) {
			this.initializeMetamodelFiles_();
		}
	}

	protected void initializeMetamodelFiles_() {
		CollectionTools.addAll(this.metamodelFiles, this.getGeneratedMetamodelFiles());
	}

	protected Iterable<IFile> getGeneratedMetamodelFiles() {
		return IterableTools.transform(this.getGeneratedMetamodelTopLevelTypes(), JavaResourceModel.FILE_TRANSFORMER);
	}

	protected Iterable<JavaResourceAbstractType> getGeneratedMetamodelTopLevelTypes() {
		return ((JpaProject2_0) this.getJpaProject()).getGeneratedMetamodelTopLevelTypes();
	}

	/**
	 * Not the prettiest code....
	 */
	// TODO check monitor for cancel
	public IStatus synchronizeMetamodel(IProgressMonitor monitor) {
		HashMap<String, PersistentType> allPersistentTypes = this.getMetamodelPersistentTypes();

		// build a list of the top-level types and a tree of their associated
		// member types etc.
		ArrayList<MetamodelSourceType2_0> topLevelTypes = new ArrayList<MetamodelSourceType2_0>(allPersistentTypes.size());
		HashMap<String, Collection<MetamodelSourceType2_0>> memberTypeTree = new HashMap<String, Collection<MetamodelSourceType2_0>>();
		for (PersistentType type1_0 : allPersistentTypes.values()) {
			PersistentType2_0 type = (PersistentType2_0) type1_0;
			String declaringTypeName = type.getDeclaringTypeName();
			MetamodelSourceType2_0 memberType = type;
			while (true) {
				if (declaringTypeName == null) {
					topLevelTypes.add(memberType);
					break;  // stop at the top-level type
				}

				// associate the member type with its declaring type
				Collection<MetamodelSourceType2_0> memberTypes = memberTypeTree.get(declaringTypeName);
				if (memberTypes == null) {
					memberTypes = new ArrayList<MetamodelSourceType2_0>();
					memberTypeTree.put(declaringTypeName, memberTypes);
				}
				memberTypes.add(memberType);

				// move out to the member type's declaring type
				String memberTypeName = declaringTypeName;
				// check for a context persistent type
				memberType = (PersistentType2_0) allPersistentTypes.get(memberTypeName);
				if (memberType != null) {
					break;  // stop - this will be processed in the outer 'for' loop
				}
				// check for a Java resource persistent type
				JavaResourceAbstractType jrat = this.getJpaProject().getJavaResourceType(memberTypeName);
				if (jrat != null) {
					declaringTypeName = jrat.getDeclaringTypeName();
				} else {
					// check for a JDT type
					IType jdtType = this.findJdtType(memberTypeName);
					if (jdtType != null) {
						IType jdtDeclaringType = jdtType.getDeclaringType();
						declaringTypeName = (jdtDeclaringType == null) ? null : jdtDeclaringType.getFullyQualifiedName('.');
					} else {
						// assume we have a non-persistent top-level type...?
						declaringTypeName = null;
					}
				}
				if (declaringTypeName == null) {
					memberType = this.selectSourceType(topLevelTypes, memberTypeName);
				} else {
					memberType = this.selectSourceType(memberTypeTree.get(declaringTypeName), memberTypeName);
				}
				if (memberType != null) {
					break;  // stop - this type has already been processed
				}
				memberType = this.buildNonPersistentMetamodelSourceType(memberTypeName);
			}
		}

		// remove any top-level type whose name differs from another only by case,
		// since, on Windows, file names are case-insensitive :-(
		// sort the original list so we end up with the same top-level type
		// remaining every time (i.e. the one that sorts out first)
		Collections.sort(topLevelTypes, MetamodelSourceType2_0.COMPARATOR);
		HashSet<String> names = new HashSet<String>(topLevelTypes.size());
		for (Iterator<MetamodelSourceType2_0> stream = topLevelTypes.iterator(); stream.hasNext(); ) {
			MetamodelSourceType2_0 topLevelType = stream.next();
			// hopefully this is case-insensitive enough...
			if ( ! names.add(topLevelType.getName().toLowerCase())) {
				stream.remove();
			}
		}

		// copy the list of metamodel files...
		HashSet<IFile> deadMetamodelFiles = new HashSet<IFile>(this.metamodelFiles);
		this.metamodelFiles.clear();
		for (MetamodelSourceType2_0 topLevelType : topLevelTypes) {
			IFile metamodelFile = topLevelType.getMetamodelFile();
			// ...remove whatever files are still present...
			deadMetamodelFiles.remove(metamodelFile);
			// ...rebuild the list of metamodel files...
			if (this.fileIsGeneratedMetamodel(metamodelFile)) {  // only add files with the Dali tag
				this.metamodelFiles.add(metamodelFile);
			}
		}
		// ...delete the files that are now gone
		// [perform the deletes first - this is critical when a file has been
		// renamed by only altering its name's case; since we will try to write
		// out a new file that, on Windows, collides with the old file :-( ]
		for (IFile deadMetamodelFile : deadMetamodelFiles) {
			this.deleteMetamodelFile(deadMetamodelFile);
		}

		// now generate the metamodel classes
		for (MetamodelSourceType2_0 topLevelType : topLevelTypes) {
			topLevelType.synchronizeMetamodel(memberTypeTree);
		}
		return Status.OK_STATUS;
	}

	/**
	 * Gather up the persistent unit's types, eliminating duplicates;
	 * if we have persistent types with the same name in multiple locations,
	 * the last one we encounter wins
	 * (i.e. the classes in the <code>orm.xml</code> take precedence)
	 */
	protected HashMap<String, PersistentType> getMetamodelPersistentTypes() {
		HashMap<String, PersistentType> allPersistentTypes = new HashMap<String, PersistentType>();
		this.addPersistentTypesTo(this.getMetamodelJarFilePersistentTypes(), allPersistentTypes);
		this.addPersistentTypesTo(this.getMetamodelClassRefPersistentTypes(), allPersistentTypes);
		this.addPersistentTypesTo(this.getMetamodelMappingFilePersistentTypes(), allPersistentTypes);
		return allPersistentTypes;
	}

	/**
	 * Remove any types that are not to be used in the metamodel.
	 * @see PersistentType2_0#getMetamodelType()
	 */
	protected Iterable<PersistentType2_0> getMetamodelJarFilePersistentTypes() {
		return IterableTools.removeNulls(IterableTools.transform(this.getJarFilePersistentTypes2_0(), PersistentType2_0.METAMODEL_TYPE_TRANSFORMER));
	}

	protected Iterable<PersistentType2_0> getJarFilePersistentTypes2_0() {
		return IterableTools.downCast(this.getJarFilePersistentTypes());
	}

	/**
	 * Remove any types that are not to be used in the metamodel.
	 * @see PersistentType2_0#getMetamodelType()
	 */
	protected Iterable<PersistentType2_0> getMetamodelClassRefPersistentTypes() {
		return IterableTools.removeNulls(IterableTools.transform(this.getClassRefPersistentTypes2_0(), PersistentType2_0.METAMODEL_TYPE_TRANSFORMER));
	}

	protected Iterable<PersistentType2_0> getClassRefPersistentTypes2_0() {
		return IterableTools.downCast(this.getClassRefPersistentTypes());
	}

	/**
	 * Remove any types that are not to be used in the metamodel.
	 * @see PersistentType2_0#getMetamodelType()
	 */
	protected Iterable<PersistentType2_0> getMetamodelMappingFilePersistentTypes() {
		return IterableTools.removeNulls(IterableTools.transform(this.getMappingFilePersistentTypes2_0(), PersistentType2_0.METAMODEL_TYPE_TRANSFORMER));
	}

	protected Iterable<PersistentType2_0> getMappingFilePersistentTypes2_0() {
		return IterableTools.downCast(this.getMappingFilePersistentTypes());
	}

	protected MetamodelSourceType2_0 selectSourceType(Iterable<MetamodelSourceType2_0> types, String typeName) {
		if (types != null) {
			for (MetamodelSourceType2_0 type : types) {
				if (type.getName().equals(typeName)) {
					return type;
				}
			}
		}
		return null;
	}

	protected MetamodelSourceType2_0 buildNonPersistentMetamodelSourceType(String nonPersistentTypeName) {
		return new NonPersistentMetamodelSourceType(nonPersistentTypeName, this.getJpaProject());
	}

	protected IType findJdtType(String typeName) {
		try {
			return this.getJpaProject().getJavaProject().findType(typeName);
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.instance().logError(ex);
			return null;
		}
	}

	protected void deleteMetamodelFile(IFile file) {
		try {
			this.deleteMetamodelFile_(file);
		} catch (CoreException ex) {
			JptJpaCorePlugin.instance().logError(ex);
		}
	}

	protected void deleteMetamodelFile_(IFile file) throws CoreException {
		if (this.fileIsGeneratedMetamodel(file)) {
			file.delete(true, null);  // true = force
		}
	}

	protected boolean fileIsGeneratedMetamodel(IFile file) {
		return ((JpaProject2_0) this.getJpaProject()).getGeneratedMetamodelTopLevelType(file) != null;
	}

	public void disposeMetamodel() {
		this.metamodelFiles.clear();
	}


	// ***** Metamodel source for non-persistent types
	public static class NonPersistentMetamodelSourceType
		implements MetamodelSourceType2_0
	{
		protected final String name;
		protected final JpaProject jpaProject;
		protected final MetamodelSourceType2_0.Synchronizer metamodelSynchronizer;

		public NonPersistentMetamodelSourceType(String name, JpaProject jpaProject) {
			super();
			this.name = name;
			this.jpaProject = jpaProject;
			this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
		}

		protected MetamodelSourceType2_0.Synchronizer buildMetamodelSynchronizer() {
			return this.getJpaFactory().buildMetamodelSynchronizer(this);
		}

		protected JpaFactory2_0 getJpaFactory() {
			return (JpaFactory2_0) this.getJpaProject().getJpaPlatform().getJpaFactory();
		}

		public String getName() {
			return this.name;
		}

		public boolean isManaged() {
			return false;
		}

		public PersistentType getSuperPersistentType() {
			return null;
		}

		public ListIterable<? extends PersistentAttribute> getAttributes() {
			return EmptyListIterable.instance();
		}

		public IFile getMetamodelFile() {
			return this.metamodelSynchronizer.getFile();
		}

		public JpaProject getJpaProject() {
			return this.jpaProject;
		}

		public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
			this.metamodelSynchronizer.synchronize(memberTypeTree);
		}

		public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType2_0>> memberTypeTree) {
			this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
		}
	}
}
