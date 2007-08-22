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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * The jpaProject context used for base jpa projects.  It is assumed that
 * the jpaProject contains at least 1 persistence.xml file.  Multiple persistence.xml
 * files will be treated as an error condition, no defaults or validation based
 * on the context of a persistence-unit will be given. Currently no support for default orm.xml
 * files or defaulting annotated java files, we don't know how the information will be packaged.
 * 
 * Multiple persistence-units can be supported, but the resulting defaults/validation
 * may be incorrect.  If the persistence-units have overlap in the java files they specify
 * as mapped, then the defaults could be wrong depending on the context.  We can only use 1 set 
 * of defaults in our tooling.
 */
public class BaseJpaProjectContext extends BaseContext
{	
	private IJpaProject jpaProject;

	private List<IJpaFile> validPersistenceXmlFiles;
	private List<IJpaFile> invalidPersistenceXmlFiles;
	
	private Collection<PersistenceUnitContext> persistenceUnitContexts;

	public BaseJpaProjectContext(IJpaProject theJpaProject) {
		super(null);
		jpaProject = theJpaProject;
		validPersistenceXmlFiles = new ArrayList<IJpaFile>();
		invalidPersistenceXmlFiles = new ArrayList<IJpaFile>();
		persistenceUnitContexts = new ArrayList<PersistenceUnitContext>();
	}
	
	@Override
	protected void initialize() {
		sortPersistenceXmlFiles();
		buildPersistenceUnitContexts();
	}
	
	private void sortPersistenceXmlFiles() {
		for (IJpaFile jpaFile : persistenceXmlFiles()) {
			if (isValidPersistenceXmlLocation(jpaFile)) {
				validPersistenceXmlFiles.add(jpaFile);
			}
			else {
				invalidPersistenceXmlFiles.add(jpaFile);
			}
		}
	}
	
	private Collection<IJpaFile> persistenceXmlFiles() {
		return this.jpaProject.jpaFiles(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE);
	}
	
	private boolean isValidPersistenceXmlLocation(IJpaFile jpaFile) {
		IFile file = jpaFile.getFile();
		IProject project = jpaProject.getProject();
		
		// check flexible jpaProject structure
		IVirtualComponent component = ComponentCore.createComponent(project);
		IVirtualFolder rootFolder = component.getRootFolder();
		IVirtualFolder metaInfFolder = rootFolder.getFolder(new Path(jpaProject.rootDeployLocation() + '/' + J2EEConstants.META_INF));
		return metaInfFolder.exists() && CollectionTools.contains(metaInfFolder.getUnderlyingFolders(), file.getParent());
	}

	//TODO need to handle clearing out defaults for JpaFiles that aren't in a persistenceUnit
	// or for when there are multiple persistence.xml files
	//TODO how do we handle files being in multiple persistenceUnits?  this is valid, but
	//our tool can really only show defaults for one or the other. should clear out defaults and
	//probably have a warning letting the user know why they get no defaults or validation
	protected void buildPersistenceUnitContexts() {
		// we currently only support *one* persistence.xml file per jpaProject,
		// so we provide no defaults or validation for those that have more or less
		if (validPersistenceXmlFiles.size() == 1) {
			IJpaFile file = validPersistenceXmlFiles.get(0);
			buildPersistenceUnitContexts(getPersistence(file));
		}
	}
	
	protected void buildPersistenceUnitContexts(Persistence persistence) {
		if (persistence != null) {
			for (Iterator stream = persistence.getPersistenceUnits().iterator(); stream.hasNext();) {
				PersistenceUnit persistenceUnit = (PersistenceUnit) stream.next();
				PersistenceUnitContext persistenceUnitContext = new PersistenceUnitContext(this, persistenceUnit);
				persistenceUnitContexts.add(persistenceUnitContext);
			}
		}
	}
	
	protected Persistence getPersistence(IJpaFile persistenceXmlFile) {
		return ((PersistenceXmlRootContentNode) persistenceXmlFile.getContent()).getPersistence();
	}
	
