/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class JavaEclipseLinkConverter<A extends EclipseLinkNamedConverterAnnotation>
	extends AbstractJavaJpaContextNode
	implements EclipseLinkConverter
{
	protected final A converterAnnotation;

	protected String name;


	protected JavaEclipseLinkConverter(JavaJpaContextNode parent, A converterAnnotation) {
		super(parent);
		this.converterAnnotation = converterAnnotation;
		this.name = converterAnnotation.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.converterAnnotation.getName());
	}

	@Override
	public void update() {
		super.update();
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.converterAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** misc **********

	@Override
	public JavaEclipseLinkConverterContainer getParent() {
		return (JavaEclipseLinkConverterContainer) super.getParent();
	}

	public A getConverterAnnotation() {
		return this.converterAnnotation;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	public char getEnclosingTypeSeparator() {
		return '.';
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateName(messages, astRoot);
	}

	protected void validateName(List<IMessage> messages, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(this.name)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY, 
					EclipseLinkJpaValidationMessages.CONVERTER_NAME_UNDEFINED, 
					EMPTY_STRING_ARRAY,
					this,
					this.getNameTextRange(astRoot)
				)
			);
			return;
		}

		if (ArrayTools.contains(EclipseLinkConvert.RESERVED_CONVERTER_NAMES, this.name)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.RESERVED_CONVERTER_NAME,
					EMPTY_STRING_ARRAY,
					this,
					this.getNameTextRange(astRoot)
				)
			);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.converterAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot){
		return this.getValidationTextRange(this.getConverterAnnotation().getNameTextRange(astRoot), astRoot);
	}

	public boolean isEquivalentTo(JpaNamedContextNode node) {
		return (this != node) &&
				(this.getType() == node.getType()) &&
				Tools.valuesAreEqual(this.name, node.getName());
	}

	// ********** metadata conversion **********

	public abstract void convertTo(OrmEclipseLinkConverterContainer ormConverterContainer);
	
	public abstract void delete();
}
