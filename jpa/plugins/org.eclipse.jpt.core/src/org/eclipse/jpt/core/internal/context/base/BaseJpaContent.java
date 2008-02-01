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
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
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
}
