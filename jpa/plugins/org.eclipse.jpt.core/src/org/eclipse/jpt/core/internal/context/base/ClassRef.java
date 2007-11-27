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

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;

public class ClassRef extends JpaContextNode implements IClassRef
{
	protected XmlJavaClassRef xmlJavaClassRef;
	
	protected String className;
	
	protected IJavaPersistentType javaPersistentType;
	
	
	public ClassRef(IPersistenceUnit parent) {
		super(parent);
	}
	
	
	public boolean isFor(String fullyQualifiedTypeName) {
		return getClassName().equals(fullyQualifiedTypeName);
	}
	
	
	// **************** class name *********************************************
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		xmlJavaClassRef.setJavaClass(newClassName);
		firePropertyChanged(CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	
	// **************** java persistent type ***********************************
	
	public IJavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(IJavaPersistentType newJavaPersistentType) {
		IJavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(IClassRef.JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlJavaClassRef classRef) {
		xmlJavaClassRef = classRef;
		className = classRef.getJavaClass();
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
		if (persistentTypeResource != null) {
			javaPersistentType = createJavaPersistentType(persistentTypeResource);
		}		
	}
	
	public void update(XmlJavaClassRef classRef) {
		xmlJavaClassRef = classRef;
		setClassName(classRef.getJavaClass());
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
		if (persistentTypeResource == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(persistentTypeResource);
			}
			else {
				setJavaPersistentType(createJavaPersistentType(persistentTypeResource));
			}
		}		
	}
	
	protected IJavaPersistentType createJavaPersistentType(JavaPersistentTypeResource persistentTypeResource) {
		IJavaPersistentType javaPersistentType = jpaFactory().createJavaPersistentType(this);
		javaPersistentType.initializeFromResource(persistentTypeResource);
		return javaPersistentType;
	}
	
	
	// *************************************************************************
	
	public ITextRange validationTextRange() {
		return this.xmlJavaClassRef.validationTextRange();
	}
}
