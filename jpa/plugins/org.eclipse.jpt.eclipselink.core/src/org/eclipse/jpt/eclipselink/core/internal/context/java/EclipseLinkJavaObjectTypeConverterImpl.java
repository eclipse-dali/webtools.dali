/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkNamedConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;

public class EclipseLinkJavaObjectTypeConverterImpl extends AbstractJavaJpaContextNode implements EclipseLinkJavaObjectTypeConverter
{	
	private JavaResourcePersistentMember resourcePersistentMember;
	
	private String name;
	
	private String dataType;
	
	private String objectType;
	
	public EclipseLinkJavaObjectTypeConverterImpl(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		super(parent);
		this.initialize(jrpm);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	public String getType() {
		return EclipseLinkNamedConverter.OBJECT_TYPE_CONVERTER;
	}

	protected String getAnnotationName() {
		return ObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}
		
	public void addToResourceModel() {
		this.resourcePersistentMember.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentMember.removeAnnotation(getAnnotationName());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConverter().getTextRange(astRoot);
	}

	protected ObjectTypeConverterAnnotation getResourceConverter() {
		return (ObjectTypeConverterAnnotation) this.resourcePersistentMember.getAnnotation(getAnnotationName());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getResourceConverter().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		getResourceConverter().setDataType(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	protected void setDataType_(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		getResourceConverter().setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}

	protected void initialize(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		ObjectTypeConverterAnnotation resourceConverter = getResourceConverter();
		this.name = this.name(resourceConverter);
		this.dataType = this.dataType(resourceConverter);
		this.objectType = this.objectType(resourceConverter);
	}
	
	public void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		ObjectTypeConverterAnnotation resourceConverter = getResourceConverter();
		this.setName_(this.name(resourceConverter));
		this.setDataType_(this.dataType(resourceConverter));
		this.setObjectType_(this.objectType(resourceConverter));
	}

	protected String name(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getName();
	}

	protected String dataType(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getDataType();
	}

	protected String objectType(ObjectTypeConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getObjectType();
	}

}
