/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaIdTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmIdTypeMapping<X extends XmlIdTypeMapping>
		extends AbstractOrmTypeMapping<X>
		implements OrmIdTypeMapping {
	
	protected final OrmIdClassReference idClassReference;
	
	protected IdTypeMapping superTypeMapping;
	
	protected AbstractOrmIdTypeMapping(OrmPersistentType parent, X xmlTypeMapping) {
		super(parent, xmlTypeMapping);
		this.idClassReference = buildIdClassReference();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.idClassReference.synchronizeWithResourceModel();
	}
	
	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.idClassReference.update(monitor);
		setSuperTypeMapping_(findSuperTypeMapping());
	}
	
	
	// ***** java *****
	
	@Override
	public JavaTypeMapping getJavaTypeMapping() {
		return (JavaIdTypeMapping) super.getJavaTypeMapping();
	}
	
	@Override
	public JavaIdTypeMapping getJavaTypeMappingForDefaults() {
		return (JavaIdTypeMapping) super.getJavaTypeMappingForDefaults();
	}
	
	
	// ***** primary key *****
	
	public OrmIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	protected OrmIdClassReference buildIdClassReference() {
		return new GenericOrmIdClassReference(this);
	}
	
	public XmlIdClassContainer getXmlIdClassContainer() {
		return getXmlTypeMapping();
	}
	
	public JavaIdClassReference getJavaIdClassReferenceForDefaults() {
		JavaIdTypeMapping javaTypeMapping = getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getIdClassReference();
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
		return findSuperTypeMapping(getFullyQualifiedParentClass());
	}
	
	protected IdTypeMapping findSuperTypeMapping(String superTypeName) {
		String thisTypeName = getPersistentType().getName();
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
		return (superResourceType == null) ? null : findSuperTypeMapping(superResourceType.getSuperclassQualifiedName());
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
	
	public InheritanceType getInheritanceStrategy() {
		return null;
	}

	public boolean isRootEntity() {
		return false;
	}

	public Entity getRootEntity() {
		return null;
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.idClassReference.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validatePrimaryKey(messages, reporter);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		this.buildPrimaryKeyValidator().validate(messages, reporter);
	}
	
	protected abstract JpaValidator buildPrimaryKeyValidator();
}
