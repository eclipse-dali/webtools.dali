/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaManyToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToOneMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaManyToOneMapping
	extends AbstractJavaManyToOneMapping
	implements EclipseLinkManyToOneMapping2_0, EclipseLinkJavaConvertibleMapping
{
	protected final EclipseLinkJavaJoinFetch joinFetch;
	
	protected final EclipseLinkJavaConverterContainer converterContainer;


	public EclipseLinkJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new EclipseLinkJavaJoinFetch(this);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.joinFetch.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.joinFetch.update(monitor);
		this.converterContainer.update(monitor);
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}


	// ********** converters **********

	public EclipseLinkJavaConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkJavaConverterContainer buildConverterContainer() {
		return new EclipseLinkJavaConverterContainerImpl(this);
	}

	// ********** converter container parent **********

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean supportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.joinFetch.validate(messages, reporter);
	}
}
