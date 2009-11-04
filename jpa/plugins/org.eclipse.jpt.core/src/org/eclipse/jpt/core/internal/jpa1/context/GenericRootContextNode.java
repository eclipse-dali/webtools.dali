/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlDefinition;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericRootContextNode
	extends AbstractJpaContextNode
	implements JpaRootContextNode
{
	/* This object has no parent, so it must point to the JPA project explicitly. */
	protected final JpaProject jpaProject;
	
	/* Main context object. */
	protected PersistenceXml persistenceXml;
	
	
	public GenericRootContextNode(JpaProject jpaProject) {
		super(null);
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		JpaXmlResource resource = getPersistenceXmlResource();
		if (resource != null) {
			this.persistenceXml = buildPersistenceXml(resource);
		}
	}
	
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public JpaProject getJpaProject() {
		return this.jpaProject;
	}
	
	@Override
	public IResource getResource() {
		return this.getProject();
	}
	
	protected IProject getProject() {
		return this.jpaProject.getProject();
	}
	
	
	// **************** JpaContextNode impl ************************************
	
	@Override
	public PersistenceUnit getPersistenceUnit() {
		// No PersistenceUnit in this context
		return null;
	}
	
	@Override
	public MappingFileRoot getMappingFileRoot() {
		// No MappingFileRoot in this context
		return null;
	}
	
	
	// **************** persistence xml ****************************************
	
	public PersistenceXml getPersistenceXml() {
		return this.persistenceXml;
	}
	
	protected void setPersistenceXml(PersistenceXml persistenceXml) {
		PersistenceXml old = this.persistenceXml;
		this.persistenceXml = persistenceXml;
		this.firePropertyChanged(PERSISTENCE_XML_PROPERTY, old, persistenceXml);
	}
	
	// *************** metamodel ****************

	public void synchronizeMetamodel() {
		if (this.persistenceXml != null) {
			this.persistenceXml.synchronizeMetamodel();
		}
	}
	
	// **************** updating ***********************************************
	
	public void update(IProgressMonitor monitor) {
		JpaXmlResource resource = getPersistenceXmlResource();
		
		if (resource != null) {
			if (this.persistenceXml == null) {
				this.setPersistenceXml(this.buildPersistenceXml(resource));
			} else {
				this.persistenceXml.update(resource);
			}
		} else {
			this.setPersistenceXml(null);
		}
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.persistenceXml != null) {
			this.persistenceXml.postUpdate();
		}
	}
	
	protected JpaXmlResource getPersistenceXmlResource() {
		return this.jpaProject.getPersistenceXmlResource();
	}
	
	protected PersistenceXml buildPersistenceXml(JpaXmlResource resource) {
		PersistenceXmlDefinition persistenceXmlDef;
		try {
			persistenceXmlDef = (PersistenceXmlDefinition) getJpaPlatform().getResourceDefinition(resource.getResourceType());
		}
		catch (IllegalArgumentException iae) {
			JptCorePlugin.log(iae);
			return null;
		}
		return (persistenceXmlDef == null) ? null : persistenceXmlDef.getContextNodeFactory().buildPersistenceXml(this, resource);
	}
	
	
	// **************** Validation *********************************************
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}

		if (this.persistenceXml == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY, 
					this.buildMissingFileMessageID(),
					this
				)
			);
			return;
		}
		if ( ! this.jpaProject.discoversAnnotatedClasses()) {
			this.validateOrphanClasses(messages);
		}
		this.persistenceXml.validate(messages, reporter);
	}

	protected String buildMissingFileMessageID() {
		return this.getPlatformFile().exists() ?
					JpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT
				:
					JpaValidationMessages.PROJECT_NO_PERSISTENCE_XML;
	}

	protected IFile getPlatformFile() {
		return this.jpaProject.convertToPlatformFile(JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH);
	}
	
	protected void validateOrphanClasses(List<IMessage> messages) {
		Persistence persistence = this.persistenceXml.getPersistence();
		if (persistence == null) {
			return;  // handled with other validation
		}
		if (persistence.persistenceUnitsSize() != 1) {
			return;  // the context model currently only supports 1 persistence unit
		}

		PersistenceUnit persistenceUnit = persistence.persistenceUnits().next();
		HashBag<String> annotatedClassNames = CollectionTools.bag(this.jpaProject.annotatedJavaSourceClassNames());
		HashBag<String> orphans = annotatedClassNames.clone();
		for (String annotatedClassName : annotatedClassNames) {
			if (persistenceUnit.specifiesPersistentType(annotatedClassName)) {
				orphans.remove(annotatedClassName);
			}
		}
		
		// TODO remove 'jrcu'
		// replace jrcu.getFile() with jrpt.getFile()
		// replace jrpt.getMappingAnnotation().getTextRange(jrcu.buildASTRoot())
		//    with jrpt.getMappingAnnotation().getTextRange()
		//    (new method #getTextRange() ?)
		for (String orphan : orphans) {
			JavaResourcePersistentType jrpt = this.jpaProject.getJavaResourcePersistentType(orphan);
			JavaResourceCompilationUnit jrcu = jrpt.getJavaResourceCompilationUnit();
			if (jrpt.isMapped()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENT_TYPE_MAPPED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT,
						new String[] {jrpt.getName()},
						jrcu.getFile(),
						jrpt.getNameTextRange(jrcu.buildASTRoot())
					)
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.PERSISTENT_TYPE_ANNOTATED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT,
						new String[] {jrpt.getName()},
						jrcu.getFile(),
						jrpt.getNameTextRange(jrcu.buildASTRoot())
					)
				);
			}
		}
	}
}
