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
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaTemporalConverter extends AbstractJavaJpaContextNode
	implements JavaTemporalConverter
{
	private TemporalType temporalType;
	
	private JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public GenericJavaTemporalConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}
	
	public String getType() {
		return Converter.TEMPORAL_CONVERTER;
	}
	
	protected String getAnnotationName() {
		return TemporalAnnotation.ANNOTATION_NAME;
	}
	
	public void addToResourceModel() {
		this.resourcePersistentAttribute.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentAttribute.removeAnnotation(getAnnotationName());
	}
	
	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType newTemporalType) {
		TemporalType oldTemporalType = this.temporalType;
		this.temporalType = newTemporalType;
		this.getResourceTemporal().setValue(TemporalType.toJavaResourceModel(newTemporalType));
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}
	
	protected void setTemporalType_(TemporalType newTemporalType) {
		TemporalType oldTemporalType = this.temporalType;
		this.temporalType = newTemporalType;
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}


	protected TemporalAnnotation getResourceTemporal() {
		return (TemporalAnnotation) this.resourcePersistentAttribute.
				getAnnotation(getAnnotationName());
	}

	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.temporalType = this.temporalType(getResourceTemporal());
	}	
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.setTemporalType_(this.temporalType(getResourceTemporal()));
	}	
	
	protected TemporalType temporalType(TemporalAnnotation resourceTemporal) {
		return resourceTemporal == null ? null : TemporalType.fromJavaResourceModel(resourceTemporal.getValue());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getResourceTemporal().getTextRange(astRoot);
	}
}
