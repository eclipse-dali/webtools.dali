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
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;

public class OrmResourceModelStructureProvider
	extends ResourceModelStructureProvider
{
	public OrmResourceModelStructureProvider(OrmResourceModel resourceModel) {
		super(resourceModel);
	}
	
	public ITreeContentProvider buildContentProvider() {
		return new OrmStructureContentProvider();
	}
	
	public ILabelProvider buildLabelProvider() {
		return new OrmStructureLabelProvider();
	}
}
