/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;

/**
 * 
 */
public class JpaFile extends JpaNode implements IJpaFile
{
	/**
	 * The IFile associated with this JPA file
	 */
	protected final IFile file;
	
	/**
	 * The resource model represented by this JPA file
	 */
	protected final IResourceModel resourceModel;
	
	
	public JpaFile(IJpaProject jpaProject, IFile file, IResourceModel resourceModel) {
		super(jpaProject);
		this.file = file;
		this.resourceModel = resourceModel;
	}
	
	/**
	 * @see IJpaFile#getFile()
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * @see IJpaFile#getResourceModel()
	 */
	public IResourceModel getResourceModel() {
		return resourceModel;
	}
	
	public IJpaContextNode contextNode(int textOffset) {
		return null;
	}
	
	/**
	 * @see IJpaFile#getResourceType()
	 */
	public String getResourceType() {
		return getResourceModel().getResourceType();
	}
	
	public void dispose() {
		getResourceModel().dispose();
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		getResourceModel().handleJavaElementChangedEvent(event);
	}
	
	public void fileAdded(IJpaFile jpaFile) {
		getResourceModel().resolveTypes();
	}
	
	public void fileRemoved(IJpaFile jpaFile) {
		getResourceModel().resolveTypes();		
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getFile().toString());
		sb.append(" (resourceType: ");
		sb.append(getResourceType());
		sb.append(")");
	}
}
