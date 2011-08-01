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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
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
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
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

	protected final ContextListContainer<MappingFileRef, XmlMappingFileRef> specifiedMappingFileRefContainer;
	protected MappingFileRef impliedMappingFileRef;

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
		this.propertyContainer = this.buildPropertyContainer();
		this.initializeProperties();

		this.specifiedMappingFileRefContainer = this.buildSpecifiedMappingFileRefContainer();
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
		this.updateSpecifiedClassRefs();

		this.updateSpecifiedMappingFileRefs();
		this.updateImpliedMappingFileRef();

		this.updateJarFileRefs();

		// update the implied class refs after all the other types, both
		// specified here and specified in the mapping files, are in place
		this.updateImpliedClassRefs();

		this.updateProperties();

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

	protected Iterable<String> getMappingFileRefNames() {
		return new TransformationIterable<MappingFileRef, String>(this.getMappingFileRefs()) {
			@Override
			protected String transform(MappingFileRef mappingFileRef) {
				return mappingFileRef.getFileName();
			}
		};
	}

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

	protected void updateSpecifiedMappingFileRefs() {
		this.specifiedMappingFileRefContainer.update();
	}

	protected ListIterable<XmlMappingFileRef> getXmlMappingFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlMappingFileRef>(this.xmlPersistenceUnit.getMappingFiles());
	}

	protected ContextListContainer<MappingFileRef, XmlMappingFileRef> buildSpecifiedMappingFileRefContainer() {
		return new SpecifiedMappingFileRefContainer();
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

	public ListIterable<JarFileRef> getJarFileRefs() {
		return this.jarFileRefContainer.getContextElements();
	}

	public int getJarFileRefsSize() {
		return this.jarFileRefContainer.getContextElementsSize();
	}

	protected Iterable<String> getJarFileNames() {
		return new TransformationIterable<JarFileRef, String>(this.getJarFileRefs()) {
			@Override
			protected String transform(JarFileRef jarFileRef) {
				return jarFileRef.getFileName();
			}
		};
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

	protected void updateJarFileRefs() {
		this.jarFileRefContainer.update();
	}

	protected ListIterable<XmlJarFileRef> getXmlJarFileRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlJarFileRef>(this.xmlPersistenceUnit.getJarFiles());
	}

	protected ContextListContainer<JarFileRef, XmlJarFileRef> buildJarFileRefContainer() {
		return new JarFileRefContainer();
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

	/**
	 * Return the class ref names, both specified and implied.
	 */
	protected Iterable<String> getClassRefNames() {
		return new TransformationIterable<ClassRef, String>(this.getClassRefs()) {
			@Override
			protected String transform(ClassRef classRef) {
				return classRef.getClassName();
			}
		};
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

	protected void syncSpecifiedClassRefs() {
		this.specifiedClassRefContainer.synchronizeWithResourceModel();
	}

	protected void updateSpecifiedClassRefs() {
		this.specifiedClassRefContainer.update();
	}

	protected ListIterable<XmlJavaClassRef> getXmlClassRefs() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlJavaClassRef>(this.xmlPersistenceUnit.getClasses());
	}

	protected ContextListContainer<ClassRef, XmlJavaClassRef> buildSpecifiedClassRefContainer() {
		return new SpecifiedClassRefContainer();
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

	protected void updateProperties() {
		this.propertyContainer.update();
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
		return new PropertyContainer();
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
		HashSet<String> names = new HashSet<String>(this.generators.size());
		this.addNonEmptyGeneratorNamesTo(names);
		return names;
	}

	protected void addNonEmptyGeneratorNamesTo(Set<String> names) {
		for (Generator generator : this.getGenerators()) {
			String generatorName = generator.getName();
			if (StringTools.stringIsNotEmpty(generatorName)) {
				names.add(generatorName);
			}
		}
	}

	protected void setGenerators(Iterable<Generator> generators) {
		this.synchronizeCollection(generators, this.generators, GENERATORS_COLLECTION);
	}

	/**
	 * We only hold "active" generators; i.e. the mapping file generators and
	 * the Java generators that are not "overridden" by mapping file
	 * generators (by generator name).
	 */
	protected Iterable<Generator> buildGenerators() {
		ArrayList<Generator> generatorList = new ArrayList<Generator>();

		this.addMappingFileGeneratorsTo(generatorList);

		HashMap<String, ArrayList<Generator>> mappingFileGenerators = this.mapGeneratorsByName(this.getMappingFileGenerators());
		HashMap<String, ArrayList<Generator>> javaGenerators = this.mapGeneratorsByName(this.getJavaGenerators());
		for (Map.Entry<String, ArrayList<Generator>> javaGeneratorEntry : javaGenerators.entrySet()) {
			if (mappingFileGenerators.get(javaGeneratorEntry.getKey()) == null) {
				generatorList.addAll(javaGeneratorEntry.getValue());
			}
		}

		return generatorList;
	}

	protected Iterable<Generator> getMappingFileGenerators() {
		ArrayList<Generator> generatorList = new ArrayList<Generator>();
		this.addMappingFileGeneratorsTo(generatorList);
		return generatorList;
	}

	protected void addMappingFileGeneratorsTo(ArrayList<Generator> generatorList) {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			MappingFile mappingFile = mappingFileRef.getMappingFile();
			// TODO bjv - bogus cast - need to add API to MappingFileRef?
			if (mappingFile instanceof OrmXml) {
				EntityMappings entityMappings = ((OrmXml) mappingFile).getRoot();
				if (entityMappings != null) {
					CollectionTools.addAll(generatorList, entityMappings.getSequenceGenerators());
					CollectionTools.addAll(generatorList, entityMappings.getTableGenerators());
				}
			}
		}
		this.addGeneratorsTo(this.getMappingFilePersistentTypes(), generatorList);
	}

	/**
	 * Include "overridden" Java generators.
	 */
	protected Iterable<Generator> getJavaGenerators() {
		ArrayList<Generator> generatorList = new ArrayList<Generator>();
		this.addJavaGeneratorsTo(generatorList);
		return generatorList;
	}

	/**
	 * Include "overridden" Java generators.
	 */
	protected void addJavaGeneratorsTo(ArrayList<Generator> generatorList) {
		this.addGeneratorsTo(this.getAllJavaPersistentTypesUnique(), generatorList);
	}

	// TODO bjv - bogus casts - need to delegate...
	protected void addGeneratorsTo(Iterable<PersistentType> persistentTypes, ArrayList<Generator> generatorList) {
		for (PersistentType persistentType : persistentTypes) {
			TypeMapping typeMapping = persistentType.getMapping();
			if (typeMapping instanceof Entity) {
				this.addGeneratorsTo(((Entity) typeMapping).getGeneratorContainer(), generatorList);
			}
			for (ReadOnlyPersistentAttribute persistentAttribute : persistentType.getAttributes()) {
				AttributeMapping attributeMapping = persistentAttribute.getMapping();
				if (attributeMapping instanceof IdMapping) {
					this.addGeneratorsTo(((IdMapping) attributeMapping).getGeneratorContainer(), generatorList);
				}
			}
		}
	}

	protected void addGeneratorsTo(GeneratorContainer generatorContainer, ArrayList<Generator> generatorList) {
		Generator generator = generatorContainer.getSequenceGenerator();
		if (generator != null) {
			generatorList.add(generator);
		}
		generator = generatorContainer.getTableGenerator();
		if (generator != null) {
			generatorList.add(generator);
		}
	}

	protected HashMap<String, ArrayList<Generator>> mapGeneratorsByName(Iterable<Generator> generatorList) {
		HashMap<String, ArrayList<Generator>> map = new HashMap<String, ArrayList<Generator>>();
		for (Generator generator : generatorList) {
			String generatorName = generator.getName();
			ArrayList<Generator> list = map.get(generatorName);
			if (list == null) {
				list = new ArrayList<Generator>();
				map.put(generatorName, list);
			}
			list.add(generator);
		}
		return map;
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
	 * We only hold "active" queries; i.e. the mapping file queries and
	 * the Java queries that are not "overridden" by mapping file
	 * queries (by query name).
	 */
	protected Iterable<Query> buildQueries() {
		ArrayList<Query> queryList = new ArrayList<Query>();

		this.addMappingFileQueriesTo(queryList);

		HashMap<String, ArrayList<Query>> mappingFileQueries = this.mapQueriesByName(this.getMappingFileQueries());
		HashMap<String, ArrayList<Query>> javaQueries = this.mapQueriesByName(this.getJavaQueries());
		for (Map.Entry<String, ArrayList<Query>> javaQueryEntry : javaQueries.entrySet()) {
			if (mappingFileQueries.get(javaQueryEntry.getKey()) == null) {
				queryList.addAll(javaQueryEntry.getValue());
			}
		}

		return queryList;
	}

	protected Iterable<Query> getMappingFileQueries() {
		ArrayList<Query> queryList = new ArrayList<Query>();
		this.addMappingFileQueriesTo(queryList);
		return queryList;
	}

	protected void addMappingFileQueriesTo(ArrayList<Query> queryList) {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			MappingFile mappingFile = mappingFileRef.getMappingFile();
			// TODO bjv - bogus cast - need to add API to MappingFileRef?
			if (mappingFile instanceof OrmXml) {
				EntityMappings entityMappings = ((OrmXml) mappingFile).getRoot();
				if (entityMappings != null) {
					this.addQueriesTo(entityMappings.getQueryContainer(), queryList);
				}
			}
		}
		this.addMappingFileQueriesTo(this.getMappingFilePersistentTypes(), queryList);
	}

	// TODO bjv - bogus casts - need to delegate...
	protected void addMappingFileQueriesTo(Iterable<PersistentType> persistentTypes, ArrayList<Query> queryList) {
		for (PersistentType persistentType : persistentTypes) {
			TypeMapping typeMapping = persistentType.getMapping();
			if (typeMapping instanceof Entity) {
				this.addQueriesTo(((Entity) typeMapping).getQueryContainer(), queryList);
			}
			// spec does not allow queries to be defined on mapped superclasses in orm.xml (huh?)
		}
	}

	/**
	 * Include "overridden" Java queries.
	 */
	protected Iterable<Query> getJavaQueries() {
		ArrayList<Query> queryList = new ArrayList<Query>();
		this.addJavaQueriesTo(queryList);
		return queryList;
	}

	/**
	 * Include "overridden" Java queries.
	 */
	protected void addJavaQueriesTo(ArrayList<Query> queryList) {
		this.addJavaQueriesTo(this.getAllJavaPersistentTypesUnique(), queryList);
	}

	// TODO bjv - bogus casts - need to delegate...
	protected void addJavaQueriesTo(Iterable<PersistentType> persistentTypes, ArrayList<Query> queryList) {
		for (PersistentType persistentType : persistentTypes) {
			TypeMapping typeMapping = persistentType.getMapping();
			if (typeMapping instanceof Entity) {
				this.addQueriesTo(((Entity) typeMapping).getQueryContainer(), queryList);
			}
// TODO not yet supported by Dali
//			else if (typeMapping instanceof MappedSuperclass) {
//				this.addQueriesTo(((MappedSuperclass) typeMapping).getQueryContainer(), queryList);
//			}
		}
	}

	protected void addQueriesTo(QueryContainer queryContainer, ArrayList<Query> queryList) {
		CollectionTools.addAll(queryList, queryContainer.getNamedQueries());
		CollectionTools.addAll(queryList, queryContainer.getNamedNativeQueries());
	}

	protected HashMap<String, ArrayList<Query>> mapQueriesByName(Iterable<Query> queryList) {
		HashMap<String, ArrayList<Query>> map = new HashMap<String, ArrayList<Query>>();
		for (Query query : queryList) {
			String queryName = query.getName();
			ArrayList<Query> list = map.get(queryName);
			if (list == null) {
				list = new ArrayList<Query>();
				map.put(queryName, list);
			}
			list.add(query);
		}
		return map;
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
	// TODO bjv remove - bogus cast to OrmPersistentType - probably need to add API to MappingFile
	// getOverriddenPersistentTypes()?
	protected Iterable<PersistentType> getMappingFileJavaPersistentTypes_() {
		return new TransformationIterable<PersistentType, PersistentType>(this.getMappingFilePersistentTypes()) {
			@Override
			protected PersistentType transform(PersistentType mappingFilePersistentType) {
				return (mappingFilePersistentType instanceof OrmPersistentType) ?
						this.transform((OrmPersistentType) mappingFilePersistentType) :
						null;
			}
			protected PersistentType transform(OrmPersistentType mappingFilePersistentType) {
				return mappingFilePersistentType.getMapping().isMetadataComplete() ?
						null :
						mappingFilePersistentType.getJavaPersistentType();
			}
		};
	}

	/**
	 * Return the persistence unit's Java persistent types, as specified by
	 * the class refs (both specified and implied) and jar files.
	 * There can be duplicate types, and any of them may be overridden by a
	 * mapping file persistence type.
	 * @see #getMappingFilePersistentTypes()
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<PersistentType> getJavaPersistentTypes() {
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
	 * Filters out the entities from the active type mappings
	 */
	protected Iterable<Entity> getActiveEntities() {
		return filterToEntities(this.getActiveTypeMappings());
	}

	/**
	 * Returns the "active" type mappings, i.e. the mapping file type mappings and
	 * the Java type mappings that are not "overridden" by mapping file
	 * type mappings (by full qualified class name).
	 */
	protected Iterable<TypeMapping> getActiveTypeMappings(){
		ArrayList<TypeMapping> typeMappingList = new ArrayList<TypeMapping>();
		CollectionTools.addAll(typeMappingList, this.getMappingFileTypeMappings());
		
		HashMap<String, ArrayList<TypeMapping>> mappingFileTypeMappings = this.mapTypeMappingsByClassName(this.getMappingFileTypeMappings());
		HashMap<String, ArrayList<TypeMapping>> javaTypeMappings = this.mapTypeMappingsByClassName(this.getJavaTypeMappings());
		for (Map.Entry<String, ArrayList<TypeMapping>> javaTypeMappingEntry : javaTypeMappings.entrySet()) {
			if (mappingFileTypeMappings.get(javaTypeMappingEntry.getKey()) == null) {
				typeMappingList.addAll(javaTypeMappingEntry.getValue());
			}
		}
		
		return typeMappingList;
	}

	/**
	 * Return a map of the type mappings keyed by type mapping name (short class name). 
	 * Since there can be duplicate (erroneously) type mapping names, 
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

	// ********** mapping file type mappings **********

	/**
	 * Return all the entities defined in the persistence unit's mapping files
	 * (i.e. excluding the Java entities).
	 */
	protected Iterable<Entity> getMappingFileEntities() {
		return this.filterToEntities(this.getMappingFileTypeMappings());
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

	public Iterable<String> getMappingFileMappedClassNames() {
		return new TransformationIterable<PersistentType, String>(this.getMappingFilePersistentTypes()) {
			@Override
			protected String transform(PersistentType persistentType) {
				return persistentType.getName();
			}
		};
	}

	// ********** Java type mappings **********

	/**
	 * These may be overridden in the mapping files.
	 * @see #getJavaPersistentTypes()
	 */
	public Iterable<Entity> getJavaEntities() {
		return this.filterToEntities(this.getJavaTypeMappings());
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

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
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
		this.validateEntityNames(messages, reporter);
	}

	protected void validateMappingFiles(List<IMessage> messages, IReporter reporter) {
		this.checkForMultiplePersistenceUnitMetadata(messages);
		this.checkForDuplicateMappingFiles(messages);
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			mappingFileRef.validate(messages, reporter);
		}
		this.checkForDuplicateMappingFileClasses(messages);
	}

	protected void checkForMultiplePersistenceUnitMetadata(List<IMessage> messages) {
		Iterable<MappingFileRef> pumdMappingFileRefs = this.getPersistenceUnitMetadataMappingFileRefs();
		if (CollectionTools.size(pumdMappingFileRefs) > 1) {
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

	protected void checkForDuplicateMappingFiles(List<IMessage> messages) {
		HashBag<String> fileNames = CollectionTools.bag(this.getMappingFileRefNames());
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

	protected void checkForDuplicateMappingFileClasses(List<IMessage> messages) {
		HashBag<String> classNames = CollectionTools.bag(this.getMappingFileMappedClassNames());
		for (PersistentType persistentType : this.getMappingFilePersistentTypes()) {
			String className = persistentType.getName();
			if ((className != null) && (!StringTools.stringIsEmpty(className)) 
					&&(classNames.count(className) > 1)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.NORMAL_SEVERITY,
								JpaValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS,
								new String[] {className}, 
								persistentType,
								persistentType.getValidationTextRange()
						)
				);
			}
		}		
	}

	protected void validateClassRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateClasses(messages);
		for (ClassRef classRef : this.getClassRefs()) {
			classRef.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateClasses(List<IMessage> messages) {
		HashBag<String> javaClassNames = CollectionTools.bag(this.getClassRefNames());
		for (ClassRef classRef : this.getClassRefs()) {
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
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
			jarFileRef.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateJarFileRefs(List<IMessage> messages) {
		HashBag<String> jarFileNames = CollectionTools.bag(this.getJarFileNames());
		for (JarFileRef jarFileRef : this.getJarFileRefs()) {
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
		HashMap<String, ArrayList<Generator>> generatorsByName = this.mapGeneratorsByName(this.getGenerators());
		for (Map.Entry<String, ArrayList<Generator>> entry : generatorsByName.entrySet()) {
			String generatorName = entry.getKey();
			if (StringTools.stringIsNotEmpty(generatorName)) {  // ignore empty names
				ArrayList<Generator> dups = entry.getValue();
				if (dups.size() > 1) {
					for (Generator dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
								new String[] {generatorName},
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
		for (Query query : this.getQueries()) {
			this.validate(query, messages, reporter);
		}
	}

	protected void checkForDuplicateQueries(List<IMessage> messages) {
		HashMap<String, ArrayList<Query>> queriesByName = this.mapQueriesByName(this.getQueries());
		for (Map.Entry<String, ArrayList<Query>> entry : queriesByName.entrySet()) {
			String queryName = entry.getKey();
			if (StringTools.stringIsNotEmpty(queryName)) {  // ignore empty names
				ArrayList<Query> dups = entry.getValue();
				if (dups.size() > 1) {
					for (Query dup : dups) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.QUERY_DUPLICATE_NAME,
								new String[] {queryName},
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
	protected void validate(Query query, List<IMessage> messages, IReporter reporter) {
		if (query instanceof OrmQuery) {
			((OrmQuery) query).validate(messages, reporter);
		} else {
			((JavaQuery) query).validate(messages, reporter, null);
		}
	}

	protected void validateEntityNames(List<IMessage> messages, IReporter reporter) {
		this.checkforDuplicateEntityNames(messages);
	}

	protected void checkforDuplicateEntityNames(List<IMessage> messages) {
		HashMap<String, ArrayList<Entity>> activeEntityNames = this.mapTypeMappingsByName(this.getActiveEntities());
		for (ArrayList<Entity> dups : activeEntityNames.values()) {
			if (dups.size() > 1) {
				for (Entity dup : dups) {
					if (!StringTools.stringIsEmpty(dup.getName())) {
						messages.add(
								DefaultJpaValidationMessages.buildMessage(
										IMessage.HIGH_SEVERITY,
										JpaValidationMessages.ENTITY_NAME_DUPLICATED,
										new String[] {dup.getName()},
										dup,
										this.extractNameTextRange(dup)
										)
								);
					}

				}
			}
		}		
	}
	
	protected TextRange extractNameTextRange(Entity entity) {
		return (entity instanceof OrmEntity) ?
				((OrmEntity) entity).getXmlTypeMapping().getNameTextRange():
				((JavaEntity) entity).getMappingAnnotation().getNameTextRange(null);
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
	public void synchronizeMetamodel() {
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
