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
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentTypeContext;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * This is the context model object which corresponds to the 
 * persistence resource model object XmlJavaClassRef.
 * XmlJavaClassRef corresponds to the class tag in the persistence.xml
 */
public class GenericClassRef extends AbstractXmlContextNode 
	implements ClassRef, PersistentTypeContext
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
		return this.xmlJavaClassRef == null;
	}
	
	
	// **************** PersistentTypeContext impl *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		// no access type at this level overrides any local access type specification
		return null;
	}
	
	public AccessType getDefaultPersistentTypeAccess() {
		return getPersistenceUnit().getDefaultAccess();
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
		JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(getClassName());
		if (jrpt != null) {
			this.javaPersistentType = buildJavaPersistentType(jrpt);
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
		JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(getClassName());
		if (jrpt == null) {
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().dispose();
			}
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(jrpt);
			}
			else {
				setJavaPersistentType(buildJavaPersistentType(jrpt));
			}
		}		
	}
	
	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return getJpaFactory().buildJavaPersistentType(this, jrpt);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		if (StringTools.stringIsEmpty(this.className)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}
		if (this.javaPersistentType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
					new String[] {this.className}, 
					this, 
					this.getValidationTextRange()
				)
			);
			return;
		}
		MappingFileRef mappingFileRef = this.getMappingFileContaining(this.className);
		if (mappingFileRef != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.LOW_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,
					new String[] {this.className, mappingFileRef.getFileName()},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}
		// 190062 only add Java validation messages if this class is not listed
		// in a mapping file
		this.validateJavaPersistentType(messages);
	}

	protected void validateJavaPersistentType(List<IMessage> messages) {
		try {
			this.javaPersistentType.validate(messages);
		} catch (Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	
	//possibly move this and make it API on PersistenceUnit
	/**
	 * Return the mapping file that contains a persistent type for the given 
	 * type name.  Return null if no mapping file contains the persistent type.
	 */
	protected MappingFileRef getMappingFileContaining(String fullyQualifiedTypeName) {
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(getPersistenceUnit().mappingFileRefs())) {
			if (mappingFileRef.getPersistentType(fullyQualifiedTypeName) != null) {
				return mappingFileRef;
			}
		}
		return null;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return this.xmlJavaClassRef.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.xmlJavaClassRef.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.xmlJavaClassRef.getValidationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getClassName());
	}
	
	public void dispose() {
		if (getJavaPersistentType() != null) {
			getJavaPersistentType().dispose();
		}
	}
}
