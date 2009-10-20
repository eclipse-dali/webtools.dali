/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public abstract class AbstractOrmBaseEmbeddedMapping<T extends AbstractXmlEmbedded> extends AbstractOrmAttributeMapping<T> implements OrmBaseEmbeddedMapping
{
	protected OrmAttributeOverrideContainer attributeOverrideContainer;

	private Embeddable embeddable;//TODO hmm, why no property change notification for setting this??
	
	protected AbstractOrmBaseEmbeddedMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.embeddable = embeddableFor(this.getJavaPersistentAttribute());
		this.attributeOverrideContainer = getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, this, this.resourceAttributeMapping);
	}

	@Override
	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		this.attributeOverrideContainer.initializeFromAttributeOverrideContainer(oldMapping.getAttributeOverrideContainer());
	}

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}
	
	
	//************* AttributeOverrideContainer.Owner implementation ********************
	
	public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
		return new VirtualXmlAttributeOverrideColumn(overridableColumn);
	}

	public TypeMapping getOverridableTypeMapping() {
		return this.embeddable;
	}

	public Embeddable getEmbeddable() {
		return this.embeddable;
	}
	
	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		if (this.getEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return this.getEmbeddable().attributeMappings();
	}
	
	@Override
	public Iterator<String> allOverrideableMappingNames() {
		if (getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			return embeddableOverrideableAttributeMappingNames();
		}
		return super.allOverrideableMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAttributeMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allOverrideableMappingNames();
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

	@Override
	public Column resolveOverridenColumn(String attributeName, boolean isMetadataComplete) {
		if (getName() == null) {
			return null;
		}
		if (getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AttributeOverride override = getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getColumn();
					}
					if (this.getEmbeddable() == null) {
						return null;
					}
					return this.getEmbeddable().resolveOverridenColumn(attributeName, isMetadataComplete);
				}
			}
		}
		return null;
	}

	public JavaBaseEmbeddedMapping getJavaEmbeddedMapping() {
		JavaPersistentAttribute jpa = this.getJavaPersistentAttribute();
		if ((jpa != null) && this.valuesAreEqual(jpa.getMappingKey(), this.getKey())) {
			return (JavaBaseEmbeddedMapping) jpa.getMapping();
		}
		return null;
	}

	
	@Override
	public void update() {
		super.update();
		this.embeddable = embeddableFor(this.getJavaPersistentAttribute());
		getAttributeOverrideContainer().update();
	}
	
	//************ static methods ************
	
	public static Embeddable embeddableFor(JavaPersistentAttribute javaPersistentAttribute) {
		return (javaPersistentAttribute == null) ? null : javaPersistentAttribute.getEmbeddable();
	}
}
