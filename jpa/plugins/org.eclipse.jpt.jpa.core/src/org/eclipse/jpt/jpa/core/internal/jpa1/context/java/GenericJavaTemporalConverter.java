/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;

public class GenericJavaTemporalConverter
	extends AbstractJavaConverter
	implements JavaTemporalConverter
{
	protected final TemporalAnnotation temporalAnnotation;

	protected TemporalType temporalType;


	public GenericJavaTemporalConverter(JavaAttributeMapping parent, TemporalAnnotation temporalAnnotation, JavaConverter.Owner owner) {
		super(parent, owner);
		this.temporalAnnotation = temporalAnnotation;
		this.temporalType = this.buildTemporalType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setTemporalType_(this.buildTemporalType());
	}


	// ********** temporal type **********

	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		if (this.valuesAreDifferent(this.temporalType, temporalType)) {
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

	public Class<? extends Converter> getType() {
		return TemporalConverter.class;
	}

	public TemporalAnnotation getConverterAnnotation() {
		return this.temporalAnnotation;
	}

	protected void removeTemporalAnnotationIfUnset() {
		if (this.temporalAnnotation.isUnset()) {
			this.temporalAnnotation.removeAnnotation();
		}
	}
}
