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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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
	
	public boolean isVirtual() {
		return xmlJavaClassRef == null;
	}
	
	
	// **************** class name *********************************************
	
	public String getClassName() {
		return this.className;
	}
	
	public void setClassName(String newClassName) {
		this.xmlJavaClassRef.setJavaClass(newClassName);
		setClassName_(newClassName);
	}
	
	protected void setClassName_(String newClassName) {
		String oldClassName = this.className;
		this.className = newClassName;
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
		initializeJavaPersistentType();
	}
	
	public void initialize(String className) {
		this.className = className;
		initializeJavaPersistentType();
	}
	
	protected void initializeJavaPersistentType() {
		JavaPersistentTypeResource persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
		if (persistentTypeResource != null) {
			this.javaPersistentType = createJavaPersistentType(persistentTypeResource);
		}				
	}
	
	public void update(XmlJavaClassRef classRef) {
		this.xmlJavaClassRef = classRef;
		setClassName_(classRef.getJavaClass());
		updateJavaPersistentType();
	}
	
	public void update(String className) {
		this.xmlJavaClassRef = null;
		setClassName_(className);
		updateJavaPersistentType();
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

	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		//classRef might have been empty
		if(javaPersistentType != null){
			javaPersistentType.addToMessages(messages, astRoot);
		}
	}
	
	public ITextRange validationTextRange() {
		return this.xmlJavaClassRef.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getClassName());
	}
}
