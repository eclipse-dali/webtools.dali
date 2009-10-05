/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaBaseEmbeddedMapping<T extends Annotation>
	extends AbstractJavaAttributeMapping<T>
	implements JavaBaseEmbeddedMapping
{
	protected final JavaAttributeOverrideContainer attributeOverrideContainer;

	private Embeddable embeddable;

	protected AbstractJavaBaseEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.attributeOverrideContainer = this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this);
	}

	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	public PersistentType getOverridablePersistentType() {
		return (this.embeddable == null) ? null : this.embeddable.getPersistentType();
	}
	
	//****************** JavaAttributeMapping implementation *******************

	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES);
	}

	public Embeddable getEmbeddable() {
		return this.embeddable;
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.attributeOverrideContainer.initialize(this.getResourcePersistentAttribute());
		this.embeddable = this.getPersistentAttribute().getEmbeddable();
	}
	
	@Override
	protected void update() {
		super.update();
		this.embeddable = this.getPersistentAttribute().getEmbeddable();
		this.attributeOverrideContainer.update(this.getResourcePersistentAttribute());
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new TransformationIterator<ColumnMapping, String>(this.allOverridableAttributes()) {
			@Override
			protected String transform(ColumnMapping attribute) {
				return attribute.getName();
			}
		};
	}

	public Iterator<ColumnMapping> allOverridableAttributes() {
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<AttributeMapping, ColumnMapping>(this.getEmbeddable().attributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return o.isOverridableAttributeMapping();
			}
		};
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		
		result = getAttributeOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	//******** Validation ******************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		getAttributeOverrideContainer().validate(messages, reporter, astRoot);
	}
}