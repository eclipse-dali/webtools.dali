/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaIdTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaIdTypeMapping<A extends Annotation>
		extends AbstractJavaTypeMapping<A>
		implements JavaIdTypeMapping {
	
	protected IdTypeMapping superTypeMapping;
	
	protected final JavaIdClassReference idClassReference;
	
	
	protected AbstractJavaIdTypeMapping(JavaPersistentType parent, A mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.idClassReference = buildIdClassReference();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.idClassReference.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		setSuperTypeMapping_(findSuperTypeMapping());
		this.idClassReference.update();
	}
	
	
	// ***** primary key ****
	
	public JavaIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	protected JavaIdClassReference buildIdClassReference() {
		return new GenericJavaIdClassReference(this);
	}
	
	public String getPrimaryKeyClassName() {
		String idClassName = getIdClassReference().getFullyQualifiedIdClassName();
		if (StringTools.isNotBlank(idClassName)) {
			return idClassName;
		}
		
		AttributeMapping idMapping = getIdAttributeMapping();
		if (idMapping != null) {
			return idMapping.getPersistentAttribute().getTypeName();
		}
		
		return null;
	}
	
	
	// ***** inheritance *****
	
	public IdTypeMapping getSuperTypeMapping() {
		return this.superTypeMapping;
	}
	
	protected void setSuperTypeMapping_(IdTypeMapping typeMapping) {
		IdTypeMapping old = this.superTypeMapping;
		this.superTypeMapping = typeMapping;
		firePropertyChanged(SUPER_TYPE_MAPPING_PROPERTY, old, typeMapping);
	}
	
	protected IdTypeMapping findSuperTypeMapping() {
		return findSuperTypeMapping(getJavaResourceType());
	}
	
	protected IdTypeMapping findSuperTypeMapping(JavaResourceType resourceType) {
		String thisTypeName = getJavaResourceType().getTypeBinding().getQualifiedName();
		String superTypeName = resourceType.getSuperclassQualifiedName();
		// short circuit if there is no super type or if the super type is this type
		if (superTypeName == null 
				|| ObjectTools.equals(thisTypeName, superTypeName)) {
			return null;
		}
		IdTypeMapping typeMapping = findTypeMapping(superTypeName);
		if (typeMapping != null) {
			return typeMapping;
		}
		JavaResourceType superResourceType = findResourceType(superTypeName);
		return (superResourceType == null) ? null : findSuperTypeMapping(superResourceType);
	}
	
	protected JavaResourceType findResourceType(String typeName) {
		return (JavaResourceType) getJpaProject().getJavaResourceType(
				typeName, JavaResourceAnnotatedElement.AstNodeType.TYPE);
	}
	
	protected IdTypeMapping findTypeMapping(String typeName) {
		return getPersistenceUnit().getIdTypeMapping(typeName);
	}
	
	public Iterable<IdTypeMapping> getInheritanceHierarchy() {
		return buildInheritanceHierarchy(this);
	}
	
	public Iterable<IdTypeMapping> getAncestors() {
		return (this.superTypeMapping == null) ?
				IterableTools.<IdTypeMapping>emptyIterable() :
				buildInheritanceHierarchy(this.superTypeMapping);
	}
	
	protected Iterable<IdTypeMapping> buildInheritanceHierarchy(IdTypeMapping start) {
		// using a chain iterable to traverse up the inheritance tree
		return ObjectTools.chain(start, new SuperTypeMappingTransformer(this));
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validatePrimaryKey(messages, reporter);
		this.idClassReference.validate(messages, reporter);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		buildPrimaryKeyValidator().validate(messages, reporter);
	}
	
	protected abstract JpaValidator buildPrimaryKeyValidator();
}
