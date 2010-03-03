/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.internal.context.orm.VirtualCascadeType;
import org.eclipse.jpt.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.core.resource.orm.CascadeType;

public class VirtualXmlCascadeType2_0
	extends CascadeType
{
	protected Cascade2_0 javaCascade;
	
	protected boolean metadataComplete;
	
	protected final VirtualCascadeType virtualXmlCascadeType1_0;
	
	
	public VirtualXmlCascadeType2_0(Cascade2_0 javaCascade, boolean metadataComplete) {
		super();
		this.javaCascade = javaCascade;
		this.metadataComplete = metadataComplete;
		this.virtualXmlCascadeType1_0 = new VirtualCascadeType(javaCascade, metadataComplete);
	}
	
	
	@Override
	public boolean isCascadeDetach() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isDetach();
	}
	
	@Override
	public void setCascadeDetach(boolean newCascadeDetach) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	
	// **************** JPA 1.0 functionality *********************************
	
	@Override
	public boolean isCascadeAll() {
		return this.virtualXmlCascadeType1_0.isCascadeAll();
	}
	
	@Override
	public void setCascadeAll(boolean value) {
		this.virtualXmlCascadeType1_0.setCascadeAll(value);
	}
	
	@Override
	public boolean isCascadeMerge() {
		return this.virtualXmlCascadeType1_0.isCascadeMerge();
	}
	
	@Override
	public void setCascadeMerge(boolean value) {
		this.virtualXmlCascadeType1_0.setCascadeMerge(value);
	}
	
	@Override
	public boolean isCascadePersist() {
		return this.virtualXmlCascadeType1_0.isCascadePersist();
	}
	
	@Override
	public void setCascadePersist(boolean value) {
		this.virtualXmlCascadeType1_0.setCascadePersist(value);
	}
	
	@Override
	public boolean isCascadeRefresh() {
		return this.virtualXmlCascadeType1_0.isCascadeRefresh();
	}
	
	@Override
	public void setCascadeRefresh(boolean value) {
		this.setCascadeRefresh(value);
	}
	
	@Override
	public boolean isCascadeRemove() {
		return this.virtualXmlCascadeType1_0.isCascadeRemove();
	}
}
