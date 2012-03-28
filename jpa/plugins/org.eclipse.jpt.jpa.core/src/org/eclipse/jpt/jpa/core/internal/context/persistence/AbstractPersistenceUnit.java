/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NonEmptyStringFilter;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
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
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
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
	extends AbstractPersistenceXmlContextNode
	implements PersistenceUnit2_0
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

	protected final ContextCollectionContainer<ClassRef, String> impliedClassRefContainer;

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

	//****** JPA 2.0 features
	protected PersistenceUnitProperties connection;
	protected PersistenceUnitProperties options;

	protected SharedCacheMode specifiedSharedCacheMode;
	protected SharedCacheMode defaultSharedCacheMode;

	protected ValidationMode specifiedValidationMode;
	protected ValidationMode defaultValidationMode = DEFAULT_VALIDATION_MODE;

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

	@Override
	public void update() {
		super.update();

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

		this.setGenerators(this.buildGenerators());
		this.setQueries(this.buildQueries());

		this.setDefaultSharedCacheMode(this.buildDefaultSharedCacheMode());
		this.setDefaultValidationMode(this.buildDefaultValidationMode());
	}


	// ********** JpaContextNode implementation **********

	@Override
	public Persistence getParent() {
		return (Persistence) super.getParent();
	}

	protected Persistence getPersistence() {
		return this.getParent();
	}

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return this;
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<PersistenceUnit> getType() {
		return PersistenceUnit.class;
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

	public ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedMappingFileRef == null) ?
				this.getSpecifiedMappingFileRefs() :
				this.getCombinedMappingFileRefs();
	}

	protected ListIterable<MappingFileRef> getCombinedMappingFileRefs() {
		return new CompositeListIterable<MappingFileRef>(
				this.getSpecifiedMappingFileRefs(),
				this.impliedMappingFileRef
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

	public Iterable<MappingFileRef> getMappingFileRefsContaining(final String typeName) {
		return new FilteringIterable<MappingFileRef> (this.getMappingFileRefs()) {
			@Override
			protected boolean accept(MappingFileRef mappingFileRef) {
				return mappingFileRef.getPersistentType(typeName) != null;
			}
		};
	}

	protected Iterable<MappingFile> getMappingFiles() {
		return new FilteringIterable<MappingFile>(this.getMappingFiles_(), NotNullFilter.<MappingFile>instance());
	}

	protected Iterable<MappingFile> getMappingFiles_() {
		return new TransformationIterable<MappingFileRef, MappingFile>(this.getMappingFileRefs()) {
			@Override
			protected MappingFile transform(MappingFileRef ref) {
				return ref.getMappingFile();
			}
		};
	}


	// ********** specified mapping file refs **********

	public ListIterable<MappingFileRef> getSpecifiedMappingFileRefs() {
		return this.specifiedMappingFileRefContainer.getContextElements();
	}

	public int getSpecifiedMappingFileRefsSize() {
		return this.specifiedMappingFileRefContainer.getContextElementsSize();
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
		return this.getContextNodeFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}

	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		this.removeSpecifiedMappingFileRef(this.specifiedMappingFileRefContainer.indexOfContextElement(mappingFileRef));
	}

	public void removeSpecifiedMappingFileRef(int index) {
		this.removeSpecifiedMappingFileRef_(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
	}

	/**
	 * dispose the mapping file ref
	 */
	protected void removeSpecifiedMappingFileRef_(int index) {
		this.specifiedMappingFileRefContainer.removeContextElement(index).dispose();
	}

	protected void syncSpecifiedMappingFileRefs() {
		this.specifiedMappingFileRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlMappingFileRef> getXmlMappingFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlMappingFileRef>(this.xmlPersistenceUnit.getMappingFiles());
	}

	protected ContextListContainer<MappingFileRef, XmlMappingFileRef> buildSpecifiedMappingFileRefContainer() {
		SpecifiedMappingFileRefContainer container = new SpecifiedMappingFileRefContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified mapping file ref container
	 */
	protected class SpecifiedMappingFileRefContainer
		extends ContextListContainer<MappingFileRef, XmlMappingFileRef>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_MAPPING_FILE_REFS_LIST;
		}
		@Override
		protected MappingFileRef buildContextElement(XmlMappingFileRef resourceElement) {
			return AbstractPersistenceUnit.this.buildSpecifiedMappingFileRef(resourceElement);
		}
		@Override
		protected ListIterable<XmlMappingFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlMappingFileRefs();
		}
		@Override
		protected XmlMappingFileRef getResourceElement(MappingFileRef contextElement) {
			return contextElement.getXmlMappingFileRef();
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
		return this.getContextNodeFactory().buildImpliedMappingFileRef(this);
	}

	protected void syncImpliedMappingFileRef() {
		this.potentialImpliedMappingFileRef.synchronizeWithResourceModel();
	}

	protected void updateImpliedMappingFileRef() {
		if (this.usesImpliedMappingFile()) {
			this.setImpliedMappingFileRef(this.potentialImpliedMappingFileRef);
			this.potentialImpliedMappingFileRef.update();
		}
		else {
			this.setImpliedMappingFileRef(null);
			//this is needed to unregister the root structure node, how we build the root structure nodes probably needs to change.
			this.potentialImpliedMappingFileRef.dispose();
		}
	}

	protected boolean usesImpliedMappingFile() {
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

	public ListIterable<JarFileRef> getJarFileRefs() {
		return this.jarFileRefContainer.getContextElements();
	}

	public int getJarFileRefsSize() {
		return this.jarFileRefContainer.getContextElementsSize();
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
		return this.getContextNodeFactory().buildJarFileRef(this, xmlJarFileRef);
	}

	public void removeJarFileRef(JarFileRef jarFileRef) {
		this.removeJarFileRef(this.jarFileRefContainer.indexOfContextElement(jarFileRef));
	}

	public void removeJarFileRef(int index) {
		this.removeJarFileRef_(index);
		this.xmlPersistenceUnit.getJarFiles().remove(index);
	}

	/**
	 * dispose the JAR file ref
	 */
	protected void removeJarFileRef_(int index) {
		this.jarFileRefContainer.removeContextElement(index).dispose();
	}

	protected void syncJarFileRefs() {
		this.jarFileRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJarFileRef> getXmlJarFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlJarFileRef>(this.xmlPersistenceUnit.getJarFiles());
	}

	protected ContextListContainer<JarFileRef, XmlJarFileRef> buildJarFileRefContainer() {
		JarFileRefContainer container = new JarFileRefContainer();
		container.initialize();
		return container;
	}

	/**
	 * JAR file ref container
	 */
	protected class JarFileRefContainer
		extends ContextListContainer<JarFileRef, XmlJarFileRef>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return JAR_FILE_REFS_LIST;
		}
		@Override
		protected JarFileRef buildContextElement(XmlJarFileRef resourceElement) {
			return AbstractPersistenceUnit.this.buildJarFileRef(resourceElement);
		}
		@Override
		protected ListIterable<XmlJarFileRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlJarFileRefs();
		}
		@Override
		protected XmlJarFileRef getResourceElement(JarFileRef contextElement) {
			return contextElement.getXmlJarFileRef();
		}
	}

	// ********** class refs **********

	@SuppressWarnings("unchecked")
	public Iterable<ClassRef> getClassRefs() {
		return new CompositeIterable<ClassRef>(
						this.getSpecifiedClassRefs(),
						this.getImpliedClassRefs()
					);
	}

	public int getClassRefsSize() {
		return this.getSpecifiedClassRefsSize() + this.getImpliedClassRefsSize();
	}

	// ********** specified class refs **********

	public ListIterable<ClassRef> getSpecifiedClassRefs() {
		return this.specifiedClassRefContainer.getContextElements();
	}

	public int getSpecifiedClassRefsSize() {
		return this.specifiedClassRefContainer.getContextElementsSize();
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

	protected Iterable<ClassRef> addSpecifiedClassRefs(Iterable<String> classNames) {
		return this.addSpecifiedClassRefs(this.getSpecifiedClassRefsSize(), classNames);
	}

	protected Iterable<ClassRef> addSpecifiedClassRefs(int index, Iterable<String> classNames) {
		ArrayList<XmlJavaClassRef> xmlClassRefs = new ArrayList<XmlJavaClassRef>();
		for (String className : classNames) {
			xmlClassRefs.add(this.buildXmlJavaClassRef(className));
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
		return this.getContextNodeFactory().buildClassRef(this, xmlClassRef);
	}

	public void removeSpecifiedClassRef(ClassRef classRef) {
		this.removeSpecifiedClassRef(this.specifiedClassRefContainer.indexOfContextElement(classRef));
	}

	public void removeSpecifiedClassRef(int index) {
		this.removeSpecifiedClassRef_(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
	}

	/**
	 * dispose the class ref
	 */
	protected void removeSpecifiedClassRef_(int index) {
		this.specifiedClassRefContainer.removeContextElement(index).dispose();
	}

	protected void removeSpecifiedClassRefs(Iterable<ClassRef> classRefs) {
		ArrayList<XmlJavaClassRef> xmlClassRefs = new ArrayList<XmlJavaClassRef>();
		for (ClassRef classRef : classRefs) {
			xmlClassRefs.add(classRef.getXmlClassRef());
		}
		this.specifiedClassRefContainer.removeAll(classRefs);
		for (ClassRef classRef : classRefs) {
			classRef.dispose();
		}
		this.xmlPersistenceUnit.getClasses().removeAll(xmlClassRefs);
	}

	protected void syncSpecifiedClassRefs() {
		this.specifiedClassRefContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJavaClassRef> getXmlClassRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlJavaClassRef>(this.xmlPersistenceUnit.getClasses());
	}

	protected ContextListContainer<ClassRef, XmlJavaClassRef> buildSpecifiedClassRefContainer() {
		SpecifiedClassRefContainer container = new SpecifiedClassRefContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified class ref container
	 */
	protected class SpecifiedClassRefContainer
		extends ContextListContainer<ClassRef, XmlJavaClassRef>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_CLASS_REFS_LIST;
		}
		@Override
		protected ClassRef buildContextElement(XmlJavaClassRef resourceElement) {
			return AbstractPersistenceUnit.this.buildClassRef(resourceElement);
		}
		@Override
		protected ListIterable<XmlJavaClassRef> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlClassRefs();
		}
		@Override
		protected XmlJavaClassRef getResourceElement(ClassRef contextElement) {
			return contextElement.getXmlClassRef();
		}
	}


	// ********** virtual class refs **********

	public Iterable<ClassRef> getImpliedClassRefs() {
		return this.impliedClassRefContainer.getContextElements();
	}

	public int getImpliedClassRefsSize() {
		return this.impliedClassRefContainer.getContextElementsSize();
	}

	protected ClassRef addImpliedClassRef(String className) {
		return this.impliedClassRefContainer.addContextElement(getImpliedClassRefsSize(), className);
	}

	protected ClassRef buildClassRef(String className) {
		return this.getContextNodeFactory().buildClassRef(this, className);
	}

	protected void updateImpliedClassRefs() {
		this.impliedClassRefContainer.update();
	}

	protected Iterable<String> getImpliedClassNames() {
		return this.excludesUnlistedClasses() ?
				EmptyIterable.<String>instance() :
				this.getImpliedClassNames_();
	}

	/**
	 * Return the names of all the Java classes in the JPA project that are
	 * mapped (i.e. have the appropriate annotation etc.) but not specified
	 * in the persistence unit or any of its mapping files.
	 */
	protected Iterable<String> getImpliedClassNames_() {
		return new FilteringIterable<String>(this.getJpaProject().getMappedJavaSourceClassNames()) {
				@Override
				protected boolean accept(String mappedClassName) {
					return ! AbstractPersistenceUnit.this.specifiesPersistentType(mappedClassName);
				}
			};
	}

	protected ContextCollectionContainer<ClassRef, String> buildImpliedClassRefContainer() {
		return new ImpliedClassRefContainer();
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
	protected class ImpliedClassRefContainer
		extends ContextCollectionContainer<ClassRef, String>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return IMPLIED_CLASS_REFS_COLLECTION;
		}
		@Override
		protected ClassRef buildContextElement(String resourceElement) {
			return AbstractPersistenceUnit.this.buildClassRef(resourceElement);
		}
		@Override
		protected Iterable<String> getResourceElements() {
			return AbstractPersistenceUnit.this.getImpliedClassNames();
		}
		@Override
		protected String getResourceElement(ClassRef contextElement) {
			return contextElement.getClassName();
		}
		@Override
		public void removeContextElement(ClassRef element) {
			super.removeContextElement(element);
			element.dispose();
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
		return this.propertyContainer.getContextElements();
	}

	public int getPropertiesSize() {
		return this.propertyContainer.getContextElementsSize();
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

	public Iterable<Property> getPropertiesWithNamePrefix(final String propertyNamePrefix) {
		if (propertyNamePrefix == null) {
			throw new NullPointerException();
		}
		return new FilteringIterable<Property>(this.getProperties()) {
			@Override
			protected boolean accept(Property property) {
				String pName = property.getName();
				return (pName != null) && pName.startsWith(propertyNamePrefix);
			}
		};
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
		this.removeProperty(this.propertyContainer.indexOfContextElement(property));
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
		Property removedProperty = this.propertyContainer.removeContextElement(index);
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
	}

	protected void propertyAdded(String propertyName, String value) {
		this.propertyValueChanged(propertyName, value);
	}

	protected void propertyRemoved(String propertyName) {
		this.connection.propertyRemoved(propertyName);
		this.options.propertyRemoved(propertyName);
	}

	protected void initializeProperties() {
		this.connection = this.getContextNodeFactory().buildConnection(this);
		this.options = this.getContextNodeFactory().buildOptions(this);
	}

	protected void syncProperties() {
		this.propertyContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlProperty> getXmlProperties() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		// clone to reduce chance of concurrency problems
		return (xmlProperties == null) ?
				EmptyListIterable.<XmlProperty>instance() :
				new LiveCloneListIterable<XmlProperty>(xmlProperties.getProperties());
	}

	protected Property addProperty_(int index, XmlProperty xmlProperty) {
		Property property = this.propertyContainer.addContextElement(index, xmlProperty);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
		return property;
	}

	protected void removeProperty_(Property property) {
		this.propertyContainer.removeContextElement(property);
		if (property.getName() != null) {
			this.propertyRemoved(property.getName());
		}
	}

	protected ContextListContainer<Property, XmlProperty> buildPropertyContainer() {
		PropertyContainer container = new PropertyContainer();
		container.initialize();
		return container;
	}

	/**
	 * property container
	 */
	protected class PropertyContainer
		extends ContextListContainer<Property, XmlProperty>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PROPERTIES_LIST;
		}
		@Override
		protected Property buildContextElement(XmlProperty resourceElement) {
			return AbstractPersistenceUnit.this.buildProperty(resourceElement);
		}
		@Override
		protected ListIterable<XmlProperty> getResourceElements() {
			return AbstractPersistenceUnit.this.getXmlProperties();
		}
		@Override
		protected XmlProperty getResourceElement(Property contextElement) {
			return contextElement.getXmlProperty();
		}
		@Override
		protected Property addContextElement_(int index, Property contextElement) {
			super.addContextElement_(index, contextElement);
			if (contextElement.getName() != null) {
				propertyAdded(contextElement.getName(), contextElement.getValue());
			}
			return contextElement;
		}
		@Override
		public void removeContextElement(Property contextElement) {
			super.removeContextElement(contextElement);
			if (contextElement.getName() != null) {
				propertyRemoved(contextElement.getName());
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

	public Iterable<Generator> getGenerators() {
		return new LiveCloneIterable<Generator>(this.generators);
	}

	public int getGeneratorsSize() {
		return this.generators.size();
	}

	public Iterable<String> getUniqueGeneratorNames() {
		return CollectionTools.set(this.getNonEmptyGeneratorNames(), this.getGeneratorsSize());
	}

	protected Iterable<String> getNonEmptyGeneratorNames() {
		return new FilteringIterable<String>(this.getGeneratorNames(), NonEmptyStringFilter.instance());
	}

	protected Iterable<String> getGeneratorNames() {
		return new TransformationIterable<Generator, String>(this.getGenerators(), JpaNamedContextNode.NameTransformer.<Generator>instance());
	}

	protected void setGenerators(Iterable<Generator> generators) {
		this.synchronizeCollection(generators, this.generators, GENERATORS_COLLECTION);
	}

	/**
	 * Generators are much like queries.
	 * @see #buildQueries()
	 */
	protected Iterable<Generator> buildGenerators() {
		ArrayList<Generator> result = CollectionTools.list(this.getMappingFileGenerators());

		HashSet<String> mappingFileGeneratorNames = this.convertToNames(result);
		HashMap<String, ArrayList<JavaGenerator>> allJavaGenerators = this.mapByName(this.getAllJavaGenerators());
		for (Map.Entry<String, ArrayList<JavaGenerator>> entry : allJavaGenerators.entrySet()) {
			if ( ! mappingFileGeneratorNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<Generator> getMappingFileGenerators() {
		return new CompositeIterable<Generator>(this.getMappingFileGeneratorLists());
	}

	protected Iterable<Iterable<Generator>> getMappingFileGeneratorLists() {
		return new TransformationIterable<MappingFileRef, Iterable<Generator>>(this.getMappingFileRefs()) {
					@Override
					protected Iterable<Generator> transform(MappingFileRef mappingFileRef) {
						return mappingFileRef.getMappingFileGenerators();
					}
				};
	}

	/**
	 * Include "overridden" Java generators.
	 */
	protected Iterable<JavaGenerator> getAllJavaGenerators() {
		return new CompositeIterable<JavaGenerator>(this.getAllJavaTypeMappingGeneratorLists());
	}

	protected Iterable<Iterable<JavaGenerator>> getAllJavaTypeMappingGeneratorLists() {
		return new TransformationIterable<TypeMapping, Iterable<JavaGenerator>>(this.getAllJavaTypeMappingsUnique()) {
					@Override
					protected Iterable<JavaGenerator> transform(TypeMapping typeMapping) {
						return new SubIterableWrapper<Generator, JavaGenerator>(this.transform_(typeMapping));
					}
					protected Iterable<Generator> transform_(TypeMapping typeMapping) {
						return typeMapping.getGenerators();
					}
				};
	}

	// ***** metadata conversion
	public boolean hasConvertibleJavaGenerators() {
		return ! this.getConvertibleJavaGenerators().isEmpty();
	}

	public void convertJavaGenerators(EntityMappings entityMappings, IProgressMonitor monitor) {
		ArrayList<JavaGenerator> convertibleJavaGenerators = this.getConvertibleJavaGenerators();
		SubMonitor subMonitor = SubMonitor.convert(monitor, JptCoreMessages.JPA_METADATA_CONVERSION_CONVERTING, convertibleJavaGenerators.size());
		for (JavaGenerator generator : convertibleJavaGenerators) {
			this.convertJavaGenerator(entityMappings, generator, subMonitor.newChild(1));
		}
		subMonitor.setTaskName(JptCoreMessages.JPA_METADATA_CONVERSION_OPERATION_COMPLETE);
	}

	protected void convertJavaGenerator(EntityMappings entityMappings, JavaGenerator generator, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptCoreMessages.JPA_METADATA_CONVERSION_OPERATION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptCoreMessages.JPA_METADATA_CONVERSION_CONVERT_GENERATOR, generator.getName()));
		generator.convertTo(entityMappings);
		generator.delete();  // delete any converted generators
	}

	protected ArrayList<JavaGenerator> getConvertibleJavaGenerators() {
		return this.extractConvertibleJavaNodes(this.getAllJavaGenerators(), this.getMappingFileGenerators());
	}


	// ********** queries **********

	public Iterable<Query> getQueries() {
		return new LiveCloneIterable<Query>(this.queries);
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
		ArrayList<Query> result = CollectionTools.list(this.getMappingFileQueries());

		HashSet<String> mappingFileQueryNames = this.convertToNames(result);
		HashMap<String, ArrayList<JavaQuery>> allJavaQueries = this.mapByName(this.getAllJavaQueries());
		for (Map.Entry<String, ArrayList<JavaQuery>> entry : allJavaQueries.entrySet()) {
			if ( ! mappingFileQueryNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<Query> getMappingFileQueries() {
		return new CompositeIterable<Query>(this.getMappingFileQueryLists());
	}

	protected Iterable<Iterable<Query>> getMappingFileQueryLists() {
		return new TransformationIterable<MappingFileRef, Iterable<Query>>(this.getMappingFileRefs()) {
					@Override
					protected Iterable<Query> transform(MappingFileRef mappingFileRef) {
						return mappingFileRef.getMappingFileQueries();
					}
				};
	}

	/**
	 * Include "overridden" Java queries.
	 */
	protected Iterable<JavaQuery> getAllJavaQueries() {
		return new CompositeIterable<JavaQuery>(this.getAllJavaTypeMappingQueryLists());
	}

	protected Iterable<Iterable<JavaQuery>> getAllJavaTypeMappingQueryLists() {
		return new TransformationIterable<TypeMapping, Iterable<JavaQuery>>(this.getAllJavaTypeMappingsUnique()) {
					@Override
					protected Iterable<JavaQuery> transform(TypeMapping typeMapping) {
						return new SubIterableWrapper<Query, JavaQuery>(this.transform_(typeMapping));
					}
					protected Iterable<Query> transform_(TypeMapping typeMapping) {
						return typeMapping.getQueries();
					}
				};
	}

	protected Iterable<TypeMapping> getAllJavaTypeMappingsUnique() {
		return new TransformationIterable<PersistentType, TypeMapping>(this.getAllJavaPersistentTypesUnique()) {
					@Override
					protected TypeMapping transform(PersistentType persistentType) {
						return persistentType.getMapping();
					}
				};
	}

	// ***** metadata conversion
	public boolean hasConvertibleJavaQueries() {
		return ! this.getConvertibleJavaQueries().isEmpty();
	}

	public void convertJavaQueries(EntityMappings entityMappings, IProgressMonitor monitor) {
		OrmQueryContainer queryContainer = entityMappings.getQueryContainer();
		ArrayList<JavaQuery> convertibleJavaQueries = this.getConvertibleJavaQueries();
		SubMonitor subMonitor = SubMonitor.convert(monitor, JptCoreMessages.JPA_METADATA_CONVERSION_CONVERTING, convertibleJavaQueries.size());
		for (JavaQuery query : convertibleJavaQueries) {
			this.convertJavaQuery(queryContainer, query, subMonitor.newChild(1));
		}
		subMonitor.setTaskName(JptCoreMessages.JPA_METADATA_CONVERSION_OPERATION_COMPLETE);
	}

	protected void convertJavaQuery(OrmQueryContainer queryContainer, JavaQuery query, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptCoreMessages.JPA_METADATA_CONVERSION_OPERATION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptCoreMessages.JPA_METADATA_CONVERSION_CONVERT_QUERY, query.getName()));
		query.convertTo(queryContainer);
		query.delete();  // delete any converted queries
	}

	protected ArrayList<JavaQuery> getConvertibleJavaQueries() {
		return this.extractConvertibleJavaNodes(this.getAllJavaQueries(), this.getMappingFileQueries());
	}


	// ********** persistent types **********

	@SuppressWarnings("unchecked")
	public Iterable<PersistentType> getPersistentTypes() {
		return new CompositeIterable<PersistentType>(
				this.getMappingFilePersistentTypes(),
				this.getJavaPersistentTypes()
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

	@SuppressWarnings("unchecked")
	public Iterable<PersistentType> getJavaPersistentTypes() {
		return new CompositeIterable<PersistentType>(
				this.getClassRefPersistentTypes(),
				this.getJarFilePersistentTypes()
			);
	}

	/**
	 * Return the non-<code>null</code> class ref persistent types,
	 * both specified and implied.
	 */
	protected Iterable<PersistentType> getClassRefPersistentTypes() {
		return new FilteringIterable<PersistentType>(
					this.getClassRefPersistentTypes_(),
					NotNullFilter.<PersistentType>instance()
				);
	}

	/**
	 * Both specified and implied. May contain <code>null</code>s.
	 * @see #getClassRefPersistentTypes()
	 */
	protected Iterable<PersistentType> getClassRefPersistentTypes_() {
		return new TransformationIterable<ClassRef, PersistentType>(this.getClassRefs()) {
			@Override
			protected PersistentType transform(ClassRef classRef) {
				return classRef.getJavaPersistentType();
			}
		};
	}

	/**
	 * We only get <em>annotated</em> types from jar files.
	 */
	protected Iterable<PersistentType> getJarFilePersistentTypes() {
		return new CompositeIterable<PersistentType>(this.getJarFilePersistentTypeLists());
	}

	/**
	 * We only get <em>annotated</em> types from jar files.
	 */
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
		// search order is significant(?)
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

	/**
	 * Ignore implied class refs and jar files.
	 */
	public boolean specifiesPersistentType(String typeName) {
		for (ClassRef classRef : this.getSpecifiedClassRefs()) {
			if (classRef.isFor(typeName)) {
				return true;
			}
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.getPersistentType(typeName) != null) {
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
	protected void addPersistentTypesTo(Iterable<? extends PersistentType> persistentTypes, HashMap<String, PersistentType> persistentTypeMap) {
		for (PersistentType pt : persistentTypes) {
			String ptName = pt.getName();
			if (ptName != null) {
				persistentTypeMap.put(ptName, pt);
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
		return new FilteringIterable<PersistentType>(
					this.getMappingFileJavaPersistentTypes_(),
					NotNullFilter.<PersistentType>instance()
				);
	}

	/**
	 * The returned list will contain a <code>null</code> for each mapping file
	 * persistent type that does not correspond to an existing Java type or is
	 * marked "metadata complete".
	 * @see #getMappingFileJavaPersistentTypes()
	 */
	protected Iterable<PersistentType> getMappingFileJavaPersistentTypes_() {
		return new TransformationIterable<PersistentType, PersistentType>(this.getMappingFilePersistentTypes()) {
			@Override
			protected PersistentType transform(PersistentType mappingFilePersistentType) {
				return mappingFilePersistentType.getOverriddenPersistentType();
			}
		};
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
		return new FilteringIterable<TypeMapping>(typeMappings) {
				@Override
				protected boolean accept(TypeMapping typeMapping) {
					return typeMapping instanceof Entity;
				}
			};
	}

	// TODO bjv - this should probably *not* return Java type mappings when PU is "metadata complete"...
	protected Iterable<TypeMapping> getTypeMappings() {
		return new TransformationIterable<PersistentType, TypeMapping>(this.getPersistentTypes()) {
				@Override
				protected TypeMapping transform(PersistentType persistentType) {
					return persistentType.getMapping();  // the mapping should never be null
				}
			};
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
		ArrayList<TypeMapping> result = CollectionTools.list(this.getMappingFileTypeMappings());

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
		return new TransformationIterable<PersistentType, TypeMapping>(this.getMappingFilePersistentTypes()) {
			@Override
			protected TypeMapping transform(PersistentType persistentType) {
				return persistentType.getMapping();
			}
		};
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
		return new TransformationIterable<PersistentType, TypeMapping>(this.getJavaPersistentTypes()) {
			@Override
			protected TypeMapping transform(PersistentType persistentType) {
				return persistentType.getMapping();
			}
		};
	}


	// ********** synchronize classes **********

	public void synchronizeClasses(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 3);

		// calculate the refs to remove and add
		HashSet<String> newTypeNames = CollectionTools.set(this.getJpaProject().getMappedJavaSourceClassNames());
		ArrayList<ClassRef> deadClassRefs = new ArrayList<ClassRef>();
		HashSet<String> mappingFileTypeNames = this.getMappingFileTypeNames();

		for (ClassRef classRef : this.getSpecifiedClassRefs()) {
			JavaPersistentType specifiedJPT = classRef.getJavaPersistentType();
			if (specifiedJPT == null) {
				// Java type cannot be resolved
				deadClassRefs.add(classRef);
			} else {
				String specifiedName = specifiedJPT.getName();
				if ( ! newTypeNames.remove(specifiedName)) {
					// Java type is not annotated
					deadClassRefs.add(classRef);
				} else if (mappingFileTypeNames.contains(specifiedName)) {
					// type is also listed in a mapping file
					deadClassRefs.add(classRef);
				}
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

		this.addSpecifiedClassRefs(newTypeNames);
		sm.worked(1);
	}

	/**
	 * Return the names of all the types specified in the persistence unit's
	 * mapping files.
	 */
	protected HashSet<String> getMappingFileTypeNames() {
		HashSet<String> result = new HashSet<String>();
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			for (PersistentType persistentType : mappingFileRef.getPersistentTypes()) {
				result.add(persistentType.getName());
			}
		}
		return result;
	}


	// ********** misc **********

	public XmlPersistenceUnit getXmlPersistenceUnit() {
		return this.xmlPersistenceUnit;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			if (jarFileRef.containsOffset(textOffset)) {
				return jarFileRef;
			}
		}
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			if (mappingFileRef.containsOffset(textOffset)) {
				return mappingFileRef;
			}
		}
		for (ClassRef classRef : this.getClassRefs()) {
			if (classRef.containsOffset(textOffset)) {
				return classRef;
			}
		}
		return this;
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlPersistenceUnit != null) && this.xmlPersistenceUnit.containsOffset(textOffset);
	}

	protected HashSet<String> convertToNames(Collection<? extends JpaNamedContextNode> nodes) {
		HashSet<String> names = new HashSet<String>(nodes.size());
		for (JpaNamedContextNode node : nodes) {
			names.add(node.getName());
		}
		return names;
	}

	protected <N extends JpaNamedContextNode> HashMap<String, ArrayList<N>> mapByName(Iterable<N> nodes) {
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
	 * Return the Java nodes that are neither overridden nor duplicated
	 * (by default any Java nodes with the same name are "duplicates").
	 */
	protected <N extends JpaNamedContextNode> ArrayList<N> extractConvertibleJavaNodes(Iterable<N> allJavaNodes, Iterable<? extends JpaNamedContextNode> mappingFileNodes) {
		ArrayList<N> convertibleNodes = new ArrayList<N>();

		HashSet<String> mappingFileNodeNames = this.convertToNames(CollectionTools.list(mappingFileNodes));
		HashMap<String, ArrayList<N>> allJavaNodesByName = this.mapByName(allJavaNodes);
		for (Map.Entry<String, ArrayList<N>> entry : allJavaNodesByName.entrySet()) {
			String javaNodeName = entry.getKey();
			if (StringTools.stringIsEmpty(javaNodeName)) {
				continue;  // ignore any nodes with an empty name(?)
			}
			ArrayList<N> javaNodesWithSameName = entry.getValue();
			if ((javaNodesWithSameName.size() == 1) && ! mappingFileNodeNames.contains(javaNodeName)) {
				convertibleNodes.add(javaNodesWithSameName.get(0));
			}
		}

		return convertibleNodes;
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

	public Iterable<String> getPackageNames() {
		Set<String> packageNames = new HashSet<String>();
		for (PersistentType pType : this.getJavaPersistentTypes()) {
			JavaResourceType jrt = ((JavaPersistentType)pType).getJavaResourceType();
			packageNames.add(jrt.getPackageName());
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
		ArrayList<MappingFileRef> pumdMappingFileRefs = CollectionTools.list(this.getPersistenceUnitMetadataMappingFileRefs());
		if (pumdMappingFileRefs.size() > 1) {
			for (MappingFileRef mappingFileRef : pumdMappingFileRefs) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA,
						new String[] {mappingFileRef.getFileName()},
						mappingFileRef.getMappingFile(),
						mappingFileRef.getPersistenceUnitMetadata().getValidationTextRange()
					)
				);
			}
		}
	}

	protected Iterable<MappingFileRef> getPersistenceUnitMetadataMappingFileRefs() {
		return new FilteringIterable<MappingFileRef>(this.getMappingFileRefs()) {
			@Override
			protected boolean accept(MappingFileRef mappingFileRef) {
				return mappingFileRef.persistenceUnitMetadataExists();
			}
		};
	}

	protected void checkForDuplicateMappingFileRefs(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<MappingFileRef>> entry : this.mapMappingFileRefsByFileName().entrySet()) {
			String fileName = entry.getKey();
			if (StringTools.stringIsNotEmpty(fileName)) {
				ArrayList<MappingFileRef> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {fileName};
					for (MappingFileRef dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
								parms,
								dup,
								dup.getValidationTextRange()
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
		for (Map.Entry<String, ArrayList<PersistentType>> entry : this.mapMappingFilePersistentTypesByName().entrySet()) {
			String ptName = entry.getKey();
			if (StringTools.stringIsNotEmpty(ptName)) {
				ArrayList<PersistentType> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {ptName};
					for (PersistentType dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.NORMAL_SEVERITY,
								JpaValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS,
								parms,
								dup,
								dup.getValidationTextRange()
							)
						);
					}
				}
			}
		}
	}

	/**
	 * Return the persistence unit's mapping file persistent types
	 * keyed by their class names.
	 */
	protected HashMap<String, ArrayList<PersistentType>> mapMappingFilePersistentTypesByName() {
		HashMap<String, ArrayList<PersistentType>> map = new HashMap<String, ArrayList<PersistentType>>();
		for (PersistentType persistentType : this.getMappingFilePersistentTypes()) {
			String ptName = persistentType.getName();
			ArrayList<PersistentType> list = map.get(ptName);
			if (list == null) {
				list = new ArrayList<PersistentType>();
				map.put(ptName, list);
			}
			list.add(persistentType);
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
			if (StringTools.stringIsNotEmpty(className)) {
				ArrayList<ClassRef> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {className};
					for (ClassRef dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
								parms,
								dup,
								dup.getValidationTextRange()
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
			if (StringTools.stringIsNotEmpty(fileName)) {
				ArrayList<JarFileRef> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {fileName};
					for (JarFileRef dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE,
								parms,
								dup,
								dup.getValidationTextRange()
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
		this.checkForDuplicateGenerators(messages);
		for (Generator generator : this.getGenerators()) {
			this.validate(generator, messages, reporter);
		}
	}

	protected void checkForDuplicateGenerators(List<IMessage> messages) {
		HashMap<String, ArrayList<Generator>> generatorsByName = this.mapByName(this.getGenerators());
		for (Map.Entry<String, ArrayList<Generator>> entry : generatorsByName.entrySet()) {
			String generatorName = entry.getKey();
			if (StringTools.stringIsNotEmpty(generatorName)) {  // ignore empty names
				ArrayList<Generator> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {generatorName};
					for (Generator dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
								parms,
								dup,
								this.extractNameTextRange(dup)
							)
						);
					}
				}
			}
		}
	}

	// TODO bjv isn't it obvious?
	protected TextRange extractNameTextRange(Generator generator) {
		return (generator instanceof OrmGenerator) ?
				((OrmGenerator) generator).getNameTextRange() :
				((JavaGenerator) generator).getNameTextRange(null);
	}

	// TODO bjv isn't it obvious?
	protected void validate(Generator generator, List<IMessage> messages, IReporter reporter) {
		if (generator instanceof OrmGenerator) {
			((OrmGenerator) generator).validate(messages, reporter);
		} else {
			((JavaGenerator) generator).validate(messages, reporter, null);
		}
	}

	/**
	 * <strong>NB:</strong> We validate queries here.
	 * @see #validateGenerators(List, IReporter)
	 */
	protected void validateQueries(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateQueries(messages);

		JpaJpqlQueryHelper queryHelper = this.createJpqlQueryHelper();

		for (Query query : this.getQueries()) {
			this.validate(query, queryHelper, messages, reporter);
		}
	}

	protected void checkForDuplicateQueries(List<IMessage> messages) {
		HashMap<String, ArrayList<Query>> queriesByName = this.mapByName(this.getQueries());
		for (Map.Entry<String, ArrayList<Query>> entry : queriesByName.entrySet()) {
			String queryName = entry.getKey();
			if (StringTools.stringIsNotEmpty(queryName)) {  // ignore empty names
				ArrayList<Query> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {queryName};
					for (Query dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.QUERY_DUPLICATE_NAME,
								parms,
								dup,
								this.extractNameTextRange(dup)
							)
						);
					}
				}
			}
		}
	}

	// TODO bjv isn't it obvious?
	protected TextRange extractNameTextRange(Query query) {
		return (query instanceof OrmQuery) ?
				((OrmQuery) query).getNameTextRange() :
				((JavaQuery) query).getNameTextRange(null);
	}

	// TODO bjv isn't it obvious?
	protected void validate(Query query, JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		if (query instanceof OrmQuery) {
			((OrmQuery) query).validate(queryHelper, messages, reporter);
		}
		else {
			((JavaQuery) query).validate(queryHelper, messages, reporter, null);
		}
	}

	protected void validateEntityNames(List<IMessage> messages) {
		for (Map.Entry<String, ArrayList<Entity>> entry : this.mapTypeMappingsByName(this.getActiveEntities()).entrySet()) {
			String entityName = entry.getKey();
			if (StringTools.stringIsNotEmpty(entityName)) {
				ArrayList<Entity> dups = entry.getValue();
				if (dups.size() > 1) {
					String[] parms = new String[] {entityName};
					for (Entity dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.ENTITY_NAME_DUPLICATED,
								parms,
								dup,
								this.extractNameTextRange(dup)
							)
						);
					}

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

	protected TextRange extractNameTextRange(Entity entity) {
		return (entity instanceof OrmEntity) ?
				((OrmEntity) entity).getXmlTypeMapping().getNameTextRange():
				((JavaEntity) entity).getMappingAnnotation().getNameTextRange();
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
		return new TransformationIterable<JavaResourceAbstractType, IFile>(this.getGeneratedMetamodelTopLevelTypes()) {
			@Override
			protected IFile transform(JavaResourceAbstractType jrpt) {
				return jrpt.getFile();
			}
		};
	}

	protected Iterable<JavaResourceAbstractType> getGeneratedMetamodelTopLevelTypes() {
		return ((JpaProject2_0) this.getJpaProject()).getGeneratedMetamodelTopLevelTypes();
	}

	/**
	 * Not the prettiest code....
	 */
	// TODO check monitor for cancel
	public IStatus synchronizeMetamodel(IProgressMonitor monitor) {
		// gather up the persistent unit's types, eliminating duplicates;
		// if we have persistent types with the same name in multiple locations,
		// the last one we encounter wins (i.e. the classes in the orm.xml take
		// precedence)
		HashMap<String, PersistentType> allPersistentTypes = new HashMap<String, PersistentType>();
		this.addPersistentTypesTo(this.getJarFilePersistentTypes(), allPersistentTypes);
		this.addPersistentTypesTo(this.getClassRefPersistentTypes(), allPersistentTypes);
		this.addPersistentTypesTo(this.getMappingFilePersistentTypes(), allPersistentTypes);

		// build a list of the top-level types and a tree of their associated
		// member types etc.
		ArrayList<MetamodelSourceType> topLevelTypes = new ArrayList<MetamodelSourceType>(allPersistentTypes.size());
		HashMap<String, Collection<MetamodelSourceType>> memberTypeTree = new HashMap<String, Collection<MetamodelSourceType>>();
		for (PersistentType type1_0 : allPersistentTypes.values()) {
			PersistentType2_0 type = (PersistentType2_0) type1_0;
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
		return Status.OK_STATUS;
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

		public ListIterable<? extends ReadOnlyPersistentAttribute> getAttributes() {
			return EmptyListIterable.instance();
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
