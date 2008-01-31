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

/**
 * This is the context model object which corresponds to the 
 * persistence resource model object XmlJavaClassRef.
 * XmlJavaClassRef corresponds to the class tag in the persistence.xml
 */
public class ClassRef extends JpaContextNode implements IClassRef
{
	protected XmlJavaClassRef xmlJavaClassRef;
	
	protected String className;
	
	protected IJavaPersistentType javaPersistentType;
	
	
	public ClassRef(IPersistenceUnit parent) {
		super(parent);
	}
	
	
	public boolean isFor(String fullyQualifiedTypeName) {
		if (getClassName() == null) {
			return false;
		}
		return getClassName().equals(fullyQualifiedTypeName);
	}
	
	
	// **************** class name *********************************************
	
	public String getClassName() {
		return this.className;
	}
	
	public void setClassName(String newClassName) {
		String oldClassName = this.className;
		this.className = newClassName;
		this.xmlJavaClassRef.setJavaClass(newClassName);
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
		this.xmlJavaClassRef = classRef;
		this.className = classRef.getJavaClass();
		this.initializeJavaPersistentType();
	}
	
	protected void initializeJavaPersistentType() {
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
		if (persistentTypeResource != null) {
			this.javaPersistentType = createJavaPersistentType(persistentTypeResource);
		}				
	}
	
	public void update(XmlJavaClassRef classRef) {
		this.xmlJavaClassRef = classRef;
		this.setClassName(classRef.getJavaClass());
		this.updateJavaPersistentType();
	}
	
	protected void updateJavaPersistentType() {
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getClassName());
	}
}
