/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IType;
import org.eclipse.jem.java.JavaClass;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode;
import org.eclipse.jpt.core.internal.content.persistence.JavaClassRef;
import org.eclipse.jpt.core.internal.content.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistenceUnitContext extends BaseContext
{	
	private PersistenceUnit persistenceUnit;
	
	private List<PersistenceUnitMetadata> persistenceUnitMetadatas;  // datas ??  datae ??  datata ??
	
	private List<MappingFileContext> mappingFileContexts;
	private List<JavaTypeContext> javaPersistentTypeContexts;
	//private Collection<JarFileContext> jarFilesContexts;
	
	private IGeneratorRepository generatorRepository;
	
	/**
	 * Stores the JavaPersistentTypeContext for JavaPersistentTypes that are referenced
	 * by more than one orm.xml file.  An error will be given to the user for this condition
	 * and the defaults will not be based on a persistence unit or orm.xml file.
	 */
	private Collection<JavaTypeContext> duplicateJavaPersistentTypes;

	
	public PersistenceUnitContext(IContext parentContext, PersistenceUnit persistenceUnit) {
		super(parentContext);
		this.persistenceUnit = persistenceUnit;
		this.persistenceUnitMetadatas = buildPersistenceUnitMetadatas();
		this.duplicateJavaPersistentTypes = new ArrayList<JavaTypeContext>();
		this.mappingFileContexts = buildMappingFileContexts();
		this.javaPersistentTypeContexts = buildJavaClassesContexts();
		this.generatorRepository = buildGeneratorRepository();
	}
	
	@Override
	protected void initialize() {
	}
	
	private List<PersistenceUnitMetadata> buildPersistenceUnitMetadatas() {
		List<PersistenceUnitMetadata> metadatas = new ArrayList<PersistenceUnitMetadata>();
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			XmlRootContentNode content = xmlRootContentNodeFor(mappingFileRef);
			if (content != null && content.getEntityMappings() != null 
					&& ! content.getEntityMappings().getPersistenceUnitMetadata().isAllFeaturesUnset()) {
				metadatas.add(content.getEntityMappings().getPersistenceUnitMetadata());
			}
		}
		return metadatas;
	}
	
	protected List<MappingFileContext> buildMappingFileContexts() {
		List<MappingFileContext> contexts = new ArrayList<MappingFileContext>();
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			XmlRootContentNode xmlRootContentNode = xmlRootContentNodeFor(mappingFileRef);
			if (xmlRootContentNode != null && xmlRootContentNode.getEntityMappings() != null) {
				contexts.add(new MappingFileContext(this, xmlRootContentNode));
			}
		}
		return contexts;
	}
	
	private XmlRootContentNode xmlRootContentNodeFor(MappingFileRef mappingFileRef) {
		IJpaFile jpaFile = mappingFileRef.getMappingFile();
		if (jpaFile != null) {
			try {
				return (XmlRootContentNode) jpaFile.getContent();
			}
			catch (ClassCastException cce) { /* do nothing, return null */ }
		}
		return null;
	}
	
	protected List<JavaTypeContext> buildJavaClassesContexts() {
		List<JavaTypeContext> javaPersistentTypeContexts = new ArrayList<JavaTypeContext>();
		for (JavaPersistentType jpType : allIncludedJavaPersistentTypes()) {
			//if it's already specified in an orm.xml file then that is its context, 
			//no need to add a javaTypeMappingContext
			if (xmlTypeMappingContextFor(jpType.getMapping()) == null) {
				JavaTypeContext javaTypeContext = 
					(JavaTypeContext) getPlatform().buildJavaTypeContext(this, jpType.getMapping());
				javaPersistentTypeContexts.add(javaTypeContext);
			}
		}
		return javaPersistentTypeContexts;
	}
	
	protected List<JavaPersistentType> allIncludedJavaPersistentTypes() {
		List<JavaPersistentType> jpTypes = new ArrayList<JavaPersistentType>();
		for (Iterator<JavaPersistentType> stream = listedJavaPersistentTypes(); stream.hasNext(); ) {
			JavaPersistentType listedJpType = stream.next();
			if (listedJpType != null && ! jpTypes.contains(listedJpType)) {
				jpTypes.add(listedJpType);
			}
		}
		for (Iterator<JavaPersistentType> stream = discoveredJavaPersistentTypes(); stream.hasNext(); ) {
			JavaPersistentType discoveredJpType = stream.next();
			if (discoveredJpType != null && discoveredJpType.getMappingKey() != IMappingKeys.NULL_TYPE_MAPPING_KEY && ! jpTypes.contains(discoveredJpType)) {
				jpTypes.add(discoveredJpType);
			}
		}
		return jpTypes;
	}
	
	protected Iterator<JavaPersistentType> listedJavaPersistentTypes() {
		return new TransformationIterator<JavaClassRef, JavaPersistentType>(persistenceUnit.getClasses().iterator()) {
			@Override
			protected JavaPersistentType transform(JavaClassRef next) {
				return javaPersistentTypeFor(next);
			}
		};
	}
	
	protected Iterator<JavaPersistentType> discoveredJavaPersistentTypes() {
		if (! persistenceUnit.getJpaProject().isDiscoverAnnotatedClasses()) {
			return EmptyIterator.instance();
		}
		Collection<IJpaFile> javaJpaFiles = persistenceUnit.getJpaProject().jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE);
		return new CompositeIterator<JavaPersistentType>(
				new TransformationIterator<IJpaFile, Iterator<JavaPersistentType>>(javaJpaFiles.iterator()) {
					@Override
					protected Iterator<JavaPersistentType> transform(IJpaFile next) {
						JpaCompilationUnit jcu = (JpaCompilationUnit) next.getContent();
						return jcu.getTypes().iterator();
					}
				}
			);
	}
	
	/**
	 * Iterate JavaTypeContexts firt to add generators to the repository.
	 * Then iterator through the MappingFileContexts and override any generators
	 * with the same name in the java
	 * 
	 * @return
	 */
	protected IGeneratorRepository buildGeneratorRepository() {
		GeneratorRepository generatorRepository = new GeneratorRepository();
		for (JavaTypeContext context : this.javaPersistentTypeContexts) {
			context.populateGeneratorRepository(generatorRepository);
		}
		for (MappingFileContext context : this.mappingFileContexts) {
			context.populateGeneratorRepository(generatorRepository);
		}

		return generatorRepository;
	}
	
	private JavaPersistentType javaPersistentTypeFor(JavaClassRef javaClassRef) {
		JavaClass javaClass = javaClassRef.getJavaClass();
		if (javaClass == null) {
			return null;
		}
		IType type = (IType) javaClass.getReflectionType();
		return jpaProject().findJavaPersistentType(type);
	}

	/**
	 * Find an XmlTypeMappingContext that references the given IJavaTYpeMapping by 
	 * specifying that java class for one of its entities, embeddables, or mapped superclasses

	 */
	public XmlTypeContext xmlTypeMappingContextFor(IJavaTypeMapping javaTypeMapping) {
		for (MappingFileContext context : this.mappingFileContexts) {
			XmlTypeContext xmlTypeMappingContext = context.xmlTypeMappingContextFor(javaTypeMapping);
			if (xmlTypeMappingContext != null) {
				return xmlTypeMappingContext;
			}
		}
		return null;
	}
	
	protected IJpaProject jpaProject() {
		return this.persistenceUnit.getJpaProject();
	}
	
	PersistenceUnit persistenceUnit() {
		return this.persistenceUnit;
	}
	
	public void refreshDefaults(DefaultsContext parentDefaults) {
		super.refreshDefaults(parentDefaults);
		for (JavaTypeContext context : this.duplicateJavaPersistentTypes) {
			// context for duplicates not be based on the persistenceUnit defaults,
			// so we're going to use the one passed here without wrapping it
			context.refreshDefaults(parentDefaults);
		}
		DefaultsContext defaults = wrapDefaultsContext(parentDefaults);
		for (MappingFileContext context : this.mappingFileContexts) {
			context.refreshDefaults(defaults);
		}
		for (JavaTypeContext context : this.javaPersistentTypeContexts) {
			context.refreshDefaults(defaults);
		}
		
		//TODO somehow need to clear out defaults for the duplicateJpaFiles, 
		//do i have to build JavaTypeContext for those as well?
	}
	
	protected DefaultsContext wrapDefaultsContext(DefaultsContext defaults) {
		final DefaultsContext puDefaults = buildPersistenceUnitDefaults(defaults);
		return new DefaultsContext(){
			public Object getDefault(String key) {
				return puDefaults.getDefault(key);
			}
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				for (Iterator<TypeContext> i = typeContexts(); i.hasNext(); ) {
					TypeContext typeContext = i.next();
					IPersistentType persistentType = typeContext.getPersistentType();
					IType jdtType = persistentType.findJdtType();
					if (jdtType != null 
							&& fullyQualifiedTypeName.equals(jdtType.getFullyQualifiedName())) {
						if (! typeContext.isRefreshed()) {
							typeContext.refreshDefaults(this);
						}
						return persistentType;
					}
				}
				return null;
			}
		};
	}
	
	protected DefaultsContext buildPersistenceUnitDefaults(final DefaultsContext defaults) {
		if (persistenceUnitMetadatas.size() == 1) {
			final PersistenceUnitDefaults puDefaults = persistenceUnitMetadatas.get(0).getPersistenceUnitDefaults();
			if (puDefaults.isAllFeaturesUnset()) {
				return defaults;
			}
			
			return new DefaultsContext() {
				public Object getDefault(String key) {
					if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY)
						|| key.equals(BaseJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY)) {
						String schema = puDefaults.getSchema();
						if (schema != null) {
							return schema;
						}
					}
					if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY)) {
						String catalog = puDefaults.getCatalog();
						if (catalog != null) {
							return catalog;
						}
					}
					if (key.equals(BaseJpaPlatform.DEFAULT_ACCESS_KEY)) {
						AccessType access = puDefaults.getAccess();
						if (access != null) {
							return access;
						}
					}
					return defaults.getDefault(key);
				}
				public IPersistentType persistentType(String fullyQualifiedTypeName) {
					return defaults.persistentType(fullyQualifiedTypeName);
				}
			};
		}
		
		return defaults;
	}
	
	private Iterator<TypeContext> typeContexts() {
		return new CompositeIterator<TypeContext>(mappingFileTypeContexts(), javaTypeContexts());
	}
	
	private Iterator mappingFileTypeContexts() {
		return new CompositeIterator(
			new TransformationIterator(this.mappingFileContexts.iterator()) {
				protected Object transform(Object next) {
					return ((MappingFileContext) next).typeContexts();
				}
			}
		);
	}
	
	private Iterator javaTypeContexts() {
		return this.javaPersistentTypeContexts.iterator();
	}
	
	public void addDuplicateJpaFile(JavaTypeContext javaPersistentTypeContext) {
		this.duplicateJavaPersistentTypes.add(javaPersistentTypeContext);
	}
	
	public boolean containsDuplicateJavaPersistentType(JavaPersistentType javaPersistentType) {
		for (JavaTypeContext context : this.duplicateJavaPersistentTypes) {
			if (context.getPersistentType() == javaPersistentType) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(IPersistentType persistentType) {
		for (MappingFileContext context : this.mappingFileContexts) {
			if (context.contains(persistentType)) {
				return true;
			}
		}
		for (JavaTypeContext context : this.javaPersistentTypeContexts) {
			if (context.contains(persistentType)) {
				return true;
			}
		}
		return false;
	}
		
	public IGeneratorRepository getGeneratorRepository() {
		return this.generatorRepository;
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addMappingFileMessages(messages);
		addClassMessages(messages);
	}
	
	protected void addMappingFileMessages(List<IMessage> messages) {
		addMultipleMetadataMessages(messages);
		addUnspecifiedMappingFileMessages(messages);
		addUnresolvedMappingFileMessages(messages);
		addInvalidMappingFileContentMessage(messages);
		addDuplicateMappingFileMessages(messages);
		
		for (MappingFileContext mappingFileContext : mappingFileContexts) {
			mappingFileContext.addToMessages(messages);
		}
	}
	
	protected void addMultipleMetadataMessages(List<IMessage> messages) {
		if (persistenceUnitMetadatas.size() > 1) {
			for (PersistenceUnitMetadata metadata : persistenceUnitMetadatas) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.ENTITY_MAPPINGS_MULTIPLE_METADATA,
						new String[] {persistenceUnit.getName()},
						metadata, metadata.getTextRange())
				);
			}
		}
	}
	
	protected void addUnspecifiedMappingFileMessages(List<IMessage> messages) {
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			if (mappingFileRef.getFileName() == null || mappingFileRef.getFileName().equals("")) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,
						mappingFileRef, mappingFileRef.getTextRange())
				);
			}
		}
	}
	
	protected void addUnresolvedMappingFileMessages(List<IMessage> messages) {
		for (Iterator stream = persistenceUnit.getMappingFiles().iterator(); stream.hasNext(); ) {
			MappingFileRef mappingFileRef = (MappingFileRef) stream.next();
			if (! (mappingFileRef.getFileName() == null || mappingFileRef.getFileName().equals(""))
					&& mappingFileRef.getMappingFile() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef, mappingFileRef.getTextRange())
				);
			}
		}
	}
	
	protected void addInvalidMappingFileContentMessage(List<IMessage> messages) {
		for (Iterator stream = persistenceUnit.getMappingFiles().iterator(); stream.hasNext(); ) {
			MappingFileRef mappingFileRef = (MappingFileRef) stream.next();
			if (mappingFileRef.getMappingFile() != null 
					&& xmlRootContentNodeFor(mappingFileRef) == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef, mappingFileRef.getTextRange())
				);
			}
		} 
	}
	
	protected void addDuplicateMappingFileMessages(List<IMessage> messages) {
		HashBag fileBag = new HashBag(
				CollectionTools.collection(
						new TransformationIterator(persistenceUnit.getMappingFiles().iterator()) {
							@Override
							protected Object transform(Object next) {
								return ((MappingFileRef) next).getFileName();
							}
						}
				)
		);
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			if (fileBag.count(mappingFileRef.getFileName()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef, mappingFileRef.getTextRange())
				);
			}
		}
	}
	
	protected void addClassMessages(List<IMessage> messages) {
		addUnspecifiedClassMessages(messages);
		addUnresolvedClassMessages(messages);
		addInvalidClassContentMessages(messages);
		addDuplicateClassMessages(messages);
		
		for (JavaTypeContext persistentTypeContext : javaPersistentTypeContexts) {
			persistentTypeContext.addToMessages(messages);
		}
	}
	
	protected void addUnspecifiedClassMessages(List<IMessage> messages) {
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			JavaClass javaClass = javaClassRef.getJavaClass();
			if (javaClass == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
						javaClassRef, javaClassRef.getTextRange())
				);
			}
		}
	}
	
	protected void addUnresolvedClassMessages(List<IMessage> messages) {
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			JavaClass javaClass = javaClassRef.getJavaClass();
			if (javaClass != null && javaClass.getReflectionType() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
						new String[] {javaClass.getQualifiedName()}, 
						javaClassRef, javaClassRef.getTextRange())
				);
			}
		}
	}
	
	protected void addInvalidClassContentMessages(List<IMessage> messages) {
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			JavaClass javaClass = javaClassRef.getJavaClass();
			if (javaClass != null && javaClass.getReflectionType() != null 
					&& (javaPersistentTypeFor(javaClassRef) == null
							|| javaPersistentTypeFor(javaClassRef).getMappingKey() == IMappingKeys.NULL_TYPE_MAPPING_KEY)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,
						new String[] {javaClassRef.getJavaClass().getQualifiedName()}, 
						javaClassRef, javaClassRef.getTextRange())
				);
			}
		} 
	}
	
	protected void addDuplicateClassMessages(List<IMessage> messages) {
		HashBag fileBag = new HashBag(
				CollectionTools.collection(
						new TransformationIterator(persistenceUnit.getClasses().iterator()) {
							@Override
							protected Object transform(Object next) {
								JavaClass javaClass = ((JavaClassRef) next).getJavaClass();
								return (javaClass == null) ? null : javaClass.getQualifiedName();
							}
						}
				)
		);
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			if (javaClassRef.getJavaClass() != null
					&& fileBag.count(javaClassRef.getJavaClass().getQualifiedName()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {javaClassRef.getJavaClass().getQualifiedName()}, 
						javaClassRef, javaClassRef.getTextRange())
				);
			}
		}
	}
	
	public String toString() {
		return StringTools.buildToStringFor( this, this.persistenceUnit.getName());
	}	
}
