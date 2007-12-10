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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class OrmStructureContentProvider
	extends ResourceModelStructureContentProvider
{
	public OrmStructureContentProvider() {
		super();
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		super.inputChanged(viewer, oldInput, newInput);
	}
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof EntityMappings) {
			return CollectionTools.array(((EntityMappings) parentElement).xmlPersistentTypes());
		}
		
		if (parentElement instanceof XmlPersistentType) {
			return CollectionTools.array(((XmlPersistentType) parentElement).attributes());
		}
		
		return super.getChildren(parentElement);
	}
	
	public Object getParent(Object element) {
		// TODO - persistent attribute parent
//		if (element instanceof XmlPersistentAttribute) {
//			return ((XmlPersistentAttribute) element).type();
//		}
		
		if (element instanceof XmlPersistentType) {
			return ((XmlPersistentType) element).entityMappings();
		}
		
		return super.getParent(element);
	}
	
	public boolean hasChildren(Object element) {
		return true;
	}
}
