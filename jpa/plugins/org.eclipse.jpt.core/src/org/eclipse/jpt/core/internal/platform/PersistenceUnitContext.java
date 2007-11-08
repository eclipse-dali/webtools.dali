/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
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
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistenceUnitContext extends BaseContext
{	
	protected final static String IMPLIED_MAPPING_FILE_LOCATION = "META-INF/orm.xml";
	
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
		for (XmlRootContentNode content : allUniqueMappingFileContents()) {
			contexts.add(new MappingFileContext(this, content));
		}
		return contexts;
	}
	
	protected List<XmlRootContentNode> allUniqueMappingFileContents() {
		List<XmlRootContentNode> contents = new ArrayList<XmlRootContentNode>();
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			XmlRootContentNode xmlRootContentNode = xmlRootContentNodeFor(mappingFileRef);
			if (xmlRootContentNode != null 
					&& xmlRootContentNode.getEntityMappings() != null
					&& ! contents.contains(xmlRootContentNode)) {
				contents.add(xmlRootContentNode);
			}
		}
		XmlRootContentNode impliedMappingFileContent = impliedMappingFileContent();
		if (impliedMappingFileContent != null
					&& impliedMappingFileContent.getEntityMappings() != null
					&& ! contents.contains(impliedMappingFileContent)) {
			contents.add(impliedMappingFileContent);
		}
		return contents;
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
	
	protected XmlRootContentNode impliedMappingFileContent() {
		// check flexible project structure
		IVirtualComponent component = ComponentCore.createComponent(persistenceUnit.getJpaProject().project());
		IVirtualFolder virtualRootFolder = component.getRootFolder();
		IVirtualFile virtualMappingFile = virtualRootFolder.getFile(new Path(IMPLIED_MAPPING_FILE_LOCATION));
		// keep track of whether one has been found so that we may know if multiple exist
		IJpaFile mappingFile = null;
		for (IFile underlyingFile : virtualMappingFile.getUnderlyingFiles()) {
			IJpaFile jpaFile = JptCorePlugin.jpaFile(underlyingFile);
			if (jpaFile != null) {
				if (mappingFile != null) {
					return null; // multiple do exist
				}
				else {
					mappingFile = jpaFile;
				}
			}
		}
		if (mappingFile != null) {
			try {
				return (XmlRootContentNode) mappingFile.getContent();
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
		return new TransformationIterator<JavaClassRef, JavaPersistentType>(new CloneIterator<JavaClassRef>(persistenceUnit.getClasses())) {
			@Override
			protected JavaPersistentType transform(JavaClassRef next) {
				return javaPersistentTypeFor(next);
			}
		};
	}
	
	protected Iterator<JavaPersistentType> discoveredJavaPersistentTypes() {
		if (! persistenceUnit.getJpaProject().discoversAnnotatedClasses()) {
			return EmptyIterator.instance();
		}
		return persistenceUnit.getJpaProject().javaPersistentTypes();
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
		IType type = javaClassRef.findJdtType();
		return jpaProject().javaPersistentType(type);
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
	
	Iterator<IPersistentType> persistentTypes() {
		return 
			new CompositeIterator<IPersistentType>(
					new CompositeIterator<IPersistentType>(
							new TransformationIterator<MappingFileContext, Iterator<IPersistentType>>(mappingFileContexts.iterator()) {
								@Override
								protected Iterator<IPersistentType> transform(MappingFileContext next) {
									return next.persistentTypes();
								}
							}
						),
					new TransformationIterator<JavaTypeContext, IPersistentType>(javaPersistentTypeContexts.iterator()) {
						@Override
						protected IPersistentType transform(JavaTypeContext next) {
							return next.getPersistentType();
						}
					}
				);
	}
	
	@Override
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		super.refreshDefaults(parentDefaults, monitor);
		for (JavaTypeContext context : this.duplicateJavaPersistentTypes) {
			checkCanceled(monitor);
			// context for duplicates not be based on the persistenceUnit defaults,
			// so we're going to use the one passed here without wrapping it
			context.refreshDefaults(parentDefaults, monitor);
		}
		DefaultsContext defaults = wrapDefaultsContext(parentDefaults, monitor);
		for (MappingFileContext context : this.mappingFileContexts) {
			checkCanceled(monitor);
			context.refreshDefaults(defaults, monitor);
		}
		for (JavaTypeContext context : this.javaPersistentTypeContexts) {
			checkCanceled(monitor);
			context.refreshDefaults(defaults, monitor);
		}
		
		//TODO somehow need to clear out defaults for the duplicateJpaFiles, 
		//do i have to build JavaTypeContext for those as well?
	}
	
	private void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}		
	}

	protected DefaultsContext wrapDefaultsContext(DefaultsContext defaults, final IProgressMonitor monitor) {
		DefaultsContext puDefaults = buildPersistenceUnitDefaults(defaults);
		return new DefaultsContextWrapper(puDefaults){
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				for (Iterator<TypeContext> i = typeContexts(); i.hasNext(); ) {
					TypeContext typeContext = i.next();
					IPersistentType persistentType = typeContext.getPersistentType();
					IType jdtType = persistentType.findJdtType();
					if (jdtType != null 
							&& fullyQualifiedTypeName.equals(jdtType.getFullyQualifiedName())) {
						if (! typeContext.isRefreshed()) {
							typeContext.refreshDefaults(this, monitor);
						}
						return persistentType;
					}
				}
				return null;
			}
		};
	}
	
	protected DefaultsContext buildPersistenceUnitDefaults(DefaultsContext defaults) {
		if (persistenceUnitMetadatas.size() == 1) {
			final PersistenceUnitDefaults puDefaults = persistenceUnitMetadatas.get(0).getPersistenceUnitDefaults();
			if (puDefaults.isAllFeaturesUnset()) {
				return defaults;
			}
			
			return new DefaultsContextWrapper(defaults) {
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
					return super.getDefault(key);
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
						metadata, metadata.validationTextRange())
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
						mappingFileRef, mappingFileRef.validationTextRange())
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
						mappingFileRef, mappingFileRef.validationTextRange())
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
						mappingFileRef, mappingFileRef.validationTextRange())
				);
			}
		} 
	}

	protected HashBag<String> mappingFileNameBag() {
		List<MappingFileRef> refs = this.persistenceUnit.getMappingFiles();
		HashBag<String> fileNameBag = new HashBag<String>(refs.size());
		CollectionTools.addAll(fileNameBag, this.fileRefNames(refs.iterator()));
		return fileNameBag;
	}

	protected Iterator<String> fileRefNames(Iterator<MappingFileRef> refs) {
		return new TransformationIterator<MappingFileRef, String>(refs) {
			@Override
			protected String transform(MappingFileRef ref) {
				return ref.getFileName();
			}
		};
	}

	protected void addDuplicateMappingFileMessages(List<IMessage> messages) {
		HashBag<String> fileNameBag = this.mappingFileNameBag();
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
			if (fileNameBag.count(mappingFileRef.getFileName()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef, mappingFileRef.validationTextRange())
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
			String javaClass = javaClassRef.getJavaClass();
			if (StringTools.stringIsEmpty(javaClass)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addUnresolvedClassMessages(List<IMessage> messages) {
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			String javaClass = javaClassRef.getJavaClass();
			if (! StringTools.stringIsEmpty(javaClass) && javaClassRef.findJdtType() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
						new String[] {javaClass}, 
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addInvalidClassContentMessages(List<IMessage> messages) {
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			String javaClass = javaClassRef.getJavaClass();
			if (! StringTools.stringIsEmpty(javaClass) && javaClassRef.findJdtType() != null 
					&& (javaPersistentTypeFor(javaClassRef) == null
							|| javaPersistentTypeFor(javaClassRef).getMappingKey() == IMappingKeys.NULL_TYPE_MAPPING_KEY)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,
						new String[] {javaClassRef.getJavaClass()}, 
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		} 
	}
	
	protected HashBag<String> classNameBag() {
		List<JavaClassRef> refs = this.persistenceUnit.getClasses();
		HashBag<String> classNameBag = new HashBag<String>(refs.size());
		CollectionTools.addAll(classNameBag, this.classRefNames(refs.iterator()));
		return classNameBag;
	}

	protected Iterator<String> classRefNames(Iterator<JavaClassRef> refs) {
		return new TransformationIterator<JavaClassRef, String>(refs) {
			@Override
			protected String transform(JavaClassRef ref) {
				return ref.getJavaClass();
			}
		};
	}

	protected void addDuplicateClassMessages(List<IMessage> messages) {
		HashBag<String> classNameBag = this.classNameBag();
		for (JavaClassRef javaClassRef : persistenceUnit.getClasses()) {
			if (javaClassRef.getJavaClass() != null
					&& classNameBag.count(javaClassRef.getJavaClass()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {javaClassRef.getJavaClass()}, 
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor( this, this.persistenceUnit.getName());
	}	
}
