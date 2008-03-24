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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * This is the context model object which corresponds to the 
 * persistence resource model object XmlJavaClassRef.
 * XmlJavaClassRef corresponds to the class tag in the persistence.xml
 */
public class GenericClassRef extends AbstractPersistenceJpaContextNode 
	implements ClassRef
{
	//this is null for the implied classRef case
	protected XmlJavaClassRef xmlJavaClassRef;
	
	protected String className;
	
	protected JavaPersistentType javaPersistentType;
	
	
	public GenericClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		super(parent);
		initialize(classRef);
	}
	
	public GenericClassRef(PersistenceUnit parent, String className) {
		super(parent);
		initialize(className);
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
		String oldClassName = this.className;
		this.className = newClassName;
		this.xmlJavaClassRef.setJavaClass(newClassName);
		firePropertyChanged(CLASS_NAME_PROPERTY, oldClassName, newClassName);
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
	
	protected void initialize(XmlJavaClassRef classRef) {
		this.xmlJavaClassRef = classRef;
		this.className = classRef.getJavaClass();
		initializeJavaPersistentType();
	}
	
	protected void initialize(String className) {
		this.className = className;
		initializeJavaPersistentType();
	}
	
	protected void initializeJavaPersistentType() {
		JavaResourcePersistentType persistentTypeResource = jpaProject().javaPersistentTypeResource(getClassName());
		if (persistentTypeResource != null) {
			this.javaPersistentType = buildJavaPersistentType(persistentTypeResource);
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
				setJavaPersistentType(buildJavaPersistentType(persistentTypeResource));
			}
		}		
	}
	
	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType resourcePersistentType) {
		return jpaFactory().buildJavaPersistentType(this, resourcePersistentType);
	}
	
	
	// *************************************************************************

	// ************************* validation *********************************

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addUnspecifiedClassMessage(messages);
		addUnresolvedClassMessage(messages);
		//classRef might have been empty
		if(getJavaPersistentType() != null){
			getJavaPersistentType().addToMessages(messages);
		}
	}
	
	protected void addUnspecifiedClassMessage(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(getClassName())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
					this, validationTextRange())
			);
		}
	}
	
	protected void addUnresolvedClassMessage(List<IMessage> messages) {
		if (! StringTools.stringIsEmpty(getClassName()) && getJavaPersistentType() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
					new String[] {getClassName()}, 
					this, 
					this.validationTextRange())
			);
		}
	}

	public JpaStructureNode structureNode(int textOffset) {
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return this.xmlJavaClassRef.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.xmlJavaClassRef.selectionTextRange();
	}
	
	public TextRange validationTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.xmlJavaClassRef.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getClassName());
	}
}
