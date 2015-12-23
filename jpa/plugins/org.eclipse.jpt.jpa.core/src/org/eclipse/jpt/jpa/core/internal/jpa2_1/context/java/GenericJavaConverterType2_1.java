/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaManagedType;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ConverterAnnotation2_1;

public class GenericJavaConverterType2_1
	extends AbstractJavaManagedType<JpaContextModel>
	implements JavaConverterType2_1
{

	protected boolean autoApply;

	protected Boolean specifiedAutoApply;

	public GenericJavaConverterType2_1(JpaContextModel parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.specifiedAutoApply = this.buildSpecifiedAutoApply();
		this.autoApply = this.buildAutoApply();
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAutoApply_(this.buildSpecifiedAutoApply());
		this.setAutoApply(this.buildAutoApply());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
	}


	// ********** auto apply **********

	public boolean isAutoApply() {
		return this.autoApply;
	}

	protected void setAutoApply(boolean autoApply) {
		boolean old = this.autoApply;
		this.autoApply = autoApply;
		firePropertyChanged(AUTO_APPLY_PROPERTY, old, autoApply);
	}

	protected boolean buildAutoApply() {
		return this.specifiedAutoApply == null ? this.isDefaultAutoApply() : this.specifiedAutoApply.booleanValue();
	}

	public boolean isDefaultAutoApply() {
		return DEFAULT_AUTO_APPLY;
	}

	public Boolean getSpecifiedAutoApply() {
		return this.specifiedAutoApply;
	}

	public void setSpecifiedAutoApply(Boolean autoApply) {
		this.getConverterAnnotation().setAutoApply(autoApply);
		this.setSpecifiedAutoApply_(autoApply);
	}

	protected void setSpecifiedAutoApply_(Boolean autoApply) {
		Boolean old = this.specifiedAutoApply;
		this.specifiedAutoApply = autoApply;
		this.firePropertyChanged(SPECIFIED_AUTO_APPLY_PROPERTY, old, autoApply);
	}

	protected Boolean buildSpecifiedAutoApply() {
		ConverterAnnotation2_1 converterAnnotation = this.getConverterAnnotation();
		return converterAnnotation != null ? converterAnnotation.getAutoApply() : null;
	}


	// ********** converter annotation **********

	protected ConverterAnnotation2_1 getConverterAnnotation() {
		return (ConverterAnnotation2_1) this.resourceType.getAnnotation(ConverterAnnotation2_1.ANNOTATION_NAME);
	}


	// ********** ManagedType implementation **********

	public Class<ConverterType2_1> getManagedTypeType() {
		return ConverterType2_1.class;
	}
}
