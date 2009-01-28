/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceXmlResourceProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.PersistenceXmlResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericRootContextNode
	extends AbstractJpaContextNode
	implements JpaRootContextNode
{
	/* This object has no parent, so it must point to the JPA project */
	protected final JpaProject jpaProject;
	
	/* Main context object */
	protected PersistenceXml persistenceXml;
	
	
	public GenericRootContextNode(JpaProject jpaProject) {
		super(null);
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		
		PersistenceXmlResourceProvider modelProvider =
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		if (resource.exists()) {
			this.persistenceXml = this.buildPersistenceXml(resource);
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
	
	public PersistenceXml addPersistenceXml() {
		if (this.persistenceXml != null) {
			throw new IllegalStateException();
		}
		PersistenceXmlResourceProvider modelProvider =
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(this.getProject());
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		modelProvider.modify(new Runnable() {
				public void run() {
					// any modification will save file
				}
			});
		PersistenceXml px = this.buildPersistenceXml(resource);
		this.setPersistenceXml(px);
		return px;
	}
	
	public void removePersistenceXml() {
		if (this.persistenceXml == null) {
			throw new IllegalStateException();
		}
		this.persistenceXml.dispose();
		PersistenceXmlResourceProvider modelProvider =
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		try {
			WorkbenchResourceHelper.deleteResource(resource);
		}
		catch (CoreException ce) {
			JptCorePlugin.log(ce);
		}
		
		if (! resource.exists()) {
			setPersistenceXml(null);
		}
	}
	
	
	// **************** updating ***********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceXmlResourceProvider modelProvider =
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		
		if (resource.exists()) {
			if (this.persistenceXml == null) {
				this.setPersistenceXml(this.buildPersistenceXml(resource));
			} else {
				this.persistenceXml.update(resource);
			}
		} else {
			this.setPersistenceXml(null);
		}
	}

	protected PersistenceXml buildPersistenceXml(PersistenceXmlResource persistenceResource) {
		return this.getJpaFactory().buildPersistenceXml(this, persistenceResource);
	}
	
	
	// **************** Validation *********************************************
	
	public void validate(List<IMessage> messages) {
		if (this.persistenceXml == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY, 
					JpaValidationMessages.PROJECT_NO_PERSISTENCE_XML,
					this
				)
			);
			return;
		}
		if ( ! this.jpaProject.discoversAnnotatedClasses()) {
			this.validateOrphanClasses(messages);
		}
		this.persistenceXml.validate(messages);
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
		HashBag<String> annotatedClassNames = CollectionTools.bag(this.jpaProject.annotatedClassNames());
		HashBag<String> orphans = annotatedClassNames.clone();
		for (String annotatedClassName : annotatedClassNames) {
			if (persistenceUnit.specifiesPersistentType(annotatedClassName)) {
				orphans.remove(annotatedClassName);
			}
		}
		
		for (String orphan : orphans) {
			JavaResourcePersistentType jrpt = this.jpaProject.getJavaResourcePersistentType(orphan);
			JavaResourceCompilationUnit jrcu = jrpt.getJavaResourceCompilationUnit();
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CONTEXT,
						new String[] {persistenceUnit.getName()},
						jrcu.getFile(),
						jrpt.getMappingAnnotation().getTextRange(jrcu.buildASTRoot())
					)
				);
		}
	}

}
