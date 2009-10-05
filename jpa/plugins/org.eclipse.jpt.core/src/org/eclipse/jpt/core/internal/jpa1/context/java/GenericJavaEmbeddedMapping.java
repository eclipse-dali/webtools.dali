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
import org.eclipse.jpt.core.JptCorePlugin;
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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
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
		this.associationOverrideContainer.initialize(this.getResourcePersistentAttribute());
	}
	
	@Override
	protected void update() {
		super.update();
		this.associationOverrideContainer.update(this.getResourcePersistentAttribute());
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
	
	//only putting this in EmbeddedMapping since relationship mappings
	//defined within an embedded id class are not supported by the 2.0 spec.
	@Override
	public Iterator<String> allMappingNames() {
		if (getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			return new CompositeIterator<String>(
				getName(),
				embeddableAttributeMappingNames()
			);
		}
		return super.allMappingNames();
	}
	
	protected Iterator<String> embeddableAttributeMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allMappingNames();
					}
				}
			)
		) {
			@Override
			protected String transform(String next) {
				return getName() + '.' + next;
			}
		};
	}
	
	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return this.getEmbeddable().attributeMappings();
	}

	@Override
	public AttributeMapping resolveMappedBy(String mappedByName) {
		AttributeMapping resolvedMappedBy = super.resolveMappedBy(mappedByName);
		if (resolvedMappedBy != null) {
			return resolvedMappedBy;
		}
		if (getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			int dotIndex = mappedByName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(mappedByName.substring(0, dotIndex))) {
					for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
						resolvedMappedBy = attributeMapping.resolveMappedBy(mappedByName.substring(dotIndex + 1));
						if (resolvedMappedBy != null) {
							return resolvedMappedBy;
						}
					}
				}
			}
		}
		return null;
	}
	
	//****************** AbstractJavaAttributeMapping implementation *******************
	@Override
	protected String[] buildSupportingAnnotationNames() {
		if (getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
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
