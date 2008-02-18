/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceXml;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class BaseJpaContent extends JpaContextNode 
	implements IBaseJpaContent
{
	protected IPersistenceXml persistenceXml;
	
	
	public BaseJpaContent(IJpaProject jpaProject) {
		super(jpaProject);
	}
	
	@Override
	protected void initialize(Node parentNode) {
		super.initialize(parentNode);
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(jpaProject().project());
		PersistenceResource pr = pae.getResource();
		
		if (pr.exists()) {
			this.persistenceXml = this.createPersistenceXml(pr);
		}
		
		pae.dispose();
	}
	
	@Override
	public EntityMappings entityMappings() {
		return null;
	}
	
	@Override
	public XmlPersistentType xmlPersistentType() {
		return null;
	}
	
	// **************** persistence xml ****************************************
	
	public IPersistenceXml getPersistenceXml() {
		return this.persistenceXml;
	}
	
	protected void setPersistenceXml(IPersistenceXml persistenceXml) {
		IPersistenceXml old = this.persistenceXml;
		this.persistenceXml = persistenceXml;
		this.firePropertyChanged(PERSISTENCE_XML_PROPERTY, old, persistenceXml);
	}
	
	public IPersistenceXml addPersistenceXml() {
		if (this.persistenceXml != null) {
			throw new IllegalStateException();
		}
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(this.jpaProject().project());
		PersistenceResource pr = pae.createDefaultResource();
		pae.dispose();
		IPersistenceXml px = this.createPersistenceXml(pr);
		this.setPersistenceXml(px);
		return px;
	}
	
	public void removePersistenceXml() {
		if (this.persistenceXml == null) {
			throw new IllegalStateException();
		}
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(jpaProject().project());
		PersistenceResource pr = pae.getResource();
		pae.dispose();
		try {
			WorkbenchResourceHelper.deleteResource(pr);
		}
		catch (CoreException ce) {
			JptCorePlugin.log(ce);
		}
		
		if (! pr.exists()) {
			this.setPersistenceXml(null);
		}
	}
	
	
	// **************** updating ***********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(jpaProject().project());
		PersistenceResource pr = pae.getResource();
		
		if (pr.exists()) {
			if (this.persistenceXml != null) {
				this.persistenceXml.update(pr);
			}
			else {
				setPersistenceXml(createPersistenceXml(pr));
			}
		}
		else {
			setPersistenceXml(null);
		}
		
		pae.dispose();
	}

	protected IPersistenceXml createPersistenceXml(PersistenceResource persistenceResource) {
		IPersistenceXml px = this.jpaFactory().createPersistenceXml(this);
		px.initialize(persistenceResource);
		return px;
	}
	
	
	// *************************************************************************
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}

	
	//******** Validation *************************************************
	
	/* If this is true, it may be assumed that all the requirements are valid 
	 * for further validation.  For example, if this is true at the point we
	 * are validating persistence units, it may be assumed that there is a 
	 * single persistence.xml and that it has valid content down to the 
	 * persistence unit level.  */
	private boolean okToContinueValidation = true;
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		addNoPersistenceXmlMessage(messages);
		//TODO - multiple persistence unit message
		addOrphanedJavaClassMessages(messages);
		
		if(okToContinueValidation) {
			getPersistenceXml().addToMessages(messages, astRoot);
		}
		
	}
	
	protected void addNoPersistenceXmlMessage(List<IMessage> messages) {
		if (persistenceXml == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY, 
						IJpaValidationMessages.PROJECT_NO_PERSISTENCE_XML,
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
