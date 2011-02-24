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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.AbstractJavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaTemporalConverter
	extends AbstractJavaConverter
	implements JavaTemporalConverter
{
	protected final TemporalAnnotation temporalAnnotation;

	protected TemporalType temporalType;


	public GenericJavaTemporalConverter(JavaAttributeMapping parent, TemporalAnnotation temporalAnnotation) {
		super(parent);
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

	@Override
	protected String getAnnotationName() {
		return TemporalAnnotation.ANNOTATION_NAME;
	}

	protected void removeTemporalAnnotationIfUnset() {
		if (this.temporalAnnotation.isUnset()) {
			this.temporalAnnotation.removeAnnotation();
		}
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateAttributeTypeWithTemporal(messages, reporter, astRoot);
	}

	protected void validateAttributeTypeWithTemporal(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (this.getAttributeMapping().getKey() == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			String typeName = ((AbstractJavaElementCollectionMapping2_0) this.getAttributeMapping()).getFullyQualifiedTargetClass();
			if (!ArrayTools.contains(TEMPORAL_MAPPING_SUPPORTED_TYPES, typeName)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE,
								new String[] {},
								this,
								this.getValidationTextRange(astRoot)
						)
				);
			}
		} else {
			String typeName = this.getAttributeMapping().getPersistentAttribute().getTypeName();
			if (!ArrayTools.contains(TEMPORAL_MAPPING_SUPPORTED_TYPES, typeName)) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE,
								new String[] {},
								this,
								this.getValidationTextRange(astRoot)
						)
				);
			}

		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.temporalAnnotation.getTextRange(astRoot);
	}
}
