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
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public class BaseJpaContent extends JpaContextNode 
	implements IBaseJpaContent
{
	protected IPersistenceXml persistenceXml;
	
	
	public BaseJpaContent(IJpaProject jpaProject) {
		super(jpaProject);
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
		if (persistenceXml != newPersistenceXml) {
			IPersistenceXml oldPersistenceXml = persistenceXml;
			persistenceXml = newPersistenceXml;
			firePropertyChanged(PERSISTENCE_XML_PROPERTY, oldPersistenceXml, newPersistenceXml);
		}
	}
	
	
	// **************** updating **********************************************
	
	public void update(IProgressMonitor monitor) {
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForRead(
						jpaProject().project(), 
						JptCorePlugin.persistenceXmlDeploymentURI(jpaProject().project()));
		PersistenceResourceModel persistenceResource = pae.getPersistenceResource();
		
		if (WorkbenchResourceHelper.getFile(persistenceResource).exists()) {
			if (persistenceXml == null) {
				IPersistenceXml persistenceXml = jpaFactory().createPersistenceXml(this);
				setPersistenceXml(persistenceXml);
			}
			persistenceXml.update(persistenceResource);
		}
		else {
			if (persistenceXml != null) {
				setPersistenceXml(null);
			}
		}
	}
}
