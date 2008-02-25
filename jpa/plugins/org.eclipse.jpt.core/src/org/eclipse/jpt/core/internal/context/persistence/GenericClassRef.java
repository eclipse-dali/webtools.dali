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
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * This is the context model object which corresponds to the 
 * persistence resource model object XmlJavaClassRef.
 * XmlJavaClassRef corresponds to the class tag in the persistence.xml
 */
public class GenericClassRef extends AbstractJpaContextNode 
	implements ClassRef
{
	protected XmlJavaClassRef xmlJavaClassRef;
	
	protected String className;
	
	protected JavaPersistentType javaPersistentType;
	
	
	public GenericClassRef(PersistenceUnit parent) {
		super(parent);
	}
	
	public String getId() {
		return PersistenceStructureNodes.CLASS_REF_ID;
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
	
	public JavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(JavaPersistentType newJavaPersistentType) {
		JavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(ClassRef.JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
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
		JavaResourcePersistentType persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
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
		JavaResourcePersistentType persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
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
	
	protected JavaPersistentType createJavaPersistentType(JavaResourcePersistentType persistentTypeResource) {
		JavaPersistentType javaPersistentType = jpaFactory().buildJavaPersistentType(this);
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
	
	public JpaStructureNode structureNode(int textOffset) {
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return xmlJavaClassRef.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return xmlJavaClassRef.selectionTextRange();
	}
	
	public TextRange validationTextRange() {
		return xmlJavaClassRef.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getClassName());
	}
}
