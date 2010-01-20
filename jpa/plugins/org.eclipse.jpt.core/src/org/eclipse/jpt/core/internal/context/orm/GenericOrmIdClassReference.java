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
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlIdClass;
import org.eclipse.jpt.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmIdClassReference
	extends AbstractXmlContextNode
	implements OrmIdClassReference
{
	protected String idClassName;

	protected JavaPersistentType idClass;
	
	
	public GenericOrmIdClassReference(OrmTypeMapping parent) {
		super(parent);
		this.idClassName = buildIdClassName();
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
	
	public String getIdClassName() {
		return this.idClassName;
	}
	
	public void setIdClassName(String newClassName) {
		String oldClassName = this.idClassName;
		this.idClassName = newClassName;
		if (valuesAreDifferent(oldClassName, newClassName)) {
			if (getIdClassElement() != null) {
				getIdClassElement().setClassName(newClassName);
				if (getIdClassElement().isUnset()) {
					removeIdClassElement();
				}
			}
			else if (newClassName != null) {
				addIdClassElement();
				getIdClassElement().setClassName(newClassName);
			}
		}
		firePropertyChanged(ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected void setIdClassName_(String newClassName) {
		String oldClassName = this.idClassName;
		this.idClassName = newClassName;
		firePropertyChanged(ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected String buildIdClassName() {
		XmlIdClass element = getIdClassElement();
		if (element != null) {
			return element.getClassName();
		}
		return null;
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
	
	protected XmlIdClass getIdClassElement() {
		return getResourceIdClassContainer().getIdClass();
	}
	
	protected void addIdClassElement() {
		getResourceIdClassContainer().setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());		
	}
	
	protected void removeIdClassElement() {
		getResourceIdClassContainer().setIdClass(null);
	}
	
	protected JavaResourcePersistentType getResourceIdClass() {
		XmlIdClass element = getIdClassElement();
		String className = (element == null) ?
				null : element.getClassName();
		if (className == null) {
			return null;
		}
		
		className = className.replace('$', '.');
		
		// first try to resolve using only the locally specified name...
		JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(className);
		if (jrpt != null) {
			return jrpt;
		}
		
		// ...then try to resolve by prepending the global package name
		String defaultPackage = getPersistentType().getDefaultPackage();
		className = defaultPackage + '.' +  className;
		return (defaultPackage == null) ?
				null : getJpaProject().getJavaResourcePersistentType(className);
	}
	
	public char getIdClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public void update() {
		setIdClassName_(buildIdClassName());
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
		XmlIdClass element = getIdClassElement();
		return (element == null) ?
			getTypeMapping().getValidationTextRange() : element.getClassNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		// TODO
	}
}
