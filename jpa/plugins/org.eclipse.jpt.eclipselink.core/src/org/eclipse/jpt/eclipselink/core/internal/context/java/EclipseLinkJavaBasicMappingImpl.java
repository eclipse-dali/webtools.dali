/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaBasicMappingImpl
	extends AbstractJavaBasicMapping 
	implements EclipseLinkBasicMapping
{
	
	protected final EclipseLinkJavaMutable mutable;
	
	public EclipseLinkJavaBasicMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.mutable = new EclipseLinkJavaMutable(this);
	}

	@Override
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		JavaConverter javaConverter = super.buildSpecifiedConverter(converterType);
		if (javaConverter != null) {
			return javaConverter;
		}
		if (converterType == Convert.ECLIPSE_LINK_CONVERTER) {
			return new EclipseLinkJavaConvert(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	@Override
	protected String getResourceConverterType() {
		//check @Convert first, this is the order that EclipseLink searches
		if (this.resourcePersistentAttribute.getSupportingAnnotation(ConvertAnnotation.ANNOTATION_NAME) != null) {
			return Convert.ECLIPSE_LINK_CONVERTER;
		}
		return super.getResourceConverterType();
	}
	
	//************ EclipselinkJavaBasicMapping implementation ****************
	
	public Mutable getMutable() {
		return this.mutable;
	}

	
	//************ initialization/update ****************

	@Override
	protected void initialize() {
		super.initialize();
		this.mutable.initialize(this.resourcePersistentAttribute);
	}
	
	@Override
	protected void update() {
		super.update();
		this.mutable.update(this.resourcePersistentAttribute);
	}

	// ********** code assist **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.convertValueTouches(pos, astRoot)) {
			result = this.persistenceConvertersNames(filter);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	protected boolean convertValueTouches(int pos, CompilationUnit astRoot) {
		if (getResourceConvert() != null) {
			return this.getResourceConvert().valueTouches(pos, astRoot);
		}
		return false;
	}

	protected ConvertAnnotation getResourceConvert() {
		return (ConvertAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(ConvertAnnotation.ANNOTATION_NAME);
	}

	protected Iterator<String> persistenceConvertersNames() {
		if(this.getEclipseLinkPersistenceUnit().convertersSize() == 0) {
			return EmptyIterator.<String> instance();
		}
		return (Iterator<String>)CollectionTools.iterator(this.getEclipseLinkPersistenceUnit().uniqueConverterNames());
	}

	private Iterator<String> convertersNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.persistenceConvertersNames(), filter);
	}

	protected Iterator<String> persistenceConvertersNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.convertersNames(filter));
	}

	protected EclipseLinkPersistenceUnit getEclipseLinkPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) this.getPersistenceUnit();
	}
	
	//************ validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mutable.validate(messages, reporter, astRoot);
	}
}
