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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
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
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(jpaProject().project());
		PersistenceResource persistenceResource = pae.getResource();
		
		if (persistenceResource.exists()) {
			this.persistenceXml = createPersistenceXml(persistenceResource);
		}
		
		pae.dispose();
	}
	
	
	// **************** persistence xml ****************************************
	
	public IPersistenceXml getPersistenceXml() {
		return persistenceXml;
	}
	
	protected void setPersistenceXml(IPersistenceXml newPersistenceXml) {
		IPersistenceXml oldPersistenceXml = persistenceXml;
		persistenceXml = newPersistenceXml;
		firePropertyChanged(PERSISTENCE_XML_PROPERTY, oldPersistenceXml, newPersistenceXml);
	}
	
	public IPersistenceXml addPersistenceXml() {
		if (persistenceXml != null) {
			throw new IllegalStateException();
		}
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(jpaProject().project());
		PersistenceResource pr = pae.createDefaultResource();
		pae.dispose();
		persistenceXml = createPersistenceXml(pr);
		return persistenceXml;
	}
	
	public void removePersistenceXml() {
		if (persistenceXml == null) {
			throw new IllegalStateException();
		}
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForWrite(jpaProject().project());
		PersistenceResource pr = pae.getResource();
		try {
			WorkbenchResourceHelper.deleteResource(pr);
		}
		catch (CoreException ce) {
			JptCorePlugin.log(ce);
		}
		pae.dispose();
		
		if (! pr.exists()) {
			persistenceXml = null;
		}
	}
	
	
	// **************** updating ***********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceArtifactEdit pae = PersistenceArtifactEdit.getArtifactEditForRead(jpaProject().project());
		PersistenceResource persistenceResource = pae.getResource();
		
		if (persistenceResource.exists()) {
			if (this.persistenceXml != null) {
				this.persistenceXml.update(persistenceResource);
			}
			else {
				setPersistenceXml(createPersistenceXml(persistenceResource));
			}
		}
		else {
			setPersistenceXml(null);
		}
		
		pae.dispose();
	}

	protected IPersistenceXml createPersistenceXml(PersistenceResource persistenceResource) {
		IPersistenceXml persistenceXml = jpaFactory().createPersistenceXml(this);
		persistenceXml.initialize(persistenceResource);
		return persistenceXml;
	}
	
	
	// *************************************************************************
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}
}
