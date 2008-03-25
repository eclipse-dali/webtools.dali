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
	
	public IFile file() {
		return file;
	}
	
	public ResourceModel resourceModel() {
		return resourceModel;
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		return resourceModel.structureNode(textOffset);
	}
	
	public String resourceType() {
		return resourceModel().getResourceType();
	}
	
	public void dispose() {
		resourceModel().dispose();
	}
	
	public void javaElementChanged(ElementChangedEvent event) {
		resourceModel().javaElementChanged(event);
	}
	
	public void fileAdded(JpaFile jpaFile) {
		resourceModel().resolveTypes();
	}
	
	public void fileRemoved(JpaFile jpaFile) {
		resourceModel().resolveTypes();		
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(file().toString());
		sb.append(" (resourceType: ");
		sb.append(resourceType());
		sb.append(")");
	}
}
