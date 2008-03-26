/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericRootContextNode extends AbstractJpaContextNode 
	implements JpaRootContextNode
{
	/* This object has no parent, so it must point to the JPA project */
	protected final JpaProject jpaProject;
	
	/* Main context object */
	protected PersistenceXml persistenceXml;
	
	
	public GenericRootContextNode(JpaProject jpaProject) {
		super(null);
		if (jpaProject == null) {
			throw new IllegalArgumentException("The JPA project must not be null");
		}
		this.jpaProject = jpaProject;
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(jpaProject.getProject());
		PersistenceResource pr = pae.getResource();
		
		if (pr.exists()) {
			this.persistenceXml = this.buildPersistenceXml(pr);
		}
		
		pae.dispose();
	}
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	
	// **************** JpaNode impl *******************************************
	
	@Override
	public JpaProject getJpaProject() {
		return jpaProject;
	}
	
	@Override
	public IResource getResource() {
		return getJpaProject().getProject();
	}
	
	@Override
	public JpaContextNode getParent() {
		return null;
	}
	
	
	// **************** JpaContextNode impl ************************************
	
	@Override
	public PersistenceUnit getPersistenceUnit() {
		// No PersistenceUnit in this context
		return null;
	}
	
	@Override
	public EntityMappings getEntityMappings() {
		// No EntityMappings in this context
		return null;
	}
	
	@Override
	public OrmPersistentType getOrmPersistentType() {
		// No OrmPersistentType in this context
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
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(this.getJpaProject().getProject());
		PersistenceResource pr = pae.createDefaultResource();
		pae.dispose();
		PersistenceXml px = this.buildPersistenceXml(pr);
		this.setPersistenceXml(px);
		return px;
	}
	
	public void removePersistenceXml() {
		if (this.persistenceXml == null) {
			throw new IllegalStateException();
		}
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(getJpaProject().getProject());
		PersistenceResource pr = pae.getResource();
		pae.dispose();
		try {
			WorkbenchResourceHelper.deleteResource(pr);
		}
		catch (CoreException ce) {
			JptCorePlugin.log(ce);
		}
		
		if (! pr.exists()) {
			setPersistenceXml(null);
		}
	}
	
	
	// **************** updating ***********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(getJpaProject().getProject());
		PersistenceResource pr = pae.getResource();
		
		if (pr.exists()) {
			if (this.persistenceXml != null) {
				this.persistenceXml.update(pr);
			}
			else {
				setPersistenceXml(this.buildPersistenceXml(pr));
			}
		}
		else {
			setPersistenceXml(null);
		}
		
		pae.dispose();
	}

	protected PersistenceXml buildPersistenceXml(PersistenceResource persistenceResource) {
		return this.getJpaFactory().buildPersistenceXml(this, persistenceResource);
	}
	
	
	// **************** Validation *********************************************
	
	/* If this is true, it may be assumed that all the requirements are valid 
	 * for further validation.  For example, if this is true at the point we
	 * are validating persistence units, it may be assumed that there is a 
	 * single persistence.xml and that it has valid content down to the 
	 * persistence unit level.  */
	private boolean okToContinueValidation = true;
	
	public void addToMessages(List<IMessage> messages) {
		addNoPersistenceXmlMessage(messages);
		//TODO - multiple persistence unit message
		addOrphanedJavaClassMessages(messages);
		
		if(okToContinueValidation) {
			getPersistenceXml().addToMessages(messages);
		}
		
	}
	
	protected void addNoPersistenceXmlMessage(List<IMessage> messages) {
		if (persistenceXml == null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY, 
						JpaValidationMessages.PROJECT_NO_PERSISTENCE_XML,
						this)
				);
			okToContinueValidation = false;
		}
	}
	
	

	
	protected void addOrphanedJavaClassMessages(List<IMessage> messages) {
//		for (Iterator<JavaPersistentType> stream = jpaProject.javaPersistentTypes(); stream.hasNext(); ) {
//			JavaPersistentType jpType = stream.next();
//			if (jpType.getMappingKey() != IMappingKeys.NULL_TYPE_MAPPING_KEY && ! contains(jpType)) {
//				messages.add(
//						JpaValidationMessages.buildMessage(
//							IMessage.HIGH_SEVERITY,
//							IJpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CONTEXT,
//							jpType.getMapping(), jpType.getMapping().validationTextRange())
//					);
//			}
//		}
	}
	
}
