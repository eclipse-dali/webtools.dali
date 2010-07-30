/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * the context model root
 */
public class GenericRootContextNode
	extends AbstractJpaContextNode
	implements JpaRootContextNode2_0
{
	/* This object has no parent, so it must point to the JPA project explicitly. */
	protected final JpaProject jpaProject;

	/* Main context object. */
	protected PersistenceXml persistenceXml;


	public GenericRootContextNode(JpaProject jpaProject) {
		super(null);  // the JPA project is not really a "parent"...
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		this.initialize();
	}


	@Override
	protected boolean requiresParent() {
		return false;
	}

	protected void initialize() {
		JpaXmlResource resource = this.resolvePersistenceXmlResource();
		if (resource != null) {
			this.persistenceXml = this.buildPersistenceXml(resource);
		}
	}

	public void update(IProgressMonitor monitor) {
		JpaXmlResource xmlResource = this.resolvePersistenceXmlResource();
		if (xmlResource == null) {
			if (this.persistenceXml != null) {
				this.persistenceXml.dispose();
				this.setPersistenceXml(null);
			}
		} else {
			if (this.persistenceXml == null) {
				this.setPersistenceXml(this.buildPersistenceXml(xmlResource));
			} else {
				this.persistenceXml.update();
			}
		}
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.persistenceXml != null) {
			this.persistenceXml.postUpdate();
		}
	}


	// ********** AbstractJpaNode overrides **********

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


	// ********** AbstractJpaContextNode overrides **********

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return null;
	}

	@Override
	public MappingFileRoot getMappingFileRoot() {
		return null;
	}


	// ********** persistence.xml **********

	public PersistenceXml getPersistenceXml() {
		return this.persistenceXml;
	}

	protected void setPersistenceXml(PersistenceXml persistenceXml) {
		PersistenceXml old = this.persistenceXml;
		this.persistenceXml = persistenceXml;
		this.firePropertyChanged(PERSISTENCE_XML_PROPERTY, old, persistenceXml);
	}

	protected JpaXmlResource resolvePersistenceXmlResource() {
		JpaXmlResource xmlResource = this.jpaProject.getPersistenceXmlResource();
		if (xmlResource == null) {
			return null;
		}
		if (xmlResource.isReverting()) {
			// 308254 - this can happen when persistence.xml is closed without saving;
			// the model is completely whacked in another thread - so wipe our model(?)
			return null;
		}
		JpaResourceType resourceType = xmlResource.getResourceType();
		if (resourceType == null) {
			return null;
		}
		if ( ! this.getJpaPlatform().supportsResourceType(resourceType)) {
			return null;
		}
		return xmlResource;
	}

	protected PersistenceXml buildPersistenceXml(JpaXmlResource resource) {
		return this.getJpaFactory().buildPersistenceXml(this, resource);
	}


	// ********** metamodel **********

	public void initializeMetamodel() {
		if (this.persistenceXml != null) {
			((PersistenceXml2_0) this.persistenceXml).initializeMetamodel();
		}
	}

	public void synchronizeMetamodel() {
		if (this.persistenceXml != null) {
			((PersistenceXml2_0) this.persistenceXml).synchronizeMetamodel();
		}
	}

	public void disposeMetamodel() {
		if (this.persistenceXml != null) {
			((PersistenceXml2_0) this.persistenceXml).disposeMetamodel();
		}
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}

		if (this.persistenceXml == null) {
			messages.add(buildPersistenceXmlValidationMessage());
			return;
		}
		if ( ! this.jpaProject.discoversAnnotatedClasses()) {
			this.validateOrphanClasses(messages);
		}
		this.persistenceXml.validate(messages, reporter);
	}

	protected IMessage buildPersistenceXmlValidationMessage() {
		int severity = IMessage.HIGH_SEVERITY;
		IFile file = getPlatformFile();
		if (file.exists()) {
			JpaXmlResource xmlResource = this.jpaProject.getPersistenceXmlResource();
			if (xmlResource != null 
					&& ! getJpaPlatform().supportsResourceType(xmlResource.getResourceType())) {
				return DefaultJpaValidationMessages.buildMessage(
					severity,
					JpaValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT,
					file);
			}
			return DefaultJpaValidationMessages.buildMessage(
				severity,
				JpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,
				file);
		}
		return DefaultJpaValidationMessages.buildMessage(
			severity,
			JpaValidationMessages.PROJECT_NO_PERSISTENCE_XML,
			this);
	}

	protected IFile getPlatformFile() {
		return this.jpaProject.getPlatformFile(JptCorePlugin.DEFAULT_PERSISTENCE_XML_RUNTIME_PATH);
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
						jrpt.getFile(),
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
						jrpt.getFile(),
						jrpt.getNameTextRange(jrcu.buildASTRoot())
					)
				);
			}
		}
	}

}
