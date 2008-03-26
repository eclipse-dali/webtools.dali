/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;

public class GenericJpaFile extends AbstractJpaNode implements JpaFile
{
	/**
	 * The IFile associated with this JPA file
	 */
	protected final IFile file;
	
	/**
	 * The resource model represented by this JPA file
	 */
	protected final ResourceModel resourceModel;
	
	
	public GenericJpaFile(JpaProject jpaProject, IFile file, ResourceModel resourceModel) {
		super(jpaProject);
		this.file = file;
		this.resourceModel = resourceModel;
	}
	
	public IFile getFile() {
		return file;
	}
	
	public ResourceModel getResourceModel() {
		return resourceModel;
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		return resourceModel.structureNode(textOffset);
	}
	
	public String getResourceType() {
		return getResourceModel().getResourceType();
	}
	
	public void dispose() {
		getResourceModel().dispose();
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		getResourceModel().javaElementChanged(event);
	}
	
	public void fileAdded(JpaFile jpaFile) {
		getResourceModel().resolveTypes();
	}
	
	public void fileRemoved(JpaFile jpaFile) {
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
