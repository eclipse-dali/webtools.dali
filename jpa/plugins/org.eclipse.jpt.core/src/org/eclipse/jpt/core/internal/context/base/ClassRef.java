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

import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;

public class ClassRef extends JpaContextNode implements IClassRef
{
	
	private IJavaPersistentType javaPersistentType;
		public static final String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentTypeProperty";
	
	public ClassRef(IPersistenceUnit parent) {
		super(parent);
	}
	
	public IJavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(IJavaPersistentType newJavaPersistentType) {
		IJavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
	}
	
	
	// **************** updating ***********************************************
	
	public void update(XmlJavaClassRef classRef) {
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(classRef.getJavaClass());
		if (persistentTypeResource == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() == null) {
				setJavaPersistentType(jpaFactory().createJavaPersistentType(this));
			}
			getJavaPersistentType().update(persistentTypeResource);
		}		
	}
}
