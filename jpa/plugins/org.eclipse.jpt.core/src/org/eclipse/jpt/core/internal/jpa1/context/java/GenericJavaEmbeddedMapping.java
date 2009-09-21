/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public class GenericJavaEmbeddedMapping
	extends AbstractJavaBaseEmbeddedMapping<EmbeddedAnnotation>
	implements JavaEmbeddedMapping2_0
{
	protected final JavaAssociationOverrideContainer associationOverrideContainer;

	public GenericJavaEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.associationOverrideContainer = ((JpaFactory2_0) this.getJpaFactory()).buildJavaAssociationOverrideContainer(this, this);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.associationOverrideContainer.initialize(this.resourcePersistentAttribute);
	}
	
	@Override
	protected void update() {
		super.update();
		this.associationOverrideContainer.update(this.resourcePersistentAttribute);
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.associationOverrideContainer.postUpdate();
	}
	
	//****************** JavaAttributeMapping implementation *******************

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EmbeddedAnnotation.ANNOTATION_NAME;
	}
	
	//****************** AbstractJavaAttributeMapping implementation *******************
	@Override
	protected String[] buildSupportingAnnotationNames() {
		if (getJpaPlatformVersion().is2_0Compatible()) {
			return ArrayTools.addAll(
				super.buildSupportingAnnotationNames(),
				JPA.ASSOCIATION_OVERRIDE,
				JPA.ASSOCIATION_OVERRIDES);
			}
		return super.buildSupportingAnnotationNames();
	}
	
	
	//****************** association overrides - 2.0 supports this *******************

	public JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}
	
	public Iterator<String> allOverridableAssociationNames() {
		return new TransformationIterator<RelationshipMapping, String>(this.allOverridableAssociations()) {
			@Override
			protected String transform(RelationshipMapping attribute) {
				return attribute.getName();
			}
		};
	}

	//TODO hmm, is this only for 2.0???
	public Iterator<RelationshipMapping> allOverridableAssociations() {
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<AttributeMapping, RelationshipMapping>(this.getEmbeddable().attributeMappings()) {
			@Override
			protected boolean accept(AttributeMapping o) {
				return o.isOverridableAssociationMapping();
			}
		};
	}

	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		
		result = getAssociationOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
}
