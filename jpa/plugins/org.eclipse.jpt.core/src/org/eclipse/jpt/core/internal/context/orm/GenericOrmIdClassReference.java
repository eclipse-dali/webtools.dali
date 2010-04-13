/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmIdClassReference
	extends AbstractXmlContextNode
	implements OrmIdClassReference
{
	protected String specifiedIdClassName;
	
	protected String defaultIdClassName;

	protected JavaPersistentType idClass;
	
	
	public GenericOrmIdClassReference(OrmTypeMapping parent, JavaIdClassReference javaIdClassReference) {
		super(parent);
		this.specifiedIdClassName = buildSpecifiedIdClassName();
		this.defaultIdClassName = buildDefaultIdClassName(javaIdClassReference);
		this.idClass = buildIdClass();
	}
	
	
	protected OrmTypeMapping getTypeMapping() {
		return (OrmTypeMapping) getParent();
	}
	
	protected OrmPersistentType getPersistentType() {
		return getTypeMapping().getPersistentType();
	}
	
	
	// **************** PersistentType.Owner impl *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		return getPersistentType().getAccess();
	}
	
	public AccessType getDefaultPersistentTypeAccess() {
		// this shouldn't be needed, since we've specified an override access, but just to be safe ...
		return getPersistentType().getAccess();
	}
	
	
	// **************** IdClassReference impl *********************************
	
	public String getSpecifiedIdClassName() {
		return this.specifiedIdClassName;
	}
	
	public void setSpecifiedIdClassName(String newClassName) {
		String oldClassName = this.specifiedIdClassName;
		this.specifiedIdClassName = newClassName;
		if (valuesAreDifferent(oldClassName, newClassName)) {
			if (getIdXmlClassRef() != null) {
				getIdXmlClassRef().setClassName(newClassName);
				if (getIdXmlClassRef().isUnset()) {
					removeIdClassElement();
				}
			}
			else if (newClassName != null) {
				addIdClassElement();
				getIdXmlClassRef().setClassName(newClassName);
			}
		}
		firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected void setSpecifiedIdClassName_(String newClassName) {
		String oldClassName = this.specifiedIdClassName;
		this.specifiedIdClassName = newClassName;
		firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected String buildSpecifiedIdClassName() {
		XmlClassReference idXmlClassRef = this.getIdXmlClassRef();
		return (idXmlClassRef == null) ? null : idXmlClassRef.getClassName();
	}
	
	public String getDefaultIdClassName() {
		return this.defaultIdClassName;
	}
	
	protected void setDefaultIdClassName_(String newClassName) {
		String oldClassName = this.defaultIdClassName;
		this.defaultIdClassName = newClassName;
		firePropertyChanged(DEFAULT_ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected String buildDefaultIdClassName(JavaIdClassReference javaIdClassReference) {
		return (javaIdClassReference == null) ? null : javaIdClassReference.getFullyQualifiedIdClassName();
	}
	
	public String getIdClassName() {
		return (this.specifiedIdClassName == null) ? this.defaultIdClassName : this.specifiedIdClassName;
	}
	
	public boolean isSpecified() {
		return getIdClassName() != null;
	}
	
	public JavaPersistentType getIdClass() {
		return this.idClass;
	}
	
	protected void setIdClass_(JavaPersistentType newIdClass) {
		JavaPersistentType oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected JavaPersistentType buildIdClass() {
		JavaResourcePersistentType resourceIdClass = getResourceIdClass();
		return (resourceIdClass == null) ? 
				null : this.buildIdClass(resourceIdClass);
	}
	
	protected JavaPersistentType buildIdClass(JavaResourcePersistentType resourceClass) {
		return getJpaFactory().buildJavaPersistentType(this, resourceClass);
	}
	
	protected XmlTypeMapping getResourceTypeMapping() {
		return getTypeMapping().getResourceTypeMapping();
	}
	
	protected XmlIdClassContainer getResourceIdClassContainer() {
		return (XmlIdClassContainer) getResourceTypeMapping();
	}
	
	protected XmlClassReference getIdXmlClassRef() {
		return this.getResourceIdClassContainer().getIdClass();
	}
	
	protected void addIdClassElement() {
		getResourceIdClassContainer().setIdClass(OrmFactory.eINSTANCE.createXmlClassReference());		
	}
	
	protected void removeIdClassElement() {
		getResourceIdClassContainer().setIdClass(null);
	}
	
	protected JavaResourcePersistentType getResourceIdClass() {
		XmlClassReference idXmlClassRef = this.getIdXmlClassRef();
		if (idXmlClassRef == null) {
			return null;
		}

		String className = idXmlClassRef.getClassName();
		if (className == null) {
			return null;
		}
		
		return this.getEntityMappings().resolveJavaResourcePersistentType(className);
	}
	
	protected EntityMappings getEntityMappings() {
		return (EntityMappings) getMappingFileRoot();
	}

	public char getIdClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public void update(JavaIdClassReference javaIdClassReference) {
		setDefaultIdClassName_(buildDefaultIdClassName(javaIdClassReference));
		setSpecifiedIdClassName_(buildSpecifiedIdClassName());
		updateIdClass();
	}
	
	protected void updateIdClass() {
		JavaResourcePersistentType resourceIdClass = getResourceIdClass();
		if (resourceIdClass == null) {
			setIdClass_(null);
		}
		else { 
			if (this.idClass == null || this.idClass.getResourcePersistentType() != resourceIdClass) {
				setIdClass_(buildIdClass(resourceIdClass));
			}
			else {
				this.idClass.update(resourceIdClass);
			}
		}
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getValidationTextRange() {
		XmlClassReference idXmlClassRef = getIdXmlClassRef();
		return (idXmlClassRef == null) ?
				this.getTypeMapping().getValidationTextRange() :
				idXmlClassRef.getClassNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// most validation is done "holistically" from the type mapping level
	}
}
