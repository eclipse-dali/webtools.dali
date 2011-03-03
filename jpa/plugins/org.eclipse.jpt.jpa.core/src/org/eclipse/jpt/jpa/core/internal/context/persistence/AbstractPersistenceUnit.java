/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingFilePersistenceUnitDefaults2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JavaResourcePersistentType2_0;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
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
	extends AbstractPersistenceXmlContextNode
	implements PersistenceUnit2_0
{
	protected final XmlPersistenceUnit xmlPersistenceUnit;

	protected String name;

	protected Boolean specifiedExcludeUnlistedClasses;

	protected PersistenceUnitTransactionType specifiedTransactionType;
	protected PersistenceUnitTransactionType defaultTransactionType;

	protected String description;

	protected String provider;

	protected String jtaDataSource;
	protected String nonJtaDataSource;

	protected final Vector<MappingFileRef> specifiedMappingFileRefs = new Vector<MappingFileRef>();
	protected final SpecifiedMappingFileRefContainerAdapter specifiedMappingFileRefContainerAdapter = new SpecifiedMappingFileRefContainerAdapter();
	protected MappingFileRef impliedMappingFileRef;

	protected final Vector<JarFileRef> jarFileRefs = new Vector<JarFileRef>();
	protected final JarFileRefContainerAdapter jarFileRefContainerAdapter = new JarFileRefContainerAdapter();

	protected final Vector<ClassRef> specifiedClassRefs = new Vector<ClassRef>();
	protected final SpecifiedClassRefContainerAdapter specifiedClassRefContainerAdapter = new SpecifiedClassRefContainerAdapter();

	protected final Set<ClassRef> impliedClassRefs = Collections.synchronizedSet(new HashSet<ClassRef>());
	protected final ImpliedClassRefContainerAdapter impliedClassRefContainerAdapter = new ImpliedClassRefContainerAdapter();

	protected final Vector<Property> properties = new Vector<Property>();
	protected final PropertyContainerAdapter propertyContainerAdapter = new PropertyContainerAdapter();

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

	//****** JPA 2.0 features
	protected PersistenceUnitProperties connection;
	protected PersistenceUnitProperties options;

	protected SharedCacheMode specifiedSharedCacheMode;
	protected SharedCacheMode defaultSharedCacheMode;

	protected ValidationMode specifiedValidationMode;
	protected ValidationMode defaultValidationMode;

	protected final Set<IFile> metamodelFiles = Collections.synchronizedSet(new HashSet<IFile>());


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
		this.initializeProperties();

		this.initializeSpecifiedMappingFileRefs();
		this.initializeJarFileRefs();
		this.initializeSpecifiedClassRefs();

		this.specifiedSharedCacheMode = this.buildSpecifiedSharedCacheMode();
		this.specifiedValidationMode = this.buildSpecifiedValidationMode();

		this.initializeMetamodelFiles();
	}

	/**
	 * These lists are just copies of what is distributed across the context
	 * model; so, if they have (virtually) changed, the resulting update has
	 * already been triggered. We don't need to trigger another one here.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(GENERATORS_COLLECTION);
		nonUpdateAspectNames.add(QUERIES_COLLECTION);
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
		this.synchronizeNodesWithResourceModel(this.getImpliedClassRefs());

		this.syncProperties();

		this.setSpecifiedSharedCacheMode_(this.buildSpecifiedSharedCacheMode());
		this.setSpecifiedValidationMode_(this.buildSpecifiedValidationMode());
	}

	// TODO bjv calculate generators and queries directly...
	/**
	 * The 'generators' and 'queries' collections are simply cleared out with
	 * each "update" and completely rebuilt as the "update" cascades through
	 * the persistence unit. When the persistence unit's "update" is
	 * complete, the collections have been populated and we fire change events.
	 * <p>
	 * Note: Clearing and rebuilding 'generators' and 'queries' should work OK
	 * since JPA project "synchronization" is single-threaded.
	 * (Either the it takes place synchronously on a single thread or
	 * asynchronously in jobs that are synchronized via scheduling rules.)
	 * <p>
	 * See calls to the following:<ul>
	 * <li>{@link #addGenerator(Generator)}
	 * <li>{@link #addQuery(Query)}
	 * </ul>
	 * [see bug 311093 before attempting to change how this works]
	 */
	@Override
	public void update() {
		super.update();

		// see method comment above
		this.generators.clear();
		this.queries.clear();

		this.setDefaultTransactionType(this.buildDefaultTransactionType());

		// update specified class refs before mapping file refs because of
		// JpaFile root structure nodes - we want the mapping file to "win",
		// as it would in a JPA runtime implementation
		this.updateNodes(this.getSpecifiedClassRefs());

		this.updateNodes(this.getSpecifiedMappingFileRefs());
		this.updateImpliedMappingFileRef();

		this.updateNodes(this.getJarFileRefs());

		// update the implied class refs after all the other types, both
		// specified here and specified in the mapping files, are in place
		this.updateImpliedClassRefs();

		this.updateNodes(this.getProperties());

		this.updatePersistenceUnitMetadata();

		this.setDefaultSharedCacheMode(this.buildDefaultSharedCacheMode());
		this.setDefaultValidationMode(this.buildDefaultValidationMode());

		// see method comment above
		this.fireCollectionChanged(GENERATORS_COLLECTION, this.generators);
		this.fireCollectionChanged(QUERIES_COLLECTION, this.queries);
	}


	// ********** JpaContextNode implementation **********

	@Override
	public Persistence getParent() {
		return (Persistence) super.getParent();
	}

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_UNIT_ID;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlPersistenceUnit.getSelectionTextRange();
	}

	public void dispose() {
		for (ClassRef classRef : this.getClassRefs()) {
			classRef.dispose();
		}
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			jarFileRef.dispose();
		}
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

	public ListIterator<MappingFileRef> mappingFileRefs() {
		return this.getMappingFileRefs().iterator();
	}

	protected Iterator<String> mappingFileRefNames() {
		return new TransformationIterator<MappingFileRef, String>(this.mappingFileRefs()) {
			@Override
			protected String transform(MappingFileRef mappingFileRef) {
				return mappingFileRef.getFileName();
			}
		};
	}
	
	protected ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedMappingFileRef == null) ?
				this.getSpecifiedMappingFileRefs() :
				this.getCombinedMappingFileRefs();
	}

	protected ListIterator<MappingFileRef> combinedMappingFileRefs() {
		return this.getCombinedMappingFileRefs().iterator();
	}

	protected ListIterable<MappingFileRef> getCombinedMappingFileRefs() {
		return new CompositeListIterable<MappingFileRef>(
				this.getSpecifiedMappingFileRefs(),
				this.impliedMappingFileRef
			);
	}

	public int mappingFileRefsSize() {
		return (this.impliedMappingFileRef == null) ?
				this.specifiedMappingFileRefsSize() :
				this.combinedMappingFileRefsSize();
	}

	protected int combinedMappingFileRefsSize() {
		return this.specifiedMappingFileRefsSize() + 1;
	}

	public Iterator<MappingFileRef> mappingFileRefsContaining(final String typeName) {
		return new FilteringIterator<MappingFileRef> (this.mappingFileRefs()) {
			@Override
			protected boolean accept(MappingFileRef mappingFileRef) {
				return mappingFileRef.getPersistentType(typeName) != null;
			}
		};
	}


	// ********** specified mapping file refs **********

	public ListIterator<MappingFileRef> specifiedMappingFileRefs() {
		return this.getSpecifiedMappingFileRefs().iterator();
	}

	protected ListIterable<MappingFileRef> getSpecifiedMappingFileRefs() {
		return new LiveCloneListIterable<MappingFileRef>(this.specifiedMappingFileRefs);
	}

	public int specifiedMappingFileRefsSize() {
		return this.specifiedMappingFileRefs.size();
	}

	public MappingFileRef addSpecifiedMappingFileRef(String fileName) {
		return this.addSpecifiedMappingFileRef(this.specifiedMappingFileRefs.size(), fileName);
	}

	public MappingFileRef addSpecifiedMappingFileRef(int index, String fileName) {
		XmlMappingFileRef xmlMappingFileRef = this.buildXmlMappingFileRef(fileName);
		MappingFileRef mappingFileRef = this.addSpecifiedMappingFileRef_(index, xmlMappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(index, xmlMappingFileRef);
		return mappingFileRef;
	}

	protected XmlMappingFileRef buildXmlMappingFileRef(String fileName) {
		XmlMappingFileRef ref = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		ref.setFileName(fileName);
		return ref;
	}

	protected MappingFileRef buildSpecifiedMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		return this.getContextNodeFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}

	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		this.removeSpecifiedMappingFileRef(this.specifiedMappingFileRefs.indexOf(mappingFileRef));
	}

	public void removeSpecifiedMappingFileRef(int index) {
		this.removeSpecifiedMappingFileRef_(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
	}

	/**
	 * dispose the mapping file ref
	 */
	protected void removeSpecifiedMappingFileRef_(int index) {
		this.removeItemFromList(index, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST).dispose();
	}

	protected void initializeSpecifiedMappingFileRefs() {
		for (XmlMappingFileRef xmlMappingFileRef : this.getXmlMappingFileRefs()) {
			this.specifiedMappingFileRefs.add(this.buildSpecifiedMappingFileRef(xmlMappingFileRef));
		}
	}

	protected void syncSpecifiedMappingFileRefs() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedMappingFileRefContainerAdapter);
	}

	protected Iterable<XmlMappingFileRef> getXmlMappingFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlMappingFileRef>(this.xmlPersistenceUnit.getMappingFiles());
	}

	protected void moveSpecifiedMappingFileRef_(int index, MappingFileRef mappingFileRef) {
		this.moveItemInList(index, mappingFileRef, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
	}

	protected MappingFileRef addSpecifiedMappingFileRef_(int index, XmlMappingFileRef xmlMappingFileRef) {
		MappingFileRef mappingFileRef = this.buildSpecifiedMappingFileRef(xmlMappingFileRef);
		this.addItemToList(index, mappingFileRef, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
		return mappingFileRef;
	}

	protected void removeSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		this.removeSpecifiedMappingFileRef_(this.specifiedMappingFileRefs.indexOf(mappingFileRef));
	}

	/**
	 * specified mapping file ref container adapter
	 */
	protected class SpecifiedMappingFileRefContainerAdapter
		implements ContextContainerTools.Adapter<MappingFileRef, XmlMappingFileRef>
	{
		public Iterable<MappingFileRef> getContextElements() {
			return AbstractPersistenceUnit.this.getSpecifiedMappingFileRefs();
		}
		public Iterable<XmlMappingFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlMappingFileRefs();
		}
		public XmlMappingFileRef getResourceElement(MappingFileRef contextElement) {
			return contextElement.getXmlMappingFileRef();
		}
		public void moveContextElement(int index, MappingFileRef element) {
			AbstractPersistenceUnit.this.moveSpecifiedMappingFileRef_(index, element);
		}
		public void addContextElement(int index, XmlMappingFileRef resourceElement) {
			AbstractPersistenceUnit.this.addSpecifiedMappingFileRef_(index, resourceElement);
		}
		public void removeContextElement(MappingFileRef element) {
			AbstractPersistenceUnit.this.removeSpecifiedMappingFileRef_(element);
		}
	}


	// ********** implied mapping file ref **********

	public MappingFileRef getImpliedMappingFileRef() {
		return this.impliedMappingFileRef;
	}

	protected MappingFileRef addImpliedMappingFileRef() {
		if (this.impliedMappingFileRef != null) {
			throw new IllegalStateException("The implied mapping file ref is already set: " + this.impliedMappingFileRef); //$NON-NLS-1$
		}
		this.impliedMappingFileRef = this.buildImpliedMappingFileRef();
		this.firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, null, this.impliedMappingFileRef);
		return this.impliedMappingFileRef;
	}

	protected MappingFileRef buildImpliedMappingFileRef() {
		return this.getContextNodeFactory().buildImpliedMappingFileRef(this);
	}

	protected void removeImpliedMappingFileRef() {
		if (this.impliedMappingFileRef == null) {
			throw new IllegalStateException("The implied mapping file ref is already unset."); //$NON-NLS-1$
		}
		MappingFileRef old = this.impliedMappingFileRef;
		this.impliedMappingFileRef = null;
		old.dispose();
		this.firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, old, null);
	}

	protected void syncImpliedMappingFileRef() {
		if (this.impliedMappingFileRef != null) {
			this.impliedMappingFileRef.synchronizeWithResourceModel();
		}
	}

	protected void updateImpliedMappingFileRef() {
		if (this.buildsImpliedMappingFile()) {
			if (this.impliedMappingFileRef == null) {
				this.addImpliedMappingFileRef();
			} else {
				this.impliedMappingFileRef.update();
			}
		} else {
			if (this.impliedMappingFileRef != null) {
				this.removeImpliedMappingFileRef();
			}
		}
	}

	protected boolean buildsImpliedMappingFile() {
		return this.impliedMappingFileIsNotSpecified() && this.impliedMappingFileExists();
	}

	protected boolean impliedMappingFileIsNotSpecified() {
		return ! this.impliedMappingFileIsSpecified();
	}

	protected boolean impliedMappingFileIsSpecified() {
		return this.mappingFileIsSpecified(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
	}

	protected boolean mappingFileIsSpecified(String impliedMappingFileName) {
		for (MappingFileRef specifiedMappingFileRef : this.getSpecifiedMappingFileRefs()) {
			if (Tools.valuesAreEqual(specifiedMappingFileRef.getFileName(), impliedMappingFileName)) {
				return true;
			}
		}
		return false;
	}

	protected boolean impliedMappingFileExists() {
		return this.getJpaProject().getDefaultOrmXmlResource() != null;
	}


	// ********** JAR file refs **********

	public ListIterator<JarFileRef> jarFileRefs() {
		return this.getJarFileRefs().iterator();
	}

	protected ListIterable<JarFileRef> getJarFileRefs() {
		return new LiveCloneListIterable<JarFileRef>(this.jarFileRefs);
	}

	public int jarFileRefsSize() {
		return this.jarFileRefs.size();
	}
	
	protected Iterator<String> jarFileNames() {
		return new TransformationIterator<JarFileRef, String>(this.jarFileRefs()) {
			@Override
			protected String transform(JarFileRef jarFileRef) {
				return jarFileRef.getFileName();
			}
		};
	}
	
	public JarFileRef addJarFileRef(String fileName) {
		return this.addJarFileRef(this.jarFileRefs.size(), fileName);
	}

	public JarFileRef addJarFileRef(int index, String fileName) {
		XmlJarFileRef xmlJarFileRef = this.buildXmlJarFileRef(fileName);
		JarFileRef jarFileRef = this.addJarFileRef_(index, xmlJarFileRef);
		this.xmlPersistenceUnit.getJarFiles().add(index, xmlJarFileRef);
		return jarFileRef;
	}

	protected XmlJarFileRef buildXmlJarFileRef(String fileName) {
		XmlJarFileRef ref = PersistenceFactory.eINSTANCE.createXmlJarFileRef();
		ref.setFileName(fileName);
		return ref;
	}

	protected JarFileRef buildJarFileRef(XmlJarFileRef xmlJarFileRef) {
		return this.getContextNodeFactory().buildJarFileRef(this, xmlJarFileRef);
	}

	public void removeJarFileRef(JarFileRef jarFileRef) {
		this.removeJarFileRef(this.jarFileRefs.indexOf(jarFileRef));
	}

	public void removeJarFileRef(int index) {
		this.removeJarFileRef_(index);
		this.xmlPersistenceUnit.getJarFiles().remove(index);
	}

	/**
	 * dispose the JAR file ref
	 */
	protected void removeJarFileRef_(int index) {
		this.removeItemFromList(index, this.jarFileRefs, JAR_FILE_REFS_LIST).dispose();
	}

	protected void initializeJarFileRefs() {
		for (XmlJarFileRef xmlJarFileRef : this.getXmlJarFileRefs()) {
			this.jarFileRefs.add(this.buildJarFileRef(xmlJarFileRef));
		}
	}

	protected void syncJarFileRefs() {
		ContextContainerTools.synchronizeWithResourceModel(this.jarFileRefContainerAdapter);
	}

	protected Iterable<XmlJarFileRef> getXmlJarFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlJarFileRef>(this.xmlPersistenceUnit.getJarFiles());
	}

	protected void moveJarFileRef_(int index, JarFileRef jarFileRef) {
		this.moveItemInList(index, jarFileRef, this.jarFileRefs, JAR_FILE_REFS_LIST);
	}

	protected JarFileRef addJarFileRef_(int index, XmlJarFileRef xmlJarFileRef) {
		JarFileRef jarFileRef = this.buildJarFileRef(xmlJarFileRef);
		this.addItemToList(index, jarFileRef, this.jarFileRefs, JAR_FILE_REFS_LIST);
		return jarFileRef;
	}

	protected void removeJarFileRef_(JarFileRef jarFileRef) {
		this.removeJarFileRef_(this.jarFileRefs.indexOf(jarFileRef));
	}

	/**
	 * JAR file ref container adapter
	 */
	protected class JarFileRefContainerAdapter
		implements ContextContainerTools.Adapter<JarFileRef, XmlJarFileRef>
	{
		public Iterable<JarFileRef> getContextElements() {
			return AbstractPersistenceUnit.this.getJarFileRefs();
		}
		public Iterable<XmlJarFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlJarFileRefs();
		}
		public XmlJarFileRef getResourceElement(JarFileRef contextElement) {
			return contextElement.getXmlJarFileRef();
		}
		public void moveContextElement(int index, JarFileRef element) {
			AbstractPersistenceUnit.this.moveJarFileRef_(index, element);
		}
		public void addContextElement(int index, XmlJarFileRef resourceElement) {
			AbstractPersistenceUnit.this.addJarFileRef_(index, resourceElement);
		}
		public void removeContextElement(JarFileRef element) {
			AbstractPersistenceUnit.this.removeJarFileRef_(element);
		}
	}


	// ********** class refs **********

	public Iterator<ClassRef> classRefs() {
		return this.getClassRefs().iterator();
	}

	@SuppressWarnings("unchecked")
	protected Iterable<ClassRef> getClassRefs() {
		return new CompositeIterable<ClassRef>(
						this.getSpecifiedClassRefs(),
						this.getImpliedClassRefs()
					);
	}

	public int classRefsSize() {
		return this.specifiedClassRefs.size() + this.impliedClassRefs.size();
	}

	protected Iterator<String> classRefNames() {
		return new TransformationIterator<ClassRef, String>(this.classRefs()) {
			@Override
			protected String transform(ClassRef classRef) {
				return classRef.getClassName();
			}
		};
	}
	

	// ********** specified class refs **********

	public ListIterator<ClassRef> specifiedClassRefs() {
		return this.getSpecifiedClassRefs().iterator();
	}

	protected ListIterable<ClassRef> getSpecifiedClassRefs() {
		return new LiveCloneListIterable<ClassRef>(this.specifiedClassRefs);
	}

	public int specifiedClassRefsSize() {
		return this.specifiedClassRefs.size();
	}

	public ClassRef addSpecifiedClassRef(String className) {
		return this.addSpecifiedClassRef(this.specifiedClassRefs.size(), className);
	}

	public ClassRef addSpecifiedClassRef(int index, String className) {
		XmlJavaClassRef xmlClassRef = this.buildXmlJavaClassRef(className);
		ClassRef classRef = this.addSpecifiedClassRef_(index, xmlClassRef);
		this.xmlPersistenceUnit.getClasses().add(index, xmlClassRef);
		return classRef;
	}

	protected XmlJavaClassRef buildXmlJavaClassRef(String className) {
		XmlJavaClassRef ref = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		ref.setJavaClass(className);
		return ref;
	}

	protected ClassRef buildClassRef(XmlJavaClassRef xmlClassRef) {
		return this.getContextNodeFactory().buildClassRef(this, xmlClassRef);
	}

	public void removeSpecifiedClassRef(ClassRef classRef) {
		this.removeSpecifiedClassRef(this.specifiedClassRefs.indexOf(classRef));
	}

	public void removeSpecifiedClassRef(int index) {
		this.removeSpecifiedClassRef_(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
	}

	/**
	 * dispose the class ref
	 */
	protected void removeSpecifiedClassRef_(int index) {
		this.removeItemFromList(index, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST).dispose();
	}

	protected void initializeSpecifiedClassRefs() {
		for (XmlJavaClassRef xmlJavaClassRef : this.getXmlClassRefs()) {
			this.specifiedClassRefs.add(this.buildClassRef(xmlJavaClassRef));
		}
	}

	protected void syncSpecifiedClassRefs() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedClassRefContainerAdapter);
	}

	protected Iterable<XmlJavaClassRef> getXmlClassRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlJavaClassRef>(this.xmlPersistenceUnit.getClasses());
	}

	protected void moveSpecifiedClassRef_(int index, ClassRef classRef) {
		this.moveItemInList(index, classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
	}

	protected ClassRef addSpecifiedClassRef_(int index, XmlJavaClassRef xmlClassRef) {
		ClassRef classRef = this.buildClassRef(xmlClassRef);
		this.addItemToList(index, classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
		return classRef;
	}

	protected void removeSpecifiedClassRef_(ClassRef classRef) {
		this.removeSpecifiedClassRef_(this.specifiedClassRefs.indexOf(classRef));
	}

	/**
	 * specified class ref container adapter
	 */
	protected class SpecifiedClassRefContainerAdapter
		implements ContextContainerTools.Adapter<ClassRef, XmlJavaClassRef>
	{
		public Iterable<ClassRef> getContextElements() {
			return AbstractPersistenceUnit.this.getSpecifiedClassRefs();
		}
		public Iterable<XmlJavaClassRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlClassRefs();
		}
		public XmlJavaClassRef getResourceElement(ClassRef contextElement) {
			return contextElement.getXmlClassRef();
		}
		public void moveContextElement(int index, ClassRef element) {
			AbstractPersistenceUnit.this.moveSpecifiedClassRef_(index, element);
		}
		public void addContextElement(int index, XmlJavaClassRef resourceElement) {
			AbstractPersistenceUnit.this.addSpecifiedClassRef_(index, resourceElement);
		}
		public void removeContextElement(ClassRef element) {
			AbstractPersistenceUnit.this.removeSpecifiedClassRef_(element);
		}
	}


	// ********** virtual class refs **********

	public Iterator<ClassRef> impliedClassRefs() {
		return this.getImpliedClassRefs().iterator();
	}

	protected Iterable<ClassRef> getImpliedClassRefs() {
		return new LiveCloneIterable<ClassRef>(this.impliedClassRefs);
	}

	public int impliedClassRefsSize() {
		return this.impliedClassRefs.size();
	}

	protected ClassRef addImpliedClassRef(String className) {
		ClassRef classRef = this.buildClassRef(className);
		this.addItemToCollection(classRef, this.impliedClassRefs, IMPLIED_CLASS_REFS_COLLECTION);
		return classRef;
	}

	protected ClassRef buildClassRef(String className) {
		return this.getContextNodeFactory().buildClassRef(this, className);
	}

	protected void removeImpliedClassRef(ClassRef classRef) {
		this.impliedClassRefs.remove(classRef);
		classRef.dispose();
		this.fireItemRemoved(IMPLIED_CLASS_REFS_COLLECTION, classRef);
	}

	protected void updateImpliedClassRefs() {
		ContextContainerTools.update(this.impliedClassRefContainerAdapter);
	}

	protected Iterable<String> getImpliedClassNames() {
		return this.excludesUnlistedClasses() ?
				EmptyIterable.<String>instance() :
				this.getImpliedClassNames_();
	}

	/**
	 * Return the names of all the Java classes in the JPA project that are
	 * mapped (i.e. have the appropriate annotation etc.) but not specified
	 * in the persistence unit.
	 */
	protected Iterable<String> getImpliedClassNames_() {
		return new FilteringIterable<String>(this.getJpaProject().getMappedJavaSourceClassNames()) {
				@Override
				protected boolean accept(String mappedClassName) {
					return ! AbstractPersistenceUnit.this.specifiesPersistentType(mappedClassName);
				}
			};
	}

	/**
	 * Virtual class ref container adapter.
	 * <p>
	 * <strong>NB:</strong> The context class ref is matched with a resource
	 * class by name.
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
	protected class ImpliedClassRefContainerAdapter
		implements ContextContainerTools.Adapter<ClassRef, String>
	{
		public Iterable<ClassRef> getContextElements() {
			return AbstractPersistenceUnit.this.getImpliedClassRefs();
		}
		public Iterable<String> getResourceElements() {
			return AbstractPersistenceUnit.this.getImpliedClassNames();
		}
		public String getResourceElement(ClassRef contextElement) {
			return contextElement.getClassName();
		}
		public void moveContextElement(int index, ClassRef element) {
			// ignore moves - we don't care about the order of the implied class refs
		}
		public void addContextElement(int index, String resourceElement) {
			// ignore the index - we don't care about the order of the implied class refs
			AbstractPersistenceUnit.this.addImpliedClassRef(resourceElement);
		}
		public void removeContextElement(ClassRef element) {
			AbstractPersistenceUnit.this.removeImpliedClassRef(element);
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

	public ListIterator<Property> properties() {
		return this.getProperties().iterator();
	}

	protected ListIterable<Property> getProperties() {
		return new LiveCloneListIterable<Property>(this.properties);
	}

	public int propertiesSize() {
		return this.properties.size();
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

	public Iterable<Property> getPropertiesNamed(final String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException();
		}
		return new FilteringIterable<Property>(this.getProperties()) {
			@Override
			protected boolean accept(Property property) {
				return Tools.valuesAreEqual(property.getName(), propertyName);
			}
		};
	}

	public Iterator<Property> propertiesWithNamePrefix(final String propertyNamePrefix) {
		if (propertyNamePrefix == null) {
			throw new NullPointerException();
		}
		return new FilteringIterator<Property>(this.properties()) {
			@Override
			protected boolean accept(Property property) {
				String pName = property.getName();
				return (pName != null) && pName.startsWith(propertyNamePrefix);
			}
		};
	}

	public Property addProperty() {
		return this.addProperty(this.properties.size());
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
		return this.getContextNodeFactory().buildProperty(this, xmlProperty);
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
		this.removeProperty(this.properties.indexOf(property));
	}

	public void removeProperty(String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException();
		}
		for (ListIterator<Property> stream = this.properties.listIterator(); stream.hasNext(); ) {
			Property property = stream.next();
			if (propertyName.equals(property.getName())) {
				this.removeProperty(stream.previousIndex());
				return;
			}
		}
		throw new IllegalArgumentException("invalid property name: " + propertyName); //$NON-NLS-1$
	}

	public void removeProperty(String propertyName, String value) {
		if ((propertyName == null) || (value == null)) {
			throw new NullPointerException();
		}
		for (ListIterator<Property> stream = this.properties.listIterator(); stream.hasNext(); ) {
			Property property = stream.next();
			if (propertyName.equals(property.getName()) && value.equals(property.getValue())) {
				this.removeProperty(stream.previousIndex());
				return;
			}
		}
		throw new IllegalArgumentException("invalid property name/value pair: " + propertyName + " = " + value); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void removeProperty(int index) {
		Property removedProperty = this.properties.remove(index);
		this.xmlPersistenceUnit.getProperties().getProperties().remove(index);

		if (this.xmlPersistenceUnit.getProperties().getProperties().isEmpty()) {
			this.xmlPersistenceUnit.setProperties(null);
		}

		this.fireItemRemoved(PROPERTIES_LIST, index, removedProperty);
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
	}

	protected void propertyAdded(String propertyName, String value) {
		this.propertyValueChanged(propertyName, value);
	}

	protected void propertyRemoved(String propertyName) {
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
	}

	protected void initializeProperties() {
		for (XmlProperty xmlProperty : this.getXmlProperties()) {
			this.properties.add(this.buildProperty(xmlProperty));
		}
		this.connection = this.getContextNodeFactory().buildConnection(this);
		this.options = this.getContextNodeFactory().buildOptions(this);
	}

	protected void syncProperties() {
		ContextContainerTools.synchronizeWithResourceModel(this.propertyContainerAdapter);
	}

	protected Iterable<XmlProperty> getXmlProperties() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		// clone to reduce chance of concurrency problems
		return (xmlProperties == null) ?
				EmptyIterable.<XmlProperty>instance() :
				new LiveCloneIterable<XmlProperty>(xmlProperties.getProperties());
	}

	protected void moveProperty_(int index, Property property) {
		this.moveItemInList(index, this.properties.indexOf(property), this.properties, PROPERTIES_LIST);
	}

	protected Property addProperty_(int index, XmlProperty xmlProperty) {
		Property property = this.buildProperty(xmlProperty);
		this.addItemToList(index, property, this.properties, PROPERTIES_LIST);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
		return property;
	}

	protected void removeProperty_(Property property) {
		this.removeItemFromList(property, this.properties, PROPERTIES_LIST);
		if (property.getName() != null) {
			this.propertyRemoved(property.getName());
		}
	}

	/**
	 * property container adapter
	 */
	protected class PropertyContainerAdapter
		implements ContextContainerTools.Adapter<Property, XmlProperty>
	{
		public Iterable<Property> getContextElements() {
			return AbstractPersistenceUnit.this.getProperties();
		}
		public Iterable<XmlProperty> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlProperties();
		}
		public XmlProperty getResourceElement(Property contextElement) {
			return contextElement.getXmlProperty();
		}
		public void moveContextElement(int index, Property element) {
			AbstractPersistenceUnit.this.moveProperty_(index, element);
		}
		public void addContextElement(int index, XmlProperty resourceElement) {
			AbstractPersistenceUnit.this.addProperty_(index, resourceElement);
		}
		public void removeContextElement(Property element) {
			AbstractPersistenceUnit.this.removeProperty_(element);
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

	public PersistenceUnitProperties getConnection() {
		return this.connection;
	}

	public PersistenceUnitProperties getOptions() {
		return this.options;
	}


	// ********** shared cache mode **********

	public SharedCacheMode getSharedCacheMode() {
		return (this.specifiedSharedCacheMode != null) ? this.specifiedSharedCacheMode : this.defaultSharedCacheMode;
	}

	public SharedCacheMode getSpecifiedSharedCacheMode() {
		return this.specifiedSharedCacheMode;
	}

	public void setSpecifiedSharedCacheMode(SharedCacheMode specifiedSharedCacheMode) {
		this.setSpecifiedSharedCacheMode_(specifiedSharedCacheMode);
		this.xmlPersistenceUnit.setSharedCacheMode(SharedCacheMode.toXmlResourceModel(specifiedSharedCacheMode));
	}

	protected void setSpecifiedSharedCacheMode_(SharedCacheMode sharedCacheMode) {
		SharedCacheMode old = this.specifiedSharedCacheMode;
		this.specifiedSharedCacheMode = sharedCacheMode;
		this.firePropertyChanged(SPECIFIED_SHARED_CACHE_MODE_PROPERTY, old, sharedCacheMode);
	}

	public SharedCacheMode getDefaultSharedCacheMode() {
		return this.defaultSharedCacheMode;
	}

	protected void setDefaultSharedCacheMode(SharedCacheMode defaultSharedCacheMode) {
		SharedCacheMode old = this.defaultSharedCacheMode;
		this.defaultSharedCacheMode = defaultSharedCacheMode;
		this.firePropertyChanged(DEFAULT_SHARED_CACHE_MODE_PROPERTY, old, defaultSharedCacheMode);
	}

	public boolean calculateDefaultCacheable() {
		SharedCacheMode sharedCacheMode = this.getSharedCacheMode();
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

	protected SharedCacheMode buildSpecifiedSharedCacheMode() {
		return SharedCacheMode.fromXmlResourceModel(this.xmlPersistenceUnit.getSharedCacheMode());
	}

	protected SharedCacheMode buildDefaultSharedCacheMode() {
		return SharedCacheMode.UNSPECIFIED;
	}

	// ********** validation mode **********

	public ValidationMode getValidationMode() {
		return (this.specifiedValidationMode != null) ? this.specifiedValidationMode : this.defaultValidationMode;
	}

	public ValidationMode getSpecifiedValidationMode() {
		return this.specifiedValidationMode;
	}

	public void setSpecifiedValidationMode(ValidationMode specifiedValidationMode) {
		this.setSpecifiedValidationMode_(specifiedValidationMode);
		this.xmlPersistenceUnit.setValidationMode(ValidationMode.toXmlResourceModel(specifiedValidationMode));
	}

	protected void setSpecifiedValidationMode_(ValidationMode validationMode) {
		ValidationMode old = this.specifiedValidationMode;
		this.specifiedValidationMode = validationMode;
		this.firePropertyChanged(SPECIFIED_VALIDATION_MODE_PROPERTY, old, validationMode);
	}

	public ValidationMode getDefaultValidationMode() {
		return this.defaultValidationMode;
	}

	protected void setDefaultValidationMode(ValidationMode defaultValidationMode) {
		ValidationMode old = this.defaultValidationMode;
		this.defaultValidationMode = defaultValidationMode;
		this.firePropertyChanged(DEFAULT_VALIDATION_MODE_PROPERTY, old, defaultValidationMode);
	}

	protected ValidationMode buildSpecifiedValidationMode() {
		return ValidationMode.fromXmlResourceModel(this.xmlPersistenceUnit.getValidationMode());
	}

	protected ValidationMode buildDefaultValidationMode() {
		return DEFAULT_VALIDATION_MODE;
	}


	// ********** generators **********

	public Iterator<Generator> generators() {
		return this.getGenerators().iterator();
	}

	protected Iterable<Generator> getGenerators() {
		return new LiveCloneIterable<Generator>(this.generators);
	}

	public int generatorsSize() {
		return this.generators.size();
	}

	public void addGenerator(Generator generator) {
		this.generators.add(generator);
	}

	public Iterable<String> getUniqueGeneratorNames() {
		HashSet<String> names = new HashSet<String>(this.generators.size());
		this.addNonNullGeneratorNamesTo(names);
		return names;
	}

	protected void addNonNullGeneratorNamesTo(Set<String> names) {
		for (Generator generator : this.getGenerators()) {
			String generatorName = generator.getName();
			if (generatorName != null) {
				names.add(generatorName);
			}
		}
	}


	// ********** queries **********

	public Iterator<Query> queries() {
		return new CloneIterator<Query>(this.queries);
	}

	public int queriesSize() {
		return this.queries.size();
	}

	public void addQuery(Query query) {
		this.queries.add(query);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMappingFiles(messages, reporter);
		this.validateClassRefs(messages, reporter);
		this.validateJarFileRefs(messages, reporter);
		this.validateProperties(messages, reporter);
	}

	protected void validateMappingFiles(List<IMessage> messages, IReporter reporter) {
		this.checkForMultiplePersistenceUnitMetadata(messages);
		this.checkForDuplicateMappingFiles(messages);
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			mappingFileRef.validate(messages, reporter);
		}
	}

	protected void checkForMultiplePersistenceUnitMetadata(List<IMessage> messages) {
		boolean first = true;
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.persistenceUnitMetadataExists()) {
				if (first) {
					first = false;
				} else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JpaValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA,
							new String[] {mappingFileRef.getFileName()},
							mappingFileRef
						)
					);
				}
			}
		}
	}

	protected void checkForDuplicateMappingFiles(List<IMessage> messages) {
		HashBag<String> fileNames = new HashBag<String>();
		CollectionTools.addAll(fileNames, this.mappingFileRefNames());
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			String fileName = mappingFileRef.getFileName();
			if (fileNames.count(fileName) > 1) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {fileName},
						mappingFileRef,
						mappingFileRef.getValidationTextRange()
					)
				);
			}
		}
	}

	protected void validateClassRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateClasses(messages);
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}

	protected void checkForDuplicateClasses(List<IMessage> messages) {
		HashBag<String> javaClassNames = new HashBag<String>();
		CollectionTools.addAll(javaClassNames, this.classRefNames());
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			ClassRef classRef = stream.next();
			String javaClassName = classRef.getClassName();
			if ((javaClassName != null)	&& (javaClassNames.count(javaClassName) > 1)) {
					  messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
								new String[] {javaClassName}, 
								classRef,
								classRef.getValidationTextRange()
						)
				);
			}
		}
	}

	protected void validateJarFileRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateJarFileRefs(messages);
		for (JarFileRef each : CollectionTools.iterable(this.jarFileRefs())) {
			each.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateJarFileRefs(List<IMessage> messages) {
		HashBag<String> jarFileNames = new HashBag<String>();
		CollectionTools.addAll(jarFileNames, this.jarFileNames());
		for (JarFileRef jarFileRef : CollectionTools.iterable(this.jarFileRefs())) {
			String jarFileName = jarFileRef.getFileName();
			if ((jarFileName != null) && (jarFileNames.count(jarFileName) > 1)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE,
						new String[] {jarFileName},
						jarFileRef,
						jarFileRef.getValidationTextRange()
					)
				);
			}
		}
	}

	protected void validateProperties(List<IMessage> messages, IReporter reporter) {
		// do nothing by default
	}

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}
	
	public TextRange getValidationTextRange() {
		return this.xmlPersistenceUnit.getValidationTextRange();
	}

	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(final IType type) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<ClassRef, Iterable<DeleteEdit>>(this.getSpecifiedClassRefs()) {
				@Override
				protected Iterable<DeleteEdit> transform(ClassRef classRef) {
					return classRef.createDeleteTypeEdits(type);
				}
			}
		);
	}

	public Iterable<DeleteEdit> createDeleteMappingFileEdits(final IFile file) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<MappingFileRef, Iterable<DeleteEdit>>(this.getSpecifiedMappingFileRefs()) {
				@Override
				protected Iterable<DeleteEdit> transform(MappingFileRef mappingFileRef) {
					return mappingFileRef.createDeleteMappingFileEdits(file);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
					this.createSpecifiedClassRefRenameTypeEdits(originalType, newName),
					this.createPersistenceUnitPropertiesRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefRenameTypeEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<ClassRef, Iterable<ReplaceEdit>>(this.getSpecifiedClassRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(ClassRef classRef) {
					return classRef.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenameTypeEdits(IType originalType, String newName) {
		return this.options.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			this.createSpecifiedClassRefMoveTypeEdits(originalType, newPackage),
			this.createPersistenceUnitPropertiesMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefMoveTypeEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<ClassRef, Iterable<ReplaceEdit>>(this.getSpecifiedClassRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(ClassRef classRef) {
					return classRef.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.options.createMoveTypeEdits(originalType, newPackage);
	}


	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createSpecifiedClassRefRenamePackageEdits(originalPackage, newName),
			this.createPersistenceUnitPropertiesRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createSpecifiedClassRefRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<ClassRef, Iterable<ReplaceEdit>>(this.getSpecifiedClassRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(ClassRef classRef) {
					return classRef.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.options.createRenamePackageEdits(originalPackage, newName);
	}

	public Iterable<ReplaceEdit> createRenameFolderEdits(final IFolder originalFolder, final String newName) {
		return this.createMappingFileRefRenameFolderEdits(originalFolder, newName);
	}

	protected Iterable<ReplaceEdit> createMappingFileRefRenameFolderEdits(final IFolder originalFolder, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<MappingFileRef, Iterable<ReplaceEdit>>(this.getSpecifiedMappingFileRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(MappingFileRef mappingFileRef) {
					return mappingFileRef.createRenameFolderEdits(originalFolder, newName);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createRenameMappingFileEdits(final IFile originalFile, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<MappingFileRef, Iterable<ReplaceEdit>>(this.getMappingFileRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(MappingFileRef mappingFileRef) {
					return mappingFileRef.createRenameMappingFileEdits(originalFile, newName);
				}
			}
		);
	}

	public int findInsertLocationForMappingFileRef() {
		return this.xmlPersistenceUnit.getLocationToInsertMappingFileRef();
	}

	public Iterable<ReplaceEdit> createMoveMappingFileEdits(final IFile originalFile, final IPath runtineDestination) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<MappingFileRef, Iterable<ReplaceEdit>>(this.getMappingFileRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(MappingFileRef mappingFileRef) {
					return mappingFileRef.createMoveMappingFileEdits(originalFile, runtineDestination);
				}
			}
		);
	}

	public Iterable<ReplaceEdit> createMoveFolderEdits(final IFolder originalFolder, final IPath runtimeDestination) {
		return this.createMappingFileRefMoveFolderReplaceEdits(originalFolder, runtimeDestination);
	}

	protected Iterable<ReplaceEdit> createMappingFileRefMoveFolderReplaceEdits(final IFolder originalFolder, final IPath runtimeDestination) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<MappingFileRef, Iterable<ReplaceEdit>>(this.getSpecifiedMappingFileRefs()) {
				@Override
				protected Iterable<ReplaceEdit> transform(MappingFileRef mappingFileRef) {
					return mappingFileRef.createMoveFolderEdits(originalFolder, runtimeDestination);
				}
			}
		);
	}

	// ********** misc **********

	public XmlPersistenceUnit getXmlPersistenceUnit() {
		return this.xmlPersistenceUnit;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (Iterator<JarFileRef> stream = this.jarFileRefs(); stream.hasNext(); ) {
			JarFileRef jarFileRef = stream.next();
			if (jarFileRef.containsOffset(textOffset)) {
				return jarFileRef;
			}
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.containsOffset(textOffset)) {
				return mappingFileRef;
			}
		}
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			ClassRef classRef = stream.next();
			if (classRef.containsOffset(textOffset)) {
				return classRef;
			}
		}
		return this;
	}

	// ------------------- all entities -----------------
	public Iterable<Entity> getEntities() {
		return new SubIterableWrapper<TypeMapping, Entity>(this.getEntities_());
	}

	protected Iterable<TypeMapping> getEntities_() {
		return new FilteringIterable<TypeMapping>(this.getTypeMappings()) {
				@Override
				protected boolean accept(TypeMapping typeMapping) {
					return typeMapping instanceof Entity;
				}
			};
	}

	protected Iterable<TypeMapping> getTypeMappings() {
		return new TransformationIterable<PersistentType, TypeMapping>(this.getPersistentTypes()) {
				@Override
				protected TypeMapping transform(PersistentType persistentType) {
					return persistentType.getMapping();  // the mapping should never be null
				}
			};
	}

	public Iterable<String> getOrmMappedClassNames() {
		return new TransformationIterable<PersistentType, String>(this.getMappingFilePersistentTypes()) {
			@Override
			protected String transform(PersistentType persistentType) {
				return persistentType.getName();
			}
		};
	}

	// ------------------ orm entities --------------------
	public Map<String, Set<String>> mapEntityNameToClassNames() {
		HashSet<String> classNames = new HashSet<String>();
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		for (String entityName : this.getFilteredEntityNames()) {
			CollectionTools.addAll(classNames, getFilteredOrmClassNames(entityName));
			map.put(entityName, classNames);
		}
		return map;
	}
	
	private Iterable<String> getFilteredOrmClassNames(String entityName){
		List<String> classNames = new ArrayList<String>();
		for (PersistentType persistentType: this.getFilteredOrmPersistentTypes()) {
			if (StringTools.stringsAreEqual(persistentType.getMapping().getName(), entityName)) {
				classNames.add(persistentType.getName());
			}
		}
		return classNames;
	}
	
	private Iterable<String> getFilteredEntityNames() {
		return new TransformationIterable<PersistentType, String>(this.getFilteredOrmPersistentTypes()) {
			@Override
			protected String transform(PersistentType persistentType) {
				return persistentType.getMapping().getName();
			}
		};
	}
	
	private Iterable<PersistentType> getFilteredOrmPersistentTypes(){
		List<PersistentType> filteredPersistentType = new ArrayList<PersistentType>();
		HashBag<String> ormEntityNames = new HashBag<String>();
		CollectionTools.addAll(ormEntityNames, this.ormEntityNames());
		for (PersistentType persistentType : this.getOrmEntityPersistentTypes()) {
			if (ormEntityNames.count(persistentType.getMapping().getName()) > 1) {
				filteredPersistentType.add(persistentType);
			}
		}
		return filteredPersistentType;
	}

	protected Iterator<String> ormEntityClassNames() {
		return new TransformationIterator<PersistentType, String>(this.getOrmEntityPersistentTypes()) {
			@Override
			protected String transform(PersistentType persistentType) {
				return persistentType.getName();
			}
		};
	}
	
	public Iterator<String> ormEntityNames() {
		return new TransformationIterator<PersistentType, String>(this.getOrmEntityPersistentTypes()) {
			@Override
			protected String transform(PersistentType persistentType) {
				return persistentType.getMapping().getName();
			}
		};
	}
	
	public Iterable<Entity> getOrmEntities(){
		return new SubIterableWrapper<PersistentType, Entity>(this.getOrmEntityPersistentTypes());
	}
	
	private Iterable<PersistentType> getOrmEntityPersistentTypes() {
		return (this.getOrmPersistentTypes(org.eclipse.jpt.jpa.core.MappingKeys.ENTITY_TYPE_MAPPING_KEY));
	}

	protected Iterable<PersistentType> getOrmPersistentTypes(final String mappingKey) {
		return new FilteringIterable<PersistentType>(this.getMappingFilePersistentTypes()) {
			@Override
			protected boolean accept(PersistentType persistentType) {
				return persistentType.getMappingKey() == mappingKey;
			}
		};
	}
	
	//--------------- java entities -----------------
	protected Iterator<String> javaEntityClassNames(){
		return new TransformationIterator<Entity, String>(this.getJavaEntities()) {
			@Override
			protected String transform(Entity javaEntity) {
				return javaEntity.getPersistentType().getName();
			}
		};
	}

	public Iterator<String> javaEntityNamesExclOverridden() {
		HashBag<String> ormMappedClassNames = new HashBag<String>();
		CollectionTools.addAll(ormMappedClassNames, this.getOrmMappedClassNames());
		List<String> javaEntityNamesExclOverridden = new ArrayList<String>();
		for (Iterator<String> javaEntityClassNames = this.javaEntityClassNames(); javaEntityClassNames.hasNext();){
			String javaEntityClassName = javaEntityClassNames.next();
			if (!ormMappedClassNames.contains(javaEntityClassName)) {
				javaEntityNamesExclOverridden.add((this.getEntity(javaEntityClassName)).getName());
			}
		}
		return javaEntityNamesExclOverridden.iterator();
	}

	public Iterator<String> javaEntityNames(){
		return new TransformationIterator<Entity, String>(this.getJavaEntities()) {
			@Override
			protected String transform(Entity javaEntity) {
				return javaEntity.getName();
			}
		};
	}

	public Iterable<Entity> getJavaEntities(){
		return new SubIterableWrapper<TypeMapping, Entity>(this.getJavaEntities_());
	}
	
	protected Iterable<TypeMapping> getJavaEntities_(){
		return new FilteringIterable<TypeMapping>(this.getJavaTypeMappings()){
			@Override
			protected boolean accept(TypeMapping typeMapping) {
				return typeMapping instanceof Entity;
			}
		};
	}
	
	private Iterable<? extends TypeMapping> getJavaTypeMappings() {
		return new TransformationIterable<PersistentType, TypeMapping>(this.getNonNullClassPersistentTypes()) {
			@Override
			protected TypeMapping transform(PersistentType persistentType) {
				return persistentType.getMapping();
			}
		};
	}	
	
	@SuppressWarnings("unchecked")
	public Iterable<PersistentType> getPersistentTypes() {
		return new CompositeIterable<PersistentType>(
				this.getMappingFilePersistentTypes(),
				this.getNonNullClassPersistentTypes(),
				this.getJarFilePersistentTypes()
			);
	}

	protected Iterable<PersistentType> getMappingFilePersistentTypes() {
		return new CompositeIterable<PersistentType>(this.getMappingFilePersistentTypeLists());
	}

	protected Iterable<Iterable<? extends PersistentType>> getMappingFilePersistentTypeLists() {
		return new TransformationIterable<PersistentTypeContainer, Iterable<? extends PersistentType>>(
				this.getMappingFileRefs(),
				PersistentTypeContainer.TRANSFORMER
			);
	}

	protected Iterable<PersistentType> getNonNullClassPersistentTypes() {
		return new FilteringIterable<PersistentType>(this.getClassPersistentTypes(), NotNullFilter.<PersistentType>instance());
	}

	protected Iterable<PersistentType> getClassPersistentTypes() {
		return new TransformationIterable<ClassRef, PersistentType>(this.getClassRefs()) {
			@Override
			protected PersistentType transform(ClassRef classRef) {
				return classRef.getJavaPersistentType();
			}
		};
	}

	protected Iterable<PersistentType> getJarFilePersistentTypes() {
		return new CompositeIterable<PersistentType>(this.getJarFilePersistentTypeLists());
	}

	protected Iterable<Iterable<? extends PersistentType>> getJarFilePersistentTypeLists() {
		return new TransformationIterable<PersistentTypeContainer, Iterable<? extends PersistentType>>(
				this.getJarFileRefs(),
				PersistentTypeContainer.TRANSFORMER
			);
	}

	public PersistentType getPersistentType(String typeName) {
		if (typeName == null) {
			return null;
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			PersistentType persistentType = mappingFileRef.getPersistentType(typeName);
			if (persistentType != null) {
				return persistentType;
			}
		}
		for (ClassRef classRef : this.getClassRefs()) {
			if (classRef.isFor(typeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			PersistentType persistentType = jarFileRef.getPersistentType(typeName);
			if (persistentType != null) {
				return persistentType;
			}
		}
		return null;
	}

	public boolean specifiesPersistentType(String className) {
		for (ClassRef classRef : this.getSpecifiedClassRefs()) {
			if (classRef.isFor(className)) {
				return true;
			}
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.getPersistentType(className) != null) {
				return true;
			}
		}
		return false;
	}

	public Entity getEntity(String typeName) {
		TypeMapping typeMapping = this.getTypeMapping(typeName);
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public Embeddable getEmbeddable(String typeName) {
		TypeMapping typeMapping = this.getTypeMapping(typeName);
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected TypeMapping getTypeMapping(String typeName) {
		PersistentType persistentType = this.getPersistentType(typeName);
		return (persistentType == null) ? null : persistentType.getMapping();
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlPersistenceUnit != null) && this.xmlPersistenceUnit.containsOffset(textOffset);
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
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
		return new TransformationIterable<JavaResourcePersistentType2_0, IFile>(this.getGeneratedMetamodelTopLevelTypes()) {
			@Override
			protected IFile transform(JavaResourcePersistentType2_0 jrpt) {
				return jrpt.getFile();
			}
		};
	}

	protected Iterable<JavaResourcePersistentType2_0> getGeneratedMetamodelTopLevelTypes() {
		return ((JpaProject2_0) this.getJpaProject()).getGeneratedMetamodelTopLevelTypes();
	}

	/**
	 * Not the prettiest code....
	 */
	public void synchronizeMetamodel() {
		// gather up the persistent unit's types, eliminating duplicates;
		// if we have persistent types with the same name in multiple locations,
		// the last one we encounter wins (i.e. the classes in the orm.xml take
		// precedence)
		HashMap<String, PersistentType2_0> allPersistentTypes = new HashMap<String, PersistentType2_0>();
		this.addPersistentTypesTo_(this.getJarFileRefs(), allPersistentTypes);
		this.addPersistentTypesTo(this.getNonNullClassPersistentTypes(), allPersistentTypes);
		this.addPersistentTypesTo_(this.getMappingFileRefs(), allPersistentTypes);

		// build a list of the top-level types and a tree of their associated
		// member types etc.
		ArrayList<MetamodelSourceType> topLevelTypes = new ArrayList<MetamodelSourceType>(allPersistentTypes.size());
		HashMap<String, Collection<MetamodelSourceType>> memberTypeTree = new HashMap<String, Collection<MetamodelSourceType>>();
		for (PersistentType2_0 type : allPersistentTypes.values()) {
			String declaringTypeName = type.getDeclaringTypeName();
			MetamodelSourceType memberType = type;
			while (true) {
				if (declaringTypeName == null) {
					topLevelTypes.add(memberType);
					break;  // stop at the top-level type
				}

				// associate the member type with its declaring type
				Collection<MetamodelSourceType> memberTypes = memberTypeTree.get(declaringTypeName);
				if (memberTypes == null) {
					memberTypes = new ArrayList<MetamodelSourceType>();
					memberTypeTree.put(declaringTypeName, memberTypes);
				}
				memberTypes.add(memberType);

				// move out to the member type's declaring type
				String memberTypeName = declaringTypeName;
				// check for a context persistent type
				memberType = allPersistentTypes.get(memberTypeName);
				if (memberType != null) {
					break;  // stop - this will be processed in the outer 'for' loop
				}
				// check for a Java resource persistent type
				JavaResourcePersistentType jrpt = this.getJpaProject().getJavaResourcePersistentType(memberTypeName);
				if (jrpt != null) {
					declaringTypeName = jrpt.getDeclaringTypeName();
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
		Collections.sort(topLevelTypes, MetamodelSourceType.COMPARATOR);
		HashSet<String> names = new HashSet<String>(topLevelTypes.size());
		for (Iterator<MetamodelSourceType> stream = topLevelTypes.iterator(); stream.hasNext(); ) {
			MetamodelSourceType topLevelType = stream.next();
			// hopefully this is case-insensitive enough...
			if ( ! names.add(topLevelType.getName().toLowerCase())) {
				stream.remove();
			}
		}

		// copy the list of metamodel files...
		HashSet<IFile> deadMetamodelFiles = new HashSet<IFile>(this.metamodelFiles);
		this.metamodelFiles.clear();
		for (MetamodelSourceType topLevelType : topLevelTypes) {
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
		for (MetamodelSourceType topLevelType : topLevelTypes) {
			topLevelType.synchronizeMetamodel(memberTypeTree);
		}
	}

	protected void addPersistentTypesTo_(Iterable<? extends PersistentTypeContainer> ptContainers, HashMap<String, PersistentType2_0> persistentTypeMap) {
		for (PersistentTypeContainer ptContainer : ptContainers) {
			this.addPersistentTypesTo(ptContainer.getPersistentTypes(), persistentTypeMap);
		}
	}

	protected void addPersistentTypesTo(Iterable<? extends PersistentType> persistentTypes, HashMap<String, PersistentType2_0> persistentTypeMap) {
		for (PersistentType persistentType : persistentTypes) {
			if (persistentType.getName() != null) {
				persistentTypeMap.put(persistentType.getName(), (PersistentType2_0) persistentType);
			}
		}
	}

	protected MetamodelSourceType selectSourceType(Iterable<MetamodelSourceType> types, String typeName) {
		if (types != null) {
			for (MetamodelSourceType type : types) {
				if (type.getName().equals(typeName)) {
					return type;
				}
			}
		}
		return null;
	}

	protected MetamodelSourceType buildNonPersistentMetamodelSourceType(String nonPersistentTypeName) {
		return new NonPersistentMetamodelSourceType(nonPersistentTypeName, this.getJpaProject());
	}

	protected IType findJdtType(String typeName) {
		try {
			return this.getJpaProject().getJavaProject().findType(typeName);
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.log(ex);
			return null;
		}
	}

	protected void deleteMetamodelFile(IFile file) {
		try {
			this.deleteMetamodelFile_(file);
		} catch (CoreException ex) {
			JptJpaCorePlugin.log(ex);
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
	protected static class NonPersistentMetamodelSourceType
		implements MetamodelSourceType
	{
		protected final String name;
		protected final JpaProject jpaProject;
		protected final MetamodelSourceType.Synchronizer metamodelSynchronizer;

		protected NonPersistentMetamodelSourceType(String name, JpaProject jpaProject) {
			super();
			this.name = name;
			this.jpaProject = jpaProject;
			this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
		}

		protected MetamodelSourceType.Synchronizer buildMetamodelSynchronizer() {
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

		public <T extends PersistentAttribute> ListIterator<T> attributes() {
			return EmptyListIterator.instance();
		}

		public IFile getMetamodelFile() {
			return this.metamodelSynchronizer.getFile();
		}

		public JpaProject getJpaProject() {
			return this.jpaProject;
		}

		public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
			this.metamodelSynchronizer.synchronize(memberTypeTree);
		}

		public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
			this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
		}
	}
}
