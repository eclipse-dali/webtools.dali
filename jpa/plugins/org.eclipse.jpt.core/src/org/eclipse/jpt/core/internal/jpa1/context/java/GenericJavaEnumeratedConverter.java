/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaEnumeratedConverter extends AbstractJavaJpaContextNode
	implements JavaEnumeratedConverter
{
	private EnumType specifiedEnumType;
	
	private JavaResourcePersistentAttribute resourcePersistenceAttribute;
	
	public GenericJavaEnumeratedConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	public String getType() {
		return Converter.ENUMERATED_CONVERTER;
	}
	
	protected String getAnnotationName() {
		return EnumeratedAnnotation.ANNOTATION_NAME;
	}
	
	public void addToResourceModel() {
		this.resourcePersistenceAttribute.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistenceAttribute.removeAnnotation(getAnnotationName());
	}
	
	public EnumType getEnumType() {
		return getSpecifiedEnumType() == null ? getDefaultEnumType() : getSpecifiedEnumType();
	}
	
	public EnumType getDefaultEnumType() {
		return EnumeratedConverter.DEFAULT_ENUM_TYPE;
	}
	
	public EnumType getSpecifiedEnumType() {
		return this.specifiedEnumType;
	}
	
	public void setSpecifiedEnumType(EnumType newSpecifiedEnumType) {
		EnumType oldSpecifiedEnumType = this.specifiedEnumType;
		this.specifiedEnumType = newSpecifiedEnumType;
		this.getResourceEnumerated().setValue(EnumType.toJavaResourceModel(newSpecifiedEnumType));
		firePropertyChanged(EnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY, oldSpecifiedEnumType, newSpecifiedEnumType);
	}

	protected void setSpecifiedEnumType_(EnumType newSpecifiedEnumType) {
		EnumType oldSpecifiedEnumType = this.specifiedEnumType;
		this.specifiedEnumType = newSpecifiedEnumType;
		firePropertyChanged(EnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY, oldSpecifiedEnumType, newSpecifiedEnumType);
	}

	protected EnumeratedAnnotation getResourceEnumerated() {
		return (EnumeratedAnnotation) this.resourcePersistenceAttribute.
				getAnnotation(getAnnotationName());
	}
	
	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistenceAttribute = jrpa;
		this.specifiedEnumType = this.specifiedEnumType(getResourceEnumerated());
	}	
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistenceAttribute = jrpa;
		this.setSpecifiedEnumType_(this.specifiedEnumType(getResourceEnumerated()));
	}	
	
	protected EnumType specifiedEnumType(EnumeratedAnnotation resourceEnumerated) {
		return resourceEnumerated == null ? null : EnumType.fromJavaResourceModel(resourceEnumerated.getValue());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getResourceEnumerated().getTextRange(astRoot);
	}
}