	@Override
	public IJpaPlatform getPlatform() {
		return this.jpaProject.getPlatform();
	}
	
	protected Iterator<IJpaFile> validPersistenceXmlFiles(){
		return validPersistenceXmlFiles.iterator();
	}
	
	public void refreshDefaults(IProgressMonitor monitor) {
		refreshDefaults(null, monitor);
	}
	
	@Override
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		super.refreshDefaults(parentDefaults, monitor);
		DefaultsContext defaultsContext = buildDefaultsContext();
		for (PersistenceUnitContext context : this.persistenceUnitContexts) {
			if (monitor.isCanceled()) {
				return;
			}
			context.refreshDefaults(defaultsContext, monitor);
		}
	}
	
	private DefaultsContext buildDefaultsContext() {
		return new DefaultsContext(){
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY) 
					|| key.equals(BaseJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY)) {
					return getProjectUserSchema();
				}
				else if (key.equals(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY)) {
					return getProjectUserCatalog();
				}
				return null;
			}
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return null;
			}
			public CompilationUnit astRoot() {
				return null;
			}
		};
	}
	
	protected ConnectionProfile getProjectConnectionProfile() {
		return this.jpaProject.connectionProfile();
	}
	
	//TODO is the userName what we want to use, or do we need a preference for the user?
	private String getProjectUserSchema() {
		ConnectionProfile profile = this.getProjectConnectionProfile();
		return  profile.getUserName();
	}

	private String getProjectUserCatalog() {
		ConnectionProfile profile = this.getProjectConnectionProfile();
		return profile.getCatalogName();
	}
	
	public boolean contains(IPersistentType persistentType) {
		for (PersistenceUnitContext context : this.persistenceUnitContexts) {
			if (context.contains(persistentType)) {
				return true;
			}
		}
		return false;
	}

	private Iterator<PersistenceUnitContext> persistenceUnitContexts() {
		return this.persistenceUnitContexts.iterator();
	}
	
	int persistenceUnitContextsSize() {
		return this.persistenceUnitContexts.size();
	}

	PersistenceUnitContext persistenceUnitContext(String persistenceUnitName) {
		for (Iterator<PersistenceUnitContext> stream = persistenceUnitContexts(); stream.hasNext(); ) {
			PersistenceUnitContext puContext = stream.next();
			if (puContext.persistenceUnit().getName().equals(persistenceUnitName)) {
				return puContext;
			}
		}
		return null;
	}
	
	Iterator<PersistenceUnit> persistenceUnits() {
		return new TransformationIterator<PersistenceUnitContext, PersistenceUnit>(this.persistenceUnitContexts()) {
			@Override
			protected PersistenceUnit transform(PersistenceUnitContext next) {
				 return next.persistenceUnit();
			}
		};
	}

	public boolean containsPersistenceUnitNamed(String name) {
		return this.persistenceUnitNamed(name) != null;
	}

	PersistenceUnit persistenceUnitNamed(String name) {
		for (PersistenceUnitContext context : this.persistenceUnitContexts) {
			if( context.persistenceUnit().getName().equals(name)) {
				return context.persistenceUnit();
			}
		}
		return null;
	}
		
