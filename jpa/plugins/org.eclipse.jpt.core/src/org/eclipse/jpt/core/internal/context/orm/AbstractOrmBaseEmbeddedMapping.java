/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


public abstract class AbstractOrmBaseEmbeddedMapping<T extends AbstractXmlEmbedded> extends AbstractOrmAttributeMapping<T> implements OrmBaseEmbeddedMapping
{
	protected OrmAttributeOverrideContainer attributeOverrideContainer;

	private Embeddable targetEmbeddable;//TODO hmm, why no property change notification for setting this??
	
	protected AbstractOrmBaseEmbeddedMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.targetEmbeddable = embeddableFor(this.getJavaPersistentAttribute());
		this.attributeOverrideContainer = getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, new AttributeOverrideContainerOwner(), this.resourceAttributeMapping);
	}

	@Override
	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		super.initializeFromOrmBaseEmbeddedMapping(oldMapping);
		this.attributeOverrideContainer.initializeFromAttributeOverrideContainer(oldMapping.getAttributeOverrideContainer());
	}

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected JavaAttributeOverride getJavaAttributeOverrideNamed(String attributeName) {
		if (getJavaEmbeddedMapping() != null) {
			return getJavaEmbeddedMapping().getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}	
	
	public Embeddable getTargetEmbeddable() {
		return this.targetEmbeddable;
	}
	
	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		if (this.getTargetEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return this.getTargetEmbeddable().attributeMappings();
	}
	
	@Override
	public Iterator<String> allOverrideableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAttributeMappingNames() :
				super.allOverrideableAttributeMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAttributeMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allOverrideableAttributeMappingNames();
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
	public Iterator<String> allOverrideableAssociationMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAssociationMappingNames() :
				super.allOverrideableAssociationMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAssociationMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allOverrideableAssociationMappingNames();
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
	public Column resolveOverridenColumn(String attributeName) {
		if (getName() == null) {
			return null;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AttributeOverride override = getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getColumn();
					}
					if (this.getTargetEmbeddable() == null) {
						return null;
					}
					return this.getTargetEmbeddable().resolveOverridenColumn(attributeName);
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
		this.targetEmbeddable = embeddableFor(this.getJavaPersistentAttribute());
		getAttributeOverrideContainer().update();
	}
	
	//************ static methods ************
	
	public static Embeddable embeddableFor(JavaPersistentAttribute javaPersistentAttribute) {
		return (javaPersistentAttribute == null) ? null : javaPersistentAttribute.getEmbeddable();
	}
	
	
	//********** AttributeOverrideContainer.Owner implementation *********	
	
	class AttributeOverrideContainerOwner implements OrmAttributeOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTargetEmbeddable();
		}
		
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmBaseEmbeddedMapping.this.getTypeMapping();
		}
		
		public Column resolveOverridenColumn(String attributeOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaAttributeOverride = getJavaAttributeOverrideNamed(attributeOverrideName);
				if (javaAttributeOverride != null && !javaAttributeOverride.isVirtual()) {
					return javaAttributeOverride.getColumn();
				}
			}
			TypeMapping overridableTypeMapping = getOverridableTypeMapping();
			Column column = null;
			if (overridableTypeMapping != null) {
				for (TypeMapping typeMapping : CollectionTools.iterable(overridableTypeMapping.inheritanceHierarchy())) {
					column = typeMapping.resolveOverridenColumn(attributeOverrideName);
					if (column != null) {
						return column;
					}
				}
			}
			return column;
		}
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			return new VirtualXmlAttributeOverrideColumn(overridableColumn);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		public boolean tableIsAllowed() {
			return true;
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
	}
}
