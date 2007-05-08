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
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IJpaCoreConstants;
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
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * The project context used for base jpa projects.  It is assumed that
 * the project contains at least 1 persistence.xml file.  Multiple persistence.xml
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
	private IJpaProject project;

	private List<IJpaFile> validPersistenceXmlFiles;
	private List<IJpaFile> invalidPersistenceXmlFiles;
	
	private Collection<PersistenceUnitContext> persistenceUnitContexts;

	public BaseJpaProjectContext(IJpaProject jpaProject) {
		super(null);
		project = jpaProject;
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
		return this.project.jpaFiles(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE);
	}
	
	private boolean isValidPersistenceXmlLocation(IJpaFile jpaFile) {
		IFile file = jpaFile.getFile();
		IContainer folder = file.getParent();
		if ((folder.getType() != IContainer.FOLDER) || ! folder.getName().equals(IJpaCoreConstants.META_INF)) {
			return false;
		}
		IJavaElement sourceFolder = JavaCore.create(((IFolder) folder).getParent());
		if (sourceFolder == null || sourceFolder.getElementType() != IJavaElement.PACKAGE_FRAGMENT_ROOT) {
			return false;
		}
		try {
			if (((IPackageFragmentRoot) sourceFolder).getKind() != IPackageFragmentRoot.K_SOURCE) {
				return false;
			}
		}
		catch (Throwable t) {
			return false;
		}
		
		return true;
	}

	//TODO need to handle clearing out defaults for JpaFiles that aren't in a persistenceUnit
	// or for when there are multiple persistence.xml files
	//TODO how do we handle files being in multiple persistenceUnits?  this is valid, but
	//our tool can really only show defaults for one or the other. should clear out defaults and
	//probably have a warning letting the user know why they get no defaults or validation
	protected void buildPersistenceUnitContexts() {
		// we currently only support *one* persistence.xml file per project,
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
		return this.project.getPlatform();
	}
	
	protected Iterator<IJpaFile> validPersistenceXmlFiles(){
		return validPersistenceXmlFiles.iterator();
	}
	
	public void refreshDefaults() {
		refreshDefaults(null);
	}
	
	public void refreshDefaults(DefaultsContext parentDefaults) {
		super.refreshDefaults(parentDefaults);
		DefaultsContext defaultsContext = buildDefaultsContext();
		for (PersistenceUnitContext context : this.persistenceUnitContexts) {
			context.refreshDefaults(defaultsContext);
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
		};
	}
	
	protected ConnectionProfile getProjectConnectionProfile() {
		return this.project.connectionProfile();
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
		Connection connection = project.getDataSource().getConnection();
		if (connection == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						IJpaValidationMessages.PROJECT_NO_CONNECTION,
						project)
				);
			okToProceedForConnectionValidation = false;
		}
	}
	
	protected void addInactiveConnectionMessage(List<IMessage> messages) {
		Connection connection = project.getDataSource().getConnection();
		if (okToProceedForConnectionValidation && ! connection.isConnected()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						IJpaValidationMessages.PROJECT_INACTIVE_CONNECTION,
						new String[] {project.getDataSource().getConnectionProfileName()},
						project)
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
						project)
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
						project)
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
							root, root.getTextRange())
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
						persistence, persistence.getTextRange())
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
						persistence, persistence.getTextRange())
				);
			okToContinueValidation = false;
		}
	}
	
	protected void addOrphanedJavaClassMessages(List<IMessage> messages) {
		for (IJpaFile jpaFile : project.jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE)) {
			for (JavaPersistentType jpType : ((JpaCompilationUnit) jpaFile.getContent()).getTypes()) {
				if (jpType.getMappingKey() != IMappingKeys.NULL_TYPE_MAPPING_KEY && ! contains(jpType)) {
					messages.add(
							JpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								IJpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CONTEXT,
								jpType.getMapping(), jpType.getMapping().getTextRange())
						);
				}
			}
		}
	}
}
