/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.core.internal.AbstractResourceModel;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class JpaXmlResourceModel extends AbstractResourceModel
{
	protected JpaXmlResource resource;
	
	
	protected JpaXmlResourceModel(IFile file) {
		super(file);
		this.resource = buildResource(file);
		this.resource.setResourceModel(this);
	}
	
	
	protected abstract JpaXmlResource buildResource(IFile file);
	
	public JpaXmlResource getResource() {
		return this.resource;
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		this.resource.javaElementChanged(event);
	}
	
	public void addResourceModelChangeListener(ResourceModelListener listener) {
		this.resource.addResourceModelChangeListener(listener);
	}
	
	public void removeResourceModelChangeListener(ResourceModelListener listener) {
		this.resource.removeResourceModelChangeListener(listener);
	}
	
	
	public void resolveTypes() {
		//nothing to do here, JavaResourceModel needs this
	}
}
