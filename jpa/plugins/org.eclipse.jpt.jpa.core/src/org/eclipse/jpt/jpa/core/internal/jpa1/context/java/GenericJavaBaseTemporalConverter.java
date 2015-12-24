/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.resource.java.BaseTemporalAnnotation;

public class GenericJavaBaseTemporalConverter
	extends AbstractJavaConverter
	implements JavaBaseTemporalConverter
{
	protected final BaseTemporalAnnotation temporalAnnotation;

	protected TemporalType temporalType;


	public GenericJavaBaseTemporalConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseTemporalAnnotation temporalAnnotation) {
		super(parentAdapter);
		this.temporalAnnotation = temporalAnnotation;
		this.temporalType = this.buildTemporalType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setTemporalType_(this.buildTemporalType());
	}


	// ********** temporal type **********

	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		if (ObjectTools.notEquals(this.temporalType, temporalType)) {
			this.temporalAnnotation.setValue(TemporalType.toJavaResourceModel(temporalType));
			this.removeTemporalAnnotationIfUnset();
			this.setTemporalType_(temporalType);
		}
	}

	protected void setTemporalType_(TemporalType temporalType) {
		TemporalType old = this.temporalType;
		this.temporalType = temporalType;
		this.firePropertyChanged(TEMPORAL_TYPE_PROPERTY, old, temporalType);
	}

	protected TemporalType buildTemporalType() {
		return TemporalType.fromJavaResourceModel(this.temporalAnnotation.getValue());
	}


	// ********** misc **********

	public Class<BaseTemporalConverter> getConverterType() {
		return BaseTemporalConverter.class;
	}

	public BaseTemporalAnnotation getConverterAnnotation() {
		return this.temporalAnnotation;
	}

	protected void removeTemporalAnnotationIfUnset() {
		if (this.temporalAnnotation.isUnset()) {
			this.getResourceAttribute().removeAnnotation(this.temporalAnnotation.getAnnotationName());
		}
	}
}
