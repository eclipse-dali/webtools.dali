/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * persistence-unit
 */
public abstract class AbstractPersistenceUnit
	extends AbstractXmlContextNode
	implements PersistenceUnit
{
	protected XmlPersistenceUnit xmlPersistenceUnit;

	protected String name;

	protected PersistenceUnitTransactionType specifiedTransactionType;
	protected PersistenceUnitTransactionType defaultTransactionType;

	protected String description;

	protected String provider;

	protected String jtaDataSource;
	protected String nonJtaDataSource;

	protected final Vector<MappingFileRef> specifiedMappingFileRefs = new Vector<MappingFileRef>();
	protected MappingFileRef impliedMappingFileRef;

	protected final Vector<JarFileRef> jarFileRefs = new Vector<JarFileRef>();

	protected final Vector<ClassRef> specifiedClassRefs = new Vector<ClassRef>();
	protected final Vector<ClassRef> impliedClassRefs = new Vector<ClassRef>();

	protected Boolean specifiedExcludeUnlistedClasses;

	protected final Vector<Property> properties = new Vector<Property>();

	/* global generator definitions, defined elsewhere in model */
	protected final Vector<Generator> generators = new Vector<Generator>();

	/* global query definitions, defined elsewhere in model */
	protected final Vector<Query> queries = new Vector<Query>();
	
	protected final Set<String> rootEntities = new HashSet<String>();
	
	protected AccessType defaultAccess;
	protected String defaultCatalog;
	protected String defaultSchema;
	protected boolean defaultCascadePersist;


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
		this.defaultTransactionType = this.buildDefaultTransactionType();
		this.description = xmlPersistenceUnit.getDescription();
		this.provider = xmlPersistenceUnit.getProvider();
		this.jtaDataSource = xmlPersistenceUnit.getJtaDataSource();
		this.nonJtaDataSource = xmlPersistenceUnit.getNonJtaDataSource();
		this.specifiedExcludeUnlistedClasses = xmlPersistenceUnit.getExcludeUnlistedClasses();

		// initialize the properties before building the entities because the
		// entities will need the properties
		this.initializeProperties();

		this.initializeJarFileRefs();

		//initialize specified classRefs before mappingFileRefs because of 
		//JpaFile rootStructureNode, we want the mapping file to "win",
		//as it would in a Jpa runtime implementation
		this.initializeSpecifiedClassRefs();
		this.initializeMappingFileRefs();
		//initialize implied classRefs last since they depend on both
		//specified classRefs and mappingFileRefs
		this.initializeImpliedClassRefs();
		this.initializePersistenceUnitDefaults();
	}

	/**
	 * These lists are just copies of what is distributed across the context
	 * model; so, if they have (virtually) changed, the resulting update has
	 * already been triggered. We don't need to trigger another one here.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(GENERATORS_LIST);
		nonUpdateAspectNames.add(QUERIES_LIST);
	}

	protected void initializeJarFileRefs() {
		for (XmlJarFileRef xmlJarFileRef : this.xmlPersistenceUnit.getJarFiles()) {
			this.jarFileRefs.add(this.buildJarFileRef(xmlJarFileRef));
		}
	}

	protected void initializeProperties() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			return;
		}
		for (XmlProperty xmlProperty : xmlProperties.getProperties()) {
			this.properties.add(this.buildProperty(xmlProperty));
		}
	}

	protected void initializeSpecifiedClassRefs() {
		for (XmlJavaClassRef xmlJavaClassRef : this.xmlPersistenceUnit.getClasses()) {
			this.specifiedClassRefs.add(this.buildClassRef(xmlJavaClassRef));
		}
	}

	protected void initializeMappingFileRefs() {
		for (XmlMappingFileRef xmlMappingFileRef : this.xmlPersistenceUnit.getMappingFiles()) {
			this.specifiedMappingFileRefs.add(this.buildSpecifiedMappingFileRef(xmlMappingFileRef));
		}
		if ( ! this.impliedMappingFileIsSpecified() && this.impliedMappingFileExists()) {
			this.impliedMappingFileRef = this.buildImpliedMappingFileRef();
		}
	}

	protected void initializeImpliedClassRefs() {
		if ( ! this.excludesUnlistedClasses()) {
			this.initializeImpliedClassRefs_();
		}
	}

	protected void initializeImpliedClassRefs_() {
		for (Iterator<String> stream = this.getJpaProject().annotatedClassNames(); stream.hasNext(); ) {
			String typeName = stream.next();
			if ( ! this.specifiesPersistentType(typeName)) {
				this.impliedClassRefs.add(this.buildClassRef(typeName));
			}
		}
	}

	protected void initializePersistenceUnitDefaults() {
		MappingFilePersistenceUnitDefaults defaults = this.getDefaults();
		this.defaultAccess = this.buildDefaultAccess(defaults);
		this.defaultCatalog = this.buildDefaultCatalog(defaults);
		this.defaultSchema = this.buildDefaultSchema(defaults);
		this.defaultCascadePersist = this.buildDefaultCascadePersist(defaults);
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
	
	public IContentType getContentType() {
		return getParent().getContentType();
	}

	public TextRange getSelectionTextRange() {
		return this.xmlPersistenceUnit.getSelectionTextRange();
	}

	public void dispose() {
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			stream.next().dispose();
		}
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			stream.next().dispose();
		}
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.xmlPersistenceUnit.setName(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** transaction type **********

	public PersistenceUnitTransactionType getTransactionType() {
		return (this.specifiedTransactionType != null) ? this.specifiedTransactionType : this.defaultTransactionType;
	}

	public PersistenceUnitTransactionType getSpecifiedTransactionType() {
		return this.specifiedTransactionType;
	}

	public void setSpecifiedTransactionType(PersistenceUnitTransactionType specifiedTransactionType) {
		PersistenceUnitTransactionType old = this.specifiedTransactionType;
		this.specifiedTransactionType = specifiedTransactionType;
		this.xmlPersistenceUnit.setTransactionType(PersistenceUnitTransactionType.toXmlResourceModel(specifiedTransactionType));
		this.firePropertyChanged(SPECIFIED_TRANSACTION_TYPE_PROPERTY, old, specifiedTransactionType);
	}

	public PersistenceUnitTransactionType getDefaultTransactionType() {
		return this.defaultTransactionType;
	}

	protected void setDefaultTransactionType(PersistenceUnitTransactionType defaultTransactionType) {
		PersistenceUnitTransactionType old = this.defaultTransactionType;
		this.defaultTransactionType = defaultTransactionType;
		this.firePropertyChanged(DEFAULT_TRANSACTION_TYPE_PROPERTY, old, defaultTransactionType);
	}


	// ********** description **********

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		String old = this.description;
		this.description = description;
		this.xmlPersistenceUnit.setDescription(description);
		this.firePropertyChanged(DESCRIPTION_PROPERTY, old, description);
	}


	// ********** provider **********

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		String old = this.provider;
		this.provider = provider;
		this.xmlPersistenceUnit.setProvider(provider);
		this.firePropertyChanged(PROVIDER_PROPERTY, old, provider);
	}


	// ********** JTA/non-JTA data source **********

	public String getJtaDataSource() {
		return this.jtaDataSource;
	}

	public void setJtaDataSource(String jtaDataSource) {
		String old = this.jtaDataSource;
		this.jtaDataSource = jtaDataSource;
		this.xmlPersistenceUnit.setJtaDataSource(jtaDataSource);
		this.firePropertyChanged(JTA_DATA_SOURCE_PROPERTY, old, jtaDataSource);
	}

	public String getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}

	public void setNonJtaDataSource(String nonJtaDataSource) {
		String old = this.nonJtaDataSource;
		this.nonJtaDataSource = nonJtaDataSource;
		this.xmlPersistenceUnit.setNonJtaDataSource(nonJtaDataSource);
		this.firePropertyChanged(NON_JTA_DATA_SOURCE_PROPERTY, old, nonJtaDataSource);
	}


	// ********** mapping file refs **********

	public ListIterator<MappingFileRef> mappingFileRefs() {
		return (this.impliedMappingFileRef == null) ? this.specifiedMappingFileRefs() : this.combinedMappingFileRefs();
	}

	protected ListIterator<MappingFileRef> combinedMappingFileRefs() {
		return new CompositeListIterator<MappingFileRef>(this.specifiedMappingFileRefs(), this.impliedMappingFileRef);
	}

	public int mappingFileRefsSize() {
		return (this.impliedMappingFileRef == null) ? this.specifiedMappingFileRefsSize() : this.combinedMappingFileRefsSize();
	}

	protected int combinedMappingFileRefsSize() {
		return this.specifiedMappingFileRefsSize() + 1;
	}

	public Iterator<MappingFileRef> mappingFileRefsContaining(final String typeName) {
		return new FilteringIterator<MappingFileRef, MappingFileRef> (this.mappingFileRefs()) {
			@Override
			protected boolean accept(MappingFileRef mappingFileRef) {
				return mappingFileRef.getPersistentType(typeName) != null;
			}
		};
	}


	// ********** specified mapping file refs **********

	public ListIterator<MappingFileRef> specifiedMappingFileRefs() {
		return new CloneListIterator<MappingFileRef>(this.specifiedMappingFileRefs);
	}

	public int specifiedMappingFileRefsSize() {
		return this.specifiedMappingFileRefs.size();
	}

	public MappingFileRef addSpecifiedMappingFileRef() {
		return this.addSpecifiedMappingFileRef(this.specifiedMappingFileRefs.size());
	}

	public MappingFileRef addSpecifiedMappingFileRef(int index) {
		XmlMappingFileRef xmlMappingFileRef = this.buildXmlMappingFileRef();
		MappingFileRef mappingFileRef = this.buildSpecifiedMappingFileRef(xmlMappingFileRef);
		this.specifiedMappingFileRefs.add(index, mappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(index, xmlMappingFileRef);
		this.fireItemAdded(SPECIFIED_MAPPING_FILE_REFS_LIST, index, mappingFileRef);
		return mappingFileRef;
	}

	protected XmlMappingFileRef buildXmlMappingFileRef() {
		return PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
	}

	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		this.removeSpecifiedMappingFileRef(this.specifiedMappingFileRefs.indexOf(mappingFileRef));
	}

	public void removeSpecifiedMappingFileRef(int index) {
		MappingFileRef mappingFileRef = this.specifiedMappingFileRefs.remove(index);
		mappingFileRef.dispose();
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
		this.fireItemRemoved(SPECIFIED_MAPPING_FILE_REFS_LIST, index, mappingFileRef);
	}

	protected void addSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		this.addItemToList(mappingFileRef, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
	}

	protected void removeSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		mappingFileRef.dispose();
		this.removeItemFromList(mappingFileRef, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
	}


	// ********** implied mapping file ref **********

	public MappingFileRef getImpliedMappingFileRef() {
		return this.impliedMappingFileRef;
	}

	protected MappingFileRef setImpliedMappingFileRef() {
		if (this.impliedMappingFileRef != null) {
			throw new IllegalStateException("The implied mapping file ref is already set."); //$NON-NLS-1$
		}
		this.impliedMappingFileRef = this.buildImpliedMappingFileRef();
		this.firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, null, this.impliedMappingFileRef);
		return this.impliedMappingFileRef;
	}

	protected MappingFileRef buildImpliedMappingFileRef() {
		return this.getJpaFactory().buildImpliedMappingFileRef(this);
	}

	protected void unsetImpliedMappingFileRef() {
		if (this.impliedMappingFileRef == null) {
			throw new IllegalStateException("The implied mapping file ref is already unset."); //$NON-NLS-1$
		}
		MappingFileRef old = this.impliedMappingFileRef;
		this.impliedMappingFileRef.dispose();
		this.impliedMappingFileRef = null;
		this.firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, old, null);
	}


	// ********** JAR file refs **********

	public ListIterator<JarFileRef> jarFileRefs() {
		return new CloneListIterator<JarFileRef>(this.jarFileRefs);
	}

	public int jarFileRefsSize() {
		return this.jarFileRefs.size();
	}

	public JarFileRef addJarFileRef() {
		return this.addJarFileRef(this.jarFileRefs.size());
	}

	public JarFileRef addJarFileRef(int index) {
		XmlJarFileRef xmlJarFileRef = this.buildXmlJarFileRef();
		JarFileRef jarFileRef = this.buildJarFileRef(xmlJarFileRef);
		this.jarFileRefs.add(index, jarFileRef);
		this.xmlPersistenceUnit.getJarFiles().add(index, xmlJarFileRef);
		this.fireItemAdded(JAR_FILE_REFS_LIST, index, jarFileRef);
		return jarFileRef;
	}

	protected XmlJarFileRef buildXmlJarFileRef() {
		return PersistenceFactory.eINSTANCE.createXmlJarFileRef();
	}

	public void removeJarFileRef(JarFileRef jarFileRef) {
		this.removeJarFileRef(this.jarFileRefs.indexOf(jarFileRef));
	}

	public void removeJarFileRef(int index) {
		JarFileRef jarFileRef = this.jarFileRefs.remove(index);
		jarFileRef.dispose();
		this.xmlPersistenceUnit.getJarFiles().remove(index);
		this.fireItemRemoved(JAR_FILE_REFS_LIST, index, jarFileRef);
	}

	protected void addJarFileRef_(JarFileRef jarFileRef) {
		this.addItemToList(jarFileRef, this.jarFileRefs, JAR_FILE_REFS_LIST);
	}

	protected void removeJarFileRef_(JarFileRef jarFileRef) {
		jarFileRef.dispose();
		this.removeItemFromList(jarFileRef, this.jarFileRefs, JAR_FILE_REFS_LIST);
	}


	// ********** class refs **********

	@SuppressWarnings("unchecked")
	public Iterator<ClassRef> classRefs() {
		return new CompositeIterator<ClassRef>(
						this.specifiedClassRefs(),
						this.impliedClassRefs()
					);
	}

	public int classRefsSize() {
		return this.specifiedClassRefs.size() + this.impliedClassRefs.size();
	}


	// ********** specified class refs **********

	public ListIterator<ClassRef> specifiedClassRefs() {
		return new CloneListIterator<ClassRef>(this.specifiedClassRefs);
	}

	public int specifiedClassRefsSize() {
		return this.specifiedClassRefs.size();
	}

	public ClassRef addSpecifiedClassRef() {
		return this.addSpecifiedClassRef(this.specifiedClassRefs.size());
	}

	public ClassRef addSpecifiedClassRef(int index) {
		XmlJavaClassRef xmlClassRef = this.buildXmlJavaClassRef();
		ClassRef classRef = this.buildClassRef(xmlClassRef);
		this.specifiedClassRefs.add(index, classRef);
		this.xmlPersistenceUnit.getClasses().add(index, xmlClassRef);
		this.fireItemAdded(SPECIFIED_CLASS_REFS_LIST, index, classRef);
		return classRef;
	}

	protected XmlJavaClassRef buildXmlJavaClassRef() {
		return PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
	}

	public void removeSpecifiedClassRef(ClassRef classRef) {
		this.removeSpecifiedClassRef(this.specifiedClassRefs.indexOf(classRef));
	}

	public void removeSpecifiedClassRef(int index) {
		ClassRef classRef = this.specifiedClassRefs.remove(index);
		classRef.dispose();
		this.xmlPersistenceUnit.getClasses().remove(index);
		this.fireItemRemoved(SPECIFIED_CLASS_REFS_LIST, index, classRef);
	}

	protected void addSpecifiedClassRef_(ClassRef classRef) {
		this.addItemToList(classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
	}

	protected void removeSpecifiedClassRef_(ClassRef classRef) {
		classRef.dispose();
		this.removeItemFromList(classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
	}


	// ********** implied class refs **********

	public Iterator<ClassRef> impliedClassRefs() {
		return new CloneIterator<ClassRef>(this.impliedClassRefs);
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
		return this.getJpaFactory().buildClassRef(this, className);
	}

	protected void removeImpliedClassRef(ClassRef classRef) {
		classRef.dispose();
		this.removeItemFromCollection(classRef, this.impliedClassRefs, IMPLIED_CLASS_REFS_COLLECTION);
	}


	// ********** exclude unlisted classes **********

	public boolean excludesUnlistedClasses() {
		return (this.specifiedExcludeUnlistedClasses != null) ? this.specifiedExcludeUnlistedClasses.booleanValue() : this.getDefaultExcludeUnlistedClasses();
	}

	public Boolean getSpecifiedExcludeUnlistedClasses() {
		return this.specifiedExcludeUnlistedClasses;
	}

	public void setSpecifiedExcludeUnlistedClasses(Boolean specifiedExcludeUnlistedClasses) {
		Boolean old = this.specifiedExcludeUnlistedClasses;
		this.specifiedExcludeUnlistedClasses = specifiedExcludeUnlistedClasses;
		this.xmlPersistenceUnit.setExcludeUnlistedClasses(this.specifiedExcludeUnlistedClasses);
		this.firePropertyChanged(SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY, old, specifiedExcludeUnlistedClasses);
	}

	public boolean getDefaultExcludeUnlistedClasses() {
		return false;  // ???
	}


	// ********** properties **********

	public ListIterator<Property> properties() {
		return new CloneListIterator<Property>(this.properties);
	}

	protected Iterable<Property> getProperties() {
		return new LiveCloneIterable<Property>(this.properties);
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

	public Iterator<Property> propertiesWithNamePrefix(final String propertyNamePrefix) {
		if (propertyNamePrefix == null) {
			throw new NullPointerException();
		}
		return new FilteringIterator<Property, Property>(this.properties()) {
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
		return this.addProperty(this.buildXmlProperty(), index);
	}

	protected XmlProperty buildXmlProperty() {
		return PersistenceFactory.eINSTANCE.createXmlProperty();
	}

	protected Property addProperty(XmlProperty xmlProperty, int index) {
		Property property = this.buildProperty(xmlProperty);

		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			xmlProperties = this.buildXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}

		this.properties.add(index, property);
		xmlProperties.getProperties().add(index, xmlProperty);
		this.fireItemAdded(PROPERTIES_LIST, index, property);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
		return property;
	}

	protected Property buildProperty(XmlProperty xmlProperty) {
		return this.getJpaFactory().buildProperty(this, xmlProperty);
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

	protected void addProperty_(int index, Property property) {
		this.addItemToList(index, property, this.properties, PROPERTIES_LIST);
		if (property.getName() != null) {
			this.propertyAdded(property.getName(), property.getValue());
		}
	}
	
	protected void removeProperty_(int index) {
		removeProperty_(this.properties.get(index));
	}

	protected void removeProperty_(Property property) {
		this.removeItemFromList(property, this.properties, PROPERTIES_LIST);
		if (property.getName() != null) {
			this.propertyRemoved(property.getName());
		}
	}
	
	protected void moveProperty_(int index, Property property) {
		this.moveItemInList(index, this.properties.indexOf(property), this.properties, PROPERTIES_LIST);
	}
	
	public void propertyNameChanged(String oldPropertyName, String newPropertyName, String value) {
		if (oldPropertyName == null && value == null) {
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
		// do nothing, override in subclasses as necessary
	}

	public void propertyAdded(String propertyName, String value) {
		this.propertyValueChanged(propertyName, value);
	}
	
	public void propertyRemoved(@SuppressWarnings("unused") String propertyName) {
		// do nothing, override in subclasses as necessary		
	}
	
	// ********** ORM persistence unit defaults **********

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType defaultAccess) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = defaultAccess;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, defaultAccess);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String defaultCatalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = defaultCatalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, defaultCatalog);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String defaultSchema) {
		String old = this.defaultSchema;
		this.defaultSchema = defaultSchema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, defaultSchema);
	}

	public boolean getDefaultCascadePersist() {
		return this.defaultCascadePersist;
	}

	protected void setDefaultCascadePersist(boolean defaultCascadePersist) {
		boolean old = this.defaultCascadePersist;
		this.defaultCascadePersist = defaultCascadePersist;
		this.firePropertyChanged(DEFAULT_CASCADE_PERSIST_PROPERTY, old, defaultCascadePersist);
	}


	// ********** generators **********

	public ListIterator<Generator> generators() {
		return new CloneListIterator<Generator>(this.generators);
	}

	public int generatorsSize() {
		return this.generators.size();
	}

	public void addGenerator(Generator generator) {
		this.generators.add(generator);
	}

	public String[] uniqueGeneratorNames() {
		HashSet<String> names = new HashSet<String>(this.generators.size());
		this.addNonNullGeneratorNamesTo(names);
		return names.toArray(new String[names.size()]);
	}

	protected void addNonNullGeneratorNamesTo(Set<String> names) {
		for (Iterator<Generator> stream = this.generators(); stream.hasNext(); ) {
			String generatorName = stream.next().getName();
			if (generatorName != null) {
				names.add(generatorName);
			}
		}
	}


	// ********** queries **********

	public ListIterator<Query> queries() {
		return new CloneListIterator<Query>(this.queries);
	}

	public int queriesSize() {
		return this.queries.size();
	}

	public void addQuery(Query query) {
		this.queries.add(query);
	}

	public void addRootWithSubEntities(String entityName) {
		this.rootEntities.add(entityName);
	}
	
	public boolean isRootWithSubEntities(String entityName) {
		return this.rootEntities.contains(entityName);
	}
	
	// ********** updating **********

	public void update(XmlPersistenceUnit xpu) {
		this.xmlPersistenceUnit = xpu;

		// the 'generators' and 'queries' lists are simply cleared out with each
		// "update" and completely rebuilt as the "update" cascades through
		// the persistence unit. When the persistence unit's "update" is
		// complete, the lists have been populated and we fire the change event.
		// @see #addGenerator(Generator) (and references)
		// @see #addQuery(Query) (and references)
		this.generators.clear();
		this.queries.clear();

		this.rootEntities.clear();
		
		this.setName(xpu.getName());
		this.setSpecifiedTransactionType(this.buildSpecifiedTransactionType());
		this.setDefaultTransactionType(this.buildDefaultTransactionType());
		this.setDescription(xpu.getDescription());
		this.setProvider(xpu.getProvider());
		this.setJtaDataSource(xpu.getJtaDataSource());
		this.setNonJtaDataSource(xpu.getNonJtaDataSource());
		this.updateJarFileRefs();

		// update 'specifiedClassRefs' before 'mappingFileRefs' because of 
		// JpaFile rootStructureNode, we want the mapping file to "win",
		// as it would in a JPA runtime implementation
		this.updateSpecifiedClassRefs();
		this.updateMappingFileRefs();

		// update 'impliedClassRefs' last since it depends on the contents of
		// both 'specifiedClassRefs' and 'mappingFileRefs'
		this.updateImpliedClassRefs();

		this.setSpecifiedExcludeUnlistedClasses(xpu.getExcludeUnlistedClasses());
		this.updateProperties();
		this.updatePersistenceUnitDefaults();

		// see comment at top of method
		this.fireListChanged(GENERATORS_LIST, this.generators);
		this.fireListChanged(QUERIES_LIST, this.queries);
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		for (ClassRef classRef : CollectionTools.iterable(this.specifiedClassRefs())) {
			classRef.postUpdate();
		}
		for (ClassRef classRef : CollectionTools.iterable(this.impliedClassRefs())) {
			classRef.postUpdate();
		}
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(this.specifiedMappingFileRefs())) {
			mappingFileRef.postUpdate();
		}
		if (this.impliedMappingFileRef != null) {
			this.impliedMappingFileRef.postUpdate();
		}
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

	/**
	 * Since this is a *list*, we simply loop through the elements and match
	 * the context to the resource element by index, not by name like we do
	 * with 'impliedClassRefs'.
	 */
	protected void updateJarFileRefs() {
		// make a copy of the XML file refs (to prevent ConcurrentModificationException)
		Iterator<XmlJarFileRef> xmlFileRefs = new CloneIterator<XmlJarFileRef>(this.xmlPersistenceUnit.getJarFiles());

		for (Iterator<JarFileRef> contextFileRefs = this.jarFileRefs(); contextFileRefs.hasNext(); ) {
			JarFileRef contextFileRef = contextFileRefs.next();
			if (xmlFileRefs.hasNext()) {
				contextFileRef.update(xmlFileRefs.next());
			} else {
				this.removeJarFileRef_(contextFileRef);
			}
		}

		while (xmlFileRefs.hasNext()) {
			this.addJarFileRef_(this.buildJarFileRef(xmlFileRefs.next()));
		}
	}

	protected JarFileRef buildJarFileRef(XmlJarFileRef xmlJarFileRef) {
		return this.getJpaFactory().buildJarFileRef(this, xmlJarFileRef);
	}

	/**
	 * Since this is a *list*, we simply loop through the elements and match
	 * the context to the resource element by index, not by name like we do
	 * with 'impliedClassRefs'.
	 */
	protected void updateSpecifiedClassRefs() {
		// make a copy of the XML class refs (to prevent ConcurrentModificationException)
		Iterator<XmlJavaClassRef> xmlClassRefs = new CloneIterator<XmlJavaClassRef>(this.xmlPersistenceUnit.getClasses());

		for (Iterator<ClassRef> contextClassRefs = this.specifiedClassRefs(); contextClassRefs.hasNext(); ) {
			ClassRef contextClassRef = contextClassRefs.next();
			if (xmlClassRefs.hasNext()) {
				contextClassRef.update(xmlClassRefs.next());
			} else {
				this.removeSpecifiedClassRef_(contextClassRef);
			}
		}

		while (xmlClassRefs.hasNext()) {
			this.addSpecifiedClassRef_(this.buildClassRef(xmlClassRefs.next()));
		}
	}

	protected ClassRef buildClassRef(XmlJavaClassRef xmlClassRef) {
		return this.getJpaFactory().buildClassRef(this, xmlClassRef);
	}

	/**
	 * Since this is a *list*, we simply loop through the elements and match
	 * the context to the resource element by index, not by name like we do
	 * with 'impliedClassRefs'.
	 */
	protected void updateMappingFileRefs() {
		// first update the specified mapping file refs...
		// make a copy of the XML file refs (to prevent ConcurrentModificationException)
		Iterator<XmlMappingFileRef> xmlFileRefs = new CloneIterator<XmlMappingFileRef>(this.xmlPersistenceUnit.getMappingFiles());

		for (Iterator<MappingFileRef> contextFileRefs = this.specifiedMappingFileRefs(); contextFileRefs.hasNext(); ) {
			MappingFileRef contextFileRef = contextFileRefs.next();
			if (xmlFileRefs.hasNext()) {
				contextFileRef.update(xmlFileRefs.next());
			} else {
				this.removeSpecifiedMappingFileRef_(contextFileRef);
			}
		}

		while (xmlFileRefs.hasNext()) {
			this.addSpecifiedMappingFileRef_(this.buildSpecifiedMappingFileRef(xmlFileRefs.next()));
		}

		// ...then update the implied mapping file ref
		if (this.impliedMappingFileIsSpecified()) {
			if (this.impliedMappingFileRef != null) {
				this.unsetImpliedMappingFileRef();
			}
		} else {
			if (this.impliedMappingFileExists()) {
				if (this.impliedMappingFileRef == null) {
					this.setImpliedMappingFileRef();
				}
				this.impliedMappingFileRef.update(null);
			} else {
				if (this.impliedMappingFileRef != null) {
					this.unsetImpliedMappingFileRef();
				}
			}
		}
	}

	protected MappingFileRef buildSpecifiedMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		return this.getJpaFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}

	protected boolean impliedMappingFileIsSpecified() {
		String impliedMappingFileName = JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH;
		for (Iterator<MappingFileRef> stream = this.specifiedMappingFileRefs(); stream.hasNext(); ) {
			if (impliedMappingFileName.equals(stream.next().getFileName())) {
				return true;
			}
		}
		return false;
	}

	protected boolean impliedMappingFileExists() {
		return getJpaProject().getDefaultOrmXmlResource() != null;
	}

	protected void updateImpliedClassRefs() {
		if (this.excludesUnlistedClasses()) {
			this.clearImpliedClassRefs_();
		} else {
			this.updateImpliedClassRefs_();
		}
	}

	protected void updateImpliedClassRefs_() {
		HashBag<ClassRef> impliedRefsToRemove = CollectionTools.bag(this.impliedClassRefs(), this.impliedClassRefsSize());
		ArrayList<ClassRef> impliedRefsToUpdate = new ArrayList<ClassRef>(this.impliedClassRefsSize());

		for (Iterator<String> annotatedClassNames = this.getJpaProject().annotatedClassNames(); annotatedClassNames.hasNext(); ) {
			String annotatedClassName = annotatedClassNames.next();
			if ( ! this.specifiesPersistentType(annotatedClassName)) {
				boolean match = false;
				for (Iterator<ClassRef> classRefs = impliedRefsToRemove.iterator(); classRefs.hasNext(); ) {
					ClassRef classRef = classRefs.next();
					if (annotatedClassName.equals(classRef.getClassName())) {
						classRefs.remove();
						impliedRefsToUpdate.add(classRef);
						match = true;
						break;
					}
				}
				if ( ! match) {
					this.addImpliedClassRef(annotatedClassName);
				}
			}
		}
		for (ClassRef classRef : impliedRefsToRemove) {
			this.removeImpliedClassRef(classRef);
		}
		// handle adding and removing implied class refs first, update the
		// remaining implied class refs last; this reduces the churn during "update"
		for (ClassRef classRef : impliedRefsToUpdate) {
			classRef.update(classRef.getClassName());
		}
	}

	protected void clearImpliedClassRefs_() {
		for (Iterator<ClassRef> stream = this.impliedClassRefs(); stream.hasNext(); ) {
			this.removeImpliedClassRef(stream.next());
		}
	}

	/**
	 * Match the elements based on the XmlProperty resource object and also keep the order
	 * the same as the source.
	 */
	protected void updateProperties() {		
		HashBag<Property> contextPropertiesToRemove = CollectionTools.bag(this.properties(), this.propertiesSize());
		int resourceIndex = 0;
		
		for (Iterator<XmlProperty> xmlProperties = this.xmlProperties(); xmlProperties.hasNext(); ) {
			XmlProperty xmlProperty = xmlProperties.next();
			boolean match = false;
			for (Iterator<Property> contextProperties = contextPropertiesToRemove.iterator(); contextProperties.hasNext();) {
				Property contextProperty = contextProperties.next();
				if (contextProperty.getXmlProperty() == xmlProperty) {
					contextProperties.remove();
					this.moveProperty_(resourceIndex, contextProperty);
					contextProperty.update();
					match = true;
					break;
				}
			}
			if ( ! match) {
				this.addProperty_(resourceIndex, this.buildProperty(xmlProperty));
			}
			resourceIndex++;
		}
		for (Property contextProperty : contextPropertiesToRemove) {
			this.removeProperty_(contextProperty);
		}
	}

	protected Iterator<XmlProperty> xmlProperties() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		// make a copy of the XML properties (to prevent ConcurrentModificationException)
		return (xmlProperties != null) ? new CloneIterator<XmlProperty>(xmlProperties.getProperties()) : EmptyIterator.<XmlProperty>instance();
	}
	
	protected int xmlPropertiesSize() {
		XmlProperties xmlProperties = this.xmlPersistenceUnit.getProperties();
		return xmlProperties == null ? 0 : xmlProperties.getProperties().size();
	}

	protected void updatePersistenceUnitDefaults() {
		MappingFilePersistenceUnitDefaults defaults = this.getDefaults();
		this.setDefaultAccess(this.buildDefaultAccess(defaults));
		this.setDefaultCatalog(this.buildDefaultCatalog(defaults));
		this.setDefaultSchema(this.buildDefaultSchema(defaults));
		this.setDefaultCascadePersist(this.buildDefaultCascadePersist(defaults));
	}

	/**
	 * return the first persistence unit defaults we encounter in a mapping file
	 */
	protected MappingFilePersistenceUnitDefaults getDefaults() {
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			MappingFileRef mappingFileRef = stream.next();
			if (mappingFileRef.persistenceUnitDefaultsExists()) {
				return mappingFileRef.getPersistenceUnitDefaults();
			}
		}
		return null;
	}

	protected AccessType buildDefaultAccess(MappingFilePersistenceUnitDefaults defaults) {
		return (defaults == null) ? null : defaults.getAccess();
	}

	protected String buildDefaultCatalog(MappingFilePersistenceUnitDefaults defaults) {
		String catalog = (defaults == null) ? null : defaults.getCatalog();
		return (catalog != null) ? catalog : this.getJpaProject().getDefaultCatalog();
	}

	protected String buildDefaultSchema(MappingFilePersistenceUnitDefaults defaults) {
		String schema = (defaults == null) ? null : defaults.getSchema();
		return (schema != null) ? schema : this.getJpaProject().getDefaultSchema();
	}

	protected boolean buildDefaultCascadePersist(MappingFilePersistenceUnitDefaults defaults) {
		return (defaults == null) ? false : defaults.isCascadePersist();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateMappingFiles(messages, reporter);
		validateClassRefs(messages, reporter);
		validateJarFileRefs(messages, reporter);
	}

	protected void validateMappingFiles(List<IMessage> messages, IReporter reporter) {
		this.checkForMultiplePersistenceUnitDefaults(messages);
		this.checkForDuplicateMappingFiles(messages);
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext();) {
			stream.next().validate(messages, reporter);
		}
	}

	protected void checkForMultiplePersistenceUnitDefaults(List<IMessage> messages) {
		Iterator<MappingFileRef> stream = mappingFileRefs();
		while (stream.hasNext()) {
			if (stream.next().persistenceUnitDefaultsExists()) {
				break;
			}
		}
		while (stream.hasNext()) {
			MappingFileRef mappingFileRef = stream.next();
			if (mappingFileRef.persistenceUnitDefaultsExists()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_DEFAULTS,
						new String[] {mappingFileRef.getFileName()},
						mappingFileRef
					)
				);
			}
		}
	}

	protected void checkForDuplicateMappingFiles(List<IMessage> messages) {
		HashBag<String> fileNames = new HashBag<String>();
		CollectionTools.addAll(fileNames, this.mappingFileRefNames());
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			MappingFileRef mappingFileRef = stream.next();
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

	protected Iterator<String> mappingFileRefNames() {
		return new TransformationIterator<MappingFileRef, String>(this.mappingFileRefs()) {
			@Override
			protected String transform(MappingFileRef mappingFileRef) {
				return mappingFileRef.getFileName();
			}
		};
	}

	protected void validateClassRefs(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateClasses(messages);
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}

	protected void checkForDuplicateClasses(List<IMessage> messages) {
		HashBag<String> classNames = new HashBag<String>();
		CollectionTools.addAll(classNames, this.classRefNames());
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			ClassRef classRef = stream.next();
			String className = classRef.getClassName();
			if ((className != null) && (classNames.count(className) > 1)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {className}, 
						classRef, 
						classRef.getValidationTextRange()
					)
				);
			}
		}
	}

	protected Iterator<String> classRefNames() {
		return new TransformationIterator<ClassRef, String>(this.classRefs()) {
			@Override
			protected String transform(ClassRef classRef) {
				return classRef.getClassName();
			}
		};
	}
	
	protected void validateJarFileRefs(List<IMessage> messages, IReporter reporter) {
		checkForDuplicateJarFileRefs(messages);
		for (JarFileRef each : CollectionTools.iterable(jarFileRefs())) {
			each.validate(messages, reporter);
		}
	}
	
	protected void checkForDuplicateJarFileRefs(List<IMessage> messages) {
		HashBag<String> jarFileNames = new HashBag<String>();
		CollectionTools.addAll(jarFileNames, jarFileNames());
		for (JarFileRef jarFileRef : CollectionTools.iterable(jarFileRefs())) {
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
	
	protected Iterator<String> jarFileNames() {
		return new TransformationIterator<JarFileRef, String>(jarFileRefs()) {
			@Override
			protected String transform(JarFileRef jarFileRef) {
				return jarFileRef.getFileName();
			}
		};
	}


	// ********** misc **********

	public JpaStructureNode getStructureNode(int textOffset) {
		for (Iterator<JarFileRef> stream = this.jarFileRefs(); stream.hasNext(); ) {
			JarFileRef jarFileRef = stream.next();
			if (jarFileRef.containsOffset(textOffset)) {
				return jarFileRef;
			}
		}
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			MappingFileRef mappingFileRef = stream.next();
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
	
	public boolean shouldValidateAgainstDatabase() {
		return connectionProfileIsActive();
	}

	public TextRange getValidationTextRange() {
		return this.xmlPersistenceUnit.getValidationTextRange();
	}

	public PersistentType getPersistentType(String typeName) {
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			PersistentType persistentType = stream.next().getPersistentType(typeName);
			if (persistentType != null) {
				return persistentType;
			}
		}
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			ClassRef classRef = stream.next();
			if (classRef.isFor(typeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		for (Iterator<JarFileRef> stream = this.jarFileRefs(); stream.hasNext(); ) {
			PersistentType persistentType = stream.next().getPersistentType(typeName);
			if (persistentType != null) {
				return persistentType;
			}
		}
		return null;
	}

	public boolean specifiesPersistentType(String className) {
		for (Iterator<ClassRef> stream = this.specifiedClassRefs(); stream.hasNext(); ) {
			if (className.equals(stream.next().getClassName())) {
				return true;
			}
		}
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			if (stream.next().getPersistentType(className) != null) {
				return true;
			}
		}
		return false;
	}

	public Entity getEntity(String typeName) {
		PersistentType persistentType = this.getPersistentType(typeName);
		if (persistentType == null) {
			return null;
		}
		TypeMapping typeMapping = persistentType.getMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public Embeddable getEmbeddable(String typeName) {
		PersistentType persistentType = this.getPersistentType(typeName);
		if (persistentType == null) {
			return null;
		}
		TypeMapping typeMapping = persistentType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlPersistenceUnit != null) && this.xmlPersistenceUnit.containsOffset(textOffset);
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

}
