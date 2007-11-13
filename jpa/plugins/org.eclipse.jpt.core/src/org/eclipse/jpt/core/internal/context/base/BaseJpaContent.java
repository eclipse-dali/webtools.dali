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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

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
		PersistenceResourceModel persistenceResource = persistenceResource();
		
		if (resourceExists(persistenceResource)) {
			this.persistenceXml = createPersistenceXml(persistenceResource);
		}
	}
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}	
	
	// **************** persistence xml ***************************************
	
	public IPersistenceXml getPersistenceXml() {
		return persistenceXml;
	}
	
	public void setPersistenceXml(IPersistenceXml newPersistenceXml) {
		IPersistenceXml oldPersistenceXml = persistenceXml;
		persistenceXml = newPersistenceXml;
		firePropertyChanged(PERSISTENCE_XML_PROPERTY, oldPersistenceXml, newPersistenceXml);
	}
	
	
	// **************** updating **********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceResourceModel persistenceResource = persistenceResource();
		
		if (resourceExists(persistenceResource)) {
			if (this.persistenceXml == null) {
				setPersistenceXml(createPersistenceXml(persistenceResource));
			}
			else {
				this.persistenceXml.update(persistenceResource);
			}
		}
		else {
			setPersistenceXml(null);
		}
	}
	
	protected PersistenceResourceModel persistenceResource() {
		PersistenceArtifactEdit pae = 
			PersistenceArtifactEdit.getArtifactEditForRead(jpaProject().project());
		return pae.getPersistenceResource(JptCorePlugin.persistenceXmlDeploymentURI(jpaProject().project()));
	}
	
	protected boolean resourceExists(PersistenceResourceModel persistenceResource) {
		return WorkbenchResourceHelper.getFile(persistenceResource).exists();
	}

	protected IPersistenceXml createPersistenceXml(PersistenceResourceModel persistenceResource) {
		IPersistenceXml persistenceXml = jpaFactory().createPersistenceXml(this);
		persistenceXml.initializeFromResource(persistenceResource);
		return persistenceXml;
	}
}