//	public IGeneratorRepository generatorRepository(IPersistentType persistentType) {
//		for (PersistenceUnitContext context : this.persistenceUnitContexts) {
//			if (context.contains(persistentType)) {
//				context.getGeneratorRepository();
//			}
//		}
//		return NullGeneratorRepository.instance();
//	}
	
	/* If this is true, it may be assumed that all the requirements are valid 
	 * for further validation.  For example, if this is true at the point we
	 * are validating persistence units, it may be assumed that there is a 
	 * single persistence.xml and that it has valid content down to the 
	 * persistence unit level.  */
	private boolean okToContinueValidation = true;
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addProjectLevelMessages(messages);
		if (okToContinueValidation) {
			for (PersistenceUnitContext context : persistenceUnitContexts) {
				context.addToMessages(messages);
			}
		}
		addOrphanedJavaClassMessages(messages);
	}
	
	protected void addProjectLevelMessages(List<IMessage> messages) {
		addConnectionMessages(messages);
		addNoPersistenceXmlMessage(messages);
		addMultiplePersistenceXmlMessage(messages);
		addInvalidPersistenceXmlContentMessage(messages);
		addNoPersistenceUnitMessage(messages);
		addMultiplePersistenceUnitMessage(messages);
	}
	
	protected void addConnectionMessages(List<IMessage> messages) {
		addNoConnectionMessage(messages);
		addInactiveConnectionMessage(messages);
	}
	
	protected boolean okToProceedForConnectionValidation = true;
	
	protected void addNoConnectionMessage(List<IMessage> messages) {
		if (! jpaProject.getDataSource().hasAConnection()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						IJpaValidationMessages.PROJECT_NO_CONNECTION,
						jpaProject)
				);
			okToProceedForConnectionValidation = false;
		}
	}
	
	protected void addInactiveConnectionMessage(List<IMessage> messages) {
		if (okToProceedForConnectionValidation && ! jpaProject.getDataSource().isConnected()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						IJpaValidationMessages.PROJECT_INACTIVE_CONNECTION,
						new String[] {jpaProject.getDataSource().getConnectionProfileName()},
						jpaProject)
				);
		}
		okToProceedForConnectionValidation = true;
	}
	
	protected void addNoPersistenceXmlMessage(List<IMessage> messages) {
		if (validPersistenceXmlFiles.isEmpty()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY, 
						IJpaValidationMessages.PROJECT_NO_PERSISTENCE_XML,
						jpaProject)
				);
			okToContinueValidation = false;
		}
	}
	
	protected void addMultiplePersistenceXmlMessage(List<IMessage> messages) {
		if (validPersistenceXmlFiles.size() > 1) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML,
						jpaProject)
				);
			okToContinueValidation = false;
		}
	}
	
	protected void addInvalidPersistenceXmlContentMessage(List<IMessage> messages) {
		if (validPersistenceXmlFiles.size() == 1) {
			IJpaFile persistenceXmlFile = (IJpaFile) validPersistenceXmlFiles.get(0);
			if (getPersistence(persistenceXmlFile) == null) {
				PersistenceXmlRootContentNode root = 
					(PersistenceXmlRootContentNode) persistenceXmlFile.getContent();
				messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,
							root, root.validationTextRange())
					);
				okToContinueValidation = false;
			}
		}
	}
	
	protected void addNoPersistenceUnitMessage(List<IMessage> messages) {
		if (okToContinueValidation && persistenceUnitContexts.size() == 0) {
			IJpaFile validPersistenceXml = validPersistenceXmlFiles.get(0);
			Persistence persistence = getPersistence(validPersistenceXml);
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
						persistence, persistence.validationTextRange())
				);
			okToContinueValidation = false;
		}
	}
	
	protected void addMultiplePersistenceUnitMessage(List<IMessage> messages) {
		if (okToContinueValidation && persistenceUnitContexts.size() > 1) {
			IJpaFile validPersistenceXml = validPersistenceXmlFiles.get(0);
			Persistence persistence = getPersistence(validPersistenceXml);
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS,
						persistence, persistence.validationTextRange())
				);
			okToContinueValidation = false;
		}
	}
	
	protected void addOrphanedJavaClassMessages(List<IMessage> messages) {
		for (IJpaFile jpaFile : jpaProject.jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE)) {
			for (JavaPersistentType jpType : ((JpaCompilationUnit) jpaFile.getContent()).getTypes()) {
				if (jpType.getMappingKey() != IMappingKeys.NULL_TYPE_MAPPING_KEY && ! contains(jpType)) {
					messages.add(
							JpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								IJpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CONTEXT,
								jpType.getMapping(), jpType.getMapping().validationTextRange())
						);
				}
			}
		}
	}
	
	public String toString() {
		return StringTools.buildToStringFor( this, this.jpaProject.getJavaProject().getProject().getName());
	}	
}
